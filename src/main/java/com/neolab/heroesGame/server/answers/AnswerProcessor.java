package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.ai.ActionEffect;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Healer;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.heroes.Magician;

import java.util.HashMap;
import java.util.Map;

public final class AnswerProcessor {

    private static Player player;
    private static Player activePlayer;
    private static BattleArena board;
    private static final ActionEffect actionEffect = new ActionEffect();

    public static ActionEffect getActionEffect() {
        return actionEffect;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        AnswerProcessor.player = player;
    }

    public static Player getActivePlayer() {
        return activePlayer;
    }

    public static void setActivePlayer(Player activePlayer) {
        AnswerProcessor.activePlayer = activePlayer;
    }

    public static BattleArena getBoard() {
        return board;
    }

    public static void setBoard(BattleArena board) {
        AnswerProcessor.board = board;
    }

    public static void handleAnswer(Answer answer) throws HeroExceptions {
        if(AnswerValidator.validateAnswer(answer, board)){
            if(answer.getAction() == HeroActions.ATTACK){
                Hero activeHero = getActiveHero(board, answer);
                //если маг или арчер то первый аргумент не используется
                activeHero.toAttack(answer.getTargetUnit(), board.getArmy(player.getId()));
                removeUsedHero(board, activeHero);
                setActionEffect(answer, activeHero);
            }
            else if(answer.getAction() == HeroActions.HEAL){
                Healer activeHero = (Healer) getActiveHero(board, answer);
                activeHero.toHeal(answer.getTargetUnit(), board.getArmy(activePlayer.getId()));
                removeUsedHero(board, activeHero);
                setActionEffect(answer, activeHero);
            }
            else {
                //получаем героя который хочет встать в оборону
                Hero activeHero = getActiveHero(board, answer);
                activeHero.setArmor(activeHero.getArmor() * (float) 1.5);
                activeHero.setDefence(true);
                removeUsedHero(board, activeHero);
                setActionEffect(answer, activeHero);
            }
        }
        else throw new HeroExceptions(HeroErrorCode.ERROR_ANSWER);
    }

    private static Hero getActiveHero(BattleArena board, Answer answer) throws HeroExceptions {
         return board.getArmy(activePlayer.getId())
                .getHero(answer.getActiveHero())
                .orElseThrow(new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT));
    }
    private static void removeUsedHero(BattleArena board, Hero hero){
        board.getArmy(activePlayer.getId()).removeAvailableHero(hero);
    }

    private static void setActionEffect(Answer answer, Hero activeHero){
        actionEffect.setAction(answer.getAction());
        actionEffect.setSourceUnit(answer.getActiveHero());

        if(answer.getAction() != HeroActions.DEFENCE){
            Map<SquareCoordinate, Integer> enemyHeroPosDamage =  new HashMap<>();

            if(activeHero instanceof Magician){
                //todo -1 заглушка, нужно передать значение урона
                board.getArmy(player.getId()).getHeroes().keySet().forEach(coord -> enemyHeroPosDamage.put(coord, -1));
            }
            else {
                enemyHeroPosDamage.put(answer.getTargetUnit(), -1);
            }
            actionEffect.setTargetUnitsMap(enemyHeroPosDamage);
        }
    }

}
