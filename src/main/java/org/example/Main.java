package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main () {
        try {
            URL image = getClass().getClassLoader().getResource("hila.jpg");// להכניס קובץ של התמונה
            if (image != null) {
                BufferedImage original = ImageIO.read(image);// מחזיק את הרולזציה של התמונה
                for (int i = 0; i < 300; i++) {
                    Color color = getMostCommonPixelColor(original);
                    original = changeColorToRed(original,color);
                    System.out.println(i);
                }

                ImageIO.write(original, "jpg", new File("C:\\Users\\הילה\\Downloads\\stream_api-master - Copy\\Pic\\src\\main\\resources\\hila2.jpg"));

            } else {
                System.out.println("Cannot find!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Color getMostCommonPixelColor(BufferedImage image) {
        // Create a map to store the count of each color
        Map<Integer, Integer> colorCountMap = new HashMap<>();

        // Iterate over each pixel in the image and count the occurrences of each color
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (pixel == Color.RED.getRGB()) {
                    continue;
                }


                // Increment the count for this color in the map
                int count = colorCountMap.getOrDefault(pixel, 0);
                colorCountMap.put(pixel, count + 1);
            }
        }

        // Find the color with the maximum count
        int maxCount = 0;
        int mostCommonColor = 0;
        for (Map.Entry<Integer, Integer> entry : colorCountMap.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                mostCommonColor = entry.getKey();
            }
        }

        // Extract the RGB values from the most common color
        int red = (mostCommonColor & 0x00ff0000) >> 16;
        int green = (mostCommonColor & 0x0000ff00) >> 8;
        int blue = mostCommonColor & 0x000000ff;

        // Create and return the Color object
        return new Color(red, green, blue);
    }



    public static BufferedImage changeColorToRed(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Create a new BufferedImage with the same dimensions and type as the original image
        BufferedImage newImage = new BufferedImage(width, height, image.getType());

        // Iterate over each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the color of the current pixel
                Color pixelColor = new Color(image.getRGB(x, y));

                // If the color matches the specified color, change it to red
                if (pixelColor.equals(color)) {
                    pixelColor = Color.RED;
                }

                // Set the modified color in the new image
                newImage.setRGB(x, y, pixelColor.getRGB());
            }
        }

        // Return the new image
        return newImage;
    }
}

