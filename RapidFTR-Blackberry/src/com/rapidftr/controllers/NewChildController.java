package com.rapidftr.controllers;

import com.rapidftr.datastore.ChildrenRecordStore;
import com.rapidftr.datastore.FormStore;
import com.rapidftr.model.Child;
import com.rapidftr.screens.NewChildScreen;
import com.rapidftr.screens.SnapshotScreen;
import com.rapidftr.screens.UiStack;
import com.rapidftr.utilities.ImageCaptureListener;

public class NewChildController extends Controller {

	private final FormStore formStore;
	private ChildrenRecordStore childRecordStore;

	public NewChildController(NewChildScreen screen, UiStack uiStack,
			FormStore formStore, ChildrenRecordStore childRecordStore) {
		super(screen, uiStack);
		this.formStore = formStore;
		this.childRecordStore = childRecordStore;
	}

	public void synchronizeForms() {
		dispatcher.synchronizeForms();
	}

	public void show() {
		((NewChildScreen) screen).setForms(formStore.getForms());
		super.show();
	}

	public void takeSnapshotAndUpdateWithNewImage(
			ImageCaptureListener imageCaptureListener) {

		SnapshotController snapshotController = new SnapshotController(
				new SnapshotScreen(), uiStack);
		snapshotController.show();
		snapshotController.setImageListener(imageCaptureListener);
	}

	public void saveChild(Child child) {
		childRecordStore.addChild(child);
	}

}
