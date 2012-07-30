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
package de.stefanteitge.mtmx.workbench.ui.property;


import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.stefanteitge.mtmx.core.file.FileLoadException;
import de.stefanteitge.mtmx.core.file.pod.PodFile;

public class PodFilePropertySource implements IPropertySource {

	private static final String FILE_COUNT = "fileCount";

	private static final String FILE_SIZE = "fileSize";

	private static final String COMMENT = "comment";

	private static final String FILE_NAME = "fileName";
	
	private final PodFile podFile;

	public PodFilePropertySource(PodFile podFile) {
		this.podFile = podFile;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor(FILE_NAME, "File name"),
				new TextPropertyDescriptor(COMMENT, "Comment"),
				new PropertyDescriptor(FILE_SIZE, "File size"),
				new PropertyDescriptor(FILE_COUNT, "Contained files"),
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		try {
			if (FILE_COUNT.equals(id)) {
				return podFile.getData().getEntryCount();
			} else if (FILE_SIZE.equals(id)) {
				return podFile.getFile().length();
			} else if (COMMENT.equals(id)) {
				return podFile.getData().getComment();
			} else if (FILE_NAME.equals(id)) {
				return podFile.getFile().getName();
			}
		} catch (FileLoadException e) {
			// TODO: log?
			return null;
		}
		
		return null;

	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}
}
