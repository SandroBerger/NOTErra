package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
         mySpinner = (Spinner) findViewById(R.id.Spinner01);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));


        forstDB = new DBHandler(this);

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

        mySpinner1 = (Spinner) findViewById(R.id.Spinner02);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize);


        baumhohe=(EditText)findViewById(R.id.baumhoehe);
        holzmenge=(EditText)findViewById(R.id.holzbewuchs_laenge_bachabschnitt);
        beschreibung=(EditText)findViewById(R.id.beschreibung);


    }

    public void save(View v){

        String baumarten=mySpinner1.getSelectedItem().toString();
        anzahl=mySpinner.getSelectedItem().toString();


        switch (anzahl) {
            case "<10":
                anzahl1=10;

                break;
            case "11-50":
                anzahl1=35;


                break;
            case "50-100":
                anzahl1=75;


                break;
            case ">100":
                anzahl1=100;


                break;

            default:
                break;

        }

if(!baumhohe.getText().toString().equals("")){
    baumhoehe=Integer.parseInt(baumhohe.getText().toString());
    holzmengen=Integer.parseInt(holzmenge.getText().toString());
    beschreibungen=beschreibung.getText().toString();
}


    forstDB.addHolzbewuchs(anzahl1,baumarten,baumhoehe,holzmengen,beschreibungen);


        if(mySpinner.getSelectedItem().toString().equals("Anzahl der Stämme/Sträucher:")){
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
        }else
            {
                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Holzbewuchs.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_holzbewuchs, menu);
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
