package com.example.android.noterra;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //Aufruf der Funktion addButton
        addButton();
    }


    public void addButton(){
        Button formButton = (Button) findViewById(R.id.formbutton);
        Button noteButton = (Button) findViewById(R.id.notebutton);
        Button gpsButton = (Button) findViewById(R.id.gpsbutton);
        Button finishButton = (Button) findViewById(R.id.finishbutton);

        formButton.setOnClickListener(this);
        noteButton.setOnClickListener(this);
        gpsButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
    }

    //Führt je nach dem welche id mit dem click auf den Button mitgegeben wurde aus.
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.formbutton:
                buttonFormClick();
                break;

            case R.id.notebutton:
                buttonNoteClick();
                break;

            case R.id.gpsbutton:
                buttonGpsClick();
                break;

            case R.id.finishbutton:
                buttonFinishClick();
                break;
        }
    }

    //Ermöglicht die weiterleitung zwischen den einzelnen bereichen der Anwendung. Es wird ein view und die jeweilige Klasse mitgegeben auf die weitergeleitet werden soll.
    private void buttonFormClick(){
        startActivity(new Intent(this, FormActivity.class));
    }
    private void buttonNoteClick(){
        startActivity(new Intent(this, NotesActivity.class));
    }
    private void buttonGpsClick(){
        startActivity(new Intent(this, GpsActivity.class));
    }
    private void buttonFinishClick(){
        startActivity(new Intent(this, FinishActivity.class));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
