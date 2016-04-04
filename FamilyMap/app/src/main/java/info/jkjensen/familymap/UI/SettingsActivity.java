package info.jkjensen.familymap.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;

public class SettingsActivity extends AppCompatActivity {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //TODO: set up SettingsActivity UI and model
    }
}
