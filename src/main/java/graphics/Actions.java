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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by minnow on 10/20/17
 */
public class Actions implements ActionListener
{
	private Action action = null;
	private JComponent component = null;

	public Actions(Action action, JComponent component)
	{
		this.action = action;
		this.component = component;
	}

	private void readFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		try
		{
			if (fileChooser.showOpenDialog(this.component) == JFileChooser.APPROVE_OPTION)
				Shared.network.read(fileChooser.getSelectedFile().getAbsolutePath());
		}
		catch (Exception e)
		{
			System.out.println("Reading error: " + e.toString());
		}
	}

	private void saveFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		try
		{
			if (fileChooser.showOpenDialog(this.component) == JFileChooser.APPROVE_OPTION)
				Shared.network.save(fileChooser.getSelectedFile().getAbsolutePath());
		}
		catch (Exception e)
		{
			System.out.println("Saving error: " + e.toString());
		}

	}

	private void train()
	{
		char n = Main.guessField.getText().charAt(0);
		Shared.network.submitGuess(n);
	}

	private void recognize()
	{
		Shared.network.grayscaleImage(Main.image);
		NumberImage number = Shared.network.guessNumberImage(62, 20, 62, 20);
		Main.result.setText(Main.result.getText() + number.getGuess());
	}

	private void changeStates(JComboBox comboBox)
	{
		Main.changeState(comboBox.getSelectedIndex());
	}

	private void changeOutput()
	{
		JTextField area = (JTextField) component;
		Main.changeOutput(area.getText() + "\n= " + ShuntingYard.prevAnswer);
		Main.clearOutput();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (this.action)
		{
			case SAVE_FILE:
				saveFile();
				break;
			case READ_FILE:
				readFile();
				break;
			case CHANGE_STATE:
				changeStates((JComboBox) e.getSource());
				break;
			case OUTPUT_AREA:
				changeOutput();
				break;
			case RECOGNIZE:
				recognize();
				break;
			case TRAIN:
				train();
				break;
		}
	}
}

