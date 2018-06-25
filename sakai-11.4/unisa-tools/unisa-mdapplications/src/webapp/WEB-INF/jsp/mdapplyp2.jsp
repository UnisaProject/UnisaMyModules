<%@ page import="za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm"%>
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
	<html:hidden property="page" value="step2"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="md.heading"/></h3>
				</div>
				<div class="panel-body clearfix">
					<sakai:group_heading><fmt:message key="md.subheading2"/></sakai:group_heading>
			
					<sakai:group_table>
					<tr>
						<td><fmt:message key="md.page2.spes"/>&nbsp;</td>
						<td colspan="2">
							<html:select property="selectedSpes">
			 					<html:options collection="speslist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr><tr>
						<td colspan="3"><fmt:message key="md.page2.instruction"/>&nbsp;<sakai:required/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.home"/>&nbsp;<sakai:required/></td>
						<td><html:text name="mdApplicationsForm" property="student.homePhone" maxlength="28" size="30"/></td>
						<td align="left"><fmt:message key="md.page2.eg1"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.work"/></td>
						<td><html:text name="mdApplicationsForm" property="student.workPhone" maxlength="28" size="30"/></td>
						<td align="left"><fmt:message key="md.page2.eg1"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.cell"/></td>
						<td><html:text name="mdApplicationsForm" property="student.cellNr" maxlength="20" size="25"/></td>
						<td align="left"><fmt:message key="md.page2.eg2"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td align="left"><fmt:message key="md.page2.eg3"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.fax"/></td>
						<td><html:text name="mdApplicationsForm" property="student.faxNr" maxlength="28" size="30"/></td>
						<td align="left"><fmt:message key="md.page2.eg1"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.emailaddress"/>&nbsp;<sakai:required/></td>
						<td colspan="2">
							<!-- changes -->	 
							<logic:equal name="mdApplicationsForm" property="student.emailAddressGood" value="true">
								<bean:write name="mdApplicationsForm" property="student.emailAddress"/>
							</logic:equal>
							<logic:notEqual name="mdApplicationsForm" property="student.emailAddressGood" value="true">
								<html:text name="mdApplicationsForm" property="student.emailAddress" maxlength="60" size="40" />
							</logic:notEqual>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page2.postal"/>&nbsp;<sakai:required/></td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line1" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line2" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line3" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td><html:text name="mdApplicationsForm" property="student.postalAddress.line4" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line5" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.postalAddress.line6" maxlength="28" size="30"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.postalcode"/>&nbsp;<sakai:required/></td>
						<td><html:text name="mdApplicationsForm" property="student.postalAddress.areaCode" maxlength="4" size="6"/></td>
						<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.country"/></td>
						<td colspan="2">
							<html:select property="selectedCountry">
			 					<html:options collection="countrylist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page2.physical"/>&nbsp;<sakai:required/></td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line1" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line2" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line3" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line4" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line5" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.physicalAddress.line6" maxlength="28" size="30"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.postalcode"/>&nbsp;</td>
						<td><html:text name="mdApplicationsForm" property="student.physicalAddress.areaCode" maxlength="4" size="6"/></td>
						<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.courier"/>&nbsp;</td>
						<td><html:text name="mdApplicationsForm" property="student.courierAddress.line1" maxlength="28" size="30"/></td>
						<td align="left"><fmt:message key="md.page2.courier2"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line2" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line3" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line4" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line5" maxlength="28" size="30"/></td>
					</tr><tr>
						<td>&nbsp;</td>
						<td colspan="2"><html:text name="mdApplicationsForm" property="student.courierAddress.line6" maxlength="28" size="30"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.postalcode"/>&nbsp;</td>
						<td><html:text name="mdApplicationsForm" property="student.courierAddress.areaCode" maxlength="4" size="6"/></td>
						<td align="left"><fmt:message key="md.page2.postcodeinfo"/></td>
					</tr><tr>
						<td><fmt:message key="md.page2.contactnr"/></td>
						<td><html:text name="mdApplicationsForm" property="student.contactNr" maxlength="40" size="40"/></td>
						<td align="left"><fmt:message key="md.page2.eg1"/></td>
					</tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="action"><fmt:message key="button.back"/></html:submit>
						<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
</html:form>
</sakai:html>