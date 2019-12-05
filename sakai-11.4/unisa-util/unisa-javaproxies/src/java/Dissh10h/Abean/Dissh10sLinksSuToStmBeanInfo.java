package Dissh10h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Dissh10sLinksSuToStmBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Dissh10sLinksSuToStm.class;
 
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
      null,   // 169
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Dissh10sLinksSuToStm.class);
      bd.setDisplayName(Dissh10sLinksSuToStm.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[15] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[16] = new PropertyDescriptor("InHighAnnualStudyMaterialName", rootClass);
         myProperties[17] = new PropertyDescriptor("InHighAnnualStudyMaterialNr", rootClass);
         myProperties[18] = new PropertyDescriptor("InHighAnnualStudyMaterialDinLanguage", rootClass);
         myProperties[18].setPropertyEditorClass(Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor.class);
         myProperties[19] = new PropertyDescriptor("InHighStudyMaterialTypeCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InLowAnnualStudyMaterialName", rootClass);
         myProperties[21] = new PropertyDescriptor("InLowAnnualStudyMaterialNr", rootClass);
         myProperties[22] = new PropertyDescriptor("InLowAnnualStudyMaterialDinLanguage", rootClass);
         myProperties[22].setPropertyEditorClass(Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor.class);
         myProperties[23] = new PropertyDescriptor("InLowStudyMaterialTypeCode", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[25] = new PropertyDescriptor("InLowStudyUnitStudyMaterialRefName", rootClass);
         myProperties[26] = new PropertyDescriptor("InLowStudyUnitStudyMaterialRefNo", rootClass);
         myProperties[27] = new PropertyDescriptor("InLowStudyUnitStudyMaterialRefLanguage", rootClass);
         myProperties[27].setPropertyEditorClass(Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor.class);
         myProperties[28] = new PropertyDescriptor("InHighStudyUnitStudyMaterialRefName", rootClass);
         myProperties[29] = new PropertyDescriptor("InHighStudyUnitStudyMaterialRefNo", rootClass);
         myProperties[30] = new PropertyDescriptor("InHighStudyUnitStudyMaterialRefLanguage", rootClass);
         myProperties[30].setPropertyEditorClass(Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor.class);
         myProperties[31] = new PropertyDescriptor("InStartingStudyUnitStudyMaterialRefLanguage", rootClass);
         myProperties[31].setPropertyEditorClass(Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor.class);
         myProperties[32] = new PropertyDescriptor("InHighSuStudyMaterialTypeCode", rootClass);
         myProperties[33] = new PropertyDescriptor("InLowSuStudyMaterialTypeCode", rootClass);
         myProperties[34] = new PropertyDescriptor("InStudyUnitStudyMaterialMkStudyUnitCode", rootClass);
         myProperties[35] = new PropertyDescriptor("InAnnualStudyMaterialAcademicYear", rootClass);
         myProperties[36] = new PropertyDescriptor("InAnnualStudyMaterialAcademicPeriod", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[50] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[53] = new IndexedPropertyDescriptor("InGStudyMaterialStockItemInStockDate", rootClass, null, null, "getInGStudyMaterialStockItemInStockDate", "setInGStudyMaterialStockItemInStockDate");
         myProperties[54] = new IndexedPropertyDescriptor("InGSuStudyMaterialTypeCode", rootClass, null, null, "getInGSuStudyMaterialTypeCode", "setInGSuStudyMaterialTypeCode");
         myProperties[55] = new IndexedPropertyDescriptor("InGStudyMaterialBarcode", rootClass, null, null, "getInGStudyMaterialBarcode", "setInGStudyMaterialBarcode");
         myProperties[56] = new IndexedPropertyDescriptor("InGStudyMaterialRemarks", rootClass, null, null, "getInGStudyMaterialRemarks", "setInGStudyMaterialRemarks");
         myProperties[57] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialMkStudyUnitCode", rootClass, null, null, "getInGStudyUnitStudyMaterialMkStudyUnitCode", "setInGStudyUnitStudyMaterialMkStudyUnitCode");
         myProperties[58] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialRefName", rootClass, null, null, "getInGStudyUnitStudyMaterialRefName", "setInGStudyUnitStudyMaterialRefName");
         myProperties[59] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialRefNo", rootClass, null, null, "getInGStudyUnitStudyMaterialRefNo", "setInGStudyUnitStudyMaterialRefNo");
         myProperties[60] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialRefLanguage", rootClass, null, null, "getInGStudyUnitStudyMaterialRefLanguage", "setInGStudyUnitStudyMaterialRefLanguage");
         myProperties[60].setPropertyEditorClass(Dissh10sLinksSuToStmStudyUnitStudyMaterialRefLanguagePropertyEditor.class);
         myProperties[61] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialPlannedIssueDate", rootClass, null, null, "getInGStudyUnitStudyMaterialPlannedIssueDate", "setInGStudyUnitStudyMaterialPlannedIssueDate");
         myProperties[62] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialActualIssueDate", rootClass, null, null, "getInGStudyUnitStudyMaterialActualIssueDate", "setInGStudyUnitStudyMaterialActualIssueDate");
         myProperties[63] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialIssueQuantity", rootClass, null, null, "getInGStudyUnitStudyMaterialIssueQuantity", "setInGStudyUnitStudyMaterialIssueQuantity");
         myProperties[64] = new IndexedPropertyDescriptor("InGStudyUnitStudyMaterialOnlyGuideFlag", rootClass, null, null, "getInGStudyUnitStudyMaterialOnlyGuideFlag", "setInGStudyUnitStudyMaterialOnlyGuideFlag");
         myProperties[64].setPropertyEditorClass(Dissh10sLinksSuToStmStudyUnitStudyMaterialOnlyGuideFlagPropertyEditor.class);
         myProperties[65] = new IndexedPropertyDescriptor("InGStudyMaterialTypeCode", rootClass, null, null, "getInGStudyMaterialTypeCode", "setInGStudyMaterialTypeCode");
         myProperties[66] = new IndexedPropertyDescriptor("InGAnnualStudyMaterialName", rootClass, null, null, "getInGAnnualStudyMaterialName", "setInGAnnualStudyMaterialName");
         myProperties[67] = new IndexedPropertyDescriptor("InGAnnualStudyMaterialNr", rootClass, null, null, "getInGAnnualStudyMaterialNr", "setInGAnnualStudyMaterialNr");
         myProperties[68] = new IndexedPropertyDescriptor("InGAnnualStudyMaterialDinLanguage", rootClass, null, null, "getInGAnnualStudyMaterialDinLanguage", "setInGAnnualStudyMaterialDinLanguage");
         myProperties[68].setPropertyEditorClass(Dissh10sLinksSuToStmAnnualStudyMaterialDinLanguagePropertyEditor.class);
         myProperties[69] = new IndexedPropertyDescriptor("InGAnnualStudyMaterialPublishingDescription", rootClass, null, null, "getInGAnnualStudyMaterialPublishingDescription", "setInGAnnualStudyMaterialPublishingDescription");
         myProperties[70] = new IndexedPropertyDescriptor("InGAnnualStudyMaterialAcademicPeriod", rootClass, null, null, "getInGAnnualStudyMaterialAcademicPeriod", "setInGAnnualStudyMaterialAcademicPeriod");
         myProperties[71] = new IndexedPropertyDescriptor("InGCsfLineActionBarAction", rootClass, null, null, "getInGCsfLineActionBarAction", "setInGCsfLineActionBarAction");
         myProperties[72] = new IndexedPropertyDescriptor("InGCsfLineActionBarLineReturnCode", rootClass, null, null, "getInGCsfLineActionBarLineReturnCode", "setInGCsfLineActionBarLineReturnCode");
         myProperties[73] = new IndexedPropertyDescriptor("InGCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGCsfLineActionBarSelectionFlag", "setInGCsfLineActionBarSelectionFlag");
         myProperties[74] = new IndexedPropertyDescriptor("InGCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGCsfLineActionBarTranslatedAction", "setInGCsfLineActionBarTranslatedAction");
         myProperties[75] = new IndexedPropertyDescriptor("InGCsfPromptPrompt1", rootClass, null, null, "getInGCsfPromptPrompt1", "setInGCsfPromptPrompt1");
         myProperties[76] = new PropertyDescriptor("InSecurityWsActionCode", rootClass);
         myProperties[77] = new PropertyDescriptor("InSecurityWsActionDescription", rootClass);
         myProperties[78] = new PropertyDescriptor("InSecurityWsFunctionNumber", rootClass);
         myProperties[79] = new PropertyDescriptor("InSecurityWsFunctionTrancode", rootClass);
         myProperties[80] = new PropertyDescriptor("InSecurityWsFunctionDescription", rootClass);
         myProperties[81] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[82] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[83] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[84] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[85] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[86] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[87] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[88] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[89] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[90] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[91] = new PropertyDescriptor("InSecurityWsWorkstationCode", rootClass);
         myProperties[92] = new PropertyDescriptor("OutWhatToDoCsfStringsString1", rootClass, "getOutWhatToDoCsfStringsString1", null);
         myProperties[93] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[94] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
         myProperties[95] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[96] = new PropertyDescriptor("OutStudyUnitStudyMaterialMkStudyUnitCode", rootClass, "getOutStudyUnitStudyMaterialMkStudyUnitCode", null);
         myProperties[97] = new PropertyDescriptor("OutStudyUnitStudyMaterialRefName", rootClass, "getOutStudyUnitStudyMaterialRefName", null);
         myProperties[98] = new PropertyDescriptor("OutStudyUnitStudyMaterialRefNo", rootClass, "getOutStudyUnitStudyMaterialRefNo", null);
         myProperties[99] = new PropertyDescriptor("OutStudyUnitStudyMaterialRefLanguage", rootClass, "getOutStudyUnitStudyMaterialRefLanguage", null);
         myProperties[100] = new PropertyDescriptor("OutStudyUnitStudyMaterialPlannedIssueDate", rootClass, "getOutStudyUnitStudyMaterialPlannedIssueDate", null);
         myProperties[101] = new PropertyDescriptor("OutStudyUnitStudyMaterialActualIssueDate", rootClass, "getOutStudyUnitStudyMaterialActualIssueDate", null);
         myProperties[102] = new PropertyDescriptor("OutStudyUnitStudyMaterialIssueQuantity", rootClass, "getOutStudyUnitStudyMaterialIssueQuantity", null);
         myProperties[103] = new PropertyDescriptor("OutStudyUnitStudyMaterialOnlyGuideFlag", rootClass, "getOutStudyUnitStudyMaterialOnlyGuideFlag", null);
         myProperties[104] = new PropertyDescriptor("OutKeepCsfStringsString500", rootClass, "getOutKeepCsfStringsString500", null);
         myProperties[105] = new PropertyDescriptor("OutAnnualStudyMaterialAcademicYear", rootClass, "getOutAnnualStudyMaterialAcademicYear", null);
         myProperties[106] = new PropertyDescriptor("OutAnnualStudyMaterialAcademicPeriod", rootClass, "getOutAnnualStudyMaterialAcademicPeriod", null);
         myProperties[107] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[112] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[113] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[114] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[117] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[118] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[119] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[120] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[121] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[122] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[123] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[124] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[125] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[126] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[127] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[128] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[129] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[130] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutGStudyMaterialStockItemInStockDate", rootClass, null, null, "getOutGStudyMaterialStockItemInStockDate", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutGSuStudyMaterialTypeCode", rootClass, null, null, "getOutGSuStudyMaterialTypeCode", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutGStudyMaterialBarcode", rootClass, null, null, "getOutGStudyMaterialBarcode", null);
         myProperties[134] = new IndexedPropertyDescriptor("OutGStudyMaterialRemarks", rootClass, null, null, "getOutGStudyMaterialRemarks", null);
         myProperties[135] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialMkStudyUnitCode", rootClass, null, null, "getOutGStudyUnitStudyMaterialMkStudyUnitCode", null);
         myProperties[136] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialRefName", rootClass, null, null, "getOutGStudyUnitStudyMaterialRefName", null);
         myProperties[137] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialRefNo", rootClass, null, null, "getOutGStudyUnitStudyMaterialRefNo", null);
         myProperties[138] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialRefLanguage", rootClass, null, null, "getOutGStudyUnitStudyMaterialRefLanguage", null);
         myProperties[139] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialPlannedIssueDate", rootClass, null, null, "getOutGStudyUnitStudyMaterialPlannedIssueDate", null);
         myProperties[140] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialActualIssueDate", rootClass, null, null, "getOutGStudyUnitStudyMaterialActualIssueDate", null);
         myProperties[141] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialIssueQuantity", rootClass, null, null, "getOutGStudyUnitStudyMaterialIssueQuantity", null);
         myProperties[142] = new IndexedPropertyDescriptor("OutGStudyUnitStudyMaterialOnlyGuideFlag", rootClass, null, null, "getOutGStudyUnitStudyMaterialOnlyGuideFlag", null);
         myProperties[143] = new IndexedPropertyDescriptor("OutGStudyMaterialTypeCode", rootClass, null, null, "getOutGStudyMaterialTypeCode", null);
         myProperties[144] = new IndexedPropertyDescriptor("OutGAnnualStudyMaterialName", rootClass, null, null, "getOutGAnnualStudyMaterialName", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGAnnualStudyMaterialNr", rootClass, null, null, "getOutGAnnualStudyMaterialNr", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGAnnualStudyMaterialDinLanguage", rootClass, null, null, "getOutGAnnualStudyMaterialDinLanguage", null);
         myProperties[147] = new IndexedPropertyDescriptor("OutGAnnualStudyMaterialPublishingDescription", rootClass, null, null, "getOutGAnnualStudyMaterialPublishingDescription", null);
         myProperties[148] = new IndexedPropertyDescriptor("OutGAnnualStudyMaterialAcademicPeriod", rootClass, null, null, "getOutGAnnualStudyMaterialAcademicPeriod", null);
         myProperties[149] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[150] = new IndexedPropertyDescriptor("OutGCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGCsfLineActionBarLineReturnCode", null);
         myProperties[151] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[152] = new IndexedPropertyDescriptor("OutGCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGCsfLineActionBarTranslatedAction", null);
         myProperties[153] = new IndexedPropertyDescriptor("OutGCsfPromptPrompt1", rootClass, null, null, "getOutGCsfPromptPrompt1", null);
         myProperties[154] = new PropertyDescriptor("OutSecurityWsActionCode", rootClass, "getOutSecurityWsActionCode", null);
         myProperties[155] = new PropertyDescriptor("OutSecurityWsActionDescription", rootClass, "getOutSecurityWsActionDescription", null);
         myProperties[156] = new PropertyDescriptor("OutSecurityWsFunctionNumber", rootClass, "getOutSecurityWsFunctionNumber", null);
         myProperties[157] = new PropertyDescriptor("OutSecurityWsFunctionTrancode", rootClass, "getOutSecurityWsFunctionTrancode", null);
         myProperties[158] = new PropertyDescriptor("OutSecurityWsFunctionDescription", rootClass, "getOutSecurityWsFunctionDescription", null);
         myProperties[159] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[160] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[161] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[162] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[163] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[164] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[165] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[166] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[167] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[168] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[169] = new PropertyDescriptor("OutSecurityWsWorkstationCode", rootClass, "getOutSecurityWsWorkstationCode", null);
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
//         callMethod = Dissh10sLinksSuToStm.class.getMethod("execute", args);
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
