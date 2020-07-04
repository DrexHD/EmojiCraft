package me.drexhd.emojicraft.util;

import com.mojang.blaze3d.systems.RenderSystem;
import me.drexhd.emojicraft.EmojiCraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;

public class LineRenderer extends DrawableHelper {
    private final String text;
    private final String emojiRegex = EmojiCraft.emojiRegex;
    private final int emojiSize = EmojiCraft.emojiSize;
    private final int length;
    private final ArrayList<String> components;

    public LineRenderer(String text) {
        this.text = text;
        this.components = toTextComponents(text);
        this.length = getLength();
    }

    private void drawEmoji(String id, int x, int y, int alpha) {
        Identifier TEXTURE;
        if (id.startsWith("mcblock_")) {
            TEXTURE = new Identifier("minecraft", "textures/block/" + id.substring(8) + ".png");
        } else if (id.startsWith("mcitem_")) {
            TEXTURE = new Identifier("minecraft", "textures/item/" + id.substring(7) + ".png");
        } else {
            TEXTURE = new Identifier("minecraft", "textures/emoji/" + id + ".png");
        }
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1 - alpha);
        EmojiCraft.getInstance().getClient().getTextureManager().bindTexture(TEXTURE);
        this.blit(x, y - 1, 0, 0, emojiSize, emojiSize, emojiSize, emojiSize);
    }

    private int getLength() {
        int l = 0;
        for(String component : components) {
            if(component.matches(emojiRegex)) {
                l+=emojiSize;
            } else {
                for(char c : component.toCharArray()) {
                    l+=EmojiCraft.getInstance().getClient().textRenderer.getCharWidth(c);
                }
            }
        }
        return l;
    }

    public int getLenght() {
        return length;
    }


    public void drawLine(int x, int y, int color, int alpha) {
        int offset = x;
        for (String component : toTextComponents(this.text)) {
            if(component.matches(emojiRegex)){
                drawEmoji(component.substring(1, component.length()-1), offset, y, alpha);
                offset += emojiSize;
            } else {
                EmojiCraft.getInstance().getClient().textRenderer.drawWithShadow(component, offset, y, color);
                offset += length(component);
            }
        }
    }

    private int length(String string) {
        int length = 0;
        for(char c : string.toCharArray()) {
            length += EmojiCraft.getInstance().getClient().textRenderer.getCharWidth(c);
        }
        return length;
    }

    /**
     * @param text Input text
     * @return an Arraylist of components which is used to render the text
     */
    private ArrayList<String> toTextComponents(String text) {
        ArrayList<String> components = new ArrayList<>();
        //Split string before emoji code
        String[] list1 = text.split("(?=" + emojiRegex + ")");

        for (String string : list1) {
            String[] list2 = string.split("(?<=" + emojiRegex + ")");
//            String[] arrayString2 = string.split("(?<=([:][a-z]{2,16}[:]))");

            components.addAll(Arrays.asList(list2));

        }
        return components;
    }

    public ArrayList<String> getComponents() {
        return components;
    }


}
