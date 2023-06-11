package characters.fabrics;

import characters.LiuKang;
import characters.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author Мария
 */
public class LiuKangFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy;
        URL iconUrl = this.getClass().getResource("images/baraka.png");
        Toolkit tk = Toolkit.getDefaultToolkit();
        enemy = new LiuKang(playerLevel + 1,
                70 + playerLevel * 5,
                18 + playerLevel * 2,
                1,
                new ImageIcon(getClass().getResource("/images/liukang.png")));
        return enemy;
    }
}