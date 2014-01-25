package com.iprint.app.ui;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iprint.app.AppContext;
import com.iprint.app.R;
import com.iprint.app.adapter.AlbumListAdapter;
import com.iprint.app.bean.AlbumEntity;
import com.iprint.app.bean.PhotoEntity;
import com.iprint.app.common.PrintHelper;
import com.iprint.app.widget.InfoDialog;

/**
 * 相册目录页面
 * 
 * @author song
 * @version 1.0
 * @created 2014-1-21
 */
public class AlbumSelectionActivity extends BaseActivity {
	private Button next_btn;
	private Button back_btn;
	private Button settings_btn;
	private Button info_btn;

	private TextView albums_sum_tex;
	private TextView totalSelected_tex;

	private ImageView imageView1;

	private ListView lvAlbum;

	private LinearLayout album_tex_layout;

	private int sumPhotos = 0; // 所有图片的数量
	private int sumSelects = 0;// 选中图片的数量
	private AlbumListAdapter adapter; // 文件夹列表的适配器
	private PhotoEntity photo = null;
	private ArrayList<PhotoEntity> photoes = new ArrayList<PhotoEntity>();
	private ArrayList<AlbumEntity> albumListData = new ArrayList<AlbumEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alubmselection);
		this.initFieldView(); // 头部用到了主体中的数据，所以让主体先执行
		this.initHeadView();
		this.initFootBar();
	}

	/**
	 * 初始化头部视图
	 */
	private void initHeadView() {
		next_btn = (Button) findViewById(R.id.next_btn);
		back_btn = (Button) findViewById(R.id.back_btn);
		album_tex_layout = (LinearLayout) findViewById(R.id.album_tex_layout);
		albums_sum_tex = (TextView) findViewById(R.id.albums_sum_tex);
		totalSelected_tex = (TextView) findViewById(R.id.totalSelected_tex);
		imageView1 = (ImageView) findViewById(R.id.imageView1);

		next_btn.setVisibility(View.VISIBLE);
		back_btn.setVisibility(View.VISIBLE);
		album_tex_layout.setVisibility(View.VISIBLE);
		imageView1.setVisibility(View.GONE);

		next_btn.setText(R.string.next);
		back_btn.setText(R.string.Back);
		albums_sum_tex.setText(getString(R.string.albums) + "("
				+ albumListData.size() + ")");
		totalSelected_tex.setText("(" + sumSelects + "/" + sumPhotos + ")");

		next_btn.setOnClickListener(null);
		back_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sumSelects > 0) {
					InfoDialog.InfoDialogBuilder builder = new InfoDialog.InfoDialogBuilder(
							AlbumSelectionActivity.this);
					builder.setTitle("");
					builder.setMessage(getString(R.string.losework));
					builder.setPositiveButton(getString(R.string.yes),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									Intent intent = new Intent();
									intent.setClass(
											AlbumSelectionActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();
								}
							});
					builder.setNegativeButton(getString(R.string.no),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					builder.create().show();
				} else {
					Intent intent = new Intent();
					intent.setClass(AlbumSelectionActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}

			}
		});
	}

	/**
	 * 初始 化主体视图
	 */
	private void initFieldView() {
		initListAlbum();
		initAlbumListData();
		setListeners();
	}

	/**
	 * 初始化底部栏
	 */
	private void initFootBar() {
		settings_btn = (Button) findViewById(R.id.settings_btn);
		info_btn = (Button) findViewById(R.id.info_btn);

		settings_btn.setOnClickListener(null);
		info_btn.setOnClickListener(null);
	}

	private void initListAlbum() {
		lvAlbum = (ListView) findViewById(R.id.album_listView);
		adapter = new AlbumListAdapter(AlbumSelectionActivity.this,
				albumListData, R.layout.album_listitem);
		lvAlbum.setAdapter(adapter);
		lvAlbum.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				AlbumListAdapter tempAdapter = ((AlbumListAdapter) lvAlbum
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

		});
	}

	private void setListeners() {
		lvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int bucketId = albumListData.get(position).getBucketId();
				String bucketName = albumListData.get(position)
						.getAlbumNameStr();
				Intent intent = new Intent();
				intent.setClass(AlbumSelectionActivity.this,
						ImageSelectionActivity.class);
				Bundle b = new Bundle();
				b.putInt("bucketId", bucketId);
				b.putString("bucketName", bucketName);
				intent.putExtras(b);
				startActivityForResult(intent, 1);
			}
		});
	}

	private void initAlbumListData() {

		final String[] columns = { MediaColumns.DATA, BaseColumns._ID,
				ImageColumns.BUCKET_ID, ImageColumns.BUCKET_DISPLAY_NAME };
		final String orderBy = BaseColumns._ID + " desc";
		String selector = ImageColumns.MIME_TYPE + " = "
				+ DatabaseUtils.sqlEscapeString("image/jpeg") + " OR "
				+ ImageColumns.MIME_TYPE + " = "
				+ DatabaseUtils.sqlEscapeString("image/jpg") + " OR "
				+ ImageColumns.MIME_TYPE + " = "
				+ DatabaseUtils.sqlEscapeString("image/png");
		Cursor c = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
				selector, null, orderBy);
		String albumName = "";
		int image_column_index = 0;
		int id = 0;
		Uri uri = null;
		int bucketId = 0;
		try {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				albumName = c.getString(c
						.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
				image_column_index = c.getColumnIndex(BaseColumns._ID);
				id = c.getInt(image_column_index);
				uri = Uri.withAppendedPath(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						Integer.toString(id));
				bucketId = c.getInt(c.getColumnIndex(ImageColumns.BUCKET_ID));
				// check if the album has been added to the
				// PrintHelper.mAlbumButton
				sumPhotos++;
				photo = new PhotoEntity();
				photo.setPhotoURI(uri.toString());
				photo.setPhotoBucketId(bucketId);
				photoes.add(photo);
				boolean added = false;
				for (AlbumEntity al : albumListData) {
					if (bucketId == al.getBucketId()) {
						// has been added
						added = true;

						break;
					}
				}
				if (!added) {
					AlbumEntity album = new AlbumEntity();
					album.setAlbumUri(uri.toString());
					album.setAlbumNameStr(albumName);
					album.setBucketId(bucketId);
					albumListData.add(album);
				}
			}

			ArrayList<PhotoEntity> tempPhotoes = new ArrayList<PhotoEntity>();
			for (AlbumEntity al : albumListData) {
				tempPhotoes = new ArrayList<PhotoEntity>();
				for (PhotoEntity photo : photoes) {
					if (photo.getPhotoBucketId() == al.getBucketId()) {
						tempPhotoes.add(photo);
					}
				}
				al.setPhotoes(tempPhotoes);
			}
			// 将媒体库中的数据存放到全局变量中
			AppContext appContex = (AppContext) getApplication();
			appContex.setAlbums(albumListData);
		} catch (Exception e) {
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			sumSelects = 0;
			AppContext appContex = (AppContext) getApplication();
			albumListData = appContex.getAlbums();
			for (AlbumEntity album : albumListData) {
				for (PhotoEntity photo : album.getPhotoes()) {
					if (photo.isChecked()) {
						sumSelects++;
					}
				}
			}
			totalSelected_tex.setText("(" + sumSelects + "/" + sumPhotos + ")");
			AlbumListAdapter tempAdapter = ((AlbumListAdapter) lvAlbum
					.getAdapter());
			if (tempAdapter != null) {
				tempAdapter.notifyDataSetChanged();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		lvAlbum.setAdapter(null);
		lvAlbum = null;
		adapter = null;
		albumListData.clear();
		albumListData = null;
		super.onDestroy();
	}

}
