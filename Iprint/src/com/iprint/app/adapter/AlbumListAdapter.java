package com.iprint.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iprint.app.R;
import com.iprint.app.bean.AlbumEntity;
import com.iprint.app.common.ImageWorkerTN;

public class AlbumListAdapter extends BaseAdapter {
	private List<AlbumEntity> listItems;// 数据集合
	private int itemViewResource;// 自定义项视图源
	private ImageWorkerTN mImageWorker;
	private LayoutInflater listContainer;// 视图容器
	private Context context;

	static class ListItemView { // 自定义控件集合
		public ImageView imageAlbum;
		public TextView nameAlbum;
		public TextView sizeNumAlbum;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public AlbumListAdapter(Context context, List<AlbumEntity> data,
			int resource) {
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.context = context;
		this.listItems = data;
		mImageWorker = new ImageWorkerTN(context);
	}

	@Override
	public int getCount() {
		return listItems.size();

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {

		ListItemView listItemView = null;
		AlbumEntity album = null;
		int albumsSumSelect = 0;
		if (convertView == null) {
			System.out.println("-----------" + position);
			convertView = listContainer.inflate(this.itemViewResource, null);

			listItemView = new ListItemView();
			listItemView.imageAlbum = (ImageView) convertView
					.findViewById(R.id.album_img_albumName);
			listItemView.nameAlbum = (TextView) convertView
					.findViewById(R.id.album_text_albumName);
			listItemView.sizeNumAlbum = (TextView) convertView
					.findViewById(R.id.album_text_numSize);
			convertView.setTag(listItemView);

		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		album = listItems.get(position);
		albumsSumSelect = album.getAlbumsSumSelected();
		listItemView.nameAlbum.setText(album.getAlbumNameStr());
		if (albumsSumSelect > 0) {
			listItemView.sizeNumAlbum.setTextColor(context.getResources()
					.getColor(R.color.yellow));
			listItemView.sizeNumAlbum.setText("(" + albumsSumSelect + "/"
					+ album.getPhotoes().size() + ")");
		} else {
			listItemView.sizeNumAlbum.setTextColor(context.getResources()
					.getColor(R.color.item_text));
			listItemView.sizeNumAlbum.setText(album.getPhotoes().size() + "");
		}
		try {
			mImageWorker.loadImage(listItems.get(position).getAlbumUri(),
					listItemView.imageAlbum);
		} catch (OutOfMemoryError oome) {
			oome.printStackTrace();
		}
		return convertView;

	}

}
