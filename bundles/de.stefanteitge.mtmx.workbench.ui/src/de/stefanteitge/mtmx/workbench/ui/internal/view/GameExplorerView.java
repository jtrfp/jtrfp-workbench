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

import java.io.File;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import de.stefanteitge.mtmx.core.file.pod.PodFile;
import de.stefanteitge.mtmx.core.tri.ITriGameDir;
import de.stefanteitge.mtmx.workbench.ui.GameProvider;
import de.stefanteitge.mtmx.workbench.ui.IGameProviderListener;
import de.stefanteitge.mtmx.workbench.ui.IconProvider;
import de.stefanteitge.mtmx.workbench.ui.internal.IconUtil;

public class GameExplorerView extends ViewPart {

	public static final String VIEW_ID = "de.stefanteitge.mtmx.workbench.ui.GameExplorerView";

	private IGameProviderListener listener;

	public GameExplorerView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		listener = new IGameProviderListener() {
			@Override
			public void listChanged() {
				createPartControl(parent);
			}
		};
		GameProvider.getInstance().setListener(listener);

		ITriGameDir gameDir = GameProvider.getInstance().getActiveGame();

		if (gameDir == null) {
			disposeChildren(parent);
			final Composite x = new Composite(parent, SWT.NONE);
			x.setLayout(new GridLayout(1, true));
			Label msgLabel = new Label(x, SWT.NONE);
			msgLabel.setText("No game configured under preferences.");
			parent.layout();
		} else {
			createTree(parent);
		}
	}

	private void createTree(Composite parent) {
		parent.setLayout(new FillLayout());

		disposeChildren(parent);

		TreeViewer treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				openRequested(selection);
			}
		});

		treeViewer.setInput(GameProvider.getInstance().getGameDirs());
		treeViewer.expandToLevel(2);
		getSite().setSelectionProvider(treeViewer);
		parent.layout();

	}

	private void disposeChildren(Composite parent) {
		Control[] controls = parent.getChildren();
		for (Control control : controls) {
			control.dispose();
		}
	}

	private void openRequested(ISelection selection) {
		// TODO: uncommented on removal of org.eclipse.ui.ide
		//		Object fe = Util.getFirstElement(selection);
		//		if (fe != null && fe instanceof File) {
		//			File file = (File) fe;
		//			IPath path = new Path(file.getAbsolutePath());
		//			IFileStore fileStore = EFS.getLocalFileSystem().getStore(path);
		//			if (fileStore.fetchInfo().exists()) {
		//				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		//				IWorkbenchPage page = window.getActivePage();
		//				try {
		//					IDE.openEditorOnFileStore(page, fileStore);
		//				} catch (PartInitException e) {
		//					// TODO: log
		//					e.printStackTrace();
		//				}
		//			}
		//		}
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		super.dispose();
		GameProvider.getInstance().setListener(null);
	}

	private static class TreeLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof PodsNode) {
				return IconProvider.getIconByPath(IconProvider.PODS_PATH);
			} else if (element instanceof IniNode) {
				return IconProvider.getIconByPath(IconProvider.INIS_PATH);
			} else if (element instanceof File) {
				return IconProvider.getIconByPath(IconProvider.INI_PATH);
			} else if (element instanceof ITriGameDir) {
				ITriGameDir mtmGameDir = (ITriGameDir) element;
				Image img = IconUtil.getSwtImage(mtmGameDir.getExeFile());
				return img;
			}

			return IconProvider.getIcon(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof ITriGameDir) {
				return ((ITriGameDir) element).getGameName();
			} else if (element instanceof File) {
				return ((File) element).getName();
			}
			return super.getText(element);
		}

	}

	private static class TreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof PodsNode) {
				return ((PodsNode) parentElement).gameDir.getPodFiles();
			} else if (parentElement instanceof IniNode) {
				ITriGameDir gameDir = ((IniNode) parentElement).gameDir;
				return gameDir.getIniFiles();
			} else if (parentElement instanceof ITriGameDir) {
				ITriGameDir gameDir = (ITriGameDir) parentElement;
				return new Object[]{new PodsNode(gameDir), new IniNode(gameDir)};
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof PodFile) {
				return null;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			Object[] children = getChildren(element);
			return children != null ? children.length > 0 : false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ITriGameDir[]) {
				return (ITriGameDir[]) inputElement;
			}
			return new Object[]{};
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
		}
	}

	private static class PodsNode {

		private final ITriGameDir gameDir;

		public PodsNode(ITriGameDir gameDir) {
			this.gameDir = gameDir;
		}

		@Override
		public String toString() {
			return "POD Files";
		}
	}

	private static class IniNode {

		private final ITriGameDir gameDir;

		public IniNode(ITriGameDir gameDir) {
			this.gameDir = gameDir;
		}

		@Override
		public String toString() {
			return "INI Files";
		}
	}
}
