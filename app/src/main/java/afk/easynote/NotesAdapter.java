package afk.easynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by The Abster on 11/23/2015.
 */
public class NotesAdapter extends BaseAdapter {

    DatabaseHandler dbHandler;
    MainActivity mainActivity;
    @Override
    public int getCount() {


        return mainActivity.NotesDataSet.size();
    }

    @Override
    public Note getItem(int position) {


        return mainActivity.NotesDataSet.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //arg2 is the view group
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list, parent, false);
        }



           TextView textView = (TextView)convertView.findViewById(R.id.noteView);

            textView.setText(mainActivity.NotesDataSet.get(position).title);





        return convertView;
    }
    public NotesAdapter(DatabaseHandler dbParam, Activity activity){
        super();
        dbHandler=dbParam;
        mainActivity=(MainActivity)activity;

    }

    public void changeDataset(String Tag){
        mainActivity.NotesDataSet=dbHandler.getAllNotesByTag(Tag);
        notifyDataSetChanged();

    }


}
