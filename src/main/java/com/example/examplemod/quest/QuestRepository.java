package com.example.examplemod.quest;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestRepository extends WorldSavedData {
    public static final String DATA_ID = "QUESTS";
    private static final String FIELD_QUESTS = "QUESTS_LIST";
    private Set<Quest> quests = new HashSet<>();

    public QuestRepository() {
        super(DATA_ID);
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT questList = (ListNBT) nbt.get(FIELD_QUESTS);
        questList.stream().forEach(q->{
            Quest quest = new Quest();
            quest.read((CompoundNBT) q);
            this.quests.add(quest);
        });
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT questList = new ListNBT();
        quests.stream().forEach(q->{
            questList.add(questList.size(), q.write(new CompoundNBT()));
        });

        compound.put(FIELD_QUESTS, questList);
        return compound;
    }

    public Set<Quest> getQuests() {
        return quests;
    }
}
