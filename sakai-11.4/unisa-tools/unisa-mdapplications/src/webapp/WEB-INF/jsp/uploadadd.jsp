
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<head>
	<script type="text/javascript" src="/unisa-mdapplications/js/jquery.js"></script>
	<script type="text/javascript" src="/unisa-mdapplications/js/jquery.blockUI.js" ></script> 
	
	<script type="text/javascript">

		//Call when document is ready
		$(document).ready(function() {
	
			$("input[value='Add']").click(function(){
	            $.blockUI({ message: '<h1><img src="/unisa-mdapplications/images/indicator.gif" alt=" * " /> File upload in progress. Please wait...</h1>' });
	        }); 

		});
	
		function doCancel(){
			$.blockUI({ message: '<h1><img src="/unisa-mdapplications/images/indicator.gif" alt=" * " /> Processing. Please wait...</h1>' });
			window.location.href = "mdapplications.do?action=walkthrough";
			return false;
		}
		
	</script>
	
</head>

	<sakai:messages/>
	<sakai:heading><fmt:message key="md.add.file"/></sakai:heading>	
	
	<sakai:instruction>
		<fmt:message key="md.file.instruction1"/><br/>
		<fmt:message key="md.file.instruction2"/><br/><br/>
	</sakai:instruction>
	
	<html:form action="/mdapplications.do" method="post" enctype="multipart/form-data">
	<html:hidden property="upload" value="upload"/>	
	<html:hidden property="page" value="upload"/>
	
	<input type="hidden" name="hidden" value="<%=request.getParameter("hidden") %>"/>
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="md.upload.file"/>
				</td>
				<td>					
					<html:file name="mdApplicationsForm"  property="theFile"/>
				</td>
			</tr>
			
		</sakai:group_table>
   		<sakai:actions>
   			<html:hidden name="mdApplicationsForm" property="cancel" value="messages"/>	
			<html:submit property="action"><fmt:message key="button.add"/></html:submit>
			<html:submit property="Cancel" value="Cancel" onclick="return doCancel();" />
		</sakai:actions>
	</html:form>
</sakai:html>