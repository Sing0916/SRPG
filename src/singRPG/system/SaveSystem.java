package singRPG.system;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import singRPG.classes.entity.Player;

public class SaveSystem {
    static Scanner scan = new Scanner(System.in);
    static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static String[] getPlayerList() throws FileNotFoundException, IOException {
        FileReader file = new FileReader("save/players.json");
        Player[] players = gson.fromJson(file, Player[].class);
        String names[] = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    public static void write(Player[] p) {
        FileWriter file;
        try {
            file = new FileWriter("save/players.json");
            gson.toJson(p, Player[].class, file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Player[] read()
            throws org.json.simple.parser.ParseException, FileNotFoundException,
            IOException {
        FileReader fileRead = new FileReader("save/players.json");
        Player[] players = gson.fromJson(fileRead, Player[].class);
        return players;
    }

    public static int create() throws FileNotFoundException, IOException,
            ParseException {
        FileReader fileRead = new FileReader("save/players.json");
        Player[] players = gson.fromJson(fileRead, Player[].class);
        players = Arrays.copyOf(players, players.length + 1);

        Util.clearScreen();
        System.out.println("Enter 0 to escape to menu.");
        System.out.print("Name: ");
        String name = scan.nextLine();
        System.out.print(gson.toJson(players, Player[].class));
        if (StringUtils.equals(name, "0"))
            return 0;
        players[players.length - 1] = new Player(100.0, 10.0, 10.0, 20.0, 10.0, 100.0, name, true, 100);
        System.out.print(gson.toJson(players, Player[].class));
        try {
            FileWriter file = new FileWriter("save/players.json");
            gson.toJson(players, Player[].class, file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players.length;
    }
}