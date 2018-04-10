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
    <form:form commandName="createVendor" method="post" action="vendorform" id="vendorForm"
               cssClass="well form-horizontal">
    <form:hidden path="vendorId"/>
     
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.description"/>
        <div class="controls">
            <form:input path="vendorName" id="description" maxlength="50"/>
            <form:errors path="vendorName" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.urlOnCampus"/>
        <div class="controls">
            <form:input path="onCampusURL" id="description" maxlength="50"/>
            <form:errors path="onCampusURL" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.urlOffCampus"/>
        <div class="controls">
            <form:input path="offCampusURL" id="description" maxlength="50"/>
            <form:errors path="offCampusURL" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.logoFilename"/>
        <div class="controls">
            <form:input path="logoFile" id="description" maxlength="50"/>
            <form:errors path="logoFile" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.url"/>
        <div class="controls">
            <form:input path="logoURL" id="description" maxlength="50"/>
            <form:errors path="logoURL" cssClass="help-inline"/>
        </div>
    </div>
      
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="vendors.isEnabled"/>
        <div class="controls">
             <form:select path="enabled">
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty vendors.vendorId}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['vendorsForm']).focus();
    });
</script>