package de.prttstft.materialmensa.ui.fragments.main.listener;

public interface MainFragmentViewHolderListener {
    void downvoteMeal(int position);

    void onClick(int position);

    void onLongClick(int position);

    void upvoteMeal(int position);
}
