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
import jtrfp.common.kfm.IKfmPodFileEntry;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class KfmPodFileEntryPropertySource implements IPropertySource {

	private static final String VERSION = "version";

	private static final String VERTEX_COUNT = "vertexCount";

	private static final Object POLY_COUNT = "polyCount";

	private static final Object TEXTURE_COUNT = "textureCount";

	private static final Object PART_COUNT = "partCount";

	private static final Object FRAME_COUNT = "frameCount";

	private final IKfmPodFileEntry podFileEntry;

	public KfmPodFileEntryPropertySource(IKfmPodFileEntry podFileEntry) {
		this.podFileEntry = podFileEntry;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new PropertyDescriptor(VERTEX_COUNT, "Vertex count"),
				new PropertyDescriptor(POLY_COUNT, "Poly count"),
				new PropertyDescriptor(TEXTURE_COUNT, "Texture count"),
				new PropertyDescriptor(PART_COUNT, "Part count"),
				new PropertyDescriptor(FRAME_COUNT, "Frame count"),
				new PropertyDescriptor(VERSION, "Version"),
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		try {
			if (VERSION.equals(id)) {
				return podFileEntry.getData().getVersion();
			} else if (VERTEX_COUNT.equals(id)) {
				return podFileEntry.getData().getVertexCount();
			} else if (POLY_COUNT.equals(id)) {
				return podFileEntry.getData().getPolyCount();
			} else if (TEXTURE_COUNT.equals(id)) {
				return podFileEntry.getData().getTextureCount();
			} else if (PART_COUNT.equals(id)) {
				return podFileEntry.getData().getPartCount();
			} else if (FRAME_COUNT.equals(id)) {
				return podFileEntry.getData().getFrameCount();
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
