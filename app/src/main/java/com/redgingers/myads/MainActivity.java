package com.redgingers.myads;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;

import java.util.Map;

public class MainActivity extends BaseActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private String TAG = getClass().getSimpleName();
    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button btnNextLevel;
    private Button btnShowAdsAtLS;
    private Button btnRemoveLS;
    private Button btnShowInMobiAd;
    private Button btnExit;

    private InterstitialAd mInterstitialAd;
    private TextView mLevelTextView;
    private boolean mCanShowAd;
    private InMobiInterstitial inMobiInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the next level button, which tries to show an interstitial when clicked.
        initViews();
        init();
        setCLicks();


        startService(new Intent(this, LockScreenTextService.class));
//        startServices();

        //Google AdMob
        loadInterstitial();
        checkSharedPrefs();
        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
    }

    private void checkSharedPrefs() {
        boolean showAd = sp.getBoolean(Constants.SHOW_ADS, false);
        if (showAd) {
            inMobiInterstitial.load();
        }
    }

    private void startServices() {
        startService(new Intent(this, LockScreenService.class));
    }

    private void initViews() {
        mLevelTextView = (TextView) findViewById(R.id.level);
        btnNextLevel = ((Button) findViewById(R.id.next_level_button));
        btnShowInMobiAd = ((Button) findViewById(R.id.btn_show_in_mobi_ad));
        btnShowAdsAtLS = ((Button) findViewById(R.id.btn_show_ads_at_ls));
        btnRemoveLS = ((Button) findViewById(R.id.btn_remove_ads_from_ls));
        btnExit = ((Button) findViewById(R.id.btn_exit));
    }

    private void setCLicks() {

        btnShowInMobiAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiInterstitial.load();

            }
        });
        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnShowAdsAtLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, LockScreenService.class));

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Constants.SHOW_ADS, true);
                editor.apply();
            }
        });

        btnRemoveLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, LockScreenService.class));

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Constants.SHOW_ADS, false);
                editor.apply();

            }
        });

    }


    private void init() {
        mLevel = START_LEVEL;
        btnNextLevel.setEnabled(false);
        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();

        //create inMobiAd
        inMobiInterstitial = new InMobiInterstitial(this, 1502205407688L, mInterstitialAdListener);
    }

    private void showInMobiAdd() {
        inMobiInterstitial.show();
    }

    private InMobiInterstitial.InterstitialAdListener2 mInterstitialAdListener = new InMobiInterstitial.InterstitialAdListener2() {
        @Override
        public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
            Log.d(TAG, "onAdLoadFinished");
            showInMobiAdd();
        }

        @Override
        public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
            Log.d(TAG, "onAdReceived");
        }

        @Override
        public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
            Log.d(TAG, "Ad can now be shown!");
            mCanShowAd = true;
            showInMobiAdd();
        }

        @Override
        public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

        }

        @Override
        public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
            Log.d(TAG, "onAdDisplayFailed");
        }

        @Override
        public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
            Log.d(TAG, "onAdWillDisplay");
        }

        @Override
        public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
            Log.d(TAG, "onAdDisplayed");
        }

        @Override
        public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

        }

        @Override
        public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
//            finish();
        }

        @Override
        public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                btnNextLevel.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                btnNextLevel.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        btnNextLevel.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView.setText("Level " + (++mLevel));
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }
}
