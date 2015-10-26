package com.arik.commit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends Activity{

    private TextView ctext;

    private TextView rtext;

    private String retxt;

    static final private int GET_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_commit);

        Bundle b = this.getIntent().getBundleExtra("text");

        TextView iw = (TextView)this.findViewById(R.id.eiw);
        TextView ct = (EditText)this.findViewById(R.id.ect);
        ct.setText(b.getString("commit"));
        ctext = ct;
        TextView ed = (TextView)this.findViewById(R.id.eed);
        TextView rm = (TextView)this.findViewById(R.id.erm);
        TextView rt = (TextView)this.findViewById(R.id.ert);
        rt.setText(b.getString("currentReminder"));
        rtext = rt;
        TextView fgt = (TextView)this.findViewById(R.id.efgt);
        TextView cancel = (TextView)this.findViewById(R.id.ecancel);

        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Rokkitt.otf");
        //if(iw == null){
        //	Toast.makeText(getActivity(), "Trying to get the text view is null", Toast.LENGTH_SHORT).show();
        //}
        iw.setTypeface(tf);
        ct.setTypeface(tf);
        ed.setTypeface(tf);
        rm.setTypeface(tf);
        rt.setTypeface(tf);
        fgt.setTypeface(tf);
        cancel.setTypeface(tf);

        //Toast.makeText(getActivity(), iwt.getText(), Toast.LENGTH_SHORT).show();

        ImageView sb = (ImageView)this.findViewById(R.id.saveButton);
        sb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle edited = new Bundle();
                edited.putString("ctext", ctext.getText().toString());
                edited.putString("rtext", rtext.getText().toString());
                Intent nt = new Intent();
                nt.putExtra("cedit", edited);
                setResult(RESULT_OK, nt);
                finish();
            }
        });

        rt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ra = new Intent(EditActivity.this, ReminderTimeActivity.class);
                startActivityForResult(ra, GET_CODE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != this.RESULT_CANCELED){
            Bundle result = data.getExtras().getBundle("reminder");
            //Log.d("CommitFragment", "Reminder size is: " + result.size());
            //Log.d("CommitFragment", "Reminder is: " + result.getString("reminder"));
            TextView rt = (TextView)this.findViewById(R.id.ert);
            //Toast.makeText(this.getActivity(), "The result is ok and the reminder is: " + "", Toast.LENGTH_SHORT).show();
            //rt.setText(result.getString("reminder"));
            //retxt = result.getString("reminder");
            rt.setText(result.getString("reminder"));
        }
    }

}
