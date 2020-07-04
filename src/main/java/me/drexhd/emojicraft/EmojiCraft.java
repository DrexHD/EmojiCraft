package me.drexhd.emojicraft;

import net.minecraft.client.MinecraftClient;

public class EmojiCraft {

    private final MinecraftClient client;
    private static EmojiCraft instance ;
    public static final int emojiSize = 10;
    public static final String emojiRegex = "([:]([a-z_0-9]){1,32}[:])";

    public EmojiCraft(MinecraftClient client) {
        this.client = client;
        instance = this;
    }

    public static EmojiCraft getInstance() {
        return instance;
    }

    public MinecraftClient getClient() {
        return client;
    }

    public int getEmojiSize() {
        return emojiSize;
    }

}
