package de.prttstft.materialmensa.ui.fragments.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentViewHolderListener;

import static android.view.View.GONE;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder> {
    private static final String LOCALE_DE = "Deutsch";
    public List<Meal> meals = new ArrayList<>();
    private MainFragmentViewHolderListener listener;
    private Context context;

    public MainFragmentAdapter(Context context, MainFragmentViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MainFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_new, parent, false);
        return new MainFragmentViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final MainFragmentViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.description.setText(meal.getCustomDescription());
        holder.price.setText(meal.getPriceString());

        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            holder.name.setText(meal.getNameDe());
        } else {
            holder.name.setText(meal.getNameEn());
        }

        if (meal.isFiltered()) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.materialDeepOrange500));
        }

        Glide.with(context).load(meal.getThumbnail())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(GONE);
                        holder.icon_container.setVisibility(GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(GONE);
                        return false;
                    }
                })
                .into(holder.image);
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
        @Bind(R.id.item_meal_icon_container) FrameLayout icon_container;
        @Bind(R.id.item_meal_description) TextView description;
        @Bind(R.id.item_meal_icon) CircleImageView image;
        @Bind(R.id.item_meal_name) TextView name;
        @Bind(R.id.item_meal_price) TextView price;
        @Bind(R.id.item_meal_icon_progress_bar) ProgressBar progressBar;

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