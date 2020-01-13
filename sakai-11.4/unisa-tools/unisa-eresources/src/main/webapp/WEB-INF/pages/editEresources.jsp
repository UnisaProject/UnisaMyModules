<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="vendorDetail.title"/></title>
    <!--  <meta name="menu" content="ContentTypeMenu"/> -->
</head>
 
<div class="span2">
    <h3><fmt:message key='allpages.description'/></h3>
    <h3><fmt:message key='vendorDetail.edit'/></h3>
</div>
<div class="span7">
    <form:errors path="*" cssClass="alert alert-error fade in" element="div"/>
    <form:form commandName="editEresources" method="post" action="editEresources" id="editEresources" cssClass="well form-horizontal">
     
    <!-- Vendor Fields to add to the object -->
    <form:hidden path="vendor.vendorId"/>
    <form:hidden path="vendor.offCampusURL"/>
    <form:hidden path="vendor.onCampusURL"/>
    <form:hidden path="vendor.logoFile"/>
    <form:hidden path="vendor.logoURL"/>
    <form:hidden path="vendor.enabled"/>
    
    <!-- Eresource Fields excluding objects cointained within the eresource to add to the object -->
    <form:hidden path="trialStartDate"/>
    <form:hidden path="trialEndDate"/>
    <form:hidden path="description"/>
    <form:hidden path="onCampusUrl"/> 
    <form:hidden path="offCampusUrl"/>    
    <form:hidden path="cdRomContactInfo"/>
    <form:hidden path="login"/> 
    <form:hidden path="mobileAccessInfo"/>   
    <form:hidden path="mobileUrl"/>
    <form:hidden path="newsLetterUrl"/> 
    <form:hidden path="password"/>    
    <form:hidden path="persLibWebpageURL"/>
    <form:hidden path="trainingMaterialURL"/> 
    
    <!-- ContentType Fields to add to the object -->
    <form:hidden path="contentType.libTxtID"/>
    <form:hidden path="contentType.fullTxtDescr"/>
    <form:hidden path="contentType.enabled"/>
    
    <!-- Newsletter Fields to add to the object -->
    <form:hidden path="newsletter.newsTitleID"/>
    <form:hidden path="newsletter.newsTitle"/>
    <form:hidden path="newsletter.enabled"/>

    
    <!-- HighlightNotes Fields to add to the object -->
    <form:hidden path="highlightNote.highlightNotesID"/>
    <form:hidden path="highlightNote.enabled"/>
     
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresources.description"/>
        <div class="controls">
            <form:input path="resourceName" id="description" maxlength="50"/>
            <form:errors path="resourceName" cssClass="help-inline"/>
        </div>
    </div>
   <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresources.vendor"/>F
        <div class="controls">
            <form:input path="vendor.vendorName" id="description" maxlength="50"/>
            <form:errors path="vendor.vendorName" cssClass="help-inline"/>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresources.placement"/>
        <div class="controls">
            <form:input path="highlightNote.highlightNote" id="description" maxlength="50"/>
            <form:errors path="highlightNote.highlightNote" cssClass="help-inline"/>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresources.enabled"/>
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
        <c:if test="${not empty eresource.eresourceId}">
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