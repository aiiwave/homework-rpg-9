package com.narxoz.rpg.artifact;

/**
 * Base type for all vault artifacts.
 *
 * Concrete artifact types remain simple data holders and forward visitation
 * through the {@link ArtifactVisitor} double-dispatch entry point.
 */
public abstract class Artifact {

    private final String name;
    private final int value;
    private final int weight;

    protected Artifact(String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Accepts a visitor.
     *
     * @param visitor the visitor to dispatch to
     */
    public abstract void accept(ArtifactVisitor visitor);
}
