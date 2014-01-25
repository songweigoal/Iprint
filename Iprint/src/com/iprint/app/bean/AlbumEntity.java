package com.iprint.app.bean;

import java.util.ArrayList;

public class AlbumEntity {
	private String albumNameStr = "";
	private String albumUri = "";
	private int albumsSumSelected = 0; // 每一个album中选择图片的个数
	private int bucketId = 0;

	private ArrayList<PhotoEntity> photoes;

	public String getAlbumNameStr() {
		return albumNameStr;
	}

	public void setAlbumNameStr(String albumNameStr) {
		this.albumNameStr = albumNameStr;
	}

	public String getAlbumUri() {
		return albumUri;
	}

	public void setAlbumUri(String albumUri) {
		this.albumUri = albumUri;
	}

	public int getAlbumsSumSelected() {
		return albumsSumSelected;
	}

	public void setAlbumsSumSlected(int albumsSumSlected) {
		this.albumsSumSelected = albumsSumSlected;
	}

	public int getBucketId() {
		return bucketId;
	}

	public void setBucketId(int bucketId) {
		this.bucketId = bucketId;
	}

	public ArrayList<PhotoEntity> getPhotoes() {
		return photoes;
	}

	public void setPhotoes(ArrayList<PhotoEntity> photoes) {
		this.photoes = photoes;
	}

}
