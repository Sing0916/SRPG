package singRPG.system;

import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import singRPG.constant.Colours;

public class Util {
    static Scanner scan = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearLine(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("\033[F");
            System.out.flush();
        }
        System.out.print("\033[0J");
    }

    public static void printLine() {
        System.out.println(Colours.ANSI_YELLOW + "-------------------------" + Colours.ANSI_RESET);
    }

    public static int random(int exclusiveUpperBound) {
        Random rand = new Random();
        return rand.nextInt(exclusiveUpperBound);
    }

    public static void pressAnyKey() {
        System.out.println("Press enter to continue...");
        try {
            scan.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int checkUserAction(int start, int end) {
        int userAction = -1;
        boolean firstAction = true;
        String input;
        while (true) {
            input = scan.nextLine();
            if (StringUtils.isNumeric(input) && !StringUtils.equals(input, "")) {
                userAction = Integer.parseInt(input);
                if ((userAction >= start) && (userAction <= end)) {
                    if (!firstAction)
                        clearLine(1);
                    break;
                } else {
                    if (firstAction) {
                        clearLine(1);
                        firstAction = false;
                    } else
                        clearLine(2);
                    System.out
                            .println("Invalid input! Please enter a number between [" + start + "] to [" + end + "]!");
                }
            } else {
                if (firstAction) {
                    clearLine(1);
                    firstAction = false;
                } else
                    clearLine(2);
                System.out.println("Invalid input! Please enter a number between [" + start + "] to [" + end + "]!");
            }
        }
        return userAction;
    }

    public static int checkUserAction(int start, int end, int opt1, int opt2) {
        int userAction = -1;
        boolean firstAction = true;
        String input;
        while (true) {
            input = scan.nextLine();
            if (StringUtils.isNumeric(input) && !StringUtils.equals(input, "")) {
                userAction = Integer.parseInt(input);
                if (((userAction >= start) && (userAction <= end)) || (userAction == opt1) || (userAction == opt2)) {
                    if (!firstAction)
                        clearLine(1);
                    break;
                } else {
                    if (firstAction) {
                        clearLine(1);
                        firstAction = false;
                    } else
                        clearLine(2);
                    if (end + 1 == opt1)
                        System.out.println(
                                "Invalid input! Please enter a number between [" + start + "] to [" + opt1 + "]!");
                    else
                        System.out.println("Invalid input! Please enter a number between [" + start + "] to [" + end
                                + "] or [" + opt1 + "]!");
                }
            } else {
                if (firstAction) {
                    clearLine(1);
                    firstAction = false;
                } else
                    clearLine(2);
                if (end + 1 == opt1)
                    System.out.println(
                            "Invalid input! Please enter a number between [" + start + "] to [" + opt1 + "]!");
                else
                    System.out.println(
                            "Invalid input! Please enter a number between [" + start + "] to [" + end + "] or [" + opt1
                                    + "]!");
            }
        }
        return userAction;
    }
}
