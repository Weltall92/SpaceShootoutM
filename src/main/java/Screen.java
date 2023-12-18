public class Screen {

    public char[] field;                // debrisField - which will include "score" & "hp" arrays area but should not write over them.
    public final int fieldSize;                       // Let's have debris start at very top but not overwrite "score" array, etc.
    public final int fieldHeight;
    public final int fieldWidth;
    private final int horizontalBorderLength;
    private final int verticalBorderLength;
    private final int userHitPointsIconsArrayStartingIndex;
    private final int userScoreIconsArrayStartingIndex;

    public Screen(int fieldHeight, int fieldWidth) {
        //if ((fieldHeight < 10 || fieldWidth < 12) || (fieldHeight > 100 || fieldWidth > 100))
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.horizontalBorderLength = fieldWidth + 2;
        this.verticalBorderLength = fieldHeight;
        this.fieldSize = fieldHeight * fieldWidth;

        this.field = new char[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            this.field[i] = ' ';
        }
        /* Set "score" & "hit points" array positions. */
        this.userScoreIconsArrayStartingIndex = 0;
        this.userHitPointsIconsArrayStartingIndex = ((fieldHeight - 1) * fieldWidth);
    }

    public void updateDisplay(Screen s, UserShip us) {
        /* Print top border */
        int n = 0;
        System.out.print((char)9627);
        for (int i = 0; i < this.horizontalBorderLength - 2; i++) {
            System.out.print((char)9632);
        }
        System.out.print((char)9628);
        System.out.println();

        /* Print field and side borders */

        /* Print score array & border */
        System.out.print((char)9475);
        for (char c : us.userScoreIcons) System.out.print(c);
        for (int i = us.userScoreIcons.length; i < fieldWidth; i++) {   // <-- This block must REPLACE (in position) of existing HP.
            System.out.print(' ');
        }
        System.out.print((char)9475);
        System.out.println();

        //int startingIndexForUserHitPointIconsOnField = s.fieldSize - s.fieldWidth;
        for (int j = 0; j < this.verticalBorderLength-2; j++) { //added"-1" (bc HPArray?) /////changed to -2 but need to reposition A
            System.out.print((char)9475);
            //
            for (int k = 0; k < this.horizontalBorderLength - 2; k++) {
                System.out.print(this.field[n]);
                n++;
            }
            //
            System.out.print((char)9475);
            System.out.println();
        }

        /* Print hit points array & border */
        System.out.print((char)9475);
        for (char c : us.userHitPointsIcons) System.out.print(c);
        //if (us.userHitPointsIcons.length < (fieldWidth - 2)) {}
        for (int i = us.userHitPointsIcons.length; i < fieldWidth; i++) {   // <-- This block must REPLACE (in position) of existing HP.
            System.out.print(' ');
        }
        System.out.print((char)9475);
        System.out.println();

        /* Print bottom border */
        System.out.print((char)9625);
        for (int i = 0; i < this.horizontalBorderLength -2; i++) {
            System.out.print((char)9632);
        }
        System.out.print((char)9631);
        System.out.println();
    }


    public void printScreen(Screen s, UserShip us) {
        /* Print top border */
        System.out.print((char) 9627);
        for (int i = 0; i < this.horizontalBorderLength - 2; i++) {
            System.out.print((char) 9632);
        }
        System.out.print((char) 9628);
        System.out.println();

        /* Print (debris) field */
        System.out.print((char)9475);   // print first left-most vertical border.

        for (int i = 0; i < fieldSize; i++) {
            if (i == s.userScoreIconsArrayStartingIndex) {   // check location to print "score" array.
                for (char c : us.userScoreIcons) {
                    System.out.print(c);
                    i++;
                }
            }

            if (i == s.userHitPointsIconsArrayStartingIndex) {   // check location to print "hit points" array.
                for (char c : us.userHitPointsIcons) {
                    System.out.print(c);
                    i++;
                }
            }

            System.out.print(s.field[i]);                // print field[i]

            if (i == fieldSize - 1) {
                System.out.println((char)9475);         // print last right-most border.
                break;
            }

            if ((i + 1) % (fieldWidth) == 0) {   // check / print right-most border, new line, then left-most border.
                System.out.print((char)9475);    // need "i+1" so math calculates correctly.
                System.out.println();
                System.out.print((char)9475);
            }
        }

        /* Print bottom border */
        System.out.print((char)9625);
        for (int i = 0; i < this.horizontalBorderLength - 2; i++) {
            System.out.print((char)9632);
        }
        System.out.print((char)9631);
        System.out.println();

    }

    public void initializeField(Screen s, UserShip us, EnemyShip es) {     // PUT IN "SHIP" PARAMETERS
        for (int i = 0; i < fieldSize; i++) {
            field[i] = ' ';
        }
        field[us.position] = us.userShipIcon;

//        int count = 0;
//        int startingIndexForUserHitPointIconsOnField = s.fieldSize - s.fieldWidth;
//        for (int j = startingIndexForUserHitPointIconsOnField; j < startingIndexForUserHitPointIconsOnField + us.userHitPointsIcons.length; j++) {
//            field[j] = us.userHitPointsIcons[count];
//            count++;
//        }

    }

}
