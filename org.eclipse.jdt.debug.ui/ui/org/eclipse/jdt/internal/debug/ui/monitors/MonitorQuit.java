package org.eclipse.jdt.internal.debug.ui.monitors;

/**********************************************************************
Copyright (c) 2000, 2002 IBM Corp.  All rights reserved.
This file is made available under the terms of the Common Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/cpl-v10.html
**********************************************************************/

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;


/**
 * Resumes all the threads
 */
public class MonitorQuit implements IViewActionDelegate {
	
	protected IViewPart fView;

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {	
		IJavaDebugTarget target= getDebugTarget();
		try {
			IThread[] threads= target.getThreads();
			
			for (int i = 0; i < threads.length; i++) {
				IJavaThread thread = (IJavaThread)threads[i];
				if(!thread.isSystemThread()){
					if (thread.isSuspended()) {
						thread.resume();
						while (thread.isSuspended()) {
							Thread.sleep(100);
						}
					}
				}
			}
		}
		catch (DebugException e) {
			JDIDebugUIPlugin.log(e);
		}
		catch (InterruptedException e){
			JDIDebugUIPlugin.log(e);
		}
	}
	
	/**
	 * Returns the current selection in the debug view or <code>null</code>
	 * if there is no selection.
	 * 
	 * @return IStructuredSelection
	 */
	private IStructuredSelection getDebugViewSelection() {
		if (fView != null) {
			ISelection s =fView.getViewSite().getPage().getSelection(IDebugUIConstants.ID_DEBUG_VIEW);
			
			if (s instanceof IStructuredSelection) {
				return (IStructuredSelection)s;
			}
		}
		return null;
	}
	
	protected IJavaDebugTarget getDebugTarget() {
		IStructuredSelection ss= getDebugViewSelection();
		if (ss.isEmpty() || ss.size() > 1) {
			return null;
		}
		Object element= ss.getFirstElement();
		if (element instanceof IDebugElement) {
			return (IJavaDebugTarget)((IDebugElement)element).getDebugTarget();
		}
		
		return null;
	}
	
	/**
	 * @see org.eclipse.ui.IViewActionDelegate#init(IViewPart)
	 */
	public void init(IViewPart view) {
		fView= view;
	}
	
	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
