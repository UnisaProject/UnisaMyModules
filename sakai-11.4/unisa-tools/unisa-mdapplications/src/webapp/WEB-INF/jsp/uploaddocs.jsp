<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<head>
	<script type="text/javascript" src="/unisa-mdapplications/js/jquery.js"></script>
	<script type="text/javascript" src="/unisa-mdapplications/js/jquery.blockUI.js" ></script>  
	<script type="text/javascript">
	
	
	
		//Call when document is ready
		$(document).ready(function() {
	
			$("input[name='Add File']").click(function(){
	            $.blockUI({ message: '<h1><img src="/unisa-mdapplications/images/indicator.gif" alt=" * " /> File upload in progress. Please wait...</h1>' });
	        }); 
			$("input[name='Save']").click(function(){
	            $.blockUI({ message: '<h1><img src="/unisa-mdapplications/images/indicator.gif" alt=" * " /> Processing. Please wait...</h1>' });
	        }); 
	
		});
		
		function doCancel(){
			$.blockUI({ message: '<h1><img src="/unisa-mdapplications/images/indicator.gif" alt=" * " /> Processing. Please wait...</h1>' });
			window.location.href = "mdapplications.do?action=walkthrough";
			return false;
		}
		
	</script>
</head>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="addfiles"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<logic:equal name="mdApplicationsForm" property="application" value="false"> 
			<sakai:heading>GENERAL doc upload</sakai:heading>
	    </logic:equal>
	    <logic:equal name="mdApplicationsForm" property="application" value="true"> 
			<sakai:heading><fmt:message key="md.heading"/></sakai:heading>
	    </logic:equal>
	    
	    <strong><fmt:message key="md.apply.studnumber"/>&nbsp;&nbsp;<bean:write name="mdApplicationsForm" property="student.number"/></strong>
	    
		<sakai:instruction>
			<fmt:message key="md.docs.info1"/><br/></br>
		</sakai:instruction>

		<logic:notEmpty name="mdApplicationsForm" property="requiredFiles">
			<sakai:group_heading><fmt:message key="md.docs.required.heading"/></sakai:group_heading>
			<strong><fmt:message key="md.docs.required.info1"/></strong>
			<sakai:flat_list>
				<logic:iterate name="mdApplicationsForm" property="requiredFiles" id="file" indexId="index">
					<logic:present name="file" property="fileName">
						<tr>
							<td>&nbsp;&nbsp;<font color="red"><bean:write name="file" property="fileName"/></font></td>
						</tr>
					</logic:present>
				</logic:iterate>
			</sakai:flat_list>
			<fmt:message key="md.docs.required.info2"/>
		</logic:notEmpty>

		<sakai:group_heading><fmt:message key="md.docs.heading"/></sakai:group_heading>
				
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td colspan="2" style="width:10px">&nbsp;</td>
			<tr>
				<td colspan="2"><strong><fmt:message key="md.docs.filetype"/>&nbsp;<sakai:required/></strong></td>
			</tr>
			<!-- File type -->
			<tr>
				<td width="50%">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td><html:radio property="fileType" value="1"/><fmt:message key="md.doc.d1"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="2"/><fmt:message key="md.doc.d2"/></td>				
						</tr><tr>
							<td><html:radio property="fileType" value="3"/><fmt:message key="md.doc.d3"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="4"/><fmt:message key="md.doc.d4"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="5"/><fmt:message key="md.doc.d5"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="6"/><fmt:message key="md.doc.d6"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="7"/><fmt:message key="md.doc.d7"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="8"/><fmt:message key="md.doc.d8"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="9"/><fmt:message key="md.doc.d9"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="10"/><fmt:message key="md.doc.d10"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="21"/><fmt:message key="md.doc.d21"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="22"/><fmt:message key="md.doc.d22"/></td>
				 		</tr>
					</table>
				</td>
				<td width="50%" valign="top">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td><html:radio property="fileType" value="11"/><fmt:message key="md.doc.d11"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="12"/><fmt:message key="md.doc.d12"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="13"/><fmt:message key="md.doc.d13"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="14"/><fmt:message key="md.doc.d14"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="15"/><fmt:message key="md.doc.d15"/></td>				
						</tr><tr>
							<td><html:radio property="fileType" value="16"/><fmt:message key="md.doc.d16"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="17"/><fmt:message key="md.doc.d17"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="18"/><fmt:message key="md.doc.d18"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="23"/><fmt:message key="md.doc.d23"/></td>
						</tr><tr>
							<td><html:radio property="fileType" value="24"/><fmt:message key="md.doc.d24"/></td>
				 		</tr><tr>
							<td><html:radio property="fileType" value="19"/><fmt:message key="md.doc.d19"/></td>
				 		</tr><tr>
					</table>
				</td>
			</tr>
		</table>
		
		<sakai:group_heading>File specifications</sakai:group_heading>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td width="50%">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							 <td style="width:10px">&nbsp;</td>
						</tr><tr>
							 <td><fmt:message key="page.files.specs1"/></td>
						</tr><tr>
							 <td><fmt:message key="page.files.specs2"/></td>
				 		</tr><tr>
							 <td>&nbsp;</td>
						</tr><tr>
							 <td>&nbsp;</td>
						</tr><tr>
							 <td>&nbsp;</td>
						</tr>
					</table>
				</td>
				<td width="50%" valign="top">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							 <td style="width:10px">&nbsp;</td>
						</tr><tr>
							 <td colspan='2'><fmt:message key="page.files.specs3"/></td>
						</tr><tr>
							 <td colspan='2'><fmt:message key="page.files.specs4"/></td>
						</tr><tr>
							 <td>&nbsp;&nbsp;<fmt:message key="page.file.ext1"/></td><td><fmt:message key="page.file.type1"/></td>
						</tr><tr>
							 <td>&nbsp;&nbsp;<fmt:message key="page.file.ext2"/></td><td><fmt:message key="page.file.type2"/></td>
						</tr><tr>
							 <td>&nbsp;&nbsp;<fmt:message key="page.file.ext3"/></td><td><fmt:message key="page.file.type3"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		
		<sakai:group_heading><fmt:message key="md.docs.selected.files"/></sakai:group_heading>
		<sakai:flat_list>
		<%
			// loop already uploaded files
		%>
		<logic:notEmpty name="mdApplicationsForm" property="studentFiles">
		<tr>
			<th>File name</th>
			<th>File type</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
		<logic:iterate name="mdApplicationsForm"
			property="studentFiles" id="file" indexId="index">
			<logic:present name="file" property="fileName">
			<tr>
				<td><bean:write name="file" property="fileName"/></td>
				<td><logic:equal name="file" property="fileType" value="1"><fmt:message key="md.doc.d1"/></logic:equal>
					<logic:equal name="file" property="fileType" value="2"><fmt:message key="md.doc.d2"/></logic:equal>
					<logic:equal name="file" property="fileType" value="3"><fmt:message key="md.doc.d3"/></logic:equal>
					<logic:equal name="file" property="fileType" value="4"><fmt:message key="md.doc.d4"/></logic:equal>				
 					<logic:equal name="file" property="fileType" value="5"><fmt:message key="md.doc.d5"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="6"><fmt:message key="md.doc.d6"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="7"><fmt:message key="md.doc.d7"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="8"><fmt:message key="md.doc.d8"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="9"><fmt:message key="md.doc.d9"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="10"><fmt:message key="md.doc.d10"/></logic:equal> 
					
 					<logic:equal name="file" property="fileType" value="11"><fmt:message key="md.doc.d11"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="12"><fmt:message key="md.doc.d12"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="13"><fmt:message key="md.doc.d13"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="14"><fmt:message key="md.doc.d14"/></logic:equal>				
 					<logic:equal name="file" property="fileType" value="15"><fmt:message key="md.doc.d15"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="16"><fmt:message key="md.doc.d16"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="17"><fmt:message key="md.doc.d17"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="18"><fmt:message key="md.doc.d18"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="21"><fmt:message key="md.doc.d21"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="22"><fmt:message key="md.doc.d22"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="23"><fmt:message key="md.doc.d23"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="24"><fmt:message key="md.doc.d24"/></logic:equal> 
 					<logic:equal name="file" property="fileType" value="19"><fmt:message key="md.doc.d19"/></logic:equal> 
				</td>
				<td><html:link href="mdapplications.do?act=clearAttachment&fname=${file.newFileName}">Remove</html:link></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<!--				<td>For testing only:&nbsp;<bean:write name="file" property="newFileName"/></td>  -->

			</tr>
			</logic:present>
			<%
				//-------- end loop  -------
			%>
		</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="mdApplicationsForm" property="studentFiles">
			<!-- <tr>
				<td><br/><strong><fmt:message key="page.apply.files.none"/>&nbsp;</strong></td>

			</tr>-->
		</logic:empty>
	</sakai:flat_list>

		<sakai:actions>
			<input type="hidden" name="inputFileName" value="<%=request.getAttribute("inputFileName") %>"/>
			<html:submit styleClass="active" property="action"><fmt:message key="button.add.attachment"/></html:submit><br><br><hr><br>
			<html:submit property="action"><fmt:message key="button.save"/></html:submit>
			<html:submit property="Cancel" value="Cancel" onclick="return doCancel();" />
		</sakai:actions>
		
	</html:form>
</sakai:html>