<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">
	<logic:equal name="satbookMonthlyForm" property="systemID" value="1">
	<html:hidden property="adminstep" value="venue"/>
    <sakai:heading><fmt:message key="adminclassrooms.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="adminclassroomsedit.instruction"/> </sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/></sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="adminclassrooms.tableheading.classrooms"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionDesc" size="20" maxlength="50"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.contactperson"/> <sakai:required/></td>
			<td><html:text name="classroom" property="contactPerson" size="20" maxlength="50"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber1"/> <sakai:required/></td>
			<td><html:text name="classroom" property="contactNum1" size="20" maxlength="30"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber2"/></td>
			<td><html:text name="classroom" property="contactNum2" size="20" maxlength="30"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline1"/><sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress1" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline2"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress2" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline3"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress3" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline4"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress4" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addresslinePcode"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddressPcode" size="5" maxlength="4" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
			<td>
				<html:radio name="classroom" property="regionActive" value="true">
					Yes
				</html:radio>
				<html:radio name="classroom" property="regionActive" value="false">
					No
				</html:radio>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminclassrooms.button.save"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.monthlyview.adminview"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.to.monthlyview"/>
		</html:submit>
	</sakai:actions>
	</logic:equal>
	<logic:equal name="satbookMonthlyForm" property="systemID" value="2">
	
	<sakai:heading><fmt:message key="adminvenues.heading2"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="adminvenuesadd.instruction"/> </sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/></sakai:instruction>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="adminvenues.tableheading.venueName"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionDesc" size="20" maxlength="50"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.contactperson"/> <sakai:required/></td>
			<td><html:text name="classroom" property="contactPerson" size="20" maxlength="50"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber1"/> <sakai:required/></td>
			<td><html:text name="classroom" property="contactNum1" size="20" maxlength="30"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.email"/><sakai:required/></td>
			<td><html:text name="classroom" property="contactNum2" size="20" maxlength="30"></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline1"/><sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress1" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline2"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddress2" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline3"/></td>
			<td><html:text name="classroom" property="regionAddress3" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addressline4"/></td>
			<td><html:text name="classroom" property="regionAddress4" size="28" maxlength="28" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassroomsedit.addresslinePcode"/> <sakai:required/></td>
			<td><html:text name="classroom" property="regionAddressPcode" size="5" maxlength="4" ></html:text></td>
		</tr><tr>
			<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
			<td>
				<html:radio name="classroom" property="regionActive" value="true">
					Yes
				</html:radio>
				<html:radio name="classroom" property="regionActive" value="false">
					No
				</html:radio>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminvenues.button.save"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminvenues.button.cancel"/>
		</html:submit>
	</sakai:actions>
</logic:equal>
</html:form>
</sakai:html>