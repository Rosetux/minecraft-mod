package com.example.examplemod.screen;

import com.example.examplemod.quest.IQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class QuestScreen extends Screen {

    private IQuest quest;

    public QuestScreen(IQuest quest) {
        super(new StringTextComponent(quest.getTitle()));
        this.quest = quest;
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 108, 200, 20,
                "Accept cancel", (args) -> {}));

        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 138, 200, 20,
                "Close", (args) -> {
            this.minecraft.displayGuiScreen((Screen) null);
        }));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/gui/demo_background.png"));
        int i = (this.width - 248) / 2;
        int j = (this.height - 166) / 2;

        this.blit(i, j, 0, 0, 248, 166);

        this.drawString(this.font, this.quest.getTitle(), i + 15,
                j + 15, 16777215);

        this.font.drawSplitString(this.quest.getDescription(), i + 15, j + 35, 150, 16777215);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
}
