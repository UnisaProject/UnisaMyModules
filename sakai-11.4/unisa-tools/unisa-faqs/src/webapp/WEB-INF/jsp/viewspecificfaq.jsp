
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>
<sakai:html>

         
     <c:if test="${faqListForm.editFAQContent ==true}"> 
       <sakai:tool_bar>
                   <html:link href="faqcontent?action=edit"
                     paramName="faqContentForm" paramProperty="content.contentId" paramId="content.contentId">
                     <fmt:message key="link.editfaq"/>
                   </html:link>                 
             <c:if test="${faqListForm.addFAQContent ==true}">             
                <html:link href="faqcontent.do?action=input">
                     <fmt:message key="link.addfaq"/>
                </html:link>       
                   </c:if>
                  <c:if test="${faqListForm.deleteFAQContent ==true}">
                     <html:link href="faqlist.do?action=removeSpecific"
                          paramName="faqContentForm" paramProperty="content.contentId" paramId="specificContentId">
                      <fmt:message key="link.remove"/>
                     </html:link>
                   </c:if>                              
          </sakai:tool_bar>
         </c:if>
          


        <html:form action="faqcontent">

        <sakai:heading><fmt:message key="faq.add.heading"/></sakai:heading>

        <div align="right">
        <sakai:actions>
                <html:hidden property="previousId"/>
                <html:hidden property="nextId"/>
                <html:hidden property="content.contentId"/>

                <logic:notEmpty name="faqContentForm" property="previousId">
                        <html:submit property="action"><fmt:message key="button.previous"/></html:submit>
                </logic:notEmpty>
                <logic:empty name="faqContentForm" property="previousId">
                        <html:submit disabled="true"><fmt:message key="button.previous"/></html:submit>
                </logic:empty>

                <html:submit property="action"><fmt:message key="button.return"/></html:submit>

                <logic:notEmpty name="faqContentForm" property="nextId">
                        <html:submit property="action"><fmt:message key="button.next"/></html:submit>
                </logic:notEmpty>
                <logic:empty name="faqContentForm" property="nextId">
                        <html:submit disabled="true"><fmt:message key="button.next"/></html:submit>
                </logic:empty>
        </sakai:actions>
        </div>

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
                                <label><strong><fmt:message key="faq.questiontitle"/></strong></label>
                        </td>
                        <td>
                                <bean:write name="faqContentForm" property="content.question"/>
                        </td>
                </tr>
                <tr>
                        <td>
                                <label><strong><fmt:message key="faq.answer"/></strong></label>
                        </td>
                        <td>
                                <bean:write name="faqContentForm" property="content.answer" filter="false"/>
                        </td>
                </tr>
                <tr>
                        <td>
                                <label><strong><fmt:message key="faq.lastmodified"/></strong></label>
                        </td>
                        <td>
                                <bean:write name="faqContentForm" property="content.modifiedOn" format="yyyy-MM-dd"/>
                        </td>
                </tr>
        </sakai:group_table>

        </html:form>
</sakai:html>