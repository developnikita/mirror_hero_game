package com.neolab.heroesGame.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.FactoryArmies;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.GameEvent;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;
import com.neolab.heroesGame.server.ActionEffect;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.dto.ExtendedServerRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SerializationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void squareCoordinateSerializationDeserializationTest() throws JsonProcessingException {
        final SquareCoordinate sq = new SquareCoordinate(1, 2);
        final String json = mapper.writeValueAsString(sq);
        final SquareCoordinate newSq = mapper.readValue(json, SquareCoordinate.class);

        assertEquals(sq, newSq);
    }

    @Test
    public void archerSerializationDeserializationTest() throws JsonProcessingException {
        final Hero archer = Archer.createInstance();
        final String json = mapper.writeValueAsString(archer);
        final Hero newArcher = mapper.readValue(json, Archer.class);

        assertEquals(archer, newArcher);
    }

    @Test
    public void footmanSerializationDeserializationTest() throws JsonProcessingException {
        final Hero footman = Footman.createInstance();
        final String json = mapper.writeValueAsString(footman);
        final Hero newFootman = mapper.readValue(json, Footman.class);

        assertEquals(footman, newFootman);
    }

    @Test
    public void healerSerializationDeserializationTest() throws JsonProcessingException {
        final Hero healer = Healer.createInstance();
        final String json = mapper.writeValueAsString(healer);
        final Hero newHealer = mapper.readValue(json, Healer.class);

        assertEquals(healer, newHealer);
    }

    @Test
    public void magicianSerializationDeserializationTest() throws JsonProcessingException {
        final Hero magician = Magician.createInstance();
        final String json = mapper.writeValueAsString(magician);
        final Hero newMagician = mapper.readValue(json, Magician.class);

        assertEquals(magician, newMagician);
    }

    @Test
    public void warlordFootmanSerializationDeserializationTest() throws JsonProcessingException {
        final Hero warlordFootman = WarlordFootman.createInstance();
        final String json = mapper.writeValueAsString(warlordFootman);
        final Hero newWarlordFootman = mapper.readValue(json, WarlordFootman.class);

        assertEquals(warlordFootman, newWarlordFootman);

        final IWarlord warlord = (IWarlord) WarlordFootman.createInstance();
        final String json2 = mapper.writeValueAsString(warlord);
        final IWarlord warlord1 = mapper.readValue(json2, IWarlord.class);

        assertEquals(warlord, warlord1);
    }

    @Test
    public void warlordMagicianSerializationDeserializationTest() throws JsonProcessingException {
        final Hero warlordMagician = WarlordMagician.createInstance();
        final String json = mapper.writeValueAsString(warlordMagician);
        final Hero newWarlordMagician = mapper.readValue(json, WarlordMagician.class);

        assertEquals(warlordMagician, newWarlordMagician);

        final IWarlord warlord = (IWarlord) WarlordMagician.createInstance();
        final String json2 = mapper.writeValueAsString(warlord);
        final IWarlord warlord1 = mapper.readValue(json2, IWarlord.class);

        assertEquals(warlord, warlord1);
    }

    @Test
    public void warlordVampireSerializationDeserializationTest() throws JsonProcessingException {
        final Hero warlordVampire = WarlordVampire.createInstance();
        final String json = mapper.writeValueAsString(warlordVampire);
        final Hero newWarlordVampire = mapper.readValue(json, WarlordVampire.class);

        assertEquals(warlordVampire, newWarlordVampire);

        final IWarlord warlord = (IWarlord) WarlordVampire.createInstance();
        final String json2 = mapper.writeValueAsString(warlord);
        final IWarlord warlord1 = mapper.readValue(json2, IWarlord.class);

        assertEquals(warlord, warlord1);
    }

    @Test
    public void armySerializationDeserializationTest() throws JsonProcessingException, HeroExceptions {
        final Army army = FactoryArmies.generateArmies(1, 2).get(1);
        final String json = mapper.writeValueAsString(army);
        final Army newArmy = mapper.readValue(json, Army.class);

        assertEquals(army, newArmy);
    }

    @Test
    public void battleArenaSerializationDeserializationTest() throws JsonProcessingException, HeroExceptions {
        final BattleArena arena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        final String json = mapper.writeValueAsString(arena);
        final BattleArena newArena = mapper.readValue(json, BattleArena.class);

        assertEquals(arena, newArena);
    }

    @Test
    public void answerSerializationDeserializationTest() throws JsonProcessingException {
        final Answer a = new Answer(new SquareCoordinate(1, 2), HeroActions.ATTACK,
                new SquareCoordinate(2, 2), 1);
        final String json = mapper.writeValueAsString(a);
        final Answer newA = mapper.readValue(json, Answer.class);

        assertEquals(a, newA);
    }

    @Test
    public void actionEffectSerializationDeserializationTest() throws JsonProcessingException {
        final Map<SquareCoordinate, Integer> map = new HashMap<>();
        map.put(new SquareCoordinate(2, 2), 2);
        final ActionEffect ae = new ActionEffect(HeroActions.ATTACK, new SquareCoordinate(1, 2), map, 1);
        final String json = mapper.writeValueAsString(ae);
        System.out.println(json);
        final ActionEffect newAe = mapper.readValue(json, ActionEffect.class);

        assertEquals(ae, newAe);
    }

    @Test
    public void extendedServerRequestTest() throws Exception {
        final BattleArena arena = new BattleArena(FactoryArmies.generateArmies(1, 2));
        final Map<SquareCoordinate, Integer> map = new HashMap<>();
        map.put(new SquareCoordinate(2, 2), 2);
        final ActionEffect ae = new ActionEffect(HeroActions.ATTACK, new SquareCoordinate(1, 2), map, 1);

        String request = ExtendedServerRequest.getRequestString(GameEvent.GAME_END_WITH_A_TIE, arena, ae);
        ExtendedServerRequest extendedServerRequest = ExtendedServerRequest.getRequestFromString(request);

        assertEquals(arena, extendedServerRequest.arena);
        assertEquals(ae, extendedServerRequest.effect);
        assertEquals(GameEvent.GAME_END_WITH_A_TIE, extendedServerRequest.event);
    }
}
