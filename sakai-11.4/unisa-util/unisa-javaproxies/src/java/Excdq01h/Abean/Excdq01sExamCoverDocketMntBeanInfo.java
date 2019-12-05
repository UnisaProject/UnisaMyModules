package Excdq01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Excdq01sExamCoverDocketMntBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Excdq01sExamCoverDocketMnt.class;
 
 //  Table of PropertyDescriptors that describe the base class.
 //  Do not expose "import" and "export" methods--these should be
 //  for our use only.  Initialized in the method because the 
 //  PropertyDescriptor new can raise an IntrospectionException.
 
   private PropertyDescriptor [] myProperties = {
      null,   // 0
      null,   // 1
      null,   // 2
      null,   // 3
      null,   // 4
      null,   // 5
      null,   // 6
      null,   // 7
      null,   // 8
      null,   // 9
      null,   // 10
      null,   // 11
      null,   // 12
      null,   // 13
      null,   // 14
      null,   // 15
      null,   // 16
      null,   // 17
      null,   // 18
      null,   // 19
      null,   // 20
      null,   // 21
      null,   // 22
      null,   // 23
      null,   // 24
      null,   // 25
      null,   // 26
      null,   // 27
      null,   // 28
      null,   // 29
      null,   // 30
      null,   // 31
      null,   // 32
      null,   // 33
      null,   // 34
      null,   // 35
      null,   // 36
      null,   // 37
      null,   // 38
      null,   // 39
      null,   // 40
      null,   // 41
      null,   // 42
      null,   // 43
      null,   // 44
      null,   // 45
      null,   // 46
      null,   // 47
      null,   // 48
      null,   // 49
      null,   // 50
      null,   // 51
      null,   // 52
      null,   // 53
      null,   // 54
      null,   // 55
      null,   // 56
      null,   // 57
      null,   // 58
      null,   // 59
      null,   // 60
      null,   // 61
      null,   // 62
      null,   // 63
      null,   // 64
      null,   // 65
      null,   // 66
      null,   // 67
      null,   // 68
      null,   // 69
      null,   // 70
      null,   // 71
      null,   // 72
      null,   // 73
      null,   // 74
      null,   // 75
      null,   // 76
      null,   // 77
      null,   // 78
      null,   // 79
      null,   // 80
      null,   // 81
      null,   // 82
      null,   // 83
      null,   // 84
      null,   // 85
      null,   // 86
      null,   // 87
      null,   // 88
      null,   // 89
      null,   // 90
      null,   // 91
      null,   // 92
      null,   // 93
      null,   // 94
      null,   // 95
      null,   // 96
      null,   // 97
      null,   // 98
      null,   // 99
      null,   // 100
      null,   // 101
      null,   // 102
      null,   // 103
      null,   // 104
      null,   // 105
      null,   // 106
      null,   // 107
      null,   // 108
      null,   // 109
      null,   // 110
      null,   // 111
      null,   // 112
      null,   // 113
      null,   // 114
      null,   // 115
      null,   // 116
      null,   // 117
      null,   // 118
      null,   // 119
      null,   // 120
      null,   // 121
      null,   // 122
      null,   // 123
      null,   // 124
      null,   // 125
      null,   // 126
      null,   // 127
      null,   // 128
      null,   // 129
      null,   // 130
      null,   // 131
      null,   // 132
      null,   // 133
      null,   // 134
      null,   // 135
      null,   // 136
      null,   // 137
      null,   // 138
      null,   // 139
      null,   // 140
      null,   // 141
      null,   // 142
      null,   // 143
      null,   // 144
      null,   // 145
      null,   // 146
      null,   // 147
      null,   // 148
      null,   // 149
      null,   // 150
      null,   // 151
      null,   // 152
      null,   // 153
      null,   // 154
      null,   // 155
      null,   // 156
      null,   // 157
      null,   // 158
      null,   // 159
      null,   // 160
      null,   // 161
      null,   // 162
      null,   // 163
      null,   // 164
      null,   // 165
      null,   // 166
      null,   // 167
      null,   // 168
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Excdq01sExamCoverDocketMnt.class);
      bd.setDisplayName(Excdq01sExamCoverDocketMnt.BEANNAME);
      return bd;
   }
 
   public PropertyDescriptor [] getPropertyDescriptors() {
      try {
         myProperties[0] = new PropertyDescriptor("tracing", rootClass);
         myProperties[1] = new PropertyDescriptor("serverLocation", rootClass);
         myProperties[2] = new PropertyDescriptor("servletPath", rootClass);
         myProperties[3] = new PropertyDescriptor("commandSent", rootClass);
         myProperties[4] = new PropertyDescriptor("clientId", rootClass);
         myProperties[5] = new PropertyDescriptor("clientPassword", rootClass);
         myProperties[6] = new PropertyDescriptor("nextLocation", rootClass);
         myProperties[7] = new PropertyDescriptor("exitStateSent", rootClass);
         myProperties[8] = new PropertyDescriptor("dialect", rootClass);
         myProperties[9] = new PropertyDescriptor("commandReturned", rootClass, "getCommandReturned", null);
         myProperties[10] = new PropertyDescriptor("exitStateReturned", rootClass, "getExitStateReturned", null);
         myProperties[11] = new PropertyDescriptor("exitStateType", rootClass, "getExitStateType", null);
         myProperties[12] = new PropertyDescriptor("exitStateMsg", rootClass, "getExitStateMsg", null);
         myProperties[13] = new PropertyDescriptor("comCfg", rootClass);
         myProperties[14] = new PropertyDescriptor("InExamPaperCoverDocketContactPersNr", rootClass);
         myProperties[15] = new PropertyDescriptor("InExamPaperCoverDocketBookRequired", rootClass);
         myProperties[16] = new PropertyDescriptor("InExamPaperCoverDocketProofReadRequest", rootClass);
         myProperties[17] = new PropertyDescriptor("InExamPaperCoverDocketStatusGc41", rootClass);
         myProperties[18] = new PropertyDescriptor("InExamPaperCoverDocketTotalRackPages", rootClass);
         myProperties[19] = new PropertyDescriptor("InExamPaperCoverDocketTotalPages", rootClass);
         myProperties[20] = new PropertyDescriptor("InExamPaperCoverDocketAnnexurePages", rootClass);
         myProperties[21] = new PropertyDescriptor("InExamPaperCoverDocketMemorandumIncl", rootClass);
         myProperties[22] = new PropertyDescriptor("InExamPaperCoverDocketDeclarationFlag", rootClass);
         myProperties[23] = new PropertyDescriptor("InExamPaperCoverDocketFillInPaperGc50", rootClass);
         myProperties[24] = new PropertyDescriptor("InExamPaperCoverDocketOpenBookGc50", rootClass);
         myProperties[25] = new PropertyDescriptor("InExamPaperCoverDocketKeepPaperFlag", rootClass);
         myProperties[26] = new PropertyDescriptor("InExamPaperCoverDocketCalcPermitFlag", rootClass);
         myProperties[27] = new PropertyDescriptor("InExamPaperCoverDocketExaminerPrtGc107", rootClass);
         myProperties[28] = new PropertyDescriptor("InExamPaperCoverDocketMcqInstrFlag", rootClass);
         myProperties[29] = new PropertyDescriptor("InExamPaperCoverDocketAttendRegFlag", rootClass);
         myProperties[30] = new PropertyDescriptor("InExamPaperCoverDocketPaperColour", rootClass);
         myProperties[31] = new PropertyDescriptor("InExamPaperCoverDocketNrDocsSubmitted", rootClass);
         myProperties[32] = new PropertyDescriptor("InExamPaperLogActionGc39", rootClass);
         myProperties[33] = new PropertyDescriptor("InExamPaperLogUpdatedBy", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsUserStudyUnitNovellUserId", rootClass);
         myProperties[35] = new PropertyDescriptor("InExamPaperDetailsExamYear", rootClass);
         myProperties[36] = new PropertyDescriptor("InExamPaperDetailsMkStudyUnitCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InExamPaperDetailsNr", rootClass);
         myProperties[38] = new PropertyDescriptor("InExamPaperDetailsNrOfPages", rootClass);
         myProperties[39] = new PropertyDescriptor("InExamPeriodDateMkExamPeriodCode", rootClass);
         myProperties[40] = new PropertyDescriptor("InExamPeriodDateMarkreadingCode", rootClass);
         myProperties[41] = new PropertyDescriptor("InExamPeriodDateScriptMarkTot", rootClass);
         myProperties[42] = new PropertyDescriptor("InExamPeriodDateMarkreadTot", rootClass);
         myProperties[43] = new PropertyDescriptor("InExamPeriodDatePaperMarkTot", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsReturnCode", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsServerVersionNumber", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsServerRevisionNumber", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsServerDevelopmentPhase", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[50] = new PropertyDescriptor("InCsfClientServerCommunicationsServerDate", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsServerTime", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsServerTimestamp", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsServerTransactionCode", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsServerUserId", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsServerRollbackFlag", rootClass);
         myProperties[56] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[57] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[58] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[59] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[60] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[61] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[62] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[63] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[64] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[65] = new IndexedPropertyDescriptor("InGExamStationeryItemCode", rootClass, null, null, "getInGExamStationeryItemCode", "setInGExamStationeryItemCode");
         myProperties[66] = new IndexedPropertyDescriptor("InGExamPaperStationeryNeedsAveragePerStudent", rootClass, null, null, "getInGExamPaperStationeryNeedsAveragePerStudent", "setInGExamPaperStationeryNeedsAveragePerStudent");
         myProperties[67] = new IndexedPropertyDescriptor("InGExamDateInstructionMkDocumentParagraphCode", rootClass, null, null, "getInGExamDateInstructionMkDocumentParagraphCode", "setInGExamDateInstructionMkDocumentParagraphCode");
         myProperties[68] = new IndexedPropertyDescriptor("InGExamDateInstructionMkWidthType", rootClass, null, null, "getInGExamDateInstructionMkWidthType", "setInGExamDateInstructionMkWidthType");
         myProperties[69] = new IndexedPropertyDescriptor("InGExamDateInstructionMkContents", rootClass, null, null, "getInGExamDateInstructionMkContents", "setInGExamDateInstructionMkContents");
         myProperties[70] = new IndexedPropertyDescriptor("InGExamPaperLanguageLanguageGc40", rootClass, null, null, "getInGExamPaperLanguageLanguageGc40", "setInGExamPaperLanguageLanguageGc40");
         myProperties[71] = new IndexedPropertyDescriptor("InGExamPaperAddInstructionsInstructionNr", rootClass, null, null, "getInGExamPaperAddInstructionsInstructionNr", "setInGExamPaperAddInstructionsInstructionNr");
         myProperties[72] = new IndexedPropertyDescriptor("InGExamPaperAddInstructionsInstruction", rootClass, null, null, "getInGExamPaperAddInstructionsInstruction", "setInGExamPaperAddInstructionsInstruction");
         myProperties[73] = new PropertyDescriptor("InWsAddressV2CellNumber", rootClass);
         myProperties[74] = new PropertyDescriptor("InWsAddressV2HomeNumber", rootClass);
         myProperties[75] = new PropertyDescriptor("InWsAddressV2WorkNumber", rootClass);
         myProperties[76] = new PropertyDescriptor("OutWsDepartmentCode", rootClass, "getOutWsDepartmentCode", null);
         myProperties[77] = new PropertyDescriptor("OutWsDepartmentCollegeCode", rootClass, "getOutWsDepartmentCollegeCode", null);
         myProperties[78] = new PropertyDescriptor("OutWsDepartmentSchoolCode", rootClass, "getOutWsDepartmentSchoolCode", null);
         myProperties[79] = new IndexedPropertyDescriptor("OutGExamPaperDetailsExamYear", rootClass, null, null, "getOutGExamPaperDetailsExamYear", null);
         myProperties[80] = new IndexedPropertyDescriptor("OutGExamPaperDetailsMkStudyUnitCode", rootClass, null, null, "getOutGExamPaperDetailsMkStudyUnitCode", null);
         myProperties[81] = new IndexedPropertyDescriptor("OutGExamPaperDetailsNr", rootClass, null, null, "getOutGExamPaperDetailsNr", null);
         myProperties[82] = new IndexedPropertyDescriptor("OutGExamPeriodDateMkExamPeriodCode", rootClass, null, null, "getOutGExamPeriodDateMkExamPeriodCode", null);
         myProperties[83] = new PropertyDescriptor("OutWsStudyUnitCode", rootClass, "getOutWsStudyUnitCode", null);
         myProperties[84] = new PropertyDescriptor("OutWsStudyUnitEngLongDescription", rootClass, "getOutWsStudyUnitEngLongDescription", null);
         myProperties[85] = new PropertyDescriptor("OutExamPaperDetailsExamYear", rootClass, "getOutExamPaperDetailsExamYear", null);
         myProperties[86] = new PropertyDescriptor("OutExamPaperDetailsMkStudyUnitCode", rootClass, "getOutExamPaperDetailsMkStudyUnitCode", null);
         myProperties[87] = new PropertyDescriptor("OutExamPaperDetailsNr", rootClass, "getOutExamPaperDetailsNr", null);
         myProperties[88] = new PropertyDescriptor("OutExamPaperDetailsNrOfPages", rootClass, "getOutExamPaperDetailsNrOfPages", null);
         myProperties[89] = new PropertyDescriptor("OutExamPaperDetailsDurationHours", rootClass, "getOutExamPaperDetailsDurationHours", null);
         myProperties[90] = new PropertyDescriptor("OutExamPaperDetailsDurationMinutes", rootClass, "getOutExamPaperDetailsDurationMinutes", null);
         myProperties[91] = new PropertyDescriptor("OutExamPeriodDateMkExamPeriodCode", rootClass, "getOutExamPeriodDateMkExamPeriodCode", null);
         myProperties[92] = new PropertyDescriptor("OutExamPeriodDateMarkreadingCode", rootClass, "getOutExamPeriodDateMarkreadingCode", null);
         myProperties[93] = new PropertyDescriptor("OutExamPeriodDateScriptMarkTot", rootClass, "getOutExamPeriodDateScriptMarkTot", null);
         myProperties[94] = new PropertyDescriptor("OutExamPeriodDateMarkreadTot", rootClass, "getOutExamPeriodDateMarkreadTot", null);
         myProperties[95] = new PropertyDescriptor("OutExamPeriodDatePaperMarkTot", rootClass, "getOutExamPeriodDatePaperMarkTot", null);
         myProperties[96] = new PropertyDescriptor("OutExamPeriodDateDate", rootClass, "getOutExamPeriodDateDate", null);
         myProperties[97] = new PropertyDescriptor("OutExamPeriodDateStartingTime", rootClass, "getOutExamPeriodDateStartingTime", null);
         myProperties[98] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[99] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[100] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[101] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[102] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[103] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[104] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[105] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[106] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[107] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[112] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[113] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[114] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[117] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[118] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[119] = new IndexedPropertyDescriptor("OutGWsExaminerSurname", rootClass, null, null, "getOutGWsExaminerSurname", null);
         myProperties[120] = new IndexedPropertyDescriptor("OutGWsExaminerInitials", rootClass, null, null, "getOutGWsExaminerInitials", null);
         myProperties[121] = new IndexedPropertyDescriptor("OutGWsExaminerTitle", rootClass, null, null, "getOutGWsExaminerTitle", null);
         myProperties[122] = new IndexedPropertyDescriptor("OutGExaminerTypeCsfStringsString3", rootClass, null, null, "getOutGExaminerTypeCsfStringsString3", null);
         myProperties[123] = new IndexedPropertyDescriptor("OutGWsEducationalInstitutionEngName", rootClass, null, null, "getOutGWsEducationalInstitutionEngName", null);
         myProperties[124] = new IndexedPropertyDescriptor("OutGEquivalentWsExamEquivalentsWsEquivalentCode", rootClass, null, null, "getOutGEquivalentWsExamEquivalentsWsEquivalentCode", null);
         myProperties[125] = new IndexedPropertyDescriptor("OutGWsUniqueAssignmentUniqueNr", rootClass, null, null, "getOutGWsUniqueAssignmentUniqueNr", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutGEquivalentExamPeriodDateDate", rootClass, null, null, "getOutGEquivalentExamPeriodDateDate", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGEquivalentExamPeriodDateDurationDays", rootClass, null, null, "getOutGEquivalentExamPeriodDateDurationDays", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGEquivalentExamPeriodDateDurationHours", rootClass, null, null, "getOutGEquivalentExamPeriodDateDurationHours", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutGEquivalentExamPeriodDateDurationMinutes", rootClass, null, null, "getOutGEquivalentExamPeriodDateDurationMinutes", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutGEquivalentExamPeriodDateStartingTime", rootClass, null, null, "getOutGEquivalentExamPeriodDateStartingTime", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutGExamStationeryItemCode", rootClass, null, null, "getOutGExamStationeryItemCode", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutGExamPaperStationeryNeedsAveragePerStudent", rootClass, null, null, "getOutGExamPaperStationeryNeedsAveragePerStudent", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutGExamDateInstructionMkDocumentParagraphCode", rootClass, null, null, "getOutGExamDateInstructionMkDocumentParagraphCode", null);
         myProperties[134] = new IndexedPropertyDescriptor("OutGExamDateInstructionMkWidthType", rootClass, null, null, "getOutGExamDateInstructionMkWidthType", null);
         myProperties[135] = new IndexedPropertyDescriptor("OutGExamDateInstructionMkContents", rootClass, null, null, "getOutGExamDateInstructionMkContents", null);
         myProperties[136] = new IndexedPropertyDescriptor("OutGExamPaperLanguageLanguageGc40", rootClass, null, null, "getOutGExamPaperLanguageLanguageGc40", null);
         myProperties[137] = new IndexedPropertyDescriptor("OutGExamPaperAddInstructionsInstructionNr", rootClass, null, null, "getOutGExamPaperAddInstructionsInstructionNr", null);
         myProperties[138] = new IndexedPropertyDescriptor("OutGExamPaperAddInstructionsInstruction", rootClass, null, null, "getOutGExamPaperAddInstructionsInstruction", null);
         myProperties[139] = new PropertyDescriptor("OutExamPaperLogActionGc39", rootClass, "getOutExamPaperLogActionGc39", null);
         myProperties[140] = new PropertyDescriptor("OutExamPaperLogUpdatedBy", rootClass, "getOutExamPaperLogUpdatedBy", null);
         myProperties[141] = new PropertyDescriptor("OutExamPaperLogUpdatedOn", rootClass, "getOutExamPaperLogUpdatedOn", null);
         myProperties[142] = new PropertyDescriptor("OutCoverDocketExistsFlagCsfStringsString1", rootClass, "getOutCoverDocketExistsFlagCsfStringsString1", null);
         myProperties[143] = new PropertyDescriptor("OutExamPaperCoverDocketContactPersNr", rootClass, "getOutExamPaperCoverDocketContactPersNr", null);
         myProperties[144] = new PropertyDescriptor("OutExamPaperCoverDocketBookRequired", rootClass, "getOutExamPaperCoverDocketBookRequired", null);
         myProperties[145] = new PropertyDescriptor("OutExamPaperCoverDocketProofReadRequest", rootClass, "getOutExamPaperCoverDocketProofReadRequest", null);
         myProperties[146] = new PropertyDescriptor("OutExamPaperCoverDocketStatusGc41", rootClass, "getOutExamPaperCoverDocketStatusGc41", null);
         myProperties[147] = new PropertyDescriptor("OutExamPaperCoverDocketTotalRackPages", rootClass, "getOutExamPaperCoverDocketTotalRackPages", null);
         myProperties[148] = new PropertyDescriptor("OutExamPaperCoverDocketTotalPages", rootClass, "getOutExamPaperCoverDocketTotalPages", null);
         myProperties[149] = new PropertyDescriptor("OutExamPaperCoverDocketAnnexurePages", rootClass, "getOutExamPaperCoverDocketAnnexurePages", null);
         myProperties[150] = new PropertyDescriptor("OutExamPaperCoverDocketMemorandumIncl", rootClass, "getOutExamPaperCoverDocketMemorandumIncl", null);
         myProperties[151] = new PropertyDescriptor("OutExamPaperCoverDocketDeclarationFlag", rootClass, "getOutExamPaperCoverDocketDeclarationFlag", null);
         myProperties[152] = new PropertyDescriptor("OutExamPaperCoverDocketFillInPaperGc50", rootClass, "getOutExamPaperCoverDocketFillInPaperGc50", null);
         myProperties[153] = new PropertyDescriptor("OutExamPaperCoverDocketOpenBookGc50", rootClass, "getOutExamPaperCoverDocketOpenBookGc50", null);
         myProperties[154] = new PropertyDescriptor("OutExamPaperCoverDocketKeepPaperFlag", rootClass, "getOutExamPaperCoverDocketKeepPaperFlag", null);
         myProperties[155] = new PropertyDescriptor("OutExamPaperCoverDocketCalcPermitFlag", rootClass, "getOutExamPaperCoverDocketCalcPermitFlag", null);
         myProperties[156] = new PropertyDescriptor("OutExamPaperCoverDocketExaminerPrtGc107", rootClass, "getOutExamPaperCoverDocketExaminerPrtGc107", null);
         myProperties[157] = new PropertyDescriptor("OutExamPaperCoverDocketMcqInstrFlag", rootClass, "getOutExamPaperCoverDocketMcqInstrFlag", null);
         myProperties[158] = new PropertyDescriptor("OutExamPaperCoverDocketAttendRegFlag", rootClass, "getOutExamPaperCoverDocketAttendRegFlag", null);
         myProperties[159] = new PropertyDescriptor("OutExamPaperCoverDocketPaperColour", rootClass, "getOutExamPaperCoverDocketPaperColour", null);
         myProperties[160] = new PropertyDescriptor("OutExamPaperCoverDocketNrDocsSubmitted", rootClass, "getOutExamPaperCoverDocketNrDocsSubmitted", null);
         myProperties[161] = new PropertyDescriptor("OutWsStaffSurname", rootClass, "getOutWsStaffSurname", null);
         myProperties[162] = new PropertyDescriptor("OutWsStaffInitials", rootClass, "getOutWsStaffInitials", null);
         myProperties[163] = new PropertyDescriptor("OutWsStaffTitle", rootClass, "getOutWsStaffTitle", null);
         myProperties[164] = new PropertyDescriptor("OutWsStaffEmailAddress", rootClass, "getOutWsStaffEmailAddress", null);
         myProperties[165] = new PropertyDescriptor("OutWsStaffContactTelno", rootClass, "getOutWsStaffContactTelno", null);
         myProperties[166] = new PropertyDescriptor("OutWsAddressV2HomeNumber", rootClass, "getOutWsAddressV2HomeNumber", null);
         myProperties[167] = new PropertyDescriptor("OutWsAddressV2WorkNumber", rootClass, "getOutWsAddressV2WorkNumber", null);
         myProperties[168] = new PropertyDescriptor("OutWsAddressV2CellNumber", rootClass, "getOutWsAddressV2CellNumber", null);
         return myProperties;
      } catch (IntrospectionException e) {
         throw new Error(e.toString());
      }
   }
 
   public MethodDescriptor[] getMethodDescriptors() {
//      Method callMethod;
//      Class args[] = { };
// 
//      try {
//         callMethod = Excdq01sExamCoverDocketMnt.class.getMethod("execute", args);
//      } catch (Exception ex) {
//               // "should never happen"
//         throw new Error("Missing method: " + ex);
//      }
// 
//               // Now create the MethodDescriptor array
//               // with visible event response methods:
//      MethodDescriptor result[] = { 
//        new MethodDescriptor(callMethod),
//      };
// 
//      return result;
        return null;
   }
 
//   public java.awt.Image getIcon(int iconKind) {
//      if (iconKind == BeanInfo.ICON_COLOR_16x16) {
//         java.awt.Image img = loadImage("Icon16.gif");
//         return img;
//      }
//      return null;
//   }
}
