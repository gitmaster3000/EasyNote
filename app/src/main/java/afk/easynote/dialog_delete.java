package afk.easynote;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class dialog_delete extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete these items?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        notifyToTarget(Activity.RESULT_OK);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();


    }

    private void notifyToTarget(int code) {
        Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {
            targetFragment.onActivityResult(getTargetRequestCode(), code, null);
        }
    }

}
