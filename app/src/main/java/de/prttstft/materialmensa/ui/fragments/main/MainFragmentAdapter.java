package de.prttstft.materialmensa.ui.fragments.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extensions.SelectionRecyclerView;
import de.prttstft.materialmensa.extras.UserSettings;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.extras.UtilitiesKt;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentViewHolderListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainFragmentAdapter extends SelectionRecyclerView<Meal, MainFragmentAdapter.MainFragmentViewHolder> {
    public static final String LOCALE_DE = "Deutsch";

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

        holder.view.setTag(meal);

        setUpFiltered(holder, meal);
        setUpImage(holder, meal);
        setUpMealText(holder, meal);
        setUpSelection(holder, selected);
        setUpSocialData(holder, meal);
        setUpFooter(holder, position);
    }


    private void setUpFiltered(MainFragmentViewHolder holder, Meal meal) {
        if (meal.isFiltered()) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.materialRed900));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
    }

    private void setUpImage(final MainFragmentViewHolder holder, Meal meal) {
        if (UserSettings.getImagesInMainView()) {
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) holder.name.getLayoutParams();
            nameLayoutParams.setMarginStart(Utilities.convertDpToPx(79));

            if (Utilities.onWifi()) {
                Glide.with(context).load(meal.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                //Timber.e(e, e.getMessage());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.image.setVisibility(VISIBLE);
                                return false;
                            }
                        })
                        .into(holder.image);
            } else {
                Glide.with(context).load(meal.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                //Timber.e(e, e.getMessage());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.image.setVisibility(VISIBLE);
                                return false;
                            }
                        })
                        .into(holder.image);
            }
        } else {
            ViewGroup.MarginLayoutParams nameLayoutParams = (ViewGroup.MarginLayoutParams) holder.name.getLayoutParams();
            nameLayoutParams.setMarginStart(Utilities.convertDpToPx(16));

            holder.image.setVisibility(GONE);
        }
    }

    private void setUpMealText(MainFragmentViewHolder holder, Meal meal) {
        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            holder.name.setText(meal.getNameDe());
        } else {
            holder.name.setText(meal.getNameEn());
        }

        holder.description.setText(meal.getCustomDescription());
        holder.price.setText(meal.getPriceString());
    }

    private void setUpSelection(MainFragmentViewHolder holder, Boolean selected) {
        if (selected) {
            holder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
        } else {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            holder.view.setBackgroundResource(backgroundResource);
            typedArray.recycle();
        }
    }

    private void setUpSocialData(MainFragmentViewHolder holder, Meal meal) {
        if (UserSettings.getSocialFeatures()) {
            if (meal.getHasScores()) {
                holder.socialRelativeLayout.setVisibility(VISIBLE);

                if (meal.getScore() > 0) {
                    holder.score.setText(context.getResources().getString(R.string.item_meal_social_positive_score, meal.getScore()));
                } else {
                    holder.score.setText(String.valueOf(meal.getScore()));
                }

                // Some logic to fade out terrible meals.
                /*if (meal.getScore() < 0) {
                    holder.view.setAlpha(meal.getScore() * 0.1F);
                }*/

                if (meal.isDownvoted()) {
                    holder.downvoteArror.setImageAlpha(255);
                    holder.upvoteArrow.setImageAlpha(79);
                } else if (meal.isUpvoted()) {
                    holder.downvoteArror.setImageAlpha(79);
                    holder.upvoteArrow.setImageAlpha(255);
                } else {
                    holder.downvoteArror.setImageAlpha(79);
                    holder.upvoteArrow.setImageAlpha(79);
                }
            } else {
                holder.socialRelativeLayout.setVisibility(GONE);
            }
        } else {
            holder.socialRelativeLayout.setVisibility(GONE);
        }
    }

    private void setUpFooter(MainFragmentViewHolder holder, int position) {
        if (position == meals.size() - 1) {
            holder.footer.setVisibility(VISIBLE);
        } else {
            holder.footer.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


    public void addMeal(Meal meal) {
        int index = getIndex(meal);

        if (index == -1) {
            meals.add(meal);
        } else {
            meals.set(index, meal);
        }

        sortMeals();
    }

    public void setSocialData(Meal meal) {
        int index = getIndex(meal);

        if (index != -1) {
            meals.get(index).setScore(meal.getScore());

            if (meal.isDownvoted()) {
                meals.get(index).setDownvoted(true);
                meals.get(index).setUpvoted(false);
            } else if (meal.isUpvoted()) {
                meals.get(index).setDownvoted(false);
                meals.get(index).setUpvoted(true);
            } else {
                meals.get(index).setDownvoted(false);
                meals.get(index).setUpvoted(false);
            }

            sortMeals();
        }
    }


    private int getIndex(Meal meal) {
        for (int i = 0; i < meals.size(); i++) {
            if (Objects.equals(meals.get(i).getNameEn(), meal.getNameEn())) {
                return i;
            }
        }

        return -1;
    }

    private void sortMeals() {
        meals = UtilitiesKt.sortMeals(meals);

        notifyDataSetChanged();
    }


    public class MainFragmentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_meal_description) TextView description;
        @Bind(R.id.item_meal_footer) RelativeLayout footer;
        @Bind(R.id.item_meal_image) ImageView image;
        @Bind(R.id.item_meal_meal_container) RelativeLayout mealRelativeLayout;
        @Bind(R.id.item_meal_social_container) RelativeLayout socialRelativeLayout;
        @Bind(R.id.item_meal_name) TextView name;
        @Bind(R.id.item_meal_price) TextView price;
        @Bind(R.id.item_meal_social_arrow_down) ImageView downvoteArror;
        @Bind(R.id.item_meal_social_arrow_up) ImageView upvoteArrow;
        @Bind(R.id.item_meal_social_score) TextView score;
        private View view;


        public MainFragmentViewHolder(View itemView, final MainFragmentViewHolderListener listener) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);

            mealRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), image);

                }
            });

            mealRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(getAdapterPosition());
                    return true;
                }
            });

            downvoteArror.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.downvoteMeal(getAdapterPosition());
                }
            });

            upvoteArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.upvoteMeal(getAdapterPosition());
                }
            });
        }
    }
}