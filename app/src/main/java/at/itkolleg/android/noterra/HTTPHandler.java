package at.itkolleg.android.noterra;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse sendet die Datenbank inhalte an das PHP-File am Server,
 * der anschließend die Daten in die MySQL-Datenbank einträgt.
 * @author Berger Sandro
 * @version 30.06.2015
 * */
public class HTTPHandler {
    private HttpClient httpclient;
    private HttpPost httpPost;
    private DBHandler forstDB;

    public HTTPHandler(Context context) throws IOException {
        forstDB = new DBHandler(context);
        httpclient = new DefaultHttpClient();
        httpPost = new HttpPost("http://schwarzenauer.hol.es/NOTErra/handler.php");

        HTTPTask httpTask = new HTTPTask();
        httpTask.execute();
    }

    /**
     * Es werden alle Daten der Tabelle aus der Datenbank ausgelesen und anschließend
     * in eine Liste gespeichert.
     * Danach wird die Liste durchloffen und jeder Wert einzeln an das PHP-File übergeben
     * Wurde die Tabelle erfolgreich hinaufgeladen, und der Responsecode 200 - OK zurückgegeben,
     * wird alles aus der Tabelle gelöscht.
     * @param tabelname Angabe des Tabellenname der versendet werden soll.
     * */
    public void sendTableToServer(String tabelname) {
        Cursor cursor = forstDB.getAllFromTable(tabelname);
        int responseCode = 0;

                cursor.moveToPosition(-1);

                while (cursor.moveToNext()) {
                    List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                    nameValuePairs.add(new BasicNameValuePair("TabellenName", tabelname));

                    try {
                        for (int i = 0; i <= cursor.getColumnCount() - 1; i++) {
                            if (!(cursor.getCount() == 0 || cursor.getString(i) == null || cursor.getString(i).equals(null))) {
                                nameValuePairs.add(new BasicNameValuePair(cursor.getColumnName(i), cursor.getString(i)));
                            }
                        }

                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                        HttpResponse response = httpclient.execute(httpPost);
                        responseCode = response.getStatusLine().getStatusCode();

                        if (responseCode == 200) {
                            String column = nameValuePairs.get(1).toString().substring(0, nameValuePairs.get(1).toString().length() - 33);
                            String uuid = nameValuePairs.get(1).toString().substring(nameValuePairs.get(1).toString().length() - 32,
                                    nameValuePairs.get(1).toString().length());

                            forstDB.deleteWhereIDIs(tabelname, column, new String[]{uuid});
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
            }
        }
    }


    /**
     * Verwendet die Methoden des HTTPHandler und sendet so
     * die Daten im Hintergrund der Anwendung.
     * @author Berger Sandro
     * @version 30.06.2015
     * */
    public class HTTPTask extends AsyncTask<Void, Void, Void> {
        /**
         * Sendet jede Tabelle einzelen an das PHP-File zur
         * weiterverarbeitung und für das eintragen in die
         * MySQL-Datenbank
         * */
        @Override
        protected Void doInBackground(Void... params) {
            sendTableToServer("tbl_Beobachtung");
            sendTableToServer("tbl_Formular");
            sendTableToServer("tbl_Holzablagerung");
            sendTableToServer("tbl_Holzbewuchs");
            sendTableToServer("tbl_OhneBehinderung");
            sendTableToServer("tbl_SchadenAnRegulierung");
            sendTableToServer("tbl_WasserAusEinleitung");
            sendTableToServer("tbl_Abflussbehinderung");
            sendTableToServer("tbl_Ablagerung");
            sendTableToServer("tbl_Notiz");
            sendTableToServer("tbl_Foto");
            sendTableToServer("tbl_Sprachaufnahme");
            sendTableToServer("tbl_Text");
            sendTableToServer("tbl_Gps");

            return null;
        }
    }
}
