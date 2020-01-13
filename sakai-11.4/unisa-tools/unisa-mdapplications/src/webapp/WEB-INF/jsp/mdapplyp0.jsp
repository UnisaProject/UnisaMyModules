<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

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
	<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.css' />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
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
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}

	</style>
</head>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step0"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center">
						<logic:equal name="mdApplicationsForm" property="loginType" value="app">	
							<sakai:heading><fmt:message key="md.heading"/></sakai:heading>
						</logic:equal>
						<logic:equal name="mdApplicationsForm" property="loginType" value="doc">	
							<sakai:heading><fmt:message key="md.heading.doc"/></sakai:heading>
						</logic:equal>
					</h3>
				</div>
				<div class="panel-body clearfix">
					<sakai:instruction>
					<fmt:message key="md.instruction"/><br/>	
					</sakai:instruction>
										
					<sakai:flat_list>
					<tr>
						<td colspan="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td><fmt:message key="md.apply.studnumber"/></td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.number" maxlength="8" size="10"/></td>
					</tr>
					<tr>
						<td><fmt:message key="md.apply.studsurname"/></td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.surname" maxlength="28" size="40"/></td>
					</tr>	
					<tr>
						<td><fmt:message key="md.apply.studfullname"/></td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.firstnames" maxlength="60" size="60"/></td>
					</tr>	
					<tr>
						<td colspan="2"><fmt:message key="md.apply.studbirthdate"/></td>
						<td>Year(ccyy)&nbsp;<html:text name="mdApplicationsForm" property="student.birthYear" maxlength="4" size="6"/>&nbsp;
						&nbsp;Month&nbsp;<html:text name="mdApplicationsForm" property="student.birthMonth" maxlength="2" size="2"/>&nbsp;
						&nbsp;Day&nbsp;<html:text name="mdApplicationsForm" property="student.birthDay" maxlength="2" size="2"/>
						</td>
					</tr>	
					</sakai:flat_list>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
</html:form>
<BR/>
	<div style="display: none;"><br><bean:write name="mdApplicationsForm" property="version"/></div>
<BR/>
</sakai:html>