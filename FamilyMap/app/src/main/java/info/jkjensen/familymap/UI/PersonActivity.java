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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.Model.FamilyMapEvent;
import info.jkjensen.familymap.R;

public class PersonActivity extends AppCompatActivity {

    FamilyMap mFamilyMap;

    List<String> mGroupList;
    List<FamilyMapEvent> mChildList;
    Map<String, List<FamilyMapEvent>> mEventCollection;
    ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFamilyMap = FamilyMap.getInstance();

        setContentView(R.layout.activity_person);
        //TODO: populate PersonActivity according to current person
        //TODO: design PersonActivity UI
        /*In order to populate the family view, find parents with mother/father information
        *
        * find spouse by searching for a person with spouse == currentpersonID
        *
        * find children by searching for people with mother or father == currentpersonID*/

        /*To populate the life events view, search for events with personID == currentpersonID*/


//        createGroupList();
//
//        createCollection();
        mGroupList = new ArrayList<>();
        mGroupList.add("Life Events");
        mGroupList.add("Family Members");

        mEventCollection = new HashMap<>();

        mChildList = mFamilyMap.getLifeEvents(mFamilyMap.getUserId());

        mEventCollection.put("Life Events", mChildList);
        mEventCollection.put("Family Members", mChildList);

        mExpandableListView = (ExpandableListView) findViewById(R.id.life_events_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, mGroupList, mEventCollection);
        mExpandableListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity context;
        private Map<String, List<FamilyMapEvent>> eventCollections;
        private List<String> groupNames;

        public ExpandableListAdapter(Activity context, List<String> groupNames,
                                     Map<String, List<FamilyMapEvent>> eventCollections) {
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
                                 boolean isLastChild, View eventView, ViewGroup parent) {
            final FamilyMapEvent event = (FamilyMapEvent) getChild(groupPosition, childPosition);
            LayoutInflater inflater = context.getLayoutInflater();

            if (eventView == null) {
                eventView = inflater.inflate(R.layout.life_events_child_item, null);
            }
//
//            TextView item = (TextView) eventView.findViewById(R.id.laptop);
//
//            ImageView delete = (ImageView) eventView.findViewById(R.id.delete);
            TextView titleAndLocation = (TextView) eventView.findViewById(R.id.life_event_title_loc);
            titleAndLocation.setText(
                    event.getDescription() + ": " + event.getCity() + ", " + event.getCountry());

            TextView name = (TextView) eventView.findViewById(R.id.life_event_name);
            name.setText(event.getPersonId()); //TODO: fix this to have the actual person's name

            eventView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //Start a new person activity
                }
            });
            return eventView;
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
            TextView item = (TextView) convertView.findViewById(R.id.life_events_title);
            item.setTypeface(null, Typeface.BOLD);
            item.setText(groupName);
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
