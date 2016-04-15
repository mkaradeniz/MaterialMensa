package de.prttstft.materialmensa.ui.fragments.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenter;
import de.prttstft.materialmensa.ui.fragments.main.presenter.MainFragmentPresenterImplementation;
import de.prttstft.materialmensa.ui.fragments.main.view.MainFragmentView;

import static de.prttstft.materialmensa.extras.Constants.ARG_PAGE;

public class MainFragment extends Fragment implements MainFragmentView {
    @Bind(R.id.fragment_main_progress_container) RelativeLayout progressBar;
    @Bind(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    private MainFragmentAdapter adapter;
    private MainFragmentPresenter presenter;

    private int page;

    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        this.presenter = new MainFragmentPresenterImplementation(this);

        setUpRecyclerView(recyclerView);

        presenter.onCreate(getArguments().getInt(ARG_PAGE));

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
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}