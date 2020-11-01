package com.geek.gutenberg.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.gutenberg.R;
import com.geek.gutenberg.model.Genre;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private ArrayList<Genre> genreList;
    private OnGenreSelectedListener mGenreSelectedListener;

    public GenreAdapter(Activity context, ArrayList<Genre> genreList, OnGenreSelectedListener listener) {
        this.context = context;
        this.genreList = genreList;
        this.mGenreSelectedListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.row_genre,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Genre genre= genreList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;
        viewHolder.tv_tag.setText(genre.getTag().toUpperCase());
        viewHolder.iv_icon.setImageDrawable(context.getDrawable(genre.getImage()));
        viewHolder.itemView.setOnClickListener(view -> mGenreSelectedListener.onGenreSelected(genreList.get(position).getTag()));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tag;
        ImageView iv_icon;

        RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tag = itemView.findViewById(R.id.tv_tag);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    public interface OnGenreSelectedListener {
        void onGenreSelected(String tag);
    }
}
