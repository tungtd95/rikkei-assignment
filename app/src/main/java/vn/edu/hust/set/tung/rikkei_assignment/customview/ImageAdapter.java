package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.util.Echo;

/**
 * Created by tungt on 10/29/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<String> listImage;
    private Context context;

    public ImageAdapter(Context context, ArrayList<String> listImage) {
        this.listImage = listImage;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String imageLink = listImage.get(position);
        File imgFile = new  File(imageLink);
        if(imgFile.exists()){
            Picasso.with(context).load("file://" + imgFile.getAbsolutePath()).resize(300, 400).into(holder.ivItemImage);
        } else {
            holder.ivItemImage.setBackground(holder.ivItemImage.getContext().getDrawable(R.drawable.ic_broken_image_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return listImage != null ? listImage.size() : 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        ImageView ivItemRemove;
        public ImageViewHolder(View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            ivItemRemove = itemView.findViewById(R.id.ivItemRemove);
        }
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }
}
