package afk.easynote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.List;

/**
 * Created by The Abster on 11/23/2015.
 */
public class NotesAdapter extends BaseAdapter {
   static List<Note>  NotesDataSet;
    DatabaseHandler dbHandler;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //infalter etc
        return null;
    }
    public NotesAdapter(DatabaseHandler dbParam){
        super();
        dbHandler=dbParam;
        NotesDataSet=dbHandler.getAllNotes();

    }

    public void changeDataset(String Tag){
        NotesDataSet=dbHandler.getAllNotesByTag(Tag);
        notifyDataSetChanged();

    }

}
