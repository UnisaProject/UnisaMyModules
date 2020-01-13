<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
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
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 
    
		$(document).ready(function() {
		    
			$("input[name='Next']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Continuing...</strong>" });
			}); 
			$("input[name='Back']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Going Back...</strong>" });
			});
		});
	</script>
</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<html:form action="/applyForStudentNumber">

		<html:hidden property="page" value="m30p1"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.m30.h0"/></h3>
				</div>
				<div class="panel-body">
					<strong><fmt:message key="page.m30.h1"/></strong><br/>
					<br/>
					<sakai:instruction>
						<fmt:message key="page.m30.h2"/><br/>
						<br/>
						<br/>1. Original Educational qualifications (High School and post-school qualifications) or only copies
						<br/>   certified correct by the Registrar of a South African university or by a South African Embassy,
						<br/>   Consulate, High Commission, Trade Mission or by a Public Notary in a foreign country. Original sworn 
						<br/>   translations into either English or Afrikaans must accompany documents issued in another language.
						<br/>
						<br/>2. Holders of American High School Diplomas must submit a letter issued by the Registrar of an 
						<br/>   accredited university in the United States of America to the effect that the holder is eligible for 
						<br/>   unconditional admission to degree studies at such a university, or submit SAT results.
						<br/>
						<br/>3. An original official academic record reflecting the courses passed in different years, if the
						<br/>   application is based on the grounds of post-school qualifications, with a prescribed minimum 
						<br/>   duration of at least three years uninterrupted study.
						<br/>
						<br/>4. A certified copy of the particulars in the applicant's identity document or passport reflecting
						<br/>   his/her date of birth and full names or the applicant's birth certificate.
						<br/>
						<br/>5. A certified copy of a marriage certificate or divorce decree (if applicable).
						<br/>
						<br/>6. An exemption fee as prescribed by the Matriculation Board will be payable.
						<br/>
						<br/>NB: FAXED OR E-MAILED DOCUMENTS ARE NOT ACCEPTABLE FOR THIS APPLICATION
						<br/>
					</sakai:instruction>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.next"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>

</html:form>
</body>
</sakai:html>