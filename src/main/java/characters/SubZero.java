package characters;

import javax.swing.*;

/**
 * @author Мария
 */
public class SubZero extends Player {

    public SubZero(int level, int health, int damage, int attack, ImageIcon icon) {
        super(level, health, damage, attack, icon);
    }

    @Override
    public String getName() {
        return "Sub-Zero";
    }
}