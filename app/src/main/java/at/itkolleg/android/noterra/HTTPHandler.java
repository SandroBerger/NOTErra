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

    public int sendTableToServer(String tabelname) {
        Cursor c = forstDB.getAllFromTable(tabelname);
        int responseCode = 0;
        c.moveToLast();

        List<NameValuePair> nameValuePairs = new ArrayList<>(2);
        nameValuePairs.add(new BasicNameValuePair("TabellenName", tabelname));

        try {
            for (int i = 0; i <= c.getColumnCount()-1; i++) {
                nameValuePairs.add(new BasicNameValuePair(c.getColumnName(i), c.getString(i)));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httpPost);
            responseCode = response.getStatusLine().getStatusCode();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    public class HTTPTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int responseCode;

            responseCode = sendTableToServer("tbl_Beobachtung");
            responseCode += sendTableToServer("tbl_Formular");
            responseCode += sendTableToServer("tbl_Holzablagerung");
            responseCode += sendTableToServer("tbl_Holzbewuchs");
            responseCode += sendTableToServer("tbl_OhneBehinderung");
            responseCode += sendTableToServer("tbl_SchadenAnRegulierung");
            responseCode += sendTableToServer("tbl_WasserAusEinleitung");
            responseCode += sendTableToServer("tbl_Abflussbehinderung");
            responseCode += sendTableToServer("tbl_Ablagerung");
            responseCode += sendTableToServer("tbl_Notiz");
            responseCode += sendTableToServer("tbl_Foto");
            responseCode += sendTableToServer("tbl_Sprachaufnahme");
            responseCode += sendTableToServer("tbl_Text");
            responseCode += sendTableToServer("tbl_Gps");

            if(responseCode == 2800){
                forstDB.deleteAllFromTable();
                forstDB.closeDB();
            }

            return null;
        }
    }
}
