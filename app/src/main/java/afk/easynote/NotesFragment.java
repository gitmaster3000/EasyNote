package afk.easynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

public class NotesFragment extends Fragment {
    View FragmentView;
    MainActivity act;
    NotesAdapter notesAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.notes_layout, null);
         act = (MainActivity)getActivity();
        notesAdapter = new NotesAdapter(act.mHandler,act);

        GridView gv = (GridView) FragmentView.findViewById(R.id.NotesGrid);
        gv.setAdapter(notesAdapter);

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
        notesAdapter.notifyDataSetChanged();
        super.onResume();


}

    public void addNote(){
        Intent intent = new Intent(act,addNote.class);
        startActivity(intent);



    }
}
