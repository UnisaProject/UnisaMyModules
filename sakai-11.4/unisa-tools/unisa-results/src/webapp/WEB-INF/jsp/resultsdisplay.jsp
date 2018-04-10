<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.results.ApplicationResources"/>
<sakai:html>
  	<sakai:heading>Examination Results</sakai:heading>
       <br/>
	<html:form action="/displayresults">
  	<!--<html:hidden property="action" value="input"/>-->
  	<html:hidden property="action" value="display"/>
  	<html:hidden property="currentPage" value="display"/>
  	<%@page import="java.util.*" %>
		<% String fromSystem ="";
			if (request.getAttribute("fromSystem")==null)
				fromSystem="";
			else
				fromSystem=(String)request.getAttribute("fromSystem");
		%>
		<input type=hidden name =fromSystem value="<%=fromSystem%>"></input>	
  	<sakai:messages/>
		<sakai:messages message="true"/>
	<sakai:group_table>
		<tr>
			<td><label>Exam Year:</label></td>
			<td><html:text property="examYear" size="6" maxlength="4"/></td>
		</tr>
		<tr>
			<td><label>Exam Period:</label></td>
			<td>
				<html:select property="examPeriod">
				<html:options collection="examPeriods" property="code" labelProperty="engDescription"/>
				</html:select>
			</td>
		</tr>
		<logic:equal name="resultsform" property="studentUser" value="false">
			<tr>
				<td><label><bean:message key="prompt.results.studentnr"/>: </label></td>
				<td><html:text property="studentNr" size="10" maxlength="8"/></td>
			</tr>
		</logic:equal>		
	</sakai:group_table>
	<br/>
	<sakai:actions>
		<html:submit value="Display"/>
	</sakai:actions>
	<hr/>
	 <logic:notEmpty name="resultsform" property="listResults">
    
	<sakai:group_table>
		<tr>
			<td><fmt:message key="prompt.resultsFor"/></td>
			<td><bean:write name="resultsform" property="studentNr"/></td>
			<td><fmt:message key="prompt.queryDate"/></td>
			<td><bean:write name="resultsform" property="queryDate"/></td>
		</tr>
	</sakai:group_table>
  	<sakai:flat_list>
  		<tr>
			<th>Course</th>
			<th>Final Mark</th>
			<th>Result</th>
		</tr>
		<logic:iterate name="resultsform" property="listResults" id="courseresult">
		<bean:define id="selectCourse" name="courseresult" property="course"/>
		<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("selectCourse",selectCourse);
						params.put("fromSystem",fromSystem);
						pageContext.setAttribute("params",params);
		%>		
		<tr>
			<td>
				<logic:equal name="courseresult" property="showDetails" value="Y">						
					<html:link href="displayresults.do?action=displayDetails" scope="page" name="params">
						<fmt:message key="page.linkDetailsText"/>					
					</html:link>
				</logic:equal>
				<bean:write name="courseresult" property="course"/>
			</td>
			<td><bean:write name="courseresult" property="finalMark"/></td>
			<td><bean:write name="courseresult" property="description"/></td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>
	<br/>
	<sakai:group_table>
		<logic:notEqual name="resultsform" property="textLine1" value="">
			<tr>
				<td><bean:write name="resultsform" property="textLine1"/></td>
			</tr>		
		</logic:notEqual>
		<logic:notEqual name="resultsform" property="textLine2" value="">
			<tr>
				<td><bean:write name="resultsform" property="textLine2"/></td>
			</tr>		
		</logic:notEqual>
		<logic:notEqual name="resultsform" property="textLine3" value="">
			<tr>
				<td><bean:write name="resultsform" property="textLine3"/></td>
			</tr>		
		</logic:notEqual>
		<logic:notEqual name="resultsform" property="textLine4" value="">
			<tr>
				<td><bean:write name="resultsform" property="textLine4"/></td>
			</tr>		
		</logic:notEqual>
		<logic:notEqual name="resultsform" property="textLine5" value="">
			<tr>
				<td><bean:write name="resultsform" property="textLine5"/></td>
			</tr>		
		</logic:notEqual>
	</sakai:group_table>
  </logic:notEmpty>
	</html:form>
</sakai:html>