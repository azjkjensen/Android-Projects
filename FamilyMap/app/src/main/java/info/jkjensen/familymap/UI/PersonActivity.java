package info.jkjensen.familymap.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import info.jkjensen.familymap.Model.FamilyMapEvent;
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

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity context;
        private Map<String, List<FamilyMapEvent>> eventCollections;
        private List<FamilyMapEvent> events;

        public ExpandableListAdapter(Activity context, List<FamilyMapEvent> events,
                                     Map<String, List<FamilyMapEvent>> eventCollections) {
            this.context = context;
            this.eventCollections = eventCollections;
            this.events = events;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return eventCollections.get(events.get(groupPosition)).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View eventView, ViewGroup parent) {
            final String laptop = (String) getChild(groupPosition, childPosition);
            LayoutInflater inflater = context.getLayoutInflater();

            if (eventView == null) {
                eventView = inflater.inflate(R.layout.life_events_child_item, null);
            }
//
//            TextView item = (TextView) convertView.findViewById(R.id.laptop);
//
//            ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
            eventView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //Start a new person activity
                }
            });
            return eventView;
        }

        public int getChildrenCount(int groupPosition) {
            return eventCollections.get(events.get(groupPosition)).size();
        }

        public Object getGroup(int groupPosition) {
            return events.get(groupPosition);
        }

        public int getGroupCount() {
            return events.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            FamilyMapEvent event = (FamilyMapEvent) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.life_events_group_item,
                        null);
            }
//            TextView item = (TextView) convertView.findViewById(R.id.laptop);
//            item.setTypeface(null, Typeface.BOLD);
//            item.setText(laptopName);
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
