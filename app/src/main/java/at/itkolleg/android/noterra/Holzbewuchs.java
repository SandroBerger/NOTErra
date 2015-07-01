package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class Holzbewuchs extends ActionBarActivity {

    private Spinner mySpinner;
    private Spinner mySpinner1;

    private EditText baumhohe;
    private EditText holzmenge;
    private EditText beschreibung;

    private DBHandler forstDB;
    private String anzahl;
    private int anzahl1;
    private int baumhoehe;
    private int holzmengen;
    private String beschreibungen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holzbewuchs);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        forstDB = new DBHandler(this);

        initialisierung();

        spinner();
        spinner1();
    }

    public void initialisierung(){
        baumhohe = (EditText) findViewById(R.id.baumhoehe);
        holzmenge = (EditText) findViewById(R.id.holzbewuchs_laenge_bachabschnitt);
        beschreibung = (EditText) findViewById(R.id.beschreibung);

        mySpinner = (Spinner) findViewById(R.id.Spinner01);
        mySpinner1 = (Spinner) findViewById(R.id.Spinner02);
    }

    public void spinner(){
        List<String> anzahl = new ArrayList<String>();
        anzahl.add("<10");
        anzahl.add("11-50");
        anzahl.add("50-100");
        anzahl.add(">100");
        anzahl.add("Anzahl der Stämme/Sträucher:");

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
    }

    public void spinner1(){
        List<String> list = new ArrayList<String>();
        list.add("Laubholz");
        list.add("Nadelholz");
        list.add("Laubholz+Nadelholz");
        list.add("Baumarten:");

        final int longsize = list.size() - 1;


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (longsize); // Truncate the list
            }
        };



        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize);
    }

    public void save(View v) {

        String baumarten = mySpinner1.getSelectedItem().toString();
        anzahl = mySpinner.getSelectedItem().toString();


        switch (anzahl) {
            case "<10":
                anzahl1 = 10;

                break;
            case "11-50":
                anzahl1 = 35;


                break;
            case "50-100":
                anzahl1 = 75;


                break;
            case ">100":
                anzahl1 = 100;

                break;
        }

        if (!baumhohe.getText().toString().equals("") && !holzmenge.getText().toString().equals("")) {
            baumhoehe = Integer.parseInt(baumhohe.getText().toString());
            holzmengen = Integer.parseInt(holzmenge.getText().toString());
        }

        beschreibungen = beschreibung.getText().toString();
        forstDB.addHolzbewuchs(anzahl1, baumarten, baumhoehe, holzmengen, beschreibungen);


        if (mySpinner.getSelectedItem().toString().equals("Anzahl der Stämme/Sträucher:")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Anzahl der Stämme bzw. Sträucher aus")
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
        } else if(baumhohe.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Baumhöhe an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            baumhohe.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();

        } else if(holzmenge.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Holzmenge an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            holzmenge.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();

        }else{
                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Holzbewuchs.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }
        }


}


