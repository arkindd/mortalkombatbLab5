import characters.Player;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Мария
 */
public class Fight {

    LinkedList<Integer> enemyMoves = new LinkedList<>();
    ArrayList<Integer> playerMovesInHisTurn = new ArrayList<>();
    ArrayList<Integer> playerMovesInEnemyTurn = new ArrayList<>();

    /**
     * Метод, вызывающийся при нажатии игроком кнопки "атаковать"
     * В зависимости от текущего хода, вызывает один из соответсвующих ходу методов атаки
     * @param game объект текущей игры
     */
    public void attack(Game game) {
        if (enemyMoves.isEmpty()) {
            setEnemyMoves(game);
        }
        game.getCurrentEnemy().setAttack(enemyMoves.pollFirst());
        if (game.getPlayerTurn()) {
            playerAttackInHisTurn(game);
        } else {
            playerAttackInEnemyTurn(game);
        }
        endRoundCheck(game);
    }

    /**
     * Метод, вызывающийся при нажатии игроком кнопки "защититься"
     * В зависимости от текущего хода, вызывает один из соответсвующих ходу методов защиты
     * @param game объект текущей игры
     */
    public void defence(Game game) {
        if (enemyMoves.isEmpty()) {
            setEnemyMoves(game);
        }
        game.getCurrentEnemy().setAttack(enemyMoves.pollFirst());
        if (game.getPlayerTurn()) {
            playerDefenceInHisTurn(game);
        } else {
            playerDefenceInEnemyTurn(game);
        }
        endRoundCheck(game);
    }

    /**
     * Метод, вызывающийся при выборе кнопки "атаковать" в ход игрока
     * В зависимости от действия противника (enemy.getAttack) определяет исход атаки игрока
     * @param game объект текущей игры
     */
    public void playerAttackInHisTurn(Game game) {
        playerMovesInHisTurn.add(1);
        Player human = game.getHuman();
        Player enemy = game.getEnemies().get(game.getRound());
        if (human.isStunned()) {
            humanIsStunned(game, human, enemy);
        } else if (enemy.isStunned()) {
            game.setTurnResult(enemy.getName() + " are stunned, you attacked");
            enemy.setHealth(enemy.getHealth() - human.getDamage());
            enemy.setStunned(false);
        } else {
            switch (enemy.getAttack()) {
                case 0 -> {
                    human.setHealth(human.getHealth() - (enemy.getDamage() / 2));
                    game.setTurnResult(enemy.getName() + " counterattacked");
                }
                case 1 -> {
                    enemy.setHealth(enemy.getHealth() - human.getDamage());
                    game.setTurnResult("You attacked");
                }
                case 2 -> {
                    game.setTurnResult(enemy.getName() + " try to heal, but you attack him!");
                    enemy.setHealth(enemy.getHealth() - (human.getDamage() * 2));
                }
            }
        }
    }

    /**
     * Метод, вызывающийся при выборе кнопки "атаковать" в ход противника
     * В зависимости от действия противника (enemy.getAttack) определяет исход атаки игрока
     * @param game объект текущей игры
     */
    public void playerAttackInEnemyTurn(Game game) {
        playerMovesInEnemyTurn.add(1);
        Player human = game.getHuman();
        Player enemy = game.getEnemies().get(game.getRound());
        if (human.isStunned()) {
            humanIsStunned(game, human, enemy);
        } else if (enemy.isStunned()) {
            game.setTurnResult(enemy.getName() + " are stunned, you attacked");
            enemy.setHealth(enemy.getHealth() - human.getDamage());
            enemy.setStunned(false);
        } else {
            switch (enemy.getAttack()) {
                case 0 -> game.setTurnResult(enemy.getName() + "didn't attack");
                case 1 -> {
                    human.setHealth(human.getHealth() - enemy.getDamage());
                    game.setTurnResult(enemy.getName() + " attacked");
                }
                case 2 -> {
                    game.setTurnResult(enemy.getName() + " try to heal, but you attack him!");
                    enemy.setHealth(enemy.getHealth() - (human.getDamage() * 2));
                }
            }
        }
    }

    /**
     * Метод, вызывающийся при выборе кнопки "защититься" в ход игрока
     * В зависимости от действия противника (enemy.getAttack) определяет исход защиты игрока
     * @param game объект текущей игры
     */
    public void playerDefenceInHisTurn(Game game) {
        playerMovesInHisTurn.add(0);
        Player human = game.getHuman();
        Player enemy = game.getEnemies().get(game.getRound());
        if (human.isStunned()) {
            humanIsStunned(game, human, enemy);
        } else if (enemy.isStunned()) {
            game.setTurnResult(enemy.getName() + " are stunned, you didn't attack");
            enemy.setStunned(false);
        } else {
            switch (enemy.getAttack()) {
                case 0 -> {
                    game.setTurnResult("Both defended himself");
                    if (Math.random() > 0.50) {
                        enemy.setStunned(true);
                    }
                }
                case 1 -> game.setTurnResult("You didn't attack");
                case 2 -> {
                    game.setTurnResult(enemy.getName() + " are healing!");
                    enemy.setHealth(enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) / 2);
                }
            }
        }
    }

    /**
     * Метод, вызывающийся при выборе кнопки "защититься" в ход противника
     * В зависимости от действия противника (enemy.getAttack) определяет исход защиты игрока
     * @param game объект текущей игры
     */
    public void playerDefenceInEnemyTurn(Game game) {
        playerMovesInEnemyTurn.add(0);
        Player human = game.getHuman();
        Player enemy = game.getEnemies().get(game.getRound());
        if (human.isStunned()) {
            humanIsStunned(game, human, enemy);
        } else if (enemy.isStunned()) {
            game.setTurnResult(enemy.getName() + " are stunned, you didn't attack");
            enemy.setStunned(false);
        } else {
            switch (enemy.getAttack()) {
                case 0 -> {
                    game.setTurnResult("Both defended himself");
                    if (Math.random() > 0.50) {
                        human.setStunned(true);
                    }
                }
                case 1 -> {
                    enemy.setHealth(enemy.getHealth() - (human.getDamage() / 2));
                    game.setTurnResult("You counterattacked");
                }
                case 2 -> {
                    game.setTurnResult(enemy.getName() + " are healing!");
                    enemy.setHealth(enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) / 2);
                }
            }
        }
    }

    /**
     * Метод, вызывающийся при условии, что игрок в этот ход находится в "стане"
     * В зависимости от действий противника определяет исход хода
     * @param game объект текущей игры
     * @param human объект игрока
     * @param enemy объект противника
     */
    private void humanIsStunned(Game game, Player human, Player enemy) {
        switch (enemy.getAttack()) {
            case 0 -> game.setTurnResult("You are stunned, " + enemy.getName() + " didn't attack");
            case 1 -> {
                game.setTurnResult("You are stunned, " + enemy.getName() + " is attacked");
                human.setHealth(human.getHealth() - enemy.getDamage());
            }
            case 2 -> {
                game.setTurnResult(enemy.getName() + " are healing!");
                enemy.setHealth(enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) / 2);
            }
        }
        human.setStunned(false);
    }

    /**
     * Метод, вызываемый при окончании раунда, т.е. после выбранной атаки или защиты игрока
     * Проверяет, не побежден ли один из участников
     * @param game объект текущей игры
     */
    public void endRoundCheck(Game game) {
        Player human = game.getHuman();
        Player enemy = game.getEnemies().get(game.getRound());
        if (human.getHealth() <= 0 & game.getItem(2).getCount() > 0) {
            human.setHealth((int) (human.getMaxHealth() * 0.05));
            game.setTurnResult(game.getTurnResult() + ". Вы воскресли");
            game.setItems(2, game.getItem(2).getCount() - 1);
        }
        if (human.getHealth() <= 0) {
            game.setHumanDefeated(true);
        } else if (enemy.getHealth() <= 0) {
            enemyDefeat(game);
        } else game.newTurn();
    }

    /**
     * Метод, вызываемый при победе игрока над противником
     * Случайным образом выдает игроку предмет, опыт и очки в соответствии с уровнем противника
     * @param game объект текущей игры
     */
    public void enemyDefeat(Game game) {
        game.AddItems();
        game.getHuman().setExperience(game.getHuman().getExperience() + game.getCurrentEnemy().getLevel() * 20);
        game.getHuman().setPoints(game.getHuman().getPoints() + game.getHuman().getHealth() + game.getCurrentEnemy().getLevel() * 30);
    }

    /**
     * Метод, определяющий поведение противника на следующий ход врага и свой ход
     * Поведение определяется только на 2 хода, после чего переопределяется
     * @param game объект текущей игры
     */
    public void setEnemyMoves(Game game) {
        enemyMoves.add(setEnemyMoveForHumanTurn(game));
        enemyMoves.add(setEnemyMoveForEnemyTurn(game));
    }

    /**
     * Метод, определяющий поведение противника на следующий ход игрока
     * Отталкивается от предыдущих действий игрока в ход игрока
     * Если игрок в свой ход чаще выбирал защиту - противник будет атаковать, чтобы не получать "стан"
     * Если игрок чаще выбирал атаку - противник будет защищаться для контратаки
     * При равных значениях противник выбирает действие случайно
     * Для босса случайным образом (15%) может определиться действие лечения
     * @param game объект текущей игры
     * @return действие противника: 0 - защита, 1 - атака, 2 (для босса) - попытка лечения
     */
    public int setEnemyMoveForHumanTurn(Game game) {
        double movesSum = 0;
        int enemyMove;
        if (game.getCurrentEnemy().isBoss() && Math.random() > 0.85) {
            return 2;
        }
        for (int move : playerMovesInHisTurn) {
            movesSum += move;
        }
        if (movesSum / playerMovesInHisTurn.size() == 0.5) {
            enemyMove = (int) (Math.random() * 2);
        } else if (movesSum / playerMovesInHisTurn.size() > 0.5) {
            enemyMove = 0;
        } else enemyMove = 1;
        return enemyMove;
    }

    /**
     * Метод, определяющий поведение противника на следующий ход противника
     * Отталкивается от предыдущих действий игрока в ход противника
     * Если игрок в в ход противника чаще выбирал защиту - противник будет защищаться, чтобы иметь шанс "застанить" игрока
     * Если игрок чаще выбирал атаку - противник будет атаковать, чтобы нанести урон
     * При равных значениях противник выбирает действие случайно
     * Для босса случайным образом (15%) может определиться действие лечения
     * @param game объект текущей игры
     * @return действие противника: 0 - защита, 1 - атака, 2 (для босса) - попытка лечения
     */
    public int setEnemyMoveForEnemyTurn(Game game) {
        double movesSum = 0;
        int enemyMove;
        if (game.getCurrentEnemy().isBoss() && Math.random() > 0.7) {
            return 2;
        }
        for (int move : playerMovesInEnemyTurn) {
            movesSum += move;
        }
        if (movesSum / playerMovesInEnemyTurn.size() == 0.5) {
            enemyMove = (int) (Math.random() * 2);
        } else if (movesSum / playerMovesInEnemyTurn.size() > 0.5) {
            enemyMove = 1;
        } else enemyMove = 0;
        return enemyMove;
    }
}