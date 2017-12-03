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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by minnow on 10/20/17
 */
public class Mouse implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Shared.network.setPoint(null, e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}
}
