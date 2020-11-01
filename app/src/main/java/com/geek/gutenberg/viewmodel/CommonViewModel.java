package com.geek.gutenberg.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.geek.gutenberg.model.Book;
import com.geek.gutenberg.model.Genre;
import com.geek.gutenberg.model.ResponseBooks;
import com.geek.gutenberg.network.base.ServiceController;
import com.geek.gutenberg.network.utils.NetworkConstants;
import com.geek.gutenberg.ui.fragment.BooksFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonViewModel extends AndroidViewModel {
    private static final String TAG = "CommonViewModel";
    private MutableLiveData<ArrayList<Genre>> genreLiveData;
    private MutableLiveData<ArrayList<Book>> booksLiveData;
    private ServiceController mServiceController;
    private String mSelectedGenre;
    private Map<String, String> queryMap;
    private String NEXT_PAGE_NUMBER;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<String> mOnError;

    public CommonViewModel(@NonNull Application application) {
        super(application);
        genreLiveData = new MutableLiveData<>();
        booksLiveData = new MutableLiveData<>();
        mIsLoading = new MutableLiveData<>();
        mOnError = new MutableLiveData<>();
        queryMap = new HashMap<>();
        mServiceController = ServiceController.getInstance(application); // Intialize service controller for data source
    }

    /**
     * Livedata for Genre
     *
     * @return return latest Genre observer
     */
    public MutableLiveData<ArrayList<Genre>> getGenreMutableLiveData() {
        return genreLiveData;
    }

    /**
     * Livedata for Books
     *
     * @return return latest Books observer
     */
    public MutableLiveData<ArrayList<Book>> getBooksMutableLiveData() {
        return booksLiveData;
    }

    /**
     * Livedata for loading state
     *
     * @return return loading state observer
     */
    public MutableLiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    /**
     * Livedata for loading state
     *
     * @return return loading state observer
     */
    public MutableLiveData<String> onError() {
        return mOnError;
    }

    /**
     * Return selected Genre
     *
     * @return selected Genre
     */
    public String getSelectedGenre() {
        return mSelectedGenre;
    }

    /**
     * Fetch Genre list from data source
     */
    public void getGenre() {
        if (mServiceController != null)
            genreLiveData.setValue(mServiceController.getGenreService().getGenre());
    }

    /**
     * Fetch books list from data source
     */
    public void getBooks() {
        if (mServiceController != null) {
            queryBooksSearch();
        }
    }

    /**
     * Retrofit call to fetch list of books based on query param. Set results in Livedata
     */
    private void queryBooksSearch() {
        mIsLoading.setValue(true);
        ArrayList<Book> booksList = new ArrayList<>();
        Call<ResponseBooks> call = mServiceController.getGutenbergService().getBooks(getQueryMap());
        call.enqueue(new Callback<ResponseBooks>() {
            @Override
            public void onResponse(Call<ResponseBooks> call, Response<ResponseBooks> response) {
                Log.d(TAG, "onResponse:");
                ResponseBooks responseBooks = response.body();
                if (responseBooks != null) {
                    Log.d(TAG, "onResponse: total books " + responseBooks.getCount());
                    Log.d(TAG, "onResponse: total books in this page " + responseBooks.getResults().size());
                    persistPageNumber(responseBooks);
                    booksList.addAll(responseBooks.getResults());
                    booksLiveData.setValue(booksList);
                }
                mIsLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<ResponseBooks> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                mIsLoading.setValue(false);
                mOnError.setValue(t.getMessage());
            }
        });
    }

    /**
     * Set selected Genre
     *
     * @param selectedGenre selected Genre
     */
    public void setSelectedGenre(String selectedGenre) {
        Log.d(TAG, "setSelectedGenre: " + selectedGenre);
        mSelectedGenre = selectedGenre;
        updateQueryMap(null);
    }

    /**
     * Update query map used to fetch list of books
     *
     * @param externalQueryMap map of additional queries. Will add those along with the default one
     */
    private void updateQueryMap(Map<String, String> externalQueryMap) {
        //Following two query params are default one
        queryMap.put(NetworkConstants.QueryParams.PARAM_MIME_TYPE, "image"); //We need books for which image covers are present. So added this MIME_TYPE
        queryMap.put(NetworkConstants.QueryParams.PARAM_TOPIC, mSelectedGenre); //Selected Genre

        if (externalQueryMap != null) {
            queryMap.putAll(externalQueryMap);
        }
    }

    /**
     * Return query map to execute retrofit api call
     *
     * @return map of queries for books selection
     */
    private Map<String, String> getQueryMap() {
        String urlQuery = "";
        for (String key : queryMap.keySet()) {
            urlQuery = urlQuery.concat(key).concat("=").concat(Objects.requireNonNull(queryMap.get(key))).concat("&");
        }
        Log.d(TAG, "getQueryMap: " + urlQuery);
        return queryMap;
    }

    /**
     * Clear metadata for books listing
     */
    public void clearBooksSelection() {
        Log.d(TAG, "clearBooksSelection: ");
        booksLiveData.postValue(null);
        onError().postValue(null);
        queryMap.clear();
    }

    /**
     * Perform search for books on user entered query
     *
     * @param query user entered search phrase
     */
    public void performSearch(String query) {
        Log.d(TAG, "performSearch: search query " + query);
        queryMap.clear();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(NetworkConstants.QueryParams.PARAM_SEARCH, query);
        updateQueryMap(queryMap);
        queryBooksSearch();
    }

    /**
     * Check for pagination event to load more books on scrolling.
     * Following params used to check user scrolled to last book in grid. If more books available then trigger api with next page number.
     *
     * @param visibleItemCount         total visible book cards on screen
     * @param totalItemCount           total books in list
     * @param firstVisibleItemPosition index of first visible book in grid
     */
    public void checkAndLoadMoreBooks(int visibleItemCount, int totalItemCount, int firstVisibleItemPosition) {
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                firstVisibleItemPosition >= 0 &&
                !isLoading().getValue()) {
            Log.d(TAG, "loadMoreBooks: page number " + NEXT_PAGE_NUMBER);
            if (!TextUtils.isEmpty(NEXT_PAGE_NUMBER)) {
                Map<String, String> queryMap = new HashMap<>();
                queryMap.put(NetworkConstants.QueryParams.PARAM_PAGE, NEXT_PAGE_NUMBER);
                updateQueryMap(queryMap);
                queryBooksSearch();
            }
        }
    }

    /**
     * persist next page number from response for pagination.
     *
     * @param responseBooks api response object for list of books.
     */
    private void persistPageNumber(ResponseBooks responseBooks) {
        if (!TextUtils.isEmpty(responseBooks.getNext())) {
            NEXT_PAGE_NUMBER = Uri.parse(responseBooks.getNext()).getQueryParameter(NetworkConstants.QueryParams.PARAM_PAGE);
            Log.d(TAG, "persistPageNumber: " + NEXT_PAGE_NUMBER);
        } else {
            NEXT_PAGE_NUMBER = null;
            Log.d(TAG, "persistPageNumber: next page not available");
        }
    }

    public void validateFormatAndView(Book book, BooksFragment.OnBookSelectedListener onBookSelectedListener) {
        if (!TextUtils.isEmpty(book.getFormats().getTextHtmlCharsetUtf8()) && isValidFormat(book.getFormats().getTextHtmlCharsetUtf8()))
            onBookSelectedListener.onBookSelected(book.getFormats().getTextHtmlCharsetUtf8());
        else if (!TextUtils.isEmpty(book.getFormats().getApplicationPdf()) && isValidFormat(book.getFormats().getApplicationPdf()))
            onBookSelectedListener.onBookSelected(book.getFormats().getApplicationPdf());
        else if (!TextUtils.isEmpty(book.getFormats().getTextPlainCharsetUtf8()) && isValidFormat(book.getFormats().getTextPlainCharsetUtf8()))
            onBookSelectedListener.onBookSelected(book.getFormats().getTextPlainCharsetUtf8());
        else if (!TextUtils.isEmpty(book.getFormats().getTextPlainCharsetUsAscii()) && isValidFormat(book.getFormats().getTextPlainCharsetUsAscii()))
            onBookSelectedListener.onBookSelected(book.getFormats().getTextPlainCharsetUsAscii());
        else
            onBookSelectedListener.invalidBookMetadata();
    }

    private boolean isValidFormat(String url) {
        for (String invalidFormat : NetworkConstants.BookFormats.INVALID_FORMATS) {
            if (url.contains(invalidFormat)) {
                return false;
            }
        }
        return true;
    }
}
