package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.util.HashMap;

import static androidx.core.content.ContextCompat.startActivity;

public class SignIn extends AppCompatActivity {
    String Tag = "로그";
    private ImageButton btn_custom_login;
    private ImageButton btn_custom_logout;
    String TAG = "출력";
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    int chk;
    //Firebase
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        chk = 1;
        btn_custom_login = (ImageButton) findViewById(R.id.btn_custom_login);
        btn_custom_logout = (ImageButton) findViewById(R.id.btn_custom_logout);

        // Firebase
      //  mDatabase = FirebaseDatabase.getInstance().getReference();

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag, "MainActivity - onLoginButtonClicked() called");
                session.open(AuthType.KAKAO_LOGIN_ALL, SignIn.this);

            }
        });

        btn_custom_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(SignIn.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void redirectHomeActivity() {
        startActivity(new Intent(this, Map.class));
        finish();
    }

    public class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();

        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }


        // 사용자 정보 요청
        public void requestMe() {

            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {


                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            int result = errorResult.getErrorCode();
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                            if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {

                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                            UserAccount kakaoAccount = result.getKakaoAccount();
                            if (kakaoAccount != null) {
                                // 이메일
                                String email = kakaoAccount.getEmail();

                                if (email != null) {
                                    Log.i("KAKAO_API", "email: " + email);

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                    Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                    Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }

                                saveDB(result.getId(), profile.getNickname(), email);
                              //  print();
                                //redirectHomeActivity();
                            }
                        }
                    });
        }

        // DB 연동 - 계정정보 갱신
        private void saveDB(final long id, final String name, final String email) {
            mDatabase.collection("UserProfile")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.contains(Long.toString(id))) {
                                        chk=0;
                                        redirectHomeActivity();

                                    }
//                                    if(true) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData().containsValue("isTel"));
//                                    }
                                 //   Log.d(TAG, document.getId() + " => " + document.getData().containsValue("isTel"));
                                }
                                if(chk == 1) {
                                    HashMap user = new HashMap<>();
                                    user.put("ID", id);
                                    user.put("name", name);
                                    user.put("email", email);
                                    user.put("Tel", "");
                                    user.put("isTel", false);
                                    user.put("isMGR", false);
                                    mDatabase.collection("UserProfile")
                                            .document(id + "")
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            });
                                    redirectPhoneActivity(id, name);
                               //     Log.d(TAG, document.getId() + " => " + document.getData().containsValue("isTel"));
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });



        }
        private void redirectHomeActivity() {
            startActivity(new Intent(SignIn.this, Map.class));
            finish();
        }
        private void redirectPhoneActivity(long id, String name) {

            Intent intent = new Intent(SignIn.this,PhoneActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("name",name);
            startActivity(intent);
        }

    }
}