<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.prescribedbooks.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="prescribedbooks.heading"/> <bean:write name="prebooksForm" property="courseCode"/><br/>
	</sakai:heading>
	<sakai:instruction> 
		<fmt:message key="prescribedbooks.booksellers.sites"/><br> <br/>
		<fmt:message key="prescribedbooks.booksellers.sites1"/>
		<fmt:message key="prescribedbooks.booksellers.sites2"/>
		<fmt:message key="prescribedbooks.booksellers.sites3"/>
		<fmt:message key="prescribedbooks.booksellers.sites4"/><br>
	</sakai:instruction>
	<logic:equal name="prebooksForm" property="confirm" value="1">     
	<logic:notEmpty name="prebooksForm" property="prebooksList">
	 <sakai:group_heading>
			<fmt:message key="prescribedbooks.heading2"/>
	</sakai:group_heading>
		<tr>
		      <td><fmt:message key="prescribedbooks.equiries"/></td>
		</tr>
	<logic:iterate name="prebooksForm" property="prebooksList" id="c" indexId="cindex">
		<sakai:flat_list>
			<logic:equal name="c" property="coloured" value="1">
				<tr>
				    <tr>
					   <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
					   <td><bean:write name="c" property="author"/></td>
					</tr>
					<tr>
					   <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
					   <td><bean:write name="c" property="title"/></td>
					</tr>
					<tr>
					  <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
					   <td><bean:write name="c" property="pubYear"/></td>
					</tr>
					<tr>
					   <td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
					   <td><bean:write name="c" property="edition"/></td>
					</tr>
					<tr>
					   <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
					   <td><bean:write name="c" property="publisher"/></td>
					</tr>
					<tr>
						<td width="15%"><fmt:message key="prescribedbooks.label.bnotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
					<tr>
						<td width="15%"><fmt:message key="prescribedbooks.label.cnotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
			</logic:equal>
			<logic:equal name="c" property="coloured" value="0">
				   	<tr bgcolor="#eeeeee">
					    <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
					    <td><bean:write name="c" property="author"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
				   	  <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
					  <td><bean:write name="c" property="title"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
					   <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
					   <td><bean:write name="c" property="pubYear"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
					    <td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
					    <td><bean:write name="c" property="edition"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
					   <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
					   <td><bean:write name="c" property="publisher"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
						<td width="15%"><fmt:message key="prescribedbooks.label.bnotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
					<tr bgcolor="#eeeeee">
						<td width="15%"><fmt:message key="prescribedbooks.label.cnotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
			</logic:equal>
		</sakai:flat_list>
	</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="prebooksForm" property="prebooksList">
      	<tr>
	        <br><br><td><fmt:message key="prescribedbooks.instruction.nobooks"/></td>
	    </tr>
	</logic:empty>
	</logic:equal>
	<logic:equal name="prebooksForm" property="confirm" value="0">
	   	 <tr>
	 		   <br><br><td><fmt:message key="prescribedbooks.instruction.nobooks"/></td>
		 </tr>
	</logic:equal>
	 <logic:equal name="prebooksForm" property="confirm" value="3">
          <tr>
                <br><br><td><fmt:message key="prescribedbooks.instruction.nobooks.year"/></td>
          </tr>
     </logic:equal>  
    <logic:equal name="prebooksForm" property="confirmEreserveBook" value="1">     
	<logic:notEmpty name="prebooksForm" property="ereserveList">
	      <sakai:group_heading>
			       <fmt:message key="ereservebooks.heading"/>
          </sakai:group_heading>
          <tr>
		       <td><fmt:message key="ereservebooks.equiries"/></td>
		 </tr>
	     <logic:iterate name="prebooksForm" property="ereserveList" id="c" indexId="cindex">
		<sakai:flat_list>
			   <logic:equal name="c" property="coloured" value="1">
				<tr>
                    <tr>
				       <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
				       <td><bean:write name="c" property="author"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
				        <td><bean:write name="c" property="title"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
				         <td><bean:write name="c" property="publisher"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
				        <td><bean:write name="c" property="pubYear"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="ereservebooks.label.volume"/></td>
				        <td><bean:write name="c" property="volume"/></td>
				    </tr>
				    <tr>
				   
				</logic:equal>
			<logic:equal name="c" property="coloured" value="0">
			    <tr bgcolor="#eeeeee">
				    <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
				    <td><bean:write name="c" property="author"/></td>
				</tr>
			    <tr bgcolor="#eeeeee">
				    <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
				    <td><bean:write name="c" property="title"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
				    <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
				    <td><bean:write name="c" property="publisher"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
				    <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
				    <td><bean:write name="c" property="pubYear"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
				    <td width="15%"><fmt:message key="ereservebooks.label.volume"/></td>
					<td><bean:write name="c" property="volume"/></td>
				</tr>
				
				
			 </logic:equal>
	    </tr>
         </sakai:flat_list>    
          </logic:iterate>
       </logic:notEmpty>
       <logic:greaterThan name="prebooksForm" property="year" value="2010">
       <logic:empty name="prebooksForm" property="ereserveList">
      	    <tr>
	             <br><br><td><fmt:message key="ereservebooks.instruction.nobooks"/></td>
	        </tr>
	   </logic:empty>
	   </logic:greaterThan>
	  </logic:equal>
	  <logic:greaterThan name="prebooksForm" property="year" value="2010">
       <logic:equal name="prebooksForm" property="confirmEreserveBook" value="0">
                <tr>
	 		     <br><br><td><fmt:message key="ereservebooks.instruction.nobooks"/></td>
		        </tr>
	   </logic:equal>
	   <logic:equal name="prebooksForm" property="confirmEreserveBook" value="3">
	        <tr>
                <br><br><td><fmt:message key="ereservebooks.instruction.nobooks.year"/></td>
            </tr>
       </logic:equal>
       </logic:greaterThan>
    <logic:equal name="prebooksForm" property="confirmRecommendedBook" value="1">     
	<logic:notEmpty name="prebooksForm" property="recommList">
	      <sakai:group_heading>
			     <fmt:message key="recommendedbooks.heading"/>
          </sakai:group_heading>
          <tr>
		       <td><fmt:message key="recommendedbooks.equiries"/></td>
		</tr>
        <logic:iterate name="prebooksForm" property="recommList" id="c" indexId="cindex">
		<sakai:flat_list>
			<logic:equal name="c" property="coloured" value="1">
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
				        <td><bean:write name="c" property="author"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
				        <td><bean:write name="c" property="title"/></td>
				    </tr>
				    <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
				        <td><bean:write name="c" property="pubYear"/></td>
				     </tr>
				     <tr>
				        <td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
				        <td><bean:write name="c" property="edition"/></td>
				     </tr>
				     <tr>
				         <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
				         <td><bean:write name="c" property="publisher"/></td>
				      </tr>
	     	</logic:equal>
			<logic:equal name="c" property="coloured" value="0">
		            <tr bgcolor="#eeeeee">
				       <td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
				       <td><bean:write name="c" property="author"/></td>
				     </tr>
				     <tr bgcolor="#eeeeee">
				         <td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
				         <td><bean:write name="c" property="title"/></td>
				     </tr>
				     <tr bgcolor="#eeeeee">
				         <td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
				         <td><bean:write name="c" property="pubYear"/></td>
				     </tr>
				     <tr bgcolor="#eeeeee">
				         <td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
				         <td><bean:write name="c" property="edition"/></td>
				     </tr>
				     <tr bgcolor="#eeeeee">
				    	  <td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
				          <td><bean:write name="c" property="publisher"/></td>
				     </tr>		
			</logic:equal>
       </sakai:flat_list>
      </logic:iterate>
       </logic:notEmpty>
     <logic:greaterThan name="prebooksForm" property="year" value="2010">
        <logic:empty name="prebooksForm" property="recommList">
      	    <tr>
	             <br><br><td><fmt:message key="recommendedbooks.instruction.nobooks"/></td>
	        </tr>
	   </logic:empty>
	   </logic:greaterThan>
	</logic:equal>
	   <logic:greaterThan name="prebooksForm" property="year" value="2010">
       <logic:equal name="prebooksForm" property="confirmRecommendedBook" value="0">
       	     <tr>
	 		      <br><br><td><fmt:message key="recommendedbooks.instruction.nobooks"/></td>
		     </tr>
	   </logic:equal>
	   <logic:equal name="prebooksForm" property="confirmRecommendedBook" value="3">
	         <tr>
                <br><br><td><fmt:message key="recommendedbooks.instruction.nobooks.year"/></td>
             </tr>
        </logic:equal>
      </logic:greaterThan>
</sakai:html>
