package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasserauseinleitung);

         mySpinner=(Spinner)findViewById(R.id.wasserauseinleitung);

        List<String> anzahl = new ArrayList<String>();
        anzahl.add("Wassereinleitung");
        anzahl.add("Wasserentnahme");
        anzahl.add("Art: Aus/Einleitung: ");

        final int listsize = anzahl.size() - 1;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, anzahl) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);

        mySpinner.setSelection(listsize);


        zweck=(RadioGroup)findViewById(R.id.zweck);

        abwasser =(RadioButton)findViewById(R.id.abwasser);
        beschneiung=(RadioButton)findViewById(R.id.beschneiung);
        bewaesserung=(RadioButton)findViewById(R.id.bewaesserung);
        trinkwasser=(RadioButton)findViewById(R.id.trinkwasser);
        freiwahl=(RadioButton)findViewById(R.id.freiwahl);
        edit=(EditText)findViewById(R.id.art_des_zweckes);

        besch=(EditText)findViewById(R.id.beschreibung);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        forstDB = new DBHandler(this);

        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                freiwahl.setChecked(true);
            }
        });
    }

    public void onclick(View view) {

        int checkedRadiobut = zweck.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.abwasser:
                if (abwasser.isChecked()) {
                   auswahl=abwasser.getText().toString();
                }
                break;
            case R.id.beschneiung:
                if (beschneiung.isChecked()) {
                    auswahl=beschneiung.getText().toString();
                }
                break;
            case R.id.bewaesserung:
                if (bewaesserung.isChecked()) {
                    auswahl=bewaesserung.getText().toString();
                }
                break;
            case R.id.trinkwasser:
                if (trinkwasser.isChecked()) {
                    auswahl=trinkwasser.getText().toString();
                }
                break;


            case R.id.freiwahl:
                if (freiwahl.isChecked()) {

                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    auswahl=edit.getText().toString();


                }
                break;


        }


    }

    public void save(View v) {


        if (freiwahl.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Text innerhalb der Auswahl des Zweckes eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })


                    .show();
        } else if (mySpinner.getSelectedItem().toString().equals("Art: Aus/Einleitung: ")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Aus- oder -Einleitung gewählt")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else if (!abwasser.isChecked() && !beschneiung.isChecked() && !bewaesserung.isChecked() && !trinkwasser.isChecked() && !freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Zweck ausgewählt")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else if (besch.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Beschreibung hinzugefügt")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else
        {

           art=mySpinner.getSelectedItem().toString();

            beschreibung=besch.getText().toString();
            forstDB.addWasserAuseinleitung(art,auswahl,beschreibung);

            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Wasserauseinleitung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);
        }








    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wasserauseinleitung, menu);
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
}
