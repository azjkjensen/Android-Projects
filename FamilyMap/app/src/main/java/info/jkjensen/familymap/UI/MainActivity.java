package info.jkjensen.familymap.UI;

import android.content.Intent;
import android.support.v4.app.Fragment;
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

    private MenuItem mMenuItemSearch;
    private MenuItem mMenuItemFilter;
    private MenuItem mMenuItemSettings;

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
        getMenuInflater().inflate(R.menu.activity_main, menu);

        mMenuItemFilter = menu.findItem(R.id.menu_item_filter);
        mMenuItemSearch = menu.findItem(R.id.menu_item_search);
        mMenuItemSettings = menu.findItem(R.id.menu_item_settings);

        if(!FamilyMap.getInstance().mIsUserLoggedIn){
            mMenuItemFilter.setVisible(false);
            mMenuItemSearch.setVisible(false);
            mMenuItemSettings.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_filter:
                Intent intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onLogin(){
        //Add our toolbar options
        mMenuItemFilter.setVisible(true);
        mMenuItemSearch.setVisible(true);
        mMenuItemSettings.setVisible(true);
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
