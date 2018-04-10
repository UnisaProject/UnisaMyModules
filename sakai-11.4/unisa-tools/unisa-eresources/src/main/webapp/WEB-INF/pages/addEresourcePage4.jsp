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
    <form:form commandName="addEresourcePage4" method="post" action="resource" id="eresource4"
               cssClass="well form-horizontal">
    <form:hidden path="eresourceId"/>
     
     <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <button type="submit" class="btn" name="_target2">
            <i class="icon-remove"></i> <fmt:message key="button.previous"/>
        </button>
        <button type="submit" class="btn" name="_cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div> 
   
   
      <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcesPage1.name"/>
        <div class="controls">
            <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.description"/>
        <div class="controls">
             <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.OnCampusURL"/>
        <div class="controls">
             <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.OffCampusURL"/>
        <div class="controls">
            <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
   
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.Vendor"/>
        <div class="controls">
             <appfuse:out>${user.userName}</appfuse:out>         
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.fullTextCoverage"/>
        <div class="controls"> 
            <appfuse:out>${user.userName}</appfuse:out>       
        </div>
      </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.cDROMContactInfo"/>
        <div class="controls">
             <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.TrainingMaterialURL"/>
        <div class="controls">
            <appfuse:out>${user.userName}</appfuse:out>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.LoginPasswordExist"/>
        <div class="controls">
              <appfuse:out>${user.userName}N</appfuse:out>          
        </div>
     </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.MobileEnabled"/>
        <div class="controls">
              <appfuse:out>${user.userName}Y</appfuse:out>
        </div>
    </div>
  
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.trialDatabase"/>
        <div class="controls">
             <form:select path="trialDatabase">  
                <form:option value="1">Yes</form:option>  
             	<form:option value="0">No</form:option>  
            </form:select>           
        </div>
     </div>
     <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.placesments"/>
        <div class="controls">
            <display:table name="resource" class="table table-condensed table-striped table-hover" requestURI="" id="eresources" export="true" pagesize="25">
	        <display:column titleKey="eresources.placement"/>
	        <display:column titleKey="eresources.startDate"/>
	       	<display:column titleKey="eresources.endDate">  <form:input path="resourceName"/></display:column>	     
		</display:table>
        </div>
    </div>
    
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="eresourcePage1.subjects"/>
        <div class="controls">
              <appfuse:out>English, geography, Arts, etc</appfuse:out>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <button type="submit" class="btn" name="_target2">
            <i class="icon-remove"></i> <fmt:message key="button.previous"/>
        </button>
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