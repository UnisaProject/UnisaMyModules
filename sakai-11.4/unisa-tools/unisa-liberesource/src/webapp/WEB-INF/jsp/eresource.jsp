<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.liberesource.ApplicationResources"/>

<sakai:html>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<body>
<html:form action="/resource">
<!-- link rel="stylesheet" type="text/css" href="http://www.unisa.ac.za/stylesheets/lib_e_resources.css" /-->
<link rel="stylesheet" type="text/css" href="https://my.unisa.ac.za/stylesheets/lib_e_resources.css" />

<html:hidden name="libmainForm" property="placeId" />
<html:hidden name="libmainForm" property="selectedSubjectId" />
<html:hidden name="libmainForm" property="selectedVendorId" />
<script language="javascript">
      function windowOpen(url){
            window.open(url,'mywindow');
      }
</script>

    <div class="heading">
       <strong><fmt:message key="res.head"/> <fmt:message key="res.head2"/></strong>
    </div>
      
      <table cellpadding="0" cellspacing="0" class="pda_main_table" width="100%" >
            <tr>
                  <td>
                        <ul id="tabs">
                              <logic:notEmpty name="libmainForm" property="tabs">
                                    <logic:iterate name="libmainForm" property="tabs" id="record" indexId="i">
                                          <c:if test="${record.selected == 'false'}"> 
                                          <li>
                                                <a href="${record.link}">
                                                      <span><bean:write name="record" property="name" /></span>
                                                </a>
                                          </li>
                                          </c:if>
                                          <c:if test="${record.selected == 'true'}"> 
                                          <li class="selectedTab">
                                                <a href="${record.link}">
                                                      <span><bean:write name="record" property="name" /></span>
                                                </a>
                                          </li>
                                          </c:if>
                                    </logic:iterate>
                              </logic:notEmpty>
                                <li>
                                <form name="guideform">
                                <select style="top: 68; left: 643; position: absolute; z-index: 1" onChange="document.location.href=this.options[this.selectedIndex].value" name="guidelinks"> 
                                      <option selected>--more--</option>
                                          <logic:notEmpty name="libmainForm" property="moreTabsList">
                                          <logic:iterate name="libmainForm" property="moreTabsList" id="record" indexId="i">
                                          <option value="${record.link}">
                                          <bean:write name="record" property="name" />
                                          </option>
                                          </logic:iterate>
                                          </logic:notEmpty>
                                    </select>
                                    </form>
                                </li>
                        </ul>
                  </td>
            </tr>
        
            <tr>

            <tr><td>
                  <sakai:messages/>
                  <sakai:messages message="true"/>
            </td></tr>
            
            
            <tr>
                <td>
                <div class="heading">
                        <fmt:message key="res.head"/> <br><strong><bean:write name="libmainForm" property="heading"/></strong>
                </div>
                <div class="instruction">
                        Click on the Subject to view the e-resources for that subject
                </div>
             	</td>
             </tr>
 
      
            <tr>
            <tr>
            <td>
            
            <div class="A-Z-list-Toplinks">
            						<logic:notEmpty name="libmainForm" property="tabs">
                                    <logic:iterate name="libmainForm" property="tabs" id="record" indexId="i">
                                          <c:if test="${record.selected == 'true'}"> 
                  
                                                <logic:notEmpty name="libmainForm" property="alphabets">
                                                      <logic:iterate name="libmainForm" property="alphabets" id="record" indexId="i">
                                                            <c:if test="${record.enabled == true}"> 
                                                                  <a href="${record.link}">
                                                                  <u>
                                                                        <span><bean:write name="record" property="alphabet"/></span>
                                                                  </a>
                                                                  </u>
                                                            </c:if>
                                                            <c:if test="${record.enabled == false}"> 
                                                                  <a>
                                                                        <span><bean:write name="record" property="alphabet"/></span>
                                                                  </a>
                                                            </c:if>
                                                            &nbsp; &nbsp;
                                                      </logic:iterate>
                                                </logic:notEmpty>
                                          </c:if>
                                    </logic:iterate>
                                    
                                    <logic:iterate name="libmainForm" property="moreTabsList" id="record2" indexId="i">
                                          <c:if test="${record2.selected == 'true'}"> 
                  
                                                <logic:notEmpty name="libmainForm" property="alphabets">
                                                      <logic:iterate name="libmainForm" property="alphabets" id="record2" indexId="i">
                                                            <c:if test="${record2.enabled == true}"> 
                                                                  <a href="${record2.link}">
                                                                  <u>
                                                                        <span><bean:write name="record2" property="alphabet" /></span>
                                                                  </u>
                                                                  </a>
                                                            </c:if>
                                                            <c:if test="${record2.enabled == false}"> 
                                                                  <a>
                                                                        <span><bean:write name="record2" property="alphabet" /></span>
                                                                  </a>
                                                            </c:if>
                                                            &nbsp; &nbsp;
                                                      </logic:iterate>
                                                </logic:notEmpty>
                                          </c:if>
                                    </logic:iterate>
                  </logic:notEmpty>
            </div>
        </td>
    </tr>
    </tr>
            </tr>
            
			<tr>
			<td>
            <logic:notEmpty name="libmainForm" property="selectedSubjectList">
                  <logic:iterate name="libmainForm" property="selectedSubjectList" id="record" indexId="i">
                  <div class="A-Z-list">
           
                           <div class="A-Z-list-logo">
                              <a href="${record.vcampusUrl}" title='${record.vresname}' target="_blank">
                                    <img name=record src="${record.url}"/>
                              </a>
                            </div>
                            <div class="A-Z-list-title">
                            
                            	<a href="${record.campusUrl}" target="_blank">
                                      <span><bean:write name="record" property="resName" /></span>
                                </a>
                                  <!-- bean:write name="record" property="resName"/-->
                             <div class="A-Z-list-highlight">
                             <strong>
                             <I>
                                  <bean:write name="record" property="highlightDesc"/>
                             </I>
                             </strong>
                            </div>
                            </div>
                                  
	                        <!-- div class="pda_div_float_padding_left"><bean:write name="record" property="daddInfo"/></div-->
									
					
							 	  <div class="A-Z-list-content-type">
	                                  <strong>
	                                  <I>
	                                     <bean:write name="record" property="contentType"/>
	                                  </I>
	                                  </strong>
                            	  </div>
                            <div class="A-Z-list-desc">
                                     <bean:write name="record" property="resdescr"/>
                            </div>

                            <logic:notEmpty name="record" property="accessNote">
                                             <div class="A-Z-list-access"><fmt:message key="access"/>
                                             <bean:write name="record" property="accessNote"/>
                                             </div>
                            </logic:notEmpty>
                            
                            <!-- div class="pda_div_padding_top"-->
                                <!-- div style="float:left; font-weight:bold"><fmt:message key="more.resources"/></div-->
                                <!--div class="pda_div_float_padding_left"><a href="javascript:windowOpen('${record.campusUrl}')"><a href="javascript:windowOpen('${record.campusUrl}')"></a></div-->
                            <!-- /div-->  

                                  <div class="A-Z-list-bottom">
                                      <div class="A-Z-list-bottom-items"><span><a style="text-decoration:none" title='${record.subjectcover}'>Subjects covered</a></span></div>

                                      <logic:notEmpty name="record" property="training">
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="${record.training}" class="bottomlinks" target="_blank">
	                                             Training
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty>
                                       
                                      <logic:notEmpty name="record" property="newsTitle">
                                          <div class="A-Z-list-bottom-items">
										  <strong> News: </strong>
                                                <a href="${record.newsUrl}" class="bottomlinks" target="_blank">
                                                      <bean:write name="record" property="newsTitle" />
                                                </a>
                                          </div>
                                      </logic:notEmpty>
                                      
                                     <logic:notEmpty name="record" property="alert">
                                      	<div class="A-Z-list-bottom-items">
                                      			<div class="A-Z-list-bottom-items"><a href="${record.alert}" target="_blank">Creating alerts</a></div>
                                      	</div>
                                      </logic:notEmpty>
                                      
                                      <logic:notEmpty name="record" property="viewPassword">
                                          View Password 
                                                <a href="${record.training}" title='${record.viewPassword}' class="bottomlinks" target="_blank">
                                                      view password
                                                </a>
                                      </logic:notEmpty>
                                </div>
            			<div>&nbsp;</div>
                        </div>
                         <div style="clear:both">
            	</div>
            </logic:iterate>
      </logic:notEmpty>
      </td>
      </tr>


	  <tr>
      <td>
      <logic:notEmpty name="libmainForm" property="selectedVendortList">
            <logic:iterate name="libmainForm" property="selectedVendortList" id="record" indexId="i">


                  <div class="A-Z-list">
                                          <div class="A-Z-list-logo">
                                            <a href="${record.vcampusUrl}" title="${record.vresname}" target="_blank">
                                                  <img name=record src="${record.url}"/>
                                            </a>
                                          </div>
                                          
                                          <div class="A-Z-list-title">
                                          <a href="${record.campusUrl}" target="_blank">
                                      			<span><bean:write name="record" property="vresname" /></span>
                                		  </a>
                                		  <div class="A-Z-list-highlight">
                                		  <strong>
                                		  <i>
                                                <bean:write name="record" property="highlightDesc"/>
                                          </i>
                                          </strong>
                                          </div>
                                          </div>
                                                
                                <!-- div class="pda_div_float_padding_left"><bean:write name="record" property="vaddInfo"/></div-->
                                  <div class="A-Z-list-content-type">
	                                  <strong>
	                                  <I>
	                                     <bean:write name="record" property="contentType"/>
	                                  </I>
	                                  </strong>
                            	  </div>
                            	  
                                  <div class="A-Z-list-desc">
                                                      <bean:write name="record" property="vresDescr"/>
                                  </div>
                                  
                                  <logic:notEmpty name="record" property="accessNote">
                                             <div class="A-Z-list-access"><fmt:message key="access"/>
                                             <bean:write name="record" property="accessNote"/>
                                             </div>
                                  </logic:notEmpty>
                                            
                                  <div class="A-Z-list-bottom">
										<div class="A-Z-list-bottom-items"><span><a style="text-decoration:none" title='${record.subjectcover}'>Subjects covered</a></span></div>
                                               
                                      <logic:notEmpty name="record" property="rfManagement">
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="${record.rfManagement}" class="bottomlinks" target="_blank">
	                                             How to cite
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty> 
	         
                                      
                                       <logic:notEmpty name="record" property="training">
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="${record.training}" class="bottomlinks" target="_blank">
	                                             Training
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty>
	                           		  
                                      <logic:notEmpty name="record" property="newsTitle">
                                          <div class="A-Z-list-bottom-items">
                                          <strong> News: </strong>
                                          </a> 
                                                <a href="${record.newsUrl}" class="bottomlinks" target="_blank">
                                                      <bean:write name="record" property="newsTitle" />
                                                </a>
                                          </div>
                                      </logic:notEmpty>
                                      
                                      
                                      <logic:notEmpty name="record" property="alert">
                                      	<div class="A-Z-list-bottom-items">
                                      			<div class="A-Z-list-bottom-items"><a href="${record.alert}" target="_blank">Creating alerts</a></div>
                                      	</div>
                                      </logic:notEmpty>
                                      
                                      <logic:notEmpty name="record" property="viewPassword">
                                          View Password 
                                                <a href="${record.training}" title='${record.viewPassword}' class="bottomlinks" target="_blank">
                                                      view password
                                                </a>
                                      </logic:notEmpty>
                                    </div>
                                    <div>&nbsp;</div>
                              
				</div>
				
				    <div style="clear:both">
            </div>
            </logic:iterate>
      </logic:notEmpty>
      </td>
      </tr>




      <tr>
      <td>
      
      <logic:notEmpty name="libmainForm" property="selectSpecificdbList">
                  <logic:iterate name="libmainForm" property="selectSpecificdbList" id="record" indexId="i">
                  <div class="A-Z-list">
                                          <div class="A-Z-list-logo">
                                                <a href="${record.vcampusUrl}" title="${record.vresname}" target="_blank">
                                                      <img name=record src="${record.url}"/>
                                                </a>
                                          </div>
                                          
                                          <div class="A-Z-list-title">
                                          <a href="${record.campusUrl}" target="_blank">
                                      			<span><bean:write name="record" property="dresName" /></span>
                                		  </a>
                                                <!-- bean:write name="record" property="dresName"/-->
                                          <div class="A-Z-list-highlight">
                                          <strong>
                                          <i>
		                                  <bean:write name="record" property="highlightDesc"/>
		                                  </i>
		                                  </strong>
		                                  </div>
                                          </div>
                                          

                                <!-- div class="pda_div_float_padding_left"><bean:write name="record" property="daddInfo"/></div-->
                                

		                            
		                            <logic:notEmpty name="record" property="expiryDate">
		                            <div class="A-Z-list-highlight">
		                            	   <strong>Trial Expires : </strong>
		                                  <bean:write name="record" property="expiryDate"/>
		                            </div>
		                            </logic:notEmpty>
		                            
                                  <div class="A-Z-list-content-type">
	                                  <strong>
	                                  <I>
	                                     <bean:write name="record" property="contentType"/>
	                                  </I>
	                                  </strong>
                            	  </div>
                                  <div class="A-Z-list-desc">
                                                      <bean:write name="record" property="dresDescr"/>
                                  </div>
		                                                                      	  
                            	  	   <logic:notEmpty name="record" property="accessNote">
                                             <div class="A-Z-list-access"><fmt:message key="access"/>
                                             <bean:write name="record" property="accessNote"/>
                                             </div>
                                       </logic:notEmpty>      
                                             <div class="A-Z-list-bottom">
                                                <div class="A-Z-list-bottom-items"><span><a style="text-decoration:none" title='${record.subjectcover}'>Subjects covered</a></span></div>
                                      
                                      <logic:notEmpty name="record" property="rfManagement">
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="${record.rfManagement}" class="bottomlinks" target="_blank">
	                                             How to cite
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty>
                                                
	                                  <logic:notEmpty name="record" property="training">
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="${record.training}" class="bottomlinks" target="_blank">
	                                             Training
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty>

                                      <logic:notEmpty name="record" property="alert">
                                      	<div class="A-Z-list-bottom-items">
                                      			<div class="A-Z-list-bottom-items"><a href="${record.alert}" target="_blank">Creating alerts</a></div>
                                      	</div>
                                      </logic:notEmpty>
                                                                            
                                      <logic:notEmpty name="record" property="newsTitle">
                                          <div class="A-Z-list-bottom-items">
                                          <strong>
                                          News:
                                          </strong>
                                           
                                                <a href="${record.newsUrl}" class="bottomlinks" target="_blank">
                                                      <bean:write name="record" property="newsTitle" />
                                                </a>
                                          </div>
                                      </logic:notEmpty>
                                      
                                     
                                      <logic:notEmpty name="record" property="viewPassword">
                                          View Password 
                                                <a href="${record.training}" title='${record.viewPassword}' class="bottomlinks" target="_blank">
                                                      view password
                                                </a>
                                      </logic:notEmpty>

                                </div>
            <div>&nbsp;</div>
                                  
            </div>
            
            <div style="clear:both">
            </div>
            </logic:iterate>
            </logic:notEmpty>

            </td>
            </tr>
      </table>
</html:form>
</body>
</sakai:html>
