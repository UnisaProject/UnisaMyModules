<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/schoolMaintenance">
		<!--<html:hidden property="currentPage" value="inputSchool"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.schoolMaintenance"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputSchool.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.type"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="schoolFilterType">
						<html:optionsCollection name="studentPlacementForm" property="listFilterSchoolType" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.category"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="schoolFilterCategory">
						<html:optionsCollection name="studentPlacementForm" property="listFilterSchoolCategory" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="schoolFilterCountry"  onchange="submit();">
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				   <td><fmt:message key="prompt.province"/>&nbsp;</td>
				   <td><html:select name="studentPlacementForm" property="schoolFilterProvince">
						 <html:optionsCollection name="studentPlacementForm" property="listFilterProvince" value="code" label="description"/>
					  </html:select>                                           
				   </td>
			  </tr>	
			  <tr>
				<td><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="districtFilter" size="30" maxlength="28" />
					<html:submit property="action">                      
						<fmt:message key="button.searchDistrict"/>
					</html:submit>
					<logic:notEmpty name="studentPlacementForm" property="listFilterSchoolDistrict">
						<html:select name="studentPlacementForm" property="schoolFilterDistrictValue">
							<html:optionsCollection name="studentPlacementForm" property="listFilterSchoolDistrict" value="value" label="label"/>
						</html:select> 
					</logic:notEmpty>
				</td>	
			  </tr>	
			  <tr>
				<td><fmt:message key="prompt.schoolFilter"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="schoolFilter" size="20" maxlength="20"/><i><fmt:message key="filter.note"/></i></td>
			</tr>	
	      </sakai:group_table>
		 <sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>
		<hr/>
		<sakai:flat_list>
			<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.school"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.schoolType"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.schoolCategory"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.country"/></th>
				<logic:equal name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
				    <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				    <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.district"/></th>
				</logic:equal>   	
				<logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
				    <logic:equal name="studentPlacementForm"  property="schoolCalledFrom"  value="inputPlacement">
				         <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				         <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.district"/></th>
				    </logic:equal>
				</logic:notEqual>
				<logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
				    <logic:equal name="studentPlacementForm"  property="schoolCalledFrom"  value="editStudentPlacement">
				         <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				         <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.district"/></th>
				    </logic:equal>
				</logic:notEqual>  
				<logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">
				    <logic:notEqual name="studentPlacementForm"  property="schoolCalledFrom"  value="editStudentPlacement">
				             <logic:notEqual name="studentPlacementForm"  property="schoolCalledFrom"  value="inputPlacement">
				                   <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.town"/></th>
				             </logic:notEqual>
				     </logic:notEqual>  
				</logic:notEqual> 	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.inUse"/></th>
			</tr>
			<logic:iterate name="studentPlacementForm" property="listSchool" id="rec" indexId="index">
				<tr>
					<td>	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="name"/>
					</td>
					<td><bean:write name="rec" property="type"/></td>
					<td><bean:write name="rec" property="category"/></td>									
					<td><bean:write name="rec" property="country"/></td>
					<logic:equal name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
					    <td><bean:write name="rec" property="province"/></td>
					    <td><bean:write name="rec" property="district"/></td>
					</logic:equal>
					<logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
				          <logic:equal name="studentPlacementForm"  property="schoolCalledFrom"  value="editStudentPlacement">
				                <td><bean:write name="studentPlacementForm" property="emptyStr"/></td>
					            <td><bean:write name="studentPlacementForm" property="emptyStr"/></td>
				          </logic:equal>
				     </logic:notEqual> 
				     <logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">	
				          <logic:equal name="studentPlacementForm"  property="schoolCalledFrom"  value="inputPlacement">
				                <td><bean:write name="studentPlacementForm" property="emptyStr"/></td>
					            <td><bean:write name="studentPlacementForm" property="emptyStr"/></td>
				          </logic:equal>
				     </logic:notEqual> 
				     <logic:notEqual name="studentPlacementForm"  property="schoolFilterCountry"  value="1015">
				         <logic:notEqual name="studentPlacementForm"  property="schoolCalledFrom"  value="editStudentPlacement">
				             <logic:notEqual name="studentPlacementForm"  property="schoolCalledFrom"  value="inputPlacement">
				                  <td><bean:write name="rec" property="town"/></td>
				             </logic:notEqual>
				         </logic:notEqual>   
				     </logic:notEqual>
					<td>
						<logic:equal name="rec" property="inUse" value="N">No</logic:equal>
						<logic:notEqual name="rec" property="inUse" value="N">Yes</logic:notEqual>
					</td>		
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.edit"/>
			</html:submit>
			<logic:notEqual name="studentPlacementForm" property="schoolCalledFrom" value="inputStudentPlacement">		
				<html:submit property="action">
						<fmt:message key="button.select"/>
				</html:submit>		
			</logic:notEqual>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>	
			<html:hidden property="action" value="countryOnchangeAction"/>		
		</sakai:actions>
	</html:form>
</sakai:html>