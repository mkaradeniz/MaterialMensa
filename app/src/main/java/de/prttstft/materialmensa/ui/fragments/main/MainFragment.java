package de.prttstft.materialmensa.ui.fragments.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.DateTimeUtilities;
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
import static de.prttstft.materialmensa.extras.RestaurantUtilites.getRandomEmoji;

public class MainFragment extends Fragment implements MainFragmentView, MainFragmentViewHolderListener {
    private static final String ARG_DAY = "ARG_DAY";
    private static final String ARG_RESTAURANT = "ARG_RESTAURANT";
    private static final String LOCALE_DE = "Deutsch";
    private static final String MIME_TYPE_TEXT = "text/plain";
    private static final String PLAY_STORE_URL = "https://goo.gl/HD2ed2";
    public static ActionMode actionMode;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_empty_container) RelativeLayout empty;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_empty_emoji) ImageView emptyEmoji;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_filtered_container) RelativeLayout filtered;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_filtered_emoji) ImageView filteredEmoji;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_offline_container) RelativeLayout connectionError;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_offline_emoji) ImageView connectionErrorEmoji;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_progress_container) RelativeLayout progressBar;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @SuppressWarnings("FieldCanBeLocal") private MainFragmentPresenter presenter;
    private ActionMode.Callback actionModeCallback;
    private MainFragmentAdapter adapter;

    public static MainFragment newInstance(int day, int restaurant) {
        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);
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
        setUpActionMode();

        emptyEmoji.setImageResource(getRandomEmoji());
        filteredEmoji.setImageResource(getRandomEmoji());

        presenter.getMeals(getArguments().getInt(ARG_DAY), getArguments().getInt(ARG_RESTAURANT));

        return view;
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.hasFixedSize();

        adapter = new MainFragmentAdapter(getContext(), this);
        adapter.hasStableIds();
        recyclerView.setAdapter(adapter);
    }

    private void setUpActionMode() {
        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_main, menu);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.about_libraries_primary_dark));
                }

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menu_main_share) {
                    shareMenu();
                    actionMode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.clearSelections();
                actionMode = null;
            }
        };
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void downvoteMeal(int position) {
        presenter.downvoteMeal(adapter.meals.get(position));
    }

    @Override
    public void onClick(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {
            Intent startDetailsActivityIntent = new Intent(getActivity(), DetailsActivity.class);
            startDetailsActivityIntent.putExtra(MEAL, new Gson().toJson(adapter.meals.get(position)));
            startActivity(startDetailsActivityIntent);
        }
    }

    @Override
    public void onLongClick(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    @Override
    public void upvoteMeal(int position) {
        presenter.upvoteMeal(adapter.meals.get(position));
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
            hideConnectionError();
        }
        hideProgress();

        presenter.getSocialData(adapter.meals);
    }

    @Override
    public void hideConnectionError() {
        if (connectionError != null) {
            connectionError.setVisibility(GONE);
        }
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
    public void showConnectionError() {
        if (connectionError != null) {
            connectionError.setVisibility(VISIBLE);
        }
        hideEmpty();
        hideFiltered();
        hideProgress();
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

    @Override
    public void updateMealWithScore(Meal meal) {
        adapter.updateMealWithScore(meal);
    }

    @Override
    public void updateMealWithVote(Meal meal) {
        adapter.updateMealWithVote(meal);
    }


    private void toggleSelection(int position) {
        if (adapter.isSelected(position)) {
            adapter.clearSelection(position);
            adapter.notifyItemChanged(position);

            if (adapter.getSelectedItemCount() == 0) {
                actionMode.finish();
            } else {
                actionMode.setTitle(adapter.getSelectedItemCount() + "");
            }
        } else {
            adapter.setSelection(position);
            adapter.notifyItemChanged(position);

            actionMode.setTitle(adapter.getSelectedItemCount() + "");
        }
    }

    private void shareMenu() {
        ShareCompat.IntentBuilder
                .from(getActivity())
                .setType(MIME_TYPE_TEXT)
                .setText(buildShareString())
                .setChooserTitle(R.string.share_chooser_title)
                .startChooser();
    }

    private String buildShareString() {
        String shareString = getActivity().getString(R.string.share_string_prefix, DateTimeUtilities.getShareDayString(getArguments().getInt(ARG_DAY)));

        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            shareString = shareString + adapter.meals.get(adapter.getSelectedItemsPositions().get(0)).getNameDe();
        } else {
            shareString = shareString + adapter.meals.get(adapter.getSelectedItemsPositions().get(0)).getNameEn();
        }

        if (adapter.getSelectedItemCount() > 1) {
            for (int i = 1; i < adapter.getSelectedItemsPositions().size(); i++) {
                if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
                    shareString = shareString + ",\n" + adapter.meals.get(adapter.getSelectedItemsPositions().get(i)).getNameDe();
                } else {
                    shareString = shareString + ",\n" + adapter.meals.get(adapter.getSelectedItemsPositions().get(i)).getNameEn();
                }
            }
        }

        shareString = shareString + getActivity().getString(R.string.share_string_suffix, PLAY_STORE_URL);

        return shareString;
    }
}