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

import jtrfp.common.act.IActPodFileEntry;
import jtrfp.common.audio.IModPodFileEntry;
import jtrfp.common.audio.IWavPodFileEntry;
import jtrfp.common.bin.IBinPodFileEntry;
import jtrfp.common.clr.IClrPodFileEntry;
import jtrfp.common.image.IBmpPodFileEntry;
import jtrfp.common.image.ITifPodFileEntry;
import jtrfp.common.kfm.IKfmPodFileEntry;
import jtrfp.common.lvl.ILvlPodFileEntry;
import jtrfp.common.pod.IPodData;
import jtrfp.common.pod.IPodFileEntry;
import jtrfp.common.raw.IRawPodFileEntry;
import jtrfp.common.sit.ISitPodFileEntry;
import jtrfp.common.tex.ITexPodFileEntry;
import jtrfp.common.trk.ITrkPodFileEntry;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;



public class PodDataContentProvider implements ITreeContentProvider {

	private VNode[] nodes;

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null && oldInput.equals(newInput)) {
			return;
		}

		IPodData data = (IPodData) newInput;

		if (data != null) {
			nodes = new VNode[]{
					new VNode(data, "Audio (MOD)", IModPodFileEntry.class),
					new VNode(data, "Audio (WAV)", IWavPodFileEntry.class),
					new VNode(data, "Color Maps (CLR)", IClrPodFileEntry.class),
					new VNode(data, "Images (BMP)", IBmpPodFileEntry.class),
					new VNode(data, "Images (TIF)", ITifPodFileEntry.class),
					new VNode(data, "Levels (LVL)", ILvlPodFileEntry.class),
					new VNode(data, "Models (BIN)", IBinPodFileEntry.class),
					new VNode(data, "Models (KFM)", IKfmPodFileEntry.class),
					new VNode(data, "Palettes (ACT)", IActPodFileEntry.class),
					new VNode(data, "Textures (RAW)", IRawPodFileEntry.class),
					new VNode(data, "Texture Lists (TEX)", ITexPodFileEntry.class),
					new VNode(data, "Trucks (TRK)", ITrkPodFileEntry.class),
					new VNode(data, "Worlds (SIT)", ISitPodFileEntry.class),
					new VNode(data.getUntypedEntries(), "Other", IPodFileEntry.class)
			};
		} else {
			nodes = new VNode[0];
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return nodes;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof VNode) {
			return ((VNode) parentElement).getEntries();
		}
		return new Object[]{};
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	static class VNode {

		private final IPodFileEntry[] entries;

		private final String name;

		private final Class<?> clazz;

		public VNode(IPodData data, String name, Class<? extends IPodFileEntry> clazz) {
			entries = data.findEntries(clazz);

			this.name = name;
			this.clazz = clazz;
		}

		public VNode(IPodFileEntry[] entries, String name, Class<?> clazz) {
			this.entries = entries;
			this.name = name;
			this.clazz = clazz;
		}

		public IPodFileEntry[] getEntries() {
			return entries;
		}

		public String getName() {
			return name;
		}

		public Class<?> getClazz() {
			return clazz;
		}
	}
}

