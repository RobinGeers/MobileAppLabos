package nmct.howest.be.evaluationstudents.loader;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import nmct.howest.be.admin.Student;
import nmct.howest.be.admin.StudentAdmin;

public class StudentsLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;

    // Array van kolomnamen
    private final String[] mColumnNames = new String[] {
            BaseColumns._ID,
            Contract.StudentColumns.COLUMN_STUDENT_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAAL
    };

    private static Object lock = new Object();

    // Constructor
    public StudentsLoader(Context context) {
        super(context);
    }

    // onStartLoading: Nagaan of data reeds werd ingeladen EN of content gewijzigd is
    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }

        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (mCursor == null) {
            loadCursor();
        }
        return mCursor;
    }

    private void loadCursor() {
        // Creeër een MatrixCursor
        synchronized (lock) {
            if (mCursor != null)
                return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;
            // Overloop alle studenten -> Per student: vul een rij op met Student properties
            for (Student student : StudentAdmin.getStudenten()) {
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(student.getNaamStudent());
                row.add(student.getVoornaamStudent());
                row.add(student.getEmailStudent());
                row.add(student.getTotaleScoreStudent());
                id++;
            }
            mCursor = cursor;
        }
    }
}
