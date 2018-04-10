<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
<sakai:tool_bar>
	<!-- Sifiso Changes:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->
	<logic:notEqual name = "bookMenuForm" property = "isDiss" value="true">
		<ul>
			<li>
				<html:link href="authorisation.do?action=codAuthRequestList" >
					<fmt:message key="link.codAuthorisation"/>
				</html:link>
			</li>
			<li>
				<html:link href="authorisation.do?action=schoolDirectorAuthRequestList" >
					<fmt:message key="link.schoolsdirectorAuthirization"/>
				</html:link>
			</li>
			<li>
				<html:link href="deanAuthorisation.do?action=deanview" >
					<fmt:message key="link.deanAuthorisation"/>
				</html:link>
			</li>
		</ul>
	</logic:notEqual>
	
	<!-- Sifiso Changes START:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->
	<logic:equal name = "bookMenuForm" property = "isDiss" value="true">
		<ul>
			<li>
				<html:link href="authorisation.do?action=codAuthRequestList" >
					<fmt:message key="link.codAuthorisation"/>
				</html:link>
			</li>
			<li>
				<html:link href="authorisation.do?action=schoolDirectorAuthRequestList" >
					<fmt:message key="link.schoolsdirectorAuthirization"/>
				</html:link> 
			</li>
			<li><html:link href="deanAuthorisation.do?action=deanview" >
					<fmt:message key="link.deanAuthorisation"/>
				</html:link>
			</li>
			<li><html:link href="authorisation.do?action=dissAuthRequestList">
					<fmt:message key="link.dissAuthorisation"/>
				</html:link>
			</li>
		</ul>
	</logic:equal>
	<!-- Sifiso Changes END:2017/07/14:LU_MA_BLU_06:Requirement 54:Diss Authorisation -->
</sakai:tool_bar>
<sakai:instruction>
		<fmt:message key="function.instructiontext"/><br/>
		<fmt:message key="function.instructiontext1"/>
	</sakai:instruction>
	<html:form action="/prebook">
		   <p/>
		   <sakai:group_heading>
			    <fmt:message key="label.selecteditcoursemsg"/>
		   </sakai:group_heading>
	       <sakai:instruction>
		         <fmt:message key="label.frmbookmenuinstr"/>
	       </sakai:instruction>
	       	<html:select name="bookMenuForm"  property="acadyear" onchange = "submit();">
			            <html:option  value="Select Academic Year">Select Academic Year</html:option>
					    <html:options  collection="yearsList" property="value" labelProperty="label" />
	   		</html:select>&nbsp;&nbsp;<fmt:message key="function.and"/><BR><BR>
	   		<html:hidden property="action" value="processAcadyear"/>
	   		
        </html:form>
    	<html:form action="/prebook">
			<html:select name="bookMenuForm" property="selectedCourse" onchange = "submit();" >
					<html:option  value="Select Course Code">Select Course Code</html:option>
					<html:options  collection = "crs" property="value" labelProperty ="label"/>
		   </html:select>
		   <html:hidden property="action" value="prebooksContinue"/>
		   <html:hidden property="continueOption" value="MAINVIEWCONTINUE"/>
	     <p/>
	 </html:form>
    <logic:notEqual  name = "bookMenuForm" property ="selectedCourse" value="-1">
      <sakai:group_heading>
   			<fmt:message key ="booklist.headinglabel"/><bean:write name = "bookMenuForm" property ="selectedCourse"/><br>
	   </sakai:group_heading>
        <sakai:instruction>
		     <fmt:message key="booklist.datelabel"/><bean:write name = "bookMenuForm" property ="listDate"/><br/>
	    </sakai:instruction>
		<sakai:flat_list>
			<tr>
			     <th><fmt:message key="booklist.typelabel"/></th>
			     <th><fmt:message key="booklist.status"/></th>
			     <th><fmt:message key="booklist.modificationdatelabel"/></th>
			     <th><fmt:message key="booklist.modifiernamelabel"/></th>
			</tr>
			<tr>
			    <td><fmt:message key="booklist.prescribedText"/>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist open for editing">
			                    <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="true">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                                 </html:link>
			                             </logic:equal>
			                   <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="false">
			                             <html:link href="prebook.do?action=prescribedBookAction" >
			                                     <fmt:message key="link.viewOption"/>
			                              </html:link>
			                   </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist submitted for authorization">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="true">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                                 </html:link>
			                             </logic:equal>
			                   <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="false">
			                              <html:link href="prebook.do?action=prescribedBookAction" >
			                                     <fmt:message key="link.viewOption"/>
			                              </html:link>
			                   </logic:equal>
			         </logic:equal>
			          <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist authorized by COD">
			              <html:link href="prebook.do?action=prescribedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			          </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="No Books prescribed for this course">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="true">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                                 </html:link>
			                             </logic:equal>
			                   <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="false">
			                             <html:link href="prebook.do?action=prescribedBookAction" >
			                                     <fmt:message key="link.viewOption"/>
			                              </html:link>
			                   </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist authorized by School Director">
			              <html:link href="prebook.do?action=prescribedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist authorized by Dean">
			              <html:link href="prebook.do?action=prescribedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Book list published by administrator">
			              <html:link href="prebook.do?action=prescribedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist open for editing by administrator">
			              <html:link href="prebook.do?action=prescribedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			     </td>
			     <td>
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="false">
			                      <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist open for editing">
			                            <bean:write name = "bookMenuForm" property ="bookListClosedMessage"/>
			                      </logic:equal>
			                      <logic:notEqual name = "bookMenuForm"  property = "bookListStatus" value="Booklist open for editing">
			                           <bean:write name = "bookMenuForm" property ="bookListStatus"/>
			                      </logic:notEqual>
			          </logic:equal>
			          <logic:equal name = "bookMenuForm" property="dateIsWithinLimits" value="true">
			                   <bean:write name = "bookMenuForm" property ="bookListStatus"/>
			          </logic:equal>
			     </td>
			     <td>
			           <bean:write name = "bookMenuForm" property = "lastmodifiedforbook"/>
			     </td>
			      <td>
			            <bean:write name = "bookMenuForm"  property = "whoModifiedPbook"/>
			      </td>
			  </tr>
			  <tr>
			    <td><fmt:message key="booklist.recommendedText"/>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist open for editing">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="true">
			                    <html:link href="prebook.do?action=recommendedBookAction" >
			                        <fmt:message key="link.editOption"/>
			                    </html:link>
			              </logic:equal>
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="false">
			                   <html:link href="prebook.do?action=recommendedBookAction" >
			                         <fmt:message key="link.viewOption"/>
			                   </html:link>
			              </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist submitted for authorization">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="true">
			                    <html:link href="prebook.do?action=recommendedBookAction" >
			                        <fmt:message key="link.editOption"/>
			                    </html:link>
			              </logic:equal>
			              <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist authorized by COD">
			                  <html:link href="prebook.do?action=recommendedBookAction" >
			                     <fmt:message key="link.viewOption"/>
			                  </html:link>
			              </logic:equal>
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="false">
			                   <html:link href="prebook.do?action=recommendedBookAction" >
			                         <fmt:message key="link.viewOption"/>
			                   </html:link>
			              </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="No Books prescribed for this course">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="true">
			                    <html:link href="prebook.do?action=recommendedBookAction" >
			                        <fmt:message key="link.editOption"/>
			                    </html:link>
			              </logic:equal>
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="false">
			                   <html:link href="prebook.do?action=recommendedBookAction" >
			                         <fmt:message key="link.viewOption"/>
			                   </html:link>
			              </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist authorized by School Director">
			              <html:link href="prebook.do?action=recommendedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist authorized by Dean">
			              <html:link href="prebook.do?action=recommendedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Book list published by administrator">
			              <html:link href="prebook.do?action=recommendedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist open for editing by administrator">
			              <html:link href="prebook.do?action=recommendedBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			      </td>
			      <td>
			               <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="false">
			                      <logic:equal name = "bookMenuForm"  property = "rbookListStatus" value="Booklist open for editing">
			                            <bean:write name = "bookMenuForm" property ="bookListClosedMessage"/>
			                      </logic:equal>
			                      <logic:notEqual name = "bookMenuForm"  property = "rbookListStatus" value="Booklist open for editing">
			                           <bean:write name = "bookMenuForm" property ="rbookListStatus"/>
			                      </logic:notEqual>
			               </logic:equal>
			               <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForR" value="true">
			                   <bean:write name = "bookMenuForm" property ="rbookListStatus"/>
			               </logic:equal>
			     </td>
			     <td>
			           <bean:write name = "bookMenuForm" property = "lastmodifiedforRbook"/>
			     </td>
			      <td>
			            <bean:write name = "bookMenuForm"  property = "whoModifiedRBook"/>
			      </td>
			   </tr>
			   <tr>
			    <td><fmt:message key="booklist.eReserveText"/>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist open for editing">
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="true">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.editOption"/>
			              </html:link>
			           </logic:equal>
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="false">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			           </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist submitted for authorization">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="true">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.editOption"/>
			              </html:link>
			           </logic:equal>
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="false">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			           </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist authorized by COD">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="No Books prescribed for this course">
			              <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="true">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.editOption"/>
			              </html:link>
			           </logic:equal>
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="false">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			           </logic:equal>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist authorized by School Director">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist authorized by Dean">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Book list published by administrator">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			         <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist open for editing by administrator">
			              <html:link href="prebook.do?action=eRerveBookAction" >
			                 <fmt:message key="link.viewOption"/>
			              </html:link>
			         </logic:equal>
			      </td>
			      <td>
			            <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="false">
			                      <logic:equal name = "bookMenuForm"  property = "ebookListStatus" value="Booklist open for editing">
			                            <bean:write name = "bookMenuForm" property ="bookListClosedMessage"/>
			                      </logic:equal>
			                      <logic:notEqual name = "bookMenuForm"  property = "ebookListStatus" value="Booklist open for editing">
			                           <bean:write name = "bookMenuForm" property ="ebookListStatus"/>
			                      </logic:notEqual>
			           </logic:equal>
			           <logic:equal name = "bookMenuForm" property="dateIsWithinLimitsForE" value="true">
			                   <bean:write name = "bookMenuForm" property ="ebookListStatus"/>
			           </logic:equal>
			     </td>
			     <td>
			           <bean:write name = "bookMenuForm" property = "lastmodifiedforEbook"/>
			     </td>
			      <td>
			           <bean:write name = "bookMenuForm"  property = "whoModifiedEBook"/>
			      </td>
			   </tr>
			   <!-- Sifiso Changes START:2017/07/14:LU_MA_BLU_06:Requirement 4:Tutor Booklist -->
			   <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist authorized by Dean">
	                <tr>
					    <td><fmt:message key="booklist.tutorPrescribedText"/>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="0">
			                            <html:link href="prebook.do?action=tutorBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                            </html:link>
					         </logic:equal>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="1">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.viewOption"/>
			                            </html:link>
					         </logic:equal>			        
					    </td>
					    <td>
					     	 <fmt:message key="booklist.tutorStatusAvailable"/>                
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm" property = "lastmodifiedforbook"/>
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm"  property = "whoModifiedPbook"/>
					    </td>
	  				</tr>
                </logic:equal>    
			  
			    <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Booklist open for editing by administrator">
			  		<tr>
					    <td><fmt:message key="booklist.tutorPrescribedText"/>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="0">
			                            <html:link href="prebook.do?action=tutorBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                            </html:link>
					         </logic:equal>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="1">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.viewOption"/>
			                            </html:link>
					         </logic:equal>			        
					    </td>
					    <td>
					     	 <fmt:message key="booklist.tutorStatusAvailable"/>                
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm" property = "lastmodifiedforbook"/>
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm"  property = "whoModifiedPbook"/>
					    </td>
  			   		</tr>
       	   	   </logic:equal>
       	   	  
       	   	   <logic:equal name = "bookMenuForm"  property = "bookListStatus" value="Book list published by administrator">
			   		<tr>
					    <td><fmt:message key="booklist.tutorPrescribedText"/>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="0">
			                            <html:link href="prebook.do?action=tutorBookAction" >
			                                    <fmt:message key="link.editOption"/>
			                            </html:link>
					         </logic:equal>
					         <logic:equal name = "bookMenuForm"  property = "tutorBookListStatus" value="1">
			                            <html:link href="prebook.do?action=prescribedBookAction" >
			                                    <fmt:message key="link.viewOption"/>
			                            </html:link>
					         </logic:equal>			        
					    </td>
					    <td>
					     	 <fmt:message key="booklist.tutorStatusAvailable"/>                
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm" property = "lastmodifiedforbook"/>
					    </td>
					    <td>
					         <bean:write name = "bookMenuForm"  property = "whoModifiedPbook"/>
					    </td>
  			  		</tr> 
           	  </logic:equal> 
       	   	  <!-- Sifiso Changes END:2017/07/14:LU_MA_BLU_06:Requirement 4:Tutor Booklist -->
		</sakai:flat_list>
		</logic:notEqual>
</sakai:html>