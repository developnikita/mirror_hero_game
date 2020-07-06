package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.heroes.Hero;

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
        return  heroes.values().removeIf(value -> value.equals(hero));
    }

    public boolean removeAvailableHero(Hero hero) {
        return availableHero.values().removeIf(value -> value.equals(hero));
    }

}
