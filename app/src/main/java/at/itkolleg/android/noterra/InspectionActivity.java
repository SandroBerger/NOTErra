package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

public class InspectionActivity extends ActionBarActivity implements View.OnClickListener {
    private DBHandler forstDB;
    private String date;
    private TextView uberschrifttextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));

        addButton();
        forstDB = new DBHandler(this);
        uberschrift();
    }

    public void uberschrift(){
        if (forstDB.tableexist("tbl_Abflussbehinderung") != 0) {
           uberschrifttextview.setText("Abflussbehindernde Einbauten");
        }
        if (forstDB.tableexist("tbl_OhneBehinderung") != 0) {
            uberschrifttextview.setText("Ereignis ohne unmittelbare Abflussbehinderung");
        }
        if (forstDB.tableexist("tbl_Ablagerung") != 0) {
            uberschrifttextview.setText("Ablagerung sonstiger abflusshemender Gegenstände");

        }
        if (forstDB.tableexist("tbl_Holzablagerung") != 0) {
           uberschrifttextview.setText("Holzablagerungen im Hochwasserabflussbereich");
        }
        if (forstDB.tableexist("tbl_Holzbewuchs") != 0) {
            uberschrifttextview.setText("Holzbewuchs im Hochwasserabflussbereich");
        }
        if (forstDB.tableexist("tbl_SchadenAnRegulierung") != 0) {
           uberschrifttextview.setText("Schäden an Regulierungsbauten");
        }
        if (forstDB.tableexist("tbl_WasserAusEinleitung") != 0) {
            uberschrifttextview.setText("Wasseraus-und -einleitungen");
        }
    }

    public void addButton(){
        Button formButton = (Button) findViewById(R.id.formbutton);
        Button noteButton = (Button) findViewById(R.id.notebutton);
        Button gpsButton = (Button) findViewById(R.id.gpsbutton);
        Button summaryButton = (Button) findViewById(R.id.finishbutton);

        formButton.setOnClickListener(this);
        noteButton.setOnClickListener(this);
        gpsButton.setOnClickListener(this);
        summaryButton.setOnClickListener(this);
        uberschrifttextview=(TextView)findViewById(R.id.hbeobachtung);

    }

    //-------------Hinzufügen der Buttonclick funktion und weiterleitung auf die einzelnen Funktionen bzw. Module wie Formular, Notiz oder GPS--
    //Führt je nach dem welche id mit dem click auf den Button mitgegeben wurde aus.
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.formbutton:
                buttonFormClick();
                break;

            case R.id.notebutton:
                buttonNoteClick();
                break;

            case R.id.gpsbutton:
                buttonGpsClick();
                break;

            case R.id.finishbutton:
                buttonSummaryClick();
                break;
        }
    }

    //Ermöglicht die weiterleitung zwischen den einzelnen bereichen der Anwendung. Es wird ein view und die jeweilige Klasse mitgegeben auf die weitergeleitet werden soll.
    private void buttonFormClick(){

        Intent intent=new Intent(InspectionActivity.this,FormActivity.class);
        startActivity(intent);

    }
    private void buttonNoteClick(){
        forstDB.addNotiz();
        startActivity(new Intent(this, NotesActivity.class));
    }
    private void buttonGpsClick(){
        startActivity(new Intent(this, GpsActivity.class));
    }
    private void buttonSummaryClick(){
        startActivity(new Intent(this, SummaryActivity.class));
    }
    //-------------------------------------------------------------------------------------------------






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_work, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_inspection, container, false);
            return rootView;
        }
    }
}
