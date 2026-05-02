package com.narxoz.rpg.vault;

/**
 * Summary of a Chronomancer's Vault run.
 */
public class VaultRunResult {

    private final int artifactsAppraised;
    private final int mementosCreated;
    private final int restoredCount;

    public VaultRunResult(int artifactsAppraised, int mementosCreated, int restoredCount) {
        this.artifactsAppraised = artifactsAppraised;
        this.mementosCreated = mementosCreated;
        this.restoredCount = restoredCount;
    }

    public int getArtifactsAppraised() {
        return artifactsAppraised;
    }

    public int getMementosCreated() {
        return mementosCreated;
    }

    public int getRestoredCount() {
        return restoredCount;
    }

    @Override
    public String toString() {
        return "VaultRunResult{"
                + "artifactsAppraised=" + artifactsAppraised
                + ", mementosCreated=" + mementosCreated
                + ", restoredCount=" + restoredCount
                + '}';
    }
}
