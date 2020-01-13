<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="vendors.title"/></title>
    <!--  <meta name="menu" content="vendorMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='vendorDetail.title'/></h3>
</div>

<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="vendor" method="post" action="vendorform" id="vendorForm" name="vendorForm" cssClass="well form-horizontal" onsubmit="return validateVendor(this)">
    <form:hidden path="vendorId"/>
		<div class="control-group">
			<appfuse:label styleClass="control-label" key="vendor.vendorName" />
			<div class="controls">
				<form:input path="vendorName" id="description" maxlength="50" />
				<form:errors path="vendorName" cssClass="help-inline" />
			</div>
            </div>
       <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendor.onCampusURL"/>
        <div class="controls">
            <form:input path="onCampusURL" id="description" maxlength="150"/>
            <form:errors path="onCampusURL" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendor.offCampusURL"/>
        <div class="controls">
            <form:input path="offCampusURL" id="description" maxlength="150"/>
            <form:errors path="offCampusURL" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendor.logoFile"/>
        <div class="controls">
            <form:input path="logoFile" id="description" maxlength="50"/>
            <form:errors path="logoFile" cssClass="help-inline"/>
        </div>
    </div>
       <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendor.logoURL"/>
        <div class="controls">
            <form:input path="logoURL" id="description" maxlength="150"/>
            <form:errors path="logoURL" cssClass="help-inline"/>
        </div>
    </div> 
      
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendor.isEnabled"/>
        <div class="controls">
             <form:select path="enabled">
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
	<div class="form-group form-actions">
    <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
          <c:if test="${not empty vendor.vendorId}">
          <button type="submit" class="btn btn-default" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>

 <v:javascript formName="vendor" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['vendorForm']).focus();
    });
</script>