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
public class Queue
{
	private int MAX_SIZE;

	private int front = 0;
	private int rear = -1;

	private int items = 0;

	private Number[] queue;

	public Queue(int size)
	{
		this.MAX_SIZE = size;

		queue = new Number[MAX_SIZE];
	}

	public Number peek() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("queue empty.");

		return queue[front];
	}

	public void enqueue(Number number) throws ArrayIndexOutOfBoundsException
	{
		if (isFull())
			throw new ArrayIndexOutOfBoundsException("queue full.");

		rear = rear == MAX_SIZE - 1 ? -1 : rear + 1; // if full reset to zero

		queue[rear] = number;
		items++;
	}

	public void enqueue(String expr) throws ArrayIndexOutOfBoundsException
	{
		if (isFull())
			throw new ArrayIndexOutOfBoundsException("queue full.");

		rear = rear == MAX_SIZE - 1 ? -1 : rear + 1; // if full reset to zero

		queue[rear] = new Number(expr, Utils.guessSymbolType(expr));
		items++;
	}

	public Number dequeue() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("queue empty.");

		Number res = peek();
		front = front == MAX_SIZE - 1 ? 0 : front + 1;

		items--;

		return res;
	}


	public Number dequeueFake() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("queue empty.");

		Number res = queue[front];
//		front = front == MAX_SIZE - 1 ? 0 : front + 1;
//
//		items--;

		return res;
	}

	public String dequeueExpr() throws ArrayIndexOutOfBoundsException
	{
		if (isEmpty())
			throw new ArrayIndexOutOfBoundsException("queue empty.");

		Number res = peek();
		front = front == MAX_SIZE - 1 ? 0 : front + 1;

		items--;

		return res.getExpr();
	}

	public Number[] traverse() throws ArrayIndexOutOfBoundsException
	{
		Number[] strings = new Number[items];

		for (int i = 0; i < items; i++)
			strings[i] = queue[i];

		return strings;
	}

	public String getString() throws ArrayIndexOutOfBoundsException
	{
		String string = "";
		for (int i = 0; i < items; i++)
			string += queue[i].getExpr() + " ";

		return string;
	}

	public boolean isFull()
	{
		return items == MAX_SIZE;
	}

	public int getSize()
	{
		return items;
	}

	public boolean isEmpty()
	{
		return items == -1;
	}

}
