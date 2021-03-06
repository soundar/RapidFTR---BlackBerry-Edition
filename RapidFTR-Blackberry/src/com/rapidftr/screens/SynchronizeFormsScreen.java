package com.rapidftr.screens;



import com.rapidftr.controllers.SynchronizeFormsController;
import com.rapidftr.controls.Button;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.decor.BorderFactory;

public class SynchronizeFormsScreen extends CustomScreen implements
		FieldChangeListener {

	private static final XYEdges PADDING = new XYEdges(10, 10, 10, 10);
	private Button okButton;

	GaugeField downloadProgressBar;

	private Manager hButtonManager;
	private Button cancelButton;

	public SynchronizeFormsScreen() {

		layoutScreen();

	}

	private void layoutScreen() {
		try {
			LabelField labelField = new LabelField("Synchronizing Forms");
			Manager hManager = new HorizontalFieldManager(FIELD_HCENTER);
			hManager.add(labelField);
			hManager.setPadding(PADDING);

			add(hManager);

			Manager hGaugeManager = new HorizontalFieldManager(FIELD_HCENTER);

			downloadProgressBar = new GaugeField("", 0, 100, 0,
					GaugeField.LABEL_AS_PROGRESS);

			downloadProgressBar.setBorder(BorderFactory
					.createSimpleBorder(new XYEdges(1, 1, 1, 1)));

			hGaugeManager.add(downloadProgressBar);
			hGaugeManager.setPadding(PADDING);
			add(hGaugeManager);

			hButtonManager = new HorizontalFieldManager(FIELD_HCENTER);
			add(hButtonManager);
			hButtonManager.setPadding(PADDING);
			okButton = new Button("Ok");
			okButton.setChangeListener(this);
			cancelButton = new Button("Cancel");
			cancelButton.setChangeListener(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void resetProgressBar() {
		downloadProgressBar.setLabel("Downloading ..");
		downloadProgressBar.setValue(0);
		hButtonManager.deleteAll();
		hButtonManager.add(cancelButton);

	}

	public void updateDownloadProgessBar(final int value) {

		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				downloadProgressBar.setValue(value);
			}
		});

	}

	public void downloadCompleted() {

		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				downloadProgressBar.setLabel("Complete");
				downloadProgressBar.setValue(100);
				hButtonManager.deleteAll();
				hButtonManager.add(okButton);
			}
		});

	}

	public void downloadFailed() {

		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {

				int result = Dialog.ask(Dialog.D_YES_NO,
						"Download Failed.\n Do you want to Retry?");
				downloadProgressBar.setValue(0);

				if (result == Dialog.YES) {
					((SynchronizeFormsController) controller)
							.synchronizeForms();
					return;
				}

				controller.popScreen();

			}
		});

	}

	public void setUp() {

		
	}

	public void fieldChanged(Field field, int context) {

		if (field.equals(okButton)) {
			controller.popScreen();
			return;
		}
		if (field.equals(cancelButton)) {
			int result = Dialog.ask(Dialog.D_YES_NO,
					"Are you sure want to cacel synchronize?");
			downloadProgressBar.setValue(0);

			if (result == Dialog.YES) {
				((SynchronizeFormsController) controller)
						.stopSynchronizingForms();
				controller.popScreen();
				return;
			}

		}

	}

	public void onAuthenticationFailure() {
		
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {

				int result = Dialog.ask(Dialog.D_OK_CANCEL,
						"You are not logged in.\n Press ok to  login");
				downloadProgressBar.setValue(0);

				
				controller.popScreen();
				
				if (result == Dialog.OK) {
					((SynchronizeFormsController) controller)
							.login();
					return;
				}
				
				

				
			}
		});

	}

	public void cleanUp() {
		
		((SynchronizeFormsController) controller).stopSynchronizingForms();
		
	}
	
	public boolean onClose() {
	
		cleanUp();
		
		return super.onClose();
	}

}
