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


public class Ablagerung extends ActionBarActivity {

    private RadioGroup  ablagerung;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablagerung);

        ablagerung=(RadioGroup)findViewById(R.id.ablagerung);
        bauaushub =(RadioButton)findViewById(R.id.Bauaushub);
        felsbloecke=(RadioButton)findViewById(R.id.fesblock);
        muellablagerung=(RadioButton)findViewById(R.id.mull);
        schotter=(RadioButton)findViewById(R.id.schotter);
        eigenes=(RadioButton)findViewById(R.id.freiwahl);


        edit=(EditText)findViewById(R.id.sonstiges);

        besch=(EditText)findViewById(R.id.Beschreibung);
        groeße=(EditText)findViewById(R.id.Großausmaß);
        laengebach=(EditText)findViewById(R.id.laenge_bachabschnitt);


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));


        forstDB = new DBHandler(this);


        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                eigenes.setChecked(true);
            }
        });

    }

    public void editclick(View view)
    {
        eigenes.setChecked(true);

    }

    public void onclick(View v){

        int checkedRadiobut= ablagerung.getCheckedRadioButtonId();
        switch(checkedRadiobut){
            case R.id.Bauaushub:
                if(bauaushub.isChecked()){
                    auswahl=bauaushub.getText().toString();
                }
                break;
            case R.id.fesblock:
                if(felsbloecke.isChecked()){
                    auswahl=felsbloecke.getText().toString();
                }
                break;
            case R.id.mull:
                if(muellablagerung.isChecked()){
                    auswahl=muellablagerung.getText().toString();
                }
                break;
            case R.id.schotter:
                if(schotter.isChecked()){
                    auswahl=schotter.getText().toString();
                }
                break;
            case R.id.freiwahl:
                if(eigenes.isChecked()){


                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    auswahl=edit.getText().toString();

                }
                break;

        }


    }








    public void save(View v) {


        if (eigenes.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Text eingegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.drawable.warning_ablagerungsart)


                    .show();
        } else if (!bauaushub.isChecked() && !felsbloecke.isChecked() && !muellablagerung.isChecked() && !schotter.isChecked() && !eigenes.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde kein Ablagerungsart ausgewählt")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        } else if (besch.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Beschreibung angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        } else if (groeße.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Größe oder Ausmaß angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        } else if (laengebach.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Es wurde keine Länge zum Bachabschnitt angegeben")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();



        }  else
        {



            String beschreibung=besch.getText().toString();

            if(!laengebach.getText().equals(""))
            {
                bachabschnitt= Integer.parseInt(laengebach.getText().toString());
                ausmas=Integer.parseInt(groeße.getText().toString());
            }



            forstDB.addAblagerung(auswahl,beschreibung, bachabschnitt,ausmas);

            String extra = getIntent().getStringExtra("Headline");
            Intent intent = new Intent(Ablagerung.this, InspectionActivity.class);
            intent.putExtra("Headline", extra);
            startActivity(intent);


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ablagerung, menu);
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
