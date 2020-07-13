package com.neolab.heroesGame.heroes;

public class WarlordFootman extends Footman implements IWarlord {

    public WarlordFootman(int hp, int damage, float precision, float armor, int unitId) {
        super(hp, damage, precision, armor, unitId);
    }

    public float getImproveCoefficient() {
        return 0.1f;
    }

    @Override
    public int getUnitId() {
        return super.getUnitId();
    }
}
