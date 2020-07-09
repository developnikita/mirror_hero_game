package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.answers.AnswerProcessor;

import java.util.HashMap;
import java.util.Map;

public class StartGame {
    private enum Turn {
        BOT1,
        BOT2,
        ;
    }

    public static void main(String[] args) {
        final int bot1Id = 1;
        final int bot2Id = 2;
        Player bot1 = new PlayerBot(bot1Id);
        Player bot2 = new PlayerBot(bot2Id);

        WarlordFootman footman1 = createWarlordFootman();
        WarlordFootman footman2 = createWarlordFootman();
        Army playerArmy = new Army(initArmy(), footman1, new SquareCoordinate(1, 1));
        Army botArmy = new Army(initArmy(), footman2, new SquareCoordinate(1, 1));

        Map<Integer, Army> armies = new HashMap<>();
        armies.put(bot1Id, playerArmy);
        armies.put(bot2Id, botArmy);

        BattleArena battleArena = new BattleArena(armies);
        Turn t = Turn.BOT1;
        Answer answer;

        while (true) {
            try {
                switch (t) {
                    case BOT1: {
                        if (!battleArena.getArmy(bot1Id).getAvailableHero().isEmpty()) {
                            System.out.println("Bot1");
                            answer = bot1.getAnswer(battleArena);
                            AnswerProcessor.setActivePlayer(bot1);
                            AnswerProcessor.setPlayer(bot2);
                            AnswerProcessor.setBoard(battleArena);
                            AnswerProcessor.handleAnswer(answer);
                        }
                        t = Turn.BOT2;
                        break;
                    }
                    case BOT2: {
                        if (!battleArena.getArmy(bot2Id).getAvailableHero().isEmpty()) {
                            System.out.println("Bot2");
                            answer = bot2.getAnswer(battleArena);
                            AnswerProcessor.setActivePlayer(bot2);
                            AnswerProcessor.setPlayer(bot1);
                            AnswerProcessor.setBoard(battleArena);
                            AnswerProcessor.handleAnswer(answer);
                        }
                        t = Turn.BOT1;
                        break;
                    }
                    default:
                        System.out.println("Error");
                        break;
                }
            } catch (HeroExceptions ex) {
                System.out.println("Test");
            }

            if (battleArena.getArmy(bot1Id).getAvailableHero().isEmpty() &&
                    battleArena.getArmy(bot2Id).getAvailableHero().isEmpty()) {
                battleArena.getArmy(bot1Id).setAvailableHeroes();
                battleArena.getArmy(bot2Id).setAvailableHeroes();
            }

            Army a = battleArena.getArmy(bot1Id);
            Army b = battleArena.getArmy(bot2Id);
            if (a.isWarlordAlive()) {
                a.cancelImprove();
            } else if (b.isWarlordAlive()) {
                b.cancelImprove();
            }

            if (battleArena.isArmyDied(bot1Id)) {
                System.out.println("Bot 2 win");
                break;
            } else if (battleArena.isArmyDied(bot2Id)) {
                System.out.println("Bot 1 win");
                break;
            }
        }
    }

    public static Map<SquareCoordinate, Hero> initArmy() {
        Map<SquareCoordinate, Hero> army = new HashMap<>();

        army.put(new SquareCoordinate(0, 0), createArcher());
        army.put(new SquareCoordinate(1, 0), createHealer());
        army.put(new SquareCoordinate(2, 0), createMagician());

        army.put(new SquareCoordinate(0, 1), createFootman());
        army.put(new SquareCoordinate(2, 1), createFootman());

        return army;
    }

    public static Archer createArcher() {
        return new Archer(90, 40, 0.85f, 0.00f);
    }

    public static Footman createFootman() {
        return new Footman(170, 50, 0.80f, 0.10f);
    }

    public static Healer createHealer() {
        return new Healer(75, 40, 1.00f, 0.00f);
    }

    public static Magician createMagician() {
        return new Magician(75, 30, 0.80f, 0.00f);
    }

    public static WarlordFootman createWarlordFootman() {
        return new WarlordFootman(180, 60, 0.90f, 0.15f);
    }

    public static WarlordMagician createWarlordMagician() {
        return new WarlordMagician(90, 40, 0.75f, 0.05f);
    }

    public static WarlordVampire createWarlordVampire() {
        return new WarlordVampire(90, 10, 0.80f, 0.05f);
    }
}
