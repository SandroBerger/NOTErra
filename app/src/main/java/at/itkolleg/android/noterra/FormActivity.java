package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


        String ar[]={"Holzablagerungen im Hochwasserabflussbereich","Ablagerung sonst. abflusshemmender Gegenstände",
                "Holzbewuchs im Hochwasserabflussbereich",
                "Schäden an Regulierungsbauten",
                "Abflussbehindernde Einbauten",
                "Wasseraus- und -einleitungen",
                "Ereignis ohne unmittelbare Abflussbehinderung" };




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ar) {

        };

        Spinner mySpinner=(Spinner)findViewById(R.id.Spinner01);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(dataAdapter);






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