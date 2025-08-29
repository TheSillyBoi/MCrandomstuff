package adrian.randomstuff.item;
import adrian.randomstuff.Randomstuff;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.item.Item;
import net.minecraft.util.Formatting;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Randomstuff.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }
    public static final TagKey<Item> IRON_INGOT_TAG = TagKey.of(RegistryKeys.ITEM, Identifier.of("minecraft", "iron_ingot"));
    public static Item hatchet;
    public static Item lightsaber_core;
    public static Item lightsaber;

    public static final ToolMaterial HatchetMaterial = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            175,
            5.0F,
            1.5F,
            14,
            IRON_INGOT_TAG
    );

    public static final ToolMaterial LightSaberMaterial = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2500,
            9.0F,
            4F,
            17,
            IRON_INGOT_TAG
    );



    public static void registerItems() {
        hatchet = register(
                "hatchet",
                s -> new HatchetItem(s.axe(HatchetMaterial, 1f, 1f)),
                new Item.Settings()
        );
        lightsaber_core = register("lightsaber_core", Item::new, new Item.Settings().rarity(Rarity.RARE));
        lightsaber = register(
                "lightsaber",
                s -> new LightsaberItem(
                        s.sword(LightSaberMaterial, 9.0f, -2.4f)
                ),
                new Item.Settings()
        );

    }

    public static void init() {

            Randomstuff.LOGGER.info("Registering Mod Items for " + Randomstuff.MOD_ID);
            registerItems();
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                    .register(group -> {
                        group.add(hatchet);
                        group.add(lightsaber);
                    });
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                    .register(group -> {
                        group.add(lightsaber_core);
                    });

    }

    public static final Map<Block, Block> PLANK_TO_SLAB = new HashMap<>();

    static {
        PLANK_TO_SLAB.put(Blocks.OAK_PLANKS, Blocks.OAK_SLAB);
        PLANK_TO_SLAB.put(Blocks.SPRUCE_PLANKS,Blocks.SPRUCE_SLAB);
        PLANK_TO_SLAB.put(Blocks.BIRCH_PLANKS,Blocks.BIRCH_SLAB);
        PLANK_TO_SLAB.put(Blocks.JUNGLE_PLANKS,Blocks.JUNGLE_SLAB);
        PLANK_TO_SLAB.put(Blocks.ACACIA_PLANKS,Blocks.ACACIA_SLAB);
        PLANK_TO_SLAB.put(Blocks.DARK_OAK_PLANKS,Blocks.DARK_OAK_SLAB);
        PLANK_TO_SLAB.put(Blocks.MANGROVE_PLANKS,Blocks.MANGROVE_SLAB);
        PLANK_TO_SLAB.put(Blocks.CHERRY_PLANKS,Blocks.CHERRY_SLAB);
    }
    public static final Map<Block, Block> LOG_TO_PLANK = new HashMap<>();

    static {
        LOG_TO_PLANK.put(Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        LOG_TO_PLANK.put(Blocks.SPRUCE_LOG,Blocks.SPRUCE_PLANKS);
        LOG_TO_PLANK.put(Blocks.BIRCH_LOG,Blocks.BIRCH_PLANKS);
        LOG_TO_PLANK.put(Blocks.JUNGLE_LOG,Blocks.JUNGLE_PLANKS);
        LOG_TO_PLANK.put(Blocks.ACACIA_LOG,Blocks.ACACIA_PLANKS);
        LOG_TO_PLANK.put(Blocks.DARK_OAK_LOG,Blocks.DARK_OAK_PLANKS);
        LOG_TO_PLANK.put(Blocks.MANGROVE_LOG,Blocks.MANGROVE_PLANKS);
        LOG_TO_PLANK.put(Blocks.CHERRY_LOG,Blocks.CHERRY_PLANKS);
    }
    public static class LightsaberItem extends Item {
        public LightsaberItem(Item.Settings settings) {

            super(settings);
        }


        @Override
        public ActionResult use(World world, PlayerEntity user, Hand hand) {
            if (!world.isClient) {
                var hitResult = user.raycast(20.0, 0.0F, false);
                if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                    BlockPos targetPos = ((net.minecraft.util.hit.BlockHitResult) hitResult).getBlockPos();
                    System.out.println(((BlockHitResult) hitResult).getBlockPos());
                    user.teleport(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, false);
                    user.getStackInHand(hand).damage(1, user);

                    return ActionResult.SUCCESS;
                }

                return ActionResult.PASS;
            }
            return null;
        }

        @Override
        public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient) {
                attacker.getWorld().playSound(
                        null,
                        attacker.getX(), attacker.getY(), attacker.getZ(),
                        Randomstuff.LIGHTSABER_SWING,
                        SoundCategory.PLAYERS,
                        1.0F, 1.0F
                );
            }

        }
    }


    public static class HatchetItem extends Item {
        public HatchetItem(Item.Settings settings) {
            super(settings);
        }

        @Override
        public ActionResult useOnBlock(ItemUsageContext context) {
            if (!context.getWorld().isClient) {
                World world = context.getWorld();
                BlockPos pos = context.getBlockPos();
                BlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                if (state.isIn(BlockTags.PLANKS)) {
                    Block slab = PLANK_TO_SLAB.get(block);
                    if (slab != null) {
                        ItemStack slabs = new ItemStack(slab.asItem(), 2);
                        Block.dropStack(world, pos, slabs);
                        context.getStack().damage(1, context.getPlayer());
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        return ActionResult.SUCCESS;
                    }
                }
                else if(state.isIn(BlockTags.LOGS)) {

                    Block plank = LOG_TO_PLANK.get(block);
                    if (plank != null) {
                        for (int i = 0; i < 10; i++){
                        System.out.println("THIS IS A LOG :3 :3 :3" + i);
                    }
                        ItemStack planks = new ItemStack(plank.asItem(), 4);
                        Block.dropStack(world, pos, planks);
                        context.getStack().damage(1, context.getPlayer());
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        return ActionResult.SUCCESS;
                    }
                }
            }



            return null;
        }

        private static Item registerItem(String name, Item item) {
            return Registry.register(Registries.ITEM, Identifier.of(Randomstuff.MOD_ID, name), item);
        }


    }
}