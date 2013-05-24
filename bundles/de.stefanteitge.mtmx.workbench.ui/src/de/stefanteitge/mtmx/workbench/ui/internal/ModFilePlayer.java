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

import java.io.File;
import java.io.IOException;

import de.quippy.javamod.main.gui.PlayThread;
import de.quippy.javamod.main.gui.PlayThreadEventListener;
import de.quippy.javamod.multimedia.mod.ModMixer;
import de.quippy.javamod.multimedia.mod.loader.Module;
import de.quippy.javamod.multimedia.mod.loader.ModuleFactory;
import de.quippy.javamod.system.Helpers;

public class ModFilePlayer implements PlayThreadEventListener {

	private File file;

	private PlayThread playerThread = null;

	static {
		try
		{
			Helpers.registerAllClasses();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}

	public ModFilePlayer(File file) {
		this.file = file;
	}

	@Override
	public void playThreadEventOccured(PlayThread thread)
	{
	}

	public void start() {
		if (file != null) {
			stop();
			Module mod = null;

			try
			{
				mod = ModuleFactory.getInstance(file);
			}
			catch (IOException ex)
			{
				System.err.println(ex.getMessage());
			}

			if (mod != null) {
				ModMixer mixer = new ModMixer(mod,
					16, // sample size (bits)
					2, // channel
					44100, // sample rate
					2, // isp
					false, // wide stereo
					true, // noise reduction
					false, // mega bass
					Helpers.PLAYER_LOOP_DEACTIVATED);
				playerThread = new PlayThread(mixer, this);
				playerThread.start();
			}
		}
	}

	public void stop() {
		if (playerThread!=null) {
			playerThread.stopMod();
			playerThread = null;
		}
	}

	public void pause() {
		if (playerThread!=null) {
			playerThread.pausePlay();
		}
	}
}