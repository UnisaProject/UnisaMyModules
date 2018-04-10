<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/mentorMaintenance">
			<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.mentor"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputMentor.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="mentorFilterData.mentorFilterCountry" onchange="submit();">
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>
			<logic:equal name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
			  <tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="mentorFilterData.mentorFilterProvince">
						<html:optionsCollection name="studentPlacementForm" property="listFilterProvince" value="code" label="description"/>
					</html:select>                                           
				</td>
			  </tr>	
			  <tr>
				<td><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorFilterData.mentorFilterDistrictValue" size="30" maxlength="28" />
					<html:submit property="action">                      
						<fmt:message key="button.searchDistrict"/>
					</html:submit>
					<logic:notEmpty name="studentPlacementForm" property="listMentorFilterDistrict">
						<html:select name="studentPlacementForm" property="mentorFilterData.mentorFilterLongDistrictValue" >
							<html:optionsCollection name="studentPlacementForm" property="listMentorFilterDistrict" value="value" label="label"/>
						</html:select> 
					</logic:notEmpty>
				</td>		
			  </tr>
			 
			</logic:equal>
			<tr>
				<td><fmt:message key="prompt.mentorTrained"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="mentorFilterData.mentorIsTrained" >
							<html:optionsCollection name="studentPlacementForm" property="listMentorTrained" value="value" label="label"/>
					</html:select>
				</td>		
			 </tr> 
			 <tr>
				<td><fmt:message key="prompt.school"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorFilterData.mentorFilterSchoolValue" size="40" maxlength="60" readonly="true"/>
				     <html:submit property="action">                      
					         <fmt:message key="button.searchSchool"/>
					 </html:submit>
					 <html:submit property="action">                      
					          <fmt:message key="button.clearSchool"/>
				    </html:submit>
				</td>		
			 </tr> 
			
			<tr>
				<td><fmt:message key="prompt.mentorFilter"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorFilterData.mentorFilter" size="20" maxlength="20"/><i><fmt:message key="filter.note"/></i></td>
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.listmentors"/>
			</html:submit>
			</sakai:actions>			
		<hr/>
		<sakai:flat_list>
		<logic:notEmpty name="studentPlacementForm" property="listMentor">
			
		     <tr>
		        <td colspan="5">&nbsp;</td>	
				 <td colspan="1" align="right">
					<html:link href="mentorMaintenance.do?action=extractFile">
						<fmt:message key="link.extractFile"/>						
					</html:link>	
				</td>
			</tr>
			<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.name"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.cellNumber"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.emailAddress"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.isTrained"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.schoolName"/></th>
			    <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.districts"/></th>
			</tr>
			   <logic:iterate name="studentPlacementForm" property="listMentor" id="rec" indexId="index">
				<tr>
					<td style="white-space:nowrap;align:left">	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="name"/>
					</td>
					<td><bean:write name="rec" property="cellNumber"/></td>
					<td><bean:write name="rec" property="emailAddress"/></td>
					<td><bean:write name="rec" property="trained"/></td>		
					<td><bean:write name="rec" property="schoolName"/></td>			
					<td><bean:write name="rec" property="provinceName"/></td>
					<td ><bean:write name="rec" property="districtName"/> </td>
				</tr>
			  </logic:iterate>
			</logic:notEmpty>
		</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.addMentor"/>
			</html:submit>
			<logic:notEmpty name="studentPlacementForm" property="listMentor">
					<html:submit property="action">
		    			<fmt:message key="button.view"/>
        			</html:submit>	
        			<logic:equal name="studentPlacementForm" property="mentorCalledFrom"  value="editStudentPlacement">	
        			      <html:submit property="action">
		    			     <fmt:message key="button.select"/>
        			      </html:submit>	
        			</logic:equal>
				    <html:submit property="action">
						<fmt:message key="button.deleteMentor"/>
				    </html:submit>	
		   </logic:notEmpty>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>	
			<html:hidden property="action" value="mentorCountryOnchangeAction"/>		
		</sakai:actions>
	</html:form>
</sakai:html>