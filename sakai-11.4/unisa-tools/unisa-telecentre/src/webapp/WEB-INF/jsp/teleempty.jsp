<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>	
	<sakai:messages/>
	<html:form action="/telecentre.do">	
	<sakai:group_heading>
	<fmt:message key="telecentre.heading"/>                
	</sakai:group_heading>
	 <logic:equal name="telecentreForm" property="aboutTele" value="N" >
	<tr><td>
	 <sakai:instruction><fmt:message key="no.access"/></sakai:instruction>
	 </td></tr>
	 </logic:equal>
	 <logic:equal name="telecentreForm" property="aboutTele" value="A" >
	   <sakai:group_table>
	   <tr><td>
	 <fmt:message key="telecentre.heading"/>
	 </td></tr>
	 <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.back"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>    
		 </sakai:group_table>
	 </logic:equal>
      <logic:equal name="telecentreForm" property="aboutTele" value="M" >
       <sakai:group_table>
	   <tr><td>
	 <fmt:message key="telecentre.heading"/>
	 </td></tr>
	 <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.back"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>    
		 </sakai:group_table> 
	 </logic:equal>
	  <logic:equal name="telecentreForm" property="aboutTele" value="K" >
       <sakai:group_table>
	   <tr><td>
	 <fmt:message key="telecentre.heading"/>
	 </td></tr>
	 <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.secondback"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>    
		 </sakai:group_table> 
	 </logic:equal>
	  <logic:equal name="telecentreForm" property="aboutTele" value="P" >
       <sakai:group_table>
	   <tr><td>
	 <fmt:message key="telecentre.heading"/>
	 </td></tr>
	 <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.secondback"/>
			   </html:submit>
		     </sakai:actions>
		     </td>
		 </tr>    
		 </sakai:group_table> 
	 </logic:equal>
	 
	 </html:form>
</sakai:html>