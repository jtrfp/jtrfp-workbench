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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;

import de.stefanteitge.mtmx.core.file.bin.IBinPodFileEntry;
import de.stefanteitge.mtmx.core.file.pod.IPodFileEntry;
import de.stefanteitge.mtmx.core.file.pod.PodFile;
import de.stefanteitge.mtmx.core.file.sit.ISitPodFileEntry;
import de.stefanteitge.mtmx.core.file.trk.ITrkPodFileEntry;
import de.stefanteitge.mtmx.core.tri.ITriGameDir;
import de.stefanteitge.mtmx.engine.tools.ModelViewer;
import de.stefanteitge.mtmx.engine.tools.TruckViewer;
import de.stefanteitge.mtmx.engine.tools.ViewerException;
import de.stefanteitge.mtmx.engine.tools.WorldViewer;
import de.stefanteitge.mtmx.workbench.ui.GameProvider;
import de.stefanteitge.mtmx.workbench.ui.UiPlugin;

public class LaunchViewerActionDelegate implements IViewActionDelegate {

	private ISelection selection;
	private IViewPart view;

	@Override
	public void init(IViewPart view) {
		this.view = view;
	}

	@Override
	public void run(IAction action) {
		final IPodFileEntry entry = getEntry();
		if (entry != null) {
			try {
				if (entry instanceof ISitPodFileEntry) {
					runWorldViewer((ISitPodFileEntry) entry);
				} else if (entry instanceof IBinPodFileEntry) {
					runModelViewer((IBinPodFileEntry) entry);
				} else if (entry instanceof ITrkPodFileEntry) {
					runTruckViewer((ITrkPodFileEntry) entry);
				} else {
					openTextEditor(entry);
				}
			} catch (ViewerException e) {
				Status status = new Status(IStatus.ERROR, UiPlugin.PLUGIN_ID, e.getMessage(), e);
				ErrorDialog.openError(
						view.getSite().getShell(),
						"Viewer Exception",
						"Failed to launch viewer.",
						status);
			} catch (PartInitException e) {
				// TODO: handle exception
			}
		}
	}

	private void openTextEditor(IPodFileEntry entry) throws PartInitException {
		// TODO: uncommented on removal of org.eclipse.ui.ide
		// http://wiki.eclipse.org/FAQ_How_do_I_open_an_editor_on_something_that_is_not_a_file%3F
		//		IWorkbenchWindow window = view.getSite().getWorkbenchWindow();
		//		IStorage storage = new PodFileEntryStorage(entry);
		//		IStorageEditorInput input = new PodFileEntryInput(storage);
		//		IWorkbenchPage page = window.getActivePage();
		//		if (page != null)
		//		{
		//			page.openEditor(input, "org.eclipse.ui.DefaultTextEditor");
		//		}
	}

	private void runTruckViewer(ITrkPodFileEntry entry) throws ViewerException {
		TruckViewer truckViewer = new TruckViewer();
		truckViewer.run(findGameDir(entry.getPodFile()), entry.getPodFile(), entry.getPath());
	}

	private void runWorldViewer(final ISitPodFileEntry entry) throws ViewerException {
		WorldViewer terrainViewer = new WorldViewer();
		terrainViewer.run(findGameDir(entry.getPodFile()), entry.getPodFile(), entry.getPath());
	}

	private void runModelViewer(final IBinPodFileEntry entry) throws ViewerException {
		ModelViewer modelViewer = new ModelViewer();
		modelViewer.run(findGameDir(entry.getPodFile()), entry.getPodFile(), entry.getPath());
	}

	protected ITriGameDir findGameDir(PodFile podFile) {
		ITriGameDir gameDirs[] = GameProvider.getInstance().getGameDirs();
		for (ITriGameDir gameDir : gameDirs) {
			if (gameDir.hasPodFile(podFile)) {
				return gameDir;
			}
		}
		return null;
	}

	private IPodFileEntry getEntry() {
		if (selection instanceof IStructuredSelection) {
			Object fe = ((IStructuredSelection) selection).getFirstElement();
			return (IPodFileEntry) fe;
		}
		return null;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}
