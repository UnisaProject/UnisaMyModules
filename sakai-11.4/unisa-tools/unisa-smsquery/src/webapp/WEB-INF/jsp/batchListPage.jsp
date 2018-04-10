<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsquery.ApplicationResources"/>

<sakai:html>	
	<html:form action="/smsQuery">
		<html:hidden property="currentPage" value="batchListPage"/>
		<html:hidden property="prevPage" value="batchListPage"/>			
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
				<fmt:message key="heading.smsRequestSearch"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.mandatory"/>&nbsp;<fmt:message key="page.mandatory"/>
		</sakai:instruction>		
		<sakai:group_table>	
			<tr>
				<td width="38%"><fmt:message key="page.fromDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td width="37%"><html:text name="smsQueryForm" property="searchFromDate" size="20" maxlength="10"/><fmt:message key="page.dateFormat"/></td>
				<td colspan="2">&nbsp;</td>
				
			</tr>
			<tr>
				<td><fmt:message key="page.toDate"/>&nbsp;<fmt:message key="page.mandatory"/></td>
				<td><html:text name="smsQueryForm" property="searchToDate" size="20" maxlength="10"/><fmt:message key="page.dateFormat"/></td>
				<td>&nbsp;</td>
			</tr>				
			<tr>
				<td><fmt:message key="page.includeData"/></td>
				<td><fmt:message key="page.responsibilityCode"/></td>
				<td><html:text name="smsQueryForm" property="searchResponsibilityCode" size="20" maxlength="6"/>&nbsp;&nbsp;&nbsp;<fmt:message key="page.or"/></td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td><fmt:message key="page.personnelNr"/></td>
				<td><html:text name="smsQueryForm" property="searchPersonnelNumber" size="20" maxlength="8"/></td>
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
			  <hr/>
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
				<th><fmt:message key="page.req.colheading.requestOn"/></th>
				<th><fmt:message key="page.req.colheading.batchNumber"/></th>
				<th><fmt:message key="page.req.colheading.msgCount"/></th>
				<th><fmt:message key="page.req.colheading.BudgetAmt"/></th>
				<th><fmt:message key="page.log.colheading.message"/></th>				
			</tr>
			<logic:iterate name="smsQueryForm" property="displayList" id="record" indexId="index">
				<tr>					
					<td width="25%"><bean:write name="record" property="requestDate"/></td>
					<td width="15%"><bean:write name="record" property="batchNr"/></td>
					<td width="20%"><bean:write name="record" property="messageCount"/></td>	
					<td width="20%"><bean:write name="record" property="budgetAmount"/></td>	
					<td width="30"><bean:write name="record" property="message"/></td>					
			</tr>
		  </logic:iterate>
		 </sakai:flat_list>
		</logic:notEmpty>
	   </sakai:group_table>	
	</html:form>
</sakai:html>