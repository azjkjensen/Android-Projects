package info.jkjensen.familymap.UI.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.jkjensen.familymap.R;

/**
 * Created by Jk on 3/21/2016.
 */
public class MapFragment extends Fragment {
    View mMapLayout;
    View mPersonLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        //Call an asyncTask to get events/people asssociated with user
        //Populate the Map with given events
        return v;
    }
}
