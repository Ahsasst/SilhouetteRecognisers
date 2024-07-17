package com.shpp.p2p.cs.otavlui.silhouettedfs;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Find background color
 */
public class Background {
    /**
     * Find background color.
     * Count frequency of each color for boundary pixels
     *
     * @param image The received image
     * @return Color of the background
     */
    protected static int findBackgroundColor(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        HashMap<Integer, Integer> colorCount = new HashMap<>();

        // Count frequency of each color for boundary pixels
        for (int x = 0; x < width; x++) {
            incrementColorCount(colorCount, image.getRGB(x, 0)); // Up border
            incrementColorCount(colorCount, image.getRGB(x, height - 1)); // Down border
        }

        for (int y = 0; y < height; y++) {
            incrementColorCount(colorCount, image.getRGB(0, y)); // Left border
            incrementColorCount(colorCount, image.getRGB(width - 1, y)); // Right border
        }

        return findCommonColor(colorCount);
    }

    /**
     * Count the amount of a particular color in the image
     *
     * @param colorCount Map to store the color number and its amount in the image
     * @param numColor The color number
     */
    protected static void incrementColorCount(HashMap<Integer, Integer> colorCount, int numColor) {
        int count = colorCount.getOrDefault(numColor, 0);
        colorCount.put(numColor, count + 1);
    }

    /**
     * Find the most common color and define it as the background colour
     *
     * @param colorCount HashMap with the value of colors and their frequency
     * @return Value of the most common color
     */
    protected static int findCommonColor(HashMap<Integer, Integer> colorCount) {
        int maxCount = 0;
        int backgroundColor = 0;
        for (HashMap.Entry<Integer, Integer> entry : colorCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                backgroundColor = entry.getKey();
            }
        }
        return backgroundColor;
    }
}
