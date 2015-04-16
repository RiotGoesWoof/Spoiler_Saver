package edu.crazycatlady.zorpix.spoilersaver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class AddShow extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show);

        //set up firebase library in every activity that will access firebase data
        //(we can also do this once in an Application class... see the android
        //documentation for more info)
        Firebase.setAndroidContext(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_show, menu);
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

    public void addshow(View view)
    {
        //create a reference to a firebase object using your unique url
        Firebase basey = new Firebase("https://spoiler-saver.firebaseio.com/");

        final EditText showName = (EditText) findViewById(R.id.nameText);
        final EditText numEp = (EditText) findViewById(R.id.numEpisodes);

        String addme = showName.getText().toString();
        int addme2 = Integer.parseInt(numEp.getText().toString());

        basey.child("Shows/").child("Show Name").child(addme).child("Number of Episodes").child(Integer.toString(addme2)).setValue(addme2);

        Context context = getApplicationContext();
        CharSequence text = "Show Added!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
