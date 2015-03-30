package be.howest.nmct;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HoroscoopFragment extends ListFragment {

    private onHoroscoopListener horoscoopListener;
    private ImageView imageViewHoroscoop;
    private TextView textViewNaamHoroscoop;
    private Button btnInfo;

    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop> {
        // Default constructor moet aanwezig zijn
        public HoroscoopAdapter() {
            // Steek Data in Layout (ListView)
            super(getActivity(), R.layout.row_horoscoop, R.id.textViewNaamHoroscoop, Data.Horoscoop.values()); // Refereert naar de parent constructor
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = super.getView(position, convertView, parent);

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

        private void toonInfo(Data.Horoscoop horoscoop) {
            Toast.makeText(getActivity(), horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum(), Toast.LENGTH_SHORT).show();
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


    public HoroscoopFragment() {
        // Required empty public constructor
    }

    public static HoroscoopFragment newInstance() {
        HoroscoopFragment fragment = new HoroscoopFragment();

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            horoscoopListener = (onHoroscoopListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChangeFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Koppelt de Adapter aan het Fragment
        setListAdapter(new HoroscoopAdapter());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Stuurt de positie door waarop er geklikt is
        horoscoopListener.onNieuwHoroscoop(position);

    }

    public interface onHoroscoopListener {
        public void onNieuwHoroscoop(int horoscoopSelected);
    }
}
