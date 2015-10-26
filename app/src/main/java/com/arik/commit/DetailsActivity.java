package com.arik.commit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commit_details);

        boolean b = this.getIntent().getBooleanExtra("setButtonText", false);

        Button back = (Button)this.findViewById(R.id.go_back);
        Button addc = (Button)this.findViewById(R.id.addcheck);
        if(b == true){
            addc.setText("Remove Check for Yesterday");
        }
        Button delete = (Button)this.findViewById(R.id.delete);

        TextView fg = (TextView)this.findViewById(R.id.forgot);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Rokkitt.otf");

        back.setTypeface(tf);
        addc.setTypeface(tf);
        delete.setTypeface(tf);
        fg.setTypeface(tf);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setResult(RESULT_OK);
                finish();
            }
        });

        addc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Button b = (Button) v;
                Bundle ac = new Bundle();
                if(b.getText().toString().equals("Add Check for Yesterday")){
                    ac.putString("add_day", "yes");
                }else if(b.getText().toString().equals("Remove Check for Yesterday")){
                    ac.putString("add_day", "no");
                }
                ac.putString("delete_me", "no");
                Intent adc = new Intent();
                adc.putExtra("what_todo", ac);
                setResult(RESULT_OK, adc);
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bundle del = new Bundle();
                del.putString("add_day", "no");
                del.putString("delete_me", "yes");
                Intent di = new Intent();
                di.putExtra("what_todo", del);
                setResult(RESULT_OK, di);
                finish();
            }
        });



    }

}

