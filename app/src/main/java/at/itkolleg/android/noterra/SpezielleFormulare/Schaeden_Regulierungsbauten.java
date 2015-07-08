package at.itkolleg.android.noterra.SpezielleFormulare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;
import at.itkolleg.android.noterra.R;

/**
 * Diese Klasse ist ein spezielles Formular names "Schäden an Regulierungsbauten".
 * Wird in der Datenbank mit der  Tabelle "tbl_SchadenAnRegulierung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Schaeden_Regulierungsbauten extends ActionBarActivity {

    private RadioGroup schaeden;
    private RadioButton geschiebsperre;
    private RadioButton laengsverbauung;
    private RadioButton querwerk;
    private RadioButton freiwahl;

    private CheckBox fehl;
    private CheckBox ausg;
    private CheckBox geschiebesperre;
    private CheckBox rissMauerwerk;
    private CheckBox schadMauerwerk;
    private CheckBox sonst;
    private CheckBox starkerbewuchs;
    private CheckBox untersp;

    private EditText edit;
    private EditText hoehe; //Freie Höhe bis Dammkrone


    private DBHandler forstDB;
    private String auswahl;
    private int fehlendeAbsturzsicherung = 0;
    private int ausgangSperrenfluegel = 0;
    private int geschiebesperre1 = 0;
    private int risse = 0;
    private int schadhaftesMauerwerk = 0;
    private int sonstiges = 0;
    private int bewuchs = 0;
    private int unterspulfundament = 0;
    private int hoehen = 0;
    private String beschreibungen;
    private String bauwerkart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schaeden__regulierungsbauten);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        initialisierung();

        datenbankVerbindung();

        textFeldFokus();
    }

    /**
     * Für die Initialisierung von Radiobuttons und Editexte
     */
    private void initialisierung() {

        schaeden = (RadioGroup) findViewById(R.id.schaeden);

        geschiebsperre = (RadioButton) findViewById(R.id.geschiebesperre);
        laengsverbauung = (RadioButton) findViewById(R.id.laengsverbauung);
        querwerk = (RadioButton) findViewById(R.id.querwerk);
        freiwahl = (RadioButton) findViewById(R.id.freiwahl);
        edit = (EditText) findViewById(R.id.artdesBauwerks_edittext);

        fehl = (CheckBox) findViewById(R.id.fehlabsturz);
        ausg = (CheckBox) findViewById(R.id.ausgsperrenfluegel);
        geschiebesperre = (CheckBox) findViewById(R.id.geschiebesperre2);
        rissMauerwerk = (CheckBox) findViewById(R.id.rissemauerwerk);
        schadMauerwerk = (CheckBox) findViewById(R.id.schadhaftesmauerwerk);
        sonst = (CheckBox) findViewById(R.id.sonst);
        starkerbewuchs = (CheckBox) findViewById(R.id.starkerbewuchs);
        untersp = (CheckBox) findViewById(R.id.Untersp_fundanment);

        hoehe = (EditText) findViewById(R.id.freiehoehedammkrone);

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
     * Mit dieser Methode werden die Radiobuttons überprüft. Stimmt nun die geklickte Id mit der jeweiligen id eines Radiobuttons überein wird dieser Radiobuttontext als String abgespeichert
     *
     * @param view ist die Momentane view die angeklickt wurde
     */
    public void artDesBauwerks(View view) {
        int checkedRadiobut = schaeden.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.geschiebesperre:
                if (geschiebsperre.isChecked()) {
                    auswahl = geschiebsperre.getText().toString();
                }
                break;
            case R.id.laengsverbauung:
                if (laengsverbauung.isChecked()) {
                    auswahl = laengsverbauung.getText().toString();
                }
                break;
            case R.id.querwerk:
                if (querwerk.isChecked()) {
                    auswahl = querwerk.getText().toString();
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
     * Diese Methode wird mit einen Klick auf eine Checkbox ausgelöst.
     * In dieser Methode werden die Checkboxes überprüft.
     *
     * @param view ist die Momentan view die ausgewählt wurde / angeklickt.
     */
    public void schdaensartBauwerk(View view) {
        if (fehl.isChecked()) {
            fehlendeAbsturzsicherung = 1;
        } else {
            fehlendeAbsturzsicherung = 0;
        }

        if (ausg.isChecked()) {
            ausgangSperrenfluegel = 1;
        } else {
            ausgangSperrenfluegel = 0;
        }
        if (geschiebesperre.isChecked()) {
            geschiebesperre1 = 1;
        } else {
            geschiebesperre1 = 0;
        }

        if (rissMauerwerk.isChecked()) {
            risse = 1;
        } else {
            risse = 0;
        }

        if (schadMauerwerk.isChecked()) {
            schadhaftesMauerwerk = 1;
        } else {
            schadhaftesMauerwerk = 0;
        }

        if (sonst.isChecked()) {
            sonstiges = 1;
        } else {
            sonstiges = 0;
        }
        if (starkerbewuchs.isChecked()) {
            bewuchs = 1;
        } else {
            bewuchs = 0;
        }

        if (untersp.isChecked()) {
            unterspulfundament = 1;
        } else {
            unterspulfundament = 0;
        }

    }

    /**
     * Diese Methode wird mit den Button speichern aufgerufen.
     * Innerhalb dieser Methode wird kontrolliert ob alle angabgen gemacht wurden. Ist dies korrekt werden die Daten an dei Datenbank weitergeleitet.
     *
     * @param v Ist die Momentan view die geklickt wurde
     */
    public void save(View v) {

        if (!hoehe.getText().toString().equals("")) {
            hoehen = Integer.parseInt(hoehe.getText().toString());
        }

        if (freiwahl.isChecked()) {
            auswahl = edit.getText().toString();
        }

        if (freiwahl.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben sie die Art des Bauwerks an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })


                    .show();
        } else if (!geschiebsperre.isChecked() && !laengsverbauung.isChecked() && !querwerk.isChecked() && !freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Art des Bauwerkes aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        } else if (!fehl.isChecked() && !ausg.isChecked() && !geschiebesperre.isChecked() && !rissMauerwerk.isChecked() && !schadMauerwerk.isChecked() && !sonst.isChecked() && !starkerbewuchs.isChecked() && !untersp.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie mind. eine Schadensart uas")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        } else if (hoehe.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben sie eine freie Höhe bis zur Dammkrone an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hoehe.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })

                    .show();
        } else {

            forstDB.addSchadenAnRegulierung(auswahl,
                    hoehen,
                    fehlendeAbsturzsicherung,
                    ausgangSperrenfluegel,
                    geschiebesperre1,
                    risse,
                    schadhaftesMauerwerk,
                    sonstiges,
                    bewuchs,
                    unterspulfundament);

            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Schaeden_Regulierungsbauten.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);

        }


    }

}
