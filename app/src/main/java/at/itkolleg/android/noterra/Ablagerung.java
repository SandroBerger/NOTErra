package at.itkolleg.android.noterra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Ablagerung extends ActionBarActivity {

    private RadioGroup  ablagerung;
    private RadioButton bauaushub;
    private RadioButton felsbloecke;
    private RadioButton muellablagerung;
    private RadioButton schotter;
    private RadioButton eigenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablagerung);

        ablagerung=(RadioGroup)findViewById(R.id.ablagerung);
        bauaushub =(RadioButton)findViewById(R.id.Bauaushub);
        felsbloecke=(RadioButton)findViewById(R.id.fesblock);
        muellablagerung=(RadioButton)findViewById(R.id.mull);
        schotter=(RadioButton)findViewById(R.id.schotter);
        eigenes=(RadioButton)findViewById(R.id.freiwahl);



    }


    public void onclick(View v){
        int checkedRadiobut= ablagerung.getCheckedRadioButtonId();

        switch(checkedRadiobut){
            case R.id.Bauaushub:
                if(bauaushub.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),bauaushub.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.fesblock:
                if(felsbloecke.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),felsbloecke.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.mull:
                if(muellablagerung.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),muellablagerung.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.schotter:
                if(schotter.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),schotter.getText(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.freiwahl:
                if(eigenes.isChecked()){

                    EditText edit=(EditText)findViewById(R.id.editText);

                    if(edit.getText().toString().equals(""))
                    {
                        new AlertDialog.Builder(this)
                                .setTitle("!!Achtung!!")
                                .setMessage("Es wurde kein Text eingegeben")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(R.drawable.warning_ablagerungsart)


                                .show();
                    }
                    else
                    {
                        Toast toast=Toast.makeText(getApplicationContext(),edit.getText(), Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                break;



        }

    }

    public void save(View v) {
        String extra = getIntent().getStringExtra("Headline");
        Intent intent = new Intent(Ablagerung.this, InspectionActivity.class);
        intent.putExtra("Headline", extra);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ablagerung, menu);
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
