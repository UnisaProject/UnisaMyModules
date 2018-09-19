<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<sakai:html>
 
  <sakai:heading><fmt:message key="page.file.heading"/></sakai:heading>
  <html:form action="/smsFile">
  	<html:hidden property="page" value="viewErrors"/>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<%@ page import="java.io.*" %>
	
	<bean:define id="errorFile" name="smsBatchForm" property="errorFileName"/>
	<%
 	BufferedReader input = new BufferedReader(new FileReader(errorFile.toString()));
	String line = "";
	%>
	<html:submit property="act"><fmt:message key="button.back"/></html:submit>
	<!--<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>-->
	<sakai:group_heading><fmt:message key="page.fileErrors.heading"/></sakai:group_heading>
	<sakai:group_table>
		<tr>
			<logic:equal name="smsBatchForm" property="fileContentType" value="STUDNUM">
				<th><fmt:message key="page.fileErrors.content.studentNumbers"/></th>
			</logic:equal>
			<logic:equal name="smsBatchForm" property="fileContentType" value="CELLNUM">
				<th><fmt:message key="page.fileErrors.content.cellPhoneNumbers"/></th>
			</logic:equal>	
				<th><fmt:message key="page.fileErrors.message"/></th>
		</tr>
		<%
		while ((line = input.readLine()) != null)
		{
			%><tr><%
			String[] array = line.split(":");
			String number ="";
			String errMsg ="";
			for(int i=0; i < array.length; i++){
				if (i==0){
					number=array[i];
				}
				if (i==1){
					errMsg=array[i];
				}
			}
			%><td><%=number%></td><td><%=errMsg%></tr><%
		}
		input.close();
		%>   	
	</sakai:group_table>
  	<br/>  	
  	<html:submit property="act"><fmt:message key="button.back"/></html:submit>
	</html:form>
</sakai:html>