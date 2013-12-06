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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jtrfp.common.FileLoadException;
import jtrfp.common.FileStoreException;
import jtrfp.common.pod.PodFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ExtractContentsActionDelegate implements IViewActionDelegate {

	private IViewPart view;

	private ISelection selection;

	@Override
	public void init(IViewPart view) {
		this.view = view;

	}

	@Override
	public void run(IAction action) {
		PodFile[] podFiles = getPodFiles();
		if (podFiles.length > 0) {
			File extractDir = chooseDir();
			if (extractDir != null) {
				Job job = new ExtractJob(podFiles, extractDir);
				job.setUser(true);
				job.schedule();
			}
		}

	}

	private File chooseDir() {
		DirectoryDialog directoryDialog = new DirectoryDialog(view.getSite().getShell());
		String path = directoryDialog.open();
		if (path != null) {
			return new File(path);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private PodFile[] getPodFiles() {
		List<PodFile> podFileList = new ArrayList<PodFile>();
		if (selection instanceof IStructuredSelection) {
			Iterator<PodFile> iterator = ((IStructuredSelection) selection).iterator();
			while (iterator.hasNext()) {
				podFileList.add(iterator.next());
			}
		}
		return podFileList.toArray(new PodFile[]{});
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	private class ExtractJob extends Job {

		private final PodFile[] podFiles;

		private final File extractDir;

		public ExtractJob(PodFile[] podFiles, File extractDir) {
			super("Extract POD Files");
			this.podFiles = podFiles;
			this.extractDir = extractDir;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask("Extracting " + podFiles.length + " POD files", podFiles.length);
			for (PodFile podFile : podFiles) {
				try {
					monitor.subTask("Extracting " + podFile.getFile().getName());
					podFile.getData().extractAll(extractDir);
					monitor.worked(1);
				} catch (FileStoreException e) {
					// TODO: report & log
					e.printStackTrace();
				} catch (FileLoadException e) {
					// TODO: report & log
					e.printStackTrace();
				}
			}
			monitor.done();
			return Status.OK_STATUS;
		}

	}
}
