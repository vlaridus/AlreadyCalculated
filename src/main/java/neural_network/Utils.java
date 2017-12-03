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

/**
 * Created by minnow on 10/14/17
 */
public class Utils
{
	// Assume same width and height of array
	static boolean[][] convertToBool(int[][] inputs)
	{
		int length = inputs.length;

		boolean[][] res = new boolean[length][length];

		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				res[i][j] = (inputs[i][j] == 1);

		return res;
	}
	static boolean[][] convertToBool(int[][] inputs, int x1, int y1, int x2, int y2)
	{
		boolean[][] res = new boolean[inputs.length][inputs.length];
//		System.out.printf("x1: %d, y1: %d, x2: %d, y2: %d\n", x1, y1, x2, y2);
//		int length = inputs.length;
//
//		for (int i = 0; i < length; i++)
//		{
//			for (int j = 0; j < length; j++)
//			{
//				if (inputs[j][i] == 1)
//					System.out.printf("x: %d y: %d\n", i, j);
//			}
//		}
		for (int i = x1; i < x2; i++)
			for (int j = y1; j < y2; j++)
				res[i][j] = (inputs[j][i] == 1);

		return res;
	}
}
