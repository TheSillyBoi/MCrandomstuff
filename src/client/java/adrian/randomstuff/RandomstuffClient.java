package adrian.randomstuff;

import adrian.randomstuff.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class RandomstuffClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		// No additional client registration needed for basic items - models are loaded automatically
		// based on the item registry name and model files in assets/randomstuff/models/item/
	}
}