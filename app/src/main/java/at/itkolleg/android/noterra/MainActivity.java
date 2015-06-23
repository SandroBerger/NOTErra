package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private File noterraDirectory;
    private DBHandler forstDB;
    private Time t = new Time(Time.getCurrentTimezone());
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    android.support.v7.app.ActionBar ab=getSupportActionBar();
         ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        if(noterraDirectory==null) {
            //Erstellt das Verzeichnis am Internen Speicher - muss noch getestet werden ob das auf allen geräten mit dem verzeichnis funktioniert!!
            noterraDirectory = new File("/storage/emulated/0/NOTErra");
            File mediaDirectory = new File("/storage/emulated/0/NOTErra/Media");
            File imageDirectory = new File("/storage/emulated/0/NOTErra/Media/Images");
            File audioDirectory = new File("/storage/emulated/0/NOTErra/Media/Audio");
            noterraDirectory.mkdir();
            mediaDirectory.mkdir();
            imageDirectory.mkdir();
            audioDirectory.mkdir();
        }

        forstDB = new DBHandler(this);
        //Aufruf der Funktion addButton
        addButton();

        TextView textView=(TextView)findViewById(R.id.datum);

        t.setToNow();
         date = t.format("%d.%m.%Y");
        textView.setText("Aktuelles Datum: " + date);
    }

    //-------------Hinzufügen der Buttonclick funktion und weiterleitung auf die Begehungsseite----
    public void addButton(){
        Button inspectionButton = (Button) findViewById(R.id.inspectionbutton);
        inspectionButton.setOnClickListener(this);
    }



    public void onClick(View v) {

        Intent intent=new Intent(MainActivity.this,InspectionActivity.class);
        intent.putExtra("Zeit",date);
        startActivity(intent);
    }

    private void buttonInspectionClick(){
        String date = t.format("%d.%m.%Y");
        forstDB.addBeobachtung(date);
        startActivity(new Intent(this, InspectionActivity.class));
    }
    //-------------------------------------------------------------------------------------------------


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
