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

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.stefanteitge.mtmx.workbench.ui.UiPlugin;

public class GamePreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	public GamePreferencePage() {
		super(GRID);
		setPreferenceStore(UiPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.EVO1_DIR,
				"4x4 Evo 1 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.EVO2_DIR,
				"4x4 Evo 2 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.BLAIR1_DIR,
				"Blair Witch 1 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.BLOODRAYNE1_DIR,
				"BloodRayne directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.BLOODRAYNE2_DIR,
				"BloodRayne 2 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.BLOWOUT_DIR,
				"Blowout directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.CART_DIR,
				"CART directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.FLY1_DIR,
				"Fly! directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.FLY2_DIR,
				"Fly! II directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.FURY3_DIR,
				"Fury3 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.GHOSTBUSTERS_DIR,
				"Ghostbusters directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.HELLBENDER_DIR,
				"Hellbender directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.MTM1_DIR,
				"MTM1 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.MTM2_DIR,
				"MTM2 directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.NOCTURNE_DIR,
				"Nocturne directory:",
				getFieldEditorParent()));
		addField(new DirectoryFieldEditor(
				IPreferenceConstants.TV_DIR,
				"Terminal Velocity directory:",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
