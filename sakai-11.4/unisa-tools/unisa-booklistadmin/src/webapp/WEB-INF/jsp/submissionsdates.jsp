<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
<h3><fmt:message key="admin.heading"/></h3>
	<hr/>
	<sakai:group_heading>
	       <fmt:message key="function.releasedateheading"/>
	  </sakai:group_heading>
	  <br>
	<sakai:messages/>
	<sakai:instruction>
	   <fmt:message key="function.submissiondateheading"/>&nbsp;<bean:write name="booklistAdminForm" property="typeOfBookListStr"/>            
	</sakai:instruction>
	<br>
	<sakai:group_heading>
	     <fmt:message key="function.currentsettings"/>        
	</sakai:group_heading>
	<br>
	<html:form action="booklistadmin">
	 	<sakai:flat_list>
		   <logic:notEmpty name="booklistAdminForm" property="booklist">
			<tr>
			<th><fmt:message key="function.academicyear"/></th>
	 	   	    <th><fmt:message key="function.level"/></th>
	 	   	    <th><fmt:message key="function.openingdate"/></th>
	 	   	    <th><fmt:message key="function.closingdate"/></th>
	 	   	    <th><fmt:message key="function.remove"/></th>
			</tr>
			
				<logic:iterate name="booklistAdminForm" property="booklist" id="d" indexId="dindex">				
		    <tr>
		   <td>
				      <bean:write name="d" property="academicYear"/>
			          </td>
			          <td>
				           <bean:write name="d" property="level"/>
			          </td>	
			          <td>
				           <bean:write name="d" property="openingDate"/>
			         </td>
			         <td>
				          <bean:write name="d" property="closingDate"/>
			         </td>	
			<td>	
				<html:checkbox name="booklistAdminForm" property='<%="bookIndex["+dindex+"].remove" %>' />
			</td>
			</tr>
			</logic:iterate>			
			</logic:notEmpty>			
		 </sakai:flat_list>
		  <br>
	  <sakai:group_heading>
	       <fmt:message key="function.updateheading"/>
	  </sakai:group_heading>
	  	  <sakai:instruction> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <fmt:message key="function.completesubmission"/>                
	      </sakai:instruction>
	       <sakai:group_table>
	            <tr>
			           <td>
				            <fmt:message key="function.selectacademicyear"/> 
				            <html:select name="booklistAdminForm"  property="academicYear">
					               <html:options  collection="yearsList" property="value" labelProperty="label" />
    		                </html:select>
			           </td>
			   </tr>
			   <tr>
			           <td>
				            <fmt:message key="function.selectacademiclevel"/> 
				            <html:select name="booklistAdminForm"  property="level">
					               <html:options  collection="levelList" property="value" labelProperty="label" />
    		                </html:select>
			           </td>
			   </tr>
			   <tr>
	                <td> 
	                    <sakai:instruction>
	                            <fmt:message key="function.showbooklistmanagement"/>                
	                    </sakai:instruction>		
	                </td>   
	                <td></td>
			   </tr>
			   <tr>
			       <td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <html:checkbox name="booklistAdminForm" property="openDateChoosen" value="true">
			                     <fmt:message key="function.chooseopeningdate"/>     
			             </html:checkbox>
			       </td>
			   </tr>
			   <tr>
			       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			          <html:select name="booklistAdminForm"  property="openingMonth">
					               <html:options  collection="monthList" property="value" labelProperty="label" />
    		          </html:select>
    		          <html:select name="booklistAdminForm"  property="openingDay">
					               <html:options  collection="dayList" property="value" labelProperty="label" />
    		          </html:select>
    		          <html:select name="booklistAdminForm"  property="openingYear">
					               <html:options  collection="yearsList" property="value" labelProperty="label" />
    		          </html:select>
    		       </td>
    		       
			   </tr>
			   <tr> 
			           <td> <br></td> 
			    </tr>
			   <tr>
			       <td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:checkbox name="booklistAdminForm" property="closingdatechoosen"  value="true"  onmousedown="submit();">
			             <fmt:message key="function.chooseclosingdate"/>
			             </html:checkbox>
			       </td>
			   </tr>
			   <tr>	 
			        <logic:equal name="booklistAdminForm"  property="closingdatechoosen" value="false">
			           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			              <html:select name="booklistAdminForm"  property="closingMonth"  disabled="true">
					               <html:options  collection="monthList" property="value" labelProperty="label" />
    		              </html:select>
    		              <html:select name="booklistAdminForm"  property="closingDay" disabled="true">
					               <html:options  collection="dayList" property="value" labelProperty="label" />
    		              </html:select>
    		              <html:select name="booklistAdminForm"  property="closingYear" disabled="true">
					               <html:options  collection="closingYearsList" property="value" labelProperty="label" />
    		              </html:select>
    		           </td>
    		        </logic:equal>
    		        <logic:equal name="booklistAdminForm"  property="closingdatechoosen" value="true">
			           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			              <html:select name="booklistAdminForm"  property="closingMonth"  disabled="false">
					               <html:options  collection="monthList" property="value" labelProperty="label" />
    		              </html:select>
    		              <html:select name="booklistAdminForm"  property="closingDay" disabled="false">
					               <html:options  collection="dayList" property="value" labelProperty="label" />
    		              </html:select>
    		              <html:select name="booklistAdminForm"  property="closingYear" disabled="false">
					               <html:options  collection="closingYearsList" property="value" labelProperty="label" />
    		              </html:select>
       		           </td>
    		        </logic:equal>
			   </tr>
	     </sakai:group_table>
	  	  <sakai:actions>
			           <html:submit styleClass="button" property="action">
			                    <fmt:message key="button.savedates"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			                <fmt:message key="button.removedatesconfirm"/>
			           </html:submit>
			           <html:submit styleClass="button" property="action">
			                <fmt:message key="button.canceldates"/>
			           </html:submit>
		 </sakai:actions>
		           <html:hidden property="action" value="handleClosingDateChoosenChkbxAction"/>
	  	 </html:form>
</sakai:html>
		 
		


