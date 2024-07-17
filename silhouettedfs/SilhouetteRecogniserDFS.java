package com.shpp.p2p.cs.otavlui.silhouettedfs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The program counts the number of silhouettes in the image
 * using the Depth-first search algorithm and displays it
 */
public class SilhouetteRecogniserDFS {
    private static final double SIZE_GARBAGE = 0.001; // Variable for cleaning up garbage

    public static void main(String[] args) {
        String fileName;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "test.jpg";
        }

        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            int silhouetteCount = Silhouette.countSilhouettes(image, SIZE_GARBAGE);
            System.out.println("Number of silhouettes: " + silhouetteCount);
        } catch (IOException e) {
            System.err.println("Error reading image!");
        }
    }
}