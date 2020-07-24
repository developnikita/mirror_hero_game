package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.aditional.CommonFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.answers.Answer;

import java.io.IOException;
import java.util.*;

public class PlayerHuman extends Player {
    private final IGraphics gui;
    private final SquareCoordinate coordinateDoesntMatters = new SquareCoordinate(-1, -1);

    public PlayerHuman(final int id, final String name, final IGraphics gui) {
        super(id, name);
        this.gui = gui;
    }

    /**
     * Выбираем героя в функции chooseUnit
     * Выбираем действие, если null возвращаемся к выбору героя
     * Выбираем цели: для мага и защиты цель не выбирается. Если null возвращаемся к выбору героя
     *
     * @param board принимаем текущую ситуацию на игровом поле
     * @return возвращаем Answer, который определяет действие игрока в текущем ходу
     */
    @Override
    public Answer getAnswer(final BattleArena board) throws IOException {

        final Army yourArmy = board.getArmy(getId());
        final Army enemyArmy = board.getEnemyArmy(getId());
        while (true) {
            final SquareCoordinate activeHeroCoordinate = chooseUnit(yourArmy);
            final Hero activeHero = yourArmy.getHero(activeHeroCoordinate).orElseThrow();

            final HeroActions action = chooseActionForHero(activeHeroCoordinate, activeHero);
            if (action == null) {
                continue;
            }

            final SquareCoordinate targetUnitCoordinate =
                    (action == HeroActions.DEFENCE || CommonFunction.isUnitMagician(activeHero)) ?
                            coordinateDoesntMatters :
                            chooseTargetCoordinate(activeHeroCoordinate, activeHero, enemyArmy, yourArmy);

            if (targetUnitCoordinate == null) {
                continue;
            }

            return new Answer(activeHeroCoordinate, action, targetUnitCoordinate, getId());
        }
    }

    public String getStringArmyFirst(final int armySize) {
        final List<String> armies = CommonFunction.getAllAvailableArmiesCode(armySize);
        return armies.get(new Random().nextInt(armies.size()));
    }

    public String getStringArmySecond(final int armySize, final Army army) {
        return getStringArmyFirst(armySize);
    }

    /**
     * Формируем строки, которые увидет пользователь, из доступных для действия героев
     * counter используется только для обозначения номера строки
     * gui.getChoose() возвращает номер строки, которую выбрал пользователь.
     * Индекс строки на 1 больше индекса соответствующей координаты
     */
    protected SquareCoordinate chooseUnit(final Army army) throws IOException {
        final List<String> strings = new ArrayList<>();
        final List<SquareCoordinate> listVariationCoordinate = new ArrayList<>();
        final Map<SquareCoordinate, Hero> heroes = army.getAvailableHeroes();
        strings.add("Выберите юнита для действия:");
        int counter = 1;
        for (final SquareCoordinate key : heroes.keySet()) {
            strings.add(String.format("%d. %s", counter++, makeUnitChooseString(key, heroes.get(key))));
            listVariationCoordinate.add(key);
        }
        return listVariationCoordinate.get(gui.getChoose(strings) - 1);
    }

    private String makeUnitChooseString(final SquareCoordinate coordinate, final Hero hero) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hero.getClassName());
        if (coordinate.getX() == 0) {
            stringBuilder.append(" на левом фланге");
        } else if (coordinate.getX() == 1) {
            stringBuilder.append(" по центру");
        } else {
            stringBuilder.append(" на правом фланге");
        }
        return stringBuilder.toString();
    }

    /**
     * Формируем список строк, которые увидет пользователь. Смотрим его выбор и отправляем соответствующий результат
     */
    private HeroActions chooseActionForHero(final SquareCoordinate coordinate, final Hero hero) throws IOException {
        final List<String> strings = new ArrayList<>();
        strings.add(String.format("Выберите действие для %s:", makeUnitChooseString(coordinate, hero)));
        strings.add("1. Защита");
        strings.add(CommonFunction.isUnitHealer(hero) ? "2. Лечить союзников" : "2. Атаковать");
        strings.add("3. Вернуться к выбору юнита");
        final int choose = gui.getChoose(strings);
        if (choose == 3) {
            return null;
        } else if (choose == 1) {
            return HeroActions.DEFENCE;
        }
        return CommonFunction.isUnitHealer(hero) ? HeroActions.HEAL : HeroActions.ATTACK;
    }

    /**
     * Формируем сет доступных целей. Для лучника это вся вражеская армия, для лекаря - вся своя армия, для воинов
     * определяем доступные цели с помощью функции CommonFunction.getCorrectTargetForFootman()
     * Формируем строки, которые увидет пользователь. counter будет равен индексу последней строки
     * gui.getChoose() возвращает номер строки, которую выбрал пользователь. Если это последняя строка, то возвращаемся
     * к выбору героя. Иначе выбираем координаты из списка доступных.
     * Индекс строки на 1 больше индекса соответствующей координаты
     */
    private SquareCoordinate chooseTargetCoordinate(final SquareCoordinate activeHeroCoordinate, final Hero activeHero,
                                                    final Army enemyArmy, final Army yourArmy) throws IOException {
        final Set<SquareCoordinate> legalTargetCoordinate;
        final Army correctArmy = CommonFunction.isUnitHealer(activeHero) ? yourArmy : enemyArmy;
        if (CommonFunction.isUnitArcher(activeHero)) {
            legalTargetCoordinate = enemyArmy.getHeroes().keySet();
        } else if (CommonFunction.isUnitHealer(activeHero)) {
            legalTargetCoordinate = yourArmy.getHeroes().keySet();
        } else {
            legalTargetCoordinate = CommonFunction.getCorrectTargetForFootman(activeHeroCoordinate, enemyArmy);
        }
        final List<String> strings = new ArrayList<>();
        final List<SquareCoordinate> listVariationCoordinate = new ArrayList<>();
        strings.add(String.format("Выберите цель %s: ", makeUnitChooseString(activeHeroCoordinate, activeHero)));

        int counter = 1;
        for (final SquareCoordinate key : legalTargetCoordinate) {
            strings.add(String.format("%d. %s", counter++, makeUnitChooseString(key, correctArmy.getHero(key).get())));
            listVariationCoordinate.add(key);
        }
        strings.add(String.format("%d. Вернуться к выбору юнита", counter));
        final int choose = gui.getChoose(strings);
        return choose == counter ? null : listVariationCoordinate.get(choose - 1);
    }

}
