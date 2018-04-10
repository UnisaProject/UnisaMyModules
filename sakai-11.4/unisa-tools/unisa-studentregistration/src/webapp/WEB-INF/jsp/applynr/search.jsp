<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle 	basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

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
	    input[type="text"] {
		    width: 100%;
		    padding: 0 5px;
		}
		
	</style>	
	
    <script type="text/javascript">  
	
		function disableBackButton(){window.history.forward()} 
		disableBackButton(); 
		window.onload=disableBackButton(); 
		window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
		window.onunload=function() { void(0) } 
		
		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
		});
	
	</script>
</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<html:form action="/applyForStudentNumber">

	<html:hidden property="page" value="search"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading" /></h3>
				</div>
				<div class="panel-body">
					<strong><fmt:message key="page.heading.enter" /></strong><br/>
					<sakai:group_table>
						<tr>
							<td><fmt:message key="page.suburb" /></td>
							<td><html:text maxlength="40" property="searchSuburb" />&nbsp;</td>
						</tr>
						<tr>
							<td><strong>OR</strong></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><fmt:message key="page.town" /></td>
							<td><html:text maxlength="40" property="searchTown" />&nbsp;</td>
						</tr>
					</sakai:group_table>
				
					<logic:notEmpty name="postalcodelist">
						<hr />
						<sakai:instruction>
							<fmt:message key="page.search.instruction" />
						</sakai:instruction>
						<br />
						<strong><fmt:message key="page.search.postalcode" /></strong>&nbsp;
							<html:select property="searchResult">
							<html:options collection="postalcodelist" property="value" labelProperty="label"/>
						</html:select>
						<br />
						<br />
					</logic:notEmpty>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
					    <logic:notEmpty name="postalcodelist">
							<html:submit property="act">
							<fmt:message key="button.continue" />
							</html:submit>
						</logic:notEmpty>
						<html:submit property="act">
							<fmt:message key="button.list" />
						</html:submit>
						<html:submit property="act">
							<fmt:message key="button.search.cancel" />
						</html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>

</html:form>
</body>
</sakai:html>