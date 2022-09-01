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

class VerticalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width / 2; x++) {
            for (int y = 0; y < height; y++) {
                int temp = pixels[y][x];
                int rightP = pixels[y][width-x -1];
                pixels[y][x] = rightP;
                pixels[y][width-x-1] = temp;
            }
        }
        return pixels;
    }
}

class HorizontalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height /2; y++) {
                int temp = pixels[y][x];
                int rightP = pixels[height-y-1][x];
                pixels[y][x] = rightP;
                pixels[height-y-1][x] = temp;
            }
        }
        return pixels;
    }
}

class Grow extends ImageEffect {
    public int[][] apply(int[][] pixels,
            ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] newArr = new int[height * 2][width * 2];
        for (int y = 0, newY = 0; y < height; y++, newY = newY + 2) {
            for (int x = 0, newX = 0; x < width; x++, newX = newX + 2) {

                // System.out.println("width of new arr: " + newArr[0].length);
                // System.out.println("hieght of new arr: " + newArr.length);

                int currPixel = pixels[y][x];

                // if (newY <= (height * 2) && newX <= (width * 2)) {
                newArr[newY][newX] = currPixel;
                newArr[newY + 1][newX] = currPixel;
                newArr[newY][newX + 1] = currPixel;
                newArr[newY + 1][newX + 1] = currPixel;
                // }

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
            for (int y = 0, newY =0; y < height; newY++, y = y + 2) {
                int pixel1 = pixels[y][x];
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
        return newArr;
    }
}


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
