package afk.easynote;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    TabFragment tabFragment;
    NotesAdapter notesAdapter;

    ListView listView;
    static  List<String>  tags;
    static String notesFragmentTag;
    DatabaseHandler mHandler;
    ArrayAdapter <String> mAdapter;
   static List<Note>  NotesDataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new DatabaseHandler(this);
        tags = mHandler.getAllTags();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list, tags);

        NotesDataSet=mHandler.getAllNotes();
        notesAdapter = new NotesAdapter(mHandler,this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
       tabFragment = new TabFragment();
        mFragmentTransaction.replace(R.id.containerView,tabFragment).commit();


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,R.string.app_name);
        listView = (ListView)findViewById(R.id.left_drawer);
        listView.setAdapter(mAdapter);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   String temp_tag = tags.get(position);
              // notesAdapter.changeDataset(temp_tag);
                Toast.makeText(getApplicationContext(), " deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String temp_tag = tags.get(pos);
                mHandler.deleteTag(temp_tag);
                for (int i = 0; i < tags.size(); i++) {
                    if (tags.get(i) == temp_tag) //should I do tags.get(i).toString ()???
                    {
                        tags.remove(i);
                        break;
                    }
                }
                Toast.makeText(getApplicationContext(), temp_tag + " deleted!", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        final EditText mEditText = (EditText) findViewById(R.id.EditText01);
        mEditText.setImeActionLabel("Add Tag", KeyEvent.KEYCODE_ENTER);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 66) {
                    String tag = mEditText.getText().toString();
                    if (!tag.matches("")) {
                        //this change here
                        long check = mHandler.addTag(tag);
                        if (check == -1) {
                            Toast.makeText(getApplicationContext(), "Tag already exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            tags.add(tag);
                            mAdapter.notifyDataSetChanged();//ppp
                            mEditText.setText("");
                        }

                    }
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                }
                return false;
            }
        });

        /*mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String tag = mEditText.getText().toString();
                    if (!tag.matches("")) {
                        //this change here
                        long check = mHandler.addTag(tag);
                        if (check == -1) {
                            Toast.makeText(getApplicationContext(), "Tag already exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            tags.add(tag);
                            mAdapter.notifyDataSetChanged();//ppp
                            mEditText.setText("");
                        }

                    }
                }
            }
        });*/

    }
}