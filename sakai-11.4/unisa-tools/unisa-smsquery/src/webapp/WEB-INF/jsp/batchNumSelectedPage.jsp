<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsquery.ApplicationResources"/>

<sakai:html>
	<html:form action="/smsQuery">
		<html:hidden property="currentPage" value="batchNumSelectedPage"/>
		<html:hidden property="prevPage" value="batchNumSelectedPage"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
				<fmt:message key="heading.results"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td width="38%"><fmt:message key="page.batchNumber"/></td>
				<td width="37%"><bean:write name="smsQueryForm" property="searchBatchNumber"/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.datesent"/></td>
				<td><bean:write name="smsQueryForm" property="dateSent"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.message"/></td>
				<td  colspan=2><bean:write name="smsQueryForm" property="message"/></td>
			</tr>
			<tr>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td><td colspan=2>&nbsp;</td>
			</tr>
			<tr>
			   <td>&nbsp;</td><td> <html:radio property="view" value="All items" ><fmt:message key="page.allitems"/> </html:radio></td>
			   <td colspan=2>&nbsp;</td>
			</tr>
			 <tr>
			    <td><fmt:message key="page.view"/></td>
			    <td> <html:radio property="view" value="Only Delivered items" ><fmt:message key="page.onlydelivereditems"/> </html:radio></td>
			   <td colspan=2>&nbsp;</td>
			  </tr>
			  <tr>
			   <td>&nbsp;</td>
			   <td> <html:radio property="view" value="Only Undelivered items"><fmt:message key="page.onlyundelivereditems"/> </html:radio></td>
			   <td>&nbsp;</td>
			   </tr>
			<tr>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
			   <td><fmt:message key="page.startrefnumber"/>
			   <td colspan="2"><html:text name="smsQueryForm" property="studentNumber" size="20" maxlength="8"/><fmt:message key="page.examplestunumber"/></td>
			</tr>
		<tr>
		<td>
		    <sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>
			</sakai:actions>	
		</td>				
		  		<td>&nbsp;</td>
		        <td>&nbsp;</td>
		</tr>
		<logic:empty name="smsQueryForm"  property="displayList">
		   <tr>
		      <td width="25%">
		           <html:hidden property="currentPage" value="batchListPage"/>
		      </td>
		      <td  width="25%">
		           <html:hidden property="currentPage" value="batchListPage"/>
		      </td>
		      <td  width="25%">
		          <html:hidden property="currentPage" value="batchListPage"/>
		      </td>
		      <td  width="25%">
		          <html:hidden property="currentPage" value="batchListPage"/>
		      </td>
		   </tr>
		 
		 </logic:empty>
		<logic:notEmpty name="smsQueryForm"  property="displayList">
			  <tr>
			        <td >
			            <sakai:heading>
				           <fmt:message key="heading.results"/>
		                </sakai:heading>	
		           </td>
		            <td>&nbsp;</td>
		            <td>		
					<sakai:actions>
						<logic:equal name="smsQueryForm"  property="pageDownFlag" value="Y">
							<html:submit property="action">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="smsQueryForm"  property="pageDownFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="action">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="smsQueryForm"  property="pageUpFlag" value="Y">
							<html:submit property="action">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="smsQueryForm"  property="pageUpFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
			 </tr>
		 	  <sakai:flat_list>		
			<tr>
				<th><fmt:message key="page.log.colheading.refNumber"/></th>
				<th><fmt:message key="page.log.colheading.cellphone"/></th>
				<th><fmt:message key="page.log.colheading.messageStatus"/></th>
		     </tr>
			<logic:iterate name="smsQueryForm" property="displayList" id="record" indexId="index">
				<bean:define id="batchNumber" name="record" property="batchNr"></bean:define>
				<bean:define id="sequenceNumber" name="record" property="sequenceNr"></bean:define>
				<%
					java.util.HashMap params = new java.util.HashMap();
					params.put ("batchNr",batchNumber);
					params.put ("sequenceNr",sequenceNumber);
					request.setAttribute("paramsName",params);
				%>
				<tr>						
					<td  width="30%">
						<bean:write name="record" property="refNumber"/>
						<html:link href="smsQuery.do?action=getLogEntryDetail" name="paramsName">
						<fmt:message key="link.view"/>	
						</html:link>						
					</td>
					<td width="25%"><bean:write name="record" property="cellNumber"/></td>
					<td  width="30%"><bean:write name="record" property="messageStatus"/></td>
			</tr>
			</logic:iterate>
		  </sakai:flat_list>
	    </logic:notEmpty>
	   </sakai:group_table>
	 </html:form>
</sakai:html>