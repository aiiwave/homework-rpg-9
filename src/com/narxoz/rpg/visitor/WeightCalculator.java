package com.narxoz.rpg.visitor;

import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.ArtifactVisitor;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;
import com.narxoz.rpg.artifact.Artifact;

/**
 * Open/Closed proof visitor.
 *
 * This 4th visitor is added without modifying any class under {@code artifact/}.
 * It computes the total carry weight of an inventory, with category-aware
 * adjustments (armor is bulkier; potions weigh slightly more when full of
 * liquid; scrolls are featherlight).
 *
 * The fact that this visitor compiles, runs, and integrates with
 * {@link com.narxoz.rpg.artifact.Inventory#accept} without touching the
 * existing artifact hierarchy demonstrates the open/closed principle in
 * action.
 */
public class WeightCalculator implements ArtifactVisitor {

    private double totalWeight = 0.0;
    private int items = 0;

    @Override
    public void visit(Weapon weapon) {
        // Weapons carry their listed weight as-is.
        addWeight(weapon, weapon.getWeight());
    }

    @Override
    public void visit(Potion potion) {
        // Potions are liquid: 10% heavier than the listed weight.
        addWeight(potion, potion.getWeight() * 1.10);
    }

    @Override
    public void visit(Scroll scroll) {
        // Scrolls are paper: half their listed weight in practice.
        addWeight(scroll, scroll.getWeight() * 0.50);
    }

    @Override
    public void visit(Ring ring) {
        // Rings are tiny: ignore listed weight, count a flat 0.1 unit.
        addWeight(ring, 0.1);
    }

    @Override
    public void visit(Armor armor) {
        // Armor is bulkier than its listed weight in real carry terms.
        addWeight(armor, armor.getWeight() * 1.25);
    }

    private void addWeight(Artifact artifact, double effective) {
        totalWeight += effective;
        items++;
        System.out.printf("  [WeightCalculator] '%s' carries as %.2f units (listed %d).%n",
                artifact.getName(), effective, artifact.getWeight());
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public int getItems() {
        return items;
    }
}
