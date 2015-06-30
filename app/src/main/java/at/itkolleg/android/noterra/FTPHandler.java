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
 * Diese Klasse ist für den Upload der Foto- und Sprachaufnahmedateien zuständig.
 * @author Berger Sandro
 * @version 30.06.2015
 * */
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

    /**
     * Stellt eine Verbindung zum FTP-Server her.
     * */
    public void ftpConnection() throws IOException {
        ftpClient.connect(servername, port);
    }

    /**
     * Ist der die Anwendung erfolgreich mit dem Server verbunden,
     * logt diese sich mithilfe dieser Methode ein.
     * */
    public void ftpLogin() throws IOException {
        ftpClient.login(username, password);
    }

    /**
     * Setzt den Speicherpfad des Server, in dem die Bilder oder Spachaufnahmen
     * gespeichert werden.
     * @param workingpath Ordnerpfad des Servers in dem die Daten abgelegt werden sollen
     * */
    public void workingDirektory(String workingpath) throws IOException {
        this.workingpath = workingpath;
        ftpClient.changeWorkingDirectory(workingpath);
    }

    /**
     * hier wird ein neues FileInputStream objket erstellt.
     * Dieses wird für den Fileupload benötigt.
     * */
    public void setFileInputStream() throws FileNotFoundException {
        inputFile = new FileInputStream(file);
    }

    /**
     * In dieser Methode werden die Daten schlussendlich auf den Server gelden.
     * Zuerst wird überprüft ob es sich um eine Bild oder Audio-Datei handelt und anschließend
     * wird der upload auf den Server gestartet.
     * */
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

    /**
     * Meldet die Anwendung wieder vom Server ab und gibt anschließend
     * die Verbindung wieder frei.
     * */
    public void closeConnection() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    /**
     * /**
     * Diese Klasse führt im Hintergrund der Anwendung den upload der Daten durch.
     * @author Berger Sandro
     * @version 30.06.2015
     * */
    public class FtpTask extends AsyncTask<Void, Void, Void> {
        /**
         * Mithilfe dieser Methode werden die in im FTPHandler definierten
         * Methoden genutzt um alle Daten auf den Server hochzuladen.
         * */
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
