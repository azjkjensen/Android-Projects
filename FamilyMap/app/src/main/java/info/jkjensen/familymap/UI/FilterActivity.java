package info.jkjensen.familymap.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.R;

/**
 * The view for the filter activity.
 */
public class FilterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;

    /**The event descriptions of all events */
    private HashMap<String, Boolean> mEventsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mFamilyMap = FamilyMap.getInstance();
        mEventsEnabled = mFamilyMap.getEventsEnabled();

        ArrayList<String> filters = new ArrayList<>();
        filters.addAll(mFamilyMap.getEventsEnabled().keySet());
        filters.add("Male");
        filters.add("Female");
        filters.add("Father's Side");
        filters.add("Mother's Side");

        mRecyclerView = (RecyclerView) findViewById(R.id.filter_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new FilterAdapter(filters));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                mFamilyMap.syncFilters();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Holder for the RecyclerView
     */
    private class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDescTextView;
        private Switch mFilterEnableSwitch;

        private String mFilterString;

        public FilterHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.filter_title);
            mDescTextView = (TextView) itemView.findViewById(R.id.filter_description);
            mFilterEnableSwitch = (Switch) itemView.findViewById(R.id.filter_switch);

            itemView.setOnClickListener(this);
        }

        public void bindResultItem(String filterItem){
            mFilterString = filterItem;

            mTitleTextView.setText(mFilterString.toUpperCase() + " Events");
            mDescTextView.setText("FILTER BY " + mFilterString.toUpperCase() + " EVENTS");

            boolean enabled = false;

            if(mFilterString.equals("Male")){
                enabled = mFamilyMap.showMaleEvents();
            } else if(mFilterString.equals("Female")){
                enabled = mFamilyMap.showFemaleEvents();
            } else if(mFilterString.equals("Mother's Side")){
                enabled = mFamilyMap.showMothersEvents();
            } else if(mFilterString.equals("Father's Side")) {
                enabled = mFamilyMap.showFathersEvents();
            } else { //If it is just an event description
                enabled = mEventsEnabled.get(mFilterString.toLowerCase());
            }
            mFilterEnableSwitch.setChecked(enabled);

            mFilterEnableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(mFilterString.equals("Male")){
                        mFamilyMap.setShowMaleEvents(isChecked);
                    } else if(mFilterString.equals("Female")){
                        mFamilyMap.setShowFemaleEvents(isChecked);
                    } else if(mFilterString.equals("Mother's Side")){
                        mFamilyMap.setShowMothersEvents(isChecked);
                    } else if(mFilterString.equals("Father's Side")) {
                        mFamilyMap.setShowFathersEvents(isChecked);
                    } else { //If it is just an event description
                        mEventsEnabled.put(mFilterString.toLowerCase(), isChecked);
                    }
//                    mFamilyMap.syncFilters();
                }
            });
        }

        @Override
        public void onClick(View v) { }
    }

    /**
     * Adapter for the RecyclerView
     */
    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder>{

        private List<String> mFilterStringList;

        public FilterAdapter(List<String> filterStringList) {
            mFilterStringList = new ArrayList<>();
            mFilterStringList.addAll(filterStringList);
        }

        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
            View view = layoutInflater.inflate(R.layout.filter_item, parent, false);

            return new FilterHolder(view);
        }

        @Override
        public void onBindViewHolder(FilterHolder holder, int position) {
            String resultItem = mFilterStringList.get(position);
            holder.bindResultItem(resultItem);
        }

        @Override
        public int getItemCount() {
            return mFilterStringList.size();
        }

    }
}
