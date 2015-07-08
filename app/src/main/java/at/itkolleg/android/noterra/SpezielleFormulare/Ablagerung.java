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
 * Diese Klasse ist ein spezielles Formular names "Ablagerung sonst. abflusshemmender Gegenstände".
 * Wird in der Datenbank mit der  Tabelle "tbl_Ablagerung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Ablagerung extends ActionBarActivity {

    private RadioGroup ablagerung;
    private RadioButton bauaushub;
    private RadioButton felsbloecke;
    private RadioButton muellablagerung;
    private RadioButton schotter;
    private RadioButton eigenes;


    private EditText edit;
    private EditText besch;
    private EditText groeße;
    private EditText laengebach;
    private EditText maßnahmen;

    private DBHandler forstDB;
    private String auswahl;
    private int bachabschnitt;
    private int ausmas;

    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablagerung);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        initialisierung();

        datenbankVerbindung();

        textFeldFokus();
    }


    /**
     * Für die Initialisierung von Radiobuttons und Editexte
     */
    private void initialisierung() {

        ablagerung = (RadioGroup) findViewById(R.id.ablagerung);
        bauaushub = (RadioButton) findViewById(R.id.Bauaushub);
        felsbloecke = (RadioButton) findViewById(R.id.fesblock);
        muellablagerung = (RadioButton) findViewById(R.id.mull);
        schotter = (RadioButton) findViewById(R.id.schotter);
        eigenes = (RadioButton) findViewById(R.id.freiwahl);

        edit = (EditText) findViewById(R.id.sonstiges);

        besch = (EditText) findViewById(R.id.Beschreibung);
        groeße = (EditText) findViewById(R.id.Großausmaß);
        laengebach = (EditText) findViewById(R.id.laenge_bachabschnitt);
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
                eigenes.setChecked(true);
            }
        });
    }


    /**
     * Diese Methode dient zur überprüfung der Radiobuttons
     * Wird nun ein Radiobutton gedrückt wird dessen Wert in einen String gespeichert
     *
     * @param v ist die View die geklickt wurde
     */
    public void ablagerungsArt(View v) {

        int checkedRadiobut = ablagerung.getCheckedRadioButtonId();
        switch (checkedRadiobut) {
            case R.id.Bauaushub:
                if (bauaushub.isChecked()) {
                    auswahl = bauaushub.getText().toString();
                }
                break;
            case R.id.fesblock:
                if (felsbloecke.isChecked()) {
                    auswahl = felsbloecke.getText().toString();
                }
                break;
            case R.id.mull:
                if (muellablagerung.isChecked()) {
                    auswahl = muellablagerung.getText().toString();
                }
                break;
            case R.id.schotter:
                if (schotter.isChecked()) {
                    auswahl = schotter.getText().toString();
                }
                break;
            case R.id.freiwahl:
                if (eigenes.isChecked()) {


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
    public void save(View v) {

        if (eigenes.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Ablagerungsart an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }

                    })


                    .show();
        } else if (!bauaushub.isChecked() && !felsbloecke.isChecked() && !muellablagerung.isChecked() && !schotter.isChecked() && !eigenes.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Ablagerungsart aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();


        } else if (groeße.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Größe bzw. Ausmaß an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            groeße.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                        }
                    })

                    .show();
        } else if (laengebach.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben sie eine Länge des Bachabschnittes an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            laengebach.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })

                    .show();
        } else {

            String beschreibung = besch.getText().toString();

            if (!laengebach.getText().equals("")) {
                bachabschnitt = Integer.parseInt(laengebach.getText().toString());
                ausmas = Integer.parseInt(groeße.getText().toString());
            }

            if (eigenes.isChecked()) {
                auswahl = edit.getText().toString();
            }


            forstDB.addAblagerung(auswahl, beschreibung, bachabschnitt, ausmas);

            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Ablagerung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);
        }
    }
}
