package singRPG.java;

import java.util.Scanner;
import singRPG.entity.Unit;
import singRPG.constant.Colours;

public class Game {
    Unit player = new Unit();
    Unit enemy = new Unit();

    public Game(Unit p, Unit e) {
        player = p;
        enemy = e;
    }

    public boolean start() {
        try (Scanner scan = new Scanner(System.in)) {
            boolean userActionValid = false;
            int counter = 1;

            while (true) {
                System.out.println("------Round " + counter + " Start------");
                System.out.println("Enemy: (ATK:" + (int) enemy.getATK() + ", DEF:" + (int) enemy.getDEF() + ")");
                showHP(enemy.getHP(), enemy.getMaxHP());
                System.out.println("--------------------");
                System.out.println("Player: (ATK:" + (int) player.getATK() + ", DEF:" + (int) player.getDEF() + ")");
                showHP(player.getHP(), player.getMaxHP());
                System.out.println("--------------------");
                System.out.println("[0]: Attack");
                System.out.println("[1]: Defence");
                System.out.println("[2]: Power Up");

                // user action
                int userAction = -1;
                while (!userActionValid) {
                    userAction = scan.nextInt();
                    System.out.print("\033[1F\33[K");
                    System.out.flush();
                    if ((userAction >= 0) && (userAction <= 2)) {
                        userActionValid = true;
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("--------------------");
                    } else {
                        System.out.print("\033[1F\33[K");
                        System.out.flush();
                        System.out.println("Invalid input!");
                    }
                }
                action(userAction, player, enemy);
                if ((enemy.getHP() <= 0)) {
                    System.out.println("Enemy is dead!");
                    break;
                }

                // enemy action
                int r = (int) (Math.random() * 3);
                action(r, enemy, player);
                if ((player.getHP() <= 0)) {
                    System.out.println("You are dead!");
                    break;
                }

                userActionValid = false;
                userAction = -1;
                counter++;
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
            return enemy.getHP() == 0 ? true : false;
        }
    }

    public static void action(int input, Unit from, Unit to) {
        double tmp;
        switch (input) {
            case 0:
                tmp = to.takeDMG(from.getATK());
                System.out.println(from.getNAME() + " used Attack!");
                if (tmp > 0)
                    System.out.println(to.getNAME() + " takes " + (int) tmp + " damage!");
                else
                    System.out.println(to.getNAME() + " takes 0 damage!");
                System.out.println(to.getNAME() + " current HP is " + to.getHP());
                break;
            case 1:
                tmp = from.DEFUP();
                System.out.println(from.getNAME() + " used Defence!");
                System.out.println(from.getNAME() + "'s defence changed to " + (int) tmp);
                break;
            case 2:
                tmp = from.pwrUp();
                System.out.println(from.getNAME() + " used Power Up!");
                System.out.println(from.getNAME() + "'s attack changed to " + (int) tmp);
                break;
        }
        System.out.println("--------------------");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void showHP(double HP, double maxHP) {
        int p = (int) Math.floor((HP / maxHP) * 10);
        System.out.print("HP: " + (int) HP + "/" + (int) maxHP + " [");
        for (int i = 0; i < 10; i++) {
            if (i < p)
                System.out.print(Colours.ANSI_GREEN + "*" + Colours.ANSI_RESET);
            else
                System.out.print(Colours.ANSI_RED + "=" + Colours.ANSI_RESET);
        }
        System.out.print("]");
        System.out.println();
    }
}
