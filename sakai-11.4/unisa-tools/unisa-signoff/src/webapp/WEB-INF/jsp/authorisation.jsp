<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.signoff.ApplicationResources"/>
<sakai:html>
<script language="JavaScript">
	function setAction() {
		document.signoffForm.action='signoff.do?act=authorisationPage'
		document.signoffForm.submit();
	}
</script>	
	<html:form action="/signoff.do">	
	<sakai:messages/>
		<logic:equal name="signoffForm" property="userPermission" value="MAINTAIN">
	<sakai:tool_bar>
	<ul>
	<li><html:link href="signoff.do?act=editStandIn&type=a">Edit Stand-in COD</html:link></li>
	<li><html:link href="signoff.do?act=editStandIn&type=b">Edit Stand-in School Director</html:link></li>
	<li><html:link href="signoff.do?act=editStandIn&type=c">Edit Stand-in Dean</html:link></li>
	</ul>
	</sakai:tool_bar>  
	</logic:equal>
	  <sakai:group_table>
	  <tr>
	   <td><b><fmt:message key="signoff.managementstructure.name"/></b></td>
			<td>
			<html:select property="code"  onchange="setAction()" style="width: 300px">
					<html:option value="-1">Please choose</html:option>	 
        		   <html:options  collection="structureName" property="value" labelProperty="label" />
    		</html:select> 
			</td>	
			</tr>
	  </sakai:group_table>
	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><fmt:message key="signoff.date"/></b>&nbsp;&nbsp;
	 <bean:write name="signoffForm" property="sysdate"/> 
	 <logic:notEmpty name="signoffForm" property="code">
	 <sakai:group_table>
	 <sakai:flat_list>
	 	   	<th><fmt:message key="signoff.level"/></th>
	 	   	<th><fmt:message key="signoff.name"/></th>
	 	   	<th><fmt:message key="signoff.status"/></th>
	 	   	<th><fmt:message key="signoff.remove"/></th> 	  
	 	   	<logic:equal name="signoffForm" property="deanStatus" value="Inactive">
	 	   	<tr bgcolor="#AAAAAA">
	 	   	</logic:equal>
	 	   	<logic:notEqual name="signoffForm" property="deanStatus" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
	 	   	<td>Dean</td>
			<td><bean:write name="signoffForm" property="dean"/></td>	
			<td><bean:write name="signoffForm" property="deanStatus"/></td>
			<td></td>
	 	   	</tr>
			<logic:notEmpty name="signoffForm" property="standinDydean">
			<logic:iterate name="signoffForm" property="standinDydean" id="d" indexId="dindex">
			<logic:equal name="d" property="status" value="Inactive">
			<tr bgcolor="#AAAAAA">
			</logic:equal>
			<logic:notEqual name="d" property="status" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
					<td> 
					
					<logic:equal name="dindex" value="0" >
                                          Stand-in Dean
                                    </logic:equal>
                                    <logic:notEqual name="dindex" value="0">
                                          &nbsp;
                                    </logic:notEqual>
					</td>
					<td>   
		    			<bean:write name="d" property="name"/>
		    		</td>
		    		<td>   
		    			<bean:write name="d" property="status"/>
		    		</td>
		    		<td>
		    		 <logic:equal name="signoffForm" property="userPermission" value="MAINTAIN">  
		    		<html:checkbox name="signoffForm" property='<%= "recordIndexed["+dindex+"].remove"%>'/>
		    		</logic:equal>
		    		</td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
		<tr><td>&nbsp;</td></tr><tr><td>
			<logic:equal name="signoffForm" property="schDirectorStatus" value="Inactive">
	 	   	<tr bgcolor="#AAAAAA">
	 	   	</logic:equal>
	 	   	<logic:notEqual name="signoffForm" property="schDirectorStatus" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
			<td>School Director</td>
			<td><bean:write name="signoffForm" property="schDirector"/></td>	
			<td><bean:write name="signoffForm" property="schDirectorStatus"/></td>
			<td></td>
			</tr>
			<logic:notEmpty name="signoffForm" property="standinSch">
			<logic:iterate name="signoffForm" property="standinSch" id="d" indexId="dindex">
		    <logic:equal name="d" property="status1" value="Inactive">
			<tr bgcolor="#AAAAAA">
			</logic:equal>
			<logic:notEqual name="d" property="status1" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
					<td> 
					
					<logic:equal name="dindex" value="0" >
                                        Stand-in School Director
                                    </logic:equal>
                                    <logic:notEqual name="dindex" value="0">
                                          &nbsp;
                                    </logic:notEqual>
					</td>
					<td>   
		    			<bean:write name="d" property="name1"/>
		    		</td>
		    		<td>   
		    			<bean:write name="d" property="status1"/>
		    		</td>
		    		<td>
		    		<logic:equal name="signoffForm" property="userPermission" value="MAINTAIN">   
		    		<html:checkbox name="signoffForm" property='<%= "recordIndexed1["+dindex+"].remove1"%>'/>
		    		</logic:equal>
		    		</td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
			<tr><td>&nbsp;</td></tr>
			<logic:equal name="signoffForm" property="codStatus" value="Inactive">
	 	   	<tr bgcolor="#AAAAAA">
	 	   	</logic:equal>
	 	   	<logic:notEqual name="signoffForm" property="codStatus" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
			<td>Chair of Department</td>
			<td><bean:write name="signoffForm" property="cod"/></td>	
			<td><bean:write name="signoffForm" property="codStatus"/></td>
			<td></td>
			</tr>
			<logic:notEmpty name="signoffForm" property="standinCod">
			<logic:iterate name="signoffForm" property="standinCod" id="d" indexId="dindex">
		    <logic:equal name="d" property="status2" value="Inactive">
			<tr bgcolor="#AAAAAA">
			</logic:equal>
			<logic:notEqual name="d" property="status2" value="Inactive">
	 	   	<tr>
	 	   	</logic:notEqual>
					<td> 
					
					<logic:equal name="dindex" value="0" >
                                        Stand-in Chairs of Department
                                    </logic:equal>
                                    <logic:notEqual name="dindex" value="0">
                                          &nbsp;
                                    </logic:notEqual>
					</td>
					<td>   
		    			<bean:write name="d" property="name2"/>
		    		</td>
		    		<td>   
		    			<bean:write name="d" property="status2"/>
		    		</td>
		    		<td>
		    		<logic:equal name="signoffForm" property="userPermission" value="MAINTAIN">   
		    		<html:checkbox name="signoffForm" property='<%= "recordIndexed2["+dindex+"].remove2"%>'/>
		    		</logic:equal>
		    		</td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
			 <logic:equal name="signoffForm" property="userPermission" value="MAINTAIN">
			<tr><td>
			 <sakai:actions>
             <html:submit styleClass="button" property="act">
	          <fmt:message key="button.remove"/>
	          </html:submit>
              </sakai:actions>
              </td></tr>
              </logic:equal>        
	</sakai:flat_list>
	</sakai:group_table>
	</logic:notEmpty>
	</html:form>
</sakai:html>





