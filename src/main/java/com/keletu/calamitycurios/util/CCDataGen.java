package com.keletu.calamitycurios.util;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCTagData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class CCDataGen {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(output, lookupProvider, CalamityCurios.MODID, existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
            }
        };

        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new CCTagData(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }
}