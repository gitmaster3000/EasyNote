package afk.easynote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * Created by Ratan on 7/29/2015.
 */
public class NotesFragment extends Fragment {
    View FragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.notes_layout, null);
        return FragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GridView gv = (GridView) FragmentView.findViewById(R.id.NotesGrid);
        gv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_gallery_item, new String[]{"abc", "asdd", "asds", "asdds"}));
    }
}
