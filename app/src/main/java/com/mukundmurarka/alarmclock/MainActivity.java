package com.mukundmurarka.alarmclock;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button timepick , save;
    TextView time;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int userYear,userMonth,userDay,userHour,userMinute;
    SimpleDateFormat format;
    java.sql.Time timeValue;

    Context context;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timepick=findViewById(R.id.button);
        time=  findViewById(R.id.textView);
         save =  findViewById(R.id.button2);


        context = MainActivity.this;
        calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog tt = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        try
                        {
                            String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                            format = new SimpleDateFormat("HH:mm");

                            timeValue = new java.sql.Time(format.parse(dtStart).getTime());

                            time.setText(String.valueOf(timeValue));
                            calendar.set(Calendar.HOUR_OF_DAY , hourOfDay);
                            calendar.set(Calendar.MINUTE , minute);
                            calendar.set(Calendar.SECOND,0);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, userHour,userMinute, DateFormat.is24HourFormat(MainActivity.this));
                tt.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                     AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(MainActivity.this, Alarm.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                         Toast.makeText(MainActivity.this, "add successfully", Toast.LENGTH_LONG).show();

                    Toast.makeText(MainActivity.this, "alarm add successfully", Toast.LENGTH_LONG).show();
                }

        });

    }
}
