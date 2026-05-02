# Quickstart Guide - Homework 9

## Prerequisites

- Java 17 or later installed
- A terminal / command prompt
- An IDE (optional but recommended)

Check your Java version:

```bash
java -version
```

You should see something like `java version "17.x.x"` or higher.

---

## Project Structure

```
homework-rpg-9/
├── ASSIGNMENT.md
├── README.md
├── QUICKSTART.md
├── STUDENT_CHECKLIST.md
├── FAQ.md
├── .gitignore
└── src/
    └── com/
        └── narxoz/
            └── rpg/
                ├── Main.java
                ├── artifact/
                │   ├── ArtifactVisitor.java
                │   ├── Artifact.java
                │   ├── Weapon.java
                │   ├── Potion.java
                │   ├── Scroll.java
                │   ├── Ring.java
                │   ├── Armor.java
                │   └── Inventory.java
                ├── combatant/
                │   ├── Hero.java
                │   └── HeroMemento.java
                ├── memento/
                │   └── Caretaker.java
                └── vault/
                    ├── ChronomancerEngine.java
                    └── VaultRunResult.java
```

You will add your concrete visitor classes and any helper classes alongside these packages.

---

## Compile and Run (Command Line)

From the `homework-rpg-9/` directory:

**On macOS / Linux:**

```bash
mkdir -p out
find src -name "*.java" | xargs javac -d out
java -cp out com.narxoz.rpg.Main
```

**On Windows (Command Prompt):**

```cmd
mkdir out
javac -d out src\com\narxoz\rpg\artifact\*.java ^
             src\com\narxoz\rpg\combatant\*.java ^
             src\com\narxoz\rpg\memento\*.java ^
             src\com\narxoz\rpg\vault\*.java ^
             src\com\narxoz\rpg\*.java
java -cp out com.narxoz.rpg.Main
```

> **Tip:** If you add new packages, compile with the `find` command on macOS / Linux so you do not have to keep updating file lists.

---

## IntelliJ IDEA Setup

1. Open IntelliJ - **File > Open** - select the `homework-rpg-9/` folder
2. Right-click `src/` - **Mark Directory as > Sources Root**
3. Go to **File > Project Structure > Project** - set **SDK** to Java 17
4. Right-click `Main.java` - **Run 'Main.main()'**

---

## VS Code Setup

1. Install the **Extension Pack for Java** from the VS Code marketplace
2. Open the `homework-rpg-9/` folder
3. VS Code should auto-detect the Java sources
4. Open `Main.java` - click **Run** above the `main` method

---

## Troubleshooting

**"package com.narxoz.rpg.X does not exist"**
You likely used the wrong package declaration or forgot to place the file under `src/com/narxoz/rpg/...`.

**"cannot find symbol: class HeroMemento"**
Make sure your imports point to `com.narxoz.rpg.combatant.HeroMemento`. The class is visible, but its accessors are intentionally package-private.

**"Main method not found"**
Ensure `Main.java` has `public static void main(String[] args)` and is in package `com.narxoz.rpg`.

**"cannot access HeroMemento getter"**
That is expected from `Caretaker`. Do not expose the snapshot internals with public getters.

**Compile error in scaffold files**
Re-check the package names and method signatures before changing the provided structure.

---

## Recommended Implementation Order

1. Fill in the simplest `accept(visitor)` method in `Weapon.java`
2. Add the same one-line `accept(visitor)` body to `Potion.java`
3. Add the same one-line `accept(visitor)` body to `Scroll.java`
4. Add the same one-line `accept(visitor)` body to `Ring.java`
5. Add the same one-line `accept(visitor)` body to `Armor.java`
6. Implement `Inventory.accept(visitor)` to walk the list
7. Implement `Hero.createMemento()` and `Hero.restoreFromMemento(...)`
8. Implement `Caretaker.save`, `undo`, `peek`, and `size`
9. Add your concrete visitor classes and then the `ChronomancerEngine` demo flow
10. Finish `Main.java` so it prints the banner and runs the demo
