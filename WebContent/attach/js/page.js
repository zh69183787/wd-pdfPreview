jQuery.myPage = 
{
	
	init : function(options){
		var defaults = {}
		var settings = $.extend({}, defaults, options);
	},
	
	search: function(options){
		var defaults = {}
		var settings = $.extend({}, defaults, options);
	}
}

$.fn.page = function() {
	 var method = arguments[0];

       if($.myPage[method]) {
           method = $.myPage[method];
           // 我们的方法是作为参数传入的，把它从参数列表中删除，因为调用方法时并不需要它
           arguments = Array.prototype.slice.call(arguments, 1);
       } else if( typeof(method) == 'object' || !method ) {
           method = $.myPage.init;
       } else {
           $.error( 'Method ' +  method + ' does not exist on jQuery.pluginName' );
           return this;
       }

       // 用apply方法来调用我们的方法并传入参数
       return method.apply(this, arguments);

}

var options = {
		a:1
		}
$(document).page('init',options);