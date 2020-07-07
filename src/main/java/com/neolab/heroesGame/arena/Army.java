package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.WarlordFootman;
import com.neolab.heroesGame.heroes.WarlordMagician;
import com.neolab.heroesGame.heroes.WarlordVampire;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Army {
    private final int playerId;
    private final Map<SquareCoordinate, Hero> heroes;
    private final Map<SquareCoordinate, Hero> availableHero;

    public Army(int playerId, Map<SquareCoordinate, Hero> heroes) {
        this.playerId = playerId;
        this.heroes = heroes;
        this.availableHero = new HashMap<>(heroes);
    }

    public int getPlayerId() {
        return playerId;
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

    public Optional<Hero> getAvailableHero(SquareCoordinate coord) {
        return Optional.of(availableHero.get(coord));
    }

    public boolean killHero(Hero hero) {
        removeAvailableHero(hero);
        return heroes.values().removeIf(value -> value.equals(hero));
    }

    public boolean removeAvailableHero(Hero hero) {
        return availableHero.values().removeIf(value -> value.equals(hero));
    }

    public boolean isWarlordAlive() {
        for (Hero h : heroes.values()) {
            if (h.getClass().equals(WarlordFootman.class) ||
                    h.getClass().equals(WarlordMagician.class) ||
                    h.getClass().equals(WarlordVampire.class)) {
                if (h.getHp() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void improveAllies() {
        Optional<Hero> test = getWarlord();

        if (test.isPresent()) {
            if (test.get().getClass().equals(WarlordFootman.class)) {
                WarlordFootman footman = (WarlordFootman) test.get();
                heroes.values()
                        .stream()
                        .filter(h -> !h.getClass().equals(WarlordFootman.class))
                        .peek(h -> improve(h, footman.getImproveCoefficient()));
            } else if (test.get().getClass().equals(WarlordVampire.class)) {
                WarlordVampire vampire = (WarlordVampire) test.get();
                heroes.values()
                        .stream()
                        .filter(h -> !h.getClass().equals(WarlordVampire.class))
                        .peek(h -> improve(h, vampire.getImproveCoefficient()));
            } else if (test.get().getClass().equals(WarlordMagician.class)) {
                WarlordMagician magician = (WarlordMagician) test.get();
                heroes.values()
                        .stream()
                        .filter(h -> !h.getClass().equals(WarlordMagician.class))
                        .peek(h -> improve(h, magician.getImproveCoefficient()));
            }
        }
    }

    private Optional<Hero> getWarlord() {
        return heroes.values()
                .stream()
                .filter(h -> h.getClass().equals(WarlordFootman.class) ||
                        h.getClass().equals(WarlordMagician.class) ||
                        h.getClass().equals(WarlordVampire.class))
                .findAny();
    }

    private void improve(Hero hero, float improveCoeff) {
        int value = hero.getHp() + Math.round((float)hero.getHp() * improveCoeff);
        hero.setHp(value);
        value = hero.getDamage() + Math.round((float)hero.getDamage() * improveCoeff);
        hero.setDamage(value);
        float armor = (1 + improveCoeff) * hero.getArmor();
        hero.setArmor(armor);
    }
}
