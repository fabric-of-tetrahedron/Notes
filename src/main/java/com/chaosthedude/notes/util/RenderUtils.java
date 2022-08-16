package com.chaosthedude.notes.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;

public class RenderUtils {

	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	private static final TextRenderer TEXT_RENDERER = CLIENT.textRenderer;
	
	public static List<String> trimStringToWidth(String str, int maxWidth) {
		List<String> trimmedStrings = new ArrayList<String>();
		for (StringVisitable text : TEXT_RENDERER.getTextHandler().wrapLines(str, maxWidth, Style.EMPTY)) {
			trimmedStrings.add(text.getString());
		}
		return trimmedStrings;
	}

	public static void renderSplitString(MatrixStack stack, String string, int x, int y, int wrapWidth, int color) {
		for (String s : trimStringToWidth(string, wrapWidth)) {
			TEXT_RENDERER.drawWithShadow(stack, s, x, y, color);
			y += TEXT_RENDERER.fontHeight;
		}
	}

	public static int getSplitStringWidth(String string, int wrapWidth) {
		final List<String> lines = trimStringToWidth(string, wrapWidth);
		int width = 0;
		for (String line : lines) {
			final int stringWidth = TEXT_RENDERER.getWidth(line);
			if (stringWidth > width) {
				width = stringWidth;
			}
		}

		return width;
	}

	public static int getSplitStringHeight(String string, int wrapWidth) {
		return TEXT_RENDERER.fontHeight * trimStringToWidth(string, wrapWidth).size();
	}

	public static int getRenderWidth(String position, int width) {
		final String positionLower = position.toLowerCase();
		if (positionLower.equals("top_left") || positionLower.equals("center_left") || positionLower.equals("bottom_left")) {
			return 10;
		}

		return CLIENT.getWindow().getScaledWidth() - width;
	}

	public static int getRenderHeight(String position, int height) {
		final String positionLower = position.toLowerCase();
		if (positionLower.equals("top_left") || positionLower.equals("top_right")) {
			return 5;
		} else if (positionLower.equals("bottom_left") || positionLower.equals("bottom_right")) {
			return CLIENT.getWindow().getScaledHeight() - height - 5;
		}

		return (CLIENT.getWindow().getScaledHeight() / 2) - (height / 2);
	}

}
