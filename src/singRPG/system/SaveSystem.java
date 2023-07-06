package singRPG.system;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import singRPG.classes.entity.Player;

public class SaveSystem {
    static Scanner scan = new Scanner(System.in);
    static String array[] = new String[3];// 0:game name, 1:version, 2:player count
    static int playerNum = 0;

    // current version = 1.2
    public static String[] readConfig()
            throws org.json.simple.parser.ParseException, FileNotFoundException, IOException {
        Object obj = new JSONParser().parse(new FileReader("config/config.json"));
        JSONObject jo = (JSONObject) obj;
        array[0] = (String) jo.get("Name");
        array[1] = (String) jo.get("Version");
        playerNum = Integer.parseInt((String) jo.get("Player"));
        array[2] = Integer.toString(playerNum);
        return array;
    }

    public static void convertGson() {

    }

    public static String[] getPlayerList() throws FileNotFoundException, IOException, ParseException {
        String names[] = new String[playerNum];
        for (int i = 0; i < playerNum; i++) {
            Object obj = new JSONParser().parse(new FileReader("save/player" + (i + 1) + ".json"));
            JSONObject jo = (JSONObject) obj;
            String name = (String) jo.get("Name");
            names[i] = name;
        }
        return names;
    }

    public static void write(Player p, int user) {
        HashMap<String, Object> playerDetails = new HashMap<String, Object>();
        playerDetails.put("Name", p.getNAME());
        playerDetails.put("MaxHp", p.getBaseHP());
        playerDetails.put("MaxMp", p.getBaseMP());
        playerDetails.put("Atk", p.getOATK());
        playerDetails.put("Def", p.getODEF());
        playerDetails.put("MAtk", p.getOMATK());
        playerDetails.put("MDef", p.getOMDEF());
        playerDetails.put("Exp", p.getEXP());
        playerDetails.put("Level", p.getLevel());
        playerDetails.put("version", array[1]);

        JSONObject playerDetailsJ = new JSONObject(playerDetails);

        try (FileWriter file = new FileWriter("save/player" + user + ".json")) {
            file.write(playerDetailsJ.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Player read(int user)
            throws org.json.simple.parser.ParseException, FileNotFoundException, IOException {
        Object obj = new JSONParser().parse(new FileReader("save/player" + user + ".json"));
        JSONObject jo = (JSONObject) obj;
        String name = (String) jo.get("Name");
        double maxHP = (double) jo.get("MaxHp");
        double maxMP = (double) jo.get("MaxMp");
        double atk = (double) jo.get("Atk");
        double def = (double) jo.get("Def");
        double matk = (double) jo.get("MAtk");
        double mdef = (double) jo.get("MDef");
        double exp = (double) jo.get("Exp");
        Player p = new Player(maxHP, atk, def, matk, mdef, maxMP, name, true, exp);
        return p;
    }

    public static void update() throws org.json.simple.parser.ParseException, FileNotFoundException, IOException {
        for (int i = 1; i <= playerNum; i++) {
            Object obj = new JSONParser().parse(new FileReader("save/player" + i + ".json"));
            JSONObject jo = (JSONObject) obj;
            String version = (String) jo.get("version");
            if (Double.parseDouble(version) < Double.parseDouble(array[1])) {
                // switch to add field to save
                // break only in the last case
                // add version case in asce order
                switch (version) {
                    case "1.2":
                        jo.put("Level", 0);
                        break;
                }
                jo.replace("version", array[1]);
                try (FileWriter file = new FileWriter("save/player" + i + ".json")) {
                    file.write(jo.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int create() throws FileNotFoundException, IOException, ParseException {
        Util.clearScreen();
        System.out.println("Enter 0 to escape to menu.");
        System.out.print("Name: ");
        String name = scan.nextLine();
        if (Integer.parseInt(name) == 0)
            return 0;
        HashMap<String, Object> playerDetails = new HashMap<String, Object>();
        playerDetails.put("Name", name);
        playerDetails.put("MaxHp", 100.0);
        playerDetails.put("MaxMp", 10.0);
        playerDetails.put("Atk", 10.0);
        playerDetails.put("Def", 10.0);
        playerDetails.put("MAtk", 20.0);
        playerDetails.put("MDef", 10.0);
        playerDetails.put("Exp", 100.0);
        playerDetails.put("Level", 0.0);
        playerDetails.put("version", array[1]);
        int temp = Integer.parseInt(array[2]) + 1;

        JSONObject playerDetailsJ = new JSONObject(playerDetails);
        try (FileWriter file = new FileWriter("save/player" + temp + ".json")) {
            file.write(playerDetailsJ.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object obj = new JSONParser().parse(new FileReader("config/config.json"));
        JSONObject jo = (JSONObject) obj;
        array[0] = (String) jo.get("Name");
        array[1] = (String) jo.get("Version");
        array[2] = (String) jo.get("Player");

        HashMap<String, Object> config = new HashMap<String, Object>();
        config.put("Name", array[0]);
        config.put("Version", array[1]);
        config.put("Player", Integer.toString(Integer.parseInt(array[2]) + 1));
        JSONObject configJ = new JSONObject(config);

        try (FileWriter file = new FileWriter("config/config.json")) {
            file.write(configJ.toJSONString());
            file.flush();
            System.out.println("Success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
}