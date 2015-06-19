package at.itkolleg.android.noterra;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SandroB on 19.06.2015.
 */
public class HTTPHandler extends Activity {
    private ResponseHandler<String> responseHandler;
    private HttpClient httpclient;
    private HttpPost httpPost;
    private DBHandler forstDB;

    public HTTPHandler() throws IOException {
        forstDB = new DBHandler(this);
        httpclient = new DefaultHttpClient();
        responseHandler = new BasicResponseHandler();
        httpPost = new HttpPost("http://schwarzenauer.hol.es/NOTErra/handler.php");

        HTTPTask httpTask = new HTTPTask();
        httpTask.execute();
    }

    public void sendSQLiteToServer(String tabelname) {
        Cursor c = forstDB.getAllFromTable(tabelname);
        c.moveToFirst();

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("TabellenName", tabelname));

        try {
            for (int i = 0; i <= c.getCount(); i++) {
                nameValuePairs.add(new BasicNameValuePair(c.getColumnName(i)+i, c.getString(i)));
            }

            String response = httpclient.execute(httpPost, responseHandler);

            if (response.contains("success")) {
                forstDB.deleteAllFromTables();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class HTTPTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            sendSQLiteToServer("tbl_Beobachtung");
            sendSQLiteToServer("tbl_Formular");
            sendSQLiteToServer("tbl_Holzablagerung");
            sendSQLiteToServer("tbl_Holzbewuchs");
            sendSQLiteToServer("tbl_OhneBehinderung");
            sendSQLiteToServer("tbl_SchadenAnRegulierung");
            sendSQLiteToServer("tbl_WasserAusEinleitung");
            sendSQLiteToServer("tbl_Abflussbehinderung");
            sendSQLiteToServer("tbl_Ablagerung");
            sendSQLiteToServer("tbl_Notiz");
            sendSQLiteToServer("tbl_Foto");
            sendSQLiteToServer("tbl_Sprachaufnahme");
            sendSQLiteToServer("tbl_Text");
            sendSQLiteToServer("tbl_Gps");

            return null;
        }
    }
}
