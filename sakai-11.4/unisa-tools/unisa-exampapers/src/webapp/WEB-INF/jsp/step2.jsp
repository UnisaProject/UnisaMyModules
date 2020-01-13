<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>

<sakai:html>
	<html:form action="/examPaperCoverDocket">
	<sakai:messages/>
	<sakai:messages message="true"/>
	<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="true">
			<sakai:instruction>
				<fmt:message key="page.update.instruction"/>
			</sakai:instruction>
	</logic:equal>
	<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="false">
	<sakai:instruction>
			<fmt:message key="page.instruction"/>
		</sakai:instruction>
	</logic:equal>
		<sakai:group_heading>
			<fmt:message key="step2.groupheading"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnit" /></td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnitDesc" /></td>
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperNo" /></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examPeriodDesc"/>&nbsp;<bean:write name="examPaperCoverDocketForm" property="exampaper.examYear"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<sakai:instruction>
			<fmt:message key="step2.instruction1"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="step2.instruction2"/>
		</sakai:instruction>
		<sakai:heading>
			<fmt:message key="step2.headingA"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<sakai:group_table>
					<tr>
						<td><fmt:message key="page.markReading"/>&nbsp;</td>
						<td><html:select property="exampaper.markReadingCode" size="3">
							<html:options collection="markReadingCodes" property="code" labelProperty="engDescription"/>
							</html:select></td>
					</tr>	
					<tr>
						<td><fmt:message key="page.fillInPaper"/>&nbsp;</td>
						<td><html:select property="fillInPaper" size="3">
							<html:options collection="fullPartialNoneCodes" property="code" labelProperty="engDescription"/>
							</html:select></td>
					</tr>		
				</sakai:group_table>
			</tr>	
			<tr>
			<sakai:group_table>
				<tr>
					<td colspan="2">
					<sakai:group_heading>
						<fmt:message key = "step2.note1"/>
					</sakai:group_heading></td>
				</tr>
			</sakai:group_table>
			</tr>	
			<tr>
				<sakai:group_table>			
					<tr>
						<td colspan="2">
							<html:checkbox name="examPaperCoverDocketForm" property="declaration" value="Y">
							<fmt:message key="page.declaration"/></html:checkbox></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>	
					<tr>
						<td><fmt:message key="page.writtenTotal"/>&nbsp;</td>
						<td><html:text name="examPaperCoverDocketForm" property="exampaper.writtenTotal" size="3" maxlength="3"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.mcqTotal"/>&nbsp;</td>
						<td><html:text name="examPaperCoverDocketForm" property="exampaper.mcqTotal" size="3" maxlength="3"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.paperTotal"/>&nbsp;</td>
						<td><html:text name="examPaperCoverDocketForm" property="exampaper.paperTotal" size="3" maxlength="3"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.totalPages"/>&nbsp;</td>
						<td><html:text name="examPaperCoverDocketForm" property="exampaper.totalPages" size="3" maxlength="3"/>&nbsp;&nbsp;<fmt:message key="page.totalPages.note"/></td>
					</tr>
					<tr>
						<td><fmt:message key="page.annexurePages"/>&nbsp;</td>
						<td><html:text name="examPaperCoverDocketForm" property="annexurePages" size="20" maxlength="20"/>&nbsp;&nbsp;<fmt:message key="page.declarationEg"/></td>
					</tr>	
<!--					<logic:equal name="examPaperCoverDocketForm" property="xpaperLogExists" value="false">						-->
<!--						<tr>-->
<!--							<td><fmt:message key="page.nrOfDocsSubmitted"/>&nbsp;</td>-->
<!--							<td><html:radio name="examPaperCoverDocketForm" property="exampaper.nrOfDocumentsSubmitted" value="1">-->
<!--											<b><fmt:message key="page.nrOfDocsSubmitted1"/></b><BR/>-->
<!--											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted1DescLine1"/><BR/>-->
<!--											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted1DescLine2"/>-->
<!--								</html:radio>-->
<!--							</td>-->
<!--						</tr>-->
<!--						<tr>			-->
<!--							<td>&nbsp;</td>-->
<!--							<td>-->
<!--								<html:radio name="examPaperCoverDocketForm" property="exampaper.nrOfDocumentsSubmitted" value="2">-->
<!--								<b><fmt:message key="page.nrOfDocsSubmitted2"/></b><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted2Desc"/></html:radio>-->
<!--							</td>								-->
<!--						</tr>	-->
<!--					</logic:equal>-->
<!--					<logic:equal name="examPaperCoverDocketForm" property="xpaperLogExists" value="true">						-->
<!--						<tr>-->
<!--							<td><fmt:message key="page.nrOfDocsSubmitted"/>&nbsp;</td>-->
<!--							<td><html:radio name="examPaperCoverDocketForm" property="exampaper.nrOfDocumentsSubmitted" value="1" disabled="true">-->
<!--											<b><fmt:message key="page.nrOfDocsSubmitted1"/></b><BR/>-->
<!--											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted1DescLine1"/><BR/>-->
<!--											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted1DescLine2"/>-->
<!--								</html:radio>-->
<!--							</td>-->
<!--						</tr>-->
<!--						<tr>			-->
<!--							<td>&nbsp;</td>-->
<!--							<td>-->
<!--								<html:radio name="examPaperCoverDocketForm" property="exampaper.nrOfDocumentsSubmitted" value="2" disabled="true">-->
<!--								<b><fmt:message key="page.nrOfDocsSubmitted2"/></b><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.nrOfDocsSubmitted2Desc"/></html:radio>-->
<!--							</td>								-->
<!--						</tr>	-->
<!--					</logic:equal>-->
				</sakai:group_table>				
			</tr>
		</sakai:group_table>	
		<sakai:heading>
			<fmt:message key="step2.headingB"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>				
				<td align="left">&nbsp;</td>
				<td align="left"><fmt:message key="page.item"/></td>
				<td align="left"><fmt:message key="page.average"/></td>
			</tr>
			<logic:iterate id="materials" name="examPaperCoverDocketForm" property="exampaper.specialMaterials" indexId="index">
				<tr>				
				<logic:equal name="examPaperCoverDocketForm" property='<%= "exampaper.specialMaterials[" + index.toString() + "].code"%>' value="MB">
					<td align="left"><html:multibox property="indexNrSelectedMaterial"><bean:write name="index"/></html:multibox></td>
					<td align="left"><bean:write name="materials" property="description"/></td>	
					<td align="left"><bean:write name="examPaperCoverDocketForm" property='<%= "exampaper.specialMaterials[" + index.toString() + "].average"%>'/></td>								
				</logic:equal>
				<logic:notEqual name="examPaperCoverDocketForm" property='<%= "exampaper.specialMaterials[" + index.toString() + "].code"%>' value="MB">
					<td align="left"><html:multibox property="indexNrSelectedMaterial"><bean:write name="index"/></html:multibox></td>
					<td align="left"><bean:write name="materials" property="description"/></td>	
					<td align="left"><html:text name="examPaperCoverDocketForm" property='<%= "exampaper.specialMaterials[" + index.toString() + "].average"%>' size="3" maxlength="5"/></td>				
				</logic:notEqual>
				</tr>
			</logic:iterate> 
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingC"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<sakai:group_table>
					<tr>
						<td>
							<html:checkbox name="examPaperCoverDocketForm" property="noBookRequired" value="true">
							<fmt:message key="page.noBookRequired"/></html:checkbox></td>
						</td>
					</tr>
				</sakai:group_table>	
			</tr>
			<tr>
				<sakai:group_table>
					<tr><td colspan="4">
						<sakai:group_heading>
							<fmt:message key = "step2.note2"/>
						</sakai:group_heading>
					</td></tr>	
				</sakai:group_table>
			</tr>
			<tr>
				<sakai:group_table>
					<tr>				
						<td>&nbsp;</td>
						<td align="left"><fmt:message key="page.book"/></td>
						<td align="left"><fmt:message key="page.bookAverage"/></td>
						<td>&nbsp;</td>
					</tr>
					<logic:iterate id="books" name="examPaperCoverDocketForm" property="exampaper.answerBooks" indexId="index">
						<tr>
							<td><html:multibox property="indexNrSelectedBook"><bean:write name="index"/></html:multibox></td>
							<td align="left"><bean:write name="books" property="description"/></td>	
							<td align="left"><html:text name="examPaperCoverDocketForm" property='<%= "exampaper.answerBooks[" + index.toString() + "].average"%>' size="3"  maxlength="5"/></td>		
							<td>&nbsp;</td>		
						</tr>				
					</logic:iterate>					
				</sakai:group_table>	
			</tr>
		</sakai:group_table>	
		<sakai:heading>
			<fmt:message key="step2.headingD"/>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.openBook"/></td>
				<td><html:select  name="examPaperCoverDocketForm" property="openBook" size="3">
					<html:options collection="openBookCodes" property="code" labelProperty="engDescription"/>
					</html:select></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>	
			<tr><td colspan="3">
				<sakai:group_heading>
					<fmt:message key = "step2.note3"/>
				</sakai:group_heading></td>
			</tr>	
			<!--<tr>
				<td><fmt:message key="page.keepPaper"/></td>
				<td align="left"><html:select  name="examPaperCoverDocketForm" property="keepPaper" size="2">
					<html:options collection="yesNoCodes" property="code" labelProperty="engDescription"/>
					</html:select></td>	
				<td>&nbsp;</td>
			</tr>  -->
			<!-- radio button -keep paper -->
			<tr><td><fmt:message key="page.keepPaper"/></td>
				<td colspan="2">		
					<sakai:group_table>
						<tr><td>
							<logic:iterate name="examPaperCoverDocketForm" property="yesNoCodes" id="record">
								<bean:define id="recordValue">
									<bean:write name="record" property="code"/>
								</bean:define>
								<logic:equal name="examPaperCoverDocketForm" property="disableKeepPaper" value="true">
								<html:radio  name="examPaperCoverDocketForm" property="keepPaper" value="<%=recordValue%>" disabled="true">
									<bean:write name="record" property="engDescription"/>
								</html:radio>	
								</logic:equal>	
								<logic:equal name="examPaperCoverDocketForm" property="disableKeepPaper" value="false">
								<html:radio  name="examPaperCoverDocketForm" property="keepPaper" value="<%=recordValue%>" disabled="false">
									<bean:write name="record" property="engDescription"/>
								</html:radio>	
								</logic:equal>							
							</logic:iterate>	
						</td></tr>						
					</sakai:group_table>
				</td>
			</tr>			
			<!--	
			<tr>
				<td><fmt:message key="page.calcPermit"/></td>
				<td align="left"><html:select  name="examPaperCoverDocketForm" property="calcPermit" size="2">
					<html:options collection="yesNoCodes" property="code" labelProperty="engDescription"/>
					</html:select></td>	
				<td>&nbsp;</td>		
			</tr>
			-->
			<!-- radio button -calc permitted -->			
			<tr><td><fmt:message key="page.calcPermit"/></td>
				<td colspan="2">		
					<sakai:group_table>
						<tr><td>
							<logic:iterate name="examPaperCoverDocketForm" property="yesNoCodes" id="record">
								<bean:define id="recordValue">
									<bean:write name="record" property="code"/>
								</bean:define>
										<html:radio  name="examPaperCoverDocketForm" property="calcPermit" value="<%=recordValue%>">
											<bean:write name="record" property="engDescription"/>
										</html:radio>								
							</logic:iterate>	
						</td></tr>						
					</sakai:group_table>
				</td>
			</tr>			
			<tr>
				<td colspan="3">
					<sakai:group_table>
					<tr>
						<td colspan="2"><fmt:message key="page.calcInstructions"/></td>
					</tr>
					<logic:iterate id="records" name="examPaperCoverDocketForm" property="exampaper.calcSpecialInstructions" indexId="index">
						<tr>
							<td align="left"><html:multibox property="calcInstructionSelection"><bean:write name="index"/></html:multibox></td>
							<td align="left"><bean:write name="records" property="label"/></td>	
						</tr>
					</logic:iterate> 
					</sakai:group_table>
				</td>
			</tr>				
		</sakai:group_table>
		<sakai:group_table>
			<tr>
				<td colspan="2"><fmt:message key="page.inUseInstructions"/></td>
			</tr>			
			</tr>
			<logic:iterate id="records" name="examPaperCoverDocketForm" property="exampaper.inUseSpecialInstructions" indexId="index">
				<tr>
					<td align="left"><html:multibox property="inUseInstructionSelection"><bean:write name="index"/></html:multibox></td>
					<td align="left"><bean:write name="records" property="label"/></td>	
				</tr>
			</logic:iterate> 
			</tr>
			</tr>
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingE"/>
		</sakai:heading>
		<sakai:group_table>
			<logic:iterate id="records" name="examPaperCoverDocketForm" property="exampaper.languages" indexId="index">
				<tr>
					<td align="left"><html:multibox property="indexNrSelectedLanguage"><bean:write name="index"/></html:multibox></td>
					<td align="left"><bean:write name="records" property="engDescription"/></td>	
				</tr>
			</logic:iterate> 
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingF"/>
		</sakai:heading>		
		<sakai:group_table>	
			<tr><td>
				<sakai:group_heading>
					<fmt:message key = "step2.note4"/>
				</sakai:group_heading></td>
			</td></tr>
			<logic:iterate id="records" name="examPaperCoverDocketForm" property="exampaper.additionalInstructions" indexId="index">
				<tr>
					<td><html:text name="examPaperCoverDocketForm" property='<%= "exampaper.additionalInstructions[" + index.toString() + "]"%>' size="80"  maxlength="132"/></td>	
				</tr>
			</logic:iterate> 
		</sakai:group_table>		
		<!--<sakai:heading>
			<fmt:message key="step2.headingG"/>			
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td>
					<fmt:message key="page.requestToProofReadQues"/></td>
				</td>
				<td>
					<html:radio name="examPaperCoverDocketForm" property="requestToProofRead" value="Y">
					<fmt:message key="page.yes"/></html:radio></td>
				</td>					
				<td>
					<html:radio name="examPaperCoverDocketForm" property="requestToProofRead" value="N">
					<fmt:message key="page.no"/></html:radio>
				</td>
			</tr>
			<tr><td colspan="3">
				<sakai:group_heading>
					<fmt:message key = "step2.note5"/>
				</sakai:group_heading></td>
			</td></tr>
		</sakai:group_table>-->		
		<sakai:heading>
			<fmt:message key="step2.headingG"/>			
		</sakai:heading>
		<!--<sakai:group_table>	
			<tr>
				<td>
					<html:radio name="examPaperCoverDocketForm" property="memoIncluded" value="Y">
					<fmt:message key="page.yes"/></html:radio>
				</td>					
				<td>
					<html:radio name="examPaperCoverDocketForm" property="memoIncluded" value="N">
					<fmt:message key="page.no"/></html:radio>
				</td>
			</tr>
		</sakai:group_table>-->				
		<sakai:group_table>
			<tr><td>
					<logic:iterate name="examPaperCoverDocketForm" property="yesNoCodes" id="record">
						<bean:define id="recordValue">
							<bean:write name="record" property="code"/>
						</bean:define>
						<html:radio  name="examPaperCoverDocketForm" property="memoIncluded" value="<%=recordValue%>">
							<bean:write name="record" property="engDescription"/>
						</html:radio>								
					</logic:iterate>	
			</td></tr>					
		</sakai:group_table>				
		<sakai:heading>
			<fmt:message key="step2.headingH"/>			
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td>
					<html:radio name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="LIST">
					<fmt:message key="page.examinerList"/></html:radio>
				</td>	
			</tr>
			<tr>			
				<td>
					<html:radio name="examPaperCoverDocketForm" property="exampaper.examinerPrt" value="PANEL">
					<fmt:message key="page.examinerPanel"/></html:radio>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="step2.headingI"/>	
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.personnelNumber"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="contactPerson" size="8" maxlength="8"/></td>
			</tr>
		</sakai:group_table>		
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>
		</html:form>
</sakai:html>