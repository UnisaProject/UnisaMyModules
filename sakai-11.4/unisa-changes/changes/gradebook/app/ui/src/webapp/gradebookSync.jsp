<f:view>
	<div class="portletBody">
	  <h:form id="gbForm">
	  
	  	<sakai:flowState bean="#{gradebookSyncBean}" />
	  	
		<t:aliasBean alias="#{bean}" value="#{gradebookSyncBean}">
			<%@ include file="/inc/appMenu.jspf"%>

			<%@ include file="/inc/breadcrumb.jspf" %>
		</t:aliasBean>
		<div class="instruction"><h:outputText value="#{msgs.gradebook_sync_instructions}" escape="false"/></div>

		<div id="buttonDiv" class="act">
			<h:commandButton
				id="submitSyncBtn"
				value="#{msgs.gradebook_sync_submit}"
				action="#{gradebookSyncBean.processSync}"
				onclick="disableSyncButtons('buttonDiv')"
				immediate="true"/>
			<h:commandButton
				id="cancelSyncBtn"
				value="#{msgs.gradebook_sync_cancel}"
				action="#{gradebookSyncBean.cancel}"
				immediate="true"/>
		</div>
		
		<script type="text/javascript">
			function disableSyncButtons(divId){
				var syncButton = document.getElementById('gbForm:submitSyncBtn');
				var syncCancelButton = document.getElementById('gbForm:cancelSyncBtn');
				
				disableButton(divId, syncButton);
				disableButton(divId, syncCancelButton);
			}
		</script>
			
	  </h:form>
	</div>
</f:view>
