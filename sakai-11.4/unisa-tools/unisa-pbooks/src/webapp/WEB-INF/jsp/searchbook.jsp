<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
<html:form action="prebook">
		<sakai:instruction>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"><b><fmt:message key="label.addnewbookinstructionforE"/>
		 <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></b><p/></p></logic:equal>
		 <logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><b><fmt:message key="label.addnewbookinstructionforR"/>
		 <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></b><p/></p></logic:equal>
		 <logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><b><fmt:message key="label.addnewbookinstruction"/>
		 <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></b><p/></p></logic:equal>
		 </sakai:instruction>

	
	<sakai:messages/>
	<sakai:instruction>
	 <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">	
		<fmt:message key="function.searchbookmsg"/>		
		<sakai:group_heading>
			<fmt:message key="label.searchbookinstruction"/>
		</sakai:group_heading>
		</logic:notEqual>
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	
			<fmt:message key="function.searchbookmsgforE"/>	
		<sakai:group_heading>
			<fmt:message key="label.searchbookinstructionforereserves"/>
		</sakai:group_heading>
		</logic:equal>
	
		<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	
		<sakai:instruction>
		
			<fmt:message key="label.choosetype"/>
			<div></div>
			<html:radio name="bookMenuForm" property="searchOption" value="Journal"><fmt:message key="function.journalarticleOrOthertype"/></html:radio>
			<html:radio name="bookMenuForm" property="searchOption" value="Law Report"><fmt:message key="function.lawreport"/></html:radio>
			<html:radio name="bookMenuForm" property="searchOption" value="Book Chapter"><fmt:message key="function.extract"/></html:radio>

		</sakai:instruction>
		</logic:equal>
	
		<fmt:message key="function.searchbookinstruction2"/>
	</sakai:instruction>

		<p/>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="label.tableheadertitle"/></td>
				<td><html:text property="txtTitle"></html:text></td>
			</tr>
			<tr>
				<td><fmt:message key="label.tableheaderauthor"/></td>
				<td><html:text property="txtAuthor"></html:text></td>
			</tr>
			
		</sakai:group_table>
		<sakai:instruction>
			<fmt:message key="function.addnewbookeginstruction"/>
			<fmt:message key="function.addnewbookeginstruction1"/>
		</sakai:instruction>
		
		<input type="hidden" name="cancelOption" value="TOCONFIRMBOOKS">
		
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.search"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	
	</html:form>

</sakai:html>
	
	