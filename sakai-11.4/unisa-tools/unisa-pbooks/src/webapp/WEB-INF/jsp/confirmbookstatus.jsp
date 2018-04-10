<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
	<logic:empty name="bookMenuForm" property="bookList">
	
	     <logic:notEqual name="bookMenuForm" property="bookListDatesStatus" value="booklist Closed">
		       <logic:notEqual name="bookMenuForm" property="bookListStatus" value="No Books prescribed for this course">
		             <logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	
	       	              <sakai:tool_bar>
                             <ul>
                             <li>  <html:link href="prebook.do?action=searchBook"><fmt:message key="link.addnew"/></html:link></li>      
                               <li> <html:link href="prebook.do?action=bookReuse">  <fmt:message key="link.reusebook"/></html:link> </li>
                              <li>  <html:link href="prebook.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link> </li>
                               <li>  <html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooks"/></html:link> </li></ul>
        	             </sakai:tool_bar>
        	             <p/>
                       	 <sakai:heading><fmt:message key="function.booklist"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		             <sakai:messages/>
    		             <sakai:messages message="true"/>
        	             <sakai:instruction><fmt:message key="function.instructiontext2"/><br/></sakai:instruction>
                         <p/>
	 	                <fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
		                <p/>
	               </logic:equal>
	               <logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	
	       	              <sakai:tool_bar>
	       	              <ul>
                             <li>
                              <html:link href="prebook.do?action=searchBook"><fmt:message key="link.addnew"/></html:link></li>      
                             <li><html:link href="prebook.do?action=bookReuse">  <fmt:message key="link.reusebook"/></html:link></li>
                             <li> <html:link href="prebook.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link> </li>
                              <li><html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooksforR"/></html:link> </li> </ul>
        	             </sakai:tool_bar>
             	         <p/>
    		             <sakai:heading><fmt:message key="function.booklistforR"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		             <sakai:messages/>
    		             <sakai:messages message="true"/>
    	                 <sakai:instruction><fmt:message key="function.instructiontext2forR"/><br/></sakai:instruction>
        	             <p/>
		                 <fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
		                 <p/>
	              </logic:equal>
	         	  <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	
	       	                  <sakai:tool_bar>
                                 <ul> <li> <html:link href="prebook.do?action=searchBook"><fmt:message key="link.addnewitem"/></html:link> </li>      
                                  <li><html:link href="prebook.do?action=bookReuse">  <fmt:message key="link.reusebookforE"/></html:link> </li>
                                  <li><html:link href="prebook.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link>  </li>
                                  <li><html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooksforE"/></html:link> </li> </ul>
        	                  </sakai:tool_bar>
           	                  <p/>
         		              <sakai:heading><fmt:message key="function.booklistforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		                  <sakai:messages/>
    		                  <sakai:messages message="true"/>
    		      	          <sakai:instruction><fmt:message key="function.instructiontext2forE"/><br/></sakai:instruction>
        	                  <p/>
		                      <fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
		                      <p/>
	               </logic:equal>
		          <html:form action="prebook">
                 	     <html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		         <sakai:actions>
        			               <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		         </sakai:actions>
                  </html:form>
              </logic:notEqual>
              <logic:equal name="bookMenuForm" property="bookListStatus" value="No Books prescribed for this course">
       
    	             <sakai:heading>
    	                 <logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklist"/> </logic:equal>
    	                 <logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistforR"/> </logic:equal>
    	                 <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistforE"/> </logic:equal>
    	
                      	<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    	                <sakai:messages/>
    	                <sakai:messages message="true"/>
    	                <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		               <sakai:group_table>
        		             <tr>
            		         <td><fmt:message key="function.datelastmodified"/></td>
               		         <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
           		             </tr>
           		             <tr>
           			         <td><fmt:message key="function.lastmodifiedby"/></td>
           			         <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
          		             </tr>
           		             <tr>
           			         <td><fmt:message key="function.currentstatus"/></td>
               		         <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
           		             </tr>   
       		               </sakai:group_table>
  		                </logic:notEmpty>
   		                <p/>
                       <html:form action="prebook">
			              <p/>
        	              <html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		          <sakai:actions>
        			          <html:submit styleClass="active" property="action"><fmt:message key="button.unconfirm"/></html:submit>
                	         <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		          </sakai:actions>
		               </html:form>      
               </logic:equal>   
           </logic:notEqual>     
           <logic:equal name="bookMenuForm" property="bookListDatesStatus" value="booklist Closed">
             	<sakai:heading>
    	            <logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklist"/> </logic:equal>
    	            <logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistforR"/> </logic:equal>
    	            <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistforE"/> </logic:equal>
                 	<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/>
                 </sakai:heading>
    	            <sakai:messages/>
    	            <sakai:messages message="true"/>
    	          <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		              <sakai:group_table>
        		              <tr>
            		             <td><fmt:message key="function.datelastmodified"/></td>
               		             <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
           		              </tr>
           		              <tr>
           			              <td><fmt:message key="function.lastmodifiedby"/></td>
           			              <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
          		              </tr>
           		              <tr>
           			             <td><fmt:message key="function.currentstatus"/></td>
               		             <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
           		              </tr>   
       		               </sakai:group_table>
  		         </logic:notEmpty>
   		         <p/>
                 <html:form action="prebook">
			     <p/>
        	     <html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		      <sakai:actions>
        			    <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		      </sakai:actions>
		        </html:form>      
          </logic:equal>
     </logic:empty>
	
     <logic:notEmpty name="bookMenuForm" property="bookList">
     
	     <logic:notEqual name="bookMenuForm" property="bookListDatesStatus" value="booklist Closed">
	     
	           <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing">
		             <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
           	             <sakai:tool_bar>
                            <ul><li><html:link href="prebook.do?action=searchBook"><fmt:message key="link.addnew"/></html:link> </li>      
                           <li> <html:link href="prebook.do?action=bookReuse"><fmt:message key="link.reusebook"/></html:link> </li>
                            <li><html:link href="prebook.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link> </li>
                            <logic:equal name="bookMenuForm" property="typeOfBookList" value="P"> <li> <html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooks"/></html:link> </li> </logic:equal>
                             <logic:equal name="bookMenuForm" property="typeOfBookList" value="R"> <li> <html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooksforR"/></html:link> </li></logic:equal>
        	             </ul>
        	             </sakai:tool_bar>
          	             <p/>
         		         <sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		             <logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    		             <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		             <sakai:messages/>
    		             <sakai:messages message="true"/>
        	          	 <sakai:instruction><fmt:message key="function.instructiontextforPandR"/><br/></sakai:instruction>
        	             <p/>
                          <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    			              <sakai:group_table>
        			            <tr>
            			          <td><fmt:message key="function.datelastmodified"/></td>
                		          <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            		            </tr>
            		            <tr> 
             			           <td><fmt:message key="function.lastmodifiedby"/></td>
            			           <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
           			            </tr>
            		            <tr>
            			          <td><fmt:message key="function.currentstatus"/></td>
                		          <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		           </tr>   
        		             </sakai:group_table>
  			            </logic:notEmpty>
    		           <p/>
            		   <sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
        	        	<html:form action="prebook">
       				       <logic:present name="bookMenuForm" property="courseNote">
					            <sakai:group_table>
						         <tr>
							       <td><fmt:message key="label.updateviewfrmnote" /></td>
							       <td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
						         </tr>
						         <tr>
							     <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing">
									<td></td>
									<td><html:link href="prebook.do?action=courseNote">
										<fmt:message key="function.updateviewfrmedit" />
										</html:link>
									</td>
							      </logic:equal>
						          </tr>
					            </sakai:group_table>
				         </logic:present>
				               <sakai:flat_list>
						        <tr>
							      <th><fmt:message key="label.tableheaderauthor" /></th>
							      <th><fmt:message key="label.tableheaderyear" /></th>
							      <th><fmt:message key="label.tableheadertitle" /></th>
							      <th><fmt:message key="label.tableheaderedition" /></th>
							      <th><fmt:message key="label.tableheaderpublisher" /></th>
							      <th><fmt:message key="label.tableheaderISBN" /></th>
							      <th><logic:equal name="bookMenuForm" property="bookListStatus"  value="Booklist open for editing">
								  <fmt:message key="label.tableheaderremove" />
								      </logic:equal>
								 </th>
					            </tr>
				                <logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
					              <tr>
						            <td><bean:write name="c" property="txtAuthor" /> <br>
						            <logic:equal name="c" property="editOption"	value="1">
						            <logic:equal name="bookMenuForm" property="bookListStatus"
							        	value="Booklist open for editing">
								   <b><html:link
								href="prebook.do?action=addNewBook&searchOption=edit"
								paramName="c" paramProperty="bookId" paramId="bookId">Edit</html:link>
								</logic:equal>
				                </logic:equal></td>
							    <td><bean:write name="c" property="txtYear" /></td>
							    <td><bean:write name="c" property="txtTitle" /></td>
							    <td><bean:write name="c" property="txtEdition" /></td>
						   	    <td><bean:write name="c" property="txtPublisher" /></td>
							    <td><bean:write name="c" property="txtISBN" /></td>
							    <td><logic:equal name="bookMenuForm" property="bookListStatus"
								value="Booklist open for editing">
								<html:checkbox name="bookMenuForm" property='<%="bookIndex["+cindex+"].remove" %>' />
							   </logic:equal></td>
					            </tr>
				             </logic:iterate>
                          </sakai:flat_list>
        	        	<p/>
       		        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        	   	        <sakai:actions>
                	       <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing">
                				<html:submit styleClass="active" property="action"><fmt:message key="button.confirm"/></html:submit>
	                           	<html:submit styleClass="active" property="action"><fmt:message key="button.remove"/></html:submit>
            	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>     
                	       </logic:equal>                     
                           <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist submitted for authorization">
                      	        <html:submit styleClass="active" property="action"><fmt:message key="button.unconfirm"/></html:submit>
                               	<html:submit styleClass="active" property="action"><fmt:message key="button.remindacademic"/></html:submit>
            	               	<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
                    	    </logic:equal> 
                	     </sakai:actions>        
	                 </html:form></p>
	           </logic:notEqual>
	           <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	          <sakai:tool_bar>
                         <ul><li><html:link href="prebook.do?action=searchBook"><fmt:message key="link.addnewitem"/></html:link>  </li>    
                         <li><li> <html:link href="prebook.do?action=bookReuse"><fmt:message key="link.reusebookforE"/></html:link> </li>
                         <li> <html:link href="prebook.do?action=courseNote"><fmt:message key="link.addcoursenote"/></html:link> </li>
                         <li> <html:link href="prebook.do?action=declareNopBooks"><fmt:message key="link.declarenopbooksforE"/></html:link> </li> </ul>
        	          </sakai:tool_bar>
        	          <p/>
          		      <sakai:heading><fmt:message key="function.booklistforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		          <sakai:messages/>
    		          <sakai:messages message="true"/>
        	       	  <sakai:instruction><fmt:message key="function.instructiontextforE"/><br/></sakai:instruction>
        	          <p/>
                      <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    			        <sakai:group_table>
        			     <tr>
            			    <td><fmt:message key="function.datelastmodified"/></td>
                		    <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            		     </tr>
            		     <tr>
             			   <td><fmt:message key="function.lastmodifiedby"/></td>
            			   <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
           			     </tr>
            		     <tr>
            			    <td><fmt:message key="function.currentstatus"/></td>
                		    <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		     </tr>   
        		      </sakai:group_table>
  			        </logic:notEmpty>
    		        <p/>
            		<sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
                	     <html:form action="prebook">
				         <logic:present name="bookMenuForm" property="courseNote">
					    <sakai:group_table>
						  <tr>
							<td><fmt:message key="label.updateviewfrmnote" /></td>
							<td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
						  </tr>
						  <tr>
							<logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing">
							
									<td></td>
									<td><html:link href="prebook.do?action=courseNote">
										<fmt:message key="function.updateviewfrmedit" />
										</html:link>
									</td>
						
							</logic:equal>
						</tr>
					</sakai:group_table>
				</logic:present>
				<sakai:flat_list>
						<tr><th><fmt:message key="label.eReserveType" /></th>
						
						<logic:notEqual name="bookMenuForm" property="eReserveType" value="L">
							<th><fmt:message key="label.tableheaderauthor" /></th>
						</logic:notEqual>
							<th><fmt:message key="label.tableheaderyear" /></th>
							<th><fmt:message key="label.tableheadertitle" /></th>
							<th><fmt:message key="label.tableheaderebookpub" /></th>
							<th><fmt:message key="label.tableheaderebookvol" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>
							<th>
								<logic:equal name="bookMenuForm" property="bookListStatus"
								value="Booklist open for editing">
								<fmt:message key="label.tableheaderremove" />
								</logic:equal>
						</th>
					</tr>
				    <logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
					  <tr>
						
						<td><bean:write name="c" property="eReserveType" /> <br>
							<logic:equal name="c" property="editOption"	value="1">					
							<logic:equal name="bookMenuForm" property="bookListStatus"
								value="Booklist open for editing">
								<b><html:link
								href="prebook.do?action=addNewBook&searchOption=edit"
								paramName="c" paramProperty="bookId" paramId="bookId">Edit</html:link>
								</logic:equal>
				                  </logic:equal></td>
				                  
				         <logic:notEqual name="bookMenuForm" property="eReserveType" value="L">
				            <td><bean:write name="c" property="txtAuthor" /></td>
				         </logic:notEqual>  
				            
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
							<td><logic:equal name="bookMenuForm" property="bookListStatus"
								value="Booklist open for editing">
								<html:checkbox name="bookMenuForm" property='<%="bookIndex["+cindex+"].remove" %>' />
							</logic:equal></td>
					</tr>
				  </logic:iterate>
                 </sakai:flat_list>
        	  	<p/>
       	     	<p/>
               	<html:hidden name="bookMenuForm" property="cancelOption" value="TOMAINVIEW"/>

        		    <sakai:actions>
                	    <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing">
                			
                        		<html:submit styleClass="active" property="action"><fmt:message key="button.confirm"/></html:submit>
	                           	<html:submit styleClass="active" property="action"><fmt:message key="button.remove"/></html:submit>
            	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>     
                	    </logic:equal>                     
                 
                    	<logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist submitted for authorization">
                           		<html:submit styleClass="active" property="action"><fmt:message key="button.unconfirm"/></html:submit>
                               	<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
                    	</logic:equal>           
                	</sakai:actions>
            	</html:form></p>
              </logic:equal>
          </logic:equal>
        <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist submitted for authorization">
     	    <html:form action="prebook">
               <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    		           <sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		           <logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	               <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    	               <sakai:messages/>
    	               <sakai:messages message="true"/>
    	              <p/>
    	              <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		             <sakai:group_table>
        		          <tr>
            		      <td><fmt:message key="function.datelastmodified"/></td>
                	      <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	          </tr>
           	              <tr>
           			      <td><fmt:message key="function.lastmodifiedby"/></td>
           			      <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		          </tr>
           		          <tr>
            		      <td><fmt:message key="function.currentstatus"/></td>
               		      <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		       </tr>   
        	            </sakai:group_table>
  		              </logic:notEmpty>
    	             <p/>
    	            <sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
     			    <logic:present name="bookMenuForm" property="courseNote">
				       <sakai:group_table>
					    <tr>
						 <td><fmt:message key="label.updateviewfrmnote" /></td>
						 <td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
					    </tr>
					    <tr>
						   <td></td>
						   <td>
						   </td>
					    </tr>
				       </sakai:group_table>
			        </logic:present>
                    <sakai:flat_list>
					   <tr>
						 <th><fmt:message key="label.tableheaderauthor" /></th>
						 <th><fmt:message key="label.tableheaderyear" /></th>
						 <th><fmt:message key="label.tableheadertitle" /></th>
						 <th><fmt:message key="label.tableheaderedition" /></th>
						 <th><fmt:message key="label.tableheaderpublisher" /></th>
						 <th><fmt:message key="label.tableheaderISBN" /></th>
					    </tr>
					   <logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						 <tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					  </logic:iterate>
            	  </sakai:flat_list>
        	    </logic:notEqual>
         	  	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
         	       <sakai:heading><fmt:message key="function.booklistforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    	           <sakai:messages/>
     	           <sakai:messages message="true"/>
    	           <p/>
    	          <logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		           <sakai:group_table>
        		        <tr>
            		      <td><fmt:message key="function.datelastmodified"/></td>
                	      <td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	       </tr>
           		       <tr>
           			      <td><fmt:message key="function.lastmodifiedby"/></td>
           			      <td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		       </tr>
           		       <tr>
            		     <td><fmt:message key="function.currentstatus"/></td>
               		     <td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		   </tr>   
        	          </sakai:group_table>
  		         </logic:notEmpty>
    	         <p/>
    	         <sakai:group_heading><fmt:message key="label.compilelstmsg"/><br/></sakai:group_heading>
    			<logic:present name="bookMenuForm" property="courseNote">
				<sakai:group_table>
					<tr>
						<td><fmt:message key="label.updateviewfrmnote" /></td>
						<td><bean:write name="bookMenuForm" property="courseNote" filter="none" /></td>
					</tr>
					<tr>
						<td></td>
						<td>
						</td>
					</tr>
				</sakai:group_table>
			</logic:present>

			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>				
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>       
         	    </logic:equal>
         	
        	<logic:empty name="bookMenuForm" property="bookList">
               	<fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
        	</logic:empty>
        	<p/>
        
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
        		   		<html:submit styleClass="active" property="action"><fmt:message key="button.unconfirm"/></html:submit>
		                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
        
		</html:form>
	</logic:equal>	

    
    
    <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist authorized by COD">
    <html:form action="prebook">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        	</logic:notEqual>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	 	<sakai:heading> 
    		<fmt:message key="function.booklistforE"/> 
    		<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> </sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>					
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        
        	</logic:equal>
        	<logic:empty name="bookMenuForm" property="bookList">
               	<fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
        	</logic:empty>
        	<p/>
        
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
        
		</html:form>
	</logic:equal>
	    <logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist authorized by School Director">
    <html:form action="prebook">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        	</logic:notEqual>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	 	<sakai:heading> 
    		<fmt:message key="function.booklistforE"/> 
    		<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> </sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>				
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
						<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        
        	</logic:equal>
        	<logic:empty name="bookMenuForm" property="bookList">
               	<fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
        	</logic:empty>
        	<p/>
        
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
        
		</html:form>
	</logic:equal>
	
	<logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist authorized by Dean">
    <html:form action="prebook">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>
        	<!-- Sifiso Changes:START: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	<p/>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">
        		<logic:notEqual name="bookMenuForm" property="tutorCount" value="-1">
        			<logic:notEqual name="bookMenuForm" property="tutorCount" value="0">
        				<fmt:message key="label.contractedtutors"/>&nbsp<bean:write name="bookMenuForm" property="tutorCount"/>
        			</logic:notEqual>
        		</logic:notEqual>
        	</logic:equal>
        	<!-- Sifiso Changes:END: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	<p/>
        	</logic:notEqual>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	 	<sakai:heading> 
    		<fmt:message key="function.booklistforE"/> 
    		<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> </sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>				
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        
        	</logic:equal>
        	<logic:empty name="bookMenuForm" property="bookList">
               	<fmt:message key="function.nobookinfo"/> <bean:write name="bookMenuForm" property="acadyear"/>.
        	</logic:empty>
        	<p/>
        
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
        
		</html:form>
	</logic:equal>
	
	<logic:equal name="bookMenuForm" property="bookListStatus" value="Booklist open for editing by administrator">
    <html:form action="prebook">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        	<!-- Sifiso Changes:START: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">
        		<logic:notEqual name="bookMenuForm" property="tutorCount" value="-1">
        			<logic:notEqual name="bookMenuForm" property="tutorCount" value="0">
        				<fmt:message key="label.contractedtutors"/>&nbsp<bean:write name="bookMenuForm" property="tutorCount"/>
        			</logic:notEqual>
        		</logic:notEqual>
        	</logic:equal>
        	<!-- Sifiso Changes:END: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	</logic:notEqual>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	 	<sakai:heading> 
    		<fmt:message key="function.booklistforE"/> 
    		<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> </sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>				
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        
        	</logic:equal>
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>
        
		</html:form>
	</logic:equal>
        	
        	
        	
        	<logic:equal name="bookMenuForm" property="bookListStatus" value="Book list published by administrator">
    <html:form action="prebook">
    <logic:notEqual name="bookMenuForm" property="typeOfBookList" value="E">
    	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
           	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        	<!-- Sifiso Changes:START: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">
        		<logic:notEqual name="bookMenuForm" property="tutorCount" value="-1">
        			<logic:notEqual name="bookMenuForm" property="tutorCount" value="0">
        				<fmt:message key="label.contractedtutors"/>&nbsp<bean:write name="bookMenuForm" property="tutorCount"/>
        			</logic:notEqual>
        		</logic:notEqual>
        	</logic:equal>
        	<!-- Sifiso Changes:END: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	</logic:notEqual>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	 	<sakai:heading> 
    		<fmt:message key="function.booklistforE"/> 
    		<bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/> </sakai:heading>    	
    	<sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderebookpublicationJ" /></th>	
						<th><fmt:message key="label.tableheaderebookvolume" /></th>
							<th><fmt:message key="label.tableheaderebookpages" /></th>				
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="ebookVolume" /></td>
							<td><bean:write name="c" property="ebook_pages" /></td>
						
						</tr>
					</logic:iterate>
            	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<p/>
        
        	</logic:equal>
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>        
		</html:form>
		
	</logic:equal>  
	</logic:notEqual>    
	<logic:equal name="bookMenuForm" property="bookListDatesStatus" value="booklist Closed">
		 <html:form action="prebook">
		      <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">
        	          
          		      <sakai:heading><fmt:message key="function.booklistforE"/> <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>
    		          <sakai:instruction><fmt:message key="function.instructiontextforE"/><br/></sakai:instruction>
        	 </logic:equal>       
           	<sakai:heading><logic:equal name="bookMenuForm" property="typeOfBookList" value="P"><fmt:message key="function.booklist"/></logic:equal>
    		<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"><fmt:message key="function.booklistforR"/></logic:equal>
    	    <bean:write name="bookMenuForm" property="courseId"/> for <bean:write name="bookMenuForm" property="acadyear"/></sakai:heading>    	
    	    <sakai:messages message="true"/>
    	<p/>
    	<logic:notEmpty name="bookMenuForm" property="lastUpdated">
    		<sakai:group_table>
        		<tr>
            		<td><fmt:message key="function.datelastmodified"/></td>
                	<td><bean:write name="bookMenuForm" property="lastUpdated.transactionTime"/></td>
            	</tr>
           		<tr>
           			<td><fmt:message key="function.lastmodifiedby"/></td>
           			<td><bean:write name="bookMenuForm" property="displayAuditTrailName"/></td>
         		</tr>
           		<tr>
            		<td><fmt:message key="function.currentstatus"/></td>
               		<td><b><bean:write name="bookMenuForm" property="bookListStatus"/></b></td>
            		</tr>   
        	</sakai:group_table>
  		</logic:notEmpty>
    	<p/>
    	
			<logic:notEmpty name="bookMenuForm" property="bookList">
				<sakai:flat_list>
					<tr>
						<th><fmt:message key="label.tableheaderauthor" /></th>
						<th><fmt:message key="label.tableheaderyear" /></th>
						<th><fmt:message key="label.tableheadertitle" /></th>
						<th><fmt:message key="label.tableheaderedition" /></th>
						<th><fmt:message key="label.tableheaderpublisher" /></th>
						<th><fmt:message key="label.tableheaderISBN" /></th>
					</tr>

					<logic:iterate  name="bookMenuForm" property="bookList" id="c" indexId="cindex">
						<tr>
							<td><bean:write name="c" property="txtAuthor" /></td>
							<td><bean:write name="c" property="txtYear" /></td>
							<td><bean:write name="c" property="txtTitle" /></td>
							<td><bean:write name="c" property="txtEdition" /></td>
							<td><bean:write name="c" property="txtPublisher" /></td>
							<td><bean:write name="c" property="txtISBN" /></td>
						</tr>
					</logic:iterate>
           	</sakai:flat_list>
        	</logic:notEmpty>        	
        	<!-- Sifiso Changes:START: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	<p/>
        	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">
        		<logic:notEqual name="bookMenuForm" property="tutorCount" value="-1">
        			<logic:notEqual name="bookMenuForm" property="tutorCount" value="0">
        				<fmt:message key="label.contractedtutors"/>&nbsp<bean:write name="bookMenuForm" property="tutorCount"/>
        			</logic:notEqual>
        		</logic:notEqual>
        	</logic:equal>
        	<!-- Sifiso Changes:END: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Count for view -->
        	       	
        	<html:hidden property="cancelOption" value="TOMAINVIEW"/>
        		<sakai:actions>
	                <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
        		</sakai:actions>        
		</html:form>
		</logic:equal>
	</logic:notEmpty>
</sakai:html>	