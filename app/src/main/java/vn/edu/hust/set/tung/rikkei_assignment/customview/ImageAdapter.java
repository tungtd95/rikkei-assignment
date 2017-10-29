package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.activity.ChangeNoteActivity;
import vn.edu.hust.set.tung.rikkei_assignment.model.Image;

/**
 * Created by tungt on 10/29/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Image> listImage;
    private Context context;
    OnImageRemove onImageRemove;

    public ImageAdapter(ChangeNoteActivity changeNoteActivity, ArrayList<Image> listImage) {
        this.listImage = listImage;
        this.context = changeNoteActivity;
        this.onImageRemove = changeNoteActivity;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        String imageLink = listImage.get(position).getLink();
        File imgFile = new  File(imageLink);
        if(imgFile.exists()){
            Picasso.with(context).load("file://" + imgFile.getAbsolutePath()).resize(300, 400).into(holder.ivItemImage);
        } else {
            holder.ivItemImage.setBackground(holder.ivItemImage.getContext().getDrawable(R.drawable.ic_broken_image_black_24dp));
        }
        holder.ivItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageRemove.onRemove(position);
            }
        });
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

    public ArrayList<Image> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<Image> listImage) {
        this.listImage = listImage;
    }
}
