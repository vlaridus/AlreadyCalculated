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

import java.lang.*;
import java.util.HashMap;

/**
 * Created by minnow on 10/12/17
 */
public class ShuntingYard
{
	private static final int MAX_EXPR = 1000;

	public static double prevAnswer = 0;

	public static boolean solving = false;

	private static String current = "x";

	private static Queue output = new Queue(MAX_EXPR);
	private static Stack operators = new Stack(MAX_EXPR);
	private static HashMap<String, Double> variables = new HashMap<>();

	public static String rpnString;

	public static void reset()
	{
		output = new Queue(MAX_EXPR);
		operators = new Stack(MAX_EXPR);
		rpnString = "";
	}

	public static String getVariable(String name)
	{
		return variables.get(name).toString();
	}

	private static boolean variableNull(String name)
	{
		return variables.get(name) == null;
	}

	private static void setVariable(String name, double value)
	{
		variables.put(name, value);
	}

	public static void init(String string)
	{
		reset();
		try
		{
			String[] strings = string.split("\n");
			start(strings[strings.length - 1]); // If the string has some newlines, use the latest line
			Main.changeOutput("= " + Double.toString(prevAnswer));
		}
		catch (Exception e)
		{
			Main.changeOutput("invalid");
//			System.out.println(e.getMessage());
//			System.out.println(Arrays.toString(e.getStackTrace()));
		}
	}

	private static void start(String string) throws Exception
	{
		if (string.contains("clear"))
		{
			Utils.clearConsole();
			Main.clearConsole();
			return;
		}
		if (string.isEmpty())
		{
			reset();
			string = rpnString;
			System.out.println("rip");
		}
		for (char c = 'a'; c < 'z'; c++) // note this please
			if (string.contains(Character.toString(c)))
			{
				if (string.contains("==") || !string.contains("="))
					break;

				rpnString = string;
				while (string.contains("ans"))
					string = string.replace("ans", Double.toString(prevAnswer));

				current = Character.toString(c);

				String[] strings = Utils.splitExpressionS(Utils.cleanUpInput(string));

				if (Character.isLetter(strings[0].charAt(0)) && strings[0].length() == 1)
				{
					for (char h = 'a'; h < 'z'; h++)
						if (strings[1].contains(Character.toString(h)))
							strings[1] = strings[1].replace(Character.toString(h), getVariable(Character.toString(h)));

					convert(Utils.cleanUpInput((strings[1])));
					double res = Double.parseDouble(evaluate().getExpr());
//					System.out.println(res);

					setVariable(current, res);
					System.out.printf("set %s to %.2f\n", current, res);
//					prevAnswer = res;
					rpnString = string;
					return;
				}

//				if (strings[0].length() > 2)
//					break;

				char cx = 'z';
				while (cx >= 'a' && !variableNull(Character.toString(cx)))
					cx--;

				current = Character.toString(cx);
				if (!string.contains(current))
				{
					cx = c;
					current = Character.toString(c);
				}

				for (char h = 'a'; h <= 'z'; h++)
					if (Character.toString(h).equals(current) || variableNull(Character.toString(h))) ;
					else if (strings[1].contains(Character.toString(h)))
						strings[1] = strings[1].replace(Character.toString(h), getVariable(Character.toString(h)));

				for (char h = 'a'; h <= 'z'; h++)
					if (Character.toString(h).equals(current) || variableNull(Character.toString(h))) ;
					else if (strings[0].contains(Character.toString(h)))
						strings[0] = strings[0].replace(Character.toString(h), getVariable(Character.toString(h)));

				convert(Utils.cleanUpInput(strings[1]));
				double res = evaluate().getNum();

				if (res < 0)
					strings[0] += "+(0+" + (res * -1) + ")";
				else if (res > 0)
					strings[0] += "-(" + res + ")";
				else
					strings[0] += "+0";

				Number number = new Number("");
				rpnString = string;
//
				double ans = 5;

				double[] results = new double[(int) number.getHighestPower() + 90];

				double highest = number.getHighestPower();

				for (int i = 0; i < 1; i++)
				{
					String tmp = newtonsMethod(strings[0], ans, 0);
					results[i] = Double.parseDouble(tmp);
					ans = results[i];
				}

				System.out.printf("= %.2f\n", ans);
				System.out.printf("= %.5f\n", ans);
//				double a = Double.parseDouble(strings[0].substring(0, 1));

//				if (highest != 1)
//					System.out.printf("%.4f\n", (res / a) / results[0]);
//				Main.changeOutput(Double.toString(ans));
				prevAnswer = ans;

				reset();
				rpnString = string;

				return;
			}

		if (string.isEmpty())
		{
			reset();
			string = rpnString;
			while (string.contains("ans"))
				string = string.replace("ans", Double.toString(prevAnswer));
			System.out.println("welp");
		}
		else
		{
			reset();
			rpnString = string;
			string = Utils.cleanUpInput(string);
		}

		string = Utils.cleanUpInput(string);

		while (string.contains("ans"))
			string = string.replace("ans", Double.toString(prevAnswer));

//		System.out.println(string);

		String[] strings = Utils.splitExpression(string);
//		if (strings.length > 1)
//			strings[1] = strings[1].substring(1);
//		if (strings.length == 1)
//			strings[1] = "";

		Number[] checks = new Number[20];
		boolean equal = true;

		int i = 0;
		for (char h = 'a'; h <= 'z'; h++)
			if (variableNull(Character.toString(h)) || strings.length == 1) ;
			else if (strings[1].contains(Character.toString(h)))
				strings[1] = strings[1].replace(Character.toString(h), getVariable(Character.toString(h)));

		for (char h = 'a'; h <= 'z'; h++)
			if (variableNull(Character.toString(h))) ;
			else if (strings[0].contains(Character.toString(h)))
				strings[0] = strings[0].replace(Character.toString(h), getVariable(Character.toString(h)));


		for (String s : strings)
		{
			convert(s);
			checks[i] = evaluate();
			i++;
		}

		double res = checks[0].getNum();
		for (int j = 0; j < i; j++)
		{
			if (res != checks[j].getNum())
			{
				System.out.printf("%.4f does not equal %.4f\n", res, checks[j].getNum());
				equal = false;
			}
			res = checks[j].getNum();
		}

		if (!equal)
			System.out.printf("= %s\n", false);
		else if (strings.length == 1)
			System.out.printf("= %.2f\n", checks[0].getNum());
		else
			System.out.printf("= %s\n", true);

		prevAnswer = checks[0].getNum();
		rpnString = string;
		setVariable("ans", prevAnswer);
	}

	private static int donePast = 0;

	public static String differentiate(String input, DerivativeType type)
	{
		int currentIndex = input.indexOf(current);
		StringBuilder newInput = new StringBuilder();

		if (input.charAt(0) == 'x')
			newInput.append("1*");

		for (int i = 0; i < input.length(); i++)
			newInput.append(input.charAt(i));

		switch (type)
		{
			case RECIPRICAL_RULE:

				int i, j;
				for (j = currentIndex; input.charAt(j) != ')' && j != input.length() - 1; j++) ;
				newInput.insert(j + 1, "^2)");

				for (i = currentIndex; input.charAt(i) != '/' && i >= 0; i--) ;
				newInput.replace(i, i + 1, "*(0-1)/(");
				donePast = newInput.indexOf("^2)") + 3;
				break;
			case NO_RULE:
				int n = 0;
				int length = newInput.length();
				for (i = 0; ; i++)
				{
					if (i >= newInput.length() - 1)
						break;
					char v = newInput.charAt(i);

					if (i < newInput.length() - 1 && (v == '+' || v == '-') && Character.isDigit(newInput.charAt(i + 1)))
					{
						newInput.insert(i + 1, "0+0*");
						i += 5;
//							while (Character.isDigit(newInput.charAt(i)) || newInput.charAt(i) == '.' || newInput.charAt(i) == ')' || newInput.charAt(i) == ')')
//								i++;
					}
					else if (i == 0 && (newInput.charAt(i + 1) == '+' || newInput.charAt(i + 1) == '-') && newInput.charAt(i + 1) != '*' && newInput.charAt(i + 1) != '/' && newInput.charAt(i + 1) != '^')
					{
						newInput.insert(1, "*0");
						i += 3;
					}
					if (i >= newInput.length() - 1)
						break;

					if (i < length - 2 && Character.isDigit(newInput.charAt(i)) && newInput.charAt(i + 1) == '^')
					{
						String after = newInput.substring(i + 1, i + 3);
						String before = newInput.substring(i, i + 1);
						if (after.charAt(0) == '^' && after.substring(1).equals(current))
							newInput.insert(i, "ln(" + before + ")*");
						i += 6;
//						n = i + 4;
					}

					if (newInput.charAt(i) == current.charAt(0))
					{
						if (i > 0 && newInput.charAt(i - 1) == '^')
							;
						else if (i > 0 && i != 2 && !Character.isLetterOrDigit(newInput.charAt(i - 1)))
							newInput.replace(i, i + 1, "1");
						else if (i < length - 1 && newInput.charAt(i + 1) == '^')
						{
							double res = Double.parseDouble(Integer.toString(newInput.charAt(i + 2) - '0'));
//							System.out.println(res);
							newInput.replace(i + 2, i + 3, Integer.toString((int) res - 1));

							double coef = Double.parseDouble(Double.toString(Double.parseDouble(newInput.substring(n + (i > 2 ? -3 : 0), i - (i <= 2 ? 1 : 1)))));
							newInput.replace((coef < 0) ? (i - 3) : i - 2, i, Integer.toString((int) res * (int) coef) + "*");
							i += 2;
							n = i + 4;
						}
						else
						{
							newInput.insert(i + 1, "^0");
							i += 2;
						}
					}
					else if (Character.isDigit(newInput.charAt(i)) && i != 0)
					{
						if (i < length - 1 && newInput.charAt(i + 1) != '^')
						{
							continue;
						}
					}
					if (i > 0 && i < newInput.length() - 1)
					{
						if (newInput.charAt(i) == '(' && (Character.isDigit(newInput.charAt(i - 1)) || newInput.charAt(i - 1) == '-' || newInput.charAt(i - 1) == '+'))
						{
							newInput.insert(i + 1, "0+0*");
							i += 5;
//							while (Character.isDigit(newInput.charAt(i)) || newInput.charAt(i) == '.' || newInput.charAt(i) == ')' || newInput.charAt(i) == ')')
//								i++;
						}
						else if ((v == '+' || v == '-') && Character.isDigit(newInput.charAt(i + 1)))
						{
							newInput.insert(i + 1, "0+0*");
							i += 5;
//							while (Character.isDigit(newInput.charAt(i)) || newInput.charAt(i) == '.' || newInput.charAt(i) == ')' || newInput.charAt(i) == ')')
//								i++;
						}
						if (Character.isLetter(newInput.charAt(i)))
							n = i;
					}

				}
		}
//		System.out.println(newInput);
		return "(" + newInput.toString() + ")";
	}

	private static int pastError = 0;

	public static String derivative(String expr, int order)
	{
		StringBuilder res = new StringBuilder();
		int n = 1;
		int count = 0;
		boolean bracket = false;

		if (!expr.contains(current))
			return "";

		// bad sorry
		for (int i = 0; i < expr.length(); i++)
		{
			char c = expr.charAt(i);
			count++;
//			res.append(c);
			if (c == '(')
			{
				bracket = true;
				continue;
			}
			if (c == ')')
			{
				bracket = false;
				continue;
			}
			if (Character.isDigit(c) && bracket)
			{
				continue;
			}

			if (Character.toString(c).equals(current))
			{
				if (expr.charAt(i + 1) == '^')
				{
					res.append(expr.charAt(i + 2));
					res.append(expr.charAt(i));
					double bes = (expr.charAt(i + 2) - '0');

//					res.append(current);
					res.append("^" + Integer.toString((int) bes - 1));
					n = i + 2;
				}
				else
				{
					res.append(expr.substring(n + 1, n + 2));
//					String l = expr.substring(0, expr.indexOf(current, i));
//					System.out.println(l);
//					if (Character.isDigit(l.charAt(0)))
//					{
//						res.append(l);
//						n = count + 1;
//					}
				}
			}
//			if (Character.toString(c).equals(current))
//				res.append("1");
			if (i < expr.length() - 1 && expr.charAt(i + 1) == '/')
			{
				int ind = expr.substring(i).indexOf(current);
				if (ind == -1) ;
				else
					pastError = count;
				res.append("1/");
				res.append(expr.substring(i + 2, i + 3));
			}

//			if (c == '^' && i < expr.length() - 1 && Character.isDigit(expr.charAt(i + 1)) && Character.toString(expr.charAt(i - 1)).equals(current))
//			{
//				res.append("*" + expr.charAt(i + 1));
//				double bes = (expr.charAt(i + 1) - '0');
//
//				n = i + 2;
//				res.append(current);
//				res.append("^" + Integer.toString((int) bes - 1));
//
//			}
		}

		return pastError > 0 ? expr : res.toString();
	}

	private static boolean isDerivative = false;

	public static String newtonsMethod(String expression, double approx, int round) throws Exception
	{
		if (round >= 5)
			return Double.toString(approx);

		String string = expression;
		String oldExpr = current;
		while (oldExpr.contains(current))
		{
			if (approx < 0)
				oldExpr = string.replace(current, "(0" + approx + ")");
			else
				oldExpr = string.replace(current, "(0+" + approx + ")");
		}
		String tmp = expression;

		tmp = differentiate(expression, DerivativeType.NO_RULE);
//		System.out.println(tmp);

		if (pastError != 0)
		{
			tmp = differentiate(expression, DerivativeType.RECIPRICAL_RULE);
			String lTmp = tmp.substring(donePast);
			tmp = tmp.substring(0, donePast);
			tmp += differentiate(lTmp, DerivativeType.NO_RULE);
		}

		pastError = 0;


		if (tmp.isEmpty())
			return Double.toString(approx);

		convert(oldExpr);
		double res = evaluate().getNum();
		if (res == 0)
			return Double.toString(approx);

		// i should String.format this lol
		String forEval = (approx < 0 ? "(0" + approx : "(" + approx) + ")-(" + ((res < 0) ? "0" : "0+") + res + ")/(" + tmp + ")";

		forEval = forEval.replace(current, Double.toString(approx));
//		System.out.println(forEval);

		convert(forEval);

		Number n = evaluate();

		double t = n.getNum();
//		System.out.println(forEval);

//		if (Math.abs(approx - t) <= 0.1)
//			return Double.toString(approx);

		return newtonsMethod(expression, t, round + 1);
	}

	public static String convert(String string) throws Exception
	{
		int index = 0;

		int len = string.length();
		while (index <= len)
		{
			Number number = Utils.nextNumber(string.substring(index));

			if (number.getLength() == 0)
				break;

			if (number.isNumber())
			{
				output.enqueue(number);
			}
			else if (number.isOperator() || number.isSingleOperaration())
			{
				while (!operators.isEmpty() && operators.peek().getPrecedence() >= number.getPrecedence() && number.isLeftAssociative())
					output.enqueue(operators.pop());

				operators.push(number); // push the original operator back onto the stack
			}
			else if (number.isBracket())
			{
				if (number.getExpr().equals("("))
				{
					operators.push(number);
				}
				else // must be ")"
				{
					if (operators.isEmpty()) ;
					else
						while (!operators.peek().getExpr().equals("("))
							output.enqueue(operators.pop());

					if (!operators.isEmpty())
						operators.pop();
				}
			}

			index += number.getLength();
		}
		while (!operators.isEmpty())
			output.enqueue(operators.pop());

		return output.getString();
	}

	public static Number evaluate() throws Exception
	{
		if (!operators.isEmpty())
			throw new Exception("not in rpn.");
		Stack stack = new Stack(MAX_EXPR);

		for (Number number : output.traverse())
		{
			if (number.isOperator())
				stack.push(number.evalTwo(stack.pop(), stack.pop()));
			else if (number.isLetter())
				continue;
			else if (number.getSymbolType() == SymbolType.SINGLE_OPERATOR)
				stack.push(number.evalOne(stack.pop()));
			else
				stack.push(number);
		}

		Number number = stack.pop();

		output.enqueue(number);

		prevAnswer = number.getNum();

		return number;
	}
}
