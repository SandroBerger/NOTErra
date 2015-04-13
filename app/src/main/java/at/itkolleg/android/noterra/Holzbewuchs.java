package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class Holzbewuchs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holzbewuchs);
        Spinner mySpinner = (Spinner) findViewById(R.id.Spinner01);

        List<String> anzahl = new ArrayList<String>();
        anzahl.add("<10");
        anzahl.add(">100");
        anzahl.add("11-50");
        anzahl.add("50-100");
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

        Spinner mySpinner1 = (Spinner) findViewById(R.id.Spinner02);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(dataAdapter1);
        mySpinner1.setSelection(longsize);


    }

    public void save(View v){
        String extra = getIntent().getStringExtra("Headline");
        Intent intent = new Intent(Holzbewuchs.this, InspectionActivity.class);
        intent.putExtra("Headline", extra);
        startActivity(intent);
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
