package de.prttstft.materialmensa.ui.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.activities.details.DetailsActivity;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentViewHolderListener;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenter;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenterImplementation;
import de.prttstft.materialmensa.ui.fragments.main.view.MainFragmentView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static de.prttstft.materialmensa.constants.GeneralConstants.MEAL;

public class MainFragment extends Fragment implements MainFragmentView, MainFragmentViewHolderListener {
    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_RESTAURANT = "ARG_RESTAURANT";
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_empty_container) RelativeLayout empty;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_empty_emoji) ImageView emptyEmoji;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_filtered_container) RelativeLayout filtered;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_filtered_emoji) ImageView filteredEmoji;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_progress_container) RelativeLayout progressBar;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @SuppressWarnings("FieldCanBeLocal") private MainFragmentPresenter presenter;
    private MainFragmentAdapter adapter;


    public static MainFragment newInstance(int page, int restaurant) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_RESTAURANT, restaurant);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        this.presenter = new MainFragmentPresenterImplementation(this);

        setUpRecyclerView(recyclerView);

        emptyEmoji.setImageResource(Utilities.getRandomEmoji());
        filteredEmoji.setImageResource(Utilities.getRandomEmoji());

        presenter.getMeals(getArguments().getInt(ARG_PAGE), getArguments().getInt(ARG_RESTAURANT));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        adapter = new MainFragmentAdapter(getContext(), this);
        adapter.hasStableIds();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent startDetailsActivityIntent = new Intent(getActivity(), DetailsActivity.class);
        startDetailsActivityIntent.putExtra(MEAL, new Gson().toJson(adapter.meals.get(position)));
        startActivity(startDetailsActivityIntent);
    }

    @Override
    public void addMeal(Meal meal) {
        if (adapter != null) {
            adapter.addMeal(meal);
        }
    }

    @Override
    public void onComplete() {
        if (adapter.getItemCount() == 0) {
            showEmpty();
        } else {
            hideEmpty();
            hideFiltered();
        }
        hideProgress();
    }

    @Override
    public void hideEmpty() {
        if (empty != null) {
            empty.setVisibility(GONE);
        }
    }

    @Override
    public void hideFiltered() {
        if (filtered != null) {
            filtered.setVisibility(GONE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void showEmpty() {
        if (empty != null) {
            empty.setVisibility(VISIBLE);
        }
    }

    @Override
    public void showFiltered() {
        if (filtered != null) {
            filtered.setVisibility(VISIBLE);
        }
    }

    @Override
    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
    }
}