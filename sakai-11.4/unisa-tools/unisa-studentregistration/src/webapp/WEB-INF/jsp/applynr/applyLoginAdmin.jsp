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
		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
		}
		#stretch {
		    width: 100%;
		    padding: 0 5px;
		}
		input[type="text"] {
			width: 100%;
			padding: 0 5px;
			height: 2em;
			line-height: 2; 
		}
		input[type="password"] {
			width: 100%;
			height: 2em;
			line-height: 2; 
		}
	</style>
	
    <script type="text/javascript">  
    
	$(document).ready(function() {
		
		$("input[name='staff.number']").focus();
		
		var showUD	= false;
		var showHON	= false;
		var showSLP = false;
		var showMD	= false;
		var showNEW	= false;
		var showRET	= false;
		
		if ($("#isDateWAPADMU").val() === "true"){
			showUD = true;
			$('.showUD').show();
		}
		if ($("#isDateWAPADMH").val() === "true"){
			showHON = true;
			$('.showHON').show();
		}
		if ($("#isDateWAPADMS").val() === "true"){
			showSLP = true;
			$('.showSLP').show();
		}
		if ($("#isDateWAPADMD").val() === "true" || $("#isDateWAPADMM").val() === "true"){
			showMD = true;
			$('.showMD').show();
		}
		if ($("#isDateWAPADMNEW").val() === "true"){
			showNEW = true;
			$('.showNEW').show();
		}
		if ($("#isDateWAPADMRET").val() === "true"){
			showRET = true;
			$('.showRET').show();
		}
		
		if (!showUD && !showHON && !showSLP && !showMD){
			$('#allOpen').hide();
			$('#allClosed').show();
		}else{
			$('#allOpen').show();
			$('#allClosed').hide();
		}
		
		$("input[value='Submit: Administrator']").click(function(){
	  		$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Accessing Administrator Site...</strong>" });
		}); 
		
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
 StudentRegistrationForm studentRegistrationForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
 String userID = studentRegistrationForm.getAdminStaff().getNumber()==null||studentRegistrationForm.getAdminStaff().getNumber().trim().length()==0 ? "User ID" :studentRegistrationForm.getAdminStaff().getNumber();
 String userPWD = studentRegistrationForm.getAdminStaff().getPassword()==null||studentRegistrationForm.getAdminStaff().getPassword().trim().length()==0 ? "Password" :studentRegistrationForm.getAdminStaff().getPassword();
%>

<body>
  <noscript><div id="portal_js_warn">This application requires JavaScript. Please enable JavaScript in your Browser.</div></noscript>
<!-- Form -->
<html:form method="post\" autocomplete=\"off" action="/applyForStudentNumber">
	<html:hidden property="page" value="stepLoginAdmin"/>
	
	<input type="hidden" name="webLogMsg" id="webLogMsg" value="<bean:write name='studentRegistrationForm' property='webLoginMsg'/>"/>
	
	<input type="hidden" name="isDateWAPADMU" id="isDateWAPADMU" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMU'/>"/>
	<input type="hidden" name="isDateWAPADMH" id="isDateWAPADMH" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMH'/>"/>
	<input type="hidden" name="isDateWAPADMS" id="isDateWAPADMS" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMS'/>"/>
	<input type="hidden" name="isDateWAPADMM" id="isDateWAPADMM" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMM'/>"/>
	<input type="hidden" name="isDateWAPADMD" id="isDateWAPADMD" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMD'/>"/>

	<input type="hidden" name="isDateWAPADMNEW" id="isDateWAPADMNEW" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMNEW'/>"/>
	<input type="hidden" name="isDateWAPADMRET" id="isDateWAPADMRET" value="<bean:write name='studentRegistrationForm' property='student.dateWAPADMRET'/>"/>
	
		

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
							<table style="width:350px;">
					            <tr>
					            	<td colspan="3" style="height:50px;color:#A90E13;font-size:14px; margin-bottom:10px;" align="center">
					                	<strong><bean:write name="studentRegistrationForm" property="webLoginMsg"/></strong><br/>
			                  			<strong><bean:write name="studentRegistrationForm" property="webLoginMsg2"/></strong>
					              	</td>
					        	</tr><tr>
					              	<td style="width:105px;height:30px" valign="middle"><fmt:message key="page.login.staff.number"/></td>
					              	<td colspan="2" style="height:30px" valign="middle">&nbsp;
					              		<html:text name="studentRegistrationForm" property="adminStaff.number" maxlength="8" size="30" value="<%=userID%>" onfocus="checkclear(this);" />
					              	</td>
					            </tr><tr>
					              	<td style="width:105px;height:30px" valign="middle"><fmt:message key="page.login.staff.password"/></td>
					              	<td colspan="2" style="height:30px" valign="middle">&nbsp;
					              		<html:password name="studentRegistrationForm" property="adminStaff.password" maxlength="30" size="30" value="<%=userPWD%>" onfocus="checkclear(this);" />
					              	</td>
					            </tr><tr>
					             	<td colspan="3">&nbsp;</td>
								</tr><tr>
					             	<td colspan="3"><fmt:message key="page.login.staff.radios"/>&nbsp;</td>
					            </tr><tr class="showUD" style="display: none;">
			           				<td>&nbsp;</td>
			           				<td style="width:50px">&nbsp;<html:radio property="adminStaff.admission" value="UD"/>&nbsp;</td>
			           				<td><fmt:message key="page.login.staff.infoUD"/></td>
			           			</tr><tr class="showHON" style="display: none;">
			           				<td>&nbsp;</td>
			           				<td style="width:50px">&nbsp;<html:radio property="adminStaff.admission" value="HON"/>&nbsp;</td>
				       				<td><fmt:message key="page.login.staff.infoHON"/></td>
				       			</tr><tr class="showSLP" style="display: none;">
			           				<td>&nbsp;</td>
			           				<td style="width:50px">&nbsp;<html:radio property="adminStaff.admission" value="SLP"/>&nbsp;</td>
				       				<td><fmt:message key="page.login.staff.infoSLP"/></td>
				       			</tr><tr class="showMD" style="display: none;">
				       				<td>&nbsp;</td>
				       				<td style="width:50px">&nbsp;<html:radio property="adminStaff.admission" value="MD"/>&nbsp;</td>
				       				<td><fmt:message key="page.login.staff.infoMD"/></td>
				       			</tr><tr>
					             	<td colspan="3">&nbsp;</td>
					            </tr><tr>
					             	<td colspan="3"><fmt:message key="page.login.staff.student"/>&nbsp;</td>
					            </tr><tr class="showNEW" style="display: none;">
				       				<td>&nbsp;</td>
				       				<td style="width:50px">&nbsp;<html:radio property="adminStaff.studentType" value="NEW"/>&nbsp;</td>
				       				<td><fmt:message key="page.login.staff.infoNEW"/></td>
				       			</tr><tr class="showRET" style="display: none;">
				       				<td>&nbsp;</td>
				       				<td style="width:50px">&nbsp;<html:radio property="adminStaff.studentType" value="RET"/>&nbsp;</td>
				       				<td><fmt:message key="page.login.staff.infoRET"/></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.submitLoginAdmin"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>


	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
</html:form>
</body>
</sakai:html>
