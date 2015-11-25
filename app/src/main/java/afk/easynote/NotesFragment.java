package afk.easynote;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;

public class NotesFragment extends Fragment {
    View FragmentView;
    MainActivity act;
    NotesAdapter notesAdapter;
    GridView gv;
    gridSelect gs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.notes_layout, null);
         act = (MainActivity)getActivity();
        notesAdapter =act.notesAdapter;
gs=new gridSelect();
     gv = (GridView) FragmentView.findViewById(R.id.NotesGrid);
        gv.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gv.setMultiChoiceModeListener(gs);
        gv.setAdapter(notesAdapter);
gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(act, addNote.class);
        intent.putExtra("TAG", "VIEW");
        Note a = notesAdapter.getItem(position);
        intent.putExtra("NOTE_ID", a.id);
        startActivity(intent);

    }
});
        Button button = (Button)FragmentView.findViewById(R.id.noteAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNote();
            }
        });
        return FragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        notesAdapter.updateDataSet();
        super.onResume();


}

    public void addNote(){
        DatabaseHandler db = new DatabaseHandler(act);

      Intent intent = new Intent(act,addNote.class);
        intent.putExtra("TAG", "NEW");
        startActivity(intent);



    }


   class gridSelect implements AbsListView.MultiChoiceModeListener {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            int selectCount = gv.getCheckedItemCount();
            MenuItem item = (MenuItem) mode.getMenu().findItem(R.id.share_item);

            switch (selectCount) {
                case 1:
                    mode.setSubtitle("One item selected");
                    mode.getMenuInflater().inflate(R.menu.contextual_list_view, mode.getMenu());
                    item = (MenuItem) mode.getMenu().findItem(R.id.share_item);
                    item.setEnabled(true);
                    break;
                default:
                    mode.setSubtitle("" + selectCount + " items selected");
                    item = (MenuItem) mode.getMenu().findItem(R.id.share_item);
                    item.setEnabled(false);
                    break;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.setTitle("Select Notes to delete");
            mode.setSubtitle("One item selected");
            mode.getMenuInflater().inflate(R.menu.contextual_list_view, menu);
            return true;


        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if (item.getItemId() == (mode.getMenu().findItem(R.id.delete_item).getItemId())) {
                SparseBooleanArray checkedItemPositions = gv.getCheckedItemPositions();
                int itemCount = gv.getCount();

                for (int i = itemCount - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {

                        act.mHandler.deleteNoteOrReminder(notesAdapter.getItem(i).id);
                       notesAdapter.updateDataSet();
                    }
                }
                notesAdapter.notifyDataSetChanged();


                mode.finish();

            }
            return true;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }



        }
    }
/* TO-DO
* Alarm Reciever and notification
* Reminders Activity
* Location Reminder using services
* Facebook Integration*/
