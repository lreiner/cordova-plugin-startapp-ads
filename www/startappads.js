var exec = require('cordova/exec');

var StartAppAds = {
	showInterstitial: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showInterstitial", []);
	},
	showRewardVideo: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showRewardVideo", []);
	}
}

module.exports = StartAppAds;
