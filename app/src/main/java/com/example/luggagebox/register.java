package com.example.luggagebox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class register extends AppCompatActivity implements View.OnClickListener{
    private Button reButton;
    EditText en_kind;
    EditText editTextDate;
    EditText editTextDate2;
    EditText editTextDate3;
    EditText editTextDate4;
    String en_k, editDate, editDate2, editDate3, editDate4;
    RadioGroup en_size;
    TextView address;
    String address_name;
    String status;
    //Firebase
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    //DB 변수
    String bkind;
    String bSzie;
    String sDate;
    String fData;
    String sTime;
    String fTime;



    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2) {
            myCalendar2.set(Calendar.YEAR, year2);
            myCalendar2.set(Calendar.MONTH, month2);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);
            updateLabe2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reButton = (Button) findViewById(R.id.btn_register);
        en_kind = (EditText) findViewById(R.id.en_kind);
        en_size = (RadioGroup) findViewById(R.id.RB);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate2 = (EditText) findViewById(R.id.editTextDate2);
        editTextDate3 = (EditText) findViewById(R.id.editTextDate3);
        editTextDate4 = (EditText) findViewById(R.id.editTextDate4);
        address = (TextView) findViewById(R.id.address);

        Intent address_info = getIntent();
        address_name = address_info.getExtras().getString("address");
        address.setText(address_name);
        EditText et_Date = (EditText) findViewById(R.id.editTextDate);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(register.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final EditText et_time = (EditText) findViewById(R.id.editTextDate2);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(register.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //시, 분 한자리 일 때 앞에 0 추가
                        String state = "";
                        String state1 = "";
                        if (selectedMinute < 10) {
                            state = "0";
                        }
                        if (selectedHour < 10) {
                            state1 = "0";
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(state1 + selectedHour + ":" + state + selectedMinute);
                    }
                }, hour, minute, true); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        EditText et_Date2 = (EditText) findViewById(R.id.editTextDate3);
        et_Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(register.this, myDatePicker2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final EditText et_time2 = (EditText) findViewById(R.id.editTextDate4);
        et_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(register.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //시, 분 한자리 일 때 앞에 0 추가
                        String state = "";
                        String state1 = "";
                        if (selectedMinute < 10) {
                            state = "0";
                        }
                        if (selectedHour < 10) {
                            state1 = "0";
                        }

                        // EditText에 출력할 형식 지정
                        et_time2.setText(state1 + selectedHour + ":" + state + selectedMinute);
                    }
                }, hour, minute, true); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy.MM.dd";    // 출력형식   2020.12.08
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.editTextDate);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabe2() {
        String myFormat2 = "yyyy.MM.dd";    // 출력형식   2020/12/08
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.KOREA);

        EditText et_date2 = (EditText) findViewById(R.id.editTextDate3);
        et_date2.setText(sdf2.format(myCalendar2.getTime()));
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_submit) {
            // 짐 종류
            en_k = en_kind.getText().toString();
            //시작 날짜
            editDate = editTextDate.getText().toString();
            editDate2 = editTextDate2.getText().toString();
            //끝 날짜
            editDate3 = editTextDate3.getText().toString();
            editDate4 = editTextDate4.getText().toString();

            //라디오 짐싸이즈
            int rb_id=en_size.getCheckedRadioButtonId();
            RadioButton rb=(RadioButton) findViewById(rb_id);
            String rb_d=rb.getText().toString();
            // saveDB(en_k, en_s,editDate,editDate2 );
            Intent register2_intent = new Intent(register.this, register2.class);
            register2_intent.putExtra("짐종류", en_k);
            register2_intent.putExtra("짐크기", rb_d);
            register2_intent.putExtra("시작날짜", editDate);
            register2_intent.putExtra("시작시간", editDate2);
            register2_intent.putExtra("끝날짜", editDate3);
            register2_intent.putExtra("끝시간", editDate4);
            register2_intent.putExtra("Address",address_name);


            // DB전송
            inputData(en_k, rb_d, editDate, editDate2, editDate3, editDate4);
            // 화면전환
            register2_intent.putExtra("status",status);
            startActivity(register2_intent);
        }
    }

    private void inputData(String bkind, String bsize, String sData, String sTime, String fData, String fTime) {
        HashMap lug_info = new HashMap<>();
        lug_info.put("ID", SignIn.id);
        lug_info.put("LugSize", bsize);
        lug_info.put("LugTitle", bkind);
        lug_info.put("ChkinDate", sData + " " + sTime);
        lug_info.put("ChkoutDate", fData + " " + fTime);
        lug_info.put("Address", address_name);
        lug_info.put("status", 0);
        status = "0";
    //    lug_info.put("ID", SignIn.id);
        mDatabase.collection("Luggagelist").document(SignIn.id+"")
                .set(lug_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }
}
