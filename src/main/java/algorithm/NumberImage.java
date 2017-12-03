package main.java.algorithm;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Created by minnow on 10/18/17
 */
public class NumberImage
{
	private BufferedImage image;
	private Point coordinates = new Point(0, 0);
	private int[] size = new int[]{0, 0};
	private int index = 0;
	private String string = "";

	private char guess = '?';

	private NormalizedLevenshtein level = new NormalizedLevenshtein();

	private Cosine cosine = new Cosine();

	public void setGuess(char newGuess)
	{
		this.guess = newGuess;
	}

	public char getGuess()
	{
		return this.guess;
	}

	private boolean[][] bits; // white on the image is true, black is false

	public BufferedImage getImage()
	{
		return this.image;
	}

	public void setImage(BufferedImage img)
	{
		if (img == null)
			throw new RuntimeException("NumberImage error: cannot set image to null!");
		this.image = img;
	}

	private String toString(boolean[][] input, boolean predicate)
	{
		StringBuilder res = new StringBuilder();

		for (int x = coordinates.x; x < this.getWidth() + coordinates.x; x++)
		{
			for (int y = coordinates.y; y < this.getHeight() + coordinates.y; y++)
			{
				int i = x - coordinates.x;
				int j = y - coordinates.y;
//				if (predicate)
				res.append((input[x][y] ? "1" : "0"));
//				else
//					res.append((input[i][j] ? "1" : "0"));
			}
		}

		return res.toString();
	}

	private boolean[][] stretch(boolean[][] input, int rFactor, int cFactor)
	{
		int row = input.length * rFactor;
		int col = input[0].length * cFactor;

		boolean[][] out = new boolean[row][col];

		for (int x = 0; x < row; x++)
			for (int y = 0; y < col; y++)
				out[x][y] = input[x / rFactor][y / cFactor];

		return out;
	}

	public void setBits(boolean[][] newBits)
	{
		this.bits = newBits;
		this.string = toString(this.bits,true);
	}

	public void setIndex(int newIndex)
	{
		this.index = newIndex;
	}

	public int getIndex()
	{
		return index;
	}

	public boolean[][] getBits()
	{
		return this.bits;
	}

	public boolean getBitAt(int x, int y)
	{
		return this.bits[x][y];
	}

	public int getX()
	{
		return coordinates.x;
	}

	public int getY()
	{
		return coordinates.y;
	}

	public void setBitAt(boolean value, int x, int y)
	{
		this.bits[x][y] = value;
	}

	public void setSize(int[] newSize)
	{
		if (newSize == null)
			throw new RuntimeException("NumberImage error: cannot set size to null!");

		this.size = newSize;
	}

	public double cost(boolean[][] input, boolean predicate)
	{
		String inputString = toString(input, true);
		this.string = toString(this.bits, true);

		if (inputString.isEmpty() || this.string.isEmpty())
			return 0; // bad

//		System.out.println(inputString);
//		System.out.println(this.string);

//		return level.distance(inputString, this.string);
		return level.similarity(inputString, this.string) + cosine.similarity(inputString, this.string);
//		return cosine.similarity(inputString, this.string);
	}

	public int getWidth()
	{
		return this.size[0];
	}

	public int getHeight()
	{
		return this.size[1];
	}

	public void setWidth(int newWidth)
	{
		this.size[0] = newWidth;
	}

	public void setHeight(int newHeight)
	{
		this.size[1] = newHeight;
	}

	public void setCoordinates(Point newCoordinates)
	{
		if (newCoordinates == null)
			throw new RuntimeException("NumberImage error: cannot set coordinates null!");
		this.coordinates = newCoordinates;
	}

	public Point getCoordinates()
	{
		return this.coordinates;
	}

	public NumberImage(String expr, BufferedImage img, int x, int y, int width, int height)
	{
		this.image = img;
		this.coordinates = new Point(x, y);
		this.size = new int[]{x, y};
		this.index = 0;
	}

	public NumberImage(String expr)
	{
		this.index = 0;
		this.bits = new boolean[300][300];
	}

	public NumberImage(String expr, int index)
	{
		this.index = index;
		this.bits = new boolean[300][300];
	}
}
