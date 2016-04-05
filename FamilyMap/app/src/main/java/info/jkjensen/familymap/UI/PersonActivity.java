package info.jkjensen.familymap.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.Model.FamilyMapEvent;
import info.jkjensen.familymap.Model.Person;
import info.jkjensen.familymap.R;

public class PersonActivity extends AppCompatActivity {

    FamilyMap mFamilyMap;
    ExpandableListView mExpandableListView;

    Person mMainPerson;
    List<String> mGroupList;
    List<Object> mEventChildList;
    List<Object> mPersonChildList;
    Map<String, List<Object>> mEventCollection;

    // TODO: Add "go to top" button to toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFamilyMap = FamilyMap.getInstance();
        mMainPerson = mFamilyMap.getCurrentPerson();

        setContentView(R.layout.activity_person);
        // TODO: populate PersonActivity according to current person
        // TODO: design PersonActivity UI
        /*In order to populate the family view, find parents with mother/father information
        *
        * find spouse by searching for a person with spouse == currentpersonID
        *
        * find children by searching for people with mother or father == currentpersonID*/

        /*To populate the life events view, search for events with personID == currentpersonID*/

        TextView firstNameView = (TextView) findViewById(R.id.person_first_name);
        firstNameView.setText(mMainPerson.getFirstName());

        TextView lastNameView = (TextView) findViewById(R.id.person_last_name);
        lastNameView.setText(mMainPerson.getLastName());

        TextView genderView = (TextView) findViewById(R.id.person_gender);
        if(mMainPerson.getGender() == "f") {
            genderView.setText("Female");
        } else {
            genderView.setText("Male");
        }


        mGroupList = new ArrayList<>();
        mGroupList.add("Life Events");
        mGroupList.add("Family Members");

        mEventCollection = new HashMap<>();

        mEventChildList = mFamilyMap.getLifeEvents(mFamilyMap.getCurrentPerson().getPersonID());
        mPersonChildList = mFamilyMap.getImmediateFamily(mFamilyMap.getCurrentPerson());

        mEventCollection.put("Life Events", mEventChildList);
        mEventCollection.put("Family Members", mPersonChildList); // TODO: This needs to be changed to a list of Person objects

        mExpandableListView = (ExpandableListView) findViewById(R.id.life_events_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, mGroupList, mEventCollection);
        mExpandableListView.setAdapter(expListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition).toString();
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity context;
        private Map<String, List<Object>> eventCollections;
        private List<String> groupNames;

        public ExpandableListAdapter(Activity context, List<String> groupNames,
                                     Map<String, List<Object>> eventCollections) {
            this.context = context;
            this.eventCollections = eventCollections;
            this.groupNames = groupNames;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return eventCollections.get(groupNames.get(groupPosition)).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View view, ViewGroup parent) {

            // TODO:Alter this to work with both expandable lists
            LayoutInflater inflater = context.getLayoutInflater();

            if(groupPosition == 0){ //If the view is Life Events
                final FamilyMapEvent event = (FamilyMapEvent) getChild(groupPosition, childPosition);

                view = inflater.inflate(R.layout.life_events_child_item, null);

                TextView titleAndLocation = (TextView) view.findViewById(R.id.life_event_title_loc);
                titleAndLocation.setText(
                        event.getDescription() + ": " + event.getCity() + ", " + event.getCountry()
                        + " (" + event.getYear() + ")");

                TextView name = (TextView) view.findViewById(R.id.life_event_name);
                name.setText(mMainPerson.getFirstName() + " " + mMainPerson.getLastName());


            } else { //If the view is Family Members
                final Person person = (Person) getChild(groupPosition, childPosition);

                    view = inflater.inflate(R.layout.family_members_child_item, null);

                TextView nameView = (TextView) view.findViewById(R.id.family_member_name);
                nameView.setText(person.getFirstName() + " " + person.getLastName());

                TextView relationshipView =
                        (TextView) view.findViewById(R.id.family_member_relationship);
                // TODO: Get the relationship and populate this textview

                if(person.getGender() == "f") {
                    ImageView icon = (ImageView) view.findViewById(R.id.family_member_icon);
                    icon.setImageDrawable(getDrawable(R.drawable.ic_gender_female_white_48dp));
                }
            }
            view.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //Start a new person activity
                }
            });
            return view;
        }

        public int getChildrenCount(int groupPosition) {
            return eventCollections.get(groupNames.get(groupPosition)).size();
        }

        public Object getGroup(int groupPosition) {
            return groupNames.get(groupPosition);
        }

        public int getGroupCount() {
            return groupNames.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String groupName = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.life_events_group_item,
                        null);
            }
            TextView lifeEventsTitle = (TextView) convertView.findViewById(R.id.life_events_title);
            lifeEventsTitle.setTypeface(null, Typeface.BOLD);
            lifeEventsTitle.setText(groupName);
            return convertView;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
