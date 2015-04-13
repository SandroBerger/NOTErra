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


public class Schaeden_Regulierungsbauten extends ActionBarActivity {

    private RadioGroup  schaeden;
    private RadioButton geschiebsperre;
    private RadioButton laengsverbauung;
    private RadioButton querwerk;
    private RadioButton freiwahl;

    private CheckBox fehl;
    private CheckBox ausg;
    private CheckBox geschiebesperre;
    private CheckBox rissMauerwerk;
    private CheckBox schadMauerwerk;
    private CheckBox sonst;
    private CheckBox starkerbewuchs;
    private CheckBox untersp;
    private CheckBox frei;

    private EditText edit;
    private EditText edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schaeden__regulierungsbauten);



        schaeden=(RadioGroup)findViewById(R.id.schaeden);

        geschiebsperre =(RadioButton)findViewById(R.id.geschiebesperre);
        laengsverbauung=(RadioButton)findViewById(R.id.laengsverbauung);
        querwerk=(RadioButton)findViewById(R.id.querwerk);
        freiwahl=(RadioButton)findViewById(R.id.freiwahl);
        edit=(EditText)findViewById(R.id.artdesBauwerks_edittext);
        edit2=(EditText)findViewById(R.id.freiwahl_bauwerk);


        fehl=(CheckBox)findViewById(R.id.fehlabsturz);
        ausg=(CheckBox)findViewById(R.id.ausgsperrenfluegel);
        geschiebesperre=(CheckBox)findViewById(R.id.geschiebesperre2);
        rissMauerwerk=(CheckBox)findViewById(R.id.rissemauerwerk);
        schadMauerwerk=(CheckBox)findViewById(R.id.schadhaftesmauerwerk);
        sonst=(CheckBox)findViewById(R.id.sonst);
        starkerbewuchs=(CheckBox)findViewById(R.id.starkerbewuchs);
        untersp=(CheckBox)findViewById(R.id.Untersp_fundanment);
        frei=(CheckBox)findViewById(R.id.eigen);
    }




    public void onclick(View view)
    {

        int checkedRadiobut= schaeden.getCheckedRadioButtonId();

        switch(checkedRadiobut){
            case R.id.geschiebesperre:
                if(geschiebsperre.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),geschiebsperre.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.laengsverbauung:
                if(laengsverbauung.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),laengsverbauung.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.querwerk:
                if(querwerk.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),querwerk.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.freiwahl:
                if(freiwahl.isChecked()){



                    edit.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);




                }
                break;



        }


    }


    public void onclick1(View view)
    {



        ArrayList<String> schadensartBauwerk=new ArrayList<String>();

        if(fehl.isChecked())
        {
            schadensartBauwerk.add(fehl.getText().toString());
        }
        if(ausg.isChecked())
        {
            schadensartBauwerk.add(ausg.getText().toString());
        }
        if(geschiebesperre.isChecked())
        {
            schadensartBauwerk.add(geschiebesperre.getText().toString());
        }
        if(rissMauerwerk.isChecked())
        {
            schadensartBauwerk.add(rissMauerwerk.getText().toString());
        }
        if(schadMauerwerk.isChecked())
        {
            schadensartBauwerk.add(schadMauerwerk.getText().toString());
        }
        if(sonst.isChecked())
        {
            schadensartBauwerk.add(sonst.getText().toString());
        }
        if(starkerbewuchs.isChecked())
        {
            schadensartBauwerk.add(starkerbewuchs.getText().toString());
        }
        if(untersp.isChecked())
        {
            schadensartBauwerk.add(untersp.getText().toString());
        }
        if(frei.isChecked())
        {




            edit2.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edit2, InputMethodManager.SHOW_IMPLICIT);



        }

    }

    public void save(View v) {


        if (freiwahl.isChecked() || frei.isChecked())
        {
            if(edit.equals("") || edit2.equals(""))
            {
                new AlertDialog.Builder(this)
                        .setTitle("!!Achtung!!")
                        .setMessage("Es wurde kein Text eingegeben")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })



                        .show();
            }
            else
            {
                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Schaeden_Regulierungsbauten.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }
        }
        else
        {
            if(geschiebesperre.isChecked()|| laengsverbauung.isChecked() || querwerk.isChecked() || fehl.isChecked() || ausg.isChecked() || geschiebsperre.isChecked() || rissMauerwerk.isChecked() ||schadMauerwerk.isChecked() || sonst.isChecked() || starkerbewuchs.isChecked() || untersp.isChecked() )
            {
                String extra = getIntent().getStringExtra("Headline");
                Intent intent = new Intent(Schaeden_Regulierungsbauten.this, InspectionActivity.class);
                intent.putExtra("Headline", extra);
                startActivity(intent);
            }

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schaeden__regulierungsbauten, menu);
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
