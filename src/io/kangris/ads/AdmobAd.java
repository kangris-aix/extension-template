package io.kangris.ads;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;

import android.app.Activity; 
import android.content.Context; 
import android.view.View; 
import android.view.ViewGroup; 
import android.view.ViewGroup.LayoutParams; 
import com.google.android.gms.ads.AdListener; 
import com.google.android.gms.ads.AdRequest; 
import com.google.android.gms.ads.AdRequest.Builder; 
import com.google.android.gms.ads.AdSize; 
import com.google.android.gms.ads.AdView; 
import com.google.android.gms.ads.InterstitialAd; 
import com.google.android.gms.ads.MobileAds; 
import com.google.android.gms.ads.reward.RewardItem; 
import com.google.android.gms.ads.reward.RewardedVideoAd; 
import com.google.android.gms.ads.reward.RewardedVideoAdListener; 

import com.google.appinventor.components.annotations.*; 
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement; 
import com.google.appinventor.components.common.*; 
import com.google.appinventor.components.runtime.*; 
import com.google.appinventor.components.runtime.util.*; 
 
@UsesLibraries(libraries = "ads.jar, base.jar, customtabs.jar, identifier.jar, basement.jar, gass.jar, lite.jar,  measurement-base.jar, measurement-sdk-api.jar")
@UsesActivities(activities = {@ActivityElement(configChanges = "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize", exported = "false", name = "com.google.android.gms.ads.AdActivity", theme = "@android:style/Theme.Translucent")})
@UsesPermissions(permissionNames = "android.permission.INTERNET, android.permission.ACCESS_NETWORK_STATE, android.permission.ACCESS_COARSE_LOCATION, android.permission.ACCESS_FINE_LOCATION")
@DesignerComponent(category = ComponentCategory.EXTENSION, description = "AdmobAd Ad Extension <br>Set Screen Responsive<br>Work On Min API : 14 <br>by kangris", iconName = "images/extension.png", nonVisible = true, version = 1) 
@SimpleObject(external = true) 
public class AdmobAd extends AndroidNonvisibleComponent { 
    private Activity activity; 
    private AdRequest adRequest; 
    private AdView adView; 
    private Context context; 
    private InterstitialAd mInterstitialAd; 
    private RewardedVideoAd mRewardedVideoAd; 
    private String adUnitId;
    private View view; 
 
    public AdmobAd(ComponentContainer container) { 
        super(container.$form());
        this.context = container.$context();
        this.activity = (Activity) this.context;
    } 
 
    public View getView() { 
        return this.view; 
    } 
 
    @SimpleFunction 
    public void InitializeAppId(String appId) { 
        MobileAds.initialize(this.activity, appId); 
    } 
  
    @SimpleProperty
    public String AdUnitIDBanner() {
        return this.adUnitId;
    }


    @DesignerProperty(defaultValue = "", editorType = "string" )
    @SimpleProperty
    public void AdUnitIDBanner(String adUnitId) {
        this.adUnitId = adUnitId;
        if (!this.adUnitId.isEmpty()) {
            this.adView.setAdUnitId(adUnitId); 
        }
    }
  
  @SimpleProperty
    public String AdUnitIDInterstitial() {
        return this.adUnitId;
    }


    @DesignerProperty(defaultValue = "", editorType = "string" )
    @SimpleProperty
    public void AdUnitIDInterstitial(String adUnitId) {
        this.adUnitId = adUnitId;
        if (!this.adUnitId.isEmpty()) {
            this.mInterstitialAd.setAdUnitId(adUnitId);
        }
    }
  
  @SimpleProperty
    public String AdUnitIDRewardAd() {
        return this.adUnitId;
    }


    @DesignerProperty(defaultValue = "", editorType = "string" )
    @SimpleProperty
    public void AdUnitIDRewardAd(String adUnitId) {
        this.adUnitId = adUnitId;
    }
 
    @SimpleFunction 
    public void LoadBannerAd(AndroidViewComponent container) { 
        this.adView = new AdView(this.activity); 
        this.adView.setAdUnitId(adUnitId); 
        this.adView.setAdSize(AdSize.BANNER); 
        this.adView.setAdListener(new AdListener() { 
            public void onAdLoaded() { 
                AdmobAd.this.BannerAdLoaded(); 
            } 
 
            public void onAdFailedToLoad(int errorCode) { 
                AdmobAd.this.BannerAdFailedToLoad(errorCode); 
            } 
 
            public void onAdOpened() { 
                AdmobAd.this.BannerAdOpened(); 
            } 
 
            public void onAdClicked() { 
                AdmobAd.this.BannerAdClicked(); 
            } 
 
            public void onAdLeftApplication() { 
                AdmobAd.this.BannerAdLeftApplication(); 
            } 
 
            public void onAdClosed() { 
                AdmobAd.this.BannerAdClosed(); 
            } 
        }); 
        this.adRequest = new Builder().build(); 
        ViewGroup viewGroup = (ViewGroup) container.getView(); 
        if (this.adView.getParent() != null) { 
            viewGroup.removeView(this.adView); 
        } 
        viewGroup.addView(this.adView, 0, new LayoutParams(-1, -2)); 
        this.adView.loadAd(this.adRequest); 
    } 
 
    @SimpleFunction 
    public void LoadInterstitialAd() { 
        this.mInterstitialAd = new InterstitialAd(this.activity); 
        this.mInterstitialAd.setAdListener(new AdListener() { 
            public void onAdLoaded() { 
                AdmobAd.this.InterstitialAdLoaded(); 
            } 
 
            public void onAdFailedToLoad(int errorCode) { 
                AdmobAd.this.InterstitialAdFailedToLoad(errorCode); 
            } 
 
            public void onAdOpened() { 
                AdmobAd.this.InterstitialAdOpened(); 
            } 
 
            public void onAdClicked() { 
                AdmobAd.this.InterstitialAdClicked(); 
            } 
 
            public void onAdLeftApplication() { 
                AdmobAd.this.InterstitialAdLeftApplication(); 
            } 
 
            public void onAdClosed() { 
                AdmobAd.this.InterstitialAdClosed(); 
            } 
        }); 
        this.mInterstitialAd.setAdUnitId(adUnitId);
        this.mInterstitialAd.loadAd(new Builder().build()); 
    } 
 
    @SimpleFunction 
    public void ShowInterstitialAd() { 
        this.mInterstitialAd.show(); 
    } 
 
    @SimpleFunction 
    public void LoadRewardedAd() { 
        this.mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.activity); 
        this.mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() { 
            public void onRewarded(RewardItem reward) { 
                AdmobAd.this.RewardedAdOnReward(reward.getType(), reward.getAmount()); 
            } 
 
            public void onRewardedVideoAdLeftApplication() { 
                AdmobAd.this.RewardedAdLeftApplication(); 
            } 
 
            public void onRewardedVideoAdClosed() { 
                AdmobAd.this.RewardedAdClosed(); 
            } 
 
            public void onRewardedVideoAdFailedToLoad(int errorCode) { 
                AdmobAd.this.RewardedAdFailedToLoad(errorCode); 
            } 
 
            public void onRewardedVideoAdLoaded() { 
                AdmobAd.this.RewardedAdLoaded(); 
            } 
 
            public void onRewardedVideoAdOpened() { 
                AdmobAd.this.RewardedAdOpened(); 
            } 
 
            public void onRewardedVideoStarted() { 
                AdmobAd.this.RewardedAdStarted(); 
            } 
 
            public void onRewardedVideoCompleted() { 
                AdmobAd.this.RewardedAdCompleted(); 
            } 
        }); 
        this.mRewardedVideoAd.loadAd(adUnitId, new Builder().build()); 
    } 
 
    @SimpleFunction 
    public void ShowRewardedAd() { 
        this.mRewardedVideoAd.show(); 
    } 
 
    @SimpleEvent 
    public void BannerAdLoaded() { 
        EventDispatcher.dispatchEvent(this, "BannerAdLoaded", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void BannerAdFailedToLoad(int errCode) { 
        EventDispatcher.dispatchEvent(this, "BannerAdFailedToLoad", new Object[]{Integer.valueOf(errCode)}); 
    } 
 
    @SimpleEvent 
    public void BannerAdOpened() { 
        EventDispatcher.dispatchEvent(this, "BannerAdOpened", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void BannerAdClicked() { 
        EventDispatcher.dispatchEvent(this, "BannerAdClicked", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void BannerAdLeftApplication() { 
        EventDispatcher.dispatchEvent(this, "BannerAdLeftApplication", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void BannerAdClosed() { 
        EventDispatcher.dispatchEvent(this, "BannerAdClosed", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdLoaded() { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdLoaded", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdFailedToLoad(int errCode) { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdFailedToLoad", new Object[]{Integer.valueOf(errCode)}); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdOpened() { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdOpened", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdClicked() { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdClicked", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdLeftApplication() { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdLeftApplication", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void InterstitialAdClosed() { 
        EventDispatcher.dispatchEvent(this, "InterstitialAdClosed", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdOnReward(String currency, int amount) { 
        EventDispatcher.dispatchEvent(this, "RewardedAdOnReward", new Object[]{currency, Integer.valueOf(amount)}); 
    } 
 
    @SimpleEvent 
    public void RewardedAdLeftApplication() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdLeftApplication", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdClosed() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdClosed", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdFailedToLoad(int errCode) { 
        EventDispatcher.dispatchEvent(this, "RewardedAdFailedToLoad", new Object[]{Integer.valueOf(errCode)}); 
    } 
 
    @SimpleEvent 
    public void RewardedAdLoaded() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdLoaded", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdOpened() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdOpened", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdStarted() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdStarted", new Object[0]); 
    } 
 
    @SimpleEvent 
    public void RewardedAdCompleted() { 
        EventDispatcher.dispatchEvent(this, "RewardedAdCompleted", new Object[0]); 
    } 
 
} 
