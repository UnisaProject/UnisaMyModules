<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>

<sakai:html>
	<html:form action="/mdRpm">
	<html:hidden property="currentPage" value="supervisorHistory"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.supervisorHisory"/>
		</sakai:heading>
		<sakai:flat_list>		
			<tr>
				<th><fmt:message key="column.supervisorHistory.date"/></th>					
				<th><fmt:message key="column.supervisorHistory.name"/></th>	
				<th><fmt:message key="column.supervisorHistory.supervisors"/></th>	
				<th><fmt:message key="column.supervisorHistory.comment"/></th>
			</tr>
				<logic:iterate name="supervisorHistoryList" id="record" indexId="index">
					<tr>
						<td  style="vertical-align:middle"><bean:write name="record" property="updatedOn"/></td>
						<td  style="vertical-align:middle"><bean:write name="record" property="updatedBy"/></td>
						<td>
							<sakai:group_table>							
								<logic:iterate name="record" property="promotorList" id="rec" indexId="ind">
									<tr>
										<td><logic:equal name="rec" property="isSupervisor" value="true">Supervisor</logic:equal>
											<logic:equal name="rec" property="isSupervisor" value="false">Assistant</logic:equal>
										</td>							
										<td><bean:write name="rec" property="person.name"/>&nbsp;(<bean:write name="rec" property="person.personnelNumber"/>)</td>
									</tr>
								</logic:iterate>
							</sakai:group_table>
						</td>
						<td  style="vertical-align:middle"><bean:write name="record" property="comment"/></td>									
					</tr>
				</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>	
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>	
		</sakai:actions>	
	</html:form>
</sakai:html>		