var cordova = require('cordova');
var exec = require('cordova/exec');

var StartAppAds = {
	init: function(appid) {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "initStartApp", [appid]);
	},
	showBanner: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showBanner", []);
	},
	hideBanner: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "hideBanner", []);
	},
	showInterstitial: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showInterstitial", []);
	},
	showRewardVideo: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showRewardVideo", []);
	}
}

module.exports = StartAppAds;
