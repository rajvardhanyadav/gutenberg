package com.geek.gutenberg.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geek.gutenberg.R;
import com.geek.gutenberg.adapter.BooksAdapter;
import com.geek.gutenberg.custom.VerticalSpaceItemDecoration;
import com.geek.gutenberg.model.Book;
import com.geek.gutenberg.viewmodel.CommonViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class BooksFragment extends Fragment implements BooksAdapter.OnBookSelectedListener, TextView.OnEditorActionListener {
    private static final String TAG = "BooksFragment";
    private CommonViewModel mViewModel;
    private TextView mTextViewGenre;
    private EditText mEditTextSearch;
    private ProgressBar mProgressbar;
    private RecyclerView mBooksRecyclerView;
    private GridLayoutManager mBooksGridLayoutManager;
    private BooksAdapter mBooksAdapter;
    private OnBookSelectedListener mOnBookSelectedListener;

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        mBooksRecyclerView = view.findViewById(R.id.rv_books);
        mTextViewGenre = view.findViewById(R.id.tv_genre);
        mEditTextSearch = view.findViewById(R.id.et_search);
        mProgressbar = view.findViewById(R.id.progress_bar);
        mEditTextSearch.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mOnBookSelectedListener = (OnBookSelectedListener) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(CommonViewModel.class);
        mViewModel.getBooksMutableLiveData().observe(Objects.requireNonNull(getActivity()), mBookListUpdateObserver);
        mViewModel.isLoading().observe(Objects.requireNonNull(getActivity()), mLoadingObserver);
        mViewModel.onError().observe(Objects.requireNonNull(getActivity()), mErrorObserver);
        mTextViewGenre.setText(mViewModel.getSelectedGenre());
        mViewModel.getBooks(); // Trigger all books api for selected genre
    }

    /**
     * Observer for error . Will show error snackbar
     */
    private Observer<String> mErrorObserver = message -> {
        Log.d(TAG, "onChanged: mErrorObserver");
        if(TextUtils.isEmpty(message))
            return;
        Snackbar snackbar = Snackbar
                .make(getView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    };

    /**
     * Observer for progressbar state toggle. Will show progress indeterminate progress when api is fetching data
     */
    private Observer<Boolean> mLoadingObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            Log.d(TAG, "onChanged: mLoadingObserver");
            if (aBoolean)
                mProgressbar.setVisibility(View.VISIBLE);
            else
                mProgressbar.setVisibility(View.GONE);
        }
    };

    /**
     * Observer for api response handling. Will load list of books into adapter
     */
    private Observer<ArrayList<Book>> mBookListUpdateObserver = new Observer<ArrayList<Book>>() {
        @Override
        public void onChanged(ArrayList<Book> books) {
            Log.d(TAG, "onChanged: mBookListUpdateObserver");
            if (books == null || books.size() == 0)
                return;
            if (mBooksAdapter == null)
                initBooksListAdapter(books);
            else
                updateBooksList(books);
        }
    };

    /**
     * Adapter for list of books. Uses GridLayout manager with span of 3
     * @param booksList list of books from response
     */
    private void initBooksListAdapter(ArrayList<Book> booksList){
        Log.d(TAG, "initBooksListAdapter: ");
        mBooksAdapter = new BooksAdapter(getActivity(), booksList, this);
        mBooksGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mBooksRecyclerView.setLayoutManager(mBooksGridLayoutManager);
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration();
        mBooksRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        mBooksRecyclerView.setAdapter(mBooksAdapter);
    }

    /**
     * Update list of books in case of pagination
     * @param booksList list of books for next page
     */
    private void updateBooksList(ArrayList<Book> booksList){
        Log.d(TAG, "updateBooksList: ");
        mBooksAdapter.updateBooksList(booksList);
    }

    /**
     * Callback on book selection from recyclerview grid.
     * Will validate available format for viewing books. Then pass available url callback to mainactivity for VIEW intent
     *
     * @param book selected book model
     */
    @Override
    public void onBookSelected(Book book) {
        if (book != null) {
            mViewModel.validateFormatAndView(book, mOnBookSelectedListener);
        }
    }

    /**
     * Scroll listener for books grid. Used for pagination purpose.
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mViewModel.checkAndLoadMoreBooks(mBooksGridLayoutManager.getChildCount(),
                    mBooksGridLayoutManager.getItemCount(),
                    mBooksGridLayoutManager.findFirstVisibleItemPosition());
        }
    };

    /**
     * edittext action handler
     * @param v edittext view object
     * @param actionId actionId for selected edittext
     * @param event keypress event
     * @return true if criteria satisfied. Will limit callback sending forward in case of true.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!TextUtils.isEmpty(v.getText().toString())) {
                //Hide keyboard on search button click
                v.clearFocus();
                InputMethodManager in = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (in != null) {
                    in.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                // Perform search on user query
                mBooksAdapter.clearBooksList();
                mViewModel.performSearch(v.getText().toString());
                return true;
            }
        }
        return false;
    }

    /**
     * Interface for book selection. Implemented by mainactivity.
     */
    public interface OnBookSelectedListener {
        void onBookSelected(String url);

        void invalidBookMetadata();
    }

    /**
     * Clearing some persisted metadata and observers when we close books grid and go back to Genre selection.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.getBooksMutableLiveData().removeObserver(mBookListUpdateObserver);
        mViewModel.isLoading().removeObserver(mLoadingObserver);
        mViewModel.onError().removeObserver(mErrorObserver);
        mViewModel.clearBooksSelection();
    }
}
