package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public abstract class Hero {
    private final int hpDefault;
    private int hpMax;
    private int hp;
    private final int damageDefault;
    private int damage;
    private final float precision;
    private float armor;
    private final float armorDefault;
    private final int armyId;
    private boolean defence = false;

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public int getDamageDefault() {
        return damageDefault;
    }

    public float getArmorDefault() {
        return armorDefault;
    }

    public Hero(int hp, int damage, float precision, float armor, int armyId) {
        this.hpDefault = hp;
        this.hpMax = hp;
        this.hp = hp;
        this.damage = damage;
        this.damageDefault = damage;
        this.precision = precision;
        this.armor = armor;
        this.armorDefault = armor;
        this.armyId = armyId;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public float getPrecision() {
        return precision;
    }

    public float getArmor() {
        return armor;
    }

    public int getArmyId() { return armyId; }

    public boolean isDefence() {
        return defence;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpDefault() {
        return hpDefault;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public void setDefence(boolean defence) {
        this.defence = defence;
    }

    /**
     * Если герой погибает удаляем его из обеих коллекций
     * @param position позиция героя
     * @param army армия противника
     * @throws HeroExceptions
     */
    public void toAttack(SquareCoordinate position, Army army) throws HeroExceptions {
        Hero targetAttack = searchTarget(position, army);

        if(isHit(this.getPrecision())){
            targetAttack.setHp(targetAttack.getHp() - calculateDamage(targetAttack));
            removeTarget(targetAttack, position, army);
        }
    }

    protected Hero searchTarget(SquareCoordinate position, Army army) throws HeroExceptions {
        return Optional.of(army.getHeroes().get(position)).orElseThrow(
                new HeroExceptions(HeroErrorCode.ERROR_TARGET_ATTACK)
        );
    }

    protected void removeTarget(Hero targetAttack, SquareCoordinate position, Army army){
        if(targetAttack.getHp() <= 0){
            army.killHero(targetAttack);
        }
    }

    protected int calculateDamage(Hero targetAttack){
        return Math.round(this.getDamage() - targetAttack.getArmor() * this.getDamage());
    }

    /**
     * подбрасываем монетку
     * @param precision точность
     * @return boolean
     */
    protected boolean isHit(float precision){
        Random rnd = new Random();
        float number = rnd.nextFloat();
        return precision > number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return getHpMax() == hero.getHpMax() &&
                getHp() == hero.getHp() &&
                getDamage() == hero.getDamage() &&
                Float.compare(hero.getPrecision(), getPrecision()) == 0 &&
                Float.compare(hero.getArmor(), getArmor()) == 0 &&
                getArmyId() == hero.getArmyId() &&
                isDefence() == hero.isDefence();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHpMax(), getHp(), getDamage(), getPrecision(), getArmor(), getArmyId(), isDefence());
    }
}
