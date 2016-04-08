package info.jkjensen.familymap.UI;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.jkjensen.familymap.Model.FamilyMap;
import info.jkjensen.familymap.Model.FamilyMapEvent;
import info.jkjensen.familymap.Model.Person;
import info.jkjensen.familymap.R;

public class SearchActivity extends AppCompatActivity {

    /**A reference to the model for the app*/
    private FamilyMap mFamilyMap;
    private RecyclerView mCrimeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //TODO; set up SearchActivity model
        mCrimeRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            if(mResultItem.getClass() == Person.class){
                Person p = (Person) resultItem;
                mNameTextView.setText(p.getFirstName() + " " + p.getLastName());

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
                mResultInformation.setText(event.getFormattedDescription());
            }
        }

        @Override
        public void onClick(View v) {
            //Setup an intent and start the appropriate activity
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mResultItem.getId());
//            startActivity(intent);
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
