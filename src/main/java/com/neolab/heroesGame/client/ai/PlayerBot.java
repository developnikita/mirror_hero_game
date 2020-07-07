package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class PlayerBot extends Player {
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
        Army thisBotArmy = getThisBotArmy(board);
        Army enemyArmy = getEnemyArmy(board);

        SquareCoordinate activeHero = chooseUnit(thisBotArmy);
        Hero hero = Optional.of(thisBotArmy.getHeroes().get(activeHero)).orElseThrow(
                new HeroExceptions(HeroErrorCode.ERROR_ACTIVE_UNIT));

        if (isUnitMagician(hero)) {
            return new Answer(activeHero, HeroActions.ATTACK, new SquareCoordinate(-1, -1), getId());
        }

        if (isUnitArcher(hero)) {
            return new Answer(activeHero, HeroActions.ATTACK, chooseTargetByArcher(enemyArmy), getId());
        }

        SquareCoordinate targetUnit;
        HeroActions action;
        if (isUnitHealer(hero)) {
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

    private SquareCoordinate chooseUnit(Army army) {
        Set<SquareCoordinate> availableHeroes = army.getAvailableHero().keySet();
        return availableHeroes.iterator().next();
    }

    private boolean isUnitMagician(Hero hero) {
        return hero.getClass() == Magician.class
                || hero.getClass() == WarlordMagician.class
                || hero.getClass() == WarlordVampire.class;
    }

    private boolean isUnitArcher(Hero hero) {
        return hero.getClass() == Archer.class;
    }

    private boolean isUnitHealer(Hero hero) {
        return hero.getClass() == Healer.class;
    }

    private SquareCoordinate chooseTargetByArcher(Army enemyArmy) {
        Set<SquareCoordinate> availableHeroes = enemyArmy.getAvailableHero().keySet();
        return availableHeroes.iterator().next();
    }

    private SquareCoordinate chooseTargetByHealer(Army army) {
        Map<SquareCoordinate, Hero> heroes = army.getHeroes();
        HashSet<SquareCoordinate> damagedHeroes = new HashSet<>();
        for (SquareCoordinate key : heroes.keySet()) {
            Optional<Hero> temp = army.getHero(key);
            if (temp.isPresent() && (temp.get().getHp() < temp.get().getHpMax())) {
                damagedHeroes.add(key);
            }
        }
        if (damagedHeroes.isEmpty()) {
            return new SquareCoordinate(-1, -1);
        }
        return damagedHeroes.iterator().next();
    }

    private SquareCoordinate chooseTargetByFootman(SquareCoordinate activeUnit, Army enemyArmy) {
        if (RANDOM.nextInt(10) == 0) {
            return new SquareCoordinate(-1, -1);
        }
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int y = 1; y >= 0; y--) {
            if (enemyArmy.getHero(new SquareCoordinate(activeUnit.getX(), y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(activeUnit.getX(), y));
            }
            if (enemyArmy.getHero(new SquareCoordinate(1, y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(1, y));
            }
            if (!validateTarget.isEmpty()) {
                break;
            }
            int x = activeUnit.getX() == 2 ? 0 : 2;
            if (enemyArmy.getHero(new SquareCoordinate(x, y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, y));
                break;
            }
        }
        return validateTarget.iterator().next();
    }

    private Army getEnemyArmy(BattleArena board) {
        return null;
    }

    private Army getThisBotArmy(BattleArena board) {
        return null;
    }
}
