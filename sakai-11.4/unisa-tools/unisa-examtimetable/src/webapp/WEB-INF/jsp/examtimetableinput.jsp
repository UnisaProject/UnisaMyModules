<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examtimetable.ApplicationResources"/>

<sakai:html>
	<html:form action="/displayExamTimetable">
		<html:hidden property="currentPage" value="input"/>
		<!--place alert message beneath tool heading-->
		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<logic:equal name="examTimetableDisplayForm" property="databaseError" value="true">
                             <sakai:instruction><font color="red"><fmt:message key="page.nullstudenterror"/></font></sakai:instruction>
        </logic:equal>
        <logic:notEqual  name="examTimetableDisplayForm" property="databaseError" value="true">
               <logic:empty name="examTimetableDisplayForm" property="student">
                             <sakai:instruction><font color="red"><fmt:message key="page.nullstudenterror"/></font></sakai:instruction>
           </logic:empty>
           <logic:notEmpty name="examTimetableDisplayForm" property="student">
            <sakai:group_table>			
			<tr>
				<td><fmt:message key="page.examYear"/>&nbsp;</td>
				<td><html:text property="examYear" size="6" maxlength="4"/></td>
			</tr><tr>
				<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
				<td><html:select property="examPeriod">
						<html:options collection="examPeriods" property="code" labelProperty="engDescription"/>
					</html:select>
				</td>
			</tr><tr>
				<td><fmt:message key="page.practicalType"/>&nbsp;</td>
				<td><html:select property="practicalType">
						<html:options collection="examPracTypes" property="code" labelProperty="engDescription"/>
					</html:select>
				</td>
			</tr><tr>
				<td><fmt:message key="page.studentNumber"/>&nbsp;</td>
				<logic:equal name="examTimetableDisplayForm" property="studentUser" value="false">
					<td><html:text  name="examTimetableDisplayForm"  property="student.number" size="10" maxlength="8"/></td>
				</logic:equal>
				<logic:equal name="examTimetableDisplayForm" property="studentUser" value="true">
					<td><bean:write name="examTimetableDisplayForm" property="student.number"/></td>
				</logic:equal>
			</tr>
		 </sakai:group_table>
		 <sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>			
		 </sakai:actions>	
		</logic:notEmpty>	
		</logic:notEqual>	
	</html:form>
</sakai:html>