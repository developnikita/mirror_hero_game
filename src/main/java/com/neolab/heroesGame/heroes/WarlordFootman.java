package com.neolab.heroesGame.heroes;

public class WarlordFootman extends Footman implements IWarlord{

    public WarlordFootman(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    public float getImproveCoefficient() { return 0.1f; }
}
