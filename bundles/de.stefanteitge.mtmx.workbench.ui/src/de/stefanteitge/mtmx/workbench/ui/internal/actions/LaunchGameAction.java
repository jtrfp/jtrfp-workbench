/*
 * This file is part of mtmX.
 *
 * mtmX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mtmX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mtmX.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stefanteitge.mtmx.workbench.ui.internal.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import de.stefanteitge.mtmx.core.tri.ITriGameDir;
import de.stefanteitge.mtmx.workbench.ui.internal.Util;

public class LaunchGameAction implements IViewActionDelegate {

	private ISelection selection;

	@Override
	public void init(IViewPart view) {
	}

	@Override
	public void run(IAction action) {
		File exeFile = getExeFile();
		if (exeFile != null) {
			try {
				Runtime.getRuntime().exec(exeFile.getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private File getExeFile() {
		Object fe = Util.getFirstElement(selection);
		if (fe != null && fe instanceof ITriGameDir) {
			ITriGameDir gameDir = (ITriGameDir) fe;
			return gameDir.getExeFile();
		}
		return null;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
