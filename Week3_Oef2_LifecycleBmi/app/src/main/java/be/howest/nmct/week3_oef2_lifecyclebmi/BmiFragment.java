package be.howest.nmct.week3_oef2_lifecyclebmi;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class BmiFragment extends Fragment {

    public static final String PREFS_NAME = "MyPrefsFile";

    // Plaats in BmiActivity bij 'onCreate' methode 'new BmiFragment()'

    // Control-views declareren als attributen
    private EditText editTextHeight, editTextMass;
    private Button buttonBereken;
    private ImageView imageViewBmi;
    private TextView textViewBmiResult, textViewCategoryResult;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View aanmaken -> koppelen aan fragment & returnen
        View viewBMI = inflater.inflate(R.layout.fragment_lifecycle_bmi, container, false);

        // Control-views koppelen aan attributen
        editTextHeight = (EditText) viewBMI.findViewById(R.id.editTextHeight);
        editTextMass = (EditText) viewBMI.findViewById(R.id.editTextMass);
        buttonBereken = (Button) viewBMI.findViewById(R.id.btnBereken);
        imageViewBmi = (ImageView) viewBMI.findViewById(R.id.imageViewBmi);
        textViewBmiResult = (TextView) viewBMI.findViewById(R.id.textViewIndexResult);
        textViewCategoryResult = (TextView) viewBMI.findViewById(R.id.textViewCategoryResult);

        buttonBereken.setOnClickListener(new View.OnClickListener(){ // ALT + ENTER om methode te implementeren
            @Override
            public void onClick(View v) {
                berekenBMI();
            }
        });

        // Opgeslagen data terugplaatsen
        // -> Haal waardes uit SharedPreferences
        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String height = data.getString("height", "0");
        String mass = data.getString("mass", "0");

        // -> Als er iets in zit plaats het in de 'editText' control-view
        if (!height.equals("0")) {
            editTextHeight.setText(height);
        }
        // -> Als er iets in zit plaats het in de 'editText' control-view
        if (!height.equals("0")) {
            editTextMass.setText(mass);
        }

        return viewBMI;
    }

    // Zodra de App opstart -> BMI herberekenen (Controleren of velden niet leeg zijn!!)
    @Override
    public void onResume() {
        super.onResume();
        berekenBMI();
    }

    @Override
    public void onStop() {
        super.onStop();

        // Bewaar height & mass als applicatie stopt in SharedPreferences (key, value)
        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME,0); //(0 -> private mode)
        SharedPreferences.Editor editor = data.edit();
        editor.putString("height", this.editTextHeight.getText().toString());
        editor.putString("mass", this.editTextMass.getText().toString());
        editor.commit();
        // Data ophalen in 'onCreateView' (Moet denk ik in onCreate)
    }

    private void berekenBMI() {

        // Als je de methode 'berekenBMI' oproept bij de onResume() methode -> CONTROLE DOEN OF TEKST LEEG IS
        // Anders crasht applicatie
        if (!this.editTextHeight.getText().toString().isEmpty()
                && !this.editTextMass.getText().toString().isEmpty()
                && !this.editTextHeight.getText().toString().equals("0")
                && !this.editTextMass.getText().toString().equals("0")) {

            // Steek ingevulde waardes in variabelen
            float height = Float.parseFloat(editTextHeight.getText().toString());
            float mass = Float.parseFloat(editTextMass.getText().toString());

            BMIinfo info = new BMIinfo(height, mass);

            // Bereken
            info.recalculate();
            float bmi = info.getBmiIndex();
            BMIinfo.Category category = BMIinfo.Category.getCategory(bmi);

            // Plaats berekend BMI en category in tekstvakken (Je kan ook "" + bmi gebruiken om te converten naar string)
            textViewBmiResult.setText(String.valueOf(bmi));
            textViewCategoryResult.setText(String.valueOf(category));

            // Verander foto die bij BMI past
            this.imageViewBmi.setImageResource(getResourceId(category));
        }
    }

    private int getResourceId(BMIinfo.Category category) {

        int imageNumber = 1; // Je kan niet in een case iets returnen dus steek je het in een variabele -> Die variabele return je na de switch
        // Zet category object om naar een string -> categoryName
        // Als de category gelijk is aan categoryName -> Toon gepaste foto
        String categoryName = String.valueOf(category);
        switch (categoryName) {
            case "GROOT_ONDERGEWICHT": imageNumber = R.drawable.silhouette_1;
            break;
            case "ERNSTIG_ONDERGEWICHT": imageNumber = R.drawable.silhouette_2;
            break;
            case "ONDERGEWICHT": imageNumber = R.drawable.silhouette_3;
            break;
            case "NORMAAL": imageNumber = R.drawable.silhouette_4;
            break;
            case "OVERGEWICHT": imageNumber = R.drawable.silhouette_5;
            break;
            case "MATIG_OVERGEWICHT": imageNumber = R.drawable.silhouette_6;
            break;
            case "ERNSTIG_OVERGEWICHT": imageNumber = R.drawable.silhouette_7;
            break;
            case "ZEER_GROOT_OVERGEWICHT": imageNumber = R.drawable.silhouette_8;
            break;
        }
        return imageNumber;
    }
}
