package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.*;
import static com.keletu.ancienttweaks.init.ATArmorMats.TANOOKI;
import com.keletu.ancienttweaks.item.TanookiArmor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ATItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AncientTweaks.MODID);
    public static final DeferredItem<Item> BAROCLAW = ITEMS.register("baroclaw", () -> new ItemBaroClaw(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final DeferredItem<Item> CRAWCARAPACE = ITEMS.register("craw_carapace", () -> new ItemCrabClaw(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> GIANTSHELL = ITEMS.register("giant_shell", () -> new ItemGiantShell(new Item.Properties().stacksTo(1).durability(999).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> giantTurtleShell = ITEMS.register("giant_tortoise_shell", () -> new ItemGiantTurtleShell(new Item.Properties().stacksTo(1).durability(1500).rarity(Rarity.RARE)));

    public static final DeferredItem<Item> blueJelly = ITEMS.register("cleansing_jelly", () -> new ItemJelly(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> pinkJelly = ITEMS.register("life_jelly", () -> new ItemJelly(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> greenJelly = ITEMS.register("vital_jelly", () -> new ItemJelly(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> grandGelatin = ITEMS.register("grand_gelatin", () -> new ItemJelly(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> theAbsorber = ITEMS.register("the_absorber", () -> new ItemTheAbsorber(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> roverDrive = ITEMS.register("rover_drive", () -> new ItemRoverDrive(new Item.Properties()));

    public static final DeferredItem<Item> theSponge = ITEMS.register("the_sponge", () -> new ItemTheSponge(new Item.Properties()));

    public static final DeferredItem<Item> tanooki_hood = ITEMS.register("tanooki_hood", () -> new TanookiArmor(TANOOKI, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> tanooki_chest = ITEMS.register("tanooki_chest", () -> new TanookiArmor(TANOOKI, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> tanooki_legs = ITEMS.register("tanooki_legs", () -> new TanookiArmor(TANOOKI, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> tanooki_shoes = ITEMS.register("tanooki_shoes", () -> new TanookiArmor(TANOOKI, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

}
