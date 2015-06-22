package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends ActionBarActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

        }


        Spinner mySpinner = (Spinner) findViewById(R.id.Spinner02);

        List<String> anzahl = new ArrayList<String>();
        anzahl.add("Niedrig(in 6-7 Jahren");
        anzahl.add("Mittel(in 3-5 Jahren)");
        anzahl.add("Hoch (in 1-2 Jahren");
        anzahl.add("Priorität");

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

        Spinner mySpinner1 = (Spinner) findViewById(R.id.Spinner03);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize1);




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

        Spinner mySpinner2 = (Spinner) findViewById(R.id.Spinner04);

        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(dataAdapter2);
        mySpinner2.setSelection(longsize2);


        String ar[]={"Holzablagerungen im Hochwasserabflussbereich",
                "Ablagerung sonst. abflusshemmender Gegenstände",
                "Holzbewuchs im Hochwasserabflussbereich",
                "Schäden an Regulierungsbauten",
                "Abflussbehindernde Einbauten",
                "Wasseraus- und -einleitungen",
                "Ereignis ohne unmittelbare Abflussbehinderung" };


        Spinner mySpinner3=(Spinner)findViewById(R.id.Spinner01);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ar) {};
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner3.setAdapter(dataAdapter3);



        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));




    }

    public void onclick(View v)
    {

        Spinner mySpinner=(Spinner)findViewById(R.id.Spinner01);
        String beobachtung=mySpinner.getSelectedItem().toString();




        switch (beobachtung) {
            case "Holzablagerungen im Hochwasserabflussbereich":
                Intent intent=new Intent(FormActivity.this,Holzablagerung.class);
                intent.putExtra("Headline",beobachtung);
                startActivity(intent);
                break;
            case "Ablagerung sonst. abflusshemmender Gegenstände":
                Intent intent1=new Intent(FormActivity.this,Ablagerung.class);
                intent1.putExtra("Headline",beobachtung);
                startActivity(intent1);


                break;
            case "Holzbewuchs im Hochwasserabflussbereich":
                Intent intent2=new Intent(FormActivity.this,Holzbewuchs.class);
                intent2.putExtra("Headline",beobachtung);
                startActivity(intent2);

                break;
            case "Schäden an Regulierungsbauten":
                Intent intent3=new Intent(FormActivity.this,Schaeden_Regulierungsbauten.class);
                intent3.putExtra("Headline",beobachtung);
                startActivity(intent3);
                break;
            case "Abflussbehindernde Einbauten":
                Intent intent4=new Intent(FormActivity.this,Abflussbehinderndeeinbauten.class);
                intent4.putExtra("Headline",beobachtung);
                startActivity(intent4);



                break;
            case "Wasseraus- und -einleitungen":
                Intent intent5=new Intent(FormActivity.this,Wasserauseinleitung.class);
                intent5.putExtra("Headline",beobachtung);
                startActivity(intent5);

                break;
            case "Ereignis ohne unmittelbare Abflussbehinderung":

                Intent intent6=new Intent(FormActivity.this,Abflussbehinderung.class);
                intent6.putExtra("Headline",beobachtung);
                startActivity(intent6);


                break;
            default:
                break;

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_form, container, false);
            return rootView;
        }
    }
}