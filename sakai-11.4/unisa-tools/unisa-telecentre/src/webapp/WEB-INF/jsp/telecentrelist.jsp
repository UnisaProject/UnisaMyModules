<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>	
	<html:form action="/telecentre.do">	
	<sakai:messages/>

	<sakai:heading>
	<fmt:message key="telecentre.heading"/>              
	</sakai:heading>
	
	<sakai:group_heading>
	<tr>
	<td><fmt:message key="visit.heading"/></td>&nbsp;
	<td> <b><bean:write name="telecentreForm" property="userId"/></b></td>
	<td><fmt:message key="visit.for"/>&nbsp;<bean:write name="telecentreForm" property="currentYear"/></td>
	</tr>
	</sakai:group_heading>
	 <tr><td>
	  <sakai:instruction><fmt:message key="visit.info"/></sakai:instruction>
	  </td></tr>
	  <sakai:group_table>
	  <tr>
	  <td><fmt:message key="telecentre.month"/></td>
	  <td><html:select property="month">
          <html:options  property="months" />
    	  </html:select> 
	   </td></tr>
		  <tr><td>
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.display"/>
			</html:submit>
		   </sakai:actions>
		  </td>
		 <td>
		  <logic:equal name="telecentreForm" property="aboutTele" value="M" >
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.back"/>
			   </html:submit>
		     </sakai:actions>
		      </logic:equal>
		    <logic:equal name="telecentreForm" property="aboutTele" value="K" >
		   <sakai:actions>
			<html:submit styleClass="button" property="action">
			    <fmt:message key="telecentre.secondback"/>
			   </html:submit>
		     </sakai:actions>
		      </logic:equal>
		 </td></tr>  
	  </sakai:group_table>
	  <sakai:group_heading>
      <fmt:message key="tele.for"/>&nbsp;&nbsp;&nbsp;<bean:write name="telecentreForm" property="month"/>
      </sakai:group_heading><br/>
      <tr><td><fmt:message key="telecentre.date"/></td>&nbsp;&nbsp;
	 <td><bean:write name="telecentreForm" property="today"/></td></tr> 
	  <sakai:group_table>
	  <sakai:flat_list>	
	 	   	<th><fmt:message key="tele.centre"/></th>
	 	   	<th><fmt:message key="tele.date"/></th>
	 	   	<th><fmt:message key="tele.timein"/></th>
	 	   	<th><fmt:message key="tele.tomeout"/></th>
            <th><fmt:message key="tele.duration"/><fmt:message key="tele.hour"/></th>	 	  
			
		<logic:notEmpty name="telecentreForm" property="telecentreInfo">
		<logic:iterate name="telecentreForm" property="telecentreInfo" id="d" indexId="dindex">
			<tr>
			<td>
				<bean:write name="d" property="centre"/>
			</td>	
			<td>
				<bean:write name="d" property="todate"/>
			</td>	
			<td>
				<bean:write name="d" property="timeIn"/>
			</td>	
			<td>
				<bean:write name="d" property="timeOut"/>
			</td>
			<td>
				<bean:write name="d" property="duration"/>
			</td>							
	        </tr>
			
		</logic:iterate>				
	</logic:notEmpty>

	</sakai:flat_list>
	 </sakai:group_table><br/>
	 <logic:empty name="telecentreForm" property="telecentreInfo">
	  <fmt:message key="tele.novisits"/>
	  </logic:empty>
	</html:form>
</sakai:html>





