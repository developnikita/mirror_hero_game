package com.neolab.heroesGame.arena;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FabricArmiesTest {

    @Test
    public void testCreateBattaleArena() {
        Map<Integer, Army> armies = FabricArmies.generateArmyes(1, 2);
        assertEquals(6, armies.get(1).getHeroes().size());
        assertEquals(6, armies.get(2).getHeroes().size());
        assertNotNull(armies.get(1).getWarlord().get());
        assertNotNull(armies.get(2).getWarlord().get());
    }
}