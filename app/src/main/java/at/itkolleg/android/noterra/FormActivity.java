package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class FormActivity extends ActionBarActivity  {


    NumberPicker Stringpicker = null;
    Button speichern = null;

    TextView textView=null;
    TextView textView1=null;

    TextView h1stammzahl=null;
    EditText stammzahl=null;

    TextView h1baumart=null;
    EditText baumart=null;

    TextView h1media=null;
    EditText mediabhd=null;

    TextView h1laenge=null;
    EditText laenge=null;

    TextView h1beschr=null;
    EditText beschr=null;

    TextView h1empf=null;
    EditText empf=null;

    Button speichern2=null;

    TextView h1kosten=null;
    EditText kosten=null;

    TextView euro=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


        textView= (TextView) findViewById(R.id.textView);
        textView1=(TextView) findViewById(R.id.textView2);
        speichern=(Button)findViewById(R.id.button);


        h1stammzahl = (TextView)findViewById(R.id.anzahlstamm);
        stammzahl = (EditText)findViewById(R.id.zahlstamm);

        h1baumart=(TextView)findViewById(R.id.h1baumart);
        baumart =(EditText)findViewById(R.id.editbaumart);

        h1media=(TextView)findViewById(R.id.h1mediabhd);
        mediabhd=(EditText)findViewById(R.id.mediabhd);

        h1laenge=(TextView)findViewById(R.id.h1bachabschnitt);
        laenge=(EditText)findViewById(R.id.bachabschnitt);

        h1beschr=(TextView)findViewById(R.id.h1beschreibung);
        beschr=(EditText)findViewById(R.id.beschreibung);

        h1empf=(TextView)findViewById(R.id.h1empfehl);
        empf=(EditText)findViewById(R.id.empfehlungen);

        h1kosten=(TextView)findViewById(R.id.h1kosten);
        kosten=(EditText)findViewById(R.id.kosten);

        speichern2=(Button)findViewById(R.id.speichbut);

         euro=(TextView)findViewById(R.id.euro);



        Stringpicker = (NumberPicker)findViewById(R.id.stringpicker);
        Stringpicker.setWrapSelectorWheel(false);

        String[] values =new String[7];
        values[0]="Holzablagerungen im Hochwasserabflussbereich";
        values[1]="Ablagerung sonst. abflusshemmender Gegenstände";
        values[2]="Holzbewuchs im Hochwasserabflussbereich";
        values[3]="Schäden an Regulierungsbauten";
        values[4]="Abflussbehindernde Einbauten";
        values[5]="Wasseraus- und -einleitungen";
        values[6]="Ereignis ohne unmittelbare Abflussbehinderung";




        Stringpicker.setVisibility(View.GONE);
        Stringpicker.setMinValue(0);
        Stringpicker.setMaxValue(6);
        Stringpicker.setDisplayedValues(values);

        speichern.setVisibility(View.GONE);



        h1stammzahl.setVisibility(View.GONE);
        stammzahl.setVisibility(View.GONE);

        h1baumart.setVisibility(View.GONE);
        baumart.setVisibility(View.GONE);

        h1media.setVisibility(View.GONE);
        mediabhd.setVisibility(View.GONE);

        h1laenge.setVisibility(View.GONE);
        laenge.setVisibility(View.GONE);

        h1beschr.setVisibility(View.GONE);
        beschr.setVisibility(View.GONE);

        h1empf.setVisibility(View.GONE);
        empf.setVisibility(View.GONE);

        h1kosten.setVisibility(View.GONE);
        kosten.setVisibility(View.GONE);

        speichern2.setVisibility(View.GONE);

        euro.setVisibility(View.GONE);

        Stringpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {





                switch (newVal){
                    case 0:
                        setTitle("Holzablagerungen im Hochwasserabflussbereich");
                       textView1.setText("Holzablagerungen im Hochwasserabflussbereich");
                        break;
                    case 1:
                        setTitle("Ablagerung sonst. abflusshemmender Gegenstände");
                        textView1.setText("Ablagerung sonst. abflusshemmender Gegenstände");
                        break;
                    case 2:
                        setTitle("Holzbewuchs im Hochwasserabflussbereich");
                        textView1.setText("Holzbewuchs im Hochwasserabflussbereich");
                        break;
                    case 3:
                        setTitle("Schäden an Regulierungsbauten");
                        textView1.setText("Schäden an Regulierungsbauten");
                        break;
                    case 4:
                        setTitle("Abflussbehindernde Einbauten");
                        textView1.setText("Abflussbehindernde Einbauten");
                        break;
                    case 5:
                        setTitle("Wasseraus- und -einleitungen");
                        textView1.setText("Wasseraus- und -einleitungen");
                        break;
                    case 6:
                        setTitle("Ereignis ohne unmittelbare Abflussbehinderung");
                        textView1.setText("Ereignis ohne unmittelbare Abflussbehinderung");
                        break;
                    default:
                        break;

                }
            }
        });
    }




    public void onClick(View v){

        textView.setVisibility(View.GONE);
        Stringpicker.setVisibility(View.VISIBLE);
        speichern.setVisibility(View.VISIBLE);
    }

    public void btnclick(View v) {


        Stringpicker.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        speichern.setVisibility(View.GONE);

        switch (getTitle().toString()) {
            case "Holzablagerungen im Hochwasserabflussbereich":

                h1stammzahl.setVisibility(View.VISIBLE);
                stammzahl.setVisibility(View.VISIBLE);
                h1baumart.setVisibility(View.VISIBLE);
                baumart.setVisibility(View.VISIBLE);
                h1media.setVisibility(View.VISIBLE);
                mediabhd.setVisibility(View.VISIBLE);
                h1laenge.setVisibility(View.VISIBLE);
                laenge.setVisibility(View.VISIBLE);
                h1beschr.setVisibility(View.VISIBLE);
                beschr.setVisibility(View.VISIBLE);
                h1empf.setVisibility(View.VISIBLE);
                empf.setVisibility(View.VISIBLE);
                h1kosten.setVisibility(View.VISIBLE);
                kosten.setVisibility(View.VISIBLE);
                speichern2.setVisibility(View.VISIBLE);
                euro.setVisibility(View.VISIBLE);

                break;
            case "Ablagerung sonst. abflusshemmender Gegenstände":


                break;
            case "Holzbewuchs im Hochwasserabflussbereich":


                break;
            case "Schäden an Regulierungsbauten":


                break;
            case "Abflussbehindernde Einbauten":


                break;
            case "Wasseraus- und -einleitungen":

                break;
            case "Ereignis ohne unmittelbare Abflussbehinderung":


                break;
            default:
                break;

        }
    }


    public void onclick1(View v){


        Intent intent=new Intent(FormActivity.this,InspectionActivity.class);
        intent.putExtra("extra", getTitle());
        startActivity(intent);
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