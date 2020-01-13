<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorstudentgrouping.ApplicationResources"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<sakai:html>
   <html:form action="/tutorStudentGrouping">
  		<html:hidden property="currentPage" value="viewInvalidRecords"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.uploadGroup"/>
		</sakai:heading>
		<%@ page import="java.io.*" %>
	
		<bean:define id="errorFile" name="tutorStudentGroupForm" property="errorFileName"/>
		<%
 		BufferedReader input = new BufferedReader(new FileReader(errorFile.toString()));
		String line = "";
		%>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<sakai:group_heading>
			<fmt:message key="instr.viewInvalidRecords.listInvalidRecords"/>
		</sakai:group_heading>
		<sakai:group_table>
			<tr>		
				<th><fmt:message key="prompt.studentNumber"/></th>
				<th><fmt:message key="prompt.errorMessage"/></th>
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