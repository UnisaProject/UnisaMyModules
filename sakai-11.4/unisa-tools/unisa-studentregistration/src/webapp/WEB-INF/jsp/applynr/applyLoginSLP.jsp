<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
        
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
		#color {
		  /**background-color: red;**/
		  float: left;
		}
		#color2 {
		  /**background-color: red;**/
		  float: left;
		}
		#doMainUDNo,#doMainUDNo,#doMainHONNo,#doMainHONNo,#doMainMDNo,#doMainDOCNo,#doMainPAYNo {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.itemSummary, .itemSummaryLite {
		    margin: 0;
		}
	
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    
	    <!-- Button Styling -->
	    
	    #ClassBtn {
			  display: inline-block;
			color: #333;
			background-color: #fff;
			
			padding: 6px 12px;
			margin-bottom: 0;
			font-size: 14px;
			font-weight: normal;
			line-height: 1.42857143;
			text-align: center;
			text-shadow: 0 1px 0 #fff;
			white-space: nowrap;
			vertical-align: middle;
			touch-action: manipulation;
			cursor: pointer;
			border: 1px solid transparent;
			border-radius: 4px;
			box-shadow: inset 0 1px 0 rgba(255, 255, 255, .15), 0 1px 1px rgba(0, 0, 0, .075);
			border-color: #ccc;
			background-repeat: repeat-x;
		}
			
			#ClassBtn.focus {
			  color: #333;
			  background-color: #e6e6e6;
			  border-color: #8c8c8c;
			}
			#ClassBtn:hover {
			  color: #333;
			  background-color: #e6e6e6;
			  border-color: #adadad;
			}

	</style>	
	
    <script type="text/javascript">  
      
		$(document).ready(function() {
	   	
			$('form,input,select,textarea').attr("autocomplete", "off");
			
		});

		function doSubmit(input,check){
			if (input === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (input === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}else if (input === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=applySLPSelect&selectSLP='+check;
			}
			document.studentRegistrationForm.submit();
		}

	</script>	
</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">

 	<noscript>
	    	Your browser does not support JavaScript or it is disabled. Please enable JavaScript in your Browser to continue.<br/>
	    	Here are the <a href="http://www.enable-javascript.com" target="_blank"> instructions how to enable JavaScript in your web browser</a>
	</noscript>

<!-- Form -->
<html:form action="/applyForStudentNumber">

	<html:hidden property="page" value="applyLoginSLP"/>
	
	<input type="hidden" name="mainSelect" id="mainSelect" value="<bean:write name='studentRegistrationForm' property='loginSelectMain' />"/>
	<input type="hidden" name="webOpenDate" id="webOpenDate" value="<bean:write name='studentRegistrationForm' property='student.webOpenDate' />"/>
	<input type="hidden" name="webLogMsg" id="webLogMsg" value="<bean:write name='studentRegistrationForm' property='webLoginMsg'/>"/>
	
	<sakai:messages/>
	<sakai:messages message="true"/>

	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.apply.select.infoSLP3"/></h3>
				</div>
				<div class="panel-body">
					<br/><br/>
					<div align="center">
						<span id="showYES">
							<button class="btn btn-default" onclick="doSubmit('Continue','YES');">YES</button>
							&nbsp;&nbsp;
						</span>
						<span id="showNO">
							&nbsp;&nbsp;
							<button class="btn btn-default" onclick="doSubmit('Continue','NO');">NO</button>
						</span>
					</div>
					<br/>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" onclick="doSubmit('Back','');">Back</button>
					<button class="btn btn-default" onclick="doSubmit('Cancel','');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
  </body>
</sakai:html>