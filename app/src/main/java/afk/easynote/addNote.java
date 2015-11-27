package afk.easynote;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class addNote extends AppCompatActivity {
    EditText title;
    EditText details;
    DatabaseHandler db;
    ArrayAdapter<String> gridAdapter;
    ArrayList<String> TagsOnNote;
    ArrayList<String> AllTags;
    GridView gridView;
String callState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(this);
        setContentView(R.layout.activity_add_note);
        Button reminder = (Button)findViewById(R.id.reminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        callState=getIntent().getStringExtra("TAG");
        title = (EditText) findViewById(R.id.Notetitle);
        details = (EditText) findViewById(R.id.NoteDetails);
        gridView = (GridView) findViewById(R.id.TagsGrid);
        setToolbar();
//TODO: Set Alarm in a function call from menu.. For notes with alarm, display as reminders in same tab
        if ( callState.equals("NEW")){




            TagsOnNote = new ArrayList<String>();
            AllTags = new ArrayList<String>();

            Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);


//get all tags from mainactivity ASYNC BEHAVIOR
            AllTags.add("ADD TAGS");
            AllTags.addAll(MainActivity.tags);

//setting adapters
            gridAdapter = new ArrayAdapter<String>(this, R.layout.tags_on_note, TagsOnNote);
            spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, AllTags));
            gridView.setAdapter(gridAdapter);

            //set spinner on click
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        ((TextView) view).setText("ADD TAGS");
                        String tag = AllTags.get(position);
                        if (!TagsOnNote.contains(tag))
                            TagsOnNote.add(tag);
                        gridAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TagsOnNote.remove(position);
                    gridAdapter.notifyDataSetChanged();
                }
            });

        }

        else if (callState.equals("VIEW")){
            details.setEnabled(false);
            title.setEnabled(false);
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
           long noteID= getIntent().getLongExtra("NOTE_ID", 0);
            Note showNote = db.getNoteOrReminder(noteID);
            details.setText(showNote.text);
            title.setText(showNote.title);

            TagsOnNote=(ArrayList)showNote.tags;
            gridAdapter = new ArrayAdapter<String>(this, R.layout.tags_on_note, TagsOnNote);
           gridView.setAdapter(gridAdapter);

        }


    }

public void setToolbar(){




    Toolbar myChildToolbar =
            (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(myChildToolbar);

    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();

    // Enable the Up button
    ab.setDisplayHomeAsUpEnabled(true);
ab.setDisplayShowTitleEnabled(false);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(callState.equals("VIEW"))

            getMenuInflater().inflate(R.menu.menu_edit_note, menu);
else
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you spe cify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_item) {
            callState="EDIT";
            AllTags = new ArrayList<String>();
            AllTags.add("ADD TAGS");
            AllTags.addAll(MainActivity.tags);
            Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

            spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, AllTags));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TagsOnNote.remove(position);
                    gridAdapter.notifyDataSetChanged();
                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        ((TextView) view).setText("ADD TAGS");
                        String tag = AllTags.get(position);
                        if (!TagsOnNote.contains(tag))
                            TagsOnNote.add(tag);
                        gridAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//get all tags from mainactivity ASYNC BEHAVIOR

            details.setEnabled(true);
            title.setEnabled(true);
//

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
if(callState.equals("NEW")) {
    String titleString = title.getText().toString();
    String detailsString = details.getText().toString();
    Note add = new Note(detailsString, titleString, 0, TagsOnNote);
    add.id = db.insertNoteOrReminder(add);
}

        if(callState.equals("EDIT")) {
            String titleString = title.getText().toString();
            String detailsString = details.getText().toString();
            Note add = new Note(detailsString, titleString, 0, TagsOnNote);
            add.id =getIntent().getLongExtra("NOTE_ID", 0);
           db.updateNoteOrReminder(add);
        }


    }
}
