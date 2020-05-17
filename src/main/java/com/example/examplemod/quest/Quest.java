package com.example.examplemod.quest;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.storage.WorldSavedData;

public class Quest extends WorldSavedData implements IQuest {
    private static final String DATA_PREFIX = "QUEST_";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "name";
    private static final String DESCRIPTION_FIELD = "description";

    private long id;
    private String title;
    private String description;

    public Quest() {
        super("");
    }

    public Quest(long id) {
        super(getPrefixed(Long.toString(id)));
        this.id = id;
    }

    @Override
    public void read(CompoundNBT nbt) {
        this.id = nbt.getLong(ID_FIELD);
        this.title = nbt.getString(TITLE_FIELD);
        this.description = nbt.getString(DESCRIPTION_FIELD);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put(ID_FIELD, LongNBT.valueOf(this.id));
        compound.put(TITLE_FIELD, StringNBT.valueOf(this.title));
        compound.put(DESCRIPTION_FIELD, StringNBT.valueOf(this.description));

        return compound;
    }

    public static String getPrefixed(String suffix) {
        return DATA_PREFIX + suffix;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
