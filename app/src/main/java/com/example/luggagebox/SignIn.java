package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class SignIn extends AppCompatActivity {
    String Tag = "로그";
    private ImageButton btn_custom_login;
    private ImageButton btn_custom_logout;

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

      btn_custom_login = (ImageButton) findViewById(R.id.btn_custom_login);
      btn_custom_logout = (ImageButton) findViewById(R.id.btn_custom_logout);

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

}
