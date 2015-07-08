package at.itkolleg.android.noterra.SpezielleFormulare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;
import at.itkolleg.android.noterra.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist ein spezielles Formular names "Wasseraus- und -einleitungen".
 * Wird in der Datenbank mit der  Tabelle "tbl_WasserAusEinleitung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Wasserauseinleitung extends ActionBarActivity {

    private Spinner mySpinner;

    private RadioGroup zweck;
    private RadioButton abwasser;
    private RadioButton beschneiung;
    private RadioButton bewaesserung;
    private RadioButton trinkwasser;
    private RadioButton freiwahl;

    private EditText edit;
    private EditText besch;

    private DBHandler forstDB;
    private String auswahl;
    private String beschreibung;
    private String art;


    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasserauseinleitung);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        initialisierung();

        datenbankVerbindung();

        textFeldFokus();

        artDerAusEinleitung();

    }

    /**
     * Für die Initialisierung von Radiobuttons und Editexte
     */
    private void initialisierung() {
        mySpinner = (Spinner) findViewById(R.id.wasserauseinleitung);
        zweck = (RadioGroup) findViewById(R.id.zweck);
        abwasser = (RadioButton) findViewById(R.id.abwasser);
        beschneiung = (RadioButton) findViewById(R.id.beschneiung);
        bewaesserung = (RadioButton) findViewById(R.id.bewaesserung);
        trinkwasser = (RadioButton) findViewById(R.id.trinkwasser);
        freiwahl = (RadioButton) findViewById(R.id.freiwahl);
        edit = (EditText) findViewById(R.id.art_des_zweckes);
        besch = (EditText) findViewById(R.id.beschreibung);


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
     * Diese Methode erstellt den Spinner / Auswahlmöglichkeit für die Art ob es eine Aus- oder -Einleitung ist.
     * Der Arrayadapter wird mit der Liste Befüllt.
     * Anhand des Arrayadapter wird das Dropdown des Spinners befüllt.
     */
    private void artDerAusEinleitung() {
        List<String> anzahl = new ArrayList<String>();
        anzahl.add("Wassereinleitung");
        anzahl.add("Wasserentnahme");
        anzahl.add("Art: Aus/Einleitung: ");

        final int listsize = anzahl.size() - 1; // Somit wird die Überschrift nicht mehr angezeigt.

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, anzahl) {
            @Override
            public int getCount() {
                return (listsize);
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setSelection(listsize);



    }

    /**
     * Diese Methode wird von dem jeweiligen Radiobutton aufgerufen.
     * Stimmt die Radiobuttonid mit der ausgewälten id überein wird dessen String abgepseichert
     * @param view
     */
    public void zweck(View view) {

        int checkedRadiobut = zweck.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.abwasser:
                if (abwasser.isChecked()) {
                    auswahl = abwasser.getText().toString();
                }
                break;
            case R.id.beschneiung:
                if (beschneiung.isChecked()) {
                    auswahl = beschneiung.getText().toString();
                }
                break;
            case R.id.bewaesserung:
                if (bewaesserung.isChecked()) {
                    auswahl = bewaesserung.getText().toString();
                }
                break;
            case R.id.trinkwasser:
                if (trinkwasser.isChecked()) {
                    auswahl = trinkwasser.getText().toString();
                }
                break;


            case R.id.freiwahl:
                if (freiwahl.isChecked()) {

                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                }
                break;


        }


    }


    /**
     * Diese Methode wird mit den Button speichern aufgerufen.
     * Innerhalb dieser Methode wird kontrolliert ob alle angabgen gemacht wurden. Ist dies korrekt werden die Daten an dei Datenbank weitergeleitet.
     *
     * @param v Ist die Momentan view die geklickt wurde
     */
    public void save(View v) {

        if (freiwahl.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie einen Zweck an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })


                    .show();
        } else if (mySpinner.getSelectedItem().toString().equals("Art: Aus/Einleitung: ")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen sie eiene Art der Aus- oder -Einleitung aus.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else if (!abwasser.isChecked() && !beschneiung.isChecked() && !bewaesserung.isChecked() && !trinkwasser.isChecked() && !freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie einen Zweck aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else {

            if(freiwahl.isChecked()){
                auswahl = edit.getText().toString();
            }
            art = mySpinner.getSelectedItem().toString();
            beschreibung = besch.getText().toString();

            forstDB.addWasserAuseinleitung(art, auswahl, beschreibung);


            Intent intent = new Intent(Wasserauseinleitung.this, InspectionActivity.class);

            startActivity(intent);
        }
    }

}
