package at.itkolleg.android.noterra;

import android.os.AsyncTask;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    private String audio = "3gpp";
    private String image = "jpg";

    FTPHandler(String filepath) throws IOException {
        this.filepath = filepath;
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
        file = new File(filepath);
        inputFile = new FileInputStream(file);
    }

    public void saveFileOnServer() throws IOException {
        ftpClient.storeFile(filepath, inputFile);
    }

    public void closeConnection() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public class FtpTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                ftpConnection();

                if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                    ftpClient.enterLocalPassiveMode();
                    ftpLogin();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    workingDirektory("Media/Images");

                    String extention = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
                    if(extention.equals("jpg")){
                        workingDirektory("Media/Images");
                    }else {
                        workingDirektory("Media/Audio");
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
