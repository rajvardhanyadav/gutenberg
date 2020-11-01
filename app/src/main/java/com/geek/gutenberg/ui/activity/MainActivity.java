package com.geek.gutenberg.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.geek.gutenberg.R;
import com.geek.gutenberg.ui.fragment.BooksFragment;
import com.geek.gutenberg.ui.fragment.GenreFragment;

public class MainActivity extends AppCompatActivity implements GenreFragment.OnGenreSelectedListener, BooksFragment.OnBookSelectedListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            addGenreFragment();
        }
    }

    /**
     * Add genre fragment
     */
    private void addGenreFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, GenreFragment.newInstance())
                .commitNow();
    }

    /**
     * Add books fragment
     */
    private void addBooksFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, BooksFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Callback after selecting genre. Will navigate to books listing
     */
    @Override
    public void onGenreSelected() {
        addBooksFragment();
    }

    /**
     * Callback on book selection. Will open url in browser using ACTION_VEW intent
     * @param url url to load book
     */
    @Override
    public void onBookSelected(String url) {
        Log.d(TAG, "onBookSelected: "+url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    /**
     * Callback when specified format book url not available.
     */
    @Override
    public void invalidBookMetadata(){
        Toast.makeText(this,"Unable to view book",Toast.LENGTH_LONG).show();
    }
}
