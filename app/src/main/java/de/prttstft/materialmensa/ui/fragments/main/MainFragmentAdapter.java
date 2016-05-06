package de.prttstft.materialmensa.ui.fragments.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extensions.SelectionRecyclerView;
import de.prttstft.materialmensa.extras.UserSettings;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentViewHolderListener;

import static android.view.View.GONE;

public class MainFragmentAdapter extends SelectionRecyclerView<Meal, MainFragmentAdapter.MainFragmentViewHolder> {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MainFragmentViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final MainFragmentViewHolder holder, int position) {
        Meal meal = meals.get(position);
        Boolean selected = isSelected(position);

        holder.description.setText(meal.getCustomDescription());
        holder.price.setText(meal.getPriceString());

        if (selected) {
            holder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
        } else {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            holder.view.setBackgroundResource(backgroundResource);
            typedArray.recycle();
        }

        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            holder.name.setText(meal.getNameDe());
        } else {
            holder.name.setText(meal.getNameEn());
        }

        if (meal.isFiltered()) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.materialDeepOrange500));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        if (UserSettings.getImagesInMainView()) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.name.getLayoutParams();
            params.setMarginStart(Utilities.convertToPx(58));

            if (meal.getImage() != null) {
                if (Utilities.onWifi()) {
                    Glide.with(context).load(meal.getImage())
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.image.setVisibility(View.VISIBLE);
                                    return false;
                                }
                            })
                            .into(holder.image);
                } else {
                    Glide.with(context).load(meal.getThumbnail())
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.image.setVisibility(View.VISIBLE);
                                    return false;
                                }
                            })
                            .into(holder.image);
                }
            } else {
                holder.image.setVisibility(GONE);
            }
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.name.getLayoutParams();
            params.setMarginStart(Utilities.convertToPx(16));

            holder.image.setVisibility(GONE);
        }
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
        @Bind(R.id.item_meal_description) TextView description;
        @Bind(R.id.item_meal_image) ImageView image;
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

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}