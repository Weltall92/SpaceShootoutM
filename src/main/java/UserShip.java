import java.awt.event.KeyEvent;

public class UserShip {
    String[] userShipHitBoxCells;
    int userHitPoints;
    int maxHitPoints = 5;
    //char[] userHitPointsIcons = {'H', 'P', '[', 9829, 9829, 9825, 9825, 9479, 9479, 9478, 9478, ']'};
    char[] userHitPointsIcons = {'H', 'P', ' ', 9829, ' ', 9829, ' ', 9829, ' ', 9825, ' ', 9825};
    char[] userScoreIcons = {'S', 'C', 'O', 'R', 'E', ' ', '0', '0', '0', '0'};


    char userShipIcon = 'A';
    int score = 0;
    int position;                   // ships index position within field array.
    int leftMovementAvailable;      // amount of steps left until border.
    int rightMovementAvailable;     // amount of steps right until border.
    int upMovementAvailable;        // amount of steps up until border.
    int downMovementAvailable;      // amount of steps down until border.

    public UserShip(Screen s) {
        //this.position = s.fieldSize - ((int)(s.fieldWidth * 1.5) + 1);
        //this.position = s.fieldSize - ((int)((2)*(s.fieldWidth * 1.5)) + 1);/////Ship A on screen, but movement limited weird.
        this.position = s.fieldSize - (2 * s.fieldWidth) + ((int)((0.5)*(s.fieldWidth)));
        this.userHitPoints = 3;
        if (s.fieldWidth % 2 == 0) {
            this.leftMovementAvailable = (s.fieldWidth / 2) - 1;
        } else {
            this.leftMovementAvailable = s.fieldWidth / 2;
        }
        this.rightMovementAvailable = s.fieldWidth / 2;
        this.upMovementAvailable = s.fieldHeight - 3;       // set to only allow movement up, not into "score" array.
        this.downMovementAvailable = 0;                     // based on defined starting position, down movement is 0.
    }

    public static boolean collisionCheck(Screen s, UserShip us, Debris[] dArray) {
        for (int i = 0; i < Debris.currOnField; i++) {
            if (us.position == dArray[i].position) {
                us.userHitPoints--;
                updateHearts(us);
                s.field[us.position] = us.userShipIcon;     // NOTE: this does not long term fix ship icon vanishing on hit...
                return true;
            }
        }
        return false;
    }

    private static void updateHearts(UserShip us) {
        int[] positionsOfHeartIconsInArray = {3, 5, 7, 9, 11};
        for (int i = 0; i < us.userHitPoints; i++) {
            us.userHitPointsIcons[positionsOfHeartIconsInArray[i]] = 9829;
        }
        for (int i = us.userHitPoints; i < us.maxHitPoints; i++) {
            // fill w/ empty heart icon
            us.userHitPointsIcons[positionsOfHeartIconsInArray[i]] = 9825;
        }
    }

    private static void updateScore(UserShip us) {
        //us.score++; // if debris removed
        if (Debris.wasDebrisRemoved) {
            us.score++;
        }
    }

    private static void drawScore(Screen s) {

    }


}
