package com.narxoz.rpg;

import com.narxoz.rpg.artifact.Inventory;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.vault.ChronomancerEngine;
import com.narxoz.rpg.vault.VaultRunResult;

import java.util.List;

/**
 * Entry point for Homework 9 - Chronomancer's Vault: Visitor + Memento.
 *
 * Builds 2 heroes with different starting states, hands them to the
 * {@link ChronomancerEngine}, and prints the final {@link VaultRunResult}.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 9 Demo: Visitor + Memento ===");

        // 1. Create at least 2 heroes with different starting states.
        Inventory aragornInv = new Inventory();
        aragornInv.addArtifact(new Weapon("Anduril", 300, 10, 18));
        aragornInv.addArtifact(new Potion("Athelas", 25, 1, 15));

        Hero aragorn = new Hero(
                "Aragorn",
                /*hp*/ 120,
                /*mana*/ 20,
                /*attack*/ 22,
                /*defense*/ 14,
                /*gold*/ 150,
                aragornInv
        );

        Inventory gandalfInv = new Inventory();
        gandalfInv.addArtifact(new Scroll("Spell of Light", 100, 1, "Lumos"));
        gandalfInv.addArtifact(new Ring("Narya", 500, 1, 9));

        Hero gandalf = new Hero(
                "Gandalf",
                /*hp*/ 80,
                /*mana*/ 200,
                /*attack*/ 12,
                /*defense*/ 8,
                /*gold*/ 60,
                gandalfInv
        );

        System.out.println();
        System.out.println("Initial party:");
        System.out.println("  - " + aragorn);
        System.out.println("  - " + gandalf);

        // 2-5. Run the ChronomancerEngine demo sequence (handles inventory,
        //      visitors, snapshot, trap, and rewind internally).
        ChronomancerEngine engine = new ChronomancerEngine();
        VaultRunResult result = engine.runVault(List.of(aragorn, gandalf));

        // 6. Print a final VaultRunResult summary.
        System.out.println();
        System.out.println("Final party state:");
        System.out.println("  - " + aragorn);
        System.out.println("  - " + gandalf);

        System.out.println();
        System.out.println("=== Final Result ===");
        System.out.println(result);
    }
}
