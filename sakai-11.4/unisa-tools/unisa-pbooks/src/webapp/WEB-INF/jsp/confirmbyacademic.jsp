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
	<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
	<sakai:heading><p/>
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">
			<fmt:message key="function.booklist"/></logic:equal>
				<logic:equal name="bookMenuForm" property="typeOfBookList" value="R">
			<fmt:message key="function.booklistforR"/></logic:equal>
			 <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> 
	</sakai:heading>
	<p/>
<sakai:messages/>

	<sakai:flat_list>
		<tr>
			<th><fmt:message key="label.tableheaderauthor"/></th>
			<th><fmt:message key="label.tableheaderyear"/></th>
			<th><fmt:message key="label.tableheadertitle"/></th>
			<th><fmt:message key="label.tableheaderedition"/></th>
		</tr>
		<p/>
		<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="txtAuthor" /></td>
				<td><bean:write name="c" property="txtYear" /></td>
				<td><bean:write name="c" property="txtTitle" /></td>
				<td><bean:write name="c" property="txtEdition" /></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	<html:hidden property="submitOption" value="CONFIRMBYACADEMIC"/>
	<html:hidden property="cancelOption" value="BOOKLISTSTATUSVIEW"/>

	<hr>
	
	<sakai:group_table>    
        <tr>
            <td><fmt:message key="function.college"/></td>
      		<td><bean:write name="bookMenuForm" property="college"/></td>
   		</tr>
   			<tr>
    		<td><fmt:message key="function.department"/></td>
            <!-- <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td> -->
            <td><bean:write name="bookMenuForm" property="department"/></td>
        </tr></br>
          <tr>
            <td><fmt:message key="function.instuctionselect"/></td>
   		</tr>
   		
        <tr>
            <td><fmt:message key="function.chairofdept"/></td></tr>
      		<tr><td><html:radio name="bookMenuForm" property = "selectedPerson" value="COD"/>
      		<bean:write name="bookMenuForm" property="codInitials"/>&nbsp;<bean:write name="bookMenuForm" property="codSurname"/></td>
   		</tr>
   		
   		<tr><td><fmt:message key="function.standincod"/></td></tr>
   		<logic:iterate  name="bookMenuForm" property="standInChairs" id="c" indexId="cindex">
   			<tr>
				<td><html:radio name="c" property = "selectedPerson" value='${c.standInNovellcode}'/><bean:write name="c" property="standInName"/>&nbsp;
				<bean:write name="c" property="standInSurname"/></td>
				
				</tr>
   		</logic:iterate>
    </sakai:group_table>
	<p/>
	</logic:notEqual>
	
	
	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
		<sakai:heading><p/>
			<fmt:message key="function.booklistforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> 
	</sakai:heading>
	<p/>
	<sakai:messages/>
		<sakai:flat_list>
		<tr>
			<th><fmt:message key="label.tableheaderauthor"/></th>
			<th><fmt:message key="label.tableheaderyear"/></th>
			<th><fmt:message key="label.tableheadertitle"/></th>
				<th><fmt:message key="label.tableheaderebookpublicationJ"/></th>
					<th><fmt:message key="label.ebookVolumecase" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>	
		</tr>
		<p/>
		<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
			<tr>
				<td><bean:write name="c" property="txtAuthor" /></td>
				<td><bean:write name="c" property="txtYear" /></td>
				<td><bean:write name="c" property="txtTitle" /></td>
				<td><bean:write name="c" property="txtPublisher" /></td>
				<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	<html:hidden property="submitOption" value="CONFIRMBYACADEMIC"/>
	<html:hidden property="cancelOption" value="BOOKLISTSTATUSVIEW"/>
	<hr>	
		<sakai:group_table>    
        <tr>
            <td><fmt:message key="function.college"/></td>
      		<td><bean:write name="bookMenuForm" property="college"/></td>
   		</tr>
   			<tr>
    		<td><fmt:message key="function.department"/></td>
            <!-- <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td> -->
            <td><bean:write name="bookMenuForm" property="department"/></td>
        </tr></br>
          <tr>
            <td><fmt:message key="function.instuctionselect"/></td>
   		</tr>
   		
        <tr>
            <td><fmt:message key="function.chairofdept"/></td></tr>
      		<tr><td><html:radio name="bookMenuForm" property = "selectedPerson" value="COD"/>
      		<bean:write name="bookMenuForm" property="codInitials"/>&nbsp;<bean:write name="bookMenuForm" property="codSurname"/></td>
   		</tr>
   		
   		<tr><td><fmt:message key="function.standincod"/></td></tr>
   		<logic:iterate  name="bookMenuForm" property="standInChairs" id="c" indexId="cindex">
   			<tr>
				<td><html:radio name="c" property = "selectedPerson" value='${c.standInNovellcode}'/><bean:write name="c" property="standInName"/>&nbsp;
				<bean:write name="c" property="standInSurname"/></td>
				
				</tr>
   		</logic:iterate>
    </sakai:group_table>
	<p/>

	</logic:equal>
	
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.submitrequestauth"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>
