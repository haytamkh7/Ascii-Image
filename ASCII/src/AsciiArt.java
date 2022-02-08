import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class AsciiArt {
	private static PrintWriter output;
	
	public static void main(String[] args) {		
		// loding the OpenCV library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// Source of the image
		String source = "photo.jpeg";
		
		// Loading the source image
		Mat matrix = Imgcodecs.imread(source);
		
		try {
			output = new PrintWriter("output.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// Clone of the original image
		Mat c = matrix.clone();
		
		// Resizing the image
		Imgproc.resize(matrix, c, new Size(200, 200), 0, 0, Imgproc.INTER_AREA);

		// Iterating through all the pixels and calculating the brightness of each pixel
		// and saving this value to the brightness 2D array
		double [][] brightness = new double [c.height()][c.width()];
		for(int i = 0; i < c.height(); i++) {
			for(int j = 0; j < c.width(); j++) {
				double intensity = (c.get(i, j)[0] + c.get(i, j)[1] + c.get(i, j)[2]) / 3.0;
				brightness[i][j] = intensity;
			}
		}
		
		// Mapping each brightness value in the brighness array to the corresponding character
		char [][] brightnessToAscii = new char [c.height()][c.width()];
		String chars = "`^\\\",:;Il!i~+_-?][}{1)(|\\\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

		for (int i = 0; i < brightness.length; i++) { //this equals to the row in our matrix.
	         for (int j = 0; j < brightness[i].length; j++) { //this equals to the column in each row.
	        	 Character c1 = chars.charAt((int) Math.ceil(((chars.length() - 1) * brightness[i][j]/255)));
	        	 brightnessToAscii[i][j] = c1;
	         }
	      }
		
		// Printing the output of the console to a Textfield (GUI)
		for (int i = 0; i < brightnessToAscii.length; i++) { //this equals to the row in our matrix.
	         for (int j = 0; j < brightnessToAscii[i].length; j++) { //this equals to the column in each row.
	        	 output.print(String.valueOf(brightnessToAscii[i][j]) + String.valueOf(brightnessToAscii[i][j]) + String.valueOf(brightnessToAscii[i][j]));
	         }
	         output.println();
	      }
		
		output.close();
		System.out.println("Check the output file");
	}
}
