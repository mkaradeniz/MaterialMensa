package de.prttstft.materialmensa.extensions;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectionRecyclerView<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItemsPositions() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public boolean isSelected(int position) {
        return selectedItems.get(position, false);
    }

    public void clearSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        if (selectedItems.size() > 0) {
            selectedItems.clear();
            notifyDataSetChanged();
        }
    }

    public void setSelection(int position) {
        selectedItems.put(position, true);
        notifyItemChanged(position);
    }
}