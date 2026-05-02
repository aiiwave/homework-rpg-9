package com.narxoz.rpg.visitor;

import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.ArtifactVisitor;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;

/**
 * Flags artifacts that look suspicious or cursed.
 *
 * The detection rules are deliberately different per artifact type so the
 * visitor produces visibly distinct output for each subclass.
 */
public class CurseDetector implements ArtifactVisitor {

    private int cursedFound = 0;
    private int safeCount = 0;

    @Override
    public void visit(Weapon weapon) {
        // Weapons with very high attack but very low value are suspicious.
        if (weapon.getAttackBonus() >= 15 && weapon.getValue() < 50) {
            System.out.printf("  [CurseDetector] !! '%s' looks too good to be true - possibly CURSED.%n",
                    weapon.getName());
            cursedFound++;
        } else {
            System.out.printf("  [CurseDetector] '%s' - clean.%n", weapon.getName());
            safeCount++;
        }
    }

    @Override
    public void visit(Potion potion) {
        // Potions with healing == 0 are probably poison disguised as remedy.
        if (potion.getHealing() <= 0) {
            System.out.printf("  [CurseDetector] !! '%s' has no healing power - likely POISON.%n",
                    potion.getName());
            cursedFound++;
        } else {
            System.out.printf("  [CurseDetector] '%s' - safe to drink.%n", potion.getName());
            safeCount++;
        }
    }

    @Override
    public void visit(Scroll scroll) {
        // Scrolls with names containing forbidden words are cursed.
        String name = scroll.getName().toLowerCase();
        String spell = scroll.getSpellName().toLowerCase();
        if (name.contains("forbidden") || name.contains("dark") || spell.contains("doom")) {
            System.out.printf("  [CurseDetector] !! '%s' carries a dark inscription - CURSED.%n",
                    scroll.getName());
            cursedFound++;
        } else {
            System.out.printf("  [CurseDetector] '%s' - benign.%n", scroll.getName());
            safeCount++;
        }
    }

    @Override
    public void visit(Ring ring) {
        // Rings with negative magic bonus are clearly cursed.
        if (ring.getMagicBonus() < 0) {
            System.out.printf("  [CurseDetector] !! '%s' drains magic (%d) - CURSED.%n",
                    ring.getName(), ring.getMagicBonus());
            cursedFound++;
        } else {
            System.out.printf("  [CurseDetector] '%s' - wearable.%n", ring.getName());
            safeCount++;
        }
    }

    @Override
    public void visit(Armor armor) {
        // Armor with negative defense bonus is cursed.
        if (armor.getDefenseBonus() < 0) {
            System.out.printf("  [CurseDetector] !! '%s' weakens its wearer - CURSED.%n",
                    armor.getName());
            cursedFound++;
        } else {
            System.out.printf("  [CurseDetector] '%s' - sound craftsmanship.%n", armor.getName());
            safeCount++;
        }
    }

    public int getCursedFound() {
        return cursedFound;
    }

    public int getSafeCount() {
        return safeCount;
    }
}
