package net.tiphainelaurent.buildersblocks.api.helpers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.block.Block;
import net.minecraft.data.client.model.Model;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Item
{
    public static enum ITEM_TYPE
    {
        BLOCK;
    }

    public static final Map<Identifier, Model> ITEMS = new HashMap<>();
    public static List<Recipe<?>> RECIPES = new LinkedList<>();

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final FabricItemSettings settings = new FabricItemSettings();
        private ITEM_TYPE type;
        private Block block;
        private String namespace = "minecraft";
        private String name;

        public net.minecraft.item.Item build()
        {
            return build(new Identifier(namespace, name));
        }


        public net.minecraft.item.Item build(final String namespace_, final String name_)
        {
            return build(new Identifier(namespace_, name_));
        }


        public net.minecraft.item.Item build(final Identifier itemId)
        {

            switch (type)
            {
                case BLOCK:
                    final BlockItem blockItem = new BlockItem(block, settings);
                    Registry.register(Registry.ITEM, itemId, blockItem);
                    ITEMS.put(itemId, new Model(Optional.of(Registry.BLOCK.getId(block)), Optional.empty()));
                    return blockItem;

                default:
                    net.minecraft.item.Item item = new net.minecraft.item.Item(settings);
                    Registry.register(Registry.ITEM, itemId, item);
                    ITEMS.put(itemId, new Model(Optional.empty(), Optional.empty()));
                    return item;
            }
        }


        public Item.Builder namespace(final String namespace_)
        {
            namespace = namespace_;
            return this;
        }


        public Item.Builder name(final String name_)
        {
            name = name_;
            return this;
        }


        public Item.Builder fromBlock(final Block block_)
        {
            type = ITEM_TYPE.BLOCK;
            block = block_;
            return this;
        }


        public Item.Builder equipmentSlot(EquipmentSlotProvider equipmentSlotProvider)
        {
            settings.equipmentSlot(equipmentSlotProvider);
            return this;
        }


        public Item.Builder fireproof()
        {
            settings.fireproof();
            return this;
        }


        public Item.Builder food(final FoodComponent foodComponent)
        {
            settings.food(foodComponent);
            return this;
        }


        public Item.Builder group(final ItemGroup group)
        {
            settings.group(group);
            return this;
        }


        public Item.Builder maxCount(final int maxCount)
        {
            settings.maxCount(maxCount);
            return this;
        }


        public Item.Builder maxDamage(final int maxDamage)
        {
            settings.maxDamage(maxDamage);
            return this;
        }


        public Item.Builder maxDamageIfAbsent(final int maxDamage)
        {
            settings.maxDamageIfAbsent(maxDamage);
            return this;
        }


        public Item.Builder rarity(final Rarity rarity)
        {
            settings.rarity(rarity);
            return this;
        }


        public Item.Builder recipeRemainder(final net.minecraft.item.Item recipeRemainder)
        {
            settings.recipeRemainder(recipeRemainder);
            return this;
        }


        public Item.Builder withRecipe(final Recipe<?> recipe)
        {
            RECIPES.add(recipe);
            return this;
        }

        public Item.Builder withRecipes(final Consumer<Item.Builder> consumer)
        {
            consumer.accept(this);
            return this;
        }
    }
}