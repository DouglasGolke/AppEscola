package br.com.appescola.appescola.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import br.com.appescola.appescola.R;

public class CalendarioActivity extends AppCompatActivity{

    private static final String TAG = "CalendarioActivity";
    private CalendarView mCalendar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario_layout);
        mCalendar = (CalendarView) findViewById(R.id.calendarView);

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month+1) + "/" + year;
                Log.d(TAG, "OnSelectedDayChange: Date: "+ date);

                Intent intent = new Intent(CalendarioActivity.this, CadastroProfessorActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });
    }
}
