package afk.easynote;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    ListView listView;
    List<String>  tags;
    DatabaseHandler mHandler;
    ArrayAdapter <String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new DatabaseHandler(this);
        tags = mHandler.getAllTags();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list, tags);



        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        listView = (ListView)findViewById(R.id.left_drawer);
        listView.setAdapter(mAdapter);


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        //listView.getOnItemLongClickListener;

        final EditText mEditText = (EditText) findViewById(R.id.EditText01);
        mEditText.setImeActionLabel("Add", KeyEvent.KEYCODE_ENTER);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String tag = mEditText.getText().toString();
                    if (!tag.matches("")) {
                        //this change here
                        long check = mHandler.addTag(tag);
                        if (check == -1)
                        {
                            Toast.makeText(getApplicationContext(), "Tag already exists!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            tags.add(tag);
                            mAdapter.notifyDataSetChanged();//ppp
                            mEditText.setText("");
                        }

                    }
                }
            }
        });

    }
}