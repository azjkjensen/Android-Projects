package info.jkjensen.familymap.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.jkjensen.familymap.R;

public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        //TODO: populate PersonActivity according to current person
        //TODO: design PersonActivity UI
        /*In order to populate the family view, find parents with mother/father information
        *
        * find spouse by searching for a person with spouse == currentpersonID
        *
        * find children by searching for people with mother or father == currentpersonID*/

        /*To populate the life events view, search for events with personID == currentpersonID*/
    }

}
