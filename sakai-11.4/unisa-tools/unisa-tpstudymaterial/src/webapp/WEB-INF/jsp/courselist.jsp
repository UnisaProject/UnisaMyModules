<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpstudymaterial.ApplicationResources" />
<sakai:html>
<html:form action="/tpStudyMaterial.do">
	<sakai:messages />
	<sakai:messages message="true" />
			<fmt:message key="tpstudymaterial.courses.info" /><b> <bean:write name="tpStudyMaterialForm" property="studentNr"/></b><br><br>
	<fmt:message key="tpstudymaterial.courses.info1"/> <b><bean:write name="tpStudyMaterialForm" property="listdate"/></b>
	<sakai:group_heading>
		<fmt:message key="tpstudymaterial.courses.heading2" />
	</sakai:group_heading></br>
	<fmt:message key="tpstudymaterial.courses.heading3" />
	 <sakai:flat_list>
	<logic:notEmpty name="tpStudyMaterialForm" property="courseList">
	<logic:iterate name="tpStudyMaterialForm" property="courseList" id="d" indexId="dindex">	
	<tr>
			<td>
				<bean:write name="d" property="courseCode"/>-<bean:write name="d" property="academicYear"/>-<bean:write name="d" property="semister"/> &nbsp;&nbsp;&nbsp;
				 <html:hidden name="tpStudyMaterialForm"  property="academicYear" value='${d.academicYear}'/>
				 <html:hidden name="tpStudyMaterialForm"  property="semister" value='${d.semister}'/>
				 <logic:equal name="d" property="blockedStatus" value="true">
				<b><html:link	href="tpStudyMaterial.do?action=viewMaterial&academicYear=${d.academicYear}&semister=${d.semister}"
								paramName="d" paramProperty="courseCode" paramId="courseCode">View materials</html:link></b>
				 </logic:equal>
			</td>
			</tr>
	</logic:iterate>
	</logic:notEmpty>
	</sakai:flat_list>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.close"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>