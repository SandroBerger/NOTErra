package at.itkolleg.android.noterra;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
public class HTTPHandler {
    private ResponseHandler<String> responseHandler;
    private HttpClient httpclient;
    private HttpPost httpPost;
    private DBHandler forstDB;

    public HTTPHandler(Context context) throws IOException {
        forstDB = new DBHandler(context);
        httpclient = new DefaultHttpClient();
        responseHandler = new BasicResponseHandler();
        httpPost = new HttpPost("http://schwarzenauer.hol.es/NOTErra/handler.php");

        HTTPTask httpTask = new HTTPTask();
        httpTask.execute();
    }

    public void sendTableToServer(String tabelname) {
        Cursor c = forstDB.getAllFromTable(tabelname);
        c.moveToFirst();

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("TabellenName", tabelname));

        try {
            for (int i = 0; i <= c.getCount()-1; i++) {
                nameValuePairs.add(new BasicNameValuePair(c.getColumnName(i), c.getString(i)));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httpPost);
            int a = response.getStatusLine().getStatusCode();
            if(a == 200) {
                forstDB.deleteAllFromTable(tabelname);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class HTTPTask extends AsyncTask<Void, Void, Void> {
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
