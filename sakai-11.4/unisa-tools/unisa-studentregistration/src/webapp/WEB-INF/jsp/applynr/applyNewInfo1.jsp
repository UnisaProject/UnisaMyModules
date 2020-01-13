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
		
	</style>	
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  

		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");	
			
		});

		/** Check Twin Sibling value **/
		function validate(){

			var language = $("select[name='selectedHomeLanguage']").find("option:selected").val();
			if (language == "-1"){
				showError("Error", "Please select your home language");
				return false;
			}
			var isTwin = $("input:radio[name='student.twinflag']:checked").val();
			if (isTwin !== "Y" && isTwin !== "N"){
				showError("Error", "Please indicate if you have a twin sibling");
				return false;
			}
			var nationality = $("select[name='selectedNationality']").find("option:selected").val();
			if (nationality == "-1"){
				showError("Error", "Please select your nationality");
				return false;
			}
			var population = $("select[name='selectedPopulationGroup']").find("option:selected").val();
			if (population == "-1"){
				showError("Error", "Please select your population group");
				return false;
			}
			var occupation = $("select[name='selectedOccupation']").find("option:selected").val();
			if (occupation == "-1"){
				showError("Error", "Please select your occupation");
				return false;
			}
			var economic = $("select[name='selectedEconomicSector']").find("option:selected").val();
			if (economic == "-1"){
				showError("Error", "Please select your economic sector");
				return false;
			}
			var activity = $("select[name='selectedPrevActivity']").find("option:selected").val();
			if (activity == "-1"){
				showError("Error", "Please select your previous activity");
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
	<html:hidden property="page" value="applyNewInfo1"/>
	
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
							<td colspan="2"><strong><fmt:message key="page.required.instruction"/></strong></td>
						</tr><tr>
							<td><fmt:message key="page.home.language"/>&nbsp;</td>
							<td>
								<html:select property="selectedHomeLanguage">
				 					<html:options collection="languagelist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.twin"/></td>
							<td valign="top">
								<html:radio property="student.twinflag" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="student.twinflag" value="N"/>&nbsp;<fmt:message key="page.no"/>
							</td>
						</tr><tr>
							<td colspan="2"><strong><fmt:message key="page.studentnr.apply.statistic"/></strong></td>
						</tr><tr>
							<td><fmt:message key="page.nationality"/>&nbsp;</td>
							<td>
								<html:select property="selectedNationality">
				 					<html:options collection="nationalitylist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.population"/>&nbsp;</td>
							<td>
								<html:select property="selectedPopulationGroup">
				 					<html:options collection="populationlist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.occupation"/>&nbsp;</td>
							<td>
								<html:select property="selectedOccupation">
				 					<html:options collection="occupationlist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.economic.sector"/>&nbsp;</td>
							<td>
								<html:select property="selectedEconomicSector">
				 					<html:options collection="economicsectorlist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.activity"/>&nbsp;</td>
							<td>
								<html:select property="selectedPrevActivity">
				 					<html:options collection="prevactivitylist" property="value" labelProperty="label"/>
								</html:select>
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