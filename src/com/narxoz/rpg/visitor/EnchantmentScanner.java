package com.narxoz.rpg.visitor;

import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.ArtifactVisitor;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;

/**
 * Scans the inventory for magical properties and prints a short
 * enchantment description per artifact.
 *
 * Treats magical items (Scroll, Ring) very differently from mundane gear
 * (Weapon, Armor) - proving the visitor pattern dispatches correctly per
 * concrete type.
 */
public class EnchantmentScanner implements ArtifactVisitor {

    private int magicalItems = 0;
    private int mundaneItems = 0;

    @Override
    public void visit(Weapon weapon) {
        // Weapons can have a faint magical aura tied to attack bonus.
        if (weapon.getAttackBonus() >= 10) {
            System.out.printf("  [EnchantmentScanner] '%s' radiates a battle-aura (+%d atk).%n",
                    weapon.getName(), weapon.getAttackBonus());
            magicalItems++;
        } else {
            System.out.printf("  [EnchantmentScanner] '%s' is a plain blade - no enchantment.%n",
                    weapon.getName());
            mundaneItems++;
        }
    }

    @Override
    public void visit(Potion potion) {
        // All potions count as alchemy, not enchantment.
        System.out.printf("  [EnchantmentScanner] '%s' contains alchemy, not enchantment.%n",
                potion.getName());
        mundaneItems++;
    }

    @Override
    public void visit(Scroll scroll) {
        // Scrolls are inherently magical - print the inscribed spell.
        System.out.printf("  [EnchantmentScanner] '%s' bears the rune of '%s' - strongly magical.%n",
                scroll.getName(), scroll.getSpellName());
        magicalItems++;
    }

    @Override
    public void visit(Ring ring) {
        // Rings: report the bonus level explicitly.
        System.out.printf("  [EnchantmentScanner] '%s' hums with magic (%+d).%n",
                ring.getName(), ring.getMagicBonus());
        magicalItems++;
    }

    @Override
    public void visit(Armor armor) {
        // Armor only enchanted when defense is high.
        if (armor.getDefenseBonus() >= 8) {
            System.out.printf("  [EnchantmentScanner] '%s' glows with a warding enchantment (+%d def).%n",
                    armor.getName(), armor.getDefenseBonus());
            magicalItems++;
        } else {
            System.out.printf("  [EnchantmentScanner] '%s' is plain forged metal.%n",
                    armor.getName());
            mundaneItems++;
        }
    }

    public int getMagicalItems() {
        return magicalItems;
    }

    public int getMundaneItems() {
        return mundaneItems;
    }
}
