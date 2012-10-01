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
import jtrfp.common.image.IBmpPodFileEntry;
import jtrfp.common.image.ITifPodFileEntry;
import jtrfp.common.pod.IPodFileEntry;
import jtrfp.common.raw.IRawPodFileEntry;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.stefanteitge.mtmx.workbench.ui.IconProvider;
import de.stefanteitge.mtmx.workbench.ui.internal.view.PodDataContentProvider.VNode;

public class PodDataLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof VNode) {
			VNode vNode = (VNode) element;
			if (IActPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.ACTS_PATH);
			} else if (IRawPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.RAWS_PATH);
			} else if (IWavPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.AUDIOS_PATH);
			} else if (IModPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.AUDIOS_PATH);
			} else if (IBmpPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.BMPS_PATH);
			} else if (ITifPodFileEntry.class.isAssignableFrom(vNode.getClazz())) {
				return IconProvider.getIconByPath(IconProvider.BMPS_PATH);
			} else {
				return IconProvider.getIconByPath(IconProvider.FOLDER_PATH);
			}
		}

		return IconProvider.getIcon(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof VNode) {
			VNode vNode = (VNode) element;
			return vNode.getName();
		} else if (element instanceof IPodFileEntry) {
			IPodFileEntry entry = (IPodFileEntry) element;
			return entry.getPath();
		}
		return super.getText(element);
	}
}
