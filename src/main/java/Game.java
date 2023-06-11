import characters.Human;
import characters.Player;
import characters.fabrics.EnemyFabric;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мария
 */
public class Game {

    private final int numberOfLocations;
    private int currentLocation = 1;
    private int round;
    private boolean playerTurn = true;
    private String turnResult;
    private boolean humanDefeated = false;
    private final Item[] items = {new Item("Малое зелье лечения", 0),
            new Item("Большое зелье лечения", 0),
            new Item("Крест возрождения", 0)};
    private final Human human;
    private ArrayList<Player> enemies;
    Fight fight = new Fight();
    private ArrayList<Result> results;

    /**
     * Конструктор для объекта текущей игры
     * Создает игрока со стандартными характиристиками, противников для первой локации, задает первые два хода противника случайным образом
     * @param numberOfLocations количество локаций, введенных игроком
     */
    public Game(int numberOfLocations){
        this.numberOfLocations = numberOfLocations;
        URL iconUrl = this.getClass().getResource("/images/kitana.png");
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.human = new Human(0, 80, 18, 1, new ImageIcon(tk.getImage(iconUrl)));
        this.enemies = enemiesInThisLocation(human.getLevel());
        this.fight.enemyMoves.add((int) (Math.random() * 2));
        this.fight.enemyMoves.add((int) (Math.random() * 2));
        try {
            this.results = ExcelReader.readFromExcel();
            assert results != null;
            results.sort(Comparator.comparing(Result::getPoints));
        } catch (IOException e){
            Logger.getLogger(JFrames.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Метод, определяющий противников для этой локации в соответствии с уровнем игрока
     * @param playerLevel уровень игрока
     * @return коллекция объектов player, содержащую всех противников для этой локации
     */
    public ArrayList<Player> enemiesInThisLocation(int playerLevel) {
        ArrayList<Player> enemies = new ArrayList<>();
        for (int i = 0; i < playerLevel + 2; i++) {
            enemies.add(EnemyFabric.createEnemy(playerLevel));
        }
        enemies.add(EnemyFabric.createBoss(playerLevel));
        return enemies;
    }

    /**
     * Метод, вызываемый при нажатии кнопки "атаковать" (создан в классе game для удобства модернизации приложения)
     * @param game объект текущей игры
     */
    public void attack(Game game) {
        fight.attack(game);
    }

    /**
     * Метод, вызываемый при нажатии кнопки "защищаться" (создан в классе game для удобства модернизации приложения)
     * @param game объект текущей игры
     */
    public void defence(Game game){
        fight.defence(game);
    }

    /**
     * Метод, меняющий значение параметра, определяющего, чей сейчас ход
     */
    public void newTurn(){
        setPlayerTurn(!getPlayerTurn());
    }

    /**
     * Метод, вызываемый при победе игрока над противником
     * @return true - если игра продолжается, false - если враги и локации закончились и игрок прошел игру
     */
    public boolean newRound(){
        setRound(round+1);
        human.setHealth(human.getMaxHealth());
        if (round==enemies.size()){
            if (currentLocation<numberOfLocations){
                newLocation();
            } else return false;
        }
        setPlayerTurn(true);
        fight.enemyMoves.clear();
        fight.enemyMoves.add((int) (Math.random() * 2));
        fight.enemyMoves.add((int) (Math.random() * 2));
        return true;
    }

    /**
     * Метод, вызываемый при прохождении игроком локации и наличии следующей
     * Создает противников для новой локации и обновляет параметры игры
     */
    public void newLocation(){
        setRound(0);
        setCurrentLocation(currentLocation+1);
        enemies = enemiesInThisLocation(human.getLevel());
        fight.enemyMoves.add((int) (Math.random() * 2));
        fight.enemyMoves.add((int) (Math.random() * 2));
    }

    /**
     * Метод, вызываемый при победе игрока над противником
     * Случайным образом определяет выдаваемый предмет игроку (1 предмет - 40%, 2 предмет - 20%, 3 предмет - 10%)
     */
    public void AddItems() {
        double i = Math.random();
        if (i <= 0.40) {
            setItems(0, +1);
        } else if ((i >= 0.41) & (i <= 0.60)) {
            setItems(1, +1);
        } else if ((i >= 0.61) & (i <= 0.70)) {
            setItems(2, +1);
        }
    }

    /**
     * Метод, вызываемый при использовании игроком предмета
     * @param item выбранный игроком предмет
     * @return true - если предмет использован успешно, false - если предмет невозможно использовать
     */
    public boolean useItem(int item){
        switch (item) {
            case 1:
                if (items[0].getCount() > 0) {
                    if (human.getHealth() + (human.getMaxHealth() * 0.25) >= human.getMaxHealth()){
                        human.setHealth(human.getMaxHealth());
                    } else human.setHealth((int) (human.getHealth() + (human.getMaxHealth() * 0.25)));
                    items[0].setCount(items[0].getCount()-1);
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (items[1].getCount() > 0) {
                    if (human.getHealth() + (human.getMaxHealth() * 0.5) >= human.getMaxHealth()){
                        human.setHealth(human.getMaxHealth());
                    } else human.setHealth((int) (human.getHealth() + (human.getMaxHealth() * 0.5)));
                    items[1].setCount(items[1].getCount()-1);
                    return true;
                } else {
                    return false;
                }
            case 3:
                return false;
        }
        return false;
    }

    /**
     * Метод, вызываемый при завершении игры
     * @param playerName введенное игроком имя
     * @throws IOException при невозможности открыть excel файл
     */
    public void gameOver(String playerName) throws IOException {
        Result result = new Result(playerName, human.getPoints());
        results.add(result);
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        ExcelReader.writeToExcel(results);
    }

    /**
     * Метод, возвращающий текущего противника для этого раунда
     */
    public Player getCurrentEnemy(){
        return enemies.get(round);
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isHumanDefeated() {
        return humanDefeated;
    }

    public void setHumanDefeated(boolean humanDefeated) {
        this.humanDefeated = humanDefeated;
    }

    public Item getItem(int i) {
        return items[i];
    }

    public Item[] getItems(){
        return items;
    }

    public void setItems(int item, int countOfItem) {
        this.items[item].setCount(countOfItem);
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getTurnResult() {
        return turnResult;
    }

    public void setTurnResult(String turnResult) {
        this.turnResult = turnResult;
    }

    public Human getHuman() {
        return human;
    }

    public ArrayList<Player> getEnemies() {
        return enemies;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}