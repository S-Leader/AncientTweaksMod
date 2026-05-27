package com.keletu.ancienttweaks;


import net.neoforged.neoforge.common.ModConfigSpec;

public class AncientTweaksConfig {

    public static final ModConfigSpec COMMON_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_AW_TWEAKS;
    public static final ModConfigSpec.BooleanValue ENABLE_BAUBLES;
    public static final ModConfigSpec.BooleanValue ENABLE_WAFFLES_IRON;
    public static final ModConfigSpec.BooleanValue ENABLE_ARMOR_BREAK_TWEAKS;

    public static final ConfigCalamityBaubles CONFIG_CALAMITY;
    public static final ConfigArmorCrumbling CONFIG_CRUMBLING;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General Options").push("general");

        ENABLE_AW_TWEAKS = builder.comment("Enable AWTweaks").define("AWTweaks", false);
        ENABLE_BAUBLES = builder.comment("Enable BaubleTweaks").define("BaubleTweaks", true);
        ENABLE_WAFFLES_IRON = builder.comment("Enable Waffles Iron").define("EnableWafflesIron", true);
        ENABLE_ARMOR_BREAK_TWEAKS = builder.comment("Enable Armor Break Tweaks").define("ArmorBreakTweaks", false);

        builder.pop();

        builder.comment("Config Options For Calamity Baubles").push("Calamity Baubles Config");

        CONFIG_CALAMITY = new ConfigCalamityBaubles(builder);
        CONFIG_CRUMBLING = new ConfigArmorCrumbling(builder);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    public static class ConfigCalamityBaubles {

        public final ModConfigSpec.DoubleValue crabClawAttackBonus;
        public final ModConfigSpec.DoubleValue crabClawReflectDamage;
        public final ModConfigSpec.IntValue crabClawArmorWeaknessTime;

        public final ModConfigSpec.DoubleValue baroclawAttackBonus;
        public final ModConfigSpec.DoubleValue baroclawArmorBonus;
        public final ModConfigSpec.DoubleValue baroclawReflectDamage;
        public final ModConfigSpec.IntValue baroclawArmorWeaknessTime;

        public final ModConfigSpec.DoubleValue giantShellArmor;
        public final ModConfigSpec.DoubleValue giantShellWeaknessArmor;
        public final ModConfigSpec.DoubleValue giantShellDashSlowness;
        public final ModConfigSpec.IntValue giantShellWeaknessTime;

        public final ModConfigSpec.DoubleValue giantTurtleShellArmor;
        public final ModConfigSpec.DoubleValue giantTurtleShellWeaknessArmor;
        public final ModConfigSpec.DoubleValue giantTurtleShellDashSlowness;
        public final ModConfigSpec.IntValue giantTurtleShellWeaknessTime;

        public final ModConfigSpec.DoubleValue greenJellySpeed;
        public final ModConfigSpec.DoubleValue greenJellyJumpHeight;

        public final ModConfigSpec.IntValue pinkJellyRegenerationTime;
        public final ModConfigSpec.IntValue pinkJellyRegenerationStrength;

        public final ModConfigSpec.IntValue grandGelatinRegenerationTime;
        public final ModConfigSpec.IntValue grandGelatinRegenerationLevel;

        public final ModConfigSpec.DoubleValue theAbsorberReflectDamage;
        public final ModConfigSpec.DoubleValue theAbsorberReflectDamageMinimum;
        public final ModConfigSpec.IntValue theAbsorberWeaknessTime;
        public final ModConfigSpec.IntValue theAbsorberWitherTime;
        public final ModConfigSpec.IntValue theAbsorberWitherLevel;
        public final ModConfigSpec.DoubleValue theAbsorberShellArmor;

        public final ModConfigSpec.IntValue theAbsorberResistanceTime;
        public final ModConfigSpec.IntValue theAbsorberResistanceLevel;
        public final ModConfigSpec.IntValue theAbsorberStrengthTime;
        public final ModConfigSpec.IntValue theAbsorberStrengthLevel;
        public final ModConfigSpec.IntValue theAbsorberRegenerationTime;
        public final ModConfigSpec.IntValue theAbsorberRegenerationLevel;

        public final ModConfigSpec.DoubleValue theAbsorberAbsorb;

        public final ModConfigSpec.IntValue bubbleHeartHeight;

        public final ModConfigSpec.IntValue roverDriveShieldCount;
        public final ModConfigSpec.IntValue roverDriveShieldCooldown;
        public final ModConfigSpec.IntValue roverDriveShieldArmor;

        public final ModConfigSpec.IntValue theSpongeShieldCount;
        public final ModConfigSpec.IntValue theSpongeShieldCooldown;
        public final ModConfigSpec.IntValue theSpongeShieldArmor;
        public final ModConfigSpec.DoubleValue theSpongeDamageDiscount;

        public ConfigCalamityBaubles(ModConfigSpec.Builder builder) {
            crabClawAttackBonus = builder.comment("how much damage will crab carapace add (percentage)").defineInRange("CrabClaw Attack Bonus", 0.07D, 0.0D, 10.0D);

            crabClawReflectDamage = builder.comment("how much damage reflect to attacker when player worn CrabClaw (percentage)").defineInRange("CrabClaw Damage Reflection", 0.25D, 0.0D, 1.0D);

            crabClawArmorWeaknessTime = builder.comment("how long 'armor break' effect applies to attacker when player worn CrabClaw (second)").defineInRange("CrabClaw ArmorWeakness Time", 5, 0, 32676);

            baroclawAttackBonus = builder.comment("how much damage will baroclaw add (percentage)").defineInRange("Baroclaw Attack Bonus", 0.10D, 0.0D, 10.0D);

            baroclawArmorBonus = builder.comment("how much armor will crab carapace add (percentage)").defineInRange("CrabClaw Armor Bonus", 0.05D, 0.0D, 10.0D);

            baroclawReflectDamage = builder.comment("how much damage reflect to attacker when player worn Baroclaw (percentage)").defineInRange("Baroclaw Damage Reflection", 0.35D, 0.0D, 1.0D);

            baroclawArmorWeaknessTime = builder.comment("how long 'armor break' effect applies to attacker when player worn Baroclaw (second)").defineInRange("Baroclaw ArmorWeakness Time", 5, 0, 32676);

            giantShellArmor = builder.comment("how much armor points will giant shell add").defineInRange("Giant Shell Armor", 6.0D, 0.0D, 100.0D);

            giantShellWeaknessArmor = builder.comment("how much armor points will giant shell add when player be attacked").defineInRange("Giant Shell Weakness Armor", 3.0D, 0.0D, 100.0D);

            giantShellDashSlowness = builder.comment("how much dash speed decreased when player worn giant shell (percentage)").defineInRange("Giant Shell Dash Speed Decrease", 0.1D, 0.0D, 1.0D);

            giantShellWeaknessTime = builder.comment("how long will player armor decrease when worn giant shell (second)").defineInRange("Giant Shell Weakness Time", 5, 0, 32676);

            giantTurtleShellArmor = builder.comment("how much armor points will giant turtle shell add").defineInRange("Giant Turtle Shell Armor", 10.0D, 0.0D, 100.0D);

            giantTurtleShellWeaknessArmor = builder.comment("how much armor points will giant turtle shell add when player be attacked").defineInRange("Giant Turtle Shell Weakness Armor", 5.0D, 0.0D, 100.0D);

            giantTurtleShellDashSlowness = builder.comment("how much dash speed decreased when player worn giant turtle shell (percentage)").defineInRange("Giant Turtle Shell Dash Speed Decrease", 0.1D, 0.0D, 1.0D);

            giantTurtleShellWeaknessTime = builder.comment("how long will player armor decrease when worn giant turtle shell (second)").defineInRange("Giant Turtle Shell Weakness Time", 5, 0, 32676);

            greenJellySpeed = builder.comment("how much speed increase will green jelly give (percentage)").defineInRange("Green Jelly Speed Bonus", 0.12D, 0.0D, 1.0D);

            greenJellyJumpHeight = builder.comment("how much jump height increase will green jelly give (percentage)").defineInRange("Green Jelly Jump Height Bonus", 0.02D, 0.0D, 1.0D);

            pinkJellyRegenerationTime = builder.comment("how long regeneration get will player get when worn pink jelly (second)").defineInRange("Pink Jelly Regeneration Time", 5, 0, 32676);

            pinkJellyRegenerationStrength = builder.comment("which level regeneration get will player get when worn pink jelly").defineInRange("Pink Jelly Regeneration Level", 1, 1, 256);

            grandGelatinRegenerationTime = builder.comment("how long regeneration will player get when worn grand gelatin (second)").defineInRange("Grand Gelatin Regeneration Time", 5, 0, 32676);

            grandGelatinRegenerationLevel = builder.comment("which level regeneration will player get when worn grand gelatin").defineInRange("Grand Gelatin Regeneration Level", 1, 1, 256);

            theAbsorberReflectDamage = builder.comment("how much damage will reflection when player worn the absorber (percentage)").defineInRange("Absorber Damage Reflection", 0.35D, 0.0D, 1.0D);

            theAbsorberReflectDamageMinimum = builder.comment("how much minimum damage will reflection when player worn the absorber").defineInRange("Absorber Minimum Damage Reflection", 3.5D, 0.0D, 32676.0D);

            theAbsorberWeaknessTime = builder.comment("how long 'armor break' effect applies to attacker when player worn absorber (second)").defineInRange("Absorber ArmorWeakness Time", 10, 0, 32676);

            theAbsorberWitherTime = builder.comment("how long wither effect applies to attacker when player worn absorber (second)").defineInRange("Absorber Wither Time", 10, 0, 32676);

            theAbsorberWitherLevel = builder.comment("which level wither effect applies to attacker when player worn absorber").defineInRange("Absorber Wither Level", 2, 1, 256);

            theAbsorberShellArmor = builder.comment("how much armor points will absorber add").defineInRange("Absorber Armor", 10.0D, 0.0D, 100.0D);

            theAbsorberResistanceTime = builder.comment("how long resistance will player get when worn the absorber (second)").defineInRange("The Absorber Resistance Time", 5, 0, 32676);

            theAbsorberResistanceLevel = builder.comment("which level resistance will player get when worn the absorber").defineInRange("Absorber Resistance Level", 1, 1, 256);

            theAbsorberStrengthTime = builder.comment("how long strength will player get when worn the absorber (second)").defineInRange("The Absorber Strength Time", 5, 0, 32676);

            theAbsorberStrengthLevel = builder.comment("which level strength will player get when worn the absorber").defineInRange("Absorber Strength Level", 1, 1, 256);

            theAbsorberRegenerationTime = builder.comment("how long regeneration will player get when worn the absorber (second)").defineInRange("The Absorber Regeneration Time", 5, 0, 32676);

            theAbsorberRegenerationLevel = builder.comment("which level regeneration will player get when worn the absorber").defineInRange("Absorber Regeneration Level", 2, 1, 256);

            theAbsorberAbsorb = builder.comment("how many damage will transform to heal when player worn absorber (percentage)").defineInRange("Absorber Absorb", 0.05D, 0.0D, 1.0D);

            bubbleHeartHeight = builder.comment("The height of the Bubble Shield Heart bar.").defineInRange("Bubble Heart Height", 39, 0, 256);

            roverDriveShieldCount = builder.comment("how much damage will rover drive absorb").defineInRange("Rover Drive Reduction Count", 6, 1, 100);

            roverDriveShieldCooldown = builder.comment("how long will rover drive cooldown (second)").defineInRange("Rover Drive Cooldown", 10, 0, 32676);

            roverDriveShieldArmor = builder.comment("how much armor point when rover shield active").defineInRange("Rover Drive Armor", 5, 0, 20);

            theSpongeShieldCount = builder.comment("how much damage will the sponge absorb").defineInRange("The Sponge Reduction Count", 20, 1, 100);

            theSpongeShieldCooldown = builder.comment("how long will the sponge cooldown (second)").defineInRange("The Sponge Cooldown", 9, 0, 32676);

            theSpongeShieldArmor = builder.comment("how much armor point when the sponge active").defineInRange("The Sponge Armor", 15, 0, 20);

            theSpongeDamageDiscount = builder.comment("how much damage reduction will give when the sponge active (percentage)").defineInRange("The Sponge Damage Discount", 0.1D, 0.0D, 1.0D);
        }
    }


    public static class ConfigArmorCrumbling {
        public final ModConfigSpec.DoubleValue level1Damage;
        public final ModConfigSpec.DoubleValue level2Damage;
        public final ModConfigSpec.DoubleValue level3Damage;
        public final ModConfigSpec.DoubleValue level4Damage;
        public final ModConfigSpec.DoubleValue level5Damage;
        public final ModConfigSpec.IntValue fadeTimer;

        public ConfigArmorCrumbling(ModConfigSpec.Builder builder) {

            level1Damage = builder.comment("level 1 armor crumbling damage").defineInRange("Level1", 10D, 0D, 25565D);


            level2Damage = builder.comment("level 2 armor crumbling damage").defineInRange("Level2", 30, 0D, 25565D);


            level3Damage = builder.comment("level 3 armor crumbling damage").defineInRange("Level3", 50, 0D, 25565D);

            level4Damage = builder.comment("level 4 armor crumbling damage").defineInRange("Level4", 80, 0D, 25565D);

            level5Damage = builder.comment("level 5 armor crumbling damage").defineInRange("Level5", 80, 0D, 25565D);

            fadeTimer = builder.comment("Timer when Crumbling Effect Start Fade (Second)").defineInRange("Crumbling Decrease Timer", 5, 1, 25565);
        }
    }
}