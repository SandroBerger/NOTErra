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




public class Abflussbehinderung extends ActionBarActivity {

    private RadioGroup beobachtung;
    private RadioButton felssturz;
    private RadioButton hochwasser;
    private RadioButton lawinenablagerung;
    private RadioButton mure;
    private RadioButton rutschung;
    private RadioButton sonstiges;
    private RadioButton freiwahl;
    private EditText edit;
    private EditText besch;

    private DBHandler forstDB;
    private String auswahl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abflussbehinderung);


        beobachtung=(RadioGroup)findViewById(R.id.art_beobachtung);

        felssturz =(RadioButton)findViewById(R.id.felssturz);
        hochwasser=(RadioButton)findViewById(R.id.hochwasser);
        lawinenablagerung=(RadioButton)findViewById(R.id.lawinenablagerung);
        mure=(RadioButton)findViewById(R.id.mure);
        rutschung=(RadioButton)findViewById(R.id.rutschung);
        sonstiges=(RadioButton)findViewById(R.id.sonst);
        freiwahl=(RadioButton)findViewById(R.id.freiwahl);
        edit=(EditText)findViewById(R.id.art_der_abflussbehinderung);
        besch=(EditText)findViewById(R.id.Beschreibung);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        forstDB = new DBHandler(this);

    }







    public void onclick(View view)
    {

        int checkedRadiobut= beobachtung.getCheckedRadioButtonId();

        switch(checkedRadiobut){
            case R.id.felssturz:
                if(felssturz.isChecked()){
                    auswahl=felssturz.getText().toString();
                }
                break;
            case R.id.hochwasser:
                if(hochwasser.isChecked()){
                    auswahl=hochwasser.getText().toString();
                }
                break;
            case R.id.lawinenablagerung:
                if(lawinenablagerung.isChecked()){
                    auswahl=lawinenablagerung.getText().toString();
                }
                break;
            case R.id.mure:
                if(mure.isChecked()){
                    auswahl=mure.getText().toString();
                }
                break;
            case R.id.rutschung:
                if(rutschung.isChecked()){
                    auswahl=rutschung.getText().toString();
                }
                break;
            case R.id.sonst:
                if(sonstiges.isChecked()){
                    auswahl=sonstiges.getText().toString();
                }
                break;

            case R.id.freiwahl:
                if(freiwahl.isChecked()){

                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    auswahl=edit.getText().toString();

                }
                break;





        }


    }

    public void save(View v) {




        if(freiwahl.isChecked())
        {
            auswahl=edit.getText().toString();

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
                forstDB.addOhneBehinderung(auswahl, besch.getText().toString());
                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Abflussbehinderung.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }
        }
        else
        {
            if(felssturz.isChecked() || hochwasser.isChecked() || lawinenablagerung.isChecked() || mure.isChecked() || rutschung.isChecked() || sonstiges.isChecked())
            {


                forstDB.addOhneBehinderung(auswahl, besch.getText().toString());

                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Abflussbehinderung.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_abflussbehinderung, menu);
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
