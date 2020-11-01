package com.geek.gutenberg.ui.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.gutenberg.R;
import com.geek.gutenberg.adapter.GenreAdapter;
import com.geek.gutenberg.custom.VerticalSpaceItemDecoration;
import com.geek.gutenberg.model.Genre;
import com.geek.gutenberg.viewmodel.CommonViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class GenreFragment extends Fragment implements GenreAdapter.OnGenreSelectedListener{

    private CommonViewModel mViewModel;
    private RecyclerView mGenreRecyclerView;
    private GenreAdapter mGenreAdapter;
    private OnGenreSelectedListener mOnGenreSelectedListener;

    public static GenreFragment newInstance() {
        return new GenreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        mGenreRecyclerView = view.findViewById(R.id.rv_genre);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mOnGenreSelectedListener = (OnGenreSelectedListener)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(CommonViewModel.class);
        mViewModel.getGenreMutableLiveData().observe(Objects.requireNonNull(getActivity()), genreListUpdateObserver);
        mViewModel.getGenre(); // Fetch Genre list from data source

    }

    /**
     * Observer for Genre list update.
     */
    private Observer<ArrayList<Genre>> genreListUpdateObserver = new Observer<ArrayList<Genre>>() {
        @Override
        public void onChanged(ArrayList<Genre> genreList) {
            setGenreAdapter(genreList);
        }
    };

    /**
     * Set recyclerview adapter for Genre
     * @param genreList list of Genre from data source observer response
     */
    private void setGenreAdapter(ArrayList<Genre> genreList){
        mGenreAdapter = new GenreAdapter(getActivity(),genreList, this);
        mGenreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration();
        mGenreRecyclerView.addItemDecoration(verticalSpaceItemDecoration);
        mGenreRecyclerView.setAdapter(mGenreAdapter);
    }

    /**
     * Callback for Genre selection.
     * Will persist selected Genre and pass callback to mainactivity for further navigation
     * @param tag selected Genre
     */
    @Override
    public void onGenreSelected(String tag) {
        mViewModel.setSelectedGenre(tag);
        mOnGenreSelectedListener.onGenreSelected();
    }

    /**
     * Interface for Genre selection callbacks. To be implemented by mainactivity.
     */
    public interface OnGenreSelectedListener {
        void onGenreSelected();
    }
}
