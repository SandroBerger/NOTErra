package at.itkolleg.android.noterra;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class SummaryActivity extends ActionBarActivity implements View.OnClickListener {

    private String outputFile = null;
    private ImageView imageView;
    private String imagePath;
    private String audioPath;
    private DBHandler forstDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));
        forstDB = new DBHandler(this);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadImage();
    }

    public void loadImage() {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finish, menu);
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

    @Override
    public void onClick(View v) {

    }

    public void loadData(){
        File imageDirectory = new File("/storage/emulated/0/NOTErra/Media/Images/");
        File audioDirectory = new File("/storage/emulated/0/NOTErra/Media/Audio/");

        File[] imageFileList = imageDirectory.listFiles();
        File[] audioFileList = audioDirectory.listFiles();

        ArrayList<File> imageFiles = new ArrayList<>();
        ArrayList<File> audioFiles = new ArrayList<>();

        for (File file : imageFileList){
            imageFiles.add(file);
        }
        for (File file : audioFileList){
            audioFiles.add(file);
        }
        if(!(imageFileList.length == 0)){
            imagePath  = imageFiles.get(imageFiles.size()-1).getPath();
        }
        if(!(audioFileList.length == 0)){
            audioPath  = audioFiles.get(audioFiles.size()-1).getPath();
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void back(View v){
        Intent intent = new Intent(SummaryActivity.this, InspectionActivity.class);
        startActivity(intent);
    }

    public void send(View v) throws IOException {
        loadData();
        try {
            if(getImagePath() != null){
                FTPHandler ftp1 = new FTPHandler(getImagePath());
            }
            if(getAudioPath() != null){
                FTPHandler ftp2 = new FTPHandler(getAudioPath());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        HTTPHandler httpHandler = new HTTPHandler(this);


        Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
        startActivity(intent);


        Context context = getApplicationContext();
        CharSequence text = "Erfolgreich gespeichert";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
            View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
            return rootView;
        }
    }
}
