# Homework 9 — Chronomancer's Vault: Visitor + Memento

## Background Story

The hero who climbed the Haunted Tower in Homework 8 reaches a sealed chamber beneath the summit: the **Chronomancer's Vault**. Inside are time crystals that can rewind a hero's state and arcane artifacts that only specialist appraisers can evaluate.

This assignment is built to force two separate behavioral patterns to appear for real:

- **Visitor** for appraising heterogeneous artifacts through double dispatch
- **Memento** for saving and restoring a hero's mutable state without exposing internals

The patterns interact at runtime, but they remain structurally independent. The vault can appraise any artifact inventory, and the hero can be rewound regardless of which visitors are used.

---

## Learning Objectives

By completing this assignment, you will:

- Apply the **Visitor** pattern to heterogeneous objects without `instanceof` dispatch
- Apply the **Memento** pattern to capture and restore state cleanly
- Use package boundaries to protect memento internals
- Practice keeping two behavioral patterns independent inside one system
- Build a demo that is readable, testable, and easy to grade from console output

---

## What Is Provided

The following files are given to you in skeleton form.

| File | Purpose |
|------|---------|
| `artifact/ArtifactVisitor.java` | Visitor interface with one overload per artifact type |
| `artifact/Artifact.java` | Abstract artifact base class |
| `artifact/Weapon.java` | Weapon skeleton |
| `artifact/Potion.java` | Potion skeleton |
| `artifact/Scroll.java` | Scroll skeleton |
| `artifact/Ring.java` | Ring skeleton |
| `artifact/Armor.java` | Armor skeleton |
| `artifact/Inventory.java` | Inventory traversal helper |
| `combatant/Hero.java` | Hero skeleton with memento hooks |
| `combatant/HeroMemento.java` | Immutable snapshot with package-private getters |
| `memento/Caretaker.java` | Snapshot storage shell |
| `vault/VaultRunResult.java` | Vault run summary data class |
| `vault/ChronomancerEngine.java` | Vault orchestration shell |
| `Main.java` | Entry point skeleton |

You may fill in the TODOs in these scaffold files, but do not rename, move, or weaken the package structure.

---

## What You Must Build

You are building **The Chronomancer's Vault** - a short RPG demo with:

- A mixed artifact inventory
- At least 3 concrete visitors
- A hero that can save and restore state through mementos
- A vault engine that prints a clear, narrated run

The design must stay close to the provided scaffold:

- `HeroMemento` stays in `com.narxoz.rpg.combatant`
- `Caretaker` stays in `com.narxoz.rpg.memento`
- `ArtifactVisitor` stays in `com.narxoz.rpg.artifact`

---

## Part 1: Visitor Pattern

### The Interface

`ArtifactVisitor` defines the double-dispatch entry points:

```java
void visit(Weapon weapon);
void visit(Potion potion);
void visit(Scroll scroll);
void visit(Ring ring);
void visit(Armor armor);
```

### Double Dispatch Contract

| Rule | Why it matters |
|------|----------------|
| Each artifact class implements `accept(ArtifactVisitor visitor)` | The artifact chooses the correct overload at runtime |
| `accept(visitor)` must call exactly one `visitor.visit(this)` | That is the double-dispatch pivot |
| `Inventory.accept(visitor)` must walk the whole list | The visitor sees heterogeneous artifacts through one traversal point |
| New reports must be added by creating new visitor classes | That proves open/closed behavior |

> **Critical requirement:** `accept(visitor)` must be the only method that calls `visitor.visit(this)`. Main, the engine, and helper code must not call `visitor.visit(weapon)` directly.

### Concrete Visitors

Implement at least **3 concrete visitors** in separate `.java` files.

Each visitor must:

- Implement all 5 `visit(...)` overloads
- Produce visibly different output or calculations
- Treat at least 2 artifact types differently in a way the grader can observe

Examples:

- `GoldAppraiser` - estimates resale value
- `EnchantmentScanner` - prints magical properties
- `CurseDetector` - flags dangerous artifacts
- `WeightCalculator` - proves open/closed extension in Part 4

### Anti-pattern Penalty Box - Visitor

If you do any of the following, the Visitor portion scores **0**:

> **Anti-pattern penalty box:** `instanceof Artifact` or any type-switch logic used to branch on artifact type.

> **Anti-pattern penalty box:** Reflection-based dispatch, a giant `if/else` ladder, or a visitor that only works for one artifact type.

> **Anti-pattern penalty box:** Modifying `Artifact.java` or the concrete artifact classes just to add a new visitor report instead of creating a new visitor class.

---

## Part 2: Memento Pattern

### The Memento Boundary

`HeroMemento` is the snapshot object. It lives in `combatant` next to `Hero`, but its getters are **package-private**.

That means:

- `Hero` can create and restore snapshots
- `Caretaker` can store and return snapshots
- `Caretaker` cannot inspect snapshot internals directly

> **Critical requirement:** `HeroMemento` must remain an opaque value to `Caretaker`. Do not add public getters, public fields, or helper methods that expose the saved state outside the originator package.

### The Caretaker API

`Caretaker` must provide these methods:

```java
void save(HeroMemento memento);
HeroMemento undo();
HeroMemento peek();
int size();
```

Expected behavior:

- `save` pushes a new snapshot
- `undo` removes and returns the most recent snapshot
- `peek` returns the most recent snapshot without removing it
- `size` reports how many snapshots are stored

### Originator Responsibilities

`Hero` owns the data that gets saved:

- HP
- mana
- gold
- inventory
- any other mutable fields you choose to keep in the hero

`Hero.createMemento()` and `Hero.restoreFromMemento(HeroMemento)` are the only originator-facing methods needed for the pattern.

### Anti-pattern Penalty Box - Memento

If you do any of the following, the Memento portion scores **0**:

> **Anti-pattern penalty box:** Public mutable fields on `HeroMemento`.

> **Anti-pattern penalty box:** `Caretaker` reading snapshot internals, calling getters on `HeroMemento`, or otherwise peeking inside the saved state.

> **Anti-pattern penalty box:** `Hero` restoring from anything other than a `HeroMemento`.

> **Anti-pattern penalty box:** Using `clone()` or a raw deep copy as a substitute for an actual memento boundary.

---

## Part 3: ChronomancerEngine + Demo

`ChronomancerEngine` is the orchestration shell. `Main.java` should use it to produce a readable vault run.

Your demo must show the following:

1. At least **2 heroes** starting with different initial states
2. A mixed inventory containing at least **5 artifact objects**
3. At least **3 concrete visitors** being applied through `Inventory.accept(visitor)`
4. At least **1 hero snapshot saved** before a vault event changes state
5. At least **1 restoration** from a saved memento after the change
6. Console output that clearly shows visitor-specific behavior
7. Console output that clearly shows hero state changing before and after rewind
8. A final `VaultRunResult` printed to the console

> **Critical requirement:** The demo must make the pattern boundaries visible in the output. The grader should be able to tell where appraisal happens, where the snapshot is taken, and where the rewind occurs.

---

## Part 4: Open/Closed Proof

Add a **4th visitor** after the initial demo works.

Requirement:

- The new visitor must be added without modifying any file under `artifact/`
- The new visitor must still work with `Inventory.accept(visitor)`
- The new visitor must produce output distinct from the first 3 visitors

This is the open/closed proof for Visitor. The artifact hierarchy must stay closed to modification when new reports are added.

If you add the 4th visitor by editing `Artifact.java`, `Weapon.java`, `Potion.java`, `Scroll.java`, `Ring.java`, `Armor.java`, or `Inventory.java`, the Visitor portion of the rubric receives **0**.

---

## Deliverables

Submit a ZIP file containing:

- All `.java` source files
- `README.md`, `ASSIGNMENT.md`, `QUICKSTART.md`, `STUDENT_CHECKLIST.md`, `FAQ.md`
- 2 UML diagrams
- No compiled `.class` files

The two diagrams are:

- **Diagram 1:** Visitor pattern - `ArtifactVisitor`, all artifact classes, and `Inventory`
- **Diagram 2:** Memento pattern - `Hero`, `HeroMemento`, `Caretaker`, and `ChronomancerEngine`

---

## Grading (15 points)

| Area | Points | What the grader checks |
|------|--------|------------------------|
| **Visitor** | 6 | Interface used correctly; `accept(visitor)` drives double dispatch; at least 3 concrete visitors; visible behavior differences; no `instanceof` dispatch; open/closed proof with a 4th visitor |
| **Memento** | 6 | `HeroMemento` keeps internals protected; `Caretaker` stores opaque snapshots; `Hero` creates/restores mementos correctly; no public leaking of snapshot state; no clone-based replacement |
| **Engine + Demo** | 3 | `ChronomancerEngine` and `Main.java` produce a clear vault run; snapshot/rewind/appraisal steps are visible; final `VaultRunResult` is printed |

Total: **15 points**
