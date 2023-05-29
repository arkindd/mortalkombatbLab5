package characters.fabrics;

import characters.Baraka;
import characters.Player;

import javax.swing.*;

/**
 *
 * @author Мария
 */
public class BarakaFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy;
        enemy = new Baraka(playerLevel+1,
                100 + playerLevel * 5,
                12 + playerLevel * 2,
                1,
                new ImageIcon("src/main/resources/images/baraka.png"));
        return enemy;
    }
}