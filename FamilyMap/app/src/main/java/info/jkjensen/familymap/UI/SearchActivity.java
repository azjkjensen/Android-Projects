package info.jkjensen.familymap.UI;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.Model.FamilyMapEvent;
import info.jkjensen.familymap.Model.Person;
import info.jkjensen.familymap.R;

public class SearchActivity extends AppCompatActivity {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;
    private RecyclerView mRecyclerView;
    private EditText mSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFamilyMap = FamilyMap.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Object> tempList = new ArrayList<>();
//        tempList.addAll(mFamilyMap.getUserPersons());
//        tempList.addAll(mFamilyMap.getUserEvents());
        mRecyclerView.setAdapter(new ResultAdapter(tempList));

        mSearchBar = (EditText) findViewById(R.id.search_bar);
        mSearchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Object> searchResults = new ArrayList<>();
                //Perform all searching and populating recyclerview
                String searchText = mSearchBar.getText().toString();

                searchResults.addAll(searchPeople(searchText));
                searchResults.addAll(searchEvents(searchText));
                mRecyclerView.setAdapter(new ResultAdapter(searchResults));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private ArrayList<Person> searchPeople(String searchText) {
        ArrayList<Person> result = new ArrayList<>();

        for(Person p : mFamilyMap.getUserPersons()){
            if(!result.contains(p)){
                if(p.getFirstName().toLowerCase().contains(searchText)){
                    result.add(p);
                    continue;
                } else if(p.getLastName().toLowerCase().contains(searchText)){
                    result.add(p);
                    continue;
                }
            }
        }
        return result;
    }

    private ArrayList<FamilyMapEvent> searchEvents(String searchText) {
        ArrayList<FamilyMapEvent> result = new ArrayList<>();

        for(FamilyMapEvent event : mFamilyMap.getUserEvents()){
            if(!result.contains(event)){
                if(event.getCountry().toLowerCase().contains(searchText)){
                    result.add(event);
                    continue;
                } else if(event.getCity().toLowerCase().contains(searchText)){
                    result.add(event);
                    continue;
                } else if(event.getDescription().toLowerCase().contains(searchText)){
                    result.add(event);
                    continue;
                } else if(event.getYear().toLowerCase().contains(searchText)){
                    result.add(event);
                    continue;
                }
            }
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ResultHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private final ImageView mIconView;
        private TextView mNameTextView;
        private TextView mResultInformation;

        private Object mResultItem;

        public ResultHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.result_item_name);
            mIconView = (ImageView) itemView.findViewById(R.id.result_item_icon);
            mResultInformation = (TextView) itemView.findViewById(R.id.result_information);

            itemView.setOnClickListener(this);
        }

        public void bindResultItem(Object resultItem){
            mResultItem = resultItem;
            if(mResultItem.getClass().equals(Person.class)){
                Person p = (Person) resultItem;
                mNameTextView.setText(p.getFirstName() + " " + p.getLastName());
                mResultInformation.setVisibility(View.INVISIBLE);
                if(p.getGender().equals("f")) {
                    mIconView.setImageDrawable(
                            ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_gender_female_white_48dp));
                } else{ //If the person is male
                    mIconView.setImageDrawable(
                            ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_gender_male_white_48dp));
                }
            } else{ //If the item is an event
                FamilyMapEvent event = (FamilyMapEvent) resultItem;
                Person person = mFamilyMap.getPersonFromEvent(event);

                mNameTextView.setText(person.getFirstName() + " " + person.getLastName());
                mResultInformation.setVisibility(View.VISIBLE);
                mResultInformation.setText(event.getFormattedDescription());

                mIconView.setImageDrawable(
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_location_on_white_48dp));
            }
        }

        @Override
        public void onClick(View v) {
            //Setup an intent and start the appropriate activity
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mResultItem.getId());
//            startActivity(intent);
            if(mResultItem.getClass().equals(Person.class)){
                mFamilyMap.setCurrentPerson((Person)mResultItem);
                Intent intent = new Intent(getBaseContext(), PersonActivity.class);
                startActivity(intent);
            } else { //If it is an event
                mFamilyMap.setSelectedEvent((FamilyMapEvent) mResultItem);
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                startActivity(intent);
            }
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultHolder>{

        private List<Object> mObjectList;

        public ResultAdapter(List<Object> objectList) {
            mObjectList = objectList;
        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
            View view = layoutInflater.inflate(R.layout.result_item, parent, false);

            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            Object resultItem = mObjectList.get(position);
            holder.bindResultItem(resultItem);
        }

        @Override
        public int getItemCount() {
            return mObjectList.size();
        }

    }
}
