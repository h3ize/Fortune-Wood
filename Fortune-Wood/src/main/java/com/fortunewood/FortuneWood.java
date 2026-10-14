package com.fortunewood;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(FortuneWood.MODID)
public class FortuneWood {
    public static final String MODID = "fortunewood";

    public FortuneWood(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NeoForge.EVENT_BUS.addListener(WoodHandler::onBlockDrops);
    }
}
