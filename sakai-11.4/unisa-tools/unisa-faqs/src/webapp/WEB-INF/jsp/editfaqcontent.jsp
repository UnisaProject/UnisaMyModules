<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>

<sakai:html>
	<html:form action="faqcontent">
	<sakai:messages/>
	
	  <c:if test="${faqListForm.addFAQContent ==true}">
	  <sakai:tool_bar>
		<html:link href="faqcategory.do?action=inputcategory">
		  <fmt:message key="link.createcategory"/>
		</html:link>
	  </sakai:tool_bar>
	  </c:if>
	  <sakai:heading><fmt:message key="faq.revisecontent"/></sakai:heading>
	  <sakai:instruction><fmt:message key="faq.instruction"/><br><fmt:message key="faq.required"/>&nbsp;<sakai:required/></sakai:instruction>
	  <!--<sakai:group_heading><fmt:message key="faq.add.heading"/></sakai:group_heading>-->
	   <sakai:group_table>
	   <tr>
			<td>
				<label><strong><fmt:message key="faq.field.category"/></strong></label>
			</td>
			<td>
				<bean:write name="faqContentForm" property="category.description"/>
			</td>
		</tr>
		<tr>
			<td>
				<label><strong><fmt:message key="faq.questiontitle"/></strong>&nbsp;<sakai:required/></label>
			</td>
			<td>

				<html:text property="content.question" size="50" maxlength="100"/>

			</td>
		</tr>
		<tr>
			<td>
				<label><strong><fmt:message key="faq.answer"/></strong>&nbsp;<sakai:required/></label>
			</td>
			<td>

				<sakai:html_area property="content.answer"/>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
    <html:hidden property="content.contentId"/>
    <html:hidden property="content.categoryId"/>

	<html:hidden property="action" value="editSave"/>
	<html:submit styleClass="active"><fmt:message key="button.save"/></html:submit>
	<html:cancel><fmt:message key="button.cancel"/></html:cancel>
	</sakai:actions>

	</html:form>
</sakai:html>

