package com.geek.gutenberg.network.impl;

import android.content.Context;

import com.geek.gutenberg.R;
import com.geek.gutenberg.model.Genre;
import com.geek.gutenberg.network.service.GenreService;

import java.util.ArrayList;

public class GenreServiceImpl implements GenreService {
    private Context mContext;

    public GenreServiceImpl(Context context) {
        this.mContext = context;
    }

    /**
     * Genre list. Model contains name tag and associated image resource.
     * @return list of Genre.
     */
    @Override
    public ArrayList<Genre> getGenre() {
        ArrayList<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_fiction), R.drawable.ic_fiction));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_drama), R.drawable.ic_drama));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_humour), R.drawable.ic_humour));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_politics), R.drawable.ic_politics));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_phil), R.drawable.ic_philosophy));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_history), R.drawable.ic_history));
        genreList.add(new Genre(mContext.getString(R.string.genre_tag_advnture), R.drawable.ic_adventure));
        return genreList;
    }
}
