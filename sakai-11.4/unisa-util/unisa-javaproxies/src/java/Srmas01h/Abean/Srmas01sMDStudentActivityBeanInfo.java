package Srmas01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srmas01sMDStudentActivityBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srmas01sMDStudentActivity.class;
 
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
         new BeanDescriptor(Srmas01sMDStudentActivity.class);
      bd.setDisplayName(Srmas01sMDStudentActivity.BEANNAME);
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
         myProperties[14] = new IndexedPropertyDescriptor("InGStudentDissertationPromoterMkPromotorNr", rootClass, null, null, "getInGStudentDissertationPromoterMkPromotorNr", "setInGStudentDissertationPromoterMkPromotorNr");
         myProperties[15] = new IndexedPropertyDescriptor("InGStudentDissertationPromoterSupervisorFlag", rootClass, null, null, "getInGStudentDissertationPromoterSupervisorFlag", "setInGStudentDissertationPromoterSupervisorFlag");
         myProperties[15].setPropertyEditorClass(Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor.class);
         myProperties[16] = new IndexedPropertyDescriptor("InGStudentDissertationPromoterMkDepartmentCode", rootClass, null, null, "getInGStudentDissertationPromoterMkDepartmentCode", "setInGStudentDissertationPromoterMkDepartmentCode");
         myProperties[17] = new IndexedPropertyDescriptor("InGCsfStringsString100", rootClass, null, null, "getInGCsfStringsString100", "setInGCsfStringsString100");
         myProperties[18] = new IndexedPropertyDescriptor("InGCsfStringsString28", rootClass, null, null, "getInGCsfStringsString28", "setInGCsfStringsString28");
         myProperties[19] = new PropertyDescriptor("InWsGenericCodeCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InStudentDissertationPromoterMkPromotorNr", rootClass);
         myProperties[21] = new PropertyDescriptor("InStudentDissertationPromoterSupervisorFlag", rootClass);
         myProperties[21].setPropertyEditorClass(Srmas01sMDStudentActivityStudentDissertationPromoterSupervisorFlagPropertyEditor.class);
         myProperties[22] = new PropertyDescriptor("InStudentDissertationPromoterType", rootClass);
         myProperties[23] = new PropertyDescriptor("InPromotorNameCsfStringsString50", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsStaffPersno", rootClass);
         myProperties[25] = new PropertyDescriptor("InStaffNameCsfStringsString50", rootClass);
         myProperties[26] = new PropertyDescriptor("InPositionMdActivityActivityDate", rootClass);
         myProperties[27] = new PropertyDescriptor("InMaintainMdActivityActivityCode", rootClass);
         myProperties[28] = new PropertyDescriptor("InMaintainMdActivityActivityDate", rootClass);
         myProperties[29] = new PropertyDescriptor("InMaintainMdActivityFeedbackDate", rootClass);
         myProperties[30] = new PropertyDescriptor("InMaintainMdActivityComments", rootClass);
         myProperties[31] = new IndexedPropertyDescriptor("InGrCsfLineActionBarAction", rootClass, null, null, "getInGrCsfLineActionBarAction", "setInGrCsfLineActionBarAction");
         myProperties[32] = new IndexedPropertyDescriptor("InGrCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGrCsfLineActionBarSelectionFlag", "setInGrCsfLineActionBarSelectionFlag");
         myProperties[33] = new IndexedPropertyDescriptor("InGrCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGrCsfLineActionBarTranslatedAction", "setInGrCsfLineActionBarTranslatedAction");
         myProperties[34] = new IndexedPropertyDescriptor("InGrMdActivityStaffNumber", rootClass, null, null, "getInGrMdActivityStaffNumber", "setInGrMdActivityStaffNumber");
         myProperties[35] = new IndexedPropertyDescriptor("InGrMdActivityActivityCode", rootClass, null, null, "getInGrMdActivityActivityCode", "setInGrMdActivityActivityCode");
         myProperties[36] = new IndexedPropertyDescriptor("InGrMdActivityEntryTimestamp", rootClass, null, null, "getInGrMdActivityEntryTimestamp", "setInGrMdActivityEntryTimestamp");
         myProperties[37] = new IndexedPropertyDescriptor("InGrMdActivityActivityDate", rootClass, null, null, "getInGrMdActivityActivityDate", "setInGrMdActivityActivityDate");
         myProperties[38] = new IndexedPropertyDescriptor("InGrMdActivityFeedbackDate", rootClass, null, null, "getInGrMdActivityFeedbackDate", "setInGrMdActivityFeedbackDate");
         myProperties[39] = new IndexedPropertyDescriptor("InGrMdActivityComments", rootClass, null, null, "getInGrMdActivityComments", "setInGrMdActivityComments");
         myProperties[40] = new IndexedPropertyDescriptor("InGrDescriptionsCsfStringsString40", rootClass, null, null, "getInGrDescriptionsCsfStringsString40", "setInGrDescriptionsCsfStringsString40");
         myProperties[41] = new IndexedPropertyDescriptor("InGrDescriptionsCsfStringsString50", rootClass, null, null, "getInGrDescriptionsCsfStringsString50", "setInGrDescriptionsCsfStringsString50");
         myProperties[42] = new PropertyDescriptor("InTypeDescriptionCsfStringsString30", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsStudentPostGraduateStudiesApproved", rootClass);
         myProperties[45].setPropertyEditorClass(Srmas01sMDStudentActivityWsStudentPostGraduateStudiesApprovedPropertyEditor.class);
         myProperties[46] = new PropertyDescriptor("InWsStudentAnnualRecordStatusCode", rootClass);
         myProperties[46].setPropertyEditorClass(Srmas01sMDStudentActivityWsStudentAnnualRecordStatusCodePropertyEditor.class);
         myProperties[47] = new PropertyDescriptor("InWsStudentAnnualRecordMkHighestQualCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InStudentNameCsfStringsString100", rootClass);
         myProperties[49] = new PropertyDescriptor("InWsQualificationShortDescription", rootClass);
         myProperties[50] = new PropertyDescriptor("InStudentDissertationMkStudentNr", rootClass);
         myProperties[51] = new PropertyDescriptor("InStudentDissertationMkStudyUnitCode", rootClass);
         myProperties[52] = new PropertyDescriptor("InStudentDissertationType", rootClass);
         myProperties[52].setPropertyEditorClass(Srmas01sMDStudentActivityStudentDissertationTypePropertyEditor.class);
         myProperties[53] = new PropertyDescriptor("InStudentDissertationTitle", rootClass);
         myProperties[54] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[55] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[56] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[57] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[58] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[59] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[60] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[61] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[62] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[63] = new PropertyDescriptor("InSecurityWsUserNovellUserCode", rootClass);
         myProperties[64] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[65] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[66] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[67] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[68] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[69] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[70] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[71] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[72] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[73] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[74] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[75] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[76] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[77] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[78] = new PropertyDescriptor("InAllowEditCsfStringsString1", rootClass);
         myProperties[79] = new PropertyDescriptor("InMessageCsfStringsString500", rootClass);
         myProperties[80] = new PropertyDescriptor("InMaintainCsfStringsString40", rootClass);
         myProperties[81] = new PropertyDescriptor("InMaintainCsfStringsString50", rootClass);
         myProperties[82] = new IndexedPropertyDescriptor("OutGStudentDissertationPromoterMkPromotorNr", rootClass, null, null, "getOutGStudentDissertationPromoterMkPromotorNr", null);
         myProperties[83] = new IndexedPropertyDescriptor("OutGStudentDissertationPromoterSupervisorFlag", rootClass, null, null, "getOutGStudentDissertationPromoterSupervisorFlag", null);
         myProperties[84] = new IndexedPropertyDescriptor("OutGStudentDissertationPromoterMkDepartmentCode", rootClass, null, null, "getOutGStudentDissertationPromoterMkDepartmentCode", null);
         myProperties[85] = new IndexedPropertyDescriptor("OutGCsfStringsString100", rootClass, null, null, "getOutGCsfStringsString100", null);
         myProperties[86] = new IndexedPropertyDescriptor("OutGCsfStringsString28", rootClass, null, null, "getOutGCsfStringsString28", null);
         myProperties[87] = new PropertyDescriptor("OutStudentDissertationPromoterMkPromotorNr", rootClass, "getOutStudentDissertationPromoterMkPromotorNr", null);
         myProperties[88] = new PropertyDescriptor("OutStudentDissertationPromoterSupervisorFlag", rootClass, "getOutStudentDissertationPromoterSupervisorFlag", null);
         myProperties[89] = new PropertyDescriptor("OutStudentDissertationPromoterType", rootClass, "getOutStudentDissertationPromoterType", null);
         myProperties[90] = new PropertyDescriptor("OutPromotorNameCsfStringsString50", rootClass, "getOutPromotorNameCsfStringsString50", null);
         myProperties[91] = new PropertyDescriptor("OutWsStaffPersno", rootClass, "getOutWsStaffPersno", null);
         myProperties[92] = new PropertyDescriptor("OutStaffNameCsfStringsString50", rootClass, "getOutStaffNameCsfStringsString50", null);
         myProperties[93] = new PropertyDescriptor("OutMaintainMdActivityActivityCode", rootClass, "getOutMaintainMdActivityActivityCode", null);
         myProperties[94] = new PropertyDescriptor("OutMaintainMdActivityActivityDate", rootClass, "getOutMaintainMdActivityActivityDate", null);
         myProperties[95] = new PropertyDescriptor("OutMaintainMdActivityFeedbackDate", rootClass, "getOutMaintainMdActivityFeedbackDate", null);
         myProperties[96] = new PropertyDescriptor("OutMaintainMdActivityComments", rootClass, "getOutMaintainMdActivityComments", null);
         myProperties[97] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarAction", rootClass, null, null, "getOutGrCsfLineActionBarAction", null);
         myProperties[98] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGrCsfLineActionBarSelectionFlag", null);
         myProperties[99] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGrCsfLineActionBarTranslatedAction", null);
         myProperties[100] = new IndexedPropertyDescriptor("OutGrMdActivityStaffNumber", rootClass, null, null, "getOutGrMdActivityStaffNumber", null);
         myProperties[101] = new IndexedPropertyDescriptor("OutGrMdActivityActivityCode", rootClass, null, null, "getOutGrMdActivityActivityCode", null);
         myProperties[102] = new IndexedPropertyDescriptor("OutGrMdActivityEntryTimestamp", rootClass, null, null, "getOutGrMdActivityEntryTimestamp", null);
         myProperties[103] = new IndexedPropertyDescriptor("OutGrMdActivityActivityDate", rootClass, null, null, "getOutGrMdActivityActivityDate", null);
         myProperties[104] = new IndexedPropertyDescriptor("OutGrMdActivityFeedbackDate", rootClass, null, null, "getOutGrMdActivityFeedbackDate", null);
         myProperties[105] = new IndexedPropertyDescriptor("OutGrMdActivityComments", rootClass, null, null, "getOutGrMdActivityComments", null);
         myProperties[106] = new IndexedPropertyDescriptor("OutGrDescriptionsCsfStringsString40", rootClass, null, null, "getOutGrDescriptionsCsfStringsString40", null);
         myProperties[107] = new IndexedPropertyDescriptor("OutGrDescriptionsCsfStringsString50", rootClass, null, null, "getOutGrDescriptionsCsfStringsString50", null);
         myProperties[108] = new PropertyDescriptor("OutTypeDescriptionCsfStringsString30", rootClass, "getOutTypeDescriptionCsfStringsString30", null);
         myProperties[109] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[110] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[111] = new PropertyDescriptor("OutWsStudentPostGraduateStudiesApproved", rootClass, "getOutWsStudentPostGraduateStudiesApproved", null);
         myProperties[112] = new PropertyDescriptor("OutWsStudentAnnualRecordStatusCode", rootClass, "getOutWsStudentAnnualRecordStatusCode", null);
         myProperties[113] = new PropertyDescriptor("OutWsStudentAnnualRecordMkHighestQualCode", rootClass, "getOutWsStudentAnnualRecordMkHighestQualCode", null);
         myProperties[114] = new PropertyDescriptor("OutStudentNameCsfStringsString100", rootClass, "getOutStudentNameCsfStringsString100", null);
         myProperties[115] = new PropertyDescriptor("OutWsQualificationShortDescription", rootClass, "getOutWsQualificationShortDescription", null);
         myProperties[116] = new PropertyDescriptor("OutStudentDissertationMkStudentNr", rootClass, "getOutStudentDissertationMkStudentNr", null);
         myProperties[117] = new PropertyDescriptor("OutStudentDissertationMkStudyUnitCode", rootClass, "getOutStudentDissertationMkStudyUnitCode", null);
         myProperties[118] = new PropertyDescriptor("OutStudentDissertationType", rootClass, "getOutStudentDissertationType", null);
         myProperties[119] = new PropertyDescriptor("OutStudentDissertationTitle", rootClass, "getOutStudentDissertationTitle", null);
         myProperties[120] = new IndexedPropertyDescriptor("OutGEditableCsfStringsString1", rootClass, null, null, "getOutGEditableCsfStringsString1", null);
         myProperties[121] = new IndexedPropertyDescriptor("OutGEditableCsfStringsString100", rootClass, null, null, "getOutGEditableCsfStringsString100", null);
         myProperties[122] = new IndexedPropertyDescriptor("OutGEditableCsfStringsString3", rootClass, null, null, "getOutGEditableCsfStringsString3", null);
         myProperties[123] = new IndexedPropertyDescriptor("OutGEditableCsfStringsString500", rootClass, null, null, "getOutGEditableCsfStringsString500", null);
         myProperties[124] = new IndexedPropertyDescriptor("OutGWsStudentAnnualRecordMkAcademicYear", rootClass, null, null, "getOutGWsStudentAnnualRecordMkAcademicYear", null);
         myProperties[125] = new IndexedPropertyDescriptor("OutGWsQualificationCode", rootClass, null, null, "getOutGWsQualificationCode", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutGWsQualificationShortDescription", rootClass, null, null, "getOutGWsQualificationShortDescription", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGDissEntryIefSuppliedFlag", rootClass, null, null, "getOutGDissEntryIefSuppliedFlag", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGStatusCsfStringsString50", rootClass, null, null, "getOutGStatusCsfStringsString50", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutGWsStudyUnitCode", rootClass, null, null, "getOutGWsStudyUnitCode", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutGWsStudyUnitEngShortDescription", rootClass, null, null, "getOutGWsStudyUnitEngShortDescription", null);
         myProperties[131] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[132] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[133] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[134] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[135] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[136] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[137] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[138] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[139] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[140] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[141] = new PropertyDescriptor("OutSecurityWsUserNovellUserCode", rootClass, "getOutSecurityWsUserNovellUserCode", null);
         myProperties[142] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[143] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[144] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[145] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[146] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[147] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[148] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[149] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[150] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[151] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[152] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[153] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[154] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[155] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[156] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[157] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[158] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[159] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[161] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[162] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[163] = new PropertyDescriptor("OutCsfClientServerCommunicationsFirstpassFlag", rootClass, "getOutCsfClientServerCommunicationsFirstpassFlag", null);
         myProperties[164] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[165] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[166] = new PropertyDescriptor("OutAllowEditCsfStringsString1", rootClass, "getOutAllowEditCsfStringsString1", null);
         myProperties[167] = new PropertyDescriptor("OutMessageCsfStringsString500", rootClass, "getOutMessageCsfStringsString500", null);
         myProperties[168] = new PropertyDescriptor("OutMaintainCsfStringsString40", rootClass, "getOutMaintainCsfStringsString40", null);
         myProperties[169] = new PropertyDescriptor("OutMaintainCsfStringsString50", rootClass, "getOutMaintainCsfStringsString50", null);
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
//         callMethod = Srmas01sMDStudentActivity.class.getMethod("execute", args);
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
