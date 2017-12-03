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


package main.java.algorithm;

import main.java.graphics.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import com.github.sarxos.webcam.Webcam;
import main.java.graphics.Action;
import main.java.neural_network.NeuralNetwork;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import static java.util.logging.Level.OFF;
import static main.java.graphics.Shared.network;

/**
 * Created by minnow on 10/12/17
 */
public class Main
{
	private static ProgramState state = ProgramState.PROMPT;

	public static BufferedImage image;

	private static JComboBox programStates;
	private static JTextField inputArea;
	private static JTextArea previousAnswers;
	private static JLabel outputArea;

	public static JTextField guessField;

	private static JButton readButton;
	private static JButton saveButton;
	private static JButton recognizeButton;
	private static JButton trainButton;

	public static JLabel result;
	public static JLabel instruction = new JLabel("Put the correct number in the input box and click train");
	private static Webcam webcam;
	private static PrintStream oldOutputStream = System.out;
	private static JLabel picLabel;
	private static UI ui;

	private static Scanner scanner;

	private static final String[] OPTIONS = {"Webcam mode", "Prompt mode"};

	public static void changeState(int newState)
	{
		state = ProgramState.values()[newState];
		result.setText("");
	}

	private static void webcamLoop()
	{
		saveButton.setVisible(true);
		readButton.setVisible(true);
		recognizeButton.setVisible(true);
		instruction.setVisible(true);
		trainButton.setVisible(true);
		picLabel.setVisible(true);
		result.setVisible(true);
		guessField.setVisible(true);

		inputArea.setVisible(false);
		outputArea.setVisible(false);
		previousAnswers.setVisible(false);

		try
		{
			Thread.sleep(30);
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}

		image = webcam.getImage();
		image = network.cropImage(image);
		image = network.grayscaleImage(image);
		image = network.boundary(image, 62, 20, 62 + 20, 20 + 30);

		picLabel.setIcon(new ImageIcon(Utils.resize(image, 400, 320)));
	}

	private static void promptLoop()
	{
		inputArea.setVisible(true);
		outputArea.setVisible(true);
		previousAnswers.setVisible(true);

		picLabel.setVisible(false);
		saveButton.setVisible(false);
		readButton.setVisible(false);
		instruction.setVisible(false);
		recognizeButton.setVisible(false);
		trainButton.setVisible(false);
		guessField.setVisible(false);
		result.setVisible(false);
	}

	private static void mainLoop()
	{
		while (true)
		{
			uiLoop();
			switch (state)
			{
				case WEBCAM: // Webcam mode, assumed already trained
					webcamLoop();
					break;
				case PROMPT: // Normal entering equations etc.
					promptLoop();
					break;
			}
		}
	}

	private static void uiLoop()
	{
	}

	private static void init() throws IOException
	{
		// WEBCAM INIT:
		// To get rid of the annoying errors:
		System.setOut(new PrintStream(new NullOutputStream()));
		System.setErr(new PrintStream(new NullOutputStream()));

		webcam = Webcam.getDefault();
		webcam.open();

		// GRAPHICS INIT:
		ui = new UI(620, 480);

		result = new JLabel();
		result.setFont(new Font("SansSerif", Font.BOLD, 16));

		programStates = new JComboBox<>(OPTIONS);
		programStates.setSelectedIndex(1);
		programStates.addActionListener(new Actions(Action.CHANGE_STATE, programStates));

		guessField = new JTextField();
		guessField.setColumns(6);

		inputArea = new JTextField();
		inputArea.setColumns(20);
		inputArea.addActionListener(new Actions(Action.OUTPUT_AREA, inputArea));

		// Java 8 has lambdas:
		inputArea.getDocument().addDocumentListener((SimpleDocumentListener) e -> ShuntingYard.init(inputArea.getText()));

		instruction.setFont(new Font("SansSerif", Font.PLAIN, 14));

		outputArea = new JLabel("= ");
		outputArea.setFont(new Font("SansSerif", Font.BOLD, 16));

		readButton = new JButton("Read file");
		saveButton = new JButton("Save file");
		recognizeButton = new JButton("Detect number/letter");
		trainButton = new JButton("Train");

		saveButton.addActionListener(new Actions(Action.SAVE_FILE, saveButton));
		readButton.addActionListener(new Actions(Action.READ_FILE, readButton));
		recognizeButton.addActionListener(new Actions(Action.RECOGNIZE, recognizeButton));
		trainButton.addActionListener(new Actions(Action.TRAIN, trainButton));

		picLabel = new JLabel();
		picLabel.addMouseListener(new Mouse());

		previousAnswers = new JTextArea(30, 30);

		// Adding components to the JPanel

		ui.getPanel().add(programStates);
		ui.getPanel().add(readButton);
		ui.getPanel().add(saveButton);
		ui.getPanel().add(recognizeButton);
		ui.getPanel().add(instruction);
		ui.getPanel().add(picLabel);
		ui.getPanel().add(inputArea);
		ui.getPanel().add(outputArea);
		ui.getPanel().add(previousAnswers);
		ui.getPanel().add(guessField);
		ui.getPanel().add(trainButton);
		ui.getPanel().add(result);

		ui.run(); // Makes the user interface viewable

		System.setOut(oldOutputStream);
		System.setErr(oldOutputStream);

	}

	public static void changeOutput(String text)
	{
		if (text.contains("clear")) // We've already handled the case below, so no point.
			return;
		if (text.contains("\n") && !text.contains("invalid")) // If the expression is invalid, there's no point in appending it.
			previousAnswers.append(text + "\n");
		outputArea.setText(text);
	}

	public static void clearOutput()
	{
		// clears output on prompt mode
		outputArea.setText("");
		inputArea.setText("");
	}

	public static void clearConsole()
	{
		previousAnswers.setText("");
	}

	public static void main(String[] args) throws Exception
	{
		// creating the Neural Network
		Shared.network = new NeuralNetwork(144, 144, 1);

		// initialization function
		init();

		// the main program "loop"
		mainLoop();

	}
}
