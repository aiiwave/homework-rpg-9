# FAQ - Homework 9: Chronomancer's Vault

---

## Visitor Pattern

**Q: What is the difference between Visitor and Strategy?**  
A: They can look similar in code, but they solve different problems. **Strategy** is about choosing one algorithm from several interchangeable algorithms. **Visitor** is about adding new operations across a fixed set of object types. In this assignment, the artifact classes stay stable while you add new appraisers like `GoldAppraiser`, `EnchantmentScanner`, or `CurseDetector`. That is Visitor, not Strategy.

**Q: What does double dispatch mean here?**  
A: The artifact chooses the right overloaded `visit(...)` method by calling `accept(visitor)`, and the visitor then receives the concrete artifact type. The dispatch happens twice: once from the object to `accept`, and once from `accept` to the correct `visit` overload.

**Q: Why must `accept(visitor)` be the only method that calls `visitor.visit(this)`?**  
A: Because that is the pattern boundary. If the engine or a helper class calls `visitor.visit(weapon)` directly, you are bypassing the point of the exercise and usually creating type-specific control flow in the wrong place.

**Q: Can I use `instanceof` inside a visitor?**  
A: No. That turns Visitor back into a type-switch. Each visitor should rely on the overloaded `visit(...)` methods, not on manual type inspection.

**Q: Can I make `ArtifactVisitor` generic and return a value from each `visit` method?**  
A: Yes, if you want to. A generic visitor can be valid. Just keep the overloads concrete and make sure every artifact type is still handled through double dispatch.

**Q: Can one visitor produce textual output while another computes a number?**  
A: Yes. That is a good way to make the visitors measurably different. For example, one visitor can print lore, another can total gold value, and another can count cursed items.

---

## Memento Pattern

**Q: Why is `HeroMemento` in `combatant` instead of `memento`?**  
A: Because the originator owns the snapshot data. `Hero` is the object whose state is being saved, so placing `HeroMemento` next to `Hero` reinforces that ownership. The caretaker lives in a different package and treats the snapshot as opaque.

**Q: Why are the getters on `HeroMemento` package-private?**  
A: That is the enforcement trick. `Hero` can read them when restoring, but `Caretaker` cannot inspect the saved state directly. The compiler helps enforce the memento boundary.

**Q: Can my `Caretaker` be a singleton?**  
A: It can be, but it does not need to be. A simple stack-like object is easier to read and grade. The important part is that it stores mementos without peeking inside them.

**Q: Is a deep copy the same thing as a memento?**  
A: Not automatically. A memento is an encapsulation boundary, not just a copied object graph. If you expose the copied internals freely, you lose the pattern's point. The assignment requires the snapshot to stay opaque to the caretaker.

**Q: Can `Hero.restoreFromMemento(...)` accept a map or a cloned object instead?**  
A: No. The hero must restore from `HeroMemento`. That keeps the intent clear and preserves the package-boundary enforcement.

**Q: Can the caretaker call getters on the memento if they are package-private?**  
A: No. That is the point of putting the caretaker in a different package. The compiler should block that access.

---

## Independence of the Two Patterns

**Q: How do Visitor and Memento relate in this assignment?**  
A: They interact at runtime, but they solve different problems. Visitor appraises the inventory. Memento saves and restores the hero. The same run can use both, but neither pattern depends structurally on the other.

**Q: Can a visitor trigger a memento save?**  
A: It can, but keep the code clean. Usually the engine or demo flow is a better place to decide when to snapshot state. The visitor should focus on artifact-specific behavior.

**Q: Can the rewind happen after a visitor changes the inventory?**  
A: Yes. That is a good demo sequence because it shows both patterns in the same run. Just make sure the output clearly separates the appraisal step from the save/restore step.

---

## Engine + Demo

**Q: What should `ChronomancerEngine` do?**  
A: It should orchestrate the run: build or receive heroes, create an inventory, apply visitors, save a memento, simulate a vault event, restore the hero, and return a `VaultRunResult`.

**Q: How much output is enough?**  
A: Enough to prove the patterns are working. The grader should be able to see appraisal, snapshot, rewind, and final summary without reading your source code.

**Q: Do I need at least 2 heroes?**  
A: Yes. The assignment expects a small party or at least two differently configured heroes so the demo feels like an RPG scenario instead of a single-object test.

**Q: What if the demo is short?**  
A: Short is fine if it is clear. The important part is that the required behaviors are visible and the final result object is printed.

---

## General

**Q: Do I need Maven or Gradle?**  
A: No. Plain `javac` is enough.

**Q: Can I add helper classes?**  
A: Yes. Helper classes are fine if they support the demo and do not weaken the package boundaries.

**Q: Should I write unit tests?**  
A: Not required. A clear `Main.java` demo is the primary deliverable.

**Q: Can I use streams or lambdas?**  
A: Yes. Use them if they make the traversal code cleaner, but do not obscure the Visitor or Memento flow.

**Q: Can I modify the scaffold files?**  
A: You may fill in the TODOs in the provided scaffold files. Do not rename them or move them out of their packages.
