package com.mtmrails.trustedserver.fabric.client;

import java.util.Optional;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

/** 登录期间缺少服务器 PSK 时显示的本地输入框。 */
final class PskPromptScreen extends Screen {
    @FunctionalInterface
    interface Submitter {
        Optional<Component> submit(String encoded);
    }

    private final Screen parent;
    private final String address;
    private final Submitter submitter;
    private final Runnable cancelled;
    private EditBox input;
    private Component error;
    private boolean finished;

    PskPromptScreen(Screen parent, String address, Submitter submitter, Runnable cancelled) {
        super(Component.literal("TrustedServer 密钥"));
        this.parent = parent;
        this.address = address;
        this.submitter = submitter;
        this.cancelled = cancelled;
    }

    @Override
    protected void init() {
        int center = width / 2;
        input = new EditBox(font, center - 150, height / 2 - 10, 300, 20,
                Component.literal("Base64 PSK"));
        input.setMaxLength(128);
        input.addFormatter((text, offset) -> FormattedCharSequence.forward("•".repeat(text.length()), Style.EMPTY));
        addRenderableWidget(input);
        addRenderableWidget(Button.builder(Component.literal("保存并连接"), button -> submit())
                .bounds(center - 150, height / 2 + 24, 145, 20).build());
        addRenderableWidget(Button.builder(Component.literal("取消"), button -> onClose())
                .bounds(center + 5, height / 2 + 24, 145, 20).build());
        setInitialFocus(input);
    }

    private void submit() {
        Optional<Component> result = submitter.submit(input.getValue().trim());
        if (result.isPresent()) {
            error = result.orElseThrow();
            return;
        }
        finished = true;
        minecraft.setScreenAndShow(parent);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.isConfirmation()) {
            submit();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public void onClose() {
        if (!finished) {
            finished = true;
            cancelled.run();
        }
        minecraft.setScreenAndShow(parent);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xC0101010);
        graphics.centeredText(font, title, width / 2, height / 2 - 72, 0xFFFFFF);
        graphics.centeredText(font, Component.literal("服务器：" + address), width / 2,
                height / 2 - 52, 0xA0A0A0);
        graphics.centeredText(font, Component.literal("请输入服主提供的 32 字节 Base64 PSK"), width / 2,
                height / 2 - 34, 0xFFFFFF);
        if (error != null) {
            graphics.centeredText(font, error, width / 2, height / 2 + 52, 0xFF5555);
        }
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
