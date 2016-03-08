package com.malzberry.lolstuff;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Malzberry on 12/26/2015.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter{
    String champ, player;
    public MyFragmentAdapter(FragmentManager fragmentManager, String player, String champ){
        super(fragmentManager);
        this.champ = champ;
        this.player = player;
    }

    public int getCount(){ return DisplayInfoActivity.NUMBER_OF_LISTS; }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return SummaryPageFrag.TITLE;
            case 1:
                return CounterTipsFrag.TITLE;
            case 2:
                return SkillsFrag.TITLE;
            default:
                break;
        }
        return "Error";
    }
    // TODO: bundle all info from activity into frag before returning here
   @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CounterTipsFrag();
        Bundle bundle = new Bundle();
        bundle.putString("champ", champ);
        bundle.putString("player", player);
        switch (position) {
        case 0:
            fragment = new SummaryPageFrag();
            fragment.setArguments(bundle);
            return fragment;
        case 1:
            fragment = new CounterTipsFrag();
            fragment.setArguments(bundle);
            return fragment;
        case 2:
            fragment = new SkillsFrag();
            fragment.setArguments(bundle);
            return fragment;
        default:
            break;
        }
        return fragment;
    }

    /*
    @Override
    public Fragment getItem(int position){
        Fragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }
*/



}
