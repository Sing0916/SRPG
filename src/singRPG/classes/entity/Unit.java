package singRPG.classes.entity;

import singRPG.constant.enums.BuffType;
import singRPG.constant.enums.DmgType;

public class Unit {
    double hp = 1;
    double atk = 1;
    double def = 1;
    double matk = 1;
    double mdef = 1;

    double maxHp = 1;
    double oAtk = 1;
    double oDef = 1;
    double oMatk = 1;
    double oMdef = 1;

    double baseHp = 1;
    double exp = 0;
    double level = 0;

    String name = "empty unit";
    boolean isEnemy = true;
    boolean shielded = false;

    public Unit() {
    }

    public Unit(double health, double attack, double Defence, double mattack, double mdefence, String n,
            boolean isPlayer, double EXP) {
        if (EXP < 100)
            EXP = 100;
        maxHp = health + 10 * Math.floor(EXP / 100);
        oAtk = attack;
        oDef = Defence;
        oMatk = mattack;
        oMdef = mdefence;

        hp = maxHp;
        atk = oAtk + 1 * Math.floor(EXP / 100);
        def = oDef + 1 * Math.floor(EXP / 100);
        matk = oMatk + 1 * Math.floor(EXP / 100);
        mdef = oMdef + 1 * Math.floor(EXP / 100);

        if (EXP <= 0)
            exp = 0;
        else
            exp = EXP;
        level = Math.floor(exp / 100);
        baseHp = health;
        name = n;
        if (isPlayer)
            isEnemy = false;
    }

    public void updateLV() {
        double tmp = level;
        level = Math.floor(exp / 100);
        if (tmp != level) {
            maxHp = baseHp + 10 * level;
            hp = maxHp;
            System.out.println("Level up! " + tmp + " -> " + level);
        }
    }

    public double takeDMG(double dmg, DmgType type) {
        double dmgDeal = 0;
        if (shielded) {
            shielded = false;
            return dmgDeal;
        } else {
            switch (type) {
                case PHY:
                    dmgDeal = dmg - def;
                    if (dmgDeal > 0)
                        if (dmgDeal < hp)
                            hp -= dmgDeal;
                        else {
                            dmgDeal = hp;
                            hp = 0;
                        }
                    break;
                case MAG:
                    dmgDeal = dmg - mdef;
                    if (dmgDeal > 0)
                        if (dmgDeal < hp)
                            hp -= dmgDeal;
                        else {
                            dmgDeal = hp;
                            hp = 0;
                        }
                    break;
                case TRE:
                    dmgDeal = dmg;
                    hp -= dmgDeal;
                    break;
            }
            return dmgDeal;
        }
    }

    public double heal(double h) {
        double healAMT = 0;
        if ((maxHp - hp) >= h) {
            healAMT = h;
            hp += healAMT;
        } else {
            healAMT = maxHp - hp;
            hp += healAMT;
        }
        return healAMT;
    }

    public double atkUp() {
        atk += 5;
        return atk;
    }

    public double defUP() {
        def += 5;
        return def;
    }

    public void buff(double a, BuffType t) {
        switch (t) {
            case ATK:
                atk += a;
                break;
            case DEF:
                def += a;
                break;
            case MATK:
                matk += a;
                break;
            case MDEF:
                mdef += a;
                break;
            case HP:
                hp += a;
                break;
            case NULL:
                break;
        }
    }

    public void shield() {
        shielded = true;
    }

    public double getHp() {
        return this.hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getAtk() {
        return this.atk;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public double getDef() {
        return this.def;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public double getMatk() {
        return this.matk;
    }

    public void setMatk(double matk) {
        this.matk = matk;
    }

    public double getMdef() {
        return this.mdef;
    }

    public void setMdef(double mdef) {
        this.mdef = mdef;
    }

    public double getMaxHp() {
        return this.maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public double getOAtk() {
        return this.oAtk;
    }

    public void setOAtk(double oAtk) {
        this.oAtk = oAtk;
    }

    public double getODef() {
        return this.oDef;
    }

    public void setODef(double oDef) {
        this.oDef = oDef;
    }

    public double getOMatk() {
        return this.oMatk;
    }

    public void setOMatk(double oMatk) {
        this.oMatk = oMatk;
    }

    public double getOMdef() {
        return this.oMdef;
    }

    public void setOMdef(double oMdef) {
        this.oMdef = oMdef;
    }

    public double getBaseHp() {
        return this.baseHp;
    }

    public void setBaseHp(double baseHp) {
        this.baseHp = baseHp;
    }

    public double getExp() {
        return this.exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getLevel() {
        return this.level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsEnemy() {
        return this.isEnemy;
    }

    public boolean getIsEnemy() {
        return this.isEnemy;
    }

    public void setIsEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public boolean isShielded() {
        return this.shielded;
    }

    public boolean getShielded() {
        return this.shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

}
