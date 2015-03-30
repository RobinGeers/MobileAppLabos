package nmct.howest.be.evaluationstudents;


import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import nmct.howest.be.evaluationstudents.loader.Contract;
import nmct.howest.be.evaluationstudents.loader.StudentsLoader;

public class StudentsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ImageView imageViewStudent;
    private TextView textViewStudentNaam, textViewStudentVoorNaam, textViewStudentEmail, textViewStudentScoreTotaal;
    private StudentAdapter mAdapter;

    // Visualiseert data afkomstig van de Cursor
    class StudentAdapter extends SimpleCursorAdapter {

        public StudentAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        // Als je scrollt zorgt bindView ervoor dat de bestaande views op het scherm hergebruikt worden -> Performance
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            imageViewStudent = (ImageView) view.findViewById(R.id.imageViewStudent);
            textViewStudentNaam = (TextView) view.findViewById(R.id.textViewStudentNaam);
            textViewStudentVoorNaam = (TextView) view.findViewById(R.id.textViewStudentVoorNaam);
            textViewStudentEmail = (TextView) view.findViewById(R.id.textViewStudentEmail);
            textViewStudentScoreTotaal = (TextView) view.findViewById(R.id.textViewStudentScore);

            // Haal de score op (Rond af 2 cijfers na de komma)
            int colNrScore = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL);
            double score = cursor.getDouble(colNrScore);
            DecimalFormat df = new DecimalFormat("##.00");

            // Vul afgeronde score in textView
            textViewStudentScoreTotaal.setText(df.format(score));

            // Vul imageView op
            if (score < 8)
                imageViewStudent.setImageResource(R.drawable.student_red);
            else if (score < 10)
                imageViewStudent.setImageResource(R.drawable.student_orange);
            else
                imageViewStudent.setImageResource(R.drawable.student_green);
        }
    }

    // Maakt nieuwe Loader aan
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StudentsLoader loader = new StudentsLoader(getActivity());
        loader.loadInBackground();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Adapter koppelen aan cursor
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Uitgevoerd als eerder aangemaakte cursor gesloten wordt. (Data is onbeschikbaar)
        mAdapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Maak lege adapter -> Koppel deze aan de ListView (Row_student!!!)
        // Koppeling van data aan textViews: Array Strings van kolomnamen -> Naar Array Strings van View-objecten (data wordt erin getoond)
        mAdapter = new StudentAdapter(getActivity(), R.layout.row_student, null, new String[] {
                Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
                Contract.StudentColumns.COLUMN_STUDENT_NAAM,
                Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
                Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL
        }, new int[]{ R.id.textViewStudentVoorNaam, R.id.textViewStudentNaam, R.id.textViewStudentEmail},0);
        setListAdapter(mAdapter);

        // Controleren of loader al bestaat -> Indien niet, wordt aangemaakt
        getLoaderManager().initLoader(0, null, this);

    }

    public StudentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewStudents = inflater.inflate(R.layout.fragment_students, container, false);

        return viewStudents;
    }
}
