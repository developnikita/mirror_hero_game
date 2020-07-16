package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.server.ActionEffect;

public interface IGraphics {
    void showPosition(BattleArena arena, ActionEffect effect, Integer playerId) throws Exception;
}
