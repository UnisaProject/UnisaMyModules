<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<!--  onchange="setTotalSelected();" -->

<script language="JavaScript">
	function setAction() {
		document.courseForm.action = 'course.do?act=courseDisplay&changeview=changed';
		document.courseForm.submit();
	}
	function setTotalSelected() {
		document.courseForm.action = 'course.do?act=totalSelected';
		document.courseForm.submit();
	}
</script>

<sakai:html>
<html:form action="/course.do"> 
<html:hidden property="atstep" value="confirm"/> 
<html:hidden property="changeview" value=""/>
	
	<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
		<sakai:tool_bar>
			<ul>
				<li><logic:equal name="courseForm" property="selectedView"  value="L">
						<html:link href="course.do?act=addCourse">
							<fmt:message key="link.addregper" />
						</html:link>
					</logic:equal></li>

				<li><logic:equal name="courseForm" property="selectedView" value="L">
                	<html:link href="course.do?act=updatePrimLecturer">
							<fmt:message key="link.updprimlecturer" />
						</html:link>
					</logic:equal></li>

				<li><html:link href="course.do?act=addPersons">
						<input type="image" src="/library/image/sakai/add.png" />
						<fmt:message key="link.addpersonthiscourse" />
					</html:link></li>

				<li><html:link href="course.do?act=viewLogs">
						<fmt:message key="link.viewchangelogs" />
					</html:link></li>
			</ul>
		</sakai:tool_bar>
	</logic:equal>
	<table width='100%'>
		<tr>
			<td align='right'>
			<html:link href="course.do?act=courseDisplay"><input type="image" align=right src="/library/image/printer.png" onClick="window.print()"/></html:link>
			</td>
		</tr>
	</table>
	
	<sakai:group_heading>
		<fmt:message key="course.info1"/> <bean:write name="courseForm" property="course"/> <br> <br>
	</sakai:group_heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

  
		<logic:notEqual name="courseForm" property="displayCourseDetails" value="displaycourse">
		<fmt:message key="course.info.insturction"/> 

		</logic:notEqual>
	<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
	<logic:equal name="courseForm" property="displayCourseDetails" value="displaycourse">
	
		<fmt:message key="course.info2"/> <br>
		<fmt:message key="course.info3"/> 
		<fmt:message key="course.info4"/>
	</logic:equal>
	</logic:equal>
  <table>

	<tr>
	<td> <fmt:message key="course.view"/> </td>
	<td> <html:select property="selectedView" onchange="setAction();">
		<html:options collection="viewOptionList" property="value" labelProperty="label"/>
	</html:select> </td> </tr>

	 
    <tr>
	<td> <fmt:message key="course.year"/> <sakai:required/> </td>
     <td><html:select name="courseForm" property="year">
	<html:options collection="yearOptions" property="value" labelProperty="label" />
	</html:select> </td>
	</tr>
	
	<tr>
	<td>  <fmt:message key="course.acadperiod"/> <sakai:required/>  </td>
	<td><html:select name="courseForm" property="acadPeriod">
	<html:options collection="acadPeriodOptions" property="value" labelProperty="label"/>
	</html:select> </td> </tr>
    </table>
    
	<sakai:actions>
	<html:hidden property="display" value="displaycourse"/>
		<html:submit property="act" >
			<fmt:message key="button.display"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	
	
	<logic:equal name="courseForm" property="displayCourseDetails" value="displaycourse">
	 <logic:equal name="mainForm" property="userPermission" value="MAINTAIN">
		<table width='100%'>
			<tr>
				<td>
					<html:link href="course.do?act=selectAll">
						<fmt:message key="select.all"/>
					</html:link> |
					<html:link href="course.do?act=selectAllVisible">
						<fmt:message key="select.all.vis"/>
					</html:link> |
					<html:link href="course.do?act=unSelectAll">
						<fmt:message key="unselect.all"/>
					</html:link> |
				</td>
				<td align='right'> 
					<html:link href="course.do?act=addPersons">
						<input type="image" src="/library/image/sakai/add.png"/>
						<fmt:message key="link.addpersonthiscourse"/>
					</html:link>
				</td>
			</tr>
		</table>
	</logic:equal>


	<p>
	
	<sakai:flat_list>
		<tr>
			<th><fmt:message key="course.tab.role"/></th>
			<th><fmt:message key="course.tab.name"/></th>
			<th><fmt:message key="course.tab.network"/></th>
			<th><fmt:message key="course.tab.staffno"/></th>
			<logic:equal name="courseForm" property="selectedView" value="L">
				<th><fmt:message key="course.tab.mark"/></th>
			</logic:equal>
			<logic:equal name="courseForm" property="selectedView" value="E">
				<th><fmt:message key="course.tab.paperno"/></th>
			</logic:equal>
			<th><fmt:message key="course.tab.status"/></th>
			<logic:equal name="mainForm" property="userPermission" value="MAINTAIN">
				<th><fmt:message key="course.tab.remove"/></th>
			</logic:equal>
		</tr>
		<logic:notEmpty name="courseForm" property="courseList">
			<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
				
				<logic:notEqual name="courseForm" property="prevAcadPeriod" value="${record.acadPeriodDesc}">
					<jsp:setProperty name="courseForm" property="prevAcadPeriod" value="${record.acadPeriodDesc}"/>
					<logic:iterate name="courseForm" property="periodHeadings" id="record2" indexId="j">										
						<logic:equal name="record2" property="periodDesc" value="${record.acadPeriodDesc}">
							
							<logic:equal name="record2" property="expand" value="YES">
								<tr><td> &nbsp;</td></tr>
								<tr><td colspan='5'>
								<logic:equal name="courseForm" property="selectedView" value="L">
									<html:link href="course.do?act=collapse&periodHeadId=${record.acadPeriodDesc}">
										<img src="/library/image/sakai/collapse.gif"/>
										<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
										<logic:equal name="courseForm" property="selectedView" value="J">
									<html:link href="course.do?act=collapse&periodHeadId=${record.acadPeriodDesc}">
										<img src="/library/image/sakai/collapse.gif"/>
										<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
								<logic:equal name="courseForm" property="selectedView" value="E">
									<html:link href="course.do?act=collapse&periodHeadId=${record.acadPeriodDesc}">
										<img src="/library/image/sakai/collapse.gif"/>
										<strong><fmt:message key="person.add.experiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
								</td>
									<logic:equal name="courseForm" property="selectedView" value="L">
									<td>&nbsp;</td>
								</logic:equal>
								
								<logic:equal name="courseForm" property="selectedView" value="E">
									<td>&nbsp;</td>
								</logic:equal>
								<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
									<td> <html:checkbox name="courseForm" property='<%= "recordIndexed2["+j+"].remove" %>'/></td>
								</logic:equal>
								</tr>	
							</logic:equal>
							
							<logic:equal name="record2" property="expand" value="NO">	
								<tr><td> &nbsp;</td></tr>
								<tr><td colspan='5'>
								<logic:equal name="courseForm" property="selectedView" value="L">
									<html:link href="course.do?act=expand&periodHeadId=${record.acadPeriodDesc}" >
										<img src="/library/image/sakai/expand.gif"/>
										<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
									<logic:equal name="courseForm" property="selectedView" value="J">
									<html:link href="course.do?act=expand&periodHeadId=${record.acadPeriodDesc}" >
										<img src="/library/image/sakai/expand.gif"/>
										<strong><fmt:message key="course.tab.regperiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
								<logic:equal name="courseForm" property="selectedView" value="E">
									<html:link href="course.do?act=expand&periodHeadId=${record.acadPeriodDesc}" >
										<img src="/library/image/sakai/expand.gif"/>
										<strong><fmt:message key="person.add.experiod"/>: <bean:write name="record2" property="periodDesc"/></strong>
									</html:link>
								</logic:equal>
								</td>
									<logic:equal name="courseForm" property="selectedView" value="L">
									<td>&nbsp;</td>
								</logic:equal>
								<logic:equal name="courseForm" property="selectedView" value="E">
									<td>&nbsp;</td>
								</logic:equal>
								<td>
									<logic:equal name="courseForm" property="userPermission" value="MAINTAIN"> 
										<html:checkbox name="courseForm" property='<%= "recordIndexed2["+j+"].remove" %>'/>
									</logic:equal>
								</td>
								</tr>	
							</logic:equal>						
						</logic:equal>
					</logic:iterate>
				</logic:notEqual>
				
				<logic:iterate name="courseForm" property="periodHeadings" id="record3" indexId="k">
						<logic:equal name="record3" property="periodDesc" value="${record.acadPeriodDesc}">
							<logic:equal name="record3" property="expand" value="YES">
								<logic:equal name="record" property="status" value="Inactive" >
									<tr bgcolor="#F0F0F0">
								</logic:equal>
								<logic:equal name="record" property="status" value="Active" >
									<tr>
								</logic:equal>
								
									<td>
										<logic:notEqual name="record" property="role" value="PRIML" >
											<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
												<html:select name="courseForm" property='<%= "recordIndexed["+i+"].role" %>'>
													<html:options collection="roleOptions" property="value" labelProperty="label"/>
												</html:select>
											</logic:equal>
											<logic:notEqual name="courseForm" property="userPermission" value="MAINTAIN">
												<bean:write name="record" property="roleDesc"/>
											</logic:notEqual>
										</logic:notEqual>
										<logic:equal name="record" property="role" value="PRIML" >
											<bean:write name="record" property="roleDesc"/>
										</logic:equal>
									</td>
									<td><bean:write name="record" property="name"/></td>
									<td><bean:write name="record" property="networkCode"/></td>
									<td>
										<logic:notEqual name="record" property="staffNo" value="1">
											<bean:write name="record" property="staffNo"/>
										</logic:notEqual>
									</td>
									<logic:equal name="courseForm" property="selectedView" value="L">
										<td><bean:write name="record" property="percentage"/></td>										
									</logic:equal>						
									
									<logic:equal name="courseForm" property="selectedView" value="E">
										<td><bean:write name="record" property="paperNo"/></td>
									</logic:equal>
									<td><bean:write name="record" property="status"/></td>
									<td>
										<logic:notEqual name="record" property="role" value="PRIML" >
											<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
												<html:checkbox name="courseForm" property='<%= "recordIndexed["+i+"].remove" %>'/>
											</logic:equal>
										</logic:notEqual>
									</td>
								</tr>
							</logic:equal>
						</logic:equal>
				</logic:iterate>		
			</logic:iterate>
		</logic:notEmpty>	
		<logic:empty name="courseForm" property="courseList">
			<tr><td colspan='6'><fmt:message key="course.no.records"/></td></tr>
		</logic:empty>			
	</sakai:flat_list>
	</p>
	
	<p>
	<sakai:actions>
		<logic:equal name="courseForm" property="userPermission" value="MAINTAIN">
			<html:submit property="act">
				<fmt:message key="button.update"/>
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
	</logic:equal>
	
</html:form>

</sakai:html>