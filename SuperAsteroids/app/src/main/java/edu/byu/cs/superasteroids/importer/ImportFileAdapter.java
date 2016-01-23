package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.byu.cs.superasteroids.R;

/**
 * Created by Tyler on 3/13/2015.
 */
public class ImportFileAdapter extends ArrayAdapter<String> {

    private List<String> files;
    private Context context;

    public ImportFileAdapter(Context context, int resource, List<String> files) {
        super(context, resource, files);
        this.context = context;
        this.files = files;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_import_file, parent, false);
        TextView textView = (TextView)view.findViewById(R.id.import_file_text);
        textView.setText(files.get(position));

        return view;
    }

    public void setFiles(List<String> files) {
        this.files.clear();
        for(String file : files) {
            this.files.add(file);
        }
        this.notifyDataSetChanged();
    }
}
