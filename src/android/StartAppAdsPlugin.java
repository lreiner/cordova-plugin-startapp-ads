package com.startapp.cordova.ad;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.app.Activity;
import android.view.View;

import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppAd.AdMode;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class StartAppAdsPlugin extends CordovaPlugin {

  private static final String TAG = "StartAppAdsPlugin";
  private static final String applicationIDAndroid = cordova.getActivity().getIntent().getStringExtra("APP_ID_ANDROID");
  private StartAppAd startAppAd = new StartAppAd(cordova.getActivity());

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    Log.d(TAG, "Initializing StartApp SDK with ID: " + applicationIDAndroid);
    StartAppSDK.init(cordova.getActivity(), applicationIDAndroid, true);
    StartAppSDK.setUserConsent(cordova.getActivity(), "pas", System.currentTimeMillis(), false);
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("showInterstitial")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showInterstitial(callbackContext);
        }
      });
      return true;
    }
    else if(action.equals("showRewardVideo")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showRewardVideo(callbackContext);
        }
      });
      return true;
    }
    return false;
  }

  public void showInterstitial() {
    startAppAd.disbleAutoInterstitial();

    startAppAd.showAd(new AdDisplayListener() {
       @Override
       public void adHidden(Ad ad) {
         Log.d(TAG, "Interstitial has been closed!");
       }

       @Override
       public void adDisplayed(Ad ad) {
         Log.d(TAG, "Interstitial displayed!");
       }

       @Override
       public void adClicked(Ad ad) {
         Log.d(TAG, "Interstitial Ad clicked!");
       }

       @Override
       public void adNotDisplayed(Ad ad) {
         Log.d(TAG, "Interstitial Ad not displayed!");
       }
    });
  }

  public void showRewardVideo(CallbackContext callbackContext) {
    final StartAppAd rewardedVideo = new StartAppAd(cordova.getActivity());

    rewardedVideo.setVideoListener(new VideoListener() {
      @Override
      public void onVideoCompleted() {
        Log.d(TAG, "Videoreward can be given now!");
      }
    });

    rewardedVideo.loadAd(AdMode.REWARDED_VIDEO, new AdEventListener() {
      @Override
      public void onReceiveAd(Ad arg0) {
          rewardedVideo.showAd();
      }

      @Override
      public void onFailedToReceiveAd(Ad arg0) {
        Log.d(TAG, "Failed to load Rewarded Video Ad!");
      }
    });
  }
}
