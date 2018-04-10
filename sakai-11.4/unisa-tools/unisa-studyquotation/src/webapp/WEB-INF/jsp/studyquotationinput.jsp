<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.studyquotation.ApplicationResources"/>
<sakai:html>

	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="studyquote.heading"/>
	</sakai:heading>		
	<sakai:instruction>
		<fmt:message key="studyquote.userinstruction"/>
	</sakai:instruction>
	
	<html:form action="studyquotation">
		<sakai:group_table>
			<tr>
				<td width="50%">
					<b><fmt:message key="studyquote.field.academicyear"/></b>
				</td><td width="50%">
					<html:text property="studyQuotation.academicYear" size="5" maxlength="4"></html:text>
				</td>
			</tr><tr>
				<td width="50%">
					<b><fmt:message key="studyquote.field.country"/></b>
				</td><td>
					<html:radio property="studyQuotation.countryCode" value="1015"/><fmt:message key="studyquote.label.sa"/></br>
					<html:radio property="studyQuotation.countryCode" value="1120"/><fmt:message key="studyquote.label.othercountry"/></br>
					<html:radio property="studyQuotation.countryCode" value="4111"/><fmt:message key="studyquote.label.allothercountry"/></br>				
				</td>			
			</tr><tr>
				<td>
					<b><fmt:message key="studyquote.field.qualification"/></b>
				</td><td>
					<html:radio property="studyQuotation.qualification" value="99999"/><fmt:message key="studyquote.label.postgraduate"/></br>			
					<html:radio property="studyQuotation.qualification" value="02011"/><fmt:message key="studyquote.label.undergraduate"/>
				</td>
			</tr><tr>
				<td>
					<b><fmt:message key="studyquote.field.qualificationcode"/></b>
				</td><td>
					<html:text property="studyQuotation.qualificationCode" size="5" maxlength="5"></html:text>
				</td>
			</tr><tr>
				<td>
					<b><fmt:message key="studyquote.field.libaccess"/></b>
				</td><td>
					<html:radio property="studyQuotation.libraryCard" value="N"/><fmt:message key="studyquote.label.no"/>			
					<html:radio property="studyQuotation.libraryCard" value="Y"/><fmt:message key="studyquote.label.yes"/>
				</td>						
			</tr><tr>
				<td>
					<b><fmt:message key="studyquote.field.applyexempt"/></b>
				</td><td>
					<html:radio property="studyQuotation.matricExemption" value="N"/><fmt:message key="studyquote.label.no"/>			
					<html:radio property="studyQuotation.matricExemption" value="Y"/><fmt:message key="studyquote.label.yes"/>
				</td>					
			</tr><tr>
				<td>
					<b><fmt:message key="studyquote.field.studyunits"/></b>
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					<html:text property="studyQuotation.studyCode1" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode2" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode3" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode4" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode5" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode6" size="7" maxlength="7"/>
				</td>
			</tr><tr>
				<td colspan="2">
					<html:text property="studyQuotation.studyCode7" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode8" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode9" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode10" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode11" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode12" size="7" maxlength="7"/>
				</td>			
			</tr><tr>
				<td colspan="2">
					<html:text property="studyQuotation.studyCode13" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode14" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode15" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode16" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode17" size="7" maxlength="7"/>
				&nbsp;
					<html:text property="studyQuotation.studyCode18" size="7" maxlength="7"/>
				</td>			
			</tr>																		
		</sakai:group_table>
		<sakai:actions>			
			<html:submit  property="action">
				<fmt:message key="studyquote.button.display"/>
			</html:submit>
			<html:submit  property="action">
				<fmt:message key="studyquote.button.clear"/>
			</html:submit>
		</sakai:actions>
	</html:form>	
</sakai:html>