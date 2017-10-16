package io.github.wotaslive.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.R;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.materialViewPager)
	MaterialViewPager mMaterialViewPager;
	@BindView(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("");
		ButterKnife.bind(this);

		Toolbar toolbar = mMaterialViewPager.getToolbar();
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
		mDrawerLayout.addDrawerListener(mDrawerToggle);
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(true);
		}
		initViewpager();
	}

	@Override
	public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onPostCreate(savedInstanceState, persistentState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	private void initViewpager() {
		MainPagerAdapter pagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
		mMaterialViewPager.getViewPager().setAdapter(pagerAdapter);

		mMaterialViewPager.setMaterialViewPagerListener(page -> {
			switch (page) {
				case 0:
					return HeaderDesign.fromColorResAndDrawable(R.color.green, getDrawable(R.drawable.bg_test1));
				case 1:
					return HeaderDesign.fromColorResAndDrawable(R.color.blue, getDrawable(R.drawable.bg_test2));
				case 2:
					return HeaderDesign.fromColorResAndDrawable(R.color.cyan, getDrawable(R.drawable.bg_test1));
				case 3:
					return HeaderDesign.fromColorResAndDrawable(R.color.red, getDrawable(R.drawable.bg_test2));
			}
			return null;
		});

		mMaterialViewPager.getViewPager().setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
		mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());
	}
}
