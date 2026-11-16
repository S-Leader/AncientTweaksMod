package com.keletu.calamitycurios.cap;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class CommunityProgress implements INBTSerializable<CompoundTag> {

    private final Set<String> defeatedBosses = new LinkedHashSet<>();
    private int clientBossCount;
    private int clientMaximum;
    private boolean synced;
    private int lastSentBossCount = -1;
    private int lastSentMaximum = -1;

    public boolean recordKill(ResourceLocation entityId) {
        return defeatedBosses.add(entityId.toString());
    }

    public Set<String> defeatedBosses() {
        return Collections.unmodifiableSet(defeatedBosses);
    }

    public boolean needsSync(int bossCount, int maximum) {
        return bossCount != lastSentBossCount || maximum != lastSentMaximum;
    }

    public void markSent(int bossCount, int maximum) {
        lastSentBossCount = bossCount;
        lastSentMaximum = maximum;
    }

    public void receiveSync(int bossCount, int maximum) {
        clientBossCount = Math.max(0, bossCount);
        clientMaximum = Math.max(0, maximum);
        synced = true;
    }

    public boolean hasClientSummary() {
        return synced;
    }

    public int clientBossCount() {
        return clientBossCount;
    }

    public int clientMaximum() {
        return clientMaximum;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag bosses = new ListTag();
        defeatedBosses.forEach(id -> bosses.add(StringTag.valueOf(id)));
        tag.put("DefeatedBosses", bosses);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        defeatedBosses.clear();
        ListTag bosses = tag.getList("DefeatedBosses", Tag.TAG_STRING);
        for (int i = 0; i < bosses.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(bosses.getString(i));
            if (id != null) {
                recordKill(id);
            }
        }
        lastSentBossCount = -1;
        lastSentMaximum = -1;
    }
}
