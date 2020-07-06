package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.Objects;
import java.util.Optional;

public abstract class Hero {
    private int hp;
    private int damage;
    private final float precision;
    private float armor;
    private final int armyId;
    private boolean defence = false;

    public Hero(int hp, int damage, float precision, float armor, int armyId) {
        this.hp = hp;
        this.damage = damage;
        this.precision = precision;
        this.armor = armor;
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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public void setDefence(boolean defence) {
        this.defence = defence;
    }

    abstract void toAttack(SquareCoordinate position, Army army);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return getHp() == hero.getHp() &&
                getDamage() == hero.getDamage() &&
                Float.compare(hero.getPrecision(), getPrecision()) == 0 &&
                Float.compare(hero.getArmor(), getArmor()) == 0 &&
                getArmyId() == hero.getArmyId() &&
                isDefence() == hero.isDefence();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHp(), getDamage(), getPrecision(), getArmor(), getArmyId(), isDefence());
    }
}
