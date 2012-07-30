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
package de.stefanteitge.mtmx.workbench.ui.internal.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.stefanteitge.mtmx.workbench.ui.UiPlugin;

public class GamePreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = UiPlugin.getDefault().getPreferenceStore();
		store.setDefault(IPreferenceConstants.BLAIR1_DIR, "");
		store.setDefault(IPreferenceConstants.BLOODRAYNE1_DIR, "");
		store.setDefault(IPreferenceConstants.BLOODRAYNE2_DIR, "");
		store.setDefault(IPreferenceConstants.BLOWOUT_DIR, "");
		store.setDefault(IPreferenceConstants.CART_DIR, "");
		store.setDefault(IPreferenceConstants.EVO1_DIR, "");
		store.setDefault(IPreferenceConstants.EVO2_DIR, "");
		store.setDefault(IPreferenceConstants.FLY1_DIR, "");
		store.setDefault(IPreferenceConstants.FLY2_DIR, "");
		store.setDefault(IPreferenceConstants.FURY3_DIR, "");
		store.setDefault(IPreferenceConstants.GHOSTBUSTERS_DIR, "");
		store.setDefault(IPreferenceConstants.HELLBENDER_DIR, "");
		store.setDefault(IPreferenceConstants.MTM1_DIR, "");
		store.setDefault(IPreferenceConstants.MTM2_DIR, "");
		store.setDefault(IPreferenceConstants.NOCTURNE_DIR, "");
		store.setDefault(IPreferenceConstants.TV_DIR, "");
	}
}
