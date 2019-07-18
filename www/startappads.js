var exec = require('cordova/exec');

var StartAppAds = {
	init: function(appid) {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "initStartApp", [appid]);
	},
	showInterstitial: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showInterstitial", []);
	},
	showRewardVideo: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showRewardVideo", []);
	}
}

module.exports = StartAppAds;
