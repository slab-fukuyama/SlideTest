package com.example.viewpager2_thumbindicator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.example.viewpager2_thumbindicator.adapter.ThumbnailAdapter;
import com.example.viewpager2_thumbindicator.databinding.ActivityTopBinding;
import com.example.viewpager2_thumbindicator.library.LoopingLayoutManager;
import com.example.viewpager2_thumbindicator.model.ImageModel;
import com.example.viewpager2_thumbindicator.viewmodel.TopViewModel;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import kotlin.collections.CollectionsKt;


public class TopActivity extends AppCompatActivity implements ThumbnailAdapter.OnItemClickListener {

    private static final String TAG = new Object() {}.getClass().getEnclosingClass().getName();
    private TopViewModel viewModel;

    private ThumbnailAdapter thumbnailAdapter;

    private int count = 0;

    @Override
    public void onItemClickListener(View view, int position, String clickedText) {

        Log.d(TAG, String.format("onItemClickListener position:%d", position));
        Toast.makeText(getApplicationContext(),String.format("%s", clickedText), Toast.LENGTH_LONG).show();
        thumbnailAdapter.updateSelectedPosition(position);

        Log.d(TAG, String.format("recyclerView.getScrollX():%d", viewModel.binding.recycler.getScrollX()));

        int scrollX = (view.getLeft() - ( 1080  / 2)) + (view.getWidth() / 2);
        Log.d(TAG, String.format("view.getLeft():%d", view.getLeft()));
        Log.d(TAG, String.format("scrollX:%d", scrollX));
        viewModel.binding.recycler.smoothScrollBy(scrollX, 0);
        Log.d(TAG, String.format("Change recyclerView.getScrollX():%d", viewModel.binding.recycler.getScrollX()));

    }

    @Override
    public void onChangeImage(ImageModel image) {
        Log.d(TAG, "root onChangeImage");

        Log.d(TAG, "onChangeImage");

        ImageView imageView = viewModel.binding.view;

        String imageUrl = image.getUrl();

        ImageLoader imageLoader = Coil.imageLoader(imageView.getContext());

        ImageRequest.Builder builder = (new ImageRequest.Builder(imageView.getContext())).data(imageUrl).target(imageView);

        ImageRequest imageRequest = builder.build();
        imageLoader.enqueue(imageRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        viewModel = new ViewModelProvider(this).get(TopViewModel.class);
        viewModel.binding = ActivityTopBinding.inflate(getLayoutInflater());

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(viewModel.binding.recycler);

        final List<ImageModel> imageList = CollectionsKt.listOf(new ImageModel[]{new ImageModel("https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ"), new ImageModel("https://i.picsum.photos/id/10/2500/1667.jpg?hmac=J04WWC_ebchx3WwzbM-Z4_KC_LeLBWr5LZMaAkWkF68"), new ImageModel("https://i.picsum.photos/id/1001/5616/3744.jpg?hmac=38lkvX7tHXmlNbI0HzZbtkJ6_wpWyqvkX4Ty6vYElZE"), new ImageModel("https://i.picsum.photos/id/1004/5616/3744.jpg?hmac=Or7EJnz-ky5bsKa9_frdDcDCR9VhCP8kMnbZV6-WOrY"), new ImageModel("https://i.picsum.photos/id/101/2621/1747.jpg?hmac=cu15YGotS0gIYdBbR1he5NtBLZAAY6aIY5AbORRAngs"), new ImageModel("https://i.picsum.photos/id/1010/5184/3456.jpg?hmac=7SE0MNAloXpJXDxio2nvoshUx9roGIJ_5pZej6qdxXs"), new ImageModel("https://i.picsum.photos/id/1013/4256/2832.jpg?hmac=UmYgZfqY_sNtHdug0Gd73bHFyf1VvzFWzPXSr5VTnDA"), new ImageModel("https://i.picsum.photos/id/1016/3844/2563.jpg?hmac=WEryKFRvTdeae2aUrY-DHscSmZuyYI9jd_-p94stBvc")});

        this.thumbnailAdapter = new ThumbnailAdapter(imageList,this);

        this.thumbnailAdapter.setOnItemClickListener(this);

        viewModel.binding.recycler.setAdapter(this.thumbnailAdapter);
        viewModel.binding.recycler.setLayoutManager(new LoopingLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        setContentView(viewModel.binding.getRoot());

    }


}
