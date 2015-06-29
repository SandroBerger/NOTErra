package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;



public class Schaeden_Regulierungsbauten extends ActionBarActivity   {

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

    private EditText edit;
    private EditText hoehe; //Freie Höhe bis Dammkrone


    private DBHandler forstDB;
    private String auswahl;
    private int fehlendeAbsturzsicherung=0;
    private int ausgangSperrenfluegel=0;
    private int geschiebesperre1=0;
    private int risse=0;
    private int schadhaftesMauerwerk=0;
    private int sonstiges=0;
    private int bewuchs=0;
    private int unterspulfundament=0;
    private int hoehen=0;
    private String beschreibungen;
    private String bauwerkart;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_schaeden__regulierungsbauten);

        schaeden = (RadioGroup) findViewById(R.id.schaeden);

        geschiebsperre = (RadioButton) findViewById(R.id.geschiebesperre);
        laengsverbauung = (RadioButton) findViewById(R.id.laengsverbauung);
        querwerk = (RadioButton) findViewById(R.id.querwerk);
        freiwahl = (RadioButton) findViewById(R.id.freiwahl);
        edit = (EditText) findViewById(R.id.artdesBauwerks_edittext);



        fehl = (CheckBox) findViewById(R.id.fehlabsturz);
        ausg = (CheckBox) findViewById(R.id.ausgsperrenfluegel);
        geschiebesperre = (CheckBox) findViewById(R.id.geschiebesperre2);
        rissMauerwerk = (CheckBox) findViewById(R.id.rissemauerwerk);
        schadMauerwerk = (CheckBox) findViewById(R.id.schadhaftesmauerwerk);
        sonst = (CheckBox) findViewById(R.id.sonst);
        starkerbewuchs = (CheckBox) findViewById(R.id.starkerbewuchs);
        untersp = (CheckBox) findViewById(R.id.Untersp_fundanment);

        hoehe=(EditText)findViewById(R.id.freiehoehedammkrone);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        forstDB = new DBHandler(this);

     edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             freiwahl.setChecked(true);
         }
     });





    }




    public void onclick(View view)
    {


        int checkedRadiobut= schaeden.getCheckedRadioButtonId();

        switch(checkedRadiobut){
            case R.id.geschiebesperre:
                if(geschiebsperre.isChecked()){
                    auswahl=geschiebsperre.getText().toString();
                }
                break;
            case R.id.laengsverbauung:
                if(laengsverbauung.isChecked()){
                    auswahl=laengsverbauung.getText().toString();
                }
                break;
            case R.id.querwerk:
                if(querwerk.isChecked()){
                    auswahl=querwerk.getText().toString();
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


    public void onclick1(View view)
    {


        if(fehl.isChecked())
        {

            fehlendeAbsturzsicherung=1;
        }
        if(ausg.isChecked())
        {

            ausgangSperrenfluegel=1;
        }
        if(geschiebesperre.isChecked())
        {

            geschiebesperre1=1;
        }
        if(rissMauerwerk.isChecked())
        {

            risse=1;
        }
        if(schadMauerwerk.isChecked())
        {

            schadhaftesMauerwerk=1;
        }
        if(sonst.isChecked())
        {

            sonstiges=1;
        }
        if(starkerbewuchs.isChecked())
        {

            bewuchs=1;
        }
        if(untersp.isChecked())
        {

            unterspulfundament=1;
        }

    }

    public void save(View v) {




        if(!hoehe.getText().toString().equals("")) {
            hoehen = Integer.parseInt(hoehe.getText().toString());
        }

        if(freiwahl.isChecked()){
            auswahl=edit.getText().toString();
        }

        forstDB.addSchadenAnRegulierung(auswahl,hoehen,fehlendeAbsturzsicherung,ausgangSperrenfluegel,geschiebesperre1,risse,schadhaftesMauerwerk,sonstiges,bewuchs, unterspulfundament);



        if (freiwahl.isChecked() && edit.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben sie die Art des Bauwerks an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })



                    .show();
        } else if (!geschiebsperre.isChecked() && !laengsverbauung.isChecked() && !querwerk.isChecked() && !freiwahl.isChecked() ) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Art des Bauwerkes aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        } else if(!fehl.isChecked() && !ausg.isChecked() && !geschiebesperre.isChecked() && !rissMauerwerk.isChecked() && !schadMauerwerk.isChecked() && !sonst.isChecked() && !starkerbewuchs.isChecked() && !untersp.isChecked()){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie mind. eine Schadensart uas")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        }else if(hoehe.getText().toString().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben sie eine freie Höhe bis zur Dammkrone an")
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

}
