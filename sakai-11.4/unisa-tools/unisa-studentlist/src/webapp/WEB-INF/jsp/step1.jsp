<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.studentlist.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<logic:equal name="studentlistform" property="hodLoggedIn" value="">
	<sakai:heading><fmt:message key="page.general.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step1.instruction"/>
		
	</sakai:instruction>
	<sakai:heading><u>Please Note:</u></sakai:heading><br>
		<sakai:instruction><fmt:message key="page.step1.instruction1"/></sakai:instruction>
     <sakai:instruction><fmt:message key="page.step1.instruction2"/></sakai:instruction>
	<sakai:group_heading><fmt:message key="page.step1.groupheading"/></sakai:group_heading>
	
	<html:form action="studentlistaction.do?action=displayOption">
		
		<sakai:group_table>
			<tr>
				<td>
					<html:radio property="format" value="weblist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.weblist"/>
				</td>
			</tr>
			<tr>
				<td>
					<html:radio property="format" value="tablist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.tablist"/>
				</td>
			</tr>
			<tr>
				<td>
					<html:radio property="format" value="commalist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.commalist"/>
				</td>
			</tr>
			<tr>
				<sakai:actions>
					<td><html:submit value="Continue"/></td>
				</sakai:actions>
			</tr>
	</sakai:group_table>		
	</html:form>
	</logic:equal>
			
	
	<logic:equal name="studentlistform" property="hodLoggedIn" value="hod">
	<sakai:heading><fmt:message key="page.general.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.hod.step1.instruction"/>
		
	</sakai:instruction>
		<sakai:heading><u>Please Note:</u></sakai:heading>
		<sakai:instruction><fmt:message key="page.step1.instruction1"/></sakai:instruction>
     <sakai:instruction><fmt:message key="page.step1.instruction2"/></sakai:instruction>
	<sakai:group_heading><fmt:message key="page.hod.step1.groupheading"/></sakai:group_heading>
	
	<html:form action="studentlistaction.do?action=displayOption">
		
		<sakai:group_table>
			<tr>
				<td>
					<html:radio property="format" value="weblist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.weblist"/>
				</td>
			</tr>
			<tr>
				<td>
					<html:radio property="format" value="tablist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.tablist"/>
				</td>
			</tr>
			<tr>
				<td>
					<html:radio property="format" value="commalist"></html:radio>
				</td>
				<td>
					<fmt:message key="page.step1.commalist"/>
				</td>
			</tr><br/>
			</sakai:group_table><b>AND</b>
				<sakai:group_heading><fmt:message key="page.hod.step1.groupheading2"/></sakai:group_heading>
			<p/>
					
		<html:select property="selectView">
					 <html:option value="l">Courses linked to your name</html:option>
        		    <html:option value="d">Courses linked to your department</html:option>        		    	
    	</html:select> 
    	<p/>
    	
    	<sakai:actions>
		<html:submit value="Continue"/>
		</sakai:actions>
				
	</html:form>
	</logic:equal>
	</sakai:html>