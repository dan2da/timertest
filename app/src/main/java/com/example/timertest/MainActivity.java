package com.example.timertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "MainActivity";

    private boolean mTimerRunning;

    //타이머 시작 종류 버튼
    private Button startBTN;
    private Button stopBTN;
    //타이머 화면
    private TextView timeView;

    private Switch powerswitch;
    private Switch powerupswitch;
    private ConstraintLayout timelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);


        timeView = (TextView) findViewById(R.id.timeView);
        powerswitch =(Switch) findViewById(R.id.powerswitch);
        powerupswitch = (Switch) findViewById(R.id.powerupswitch);
        timelayout = (ConstraintLayout)findViewById(R.id.timelayout);
        stopBTN = (Button)findViewById(R.id.stopBTN);
        startBTN= (Button)findViewById(R.id.startBTN);

        // TODO : 타이머 돌릴 총시간
        String conversionTime = "000010";

        // 카운트 다운 시작
        countDown(conversionTime);

        //스위치
        powerswitch.setOnCheckedChangeListener(this);
        powerupswitch.setOnCheckedChangeListener(this);


    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
        switch (compoundButton.getId()){
            case(R.id.powerswitch):
                if(ischecked) {
                    timelayout.setVisibility(View.VISIBLE);
                    powerupswitch.setEnabled(false);
                }
                else {
                    timelayout.setVisibility(View.INVISIBLE);
                    powerupswitch.setEnabled(true);
                }
        }
    }






    private void countDown(String time) {
        long conversionTime = 0;

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        //1시간 = 3600000 14시간 =50400000
        new CountDownTimer(3600000, 1000) {

            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

                // 시
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                //분을 위한 나머지 = h_na
                long h_na = millisUntilFinished % (60 * 60 * 1000);
                //계산의 몫, 나머지 확인을 위한 로그
                Log.i(TAG, hour + "-" + h_na);

                //h_na의 몫 = 분
                String min = String.valueOf(h_na / (60 * 1000));

                //h_na의 나머지 = 분
                String second = String.valueOf((h_na % (60 * 1000)) / (1000));
                Log.i(TAG, hour + "-" + min + "-" + second);


                // 시간이 한자리면 0을 붙인다
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                timeView.setText(hour + ":" + min + ":" + second);
            }

            @Override
            public void onFinish() {
                timeView.setText("시간종료!");


            }
        }.start();


    }





}



