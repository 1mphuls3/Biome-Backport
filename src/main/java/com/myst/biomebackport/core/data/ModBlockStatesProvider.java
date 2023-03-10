package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.block.DecoratedPotBlock;
import com.myst.biomebackport.common.block.ModSlabBlock;
import com.myst.biomebackport.common.block.ModStairBlock;
import com.myst.biomebackport.common.block.PinkPetalsBlock;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.block.ModLogBlock;
import com.myst.biomebackport.core.block.ModWoodBlock;
import com.myst.biomebackport.core.helper.DataHelper;
import com.myst.biomebackport.core.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.function.Function;

import static com.myst.biomebackport.BiomeBackport.modPath;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BiomeBackport.MODID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "BiomeBackport BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        DataHelper.takeAll(blocks, i -> i.get() instanceof LeavesBlock).forEach(this::cutoutBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof AbstractGlassBlock).forEach(this::translucentBlock);

        DataHelper.takeAll(blocks, i -> (i.get() instanceof ModWoodBlock) || i.getId().toString().contains("wood")).forEach(this::woodBlock);
        DataHelper.takeAll(blocks, i -> (i.get() instanceof ModLogBlock || i.get() instanceof RotatedPillarBlock) && i.getId().toString().contains("log")).forEach(this::logBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof RotatedPillarBlock).forEach(this::logBlock);

        DataHelper.takeAll(blocks, i -> i.get() instanceof StairBlock).forEach(this::stairsBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallBlock).forEach(this::wallBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof DoorBlock).forEach(this::doorBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof ButtonBlock).forEach(this::buttonBlock);

        DataHelper.takeAll(blocks, i -> i.get() instanceof TallFlowerBlock).forEach(this::tallPlantBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof BushBlock && !(i.get() instanceof PinkPetalsBlock)).forEach(this::plantBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof FlowerPotBlock).forEach(this::flowerPotBlock);

        DataHelper.takeAll(blocks, i -> !(i.get() instanceof RotatedPillarBlock || i.get() instanceof ModLogBlock || i.get() instanceof ModBlock
                || i.get() instanceof SlabBlock || i.get() instanceof LiquidBlock || i.get() instanceof PinkPetalsBlock || i.get() instanceof SignBlock
                || i.get() instanceof ButtonBlock || i.get() instanceof PressurePlateBlock || i.get() instanceof FenceBlock
                || i.get() instanceof FenceGateBlock || i.get() instanceof DecoratedPotBlock)).forEach(this::basicBlock);

        Collection<RegistryObject<Block>> slabs = DataHelper.takeAll(blocks, b -> b.get() instanceof ModSlabBlock);
        slabs.forEach(this::slabBlock);
    }

    public void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void translucentBlock(RegistryObject<Block> blockRegistryObject) {
        basicBlockWithRendertype(blockRegistryObject, "translucent");
    }

    public void cutoutBlock(RegistryObject<Block> blockRegistryObject) {
        basicBlockWithRendertype(blockRegistryObject, "cutout");
    }

    public void basicBlockWithRendertype(RegistryObject<Block> blockRegistryObject, String renderType) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String modelLocation = "block/cube_all";
        ModelFile block = models().withExistingParent(name, new ResourceLocation(modelLocation)).texture("all", modPath("block/" + name)).renderType(renderType);
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(block).build());
    }

    public void woodBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length()-5) + "_log";
        ModelFile vertical = models().withExistingParent(name, new ResourceLocation("block/cube_column"))
                .texture("end", modPath("block/" + baseName)).texture("side", modPath("block/" + baseName));
        ModelFile horizontal = models().withExistingParent(name + "_horizontal", new ResourceLocation("block/cube_column_horizontal"))
                .texture("end", modPath("block/" + baseName)).texture("side", modPath("block/" + baseName));

        getVariantBuilder(blockRegistryObject.get()).partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.X).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel().partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(horizontal).rotationX(90).addModel().partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(vertical).addModel();}

    public void logBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile vertical = models().withExistingParent(name, new ResourceLocation("block/cube_column"))
                .texture("end", modPath("block/" + name + "_top")).texture("side", modPath("block/" + name));
        ModelFile horizontal = models().withExistingParent(name + "_horizontal", new ResourceLocation("block/cube_column_horizontal"))
                .texture("end", modPath("block/" + name + "_top")).texture("side", modPath("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.X).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel().partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(horizontal).rotationX(90).addModel().partialState()
                .with(RotatedPillarBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(vertical).addModel();
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();

        ModSlabBlock block = (ModSlabBlock) blockRegistryObject.get();
        Block baseBlock = block.baseState.getBlock();
        String baseName = Registry.BLOCK.getKey(baseBlock).getPath();
        slabBlock((SlabBlock) blockRegistryObject.get(), modPath(baseName), modPath("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();

        ModStairBlock block = (ModStairBlock) blockRegistryObject.get();
        Block baseBlock = block.baseState.getBlock();
        String baseName = Registry.BLOCK.getKey(baseBlock).getPath();
        stairsBlock((StairBlock) blockRegistryObject.get(), modPath("block/" + baseName));
    }

    public void wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length()-5);
        wallBlockWithRenderType((WallBlock) blockRegistryObject.get(), modPath("block/" + baseName), "cutout");
    }

    public void invisibleBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile empty = models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", modPath("item/flame")).renderType("translucent");
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(empty).build());
    }

    public void doorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        doorBlockWithRenderType((DoorBlock) blockRegistryObject.get(), modPath("block/" + name + "_bottom"), modPath("block/" + name + "_top"), "cutout");
    }

    public void trapdoorBlock(RegistryObject<Block> blockRegistryObject) {
        trapdoorBlockWithRenderType((TrapDoorBlock) blockRegistryObject.get(), blockTexture(blockRegistryObject.get()), true, "cutout");
    }

    public void pressurePlateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        if(!(baseName.contains("planks"))) {
            baseName += "_planks";
        }
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", modPath("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", modPath("block/" + baseName));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }

    public void buttonBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        if(!(baseName.contains("planks"))) {
            baseName += "_planks";
        }
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", modPath("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", modPath("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.POWERED) ?
                pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.getValue(BlockStateProperties.ATTACH_FACE)
                .equals(AttachFace.WALL)).rotationX(s.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                .rotationY((((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180)
                        + (s.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());

        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", modPath("block/" + baseName));
    }
    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", modPath("block/" + name + "_bottom")).renderType("cutout");;
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", modPath("block/" + name + "_top")).renderType("cutout");;

        getVariantBuilder(blockRegistryObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState()
                .with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }

    public void plantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", modPath("block/" + name)).renderType("cutout");
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }

    public void flowerPotBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String flower = name.substring(7);
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/flower_pot_cross")).texture("plant", modPath("block/" + flower)).renderType("cutout");
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }
}
