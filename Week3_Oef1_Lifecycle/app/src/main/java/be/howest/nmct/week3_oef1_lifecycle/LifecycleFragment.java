package be.howest.nmct.week3_oef1_lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class LifecycleFragment extends Fragment {

    // Control-views declareren als attributen
    private Button buttonAfsluiten;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("LifecycleFragment", "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLifecycle = inflater.inflate(R.layout.fragment_lifecycle, container, false);

        // Control-views koppelen aan attributen
        buttonAfsluiten = (Button) viewLifecycle.findViewById(R.id.btnAfsluiten);

        buttonAfsluiten.setOnClickListener(new View.OnClickListener(){ // ALT + ENTER om methode te implementeren
            @Override
            public void onClick(View v) {
            afsluiten();
            }
        });

        Log.d("LifecycleFragment", "onCreateView");

        return viewLifecycle;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("LifecycleFragment", "onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LifecycleFragment", "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LifecycleFragment", "onDetach");
    }

    private void afsluiten() {
        getActivity().finish();
    }
}
