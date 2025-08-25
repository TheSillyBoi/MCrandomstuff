package adrian.randomstuff.item;

import adrian.randomstuff.Randomstuff;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;


public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Randomstuff.MOD_ID, name));
        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static final Item hatchet;

    static {
        hatchet = register("hatchet", Item::new, new Item.Settings());
    }
//    public static final Item lightsaber_core = register(new Item(new Item.Settings()
//            .maxCount(1)
//            .rarity(Rarity.EPIC)
//            .fireproof()
//    ));}

    public static final Item lightsaber = register(
            "lightsaber",
            settings -> new Item(
                    settings.sword(ToolMaterial.DIAMOND,
                            9.0f,
                            -2.4f)
            ),
            new Item.Settings()
    );

    public static final Item lightsaber_core = registerItem("lightsaber_core",
            new Item(new Item.Settings().fireproof()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Randomstuff.MOD_ID, "lightsaber_core")))));





    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Randomstuff.MOD_ID, name), item);
    }

    public static void initialize() {
        Randomstuff.LOGGER.info("Registering Mod Items for " + Randomstuff.MOD_ID);
        // Add items to creative tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(group -> {
                    group.add(lightsaber_core);
                    group.add(hatchet);
                    group.add(lightsaber);
                });
    }
}