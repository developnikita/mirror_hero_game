package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.IWarlord;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Army {
    private final Map<SquareCoordinate, Hero> heroes;
    private IWarlord warlord;
    private Map<SquareCoordinate, Hero> availableHero;

    public Army(Map<SquareCoordinate, Hero> heroes) throws HeroExceptions {
        this.heroes = heroes;
        this.warlord = findWarlord();
        setAvailableHeroes();
        improveAllies();
    }

    private IWarlord findWarlord() throws HeroExceptions {
        IWarlord iWarlord = null;
        for (SquareCoordinate coordinate : heroes.keySet()) {
            if (heroes.get(coordinate) instanceof IWarlord) {
                if (warlord != null) {
                    throw new HeroExceptions(HeroErrorCode.ERROR_SECOND_WARLORD_ON_ARMY);
                }
                iWarlord = (IWarlord) heroes.get(coordinate);
            }
        }
        if (iWarlord == null) {
            throw new HeroExceptions(HeroErrorCode.ERROR_EMPTY_WARLORD);
        }
        return iWarlord;
    }

    public Map<SquareCoordinate, Hero> getHeroes() {
        return heroes;
    }

    public Map<SquareCoordinate, Hero> getAvailableHero() {
        return availableHero;
    }

    public Optional<Hero> getHero(SquareCoordinate coord) {
        return Optional.ofNullable(heroes.get(coord));
    }

    public void setAvailableHeroes() {
        this.availableHero = new HashMap<>(heroes);
    }

    public void killHero(Hero hero) {
        removeAvailableHero(hero);
        heroes.values().removeIf(value -> value.equals(hero));
    }

    public void setWarlord(IWarlord warlord) {
        this.warlord = warlord;
    }

    public void removeAvailableHero(Hero hero) {
        availableHero.values().removeIf(value -> value.equals(hero));
    }

    public boolean removeHero(Hero hero, Army army) {
        if (hero instanceof IWarlord) {
            army.setWarlord(null);
        }
        if (hero.getHp() <= 0) {
            removeAvailableHero(hero);
            return true;
        }
        return false;
    }

    public boolean isWarlordAlive() {
        Optional<IWarlord> warlord = Optional.ofNullable(getWarlord());
        return warlord.isPresent();
    }

    public void improveAllies() {
        Optional<IWarlord> warlord = Optional.ofNullable(getWarlord());
        warlord.ifPresent(iWarlord -> heroes.values()
                .forEach(h -> improve(h, iWarlord.getImproveCoefficient())));
    }

    public IWarlord getWarlord() {
        return this.warlord;
    }

    private void improve(Hero hero, float improveCoeff) {
        int value = hero.getHpMax() + Math.round((float) hero.getHpMax() * improveCoeff);
        hero.setHpMax(value);
        value = hero.getDamageDefault() + Math.round((float) hero.getDamageDefault() * improveCoeff);
        hero.setDamage(value);
        float armor = (1.0f + improveCoeff) * hero.getArmorDefault();
        hero.setArmor(armor);
    }

    public void cancelImprove() {
        heroes.values().forEach(this::cancel);
    }

    private void cancel(Hero hero) {
        hero.setArmor(hero.getArmorDefault());
        hero.setHpMax(hero.getHpDefault());
        hero.setDamage(hero.getDamageDefault());
    }
}
