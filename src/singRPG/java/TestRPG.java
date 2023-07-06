package singRPG.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import singRPG.classes.Magic;
import singRPG.system.MagicSystem;
import singRPG.system.SaveSystem;
import singRPG.system.Util;

public class TestRPG {
    static Scanner scan = new Scanner(System.in);
    int userAction = -1;

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        Util.clearScreen();

        while (true) {
            Util.printLine();
            System.out.println("[1] Create new player");
            System.out.println("[2] Create Magic");
            System.out.println("[3] List Magic");
            int userAction = Util.checkUserAction(1, 3);
            switch (userAction) {
                case 1:
                    SaveSystem.create();
                    break;
                case 2:
                    MagicSystem.createMagic();
                    break;
                case 3:
                    Util.clearScreen();
                    Magic magics[] = MagicSystem.readMagic();
                    String format;
                    for (int i = 0; i < magics.length; i++) {
                        switch (magics[i].getMagicType()) {
                            case DMG:
                                format = "%s%s%s%-20s%s%-8s%s%-7s%s%s\n";
                                System.out.printf(format,
                                        "[", (i + 1), "]: ", magics[i].getName(), "| Damage: ",
                                        magics[i].getAmount(), "| Cost: ",
                                        magics[i].getCost(), "| Hit Chance: ",
                                        (double) magics[i].getChance() / 10);
                                break;
                            case HEAL:
                                format = "%s%s%s%-20s%s%-8s%s%-7s%s%s\n";
                                System.out.printf(format,
                                        "[", (i + 1), "]: ", magics[i].getName(), "| Amount: ",
                                        magics[i].getAmount(), "| Cost: ",
                                        magics[i].getCost(), "| Hit Chance: ",
                                        (double) magics[i].getChance() / 10);
                                break;
                            case BUFF:
                                format = "%s%s%s%-20s%s%-8s%s%-7s%s%-4s%s%s\n";
                                System.out.printf(format,
                                        "[", (i + 1), "]: ", magics[i].getName(), "| Amount: ",
                                        magics[i].getAmount(), "| Cost: ",
                                        magics[i].getCost(), "| Hit Chance: ",
                                        (double) magics[i].getChance() / 10, "| Buff Type: ",
                                        magics[i].getBufftype());
                                break;
                        }
                    }
                    Util.pressAnyKey();
                    break;
            }
            Util.pressAnyKey();
            Util.clearScreen();
        }
    }
}
