package me.drexhd.emojicraft.mixin;

import me.drexhd.emojicraft.EmojiCraft;
import me.drexhd.emojicraft.chat.ChatEmojiHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Mutable
    @Shadow @Final private ChatHud chatHud;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void redirectChatHud(MinecraftClient client, CallbackInfo ci) {
        new EmojiCraft(client);
        this.chatHud = new ChatEmojiHud(client);
    }


}
