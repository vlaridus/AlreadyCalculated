/**********************************************************
 * Title:	 		AlreadyCalculated
 * Filename:	 	a lot
 * Author:  			Sebastian Desaulniers
 * Teacher:  		Mr. Ostropolec
 * Course:			ICS3U
 * Due Date: 		End of Unit 2..?
 * Description:		Something really cool
 * Creation date:	09/27/2017
 * Notes:
 *  I tried my best with the comments and code, alright?
 ***********************************************************/

package main.java.neural_network;

import main.java.algorithm.Main;
import main.java.algorithm.NumberImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by minnow on 10/14/17
 */
public class NeuralNetwork
{

	private final int GRAYSCALE_AMT = 90;
	// higher = darker// true
	// If it's too hard to see (like dark pixels), turn it down

	private Random random;

	private String inputString = "";
	private String hiddenString = "";

	private int[][] inputs;
	private int[][][] hiddens;
	private double[] order;
	private double[] averaged;

	private int inputLayerSize, hiddenLayerSize, outputLayerSize;

	private NumberImage[] numbers;

	public NeuralNetwork(int inputLayerSize, int hiddenLayerSize, int outputLayerSize)
	{
		this.inputLayerSize = inputLayerSize;
		this.hiddenLayerSize = hiddenLayerSize;
		this.outputLayerSize = outputLayerSize;

		this.averaged = new double[inputLayerSize];
		this.order = new double[inputLayerSize];

		random = new Random();

		inputs = new int[this.inputLayerSize + 10][this.inputLayerSize + 10];
		hiddens = new int[91][this.hiddenLayerSize][this.inputLayerSize];
		for (int i = 0; i < 40; i++)
			for (int j = 0; j < this.hiddenLayerSize; j++)
				for (int k = 0; k < this.hiddenLayerSize; k++)
					hiddens[i][j][k] = 1;

		this.numbers = new NumberImage[5000];
		for (int i = 0; i < this.numbers.length; i++)
		{
			this.numbers[i] = new NumberImage("?");
			this.numbers[i].setBits(Utils.convertToBool(hiddens[0]));
		}
	}

	public BufferedImage boundary(BufferedImage image, int x1, int y1, int x2, int y2)
	{
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		img.getGraphics().drawImage(image, 0, 0, null);
		for (int i = x1; i < x2; i++)
			img.setRGB(i, y1, Color.RED.getRGB());

		for (int i = y1; i < y2; i++)
			img.setRGB(x1, i, Color.RED.getRGB());

		for (int i = x2; i >= x1; i--)
			img.setRGB(i, y2, Color.RED.getRGB());

		for (int i = y2; i >= y1; i--)
			img.setRGB(x2, i, Color.RED.getRGB());

		return img;
	}

	public int numPoints = -1;

	private int[][] points;
	int[] max, min;

	private int fileIndex = 0;

	public void setPoint(String ans, int x, int y)
	{
		if (numPoints == -1)
		{
			points = new int[2][2];
			numPoints = 0;
			return;
		}

		double ratioX = 700.0 / 144;
		double ratioY = 400.0 / 144;
		points[numPoints][0] = (int) (x / ratioX);
		points[numPoints][1] = (int) (y / ratioY);
		Main.image.setRGB((int) (x / ratioX), (int) (y / ratioY), Color.BLACK.getRGB()); // fix
		numPoints++;

		if (numPoints >= 2)
		{
			min = points[0];
			max = points[1];
			if (!hiddenString.isEmpty())
			{
//				min[1] = (int) (min[1] * (176 / 144.0));
//				max[1] = (int) (max[1] * (176 / 144.0));
				int c = (int) hiddenString.charAt(0);
				int index = fileIndex;

				this.numbers[index].setIndex(index);
				this.numbers[index].setGuess((char) c);

				this.numbers[index].setWidth(max[0] - min[0]);
				this.numbers[index].setHeight(max[1] - min[1]);
				this.numbers[index].setCoordinates(new Point(min[0], min[1]));
				this.numbers[index].setBits(Utils.convertToBool(this.inputs, min[0], min[1], max[0], max[1]));

				fileIndex++;
			}
			hiddenString = "";
//			guessNumberImage();

			points = new int[2][2];
			numPoints = -1;
		}
	}

	public void solveImage(BufferedImage img)
	{
		for (int i = 0; i < inputLayerSize; )
		{
			NumberImage number = guessNumberImage(i, 0, i + 5, this.inputLayerSize);

			System.out.printf("Found number: %c\n", number.getGuess());

			int c = number.getIndex();

			averaged[c]++;
			if (c < 0 || number.getWidth() == 0)
				i++;
			else
				i += number.getWidth();
		}
//		for (int i = '*'; i <= '9'; i++)
//		{
//			System.out.printf("Character: %c, count: %d\n", (char) i, (int) averaged[i - 33]);
//			averaged[i - 33] = 0;
//		}

	}

	// 1 == black
	// 0 == white

	public BufferedImage cropImage(BufferedImage image)
	{
		BufferedImage res = image.getSubimage(0, 0, 176, inputLayerSize);
		return res;
	}

	public BufferedImage grayscaleImage(BufferedImage image)
	{
		int w = inputLayerSize;
		int h = inputLayerSize;

		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);//, BufferedImage.TYPE_USHORT_GRAY);

		result.getGraphics().drawImage(image, 0, 0, null);

		WritableRaster raster = result.getRaster();
		int[] pixels = new int[w];
		for (int i = 0; i < h; i++)
		{
			raster.getPixels(0, i, w, 1, pixels);
			for (int j = 0; j < pixels.length; j++)
			{
				if (pixels[j] < GRAYSCALE_AMT)
				{
					this.inputs[i][j] = 1;
					pixels[j] = 0;
				}
				else
				{
					this.inputs[i][j] = 0;
					pixels[j] = 255;
				}
			}
			raster.setPixels(0, i, h, 1, pixels);
		}

		return result;
	}

	public NumberImage guessNumberImage()
	{
		NumberImage lowest = null;
		double currentDistance = 0;

		for (NumberImage imageNumber : this.numbers)
		{
			if (imageNumber.getWidth() == 0)
				continue;

			double dist = imageNumber.cost(Utils.convertToBool(this.inputs), true);

			if (dist > currentDistance)
			{
				currentDistance = dist;
				lowest = imageNumber;
			}
		}
		return (lowest == null) ? this.numbers[0] : lowest;
	}

	public NumberImage guessNumberImage(int x1, int y1, int x2, int y2)
	{
		NumberImage lowest = null;
		double currentDistance = 0;

		for (NumberImage imageNumber : this.numbers)
		{
			if (imageNumber.getWidth() == 0)
				continue;

			double dist = imageNumber.cost(Utils.convertToBool(this.inputs, x1, y1, x1 + 20, y1 + 30), true);
//			System.out.println(dist);
			if (dist > currentDistance)
			{
				System.out.println(dist);
				currentDistance = dist;
				lowest = imageNumber;
			}
		}
		if (lowest == null)
		{
			System.out.println("not found.");
			return this.numbers[0];
		}
		return lowest;
	}

	public void submitGuess(int guess)
	{
		char c = (char) guess;
		guess = fileIndex;

		this.numbers[guess].setIndex(guess);
		this.numbers[guess].setGuess(c);

		this.numbers[guess].setWidth(20);
		this.numbers[guess].setHeight(30);
		this.numbers[guess].setCoordinates(new Point(62, 20));
		this.numbers[guess].setBits(Utils.convertToBool(this.inputs, 62, 20, 82, 50));

		fileIndex++;
	}

	public void guessed(boolean right, int index, int guess)
	{
		if (right)
		{

//			numbers[index].setBits(Utils.convertToBool(this.hiddens[index - 33]));
		}
		else
		{
			char c = (char) index;
			index = fileIndex;

			this.numbers[index].setIndex(index);
			this.numbers[index].setGuess(c);

			this.numbers[index].setWidth(20);
			this.numbers[index].setHeight(30);
			this.numbers[index].setCoordinates(new Point(62, 20));
			this.numbers[index].setBits(Utils.convertToBool(this.inputs, 62, 20, 82, 50));

			System.out.println(c);

			fileIndex++;
			System.out.println(index);
//			numbers[index - 33].setWidth(this.inputLayerSize);
//			numbers[index - 33].setHeight(this.inputLayerSize);
			hiddenString = Character.toString((char) index);
//			numbers[index - 33].setBits(Utils.convertToBool(this.inputs));
//			numbers[index - 33].setIndex(index - 33);
//			numbers[index - 33].setGuess((char) index);
			// TODO: overload
		}
	}

	public void read(String filename) throws FileNotFoundException
	{
		// '!' to '>' to 'x'
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter(",\\s*|\\s+");

		char currentCharacter = 0;

		for (NumberImage number : this.numbers)
		{
			if (scanner.hasNext())
				number.setGuess(scanner.next().charAt(0));

			if (scanner.hasNextInt())
				number.setWidth(scanner.nextInt());
			if (scanner.hasNextInt())
				number.setHeight(scanner.nextInt());
			if (scanner.hasNextInt())
				number.setCoordinates(new Point(scanner.nextInt(), scanner.nextInt()));

			if (number.getWidth() == 0)
				continue;

			fileIndex++;

			currentCharacter++;

			for (int i = number.getX(); i < number.getWidth() + number.getX(); i++)
			{
				for (int j = number.getY(); j < number.getHeight() + number.getY(); j++)
				{
					if (scanner.hasNextInt())
					{
						int val = scanner.nextInt();
						number.setBitAt(val == 1, i, j);
					}
					else
					{
						i = number.getWidth() * 3;
						break;
					}
				}
			}
			if (scanner.hasNextLine())
				scanner.nextLine();
		}
		scanner.close();
//		Main.picLabel.requestFocusInWindow();

		System.out.println("Successfully read from file.");
	}

	public void save(String filename) throws FileNotFoundException
	{
		// '!' to '>' to 'x'
		File file = new File(filename);
		PrintWriter writer = new PrintWriter(file);

		StringBuilder grid = new StringBuilder();

		char currentCharacter = 0;
		for (NumberImage number : this.numbers)
		{
			grid.append(number.getGuess());
			grid.append(",");
			grid.append(number.getWidth());
			grid.append(",");
			grid.append(number.getHeight());
			grid.append(",");
			grid.append(number.getX());
			grid.append(",");
			grid.append(number.getY());
			grid.append(",");

			if (number.getWidth() == 0)
			{
				grid.append('\n');
				continue;
			}
			System.out.printf("Number width: %d\n", number.getWidth());
			System.out.printf("Number height: %d\n", number.getHeight());
			System.out.printf("x: %d\n", number.getX());
			System.out.printf("y: %d\n", number.getY());

			for (int i = number.getX(); i < number.getX() + number.getWidth(); i++)
			{
				for (int j = number.getY(); j < number.getY() + number.getHeight(); j++)
				{
//					grid.append((number.getBitAt(i - number.getX(), j - number.getY()) ? "1" : "0"));
					grid.append((number.getBitAt(i, j) ? "1" : "0"));
					grid.append(",");
				}
			}

			grid.append('\n');
			currentCharacter++;
		}

		writer.write(grid.toString());
		writer.close();
//		Main.picLabel.requestFocusInWindow();
		System.out.println("Successfully wrote to file.");
	}
}
