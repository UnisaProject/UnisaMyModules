<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.*" %>
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
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		}
		table { 
		    border-spacing: 2px;
		    border-collapse: separate;
		}
		td { 
		    padding: 2px;
		}
	</style>
	
    <script type="text/javascript">  
    
	$(document).ready(function() {
		
		$("input[name='adminStaff.number']").focus();
		
		var showADM	= false;

		if ($("#isDateWEBMDADM").val() === "true"){
			$('#allOpen').show();
			$('#allClosed').hide();
		}else{
			$('#allOpen').hide();
			$('#allClosed').show();
		}
		
	});
	
	function checkclear(what){
		if(!what._haschanged){
			what.value='';
		};
			what._haschanged=true;
	}
		
	</script>

</head>

<%
 MdApplicationsForm mdForm = (MdApplicationsForm)session.getAttribute("mdApplicationsForm");
 String userID = mdForm.getAdminStaff().getNumber()==null||mdForm.getAdminStaff().getNumber().trim().length()==0 ? "User ID" :mdForm.getAdminStaff().getNumber();
 String userPWD = mdForm.getAdminStaff().getPassword()==null||mdForm.getAdminStaff().getPassword().trim().length()==0 ? "Password" :mdForm.getAdminStaff().getPassword();
%>

<body>
  <noscript><div id="portal_js_warn">This application requires JavaScript. Please enable JavaScript in your Browser.</div></noscript>
<!-- Form -->
<html:form method="post\" autocomplete=\"off" action="/mdapplications">
	<html:hidden property="page" value="stepLoginAdmin"/>
	
	<input type="hidden" name="webLogMsg" id="webLogMsg" value="<bean:write name='mdApplicationsForm' property='webLoginMsg'/>"/>
	<input type="hidden" name="isDateWEBMDADM" id="isDateWEBMDADM" value="<bean:write name='mdApplicationsForm' property='dateWEBMDADM'/>"/>
	<input type="hidden" name="isDateWEBMDAPP" id="isDateWEBMDAPP" value="<bean:write name='mdApplicationsForm' property='dateWEBMDAPP'/>"/>
	<input type="hidden" name="isDateWEBMDDOC" id="isDateWEBMDDOC" value="<bean:write name='mdApplicationsForm' property='dateWEBMDDOC'/>"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.login.staff.heading"/></h3>
				</div>
				<div class="panel-body clearfix">
					<div id="allClosed" style="display: none;">
						<div class="panel panel-default">
							<div class="light" align="center">
								<br/>
								<font color="#A90E13">The Administration application period is closed.</font>
								<br/><br/>
							</div>
						</div>
					</div>
					<div id="allOpen" style="display: none;">
						<div align="center">
							<table style="width:400px;">
					            <tr>
					            	<td colspan="2" style="height:50px;color:#A90E13;font-size:14px; margin-bottom:10px;" align="center">
					                	<strong><bean:write name="mdApplicationsForm" property="webLoginMsg"/></strong><br/>
					              	</td>
					        	</tr><tr>
					              	<td style="height:30px" valign="middle"><fmt:message key="page.login.staff.number"/></td>
					              	<td style="height:30px" valign="middle">&nbsp;
					              		<html:text name="mdApplicationsForm" property="adminStaff.number" maxlength="8" size="30" value="<%=userID%>" onfocus="checkclear(this);" />
					              	</td>
					            </tr><tr>
					              	<td style="height:30px" valign="middle"><fmt:message key="page.login.staff.password"/></td>
					              	<td style="height:30px" valign="middle">&nbsp;
					              		<html:password name="mdApplicationsForm" property="adminStaff.password" maxlength="30" size="30" value="<%=userPWD%>" onfocus="checkclear(this);" />
					              	</td>
					            </tr><tr>
					             	<td colspan="2">&nbsp;</td>
								</tr><tr>
					             	<td colspan="2"><fmt:message key="page.login.staff.radios"/>&nbsp;</td>
					            </tr><tr>
			           				<td colspan="2" style="width:400px">
			           					&nbsp;<html:radio property="adminStaff.admission" value="app"/>
			           					&nbsp;<fmt:message key="page.login.staff.infoAPP"/>
									</td>
			           			</tr><tr>
			           				<td colspan="2" style="width:400px">
			           					&nbsp;<html:radio property="adminStaff.admission" value="doc"/>
			           					&nbsp;<fmt:message key="page.login.staff.infoDOC"/>
			           				</td>
				       			</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="action"><fmt:message key="button.submitLoginAdmin"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>


	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="mdApplicationsForm" property="version"/></div>
</html:form>
</body>
</sakai:html>
