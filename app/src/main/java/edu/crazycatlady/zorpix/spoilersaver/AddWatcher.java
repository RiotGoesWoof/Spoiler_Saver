package edu.crazycatlady.zorpix.spoilersaver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;


public class AddWatcher extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    private List<String> spinnerFiller = new ArrayList<String>();
    private String spintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watcher);
        ArrayAdapter adapting = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerFiller);
        Spinner spinMe = (Spinner) findViewById(R.id.spinIt);
        spinMe.setAdapter(adapting);
        spinMe.setOnItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.menu_add_watcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_MainActivity) {
            Log.d("AddShow", "Going Back");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spintext = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void addwat(View view)
    {
        //create a reference to a firebase object using your unique url
        Firebase basey = new Firebase("https://spoiler-saver.firebaseio.com/");

        final EditText watName = (EditText) findViewById(R.id.watName);
        final EditText watEp = (EditText) findViewById(R.id.currentEp);

        String addme = watName.getText().toString();
        String addme2 = watEp.getText().toString();

        basey.child("Shows/").child("Show Name").child(spintext).child("People Watching").child(addme).setValue(addme2);

        Context context = getApplicationContext();
        CharSequence text = "Watcher Added!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
