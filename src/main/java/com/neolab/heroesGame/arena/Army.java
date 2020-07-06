package com.neolab.heroesGame.arena;

import java.util.*;

public class Army {
    private final int playerId;
    private final Map<SquareCoordinate, Hero> heroes;
    private final Map<SquareCoordinate, Hero> availableHero;

    public Army(int playerId, Map<SquareCoordinate, Hero> heroes) {
        this.playerId = playerId;
        this.heroes = heroes;
        this.availableHero = new HashMap<SquareCoordinate, Hero>(heroes);
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
        return new Optional<Hero>(heroes.get(coord));
    }

    public Optional<Hero> getAvailableHero(SquareCoordinate coord) {
        return new Optional<Hero>(availableHero.get(coord));
    }

    public boolean killHero(Hero hero) {
        removeAvailableHero(hero);
        return  heroes.values().removeIf(value -> value.equal(hero));
    }

    public boolean removeAvailableHero(Hero hero) {
        return availableHero.values().removeIf(value -> value.equal(hero));
    }

    
}
