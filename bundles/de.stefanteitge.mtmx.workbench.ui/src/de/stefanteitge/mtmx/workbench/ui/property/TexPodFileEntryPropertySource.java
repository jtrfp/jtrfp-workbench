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

import jtrfp.common.FileLoadException;
import jtrfp.common.tex.ITexPodFileEntry;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TexPodFileEntryPropertySource implements IPropertySource {

	private static final String TEXTURE_COUNT = "textureCount";

	private final ITexPodFileEntry podFileEntry;

	public TexPodFileEntryPropertySource(ITexPodFileEntry podFileEntry) {
		this.podFileEntry = podFileEntry;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor(TEXTURE_COUNT, "Texture count")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		try {
			if (TEXTURE_COUNT.equals(id)) {
				return podFileEntry.getData().getTextureCount();
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
