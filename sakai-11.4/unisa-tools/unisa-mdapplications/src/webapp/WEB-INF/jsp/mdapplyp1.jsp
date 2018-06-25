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
		}
	</style>
</head>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step1"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="md.heading.p1"/></h3>
				</div>
				<div class="panel-body clearfix">
					<sakai:group_heading><fmt:message key="md.subheading1"/></sakai:group_heading>

					<sakai:group_table>
					<tr>
						<td colspan="3"><fmt:message key="md.page1.instruction"/>&nbsp;<sakai:required/></td>
					</tr><tr>
						<td colspan="3"><strong><fmt:message key="md.page1.instruct2"/>&nbsp;</strong><sakai:required/></td>
					</tr><tr>
						<td><fmt:message key="md.page1.studnumber"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.number"/></td>
					</tr><tr>
						<td><fmt:message key="md.page1.proqual"/>&nbsp;<sakai:required/></td>
						<td colspan="2">
							<html:select property="selectedQual">
			 					<html:options collection="quallist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page1.acadyear"/>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.academicYear"/></td>
					</tr><tr>
						<td><fmt:message key="md.page1.surname"/>&nbsp;<sakai:required/></td>
						<td><bean:write name="mdApplicationsForm" property="student.surname" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.initials"/>&nbsp;<sakai:required/></td>
						<td><bean:write name="mdApplicationsForm" property="student.initials" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.title"/>&nbsp;<sakai:required/></td>
			<!--			<td><bean:write name="mdApplicationsForm" property="student.title" /></td>  -->
						<td colspan="2">
							<html:select property="student.title">
			 					<html:options collection="titlelist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page1.firstnames"/>&nbsp;<sakai:required/></td>
						<td><bean:write name="mdApplicationsForm" property="student.firstnames" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.maiden"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.maidenName" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.birthdate"/>&nbsp;<sakai:required/></td>
						<td><bean:write name="mdApplicationsForm" property="student.birthYear" />
						<bean:write name="mdApplicationsForm" property="student.birthMonth" />
						<bean:write name="mdApplicationsForm" property="student.birthDay" /></td>
			<!--			<td colspan="2">
							<html:text name="mdApplicationsForm" property="student.birthYear" maxlength="4" size="5"/>&nbsp;
							/&nbsp;<html:text name="mdApplicationsForm" property="student.birthMonth" maxlength="2" size="3"/>&nbsp;
							/&nbsp;<html:text name="mdApplicationsForm" property="student.birthDay" maxlength="2" size="3"/>&nbsp;
						</td>   -->
					</tr><tr>
						<td><fmt:message key="md.page1.enter"/>&nbsp;<sakai:required/></td>
						<td><html:radio property="student.idType" value="R"/><fmt:message key="md.page1.id"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.idNumber" /></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td><html:radio property="student.idType" value="P"/><fmt:message key="md.page1.foreign"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.passportNumber" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.gender"/>&nbsp;<sakai:required/></td>
						<td><html:radio property="student.gender" value="F"/><fmt:message key="md.page1.female"/></td>
						<td><html:radio property="student.gender" value="M"/><fmt:message key="md.page1.male"/></td>
					</tr><tr>
						<td><fmt:message key="md.page1.language"/>&nbsp;<sakai:required/></td>
						<td><html:radio property="student.language" value="E"/><fmt:message key="md.page1.eng"/></td>
						<td><html:radio property="student.language" value="A"/><fmt:message key="md.page1.afr"/>
					</tr><tr>
						<td><fmt:message key="md.page1.disability"/></td>
						<td colspan="2">
							<html:select property="selectedDisability">
			 					<html:options collection="disabilitylist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr><tr>
						<td colspan="2">
							<html:radio  property ="agreeQualInfo" value="Y"/>&nbsp;<fmt:message key="md.page1.agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
</html:form>
</sakai:html>