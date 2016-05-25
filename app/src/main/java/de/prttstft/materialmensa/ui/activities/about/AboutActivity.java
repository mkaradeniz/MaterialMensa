package de.prttstft.materialmensa.ui.activities.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
    @SuppressWarnings("SpellCheckingInspection") private static final Uri API_URL = Uri.parse("http://www.studentenwerk-pb.de/");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri EMAIL = Uri.parse("mailto:prttstft.dev@gmail.com");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri EMOJI_ONE_URL = Uri.parse("http://emojione.com/");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri GIT_HUB_REPO_URL = Uri.parse("https://github.com/prttstft/MaterialMensa");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri ICONS_EIGHT_URL = Uri.parse("https://icons8.com/");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri PLAY_STORE_URL = Uri.parse("https://goo.gl/HD2ed2");
    @SuppressWarnings("SpellCheckingInspection") private static final Uri WISH_LIST_URL = Uri.parse("https://www.amazon.de/registry/wishlist/2RQNQ7DEKD9WF");
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_api) AppCompatButton buttonApi;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_emojis) AppCompatButton buttonEmojis;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_git_hub) AppCompatButton buttonGitHub;
    @SuppressWarnings({"WeakerAccess", "unused"}) @Bind(R.id.activity_about_button_icons) AppCompatButton buttonIcons;
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
        buttonApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(API_URL);
            }
        });

        buttonEmojis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(EMOJI_ONE_URL);
            }
        });

        buttonGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(GIT_HUB_REPO_URL);
            }
        });

        buttonIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomTab(ICONS_EIGHT_URL);
            }
        });

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
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

    private void rateApp() {
        try {
            Intent rateIntent = startPlayStoreIntent();
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            startCustomTab(PLAY_STORE_URL);
        }
    }

    private Intent startPlayStoreIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", "market://details", getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
}