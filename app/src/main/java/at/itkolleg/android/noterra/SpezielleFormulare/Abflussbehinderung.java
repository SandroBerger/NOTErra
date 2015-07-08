package at.itkolleg.android.noterra.SpezielleFormulare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;
import at.itkolleg.android.noterra.R;


/**
 * Diese Klasse ist ein spezielles Formular names "Ereignis ohne unmittelbare Abflussbehinderung".
 * Wird in der Datenbank mit der  Tabelle "tbl_OhneBehinderung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Abflussbehinderung extends ActionBarActivity {

    private RadioGroup beobachtung;
    private RadioButton felssturz;
    private RadioButton hochwasser;
    private RadioButton lawinenablagerung;
    private RadioButton mure;
    private RadioButton rutschung;
    private RadioButton sonstiges;
    private RadioButton freiwahl;
    private EditText edit;
    private EditText besch;

    private DBHandler forstDB;
    private String auswahl;


    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abflussbehinderung);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        initialisierung();

        datenbankVerbindung();

        textFeldFokus();
    }

    /**
     * Für die Initialisierung von Radiobuttons und Editexte
     */
    private void initialisierung() {

        beobachtung = (RadioGroup) findViewById(R.id.art_beobachtung);

        felssturz = (RadioButton) findViewById(R.id.felssturz);
        hochwasser = (RadioButton) findViewById(R.id.hochwasser);
        lawinenablagerung = (RadioButton) findViewById(R.id.lawinenablagerung);
        mure = (RadioButton) findViewById(R.id.mure);
        rutschung = (RadioButton) findViewById(R.id.rutschung);
        sonstiges = (RadioButton) findViewById(R.id.sonst);
        freiwahl = (RadioButton) findViewById(R.id.freiwahl);
        edit = (EditText) findViewById(R.id.art_der_abflussbehinderung);
        besch = (EditText) findViewById(R.id.Beschreibung);
    }

    /**
     * Für die Verbindung der Datebank zuständig
     */
    private void datenbankVerbindung() {
        forstDB = new DBHandler(this);
    }

    /**
     * Diese Methode kontrolliert ob das textfeld geklickt wurde. Wurde das Sonstiges Edixtext geklickt wird automatisch die Checkbox auf True gesetzt
     */
    private void textFeldFokus() {
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                freiwahl.setChecked(true);
            }
        });
    }

    /**
     * Diese Methode ist für die Abfrage der Radiobuttons zustädnig. Sobald ein Radiobutton geklickt wird, springt es in deise Methode hinein. Ausgehend von dem XML-File mit der onclick Methode.
     *
     * @param view Die aktuelle Ansicht / View die geklickt wurde
     */
    public void artDerBeobachtung(View view) {

        int checkedRadiobut = beobachtung.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.felssturz:
                if (felssturz.isChecked()) {
                    auswahl = felssturz.getText().toString();
                }
                break;
            case R.id.hochwasser:
                if (hochwasser.isChecked()) {
                    auswahl = hochwasser.getText().toString();
                }
                break;
            case R.id.lawinenablagerung:
                if (lawinenablagerung.isChecked()) {
                    auswahl = lawinenablagerung.getText().toString();
                }
                break;
            case R.id.mure:
                if (mure.isChecked()) {
                    auswahl = mure.getText().toString();
                }
                break;
            case R.id.rutschung:
                if (rutschung.isChecked()) {
                    auswahl = rutschung.getText().toString();
                }
                break;
            case R.id.sonst:
                if (sonstiges.isChecked()) {
                    auswahl = sonstiges.getText().toString();
                }
                break;

            case R.id.freiwahl:
                if (freiwahl.isChecked()) {

                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    auswahl = edit.getText().toString();
                }
                break;
        }
    }

    /**
     * Diese Methode wird vom Button Speichern abgerufen. Als aller erstes wird kontrolliert ob alles nötige Eingegebn wurde.
     * Ist alles Vorhanden werden die Daten in die SQLite Datenbank übernommen.
     *
     * @param v View die geklickt wurde
     */
    public void save(View view) {

        if (freiwahl.isChecked()) {
            auswahl = edit.getText().toString();
        }

        if (edit.getText().toString().equals("") && freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie die Art der Beobachtung an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })


                    .show();

        } else if (!felssturz.isChecked() && !hochwasser.isChecked() && !lawinenablagerung.isChecked() && !mure.isChecked() && !rutschung.isChecked() && !sonstiges.isChecked() && !freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie die Art der Beobachtung an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })


                    .show();

        } else {
            forstDB.addOhneBehinderung(auswahl, besch.getText().toString());
            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Abflussbehinderung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);
        }


    }
}
