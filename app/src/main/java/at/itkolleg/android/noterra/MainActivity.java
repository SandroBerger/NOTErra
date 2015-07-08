package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;

import java.io.File;

/**
 * Diese Klasse erstellt die eine Directory falls sie noch nicht vorhanden ist.
 *
 * @author Gutsche Christoph
 */
public class MainActivity extends ActionBarActivity  {

    private File noterraDirectory;
    private DBHandler forstDB;
    private Time t = new Time(Time.getCurrentTimezone());
    private String date;

    private TextView textView;
    private Button inspectionButton;


    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
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

        DatenbankVerbindung();

        //Aufruf der Funktion addButton
        initialisierung();

        t.setToNow();
         date = t.format("%d.%m.%Y");
        textView.setText("Aktuelles Datum: " + date);
    }

    private void initialisierung(){
         textView=(TextView)findViewById(R.id.datum);
         inspectionButton = (Button) findViewById(R.id.inspectionbutton);
    }

    /**
     * Hier wird ein Datenbankobjekt erzeugt für die Verbindung
     */
    private void DatenbankVerbindung(){
        forstDB = new DBHandler(this);
    }


    /**
     * Wird der Button begehung starten gedrückt somit gelant man zum Hauptfenster.
     * @param v
     */
    public void onClick(View v) {
        forstDB.addBeobachtung(date);
        Intent intent=new Intent(MainActivity.this,InspectionActivity.class);
        startActivity(intent);
    }
}
