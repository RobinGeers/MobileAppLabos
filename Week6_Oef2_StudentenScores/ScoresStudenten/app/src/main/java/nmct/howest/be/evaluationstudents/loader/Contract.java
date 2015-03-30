package nmct.howest.be.evaluationstudents.loader;

import android.provider.BaseColumns;

/**
 * Created by robin_000 on 29/03/2015.
 */
public final class Contract {

    // Kolomnamen bijhouden in afzonderlijke klasse
    public interface StudentColumns extends BaseColumns {
        public static final String COLUMN_STUDENT_NAAM = "student_naam";
        public static final String COLUMN_STUDENT_VOORNAAM = "student_voornaam";
        public static final String COLUMN_STUDENT_EMAIL = "student_email";
        public static final String COLUMN_STUDENT_SCORE_TOTAAL = "student_score_totaal";
    }
}
