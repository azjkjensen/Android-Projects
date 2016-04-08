package info.jkjensen.familymap.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;
import info.jkjensen.familymap.UI.Fragments.MapFragment;

public class SettingsActivity extends AppCompatActivity {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;
    String[] mSpinnerColors;
    private String[] mMapTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //TODO: set up SettingsActivity UI and model

        mFamilyMap = FamilyMap.getInstance();

        mSpinnerColors = getResources().getStringArray(R.array.colors);

        Spinner lifeStorySpinner = (Spinner) findViewById(R.id.story_line_color_spinner);
        lifeStorySpinner.setSelection(getPosition(mSpinnerColors, mFamilyMap.getLifeStoryColor()));
        lifeStorySpinner.setOnItemSelectedListener(mLifeSpinnerListener);

        Switch lifeStorySwitch = (Switch) findViewById(R.id.story_line_switch);
        lifeStorySwitch.setChecked(mFamilyMap.showLifeStoryLines());
        lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFamilyMap.setShowLifeStoryLines(isChecked);
            }
        });

        Spinner familyTreeSpinner = (Spinner) findViewById(R.id.tree_line_color_spinner);
        familyTreeSpinner.setSelection(getPosition(mSpinnerColors, mFamilyMap.getFamilyTreeColor()));
        familyTreeSpinner.setOnItemSelectedListener(mTreeSpinnerListener);

        Switch treeSwitch = (Switch) findViewById(R.id.tree_line_switch);
        treeSwitch.setChecked(mFamilyMap.showFamilyTreeLines());
        treeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFamilyMap.setShowFamilyTreeLines(isChecked);
            }
        });

        Spinner spouseSpinner = (Spinner) findViewById(R.id.spouse_line_color_spinner);
        spouseSpinner.setSelection(getPosition(mSpinnerColors, mFamilyMap.getSpouseStoryColor()));
        spouseSpinner.setOnItemSelectedListener(mSpouseSpinnerListener);

        Switch spouseSwitch = (Switch) findViewById(R.id.spouse_line_switch);
        spouseSwitch.setChecked(mFamilyMap.showSpouseLines());
        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFamilyMap.setShowSpouseLines(isChecked);
            }
        });

        mMapTypes = getResources().getStringArray(R.array.map_types);

        final Spinner mapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        mapTypeSpinner.setSelection(getMapTypePosition(mMapTypes, mFamilyMap.getAmazonMap().getMapType()));
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int mapType = mFamilyMap.getAmazonMap().getMapType();
                String mapTypeString = mMapTypes[position].toLowerCase();
                if(mapTypeString.equals("satellite")){
                    mapType = AmazonMap.MAP_TYPE_SATELLITE;
                } else if(mapTypeString.equals("terrain")){
                    mapType = AmazonMap.MAP_TYPE_TERRAIN;
                } else if(mapTypeString.equals("normal")){
                    mapType = AmazonMap.MAP_TYPE_NORMAL;
                } else if(mapTypeString.equals("hybrid")){
                    mapType = AmazonMap.MAP_TYPE_HYBRID;
                }
                mFamilyMap.getAmazonMap().setMapType(mapType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        RelativeLayout logoutButton = (RelativeLayout) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFamilyMap.resetUserSession();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final Activity currentActivity = this;
        RelativeLayout syncButton = (RelativeLayout) findViewById(R.id.sync_button);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(currentActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//
//                FragmentManager fm = getSupportFragmentManager();
//                ((MapFragment)fm.findFragmentByTag("mapfragment")).drawMapLines();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getPosition(String[] array, int color) {
        String colorString = "None";
        if(color == Color.RED){
            colorString = "red";
        } else if (color == Color.BLUE) {
            colorString = "blue";
        } else if(color == Color.GREEN) {
            colorString = "green";
        }

        for(int i = 0; i < array.length; i++){
            if(colorString.equals(array[i].toLowerCase())){
                return i;
            }
        }
        return 0;
    }

    private int getMapTypePosition(String[] array, int mapType) {
        String mapString = "None";
        if(mapType == AmazonMap.MAP_TYPE_HYBRID){
            mapString = "hybrid";
        } else if (mapType == AmazonMap.MAP_TYPE_NORMAL) {
            mapString = "normal";
        } else if(mapType == AmazonMap.MAP_TYPE_SATELLITE) {
            mapString = "satellite";
        } else if(mapType == AmazonMap.MAP_TYPE_TERRAIN) {
            mapString = "terrain";
        }

        for(int i = 0; i < array.length; i++){
            if(mapString.equals(array[i].toLowerCase())){
                return i;
            }
        }
        return 0;
    }

    AdapterView.OnItemSelectedListener mLifeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0){
                mFamilyMap.setLifeStoryColor(mSpinnerColors[position]);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener mTreeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0) {
                mFamilyMap.setFamilyTreeColor(mSpinnerColors[position]);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener mSpouseSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0){
                mFamilyMap.setSpouseStoryColor(mSpinnerColors[position]);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
