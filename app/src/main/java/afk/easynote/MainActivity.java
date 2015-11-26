package afk.easynote;

import android.os.Bundle;
import android.provider.Settings;
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

import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    TabFragment tabFragment;
    NotesAdapter notesAdapter;
    RemindersAdapter remindersAdapter;

    ListView listView;
    static  List<String>  tags;
    static String notesFragmentTag;
    DatabaseHandler mHandler;
    ArrayAdapter <String> mAdapter;
   static List<Note>  NotesDataSet;
    static List<Note>  RemindersDataSet;
    String deviceId;

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = md5(android_id).toUpperCase();

        FacebookSdk.sdkInitialize(getApplicationContext());

        mHandler = new DatabaseHandler(this);
        tags = mHandler.getAllTags();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list, tags);

//
        NotesDataSet=mHandler.getAllNotes();
        RemindersDataSet=mHandler.getAllReminders();
        notesAdapter = new NotesAdapter(mHandler,this);
       remindersAdapter= new RemindersAdapter(mHandler,this);

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String temp_tag = tags.get(pos);
                if (temp_tag.equals("All"))
                {
                    return false;
                }
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp_tag = tags.get(position);
                if (temp_tag.equals("All")) {
                    notesAdapter.NotesDataSet = mHandler.getAllNotes();
                    notesAdapter.notifyDataSetChanged();
                    mDrawerLayout.closeDrawers();

                } else {
                    notesAdapter.changeDataset(temp_tag);
                    mDrawerLayout.closeDrawers();
                }
                //Toast.makeText(getApplicationContext(), " Bro, you tapped?", Toast.LENGTH_SHORT).show();
            }
        });

        final EditText mEditText = (EditText) findViewById(R.id.EditText01);
        mEditText.setImeActionLabel("Add Tag", KeyEvent.KEYCODE_ENTER);


        if (!(mHandler.addTag("All") == -1))
        {
            tags.add("All");
        }

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 66) {
                    String tag = mEditText.getText().toString();
                    if (!tag.matches("")) {
                        if (tag.equals("All")) {
                            return false;
                        }
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