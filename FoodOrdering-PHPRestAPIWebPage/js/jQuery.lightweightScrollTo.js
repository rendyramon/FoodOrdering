/**
 * Script implements the parallax scrolling of the site, which is in this case
 * a simple animated scroll to.
 *
 * @param {object} options Options
 *		- {number} duration Animation time in milliseconds
 *		- {number} offset Offset in pixel
 *		- {boolean}  updateUrl If true, the hash of the URL gets updated
 */
jQuery.fn.lightweightScrollTo = function(options) {

	options = jQuery.extend({
		offset: 0,
		duration: 1000,
		updateUrl: true
	}, options);

	this.on('click', function(event) {
		console.log('click');
		var anchor = jQuery(this).attr('href');
		var target = jQuery(anchor);
		if (target.length) {
			event.preventDefault();
			jQuery('html, body').animate({
				scrollTop: target.offset().top - options.offset
			}, options.duration, function() {
				if (options.updateUrl) {
					window.location.hash = anchor;					
				}
			});
		}
	});	
};