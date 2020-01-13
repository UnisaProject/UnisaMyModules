<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>

<sakai:html>

	 <c:if test="${faqListForm.addFAQContent ==true}">
	   <sakai:tool_bar>
		  <html:link href="faqcontent.do?action=input">
		  <fmt:message key="link.addfaq"/>
	    </html:link>
	   
	   <c:if test="${faqListForm.addFAQContent ==true}">
		 <html:link href="faqcategory.do?action=inputcategory">
		   <fmt:message key="link.createcategory"/>
		   </html:link>
		 </c:if>
		</sakai:tool_bar>
	</c:if>


	<html:form action="faqlist">
	<sakai:messages/>
	
	<logic:equal name="usertype" value="Instructor">	
	   <sakai:heading><fmt:message key="faq.faqview.heading"/>
	                 <bean:write name="faqListForm" property="siteId"/>
	    </sakai:heading>
	 </logic:equal>
	  <logic:equal name="usertype" value="">
	  <sakai:heading><fmt:message key="faq.faqstudentview.heading"/></sakai:heading>
	  </logic:equal>
	  <sakai:instruction><fmt:message key="faq.faqview.instruction"/></sakai:instruction>
	  <sakai:flat_list>

		<tr>
			<th align="left"><fmt:message key="faq.viewfaq.heading"/></th>
			<th align="left"><fmt:message key="faq.viewfaq1.heading"/></th>
			<c:if test="${(faqListForm.deleteFAQCategory ==true)||(faqListForm.deleteFAQContent ==true && faqListForm.faqExist == true)}">				
			   		 <th align="left"><fmt:message key="faq.viewfaq2.heading"/></th>			    		 			
			</c:if>
		</tr>

	<logic:notEmpty name="faqListForm" property="categories">
			<logic:iterate name="faqListForm" property="categories" id="c" indexId="cindex">
			<tr>
				<td>
					<logic:equal name="c" property="expanded" value="true">
		
					   <html:link href="faqlist.do?action=collapse" paramName="c" paramProperty="categoryId" paramId="expandCategoryId">
						<img src="/library/image/sakai/collapse.gif"/>
						<strong><bean:write name="c" property="description"/></strong>
						</html:link>
					</logic:equal>

					<logic:equal name="c" property="expanded" value="false">															
					
						<html:link href="faqlist.do?action=expand" paramName="c" paramProperty="categoryId" paramId="expandCategoryId">
						<img src="/library/image/sakai/expand.gif"/>
						<strong><bean:write name="c" property="description"/></strong>
						</html:link>

					</logic:equal>
				<c:if test="${faqListForm.editFAQCategory ==true}">
					<!--<logic:equal name="usertype" value="Instructor">-->
						<html:link href="faqcategory?action=editcategory" paramName="c" paramProperty="categoryId" paramId="category.categoryId">
						[ Edit ]
						</html:link>
					<!--</logic:equal>-->
					</c:if>

				</td>
				<td><bean:write name="c" property="modifiedOn" format="yyyy-MM-dd" 	/></td>
				<c:if test="${faqListForm.deleteFAQCategory ==true }">
				<!--<logic:equal name="usertype" value="Instructor">-->
					<td><html:checkbox name="faqListForm" property='<%= "categoryIndexed["+cindex+"].remove" %>' /></td>
				<!--</logic:equal>-->
				</c:if>
			</tr>

			<logic:equal name="c" property="expanded" value="true">
				<logic:iterate name="c" property="contents" id="q" indexId="qindex">
				<tr>
					<td>&nbsp;&nbsp;
						<html:link href="faqcontent.do?action=view" paramName="q" paramProperty="contentId" paramId="content.contentId">

						 <bean:write name="q" property="question"/></html:link>
						<c:if test="${faqListForm.editFAQContent ==true}"> 
						<!--<logic:equal name="usertype" value="Instructor">-->
							<html:link href="faqcontent?action=edit" paramName="q" paramProperty="contentId" paramId="content.contentId">
							[ Edit ]
							</html:link>
						<!--</logic:equal>-->
						</c:if>
					</td>
					<td>
						<bean:write name="q" property="modifiedOn" format="yyyy-MM-dd"/>
					</td>
					
					<c:if test="${faqListForm.deleteFAQContent ==true}">
					<!--<logic:equal name="usertype" value="Instructor">-->
						<td><html:checkbox name="faqListForm" property='<%= "categoryIndexed["+cindex+"].contentIndexed["+qindex+"].remove" %>'/></td>
					<text >
					<!--</logic:equal>-->
					</c:if>
				</tr>
				</logic:iterate>
				
				
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
			</logic:equal>
			</logic:iterate>
	</logic:notEmpty>
	</sakai:flat_list>
	<logic:empty name="faqListForm" property="categories">
		<fmt:message key="faq.notavailable"/>
	</logic:empty>
	<logic:notEmpty name="faqListForm" property="categories">
		
		<c:if test="${(faqListForm.deleteFAQCategory ==true)||(faqListForm.deleteFAQContent ==true && faqListForm.faqExist == true)}">
			<sakai:actions>
			<html:hidden property="action" value="confirmRemove"/>
			<html:submit styleClass="active"><fmt:message key="button.remove"/></html:submit>
			<html:cancel><fmt:message key="button.clear"/></html:cancel>
			</sakai:actions>
		</c:if>

	</logic:notEmpty>

	</html:form>
</sakai:html>


