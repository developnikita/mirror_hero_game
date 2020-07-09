package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.heroes.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Army {
    private final Map<SquareCoordinate, Hero> heroes;
    private Map<SquareCoordinate, Hero> availableHero;

    public Army(Map<SquareCoordinate, Hero> heroes) {
        this.heroes = heroes;
        setAvailableHeroes();
        improveAllies();
    }

    public Map<SquareCoordinate, Hero> getHeroes() {
        return heroes;
    }

    public Map<SquareCoordinate, Hero> getAvailableHero() {
        return availableHero;
    }

    public Optional<Hero> getHero(SquareCoordinate coord) {
        return Optional.of(heroes.get(coord));
    }

    public void setAvailableHeroes() {
        this.availableHero = new HashMap<>(heroes);
    }

    public void killHero(Hero hero) {
        removeAvailableHero(hero);
        heroes.values().removeIf(value -> value.equals(hero));
    }

    public void removeAvailableHero(Hero hero) {
        availableHero.values().removeIf(value -> value.equals(hero));
    }

    public boolean isWarlordAlive() {
        Optional<Hero> warlord =  getWarlord();
        return warlord.isPresent();
    }

    public void improveAllies() {
        Optional<Hero> warlord = getWarlord();

        if (warlord.isPresent()) {
            if (warlord.get() instanceof WarlordFootman) {
                WarlordFootman footman = (WarlordFootman) warlord.get();
                heroes.values()
                        .stream()
                        .filter(h -> !(h instanceof IWarlord))
                        .forEach(h -> improve(h, footman.getImproveCoefficient()));
            }
            else if (warlord.get() instanceof WarlordVampire || warlord.get() instanceof WarlordMagician) {
                WarlordMagician mage = (WarlordMagician) warlord.get();
                heroes.values()
                        .stream()
                        .filter(h -> !(h instanceof IWarlord))
                        .forEach(h -> improve(h, mage.getImproveCoefficient()));
            }
        }
    }

    private Optional<Hero> getWarlord() {
        return heroes.values()
                .stream()
                .filter(h -> h instanceof IWarlord)
                .findAny();
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
