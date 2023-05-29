package characters.fabrics;

import characters.LiuKang;
import characters.Player;

import javax.swing.*;

/**
 * @author Мария
 */
public class LiuKangFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy;
        enemy = new LiuKang(playerLevel + 1,
                70 + playerLevel * 5,
                18 + playerLevel * 2,
                1,
                new ImageIcon("src/main/resources/images/liukang.png"));
        return enemy;
    }
}