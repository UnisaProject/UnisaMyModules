	function sendRequestObject(urls, paramString)
	{
	var xmlhttp;
	//var keys=document.dummy.sele.value
	//var urls="http://www.java4s.com:2011/On_select_from_database_dropdown_Update_2/db_fetch.jsp?ok="+keys
	if (window.XMLHttpRequest)
	  {
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	 
	xmlhttp.onreadystatechange=function()
	  {
	 
	removeall();
	 
	  if (xmlhttp.readyState==4)
	    {
	            z=0;
	            var roott=xmlhttp.responseXML.documentElement;
	            var ress=roott.getElementsByTagName("option")[z].childNodes[0].nodeValue;
	 
	            while(ress!=null)
	            {
	                    addoptions(ress)
	                    z++
	             var ress=roott.getElementsByTagName("option")[z].childNodes[0].nodeValue;
	            }
	    }
	 
	    function removeall()
	    {
	 
	        var ct=document.getElementById('postalcode').length;
	        for(i=ct; i>=0; i--)    {    
	        	document.getElementById('postalcode').options[i]=null;
	             }
	    }
	 
	    function addoptions(reslt)
	    {
	 
	        var ct1=document.createElement("OPTION");
	        ct1.text=reslt;
	        ct1.value=reslt;
	        document.getElementById('postalcode').options.add(ct1);
	 
	    }
	}    
	 
	xmlhttp.open("POST",urls,true);
	xmlhttp.send(paramString);
	}	