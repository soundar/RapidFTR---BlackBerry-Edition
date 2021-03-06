package com.rapidftr.model;

import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.rapidftr.controls.Button;
import com.rapidftr.screens.NewChildScreen;
import com.rapidftr.utilities.ImageCaptureListener;
import com.rapidftr.utilities.ImageUtility;

public class PhotoUploadFormField extends FormField implements
		ImageCaptureListener {

	public static final String TYPE = "photo_upload_box";

	private Bitmap bitmap;
	private VerticalFieldManager manager;
	private String imageLocation;
	private Button capturePhoto;

	private PhotoUploadFormField(String name) {
		super(name, TYPE);
	}

	public void initializeLayout(final NewChildScreen newChildScreen) {

		final ImageCaptureListener imageChanageListener = this;

		manager = new VerticalFieldManager(Field.FIELD_LEFT);
		bitmap = Bitmap.getBitmapResource("res/head.png");
		capturePhoto = new Button(bitmap);

		capturePhoto.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				newChildScreen.takePhoto(imageChanageListener);
			}
		});

		manager.add(new LabelField(name));
		manager.add(capturePhoto);

	}

	public Manager getLayout() {
		return manager;
	}

	public static PhotoUploadFormField createdFormField(String name, String type) {
		if (type.equals(TYPE)) {
			return new PhotoUploadFormField(name);
		}

		return null;

	}

	public void onImagedSaved(String imageLocation, EncodedImage encodedImage) {

		this.imageLocation = imageLocation;

		ImageUtility imageUtility = new ImageUtility();

		int requiredWidth = Fixed32.toFP(bitmap.getWidth());
		int requiredHeight = Fixed32.toFP(bitmap.getHeight());

		bitmap = imageUtility.scaleImage(encodedImage, requiredWidth,
				requiredHeight);

		capturePhoto.setBitmap(bitmap);

	}

	public String getValue() {
		return imageLocation;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (!(obj instanceof PhotoUploadFormField))
			return false;

		PhotoUploadFormField field = (PhotoUploadFormField) obj;
		return name.equals(field.name);
	}

}
