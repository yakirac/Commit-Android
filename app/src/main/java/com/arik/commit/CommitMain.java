package com.arik.commit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.Toast;

import org.json.*;

public class CommitMain extends FragmentActivity {

    private String FILENAME = "commits.json";

    private ViewPager cPager;
    private CommitPagerAdapter cpAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_main);

        cPager = (ViewPager) findViewById(R.id.cpager);
        cpAdapter = new CommitPagerAdapter(getSupportFragmentManager(), cPager);
        cpAdapter.createFirstFragment();
        loadData(cpAdapter);
        cPager.setAdapter(cpAdapter);
        //cPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_commit_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle appState){
        super.onSaveInstanceState(appState);

        //SparseArray<ArrayList<Fragment>> currentFragments = new SparseArray<ArrayList<Fragment>>();
        //currentFragments.put(1, cpAdapter.getFragments());
        Log.d("Save Instance CommitFragment", "Number of fragments is: " + cpAdapter.getCount());

        //appState.putSparseParcelableArray("appFragments", (SparseArray<? extends Parcelable>) currentFragments);
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d("On Pause CommitFragment", "Number of fragments is: " + cpAdapter.getCount());
        ArrayList<Fragment> currentFragments = cpAdapter.getFragments();
        ArrayList<CommitData> cdata = new ArrayList<CommitData>();
        for(int i = 0; i < currentFragments.size(); i++){
            if(i > 0){
                CommitInProgressFragment cip = (CommitInProgressFragment)currentFragments.get(i);
                CommitData cd = new CommitData();
                cd.setCommitText(cip.getText());
                cd.setReminder(cip.getReminder());
                cd.setCurrentDays(cip.getDays());
                cdata.add(cd);
            }
        }
        stopService(new Intent(CommitMain.this, CommitNotifier.class));
        saveData(cdata);

    }

    @Override
    protected void onStop(){
        super.onStop();

        Log.d("On Stop CommitFragment", "Number of fragments is: " + cpAdapter.getCount());
        ArrayList<Fragment> currentFragments = cpAdapter.getFragments();
        ArrayList<CommitData> cdata = new ArrayList<CommitData>();
        for(int i = 0; i < currentFragments.size(); i++){
            if(i > 0){
                CommitInProgressFragment cip = (CommitInProgressFragment)currentFragments.get(i);
                CommitData cd = new CommitData();
                cd.setCommitText(cip.getText());
                cd.setReminder(cip.getReminder());
                cd.setCurrentDays(cip.getDays());
                cdata.add(cd);
            }
        }
        stopService(new Intent(CommitMain.this, CommitNotifier.class));
        saveData(cdata);
    }

    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(CommitMain.this, CommitNotifier.class));
        Log.d("On Destroy CommitFragment", "Number of fragments is: " + cpAdapter.getCount());
        //ArrayList<Fragment> currentFragments = cpAdapter.getFragments();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.d("On Resume CommitFragment", "Number of fragments is: " + cpAdapter.getCount());

        //loadData();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        Log.d("On Restart CommitFragment", "Number of fragments is: " + cpAdapter.getCount());

        //loadData();
    }

    @Override
    protected void onRestoreInstanceState(Bundle restoredState){
        super.onRestoreInstanceState(restoredState);

        //SparseArray<? extends Parcelable> previousFragments = restoredState.getSparseParcelableArray("appFragments");


        //cpAdapter.setFragments((ArrayList<Fragment>) previousFragments.get(1));

        Log.d("Restore Instance CommitFragment", "Number of fragments is: " + cpAdapter.getCount());
    }

    private void saveData(ArrayList<CommitData> data){
        JSONArray commits = new JSONArray();

        for(CommitData c : data){
            commits.put(c);
        }

        try{
            final FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(commits.toString().getBytes());
            fos.close();
        }catch(IOException ioe){
            Log.e("Exception in saving the JSONArray to file: " + ioe, ioe.toString());
        }

        Log.d("In saveData, The JSONArray of commits: ", commits.toString());

    }

    private void loadData(CommitPagerAdapter cpa)
    {
        try{
            FileInputStream fis = openFileInput(FILENAME);
            byte [] input = new byte[fis.available()];
            JSONObject storedCommits = new JSONObject();
            while(fis.read(input) != -1){
                String json = new String(input);
                storedCommits.put("sCommits", json);
            }

            Log.d("In loadData, The JSONArray of commits loaded from the file: ", storedCommits.toString());

            if(storedCommits.length() > 0){
                JSONArray coms = new JSONArray(storedCommits.getString("sCommits"));
                for(int i = 0; i < coms.length(); i++){
                    try {
                        JSONObject com = new JSONObject(coms.getString(i));
                        cpa.addFragment(com.getString("Commit"), com.getString("Reminder"), com.getInt("Days"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch(IOException ioe){
            Log.e("Exception in loading the JSON file" + ioe, ioe.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


	/*private class CommitPagerAdapter extends FragmentStatePagerAdapter{

		//int lastPosition;
		private List<Fragment> fragments;

		public CommitPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			fragments = new ArrayList<Fragment>();
		}

		public void createFirstFragment(){
			int pos = 0;
			if(fragments.isEmpty()){
				Fragment frag = CommitFragment.create(pos, cPager);
				fragments.add(frag);
			}
		}

		public void addFragment(){
			Fragment newFrag = CommitFragment.create(fragments.size(), cPager);
			fragments.add(newFrag);
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "" + position + "", Toast.LENGTH_SHORT).show();
			//return CommitFragment.create(position, cPager);
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//return 2;
			return fragments.size();
		}


	}*/

}

