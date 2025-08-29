
package adrian.randomstuff;
import adrian.randomstuff.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Randomstuff implements ModInitializer {
    public static final String MOD_ID = "randomstuff";

    public static final SoundEvent LIGHTSABER_IGNITE = SoundEvent.of(Identifier.of(MOD_ID, "lightsaber.ignite"));
    public static final SoundEvent LIGHTSABER_SWING  = SoundEvent.of(Identifier.of(MOD_ID, "lightsaber.swing"));

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID, "lightsaber.ignite"), LIGHTSABER_IGNITE);
        Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID, "lightsaber.swing"), LIGHTSABER_SWING);
        ModItems.init();
        LOGGER.info("Hello Fabric world!");
    }
}