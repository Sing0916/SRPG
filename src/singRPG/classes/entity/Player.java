package singRPG.classes.entity;

public class Player extends Unit {
    double mp = 50;
    double maxMp = 50;
    double baseMp = 50;

    public Player(double health, double attack, double Defence, double mattack, double mdefence, double mp, String n,
            boolean isPlayer, double EXP) {
        super(health, attack, Defence, mattack, mdefence, n, isPlayer, EXP);
        baseMp = mp;
        maxMp = mp + 5 * Math.floor(EXP / 100);
        mp = maxMp;
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