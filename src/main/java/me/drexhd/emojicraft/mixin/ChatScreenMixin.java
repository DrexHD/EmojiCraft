package me.drexhd.emojicraft.mixin;

import me.drexhd.emojicraft.EmojiCraft;
import me.drexhd.emojicraft.chat.EmojiFieldWidget;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen{

    @Shadow protected TextFieldWidget chatField;
    private int messageHistorySize = -1;
    private String originalChatText = "";
    private CommandSuggestor commandSuggestor;



    public ChatScreenMixin(Text title) {
        super(title);
        originalChatText = title.asString();
    }

    @Inject(method = "init", at = @At(value = "HEAD"), cancellable = true)
    private void onChatOpen(CallbackInfo ci){
        this.chatField = new EmojiFieldWidget(this.font, 4, this.height - 12, this.width - 4, 12);
        this.minecraft.keyboard.enableRepeatEvents(true);
        messageHistorySize = this.minecraft.inGameHud.getChatHud().getMessageHistory().size();
        this.chatField.setMaxLength(256);
        this.chatField.setHasBorder(false);
        this.chatField.setText(this.originalChatText);
        this.chatField.setChangedListener(this::onChatFieldUpdate);
        this.children.add(this.chatField);
        this.commandSuggestor = new CommandSuggestor(EmojiCraft.getInstance().getClient(), this, this.chatField, EmojiCraft.getInstance().getClient().textRenderer, false, false, 1, 10, true, -805306368);
        this.commandSuggestor.refresh();
        this.setInitialFocus(this.chatField);
        ci.cancel();
    }

    private void onChatFieldUpdate(String string) {
        String string2 = this.chatField.getText();
        this.commandSuggestor.setWindowActive(!string2.equals(this.originalChatText));
        this.commandSuggestor.refresh();
    }

}