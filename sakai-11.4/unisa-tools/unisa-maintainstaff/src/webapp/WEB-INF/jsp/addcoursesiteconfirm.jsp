<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<!--  getElementById -->
<script language="JavaScript">
	function saveCourseSite() {
		document.courseForm.buttonFinish.disabled=true;
		document.courseForm.action = 'course.do?act=Finish'
		document.courseForm.submit();
		
	}
</script>

<sakai:html>
<html:form action="/course"> 
	<html:hidden property="atstep" value="save"/>
	<html:hidden property="display" value="displaycourse"/>
    <html:hidden property="changeview" value=""/>
	
	<sakai:group_heading>
		<fmt:message key="course.confirm.info"/> <bean:write name="courseForm" property="course"/> <bean:write name="courseForm" property="acadPeriodDesc"/>
	</sakai:group_heading>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<sakai:flat_list>
		<tr>
			<th> # </th>
			<th align="left"> <fmt:message key="course.tab.name"/> </th>
			<th align="left"> <fmt:message key="course.tab.role"/> </th>
		</tr>
		<logic:equal name="courseForm" property="selectedView" value="L">
			<tr>
				<td> 1 </td>
				<td> <bean:write name="courseForm" property="newPrimlName"/> </td>
				<td> Primary Lecturer </td>
			</tr>
		</logic:equal>
		<logic:iterate name="courseForm" property="courseList" id="record" indexId="i">
			<logic:notEqual name="record" property="networkCode" value="">
				<tr>
					<logic:equal name="courseForm" property="selectedView" value="L">
						<td> <%= i+2 %> </td>
					</logic:equal>
					<logic:equal name="courseForm" property="selectedView" value="E">
						<td> <%= i+1 %> </td>
					</logic:equal>
					<td> <bean:write name="record" property="name"/> </td>
					<td> <bean:write name="record" property="roleDesc"/></td>
				</tr>
			</logic:notEqual>
		</logic:iterate>
	</sakai:flat_list>
	</p>
	
	<p>
	<sakai:actions>
		<!-- ->html:submit styleClass="active" title="finish" property="act" -->
			 <!-- >fmt:message key="button.finish" / -->
		<!-- >/html:submit -->
		<input name="buttonFinish" type="button" value="Finish" onClick="saveCourseSite()"/>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.back"/>
		</html:submit>
		<html:submit styleClass="active" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>

</html:form>

</sakai:html>