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
	<html:hidden property="page" value="step5"/>

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
					<sakai:group_heading><fmt:message key="md.subheading5"/></sakai:group_heading>
			
					<sakai:group_table>
					<tr>
						<td colspan="2"><strong><fmt:message key="md.page5.instruction"/>&nbsp;</strong></td>
					</tr><tr>
						<td><fmt:message key="md.page5.studentnr"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.number" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.title"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.title" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.surname"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.surname" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.firstnames"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.firstnames" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.maiden"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.maidenName" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.initials"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.initials" /></td>
					</tr><tr>
						<td><fmt:message key="md.page5.identity"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.idNumber" /></td>
					</tr><tr>
						<td><fmt:message key="md.page5.foreignid"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.passportNumber" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.birthdate"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.birthYear" />/
						<bean:write name="mdApplicationsForm" property="student.birthMonth" />/
						<bean:write name="mdApplicationsForm" property="student.birthDay" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.gender"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.gender" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.language"/></td>
						<logic:equal name="mdApplicationsForm" property="student.language" value="E">
						<td><bean:write name="mdApplicationsForm" property="student.language" /> English</td></logic:equal>
						<logic:equal name="mdApplicationsForm" property="student.language" value="A">
						<td><bean:write name="mdApplicationsForm" property="student.language" /> Afrikaans</td></logic:equal>
					</tr><tr>
						<td><fmt:message key="md.page1.disability"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.disability.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.home"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.homePhone" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.work"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.workPhone" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.cell"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.cellNr" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.fax"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.faxNr" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.emailaddress"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.emailAddress" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.postal"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line1" /></td>
					</tr>
					<logic:notEqual name="mdApplicationsForm" property="student.postalAddress.line2" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line2" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.postalAddress.line3" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line3" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.postalAddress.line4" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line4" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.postalAddress.line5" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line5" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.postalAddress.line6" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.line6" /></td>
					</tr>
					</logic:notEqual>
					<tr>
						<td><fmt:message key="md.page2.postalcode"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.postalAddress.areaCode" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.country"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.country.code" />&nbsp;&nbsp;
						<bean:write name="mdApplicationsForm" property="student.country.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.physical"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line1" /></td>
					</tr>
					<logic:notEqual name="mdApplicationsForm" property="student.physicalAddress.line2" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line2" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.physicalAddress.line3" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line3" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.physicalAddress.line4" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line4" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.physicalAddress.line5" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line5" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.physicalAddress.line6" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.line6" /></td>
					</tr>
					</logic:notEqual>
					<tr>
						<td><fmt:message key="md.page2.postalcode"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.physicalAddress.areaCode" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.courier"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line1" /></td>
					</tr>
					<logic:notEqual name="mdApplicationsForm" property="student.courierAddress.line2" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line2" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.courierAddress.line3" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line3" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.courierAddress.line4" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line4" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.courierAddress.line5" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line5" /></td>
					</tr>
					</logic:notEqual>
					<logic:notEqual name="mdApplicationsForm" property="student.courierAddress.line6" value="">
					<tr>
						<td>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.line6" /></td>
					</tr>
					</logic:notEqual>
					<tr>
						<td><fmt:message key="md.page2.postalcode"/>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="student.courierAddress.areaCode" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.contactnr"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.contactNr" /></td>
					</tr><tr>
						<td><fmt:message key="md.page5.sharedetails"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.shareContactDetails" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.examcentre"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.exam.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.homelanguage"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.homeLanguage.desc" /></td>
					</tr><tr>
			<!--			<td><fmt:message key="md.page5.twin"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.twinflag" /></td>
					</tr><tr>-->
						<td><fmt:message key="md.page3.nationality"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.nationalty.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.population"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.populationGroup.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.occupation"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.occupation.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.econsector"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.economicSector.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.activity"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.prevActivity.desc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page1.proqual"/></td>
						<td><bean:write name="mdApplicationsForm" property="qual.qualCode" />
						<bean:write name="mdApplicationsForm" property="qual.qualDesc" /></td>
					</tr><tr>
						<td><fmt:message key="md.page2.spes"/>&nbsp;</td>
						<td><bean:write name="mdApplicationsForm" property="qual.specDesc" /></td>
			<!--		<td colspan="2"><fmt:message key="md.page3.last"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.lastStatus" /></td>
					</tr><tr>
						<td><fmt:message key="md.page3.training"/></td>
						<td><bean:write name="mdApplicationsForm" property="student.computerTraining" /></td>  -->
					</tr><tr>
						<td colspan="2"><strong><fmt:message key="md.page3.declare"/></strong></td>
					</tr><tr>
						<td colspan="2"><fmt:message key="md.page5.declare1"/></td>
					</tr><tr>
						<td colspan="2"><fmt:message key="md.page5.declare2"/></td>
					</tr><tr>
					</tr><tr>
						<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio  property ="agree" value="Y"/><fmt:message key="md.page3.agree"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio property ="agree" value="N"/><fmt:message key="md.page3.disagree"/>
						</td>
					</tr><tr>
						<td colspan="2"></td>
					</tr>
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="action"><fmt:message key="button.request"/></html:submit>
						<html:submit property="action"><fmt:message key="button.back"/></html:submit>
						<html:submit property="action"><fmt:message key="button.withdraw"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
</html:form>
</sakai:html>