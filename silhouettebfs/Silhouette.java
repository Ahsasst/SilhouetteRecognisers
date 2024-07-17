package com.shpp.p2p.cs.otavlui.silhouettebfs;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Implements the definition of silhouettes in the image and their calculation
 */
public class Silhouette {
    /**
     * Count silhouettes in the image using the Depth-first search algorithm
     *
     * @param image The received image
     * @param sizeGarbage Variable for cleaning up garbage
     * @return Number of silhouettes
     */
    protected static int countSilhouettes(BufferedImage image, double sizeGarbage) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixel = width * height;
        double imgGarbage = sizeGarbage * totalPixel; // Variable for cleaning up garbage in the images

        // Find the background color
        int backgroundColor = Background.findBackgroundColor(image);

        boolean[][] visitedPixels = new boolean[width][height];
        int silhouetteCount = 0;

        // Iterate through each pixel in the image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!visitedPixels[x][y] && isSilhouette(image, x, y, backgroundColor)) {
                    // Find a silhouette, increment count and perform DFS to mark connected pixels
                    int size = bfs(image, visitedPixels, x, y, backgroundColor);
                    if (size >= imgGarbage) {
                        silhouetteCount++;
                    }
                }
            }
        }

        return silhouetteCount;
    }

    /**
     * Implementation of the algorithm of Breadth-first Search for detecting silhouettes
     *
     * @param image The received image
     * @param visitedPixels Array of visited pixels
     * @param startX X coordinate of the pixel
     * @param startY Y coordinate of the pixel
     * @param backgroundColor The background colour
     * @return Silhouette size
     */
    protected static int bfs(BufferedImage image, boolean[][] visitedPixels, int startX, int startY, int backgroundColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = 0; // Silhouette size

        LinkedList<int[]> listPixels = new LinkedList<>();
        listPixels.add(new int[]{startX, startY});

        // Define the 4 possible directions for neighbors
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!listPixels.isEmpty()) {
            int[] currentPixel = listPixels.poll();
            int x = currentPixel[0];
            int y = currentPixel[1];

            // Check if the pixel is within the bounds of the image and is not visited
            if (x >= 0 && x < width && y >= 0 && y < height && !visitedPixels[x][y]
                    && isSilhouette(image, x, y, backgroundColor)) {
                // Mark the currentPixel pixel as visited
                visitedPixels[x][y] = true;
                size++;

                // Explore all neighbors
                for (int i = 0; i < 4; i++) {
                    int newX = x + dx[i];
                    int newY = y + dy[i];

                    // Add neighboring pixels to the listPixels to be processed
                    listPixels.add(new int[]{newX, newY});
                }
            }
        }

        return size;
    }

    /**
     * Check if the pixel belongs to the silhouette.
     * Compare brightness to determine if the pixel is background or not
     *
     * @param image The received image
     * @param x X coordinate of the pixel
     * @param y Y coordinate of the pixel
     * @param backgroundColor The background colour
     * @return True if the pixel belongs to a silhouette
     */
    protected static boolean isSilhouette(BufferedImage image, int x, int y, int backgroundColor) {
        int pixelColor = image.getRGB(x, y);

        Color rgbPixel = new Color(pixelColor);
        Color rgbBackground = new Color(backgroundColor);

        return !isSameColorWithBackground(rgbPixel, rgbBackground); // If pixel color is similar to background color, it's background
    }

    /**
     * Check if pixel is the same color with a background.
     * Compare brightness to determine if the pixel is background or not
     *
     * @param rgbPixel Get the pixel colors
     * @param rgbBackground Get the background colors
     * @return True if the pixel is background
     */
    protected static boolean isSameColorWithBackground(Color rgbPixel, Color rgbBackground) {
        double threshold = 100; // Threshold to determine if pixel is the same color with a background

        // Compare brightness to determine if the pixel is background or not
        return Math.abs(rgbPixel.pixelAlpha() - rgbBackground.pixelAlpha()) < threshold &&
                Math.abs(rgbPixel.pixelRed() - rgbBackground.pixelRed()) < threshold &&
                Math.abs(rgbPixel.pixelGreen() - rgbBackground.pixelGreen()) < threshold &&
                Math.abs(rgbPixel.pixelBlue() - rgbBackground.pixelBlue()) < threshold;
    }

    /**
     * Get the pixel colors
     *
     * @param pixelColor
     */
    protected record Color(int pixelColor) {
        public int pixelAlpha() {
            return (pixelColor >> 24) & 0xFF;
        }

        public int pixelRed() {
            return (pixelColor >> 16) & 0xff;
        }

        public int pixelGreen() {
            return (pixelColor >> 8) & 0xff;
        }

        public int pixelBlue() {
            return pixelColor & 0xff;
        }
    }
}
