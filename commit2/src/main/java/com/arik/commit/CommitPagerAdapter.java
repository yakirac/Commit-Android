package com.arik.commit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class CommitPagerAdapter extends FragmentStatePagerAdapter{

    //int lastPosition;
    private List<Fragment> fragments;

    private ViewPager pager;

    public CommitPagerAdapter(FragmentManager fm, ViewPager vp) {
        super(fm);
        // TODO Auto-generated constructor stub
        fragments = new ArrayList<Fragment>();
        pager = vp;
    }

    //Create the first fragment that you see when the app loads up
    public void createFirstFragment(){
        int pos = 0;
        if(fragments.isEmpty()){
            Fragment frag = CommitFragment.create(pos, this);
            fragments.add(frag);
        }
    }

    //Create the fragments from the previous state before the application was closed
    public void createStateFragments(int lastState, ArrayList<ArrayList<String>> fragData){

    }

    //Add a new fragment to the list of fragments
    public void addFragment(String text, String rem, int days){
        Fragment newFrag = CommitInProgressFragment.create(fragments.size(), this, text, rem, days);
        fragments.add(newFrag);
        notifyDataSetChanged();
        pager.setCurrentItem(fragments.size(), true);
    }

    public void removeFragment(Fragment f){
        //Log.d("CommitPagerAdapter", "Before the fragment is remowved, the size is: " + fragments.size());
        if(fragments.contains(f)){
            fragments.remove(f);
            notifyDataSetChanged();
            pager.setCurrentItem(fragments.size(), true);
            //Toast.makeText(, "The fragment has been removed", Toast.LENGTH_SHORT).show();
            //Log.d("CommitPagerAdapter", "The fragment exists and should be removed. The size is: " + fragments.size());
        }
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        //Toast.makeText(getApplicationContext(), "" + position + "", Toast.LENGTH_SHORT).show();
        //return CommitFragment.create(position, cPager);
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //return 2;
        return fragments.size();
    }

    public void setFragments(ArrayList<Fragment> previousFragments){
        fragments = previousFragments;
    }

    public ArrayList<Fragment> getFragments(){
        return (ArrayList<Fragment>) fragments;
    }


}

