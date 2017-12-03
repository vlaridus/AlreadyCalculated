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

package main.java.graphics;

import main.java.algorithm.Main;
import main.java.algorithm.NumberImage;
import main.java.algorithm.ShuntingYard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by minnow on 10/20/17
 */
public class Keyboard implements KeyListener

{
	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
		{
			int n = Main.result.getText().length();
			Main.result.setText(Main.result.getText().substring(0, n - 1));
		}
		if (e.getKeyChar() == 'r')
			Shared.network.numPoints = -1;

		if (e.getKeyChar() == '\n')
		{
//					System.out.println("newline");
//					network.solveImage(Main.image);
//					System.out.println("done");
//				}
//				if (e.getKeyChar() == 'g')
//				{
//					BufferedImage image = network.grayscaleImage(network.cropImage(Main.webcam.getImage()));
//					network.convertImage(image, true);
			System.out.println("Guessing...");

			NumberImage n = Shared.network.guessNumberImage();
			char c = (char) n.getIndex();
			System.out.printf("Guessed: %c, might also be: %c\n", (char) (c), n.getGuess());
			Main.result.setText(Main.result.getText() + Character.toString((char) c));
		}
		else
		{
			char n = e.getKeyChar();
			if (n == ' ')
				ShuntingYard.init(Main.result.getText());
			else if (Character.isLetterOrDigit(n) || n == '+' || n == '*' || n == '-' || n == 'x' || n == '/' || n == '=')
			{
				System.out.println("Submitted: " + n);
				Shared.network.submitGuess(n);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}
}
