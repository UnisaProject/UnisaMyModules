<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.studentlist.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading><fmt:message key="page.general.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step2.instruction"/><br/>
		<fmt:message key="page.step2.instruction1"/>
		<fmt:message key="page.step2.instruction2"/>
		<fmt:message key="page.step2.instruction3"/>
	</sakai:instruction>	
		
	<sakai:group_heading><fmt:message key="page.step2.groupheading"/></sakai:group_heading> <br/>
	
	 <html:form action="studentlistaction.do?action=step2">	
	 <sakai:group_table>	 
	 <tr><td>	
	 <html:select name="studentlistform"  property="displayOption" onchange = "submit();">
	 <html:option  value="">Select display option</html:option>
	 <html:option  value="curYear">All modules of current year</html:option>
	 <html:option  value="curYearSem1">Current year/semester 1 modules</html:option>
	 <html:option  value="curYearSem2">Current year/semester 2 modules</html:option>
	 <html:option  value="curYearYear">Current year/year modules</html:option>
	 <html:option  value="all">All modules</html:option>	 
	</html:select>
	</td></tr>
    </sakai:group_table>
	</html:form>	
	
	 <html:form action="studentlistaction.do?action=step3">	
	 		 <sakai:group_table>
	 		 
			<logic:present name="sitesPerLecturer">
				<logic:iterate id="sites" name="sitesPerLecturer" indexId="index">
					<tr>
						<td><html:multibox property="indexNrOfSelectedSite"><bean:write name="index"/></html:multibox></td>
						<td><bean:write name="sites" property="lecturerSites"/></td> 
					</tr>
				</logic:iterate>
			</logic:present>
			<tr>
				<sakai:actions>
					<td colspan="2">
					<html:submit value="Continue"   disabled="${studentlistform.noSitesExists == true}"/>
					<html:cancel value="Cancel" titleKey="button.cancel" disabled="${studentlistform.noSitesExists == true}"/>
					</td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>
</sakai:html>