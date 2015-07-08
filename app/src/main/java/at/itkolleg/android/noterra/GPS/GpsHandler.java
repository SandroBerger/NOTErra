package at.itkolleg.android.noterra.GPS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;

public class GpsHandler extends FragmentActivity implements LocationListener {

    private Context context = null;

    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;


    Location location;

    double laengengrad;
    double breitengrad;

    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10;

    protected LocationManager locationManager;

    public GpsHandler() {
    }

    public GpsHandler(Context context) {
        this.context = context;
        getLocation();
    }

    /**
     * Mithilfe dieser Methode wird der Standort wermittelt.
     * zuerst wird �berpr�ft ob es entweder �ber GPS oder das Ntzwerk m�glich ist
     * und anschlie�end die jeweilige verfgbare Ortungsmethode ausgef�hrt
     *
     * */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {
                this.canGetLocation = false;
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            breitengrad = location.getLatitude();
                            laengengrad = location.getLongitude();
                        }
                    }
                }

                if (isGpsEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                breitengrad = location.getLatitude();
                                laengengrad = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Diese Methode lest den Aktuellen Breitengrad aus.
     *
     * @return aktueller Breitengrad
     * */
    public double getBreitengrad() {
        if (location != null) {
            breitengrad = location.getLatitude();
        }
        return breitengrad;
    }

    /**
     * Diese Methode lest den Aktuellen L�ngengrad aus.
     *
     * @return aktueller L�ngengrad
     * */
    public double getLaengengrad() {
        if (location != null) {
            laengengrad = location.getLongitude();
        }
        return laengengrad;
    }

    /**
     * Gibt an ob eine Ortung m�glich ist oder nicht
     *
     * @return true oder false
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Diese Methode zeigt die Fehlermeldung am Smartphone an
     * wenn eine Ortung nicht m�glich ist, da der Ortungsdienst
     * ausgeschaltet ist und gibt die m�glichkeit direkt in die
     * daf�r vorgesehenen Einstellungen zu navigieren
     *
     **/
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS - Fehler");
        alertDialog.setMessage("GPS ist nicht aktiviert. Wollen Sie es einschalten?");
        alertDialog.setPositiveButton("Einstellungen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, InspectionActivity.class);
                context.startActivity(intent);
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //TODO Autogenerierte methode
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //TODO Autogenerierte methode
    }

    @Override
    public void onProviderEnabled(String provider) {
        //TODO Autogenerierte methode
    }

    @Override
    public void onProviderDisabled(String provider) {
        //TODO Autogenerierte methode
    }
}
