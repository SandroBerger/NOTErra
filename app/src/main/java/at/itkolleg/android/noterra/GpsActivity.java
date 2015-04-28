package at.itkolleg.android.noterra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
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
        createMap();
    }

    public void createMap() {
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //map.addCircle(circleOptions);

        getLaengeBreite();

        AKTUELLER_STANDORT = new LatLng(breitengrad, laengengrad);

        if (map != null) {
            Marker aktuellerStandort = map.addMarker(new MarkerOptions().position(AKTUELLER_STANDORT)
                    .title("Aktueller Standort"));
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(AKTUELLER_STANDORT, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }

    public void getLaengeBreite() {
        if (gps.canGetLocation()) {
            breitengrad = gps.getBreitengrad();
            laengengrad = gps.getLaengengrad();
        } else {
            gps.showSettingsAlert();
        }
    }

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
