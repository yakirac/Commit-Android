package com.arik.commit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ReminderTimeActivity extends Activity {

    private String time = "";
    private TimePicker tpicker;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_reminder);

        Typeface tf = Typeface.createFromAsset(this.getApplication().getAssets(), "fonts/Rokkitt.otf");

        TextView wt =(TextView)this.findViewById(R.id.rtime);
        wt.setTypeface(tf);
        Button dr = (Button)this.findViewById(R.id.dontr);
        dr.setTypeface(tf);
        dr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(v.getContext(), "Okay. We won't remind you.", Toast.LENGTH_SHORT).show();
                Bundle dremind = new Bundle();
                dremind.putString("reminder", "No Reminder");
                Intent nt = new Intent();
                nt.putExtra("reminder", dremind);
                setResult(RESULT_OK, nt);
                //Log.d("Reminder Time Activity", "In dr Reminder is: " + dremind.getString("reminder"));
                finish();
            }
        });

        TimePicker tp = (TimePicker)this.findViewById(R.id.timePicker);
        tpicker = tp;
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                if(hourOfDay >= 12){
                    if(hourOfDay > 12){
                        hourOfDay = hourOfDay - 12;
                    }
                    if(minute < 10){
                        time = "" + hourOfDay + ":0" + minute + " PM";
                    }else {
                        time = "" + hourOfDay + ":" + minute + " PM";
                    }
                }else {
                    if(hourOfDay == 0){
                        hourOfDay = 12;
                    }
                    if(minute < 10){
                        time = "" + hourOfDay + ":0" + minute + " AM";
                    }else {
                        time = "" + hourOfDay + ":" + minute + " AM";
                    }
                }

            }
        });


        Button cancel = (Button)this.findViewById(R.id.cancel);
        cancel.setTypeface(tf);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(v.getContext(), "We are just going to close here.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button save = (Button)this.findViewById(R.id.save);
        save.setTypeface(tf);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(v.getContext(), "We are going to save and close here.", Toast.LENGTH_SHORT).show();
                setTime();
                Bundle rem = new Bundle();
                rem.putString("reminder", time);
                Intent nt = new Intent();
                nt.putExtra("reminder", rem);
                setResult(RESULT_OK, nt);
                //Log.d("Reminder Time Activity", "In save Reminder is: " + rem.getString("reminder"));
                startService(new Intent(ReminderTimeActivity.this, CommitNotifier.class));
                Log.d("Reminder Time Activity", "In save after the service is started");
                finish();
            }
        });


    }

    public void setTime(){
        String stime = "";
        int hour = tpicker.getCurrentHour();
        int minute = tpicker.getCurrentMinute();
        if(hour >= 12){
            if(hour > 12){
                hour = hour - 12;
            }
            if(minute < 10){
                stime = "" + hour + ":0" + minute + " PM";
            }else {
                stime = "" + hour + ":" + minute + " PM";
            }
        }else {
            if(hour == 0){
                hour = 12;
            }
            if(minute < 10){
                stime = "" + hour + ":0" + minute + " AM";
            }else {
                stime = "" + hour + ":" + minute + " AM";
            }
        }

        if(time.equals("")){
            time = stime;
        }

    }

}
