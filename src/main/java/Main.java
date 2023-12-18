//import java.awt.event.KeyEvent;

import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;

/* NOTE: Best viewed with IntelliJ IDEA > Preferences > General > Scrolling > "enable smooth scrolling" OFF. */

public class Main {
    public static void main(String[] args) throws Exception {
        Screen s = new Screen(7, 13);   // fieldWidth minimum = 12
        UserShip us = new UserShip(s);
        EnemyShip es = new EnemyShip();
        s.initializeField(s, us, es);
        s.updateDisplay(s, us);
        Random random = new Random();

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = defaultTerminalFactory.createTerminal();
        TerminalScreen sc = new TerminalScreen(terminal);

        long frameStart;
        long frameFinish;
        long frameTotal;

        long currentLoopIterationTime;
        long moveDebrisTime = System.currentTimeMillis() + 500L;
        boolean okayToCreateNewDebris = true;

        boolean gameContinues = true;

        /* GAME LOOP */
        while (gameContinues) {
            frameStart = System.currentTimeMillis();

            var input = sc.pollInput();
            if (input != null) {
                var keyType = input.getKeyType();
                if (keyType == KeyType.ArrowLeft && us.leftMovementAvailable > 0) {
                    s.field[us.position] = ' ';
                    s.field[us.position - 1] = us.userShipIcon;
                    us.position--;
                    us.leftMovementAvailable--;
                    us.rightMovementAvailable++;
                }
                else if (keyType == KeyType.ArrowRight && us.rightMovementAvailable > 0) {
                    s.field[us.position] = ' ';
                    s.field[us.position + 1] = us.userShipIcon;
                    us.position++;
                    us.rightMovementAvailable--;
                    us.leftMovementAvailable++;
                }
                else if (keyType == KeyType.ArrowUp && us.upMovementAvailable > 0) {
                    s.field[us.position] = ' ';
                    s.field[us.position - s.fieldWidth] = us.userShipIcon;
                    us.position = us.position - s.fieldWidth;
                    us.upMovementAvailable--;
                    us.downMovementAvailable++;
                }
                else if (keyType == KeyType.ArrowDown && us.downMovementAvailable > 0) {
                    s.field[us.position] = ' ';
                    s.field[us.position + s.fieldWidth] = us.userShipIcon;
                    us.position = us.position + s.fieldWidth;
                    us.downMovementAvailable--;
                    us.upMovementAvailable++;
                }
            }

            /* Check & Create new Debris object */
            if (okayToCreateNewDebris) {
                Debris.createDebrisOnField(s);
                okayToCreateNewDebris = false;
            }

            //s.updateDisplay(s, us);
            s.printScreen(s, us);

            /* Check & Move Debris object(s) downward */
            currentLoopIterationTime = System.currentTimeMillis();
            if (currentLoopIterationTime > moveDebrisTime) {
                Debris.moveDebrisDownward(s);
                UserShip.collisionCheck(s, us, Debris.debrisOnFieldArray);     // NEED TO REDRAW SHIP AFTER IMPACT. Decrement score.
                if (us.userHitPoints == 0) gameContinues = false;
                currentLoopIterationTime = System.currentTimeMillis();
                moveDebrisTime = currentLoopIterationTime + 300L;           // Controls debris fall speed
                okayToCreateNewDebris = true;
            }

            //s.updateDisplay(s, us);
            s.printScreen(s, us);

            frameFinish = System.currentTimeMillis();
            frameTotal = frameFinish - frameStart;
            if (frameTotal < 16) {
                Thread.sleep(16 - frameTotal);
            }

        }

        System.out.println("GAME OVER");



//        for (int i = 9000; i < 10000; i++) {
//            System.out.println(i + "     " + (char)(i));
//        }
    }

//    public static void keyPressed(KeyEvent e, UserShip us, Screen s) {
//        int key = e.getKeyCode();
//        if (key == KeyEvent.VK_LEFT) {
//            if (us.leftMovementAvailable > 0) {
//                us.leftMovementAvailable = us.leftMovementAvailable - 1;
//                us.rightMovementAvailable = us.rightMovementAvailable + 1;
//                int previousPosition = us.position;
//                us.position = us.position - 1;
//                s.field[previousPosition] = ' ';
//                s.field[us.position] = us.userShipIcon;
//            }
//        }
//        else if (key == KeyEvent.VK_RIGHT) {
//            if (us.rightMovementAvailable > 0) {
//                us.rightMovementAvailable = us.rightMovementAvailable - 1;
//                us.leftMovementAvailable = us.leftMovementAvailable + 1;
//                int previousPosition = us.position;
//                us.position = us.position + 1;
//                s.field[previousPosition] = ' ';
//                s.field[us.position] = us.userShipIcon;
//            }
//        }
//        else {
//            // do nothing
//        }
//    }

}

// ASCII Numbers:
// 9833-6 music notes
// ASCII: 186, 187, 197, 449(hp), 478(ship shoot)
// 664(bullet2), 706-709(<>'s), 730(bullet3), 735(x)
// 751-754(smaller <>'s), 755(left bullet), 778(blt)
// 805(btm blt), 823-24(angled /), 839(=), 888(hp)
// 1126(alt ship), 990-1(bolt), 1421(pinwheel),
// 1232(A ship being hit frame), 1758(blt)
// 2039(?), 1805, 3663-4(flash blt), 3572(wave)
// 6816(pluscircle), 6671+(mountains), 7164-6(idk)
// 7140-1(up/dwn wave), 8224(cross), 9994-5(yellow hand)
// 9989(green check), 9825,9(heart), 9760(s&cb), 9616+-(hp)