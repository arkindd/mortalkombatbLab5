package characters;

import javax.swing.*;

/**
 * @author Мария
 */
public class SonyaBlade extends Player {

    public SonyaBlade(int level, int health, int damage, int attack, ImageIcon icon) {
        super(level, health, damage, attack, icon);
    }

    @Override
    public String getName() {
        return "Sonya Blade (boss)";
    }
}