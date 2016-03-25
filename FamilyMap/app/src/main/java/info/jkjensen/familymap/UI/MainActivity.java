package info.jkjensen.familymap.UI;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;
import info.jkjensen.familymap.UI.Fragments.LoginFragment;
import info.jkjensen.familymap.UI.Fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    private static final String MAP_FRAGMENT_TAG = "mapfragment";

    private FamilyMap mFamilyMap;
    private LoginFragment mLoginFragment;
    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mLoginFragment = (LoginFragment) fm.findFragmentById(R.id.fragment_container);
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mLoginFragment)
                    .commit();
        }

        mMapFragment = new MapFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.fragment_crime_list, menu);
//
//        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
//        if(mSubtitleVisible){
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        } else {
//            subtitleItem.setTitle(R.string.show_subtitle);
//        }
    }

    public void onLogin(){
        //remove login fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .remove(mLoginFragment)
                .commit();
        //add map fragment
        fm.beginTransaction()
                .add(R.id.fragment_container, mMapFragment, MAP_FRAGMENT_TAG).commit();
    }
}
