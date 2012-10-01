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

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jtrfp.common.FileLoadException;
import jtrfp.common.FileStoreException;
import jtrfp.common.act.ActFile;
import jtrfp.common.act.DefaultActColorTable;
import jtrfp.common.act.IActData;
import jtrfp.common.act.IActPodFileEntry;
import jtrfp.common.audio.IModPodFileEntry;
import jtrfp.common.image.IBmpPodFileEntry;
import jtrfp.common.image.ITifPodFileEntry;
import jtrfp.common.pod.IPodData;
import jtrfp.common.pod.IPodFileEntry;
import jtrfp.common.raw.IRawPodFileEntry;
import jtrfp.common.raw.RawImage;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.stefanteitge.mtmx.workbench.ui.internal.ModFilePlayer;

public class MediaView extends ViewPart {

	public static final String VIEW_ID = "de.stefanteitge.mtmx.workbench.ui.MediaView";

	private JPanel panel;

	private ISelectionListener selectionListener;

	private Composite embeddedComposite;

	private Image image;

	private String errorMsg = "No media selected.";

	public ModFilePlayer modFilePlayer;

	public MediaView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		embeddedComposite = new Composite(parent, SWT.EMBEDDED);
		final Frame baseSWTAWTFrame = SWT_AWT.new_Frame(embeddedComposite);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				panel = new JPanel() {
					private static final long serialVersionUID = 2270441173592540794L;

					@Override
					public void paint(Graphics g) {
						if (image != null) {
							g.drawImage(image, 0, 0, getBounds().width, getBounds().height, null);
						} else {
							g.setColor(new Color(255, 255, 255));
							g.fillRect(0, 0, getBounds().width, getBounds().height);
							g.setColor(new Color(0, 0, 0));
							g.drawString(errorMsg, 5, 15);
						}
					};
				};
				baseSWTAWTFrame.add(panel);
			}
		});

		hookListeners();
	}

	private void hookListeners() {
		ISelectionService ss = getSite().getWorkbenchWindow().getSelectionService();
		selectionListener = new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				if (modFilePlayer != null) {
					modFilePlayer.stop();
				}

				Object fe = ((IStructuredSelection) selection).getFirstElement();
				if (fe instanceof IPodFileEntry) {
					IPodFileEntry entry = (IPodFileEntry) fe;
					if (isViewable(entry)) {
						fileSelected(entry);
					}
				}
			}
		};
		ss.addSelectionListener(selectionListener);
	}

	protected boolean isViewable(IPodFileEntry entry) {
		boolean isImage = isBmp(entry) || isTif(entry);
		if (isAct(entry) || isRaw(entry) || isImage || isMod(entry)) {
			return true;
		}
		return false;
	}

	private boolean isRaw(IPodFileEntry entry) {
		return entry instanceof IRawPodFileEntry;
	}

	private boolean isAct(IPodFileEntry entry) {
		return entry instanceof IActPodFileEntry;
	}

	private boolean isMod(IPodFileEntry entry) {
		return entry instanceof IModPodFileEntry;
	}

	protected void fileSelected(IPodFileEntry entry) {
		if (panel != null) {
			boolean refresh = false;
			if (isAct(entry)) {
				refresh = true;
				showAct((IActPodFileEntry) entry);
			} else if (isRaw(entry)) {
				refresh = true;
				showRaw((IRawPodFileEntry) entry);
			} else if (isBmp(entry) || isTif(entry)) {
				refresh = true;
				showImage(entry);
			} else if (entry instanceof IModPodFileEntry) {
				try {
					if (modFilePlayer != null) {
						modFilePlayer.stop();
					}

					File temp = File.createTempFile("mtmx", ".mod");
				    temp.deleteOnExit();
				    entry.toFile(temp);
					modFilePlayer = new ModFilePlayer(temp);
					modFilePlayer.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				} catch (FileStoreException e) {
					// TODO Auto-generated catch block
				}
			}

			if (refresh) {
				String desc = entry.getPodFile().getFile().getName() + ": " + entry.getPath();
				setContentDescription(desc);
			}
		}
	}

	private boolean isTif(IPodFileEntry entry) {
		return entry instanceof ITifPodFileEntry;
	}

	private void showImage(IPodFileEntry podFileEntry) {
		try {
			setImage(ImageIO.read(podFileEntry.getInputStreamFromPod()));
		} catch (IOException e) {
			setImage(null);
		}
	}

	private boolean isBmp(IPodFileEntry entry) {
		return entry instanceof IBmpPodFileEntry;
	}

	private void showAct(IActPodFileEntry podFileEntry) {
		try {
			setImage(podFileEntry.getData().toImage());
		} catch (FileLoadException e) {
			setImage(null);
		}
	}

	private void showRaw(IRawPodFileEntry podFileEntry) {
		try {
			IActData colorTable = null;
			IPodData data = podFileEntry.getPodFile().getData();
			IPodFileEntry actEntry = data.findColorTable(podFileEntry);

			if (actEntry != null) {
				ActFile actFile = new ActFile(toTempFile(actEntry));
				colorTable = actFile.getData();
			}

			if (colorTable == null) {
				colorTable = new DefaultActColorTable(256);
			}

			RawImage rawImage = new RawImage(podFileEntry.getRawData(), colorTable);
			setImage(rawImage.toImage());
		} catch (IOException e) {
			setImage(null);
		} catch (FileLoadException e) {
			setImage(null);
		} catch (FileStoreException e) {
			setImage(null);
		}
	}

	private void setImage(Image newImage) {
		if (newImage == null) {
			image = null;
			errorMsg = "Failed to display media";
		} else {
			image = newImage;
			errorMsg = "";
		}
		panel.repaint();
	}

	private File toTempFile(IPodFileEntry podFileEntry) throws FileStoreException, IOException {
		File file = File.createTempFile("mtmx", ".media.tmp");
		podFileEntry.toFile(file);
		return file;
	}

	@Override
	public void setFocus() {
		if (embeddedComposite != null && !embeddedComposite.isDisposed()) {
			embeddedComposite.setFocus();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (selectionListener != null) {
			ISelectionService ss = getSite().getWorkbenchWindow().getSelectionService();
			ss.removeSelectionListener(selectionListener);
		}
	}
}
