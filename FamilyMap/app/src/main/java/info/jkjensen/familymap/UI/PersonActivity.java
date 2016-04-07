package info.jkjensen.familymap.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

    MenuItem mMenuItemGoToTop;

    Person mMainPerson;
    List<String> mGroupList;
    List<Object> mEventChildList;
    List<Object> mPersonChildList;
    Map<String, List<Object>> mEventCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFamilyMap = FamilyMap.getInstance();
        mMainPerson = mFamilyMap.getCurrentPerson();

        setContentView(R.layout.activity_person);
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
        mEventCollection.put("Family Members", mPersonChildList);

        mExpandableListView = (ExpandableListView) findViewById(R.id.life_events_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, mGroupList, mEventCollection);
        mExpandableListView.setAdapter(expListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.e("jk_click", "clicked!");
                if(groupPosition == 0){ // We are dealing with an event
                    FamilyMapEvent clicked = (FamilyMapEvent) expListAdapter.getChild(
                            groupPosition, childPosition);
                    mFamilyMap.setSelectedEvent(clicked);
//                    mFamilyMap.setCurrentPerson(mFamilyMap.getPersonFromEvent(clicked));
                    Intent intent = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(intent);
                } else { // We are dealing with a Person
                    Person clicked = (Person) expListAdapter.getChild(
                            groupPosition, childPosition);
                    mFamilyMap.setCurrentPerson(clicked);
                    Intent intent = new Intent(getBaseContext(), PersonActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
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
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return eventCollections.get(groupNames.get(groupPosition)).get(childPosition);
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View view, ViewGroup parent) {
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
                relationshipView.setText(person.getRelationship());

                if(person.getGender().equals("f")) {
                    ImageView icon = (ImageView) view.findViewById(R.id.family_member_icon);
                    icon.setImageDrawable(getDrawable(R.drawable.ic_gender_female_white_48dp));
                }
            }
            return view;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return eventCollections.get(groupNames.get(groupPosition)).size();
        }
        @Override
        public Object getGroup(int groupPosition) {
            return groupNames.get(groupPosition);
        }
        @Override
        public int getGroupCount() {
            return groupNames.size();
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
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
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}
