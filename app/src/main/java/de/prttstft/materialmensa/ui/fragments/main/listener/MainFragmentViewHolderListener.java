package de.prttstft.materialmensa.ui.fragments.main.listener;

import android.widget.ImageView;

public interface MainFragmentViewHolderListener {
    void downvoteMeal(int position);

    void onClick(int adapterPosition, ImageView image);

    void onLongClick(int position);

    void upvoteMeal(int position);
}
