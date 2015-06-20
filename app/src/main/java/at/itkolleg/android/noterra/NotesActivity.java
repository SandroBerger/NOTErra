package at.itkolleg.android.noterra;

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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));
        forstDB = new DBHandler(this);
        loadImage();
        addButton();
    }

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

    private void cameraButtonClick() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        outputFile = "/storage/emulated/0/NOTErra/Media/Images/begehungImage_" + getCurrentTime() + ".jpg";
        setImagePfad(outputFile);

        Uri uriSavedImage = Uri.fromFile(new File(outputFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        startActivityForResult(intent, CAMERA_REQUEST);
    }

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

    private void playButtonClick() {

        try {
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(outputFile);
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

    private void saveButtonClick() {
        forstDB.addImageRef(imagePfad);
        forstDB.addAudioRef(audioPfad);
        forstDB.addNoteText(textNotiz.getText().toString());
        System.out.println(forstDB.getAllFromTable("tbl_Notiz").getString(2));
        startActivity(new Intent(this, InspectionActivity.class));
    }

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

    //---------------------------------------------------------------------
//---------------Ladet das bild in den Imageview wenn eines vorhanden ist in der Ordner struktur------------------
    public void loadImage() {
       /*File imageDir = new File("/storage/emulated/0/NOTErra/Media/Images/");
        final ArrayList<String> imageFiles = new ArrayList<>();

        imageDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                imageFiles.add(file.getAbsolutePath());
                return false;
            }
        });*/

        outputFile = "/storage/emulated/0/NOTErra/Media/Images/begehungImage_" + getCurrentTime() + ".jpg";

        File imgFile = new File(outputFile);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView = (ImageView) this.findViewById(R.id.imageview);
            imageView.setImageBitmap(myBitmap);
        }
    }

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

    //---------------------------------------------------------------------
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadImage();
    }

    private void createRecorder() {
        outputFile = "/storage/emulated/0/NOTErra/Media/Audio/begehungAudio_" + getCurrentTime() + ".3gpp";

        setAudioPfad(outputFile);

        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outputFile);
    }

    public void setAudioPfad(String AudioPfad) {
        this.audioPfad = AudioPfad;
    }

    public String getAudioPfad() {
        return audioPfad;
    }

    public void setImagePfad(String ImagePfad) {
        this.imagePfad = ImagePfad;
    }

    public String getImagePfad() {
        return imagePfad;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
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
