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
 * Diese Klasse ist ein spezielles Formular names "Holzablagerungen im Hochwasserabflussbereich".
 * Wird in der Datenbank mit der  Tabelle "tbl_Holzablagerung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Holzablagerung extends ActionBarActivity {

    private Spinner mySpinner;
    private Spinner mySpinner1;

    private EditText bhd;
    private EditText holzmenge;
    private EditText lbachabschnitt;
    private EditText maßnahmen;
    private EditText kosten;

    private EditText besch;

    private DBHandler forstDB;
    private int media;
    private int bachabschnitt;
    private int holzmengen;
    private int anzahl1;
    private String anzahl;

    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holzablagerung);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        initialisierung();

        datenbankVerbindung();

        stammAnzahl();

        baumArt();
    }

    /**
     * Für die Initialisierung von Editexte
     */
    private void initialisierung() {
        mySpinner = (Spinner) findViewById(R.id.Spinner01);
        mySpinner1 = (Spinner) findViewById(R.id.Spinner02);

        bhd = (EditText) findViewById(R.id.Mediabhd);
        holzmenge = (EditText) findViewById(R.id.Holzmenge);
        lbachabschnitt = (EditText) findViewById(R.id.bachabschnitt);
    }

    /**
     * Für die Verbindung der Datebank zuständig
     */
    private void datenbankVerbindung() {
        forstDB = new DBHandler(this);
    }

    /**
     * Diese Methode erstellt den Spinner / Auswahlmöglichkeit für die Anzhal der Stämme
     * Der Arrayadapter wird mit der Liste Befüllt.
     * Anhand des Arrayadapter wird das Dropdown des Spinners befüllt.
     */
    private void stammAnzahl() {

        List<String> anzahl = new ArrayList<>();
        anzahl.add("<5");
        anzahl.add("5-20");
        anzahl.add("21-50");
        anzahl.add(">50");
        anzahl.add("Anzahl der Stämme:");


        final int listsize = anzahl.size() - 1; // Somit kann man die Überschrift nicht mehr auswählen.

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
     * Diese Methode erstellt den Spinner / Auswahlmöglichkeit für die Baumart
     * Der Arrayadapter wird mit der Liste Befüllt.
     * Anhand des Arrayadapter wird das Dropdown des Spinners befüllt.
     */
    private void baumArt() {

        List<String> list = new ArrayList<>();
        list.add("Laubholz");
        list.add("Nadelholz");
        list.add("Laubholz+Nadelholz");
        list.add("Baumarten:");

        final int longsize = list.size() - 1;// Somit kann man die Überschrift nicht mehr auswählen.


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (longsize);
            }
        };

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize);


    }


    /**
     * Diese Methode wird mit den Button speichern aufgerufen.
     * Innerhalb dieser Methode wird Kontrolliert ob alle angabgen gemacht wurden. Ist dies korrekt werden die Daten an dei Datenbank weitergeleitet.
     *
     * @param v Ist die Momentan view die geklickt wurde
     */
    public void save(View v) {

        if (mySpinner.getSelectedItem().toString().equals("Anzahl der Stämme:")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Anzahl der Stämme aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (mySpinner1.getSelectedItem().toString().equals("Baumarten:")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Baumart aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (bhd.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Media eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bhd.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();
        } else if (holzmenge.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Holzmenge eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holzmenge.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();
        } else if (lbachabschnitt.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Länge des Bachabschnittes angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lbachabschnitt.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();

        } else {


            String baumarten = mySpinner1.getSelectedItem().toString();
            anzahl = mySpinner.getSelectedItem().toString();

            // Weil in der Datenbank nur Int Werte gespeichert werden in dieser Spalte werden die angegebenen Werte gespeichert.
            switch (anzahl) {
                case "<5":
                    anzahl1 = 5;

                    break;
                case "5-20":
                    anzahl1 = 20;


                    break;
                case "21-50":
                    anzahl1 = 35;

                    break;
                case ">50":
                    anzahl1 = 50;

                    break;
            }

            if (!bhd.getText().equals("")) {
                media = Integer.parseInt(bhd.getText().toString());
            }

            if (!lbachabschnitt.getText().equals("")) {
                bachabschnitt = Integer.parseInt(lbachabschnitt.getText().toString());
            }

            if (!holzmenge.getText().equals("")) {
                holzmengen = Integer.parseInt(holzmenge.getText().toString());
            }


            forstDB.addHolzablagerung(anzahl1, baumarten, media, holzmengen, bachabschnitt);


            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Holzablagerung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);

        }


    }


}
