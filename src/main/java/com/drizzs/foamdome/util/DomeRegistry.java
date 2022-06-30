package com.drizzs.foamdome.util;

import com.drizzs.foamdome.FoamDome;
import com.drizzs.foamdome.blockentities.Foam;
import com.drizzs.foamdome.blockentities.dome.*;
import com.drizzs.foamdome.blocks.dome.*;
import com.drizzs.foamdome.common.containers.base.FoamContainer;
import com.drizzs.foamdome.entities.AntiGravityEntity;
import com.drizzs.foamdome.items.DomeItems;
import com.drizzs.foamdome.items.FoamCartridge;
import com.drizzs.foamdome.items.ShapeCartridge;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Objects;

import static com.drizzs.foamdome.FoamDome.MOD_ID;
import static com.drizzs.foamdome.util.DomeGroup.DOME_GROUP;

public class DomeRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static Map<RegistryObject<Block>, String> blocklist = Maps.newHashMap();

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

    //Blocks
    //DomeCreators
    public static final RegistryObject<Block> BASIC_DOME_CREATOR = BLOCKS.register("basic_dome_creator", BasicDomeCreatorBlock::new
            );
    public static final RegistryObject<Block> GLASS_DOME_CREATOR = BLOCKS.register("glass_dome_creator", GlassDomeCreatorBlock::new
            );
    public static final RegistryObject<Block> GRAVITY_DOME_CREATOR = BLOCKS.register("gravity_dome_creator", GravityDomeCreatorBlock::new
            );
    public static final RegistryObject<Block> ACID_DOME_CREATOR = BLOCKS.register("acid_dome_creator", AcidDomeCreatorBlock::new
            );
    public static final RegistryObject<Block> SKY_DOME_CREATOR = BLOCKS.register("sky_dome_creator", SkyDomeCreatorBlock::new
    );
    public static final RegistryObject<Block> GLASS_SKY_DOME_CREATOR = BLOCKS.register("glass_sky_dome_creator", GlassSkyDomeCreatorBlock::new
    );


    //FoamBlocks
    public static final RegistryObject<Block> BASIC_FOAM = BLOCKS.register("basic_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> GLASS_FOAM = BLOCKS.register("glass_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> HARD_GRAVITY_FOAM = BLOCKS.register("hard_gravity_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> ACID_FOAM = BLOCKS.register("acid_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> DISOLVABLE_SKY_FOAM = BLOCKS.register("disolvable_sky_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> HARD_SKY_FOAM = BLOCKS.register("hard_sky_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> GLASS_SKY_FOAM = BLOCKS.register("glass_sky_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));

    public static final RegistryObject<Block> DISOLVABLE_GRAVITY_FOAM = BLOCKS.register("disolvable_gravity_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> DISOLVABLE_BASIC_FOAM = BLOCKS.register("disolvable_basic_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));
    public static final RegistryObject<Block> DISOLVABLE_GLASS_FOAM = BLOCKS.register("disolvable_glass_foam", () ->
            new Foam(BlockBehaviour.Properties.of(Material.SNOW).strength(0,0).noDrops().sound(SoundType.SNOW).randomTicks()
            ));


    //NotFoam
    //NotFoamBlocks
    public static final RegistryObject<Block> NOT_FOAM = BLOCKS.register("not_foam", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .sound(SoundType.STONE)
                    .strength(30.0F, 600.0F)
                    .noDrops()
            ));

    //Items

    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_2 = ITEMS.register("foam_cartridge_size_2",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 2)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_3 = ITEMS.register("foam_cartridge_size_3",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 3)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_4 = ITEMS.register("foam_cartridge_size_4",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 4)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_5 = ITEMS.register("foam_cartridge_size_5",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 5)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_6 = ITEMS.register("foam_cartridge_size_6",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 6)
    );
    public static final RegistryObject<Item> FOAM_CARTRIDGE_SIZE_7 = ITEMS.register("foam_cartridge_size_7",
            () -> new FoamCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), 7)
    );

    public static final RegistryObject<Item> SHAPE_CARTRIDGE_CUBE = ITEMS.register("cube_cartridge",
            () -> new ShapeCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), "cube")
    );
    public static final RegistryObject<Item> SHAPE_CARTRIDGE_TUNNEL = ITEMS.register("tunnel_cartridge",
            () -> new ShapeCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), "tunnel")
    );
    public static final RegistryObject<Item> SHAPE_CARTRIDGE_DOME = ITEMS.register("dome_cartridge",
            () -> new ShapeCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), "dome")
    );
    public static final RegistryObject<Item> SHAPE_CARTRIDGE_HALF_DOME = ITEMS.register("half_dome_cartridge",
            () -> new ShapeCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), "half")
    );
    public static final RegistryObject<Item> SHAPE_CARTRIDGE_PYRAMID= ITEMS.register("pyramid_cartridge",
            () -> new ShapeCartridge(Objects.requireNonNull(new Item.Properties()
                    .tab(DOME_GROUP).stacksTo(1)), "pyramid")
    );

    public static final RegistryObject<Item> FOAM_ICON = ITEMS.register("foam_icon",
            () -> new Item((new Item.Properties())));

    //TileEntities
    public static final RegistryObject<BlockEntityType<?>> BASIC_DOME_TILE = TILE_ENTITY_TYPE.register("basic_dome_creator",
            () -> BlockEntityType.Builder.of(BasicFoamCreatorTile::new, BASIC_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> GLASS_DOME_TILE = TILE_ENTITY_TYPE.register("glass_dome_creator",
            () -> BlockEntityType.Builder.of(GlassFoamCreatorTile::new, GLASS_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> GRAVITY_DOME_TILE = TILE_ENTITY_TYPE.register("gravity_dome_creator",
            () -> BlockEntityType.Builder.of(GravityFoamCreatorTile::new, GRAVITY_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> ACID_DOME_TILE = TILE_ENTITY_TYPE.register("acid_dome_creator",
            () -> BlockEntityType.Builder.of(AcidFoamCreatorTile::new, ACID_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> SKY_DOME_TILE = TILE_ENTITY_TYPE.register("sky_dome_creator",
            () -> BlockEntityType.Builder.of(SkyFoamCreatorTile::new, SKY_DOME_CREATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> GLASS_SKY_DOME_TILE = TILE_ENTITY_TYPE.register("glass_sky_dome_creator",
            () -> BlockEntityType.Builder.of(GlassSkyFoamCreatorTile::new, GLASS_SKY_DOME_CREATOR.get()).build(null));

    //Containers
    public static final RegistryObject<MenuType<FoamContainer>> FOAM_CONTAINER = CONTAINERS.register("foam_container", ()-> new MenuType<>(FoamContainer::new));
    //EntityTypes
    public static final RegistryObject<EntityType<Entity>> ANTI_GRAVITY_ENTITY = ENTITY_TYPE.register("anti_gravity_entity",
            () -> EntityType.Builder.of(AntiGravityEntity::new, MobCategory.MISC).build("anti_gravity_entity"));

    public static void register(IEventBus eventBus) {
        blocklist.put(BASIC_DOME_CREATOR, "basic_dome_creator");
        blocklist.put(GLASS_DOME_CREATOR, "glass_dome_creator");
        blocklist.put(GRAVITY_DOME_CREATOR, "gravity_dome_creator");
        blocklist.put(ACID_DOME_CREATOR, "acid_dome_creator");
        blocklist.put(GLASS_SKY_DOME_CREATOR, "glass_sky_dome_creator");
        blocklist.put(SKY_DOME_CREATOR, "sky_dome_creator");

        blocklist.put(NOT_FOAM, "not_foam");

        for (RegistryObject<Block> block : blocklist.keySet()) {
            ITEMS.register(blocklist.get(block), () -> new DomeItems(block.get(), new Item.Properties().tab(DOME_GROUP)));
        }
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        TILE_ENTITY_TYPE.register(eventBus);
        CONTAINERS.register(eventBus);
        ENTITY_TYPE.register(eventBus);
    }
}
