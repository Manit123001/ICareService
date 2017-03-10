package com.example.mcnewz.icareservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;

import java.util.Calendar;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class WarningInfomationFragment extends Fragment {

    public WarningInfomationFragment() {
        super();
    }

    public static WarningInfomationFragment newInstance() {
        WarningInfomationFragment fragment = new WarningInfomationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragments_warning_infomation, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send2, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public interface FragmentListener{
        void onSendClickRegencyInfo(String finishSendData, String s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_send:
//                millis = String.valueOf(Calendar.getInstance().getTimeInMillis());
//                setRegencyInfo();
//
//
//                if(selectedPhoto == null || selectedPhoto.equals("")){
//                    RegencyInfomationFragment.FragmentListener listener = (RegencyInfomationFragment.FragmentListener) getActivity();
//                    listener.onSendClickRegencyInfo(finishSendData, "test");
//                }
                Toast.makeText(getContext(), "Warning Send ", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
