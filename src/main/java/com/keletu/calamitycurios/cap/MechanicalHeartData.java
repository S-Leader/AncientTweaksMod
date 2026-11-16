package com.keletu.calamitycurios.cap;

public final class MechanicalHeartData {

    private boolean synced;
    private boolean equipped;
    private long energy;
    private int experiencePerLevel = 300;
    private int maximumLevel = 5;

    public boolean needsSync(boolean equipped, long energy, int experiencePerLevel, int maximumLevel) {
        return !synced || this.equipped != equipped || this.energy != energy
                || this.experiencePerLevel != experiencePerLevel || this.maximumLevel != maximumLevel;
    }

    public void update(boolean equipped, long energy, int experiencePerLevel, int maximumLevel) {
        this.equipped = equipped;
        this.energy = Math.max(0L, energy);
        this.experiencePerLevel = Math.max(1, experiencePerLevel);
        this.maximumLevel = Math.max(1, maximumLevel);
        synced = true;
    }

    public boolean equipped() {
        return equipped;
    }

    public int level() {
        return (int) Math.min(energy / experiencePerLevel, maximumLevel);
    }

    public float progress() {
        return level() >= maximumLevel ? 1.0F : (float) (energy % experiencePerLevel) / experiencePerLevel;
    }
}
