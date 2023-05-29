package characters.fabrics;

import characters.Player;
import characters.SubZero;

import javax.swing.*;

/**
 * @author Мария
 */
public class SubZeroFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy;
        enemy = new SubZero(playerLevel + 1,
                60 + playerLevel * 5,
                20 + playerLevel * 2,
                1,
                new ImageIcon("src/main/resources/images/subzero.png"));
        return enemy;
    }
}