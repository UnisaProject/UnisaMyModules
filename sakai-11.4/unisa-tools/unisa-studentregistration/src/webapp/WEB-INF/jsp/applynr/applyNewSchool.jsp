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
			
			//Check Radio selected value
			var radioSelected = $("input:radio[name='studentApplication.matricCertificate']:checked").val();
			if (radioSelected == "CG"){
				$("#currentGrade12").show();
			}else{
				$("#currentGrade12").hide();
			}
			
			var province = $("select[name='selectedProvince']").val();
			if (province != null && province != "-1"){
				var prevSchool = $("#prevSchool").val();
				if (prevSchool != null && prevSchool != "-1"){
					populateSchools(prevSchool);
				}
			}
			
			
			/**Change Matric Province - Use Ajax to Get School list without page refresh **/
			$("select[name='selectedProvince']").change(function(){
				cleanError();
				
				$("select[name='selectedSchool']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selectedSchool']").append('<option value="-1">Loading....</option>'); //Temp option to show if database retrieval is slow
				
				var province = $(this).val();
				var url = 'applyForStudentNumber.do?act=populateSchools&selectedProvince='+province;
				var schoolErr = false;
				$.getJSON(url, function(data) {
					$("select[name='selectedSchool']").empty(); //Remove all previous options again (Remove temp option above)
					var ddItems = [];
					ddItems.push('<option value="-1">Select School</option>');
					cache : false,
					$.each(data, function(key, data2) {
						//alert("Schools Key="+key);
						if (key === "Error"){
							showError("Error", data2);
							schoolErr = true;
							return false;
						}
						if (key === "Schools"){
							$.each(data2, function(key2, val2) {
								var splitString = val2.split('@');
								//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
								ddItems.push("<option value='"+val2+"'>"+splitString[1].toUpperCase()+"</option>");
							});
						}
					});
					if (!schoolErr){
						$("select[name='selectedSchool']").html(ddItems);
					}
				});
			});
		});
		
		function populateSchools(prevSchool) {
			/**Change Matric Province - Use Ajax to Get School list without page refresh **/
			$("select[name='selectedSchool']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selectedSchool']").append('<option value="-1">Loading....</option>'); //Temp option to show if database retrieval is slow
			
			var province = $("select[name='selectedProvince']").val();
			var url = 'applyForStudentNumber.do?act=populateSchools&selectedProvince='+province;
			var schoolErr = false;
			$.getJSON(url, function(data) {
				$("select[name='selectedSchool']").empty(); //Remove all previous options again (Remove temp option above)
				var ddItems = [];
				ddItems.push('<option value="-1">Select School</option>');
				cache : false,
				$.each(data, function(key, data2) {
					//alert("Schools Key="+key);
					if (key === "Error"){
						showError("Error", data2);
						schoolErr = true;
						return false;
					}
					if (key === "Schools"){
						$.each(data2, function(key2, val2) {
							var splitString = val2.split('@');
							//alert("Option="+key2+", Value="+splitString[0]+", Description="+splitString[1]);
							if(val2.toUpperCase() === prevSchool.toUpperCase()) {
								ddItems.push("<option value='"+val2+"' selected=selected>"+splitString[1].toUpperCase()+"</option>");
						    }else{
						    	ddItems.push("<option value='"+val2+"'>"+splitString[1].toUpperCase()+"</option>");
						    }
						});
					}
				});
				if (!schoolErr){
					$("select[name='selectedSchool']").html(ddItems);
				}
			});
		}

		function cleanError(){
			/**Clean error**/
			$("#forSchool").text('');
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=applyNewSchool';
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
	<html:hidden property="page" value="applyNewSchool"/>
	
	<input type="hidden" name="prevProv" id="prevProv" value="<bean:write name='studentRegistrationForm' property='selectedProvince'/>"/>
	<input type="hidden" name="prevSchool" id="prevSchool" value="<bean:write name='studentRegistrationForm' property='selectedSchool'/>"/>

	<label id="forSchool" style="color:red"></label>

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
							<td colspan="2"><strong><fmt:message key="page.apply.matric"/></strong></td>
						</tr><tr>
							<td colspan="2"><strong><fmt:message key="page.apply.matric.info"/></strong></td>
						</tr><tr>
							<td colspan="2">&nbsp;</td>
						</tr><tr>
							<td colspan="2"><strong><fmt:message key="page.apply.matriccertificate"/></strong></td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.matricCertificate" value="SC" onclick="currentGrade12(this);"/>&nbsp;<fmt:message key="page.apply.matricsc"/>&nbsp;&nbsp;
							</td><td>
								<html:radio property="studentApplication.matricCertificate" value="NC" onclick="currentGrade12(this);"/>&nbsp;<fmt:message key="page.apply.matricnc"/>
							</td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.matricCertificate" value="CG" onclick="currentGrade12(this);"/>&nbsp;<fmt:message key="page.apply.matriccg"/>&nbsp;&nbsp;
							</td><td>
								<html:radio property="studentApplication.matricCertificate" value="FF" onclick="currentGrade12(this);"/>&nbsp;<fmt:message key="page.apply.matricff"/>
							</td>
						</tr><tr id="currentGrade12" style="display: none;">
							<td colspan="2"><fmt:message key="page.apply.matriccg.info"/></td>		
						</tr><tr>
							<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.matricCertificate" value="OO" onclick="currentGrade12(this);"/>&nbsp;<fmt:message key="page.apply.matricoo"/>&nbsp;&nbsp;
								<html:text name="studentRegistrationForm" property="studentApplication.matricCertOther" maxlength="30" size="40"/>				
							</td>
						</tr><tr>
							<td colspan="2"><strong><fmt:message key="page.apply.matricadmission"/></strong></td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.heAdmission" value="E"/>&nbsp;<fmt:message key="page.apply.matricadme"/>&nbsp;&nbsp;
							</td><td>
								<html:radio property="studentApplication.heAdmission" value="M"/>&nbsp;<fmt:message key="page.apply.matricadmm"/>&nbsp;&nbsp;
							</td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.heAdmission" value="F"/>&nbsp;<fmt:message key="page.apply.matricadmf"/>
							</td><td>
								<html:radio property="studentApplication.heAdmission" value="B"/>&nbsp;<fmt:message key="page.apply.matricadmb"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.heAdmission" value="D"/>&nbsp;<fmt:message key="page.apply.matricadmd"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td><td>
								<html:radio property="studentApplication.heAdmission" value="C"/>&nbsp;<fmt:message key="page.apply.matricadmc"/>&nbsp;&nbsp;
							</td>
						</tr><tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<html:radio property="studentApplication.heAdmission" value="S"/>&nbsp;<fmt:message key="page.apply.matricadms"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td><td>
								<html:radio property="studentApplication.heAdmission" value="O"/>&nbsp;<fmt:message key="page.apply.matricadmo"/>
							</td>
						</tr><tr>
							<td><fmt:message key="page.apply.examnumber"/></td>
							<td><html:text name="studentRegistrationForm" property="studentApplication.matricExamnr" maxlength="13" size="15"/></td>
						</tr><tr>
							<td><fmt:message key="page.apply.matricyear"/></td>
							<td><html:text name="studentRegistrationForm" property="studentApplication.matricExamyear" maxlength="4" size="15"/></td>
						</tr><tr>
							<td><fmt:message key="page.apply.matricprovince"/></td>
							<td>
								<html:select name="studentRegistrationForm" property="selectedProvince" 
									errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
				 					<html:options collection="provincelist" property="value" labelProperty="label"/>
								</html:select>
							</td>
						</tr><tr>
							<td><fmt:message key="page.apply.matricSchool"/></td>
							<td>
								<html:select  name="studentRegistrationForm" property="selectedSchool"
									errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
									<html:option value="-1">Select School</html:option>
								</html:select>
							</td>
						</tr>
					</sakai:group_table>
				
					<sakai:actions>
						<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
						<html:submit property="act"><fmt:message key="button.back"/></html:submit>
						<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
					</sakai:actions>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" name="test" id="test" value="Edmund input Test"/>

</html:form>
</body>
</sakai:html>