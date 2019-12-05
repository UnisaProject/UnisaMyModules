package Exerp01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Exerp01sPrtResultsAndTimetabBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Exerp01sPrtResultsAndTimetab.class;
 
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
      null,   // 170
      null,   // 171
      null,   // 172
      null,   // 173
      null,   // 174
      null,   // 175
      null,   // 176
      null,   // 177
      null,   // 178
      null,   // 179
      null,   // 180
      null,   // 181
      null,   // 182
      null,   // 183
      null,   // 184
      null,   // 185
      null,   // 186
      null,   // 187
      null,   // 188
      null,   // 189
      null,   // 190
      null,   // 191
      null,   // 192
      null,   // 193
      null,   // 194
      null,   // 195
      null,   // 196
      null,   // 197
      null,   // 198
      null,   // 199
      null,   // 200
      null,   // 201
      null,   // 202
      null,   // 203
      null,   // 204
      null,   // 205
      null,   // 206
      null,   // 207
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Exerp01sPrtResultsAndTimetab.class);
      bd.setDisplayName(Exerp01sPrtResultsAndTimetab.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InFromEmailAddressCsfStringsString132", rootClass);
         myProperties[15] = new PropertyDescriptor("InToEmailAddressCsfStringsString132", rootClass);
         myProperties[16] = new PropertyDescriptor("InFaxNoCsfStringsString132", rootClass);
         myProperties[17] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[18] = new PropertyDescriptor("InExamDetailStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[19] = new PropertyDescriptor("InExamDetailStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[20] = new PropertyDescriptor("InExamDetailStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[21] = new PropertyDescriptor("InExamDetailStudentAnnualRecordStatusCode", rootClass);
         myProperties[22] = new PropertyDescriptor("InExamDetailStudentAnnualRecordLibraryAccessLevel", rootClass);
         myProperties[23] = new PropertyDescriptor("InExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", rootClass);
         myProperties[24] = new PropertyDescriptor("InExamDetailStudentAnnualRecordMkHighestQualificationCode", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsStudentFirstNames", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsStudentMkHomeLanguage", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsStudentMkCorrespondenceLanguage", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsStudentDeceasedFlag", rootClass);
         myProperties[32].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentDeceasedFlagPropertyEditor.class);
         myProperties[33] = new PropertyDescriptor("InWsStudentLibraryBlackList", rootClass);
         myProperties[33].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentLibraryBlackListPropertyEditor.class);
         myProperties[34] = new PropertyDescriptor("InWsStudentExamFeesDebtFlag", rootClass);
         myProperties[34].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentExamFeesDebtFlagPropertyEditor.class);
         myProperties[35] = new PropertyDescriptor("InWsStudentDisciplinaryIncident", rootClass);
         myProperties[35].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentDisciplinaryIncidentPropertyEditor.class);
         myProperties[36] = new PropertyDescriptor("InWsStudentPhasedOutStatus", rootClass);
         myProperties[36].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentPhasedOutStatusPropertyEditor.class);
         myProperties[37] = new PropertyDescriptor("InWsStudentFinancialBlockFlag", rootClass);
         myProperties[37].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentFinancialBlockFlagPropertyEditor.class);
         myProperties[38] = new PropertyDescriptor("InWsStudentAccessRestrictedFlag", rootClass);
         myProperties[38].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentAccessRestrictedFlagPropertyEditor.class);
         myProperties[39] = new PropertyDescriptor("InWsStudentNumberRestrictedFlag", rootClass);
         myProperties[39].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentNumberRestrictedFlagPropertyEditor.class);
         myProperties[40] = new PropertyDescriptor("InWsStudentResultBlockFlag", rootClass);
         myProperties[40].setPropertyEditorClass(Exerp01sPrtResultsAndTimetabWsStudentResultBlockFlagPropertyEditor.class);
         myProperties[41] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsStudentMkLastAcademicPeriod", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[44] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[45] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[46] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[47] = new PropertyDescriptor("InStudentAnnualRecordStatusCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InStudentAnnualRecordLibraryAccessLevel", rootClass);
         myProperties[49] = new PropertyDescriptor("InStudentAnnualRecordSpecialLibraryAccessLevel", rootClass);
         myProperties[50] = new PropertyDescriptor("InStudentAnnualRecordMkHighestQualificationCode", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[56] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[57] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[58] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[59] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[60] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[61] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[62] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[63] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[64] = new PropertyDescriptor("InSecurityWsActionCode", rootClass);
         myProperties[65] = new PropertyDescriptor("InSecurityWsActionDescription", rootClass);
         myProperties[66] = new PropertyDescriptor("InSecurityWsFunctionNumber", rootClass);
         myProperties[67] = new PropertyDescriptor("InSecurityWsFunctionTrancode", rootClass);
         myProperties[68] = new PropertyDescriptor("InSecurityWsFunctionDescription", rootClass);
         myProperties[69] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[70] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[71] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[72] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[73] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[74] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[75] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[76] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[77] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[78] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[79] = new PropertyDescriptor("InSecurityWsWorkstationCode", rootClass);
         myProperties[80] = new PropertyDescriptor("InIpAddressCsfStringsString15", rootClass);
         myProperties[81] = new PropertyDescriptor("OutLine1CsfStringsString100", rootClass, "getOutLine1CsfStringsString100", null);
         myProperties[82] = new PropertyDescriptor("OutLine2CsfStringsString100", rootClass, "getOutLine2CsfStringsString100", null);
         myProperties[83] = new PropertyDescriptor("OutLine3CsfStringsString100", rootClass, "getOutLine3CsfStringsString100", null);
         myProperties[84] = new PropertyDescriptor("OutLine4CsfStringsString100", rootClass, "getOutLine4CsfStringsString100", null);
         myProperties[85] = new PropertyDescriptor("OutLine5CsfStringsString100", rootClass, "getOutLine5CsfStringsString100", null);
         myProperties[86] = new PropertyDescriptor("OutFinalOrProvCsfStringsString1", rootClass, "getOutFinalOrProvCsfStringsString1", null);
         myProperties[87] = new PropertyDescriptor("OutFromEmailCsfStringsString132", rootClass, "getOutFromEmailCsfStringsString132", null);
         myProperties[88] = new PropertyDescriptor("OutToEmailCsfStringsString132", rootClass, "getOutToEmailCsfStringsString132", null);
         myProperties[89] = new PropertyDescriptor("OutEmailWizfuncReportingControlPathAndFilename", rootClass, "getOutEmailWizfuncReportingControlPathAndFilename", null);
         myProperties[90] = new PropertyDescriptor("OutSubjectCsfStringsString132", rootClass, "getOutSubjectCsfStringsString132", null);
         myProperties[91] = new PropertyDescriptor("OutEmailOrFaxCsfStringsString1", rootClass, "getOutEmailOrFaxCsfStringsString1", null);
         myProperties[92] = new PropertyDescriptor("OutFaxNoCsfStringsString132", rootClass, "getOutFaxNoCsfStringsString132", null);
         myProperties[93] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[94] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[95] = new IndexedPropertyDescriptor("OutGCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGCsfLineActionBarLineReturnCode", null);
         myProperties[96] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[97] = new IndexedPropertyDescriptor("OutGCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGCsfLineActionBarTranslatedAction", null);
         myProperties[98] = new IndexedPropertyDescriptor("OutGRemarkCsfStringsString1", rootClass, null, null, "getOutGRemarkCsfStringsString1", null);
         myProperties[99] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitMkStudyUnitCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitMkStudyUnitCode", null);
         myProperties[100] = new IndexedPropertyDescriptor("OutGFinalMarkCsfStringsString3", rootClass, null, null, "getOutGFinalMarkCsfStringsString3", null);
         myProperties[101] = new IndexedPropertyDescriptor("OutGRemarksCsfStringsString50", rootClass, null, null, "getOutGRemarksCsfStringsString50", null);
         myProperties[102] = new IndexedPropertyDescriptor("OutGStudentExamResultAdjustmentMark", rootClass, null, null, "getOutGStudentExamResultAdjustmentMark", null);
         myProperties[103] = new IndexedPropertyDescriptor("OutGStudentExamResultFinalMark", rootClass, null, null, "getOutGStudentExamResultFinalMark", null);
         myProperties[104] = new IndexedPropertyDescriptor("OutGStudentExamResultMkResultTypeCode", rootClass, null, null, "getOutGStudentExamResultMkResultTypeCode", null);
         myProperties[105] = new IndexedPropertyDescriptor("OutGStudentExamResultSubminAdjust", rootClass, null, null, "getOutGStudentExamResultSubminAdjust", null);
         myProperties[106] = new IndexedPropertyDescriptor("OutGStudentExamResultYearMark", rootClass, null, null, "getOutGStudentExamResultYearMark", null);
         myProperties[107] = new IndexedPropertyDescriptor("OutGStudentExamResultPortfolioMark", rootClass, null, null, "getOutGStudentExamResultPortfolioMark", null);
         myProperties[108] = new IndexedPropertyDescriptor("OutGStudentExamResultExamMark", rootClass, null, null, "getOutGStudentExamResultExamMark", null);
         myProperties[109] = new IndexedPropertyDescriptor("OutGStudentExamResultExamMarkOverrite", rootClass, null, null, "getOutGStudentExamResultExamMarkOverrite", null);
         myProperties[110] = new IndexedPropertyDescriptor("OutGP1StudentExamPaperResultNr", rootClass, null, null, "getOutGP1StudentExamPaperResultNr", null);
         myProperties[111] = new IndexedPropertyDescriptor("OutGP1StudentExamPaperResultPaperMark", rootClass, null, null, "getOutGP1StudentExamPaperResultPaperMark", null);
         myProperties[112] = new IndexedPropertyDescriptor("OutGP1StudentExamPaperResultMarkreadingMark", rootClass, null, null, "getOutGP1StudentExamPaperResultMarkreadingMark", null);
         myProperties[113] = new IndexedPropertyDescriptor("OutGP1StudentExamPaperResultWrittenMark", rootClass, null, null, "getOutGP1StudentExamPaperResultWrittenMark", null);
         myProperties[114] = new IndexedPropertyDescriptor("OutGP1StudentExamPaperResultOralFlag", rootClass, null, null, "getOutGP1StudentExamPaperResultOralFlag", null);
         myProperties[115] = new IndexedPropertyDescriptor("OutGP2StudentExamPaperResultNr", rootClass, null, null, "getOutGP2StudentExamPaperResultNr", null);
         myProperties[116] = new IndexedPropertyDescriptor("OutGP2StudentExamPaperResultPaperMark", rootClass, null, null, "getOutGP2StudentExamPaperResultPaperMark", null);
         myProperties[117] = new IndexedPropertyDescriptor("OutGP2StudentExamPaperResultMarkreadingMark", rootClass, null, null, "getOutGP2StudentExamPaperResultMarkreadingMark", null);
         myProperties[118] = new IndexedPropertyDescriptor("OutGP2StudentExamPaperResultWrittenMark", rootClass, null, null, "getOutGP2StudentExamPaperResultWrittenMark", null);
         myProperties[119] = new IndexedPropertyDescriptor("OutGP2StudentExamPaperResultOralFlag", rootClass, null, null, "getOutGP2StudentExamPaperResultOralFlag", null);
         myProperties[120] = new IndexedPropertyDescriptor("OutGP1ExamPeriodDatePaperWeight", rootClass, null, null, "getOutGP1ExamPeriodDatePaperWeight", null);
         myProperties[121] = new IndexedPropertyDescriptor("OutGP1ExamPeriodDatePaperSubmin", rootClass, null, null, "getOutGP1ExamPeriodDatePaperSubmin", null);
         myProperties[122] = new IndexedPropertyDescriptor("OutGP2ExamPeriodDatePaperWeight", rootClass, null, null, "getOutGP2ExamPeriodDatePaperWeight", null);
         myProperties[123] = new IndexedPropertyDescriptor("OutGP2ExamPeriodDatePaperSubmin", rootClass, null, null, "getOutGP2ExamPeriodDatePaperSubmin", null);
         myProperties[124] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationYearMarkWeight", rootClass, null, null, "getOutGWsFinalMarkCalculationYearMarkWeight", null);
         myProperties[125] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationExaminationWeight", rootClass, null, null, "getOutGWsFinalMarkCalculationExaminationWeight", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationPortfolioWeight", rootClass, null, null, "getOutGWsFinalMarkCalculationPortfolioWeight", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationYmSubminimum", rootClass, null, null, "getOutGWsFinalMarkCalculationYmSubminimum", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationPfSubminimum", rootClass, null, null, "getOutGWsFinalMarkCalculationPfSubminimum", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationXamSubminimum", rootClass, null, null, "getOutGWsFinalMarkCalculationXamSubminimum", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationCalcYmSuppl", rootClass, null, null, "getOutGWsFinalMarkCalculationCalcYmSuppl", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutGWsFinalMarkCalculationCalcYmSubmin", rootClass, null, null, "getOutGWsFinalMarkCalculationCalcYmSubmin", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutGMessageCsfStringsString100", rootClass, null, null, "getOutGMessageCsfStringsString100", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutGShowDetailsIefSuppliedFlag", rootClass, null, null, "getOutGShowDetailsIefSuppliedFlag", null);
         myProperties[134] = new PropertyDescriptor("OutWizfuncReportingControlPrinterCode", rootClass, "getOutWizfuncReportingControlPrinterCode", null);
         myProperties[135] = new PropertyDescriptor("OutWizfuncReportingControlWizfuncReturnCode", rootClass, "getOutWizfuncReportingControlWizfuncReturnCode", null);
         myProperties[136] = new PropertyDescriptor("OutWizfuncReportingControlWizfuncReturnMessage", rootClass, "getOutWizfuncReportingControlWizfuncReturnMessage", null);
         myProperties[137] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordMkStudentNr", rootClass, "getOutExamDetailStudentAnnualRecordMkStudentNr", null);
         myProperties[138] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordMkAcademicYear", rootClass, "getOutExamDetailStudentAnnualRecordMkAcademicYear", null);
         myProperties[139] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordMkAcademicPeriod", rootClass, "getOutExamDetailStudentAnnualRecordMkAcademicPeriod", null);
         myProperties[140] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordStatusCode", rootClass, "getOutExamDetailStudentAnnualRecordStatusCode", null);
         myProperties[141] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordLibraryAccessLevel", rootClass, "getOutExamDetailStudentAnnualRecordLibraryAccessLevel", null);
         myProperties[142] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", rootClass, "getOutExamDetailStudentAnnualRecordSpecialLibraryAccessLevel", null);
         myProperties[143] = new PropertyDescriptor("OutExamDetailStudentAnnualRecordMkHighestQualificationCode", rootClass, "getOutExamDetailStudentAnnualRecordMkHighestQualificationCode", null);
         myProperties[144] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[145] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[146] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[147] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[148] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[149] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[150] = new PropertyDescriptor("OutWsStudentMkHomeLanguage", rootClass, "getOutWsStudentMkHomeLanguage", null);
         myProperties[151] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[152] = new PropertyDescriptor("OutWsStudentDeceasedFlag", rootClass, "getOutWsStudentDeceasedFlag", null);
         myProperties[153] = new PropertyDescriptor("OutWsStudentLibraryBlackList", rootClass, "getOutWsStudentLibraryBlackList", null);
         myProperties[154] = new PropertyDescriptor("OutWsStudentExamFeesDebtFlag", rootClass, "getOutWsStudentExamFeesDebtFlag", null);
         myProperties[155] = new PropertyDescriptor("OutWsStudentDisciplinaryIncident", rootClass, "getOutWsStudentDisciplinaryIncident", null);
         myProperties[156] = new PropertyDescriptor("OutWsStudentPhasedOutStatus", rootClass, "getOutWsStudentPhasedOutStatus", null);
         myProperties[157] = new PropertyDescriptor("OutWsStudentFinancialBlockFlag", rootClass, "getOutWsStudentFinancialBlockFlag", null);
         myProperties[158] = new PropertyDescriptor("OutWsStudentAccessRestrictedFlag", rootClass, "getOutWsStudentAccessRestrictedFlag", null);
         myProperties[159] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[160] = new PropertyDescriptor("OutWsStudentResultBlockFlag", rootClass, "getOutWsStudentResultBlockFlag", null);
         myProperties[161] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[162] = new PropertyDescriptor("OutWsStudentMkLastAcademicPeriod", rootClass, "getOutWsStudentMkLastAcademicPeriod", null);
         myProperties[163] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[164] = new PropertyDescriptor("OutStudentAnnualRecordMkStudentNr", rootClass, "getOutStudentAnnualRecordMkStudentNr", null);
         myProperties[165] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicYear", rootClass, "getOutStudentAnnualRecordMkAcademicYear", null);
         myProperties[166] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicPeriod", rootClass, "getOutStudentAnnualRecordMkAcademicPeriod", null);
         myProperties[167] = new PropertyDescriptor("OutStudentAnnualRecordStatusCode", rootClass, "getOutStudentAnnualRecordStatusCode", null);
         myProperties[168] = new PropertyDescriptor("OutStudentAnnualRecordLibraryAccessLevel", rootClass, "getOutStudentAnnualRecordLibraryAccessLevel", null);
         myProperties[169] = new PropertyDescriptor("OutStudentAnnualRecordSpecialLibraryAccessLevel", rootClass, "getOutStudentAnnualRecordSpecialLibraryAccessLevel", null);
         myProperties[170] = new PropertyDescriptor("OutStudentAnnualRecordMkHighestQualificationCode", rootClass, "getOutStudentAnnualRecordMkHighestQualificationCode", null);
         myProperties[171] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[172] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[173] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[174] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[175] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[176] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[177] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[178] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[179] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[180] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[181] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[182] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[183] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[184] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[185] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[186] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[187] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[188] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[189] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[190] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[191] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[192] = new PropertyDescriptor("OutSecurityWsActionCode", rootClass, "getOutSecurityWsActionCode", null);
         myProperties[193] = new PropertyDescriptor("OutSecurityWsActionDescription", rootClass, "getOutSecurityWsActionDescription", null);
         myProperties[194] = new PropertyDescriptor("OutSecurityWsFunctionNumber", rootClass, "getOutSecurityWsFunctionNumber", null);
         myProperties[195] = new PropertyDescriptor("OutSecurityWsFunctionTrancode", rootClass, "getOutSecurityWsFunctionTrancode", null);
         myProperties[196] = new PropertyDescriptor("OutSecurityWsFunctionDescription", rootClass, "getOutSecurityWsFunctionDescription", null);
         myProperties[197] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[198] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[199] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[200] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[201] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[202] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[203] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[204] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[205] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[206] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[207] = new PropertyDescriptor("OutSecurityWsWorkstationCode", rootClass, "getOutSecurityWsWorkstationCode", null);
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
//         callMethod = Exerp01sPrtResultsAndTimetab.class.getMethod("execute", args);
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
