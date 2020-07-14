package com.neolab.heroesGame.arena;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neolab.heroesGame.aditional.SquareCoordinateKeySerializer;
import com.neolab.heroesGame.aditional.SquareCoordinateKeyDeserializer;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.IWarlord;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Army {

    @JsonSerialize(keyUsing = SquareCoordinateKeySerializer.class)
    @JsonDeserialize(keyUsing = SquareCoordinateKeyDeserializer.class)
    private final Map<SquareCoordinate, Hero> heroes;
    private IWarlord warlord;
    @JsonSerialize(keyUsing = SquareCoordinateKeySerializer.class)
    @JsonDeserialize(keyUsing = SquareCoordinateKeyDeserializer.class)
    private Map<SquareCoordinate, Hero> availableHero;

    public Army(final Map<SquareCoordinate, Hero> heroes) throws HeroExceptions {
        this.heroes = heroes;
        this.warlord = findWarlord();
        roundIsOver();
        improveAllies();
    }

    @JsonCreator
    public Army(@JsonProperty("heroes") final Map<SquareCoordinate, Hero> heroes,
                @JsonProperty("warlord") final IWarlord warlord,
                @JsonProperty("availableHero") final Map<SquareCoordinate, Hero> availableHero) {
        this.heroes = heroes;
        this.warlord = warlord;
        this.availableHero = availableHero;
    }

    private IWarlord findWarlord() throws HeroExceptions {
        IWarlord iWarlord = null;
        for (final SquareCoordinate coordinate : heroes.keySet()) {
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

    public Optional<Hero> getHero(final SquareCoordinate coordinate) {
        return Optional.ofNullable(heroes.get(coordinate));
    }

    public void roundIsOver() {
        this.availableHero = new HashMap<>(heroes);
    }

    public void killHero(final int heroId) {
        if (warlord.getUnitId() == heroId) {
            cancelImprove();
        }
        removeAvailableHeroById(heroId);
        heroes.values().removeIf(value -> value.getUnitId() == heroId);
    }

    public void setWarlord(final IWarlord warlord) {
        this.warlord = warlord;
    }

    public void removeAvailableHeroById(final int heroId) {
        availableHero.values().removeIf(value -> value.getUnitId() == heroId);
    }

    public boolean removeHero(final Hero hero, final Army army) {
        if (hero instanceof IWarlord) {
            army.setWarlord(null);
            cancelImprove();
        }
        if (hero.getHp() <= 0) {
            removeAvailableHeroById(hero.getUnitId());
            return true;
        }
        return false;
    }

    public boolean isWarlordAlive() {
        final Optional<IWarlord> warlord = Optional.ofNullable(getWarlord());
        return warlord.isPresent();
    }

    public void improveAllies() {
        final Optional<IWarlord> warlord = Optional.ofNullable(getWarlord());
        warlord.ifPresent(iWarlord -> heroes.values()
                .forEach(h -> improve(h, iWarlord.getImproveCoefficient())));
    }

    public IWarlord getWarlord() {
        return this.warlord;
    }

    private void improve(final Hero hero, final float improveCoefficient) {
        int value = hero.getHpMax() + Math.round((float) hero.getHpMax() * improveCoefficient);
        hero.setHpMax(value);
        value = hero.getDamageDefault() + Math.round((float) hero.getDamageDefault() * improveCoefficient);
        hero.setDamage(value);
        final float armor = (1.0f + improveCoefficient) * hero.getArmorDefault();
        hero.setArmor(armor);
    }

    public void cancelImprove() {
        heroes.values().forEach(this::cancel);
    }

    private void cancel(final Hero hero) {
        hero.setArmor(hero.getArmorDefault());
        hero.setHpMax(hero.getHpDefault());
        hero.setDamage(hero.getDamageDefault());
    }
}
