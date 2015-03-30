package be.howest.nmct;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefsFile";
    // Attributen declareren
    private Button btnSelecteerGeboortejaar, btnSelecteerHoroscoop;
    public TextView textViewResultGeboortejaar;
    public ImageView imageViewResultHoroscoop;


    private onChangeFragmentListener changeFragmentListener;
    public static final String EXTRA_BIRTHDAY = "";
    public static final String EXTRA_HOROSCOPE = "";

    public static String gekozenGeboorteJaar = "";
    public static int gekozenHoroscoop = 0;

    public MainFragment() {
    }

    public static MainFragment newInstance(String gekozenGeboortejaar) {
        MainFragment fragment = new MainFragment();

        // Steek het gekozen geboortejaar in Bundle (mandje)
        Bundle args = new Bundle();
        args.putString(EXTRA_BIRTHDAY, gekozenGeboortejaar);
        fragment.setArguments(args);

        return fragment;
    }

    public static MainFragment newInstance(int gekozenHoroscoop) {
        MainFragment fragment = new MainFragment();

        // Steek de gekozen horoscoop in Bundle (mandje)
        Bundle args = new Bundle();
        args.putInt(EXTRA_HOROSCOPE, gekozenHoroscoop);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Controleren of de Activity de interface implementeerd -> Zodat Activity met fragment kan communiceren
        try {
            changeFragmentListener = (onChangeFragmentListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChangeFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            gekozenGeboorteJaar = getArguments().getString(EXTRA_BIRTHDAY);

            gekozenHoroscoop = getArguments().getInt(EXTRA_HOROSCOPE);
        }
    }

    private int getResourceId(int gekozenHoroscoop) {
        switch (gekozenHoroscoop) {
            case 0: return R.drawable.waterman;
            case 1: return R.drawable.vissen;
            case 2: return R.drawable.ram;
            case 3: return R.drawable.stier;
            case 4: return R.drawable.tweeling;
            case 5: return R.drawable.kreeft;
            case 6: return R.drawable.leeuw;
            case 7: return R.drawable.maagd;
            case 8: return R.drawable.weegschaal;
            case 9: return R.drawable.schorpioen;
            case 10: return R.drawable.boogschutter;
            case 11: return R.drawable.steenbok;
            default: return -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewMain = inflater.inflate(R.layout.fragment_main,container,false);

        // Attributen koppelen aan control-views
        textViewResultGeboortejaar = (TextView) viewMain.findViewById(R.id.textViewGeboortejaar);
        btnSelecteerGeboortejaar = (Button) viewMain.findViewById(R.id.buttonSelecteerGeboortejaar);
        btnSelecteerHoroscoop = (Button) viewMain.findViewById(R.id.buttonSelecteerHoroscoop);
        imageViewResultHoroscoop = (ImageView) viewMain.findViewById(R.id.imageViewResultaatHoroscoop);

        // Click events
        btnSelecteerGeboortejaar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragmentListener.onSelecteerGeboortejaar();
            }
        });

        btnSelecteerHoroscoop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragmentListener.onSelecteerHoroscoop();
            }
        });


        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String geboortejaar = data.getString("gekozenGeboorteJaar", "");
        int horoscoop = data.getInt("gekozenHoroscoop", 0);

        // Als de variabele leeg is -> Vul ze op uit SharedPreferences
        if (gekozenGeboorteJaar == null || gekozenGeboorteJaar.equals(""))
        gekozenGeboorteJaar = geboortejaar;

        // Als de variabele leeg is -> Vul ze op uit SharedPreferences
        if (gekozenHoroscoop == 0)
        gekozenHoroscoop = horoscoop;

        showGekozenGeboortejaar();
        showGekozenHoroscoop();

        return viewMain;
    }

    public void showGekozenGeboortejaar() {
        // Toon het gekozen geboortejaar (Mag niet in onCreate want element bestaat daar nog niet)
        textViewResultGeboortejaar.setText("Geboortejaar: " + gekozenGeboorteJaar);
    }

    public void showGekozenHoroscoop() {
        // Toon de gekozen horoscoop (Mag niet in onCreate want element bestaat daar nog niet)
        if (gekozenHoroscoop > 0)
            imageViewResultHoroscoop.setImageResource(getResourceId(gekozenHoroscoop));
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("gekozenGeboorteJaar", gekozenGeboorteJaar);
        editor.putInt("gekozenHoroscoop", gekozenHoroscoop);
        editor.commit();
        // Data ophalen in onCreate
    }

    // Interface maken -> Staat een Fragment toe om te communiceren met zijn Activity
    public interface onChangeFragmentListener {
        // Deze methodes wordt ook uitgevoerd in Activity
        public void onSelecteerGeboortejaar();
        public void onSelecteerHoroscoop();
    }
}
