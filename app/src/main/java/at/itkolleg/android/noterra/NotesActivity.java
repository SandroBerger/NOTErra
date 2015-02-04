package at.itkolleg.android.noterra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class NotesActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        addButton();
    }
//-------------Button Hinzufügen und funktion hinzufügen zugriff auf kamera, ufnahme von audio und notiz aufnahme----
    public void addButton(){
        ImageButton cameraButton = (ImageButton) findViewById(R.id.camerabutton);
        ImageButton recordButton = (ImageButton) findViewById(R.id.recordbutton);
        ImageButton playButton = (ImageButton) findViewById(R.id.playbutton);
        ImageButton stopButton = (ImageButton) findViewById(R.id.stopbutton);
        Button saveButton = (Button) findViewById(R.id.savebutton);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.deletebutton);

        cameraButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.camerabutton:
                cameraButtonClick();
                break;
            case R.id.recordbutton:
                recordButtonClick();
                break;
            case R.id.playbutton:
                playButtonClick();
                break;
            case R.id.stopbutton:
                stopButtonClick();
                break;
            case R.id.savebutton:
                saveButtonClick();
                break;
            case R.id.deletebutton:
                imageDeleteButton();
                break;
        }
    }

    private void cameraButtonClick(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(new Intent(intent));
    }
    private void recordButtonClick(){
        Context context = getApplicationContext();
        CharSequence text = "Aufnahme wird gestartet!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private void playButtonClick(){
        Context context = getApplicationContext();
        CharSequence text = "Aufnahme wird abgespielt!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private void stopButtonClick(){
        Context context = getApplicationContext();
        CharSequence text = "Aufnahme wird gestoppt!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private void saveButtonClick(){
        startActivity(new Intent(this, MainActivity.class));
    }
    private void imageDeleteButton(){
        Context context = getApplicationContext();
        CharSequence text = "Bild wird gelöscht!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
//---------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
            return rootView;
        }
    }
}
