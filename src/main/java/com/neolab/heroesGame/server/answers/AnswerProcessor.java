package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Healer;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class AnswerProcessor {

    private int waitingPlayerId;
    private int activePlayerId;
    private final BattleArena board;
    private ActionEffect actionEffect;

    public AnswerProcessor(final int activePlayerId, final int waitingPlayerId, final BattleArena board) {
        this.waitingPlayerId = waitingPlayerId;
        this.activePlayerId = activePlayerId;
        this.board = board;
        actionEffect = null;
    }

    public ActionEffect getActionEffect() {
        return actionEffect;
    }

    public void setWaitingPlayerId(final int waitingPlayerId) {
        this.waitingPlayerId = waitingPlayerId;
    }

    public void setActivePlayerId(final int activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public void handleAnswer(final Answer answer) throws HeroExceptions {
        if (AnswerValidator.isAnswerValidate(answer, board)) {
            switch (answer.getAction()){
                case ATTACK -> {
                    final Hero activeHero = getActiveHero(board, answer);

                    //если маг или арчер то первый аргумент не используется
                    Map<SquareCoordinate, Integer> enemyHeroPosDamage = activeHero
                            .toAttack(answer.getTargetUnit(), board.getArmy(waitingPlayerId));
                    removeUsedHero(activePlayerId, activeHero.getUnitId());
                    setActionEffect(answer, enemyHeroPosDamage);
                }
                case DEFENCE -> {
                    //получаем героя который хочет встать в оборону
                    final Hero activeHero = getActiveHero(board, answer);
                    activeHero.setArmor(activeHero.getArmor() * (float) 1.5);
                    activeHero.setDefence();
                    removeUsedHero(activePlayerId, activeHero.getUnitId());
                    setActionEffect(answer, new HashMap<>());
                }
                case HEAL -> {
                    final Healer activeHero = (Healer) getActiveHero(board, answer);
                    final Map<SquareCoordinate, Integer> allyHeroPosHeal = activeHero
                            .toHeal(answer.getTargetUnit(), board.getArmy(activePlayerId));
                    removeUsedHero(activePlayerId, activeHero.getUnitId());
                    setActionEffect(answer, allyHeroPosHeal);
                }
                default ->  throw new HeroExceptions(HeroErrorCode.ERROR_ACT);
            }

        } else throw new HeroExceptions(HeroErrorCode.ERROR_ANSWER);
    }

    private Hero getActiveHero(final BattleArena board, final Answer answer) throws HeroExceptions {
        Optional<Hero> activeHero = board.getArmy(activePlayerId).getHero(answer.getActiveHero());
        if (activeHero.isPresent()) {
            return activeHero.get();
        }
        throw new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT);
    }

    private void removeUsedHero(final int activePlayerId, final int heroId) {
        board.removeUsedHeroesById(heroId, activePlayerId);
    }

    private void setActionEffect(final Answer answer, final Map<SquareCoordinate, Integer> enemyHeroPosDamage) {
        actionEffect = new ActionEffect(answer.getAction(), answer.getActiveHero(), enemyHeroPosDamage);
    }

}
