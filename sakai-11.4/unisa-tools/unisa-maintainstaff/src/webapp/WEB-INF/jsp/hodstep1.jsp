<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<sakai:html>

<html:form action="/myUnisaHod"> 
<sakai:messages/>
<sakai:messages message="true"/>
   <table>
        <tr> 
         <td>
		<fmt:message key="step1.instruction1"/></td></tr>
		<tr>
		<td>
		<fmt:message key="step1.instruction2"/></td>
		
		  <td>
		  
		  <html:select property="departmentTypeForReport">
			<html:options collection="departmentList" property="value" labelProperty="label" />
		</html:select>
		</td>
		</tr>
		</table>  </br>
		
		    <sakai:actions>
			<html:submit property="action">
		<fmt:message key="button.getDetail"/>
		</html:submit>
		</sakai:actions> <br/><br/>
		
				<sakai:flat_list>
				
			    	<tr>
					<th align="left"><fmt:message key="function.code"/></th>
					<th align="left"><fmt:message key="function.number"/></th>
					<th align="left"><fmt:message key="function.title"/></th>
					<th align="left"><fmt:message key="function.initials"/></th>
					<th align="left"><fmt:message key="function.name"/></th>
					<th align="left"><fmt:message key="function.remove"/></th>
					</tr>
					<logic:iterate name="hoddisplayform" property="departmentOrderList"  id="record" indexId="i" >
						<tr>
							<td><bean:write name="record" property="userCode"/>&nbsp;</td>
							<td><bean:write name="record" property="pnumber" filter="false"/>&nbsp;</td>
							<td><bean:write name="record" property="title"/>&nbsp;</td>
							<td><bean:write name="record" property="initials"/>&nbsp;</td>							
							<td><bean:write name="record" property="surName"/>&nbsp;</td>
							
							<td><html:multibox property="selectedDepartment"> <bean:write name="record" property="pnumber"/>
							</html:multibox>
							</td>
							
						</tr>
					</logic:iterate>
					</sakai:flat_list>
				
               <fmt:message key="step1.instruction3"/>				
			
				
				<sakai:actions>
		
		<html:submit property="action">
		<fmt:message key="button.addStandin"/>
		</html:submit>
		
		 
	   <html:submit property="action">
		<fmt:message key="button.removeStandin"/>
		</html:submit>
		
		<html:submit property="action">
			<fmt:message key="button.cancel"/>
		</html:submit>
				
		
	   </sakai:actions>
	
	</html:form>
	</sakai:html>
	

	
	
	