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
package de.stefanteitge.mtmx.workbench.ui.internal;

import java.io.IOException;
import java.io.InputStream;

import jtrfp.common.pod.IPodFileEntry;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.stefanteitge.mtmx.workbench.ui.UiPlugin;


public class PodFileEntryStorage implements IStorage {

	private final IPodFileEntry entry;

	public PodFileEntryStorage(IPodFileEntry entry) {
		this.entry = entry;
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return entry.getInputStreamFromPod();
		} catch (IOException e) {
			IStatus status = new Status(
					IStatus.ERROR,
					UiPlugin.PLUGIN_ID,
					"Opening input stream for POD file entry failed.",
					e);
			throw new CoreException(status);
		}
	}

	@Override
	public IPath getFullPath() {
		return null;
	}

	@Override
	public String getName() {
		return entry.getPath();
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
