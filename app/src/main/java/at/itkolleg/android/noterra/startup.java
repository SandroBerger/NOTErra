package at.itkolleg.android.noterra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * Diese Klasse dient zur angezige des Ladebildschirms.
 */
public class startup extends Activity {

    /**
     * In diese Methode wird automatisch bei Klassenaufruf gestartet.
     *
     * @param savedInstanceState Wird benutzt um vorherige Zustand bzw. Informatioenn zu laden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);

        new CountDownTimer(1000, 1000) {
            public void onFinish() {
                Intent startActivity = new Intent(startup.this,MainActivity.class);
                startActivity(startActivity);
                finish();
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();

    }
}
