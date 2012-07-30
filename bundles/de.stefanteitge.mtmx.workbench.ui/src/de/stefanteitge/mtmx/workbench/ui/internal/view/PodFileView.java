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
package de.stefanteitge.mtmx.workbench.ui.internal.view;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.stefanteitge.mtmx.core.file.FileLoadException;
import de.stefanteitge.mtmx.core.file.pod.PodFile;

public class PodFileView extends ViewPart {

	public static final String VIEW_ID = "de.stefanteitge.mtmx.workbench.ui.PodFileView";

	private TreeViewer podFileEntryList;

	private ISelectionListener selectionListener;

	public PodFileView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		podFileEntryList = new TreeViewer(parent);
		podFileEntryList.setContentProvider(new PodDataContentProvider());
		podFileEntryList.setLabelProvider(new PodDataLabelProvider());

		hookListeners();

		getSite().setSelectionProvider(podFileEntryList);
	}

	private void hookListeners() {
		ISelectionService ss = getSite().getWorkbenchWindow().getSelectionService();
		selectionListener = new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				Object fe = ((IStructuredSelection) selection).getFirstElement();
				if (fe instanceof PodFile) {
					fileSelected((PodFile) fe);
				}
			}
		};
		ss.addSelectionListener(selectionListener);
	}


	protected void fileSelected(PodFile podFile) {
		if (podFileEntryList != null && !podFileEntryList.getControl().isDisposed()) {
			try {
				podFileEntryList.setInput(podFile.getData());
			} catch (FileLoadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setContentDescription(podFile.getFile().getName());

		}
	}

	@Override
	public void setFocus() {
		if (podFileEntryList != null && !podFileEntryList.getControl().isDisposed()) {
			podFileEntryList.getControl().setFocus();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (selectionListener != null) {
			ISelectionService ss = getSite().getWorkbenchWindow().getSelectionService();
			ss.removeSelectionListener(selectionListener);
		}
	}
}
