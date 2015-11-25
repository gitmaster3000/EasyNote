package afk.easynote;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by The Abster on 11/23/2015.
 */
public class NotesAdapter extends BaseAdapter {

    DatabaseHandler dbHandler;
    MainActivity mainActivity;
     List<Note>  NotesDataSet=mainActivity.NotesDataSet;
    @Override
    public int getCount() {


        return NotesDataSet.size();
    }

    @Override
    public Note getItem(int position) {


        return NotesDataSet.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //arg2 is the view group


       CheckableTextView i;


        if (convertView == null) {

           /*     i.setLayoutParams(new ViewGroup.LayoutParams( GridView.LayoutParams.MATCH_PARENT,
                        GridView.LayoutParams.WRAP_CONTENT));
            i.setTextColor(mainActivity.getResources().getColor(R.color.));
                l = new CheckableLayout(mainActivity);
                l.setLayoutParams(new GridView.LayoutParams(
                        GridView.LayoutParams.WRAP_CONTENT,
                        GridView.LayoutParams.WRAP_CONTENT));
                l.addView(i); */
            LayoutInflater layoutInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
convertView = layoutInflater.inflate(R.layout.notes_grid,parent,false);
        }



            i = (CheckableTextView) convertView.findViewById(R.id.noteView);

            i.setText(NotesDataSet.get(position).title);

        return convertView;
    }
    public NotesAdapter(DatabaseHandler dbParam, Activity activity){
        super();
        dbHandler=dbParam;
        mainActivity=(MainActivity)activity;

    }

    public void changeDataset(String Tag){
     NotesDataSet=dbHandler.getAllNotesByTag(Tag);
        notifyDataSetChanged();

    }


}
