
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>

<sakai:html>
	<html:form action="faqcategory">
	<sakai:messages/>
	  <sakai:heading><fmt:message key="faq.category.revise"/></sakai:heading>
	  <sakai:instruction><fmt:message key="faq.required"/>&nbsp;<sakai:required/></sakai:instruction>
	  <!--<sakai:group_heading><fmt:message key="faq.category.heading"/></sakai:group_heading>-->
	  <sakai:group_table>
		<tr>
			<td>
				<label><fmt:message key="faq.field.category"/>&nbsp;<sakai:required/></label>
			</td>
			<td>
				<html:text property="category.description" size="50" maxlength="100"/>

			</td>
		</tr>
		<tr>
			<td>
				<label><fmt:message key="faq.field.datefield"/></label>
			</td>
			<td>
				<bean:write name="faqCategoryForm" property="category.modifiedOn" format="dd MMM yyyy, hh:mm"/>
			</td>
		</tr>
	</sakai:group_table>


   <sakai:actions>
    <html:hidden property="category.categoryId"/>
	<html:hidden property="action" value="saveeditcategory"/>
	<html:submit styleClass="active"><fmt:message key="button.save"/></html:submit>
	<html:cancel><fmt:message key="button.cancel"/></html:cancel>
	</sakai:actions>

	</html:form>
</sakai:html>

