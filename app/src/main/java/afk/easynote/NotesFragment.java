package afk.easynote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class NotesFragment extends Fragment {
    View FragmentView;
NotesAdapter notesAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.notes_layout, null);
        MainActivity act = (MainActivity)getActivity();
        notesAdapter = new NotesAdapter(act.mHandler);

        GridView gv = (GridView) FragmentView.findViewById(R.id.NotesGrid);
        gv.setAdapter(notesAdapter);
        return FragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        notesAdapter.notifyDataSetChanged();

}
}
