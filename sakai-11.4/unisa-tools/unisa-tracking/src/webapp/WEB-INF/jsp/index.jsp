<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>
<sakai:html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content='initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Assignment Tracking</title>
	
	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.min.css" />" rel="stylesheet"  type="text/css" />

    <!-- CSS code from Bootply.com editor -->
    <style type="text/css">
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		
		.panel {
	        /*height : 300px;*/
	        margin-bottom:0;
	        position: relative;
	    }
	
	    .panel .panel-footer {
	        position : absolute;
	        bottom : 0;
	        margin-bottom:1px;
	        width : 100%;
	    }
	</style>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

 	<script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script> 
 	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" />"></script> 

 	<script type="text/javascript">

	 	$(document).ready(function(){
	 		$.removeCookie("inputNum");
	 		$.removeCookie("input1");
	 		$.removeCookie("imageConPlus");
	 		$.removeCookie("imageDctPlus");
	 		$.removeCookie("imageAssPlus");
	 		$.removeCookie("inputNumOut");
	 		$.removeCookie("inputOut1");
	 		$.removeCookie("imageAdrPlusOut");
	 		$.removeCookie("imageConPlusOut");
	 		$.removeCookie("imageDctPlusOut");
	 		$.removeCookie("imageAssPlusOut");
	 	});
	 	
	    function setAction(action) {
	    	var check = action;
	    	if(check == "in"){
	    		//alert("Action: "+check);
	    		document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=in';
	    	}else if(check == "out"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=out';
	    	}else if(check == "search"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=search';
	    	}else if(check == "report"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=report';
	    	}
			document.trackingForm.submit();
		}
	</script>
    
</head>

 	<noscript>
	    <style type="text/css">
	        .container {display:none;}
	    </style>
	    <div class="noscriptmsg">
	    	Your browser does not support JavaScript or it is disabled. Please enable JavaScript in order to continue.<br/>
	    	Here are the <a href="http://www.enable-javascript.com" target="_blank"> instructions how to enable JavaScript in your web browser</a>
	    </div>
	</noscript>
	
	<html:form action="/tracking">

	<p>
		<sakai:messages/>
		<sakai:messages message="true"/>
	</p>

     <div class="container">
       <br style="height:5px;">
        <div class="row equal">
	        <div class='col-xs-6'>
	         	<div class="panel panel-default">
	         		<div class="panel-heading">
	         			<h3 class="panel-title text-center"><fmt:message key="bookin.header"/></h3>
		          	</div>
					<div class="panel-body">
						<fmt:message key="bookin.info"/>
	            		<br/>&nbsp;<br/>
	            		<br/>&nbsp;<br/>
	            	</div>
	            	<div class="panel-footer clearfix">
						<button class="btn btn-default btn-block" type="button" onclick="setAction('in');"><fmt:message key="button.indexin" /></button>
					</div>
	          	</div>
	        </div><!-- /.col-xs-6 -->
        
        
	        <div class='col-xs-6'>
	          	<div class="panel panel-default">
		          	<div class="panel-heading">
		          		<h3 class="panel-title text-center"><fmt:message key="bookout.header"/></h3>
		          	</div>
					<div class="panel-body">
						<fmt:message key="bookout.info"/>
						<br/>&nbsp;<br/>
						<br/>&nbsp;<br/>
					</div>
					<div class="panel-footer clearfix">
						<button class="btn btn-default btn-block" type="button" onclick="setAction('out');"><fmt:message key="button.indexout" /></button>
					</div> 
	            </div>
	        </div><!-- /.col-xs-6 -->
	  	</div>
		<br/>
		<!--  <div class="row equal">
				<div class='col-xs-6'>
		          	<div class="panel panel-default">
			          	<div class="panel-heading">
			          		<h3 class="panel-title text-center"><fmt:message key="search.header"/></h3>
			          	</div>
						<div class="panel-body">
							<fmt:message key="search.info"/>
							<br/>&nbsp;<br/>
							<br/>&nbsp;<br/>
						</div>
						<div class="panel-footer clearfix">
							<button class="btn btn-default btn-block" type="button" onclick="setAction('search');"><fmt:message key="button.indexsearch" /></button>
						</div> 
		            </div>
		        </div> /.col-xs-6 
		        
				<div class='col-xs-6'>
		          	<div class="panel panel-default">
			          	<div class="panel-heading">
			          		<h3 class="panel-title text-center"><fmt:message key="report.header"/></h3>
			          	</div>
						<div class="panel-body">
							<fmt:message key="report.info"/>
							<br/>&nbsp;<br/>
							<br/>&nbsp;<br/>
						</div>
						<div class="panel-footer clearfix">
							<button class="btn btn-default btn-block" type="button" onclick="setAction('report');"><fmt:message key="button.indexreport" /></button>
						</div> 
		            </div>
		        </div><!-- /.col-xs-6 
			</div> -->	
	       	       
	   <div style="text-align:center; display: none;"><br><bean:write name="trackingForm" property="version"/></div>
	</div>
       
</html:form>

</sakai:html>
