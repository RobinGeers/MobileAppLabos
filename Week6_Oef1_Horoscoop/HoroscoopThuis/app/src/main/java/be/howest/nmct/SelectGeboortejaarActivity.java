package be.howest.nmct;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectGeboortejaarActivity extends ListActivity {

    // Hardcoded Data in List<String>
    private final static List<String> GEBOORTEJAREN;
    static {
        GEBOORTEJAREN = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900); // Maak Arraylist met lengte 115, vul daarna op van 1900 tot nu
        for (int jaar = 1900; jaar < Calendar.getInstance().get(Calendar.YEAR); jaar++) {
            GEBOORTEJAREN.add("" + jaar);
        }
    }

    private ListAdapter myListAdapter;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Geen setContentView -> Geen layout-file nodig omdat we enkel list nodig hebben

        // ArrayAdapter: Steek Data in ListView
        // (this -> Parent object, Textview dat wordt aangemaakt, Data)
        myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, GEBOORTEJAREN);
        setListAdapter(myListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Geselecteerd geboortejaar
        String sGeboortejaar = GEBOORTEJAREN.get(position);

        // Stuur geselecteerd geboortejaar nar MainActivity
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_BIRTHDAY, sGeboortejaar);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_geboortejaar, menu);
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
