package de.prttstft.materialmensa.ui.fragments.main.listener;

import android.view.View;

public interface MainFragmentViewHolderListener {
    void onClick(int position);

    void onLongClick(View view, int position);

    void finishActionMode();
}
