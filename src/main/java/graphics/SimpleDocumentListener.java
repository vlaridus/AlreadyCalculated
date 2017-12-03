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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by minnow on 10/20/17
 */
public interface SimpleDocumentListener extends DocumentListener
{
	void update(DocumentEvent e);

	@Override
	default void insertUpdate(DocumentEvent e)
	{
		update(e);
	}

	@Override
	default void removeUpdate(DocumentEvent e)
	{
//		update(e);
	}

	@Override
	default void changedUpdate(DocumentEvent e)
	{
//		update(e);
	}
}
