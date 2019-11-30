# Cordova Plugin for StartApp Ads
<table>
<tr>
<td>
Cordova Plugin for StartApp Ads. This Cordova Plugin is made to use StartApp Ads with Frameworks like Ionic. You can use Banner Ads, Interstitial Ads and Video Reward Ads. Easy to implement and easy to use. Please read the documentation below!
</tr>
</table>

###  IMPORTANT
<table>
<tr>
<td>
:warning: Only ANDROID is supported at the moment. I'm working hard to implement iOS as well. Stay tuned for future updates!
</td>
</tr>
</table>

## Ionic 3+ Implementation

### 1. Installation
First you need to install the cordova plugin in your Ionic Project with Cordova CLI

```
ionic cordova plugin add cordova-plugin-startapp-ads
```
or with URL
```
ionic cordova plugin add https://github.com/lreiner/cordova-plugin-startapp-ads.git
```

### 2. Setup the Plugin
To start declare the Plugin below your imports:
**NOTE: This must be declared in every Page where you want to use StartAppAds!**
```javascript
import ..... //your imports
declare var StartAppAds:any;
```
Now you need to init your Plugin with your App ID from StartApp first. I would recommend to do it in your **app.component.ts** file when platfrom is ready.
```javascript
this.platform.ready().then(() => {
  if(this.platform.is("android")) {
    StartAppAds.init("<your-android-app-id>");
  }
});
```
Nice! :) Now you can use StartAppAds everywhere in your project.

### 3. Show/Hide Banner Ads
Show a Banner Ad on the bottom of your app. (I recommend to either do this in **app.components.ts** or in the **constructor** of any other page).
```javascript
StartAppAds.showBanner();
```
Hide the Banner
```javascript
StartAppAds.hideBanner();
```
Events you can subscribe to (recommended to put it in the **constructor** of any page):
```javascript
document.addEventListener('startappads.banner.load', () => {
  //banner has been loaded and displayed.
  //do something here
});

document.addEventListener('startappads.banner.load_fail', () => {
  //banner failed to load
  //do something here
  //IMPORTANT: if banner failed to load dont use StartAppAds.showBanner(); again. StartAppAds will load a new one by itself!
});

document.addEventListener('startappads.banner.clicked', () => {
  //banner has been clicked
  //do something here. Usefull to hide banner to prevent click bombing.
});

document.addEventListener('startappads.banner.hide', () => {
  //banner has been removed
  //do something here
});
```
### 4. Show Interstitial Ads
Show a Interstitial Ad:
```javascript
StartAppAds.showInterstitial();
```
Events you can subscribe to (recommended to put it in the **constructor** of any page):
```javascript
document.addEventListener('startappads.interstitial.closed', () => {
  //interstitial closed by user
  //do something here
});

document.addEventListener('startappads.interstitial.displayed', () => {
  //interstitial showed up
  //do something here
});

document.addEventListener('startappads.interstitial.clicked', () => {
  //interstitial clicked by user
  //do something here
});

document.addEventListener('startappads.interstitial.not_displayed', () => {
  //interstitial loaded and ready but somehow not showed to user
  //do something here
});

document.addEventListener('startappads.interstitial.load_fail', () => {
  //interstitial failed to load
  //do something here
});
```
#### IMPORTANT:
```
Do not call `showInterstitial()` from within `load_fail` event. The SDK will automatically try to reload an ad upon a failure.
```

### 5. Show Rewarded Video Ads
Show a Rewarded Video Ad:
```javascript
StartAppAds.showRewardVideo();
```
Events you can subscribe to (recommended to put it in the **constructor** of any page):
```javascript
document.addEventListener('startappads.reward_video.reward', () => {
  //user watched video reward can be given now
  //do something here
});

document.addEventListener('startappads.reward_video.load', () => {
  //reward video finished loading
  //do something here
});

document.addEventListener('startappads.reward_video.closed', () => {
  //user closed video reward 
  //do something here
});

document.addEventListener('startappads.reward_video.clicked', () => {
  //user click on video reward 
  //do something here
});

document.addEventListener('startappads.reward_video.load_fail', () => {
  //reward video failed to load. Probably no Ads available at the moment
  //do something here
});
```

## Donation [![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.me/LukasReiner/) 
If this project help you reduce time to develop, you can give me a cup of coffee (or a Beer of course) :)

[![Support via PayPal](https://cdn.rawgit.com/twolfson/paypal-github-button/1.0.0/dist/button.svg)](https://paypal.me/lreiner97) 

## Git - Fork

```
$ git clone https://github.com/lreiner/cordova-plugin-startapp-ads
```
When you fork a project in order to propose changes to the original repository, you can configure Git to pull changes from the original, or upstream, repository into the local clone of your fork.</br >
[Click here to see how to keep a fork synched](https://help.github.com/articles/fork-a-repo/)

## Releases

Too see all published releases, please take a look at the [tags of this repository](https://github.com/lreiner/cordova-plugin-startapp-ads/tags).
