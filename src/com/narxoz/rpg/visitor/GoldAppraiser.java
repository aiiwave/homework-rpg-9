package com.narxoz.rpg.visitor;

import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.ArtifactVisitor;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;

/**
 * Estimates the resale value of vault artifacts in gold.
 *
 * Each artifact type uses a different multiplier so the visitor produces
 * visibly distinct numbers for different artifact types - proving real
 * double-dispatch behavior in the output.
 */
public class GoldAppraiser implements ArtifactVisitor {

    private int totalValue = 0;
    private int itemsAppraised = 0;

    @Override
    public void visit(Weapon weapon) {
        // Weapons are durable goods: they keep most of their value.
        int appraised = (int) Math.round(weapon.getValue() * 1.20);
        totalValue += appraised;
        itemsAppraised++;
        System.out.printf("  [GoldAppraiser] Weapon  '%s' -> %d gold (atk +%d)%n",
                weapon.getName(), appraised, weapon.getAttackBonus());
    }

    @Override
    public void visit(Potion potion) {
        // Potions are consumed once: depreciated value.
        int appraised = (int) Math.round(potion.getValue() * 0.60);
        totalValue += appraised;
        itemsAppraised++;
        System.out.printf("  [GoldAppraiser] Potion  '%s' -> %d gold (heals %d)%n",
                potion.getName(), appraised, potion.getHealing());
    }

    @Override
    public void visit(Scroll scroll) {
        // Scrolls hold knowledge: collectors pay premium.
        int appraised = (int) Math.round(scroll.getValue() * 1.50);
        totalValue += appraised;
        itemsAppraised++;
        System.out.printf("  [GoldAppraiser] Scroll  '%s' -> %d gold (spell '%s')%n",
                scroll.getName(), appraised, scroll.getSpellName());
    }

    @Override
    public void visit(Ring ring) {
        // Rings carry enchantments: very valuable on the market.
        int appraised = ring.getValue() * 2 + ring.getMagicBonus() * 5;
        totalValue += appraised;
        itemsAppraised++;
        System.out.printf("  [GoldAppraiser] Ring    '%s' -> %d gold (magic %+d)%n",
                ring.getName(), appraised, ring.getMagicBonus());
    }

    @Override
    public void visit(Armor armor) {
        // Armor sells well, scaled by defense.
        int appraised = armor.getValue() + armor.getDefenseBonus() * 3;
        totalValue += appraised;
        itemsAppraised++;
        System.out.printf("  [GoldAppraiser] Armor   '%s' -> %d gold (def +%d)%n",
                armor.getName(), appraised, armor.getDefenseBonus());
    }

    public int getTotalValue() {
        return totalValue;
    }

    public int getItemsAppraised() {
        return itemsAppraised;
    }
}
