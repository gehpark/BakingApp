package com.example.gracepark.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.GONE;

/**
 * Created by gracepark on 8/12/17.
 */

public class RecipeStepFragment extends Fragment {

    private LayoutInflater mInflater;
    private String mMedia;
    private String mText;
    private String mNextButtonText;
    private boolean mShowNextButton = true;
    private View mRootView;

    OnNextClickListener mCallback;

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
        if (mRootView == null) {
            return;
        }
        mRootView.findViewById(R.id.step_media);
        ((TextView) mRootView.findViewById(R.id.step_text)).setText(mText);
        Button nextButton = ((Button) mRootView.findViewById(R.id.next_step_button));
        if (mShowNextButton) {
            nextButton.setText(mNextButtonText);
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

}
