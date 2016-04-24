package de.prttstft.materialmensa.ui.activities.about;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.BuildConfig;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;

public class AboutActivity extends AppCompatActivity {
    @SuppressWarnings("SpellCheckingInspection") private static final String CHROME_PACKAGE = "com.android.chrome";
    @SuppressWarnings("SpellCheckingInspection") private static final Uri EMAIL = Uri.parse("mailto:prttstft@gmail.com");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri GIT_HUB_REPO_URL = Uri.parse("https://github.com/prttstft/MaterialMensa");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri ICONS_EIGHT_URL = Uri.parse("https://icons8.com/");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri PLAY_STORE_URL = Uri.parse("https://github.com/prttstft/MaterialMensa");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri WISH_LIST_URL = Uri.parse("https://www.amazon.de/registry/wishlist/2RQNQ7DEKD9WF");
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_git_hub) AppCompatButton buttonGitHub;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_icons) AppCompatButton icons;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_libraries) AppCompatButton buttonLibraries;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_rate) AppCompatButton buttonRate;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_wish_list) AppCompatButton buttonWishList;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_credits) TextView credits;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_toolbar) Toolbar toolbar;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_version) TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        version.setText(getString(R.string.activity_about_version, BuildConfig.VERSION_NAME, Utilities.addLeadingZero(BuildConfig.VERSION_CODE, 3)));

        setUpOnClickListeners();
    }

    private void setUpOnClickListeners() {
        buttonGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(GIT_HUB_REPO_URL);
            }
        });

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(PLAY_STORE_URL);
            }
        });

        buttonWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(WISH_LIST_URL);
            }
        });

        buttonLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Status Bar bugged.
                Intent startLibrariesActivityIntent = new LibsBuilder()
                        .withActivityTheme(R.style.AboutLibraries)
                        .withActivityTitle(getString(R.string.activity_about_libraries_title))
                        .withFields(R.string.class.getFields())
                        .withLicenseDialog(true)
                        .withLicenseShown(true)
                        .withLibraries()
                        .intent(AboutActivity.this);
                startActivity(startLibrariesActivityIntent);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO, EMAIL);
                sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.activity_about_email_subject));
                startActivity(sendEmailIntent);
            }
        });

        icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(ICONS_EIGHT_URL);
            }
        });
    }

    private void startCustomTab(Uri url) {
        CustomTabsIntent.Builder startCustomTabIntent = new CustomTabsIntent.Builder();
        startCustomTabIntent.setToolbarColor(ContextCompat.getColor(AboutActivity.this, R.color.colorPrimary));
        startCustomTabIntent.setShowTitle(true);
        startCustomTabIntent.setSecondaryToolbarColor(ContextCompat.getColor(AboutActivity.this, R.color.colorAccent));

        if (chromeInstalled()) {
            startCustomTabIntent.build().intent.setPackage(CHROME_PACKAGE);
        }

        startCustomTabIntent.build().launchUrl(AboutActivity.this, url);
    }

    private boolean chromeInstalled() {
        try {
            getPackageManager().getPackageInfo(CHROME_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}