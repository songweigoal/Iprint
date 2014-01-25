package com.iprint.app;

import java.util.ArrayList;

import com.iprint.app.bean.AlbumEntity;

import android.app.Application;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author song
 * @version 1.0
 * @created 2012-2-23
 */
public class AppContext extends Application {

	private ArrayList<AlbumEntity> albums;

	@Override
	public void onCreate() {
		System.out.println("111111111");
		super.onCreate();
	}

	public ArrayList<AlbumEntity> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<AlbumEntity> albums) {
		this.albums = albums;
	}

}
