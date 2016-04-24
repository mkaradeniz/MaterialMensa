package de.prttstft.materialmensa.ui.fragments.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentViewHolderListener;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder> {
    public List<Meal> meals = new ArrayList<>();
    private MainFragmentViewHolderListener listener;

    public MainFragmentAdapter(MainFragmentViewHolderListener listener) {
        this.listener = listener;
    }

    @Override
    public MainFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MainFragmentViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(MainFragmentViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.description.setText(meal.getCustomDescription());
        holder.name.setText(meal.getNameEn());
        holder.price.setText(meal.getPriceString());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);

        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal meal1, Meal meal2) {
                return (meal1.getOrderNumber() - meal2.getOrderNumber());
            }
        });

        notifyDataSetChanged();
    }

    public class MainFragmentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_details_description) TextView description;
        @Bind(R.id.item_meal_name) TextView name;
        @Bind(R.id.item_meal_price) TextView price;
        private View view;

        public MainFragmentViewHolder(View itemView, final MainFragmentViewHolderListener listener) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}