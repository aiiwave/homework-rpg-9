package com.narxoz.rpg.vault;

import com.narxoz.rpg.artifact.Armor;
import com.narxoz.rpg.artifact.Inventory;
import com.narxoz.rpg.artifact.Potion;
import com.narxoz.rpg.artifact.Ring;
import com.narxoz.rpg.artifact.Scroll;
import com.narxoz.rpg.artifact.Weapon;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.HeroMemento;
import com.narxoz.rpg.memento.Caretaker;
import com.narxoz.rpg.visitor.CurseDetector;
import com.narxoz.rpg.visitor.EnchantmentScanner;
import com.narxoz.rpg.visitor.GoldAppraiser;
import com.narxoz.rpg.visitor.WeightCalculator;

import java.util.List;

/**
 * Orchestrates the Chronomancer's Vault demo run.
 *
 * The engine wires together the Visitor and Memento patterns:
 *   - Visitor: an Inventory of mixed artifacts is appraised by 4 visitors
 *   - Memento: each hero is snapshotted before a vault trap, then rewound
 *
 * The two patterns stay structurally independent - appraisal does not
 * touch hero state, and rewind does not depend on the visitors.
 */
public class ChronomancerEngine {

    /**
     * Runs the vault sequence for the supplied party.
     *
     * @param party the heroes entering the vault
     * @return a {@link VaultRunResult} summary of the run
     */
    public VaultRunResult runVault(List<Hero> party) {
        if (party == null || party.isEmpty()) {
            System.out.println("No heroes entered the vault. Nothing to do.");
            return new VaultRunResult(0, 0, 0);
        }

        // ============================================================
        // STAGE 1: build the vault's mixed artifact inventory
        // ============================================================
        System.out.println();
        System.out.println("--- STAGE 1: Vault inventory unsealed ---");
        Inventory vaultInventory = buildVaultInventory();
        System.out.printf("Vault inventory contains %d artifacts.%n", vaultInventory.size());

        // ============================================================
        // STAGE 2: VISITOR PATTERN - appraise the vault inventory
        // ============================================================
        System.out.println();
        System.out.println("--- STAGE 2: Visitor pattern - appraising inventory ---");

        GoldAppraiser appraiser = new GoldAppraiser();
        EnchantmentScanner scanner = new EnchantmentScanner();
        CurseDetector detector = new CurseDetector();

        System.out.println();
        System.out.println(">> Visitor 1: GoldAppraiser");
        vaultInventory.accept(appraiser);
        System.out.printf("   => total resale value: %d gold across %d items%n",
                appraiser.getTotalValue(), appraiser.getItemsAppraised());

        System.out.println();
        System.out.println(">> Visitor 2: EnchantmentScanner");
        vaultInventory.accept(scanner);
        System.out.printf("   => magical: %d, mundane: %d%n",
                scanner.getMagicalItems(), scanner.getMundaneItems());

        System.out.println();
        System.out.println(">> Visitor 3: CurseDetector");
        vaultInventory.accept(detector);
        System.out.printf("   => cursed flagged: %d, safe: %d%n",
                detector.getCursedFound(), detector.getSafeCount());

        // ============================================================
        // STAGE 3: OPEN/CLOSED PROOF - add a 4th visitor with no edits
        //          to any artifact class
        // ============================================================
        System.out.println();
        System.out.println("--- STAGE 3: Open/Closed proof - 4th visitor added ---");
        WeightCalculator weigher = new WeightCalculator();
        System.out.println(">> Visitor 4: WeightCalculator");
        vaultInventory.accept(weigher);
        System.out.printf("   => total carry weight: %.2f units across %d items%n",
                weigher.getTotalWeight(), weigher.getItems());

        int totalAppraised = appraiser.getItemsAppraised()
                + scanner.getMagicalItems() + scanner.getMundaneItems()
                + detector.getCursedFound() + detector.getSafeCount()
                + weigher.getItems();

        // ============================================================
        // STAGE 4: MEMENTO PATTERN - snapshot, trap, rewind for each hero
        // ============================================================
        System.out.println();
        System.out.println("--- STAGE 4: Memento pattern - time-crystal snapshots ---");

        Caretaker caretaker = new Caretaker();
        int mementosCreated = 0;
        int restoredCount = 0;

        for (Hero hero : party) {
            System.out.println();
            System.out.printf("Hero entering the rewind chamber: %s%n", hero);

            // Save snapshot BEFORE the vault event.
            HeroMemento snapshot = hero.createMemento();
            caretaker.save(snapshot);
            mementosCreated++;
            System.out.printf("  -> snapshot saved (caretaker history size = %d)%n",
                    caretaker.size());

            // Vault trap fires: hero loses HP, mana, and gold.
            System.out.println("  ** Vault trap triggered: spike-rune deals 30 damage, "
                    + "drains 5 mana, scatters 25 gold **");
            hero.takeDamage(30);
            hero.spendMana(5);
            hero.spendGold(25);
            System.out.printf("  -> after trap: %s%n", hero);

            // Optional: peek to confirm the snapshot is still there.
            System.out.printf("  -> caretaker.peek() returned a snapshot? %s%n",
                    caretaker.peek() != null);

            // Rewind via Memento.
            System.out.println("  ** Chronomancer activates time crystal - rewinding **");
            HeroMemento restored = caretaker.undo();
            hero.restoreFromMemento(restored);
            restoredCount++;
            System.out.printf("  -> after rewind: %s%n", hero);
            System.out.printf("  -> caretaker history size after undo = %d%n",
                    caretaker.size());
        }

        // ============================================================
        // STAGE 5: build the run result
        // ============================================================
        System.out.println();
        System.out.println("--- STAGE 5: Vault run summary ---");
        return new VaultRunResult(totalAppraised, mementosCreated, restoredCount);
    }

    private Inventory buildVaultInventory() {
        Inventory inv = new Inventory();
        inv.addArtifact(new Weapon("Iron Sword", 50, 8, 7));
        inv.addArtifact(new Weapon("Whisper-Blade", 40, 5, 16));   // suspicious
        inv.addArtifact(new Potion("Healing Draught", 30, 1, 25));
        inv.addArtifact(new Potion("Murky Vial", 20, 1, 0));        // poison
        inv.addArtifact(new Scroll("Scroll of Light", 75, 1, "Illuminate"));
        inv.addArtifact(new Scroll("Forbidden Codex", 200, 2, "Doombringer")); // cursed
        inv.addArtifact(new Ring("Ring of Vigor", 120, 1, 4));
        inv.addArtifact(new Ring("Tarnished Band", 15, 1, -2));     // cursed
        inv.addArtifact(new Armor("Steel Plate", 180, 20, 12));
        return inv;
    }
}
