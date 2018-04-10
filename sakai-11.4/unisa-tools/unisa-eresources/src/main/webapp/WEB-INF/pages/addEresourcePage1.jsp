<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="contentTypeDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='contentTypeDetail.heading'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="addEresourcePage1" method="post" action="resource" id="eresource"
               cssClass="well form-horizontal">
    <form:hidden path="eresourceId"/>
     
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcesPage1.name"/>
        <div class="controls">
            <form:textarea path="resourceName" id="resourceName" rows="3" cols="1000" maxlength="255"/>
            <form:errors path="resourceName" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.description"/>
        <div class="controls">
            <form:input path="description" id="description" maxlength="255"/>
            <form:errors path="description" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.OnCampusURL"/>
        <div class="controls">
            <form:input path="onCampusUrl" id="onCampusUrl" maxlength="300"/>
            <form:errors path="onCampusUrl" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.OffCampusURL"/>
        <div class="controls">
            <form:input path="offCampusUrl" id="offCampusUrl" maxlength="300"/>
            <form:errors path="offCampusUrl" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.mobileURL"/>
        <div class="controls">
            <form:input path="mobileUrl" id="mobileUrl" maxlength="300"/>
            <form:errors path="mobileUrl" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.mobileAvailability"/>
        <div class="controls">
             <form:select path="mobileAccessAvailability">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.mobileInformation"/>
        <div class="controls">
            <form:input path="mobileAccessInfo" id="mobileAccessInfo" maxlength="255"/>
            <form:errors path="mobileAccessInfo" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.Vendor"/>
        <div class="controls">
        <%-- <form:select path="vendor">
    		<form:options items="${vendorList}" />
		</form:select> --%>
            <form:select path="vendor" items="${vendorList}" />
            <form:errors path="vendor" cssClass="help-inline"/>           
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.fullTextCoverage"/>
        <div class="controls"> 
           <%--  <form:select path="contentType" items="${contentTypeList}" /> --%>
            <%-- <form:select path="contentType" items="${webFrameworkList}" /> --%>
            <form:select path="contentType" items="${countryList}" />
            <form:errors path="contentType" cssClass="help-inline"/>          
        </div>
      </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.cDROMContactInfo"/>
        <div class="controls">
            <form:input path="cdRomContactInfo" id="cdRomContactInfo" maxlength="255"/>
            <form:errors path="cdRomContactInfo" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.TrainingMaterialURL"/>
        <div class="controls">
            <form:input path="trainingMaterialURL" id="trainingMaterialURL" maxlength="300"/>
            <form:errors path="trainingMaterialURL" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.NewsletterTitle"/>
        <div class="controls">
          	 <form:select path="newsletter" items="${newsletterList}" />
             <form:errors path="newsletter" cssClass="help-inline"/>       
        </div>
      </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.NewsletterURL"/>
        <div class="controls">
            <form:input path="newsLetterUrl" id="newsLetterUrl" maxlength="300"/>
            <form:errors path="newsLetterUrl" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.AccessNote"/>
        <div class="controls">
          	 <form:select path="highlightNote" items="${highlightNotesList}" />
             <form:errors path="highlightNote" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.LoginPasswordExist"/>
        <div class="controls">
             <form:select path="logonExist">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
     </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.login"/>
        <div class="controls">
            <form:input path="login" id="login" maxlength="20"/>
            <form:errors path="login" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.Password"/>
        <div class="controls">
            <form:input path="password" id="password" maxlength="20"/>
            <form:errors path="password" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.DisabilityCompliance"/>
        <div class="controls">
             <form:select path="disabilityComply">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
     </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.PersonalURL"/>
        <div class="controls">
            <form:input path="persLibWebpageURL" id="persLibWebpageURL" maxlength="300"/>
            <form:errors path="persLibWebpageURL" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.AvailabilityResourceSystem"/>
        <div class="controls">
             <form:select path="resourceDiscoverySystemIndicator">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.MobileEnabled"/>
        <div class="controls">
             <form:select path="enabled">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
    </div>
    
    <div class="form-actions">
        <button type="submit" name="_target1" class="btn btn-primary">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.continue"/>
        </button>
        <c:if test="${not empty eresources.eresourceId}">
          <button type="submit" class="btn" name="delete">
              <i class="icon-trash"></i> <fmt:message key="button.delete"/>
          </button>
        </c:if>
        <button type="submit" class="btn" name="_cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
    </form:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['resource']).focus();
    });
</script>