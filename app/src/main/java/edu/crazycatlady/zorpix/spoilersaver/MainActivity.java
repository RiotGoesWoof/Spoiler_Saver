package edu.crazycatlady.zorpix.spoilersaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private List<String> spinnerFiller = new ArrayList<String>();
    private List<String> listviewFiller = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapting = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerFiller);
        Spinner spinMe = (Spinner) findViewById(R.id.spinIt);
        spinMe.setAdapter(adapting);
        spinMe.setOnItemSelectedListener(this);

        ArrayAdapter listaddapting = new ArrayAdapter(this, R.layout.custom_listview, listviewFiller);
        ListView listy = (ListView) findViewById(R.id.peepWatch);
        listy.setAdapter(listaddapting);

        //set up firebase library in every activity that will access firebase data
        //(we can also do this once in an Application class... see the android
        //documentation for more info)
        Firebase.setAndroidContext(this);

        //create a reference to a firebase object using your unique url
        Firebase basey = new Firebase("https://spoiler-saver.firebaseio.com/");

        //Retrieve new posts as they are added to Firebase
        basey.child("Shows/Show Name").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                spinnerFiller.add(snapshot.getKey());
                Spinner spinMe = (Spinner) findViewById(R.id.spinIt);
                ((ArrayAdapter) spinMe.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_AddShow) {
            Log.d("MainActivity", "Adding a Show");
            Intent intent = new Intent(this, AddShow.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_AddWatcher) {
            Log.d("MainActivity", "Adding a Watcher");
            Intent intent = new Intent(this, AddWatcher.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_ChangeEp) {
            Log.d("MainActivity", "Changing Episode");
            Intent intent = new Intent(this, ChangeEpisode.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        listviewFiller.clear();
        String text = parent.getSelectedItem().toString();

        //create a reference to a firebase object using your unique url
        Firebase basey = new Firebase("https://spoiler-saver.firebaseio.com/");

        //Retrieve new posts as they are added to Firebase
        basey.child("Shows/Show Name/"+text+"/Number of Episodes").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                TextView numbers = (TextView) findViewById(R.id.epText);
                numbers.setText(snapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });

        basey.child("Shows/Show Name/" + text + "/People Watching").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                String viewer = snapshot.getKey() + " is on episode " + snapshot.getValue();
                listviewFiller.add(viewer);
                ListView list = (ListView) findViewById(R.id.peepWatch);
                ((ArrayAdapter) list.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChild) {

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
