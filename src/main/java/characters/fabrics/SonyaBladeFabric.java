package characters.fabrics;

import characters.Player;
import characters.SonyaBlade;

import javax.swing.*;

/**
 * @author Мария
 */
public class SonyaBladeFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy = new SonyaBlade(playerLevel + 1,
                80 + playerLevel * 5,
                16 + playerLevel * 2,
                1,
                new ImageIcon(getClass().getResource("/images/sonya blade.png")));
        enemy.setBoss(true);
        return enemy;
    }
}