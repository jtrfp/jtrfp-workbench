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
package de.stefanteitge.mtmx.workbench.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import de.stefanteitge.mtmx.workbench.ui.internal.view.GameExplorerView;
import de.stefanteitge.mtmx.workbench.ui.internal.view.MediaView;
import de.stefanteitge.mtmx.workbench.ui.internal.view.PodFileView;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "de.stefanteitge.mtmx.workbench.ui.Perspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();

		IFolderLayout topLeftFolder = layout.createFolder(
				"topLeft",
				IPageLayout.LEFT,
				0.25f,
				editorArea);
		topLeftFolder.addView(GameExplorerView.VIEW_ID);

		IFolderLayout bottomLeftFolder = layout.createFolder(
				"bottomLeft",
				IPageLayout.BOTTOM,
				0.5f,
		"topLeft");
		bottomLeftFolder.addView("org.eclipse.ui.views.PropertySheet");
		bottomLeftFolder.addView(MediaView.VIEW_ID);

		IFolderLayout rightFolder = layout.createFolder(
				"right",
				IPageLayout.RIGHT,
				0.75f,
				editorArea);
		rightFolder.addView(PodFileView.VIEW_ID);
	}

}
