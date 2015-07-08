package at.itkolleg.android.noterra.Hauptformular;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.R;
import at.itkolleg.android.noterra.SpezielleFormulare.*;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends ActionBarActivity {

    private EditText gemeinde;
    private EditText kosten;
    private EditText maßnahmen;

    private CheckBox absturzsicherung;
    private CheckBox baumestr;
    private CheckBox bauwerksan;
    private CheckBox bauwerkwart;
    private CheckBox durchlassfrei;
    private CheckBox genehmigung;
    private CheckBox hindernisseentf;
    private CheckBox hindernissespreng;
    private CheckBox holzablang;
    private CheckBox keinemaßnahm;
    private CheckBox sperreodgerinne;
    private CheckBox ufersichern;
    private CheckBox zustandbeob;

    private int absturzsicherungint;
    private int baumestrint;
    private int bauwerksanint;
    private int bauwerkwartint;
    private int durchlassfreiint;
    private int genehmigungint;
    private int hindernisseentfint;
    private int hindernissesprengint;
    private int holzablangint;
    private int keinemaßnahmint;
    private int sperreodgerinneint;
    private int ufersichernint;
    private int zustandbeobint;
    private DBHandler forstDB;

    private String gemeindestr;
    private String date;
    private int kostenint;
    private String maßnhamenstr;
    private String prioritat;
    private int foederfahig;
    private String abwicklung;


    private Spinner mySpinner1;
    private Spinner mySpinner2;
    private Spinner mySpinner5;
    private Spinner mySpinner4;
    private Spinner mySpinner3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        forstDB = new DBHandler(this);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));
        initialisierung();
        getDate();
        spinner1(); // Für die Priorität
        spinner2(); // Für die Förderfähigkeit
        spinner3(); // Für die Abwicklung
        auswahlFormular(); // Auswahl der Speziellen Formulare
    }

    public void getDate(){
        Cursor czweck = forstDB.getLastInformation("Zeit", "tbl_Beobachtung");
        date = czweck.getString(0);
    }


    public void initialisierung() {
        absturzsicherung = (CheckBox) findViewById(R.id.abstuzsicherung);
        baumestr = (CheckBox) findViewById(R.id.baumestraucher);
        bauwerksan = (CheckBox) findViewById(R.id.bauwerksan);
        bauwerkwart = (CheckBox) findViewById(R.id.bauwerkwar);
        durchlassfrei = (CheckBox) findViewById(R.id.durchlass);
        genehmigung = (CheckBox) findViewById(R.id.genehmigung);
        hindernisseentf = (CheckBox) findViewById(R.id.hindernisseentf);
        hindernissespreng = (CheckBox) findViewById(R.id.hindernissp);
        holzablang = (CheckBox) findViewById(R.id.holzablang);
        keinemaßnahm = (CheckBox) findViewById(R.id.keineMaßn);
        sperreodgerinne = (CheckBox) findViewById(R.id.sperreodgerinne);
        ufersichern = (CheckBox) findViewById(R.id.ufersich);
        zustandbeob = (CheckBox) findViewById(R.id.zustandbeob);

        gemeinde = (EditText) findViewById(R.id.gemeinde);
        kosten = (EditText) findViewById(R.id.kosten);
        maßnahmen = (EditText) findViewById(R.id.maßnahmen);

        mySpinner1 = (Spinner) findViewById(R.id.Spinner01);
        mySpinner2 = (Spinner) findViewById(R.id.Spinner02);
        mySpinner3 = (Spinner) findViewById(R.id.Spinner03);
        mySpinner4 = (Spinner) findViewById(R.id.Spinner04);

    }


    public void spinner1(){
        List<String> anzahl = new ArrayList<String>();
        anzahl.add("Niedrig(in 6-7 Jahren)");
        anzahl.add("Mittel(in 3-5 Jahren)");
        anzahl.add("Hoch (in 1-2 Jahren)");
        anzahl.add("Priorität");

        final int listsize = anzahl.size() - 1;

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, anzahl) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter);

        mySpinner1.setSelection(listsize);
    }

    public void spinner2(){
        List<String> list = new ArrayList<String>();
        list.add("Ja");
        list.add("Nein");
        list.add("Förderfähig");

        final int longsize1 = list.size() - 1;


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (longsize1); // Truncate the list
            }
        };


        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(dataAdapter1);
        mySpinner2.setSelection(longsize1);
    }

    public void spinner3(){
        List<String> list1 = new ArrayList<String>();
        list1.add("Gemeinde");
        list1.add("Gebietsbauleitung");
        list1.add("Abwicklung");

        final int longsize2 = list1.size() - 1;


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1) {
            @Override
            public int getCount() {
                return (longsize2); // Truncate the list
            }
        };


        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner3.setAdapter(dataAdapter2);
        mySpinner3.setSelection(longsize2);
    }

    public void auswahlFormular(){
        String ar[] = {"Holzablagerungen im Hochwasserabflussbereich",
                "Ablagerung sonst. abflusshemmender Gegenstände",
                "Holzbewuchs im Hochwasserabflussbereich",
                "Schäden an Regulierungsbauten",
                "Abflussbehindernde Einbauten",
                "Wasseraus- und -einleitungen",
                "Ereignis ohne unmittelbare Abflussbehinderung"};


        ArrayAdapter<String> dataAdapter3 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ar) {
        };
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner4.setAdapter(dataAdapter3);
    }



    public void defMaßnahmen(View v) {


        if (absturzsicherung.isChecked()) {
            absturzsicherungint = 1;
        } else {
            absturzsicherungint = 0;
        }
        if (baumestr.isChecked()) {
            baumestrint = 1;
        } else {
            baumestrint = 0;
        }
        if (bauwerkwart.isChecked()) {
            bauwerksanint = 1;
        } else {
            bauwerksanint = 0;
        }
        if (bauwerkwart.isChecked()) {
            bauwerkwartint = 1;
        } else {
            bauwerkwartint = 0;
        }
        if (durchlassfrei.isChecked()) {
            durchlassfreiint = 1;
        } else {
            durchlassfreiint = 0;
        }
        if (genehmigung.isChecked()) {
            genehmigungint = 1;
        } else {
            genehmigungint = 0;
        }
        if (hindernisseentf.isChecked()) {
            hindernisseentfint = 1;
        } else {
            hindernisseentfint = 0;
        }
        if (hindernissespreng.isChecked()) {
            hindernissesprengint = 1;
        } else {
            hindernissesprengint = 0;
        }
        if (holzablang.isChecked()) {
            holzablangint = 1;
        } else {
            holzablangint = 0;
        }
        if (keinemaßnahm.isChecked()) {
            keinemaßnahmint = 1;
        } else {
            keinemaßnahmint = 0;
        }
        if (sperreodgerinne.isChecked()) {
            sperreodgerinneint = 1;
        } else {
            sperreodgerinneint = 0;
        }
        if (ufersichern.isChecked()) {
            ufersichernint = 1;
        } else {
            ufersichernint = 0;
        }
        if (zustandbeob.isChecked()) {
            zustandbeobint = 1;
        } else {
            zustandbeobint = 0;
        }


    }


    public void onclick(View v) {
        if (!kosten.getText().toString().equals("")) {
            kostenint = Integer.parseInt(kosten.getText().toString());
        }

        gemeindestr = gemeinde.getText().toString();
        maßnhamenstr = maßnahmen.getText().toString();

        prioritat = mySpinner1.getSelectedItem().toString();

        if (mySpinner2.getSelectedItem().equals("Ja")) {
            foederfahig = 1;
        } else {
            foederfahig = 0;
        }

        abwicklung = mySpinner3.getSelectedItem().toString();


        forstDB.addFormular(gemeindestr, date, kostenint, maßnhamenstr, prioritat, foederfahig, abwicklung, absturzsicherungint, baumestrint, bauwerksanint, bauwerkwartint, durchlassfreiint, genehmigungint, hindernisseentfint, hindernissesprengint, holzablangint, keinemaßnahmint, sperreodgerinneint, ufersichernint, zustandbeobint);

        if (gemeinde.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Gemeinde an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            gemeinde.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();

        } else if (kosten.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie die Kostenschätzung an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            kosten.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();
        } else if (maßnahmen.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte geben Sie eine Maßnahme an")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            maßnahmen.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }).show();
        } else if (mySpinner1.getSelectedItem().toString().equals("Priorität")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Priorität aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (mySpinner2.getSelectedItem().toString().equals("Förderfähig")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie die Förderfähigkeit aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (mySpinner3.getSelectedItem().toString().equals("Abwicklung")) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie eine Abwicklung aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (!absturzsicherung.isChecked() && !baumestr.isChecked() && !bauwerksan.isChecked() && !bauwerkwart.isChecked() && !durchlassfrei.isChecked() && !genehmigung.isChecked() && !hindernisseentf.isChecked() && !hindernissespreng.isChecked() && !holzablang.isChecked() && !keinemaßnahm.isChecked() && !sperreodgerinne.isChecked() && !ufersichern.isChecked() && !zustandbeob.isChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("!!Achtung!!")
                    .setMessage("Bitte wählen Sie mindestens eine Maßnahme aus")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else {


            String beobachtung = mySpinner4.getSelectedItem().toString();

            switch (beobachtung) {
                case "Holzablagerungen im Hochwasserabflussbereich":
                    Intent intent = new Intent(FormActivity.this, Holzablagerung.class);
                    intent.putExtra("Headline", beobachtung);
                    startActivity(intent);
                    break;
                case "Ablagerung sonst. abflusshemmender Gegenstände":
                    Intent intent1 = new Intent(FormActivity.this, Ablagerung.class);
                    intent1.putExtra("Headline", beobachtung);
                    startActivity(intent1);


                    break;
                case "Holzbewuchs im Hochwasserabflussbereich":
                    Intent intent2 = new Intent(FormActivity.this, Holzbewuchs.class);
                    intent2.putExtra("Headline", beobachtung);
                    startActivity(intent2);

                    break;
                case "Schäden an Regulierungsbauten":
                    Intent intent3 = new Intent(FormActivity.this, Schaeden_Regulierungsbauten.class);
                    intent3.putExtra("Headline", beobachtung);
                    startActivity(intent3);
                    break;
                case "Abflussbehindernde Einbauten":
                    Intent intent4 = new Intent(FormActivity.this, Abflussbehinderndeeinbauten.class);
                    intent4.putExtra("Headline", beobachtung);
                    startActivity(intent4);


                    break;
                case "Wasseraus- und -einleitungen":
                    Intent intent5 = new Intent(FormActivity.this, Wasserauseinleitung.class);
                    intent5.putExtra("Headline", beobachtung);
                    startActivity(intent5);

                    break;
                case "Ereignis ohne unmittelbare Abflussbehinderung":

                    Intent intent6 = new Intent(FormActivity.this, Abflussbehinderung.class);
                    intent6.putExtra("Headline", beobachtung);
                    startActivity(intent6);

                    break;
            }
        }

    }

}