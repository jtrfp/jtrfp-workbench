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

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public final class IconUtil {

	private IconUtil() {
	}

	public static Image getSwtImage(File file) {
		if (!file.exists()) {
			return null;
		}

		Icon icon = getAwtIcon(file);

		if (icon == null) {
			return null;
		}
		if (icon instanceof ImageIcon) {
			ImageIcon imageIcon = (ImageIcon) icon;
			java.awt.Image image = imageIcon.getImage();
			if (image instanceof BufferedImage) {
				BufferedImage bufferedImage = (BufferedImage) image;
				ImageData imageData = makeSwtData(bufferedImage);
				return new Image(null, imageData);
			}
		}

		return null;
	}

	private static ImageData makeSwtData(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {

			DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();

			PaletteData palette = new PaletteData(
					colorModel.getRedMask(),
					colorModel.getGreenMask(),
					colorModel.getBlueMask());

			ImageData imageData = new ImageData(
					bufferedImage.getWidth(),
					bufferedImage.getHeight(),
					colorModel.getPixelSize(),
					palette);

			for (int y = 0; y < imageData.height; y++) {
				for (int x = 0; x < imageData.width; x++) {
					int rgb = bufferedImage.getRGB(x, y);
					RGB rgbObject = new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
					int pixel = palette.getPixel(rgbObject);
					imageData.setPixel(x, y, pixel);
				}
			}
			return imageData;
		}
		return null;
	}

	public static Icon getAwtIcon(File file) {
		Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
		return icon;
	}
}
