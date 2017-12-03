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

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame
{
	private JPanel panel;

	/**
		@param screen screen number to use, OS-specific
	 */
	private void showScreen(int screen)
	{
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

		if (screen > -1 && screen < screenDevices.length)
			this.setLocation(screenDevices[screen].getDefaultConfiguration().getBounds().x, super.getY());
		else if (screenDevices.length > 0)
			this.setLocation(screenDevices[0].getDefaultConfiguration().getBounds().x, super.getY());
		else
			throw new RuntimeException("No screens found!");
	}

	public JPanel getPanel()
	{
		return panel;
	}

	private void initUI()
	{
		getPanel().setFocusable(true);
		getPanel().addKeyListener(new Keyboard());

		showScreen(1);
	}

	public UI(int width, int height)
	{
		panel = new JPanel();

		super.setTitle("AlreadyCalculated");
		super.setSize(width, height);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.setLayout(new GridLayout());

		super.add(panel);

		initUI();
	}

	public void run()
	{
		setVisible(true);
	}
}
