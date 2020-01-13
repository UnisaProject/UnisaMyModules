
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>

<sakai:html>

	<html:form action="faqlist">
	<sakai:messages/>
	  <sakai:heading><fmt:message key="faq.confirmremove.heading"/></sakai:heading>
	  <sakai:instruction><font color="red"><fmt:message key="faq.confirmremove.instruction"/></font></sakai:instruction>
	  <sakai:flat_list>
		<tr>
			<th align="left"><fmt:message key="faq.confirmremove.category"/></th>
			<th align="left"><fmt:message key="faq.confirmremove.questiontitle"/></th>
			<th align="left"><fmt:message key="faq.confirmremove.modified"/></th>
		</tr>
		<logic:iterate name="faqListForm" property="categories" id="c" indexId="cindex">
		
		<logic:equal name="c" property="remove" value="true">
		<tr>
			<td>
				<strong><bean:write name="c" property="description"/></strong>			
			</td>
			<td>
				<fmt:message key="faq.confirmremove.all"/>
			</td>
			<td>
				<bean:write name="c" property="modifiedOn" format="dd/MM/yyyy"/>
			</td>
		</tr>
		</logic:equal>
		
		<logic:equal name="c" property="remove" value="false">
			<logic:notEmpty name="c" property="contents">
			<logic:iterate name="c" property="contents" id="q">
				<logic:equal name="q" property="remove" value="true">
				<tr>
					<td><bean:write name="c" property="description"/></td>
					<td><bean:write name="q" property="question"/></td>
					<td><bean:write name="q" property="modifiedOn" format="dd/MM/yyyy"/></td>
				</tr>
				</logic:equal>
			</logic:iterate>
			</logic:notEmpty>
		</logic:equal>
		
		</logic:iterate>
   </sakai:flat_list>
   
   <sakai:actions>
		<html:hidden property="action" value="remove"/>
		<html:submit styleClass="active"><fmt:message key="button.delete"/></html:submit>
		<html:cancel><fmt:message key="button.cancel"/></html:cancel>
	</sakai:actions>

	</html:form>
</sakai:html>


