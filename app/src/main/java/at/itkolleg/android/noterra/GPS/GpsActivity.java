package at.itkolleg.android.noterra.GPS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;
import at.itkolleg.android.noterra.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GpsActivity extends FragmentActivity {

    static LatLng AKTUELLER_STANDORT = null;
    private GoogleMap map;
    private GpsHandler gps;
    private DBHandler forstDB;

    private double laengengrad;
    private double breitengrad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        gps = new GpsHandler(this);
        forstDB = new DBHandler(this);
        createMap();
    }


    /**
     * Mit dieser Methode wird die Kartenansicht erstellt und
     * am Smartphone im Vollbildmodus angezeigt.
     * */
    public void createMap() {
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        getLaengeBreite();
        forstDB.addCoordinates(""+laengengrad, ""+breitengrad);

        AKTUELLER_STANDORT = new LatLng(breitengrad, laengengrad);

        if (map != null) {
            Marker aktuellerStandort = map.addMarker(new MarkerOptions().position(AKTUELLER_STANDORT)
                    .title("Aktueller Standort"));
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(AKTUELLER_STANDORT, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }

    /**
     * Setz die Variablen breiten- und laengengrad mit den aktuellen
     * Positionsdaten
     * */
    public void getLaengeBreite() {
        if (gps.canGetLocation()) {
            breitengrad = gps.getBreitengrad();
            laengengrad = gps.getLaengengrad();


        } else {
            gps.showSettingsAlert();
        }
    }

    /**
     * Kehrt der Benutzer von den Einstellungen zur�ck
     * wird er anschlie�end direkt in die Haupt�bersicht weitergeleitet
     * und muss anschlie�end erneut in die Positionsermittlung hineingehen.
     * */
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this, InspectionActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gps, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_gps, container, false);
            return rootView;
        }
    }
}
