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
import java.util.ArrayList;
import java.util.List;

import jtrfp.game.GameDirFactory;
import jtrfp.game.ITriGameDir;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import de.stefanteitge.mtmx.workbench.ui.internal.prefs.IPreferenceConstants;

/**
 * @author Stefan Teitge
 */
public final class GameProvider {

	private ITriGameDir activeGame;

	private List<ITriGameDir> gameDirs;

	private static GameProvider instance;

	private IGameProviderListener gameProviderListener;

	private GameProvider() {
		gameDirs = new ArrayList<ITriGameDir>();

		loadGames();

		IPropertyChangeListener listener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				gameDirs = new ArrayList<ITriGameDir>();
				loadGames();
				if (gameProviderListener != null) {
					gameProviderListener.listChanged();
				}
			}
		};
		UiPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(listener);
	}

	/**
	 * API to be changed, do not use.
	 */
	public IGameProviderListener getListener() {
		return gameProviderListener;
	}

	/**
	 * API to be changed, do not use.
	 */
	public void setListener(IGameProviderListener gameProviderListener) {
		this.gameProviderListener = gameProviderListener;
	}

	private void loadGames() {
		activeGame = null;
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.BLAIR1_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.BLOODRAYNE1_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.BLOODRAYNE2_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.BLOWOUT_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.CART_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.EVO1_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.EVO2_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.FLY1_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.FLY2_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.FURY3_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.HELLBENDER_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.MTM1_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.MTM2_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.NOCTURNE_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.TV_DIR)));
		addGame(GameDirFactory.create(getDir(IPreferenceConstants.GHOSTBUSTERS_DIR)));
		checkActive();
	}

	public static GameProvider getInstance() {
		if (instance == null) {
			instance = new GameProvider();
		}

		return instance;
	}

	private void checkActive() {
		if (activeGame == null && gameDirs.size() > 0) {
			setActiveGame(gameDirs.get(0));
		}
	}

	private File getDir(String value) {
		IPreferenceStore store = UiPlugin.getDefault().getPreferenceStore();
		String dirPath = store.getString(value);
		if (dirPath != null) {
			return new File(dirPath);
		}
		return null;
	}

	public ITriGameDir getActiveGame() {
		return activeGame;
	}

	public ITriGameDir[] getGameDirs() {
		return gameDirs.toArray(new ITriGameDir[]{});
	}

	private void addGame(ITriGameDir gameDir) {
		if (gameDir == null) {
			return;
		}
		gameDirs.add(gameDir);
		checkActive();
	}

	public void setActiveGame(ITriGameDir gameDir) {
		activeGame = gameDir;
	}
}
