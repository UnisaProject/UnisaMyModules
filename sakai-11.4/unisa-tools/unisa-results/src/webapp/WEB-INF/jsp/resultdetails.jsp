<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.results.ApplicationResources"/>
<sakai:html>

  	<sakai:heading>Examination Results</sakai:heading>
  	<logic:empty name="resultsform" property="selectResult">
       <html:form action="/displayresults">
		<html:hidden property="action" value="display"/>
		<html:hidden property="currentPage" value="input"/>
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
        <sakai:instruction>Please enter the relevant information required for the displaying of results.</sakai:instruction>
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
		</html:form>
  	</logic:empty>
  	<logic:notEmpty name="resultsform" property="selectResult">
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
			<td><fmt:message key="prompt.queryDate"/></td>
			<td><bean:write name="resultsform" property="queryDate"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="prompt.results.studentnr"/>					
			</td>
			<td>
				<bean:write name="resultsform" property="studentNr"/>
			</td>	
			<td>&nbsp;</td>
		</tr>
		<tr>
			
			<td>
				<fmt:message key="prompt.results.studyUnit"/>					
			</td>
			<td>
				<bean:write name="resultsform" property="selectResult.course"/>
			</td>	
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="prompt.results.finalMark"/>					
			</td>
			<td>
				<bean:write name="resultsform" property="selectResult.finalMark"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td>
				<bean:write name="resultsform" property="selectResult.description"/>
			</td>		
		</tr>
	</sakai:group_table>
	<sakai:group_heading>
		<fmt:message key="table.heading.markDetails"/>
	</sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<td width="25%">
				<fmt:message key="prompt.results.yearMark"/>					
			</td>
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.yearMark" format="0.00"/>
				<fmt:message key="prompt.results.perc"/>
			</td>
			<td width="2%">
			&nbsp;
			</td>	
			<td width="10%">
				<fmt:message key="prompt.results.weight"/>					
			</td>	
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.yearMarkWeight" format="0"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td width="43%">
			&nbsp;
			</td>							
		</tr>
		<tr>
			<td>
				<fmt:message key="prompt.results.examMark"/>					
			</td>
			<td align="right" >
				<bean:write name="resultsform" property="selectResult.examMark" format="0.00"/>
				<fmt:message key="prompt.results.perc"/>
			</td>
			<td>
			&nbsp;
			</td>		
			<td>
				<fmt:message key="prompt.results.weight"/>					
			</td>	
			<td align="right">
				<bean:write name="resultsform" property="selectResult.examMarkWeight" format="0"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td>
			&nbsp;
			</td>							
		</tr>
		<tr>
			<td>
				<fmt:message key="prompt.results.portfolioMark"/>					
			</td>
			<td align="right">
				<bean:write name="resultsform" property="selectResult.portfolioMark" format="0.00"/>
				<fmt:message key="prompt.results.perc"/>
			</td>
			<td>
			&nbsp;
			</td>				
			<td>
				<fmt:message key="prompt.results.weight"/>					
			</td>	
			<td align="right">
				<bean:write name="resultsform" property="selectResult.portfolioWeight" format="0"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td>
			&nbsp;
			</td>			
		</tr>		
	</sakai:flat_list>
	<logic:notEmpty name="resultsform" property="selectResult.message">			
		<sakai:heading><bean:write name="resultsform" property="selectResult.message"/></sakai:heading>
	</logic:notEmpty>
	<logic:notEqual name="resultsform" property="selectResult.paper2Weight" value="0">
		<sakai:group_heading>
			<fmt:message key="table.heading.paperMarks"/>
		</sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<td  width="25%">
				<fmt:message key="prompt.results.paper1Mark"/>					
			</td>
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.paper1Mark" format="0.00"/>
				<fmt:message key="prompt.results.perc"/>
			</td>		
			<td width="2%">
			&nbsp;
			</td>		
			<td width="10%">
				<fmt:message key="prompt.results.weight"/>					
			</td>	
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.paper1Weight" format="0"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td width="43%">
			&nbsp;
			</td>				
		</tr>	
		<tr>
			<td width="25%">
				<fmt:message key="prompt.results.paper2Mark"/>					
			</td>			
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.paper2Mark" format="0.00"/>
				<fmt:message key="prompt.results.perc"/>
			</td>	
			<td width="2%">
			&nbsp;
			</td>			
			<td width="10%">
				<fmt:message key="prompt.results.weight"/>					
			</td>	
			<td align="right" width="10%">
				<bean:write name="resultsform" property="selectResult.paper2Weight" format="0"/>
				<fmt:message key="prompt.results.perc"/>
			</td>
			<td width="43%">
			&nbsp;
			</td>						
		</tr>		
	</sakai:flat_list>
	</logic:notEqual>		
	<sakai:actions>
		<html:submit value="Return"/>
	</sakai:actions>
	</html:form>
	</logic:notEmpty>
  </sakai:html>