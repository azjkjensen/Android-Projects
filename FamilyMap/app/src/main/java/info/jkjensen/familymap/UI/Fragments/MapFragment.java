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


//        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//
//        mMapLayout = v.findViewById(R.id.map);
//        ViewGroup.LayoutParams mapParams =  mMapLayout.getLayoutParams();
////        mapParams.height = (int)((7f/8f) * dpHeight);
////        mMapLayout.setLayoutParams(mapParams);
//
//        mPersonLayout = v.findViewById(R.id.layout_person);
//        ViewGroup.LayoutParams personParams =  mPersonLayout.getLayoutParams();
////        personParams.height = (int)((1f/8f) * dpHeight);
////        mPersonLayout.setLayoutParams(personParams);

        return v;
    }
}
