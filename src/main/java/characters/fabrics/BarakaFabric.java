package characters.fabrics;

import characters.Baraka;
import characters.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author Мария
 */
public class BarakaFabric implements EnemyFabricInterface {

    @Override
    public Player create(int playerLevel) {
        Player enemy;
        URL iconUrl = this.getClass().getResource("/images/baraka.png");
        Toolkit tk = Toolkit.getDefaultToolkit();
        enemy = new Baraka(playerLevel+1,
                100 + playerLevel * 5,
                12 + playerLevel * 2,
                1,
                new ImageIcon(tk.getImage(iconUrl)));
        return enemy;
    }
}