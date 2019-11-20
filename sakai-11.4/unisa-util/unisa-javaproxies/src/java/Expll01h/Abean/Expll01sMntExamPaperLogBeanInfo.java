package Expll01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Expll01sMntExamPaperLogBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Expll01sMntExamPaperLog.class;
 
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
      null,   // 208
      null,   // 209
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Expll01sMntExamPaperLog.class);
      bd.setDisplayName(Expll01sMntExamPaperLog.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InExamTypistLogEntryPaperNo", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsExpll01QuestPapOption", rootClass);
         myProperties[15].setPropertyEditorClass(Expll01sMntExamPaperLogWsExpll01QuestPapOptionPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InWsExpll01LogisticsOption", rootClass);
         myProperties[16].setPropertyEditorClass(Expll01sMntExamPaperLogWsExpll01LogisticsOptionPropertyEditor.class);
         myProperties[17] = new PropertyDescriptor("InNrOfCombinedPapersIefSuppliedCount", rootClass);
         myProperties[18] = new PropertyDescriptor("InNextExamTypistLogExamYear", rootClass);
         myProperties[19] = new PropertyDescriptor("InNextExamTypistLogMkExamPeriodCod", rootClass);
         myProperties[20] = new PropertyDescriptor("InPrevExamTypistLogExamYear", rootClass);
         myProperties[21] = new PropertyDescriptor("InPrevExamTypistLogMkExamPeriodCod", rootClass);
         myProperties[22] = new IndexedPropertyDescriptor("InGCalcQuantIefSuppliedCount", rootClass, null, null, "getInGCalcQuantIefSuppliedCount", "setInGCalcQuantIefSuppliedCount");
         myProperties[23] = new IndexedPropertyDescriptor("InGPaperColourCsfStringsString10", rootClass, null, null, "getInGPaperColourCsfStringsString10", "setInGPaperColourCsfStringsString10");
         myProperties[24] = new IndexedPropertyDescriptor("InGExamPeriodDateDate", rootClass, null, null, "getInGExamPeriodDateDate", "setInGExamPeriodDateDate");
         myProperties[25] = new IndexedPropertyDescriptor("InGIefSuppliedCount", rootClass, null, null, "getInGIefSuppliedCount", "setInGIefSuppliedCount");
         myProperties[26] = new IndexedPropertyDescriptor("InGIefSuppliedCommand", rootClass, null, null, "getInGIefSuppliedCommand", "setInGIefSuppliedCommand");
         myProperties[27] = new IndexedPropertyDescriptor("InGIefSuppliedSelectChar", rootClass, null, null, "getInGIefSuppliedSelectChar", "setInGIefSuppliedSelectChar");
         myProperties[28] = new IndexedPropertyDescriptor("InGExamTypistLogEntryPaperNo", rootClass, null, null, "getInGExamTypistLogEntryPaperNo", "setInGExamTypistLogEntryPaperNo");
         myProperties[29] = new IndexedPropertyDescriptor("InGExamTypistLogEntryTypist", rootClass, null, null, "getInGExamTypistLogEntryTypist", "setInGExamTypistLogEntryTypist");
         myProperties[30] = new IndexedPropertyDescriptor("InGExamTypistLogEntryPaperFormat", rootClass, null, null, "getInGExamTypistLogEntryPaperFormat", "setInGExamTypistLogEntryPaperFormat");
         myProperties[31] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateReceived", rootClass, null, null, "getInGExamTypistLogEntryDateReceived", "setInGExamTypistLogEntryDateReceived");
         myProperties[32] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateProof1", rootClass, null, null, "getInGExamTypistLogEntryDateProof1", "setInGExamTypistLogEntryDateProof1");
         myProperties[33] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateProof2", rootClass, null, null, "getInGExamTypistLogEntryDateProof2", "setInGExamTypistLogEntryDateProof2");
         myProperties[34] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateProof3", rootClass, null, null, "getInGExamTypistLogEntryDateProof3", "setInGExamTypistLogEntryDateProof3");
         myProperties[35] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateToPrint", rootClass, null, null, "getInGExamTypistLogEntryDateToPrint", "setInGExamTypistLogEntryDateToPrint");
         myProperties[36] = new IndexedPropertyDescriptor("InGExamTypistLogEntryCpfReportPrinted", rootClass, null, null, "getInGExamTypistLogEntryCpfReportPrinted", "setInGExamTypistLogEntryCpfReportPrinted");
         myProperties[37] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateInSafe", rootClass, null, null, "getInGExamTypistLogEntryDateInSafe", "setInGExamTypistLogEntryDateInSafe");
         myProperties[38] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateToDept", rootClass, null, null, "getInGExamTypistLogEntryDateToDept", "setInGExamTypistLogEntryDateToDept");
         myProperties[39] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateFromDept", rootClass, null, null, "getInGExamTypistLogEntryDateFromDept", "setInGExamTypistLogEntryDateFromDept");
         myProperties[40] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDate2ToPrint", rootClass, null, null, "getInGExamTypistLogEntryDate2ToPrint", "setInGExamTypistLogEntryDate2ToPrint");
         myProperties[41] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDate2FromPrint", rootClass, null, null, "getInGExamTypistLogEntryDate2FromPrint", "setInGExamTypistLogEntryDate2FromPrint");
         myProperties[42] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDate3ToPrint", rootClass, null, null, "getInGExamTypistLogEntryDate3ToPrint", "setInGExamTypistLogEntryDate3ToPrint");
         myProperties[43] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDate3FromPrint", rootClass, null, null, "getInGExamTypistLogEntryDate3FromPrint", "setInGExamTypistLogEntryDate3FromPrint");
         myProperties[44] = new IndexedPropertyDescriptor("InGExamTypistLogEntryQuant3ToPrint", rootClass, null, null, "getInGExamTypistLogEntryQuant3ToPrint", "setInGExamTypistLogEntryQuant3ToPrint");
         myProperties[45] = new IndexedPropertyDescriptor("InGExamTypistLogEntryQuantToPrint", rootClass, null, null, "getInGExamTypistLogEntryQuantToPrint", "setInGExamTypistLogEntryQuantToPrint");
         myProperties[46] = new IndexedPropertyDescriptor("InGExamTypistLogEntryQuantCalcedOn", rootClass, null, null, "getInGExamTypistLogEntryQuantCalcedOn", "setInGExamTypistLogEntryQuantCalcedOn");
         myProperties[47] = new IndexedPropertyDescriptor("InGExamTypistLogEntryQuant2ToPrint", rootClass, null, null, "getInGExamTypistLogEntryQuant2ToPrint", "setInGExamTypistLogEntryQuant2ToPrint");
         myProperties[48] = new IndexedPropertyDescriptor("InGExamTypistLogEntryQuant2CalcedOn", rootClass, null, null, "getInGExamTypistLogEntryQuant2CalcedOn", "setInGExamTypistLogEntryQuant2CalcedOn");
         myProperties[49] = new IndexedPropertyDescriptor("InGExamTypistLogEntrySurplusQuant", rootClass, null, null, "getInGExamTypistLogEntrySurplusQuant", "setInGExamTypistLogEntrySurplusQuant");
         myProperties[50] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateScanned", rootClass, null, null, "getInGExamTypistLogEntryDateScanned", "setInGExamTypistLogEntryDateScanned");
         myProperties[51] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDateAfrScanned", rootClass, null, null, "getInGExamTypistLogEntryDateAfrScanned", "setInGExamTypistLogEntryDateAfrScanned");
         myProperties[52] = new IndexedPropertyDescriptor("InGExamTypistLogEntryOpenForWeb", rootClass, null, null, "getInGExamTypistLogEntryOpenForWeb", "setInGExamTypistLogEntryOpenForWeb");
         myProperties[53] = new IndexedPropertyDescriptor("InGExamTypistLogEntryDocketChanges", rootClass, null, null, "getInGExamTypistLogEntryDocketChanges", "setInGExamTypistLogEntryDocketChanges");
         myProperties[54] = new IndexedPropertyDescriptor("InGExamTypistLogEntryPaperChanges", rootClass, null, null, "getInGExamTypistLogEntryPaperChanges", "setInGExamTypistLogEntryPaperChanges");
         myProperties[55] = new IndexedPropertyDescriptor("InGExamTypistLogEntryElectronicPaper", rootClass, null, null, "getInGExamTypistLogEntryElectronicPaper", "setInGExamTypistLogEntryElectronicPaper");
         myProperties[56] = new IndexedPropertyDescriptor("InGMyunisaWsRegistrationDatesFromDate", rootClass, null, null, "getInGMyunisaWsRegistrationDatesFromDate", "setInGMyunisaWsRegistrationDatesFromDate");
         myProperties[57] = new IndexedPropertyDescriptor("InGEngPaperCsfStringsString1", rootClass, null, null, "getInGEngPaperCsfStringsString1", "setInGEngPaperCsfStringsString1");
         myProperties[58] = new IndexedPropertyDescriptor("InGAfrPaperCsfStringsString1", rootClass, null, null, "getInGAfrPaperCsfStringsString1", "setInGAfrPaperCsfStringsString1");
         myProperties[59] = new PropertyDescriptor("InSearchCriteriaExamTypistLogExamYear", rootClass);
         myProperties[60] = new PropertyDescriptor("InSearchCriteriaExamTypistLogMkExamPeriodCod", rootClass);
         myProperties[61] = new PropertyDescriptor("InSearchCriteriaExamTypistLogMkStudyUnitCode", rootClass);
         myProperties[62] = new PropertyDescriptor("InKeyExamTypistLogExamYear", rootClass);
         myProperties[63] = new PropertyDescriptor("InKeyExamTypistLogMkExamPeriodCod", rootClass);
         myProperties[64] = new PropertyDescriptor("InKeyExamTypistLogMkStudyUnitCode", rootClass);
         myProperties[65] = new PropertyDescriptor("InKeyExamTypistLogCombinedWith", rootClass);
         myProperties[66] = new PropertyDescriptor("InKeyExamTypistLogRemarks", rootClass);
         myProperties[67] = new PropertyDescriptor("InKeyExamTypistLogRemarks2", rootClass);
         myProperties[68] = new PropertyDescriptor("InKeyExamTypistLogRemarks3", rootClass);
         myProperties[69] = new PropertyDescriptor("InKeyExamTypistLogRemarks4", rootClass);
         myProperties[70] = new PropertyDescriptor("InKeyExamTypistLogRemarks5", rootClass);
         myProperties[71] = new PropertyDescriptor("InKeyExamTypistLogRemarks6", rootClass);
         myProperties[72] = new PropertyDescriptor("InKeyExamTypistLogPaperExpected", rootClass);
         myProperties[73] = new PropertyDescriptor("InReportDateExamTypistLogEntryDateToPrint", rootClass);
         myProperties[74] = new PropertyDescriptor("InReportWizfuncReportingControlPathAndFilename", rootClass);
         myProperties[75] = new PropertyDescriptor("InOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod", rootClass);
         myProperties[75].setPropertyEditorClass(Expll01sMntExamPaperLogWsStudyUnitPeriodDetailMkExamPeriodPropertyEditor.class);
         myProperties[76] = new PropertyDescriptor("InOutstExamPapersWsStudyUnitPeriodDetailMkExamYear", rootClass);
         myProperties[77] = new PropertyDescriptor("InOutstExamPapersWsStudyUnitPeriodDetailMkAcademicYear", rootClass);
         myProperties[78] = new PropertyDescriptor("InOutstExamPapersWsStudyUnitPeriodDetailSemesterPeriod", rootClass);
         myProperties[79] = new PropertyDescriptor("InScCpfAccessIefSuppliedFlag", rootClass);
         myProperties[79].setPropertyEditorClass(Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor.class);
         myProperties[80] = new PropertyDescriptor("InScTypAccessIefSuppliedFlag", rootClass);
         myProperties[80].setPropertyEditorClass(Expll01sMntExamPaperLogIefSuppliedFlagPropertyEditor.class);
         myProperties[81] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[82] = new PropertyDescriptor("InWsStudyUnitMkDepartmentCode", rootClass);
         myProperties[83] = new PropertyDescriptor("InWsStudyUnitEngLongDescription", rootClass);
         myProperties[84] = new PropertyDescriptor("InWsStudyUnitCollegeFlag", rootClass);
         myProperties[85] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[86] = new PropertyDescriptor("InSecurityWsUserEMail", rootClass);
         myProperties[87] = new PropertyDescriptor("InSecurityWsUserNovellUserCode", rootClass);
         myProperties[88] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[89] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[90] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[91] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[92] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[93] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[94] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[95] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[96] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[97] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[98] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[99] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[100] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[101] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[102] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[103] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[104] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[105] = new PropertyDescriptor("InWsDepartmentCode", rootClass);
         myProperties[106] = new PropertyDescriptor("InWsUnisaCollegeCode", rootClass);
         myProperties[107] = new PropertyDescriptor("InOnlineOrInternetCsfStringsString1", rootClass);
         myProperties[108] = new PropertyDescriptor("OutNrOfCombinedPapersIefSuppliedCount", rootClass, "getOutNrOfCombinedPapersIefSuppliedCount", null);
         myProperties[109] = new PropertyDescriptor("OutNextExamTypistLogExamYear", rootClass, "getOutNextExamTypistLogExamYear", null);
         myProperties[110] = new PropertyDescriptor("OutNextExamTypistLogMkExamPeriodCod", rootClass, "getOutNextExamTypistLogMkExamPeriodCod", null);
         myProperties[111] = new PropertyDescriptor("OutPrevExamTypistLogExamYear", rootClass, "getOutPrevExamTypistLogExamYear", null);
         myProperties[112] = new PropertyDescriptor("OutPrevExamTypistLogMkExamPeriodCod", rootClass, "getOutPrevExamTypistLogMkExamPeriodCod", null);
         myProperties[113] = new PropertyDescriptor("OutKeyExamTypistLogExamYear", rootClass, "getOutKeyExamTypistLogExamYear", null);
         myProperties[114] = new PropertyDescriptor("OutKeyExamTypistLogMkExamPeriodCod", rootClass, "getOutKeyExamTypistLogMkExamPeriodCod", null);
         myProperties[115] = new PropertyDescriptor("OutKeyExamTypistLogMkStudyUnitCode", rootClass, "getOutKeyExamTypistLogMkStudyUnitCode", null);
         myProperties[116] = new PropertyDescriptor("OutKeyExamTypistLogCombinedWith", rootClass, "getOutKeyExamTypistLogCombinedWith", null);
         myProperties[117] = new PropertyDescriptor("OutKeyExamTypistLogRemarks", rootClass, "getOutKeyExamTypistLogRemarks", null);
         myProperties[118] = new PropertyDescriptor("OutKeyExamTypistLogRemarks2", rootClass, "getOutKeyExamTypistLogRemarks2", null);
         myProperties[119] = new PropertyDescriptor("OutKeyExamTypistLogRemarks3", rootClass, "getOutKeyExamTypistLogRemarks3", null);
         myProperties[120] = new PropertyDescriptor("OutKeyExamTypistLogRemarks4", rootClass, "getOutKeyExamTypistLogRemarks4", null);
         myProperties[121] = new PropertyDescriptor("OutKeyExamTypistLogRemarks5", rootClass, "getOutKeyExamTypistLogRemarks5", null);
         myProperties[122] = new PropertyDescriptor("OutKeyExamTypistLogRemarks6", rootClass, "getOutKeyExamTypistLogRemarks6", null);
         myProperties[123] = new PropertyDescriptor("OutKeyExamTypistLogPaperExpected", rootClass, "getOutKeyExamTypistLogPaperExpected", null);
         myProperties[124] = new PropertyDescriptor("OutSearchCriteriaExamTypistLogExamYear", rootClass, "getOutSearchCriteriaExamTypistLogExamYear", null);
         myProperties[125] = new PropertyDescriptor("OutSearchCriteriaExamTypistLogMkExamPeriodCod", rootClass, "getOutSearchCriteriaExamTypistLogMkExamPeriodCod", null);
         myProperties[126] = new PropertyDescriptor("OutSearchCriteriaExamTypistLogMkStudyUnitCode", rootClass, "getOutSearchCriteriaExamTypistLogMkStudyUnitCode", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGCalcQuantIefSuppliedCount", rootClass, null, null, "getOutGCalcQuantIefSuppliedCount", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGPaperColourCsfStringsString10", rootClass, null, null, "getOutGPaperColourCsfStringsString10", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutGExamPeriodDateDate", rootClass, null, null, "getOutGExamPeriodDateDate", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutGIefSuppliedCount", rootClass, null, null, "getOutGIefSuppliedCount", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutGIefSuppliedCommand", rootClass, null, null, "getOutGIefSuppliedCommand", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutGIefSuppliedSelectChar", rootClass, null, null, "getOutGIefSuppliedSelectChar", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryPaperNo", rootClass, null, null, "getOutGExamTypistLogEntryPaperNo", null);
         myProperties[134] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryTypist", rootClass, null, null, "getOutGExamTypistLogEntryTypist", null);
         myProperties[135] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryPaperFormat", rootClass, null, null, "getOutGExamTypistLogEntryPaperFormat", null);
         myProperties[136] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateReceived", rootClass, null, null, "getOutGExamTypistLogEntryDateReceived", null);
         myProperties[137] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateProof1", rootClass, null, null, "getOutGExamTypistLogEntryDateProof1", null);
         myProperties[138] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateProof2", rootClass, null, null, "getOutGExamTypistLogEntryDateProof2", null);
         myProperties[139] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateProof3", rootClass, null, null, "getOutGExamTypistLogEntryDateProof3", null);
         myProperties[140] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateToPrint", rootClass, null, null, "getOutGExamTypistLogEntryDateToPrint", null);
         myProperties[141] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryCpfReportPrinted", rootClass, null, null, "getOutGExamTypistLogEntryCpfReportPrinted", null);
         myProperties[142] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateInSafe", rootClass, null, null, "getOutGExamTypistLogEntryDateInSafe", null);
         myProperties[143] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateToDept", rootClass, null, null, "getOutGExamTypistLogEntryDateToDept", null);
         myProperties[144] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateFromDept", rootClass, null, null, "getOutGExamTypistLogEntryDateFromDept", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDate2ToPrint", rootClass, null, null, "getOutGExamTypistLogEntryDate2ToPrint", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDate2FromPrint", rootClass, null, null, "getOutGExamTypistLogEntryDate2FromPrint", null);
         myProperties[147] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDate3ToPrint", rootClass, null, null, "getOutGExamTypistLogEntryDate3ToPrint", null);
         myProperties[148] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDate3FromPrint", rootClass, null, null, "getOutGExamTypistLogEntryDate3FromPrint", null);
         myProperties[149] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryQuant3ToPrint", rootClass, null, null, "getOutGExamTypistLogEntryQuant3ToPrint", null);
         myProperties[150] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryQuantToPrint", rootClass, null, null, "getOutGExamTypistLogEntryQuantToPrint", null);
         myProperties[151] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryQuantCalcedOn", rootClass, null, null, "getOutGExamTypistLogEntryQuantCalcedOn", null);
         myProperties[152] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryQuant2ToPrint", rootClass, null, null, "getOutGExamTypistLogEntryQuant2ToPrint", null);
         myProperties[153] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryQuant2CalcedOn", rootClass, null, null, "getOutGExamTypistLogEntryQuant2CalcedOn", null);
         myProperties[154] = new IndexedPropertyDescriptor("OutGExamTypistLogEntrySurplusQuant", rootClass, null, null, "getOutGExamTypistLogEntrySurplusQuant", null);
         myProperties[155] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateScanned", rootClass, null, null, "getOutGExamTypistLogEntryDateScanned", null);
         myProperties[156] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDateAfrScanned", rootClass, null, null, "getOutGExamTypistLogEntryDateAfrScanned", null);
         myProperties[157] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryOpenForWeb", rootClass, null, null, "getOutGExamTypistLogEntryOpenForWeb", null);
         myProperties[158] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryDocketChanges", rootClass, null, null, "getOutGExamTypistLogEntryDocketChanges", null);
         myProperties[159] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryPaperChanges", rootClass, null, null, "getOutGExamTypistLogEntryPaperChanges", null);
         myProperties[160] = new IndexedPropertyDescriptor("OutGExamTypistLogEntryElectronicPaper", rootClass, null, null, "getOutGExamTypistLogEntryElectronicPaper", null);
         myProperties[161] = new IndexedPropertyDescriptor("OutGMyunisaWsRegistrationDatesFromDate", rootClass, null, null, "getOutGMyunisaWsRegistrationDatesFromDate", null);
         myProperties[162] = new IndexedPropertyDescriptor("OutGEngPaperCsfStringsString1", rootClass, null, null, "getOutGEngPaperCsfStringsString1", null);
         myProperties[163] = new IndexedPropertyDescriptor("OutGAfrPaperCsfStringsString1", rootClass, null, null, "getOutGAfrPaperCsfStringsString1", null);
         myProperties[164] = new IndexedPropertyDescriptor("OutGElectronicLogsCsfStringsString1", rootClass, null, null, "getOutGElectronicLogsCsfStringsString1", null);
         myProperties[165] = new PropertyDescriptor("OutScCpfAccessIefSuppliedFlag", rootClass, "getOutScCpfAccessIefSuppliedFlag", null);
         myProperties[166] = new PropertyDescriptor("OutScTypAccessIefSuppliedFlag", rootClass, "getOutScTypAccessIefSuppliedFlag", null);
         myProperties[167] = new PropertyDescriptor("OutIefSuppliedFlag", rootClass, "getOutIefSuppliedFlag", null);
         myProperties[168] = new PropertyDescriptor("OutWsFunctionNumber", rootClass, "getOutWsFunctionNumber", null);
         myProperties[169] = new IndexedPropertyDescriptor("OutGListIefSuppliedSelectChar", rootClass, null, null, "getOutGListIefSuppliedSelectChar", null);
         myProperties[170] = new IndexedPropertyDescriptor("OutGListCsfStringsString100", rootClass, null, null, "getOutGListCsfStringsString100", null);
         myProperties[171] = new PropertyDescriptor("OutWsStudyUnitMkDepartmentCode", rootClass, "getOutWsStudyUnitMkDepartmentCode", null);
         myProperties[172] = new PropertyDescriptor("OutWsStudyUnitEngLongDescription", rootClass, "getOutWsStudyUnitEngLongDescription", null);
         myProperties[173] = new PropertyDescriptor("OutWsStudyUnitCollegeFlag", rootClass, "getOutWsStudyUnitCollegeFlag", null);
         myProperties[174] = new PropertyDescriptor("OutErrMsgCsfStringsString500", rootClass, "getOutErrMsgCsfStringsString500", null);
         myProperties[175] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[176] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[177] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[178] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[179] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[180] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[181] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[182] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[183] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[184] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[185] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[186] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[187] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[188] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[189] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[190] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[191] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[192] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[193] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[194] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[195] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[196] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[197] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[198] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[199] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[200] = new PropertyDescriptor("OutReportWizfuncReportingControlFunction", rootClass, "getOutReportWizfuncReportingControlFunction", null);
         myProperties[201] = new PropertyDescriptor("OutReportWizfuncReportingControlPathAndFilename", rootClass, "getOutReportWizfuncReportingControlPathAndFilename", null);
         myProperties[202] = new PropertyDescriptor("OutReportWizfuncReportingControlReportWidth", rootClass, "getOutReportWizfuncReportingControlReportWidth", null);
         myProperties[203] = new PropertyDescriptor("OutReportWizfuncReportingControlStartingLineCount", rootClass, "getOutReportWizfuncReportingControlStartingLineCount", null);
         myProperties[204] = new PropertyDescriptor("OutReportWizfuncReportingControlEndingLineCount", rootClass, "getOutReportWizfuncReportingControlEndingLineCount", null);
         myProperties[205] = new PropertyDescriptor("OutReportWizfuncReportingControlWizfuncReturnCode", rootClass, "getOutReportWizfuncReportingControlWizfuncReturnCode", null);
         myProperties[206] = new PropertyDescriptor("OutReportWizfuncReportingControlWizfuncReturnMessage", rootClass, "getOutReportWizfuncReportingControlWizfuncReturnMessage", null);
         myProperties[207] = new IndexedPropertyDescriptor("OutEquGWsExamEquivalentsWsEquivalentCode", rootClass, null, null, "getOutEquGWsExamEquivalentsWsEquivalentCode", null);
         myProperties[208] = new IndexedPropertyDescriptor("OutEquGWsExamEquivalentsWsSamePaperFlag", rootClass, null, null, "getOutEquGWsExamEquivalentsWsSamePaperFlag", null);
         myProperties[209] = new PropertyDescriptor("OutPrintQuantityIefSuppliedCount", rootClass, "getOutPrintQuantityIefSuppliedCount", null);
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
//         callMethod = Expll01sMntExamPaperLog.class.getMethod("execute", args);
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
