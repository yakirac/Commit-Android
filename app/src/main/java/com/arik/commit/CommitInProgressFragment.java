package com.arik.commit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommitInProgressFragment extends Fragment{

    public static final String ARG_PAGE = "page";

    static final private int EDIT = 0;
    static final private int DETAILS = 1;

    private int pageNumber;

    private String text;

    private String reminder;

    private CommitPagerAdapter pad;

    private boolean didIt = false;

    private int daysInARow = 0;
    private int dayToCheck = 0;
    private int totalIndicators = 0;

    private String addCheck = "";
    private String deleteMe = "";

    public CommitInProgressFragment(){

    }

    public CommitInProgressFragment(CommitPagerAdapter pa, String ct, String rem, int days){
        pad = pa;
        text = ct;
        reminder = rem;
        daysInARow = days;
    }

    public static CommitInProgressFragment create(int pageNumber, CommitPagerAdapter adapter, String ct, String rem, int days){
        CommitInProgressFragment fragment = new CommitInProgressFragment(adapter, ct, rem, days);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.commitment, container, false);

        TextView dy = (TextView)rootView.findViewById(R.id.dy);
        TextView ct = (TextView)rootView.findViewById(R.id.ct);
        ct.setText(text);
        TextView td = (TextView)rootView.findViewById(R.id.td);
        TextView edit = (TextView)rootView.findViewById(R.id.edit);
        TextView details = (TextView)rootView.findViewById(R.id.details);
        TextView dsrow = (TextView)rootView.findViewById(R.id.dsrow);
        if(daysInARow == 1){
            dsrow.setText(Html.fromHtml("<b>" + daysInARow + "</b> day in a row."));
        }else {
            dsrow.setText(Html.fromHtml("<b>" + daysInARow + "</b> days in a row."));
        }


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rokkitt.otf");

        dy.setTypeface(tf);
        td.setTypeface(tf);
        edit.setTypeface(tf);
        details.setTypeface(tf);
        //dsrow.setText("" + daysInARow + " " + dsrow.getText() + "");
		/*TextView iw = (TextView)rootView.findViewById(R.id.iw);
		iw.setText("Did you");

		TextView ed = (TextView)rootView.findViewById(R.id.ed);
		ed.setText("today?");*/

        ImageView yes = (ImageView)rootView.findViewById(R.id.yesButton);
        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ImageView iv = (ImageView)v;
                if(didIt == false){
                    iv.setImageResource(R.drawable.yescheck);
                    //v.setBackgroundResource(R.drawable.yescheck);
                    didIt = true;
                    addDay();
                }else {
                    iv.setImageResource(R.drawable.yes);
                    //v.setBackgroundResource(R.drawable.yes);
                    didIt = false;
                    removeDay();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView nct = (TextView)getView().findViewById(R.id.ct);
                Intent eint = new Intent(getActivity(), EditActivity.class);
                Bundle b = new Bundle();
                b.putString("commit", nct.getText().toString());
                b.putString("currentReminder", reminder);
                eint.putExtra("text", b);
                startActivityForResult(eint, EDIT);

            }

        });

        details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent dint = new Intent(getActivity(), DetailsActivity.class);
                if(addCheck.equals("yes")){
                    dint.putExtra("setButtonText", true);
                }else {
                    dint.putExtra("setButtonText", false);
                }
                startActivityForResult(dint, DETAILS);

            }
        });

        LinearLayout ll = (LinearLayout)rootView.findViewById(R.id.day_tracker);
		/*for(int i=0; i < 60; i++){
			View v = new View(ll.getContext());
			v.setId(i);
			v.setBackgroundColor(Color.parseColor("#9C7156"));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,60);
			params.setMargins(1, 0, 1, 0);
			v.setLayoutParams(params);
			ll.addView(v, params);
		}*/
        addDayIndicators(ll);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT){
            if(data != null){
                Bundle result = data.getExtras().getBundle("cedit");
                //Log.d("CommitFragment", "Reminder size is: " + result.size());
                //Log.d("CommitFragment", "Reminder is: " + result.getString("reminder"));
                TextView ect = (TextView)this.getView().findViewById(R.id.ct);
                //Toast.makeText(this.getActivity(), "The result is ok and the reminder is: " + "", Toast.LENGTH_SHORT).show();
                ect.setText(result.getString("ctext"));
                reminder = result.getString("rtext");
            }
        }else if(requestCode == DETAILS){
            if(data != null){
                Bundle dresult = data.getExtras().getBundle("what_todo");
                String add = dresult.getString("add_day");
                String delete = dresult.getString("delete_me");

                if(add.equals("yes")){
                    addCheck = add;
                    addDay();
                }else if(add.equals("no") && delete.equals("yes")){
                    deleteMe = delete;
                    pad.removeFragment(this);

                }else if(add.equals("no") && delete.equals("no")){
                    removeDay();
                }
            }
        }
    }


    public int getPageNumber(){
        return pageNumber;
    }

    public String getText(){
        return text;
    }

    public String getReminder(){
        return reminder;
    }

    public int getDays(){
        return daysInARow;
    }

    public void addDay(){
        dayToCheck = daysInARow;
        if(dayToCheck > daysInARow){
            dayToCheck++;
        }
        daysInARow++;
        TextView tv = (TextView)this.getView().findViewById(R.id.dsrow);
        if(daysInARow == 1){
            tv.setText(Html.fromHtml("<b>" + daysInARow + "</b> day in a row."));
        }else {
            tv.setText(Html.fromHtml("<b>" + daysInARow + "</b> days in a row."));
        }
        //View v = this.getView().findViewById(R.id.didit);
        LinearLayout ll = (LinearLayout)this.getView().findViewById(R.id.day_tracker);
        View v = ll.getChildAt(dayToCheck);
        v.setBackgroundColor(Color.parseColor("#b04b0c"));
    }

    public void removeDay(){
        daysInARow--;
        dayToCheck = daysInARow;
        TextView tv = (TextView)this.getView().findViewById(R.id.dsrow);
        if(daysInARow == 1){
            tv.setText(Html.fromHtml("<b>" + daysInARow + "</b> day in a row."));
        }else {
            tv.setText(Html.fromHtml("<b>" + daysInARow + "</b> days in a row."));
        }
        //View v = this.getView().findViewById(R.id.didit);
        //v.setBackgroundColor(Color.parseColor("#9C7156"));
        LinearLayout ll = (LinearLayout)this.getView().findViewById(R.id.day_tracker);
        View v = ll.getChildAt(dayToCheck);
        v.setBackgroundColor(Color.parseColor("#9C7156"));
    }

    public void resetDays(){
        daysInARow = 0;
        TextView tv = (TextView)this.getView().findViewById(R.id.dsrow);
        tv.setText(Html.fromHtml("<b>" + daysInARow + "</b> days in a row."));
    }

    public void addDayIndicators(LinearLayout lay){
        int j = daysInARow;
        for(int i=0; i < 100; i++){
            View v = new View(lay.getContext());
            v.setId(totalIndicators + i);
            totalIndicators = totalIndicators + i;
            if(j > 0){
                v.setBackgroundColor(Color.parseColor("#b04b0c"));
                j--;
            }else {
                v.setBackgroundColor(Color.parseColor("#9C7156"));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,80);
            params.setMargins(1, 0, 1, 0);
            v.setLayoutParams(params);
            lay.addView(v, params);
        }
    }

}

