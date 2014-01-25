package com.iprint.app.bean;

public class PhotoEntity {
	private String photoURI;
	private int photoBucketId;
	private boolean isChecked;

	public String getPhotoURI() {
		return photoURI;
	}

	public void setPhotoURI(String photoURI) {
		this.photoURI = photoURI;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getPhotoBucketId() {
		return photoBucketId;
	}

	public void setPhotoBucketId(int photoBucketId) {
		this.photoBucketId = photoBucketId;
	}

}
