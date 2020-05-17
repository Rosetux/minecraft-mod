package com.example.examplemod.quest;

import com.example.examplemod.server.IServerService;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class QuestService implements IQuestService {
    private IServerService serverService;
    private QuestRepository questRepository = new QuestRepository();

    public QuestService(IServerService serverService) {
        this.serverService = serverService;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadQuests);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Quest createQuest(String title, String description) {
        Quest quest = new Quest(generateUniqueIdentifier());
        quest.setTitle(title);
        quest.setDescription(description);

        this.persistQuest(quest);

        return quest;
    }

    public void persistQuest(Quest quest) {
        this.questRepository.getQuests().add(quest);
        this.serverService.getServer().getWorld(DimensionType.OVERWORLD).getSavedData().set(questRepository);
        this.questRepository.markDirty();
    }

    @Override
    public Set<Quest> retrieveQuests() {
        return this.questRepository.getQuests();
    }

    @Override
    public void writeQuest(Quest quest) {
        this.persistQuest(quest);
    }

    @Override
    public Optional<IQuest> loadQuest(Long id) {
        return this.questRepository.getQuests().stream().filter(q->{
            return q.getId() == id;
        }).findFirst().map(q->(IQuest) q);
    }

    @SubscribeEvent
    public void loadQuests(FMLServerStartedEvent event) {
        this.questRepository = this.serverService.getServer().getWorld(DimensionType.OVERWORLD)
                .getSavedData().<QuestRepository>get(QuestRepository::new, QuestRepository.DATA_ID);

        if(this.questRepository == null) {
            questRepository = new QuestRepository();
            this.serverService.getServer().getWorld(DimensionType.OVERWORLD).getSavedData().set(questRepository);
        }
    }

    private long generateUniqueIdentifier() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
