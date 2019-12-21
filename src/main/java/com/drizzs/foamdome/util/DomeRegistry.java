package com.drizzs.foamdome.util;

import com.drizzs.foamdome.blocks.Foam;
import com.drizzs.foamdome.blocks.domecreators.tile.GlassDomeCreatorTile;
import com.drizzs.foamdome.blocks.domecreators.tile.GravityDomeCreatorTile;
import com.drizzs.foamdome.items.DomeItems;
import com.drizzs.foamdome.blocks.domecreators.AcidDomeCreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.BasicDomeCreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.GlassDomeCreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.GravityDomeCreatorBlock;
import com.drizzs.foamdome.blocks.domecreators.tile.AcidDomeCreatorTile;
import com.drizzs.foamdome.blocks.domecreators.tile.BasicDomeCreatorTile;
import com.drizzs.foamdome.items.FoamCartridge;
import com.drizzs.foamdome.blocks.tunnelcreators.AcidTunnelCreatorBlock;
import com.drizzs.foamdome.blocks.tunnelcreators.BasicTunnelCreatorBlock;
import com.drizzs.foamdome.blocks.tunnelcreators.GlassTunnelCreatorBlock;
import com.drizzs.foamdome.blocks.tunnelcreators.GravityTunnelCreatorBlock;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.AcidTunnelCreatorTile;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.BasicTunnelCreatorTile;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.GlassTunnelCreatorTile;
import com.drizzs.foamdome.blocks.tunnelcreators.tile.GravityTunnelCreatorTile;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

import static com.drizzs.foamdome.FoamDome.MOD_ID;
import static com.drizzs.foamdome.util.DomeGroup.DOME_GROUP;
import static net.minecraft.block.material.Material.*;

public class DomeRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static Map<RegistryObject<Block>, String> blocklist = Maps.newHashMap();

    //Blocks
    //DomeCreators
    public static final RegistryObject<Block> BASIC_DOME_CREATOR = BLOCKS.register("basic_dome_creator", () ->
            new BasicDomeCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> GLASS_DOME_CREATOR = BLOCKS.register("glass_dome_creator", () ->
            new GlassDomeCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> GRAVITY_DOME_CREATOR = BLOCKS.register("gravity_dome_creator", () ->
            new GravityDomeCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> ACID_DOME_CREATOR = BLOCKS.register("acid_dome_creator", () ->
            new AcidDomeCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));

    //TunnelCreators
    public static final RegistryObject<Block> BASIC_TUNNEL_CREATOR = BLOCKS.register("basic_tunnel_creator", () ->
            new BasicTunnelCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> GLASS_TUNNEL_CREATOR = BLOCKS.register("glass_tunnel_creator", () ->
            new GlassTunnelCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> GRAVITY_TUNNEL_CREATOR = BLOCKS.register("gravity_tunnel_creator", () ->
            new GravityTunnelCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));
    public static final RegistryObject<Block> ACID_TUNNEL_CREATOR = BLOCKS.register("acid_tunnel_creator", () ->
            new AcidTunnelCreatorBlock(Block.Properties.create(IRON)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .hardnessAndResistance(6)
            ));

    //FoamBlocks
    public static final RegistryObject<Block> BASIC_FOAM = BLOCKS.register("basic_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));
    public static final RegistryObject<Block> GLASS_FOAM = BLOCKS.register("glass_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));
    public static final RegistryObject<Block> HARD_GRAVITY_FOAM = BLOCKS.register("hard_gravity_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));
    public static final RegistryObject<Block> ACID_FOAM = BLOCKS.register("acid_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));

    public static final RegistryObject<Block> DISOLVABLE_GRAVITY_FOAM = BLOCKS.register("disolvable_gravity_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));
    public static final RegistryObject<Block> DISOLVABLE_BASIC_FOAM = BLOCKS.register("disolvable_basic_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));
    public static final RegistryObject<Block> DISOLVABLE_GLASS_FOAM = BLOCKS.register("disolvable_glass_foam", () ->
            new Foam(Block.Properties.create(SNOW)
                    .sound(SoundType.SNOW)
                    .tickRandomly()
                    .hardnessAndResistance(0)
                    .noDrops()
            ));


    //NotFoam
    //NotFoamBlocks
    public static final RegistryObject<Block> NOT_FOAM = BLOCKS.register("not_foam", () ->
            new Block(Block.Properties.create(ROCK)
                    .sound(SoundType.STONE)
                    .harvestTool(ToolType.PICKAXE)
                    .hardnessAndResistance(30.0F, 600.0F)
                    .noDrops()
            ));

    //Items

    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_2 = ITEMS.register("foam_cartridge_size_2",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 2)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_3 = ITEMS.register("foam_cartridge_size_3",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 3)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_4 = ITEMS.register("foam_cartridge_size_4",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 4)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_5 = ITEMS.register("foam_cartridge_size_5",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 5)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_6 = ITEMS.register("foam_cartridge_size_6",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 6)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_7 = ITEMS.register("foam_cartridge_size_7",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .group(DOME_GROUP).maxStackSize(1)), 7)
    );

    public static final RegistryObject<Item> FOAM_ICON = ITEMS.register("foam_icon",
            () -> new Item((new Item.Properties())));

    //TileEntities
    //DomeCreators
    public static final RegistryObject<TileEntityType<?>> BASIC_DOME_TILE = TILE_ENTITY_TYPE.register("basic_dome_creator",
            () -> TileEntityType.Builder.create(BasicDomeCreatorTile::new, BASIC_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> GLASS_DOME_TILE = TILE_ENTITY_TYPE.register("glass_dome_creator",
            () -> TileEntityType.Builder.create(GlassDomeCreatorTile::new, GLASS_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> GRAVITY_DOME_TILE = TILE_ENTITY_TYPE.register("gravity_dome_creator",
            () -> TileEntityType.Builder.create(GravityDomeCreatorTile::new, GRAVITY_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ACID_DOME_TILE = TILE_ENTITY_TYPE.register("acid_dome_creator",
            () -> TileEntityType.Builder.create(AcidDomeCreatorTile::new, ACID_DOME_CREATOR.get()).build(null));
    //TunnelCreators
    public static final RegistryObject<TileEntityType<?>> BASIC_TUNNEL_TILE = TILE_ENTITY_TYPE.register("basic_tunnel_creator",
            () -> TileEntityType.Builder.create(BasicTunnelCreatorTile::new, BASIC_TUNNEL_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> GLASS_TUNNEL_TILE = TILE_ENTITY_TYPE.register("glass_tunnel_creator",
            () -> TileEntityType.Builder.create(GlassTunnelCreatorTile::new, GLASS_TUNNEL_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> GRAVITY_TUNNEL_TILE = TILE_ENTITY_TYPE.register("gravity_tunnel_creator",
            () -> TileEntityType.Builder.create(GravityTunnelCreatorTile::new, GRAVITY_TUNNEL_CREATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ACID_TUNNEL_TILE = TILE_ENTITY_TYPE.register("acid_tunnel_creator",
            () -> TileEntityType.Builder.create(AcidTunnelCreatorTile::new, ACID_TUNNEL_CREATOR.get()).build(null));


    public static void register(IEventBus eventBus) {
        blocklist.put(BASIC_DOME_CREATOR, "basic_dome_creator");
        blocklist.put(GLASS_DOME_CREATOR, "glass_dome_creator");
        blocklist.put(GRAVITY_DOME_CREATOR, "gravity_dome_creator");
        blocklist.put(ACID_DOME_CREATOR, "acid_dome_creator");

        blocklist.put(BASIC_TUNNEL_CREATOR, "basic_tunnel_creator");
        blocklist.put(GLASS_TUNNEL_CREATOR, "glass_tunnel_creator");
        blocklist.put(GRAVITY_TUNNEL_CREATOR, "gravity_tunnel_creator");
        blocklist.put(ACID_TUNNEL_CREATOR, "acid_tunnel_creator");

        blocklist.put(NOT_FOAM, "not_foam");

        for (RegistryObject<Block> block : blocklist.keySet()) {
            ITEMS.register(blocklist.get(block), () -> new DomeItems(block.get(), new Item.Properties().group(DOME_GROUP)));
        }
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        TILE_ENTITY_TYPE.register(eventBus);
    }
}
