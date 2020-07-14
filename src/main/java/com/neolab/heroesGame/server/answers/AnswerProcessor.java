package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.util.Collections;
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

    /**
     * Проверяем все ли в порядке с запросом.
     * Объявляем переменные, которые будут использоваться для формирования actionEffect
     * Если юнит защищался - сбрасываем защиту. Если защищается сейчас - устанавливаем.
     * Если юнит не защищается, то определяем над какой армией будет совершаться действие:
     * - для HEAL над нашей
     * - для ATTACK над вражеской
     *
     * @param answer - ответ игрока на вопрос "Что делаем?"
     * @throws HeroExceptions выбрасываем исключение в соответствии с ошибкой в запросе (answer)
     */
    public void handleAnswer(final Answer answer) throws HeroExceptions {
        if (AnswerValidator.isAnswerValidate(answer, board)) {
            Map<SquareCoordinate, Integer> effectActionMap;
            Hero activeHero = getActiveHero(board, answer);

            if (activeHero.isDefence()) {
                activeHero.cancelDefence();
            }
            if (answer.getAction() == HeroActions.DEFENCE) {
                effectActionMap = Collections.emptyMap();
                activeHero.setDefence();
            } else {
                effectActionMap = activeHero.toAct(answer.getTargetUnit(), answer.getAction() == HeroActions.HEAL
                        ? board.getArmy(activePlayerId) : board.getArmy(waitingPlayerId));
            }

            removeUsedHero(answer.getActiveHero());
            setActionEffect(answer, effectActionMap);
        } else {
            throw new HeroExceptions(HeroErrorCode.ERROR_ANSWER);
        }

    }

    private Hero getActiveHero(final BattleArena board, final Answer answer) throws HeroExceptions {
        Optional<Hero> activeHero = board.getArmy(activePlayerId).getHero(answer.getActiveHero());
        if (activeHero.isPresent()) {
            return activeHero.get();
        }
        throw new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT);
    }

    private void removeUsedHero(final SquareCoordinate coords) {
        board.getArmy(activePlayerId).removeAvailableHeroByCoord(coords);
    }

    private void setActionEffect(final Answer answer, final Map<SquareCoordinate, Integer> enemyHeroPosDamage) {
        actionEffect = new ActionEffect(answer.getAction(), answer.getActiveHero(), enemyHeroPosDamage);
    }

}
