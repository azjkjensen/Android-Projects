package info.jkjensen.familymap.UI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;
import info.jkjensen.familymap.UI.Fragments.LoginFragment;
import info.jkjensen.familymap.UI.Fragments.MapFragment;

public class MapActivity extends AppCompatActivity {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;
    private MapFragment mMapFragment;

    private MenuItem mMenuItemGoToTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFamilyMap = FamilyMap.getInstance();
        FragmentManager fm = getSupportFragmentManager();

        mMapFragment = (MapFragment) fm.findFragmentById(R.id.fragment_container);
        if(mMapFragment == null){
            mMapFragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mMapFragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_person, menu);

        mMenuItemGoToTop = menu.findItem(R.id.menu_item_top);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_item_top:
                // TODO: Pop back to running MainActivity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
