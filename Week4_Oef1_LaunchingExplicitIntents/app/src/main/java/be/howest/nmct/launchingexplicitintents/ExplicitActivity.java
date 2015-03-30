package be.howest.nmct.launchingexplicitintents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ExplicitActivity extends Activity {

    // Variabele EXTRA_INFO staat hier -> Omdat je hem in deze activity moet tonen
    public static final String EXTRA_INFO = "be.howest.nmct.android.launchingexplicitintents.EXTRA_INFO";
    private TextView textviewData;
    private Button btnOk, btnCancel, btnCustomCode;
    public static final int RESULT_CODE_CUSTOM = 3; // Eigen result code die je terugstuurt ipv 'OK' of 'Cancel'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        // Koppel attribuut aan control-view
        textviewData = (TextView) findViewById(R.id.textViewData);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCustomCode = (Button) findViewById(R.id.btnCustomCode);

        // Steek de ontvangen data in textView
        String value = getIntent().getStringExtra(ExplicitActivity.EXTRA_INFO);
        textviewData.setText(value);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Geef CODE (-1) terug aan de parent (vorige activity), sluit activity
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Geef CODE (0) terug aan de parent (vorige activity), sluit activity
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnCustomCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Vul de variabelen op in parent activity die je wilt weergeven
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_INFO_LASTNAME, "Geers");
                intent.putExtra(MainActivity.EXTRA_INFO_AGE, 20);
                // Geef DATA terug aan de parent (vorige activity), sluit activity
                setResult(ExplicitActivity.RESULT_CODE_CUSTOM, intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explicit, menu);
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
