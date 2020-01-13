<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
        
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
		#color {
		  /**background-color: red;**/
		  float: left;
		}
		#color2 {
		  /**background-color: red;**/
		  float: left;
		}
		#doMainUDNo,#doMainUDNo,#doMainHONNo,#doMainHONNo,#doMainMDNo,#doMainDOCNo,#doMainPAYNo {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.itemSummary, .itemSummaryLite {
		    margin: 0;
		}
	
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    input[type="text"] {
		    width: 100%;
		    padding: 0 5px;
		}
		
	</style>	
	
    <script type="text/javascript">  
      
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  
    
		$(document).ready(function() {
		    
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			//Reset all Radio Buttons
			$('input:radio[name="studentApplication.radioPrev"]').removeAttr('checked');
			$('input:radio[name="studentApplication.radioNDP"]').removeAttr('checked');
			$("input:radio[name='studentApplication.radioRPL']").removeAttr('checked');			
			
			$('input[name="studentApplication.radioNDP"]:radio').change(function(){
				var radVal = $(this).attr("value");
				if (radVal == 'Y') {
			    	//alert("NDP Selected");
			    	$("#doModules").show();
				}else if (radVal == 'N') {
					//alert("NDP De-Selected");
					$("#doModules").hide();
				}
			});
			
		});
		
		function showError(errorTitle, errorText) {
			// show the actual error modal
			$('#dialogContent').html(errorText);
			$('#dialogHolder').dialog({
				autoOpen: true,
			  	title: errorTitle,
			  	modal: true,
			  	buttons: {
			  		"Ok": function() {
			  			$(this).dialog("close");
			  		}
			  	}
			});
		}
		
		function validateSelect(){
			//alert("In Validate");
			
			var radioPrev = $("input:radio[name='studentApplication.radioPrev']:checked").val();
			var radioRPL = $("input:radio[name='studentApplication.radioRPL']:checked").val();
			var radioNDP = $("input:radio[name='studentApplication.radioNDP']:checked").val();
			
			var ndpRegList1 = $("input[name='studentApplication.ndpRegSu1']").val();
			
			if (radioPrev == null || radioPrev == "" || radioPrev == "undefined"){
				showError("Error", "Please indicate if you have previously applied for postgraduate qualifications at Unisa");
				return false;
			}
			if (radioRPL == null || radioRPL == "" || radioRPL == "undefined"){
				showError("Error", "Please indicate if you applied for direct admission via the Recognition of Prior Learning (RPL) route");
				return false;
			}
			if (radioNDP == null || radioNDP == "" || radioNDP == "undefined"){
				showError("Error", "Please indicate if you have completed or if you wish to register for any non-degree purposes (NDP) module/s to gain admission to a specific qualification");
				return false;
			}
			if (radioNDP == "Y" && (ndpRegList1 == null || ndpRegList1 == "" || ndpRegList1 == "undefined")){
				showError("Error", "Please enter at least one Study Unit or Module you wish to register for.");
				return false;
			}
			doSubmit("Continue");
		}
		
		function doSubmit(input,check){
			if (input === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=applyNDP';
			}else if (input === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (input === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel'; 
			}
			$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br></strong>" });
			document.studentRegistrationForm.submit();
		}

	</script>	
</head>
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">

<!-- Form -->
<html:form action="/applyForStudentNumber">

	<html:hidden property="page" value="applyNDP"/>
	
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<div class="container">
		<br/>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.post.NDP.header"/></h3>
				</div>
				<div class="panel-body">
					<table>
						<tr>
							<td><strong><fmt:message key="page.required.instruction"/></strong></td>
							<td width="50px" align="center"><fmt:message key="page.yes"/></td>
							<td width="50px" align="center"><fmt:message key="page.no"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.studentnr.post.radioPrev"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioPrev" value="Y"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioPrev" value="N"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.studentnr.post.radioRPL"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioRPL" value="Y"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioRPL" value="N"/></td>
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.studentnr.post.radioNDP"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioNDP" value="Y"/></td>
							<td align="center"><html:radio name="studentRegistrationForm" property="studentApplication.radioNDP" value="N"/></td>
						</tr>
					</table>
					<div id="doModules" style="display: none;">
						<table>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr><td colspan="2"><fmt:message key="page.studentnr.post.NDP.info1"/></td></tr>
							<tr><td colspan="2"><fmt:message key="page.studentnr.post.NDP.info2"/></td></tr>
							<tr><td colspan="2"><fmt:message key="page.studentnr.post.NDP.info3"/></td></tr>
							<tr><td colspan="2">&nbsp;</td></tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu1" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu2" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu3" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu4" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu5" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu6" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu7" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu8" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu9" maxlength="7" size="15"/></td>
							</tr>
							<tr><td><fmt:message key="page.studentnr.post.NDP.modules"/></td>
								<td><html:text name="studentRegistrationForm" property="studentApplication.ndpRegSu10" maxlength="7" size="15"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="validateSelect('Continue');">Continue</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');">Back</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>

</html:form>
  </body>
</sakai:html>