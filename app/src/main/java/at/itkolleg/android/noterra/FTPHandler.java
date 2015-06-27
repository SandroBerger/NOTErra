package at.itkolleg.android.noterra;

import android.os.AsyncTask;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by SandroB on 23.05.2015.
 */
public class FTPHandler {

    FTPClient ftpClient = new FTPClient();
    private String servername = "ftp.schwarzenauer.hol.es";
    private int port = 21;
    private String username = "u781176503.user";
    private String password = "1234Forst";
    private String workingpath;
    private String filepath;
    private File file;
    private FileInputStream inputFile;
    private String remotePathImage = "/Media/Images/";
    private String remotePathAudio = "/Media/Audio/";
    private String image = "jpg";
    String extention;
    private String timestamp;

    public FTPHandler(String filepath) throws IOException {
        this.filepath = filepath;
        file = new File(filepath);
        extention = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());

        FtpTask ftptask = new FtpTask();
        ftptask.execute();
    }

    public void ftpConnection() throws IOException {
        ftpClient.connect(servername, port);
    }

    public void ftpLogin() throws IOException {
        ftpClient.login(username, password);
    }

    public void workingDirektory(String workingpath) throws IOException {
        this.workingpath = workingpath;
        ftpClient.changeWorkingDirectory(workingpath);
    }

    public void setFileInputStream() throws FileNotFoundException {
        inputFile = new FileInputStream(file);
    }

    public void saveFileOnServer() throws IOException {
        boolean checkFileUpload;

        if(extention.equals(image)){
            checkFileUpload = ftpClient.storeFile(remotePathImage + file.getName(), inputFile);
        }else {
            checkFileUpload = ftpClient.storeFile(remotePathAudio + file.getName(), inputFile);
        }

        if(checkFileUpload == true){
            file.delete();
        }
    }

    public void closeConnection() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    private String getCurrentTime() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String time = day + "." + month + "." + year + "_" + hour + ":" + minute + ":" + second;

        return time;
    }

    public class FtpTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                ftpConnection();

                if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                    ftpClient.enterLocalPassiveMode();
                    ftpLogin();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    if (extention.equals(image)) {
                        workingDirektory(remotePathImage);
                    } else {
                        workingDirektory(remotePathAudio);
                    }
                    setFileInputStream();
                    saveFileOnServer();
                    closeConnection();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
