package com.joshmanisdabomb.aimagg.event;

import com.joshmanisdabomb.aimagg.AimaggBiome;
import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggEntities;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimaggRecipes;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class AimaggRegistry {

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
	    event.getRegistry().registerAll(AimaggBlocks.registry.toArray(new Block[]{}));
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
	    event.getRegistry().registerAll(AimaggItems.registry.toArray(new Item[]{}));
	    event.getRegistry().registerAll(AimaggEquipment.registry.toArray(new Item[]{}));
	    event.getRegistry().registerAll(AimaggBlocks.ibRegistry.toArray(new Item[]{}));
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
	    event.getRegistry().registerAll(AimaggEntities.registry.toArray(new EntityEntry[]{}));
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
	    event.getRegistry().registerAll(AimaggRecipes.registry.toArray(new IRecipe[]{}));
	}
	
	@SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event) {
		for (AimaggBiome b : AimaggBiome.values()) {
			event.getRegistry().register(b.getBiome());
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerRenders(ModelRegistryEvent event) {
	    AimaggBlocks.registerRenders(event);
	    AimaggItems.registerRenders(event);
    }
	
}
