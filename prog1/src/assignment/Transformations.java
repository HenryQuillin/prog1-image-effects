package assignment;
/**
 *
 * CS314H Programming Assignment 1 - Java image processing
 *
 * Included is the Invert effect from the assignment.  Use this as an
 * example when writing the rest of your transformations.  For
 * convenience, you should place all of your transformations in this file.
 *
 * You can compile everything that is needed with
 * javac -d bin src/assignment/*.java
 *
 * You can run the program with
 * java -cp bin assignment.JIP
 *
 * Please note that the above commands assume that you are in the prog1
 * directory.
 */

import java.util.ArrayList;

class Invert extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = ~pixels[y][x];
            }
        }
        return pixels;
    }
}

// Iterate through the pixels array and replace each pixel with a new pixel that does not contain a red value 
class NoRed extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(0, getGreen(pixels[y][x]), getBlue(pixels[y][x]));
            }
        }
        return pixels;
    }
}

// Iterate through the pixels array and replace each pixel with a new pixel that does not contain a green value 
class NoGreen extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(getRed(pixels[y][x]), 0, getBlue(pixels[y][x]));
            }
        }
        return pixels;
    }
}

// Iterate through the pixels array and replace each pixel with a new pixel that does not contain a blue value 
class NoBlue extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(getRed(pixels[y][x]), getGreen(pixels[y][x]), 0);
            }
        }
        return pixels;
    }
}


class RedOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(getRed(pixels[y][x]), 0, 0);
            }
        }
        return pixels;
    }
}

class GreenOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(0, getGreen(pixels[y][x]), 0);
            }
        }
        return pixels;
    }
}

class BlueOnly extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = makePixel(0, 0, getBlue(pixels[y][x]));
            }
        }
        return pixels;
    }
}

// For each pixel, get the average of the 3 RBG values and create a new pixel with that average value for each shade.
class BlackAndWhite extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int average = (getRed(pixels[y][x]) + getGreen(pixels[y][x]) + getBlue(pixels[y][x])) / 3;

                pixels[y][x] = makePixel(average, average, average);
            }
        }
        return pixels;
    }
}


// Loop through half of the columns and swap the current pixel with the corrensponding pixel on the right side.  
class VerticalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width / 2; x++) {
            for (int y = 0; y < height; y++) {
                int temp = pixels[y][x]; // Set temp  to the current pixel on the left side
                int rightP = pixels[y][width - x - 1]; // Get the right pixel by substracting (x index - 1) from the image width
                pixels[y][x] = rightP; // Set the left pixel to the right pixel
                pixels[y][width - x - 1] = temp; // Set the right pixel to the old left pixel
            }
        }
        return pixels;
    }
}

// Loop through half of the rows and swap the current pixel with the corrensponding pixel on the right side.  
class HorizontalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height / 2; y++) {
                int temp = pixels[y][x];
                int rightP = pixels[height - y - 1][x];
                pixels[y][x] = rightP;
                pixels[height - y - 1][x] = temp;
            }
        }
        return pixels;
    }
}

// grow() maps each pixel in the original array to 4 pixels in s new array.  
class Grow extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;
        // Initialize new a new array that is double the size of the original array
        int[][] newArr = new int[height * 2][width * 2]; 
        for (int y = 0, newY = 0; y < height; y++, newY = newY + 2) {
            for (int x = 0, newX = 0; x < width; x++, newX = newX + 2) {
                // here we are incrementing newX and newY by 2 instead of 1 so to not overwrite the previously copied pixel 

                int currPixel = pixels[y][x];

                newArr[newY][newX] = currPixel;
                newArr[newY + 1][newX] = currPixel;
                newArr[newY][newX + 1] = currPixel;
                newArr[newY + 1][newX + 1] = currPixel;

            }
        }
        
        return newArr;
    }
}

class Shrink extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {

                            
        int width = pixels[0].length;
        int height = pixels.length;
        
        int[][] newArr = new int[height / 2][width / 2];

        for (int x = 0, newX = 0; x < width; newX++, x = x + 2) {
            for (int y = 0, newY = 0; y < height; newY++, y = y + 2) {
                int pixel1 = pixels[y][x];
                // if image cannot be any smaller 
                if (width <= 1 && height <= 1) {
                    return pixels;
                // if only 1 column 
                } else if (width <= 1) {
                    newArr = new int[height / 2][1];
                    int pixel2 = pixels[y + 1][x];
                    int averageR = (getRed(pixel1) + getRed(pixel2)) / 2;
                    int averageG = (getGreen(pixel1) + getGreen(pixel2)) / 2;
                    int averageB = (getBlue(pixel1) + getBlue(pixel2)) / 2; 
                    int newPixel = makePixel(averageR, averageG, averageB);
                    newArr[newY][0] = newPixel;
                // if only 1 row 
                } else if (height <= 1) {
                    newArr = new int[1][width / 2];
                    int pixel2 = pixels[y][x+1];
                    int averageR = (getRed(pixel1) + getRed(pixel2)) / 2;
                    int averageG = (getGreen(pixel1) + getGreen(pixel2)) / 2;
                    int averageB = (getBlue(pixel1) + getBlue(pixel2)) / 2; 
                    int newPixel = makePixel(averageR, averageG, averageB);
                    newArr[0][newX] = newPixel;
                } else {
                    newArr = new int[height / 2][width / 2];

                    int pixel2 = pixels[y + 1][x];
                    int pixel3 = pixels[y][x+1];
                    int pixel4 = pixels[y + 1][x + 1];
                    
                    int averageR = (getRed(pixel1) + getRed(pixel2) + getRed(pixel3) + getRed(pixel4)) / 4;
                    int averageG = (getGreen(pixel1) + getGreen(pixel2) + getGreen(pixel3) + getGreen(pixel4)) / 4;
                    int averageB = (getBlue(pixel1) + getBlue(pixel2) + getBlue(pixel3) + getBlue(pixel4)) / 4;
    
                    int newPixel = makePixel(averageR, averageG, averageB);
                    newArr[newY][newX] = newPixel; 
                }


            }
        }
        return newArr;
    }
}

// Threshold iterates over each pixel and uses if statements to check whether the red, green, and or blue values of the pixel are above the threshold, sets the values to 0 if below and 255 if above, and sets the current pixel to a new pixel with the three new RBG values.
class Threshold extends ImageEffect {


    public Threshold() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("threshold",
                                           "set each of " +
                                           "the R, G, and B values in the output image to either fully on (255) or fully off (0), depending on whether its" +
                                           "value in the input image is above some threshold",
                                           10, 0, 1000));
    }

    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
                            
            int width = pixels[0].length;
            int height = pixels.length;     
            int red; 
            int green; 
            int blue; 
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (getRed(pixels[y][x]) > params.get(0).getValue()) {
                        red = 255;  
                    } else {
                        red = 0; 
                    }
                    if (getGreen(pixels[y][x]) > params.get(0).getValue()) {
                        green = 255;  
                    } else {
                        green = 0; 
                    }
                    if (getBlue(pixels[y][x]) > params.get(0).getValue()) {
                        blue = 255;  
                    } else {
                        blue = 0; 
                    }

                    pixels[y][x] = makePixel(red, green, blue);
                }
            }
        
        return pixels;
    }
}
