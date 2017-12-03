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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.*;

/**
 * Created by minnow on 10/12/17
 */
public class Utils
{
	private static String sp;

	private static boolean hasLetters(String string)
	{
		return !string.matches("[^A-Za-z0-9] ");
	}

	private static int isVariable(String string)
	{
		int needed = 0;
		for (int i = 0; i < string.length() - 1; i++)
		{
			char c = string.charAt(i);
			if (Character.isLetter(c))
				needed++;
			for (int j = i; j < string.length(); j++)
			{
				char d = string.charAt(j);
				if (Character.isDigit(d))
					needed++;
			}
			if (needed >= 2)
				return i;
		}
		return -1;
	}

	public static SymbolType guessSymbolType(String string)
	{
		SymbolType type = SymbolType.OTHER;

		sp = string;

		if (string.isEmpty())
			return type;

		char character = string.charAt(0);

		string = string.toLowerCase().trim();
		String cmp;

		if (string.length() >= 4)
			cmp = string.substring(0, 4);
		else
			cmp = string;
//			System.out.println(cmp);

		if (cmp.length() > 0)
		{
			String[] test = cmp.split("([0-9]|[)-=(*/% ])");

			if (test.length > 0)
				cmp = test[0];
			switch (cmp)
			{
				case "exp":
				case "ln":
				case "sqrt":
					string = string.substring(0, cmp.length());
					sp = string;
					return SymbolType.SINGLE_OPERATOR;
			}
		}


		string = string.substring(0, 1);

		sp = string;

		if (string.contains(")") || string.contains("("))
			return SymbolType.BRACKET;
		else if (string.equals("sin") || string.equals("cos") || string.equals("tan"))
			return SymbolType.SINGLE_OPERATOR;
		else if (Character.isDigit(character))
			type = SymbolType.NUMBER;
		else if (Character.isLetter(character))
			type = SymbolType.LETTER;
		else
			type = SymbolType.OPERATOR;

		return type;
	}

	public static String[] splitExpression(String string)
	{
		String[] res = string.split("==");

		return res;
	}

	public static String[] splitExpressionS(String string)
	{
		String[] res = string.split("=");

		return res;
	}


	public static BufferedImage resize(BufferedImage img, int newW, int newH)
	{
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	public static String cleanUpInput(String original)
	{
		StringBuilder res = new StringBuilder();
		char m = '3';

//		System.out.println(original);
		if (original.trim().equals("0"))
			return "0";
		for (int i = 0; i < original.length(); i++)
		{
			char c = original.charAt(i);
			String cmp;
			if (i < original.length() - 4)
				cmp = original.substring(i, i + 4);
			else
				cmp = original.substring(i);

			if (cmp.contains("sqrt"))
				continue;

			if (c == ' ')
				continue;

			if (c == '-' && i == 0)
			{
				res.append("0-");
				continue;
			}
			if (i <= original.length() - 2 && original.charAt(i + 1) == '(' && Character.isDigit(c))
			{
				res.append(c);
				res.append("*");
				continue;
			}
			if (i <= original.length() - 6 && original.substring(i + 1, i + 1 + 4).equals("sqrt") && (Character.isDigit(c)))
			{
				res.append(c);
				res.append("*");
				continue;
			}
			if (i != 0 && original.charAt(i - 1) == '(' && c == '-')
			{
				res.append("0-");
				continue;
			}

//			if (i != 0 && Character.isLetter(c) && !Character.isDigit(original.charAt(i - 1)) && Character.isLetter(original.charAt(i - 1)))
//				res.append("1");

			if (i == 0 && Character.isLetter(c))
				res.append("1");
			res.append(c);
		}

		StringBuilder newRes = new StringBuilder();

		char prev = ' ';
		int v = 0;
		boolean reset = true;
		for (int n = 0; n < original.length(); n++)
		{
			String cmp;

			if (n == 0 && original.length() > 2 && Character.isDigit(original.charAt(n + 1)) && original.charAt(n) == '-')
			{
				newRes.append("0-");
				newRes.append(original.charAt(n + 1));
				n += 2;
				v += 2;
			}

			if (n < original.length() - 4)
				cmp = original.substring(n, n + 4);
			else
				cmp = original.substring(n);

			String[] split = cmp.split("([0-9]|[)-=(*/%])");

			if (split.length > 0)
				cmp = split[0];
			else
				cmp = "";

			switch (cmp)
			{
				case "exp":
				case "ln":
				case "sqrt":
					newRes.append(cmp);
					n += cmp.length() - 1;
					v += cmp.length();
					reset = true;
					continue;
				default:
					if (cmp.isEmpty())
						break;
					for (char o = 'a'; o <= 'z'; o++)
						if (res.toString().contains("=") && res.toString().substring(0, res.indexOf("=")).length() == 1)
							while (original.substring(n, n + 1).equals(Character.toString(o)))
								original = original.replace(Character.toString(o), ShuntingYard.getVariable(Character.toString(o)));
			}
			char i = original.charAt(n);

			if (n < original.length() - 1 && Character.isDigit(i) && (Character.isLetter(original.charAt(n + 1)) || original.charAt(n + 1) == '('))
			{
				newRes.append(i);
				newRes.append("*");
			}
			else
			{
				i = original.charAt(v);
				if (i != ' ' && i != '\t')
					newRes.append(i);
			}
			v++;
		}
		return newRes.toString();
	}

	public static void clearConsole()
	{
		for (int i = 0; i < 50; i++)
			System.out.print("\r\n");
	}

	public static Number nextNumber(String string)
	{
		Number number = null;
		StringBuilder res = new StringBuilder();

		for (int i = 0; i < string.length(); i++)
		{
			char c = string.charAt(i);
			if (c == '.') ;
			else if (!Character.isDigit(c))
				break;
			res.append(c);
		}

		if (res.length() == 0)
		{
			SymbolType tmp = guessSymbolType(string);
			number = new Number(sp, tmp);
		}
		else
		{
			number = new Number(res.toString(), SymbolType.NUMBER);
		}

		return number;
	}
}
