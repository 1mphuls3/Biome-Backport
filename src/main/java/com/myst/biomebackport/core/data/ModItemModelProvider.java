package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.block.ChiseledBookshelfBlock;
import com.myst.biomebackport.common.block.PinkPetalsBlock;
import com.myst.biomebackport.common.item.HangingSignItem;
import com.myst.biomebackport.core.registry.ItemRegistry;
import com.myst.biomebackport.core.helper.DataHelper;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BiomeBackport.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());

        DataHelper.takeAll(items, i -> ((i.get() instanceof SwordItem) || (i.get() instanceof PickaxeItem) || (i.get() instanceof AxeItem) ||
                (i.get() instanceof ShovelItem) || (i.get() instanceof HoeItem || i.get() == ItemRegistry.CHISEL.get()))).forEach(this::handheldItem);
        DataHelper.takeAll(items, i -> i.get() instanceof ForgeSpawnEggItem).forEach(this::eggItem);

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock).forEach(this::doublePlantBlock);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof BushBlock).forEach(this::plantBlock);

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof WallBlock).forEach(this::wallBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoorBlock).forEach(this::generatedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof TrapDoorBlock).forEach(this::trapdoorBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof PressurePlateBlock).forEach(this::pressurePlateBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof ButtonBlock).forEach(this::buttonBlockItem);

        DataHelper.takeAll(items, i -> i.get() instanceof SignItem || i.get() instanceof HangingSignItem).forEach(this::generatedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem block && block.getBlock() instanceof PinkPetalsBlock).forEach(this::generatedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem block && block.getBlock() instanceof ChiseledBookshelfBlock).forEach(this::inventoryBlockItem);

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::translucentBlockItem);

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem item && !(item.getBlock() instanceof PinkPetalsBlock || item.getBlock() instanceof ButtonBlock
                || item.getBlock() instanceof PressurePlateBlock || item.getBlock() instanceof FenceBlock || item.getBlock() instanceof FenceGateBlock)).forEach(this::blockItem);
        DataHelper.takeAll(items, i -> !(i.get() instanceof BlockItem || i.get() instanceof TieredItem)).forEach(this::generatedItem);
    }

    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

    private void blockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name)));
    }

    private void translucentBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name))).renderType("translucent");
    }

    private void handheldItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", modPath("item/" + name));
    }

    private void generatedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", modPath("item/" + name));
    }

    private void inventoryBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, modPath("block/" + name + "_inventory"));
    }

    private void doublePlantBlock(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", modPath("block/" + name + "_top"));
    }

    private void plantBlock(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", modPath("block/" + name));
    }
    private void wallBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallInventory(name, modPath("block/" + baseName));
    }

    private void fenceBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name + "_inventory")));
    }

    private void trapdoorBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name + "_bottom")));
    }

    private void pressurePlateBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name + "_up")));
    }

    private void buttonBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modPath("block/" + name + "_inventory")));
    }

    private void eggItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, new ResourceLocation("item/template_spawn_egg"));
    }
}
