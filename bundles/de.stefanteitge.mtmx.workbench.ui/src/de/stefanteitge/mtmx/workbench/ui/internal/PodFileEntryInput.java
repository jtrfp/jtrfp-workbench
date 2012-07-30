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

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

// TODO: uncommented on removal of org.eclipse.ui.ide
//public class PodFileEntryInput implements IStorageEditorInput {
public class PodFileEntryInput {

	private final IStorage storage;

	public PodFileEntryInput(IStorage storage) {
		this.storage = storage;
	}

	//	@Override
	public boolean exists() {
		return true;
	}

	//	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	//	@Override
	public String getName() {
		return storage.getName();
	}

	//	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	//	@Override
	public String getToolTipText() {
		return storage.getName();
	}

	//	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return null;
	}

	//	@Override
	public IStorage getStorage() throws CoreException {
		return storage;
	}

}
