package de.prttstft.materialmensa.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;

public class Library extends AbstractItem<Library, Library.ViewHolder> {
    public String author;
    public String description;
    public String license;
    public String name;

    public Library() {
    }

    public Library(String author, String description, String license, String name) {
        this.author = author;
        this.description = description;
        this.license = license;
        this.name = name;
    }

    @Override
    public int getType() {
        return R.id.activity_about_library_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_attributions;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);

        viewHolder.text_author.setText(author);
        viewHolder.text_description.setText(description);
        viewHolder.text_license.setText(license);
        viewHolder.text_name.setText(name);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_attributions_text_author) TextView text_author;
        @Bind(R.id.item_attributions_text_description) TextView text_description;
        @Bind(R.id.item_attributions_text_license) TextView text_license;
        @Bind(R.id.item_attributions_text_name) TextView text_name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}