package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.aditional.CommonFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.answers.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PlayerBot extends Player {
    private static final Logger LOGGER = LoggerFactory.getLogger(Answer.class);
    private final long SEED = 5916;
    private final Random RANDOM = new Random(SEED);

    public PlayerBot(int id) {
        super(id);
    }

    /**
     * бот работает по следующим правилам:
     * 1. Действующий юнит выбирается случайно
     * 2. Маги и лучники всегда атакуют
     * 3. Все выбирают цель из доступных случайно
     * 4. Хилер защищается только если у всех союзников фул хп
     * 5. Если у нескольких союзников не полные хп, то цель выбирается случайно
     * 5. Файтер с 10% шансов встают в защиту.
     * 6. Для якобы случайного выбора используется Set.iterator.next()
     *
     * @param board принимаем текущую ситуацию на игровом поле
     * @return возвращаем Answer, который определяет действие игрока в текущем ходу
     * @throws HeroExceptions текущее исключение вообще не должно выбрасываться, но кто его знает
     */
    @Override
    public Answer getAnswer(BattleArena board) throws HeroExceptions {
        Army thisBotArmy = CommonFunction.getCurrentPlayerArmy(board, getId());
        Army enemyArmy = CommonFunction.getEnemyArmy(board, thisBotArmy);

        SquareCoordinate activeHero = chooseUnit(thisBotArmy);
        Hero hero = Optional.of(thisBotArmy.getHeroes().get(activeHero)).orElseThrow(
                new HeroExceptions(HeroErrorCode.ERROR_ON_BATTLE_ARENA));

        if (CommonFunction.isUnitMagician(hero)) {
            return new Answer(activeHero, HeroActions.ATTACK, new SquareCoordinate(-1, -1), getId());
        }

        if (CommonFunction.isUnitArcher(hero)) {
            return new Answer(activeHero, HeroActions.ATTACK, chooseTargetByArcher(enemyArmy), getId());
        }

        SquareCoordinate targetUnit;
        HeroActions action;
        if (CommonFunction.isUnitHealer(hero)) {
            targetUnit = chooseTargetByHealer(thisBotArmy);
            action = HeroActions.HEAL;
        } else {
            targetUnit = chooseTargetByFootman(activeHero, enemyArmy);
            action = HeroActions.ATTACK;
        }

        if (targetUnit == null) {
            return new Answer(activeHero, HeroActions.DEFENCE, new SquareCoordinate(-1, -1), getId());
        }

        return new Answer(activeHero, action, targetUnit, getId());
    }

    protected SquareCoordinate chooseUnit(Army army) {
        Set<SquareCoordinate> availableHeroes = army.getAvailableHero().keySet();
        return availableHeroes.iterator().next();
    }

    protected SquareCoordinate chooseTargetByArcher(Army enemyArmy) {
        Set<SquareCoordinate> availableHeroes = enemyArmy.getHeroes().keySet();
        return availableHeroes.iterator().next();
    }

    protected SquareCoordinate chooseTargetByHealer(Army army) {
        Map<SquareCoordinate, Hero> heroes = army.getHeroes();
        HashSet<SquareCoordinate> damagedHeroes = new HashSet<>();
        for (SquareCoordinate key : heroes.keySet()) {
            Optional<Hero> temp = army.getHero(key);
            if (temp.isPresent() && (temp.get().getHp() < temp.get().getHpMax())) {
                damagedHeroes.add(key);
            }
        }
        if (damagedHeroes.isEmpty()) {
            return null;
        }
        return damagedHeroes.iterator().next();
    }

    protected SquareCoordinate chooseTargetByFootman(SquareCoordinate activeUnit, Army enemyArmy) throws HeroExceptions {
        if (RANDOM.nextInt(10) == 0) {
            return null;
        }
        Set<SquareCoordinate> validateTarget = CommonFunction.getCorrectTargetForFootman(activeUnit, enemyArmy);
        if (validateTarget.isEmpty()) {
            LOGGER.error(String.format("Юнит (%d,%d) не может найти цель в армии:\n%s",
                    activeUnit.getX(), activeUnit.getY(), CommonFunction.printArmy(enemyArmy)));
            throw new HeroExceptions(HeroErrorCode.ERROR_ON_BATTLE_ARENA);
        }
        return validateTarget.iterator().next();
    }
}
