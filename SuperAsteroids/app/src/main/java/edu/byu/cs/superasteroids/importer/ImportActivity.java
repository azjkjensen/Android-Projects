package edu.byu.cs.superasteroids.importer;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.R;

public class ImportActivity extends ActionBarActivity {

    private ListView listView;
    private Resources res;
    private AssetManager am;
    private List<String> fileList;
    private IGameDataImporter dataImporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //TODO: Set the dataimporter to an instance of your GameDataImporter
        dataImporter = new DataImporter();
    }


    @Override
    public void onResume() {

        super.onResume();

        res = getResources();
        am = res.getAssets();

        String[] files = null;
        try {
            files = am.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileList = null;
        if(files != null) {
            fileList = new ArrayList<String>();
            for(String file : files) {
                if(file.endsWith(".json"))
                    fileList.add(file);
            }
            if(fileList.size() > 0) {
                ImportFileAdapter fileAdapter = new ImportFileAdapter(this,
                        android.R.layout.simple_list_item_1, fileList);
                ListView listView = (ListView) findViewById(R.id.import_list);
                listView.setAdapter(fileAdapter);
                listView.setOnItemClickListener(fileClickListener);
                fileAdapter.notifyDataSetChanged();
            }
        }

    }

    private AdapterView.OnItemClickListener fileClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Toast toast = Toast.makeText(ImportActivity.this, "", Toast.LENGTH_LONG);
            if(dataImporter == null) {
                toast.setText("The importer has not been implemented or created yet...\nThe file was not imported.");
            }
            else {
                try {

                    boolean success = dataImporter.importData(new InputStreamReader(
                            new BufferedInputStream(am.open(fileList.get(i)))));

                    if (success)
                        toast.setText("The file was imported.");
                    else
                        toast.setText("An error occurred as the importer tried to import the file.");

                } catch (IOException e) {
                    e.printStackTrace();
                    toast.setText("The file could not be imported, because it does not exist.");
                }
            }
            toast.show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_import, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_import, container, false);
            return rootView;
        }
    }
}
