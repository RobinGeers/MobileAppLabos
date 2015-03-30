package be.howest.nmct.implicitintents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LocationActivity extends Activity {

    private Button btnToonLocatie;
    private EditText editTextLatitude, editTextLongtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btnToonLocatie = (Button) findViewById(R.id.btnToonLocatie);
        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextLongtitude = (EditText) findViewById(R.id.editTextLongtitude);

        btnToonLocatie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toonLocatie();
            }
        });
    }

    private void toonLocatie() {
        float latitude = Float.parseFloat(editTextLatitude.getText().toString());
        float longtitude = Float.parseFloat(editTextLongtitude.getText().toString());

        Uri intentUri = Uri.parse("geo:" + latitude + ", " + longtitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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
