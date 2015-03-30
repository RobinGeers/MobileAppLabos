package be.howest.nmct;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class HoroscoopActivity extends ListActivity {

    private ImageView imageViewHoroscoop;
    private TextView textViewNaamHoroscoop;
    private Button btnInfo;

    // Custom adapter -> We hebben images, buttons nodig in onze List
    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop> {
        // Default constructor moet aanwezig zijn
        public HoroscoopAdapter() {
            // Steek Data in Layout (ListView)
            super(HoroscoopActivity.this, R.layout.row_horoscoop, R.id.textViewNaamHoroscoop, Data.Horoscoop.values()); // Refereert naar de parent constructor
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row =  super.getView(position, convertView, parent);

            // Overloop elk horoscoop object (Moet final zijn zodat je deze kunt hergebruiken hieronder in btnInfo -> Wordt op de Heap geplaatst)
            final Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];

            /* MET Caching van Control-Views
            ViewHolder holder = (ViewHolder) row.getTag();

            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            // Koppel attributen aan Control-View van Row!!!!
            imageViewHoroscoop = holder.imageViewHoroscoop;
            textViewNaamHoroscoop = holder.textViewNaamHoroscoop;
            btnInfo = holder.btnInfo;*/


            /* ZONDER Caching van Control-Views */
            // Koppel attributen aan Control-View van Row!!!!
            imageViewHoroscoop = (ImageView) row.findViewById(R.id.imageViewHoroscoop);
            textViewNaamHoroscoop = (TextView) row.findViewById(R.id.textViewNaamHoroscoop);
            btnInfo = (Button) row.findViewById(R.id.buttonInfo);

            // Vul Control-Views op met properties van object Horoscoop
            imageViewHoroscoop.setImageResource(getResourceId(horoscoop));
            textViewNaamHoroscoop.setText(horoscoop.getNaamHoroscoop());
            btnInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    toonInfo(horoscoop);
                }
            });

            return row;
        }

        private void toonInfo(Data.Horoscoop horoscoop) {
            Toast.makeText(HoroscoopActivity.this, horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum(), Toast.LENGTH_SHORT).show();
        }

        private int getResourceId(Data.Horoscoop horoscoop) {
            switch (horoscoop) {
                case WATERMAN: return R.drawable.waterman;
                case VISSEN: return R.drawable.vissen;
                case RAM: return R.drawable.ram;
                case STIER: return R.drawable.stier;
                case TWEELING: return R.drawable.tweeling;
                case KREEFT: return R.drawable.kreeft;
                case LEEUW: return R.drawable.leeuw;
                case MAAGD: return R.drawable.maagd;
                case WEEGSCHAAL: return R.drawable.weegschaal;
                case SCHORPIOEN: return R.drawable.schorpioen;
                case BOOGSCHUTTER: return R.drawable.boogschutter;
                case STEENBOK: return R.drawable.steenbok;
                default: return R.drawable.waterman;
            }
        }
    }

    // Cachen van Control-View elementen zodat de lijst vlotter scrollt (bij duizenden items)
    class ViewHolder {
        public ImageView imageViewHoroscoop = null;
        public TextView textViewNaamHoroscoop = null;
        public Button btnInfo = null;

        public ViewHolder(View row) {
            this.imageViewHoroscoop = (ImageView) row.findViewById(R.id.imageViewHoroscoop);
            this.textViewNaamHoroscoop = (TextView) row.findViewById(R.id.textViewNaamHoroscoop);
            this.btnInfo = (Button) row.findViewById(R.id.buttonInfo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Geen setContentView -> Geen layout-file nodig omdat we enkel list nodig hebben

        // Koppelt de Adapter aan de Activity
        setListAdapter(new HoroscoopAdapter());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // Stuur de positie door waarop er geklikt is
        Intent intent = new Intent();
        intent.putExtra(String.valueOf(MainActivity.EXTRA_HOROSCOPE), position);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_horoscoop, menu);
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
