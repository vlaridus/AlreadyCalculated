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

/**
 * Created by minnow on 10/12/17
 */
public class Number
{
	private String expr;
	private SymbolType symbolType = SymbolType.OTHER;
	private NumberType numberType = NumberType.INTEGER;

	private double num = 0;
	private static double highestPower = 1;

	public double getHighestPower()
	{
		return Number.highestPower;
	}

	public void setHighestPower(double n)
	{
		Number.highestPower = n;
	}

	public Number(String expr, SymbolType symType)
	{
		this.expr = expr;
		this.symbolType = symType;

		if (this.symbolType == SymbolType.NUMBER)
		{
			this.num = Double.parseDouble(expr);
		}
	}

	public Number(String expr, SymbolType symType, NumberType numType)
	{
		this.expr = expr;
		this.symbolType = symType;
		this.numberType = numType;

		this.num = Double.parseDouble(expr);
	}

	public Number(String expr, NumberType numType)
	{
		this.expr = expr;
		this.numberType = numType;
		this.num = Double.parseDouble(expr);
	}

	public Number(String expr)
	{
		this.expr = expr;
	}

	public boolean isNumber()
	{
		return symbolType == SymbolType.NUMBER;
	}

	public boolean isBracket()
	{
		return symbolType == SymbolType.BRACKET;
	}

	public boolean isOperator()
	{
		return symbolType == SymbolType.OPERATOR;
	}

	public boolean isLetter()
	{
		return symbolType == SymbolType.LETTER;
	}

	public boolean isSingleOperaration()
	{
		return symbolType == SymbolType.SINGLE_OPERATOR;
	}

	public boolean isOther()
	{
		return symbolType == SymbolType.OTHER;
	}

	public SymbolType getSymbolType()
	{
		return symbolType;
	}

	public NumberType getNumberType()
	{
		return this.numberType;
	}

	public String getExpr()
	{
		return expr;
	}

	public boolean isLeftAssociative()
	{
		return !getExpr().equals("^");
	}

	public int getLength()
	{
		return expr.length();
	}

	public int getPrecedence() throws Exception
	{
		int top = 5;
		switch (getExpr())
		{
			case "(":
			case ")":
				return -100;
			case "^":
			case "sqrt":
			case "ln":
				return 2 + top;
			case "*":
			case "/":
			case "%":
				return 1 + top;
			case "-":
			case "+":
				return top;
			case "x":
				return 100;
			case "=":
				return -10000;
			default:
				throw new Exception("unknown operator:" + getExpr());
		}
	}

	public double getNum()
	{
		return num;
	}

	public Number changeNum(double newVal)
	{
		num = newVal;
		return this;
	}

	public double evalDoubleOne(double num1) throws Exception
	{
		switch (getExpr())
		{
			case "sqrt":
				return Math.sqrt(num1);
			case "ln":
				return Math.log(num1);
			case "ans":
				return ShuntingYard.prevAnswer;
			default:
				throw new Exception("Unknown operator " + getExpr());
		}

	}

	public double evalDouble(double num2, double num1) throws Exception // notice order, remember stack FIFO
	{
		switch (getExpr())
		{
			case "+":
				return num1 + num2;
			case "-":
				return num1 - num2;
			case "*":
				return num1 * num2;
			case "/":
				return num1 / num2;
			case "^":
				if (getHighestPower() < num2)
					setHighestPower(num2);
				return Math.pow(num1, num2);
			default:
				throw new Exception("bad operator: " + getExpr());
		}
	}

	public double evalReverseDouble(double num2, double num1) throws Exception // notice order, remember stack FIFO
	{
		switch (getExpr())
		{
			case "+":
				return num1 - num2;
			case "-":
				return num1 + num2;
			case "*":
				return num1 / num2;
			case "/":
				return num1 * num2;
			case "^":
				return Math.pow(num1, num2);
			default:
				throw new Exception("bad operator: " + getExpr());
		}
	}

	public Number evalTwo(Number num1, Number num2) throws Exception
	{
		return new Number(Double.toString(evalDouble(num1.getNum(), num2.getNum())), SymbolType.NUMBER);
	}

	public Number evalOne(Number num1) throws Exception
	{
		return new Number(Double.toString((evalDoubleOne(num1.getNum()))), SymbolType.NUMBER);
	}
}
