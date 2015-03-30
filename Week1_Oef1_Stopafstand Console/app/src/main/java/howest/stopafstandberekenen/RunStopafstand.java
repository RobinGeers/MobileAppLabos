package howest.stopafstandberekenen;

import java.util.Scanner;

/**
 * Created by robin_000 on 13/02/2015.
 */
public class RunStopafstand {

    public static void main(String[] args) {

        // Variabele
        StopafstandInfo.WegType type;

        // Waarden inlezen
        Scanner sc = new Scanner(System.in);

        System.out.println("Geef uw snelheid op (in km/u: \t");
        int snelheid = sc.nextInt();

        System.out.println("Geef uw reactietijd op (in sec.): \t");
        float reactietijd = sc.nextFloat();

        System.out.println("Geef uw type wegdek op (NAT of DROOG): \t");
        String wegtype = sc.next();

        switch (wegtype) {
            case "NAT": type = StopafstandInfo.WegType.WEGDEK_NAT;
                break;
            case "DROOG": type = StopafstandInfo.WegType.WEGDEK_DROOG;
                break;
            default: type = StopafstandInfo.WegType.WEGDEK_DROOG;
        }

        System.out.println("Uw snelheid: \t" + snelheid);
        System.out.println("Uw reactietijd: \t" + reactietijd);
        System.out.println("Uw wegtype: \t" + type);

        // Toon afstand
        StopafstandInfo afstand = new StopafstandInfo(snelheid, reactietijd, type);
        System.out.println("Uw stopafstand bedraagt: " + afstand.getStopafstand());
    }
}
