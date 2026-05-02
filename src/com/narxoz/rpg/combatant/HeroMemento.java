package com.narxoz.rpg.combatant;

import com.narxoz.rpg.artifact.Artifact;
import java.util.List;

/**
 * Immutable snapshot of a hero's mutable state.
 *
 * This class intentionally lives next to {@link Hero} so the originator can
 * restore itself without exposing internals to other packages.
 */
public final class HeroMemento {

    private final String name;
    private final int hp;
    private final int mana;
    private final int gold;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private final List<Artifact> inventorySnapshot;

    HeroMemento(String name,
                int hp,
                int mana,
                int gold,
                int maxHp,
                int attackPower,
                int defense,
                List<Artifact> inventorySnapshot) {
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.gold = gold;
        this.maxHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventorySnapshot = inventorySnapshot == null ? List.of() : List.copyOf(inventorySnapshot);
    }

    String getName() {
        return name;
    }

    int getHp() {
        return hp;
    }

    int getMana() {
        return mana;
    }

    int getGold() {
        return gold;
    }

    int getMaxHp() {
        return maxHp;
    }

    int getAttackPower() {
        return attackPower;
    }

    int getDefense() {
        return defense;
    }

    List<Artifact> getInventorySnapshot() {
        return inventorySnapshot;
    }
}
