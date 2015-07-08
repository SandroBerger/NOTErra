package at.itkolleg.android.noterra.Notiz;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import at.itkolleg.android.noterra.DatenbankSQLite.DBHandler;
import at.itkolleg.android.noterra.Hauptfenster.InspectionActivity;
import at.itkolleg.android.noterra.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Diese Klasse behandelt das Aufnehmen von Foto-, Sprach-, und Textnotizen.
 * @author Berger Sandro
 * @version 30.06.2015
 * */
public class NotesActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;
    private int recButtonCount = 1;
    private Chronometer myChrono = null;
    private ImageView imageView;
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;
    private ImageButton cameraButton;
    private ImageButton recordButton;
    private ImageButton playButton;
    private ImageButton stopButton;
    private ImageButton deleteAudioButton;
    private ImageButton deleteButton;
    private Button saveButton;
    private String imagePfad;
    private String audioPfad;
    private EditText textNotiz;
    private DBHandler forstDB;

    /**
     * Wird autmatisch beim Aufruf der Klasse ausgeführt
     * In dieser Methode wird ein Datenbank-Objekt Initalisiert
     * und die Button des XML den Button-Variablen hinzugefügt.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));
        forstDB = new DBHandler(this);
        addButton();
    }

    /**
     * Hier wird jeder Button-Variable die Referenz zum XML-file zugewiesen.
     * Anschließend wird ein Click-Listener an den Variablen Aufgerufen,
     * der überprüft ob ein button gedrückt wurde
     * */
    //-------------Button Hinzufügen und funktion hinzufügen zugriff auf kamera, ufnahme von audio und notiz aufnahme----
    public void addButton() {
        cameraButton = (ImageButton) findViewById(R.id.camerabutton);
        recordButton = (ImageButton) findViewById(R.id.recordbutton);
        playButton = (ImageButton) findViewById(R.id.playbutton);
        stopButton = (ImageButton) findViewById(R.id.stopbutton);
        deleteAudioButton = (ImageButton) findViewById(R.id.deleteaudiobutton);
        deleteButton = (ImageButton) findViewById(R.id.deletebutton);
        saveButton = (Button) findViewById(R.id.savebutton);
        myChrono = (Chronometer) findViewById(R.id.chronometer);
        textNotiz = (EditText) findViewById(R.id.notetextfield);


        cameraButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        deleteAudioButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    /**
     * onClick ist eine automatisch generierte Methode.
     * Hier werden nach dem Klick auf einen Button die jeweiligen
     * zugeordneten Methode ausgeführt. Anhand eines Switches wird
     * die prüfung ausgeführt.
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camerabutton:
                cameraButtonClick();
                break;
            case R.id.recordbutton:
                recordButtonClick();
                break;
            case R.id.playbutton:
                playButtonClick();
                break;
            case R.id.stopbutton:
                stopButtonClick();
                break;
            case R.id.deleteaudiobutton:
                deleteAudioButtonClick();
                break;
            case R.id.savebutton:
                saveButtonClick();
                break;
            case R.id.deletebutton:
                imageDeleteClick();
                break;
        }
    }

    /**
     * Wird auf den Camera-Image-Button geklickt, wird diese Methode ausgeführt.
     * Hier wird die Systemkamera gestartet und ein Dateiname angegeben, wie das
     * Bild in der Ordnerstruktur benannt werden soll.
     * */
    private void cameraButtonClick() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        outputFile = "/storage/emulated/0/NOTErra/Media/Images/begehungImage_" + getCurrentTime() + ".jpg";
        setImagePfad(outputFile);

        Uri uriSavedImage = Uri.fromFile(new File(outputFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * Erstellt einen neues Recorder-Objekt mithilfe der Methode createRecorder().
     * und startet anschließend die Sprachaufnahme und ändert während der Aufnahme
     * das Bild auf einen Stopp-Button.
     * Wird ein zweitesmal gedrückt stoppt die Aufnahme und der Button wird wieder
     * zum Aufnahme-Button
     * */
    private void recordButtonClick() {
        if (recButtonCount == 1) {
            try {
                createRecorder();
                myRecorder.prepare();
                myRecorder.start();
                myChrono.start();

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            recButtonCount++;

            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.stoprec));

            Context context = getApplicationContext();
            CharSequence text = "Aufnahme wird gestartet!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else if (recButtonCount == 2) {
            try {
                myRecorder.stop();
                myRecorder.release();
                myChrono.stop();
                myChrono.setBase(SystemClock.elapsedRealtime());

                recordButton.setEnabled(true);
                playButton.setEnabled(true);

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            recButtonCount = 1;
            recordButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.record));

            Context context = getApplicationContext();
            CharSequence text = "Aufnahme wird gestoppt!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Startet die im vorhinein aufgenommene Sprachaufnahme mithilfe
     * des vorher definierten Speicherortes + Dateiname
     * */
    private void playButtonClick() {
        try {
            FileInputStream audioFile = new FileInputStream(getAudioPfad());
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(audioFile.getFD());
            myPlayer.prepare();
            myPlayer.start();

            playButton.setEnabled(false);
            stopButton.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Context context = getApplicationContext();
        CharSequence text = "Aufnahme gestartet";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Hier wird überorüft ob ein MediaPlayer-Objekt erstellt wurde.
     * Anschliepend wird beim Klick die Sprachaufnahme wieder gestoppt.
     * */
    private void stopButtonClick() {
        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;
                playButton.setEnabled(true);
                stopButton.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio wird gestoppt!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ermöglicht dem Anwender das Löschen der vorher aufgenommenen
     * Sprachaufnahme. Hierbei wird zur vermeidung einer versehendlichen
     * Löschung eine Warnung ausgegeben und erfordert anschließend eine Bestätigung
     * */
    private void deleteAudioButtonClick() {
        new AlertDialog.Builder(this)
                .setTitle("Aufnahme löschen")
                .setMessage("Wollen Sie die Aufnahme löschen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String pfad = getAudioPfad();
                        File fdelete = new File(pfad);
                        if (fdelete.exists()) {
                            fdelete.delete();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Speichert die getätigten Notizen in die Datenbank
     * Jedoch wird von einer Sprachaufnahme und von einem Bild
     * nur die Referenz gespeichert. Die Textnotiz wird direkt eingetragen.
     * Anschließend kehrt die Anwendung wieder auf die Übersichtsseite zurück.
     * */
    private void saveButtonClick() {
        forstDB.addImageRef(imagePfad);
        forstDB.addAudioRef(audioPfad);
        forstDB.addNoteText(textNotiz.getText().toString());
        startActivity(new Intent(this, InspectionActivity.class));
    }

    /**
     * Ermöglicht das löschen des gemachten Bildes.
     * */
    private void imageDeleteClick() {
        new AlertDialog.Builder(this)
                .setTitle("Bild löschen")
                .setMessage("Wollen Sie das Bild löschen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String pfad = getImagePfad();
                        // continue with delete
                        File fdelete = new File(pfad);
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                imageView.setImageDrawable(null);
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Lädt das vorhinein gemachte Bild in die ImageView der Anwendung.
     * Kehrt der Anwender zur anwendung ohne gemachtes Bild zurück wird nichts
     * geladen.
     * */
    public void loadImage() {
        outputFile = getImagePfad();

        File imgFile = new File(outputFile);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView = (ImageView) this.findViewById(R.id.imageview);
            imageView.setImageBitmap(myBitmap);
        }
    }

    /**
     * Lest die aktuelle Zeit aus dem Android-System aus.
     * @return der aktuelle Zeitpunkt indem z.B. das Foto getätigt wurde.
     * */
    private String getCurrentTime() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        String time = day + "." + month + "." + year + "_" + hour + ":" + minute;

        return time;
    }

    /**
     * Wird aufgerufen, wenn der Anwender von der Systemkamera wieder zurück
     * in die Anwendung kehrt. Hierbei wird die Methode loadImage() audgeführt,
     * dass das gemachte Bild in die ImageView der Anwendung hineilädt.
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadImage();
    }

    /**
     * Es wird ein neuer MediaRecorder erstellt um eine Sprachaufnahme starten zu können.
     * Desweiteren wird hier auch der Pfad und Dateiname für die Sprachaufnahme festgelegt.
     * Hier wird auch die Quelle, das Dateiformat und das Encoderformat angegeben.
     * */
    private void createRecorder() {
        outputFile = "/storage/emulated/0/NOTErra/Media/Audio/begehungAudio_" + getCurrentTime() + ".3gpp";

        setAudioPfad(outputFile);

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outputFile);
    }

    /**
     * Ermöglicht das setzen des Speicherpfades
     * der gemachten Sprachaufnahme
     * @param AudioPfad Der Datei- und Pfadnamen ist hier anzugeben
     * */
    public void setAudioPfad(String AudioPfad) {
        this.audioPfad = AudioPfad;
    }

    /**
     * Liefert den Pfad der gemachten Sprachaufnahme.
     * @return Speicherpfad der Sprachaufname
     * */
    public String getAudioPfad() {
        return audioPfad;
    }

    /**
     * Ermöglicht das setzen des Speicherpfades
     * des gemachten Bildes
     * @param ImagePfad Der Datei- und Pfadnamen ist hier anzugeben
     * */
    public void setImagePfad(String ImagePfad) {
        this.imagePfad = ImagePfad;
    }

    /**
     * Liefert den Pfad des gemachten Bildes.
     * @return Speicherpfad des Bildes
     * */
    public String getImagePfad() {
        return imagePfad;
    }

    /**
     * Automatisch generierte Methode
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    /**
     * Automatisch generierte Methode
     * */
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
