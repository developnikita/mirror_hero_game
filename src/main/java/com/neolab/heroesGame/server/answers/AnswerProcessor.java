package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Healer;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.util.Map;
import java.util.Optional;

public final class AnswerProcessor {

    private static int playerId;
    private static int activePlayerId;
    private static BattleArena board;
    private static ActionEffect actionEffect;

    public static ActionEffect getActionEffect() {
        return actionEffect;
    }

    public static int getPlayerId() {
        return playerId;
    }

    public static void setPlayerId(int playerId) {
        AnswerProcessor.playerId = playerId;
    }

    public static int getActivePlayerId() {
        return activePlayerId;
    }

    public static void setActivePlayerId(int activePlayerId) {
        AnswerProcessor.activePlayerId = activePlayerId;
    }

    public static BattleArena getBoard() {
        return board;
    }

    public static void setBoard(BattleArena board) {
        AnswerProcessor.board = board;
    }

    public static void handleAnswer(Answer answer) throws HeroExceptions {
        if (AnswerValidator.isAnswerValidate(answer, board)) {
            if (answer.getAction() == HeroActions.ATTACK) {
                Hero activeHero = getActiveHero(board, answer);
                //если маг или арчер то первый аргумент не используется
                Map<SquareCoordinate, Integer> enemyHeroPosDamage = activeHero
                        .toAttack(answer.getTargetUnit(), board.getArmy(playerId));
                removeUsedHero(board, activeHero.getUnitId());
                setActionEffect(answer, enemyHeroPosDamage);
            } else if (answer.getAction() == HeroActions.HEAL) {
                Healer activeHero = (Healer) getActiveHero(board, answer);
                Map<SquareCoordinate, Integer> allyHeroPosHeal = activeHero
                        .toHeal(answer.getTargetUnit(), board.getArmy(activePlayerId));
                removeUsedHero(board, activeHero.getUnitId());
                setActionEffect(answer, allyHeroPosHeal);
            } else {
                //получаем героя который хочет встать в оборону
                Hero activeHero = getActiveHero(board, answer);
                activeHero.setArmor(activeHero.getArmor() * (float) 1.5);
                activeHero.setDefence();
                removeUsedHero(board, activeHero.getUnitId());
                setActionEffect(answer, null);
            }
        } else throw new HeroExceptions(HeroErrorCode.ERROR_ANSWER);
    }

    private static Hero getActiveHero(BattleArena board, Answer answer) throws HeroExceptions {
        Optional<Hero> activeHero = board.getArmy(activePlayerId).getHero(answer.getActiveHero());
        if(activeHero.isPresent()){
            return activeHero.get();
        }
        throw new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT);
    }

    private static void removeUsedHero(BattleArena board, int heroId) {
        board.getArmy(activePlayerId).removeAvailableHeroById(heroId);
    }

    private static void setActionEffect(Answer answer, Map<SquareCoordinate, Integer> enemyHeroPosDamage) {
        actionEffect = new ActionEffect(answer.getAction(), answer.getActiveHero(), enemyHeroPosDamage);
    }

}
