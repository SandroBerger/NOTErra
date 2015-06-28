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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class Abflussbehinderndeeinbauten extends ActionBarActivity {

    private  RadioGroup einbaut;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablufssbehinderndeeinbauten);

        einbaut=(RadioGroup)findViewById(R.id.artdereinbauten);

        bruecke =(RadioButton)findViewById(R.id.bruckesteg);
        huette=(RadioButton)findViewById(R.id.huette);
        staubrett=(RadioButton)findViewById(R.id.staubrett);
        rohrdurchlass=(RadioButton)findViewById(R.id.rohrdurchlass);
        freiwahl=(RadioButton)findViewById(R.id.freiwahl);

        editText=(EditText)findViewById(R.id.art_sonstiges);

        beschreibung=(EditText)findViewById(R.id.Beschreibung);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        forstDB = new DBHandler(this);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                freiwahl.setChecked(true);
            }
        });

    }

    public void onclick(View view) {

        int checkedRadiobut = einbaut.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.bruckesteg:
                if (bruecke.isChecked()) {

                    auswahl=bruecke.getText().toString();
                }
                break;
            case R.id.huette:
                if (huette.isChecked()) {

                    auswahl=huette.getText().toString();
                }
                break;
            case R.id.staubrett:
                if (staubrett.isChecked()) {

                    auswahl=staubrett.getText().toString();
                }
                break;
            case R.id.rohrdurchlass:
                if (rohrdurchlass.isChecked()) {

                    auswahl=rohrdurchlass.getText().toString();
                }
                break;


            case R.id.freiwahl:
                if (freiwahl.isChecked()) {


                    editText.requestFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    auswahl=editText.getText().toString();
                }
                break;
        }

    }

    public void save(View v){

        if(freiwahl.isChecked()){
            auswahl=editText.getText().toString();
        }


        if (editText.getText().toString().equals("") && freiwahl.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Text eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })



                    .show();
        } else if (!bruecke.isChecked() && !huette.isChecked() && !staubrett.isChecked() && !rohrdurchlass.isChecked() && !freiwahl.isChecked() ) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Art der Einbauten ausgewählt")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        } else if (beschreibung.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Beschreibung angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        } else
        {


            forstDB.addAbflussbehinderung(auswahl,beschreibung.getText().toString());

            String extra= getIntent().getStringExtra("Headline");
            Intent intent=new Intent(Abflussbehinderndeeinbauten.this, InspectionActivity.class);
            intent.putExtra("Headline",extra);
            startActivity(intent);




        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ablufssbehinderndeeinbauten, menu);
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
