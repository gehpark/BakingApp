package com.example.gracepark.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.view.View.GONE;

/**
 * Fragment to hold information about each step of a recipe.
 */

public class RecipeStepFragment extends Fragment {

    private LayoutInflater mInflater;
    private String mMedia;
    private String mText;
    private String mNextButtonText;
    private String mUserAgent;
    private boolean mShowNextButton = true;
    private View mRootView;

    OnNextClickListener mCallback;
    private SimpleExoPlayer mExoPlayer;

    public RecipeStepFragment() {}

    public interface OnNextClickListener {
        void onNextClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnNextClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mRootView = mInflater.inflate(R.layout.fragment_recipe_step, container, false);
        setViews();
        return mRootView;
    }

    public void setViews() {
        if (mRootView == null) { return; }

        SimpleExoPlayerView playerView = (SimpleExoPlayerView) mRootView.findViewById(R.id.player_view);
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mUserAgent = Util.getUserAgent(getActivity().getApplicationContext(), "RecipeStepFragment");
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector, loadControl);
            playerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(true);

        }

        View placeholderMediaView = mRootView.findViewById(R.id.step_media);
        if (mMedia != null && !mMedia.isEmpty()) {
            initializePlayer(Uri.parse(mMedia));
            playerView.setVisibility(View.VISIBLE);
            placeholderMediaView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.GONE);
            placeholderMediaView.setVisibility(View.VISIBLE);
        }
        ((TextView) mRootView.findViewById(R.id.step_text)).setText(mText);
        Button nextButton = ((Button) mRootView.findViewById(R.id.next_step_button));
        if (mShowNextButton) {
            String buttonText = getString(R.string.next_prepend).concat(mNextButtonText);
            nextButton.setText(buttonText);
            nextButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCallback.onNextClicked();
                        }
                    }
            );
        } else {
            nextButton.setVisibility(GONE);
        }
    }

    public void setMedia(String media) {
        mMedia = media;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setNextButtonText(String text) {
        mNextButtonText = text;
    }

    public void hideNextButton() {
        mShowNextButton = false;
    }

    private void initializePlayer(Uri mediaUri) {
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getActivity().getApplicationContext(), mUserAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
