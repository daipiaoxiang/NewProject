package com.newproject.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.newproject.R;
import com.newproject.adapter.PageViewAdapter;
import com.newproject.base.BaseActivity;
import com.newproject.widget.TDevice;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {

	@BindView(R.id.guide)
	protected ViewPager pagerGuide;
//	@Bind(R.id.start)
//	protected Button btnStart;


	// 存放View
	private ArrayList<View> views;

	// 引导图片资源
	private static final int[] pics = { R.mipmap.ic_launcher,
			R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher };

	@Override
	protected int getLayoutId() {
		return R.layout.activity_guide;
	}

	@Override
	public boolean hasActionBar() {
		return false;
	}

	@Override
	protected void initView() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//		}
		views = new ArrayList<View>();
		PageViewAdapter adapterGuide = new PageViewAdapter(views);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			RelativeLayout view = new RelativeLayout(this);
			view.setBackgroundResource(pics[i]);
			if (i == pics.length-1) {//最后一项增加按钮
				Button btnStart = new Button(this);
				view.addView(btnStart);
				btnStart.setText("点击下一项");
				btnStart.setTextColor(getResources().getColorStateList(R.color.black));
				btnStart.setBackgroundResource(R.mipmap.ic_launcher);
				int dp10 = (int) TDevice.dpToPixel(10);
				btnStart.setPadding(dp10, dp10, dp10, dp10);
				btnStart.setTextSize(20);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				params.setMargins(0, 0, 0, (int)(TDevice.getScreenHeight()*0.134));//TODO 需要处理
				btnStart.setLayoutParams(params);
				btnStart.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						start();
					}
				});
			}
			views.add(view);
		}

		pagerGuide.setAdapter(adapterGuide);
	}

//	@Override
//	public void onPageScrollStateChanged(int arg0) {
//
//	}
//
//	@Override
//	public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//	}
//
//	@Override
//	public void onPageSelected(int arg0) {
//		if (arg0 == (pics.length - 1)) {
//			btnStart.setVisibility(View.VISIBLE);
//		} else {
//			btnStart.setVisibility(View.INVISIBLE);
//		}
//	}

	@OnClick({ R.id.jump})
	protected void start() {
		Intent to=new Intent(this,LoginActivity.class);
		startActivity(to);
//		Intent intent = new Intent(this, MainActivity.class);
//		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
