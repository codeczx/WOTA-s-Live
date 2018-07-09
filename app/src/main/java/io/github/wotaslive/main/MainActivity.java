package io.github.wotaslive.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.R;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

	@BindView(R.id.materialViewPager)
	MaterialViewPager mMaterialViewPager;
	@BindView(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private MainContract.MainPresenter mPresenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		setTitle("");
		ButterKnife.bind(this);
		new MainPresenterImpl(this);
		mPresenter.loanHeader(this);

		Toolbar toolbar = mMaterialViewPager.getToolbar();
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(true);
		}

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
		mDrawerLayout.addDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
		initViewpager();
	}

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
			mDrawerLayout.closeDrawer(Gravity.START);
		}
		else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		mPresenter.unSubscribe();
		super.onDestroy();
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

		mMaterialViewPager.setMaterialViewPagerListener(page ->
				HeaderDesign.fromColorResAndDrawable(R.color.colorPrimaryDark, getDrawable(R.drawable.bg_default_header)));

		mMaterialViewPager.getViewPager().setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
		mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());
	}

	@Override
	public void setPresenter(MainContract.MainPresenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void updateHeader(@NotNull MaterialViewPager.Listener listener) {
		mMaterialViewPager.setMaterialViewPagerListener(listener);
		mMaterialViewPager.notifyHeaderChanged();
	}
}
