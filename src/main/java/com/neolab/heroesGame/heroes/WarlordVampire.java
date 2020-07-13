package com.neolab.heroesGame.heroes;

public class WarlordVampire extends Magician implements IWarlord {

    public WarlordVampire(int hp, int damage, float precision, float armor, int unitId) {
        super(hp, damage, precision, armor, unitId);
    }

    public float getImproveCoefficient() {
        return 0.05f;
    }

    @Override
    public int getUnitId() {
        return super.getUnitId();
    }

}
