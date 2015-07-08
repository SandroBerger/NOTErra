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
 * Diese Klasse ist ein spezielles Formular names "Abflussbehindernde Einbauten".
 * Wird in der Datenbank mit der  Tabelle "tbl_Abflussbehinderung" abgespeichert.
 *
 * @author Gutsche Christoph
 */
public class Abflussbehinderndeeinbauten extends ActionBarActivity {

    private RadioGroup einbaut;
    private RadioButton bruecke;
    private RadioButton huette;
    private RadioButton staubrett;
    private RadioButton rohrdurchlass;
    private RadioButton freiwahl;
    private EditText editText;
    private EditText beschreibung;

    //Datenbankteil
    private DBHandler forstDB;
    private String auswahl;

    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//vorherige Informationen laden
        setContentView(R.layout.activity_ablufssbehinderndeeinbauten);  // Anbindung an das XML

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground)); //Hintergrundbild für die ActionBar

        initialisierung(); // Initialisierung

        datenbankVerbindung(); // Verbindung zur SQLite Datenbank

        textFeldFokus(); // Dient zu fokusierung des Textfeldes


    }

    /**
     * Diese Methode kontrolliert ständig ob das textfeld geklickt wurde. Wurde das Sonstiges Edixtext geklickt wird automatisch die Checkbox auf True gesetzt
     */
    private void textFeldFokus() {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                freiwahl.setChecked(true);
            }
        });
    }

    /**
     * Für die Verbindung der Datebank zuständig
     */
    private void datenbankVerbindung() {
        forstDB = new DBHandler(this);
    }

    /**
     * Für die Initialisierung von Radiobuttons und Editexte
     */
    private void initialisierung() {
        einbaut = (RadioGroup) findViewById(R.id.artdereinbauten);
        bruecke = (RadioButton) findViewById(R.id.bruckesteg);
        huette = (RadioButton) findViewById(R.id.huette);
        staubrett = (RadioButton) findViewById(R.id.staubrett);
        rohrdurchlass = (RadioButton) findViewById(R.id.rohrdurchlass);
        freiwahl = (RadioButton) findViewById(R.id.freiwahl);


        editText = (EditText) findViewById(R.id.art_sonstiges);
        beschreibung = (EditText) findViewById(R.id.Beschreibung);
    }

    /**
     * Aufgerufen wird die Methode artDerEinabut mithilfe der onclick Methode von dem XMLFile.
     * Überprüfung der Radiobuttons. Wird ein Radiobutton geklickt, wird dessen Wert abgfragt, und in einen String gespeichert
     *
     * @param view ist die View die geklickt wurde
     */
    public void artDerEinbaut(View view) {

        int checkedRadiobut = einbaut.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.bruckesteg:
                if (bruecke.isChecked()) {

                    auswahl = bruecke.getText().toString();
                }
                break;
            case R.id.huette:
                if (huette.isChecked()) {

                    auswahl = huette.getText().toString();
                }
                break;
            case R.id.staubrett:
                if (staubrett.isChecked()) {

                    auswahl = staubrett.getText().toString();
                }
                break;
            case R.id.rohrdurchlass:
                if (rohrdurchlass.isChecked()) {

                    auswahl = rohrdurchlass.getText().toString();
                }
                break;


            case R.id.freiwahl:
                if (freiwahl.isChecked()) {

                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    auswahl = editText.getText().toString();
                }
                break;
        }

    }

    /**
     * Diese Methode wird von Button Speichern (XML-File) aus aufgerufen.
     * Kontrolliert ob alle wichtigen Werte eingegeben oder ausgewält wurden.
     * Ist alles korrekt werden die Werte in der Datenbank übernommen und man gelangt zurück auf das Hauptfenster.
     *
     * @param v gibt die view an die gerade geklickt wurde
     */
    public void save(View v) {

        if (freiwahl.isChecked()) {
            auswahl = editText.getText().toString();
        }

        if (editText.getText().toString().equals("") && freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie einen Art der Einbaut ein")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    })


                    .show();
        } else if (!bruecke.isChecked() && !huette.isChecked() && !staubrett.isChecked() && !rohrdurchlass.isChecked() && !freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Art der Einbaut aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        } else {
            forstDB.addAbflussbehinderung(auswahl, beschreibung.getText().toString());

            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Abflussbehinderndeeinbauten.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);
        }
    }
}
