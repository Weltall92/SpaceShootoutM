import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;         // IDEA: Change this class to "DownwardDebris" and create sideways Debris too?

public class Debris {
    static int maxOnField = 4;
    static int currOnField = 0;
    static Debris[] debrisOnFieldArray = new Debris[maxOnField];
    int position;
    char debrisIcon = 'x';
    static boolean wasDebrisRemoved = false;

    public Debris(int position) {
        // random position, starting position must be between s.fieldWidth+1 and s.fieldWidth+s.fieldWidth-1
        this.position = position;
    }

    public void addToDebrisArray() {
        debrisOnFieldArray[currOnField] = this;
        currOnField++;
    }

    public void removeFromDebrisArray(Debris d) {
        //
    }

    public static void createDebrisOnField(Screen s) {
        Random random = new Random();
        if (Debris.currOnField < Debris.maxOnField) {
            int r = random.nextInt(s.fieldWidth * 2);
            Debris d = new Debris((r % s.fieldWidth) /*+ s.fieldWidth*/);/////////////////commented out, still need remove extra row
            d.addToDebrisArray();
            s.field[d.position] = d.debrisIcon;
        }
    }

    public static void moveDebrisDownward(Screen s) {
        if (Debris.currOnField > 0) {

            /* Erase current position for all debris objects on field */
            for (int i = 0; i < Debris.currOnField; i++) {
                s.field[Debris.debrisOnFieldArray[i].position] = ' ';
            }

            /* Loop and check if need to remove debris object */
            wasDebrisRemoved = false;
            for (int i = 0; i < Debris.currOnField; i++) {
                if (Debris.debrisOnFieldArray[i].position > ((s.fieldWidth * (s.fieldHeight - 2)) - 1)) {     // '-1' because debris kept hitting and erasing 'H'
                    /* Remove debris object */
                    Debris.debrisOnFieldArray[i] = null;
                    Debris.currOnField--;
                    wasDebrisRemoved = true;
                }
            }

            /* Re-order array (if debris was removed) */
            if (wasDebrisRemoved) {
                int nextAvailableIndex = -1;
                if (Debris.debrisOnFieldArray[0] == null) nextAvailableIndex = 0;
                for (int j = 1; j < Debris.debrisOnFieldArray.length; ) {
                    if (Debris.debrisOnFieldArray[j] == null) {
                        if (Debris.debrisOnFieldArray[j - 1] != null) {
                            nextAvailableIndex = j;
                        } else {
                            if (j - 1 == 0) {
                                nextAvailableIndex = 0;  // covers case if first 2 elements are nulls.
                            }
                        }
                        j++;
                    } else {
                        if (nextAvailableIndex > -1) {  // covers 2nd position not null and 1st position null for first iteration.
                            Debris.debrisOnFieldArray[nextAvailableIndex] = Debris.debrisOnFieldArray[j];
                            Debris.debrisOnFieldArray[j] = null;
                            nextAvailableIndex = j;
                        } else {      // case if a bunch of Debris objects and no null.
                            j++;    // keep searching array, no need to move object.
                        }
                    }
                }
            }


            /* Move remaining debris downward */
            for (int i = 0; i < Debris.currOnField; i++) {
                Debris.debrisOnFieldArray[i].position += s.fieldWidth;                                      // ~update
                s.field[Debris.debrisOnFieldArray[i].position] = Debris.debrisOnFieldArray[i].debrisIcon;   // ~draw
            }
        }
    }



}
