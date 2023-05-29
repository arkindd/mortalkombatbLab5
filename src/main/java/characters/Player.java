package characters;

import lombok.Data;

import javax.swing.*;

/**
 * @author Мария
 */
@Data
public class Player {

    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int attack;
    private ImageIcon icon;
    private boolean isStunned;
    private boolean isBoss = false;

    public Player(int level, int health, int damage, int attack, ImageIcon icon) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.attack = attack;
        this.maxHealth = health;
        this.icon = icon;
    }

    public String getName() {
        return "";
    }
}