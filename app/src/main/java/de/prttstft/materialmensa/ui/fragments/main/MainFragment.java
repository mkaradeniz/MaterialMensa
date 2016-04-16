package de.prttstft.materialmensa.ui.fragments.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenter;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenterImplementation;
import de.prttstft.materialmensa.ui.fragments.main.view.MainFragmentView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static de.prttstft.materialmensa.extras.Constants.ARG_PAGE;
import static de.prttstft.materialmensa.extras.Constants.ARG_RESTAURANT;

public class MainFragment extends Fragment implements MainFragmentView {
    @Bind(R.id.fragment_main_progress_container) RelativeLayout progressBar;
    @Bind(R.id.fragment_main_empty_container) RelativeLayout empty;
    @Bind(R.id.fragment_main_empty_emoji) ImageView emptyEmoji;
    @Bind(R.id.fragment_main_filtered_container) RelativeLayout filtered;
    @Bind(R.id.fragment_main_filtered_emoji) ImageView filteredEmoji;
    @Bind(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    private MainFragmentAdapter adapter;
    @SuppressWarnings("FieldCanBeLocal") private MainFragmentPresenter presenter;

    public static MainFragment newInstance(int page, int restaurant) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_RESTAURANT, restaurant);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        adapter = new MainFragmentAdapter(getContext());
        adapter.hasStableIds();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addMeal(Meal meal) {
        adapter.addMeal(meal);
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
        empty.setVisibility(GONE);
    }

    @Override
    public void hideFiltered() {
        filtered.setVisibility(GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void showEmpty() {
        empty.setVisibility(VISIBLE);
    }

    @Override
    public void showFiltered() {
        filtered.setVisibility(VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }
}