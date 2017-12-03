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
public class Stack
{
	private Number[] stack;
	private int pivot = -1;

	private final int MAX_SIZE;

	public Stack(int size)
	{
		this.MAX_SIZE = size;

		stack = new Number[MAX_SIZE];
	}

	public void push(Number number) throws ArrayIndexOutOfBoundsException
	{
		if (isFull())
			throw new ArrayIndexOutOfBoundsException("stack is full.");

		stack[++pivot] = number;
	}

	public void push(String expr) throws ArrayIndexOutOfBoundsException
	{
		if (isFull())
			throw new ArrayIndexOutOfBoundsException("stack is full.");

		stack[++pivot] = new Number(expr);
	}

	public Number pop() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("stack is empty.");

		return stack[pivot--];
	}

	public String popExpr() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("stack is empty.");

		return stack[pivot--].getExpr();
	}


	public Number peek() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("stack is empty.");

		return stack[pivot];
	}

	public String peekExpr() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("stack is empty.");

		return stack[pivot].getExpr();
	}

	public boolean isEmpty()
	{
		return pivot == -1;
	}

	public int getSize()
	{
		return pivot;
	}

	public boolean isFull()
	{
		return pivot == MAX_SIZE - 1;
	}
}
