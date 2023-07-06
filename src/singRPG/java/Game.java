package singRPG.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import singRPG.classes.Magic;
import singRPG.classes.entity.Player;
import singRPG.classes.entity.Unit;
import singRPG.constant.Colours;
import singRPG.constant.enums.DmgType;
import singRPG.system.MagicSystem;
import singRPG.system.Util;

public class Game {
    static Player player = new Player();
    static Unit enemy = new Unit();
    static Magic magics[];
    static Scanner scan = new Scanner(System.in);
    static int userAction = -1;

    // game initialize
    public Game(Player p, Unit e) throws FileNotFoundException, IOException, ParseException {
        player = p;
        enemy = e;
        magics = MagicSystem.readMagic();
    }

    // main game logic
    public boolean start() {
        // round counter
        int counter = 1;

        // main user action loop
        userLoop: while (true) {
            System.out
                    .println(Colours.ANSI_YELLOW + "------Round " + counter + " Start------" + Colours.ANSI_RESET);
            showDetail(enemy);
            showDetail(player);
            System.out.println("[1]: Attack");
            System.out.println("[2]: Magic");
            System.out.println("[3]: Defence");

            // user action
            userAction = Util.checkUserAction(1, 3);
            boolean back = playerAction(userAction, player, enemy);
            if (!back) {
                Util.clearScreen();
                continue userLoop;
            }
            if ((enemy.getHp() <= 0)) {
                System.out.println("Enemy is dead!");
                player.setExp(player.getExp() + (player.getLevel() - enemy.getLevel() + 1) * 15);
                return true;
            }

            // enemy action
            int r = (int) (Math.random() * 4);
            enemyAction(r, enemy, player);
            if ((player.getHp() <= 0)) {
                System.out.println("You are dead!");
                return false;
            }

            // round end
            counter++;
            Util.pressAnyKey();
            Util.clearScreen();
            if (player.getMp() < 50)
                player.setMp(player.getMp() + 5);
        }
    }

    public static boolean playerAction(int input, Player from, Unit to) {
        double tmp;
        switch (input) {
            case 1:
                // normal attack
                Util.clearScreen();
                Util.printLine();
                tmp = to.takeDMG(from.getAtk(), DmgType.PHY);
                System.out.println(from.getName() + " used Attack!");
                if (tmp > 0)
                    System.out.println(to.getName() + " takes " + (int) tmp + " damage!");
                else
                    System.out.println(to.getName() + " takes 0 damage!");
                System.out.println(to.getName() + " current HP is " + (int) to.getHp());
                break;
            case 2:
                // magic
                Util.clearLine(4);
                int menuCount = 0;
                int userAction = -1;
                int okMagic = 0;
                System.out.println("[0]: Back");
                System.out.println("[1]: Up");
                menuLoop: while (true) {
                    for (int i = 0; (i < (3 - ((menuCount + 1) * 3 - magics.length))) && i < 3; i++) {
                        showMagic(menuCount, i);
                    }
                    if ((3 - ((menuCount + 1) * 3 - magics.length)) < 0)
                        okMagic = 0;
                    else if ((3 - ((menuCount + 1) * 3 - magics.length)) > 3)
                        okMagic = 3;
                    else
                        okMagic = 3 - ((menuCount + 1) * 3 - magics.length);
                    for (int i = 0; (i < ((menuCount + 1) * 3 - magics.length)) && i < 3; i++) {
                        System.out
                                .println(
                                        "[" + (i + 2) + "]: " + Colours.ANSI_RED + "--------------------"
                                                + Colours.ANSI_RESET);
                    }
                    System.out.println("[5]: Down");
                    userAction = Util.checkUserAction(0, okMagic + 1, 5, 898);
                    if (userAction == 0) {
                        return false;
                    } else if (userAction == 1) {
                        menuCount--;
                        Util.clearLine(5);
                    } else if (userAction == 5) {
                        menuCount++;
                        Util.clearLine(5);
                    } else {
                        break menuLoop;
                    }
                }
                Util.clearScreen();
                Util.printLine();
                if (userAction == 898) {
                    System.out.println(from.getName() + " used cheats!");
                    tmp = to.takeDMG(to.getHp(), DmgType.TRE);
                    System.out.println(to.getName() + " takes " + (int) tmp + " damage!");
                } else {
                    System.out.println(
                            from.getName() + " used " + magics[menuCount * 3 + userAction - 2].getName() + "!");
                    switch (magics[menuCount * 3 + userAction - 2].getMagicType()) {
                        case DMG:
                            int r = (int) (Math.random() * 10);
                            if (r <= magics[menuCount * 3 + userAction - 2].getChance()) {
                                tmp = to.takeDMG(magics[menuCount * 3 + userAction - 2].getAmount(), DmgType.MAG);
                                from.useMagic(magics[menuCount * 3 + userAction - 2].getCost());
                            } else
                                tmp = 0;
                            if (tmp > 0)
                                System.out.println(to.getName() + " takes " + (int) tmp + " damage!");
                            else
                                System.out.println(from.getName() + "'s Magic missed!");
                            System.out.println(to.getName() + " current HP is " + (int) to.getHp());
                            break;
                        case HEAL:
                            double amt = from.heal(magics[menuCount * 3 + userAction - 2].getAmount());
                            System.out
                                    .println(from.getName() + " healed for " + (int) amt + " HP!");
                            System.out.println(from.getName() + " current HP is " + (int) to.getHp());
                            break;
                        case BUFF:
                            from.buff(magics[menuCount * 3 + userAction - 2].getAmount(),
                                    magics[menuCount * 3 + userAction - 2].getBufftype());
                            break;
                    }
                }
                break;
            case 3:
                // defence
                Util.clearScreen();
                Util.printLine();
                from.shield();
                System.out.println(from.getName() + " used Defence!");
                System.out.println(from.getName() + " shielded himself");
                break;
        }
        Util.printLine();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void enemyAction(int input, Unit from, Player to) {
        double tmp;
        switch (input) {
            case 0:
                // normal attack
                tmp = to.takeDMG(from.getAtk(), DmgType.PHY);
                System.out.println(from.getName() + " used Attack!");
                if (tmp > 0)
                    System.out.println(to.getName() + " takes " + (int) tmp + " damage!");
                else
                    System.out.println(to.getName() + " takes 0 damage!");
                System.out.println(to.getName() + " current HP is " + (int) to.getHp());
                break;
            case 1:
                // defence
                tmp = from.defUP();
                System.out.println(from.getName() + " used Defence!");
                System.out.println(from.getName() + "'s defence changed to " + (int) tmp);
                break;
            case 2:
                // power up
                tmp = from.atkUp();
                System.out.println(from.getName() + " used Power Up!");
                System.out.println(from.getName() + "'s attack changed to " + (int) tmp);
                break;
            case 3:
                int r = (int) (Math.random() * 5);
                if (r < 2) {
                    tmp = from.heal((int) (Math.random() * 21));
                    System.out
                            .println(from.getName() + " healed for " + (int) tmp + " HP!");
                    System.out.println(from.getName() + " current HP is " + (int) to.getHp());
                } else {
                    System.out.println(from.getName() + " did nothing!");
                }
                break;
        }
        Util.printLine();
    }

    public static void showMagic(int menuCount, int i) {
        String format = "%s%s%s%-20s%s%-8s%s%-7s%s%s\n";
        if (menuCount < 0) {
            menuCount = 0;
        }
        switch (magics[menuCount * 3 + i].getMagicType()) {
            case BUFF:
                format = "%s%s%s%-20s%s%-8s%s%-7s%s%-7s%s%s\n";
                System.out.printf(format,
                        "[", (i + 2), "]: ", magics[menuCount * 3 + i].getName(), "Amount: ",
                        magics[menuCount * 3 + i].getAmount(), "Cost: ",
                        magics[menuCount * 3 + i].getCost(), "Hit Chance: ",
                        (double) magics[menuCount * 3 + i].getChance() / 10, "Buff Type: ",
                        magics[menuCount * 3 + i].getBufftype());
                break;
            case DMG:
                System.out.printf(format,
                        "[", (i + 2), "]: ", magics[menuCount * 3 + i].getName(), "Damage: ",
                        magics[menuCount * 3 + i].getAmount(), "Cost: ",
                        magics[menuCount * 3 + i].getCost(), "Hit Chance: ",
                        (double) magics[menuCount * 3 + i].getChance() / 10);
                break;
            case HEAL:
                System.out.printf(format,
                        "[", (i + 2), "]: ", magics[menuCount * 3 + i].getName(), "Amount: ",
                        magics[menuCount * 3 + i].getAmount(), "Cost: ",
                        magics[menuCount * 3 + i].getCost(), "Hit Chance: ",
                        (double) magics[menuCount * 3 + i].getChance() / 10);
                break;
        }
    }

    // show stat
    public static void showDetail(Unit u) {
        String format = "%s%s%-3s%s%s%-10s%s%-3s%s%-3s\n";
        System.out.printf(format, Colours.ANSI_PURPLE, "Lv.", (int) u.getLevel(), Colours.ANSI_RESET, " ", u.getName(),
                "| ATK:", (int) u.getAtk(), " DEF:", (int) u.getDef());
        int p = (int) Math.floor((u.getHp() / u.getMaxHp()) * 20);
        format = "%s%3s%s%s%s%s";
        System.out.printf(format, "HP: ", (int) u.getHp(), "/", (int) u.getMaxHp(), Colours.ANSI_YELLOW, " [");
        for (int i = 0; i < 20; i++) {
            if (i < p)
                System.out.print(Colours.ANSI_GREEN + "=" + Colours.ANSI_RESET);
            else {
                if ((p == 0) && (i == 0))
                    System.out.print(Colours.ANSI_GREEN + "|" + Colours.ANSI_RESET);
                else
                    System.out.print(Colours.ANSI_RED + "-" + Colours.ANSI_RESET);
            }
        }
        System.out.print(Colours.ANSI_YELLOW + "]");
        System.out.println("");
        Util.printLine();
    }

    // show stat
    public static void showDetail(Player u) {
        String format = "%s%s%-3s%s%s%-10s%s%-3s%s%-3s\n";
        System.out.printf(format, Colours.ANSI_PURPLE, "Lv.", (int) u.getLevel(), Colours.ANSI_RESET, " ", u.getName(),
                "| ATK:", (int) u.getAtk(), " DEF:", (int) u.getDef());
        int p = (int) Math.floor((u.getHp() / u.getMaxHp()) * 20);
        format = "%s%3s%s%s%s%s";
        System.out.printf(format, "HP: ", (int) u.getHp(), "/", (int) u.getMaxHp(), Colours.ANSI_YELLOW, " [");
        for (int i = 0; i < 20; i++) {
            if (i < p)
                System.out.print(Colours.ANSI_GREEN + "=" + Colours.ANSI_RESET);
            else
                System.out.print(Colours.ANSI_RED + "-" + Colours.ANSI_RESET);
        }
        p = (int) Math.floor((u.getMp() / u.getMaxMp()) * 20);
        System.out.print(Colours.ANSI_YELLOW + "]" + Colours.ANSI_RESET + "  MP: " + (int) u.getMp() + "/"
                + (int) u.getMaxMp() + Colours.ANSI_YELLOW + " [");
        for (int i = 0; i < 20; i++) {
            if (i < p)
                System.out.print(Colours.ANSI_CYAN + "=" + Colours.ANSI_RESET);
            else {
                if ((p == 0) && (i == 0))
                    System.out.print(Colours.ANSI_GREEN + "|" + Colours.ANSI_RESET);
                else
                    System.out.print(Colours.ANSI_RED + "-" + Colours.ANSI_RESET);
            }
        }
        System.out.print(Colours.ANSI_YELLOW + "]");
        System.out.println("");
        Util.printLine();
    }
}