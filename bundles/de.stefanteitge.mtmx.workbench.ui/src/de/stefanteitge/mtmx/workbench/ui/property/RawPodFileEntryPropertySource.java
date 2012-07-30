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

import de.stefanteitge.mtmx.core.file.FileLoadException;
import de.stefanteitge.mtmx.core.file.raw.IRawPodFileEntry;

public class RawPodFileEntryPropertySource implements IPropertySource {

	private static final String HEIGHT = "vertexCount";

	private static final Object WIDTH = "polyCount";

	private final IRawPodFileEntry podFileEntry;

	public RawPodFileEntryPropertySource(IRawPodFileEntry podFileEntry) {
		this.podFileEntry = podFileEntry;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new PropertyDescriptor(HEIGHT, "Height"),
				new PropertyDescriptor(WIDTH, "Width"),
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		try {
			if (HEIGHT.equals(id)) {
				return podFileEntry.getRawData().getHeight();
			} else if (WIDTH.equals(id)) {
				return podFileEntry.getRawData().getWidth();
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
