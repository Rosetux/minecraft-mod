package com.example.examplemod;

import com.example.examplemod.command.ICommandService;
import com.example.examplemod.entity.ModEntityType;
import com.example.examplemod.entity.PnjEntity;
import com.example.examplemod.network.TakesServer;
import com.example.examplemod.quest.IQuestService;
import com.example.examplemod.quest.Quest;
import com.example.examplemod.quest.capability.IQuestCapability;
import com.example.examplemod.quest.capability.QuestCapability;
import com.example.examplemod.quest.capability.QuestCapabilityFactory;
import com.example.examplemod.quest.capability.QuestCapabilityStorage;
import com.example.examplemod.renderer.IRendererService;
import com.example.examplemod.server.IServerService;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class MyExampleMod {
    private IServerService serverService;
    private ICommandService commandService;
    private IRendererService rendererService;
    private IQuestService questService;

    public MyExampleMod(ICommandService commandService, IServerService serverService, IRendererService rendererService,
                        IQuestService questService) {
        this.commandService = commandService;
        this.serverService = serverService;
        this.rendererService = rendererService;
        this.questService = questService;
        this.serverService.addListener(this::registerCommands);

        MyExampleMod self = this;

        Thread t = new Thread() {
            public void run() {
                try {
                    new TakesServer(self).run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntityRegistry);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntityCapabilities);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerCommands(FMLServerStartedEvent event) {
        this.commandService.registerCommands();
    }

    @SubscribeEvent
    public void registerRenderers(FMLClientSetupEvent event) {
        rendererService.registerRenderers();
        CapabilityManager.INSTANCE.register(IQuestCapability.class, new QuestCapabilityStorage(questService),
                new QuestCapabilityFactory(questService));
    }

    @SubscribeEvent
    public void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        if(ModEntityType.PNJ == null) {
            ModEntityType.init();
        }
    }

    @SubscribeEvent
    public void onEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        QuestCapability defaultImplementation = new QuestCapability();
        if(event.getObject() instanceof PnjEntity) {
            event.addCapability(QuestCapability.CAPABILITY_LOCATION, new ICapabilitySerializable<CompoundNBT>() {
                @Override
                public CompoundNBT serializeNBT() {
                    return (CompoundNBT) QuestCapabilityStorage.QUEST_CAPABILITY.writeNBT(defaultImplementation, null);
                }

                @Override
                public void deserializeNBT(CompoundNBT nbt) {
                    QuestCapabilityStorage.QUEST_CAPABILITY.readNBT(defaultImplementation, null, nbt);
                }

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return LazyOptional.of(() -> {
                        return (T) new QuestCapabilityFactory(questService).call();
                    });
                }
            });
        }
    }

    public IServerService getServerService() {
        return serverService;
    }

    public IQuestService getQuestService() {
        return questService;
    }
}
