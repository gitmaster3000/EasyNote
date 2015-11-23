package afk.easynote;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class addNote extends AppCompatActivity {
    EditText title;
    EditText details;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        db  = new DatabaseHandler(this);
         title = (EditText)findViewById(R.id.Notetitle);
       details = (EditText)findViewById(R.id.NoteDetails);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

       String titleString = title.getText().toString();
        String detailsString = details.getText().toString();
        Note add = new Note(detailsString,titleString,0);
      add.id=  db.insertNoteOrReminder(add);
        MainActivity.NotesDataSet.add(add);
    }
}
