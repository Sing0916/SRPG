package singRPG.java;

import java.util.Scanner;
import singRPG.classes.entity.Player;
import singRPG.classes.entity.Unit;
import singRPG.constant.Colours;
import singRPG.system.SaveSystem;
import singRPG.system.Util;

class RPG {
    static Scanner scan = new Scanner(System.in);
    static final String name = "SRPG";
    static int userAction = -1;
    static boolean win = false;
    static Player[] players;

    public static void main(String[] args) throws Exception {
        Util.clearScreen();

        // loop game
        outer: while (true) {
            Util.printLine();
            System.out.println("Welcome to " + name);
            Util.printLine();
            System.out.println("[1] Start");
            System.out.println("[2] Load");
            System.out.println("[3] Quit");
            userAction = Util.checkUserAction(1, 3);
            switch (userAction) {
                case 1:
                    userAction = SaveSystem.create();
                    break;
                case 2:
                    Util.clearLine(4);
                    System.out.println("[0] Back");
                    System.out.println("Select player:");
                    String names[] = SaveSystem.getPlayerList();
                    for (int i = 0; i < names.length; i++) {
                        System.out.println("[" + (i + 1) + "] " + names[i]);
                    }
                    userAction = Util.checkUserAction(0, names.length);
                    break;
                case 3:
                    break outer;
            }

            // back from select player
            if (userAction == 0) {
                Util.clearScreen();
                continue outer;
            }
            userAction--;
            while (true) {
                loop();
                Util.printLine();
                System.out.println("Game ended, wish to continue?");
                System.out.println("[0] Yes, let's go another round");
                System.out.println("[1] No, get me out of here");
                Util.printLine();
                int leave = Util.checkUserAction(0, 1);
                if (leave == 1) {
                    Util.clearScreen();
                    Util.printLine();
                    System.out.println("Bye bye, see you later!");
                    Util.printLine();
                    break outer;
                }
            }
        }
    }

    public static void loop() throws Exception {
        Util.clearScreen();
        Util.printLine();
        players = SaveSystem.read();
        String[] enemyNames = SaveSystem.readEnemyNames();
        Unit[] enemies = new Unit[3];
        for (int i = 0; i < 3; i++) {
            enemies[i] = new Unit(enemyNames[Util.random(enemyNames.length)], false,
                    (players[userAction].getLevel() - (Util.random(15) + 1)) * 100);
            showDetail(enemies[i]);
        }
        System.out.println("Choose 1 enemy to fight!");
        String format = "%s%s%s%-3s%s%s%s\n";
        System.out.printf(format, "[0] ", Colours.ANSI_PURPLE, "Lv.", (int) enemies[0].getLevel(), Colours.ANSI_RESET,
                " ", enemies[0].getName());
        System.out.printf(format, "[1] ", Colours.ANSI_PURPLE, "Lv.", (int) enemies[1].getLevel(), Colours.ANSI_RESET,
                " ", enemies[1].getName());
        System.out.printf(format, "[2] ", Colours.ANSI_PURPLE, "Lv.", (int) enemies[2].getLevel(), Colours.ANSI_RESET,
                " ", enemies[2].getName());
        int chooseEnemy = Util.checkUserAction(0, 2);

        Game game = new Game(players[userAction], enemies[chooseEnemy]);
        Util.clearScreen();

        // game start
        win = game.start();
        if (win)
            System.out.println("You win!");
        else
            System.out.println("You lose!");

        // game end
        players[userAction].updateLV();
        System.out.println("EXP: " + (int) players[userAction].getExp() + "/"
                + (int) (players[userAction].getLevel() + 1) * 100);
        SaveSystem.write(players);
        Util.pressAnyKey();
        Util.clearScreen();
    }

    public static void showDetail(Unit u) {
        String format = "%s%s%-3s%s%s%-10s%s%-3s%s%-4s%s%s\n";
        System.out.printf(format, Colours.ANSI_PURPLE, "Lv.", (int) u.getLevel(), Colours.ANSI_RESET, " ", u.getName(),
                "| ATK:", (int) u.getAtk(), " DEF:", (int) u.getDef(), "HP:", (int) u.getHp());
        Util.printLine();
    }
}
