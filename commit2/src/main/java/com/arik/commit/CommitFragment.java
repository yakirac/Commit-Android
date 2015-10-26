package com.arik.commit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class CommitFragment extends Fragment{

    public static final String ARG_PAGE = "page";

    private int fPageNumber;

    private ViewPager pgr;

    private CommitPagerAdapter cpa;

    private TextView ctext;

    private TextView rtext;

    static final private int GET_CODE = 0;


    public CommitFragment(){

    }

    public CommitFragment(CommitPagerAdapter pa){
        cpa = pa;
    }

    public static CommitFragment create(int pageNumber, CommitPagerAdapter adapter){
        CommitFragment fragment = new CommitFragment(adapter);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.commit_temp, container, false);

        TextView iw = (TextView)rootView.findViewById(R.id.iw);
        TextView ct = (EditText)rootView.findViewById(R.id.ct);
        ctext = ct;
        TextView ed = (TextView)rootView.findViewById(R.id.ed);
        TextView rm = (TextView)rootView.findViewById(R.id.rm);
        TextView rt = (TextView)rootView.findViewById(R.id.rt);
        rtext = rt;
        TextView fgt = (TextView)rootView.findViewById(R.id.fgt);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rokkitt.otf");
        //if(iw == null){
        //	Toast.makeText(getActivity(), "Trying to get the text view is null", Toast.LENGTH_SHORT).show();
        //}
        iw.setTypeface(tf);
        ct.setTypeface(tf);
        ed.setTypeface(tf);
        rm.setTypeface(tf);
        rt.setTypeface(tf);
        fgt.setTypeface(tf);

        //Toast.makeText(getActivity(), iwt.getText(), Toast.LENGTH_SHORT).show();

        ImageView cb = (ImageView)rootView.findViewById(R.id.commitButton);

        cb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(getActivity(), "We want to create a new commit", Toast.LENGTH_SHORT).show();
                if(!ctext.getText().equals("")){
                    StringBuilder sb = new StringBuilder(ctext.getText().toString());
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    cpa.addFragment(sb.toString(), rtext.getText().toString(), 0);
                }
                ctext.setText("");
                rtext.setText("4:00 PM");
            }

        });

        rt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ra = new Intent(getActivity(), ReminderTimeActivity.class);
                startActivityForResult(ra, GET_CODE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != this.getActivity().RESULT_CANCELED){
            Bundle result = data.getExtras().getBundle("reminder");
            //Log.d("CommitFragment", "Reminder size is: " + result.size());
            //Log.d("CommitFragment", "Reminder is: " + result.getString("reminder"));
            TextView rt = (TextView)this.getView().findViewById(R.id.rt);
            //Toast.makeText(this.getActivity(), "The result is ok and the reminder is: " + "", Toast.LENGTH_SHORT).show();
            rt.setText(result.getString("reminder"));
        }
    }

    public int getPageNumber(){
        return fPageNumber;
    }

}

