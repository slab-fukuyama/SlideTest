package com.example.viewpager2_thumbindicator.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager2_thumbindicator.R;
import com.example.viewpager2_thumbindicator.databinding.ThumbnailItemBinding;
import com.example.viewpager2_thumbindicator.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder>{

    private static final String TAG = new Object(){}.getClass().getEnclosingClass().getName();

    private int selectedPosition;
    private List<ImageModel> imageList = new ArrayList<>();

    //    private final Function1 onThumbnailClick;
    private ThumbnailAdapter.OnItemClickListener ItemClickListener;

    public interface OnItemClickListener{
        void onItemClickListener(View view, int position, String clickedText );
        void onChangeImage(ImageModel image);
    }

    // リスナー
    public void setOnItemClickListener( ThumbnailAdapter.OnItemClickListener listener){
        this.ItemClickListener = listener;
    }

    public ThumbnailAdapter.OnItemClickListener getItemClickListener(){
        return this.ItemClickListener;
    }

    public ThumbnailAdapter(List<ImageModel> imageList, ThumbnailAdapter.OnItemClickListener callback) {
        super();
        this.imageList = imageList;
        this.ItemClickListener = callback;
    }

    @Override
    public ThumbnailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ThumbnailItemBinding binding = ThumbnailItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ThumbnailAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ThumbnailAdapter.ViewHolder holder, int position) {
        holder.bind(this.imageList.get(position), position);
        if (this.selectedPosition == position) {
//            holder.getBinding().rlThumbnail.setBackgroundColor(Color.BLUE);//.setBackgroundResource(Color.BLUE);
            holder.getBinding().rlThumbnail.setBackgroundResource(R.drawable.selected_border);
        } else {
            holder.getBinding().rlThumbnail.setBackgroundColor(Color.RED);//.setBackgroundResource(700120);
            holder.getBinding().rlThumbnail.setBackgroundResource(R.drawable.default_border);
        }

    }

    @Override
    public int getItemCount() {
        return this.imageList.size();
    }

    public void updateSelectedPosition(int position){
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {

        private ThumbnailItemBinding binding;

        public void bind( ImageModel image, int position) {

            ImageView imageView = this.binding.ivThumbnail;

            ImageLoader imageLoader = Coil.imageLoader(imageView.getContext());

            ImageRequest.Builder builder = (new ImageRequest.Builder(imageView.getContext())).data(image.getUrl()).target(imageView);

            ImageRequest request = builder.build();
            imageLoader.enqueue(request);
            this.binding.rlThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick");
                    ThumbnailAdapter.this.getItemClickListener().onChangeImage(image);
                    ItemClickListener.onItemClickListener(v, position, image.getUrl());
                }
            });

        }

        public ThumbnailItemBinding getBinding() {
            return this.binding;
        }

        public ViewHolder( ThumbnailItemBinding binding) {
            super((View)binding.getRoot());
            this.binding = binding;
        }

    }

}
