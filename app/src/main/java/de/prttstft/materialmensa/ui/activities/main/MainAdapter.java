package de.prttstft.materialmensa.ui.activities.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private Context context;
    private List<Meal> meals = new ArrayList<>();

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_meal, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Meal meal = meals.get(position);

        Glide.with(context).load(meal.getThumbnail()).into(holder.picture);
        holder.text_primary.setText(meal.getNameEn());
        holder.text_secondary.setText(meal.getPriceString(""));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        notifyDataSetChanged();

        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal meal1, Meal meal2) {
                return (meal1.getOrderInfo() - meal2.getOrderInfo()); // Descending
            }
        });

        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_restaurant_icon) ImageView picture;
        @Bind(R.id.item_restaurant_meal_text_primary) TextView text_primary;
        @Bind(R.id.item_restaurant_meal_text_secondary) TextView text_secondary;
        private View view;

        public MainViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}