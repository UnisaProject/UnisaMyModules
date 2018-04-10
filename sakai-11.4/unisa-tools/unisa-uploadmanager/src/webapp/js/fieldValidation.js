unisa = {};
unisa.uploadmanager = function($, window, undefined) {
    
	return {
    		fielderrorFn :function(fieldname,errormessage) {
    		var $value = $(":input[name='"+fieldname+"']");
    		
    		$value.css({
    			"background" : "none repeat scroll 0 0 #e8d4d7",
    	        "border" : "1px solid #f60000"
            });
    		
    		$value.focus();
    		$value.attr('title',
    				errormessage).tooltip({
				disabled : false,    position: {
	                  my: "center bottom",
	                  at: "center top-10",
	                  collision: "none"
	               }
			}).tooltip("open");
    		}
    	};	
}(jQuery, window, undefined);