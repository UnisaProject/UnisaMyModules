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
<script language="javascript">
      function windowOpen(url){
            window.open(url,'mywindow');
      }
</script>

    <div class="heading">
       <strong><fmt:message key="res.head"/> <fmt:message key="res.head2"/></strong>
    </div>
	
	<table class="pda_main_table" width="100%">
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
	  	    <form name="guideform">
		  	  <select style="top: 68; left: 643; position: absolute; z-index: 1" onChange="document.location.href=this.options[this.selectedIndex].value" name="guidelinks" > 
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
	    <td>
		    <hr/>
		    <strong>
				Don't know where to start? <br/>
			</strong>
			<br/>
			<ul>
			<li><strong>Electronic resources</strong> allows you to locate and access databases for your study and research</li>
			<li><strong>A - Z</strong> is an alphabetical list of all the Unisa Library's e-resources</li>
			<li><strong>Subjects</strong> lists the subjects by which you can identify specific e-resources</li>
			<li><strong>Publishers</strong> is a list of e-resource publishers or vendors</li>
			<li><strong>E-Journals</strong> lists the Unisa Library's collection of full text electronic journals</li>
			<li><strong>E-Books</strong> lists all the major full text e-book collections</li> 
			<li><strong>More</strong> provides additional options to identify e-resource titles</li>
			</ul>
		</td>
    </tr>
	
	<tr>
		<td>
			<hr/>
			<strong>
				Tips and Help <br/>
			</strong><br/>
				<ul>
				<li>The Library's e-resources are available to all Unisa staff and currently registered Unisa students</li>
				<li>Students need their Unisa student number and myUnisa password to access the e-resources. If you do not have a myUnisa password, you should first go to <html:link href="http://my.unisa.ac.za/" target="_blank">http://my.unisa.ac.za</html:link> and use <strong>Claim myLife Email</strong> and then  <strong>Join myUnisa</strong> to create your myUnisa password. Watch a <html:link href="http://podcasts.unisa.ac.za/library/attachments/18060C26-8A2C-4807-9269-C3F198B2CF3C/611B486F-1B09-47BD-AA08-5E92C0380811.m4v" target="_blank">short video</html:link> on how to do this.</li>
				<li><strong>Unisa staff</strong> must use their Unisa network username and password</li>
				<li>Read the <html:link href="http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=26901" target="_blank">Terms and conditions</html:link> for use of the Unisa Library Electronic resources.</li>
				<li>Are you experiencing <strong>problems</strong> using the e-resources? Read the Troubleshooting tips or <html:link href="http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=26910" target="_blank">report your problem online</html:link>.</li>
				<li>Find out about Mobile access to the library's e-resources (coming in 2012).</li>
				<li>Read the <html:link href="http://unisadbasenews.wordpress.com" target="_blank">E-Resources News @ Unisa Library</html:link> news blog to learn about new e-resources added to the collection, e-resources on trial or changes and new content added to existing e-resources.</li>
				<li><html:link href="http://www.unisa.ac.za/default.asp?Cmd=ViewContent&ContentID=15871" target="_blank">Ask a librarian </html:link> is an online form to request additional help</li>
				<li><html:link href="http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=26194" target="_blank">Training</html:link> provides access to a collection of manuals that will help you to search the e-resources  effectively</li>
				<li>The library welcomes <html:link href="http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=26922" target="_blank">feedback and suggestions</html:link> on our e-resources</li>
				</ul>
			<hr/>
		</td>
	</tr>
	
	<tr class="pda_tr_spacer">
	<td>
	<strong>
		Featured database
	</strong>
	</td>
	</tr>
	
	
	  <tr>
      <td>
      <logic:notEmpty name="libmainForm" property="featuredDatabase">
                  <logic:iterate name="libmainForm" property="featuredDatabase" id="record" indexId="i">
                  <div class="A-Z-list">
                                          <div class="A-Z-list-logo">
                                                <a href="${record.vcampusUrl}" title='${record.vresname}' target="_blank">
                                                      <img name=record src="${record.url}"/>
                                                </a>
                                          </div>
                                          <div class="A-Z-list-title">
                                                <a href="${record.campusUrl}" target="_blank">
                                      				<span><bean:write name="record" property="dresName" /></span>
                                				</a>
                                				
                                		  <div class="A-Z-list-highlight">
                                		  <strong>
                                		  <I>
		                                  <bean:write name="record" property="highlightDesc"/>
		                                  </I>
		                                  </strong>
		                            	  </div>
                                          </div>
                                    


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
                                       
                                  <!-- div class="pda_div_float_padding_left"><bean:write name="record" property="daddInfo"/></div-->

                                  <div class="A-Z-list-bottom">
                                                <div class="A-Z-list-bottom-items"><span><a style="text-decoration:none" title='${record.subjectcover}'>Subjects covered</a></span></div>
                                                
                                      <logic:notEmpty name="record" property="rfManagement">
                                      
	                                      <div class="A-Z-list-bottom-items">
	                                       <a href="javascript:windowOpen('${record.rfManagement}')" class="bottomlinks">
	                                             How to cite
	                                       </a>
	                                      </div>
	                           		  </logic:notEmpty>
	                           		  
                                     <logic:notEmpty name="record" property="training">
                                                <fmt:message key="training"/>
                                              <div class="A-Z-list-bottom-items">
                                           
                                                <a href="${record.training}" title='${record.training}' class="bottomlinks" target="_blank">
                                                      <bean:write name="record" property="training" filter="false"/>
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
                                          News :
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