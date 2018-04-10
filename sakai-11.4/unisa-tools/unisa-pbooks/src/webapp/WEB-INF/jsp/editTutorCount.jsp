<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
<html:form action="prebook">
	<logic:notEmpty name="bookMenuForm" property="bookList">
		<logic:equal name="bookMenuForm" property="countUpdateTracker" value="0">	
      		<p/>
            <sakai:heading>
               	<fmt:message key="function.booklistsforTutor"/> for <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/>
            </sakai:heading>
            <sakai:messages/>
            <sakai:messages message="true"/>
            <sakai:flat_list>
		        <tr>
			      	<th><fmt:message key="label.tableheaderauthor" /></th>
			      	<th><fmt:message key="label.tableheaderyear" /></th>
			      	<th><fmt:message key="label.tableheadertitle" /></th>
			      	<th><fmt:message key="label.tableheaderedition" /></th>
			      	<th><fmt:message key="label.tableheaderlastmodified"/></th>
	            </tr>
	               <logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
		               <tr>
				            <td><bean:write name="c" property="txtAuthor" /> <br>
						    <td><bean:write name="c" property="txtYear" /></td>
						    <td><bean:write name="c" property="txtTitle" /></td>
						    <td><bean:write name="c" property="txtEdition" /></td>
						    <td><bean:write name="bookMenuForm" property="lastmodifiedforbook"/></td>
		            	</tr>
	            	</logic:iterate>
            </sakai:flat_list>
            <p/>
            <fmt:message key="label.contractedtutors"/>&nbsp<html:text property="tutorCount" size="4" maxlength="4" value=""></html:text>
            <p/>
            <html:form action="prebook">
           		<html:hidden property="cancelOption" value="TOMAINVIEW"/>
	        	<sakai:actions>
    		    	<html:submit styleClass="active" property="action"><fmt:message key="button.tutorCount"/></html:submit>
     			    <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
     		    </sakai:actions>
            </html:form>
    	</logic:equal>
    	<logic:equal name="bookMenuForm" property="countUpdateTracker" value="1">	
      		<p/>
            <sakai:heading>
               	<fmt:message key="function.booklistsforTutor"/> for <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/>
            </sakai:heading>
            <sakai:messages/>
            <sakai:messages message="true"/>
            <sakai:flat_list>
		        <tr>
			      	<th><fmt:message key="label.tableheaderauthor" /></th>
			      	<th><fmt:message key="label.tableheaderyear" /></th>
			      	<th><fmt:message key="label.tableheadertitle" /></th>
			      	<th><fmt:message key="label.tableheaderedition" /></th>
			      	<th><fmt:message key="label.tableheaderlastmodified"/></th>
	            </tr>
	            <logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
		            <tr>
			            <td><bean:write name="c" property="txtAuthor" /> <br>
					    <td><bean:write name="c" property="txtYear" /></td>
					    <td><bean:write name="c" property="txtTitle" /></td>
					    <td><bean:write name="c" property="txtEdition" /></td>
					    <td><bean:write name="bookMenuForm" property="lastmodifiedforbook"/></td>
		            </tr>
	           	</logic:iterate>		             
            </sakai:flat_list>
            <p/><fmt:message key="label.contractedtutors"/>&nbsp<bean:write name="bookMenuForm" property="tutorCount"/>
            <p/><hr><p/>
            <fmt:message key="function.booklistsforTutorHeading"/>
            <p/>
            <fmt:message key="function.instructionforTutor"/>
            <p/>
            
            <sakai:flat_list>
            	<tr>
            		<td><b>DISS</b></td>
            	</tr>
            	 <logic:iterate name="bookMenuForm" property="dissAuthNames" id="d" indexId="dindex">
	             	<tr>
			            <td>
			            	<html:radio name="bookMenuForm" property="selectedDissName" value="${d}">
            					<bean:write name="d"/>
            				</html:radio><br>
            			</td>
	            	</tr>
	            </logic:iterate>
            </sakai:flat_list>
            <p/>
           		<html:hidden property="cancelOption" value="TOMAINVIEW"/>
	        	<sakai:actions>
    		    	<html:submit styleClass="active" property="action"><fmt:message key="button.tutorCount"/></html:submit>
     			    <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
     		    </sakai:actions>
            
    	</logic:equal>
	</logic:notEmpty>
	
	<script type="text/javascript">
		var selRadio = document.getElementById('<bean:write name="bookMenuForm"/>');
		selRadio.checked=true;
	</script>
</html:form>
</sakai:html>	