package afk.easynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by The Abster on 11/23/2015.
 */
public class RemindersAdapter extends BaseAdapter {

    public DatabaseHandler dbHandler;
    public MainActivity mainActivity;
     public List<Note> RemindersDataSet=mainActivity.RemindersDataSet;
    @Override
    public int getCount() {


        return RemindersDataSet.size();
    }

    @Override
    public Note getItem(int position) {


        return RemindersDataSet.get(position);

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

            i.setText(RemindersDataSet.get(position).title);

        return convertView;
    }

    public RemindersAdapter(DatabaseHandler dbParam, Activity activity){
        super();
        dbHandler=dbParam;
        mainActivity=(MainActivity)activity;
       RemindersDataSet=mainActivity.NotesDataSet;

    }

    public void updateDataSet(){
        RemindersDataSet=dbHandler.getAllReminders();
        notifyDataSetChanged();
    }

    public void changeDataset(String Tag){
        RemindersDataSet=dbHandler.getAllRemindersByTag(Tag);
        notifyDataSetChanged();
    }


}
