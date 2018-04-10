<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.personForm.action = 'person.do?act=personDisplay';
		document.personForm.submit();
	}
</script>

<sakai:html>
<html:form action="/person"> 
	<html:hidden property="atstep" value="confirm"/>

	<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
		<sakai:tool_bar>
		<ul>
				<li>
			<html:link href="person.do?act=addPerson">
				<fmt:message key="link.addpersoncourse"/>
			</html:link> </li></ul>
			<!--<toolbar_item>
			<html:link href="person.do?act=personLogs">
				<fmt:message key="link.viewchangelogs"/>
			</html:link>
			</toolbar_item>
		--></sakai:tool_bar>
		
	</logic:equal>
	<table width='100%'>
		<tr>
			<td align='right'>
				<html:link href="person.do?act=personDisplay"><input type="image" align="right" src="/library/image/printer.png" onClick="window.print()"/></html:link>
			</td>
		</tr>
	</table>

	<sakai:group_heading>
		<fmt:message key="person.info1"/> <bean:write name="personForm" property="personName"/> : <bean:write name="personForm" property="personNetworkCode"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>

	<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
		<p>
		<fmt:message key="person.info2"/> <br>
		<fmt:message key="person.info3"/> 
		<fmt:message key="person.info4"/>
		<fmt:message key="person.info5"/>
	</logic:equal> 
	
	<p>
	<fmt:message key="course.view"/>
	<html:select property="selectedView" onchange="setAction();">
		<html:options collection="viewOptionList" property="value" labelProperty="label"/>
	</html:select>
	</p>
	
	<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
		<p>	
		<html:link href="person.do?act=selectAll">
			<fmt:message key="select.all"/>
		</html:link> |
		<html:link href="person.do?act=selectAllVisible">
			<fmt:message key="select.all.vis"/>
		</html:link> |
		<html:link href="person.do?act=unSelectAll">
			<fmt:message key="unselect.all"/>
		</html:link> |
		</p>
	</logic:equal>
	
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="course.tab.course"/></th>
				<th><fmt:message key="course.tab.role"/></th>
				<logic:equal name="personForm" property="selectedView" value="E">
				<th><fmt:message key="course.tab.paperno"/></th>
				</logic:equal>
				<th><fmt:message key="course.tab.status"/></th>
				<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
					<th><fmt:message key="course.tab.remove"/></th>
				</logic:equal>
			</tr>
			<logic:notEmpty name="personForm" property="courseList">
				<logic:iterate name="personForm" property="courseList" id="record" indexId="i">
					<logic:notEqual name="personForm" property="prevAcadPeriod" value="${record.acadPeriodDesc}">
						<jsp:setProperty name="personForm" property="prevAcadPeriod" value="${record.acadPeriodDesc}"/>					
						<logic:iterate name="personForm" property="periodHeadings" id="record2" indexId="j">
							<logic:equal name="record2" property="periodDesc" value="${record.acadPeriodDesc}">
								
								<logic:equal name="record2" property="expand" value="YES">
									<tr><td> &nbsp;</td></tr>
									<tr><td colspan='5'>
									<logic:notEqual name="personForm" property="selectedView" value="E">
										<html:link href="person.do?act=collapse&periodHeadId=${record.acadPeriodDesc}">
											<img src="/library/image/sakai/collapse.gif"/>
											<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
										</html:link>
									</logic:notEqual>
									<logic:equal name="personForm" property="selectedView" value="E">
										<html:link href="person.do?act=collapse&periodHeadId=${record.acadPeriodDesc}">
											<img src="/library/image/sakai/collapse.gif"/>
											<strong><fmt:message key="person.add.experiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
										</html:link>
									</logic:equal>
									</td></tr>	
								</logic:equal>
								
								<logic:equal name="record2" property="expand" value="NO">	
									<tr><td> &nbsp;</td></tr>
									<tr><td colspan='5'>
									<logic:notEqual name="personForm" property="selectedView" value="E">
										<html:link href="person.do?act=expand&periodHeadId=${record.acadPeriodDesc}" >
											<img src="/library/image/sakai/expand.gif"/>
											<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
										</html:link>
									</logic:notEqual>
									<logic:equal name="personForm" property="selectedView" value="E">
										<html:link href="person.do?act=expand&periodHeadId=${record.acadPeriodDesc}" >
											<img src="/library/image/sakai/expand.gif"/>
											<strong><fmt:message key="person.add.experiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
										</html:link>
									</logic:equal>
									</td></tr>	
								</logic:equal>
							</logic:equal>
						</logic:iterate>
					</logic:notEqual>


				
					<logic:iterate name="personForm" property="periodHeadings" id="record3" indexId="k">
						<logic:equal name="record3" property="periodDesc" value="${record.acadPeriodDesc}">
							<logic:equal name="record3" property="expand" value="YES">
								<logic:equal name="record" property="status" value="Inactive" >
									<tr bgcolor="#F0F0F0">
								</logic:equal>
								<logic:equal name="record" property="status" value="Active" >
									<tr>
								</logic:equal>
									<td><bean:write name="record" property="course"/></td>
									<td>
										<logic:notEqual name="record" property="role" value="PRIML" >
											<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
												<html:select name="personForm" property='<%= "recordIndexed["+i+"].role" %>'>
													<html:options collection="roleOptions" property="value" labelProperty="label"/>
												</html:select>
											</logic:equal>
											<logic:notEqual name="personForm" property="userPermission" value="MAINTAIN">
												<bean:write name="record" property="roleDesc"/>
											</logic:notEqual>
										</logic:notEqual>
										<logic:equal name="record" property="role" value="PRIML" >
											<bean:write name="record" property="roleDesc"/>
										</logic:equal>
									</td>
									<logic:equal name="personForm" property="selectedView" value="E">
									<td><bean:write name="record" property="paperNo"/></td>
									</logic:equal>
									<td><bean:write name="record" property="status"/></td>
									<td>
										<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
											<logic:notEqual name="record" property="role" value="PRIML" >
												<html:checkbox name="personForm" property='<%= "recordIndexed["+i+"].remove" %>'/>
											</logic:notEqual>
										</logic:equal>
									</td>
								</tr>
							</logic:equal>
						</logic:equal>
					</logic:iterate>
				</logic:iterate>
			</logic:notEmpty>
			<logic:empty name="personForm" property="courseList">
				<tr><td colspan='4'> <fmt:message key="person.no.records"/> </td></tr>
			</logic:empty>
		</sakai:flat_list>
	
		<p>
		<sakai:actions>
			<logic:equal name="personForm" property="userPermission" value="MAINTAIN">
				<html:submit property="act">
					<fmt:message key="button.updateperson"/>
				</html:submit>
				<html:submit property="act">
					<fmt:message key="button.remove"/>
				</html:submit>
			</logic:equal>
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>
		</sakai:actions>	
		</p>
		
	</html:form>
</sakai:html>