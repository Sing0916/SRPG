package singRPG.classes;

import singRPG.constant.enums.BuffType;
import singRPG.constant.enums.MagicType;

public class Magic {
    MagicType magicType;
    BuffType bufftype;
    double cost;
    double amount;
    String name;
    int chance;// 0-9

    public Magic() {
    }

    public Magic(MagicType magicType, BuffType bufftype, double cost, double amount, String name, int chance) {
        this.magicType = magicType;
        this.bufftype = bufftype;
        this.cost = cost;
        this.amount = amount;
        this.name = name;
        this.chance = chance;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public void setMagicType(MagicType magicType) {
        this.magicType = magicType;
    }

    public BuffType getBufftype() {
        return this.bufftype;
    }

    public void setBufftype(BuffType bufftype) {
        this.bufftype = bufftype;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}