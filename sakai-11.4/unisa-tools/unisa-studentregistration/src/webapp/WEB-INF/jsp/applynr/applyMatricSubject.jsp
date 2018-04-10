<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
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

<%
 StudentRegistrationForm stuRegForm = (StudentRegistrationForm)session.getAttribute("studentRegistrationForm");
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
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style type="text/css">
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
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.ui-dialog {
		    left: 50% !important;
		    top: 100px !important;
		    margin-left: -175px !important; 
		    /*margin-top: -175px !important;*/
		} 
		.dialog .ui-icon {
		    background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		.ui-state-default .ui-icon {
			background-image: url("<c:url value='/resources/images/ui-icons_222222_256x240.png' />");
		}
		/* Override jQuery UI theme's padding on buttons: */
		.ui-button-text-only .ui-button-text {
			padding: 0.2em 0.5em;
		}
		input[type='radio'] {
			float: left;
		}
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		    display: table-cell;
		}
		table { 
		    border-spacing: 2px;
		    border-collapse: separate;
		}
		td { 
		    padding: 2px;
		}
		.buttonFooter {
		    background-color: #333;
		    padding: 16px;
		}
	</style>
	
	<script type="text/javascript">
	
		function disableBackButton(){window.history.forward()} 
		disableBackButton(); 
		window.onload=disableBackButton(); 
		window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
		window.onunload=function() { void(0) }  
		
		/**Call when document is ready**/
		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			var matCheck = $("#matCert").val();
			//alert("Page Start - matCheck="+matCheck);
			if (matCheck == "MATR" || matCheck == "SENR" || matCheck == "ONBK"){
				//alert("Page Start - Show SC");
				 $(".goSC").show();
				 $(".goNSC").hide();
			}else{
				//alert("Page Start - Show NSC");
				 $(".goSC").hide();
				 $(".goNSC").show();
			}
			
			var listSize = $("#listSize").val();
			var listNext = 0;
			if (listSize > 0){
				for (var listNo = 0; listNo < listSize; listNo++){
					//Get Previously selected Subject Info
					var listPlus = listNo + 1;
					var selSubjectName = $("#selectedSubName"+listPlus).val();
					var selSubjectGrade = $("#selectedSubGrade"+listPlus).val();
					var selSubjectSymbol = $("#selectedSubSymbol"+listPlus).val();
					var selSubjectResult = $("#selectedSubResult"+listPlus).val();
					if (selSubjectName != null && selSubjectName != "" && selSubjectName != "undefined"){
						populateSubject(listPlus, selSubjectName, selSubjectGrade, selSubjectSymbol, selSubjectResult);
					}
					listNext = listPlus;
				}
				populateSubject(listNext + 1, "", "", "", "");
			}else{
				populateSubject(1, "", "", "", "");
			}

		    //Change Subject
		    $("select[name='subjectName1']").change(function(){
				populateSubject(2, "", "", "", "");
		    });
		    $("select[name='subjectName2']").change(function(){
				populateSubject(3, "", "", "", "");
		    });
		    $("select[name='subjectName3']").change(function(){
				populateSubject(4, "", "", "", "");
		    });
		    $("select[name='subjectName4']").change(function(){
				populateSubject(5, "", "", "", "");
		    });
		    $("select[name='subjectName5']").change(function(){
				populateSubject(6, "", "", "", "");
		    });
		    $("select[name='subjectName6']").change(function(){
				populateSubject(7, "", "", "", "");
		    });
		    $("select[name='subjectName7']").change(function(){
				populateSubject(8, "", "", "", "");
		    });
		    $("select[name='subjectName8']").change(function(){
				populateSubject(9, "", "", "", "");
		    });
		    $("select[name='subjectName9']").change(function(){
				populateSubject(10, "", "", "", "");
		    });
		    $("select[name='subjectName10']").change(function(){
				populateSubject(11, "", "", "", "");
		    });
		    $("select[name='subjectName11']").change(function(){
				populateSubject(12, "", "", "", "");
		    });
		    
		});
		
		//This will sort your array
		function SortByCode(a, b){
		  	var aCode = a.code.toLowerCase();
		  	var bCode = b.code.toLowerCase(); 
		  	return ((aCode < bCode) ? -1 : ((aCode > bCode) ? 1 : 0));
		}
		
		function SortByDesc(a, b){
			var aName = a.desc.toLowerCase();
			var bName = b.desc.toLowerCase(); 
			return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
		}
		
		function populateSubject(subNo, selSubjectName, selSubjectGrade, selSubjectSymbol, selSubjectResult){
			//alert("In populateSubject - subNo="+subNo);
			
			$("select[name='subjectName"+subNo+"']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='subjectGrade"+subNo+"']").empty();
			$("select[name='subjectSymbol"+subNo+"']").empty();
			$("select[name='subjectResult"+subNo+"']").empty();
			$("select[name='subjectName"+subNo+"']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			$("select[name='subjectGrade"+subNo+"']").append('<option value="0">Loading....</option>');
			$("select[name='subjectSymbol"+subNo+"']").append('<option value="0">Loading....</option>');
			$("select[name='subjectResult"+subNo+"']").append('<option value="0">Loading....</option>');
			
			var subjectErr = false;
			var matCert = $("#matCert").val();
			//alert("In populateSubject - matCert="+matCert);
			
			var subject = [];
			for (var i = 1; i <= 12; i++){
				subject[i]  = $("select[name='subjectName"+i+"']").find("option:selected").val();
				if (subject[i]==null || subject[i]=="" || subject[i]==="undefined" || subject[i]==="false" || subject[i]==false || !subject[i]){
					subject[i]="0";
				/*}else{
					subject[i]  = $("#selectedSubName"+i).val();
					if (subject[i]==null || subject[i]=="" || subject[i]==="undefined" || subject[i]==="false" || subject[i]==false || !subject[i]){
						subject[i]="0";
					}*/
				}
			}
			
			var url = 'applyForStudentNumber.do?act=populateMatSubjects&matCert='+matCert+"&subject1="+subject[1]+"&subject2="+subject[2]+"&subject3="+subject[3]+"&subject4="+subject[4]+"&subject5="+subject[5]+"&subject6="+subject[6]+"&subject7="+subject[7]+"&subject8="+subject[8]+"&subject9="+subject[9]+"&subject10="+subject[10]+"&subject11="+subject[11]+"&subject12="+subject[12];
			
			//alert("In populateSubject - URL="+url);
			$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				$("select[name='subjectName"+subNo+"']").empty(); //Remove all previous options again (Remove temp option above)
				var ddItems = [];
				var count = 0;
				//data.sort(SortByDesc);
				cache : false,
				$.each(data, function(key, keyValue) {
					//alert("Subject Key="+key);
					if (key === "Error"){
						showError("Error", keyValue);
						subjectErr = true;
						return false;
					}else if (key === "Empty"){
						showError("Empty", keyValue);
						subjectErr = true;
						return false;
					}else if (key === "Subjects"){
						ddItems.push("<option value='0'>Select Subject</option>");
						$.each(keyValue, function(key2, keyValue2) {
							//alert("Users="+key2+"~"+keyValue2.toUpperCase());
							if (selSubjectName != null && selSubjectName != "" && selSubjectName != "undefined" && key2 == selSubjectName){
								ddItems.push("<option value='"+key2+"' selected=selected>"+keyValue2+"</option>");
							}else{
								ddItems.push("<option value='"+key2+"'>"+keyValue2+"</option>");
							}
							count++;
						});
					}
				});
				if(count==0){
					 $("select[name='subjectName"+subNo+"']").empty(); //Remove all previous options (Index cleanup for various browsers) 
					 $("select[name='subjectName"+subNo+"']").html('<option value="0">No Subject Found</option>');
					 $("select[name='subjectGrade"+subNo+"']").empty();
					 $("select[name='subjectGrade"+subNo+"']").html('<option value="0">No Subject</option>');
					 $("select[name='subjectSymbol"+subNo+"']").empty();
					 $("select[name='subjectSymbol"+subNo+"']").html('<option value="0">No Subject</option>');
					 $("select[name='subjectResult"+subNo+"']").empty();
					 $("select[name='subjectResult"+subNo+"']").html('<option value="0">No Subject</option>');
					 
				}else{
					$("select[name='subjectName"+subNo+"']").html(ddItems);
					if (matCert === "MATR" || matCert === "SENR" || matCert === "ONBK"){
						$("select[name='subjectGrade"+subNo+"']").html('<option value="0">No Subject</option>');
						$("select[name='subjectSymbol"+subNo+"']").html('<option value="0">No Subject</option>');
						populateGrade(subNo, selSubjectGrade);
						populateSymbol(subNo, selSubjectSymbol);
						$("select[name='subjectResult"+subNo+"']").empty();
						$(".goSC").show();
						$(".goNSC").hide();
					}else{
						$("select[name='subjectResult"+subNo+"']").html('<option value="0">No Subject</option>');
						populateResult(subNo, selSubjectResult);
						$("select[name='subjectGrade"+subNo+"']").empty();
						$("select[name='subjectSymbol"+subNo+"']").empty();
						$(".goSC").hide();
						$(".goNSC").show();
					 }	
				}
			});
		}
		
		function populateGrade(subNo, selSubjectGrade){
			//alert("populateGrade"+subNo);
			$("select[name='subjectGrade"+subNo+"']").empty();
			var ddGrades = [];
			ddGrades.push('<option value="0">Select Grade</option>');
			if (selSubjectGrade != null && selSubjectGrade != "" && selSubjectGrade != "undefined" && selSubjectGrade == "HG"){
				ddGrades.push('<option value="HG" selected=selected>HG - Higher Grade&nbsp;</option>');
			}else{
				ddGrades.push('<option value="HG">HG - Higher Grade&nbsp;</option>');
			}
			if (selSubjectGrade != null && selSubjectGrade != "" && selSubjectGrade != "undefined" && selSubjectGrade == "SG"){
				ddGrades.push('<option value="SG" selected=selected>SG - Standard Grade&nbsp;</option>');	
			}else{
				ddGrades.push('<option value="SG">SG - Standard Grade&nbsp;</option>');
			}
			if (selSubjectGrade != null && selSubjectGrade != "" && selSubjectGrade != "undefined" && selSubjectGrade == "LG"){
				ddGrades.push('<option value="LG" selected=selected>LG - Lower Grade&nbsp;</option>');
			}else{
				ddGrades.push('<option value="LG">LG - Lower Grade&nbsp;</option>');
			}
			$("select[name='subjectGrade"+subNo+"']").html(ddGrades);
		}
		
		function populateSymbol(subNo, selSubjectSymbol){
			//alert("populateSymbol"+subNo);
			$("select[name='subjectSymbol"+subNo+"']").empty();
			var ddSymbols = [];
			ddSymbols.push('<option value="0">Select Symbol</option>');
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "A"){
				ddSymbols.push('<option value="A" selected=selected>A: 80 – 100%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="A">A: 80 – 100%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "B"){
				ddSymbols.push('<option value="B" selected=selected>B: 70 –  79%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="B">B: 70 –  79%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "C"){
				ddSymbols.push('<option value="C" selected=selected>C: 60 –  69%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="C">C: 60 –  69%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "D"){
				ddSymbols.push('<option value="D" selected=selected>D: 50 –  59%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="D">D: 50 –  59%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "E"){
				ddSymbols.push('<option value="E" selected=selected>E: 40 –  49%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="E">E: 40 –  49%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "F"){
				ddSymbols.push('<option value="F" selected=selected>F: 33 –  39%&nbsp;&nbsp;</option>');
			}else{
				ddSymbols.push('<option value="F">F: 33 –  39%&nbsp;&nbsp;</option>');
			}
			if (selSubjectSymbol != null && selSubjectSymbol != "" && selSubjectSymbol != "undefined" && selSubjectSymbol == "G"){
				ddSymbols.push('<option value="G" selected=selected>G:  0 –  32%&nbsp;&nbsp;</option>');	
			}else{
				ddSymbols.push('<option value="G">G:  0 –  32%&nbsp;&nbsp;</option>');
			}
			$("select[name='subjectSymbol"+subNo+"']").html(ddSymbols);
		}

		function populateResult(subNo, selSubjectResult){
			//alert("populateResult"+subNo);
			$("select[name='subjectResult"+subNo+"']").empty();
			var ddResults = [];
			ddResults.push('<option value="0">Select Result</option>');
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "A"){
				ddResults.push('<option value="A" selected=selected>80 – 100%&nbsp;</option>');
			}else{
				ddResults.push('<option value="A">80 – 100%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "B"){
				ddResults.push('<option value="B" selected=selected>70 –  79%&nbsp;</option>');
			}else{
				ddResults.push('<option value="B">70 –  79%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "C"){
				ddResults.push('<option value="C" selected=selected>60 –  69%&nbsp;</option>');
			}else{
				ddResults.push('<option value="C">60 –  69%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "D"){
				ddResults.push('<option value="D" selected=selected>50 –  59%&nbsp;</option>');
			}else{
				ddResults.push('<option value="D">50 –  59%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "E"){
				ddResults.push('<option value="E" selected=selected>40 –  49%&nbsp;</option>');
			}else{
				ddResults.push('<option value="E">40 –  49%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "F"){
				ddResults.push('<option value="F" selected=selected>30 –  39%&nbsp;</option>');
			}else{
				ddResults.push('<option value="F">30 –  39%&nbsp;</option>');
			}
			if (selSubjectResult != null && selSubjectResult != "" && selSubjectResult != "undefined" && selSubjectResult == "G"){
				ddResults.push('<option value="G" selected=selected> 0 –  29%&nbsp;</option>');
			}else{
				ddResults.push('<option value="G"> 0 –  29%&nbsp;</option>');
			}
			$("select[name='subjectResult"+subNo+"']").html(ddResults);
		}
		
		//Click Continue button
		function validateSelect(){
			
			var isLOR = false; //Check for Life Orientation (LOR)
			var isSelected = false; //Check if there is a subject in 7-12 for LOR
			var checkError = false;
			var matCert = $("#matCert").val();

			//Check first Subject as Compulsory
			var subject  = $("select[name='subjectName1']").find("option:selected").val();
			var subjectText = $("select[name='subjectName1']").find("option:selected").text();
			if (subject==null || subject==="0" || subject=="" || subject==="undefined" || subject==="false" || subject==false || !subject){
				checkError = true;
				showError("Error", "Please select at least one subject");
				return false;
			}
				
			for (var i = 1; i <= 6; i++){ //Check first 6 Subjects
				var subject  = $("select[name='subjectName"+i+"']").find("option:selected").val();
				var subjectText = $("select[name='subjectName"+i+"']").find("option:selected").text();
				if (subject!=null && subject!="0" && subject!="" && subject!="undefined" && subject!="false" && subject!=false ){
					if ("LOR" === subject){
						isLOR = true;
					}
					if(matCert === "MATR" || matCert === "SENR" || matCert === "ONBK"){
						var subjectGrade = $("select[name='subjectGrade"+i+"']").find("option:selected").val();
						var subjectSymbol = $("select[name='subjectSymbol"+i+"']").find("option:selected").val();
						if (subjectGrade==null || subjectGrade=="" || subjectGrade==="0" || subjectGrade==="undefined" || subjectGrade==="false" || subjectGrade==false || !subjectGrade){
							checkError = true;
							showError("Error", "Please select a Grade for subject: <br/>"+subjectText);
							return false;
						}else if (subjectSymbol==null || subjectSymbol=="" || subjectSymbol==="0" || subjectSymbol==="undefined" || subjectSymbol==="false" || subjectSymbol==false || !subjectSymbol){
							checkError = true;
							showError("Error", "Please select a Result/Symbol for subject: <br/>"+subjectText);
							return false;
						}
					}else if(matCert === "DEGR" || matCert === "DIPL" || matCert === "HCER"){
						var subjectResult = $("select[name='subjectResult"+i+"']").find("option:selected").val();
						if (subjectResult==null || subjectResult=="" || subjectResult==="0" || subjectResult==="undefined" || subjectResult==="false" || subjectResult==false || !subjectResult){
							checkError = true;
							showError("Error", "Please select a Result for subject: <br/>"+subjectText);
							return false;
						}
					}
				}
			}

			for (var i = 7; i <= 12; i++){ //Check Subjects 7-12
				var subject  = $("select[name='subjectName"+i+"']").find("option:selected").val();
				var subjectText = $("select[name='subjectName"+i+"']").find("option:selected").text();
				if (subject!=null && subject!="0" && subject!="" && subject!="undefined" && subject!="false" && subject!=false ){
					isSelected = true;
					if(matCert === "MATR" || matCert === "SENR" || matCert === "ONBK"){
						var subjectGrade = $("select[name='subjectGrade"+i+"']").find("option:selected").val();
						var subjectSymbol = $("select[name='subjectSymbol"+i+"']").find("option:selected").val();
						if (subjectGrade==null || subjectGrade=="" || subjectGrade==="0" || subjectGrade==="undefined" || subjectGrade==="false" || subjectGrade==false || !subjectGrade){
							checkError = true;
							showError("Error", "Please select a Grade for subject: <br/>"+subjectText);
							return false;
						}else if (subjectSymbol==null || subjectSymbol=="" || subjectSymbol==="0" || subjectSymbol==="undefined" || subjectSymbol==="false" || subjectSymbol==false || !subjectSymbol){
							checkError = true;
							showError("Error", "Please select a Result/Symbol for subject: <br/>"+subjectText);
							return false;
						}
					}else if(matCert === "DEGR" || matCert === "DIPL" || matCert === "HCER"){
						var subjectResult = $("select[name='subjectResult"+i+"']").find("option:selected").val();
						if (subjectResult==null || subjectResult=="" || subjectResult==="0" || subjectResult==="undefined" || subjectResult==="false" || subjectResult==false || !subjectResult){
							checkError = true;
							showError("Error", "Please select a Result for subject: <br/>"+subjectText);
							return false;
						}
					}
				}
			}
			
			//Check if Life Orientation is part of first 6 subjects. If so, request at least one more subject as LOR is not counted for APS
			if(matCert === "DEGR" || matCert === "DIPL" || matCert === "HCER"){
				if (isLOR == true  && isSelected == false){
					checkError = true;
					showError("Error", "Please select another subject other than, <br/>or in addition to Life Orientation");
					return false;
				}
			}
			
		    if (checkError == false){
		    	$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Processing. Please wait...</strong>" });
		    	//doSubmit("Continue");
		  	}else{
		  		showError("Error", "An error occurred during processing of the page. Please check all your information or restart application process.");
				return false;
		  	}
		}
		
		//Click button
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepMatricSubject';
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

	<html:hidden property="page" value="applyMatricSubject"/>
	<input type="hidden" name="matCert" id="matCert" value="<bean:write name='studentRegistrationForm' property='selectMatric' />"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.aps.heading"/></h3>
				</div>
				<div class="panel-body">
					<fmt:message key="page.aps.subject.info"/><br/>

						<% ArrayList subjectList = (ArrayList)  request.getAttribute("subjectList");%>
						<input type="hidden" id="listSize" value="<bean:write name='studentRegistrationForm' property='apsSize' />" />
						
						<% int count = 1; %>
						<% int countNull = 1; %>
						<% if (subjectList != null){ %>
							<% for(int subNo = 0; subNo < subjectList.size(); subNo++) { %>
								<% if (stuRegForm.getSubjects().get(subNo).getSubjectName() != null && stuRegForm.getSubjects().get(subNo).getSubjectName() != "" && stuRegForm.getSubjects().get(subNo).getSubjectName() != "undefinded"){ %>
									<input type="hidden" id="selectedSubName<%= count%>" value="<%= stuRegForm.getSubjects().get(subNo).getSubjectName()%>"/>
								<% }else{ %>
						  			<input type="hidden" id="selectedSubName<%= count%>" value=""/>
						  		<% } %>
						  		<% if (stuRegForm.getSubjects().get(subNo).getSubjectGrade() != null && stuRegForm.getSubjects().get(subNo).getSubjectGrade() != "" && stuRegForm.getSubjects().get(subNo).getSubjectGrade() != "undefinded"){ %>
									<input type="hidden" id="selectedSubGrade<%= count%>" value="<%= stuRegForm.getSubjects().get(subNo).getSubjectGrade()%>"/>
								<% }else{ %>
						  			<input type="hidden" id="selectedSubGrade<%= count%>" value=""/>
						  		<% } %>
						  		<% if (stuRegForm.getSubjects().get(subNo).getSubjectSymbol() != null && stuRegForm.getSubjects().get(subNo).getSubjectSymbol() != "" && stuRegForm.getSubjects().get(subNo).getSubjectSymbol() != "undefinded"){ %>
									<input type="hidden" id="selectedSubSymbol<%= count%>" value="<%= stuRegForm.getSubjects().get(subNo).getSubjectSymbol()%>"/>
								<% }else{ %>
						  			<input type="hidden" id="selectedSubSymbol<%= count%>" value=""/>
						  		<% } %>
						  		<% if (stuRegForm.getSubjects().get(subNo).getSubjectResult() != null && stuRegForm.getSubjects().get(subNo).getSubjectResult() != "" && stuRegForm.getSubjects().get(subNo).getSubjectResult() != "undefinded"){ %>
									<input type="hidden" id="selectedSubResult<%= count%>" value="<%= stuRegForm.getSubjects().get(subNo).getSubjectResult()%>"/>
								<% }else{ %>
						  			<input type="hidden" id="selectedSubResult<%= count%>" value=""/>
						  		<% } %>
								<% count++; %>
							<% } %>
						<% }else{ %>
							<% for(int subNull = 0; subNull < 12; subNull++) { %>
								<input type="hidden" id="selectedSubName<%= countNull%>" value=""/>
								<input type="hidden" id="selectedSubGrade<%= countNull%>" value=""/>
								<input type="hidden" id="selectedSubSymbol<%= countNull%>" value=""/>
								<input type="hidden" id="selectedSubResult<%= countNull%>" value=""/>
								<% countNull++; %>
							<% } %>
						<% } %>
				
						<table class="itemSummary">
							<% int i = 1;%>
					        <c:forEach var="j" begin="1" end="12">
						        <tr>
									<td><select name="subjectName<%= i%>" style="width:100%"></select></td>
									<td class="goSC" style="display: none;"><select name="subjectGrade<%= i%>" style="width:100%"></select></td>
									<td class="goSC" style="display: none;"><select name="subjectSymbol<%= i%>" style="width:100%"></select></td>
									<td class="goNSC" style="display: none;"><select name="subjectResult<%= i%>" style="width:100%"></select></td>
						            <% i++; %>
						        </tr>
					        </c:forEach>
						</table>
					<br/>
					<fmt:message key="page.aps.matric.not"/><br/>
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

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>