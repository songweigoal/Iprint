package com.iprint.app.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.iprint.app.AppContext;
import com.iprint.app.R;
import com.iprint.app.adapter.ImageSelectionAdapter;
import com.iprint.app.bean.AlbumEntity;
import com.iprint.app.bean.PhotoEntity;
import com.iprint.app.common.PrintHelper;
import com.iprint.app.view.ImageCheckBoxView;

public class ImageSelectionActivity extends BaseActivity {
	private Button next_btn;
	private Button back_btn;
	private Button settings_btn;
	private Button info_btn;
	private Button selectall_btn;
	private Button deselectall_btn;
	private Button modeSwitch_btn;

	private GridView picturesGrid;
	private ImageSelectionAdapter mImageAdapter;

	private String albumName;
	private int albumId;
	private ArrayList<PhotoEntity> photoes = null;
	private PhotoEntity photoObj = null;
	private ArrayList<AlbumEntity> albums = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageselection);
		Bundle b = getIntent().getExtras();
		albumId = b.getInt("bucketId");
		albumName = b.getString("bucketName");
		photoes = new ArrayList<PhotoEntity>();
		this.initFieldView(); // 头部用到了主体中的数据，所以让主体先执行
		this.initHeadView();
		this.initFootBar();

	}

	// 从全局变量中获取相片对象
	public void initImageSelectionData() {
		AppContext appContex = (AppContext) getApplication();
		albums = appContex.getAlbums();
		for (AlbumEntity albEntity : albums) {
			if (albEntity.getBucketId() == albumId) {
				photoes = albEntity.getPhotoes();
				break;
			}
		}
	}

	/**
	 * 初始化头部视图
	 */
	private void initHeadView() {
		back_btn = (Button) findViewById(R.id.back_btn);
		next_btn = (Button) findViewById(R.id.next_btn);
		back_btn.setVisibility(View.VISIBLE);
		next_btn.setVisibility(View.VISIBLE);
		back_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				ImageSelectionActivity.this.setResult(RESULT_OK, i);
				ImageSelectionActivity.this.finish();
			}
		});
	}

	/**
	 * 初始 化主体视图
	 */
	private void initFieldView() {
		initImageSelectionData();
		mImageAdapter = new ImageSelectionAdapter(ImageSelectionActivity.this,
				photoes, albumName);
		picturesGrid = (GridView) findViewById(R.id.picturesGrid);
		picturesGrid.setAdapter(mImageAdapter);
		mImageAdapter.notifyDataSetChanged();

		picturesGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				ImageCheckBoxView icbv = (ImageCheckBoxView) view;
				photoObj = photoes.get(position);
				boolean isChecked = photoObj.isChecked();
				if (isChecked) {
					photoObj.setChecked(false);
					icbv.invalidate();
				} else {
					photoObj.setChecked(true);
					icbv.invalidate();
				}

			}
		});
		picturesGrid.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				ImageSelectionAdapter tempAdapter = ((ImageSelectionAdapter) picturesGrid
						.getAdapter());
				if (tempAdapter != null) {
					if (scrollState != 0) {
						PrintHelper.isScrolling = true;
					} else {
						PrintHelper.isScrolling = false;
						tempAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}
		});

	}

	/**
	 * 初始化底部栏
	 */
	private void initFootBar() {
		settings_btn = (Button) findViewById(R.id.settings_btn);
		info_btn = (Button) findViewById(R.id.info_btn);
		selectall_btn = (Button) findViewById(R.id.selectall_btn);
		deselectall_btn = (Button) findViewById(R.id.deselectall_btn);
		modeSwitch_btn = (Button) findViewById(R.id.modeSwitch_btn);

		selectall_btn.setVisibility(View.VISIBLE);
		deselectall_btn.setVisibility(View.VISIBLE);
		modeSwitch_btn.setVisibility(View.VISIBLE);

		settings_btn.setOnClickListener(null);
		info_btn.setOnClickListener(null);
		selectall_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				for (PhotoEntity photoObj : photoes) {
					photoObj.setChecked(true);
					mImageAdapter.notifyDataSetChanged();
				}

			}
		});

		deselectall_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				for (PhotoEntity photoObj : photoes) {
					photoObj.setChecked(false);
					mImageAdapter.notifyDataSetChanged();
				}

			}
		});
	}

	// 当点击相片是，相片的状态会发生改变，将改变后的数据保存在全局变量中。
	@Override
	protected void onPause() {
		int albumsSumSlected = 0;
		for (PhotoEntity photoObj : photoes) {
			if (photoObj.isChecked()) {
				albumsSumSlected++;
			}
		}
		AppContext appContex = (AppContext) getApplication();
		for (AlbumEntity albEntity : albums) {
			if (albEntity.getBucketId() == albumId) {
				albEntity.setPhotoes(photoes);
				albEntity.setAlbumsSumSlected(albumsSumSlected);
				break;
			}
		}
		appContex.setAlbums(albums);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		picturesGrid.setAdapter(null);
		picturesGrid = null;
		mImageAdapter = null;
		super.onDestroy();
	}
}
