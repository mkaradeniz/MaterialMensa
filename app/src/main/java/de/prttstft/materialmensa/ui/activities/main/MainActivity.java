package de.prttstft.materialmensa.ui.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenter;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenterImplementation;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {
    @Bind(R.id.activity_main_progress_container) RelativeLayout progressBar;
    @Bind(R.id.activity_main_recycler_view) RecyclerView recyclerView;
    @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    private MainAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.presenter = new MainPresenterImplementation(this);

        setUpRecyclerView(recyclerView);

        presenter.onCreate();
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        adapter = new MainAdapter(this);
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
