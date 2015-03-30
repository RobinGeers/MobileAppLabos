package be.howest.nmct;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectGeboortejaarFragment extends ListFragment {

    public onSelecteerGeboortejaarFragmentListener geboorteJaarListener;


    public SelectGeboortejaarFragment() {
    }


    public static SelectGeboortejaarFragment newInstance() {
        SelectGeboortejaarFragment fragment = new SelectGeboortejaarFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            geboorteJaarListener = (onSelecteerGeboortejaarFragmentListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChangeFragmentListener");
        }
    }

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, GEBOORTEJAREN); // android.R!!! anders vind je simple_list_item_1 niet
        setListAdapter(myListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String sgekozenGeboortejaar = GEBOORTEJAREN.get(position);

        // Geef jaartal mee aan MainActivity
        geboorteJaarListener.onNieuwGeboortejaarGekozen(sgekozenGeboortejaar);
    }


    public interface onSelecteerGeboortejaarFragmentListener {
        public void onNieuwGeboortejaarGekozen(String geboortejaar);
    }
}
