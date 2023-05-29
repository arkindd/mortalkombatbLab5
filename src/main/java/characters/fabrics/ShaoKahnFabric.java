package characters.fabrics;

import characters.Player;
import characters.ShaoKahn;

import javax.swing.*;

/**
 * @author Мария
 */
public class ShaoKahnFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy = new ShaoKahn(playerLevel + 2,
                100 + playerLevel * 8,
                25 + playerLevel * 4,
                1,
                new ImageIcon("src/main/resources/images/shao kahn.png"));
        enemy.setBoss(true);
        return enemy;
    }
}