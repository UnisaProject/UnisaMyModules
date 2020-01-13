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
	    <sakai:heading><fmt:message key="adminmaterial.heading"/></sakai:heading>
	    <sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>
	
		<sakai:messages/>
		<sakai:messages message="true"/>
	
		<sakai:instruction> <fmt:message key="adminmaterialedit.instruction"/> </sakai:instruction>
	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="adminmaterialedit.material"/> <sakai:required/></td>
				<td>
					<html:text name="material" property="materialDesc" size="30" maxlength="40"> </html:text>
				</td>
			</tr><tr>
				<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
				<td>
					<html:radio name="material" property="materialAct" value="true">
						Yes
					</html:radio>
					<html:radio name="material" property="materialAct" value="false">
						No
					</html:radio>
				</td>
			</tr>
		</sakai:group_table>
	
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="adminmaterial.button.save"/>
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
		<logic:equal name="adminForm" property="formToUse" value="bkngtype">
			<sakai:heading><fmt:message key="adminbookingtype.heading2"/></sakai:heading>
		    <sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>
		
			<sakai:messages/>
			<sakai:messages message="true"/>
		
			<sakai:instruction> <fmt:message key="adminbookingtypeadd.instruction"/> </sakai:instruction>
		
			<sakai:group_table>
				<tr>
					<td><fmt:message key="adminbookingtype.bookingtype"/> <sakai:required/></td>
					<td>
						<html:text name="vbookingTypeForm" property="bookingTypeDesc" size="30" maxlength="40"> </html:text>
					</td>
				</tr><tr>
					 <td><fmt:message key="adminclassrooms.tableheading.active"/> <sakai:required/></td>
					<td>
						<html:radio name="vbookingTypeForm" property="bookingTypeAct" value="true">
							Yes
						</html:radio>
						<html:radio name="vbookingTypeForm" property="bookingTypeAct" value="false">
							No
						</html:radio>
					</td>
				</tr>
			</sakai:group_table>
		
			<sakai:actions>
				<html:submit property="action">
					<fmt:message key="adminbookingtype.button.save"/>
				</html:submit>
				<html:submit property="action">
					<fmt:message key="adminbookingtype.button.cancel"/>
				</html:submit>
		
			</sakai:actions>
		</logic:equal>
		<logic:equal name="adminForm" property="formToUse" value="material">
			<sakai:heading><fmt:message key="adminmaterial.heading"/></sakai:heading>
		    <sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>
		
			<sakai:messages/>
			<sakai:messages message="true"/>
		
			<sakai:instruction> <fmt:message key="adminmaterialedit.instruction"/> </sakai:instruction>
		
			<sakai:group_table>
				<tr>
					<td><fmt:message key="adminmaterialedit.material"/> <sakai:required/></td>
					<td>
						<html:text name="material" property="materialDesc" size="30" maxlength="40"> </html:text>
					</td>
				</tr><tr>
					 <td><fmt:message key="adminclassrooms.tableheading.active"/> <sakai:required/></td>
					<td>
						<html:radio name="material" property="materialAct" value="true">
							Yes
						</html:radio>
						<html:radio name="material" property="materialAct" value="false">
							No
						</html:radio>
					</td>
				</tr>
			</sakai:group_table>
		
			<sakai:actions>
			<html:submit property="action">
				<fmt:message key="adminmaterial.button.save"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="adminmaterial.button.cancel"/>
			</html:submit>
		
			</sakai:actions>
		</logic:equal>
	</logic:equal> 
	</html:form>
</sakai:html>