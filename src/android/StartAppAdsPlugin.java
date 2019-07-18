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

  private CallbackContext PUBLIC_CALLBACKS = null;
  private static final String TAG = "StartAppAdsPlugin";
  private StartAppAd startAppAd;
  private CordovaWebView cWebView;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    cWebView = webView;
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    PUBLIC_CALLBACKS = callbackContext;

    if (action.equals("initStartApp")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          initStartApp(PUBLIC_CALLBACKS, args.optString(0));
        }
      });
      return true;
    }
    else if (action.equals("showInterstitial")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showInterstitial(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if(action.equals("showRewardVideo")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showRewardVideo(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    return false;
  }

  public void initStartApp(CallbackContext callbackContext, String appID) {
    Log.d(TAG, "Initializing StartApp SDK with ID: " +  appID);
    startAppAd = new StartAppAd(cordova.getActivity());
    StartAppSDK.init(cordova.getActivity(), appID, true);
    StartAppSDK.setUserConsent(cordova.getActivity(), "pas", System.currentTimeMillis(), false);
  }

  public void showInterstitial(CallbackContext callbackContext) {
    startAppAd.loadAd(new AdEventListener() {
        @Override
        public void onReceiveAd(Ad ad) {
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

        @Override
        public void onFailedToReceiveAd(Ad ad) {
          Log.d(TAG, "Failed to Receive Interstitial!");
        }
    });
  }

  public void showRewardVideo(CallbackContext callbackContext) {
    final StartAppAd rewardedVideo = new StartAppAd(cordova.getActivity());

    rewardedVideo.setVideoListener(new VideoListener() {
      @Override
      public void onVideoCompleted() {
        Log.d(TAG, "Video Reward can be given now!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.reward');");
      }
    });

    rewardedVideo.loadAd(AdMode.REWARDED_VIDEO, new AdEventListener() {
      @Override
      public void onReceiveAd(Ad arg0) {
          Log.d(TAG, "Reward Video loaded!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.load');");
          rewardedVideo.showAd();
      }

      @Override
      public void onFailedToReceiveAd(Ad arg0) {
        Log.d(TAG, "Failed to load Rewarded Video Ad!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.load_fail');");
      }
    });
  }
}
