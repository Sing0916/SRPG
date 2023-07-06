package singRPG.system;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import singRPG.classes.Magic;
import singRPG.constant.enums.BuffType;
import singRPG.constant.enums.MagicType;

public class MagicSystem {
    static Scanner scan = new Scanner(System.in);
    static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static Magic[] readMagic() throws FileNotFoundException, IOException,
            ParseException {
        FileReader file = new FileReader("config/magic.json");
        Magic[] magics = gson.fromJson(file, Magic[].class);
        return magics;
    }

    public static void createMagic() throws FileNotFoundException, IOException,
            ParseException {
        FileReader fileRead = new FileReader("config/magic.json");
        Magic[] magics = gson.fromJson(fileRead, Magic[].class);
        magics = Arrays.copyOf(magics, magics.length + 1);

        System.out.print("Magic Type(DMG,BUFF,HEAL): ");
        String mtypeS = scan.nextLine();
        System.out.print("Buff Type(ATK,DEF,MATK,MDEF,HP)(NULL): ");
        String btypeS = scan.nextLine();
        System.out.print("Name: ");
        String name = scan.nextLine();
        System.out.print("Amount: ");
        double amt = scan.nextDouble();
        System.out.print("Cost: ");
        double cost = scan.nextDouble();
        System.out.print("Chance(0-9): ");
        int chance = scan.nextInt();
        MagicType mtype = MagicType.valueOf(mtypeS);
        if (btypeS.equals("NULL")) {
            magics[magics.length - 1] = new Magic(mtype, BuffType.NULL, cost, amt, name, chance);
        } else {
            BuffType btype = BuffType.valueOf(btypeS);
            magics[magics.length - 1] = new Magic(mtype, btype, cost, amt, name, chance);
        }
        FileWriter fileWrite = new FileWriter("config/magic.json");
        gson.toJson(magics, Magic[].class, fileWrite);
        fileWrite.flush();
    }

    public void write(Magic[] magics) throws IOException {
        FileWriter file = new FileWriter("config/magic.json");
        gson.toJson(magics, Magic[].class, file);
        file.flush();
    }
}
