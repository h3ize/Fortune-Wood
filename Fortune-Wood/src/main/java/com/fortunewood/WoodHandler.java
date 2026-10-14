package com.fortunewood;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

public final class WoodHandler {
    private WoodHandler() {
    }

    public static void onBlockDrops(BlockDropsEvent event) {
        ServerLevel level = event.getLevel();
        BlockState state = event.getState();
        if (!state.is(BlockTags.LOGS)) {
            return;
        }
        ItemStack tool = event.getTool();
        if (!(tool.getItem() instanceof AxeItem)) {
            return;
        }
        int fortune = fortuneLevel(level, tool);
        if (fortune <= 0 || event.getDrops().isEmpty()) {
            return;
        }

        int extra = Config.WOOD_PER_LEVEL.get() * (fortune + level.random.nextInt(fortune + 1));
        Item log = state.getBlock().asItem();
        double x = event.getPos().getX() + 0.5;
        double y = event.getPos().getY() + 0.5;
        double z = event.getPos().getZ() + 0.5;
        while (extra > 0) {
            int count = Math.min(extra, 64);
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(log, count));
            entity.setDefaultPickUpDelay();
            event.getDrops().add(entity);
            extra -= count;
        }
    }

    private static int fortuneLevel(ServerLevel level, ItemStack stack) {
        Holder<Enchantment> fortune = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.FORTUNE);
        return EnchantmentHelper.getItemEnchantmentLevel(fortune, stack);
    }
}
