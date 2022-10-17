/* 
 * Name: Jasmine Mann
 * Section Leader: Trey Connelly 
 */

/*
 * This file allows the user to make different changes to a selected image.
 * It grants the user the ability to turn the image they selected left, 
 * right, and also flip it horizontally. It allows the user to apply
 * a negative affect on the image which takes the inverse color of each
 * pixel of the image and changes it to that color. The user can also 
 * turn green pixels into transparent pixels which creates a greenscreen
 * affect. They can also blur the image, crop any portion of the image, 
 * and equalize the picture selected. It is similar to the program photoshop.
 */

import java.util.ArrayList;

import acm.graphics.*;

public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface {

	/*
	 * Gets the pixels of the image selected and changes them 
	 * to another location that turns the entire image left 
	 * 90 degrees
	 */
	public GImage rotateLeft(GImage source) {
		int beforeImage[][]= source.getPixelArray (); 
		int width = beforeImage[0].length;
		int height = beforeImage.length;
		int afterImage[][] = new int[width][height]; 
		for(int col = 0; col < height; col++) {
			for(int row = 0; row < width; row++) { // goes through each pixel in the image selected by the user
				afterImage [row][col] = beforeImage [col][width - row - 1]; 
			}
		}
		source.setPixelArray(afterImage);
		return source;
	}
	/*
	 * Gets the pixels of the image selected and changes them 
	 * to another location that turns the entire image right 
	 * 90 degrees
	 */
	public GImage rotateRight(GImage source) {
		int beforeImage[][]= source.getPixelArray (); 
		int width = beforeImage[0].length;
		int height = beforeImage.length;
		int afterImage[][] = new int[width][height]; 
		for(int col = 0; col < height; col++) {
			for(int row = 0; row < width; row++) { // goes through each pixel in the image selected by the user
				afterImage [row][col] = beforeImage [height - col - 1][row]; 
			}
		}
		source.setPixelArray(afterImage);
		return source;
	}

	/*
	 * Gets the pixels of the image selected and changes them 
	 * to another location that flips the entire image 
	 * horizontally
	 */
	public GImage flipHorizontal(GImage source) {
		int beforeImage[][]= source.getPixelArray (); 
		int width = beforeImage[0].length;
		int height = beforeImage.length;
		int afterImage[][] = new int[height][width];
		for(int col = 0; col < width; col++) {
			for(int row = 0; row < height; row++) { // goes through each pixel in the image selected by the user
				afterImage [row][col] = beforeImage [row][width - col - 1]; 
			}
		}
		source.setPixelArray(afterImage);
		return source;
	}

	/*
	 * Gets all the pixels of the image selected and finds 
	 * the inverse of those pixels to make up the image, 
	 * applying a negative color affect
	 */
	public GImage negative(GImage source) {
		int[][] pixels = source.getPixelArray();
		int width = pixels[0].length;
		int height = pixels.length;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) { // goes through each pixel in the image selected by the user
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int inverseNum = 255;
				int redInv = inverseNum - red; // minuses the red color value of the pixel selected from the number 255 to find the inverse of color of that pixel
				int greenInv = inverseNum - green;
				int blueInv = inverseNum - blue;
				int inversePixel = GImage.createRGBPixel(redInv, greenInv, blueInv); //creates the inverse pixel using the inverse of the colors found from the original pixel
				pixels[row][col] = inversePixel;
			}
		}
		source.setPixelArray(pixels);
		return source;
	}

	/*
	 * Checks all the pictures of the image selected and finds pixels 
	 * with a certain amount of green color, and changes those pixels 
	 * to transparent pixels, applying a greenscreen affect on the image
	 */
	public GImage greenScreen(GImage source) {
		int[][] pixels = source.getPixelArray();
		int width = pixels[0].length;
		int height = pixels.length;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) { // goes through each pixel in the image selected by the user
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int blue = GImage.getBlue(pixel);
				int green = GImage.getGreen(pixel);
				int biggerNum = Math.max(red, blue); // Finds the biggest number between the red and blue values
				if(green > biggerNum * 2) { //will change the pixel color to transparent if the green value is bigger than twice the largest blue and red value
					int transparentPixel = GImage.createRGBPixel(red, green, blue, 0);
					pixels[row][col] = transparentPixel;
				}
			}
		}
		source.setPixelArray(pixels);
		return source;
	}

	/*
	 * Gets each pixel of the image selected as well as it's
	 * neighboring pixels and averages the red, green, and blue
	 * components to create a new pixel, creating the blurring 
	 * affect
	 */
	public GImage blur(GImage source) {
		int originalImage[][]= source.getPixelArray (); 
		int width = originalImage[0].length;
		int height = originalImage.length;
		int newImage[][] = new int[height][width];
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				int redSum = 0;
				int greenSum = 0;
				int blueSum = 0;
				int numPixels = 0;
				for(int minRow = row - 1; minRow <= row + 1; minRow++) {
					for(int minCol = col - 1; minCol <= col + 1; minCol++) { //goes through each pixel in the cropped section chosen by the user
						if(minRow >= 0 && minRow < height && minCol >=0 && minCol < width) { //goes through each of the neighboring pixels that exist including the pixel in the middle
							int outerPixel = originalImage[minRow][minCol];
							int red = GImage.getRed(outerPixel);
							redSum += red; //adds the red values of each of the neighboring pixel including the middle pixel
							numPixels++; //gets the number of neighboring pixels including the middle pixel
							int green = GImage.getGreen(outerPixel);
							greenSum += green;
							int blue = GImage.getBlue(outerPixel);
							blueSum += blue;
						}
					}
				}
				int newPixRed = redSum / numPixels; //divides the added red values of all the neighboring pixels and middle pixel by the number of neighboring pixels including the middle pixel to get the average red value
				int newPixGreen = greenSum / numPixels;
				int newPixBlue = blueSum / numPixels;
				int newPixel = GImage.createRGBPixel(newPixRed, newPixGreen, newPixBlue); //creates a new pixel with the average red, green, and blue values and adds it to the canvas
				newImage[row][col] = newPixel;
			}
		}
		source.setPixelArray(newImage);
		return source;
	}

	/*
	 * Gets the pixels in the selected space chosen from the user
	 * and displays those pixels, cropping the image
	 */
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int[][]pixels = source.getPixelArray();
		int[][]croppedPixels = new int[cropHeight][cropWidth];
		for(int col = cropX; col < cropWidth + cropX; col++) {
			for(int row = cropY; row < cropHeight + cropY; row++) { //goes through each pixel in the cropped section selected by the user
				int pixel = pixels[row][col];
				croppedPixels[row - cropY][col - cropX] = pixel; // selects the pixels from the cropped section to re-create the original image with only that cropped section
			}
		}
		source.setPixelArray(croppedPixels);
		return source;
	}

	/*
	 * Calls the getLuminosityHistogram, getCumulativeLumHistogram, and 
	 * modifyPixel methods and returns an equalized version of the 
	 * image selected by the user
	 */
	public GImage equalize(GImage source) {
		int[] lumHistogram = getLuminosityHistogram(source);
		int[] cumulativeLumHisto = getCumulativeLumHistogram(source, lumHistogram);
		modifyPixel(source, cumulativeLumHisto);
		return source;
	}

	/*
	 * Gets the luminosity of each pixel of the image selected 
	 * by the user and adds their values to an array that keeps
	 * track of the amount of pixels with a certain luminosity value.
	 */
	private int[] getLuminosityHistogram(GImage source) {
		int[][]pixels = source.getPixelArray();
		int[] lumHistogram = new int[256];
		int width = pixels[0].length;
		int height = pixels.length;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col< width; col++) { // goes through each pixel in the image selected by the user
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int luminosity = computeLuminosity(red, green, blue); //gets the luminosity of the selected pixel using its red, green, and blue values
				lumHistogram[luminosity]++; //adds 1 to the value of the index in the array to keep track of how many pixels have that same luminosity as that index
			}
		}
		return lumHistogram;
	}

	/*
	 * creates a new array and uses information from the array 
	 * lumHistogram that adds the pixels with a certain luminosity value
	 * with pixels that have a luminosity lower than that value.
	 */
	private int[] getCumulativeLumHistogram(GImage source, int[]lumHistogram) {
		int[] cumulativeLumHisto = new int[256];
		for(int i = 0; i < 256; i++) {
			if(i == 0) { // if the index is 0, only add the value of index 0 from lumHistogram
				cumulativeLumHisto[0] = lumHistogram[0];
			}else {
				int addedLums = 0;
				for(int j = i; j >= 0; j--) { 
					addedLums += lumHistogram[j]; //goes to every index apart from 0 and adds the value of that index and as well as the value from indexs less than that one
				}
				cumulativeLumHisto[i] = addedLums; //adds that value to the array cumulativeLumHisto
			}
		}
		return cumulativeLumHisto;
	}

	/*
	 * Goes to each pixel in the image selected by the user and modifies
	 * that pixel through obtaining that pixels luminosity and
	 * using the array cumulativeLumHisto to find the values of 
	 * other pixels with that same luminosity value or lower. It 
	 * puts those values in a mathematical equation to get new 
	 * RGB values and creates a new grayscale pixel which it then
	 * adds to the canvas
	 */
	private GImage modifyPixel(GImage source, int[]cumulativeLumHisto) {
		int[][]pixels = source.getPixelArray();
		int width = pixels[0].length;
		int height = pixels.length;
		int totalPixels = width * height;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) { // goes through each pixel in the image selected by the user
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int pixelLum = computeLuminosity(red, green, blue);
				double pixelsWithLum = cumulativeLumHisto[pixelLum]; // finds the value of all the pixels that have the same or lower luminosity than pixelLum through the array cumulativeLumHisto and saves it to a variable
				int newRGB = (int) (255 * (pixelsWithLum / totalPixels)); // spreads the image's luminosity values across as much of the range of possible luminosity values as possible by using an equation pixelsWithLum, totalPixels and the number 255
				int grayPixel = GImage.createRGBPixel(newRGB, newRGB, newRGB); //creates a grey pixel with the new RGB values and adds it to the canvas
				pixels[row][col] = grayPixel;
			}
		}
		source.setPixelArray(pixels);
		return source;
	}
}
