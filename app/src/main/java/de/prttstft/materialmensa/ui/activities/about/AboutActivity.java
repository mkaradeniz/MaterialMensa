package de.prttstft.materialmensa.ui.activities.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Library;
import de.prttstft.materialmensa.ui.activities.about.presenter.AboutPresenter;
import de.prttstft.materialmensa.ui.activities.about.presenter.AboutPresenterImplementation;
import de.prttstft.materialmensa.ui.activities.about.view.AboutView;

public class AboutActivity extends AppCompatActivity implements AboutView {
    @Bind(R.id.activity_about_recycler_view) RecyclerView recyclerView;
    @Bind(R.id.activity_about_toolbar) Toolbar toolbar;
    FastItemAdapter adapter;
    AboutPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new AboutPresenterImplementation(this);

        setUpRecyclerView(recyclerView);
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        adapter = new FastItemAdapter();
        recyclerView.setAdapter(adapter);

        presenter.getLibraries();
    }

    @Override
    public void addLibrary(Library library) {
        //noinspection unchecked
        adapter.add(library);
    }
}
