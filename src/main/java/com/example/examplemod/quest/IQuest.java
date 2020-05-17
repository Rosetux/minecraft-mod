package com.example.examplemod.quest;

public interface IQuest {
    long getId();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);
}
