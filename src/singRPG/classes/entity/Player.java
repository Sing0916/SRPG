package singRPG.classes.entity;

public class Player extends Unit {
    double mp = 50;
    double maxMp = 50;
    double baseMp = 50;

    public Player(String n, boolean isPlayer, double EXP) {
        super(n, isPlayer, EXP);
        mp = 50 + 5 * level;
        maxMp = mp;
        baseMp = mp;
    }

    public Player() {
        super();
    }

    public void useMagic(double c) {
        mp -= c;
    }

    public double getMp() {
        return this.mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public double getMaxMp() {
        return this.maxMp;
    }

    public void setMaxMp(double maxMp) {
        this.maxMp = maxMp;
    }

    public double getBaseMp() {
        return this.baseMp;
    }

    public void setBaseMp(double baseMp) {
        this.baseMp = baseMp;
    }
}