package com.offcasoftware.shop2.view;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;
import com.offcasoftware.shop2.repository.ProductRepository;
import com.offcasoftware.shop2.repository.ProductRepositoryInterface;
import com.offcasoftware.shop2.view.widget.FragmentCommunicationActivity;
import com.offcasoftware.shop2.view.widget.ProductCardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements ProductCardView.ProductCardViewInterface {

    @BindView(R.id.activity_main)
    View mRootLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.design_navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupActionBarDrawerToggle();
        setupNavigationView();
        setupBottomNavigationView();
//        displayData();

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onProductClicked(final Product product) {
        Context context = this;
        Context context1 = getApplicationContext();
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(ProductDetailsActivity.INTENT_PRODUCT_ID, product.getId());
        startActivity(intent);

        Log.d("Shop", "Product clicked: " + product.getName());
    }

    @OnClick(R.id.add_new_product)
    public void onAddProductClicked(View view) {
        Intent intent = new Intent(MainActivity.this,
                AddProductActivity.class);
        startActivity(intent);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Mój sklep");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                        R.string.drawer_open, R.string.drawer_close) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setupNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                        mDrawerLayout.closeDrawer(GravityCompat.START);

                        MainActivity.this.onNavigationItemSelected(item.getItemId());
                        return false;
                    }
                });
    }

    private void setupBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                        MainActivity.this.onNavigationItemSelected(item.getItemId());
                        return false;
                    }
                });
    }

    private void onNavigationItemSelected(@IdRes int menuId) {
        switch (menuId) {
            case R.id.action1:
                //Toast.makeText(MainActivity.this, "Action1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, StorageActivity.class));
                break;
            case R.id.action2:
                //Toast.makeText(MainActivity.this, "Action2", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test.class));
                break;
            case R.id.action3:
//                startActivity(new Intent(this, FragmentTestActivity.class));
                startActivity(new Intent(this, FragmentCommunicationActivity.class));
               // Toast.makeText(MainActivity.this, "Action3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
