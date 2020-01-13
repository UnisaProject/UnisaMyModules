<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
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
		.full-width {
		  width: 100vw;
		  position: relative;
		  left: 50%;
		  right: 50%;
		  margin-left: -50vw;
		  margin-right: -50vw;
		}
		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
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
			
		    //change Disability 
			$("select[name='selectedDisability']").change(function(){
				var qual = $(this).val();
				//alert("qual: " + qual);
				var zxcenable=qual.split('@')[0];  
				//alert("zxcenable: " + zxcenable);
				if(zxcenable <= 1){
					//alert("disText: hide");
					$("#disText").hide();
					//$(".slidingDiv").hide();
				} else {
					//alert("disText: show");
					$("#disText").show();
				}
			});
		    
			$("input[name='Continue']").click(function(){
				//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Continuing...</strong>" });
	        }); 
			$("input[name='Back']").click(function(){
	            //$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Going Back...</strong>" });
	        });
			$("input[name='Cancel']").click(function(){
	            //$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Logging Out...</strong>" });
	        });
	});
	
    function enableDisable(zxcobj){ 
    	zxcval=zxcobj.options[zxcobj.selectedIndex].value;
    	//alert("disability: " + zxcval);
    	zxcenable=zxcval.split('@')[0];  
    	//alert("zxcenable: " + zxcenable);
    	if (zxcenable > 1){
    		//alert("disability: block");
    		document.getElementById('disText').style.display = 'none';
    		//var mySpan = document.getElementById('disText');
    		//mySpan.style.display = 'inline';
			return false;
    	}else{
    		//alert("disability: none");
    		document.getElementById('disText').style.display = 'block';
    		//var mySpan = document.getElementById('disText');
    		//mySpan.style.display = 'block';
    		return false;
    	}
    }
    
	//Click button
	function doSubmit(button){
		if (button === "Continue"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepRetRadio';
		}else if (button === "Back"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
		}else if (button === "Cancel"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
		}
		document.studentRegistrationForm.submit();
	}
	
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
  
     </script>  
     
</head>  
<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyNewPersonal"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
	<br/>
	<div class="container full-width">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">	
					<sakai:group_table>
						<tr>
							<td colspan="3"><strong><fmt:message key="page.required.instruction"/></strong></td>
						</tr><tr>
							<td><fmt:message key="page.surname"/>&nbsp;</td>
							<td colspan="2"><html:text name="studentRegistrationForm" property="student.surname" maxlength="28" size="30"/></td>
						</tr><tr>
							<td><fmt:message key="page.initials"/>&nbsp;</td>
							<td colspan="2"><html:text name="studentRegistrationForm" property="student.initials" maxlength="20" size="22"/><span class="small"><i>(Leave a space between initials)</i></span></td>
						</tr><tr>
							<td><fmt:message key="page.title"/>&nbsp;</td>
							<td colspan="2">
								<html:select property="student.title">
				 					<html:options collection="titlelist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.firstnames"/>&nbsp;</td>
							<td colspan="2"><html:text name="studentRegistrationForm" property="student.firstnames" maxlength="60" size="40"/></td>
						</tr><tr>
							<td>
								<fmt:message key="page.studentnr.apply.maiden"/><br/>
								<span class="small"><i><fmt:message key="page.studentnr.apply.maidenMsg"/></i></span>
							</td>
							<td colspan="2"><html:text name="studentRegistrationForm" property="student.maidenName" maxlength="28" size="30"/></td>
						</tr><tr>
							<td><fmt:message key="page.studentnr.apply.birthdate"/>&nbsp;</td>
							<td colspan="2">
								<html:text name="studentRegistrationForm" property="student.birthYear" maxlength="4" size="5"/>&nbsp;
								/&nbsp;<html:text name="studentRegistrationForm" property="student.birthMonth" maxlength="2" size="3"/>&nbsp;
								/&nbsp;<html:text name="studentRegistrationForm" property="student.birthDay" maxlength="2" size="3"/>&nbsp;
							</td>
						</tr><tr>
							<td><fmt:message key="page.studentnr.apply.enter"/>&nbsp;</td>
							<td><html:radio property="student.idType" value="R"/>&nbsp;<span class="small"><fmt:message key="page.studentnr.apply.id"/></span></td>
							<td><html:text name="studentRegistrationForm" property="student.idNumber" maxlength="13" size="30"/></td>
						</tr><tr>
							<td>&nbsp;</td>
							<td><html:radio property="student.idType" value="F"/>&nbsp;<span class="small"><fmt:message key="page.studentnr.apply.foreign"/></span></td>
							<td><html:text name="studentRegistrationForm" property="student.foreignIdNumber" maxlength="30" size="32"/></td>
						</tr><tr>
							<td>&nbsp;</td>
							<td><html:radio property="student.idType" value="P"/>&nbsp;<span class="small"><fmt:message key="page.studentnr.apply.passport"/></span></td>
							<td><html:text name="studentRegistrationForm" property="student.passportNumber" maxlength="30" size="32"/></td>
						</tr><tr>
							<td><fmt:message key="page.studentnr.apply.gender"/>&nbsp;</td>
							<td><html:radio property="student.gender" value="F"/>&nbsp;<fmt:message key="page.female"/></td>
							<td><html:radio property="student.gender" value="M"/>&nbsp;<fmt:message key="page.male"/></td>
						</tr><tr>
							<td><fmt:message key="page.studentnr.apply.disability"/></td>
							<td colspan="2">
								<html:select property="selectedDisability">
				 					<html:options collection="disabilitylist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td colspan="3">
								<div id="disText" style="display: none;">
									<fmt:message key="page.studentnr.apply.disabilityText"/>
								</div>
							</td>
						</tr>
						
					</sakai:group_table>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>

</html:form>
</body>
</sakai:html>