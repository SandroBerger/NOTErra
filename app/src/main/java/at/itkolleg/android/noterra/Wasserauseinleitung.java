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

    private RadioGroup zweck;
    private RadioButton abwasser;
    private RadioButton beschneiung;
    private RadioButton bewaesserung;
    private RadioButton trinkwasser;
    private RadioButton freiwahl;

    private EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasserauseinleitung);

        Spinner mySpinner=(Spinner)findViewById(R.id.wasserauseinleitung);

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


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

    }

    public void onclick(View view) {

        int checkedRadiobut = zweck.getCheckedRadioButtonId();

        switch (checkedRadiobut) {
            case R.id.abwasser:
                if (abwasser.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), abwasser.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.beschneiung:
                if (beschneiung.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), beschneiung.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.bewaesserung:
                if (bewaesserung.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), bewaesserung.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.trinkwasser:
                if (trinkwasser.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), trinkwasser.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;


            case R.id.freiwahl:
                if (freiwahl.isChecked()) {

                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);



                }
                break;


        }


    }

    public void save(View v){

        if(freiwahl.isChecked())
        {


        if (edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Text eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })



                    .show();
        } else {
            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Wasserauseinleitung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);
        }
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
