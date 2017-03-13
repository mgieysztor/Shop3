package com.offcasoftware.shop2.view.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;
import com.offcasoftware.shop2.repository.ProductRepository;
import com.offcasoftware.shop2.repository.ProductRepositoryInterface;
import com.offcasoftware.shop2.view.ProductDetailsActivity;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListFragment extends Fragment implements ProductCardView.ProductCardViewInterface {

    @BindDimen(R.dimen.product_list_item_height)
    int mProductListItemHeight;

    @BindView(R.id.line1)
    LinearLayout mContainer;

    private ProductRepositoryInterface mProductRepository
            = ProductRepository.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayData();
    }

    private void displayData() {
        List<Product> products = mProductRepository.getProducts();

        for (Product product : products) {

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            mProductListItemHeight);
            ProductCardView productCardView = new ProductCardView(getActivity());
            productCardView.setLayoutParams(layoutParams);
            productCardView.bindTo(product, this);
            mContainer.addView(productCardView);
        }
    }

    @Override
    public void onProductClicked(Product product) {


        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(ProductDetailsActivity.INTENT_PRODUCT_ID, product.getId());
        startActivity(intent);

        Log.d("Shop", "Product clicked: " + product.getName());
    }
}
