package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holzablagerung);

        bhd=(EditText)findViewById(R.id.Mediabhd);
        holzmenge=(EditText)findViewById(R.id.Holzmenge);
        lbachabschnitt=(EditText)findViewById(R.id.bachabschnitt);



        forstDB = new DBHandler(this);


         mySpinner = (Spinner) findViewById(R.id.Spinner01);

        List<String> anzahl = new ArrayList<>();
        anzahl.add("<5");
        anzahl.add("5-20");
        anzahl.add("21-50");
        anzahl.add(">50");
        anzahl.add("Anzahl der Stämme:");


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


        List<String> list = new ArrayList<>();
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

         mySpinner1 = (Spinner) findViewById(R.id.Spinner02);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));



    }



    public void onclick(View v) {

        if(mySpinner.getSelectedItem().toString().equals("Anzahl der Stämme:")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Anzahl der Stämme aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (mySpinner1.getSelectedItem().toString().equals("Baumarten:")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Baumart aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if(bhd.getText().toString().equals(""))
        {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Media eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (holzmenge.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Holzmenge eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (lbachabschnitt.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Länge des Bachabschnittes angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();

        } else
        {


            String baumarten=mySpinner1.getSelectedItem().toString();
             anzahl=mySpinner.getSelectedItem().toString();


            switch (anzahl) {
                case "<5":
                    anzahl1=5;

                    break;
                case "5-20":
                    anzahl1=20;


                    break;
                case "21-50":
                    anzahl1=35;

                    break;
                case ">50":
                    anzahl1=50;

                    break;


                default:
                    break;

            }

            if(!bhd.getText().equals(""))
            {
                media= Integer.parseInt(bhd.getText().toString());
                bachabschnitt=Integer.parseInt(lbachabschnitt.getText().toString());
            }
                holzmengen=Integer.parseInt(holzmenge.getText().toString());


            forstDB.addHolzablagerung(anzahl1,baumarten,media,holzmengen,bachabschnitt);


            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Holzablagerung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);

        }





    }





}
