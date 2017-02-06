package com.iprint.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iprint.app.R;

/**
 * 应用程序首页
 * 
 * @author song wei
 * @version 1.0
 * @created 2014-1-21 song2
 */
public class MainActivity extends BaseActivity {
	private Button settings_btn;
	private Button info_btn;
	private ImageView main_prints_img;
	private TextView main_prints_tex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		this.initFieldView();
		this.initFootBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 初始 化主体视图
	 */
	private void initFieldView() {
		OnClickListener printsClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AlbumSelectionActivity.class);
				startActivity(intent);
				finish();
			}
		};
		main_prints_img = (ImageView) findViewById(R.id.main_prints_img);
		main_prints_tex = (TextView) findViewById(R.id.main_prints_tex);
		main_prints_img.setOnClickListener(printsClickListener);
		main_prints_tex.setOnClickListener(printsClickListener);
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

}
