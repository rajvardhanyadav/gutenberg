package com.geek.gutenberg.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.gutenberg.R;
import com.geek.gutenberg.model.Author;
import com.geek.gutenberg.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "BooksAdapter";
    private Activity context;
    private ArrayList<Book> mBooksList;
    private OnBookSelectedListener mOnBookSelectedListener;

    public BooksAdapter(Activity context, ArrayList<Book> booksList, OnBookSelectedListener listener) {
        this.context = context;
        this.mBooksList = booksList;
        this.mOnBookSelectedListener = listener;
    }

    public void updateBooksList(ArrayList<Book> booksList){
        this.mBooksList.addAll(booksList);
        notifyDataSetChanged();
        Log.d(TAG, "updateBookList: total books "+this.mBooksList.size());
    }

    public void clearBooksList(){
        this.mBooksList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_book, parent, false);
        return new BooksAdapter.RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Book book = mBooksList.get(position);
        BooksAdapter.RecyclerViewViewHolder viewHolder = (BooksAdapter.RecyclerViewViewHolder) holder;
        viewHolder.tv_title.setText(book.getTitle().toUpperCase());
        viewHolder.tv_author.setText(getAuthor(book.getAuthors()));
        Picasso.get().load(book.getFormats().getImageJpeg()).into(viewHolder.iv_icon);
        viewHolder.itemView.setOnClickListener(view -> mOnBookSelectedListener.onBookSelected(book));
    }

    @Override
    public int getItemCount() {
        return mBooksList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author;
        ImageView iv_icon;

        RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    /**
     * Get author name from list. As of now, return name of first author in list
     * @param authors list of authors from Book model
     * @return name of author or blank
     */
    private String getAuthor(List<Author> authors){
        String author="";
        if(authors!=null && authors.size()>0)
            author = authors.get(0).getName();
        return author;
    }

    public interface OnBookSelectedListener {
        void onBookSelected(Book book);
    }
}