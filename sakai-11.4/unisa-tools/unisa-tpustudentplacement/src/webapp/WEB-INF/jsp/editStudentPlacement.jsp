<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>
  <head>  
      <meta charset="utf-8">  
      <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  
      <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>  
  
      <!-- Javascript -->  
      <script>  
         $(function() {
        	 $( "#startDate" ).datepicker({
                 showOn:"button",
                 buttonImage:"https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                 buttonImageOnly: true,
                 altField: '#startDate',
                 dateFormat: 'yy/mm/dd',
                 beforeShowDay: $.datepicker.noWeekends
              });
        	 $( "#endDate" ).datepicker({
                showOn:"button",
                buttonImage: "https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                buttonImageOnly: true,
                altField: '#endDate',
                dateFormat: 'yy/mm/dd',
                beforeShowDay: $.datepicker.noWeekends
             });
        	 });
         
      </script> 
      <script type="text/javascript">
      </script>
  </head>  	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.studentPlacement.editPlacement"/> 
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>	
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacement.studentInfo"/> 
		</sakai:group_heading>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>/<fmt:message key="prompt.semester"/></td>
				<td><bean:write name="studentPlacementForm" property="acadYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.studentNr"/></td>
				<td><bean:write name="studentPlacementForm" property="student.number"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.studentName"/></td>
				<td><bean:write name="studentPlacementForm" property="student.name"/>
					<html:submit property="action">
						<fmt:message key="button.contactDetails"/>
					</html:submit>	
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.qualification"/></td>
				<td><bean:write name="studentPlacementForm" property="student.qual.description"/></td>
			</tr>				
		</sakai:group_table>
		<hr/>	
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacement.modulePlacement"/> 
		</sakai:group_heading>	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.module"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="studentPlacement.module">
						<html:options name="studentPlacementForm" property="student.listPracticalModules"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.school"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacement.schoolDesc" size="40" maxlength="60" readonly="true"/>
				<html:submit property="action">                      
					<fmt:message key="button.searchSchool"/>
				</html:submit>				
			</tr> 
			<tr>
			  <td><fmt:message key="prompt.fulltimeStu"/></td>
				<td><html:select name="studentPlacementForm" property="studentPlacement.stuFullTime" >
							<html:optionsCollection name="studentPlacementForm" property="listMentorTrained" value="value" label="label"/>
					</html:select>
				</td>
			</tr> 
			<tr>
			  <tr>
				   <td><fmt:message key="prompt.mentor"/>
				    <td><html:text name="studentPlacementForm" property="studentPlacement.mentorName" size="30" maxlength="28" readonly="true"/>
				    <html:submit property="action">                      
					    <fmt:message key="button.searchMentor"/>
				    </html:submit>				
			     </tr>
			  <tr>
			   <td><fmt:message key="prompt.town"/></td>
				<td><bean:write name ="studentPlacementForm" property="studentPlacement.town" /></td>
			</tr>
			<logic:notEqual name="studentPlacementForm" property="currentPage"   value="editPrelimPlacement">
			    <tr>
				   <td><fmt:message key="prompt.supervisor"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				    <td><html:text name="studentPlacementForm" property="studentPlacement.supervisorName" size="30" maxlength="28" readonly="true"/>
				    <html:submit property="action">                      
					    <fmt:message key="button.searchSupervisor"/>
				    </html:submit>				
			     </tr>
			</logic:notEqual>
			<logic:equal name="studentPlacementForm" property="currentPage"   value="editPrelimPlacement">
			     <tr>
				     <td><fmt:message key="prompt.supervisor"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				     <td><html:text name="studentPlacementForm" property="studentPlacement.supervisorName" size="30" maxlength="28" readonly="true"/>
				         <html:select name="studentPlacementForm" property="studentPlacement.supervisorCode"  onchange="submit();">
						      <html:optionsCollection name="studentPlacementForm" property="listProvSupervisor"    value="value"  label="label"/>
					     </html:select>    
				         <html:submit property="action">                      
					            <fmt:message key="button.searchSupervisor"/>
				         </html:submit>	
				    </td>			
			      </tr>
			</logic:equal>     
			<tr>
				<td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
			</tr>
			<tr>
				<td><fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="2"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.evaluationMark"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="studentPlacement.evaluationMark" size="4" maxlength="3"/></td>
			</tr>	
		</sakai:group_table>	
		<sakai:actions>
		    <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="add">
			    <html:submit property="action">
					<fmt:message key="button.save"/>
			    </html:submit>
			 </logic:equal>
			 <logic:notEqual name="studentPlacementForm" property="studentPlacementAction"   value="add">  	
			      <html:submit property="action">
				   	   <fmt:message key="button.saveEditedData"/>
			        </html:submit>
			 </logic:notEqual>
			 <html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
			<logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="editPrelimPlacement">
			       <logic:equal name="studentPlacementForm" property="firstPlacementReached"    value="Y" >	
			            <html:submit property="action"  disabled="true">
				         	 <fmt:message key="button.previous"/>
			             </html:submit>
			        </logic:equal>
			        <logic:notEqual name="studentPlacementForm" property="firstPlacementReached"    value="Y">	
			           <html:submit property="action">
				        	 <fmt:message key="button.previous"/>
			           </html:submit>	
			        </logic:notEqual>	
			        <logic:equal name="studentPlacementForm" property="lastPlacementReached"   value="Y">	
			              <html:submit property="action"  disabled="true">
				     	     <fmt:message key="button.next"/>
			              </html:submit>
			        </logic:equal>
			        <logic:notEqual name="studentPlacementForm" property="lastPlacementReached"   value="Y">		
			              <html:submit property="action">
				     	     <fmt:message key="button.next"/>
			              </html:submit>
			       </logic:notEqual>
			        <html:submit property="action">
					    <fmt:message key="button.correspondence"/>
			        </html:submit>						
		   </logic:equal>
		</sakai:actions>
		<html:hidden property="action" value="supervForProvOnchangeAction"/>						
	</html:form>
</sakai:html>