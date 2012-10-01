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

import java.io.File;

import jtrfp.common.act.IActPodFileEntry;
import jtrfp.common.audio.IModPodFileEntry;
import jtrfp.common.audio.IWavPodFileEntry;
import jtrfp.common.image.IBmpPodFileEntry;
import jtrfp.common.pod.IPodFileEntry;
import jtrfp.common.pod.PodFile;
import jtrfp.common.raw.IRawPodFileEntry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public final class IconProvider {

	public static final String PODS_PATH = "icons/folder_brick.png";

	private static final String POD_PATH = "icons/brick.png";

	public static final String INIS_PATH = "icons/folder_page.png";

	public static final String INI_PATH = "icons/page.png";

	public static final String ACTS_PATH = "icons/folder_palette.png";

	private static final String ACT_PATH = "icons/palette.png";

	public static final String RAWS_PATH = "icons/folder_image.png";

	private static final String RAW_PATH = "icons/image.png";

	public static final String AUDIOS_PATH = "icons/folder_bell.png";

	private static final String AUDIO_PATH = "icons/bell.png";

	public static final String BMPS_PATH = "icons/folder_picture.png";

	private static final String BMP_PATH = "icons/picture.png";

	public static final String FOLDER_PATH = "icons/folder.png";

	private IconProvider() {
	}

	public static Image getIcon(Object element) {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

		if (element instanceof IActPodFileEntry) {
			return getIconByPath(ACT_PATH);
		} else if (element instanceof IRawPodFileEntry) {
			return getIconByPath(RAW_PATH);
		} else if (element instanceof IBmpPodFileEntry) {
			return getIconByPath(BMP_PATH);
		} else if (element instanceof IWavPodFileEntry || element instanceof IModPodFileEntry) {
			return getIconByPath(AUDIO_PATH);
		} else if (element instanceof IPodFileEntry) {
			return sharedImages.getImage(ISharedImages.IMG_OBJ_FILE);
		} else if (element instanceof PodFile) {
			return getIconByPath(POD_PATH);
		} else if (element instanceof File) {
			return sharedImages.getImage(ISharedImages.IMG_OBJ_FILE);
		}

		return null;
	}

	public static Image getIconByPath(String path) {
		ImageRegistry registry = UiPlugin.getDefault().getImageRegistry();

		ImageDescriptor descriptor = registry.getDescriptor(path);

		if (descriptor == null) {
			descriptor = UiPlugin.imageDescriptorFromPlugin(UiPlugin.PLUGIN_ID, path);
			if (descriptor != null) {
				Image image = descriptor.createImage();
				registry.put(path, image);
				return image;
			} else {
				return null;
			}
		}

		return descriptor.createImage();
	}
}
