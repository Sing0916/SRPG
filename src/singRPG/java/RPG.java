package singRPG.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import singRPG.classes.entity.Player;
import singRPG.classes.entity.Unit;
import singRPG.system.SaveSystem;
import singRPG.system.Util;

class RPG {
    static Scanner scan = new Scanner(System.in);
    static final String name = "SRPG";
    static int userAction = -1;

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        Util.clearScreen();
        Player[] players;

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
            players = SaveSystem.read();
            Unit enemy = new Unit(100, 5, 5, 0, 0, "Wolf", false,
                    (players[userAction].getLevel() - (Math.random() * 15 + 1)) * 100);
            Game game = new Game(players[userAction], enemy);
            Util.clearScreen();

            // game start
            boolean win = game.start();
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
    }
}
