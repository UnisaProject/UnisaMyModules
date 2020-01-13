<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.unmarkassfollowup.ApplicationResources"/>


<sakai:html>
	<html:form action="/unmarkAssFollowUp">	
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:group_heading>
			<fmt:message key="instruction.input"/> 
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>				
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="unmarkAssFollowUpForm" property="acadYear" size="4" maxlength="4"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td>
					<html:select name="unmarkAssFollowUpForm" property="semester" style="width:150px;">
						<html:optionsCollection name="unmarkAssFollowUpForm" property="listSemester" value="code" label="engDescription"/>
					</html:select>                                           
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.breakDownLevel"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td>
					<sakai:group_table>
						<logic:equal name="unmarkAssFollowUpForm" property="userType" value="dsaaUser">
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="COLLEGE"></html:radio>
									<fmt:message key="prompt.college"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedCollegeCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listCollege" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="SCHOOL"></html:radio>
									<fmt:message key="prompt.school"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedSchool">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listSchool" value="collegeSchoolCode" label="description" />
									</html:select><BR/>                                                                                 
								</td>
							</tr>
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="DEPARTMENT"></html:radio>
									<fmt:message key="prompt.department"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedDptCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listDepartment" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
						</logic:equal>
						<logic:equal name="unmarkAssFollowUpForm" property="userType" value="dean">
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="COLLEGE"></html:radio>
									<fmt:message key="prompt.college"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedCollegeCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listCollege" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="SCHOOL"></html:radio>
									<fmt:message key="prompt.school"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedSchool">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listSchool" value="collegeSchoolCode" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="DEPARTMENT"></html:radio>
									<fmt:message key="prompt.department"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedDptCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listDepartment" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
						</logic:equal>
						<logic:equal name="unmarkAssFollowUpForm" property="userType" value="director">
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="SCHOOL"></html:radio>
									<fmt:message key="prompt.school"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedSchool">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listSchool" value="collegeSchoolCode" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="DEPARTMENT"></html:radio>
									<fmt:message key="prompt.department"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedDptCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listDepartment" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
						</logic:equal>
						<logic:equal name="unmarkAssFollowUpForm" property="userType" value="cod">
							<tr>
								<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="DEPARTMENT"></html:radio>
									<fmt:message key="prompt.department"/>
								</td>
								<td colspan="2">
									<html:select name="unmarkAssFollowUpForm" property="selectedDptCode">
										<html:optionsCollection name="unmarkAssFollowUpForm"  property="listDepartment" value="code" label="description"/>
									</html:select><BR/>
								</td>
							</tr>
						</logic:equal>
						<tr>
							<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="STUDYUNIT"></html:radio>
								<fmt:message key="prompt.studyUnit"/>
							</td>
							<td colspan="2"><html:text name="unmarkAssFollowUpForm" property="studyUnit" size="10" maxlength="8"/></td>
						</tr>
						<tr>
							<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="MARKER"></html:radio>
								<fmt:message key="prompt.marker"/>
							</td>
							<td><fmt:message key="prompt.personnelNumber"/></td>
							<td><html:text name="unmarkAssFollowUpForm" property="markerPersno" size="10" maxlength="8"/></td>
						</tr>
						<tr>
							<td><html:radio name="unmarkAssFollowUpForm" property="selectedBreakdownLevel" value="ASSIGNMENT"></html:radio>
								<fmt:message key="prompt.assignment"/>
							</td>
							<td><fmt:message key="prompt.studyUnit"/>
								<br/>
								<fmt:message key="prompt.assNr"/>
							</td>
							<td><html:text name="unmarkAssFollowUpForm" property="assignment.studyUnit" size="10" maxlength="8"/>
								<br/>
								<html:text name="unmarkAssFollowUpForm" property="assignment.assNumber" size="10" maxlength="3"/>
							</td>
						</tr>
					</sakai:group_table>					                                     
				</td>	
			</tr>	
			<tr>
				<td><fmt:message key="prompt.daysOutstanding"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="unmarkAssFollowUpForm" property="daysOutstanding" size="4" maxlength="3"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.assignmentType"/>&nbsp;</td>
				<td>
					<html:select name="unmarkAssFollowUpForm" property="selectedAssignmentType" style="width:150px;">
						<html:optionsCollection name="unmarkAssFollowUpForm" property="listAssignmentType" value="code" label="engDescription"/>
					</html:select>                                           
				</td>	
			</tr>	
			<tr>
				<td><fmt:message key="prompt.modeOfSubmission"/>&nbsp;</td>
				<td>
					<html:select name="unmarkAssFollowUpForm" property="selectedModeOfSubmission" style="width:150px;">
						<html:optionsCollection name="unmarkAssFollowUpForm" property="listModeOfSubmission" value="code" label="engDescription"/>
					</html:select>                                           
				</td>	
			</tr>	
			<tr>
				<td><fmt:message key="prompt.modeOfMarking"/>&nbsp;</td>
				<td>
					<html:select name="unmarkAssFollowUpForm" property="selectedModeOfMarking" style="width:150px;">
						<html:optionsCollection name="unmarkAssFollowUpForm" property="listModeOfMarking" value="code" label="engDescription"/>
					</html:select>                                           
				</td>	
			</tr>				
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.summary"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.detail"/>
			</html:submit>		
		</sakai:actions>			
	</html:form>
</sakai:html>
		