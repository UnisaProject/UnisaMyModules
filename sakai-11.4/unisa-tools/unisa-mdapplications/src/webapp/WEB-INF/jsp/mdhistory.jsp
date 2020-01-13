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
		.textareaMax {
			width:90%
		}
		#stretch {
		    width: 100%;
		    padding: 0 5px;
		}
		input[type="text"] {
			width: 100%;
			padding: 0 5px;
		}
		select	{
	    width: 100%;
	    text-overflow: ellipsis;
		}
	</style>
</head>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="history"/>

	<BR/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="md.page4.header"/></h3>
				</div>
				<div class="panel-body clearfix">
					<sakai:group_table>
					<tr>
						<td colspan="3"><strong><fmt:message key="md.page4.tertiary"/></strong></td>
					</tr><tr>
						<td><strong><fmt:message key="md.page4.ha1"/></strong></td>
						<td><strong><fmt:message key="md.page4.ha2"/></strong></td>
						<td><strong><fmt:message key="md.page4.ha5"/></strong></td>
					</tr><tr>
						<td><strong><fmt:message key="md.page4.hb1"/></strong></td>
						<td><strong><fmt:message key="md.page4.hb2"/></strong></td>
						<td><strong><fmt:message key="md.page4.hb5"/></strong></td>
					</tr><tr>
						<td><fmt:message key="md.page4.hc1"/></td>
						<td><fmt:message key="md.page4.hc2"/></td>
						<td><strong><fmt:message key="md.page4.hc5"/></strong></td>
					</tr>
			
					<logic:iterate name="mdApplicationsForm" property="mdprevList" id="record" indexId="index"> 
					<tr>
						<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histUniv"%>' maxlength="30" size="40"/></td>
						<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histQual"%>' maxlength="20" size="30"/></td>
						<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histComplete"%>' maxlength="4" size="5"/></td> 
					</tr>				
					</logic:iterate>
					</sakai:group_table>
					<sakai:group_table>
					<tr>
						<td><fmt:message key="md.page4.q3"/>&nbsp;<sakai:required/></td>
						<td colspan="2">
							<html:select property="selectedInterestArea">
			 					<html:options collection="interestArealist" property="value" labelProperty="label"/>
							</html:select>
						</td>
					</tr>
					</sakai:group_table>
					<sakai:group_table>
					<tr>
						<td><fmt:message key="md.page4.q2"/>&nbsp;<sakai:required/></td>
					</tr><tr>									
						<td><html:textarea name="mdApplicationsForm" styleClass="textareaMax" property="student.researchTopic" rows="4"/></td>	
					</tr><tr>
						<td><fmt:message key="md.page4.q4"/>&nbsp;<sakai:required/></td>
					</tr><tr>						
						<td><html:text name="mdApplicationsForm" property="student.lecturer" maxlength="80"/></td>
					</tr>
					</sakai:group_table>
					<sakai:group_table>
					<tr>
						<td><fmt:message key="md.page4.q5"/>&nbsp;<sakai:required/></td>
						<!-- <td><html:text name="mdApplicationsForm" property="student.passedndp" maxlength="1" size="1"/></td>  -->
						<td style="white-space: nowrap">
							<html:radio property="student.passedndp" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio property="student.passedndp" value="N"/><fmt:message key="page.no"/>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page4.q6"/>&nbsp;<sakai:required/></td>
						<!-- <td><html:text name="mdApplicationsForm" property="student.appliedmd" maxlength="1" size="1"/></td>  -->
						<td style="white-space: nowrap">
							<html:radio property="student.appliedmd" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio property="student.appliedmd" value="N"/><fmt:message key="page.no"/>
						</td>
					</tr><tr>
						<td><fmt:message key="md.page4.q7"/>&nbsp;<sakai:required/></td>
						<!-- <td><html:text name="mdApplicationsForm" property="student.appliedqual" maxlength="1" size="1"/></td>  -->
						<td style="white-space: nowrap">
							<html:radio property="student.appliedqual" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio property="student.appliedqual" value="N"/><fmt:message key="page.no"/>
						</td>
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