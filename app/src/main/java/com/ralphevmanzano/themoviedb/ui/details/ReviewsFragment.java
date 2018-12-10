package com.ralphevmanzano.themoviedb.ui.details;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.databinding.FragmentReviewsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.viewmodels.ReviewsViewModel;

public class ReviewsFragment extends BaseFragment<ReviewsViewModel, FragmentReviewsBinding> {
    @Override
    public Class<ReviewsViewModel> getViewModel() {
        return ReviewsViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_reviews;
    }
}
