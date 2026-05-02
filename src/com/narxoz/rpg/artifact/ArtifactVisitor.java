package com.narxoz.rpg.artifact;

/**
 * Visitor for heterogeneous vault artifacts.
 *
 * Concrete visitors implement these overloads to demonstrate double dispatch
 * without instanceof chains.
 */
public interface ArtifactVisitor {

    /**
     * Visits a weapon artifact.
     *
     * @param weapon the weapon being visited
     */
    void visit(Weapon weapon);

    /**
     * Visits a potion artifact.
     *
     * @param potion the potion being visited
     */
    void visit(Potion potion);

    /**
     * Visits a scroll artifact.
     *
     * @param scroll the scroll being visited
     */
    void visit(Scroll scroll);

    /**
     * Visits a ring artifact.
     *
     * @param ring the ring being visited
     */
    void visit(Ring ring);

    /**
     * Visits an armor artifact.
     *
     * @param armor the armor being visited
     */
    void visit(Armor armor);
}
