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
			
			//Hide doFinAid for SLP Students
			var isStuSLP = $("#isStuSLP").val();
			if (isStuSLP == "true"){
				$("#doFinAid").hide();	
				$("input:radio[name='studentApplication.finaidNsfas'][value='N']").prop('checked', true);
				$("input:radio[name='studentApplication.completeQual'][value='N']").prop('checked', true);
				$('#completeText').val(' ');
			}
			
			//Hide completeText
			$(".doCompleteText").css('visibility', 'hidden');
			
			//Manage Exam Centres according to Radio buttons
			/** Check previous selected Prison value **/
			var prisonRadio = $('#prevPrison').val(); 
			if (prisonRadio !== null && prisonRadio !== "" && prisonRadio !== "undefined"){
				//alert("Previous Prison Radio value="+prisonRadio);
				var prisonPrev = $('#prevExam').val(); 
				if (prisonPrev != null && prisonPrev != "" && prisonPrev !== "undefined"){
					if (prisonRadio === "Y"){
						//alert("Previous Prison Selected value="+prisonPrev);
						populateExamCentres(prisonPrev, "P");
					}else{
						populateExamCentres(prisonPrev, "F");
					}
				}else{
					//alert("Previous Prison Selected value Not Found!!");
					//populateExamCentres("", "FP");
					if (prisonRadio === "Y"){
						//alert("Previous Prison Selected value="+prisonPrev);
						populateExamCentres("", "P");
					}else{
						populateExamCentres("", "F");
					}
				}
			}else{
				//alert("Previous Prison Radio value Not Found!!");
				//populateExamCentres("", "FP");
				$("select[name='selectedExamCentre']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selectedExamCentre']").append('<option value="-1">Select Prisoner Yes/No above</option>'); //Temp option to show if database retrieval is slow

			}
			
			$("input:radio[name='studentApplication.prisoner']").change(function() {
				var isPrisoner = $(this).val();
				var selectFP = "FP";
				if (isPrisoner === 'Y') {
					selectFP = "P";
				}else if (isPrisoner === 'N') {
					selectFP = "F";
				}else{
					selectFP = "FP"; //Catch All
				}
				var prisonPrev = $('#prevExam').val(); 
				if (prisonPrev != null && prisonPrev != "" && prisonPrev !== "undefined"){
					populateExamCentres(prisonPrev, selectFP);
				}else{
					populateExamCentres("", selectFP);
				}
			});
			
			//$("input:radio[name='studentApplication.prisoner'][value='N']").prop('checked', true);
			
			var maxLength = 100;
			var qualRadio = $("input:radio[name='studentApplication.completeQual']:checked").val();
			if (qualRadio !== null && qualRadio !== "" && qualRadio !== "undefined") {
				if (qualRadio === 'Y') {
					$(".doCompleteText").css('visibility', 'visible');
					var textLine = $('#textLine').val();
				    if (textLine && 0 !== textLine.length){
				    	var textLength = maxLength - textLine.length;
				    	$('#completeText').val(textLine);
				    	$('#chars').text(textLength);
				    }
				}
			}else{
				$(".doCompleteText").css('visibility', 'hidden');
			}
			
			$("input:radio[name='studentApplication.completeQual']").change(function() {
				if (this.value === 'Y') {
					$(".doCompleteText").css('visibility', 'visible');
				}else {
					$(".doCompleteText").css('visibility', 'hidden');
			    }
			});

			
			$('#completeText').keyup(function() {
				var textLength = maxLength - $(this).val().length;
				$('#chars').text(textLength);
			});
			
			/**Change Exam Centre - Warn about Prison list without page refresh **/
			$("select[name='selectedExamCentre']").change(function(){
				//var isPrison = $("input:radio[name='studentApplication.prisoner']:checked").val();
				//if (isPrison === 'Y') {
				var valExam = $("select[name='selectedExamCentre']").find("option:selected").text();
				if (valExam != "-1" &&  (valExam.toLowerCase().indexOf("correctional") >= 0)){
					var chkP = confirm("You have selected a correctional facility as exam center. Click on 'OK' to confirm or on 'Cancel' to select a new exam centre.");
					if (chkP === false) {
						$("input:radio[name='studentApplication.prisoner'][value='N']").prop('checked', true);
						populateExamCentres("", "F");
					}
			    }
			});		
		});
		
		function populateExamCentres(examCentre, selectFP) {
			/**Change Exam Centres - Use Ajax to Get Centre list without page refresh **/
			$("select[name='selectedExamCentre']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selectedExamCentre']").append('<option value="-1">Loading....</option>'); //Temp option to show if database retrieval is slow
			
			var url = 'applyForStudentNumber.do?act=populateExamCentres&type='+selectFP;
			var examErr = false;
			$.getJSON(url, function(data) {
				$("select[name='selectedExamCentre']").empty(); //Remove all previous options again (Remove temp option above)
				var ddItems = [];
				ddItems.push('<option value="-1">Select from Menu</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Exam Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						examErr = true;
						return false;
					}
					if (key === "Exam"){
						$.each(data2, function(key2, val2) {
							var splitString = val2.split('~');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if(splitString[0].toUpperCase() === examCentre.toUpperCase()) {
								ddItems.push("<option value='"+splitString[0].toUpperCase()+"' selected=selected>"+splitString[1].toUpperCase()+"</option>");
						    }else{
						    	ddItems.push("<option value='"+splitString[0].toUpperCase()+"'>"+splitString[1].toUpperCase()+"</option>");
						    }
						});
					}
				});
				if (!examErr){
					$("select[name='selectedExamCentre']").html(ddItems);
				}
			});
		}

		function validateSelect(){
			var radioCounsel = $("input:radio[name='studentApplication.careerCounsel']:checked").val();
			var radioStaff = $("input:radio[name='studentApplication.staffCurrent']:checked").val();
			var radioDeceased = $("input:radio[name='studentApplication.staffDeceased']:checked").val();
			var radioPrisoner = $("input:radio[name='studentApplication.prisoner']:checked").val();
			var radioNSFAS = $("input:radio[name='studentApplication.finaidNsfas']:checked").val();
			var radioComplete = $("input:radio[name='studentApplication.completeQual']:checked").val();
				
			if (radioCounsel == null || radioCounsel == "" || radioCounsel == "undefined"){
				showError("Warning", "Please confirm if you require further Career counceling.");
				return false;
			}
			if (radioStaff == null || radioStaff == "" || radioStaff == "undefined"){
				showError("Warning", "Please confirm if you are a current or retired Unisa staff member.");
				return false;
			}
			if (radioDeceased == null || radioDeceased == "" || radioDeceased == "undefined"){
				showError("Warning", "Please confirm if you are a dependant of a current, retired or deceased permanent Unisa staff member.");
				return false;
			}
			if (radioPrisoner == null || radioPrisoner == "" || radioPrisoner == "undefined"){
				showError("Warning", "Please confirm if you are a prisoner.");
				return false;
			}
			var valExam = $("select[name='selectedExamCentre']").find("option:selected").val();
			if (valExam == null || valExam == "" || valExam == "undefined" || valExam == "-1"){
				showError("Warning", "Please select an Examination Centre."); 
				return false;
			}
			if (radioNSFAS == null || radioNSFAS == "" || radioNSFAS == "undefined"){
				showError("Warning", "Please indicate whether you will require Financial aid from NSFAS.");
				return false;
			}
			if (radioComplete == null || radioComplete == "" || radioComplete == "undefined"){
				showError("Warning", "Please confirm if you are in the process of completing a qualification.");
				return false;
			}else{
				if (radioComplete == "Y"){ 
					 var comment = $.trim($('#completeText').val());
					 if(comment.length == 0){
					    // textarea is empty or contains only white-space
						showError("Warning", "Please enter which qualification you will be completing.");
						return false;
					}
				}
			}
			//$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Processing. Please wait...</strong>" });
		}

		function cleanError(){
			/**Clean error**/
			$("#forExam").text('');
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
	<html:hidden property="page" value="applyNewInfo2"/>
	
	<input type="hidden" name="textLine" id="textLine" value="<bean:write name='studentRegistrationForm' property='studentApplication.completeText'/>"/>
	<input type="hidden" name="prevExam" id="prevExam" value="<bean:write name='studentRegistrationForm' property='selectedExamCentre'/>"/>
	<input type="hidden" name="prevPrison" id="prevPrison" value="<bean:write name='studentRegistrationForm' property='studentApplication.prisoner'/>"/>	
	<input type="hidden" id="isStuSLP" name="isStuSLP" value="<bean:write name='studentRegistrationForm' property='student.stuSLP' />" />
	

	<label id="forSchool" style="color:red"></label>
	<label id="forExam" style="color:red"></label>

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
							<td style="width:75%"><fmt:message key="page.apply.careercounsel"/></td>
							<td>
								<html:radio property="studentApplication.careerCounsel" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.careerCounsel" value="N"/>&nbsp;<fmt:message key="page.no"/>
							</td>
						</tr><tr>
							<td><fmt:message key="page.apply.currentStaff"/>&nbsp;</td>
							<td>
								<html:radio property="studentApplication.staffCurrent" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.staffCurrent" value="N"/>&nbsp;<fmt:message key="page.no"/>
							</td>
						</tr><tr>
							<td><fmt:message key="page.apply.deceasedStaff"/>&nbsp;</td>
							<td>
								<html:radio property="studentApplication.staffDeceased" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.staffDeceased" value="N"/>&nbsp;<fmt:message key="page.no"/>
							</td>
						</tr><tr>
							<td><fmt:message key="page.apply.prisoner"/>&nbsp;</td>
							<td>
								<html:radio property="studentApplication.prisoner" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.prisoner" value="N"/>&nbsp;<fmt:message key="page.no"/>
							</td>
						</tr><tr>
							<td colspan="2"><fmt:message key="page.examination.centre"/>&nbsp;</td>
						</tr><tr>
							<td colspan="2">
								<html:select name="studentRegistrationForm" property="selectedExamCentre" 
									errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" >
								</html:select>
							</td>
						</tr><tr>
							<td colspan="2"><font size="1">&nbsp;</font></td>
						</tr>
					</sakai:group_table>
					<div id="doFinAid">
						<sakai:group_table>
							<tr>
								<td colspan="2"><strong><fmt:message key="page.apply.finaid"/>&nbsp;</strong></td>
							</tr><tr>
								<td><fmt:message key="page.apply.nsfas"/></td>
								<td>
									<html:radio property="studentApplication.finaidNsfas" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="studentApplication.finaidNsfas" value="N"/>&nbsp;<fmt:message key="page.no"/>
								</td>
							</tr><tr>
								<td><fmt:message key="page.apply.complete"/></td>
								<td>
									<html:radio property="studentApplication.completeQual" value="Y"/>&nbsp;<fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="studentApplication.completeQual" value="N"/>&nbsp;<fmt:message key="page.no"/>
								</td>
							</tr><tr>
								<td>
									<div class="doCompleteText">
										<fmt:message key="page.apply.completeInfo"/>
									</div>
								</td>
								<td>
									<div class="doCompleteText">
										<textarea id="completeText" name="completeText" rows="2" cols="50" maxlength="100"></textarea>
										<br>
										<font size="1"><span id="chars"></span> Characters remaining</font>
									</div>
								</td>
							</tr>
						</sakai:group_table>
					</div>
				</div>
				<div class="panel-footer clearfix">
					<sakai:actions>
						<html:submit property="act" onclick="return validateSelect();"><fmt:message key="button.continue"/></html:submit>
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