<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.signoff.ApplicationResources"/>
<sakai:html>
<script language="JavaScript">
	function setAction() {
		document.signoffForm.action='signoff.do?act=orderSignoffDisplay'
		document.signoffForm.submit();
	}

	function moveUp() {
		document.signoffForm.action='signoff.do?act=moveUp'
		document.signoffForm.submit();
	}
	function moveDown() {
		document.signoffForm.action='signoff.do?act=moveDown'
		document.signoffForm.submit();
	}
</script>

	<html:form action="/signoff.do">	
		<html:hidden property="atStep" value="3"/>
	<sakai:messages/>
	<sakai:heading>
	<tr>
	<td><fmt:message key="signoff.order"/></td>
	</tr>
   </sakai:heading>
	  <sakai:group_table>
	  <tr>
	   <td><fmt:message key="signoff.auth"/>&nbsp;<sakai:required/></td>
			<td>
			<html:select property="levelCode"  onchange="setAction()" style="width: 300px">
					<html:option value="-1">Please choose</html:option>	
					<html:option value="clg">College</html:option>	
					<html:option value="sch">School</html:option>	
					<html:option value="dpt">Department</html:option>	 
    		</html:select> 
			</td>	
			</tr>
			<tr>
	   <td><fmt:message key="signoff.dpt"/>&nbsp;<sakai:required/></td>
			<td>
			<html:select property="dpt_list"  style="width: 300px">
					<html:option value="-1">Please choose</html:option>	 
        		   <html:options  collection="orderDisplyList" property="value" labelProperty="label" />
    		</html:select> 
			</td>	
			</tr>
	  </sakai:group_table>
  <sakai:group_table>
	<tr><td>
	<sakai:actions>
		<html:submit styleClass="button" property="act">
		<fmt:message key="button.display"/>
		</html:submit>
		<html:submit styleClass="button" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</td></tr>
	</sakai:group_table>
	<logic:notEqual name="signoffForm" property="levelCode" value="-1">
	<logic:notEqual name="signoffForm" property="dpt_list" value="-1">
	<logic:equal name="signoffForm" property="display" value="display">
	<logic:notEqual name="signoffForm" property="fullNameList" value="0">
	<sakai:group_heading><bean:write name="signoffForm"  property="orderStructure"/></sakai:group_heading>
	<p>
	<fmt:message key="signoff.displayinfo"/>
	</p>
	 <sakai:group_table>
	 <tr>
	 <td><b><bean:write name="signoffForm"  property="level_list"/></b></td>
	 <td><html:select name="signoffForm"  property="order_list" style="width: 250px" size="6">
	    <html:options  collection="fullNamesList" property="value" labelProperty="label" />
	 </html:select></td>
	 <td>
		<html:link href="javascript:moveUp()"><html:img  src="/library/image/up.png"/></html:link> 
	 <br></br>
	 	<html:link href="javascript:moveDown()"><html:img  src="/library/image/down.png"/></html:link> 
	 </td>	
	 </tr>
	 <tr><td>
	 <sakai:actions>
		<html:submit styleClass="button" property="act">
		<fmt:message key="button.submit"/>
		</html:submit>
		<html:submit styleClass="button" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</td></tr>
	 </sakai:group_table>
	 </logic:notEqual>
	 </logic:equal>
	 	<logic:equal name="signoffForm" property="fullNameList" value="0">
	 	Information is not available for your selection.
	 	</logic:equal>
	</logic:notEqual>
	</logic:notEqual>
	</html:form>
</sakai:html>





