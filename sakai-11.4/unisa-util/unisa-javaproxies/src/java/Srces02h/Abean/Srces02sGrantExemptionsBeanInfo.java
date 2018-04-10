package Srces02h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srces02sGrantExemptionsBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srces02sGrantExemptions.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srces02sGrantExemptions.class);
      bd.setDisplayName(Srces02sGrantExemptions.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InCanUpdateCsfStringsString1", rootClass);
         myProperties[15] = new PropertyDescriptor("InFaxOrEmailCsfStringsString1", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[17] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsStudentFirstNames", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsStudentBirthDate", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsStudentNumberRestrictedFlag", rootClass);
         myProperties[20].setPropertyEditorClass(Srces02sGrantExemptionsWsStudentNumberRestrictedFlagPropertyEditor.class);
         myProperties[21] = new PropertyDescriptor("InStudentNameCsfStringsString100", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsQualificationCode", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsQualificationShortDescription", rootClass);
         myProperties[24] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[25] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[26] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[27] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[28] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[29] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[30] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[31] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[32] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[33] = new PropertyDescriptor("InSecurityWsUserNovellUserCode", rootClass);
         myProperties[34] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[48] = new IndexedPropertyDescriptor("InGroCsfLineActionBarAction", rootClass, null, null, "getInGroCsfLineActionBarAction", "setInGroCsfLineActionBarAction");
         myProperties[49] = new IndexedPropertyDescriptor("InGroCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGroCsfLineActionBarSelectionFlag", "setInGroCsfLineActionBarSelectionFlag");
         myProperties[50] = new IndexedPropertyDescriptor("InGroCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGroCsfLineActionBarTranslatedAction", "setInGroCsfLineActionBarTranslatedAction");
         myProperties[51] = new IndexedPropertyDescriptor("InGroStudentAcadrecOtherMkEducationalInst", rootClass, null, null, "getInGroStudentAcadrecOtherMkEducationalInst", "setInGroStudentAcadrecOtherMkEducationalInst");
         myProperties[52] = new IndexedPropertyDescriptor("InGroStudentAcadrecOtherStudyUnitCode", rootClass, null, null, "getInGroStudentAcadrecOtherStudyUnitCode", "setInGroStudentAcadrecOtherStudyUnitCode");
         myProperties[53] = new IndexedPropertyDescriptor("InGroStudentAcadrecOtherAcademicYear", rootClass, null, null, "getInGroStudentAcadrecOtherAcademicYear", "setInGroStudentAcadrecOtherAcademicYear");
         myProperties[54] = new IndexedPropertyDescriptor("InGroStudentAcadrecOtherMark", rootClass, null, null, "getInGroStudentAcadrecOtherMark", "setInGroStudentAcadrecOtherMark");
         myProperties[55] = new IndexedPropertyDescriptor("InGroWsEducationalInstitutionEngName", rootClass, null, null, "getInGroWsEducationalInstitutionEngName", "setInGroWsEducationalInstitutionEngName");
         myProperties[56] = new IndexedPropertyDescriptor("InGrCsfLineActionBarAction", rootClass, null, null, "getInGrCsfLineActionBarAction", "setInGrCsfLineActionBarAction");
         myProperties[57] = new IndexedPropertyDescriptor("InGrCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGrCsfLineActionBarSelectionFlag", "setInGrCsfLineActionBarSelectionFlag");
         myProperties[58] = new IndexedPropertyDescriptor("InGrCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGrCsfLineActionBarTranslatedAction", "setInGrCsfLineActionBarTranslatedAction");
         myProperties[59] = new IndexedPropertyDescriptor("InGrUnisaWsStudyUnitCode", rootClass, null, null, "getInGrUnisaWsStudyUnitCode", "setInGrUnisaWsStudyUnitCode");
         myProperties[60] = new IndexedPropertyDescriptor("InGrCsfStringsString1", rootClass, null, null, "getInGrCsfStringsString1", "setInGrCsfStringsString1");
         myProperties[61] = new IndexedPropertyDescriptor("InGrCsfStringsString20", rootClass, null, null, "getInGrCsfStringsString20", "setInGrCsfStringsString20");
         myProperties[62] = new IndexedPropertyDescriptor("InGrPreviousExemptionCsfStringsString1", rootClass, null, null, "getInGrPreviousExemptionCsfStringsString1", "setInGrPreviousExemptionCsfStringsString1");
         myProperties[63] = new IndexedPropertyDescriptor("InGrValidCombinationIefSuppliedCount", rootClass, null, null, "getInGrValidCombinationIefSuppliedCount", "setInGrValidCombinationIefSuppliedCount");
         myProperties[64] = new IndexedPropertyDescriptor("InGrWsCombinationDescriptionDescription", rootClass, null, null, "getInGrWsCombinationDescriptionDescription", "setInGrWsCombinationDescriptionDescription");
         myProperties[65] = new IndexedPropertyDescriptor("InGrWsEducationalInstitutionCode", rootClass, null, null, "getInGrWsEducationalInstitutionCode", "setInGrWsEducationalInstitutionCode");
         myProperties[66] = new PropertyDescriptor("InQualificationSpecialityTypeSpecialityCode", rootClass);
         myProperties[67] = new PropertyDescriptor("InQualificationSpecialityTypeEnglishDescription", rootClass);
         myProperties[68] = new PropertyDescriptor("InMaj1StudentQualificationMajorMkMajorSubjectCode", rootClass);
         myProperties[69] = new PropertyDescriptor("InMaj2StudentQualificationMajorMkMajorSubjectCode", rootClass);
         myProperties[70] = new PropertyDescriptor("InEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", rootClass);
         myProperties[71] = new PropertyDescriptor("InEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", rootClass);
         myProperties[72] = new PropertyDescriptor("InWsMatricCertificateCode", rootClass);
         myProperties[73] = new PropertyDescriptor("InWsMatricCertificateEngDescription", rootClass);
         myProperties[74] = new PropertyDescriptor("InWsMatricCertificateAfrDescription", rootClass);
         myProperties[75] = new PropertyDescriptor("InWsMatricRecordCategory", rootClass);
         myProperties[75].setPropertyEditorClass(Srces02sGrantExemptionsWsMatricRecordCategoryPropertyEditor.class);
         myProperties[76] = new PropertyDescriptor("InWsMatricRecordFullExemptionDate", rootClass);
         myProperties[77] = new PropertyDescriptor("InWsMatricRecordExemptionEffectiveDate", rootClass);
         myProperties[78] = new PropertyDescriptor("InWsMatricRecordExemptionExpiryDate", rootClass);
         myProperties[79] = new PropertyDescriptor("InWsMatricRecordAuditedFlag", rootClass);
         myProperties[79].setPropertyEditorClass(Srces02sGrantExemptionsWsMatricRecordAuditedFlagPropertyEditor.class);
         myProperties[80] = new PropertyDescriptor("InWsMatricRecordMkUserCode", rootClass);
         myProperties[81] = new PropertyDescriptor("InWsMatricRecordSymbol", rootClass);
         myProperties[82] = new PropertyDescriptor("InWsMatricRecordOrigExemptDate", rootClass);
         myProperties[83] = new PropertyDescriptor("InWsMatricStatusCode", rootClass);
         myProperties[84] = new PropertyDescriptor("InWsMatricStatusNumber", rootClass);
         myProperties[85] = new PropertyDescriptor("InWsMatricStatusAfrDescription", rootClass);
         myProperties[86] = new PropertyDescriptor("InWsMatricStatusEngDescription", rootClass);
         myProperties[87] = new PropertyDescriptor("InWsMatricStatusDateType", rootClass);
         myProperties[88] = new IndexedPropertyDescriptor("InGWsMatricSubjectResultGrade", rootClass, null, null, "getInGWsMatricSubjectResultGrade", "setInGWsMatricSubjectResultGrade");
         myProperties[88].setPropertyEditorClass(Srces02sGrantExemptionsWsMatricSubjectResultGradePropertyEditor.class);
         myProperties[89] = new IndexedPropertyDescriptor("InGWsMatricSubjectResultMark", rootClass, null, null, "getInGWsMatricSubjectResultMark", "setInGWsMatricSubjectResultMark");
         myProperties[90] = new IndexedPropertyDescriptor("InGWsMatricSubjectResultBorderMark", rootClass, null, null, "getInGWsMatricSubjectResultBorderMark", "setInGWsMatricSubjectResultBorderMark");
         myProperties[91] = new IndexedPropertyDescriptor("InGWsMatricSubjectCode", rootClass, null, null, "getInGWsMatricSubjectCode", "setInGWsMatricSubjectCode");
         myProperties[92] = new PropertyDescriptor("InPermExemptAllowedIefSuppliedFlag", rootClass);
         myProperties[92].setPropertyEditorClass(Srces02sGrantExemptionsIefSuppliedFlagPropertyEditor.class);
         myProperties[93] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[94] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[95] = new PropertyDescriptor("InFaxNrCsfStringsString132", rootClass);
         myProperties[96] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[97] = new PropertyDescriptor("InPrintPermsCsfStringsString1", rootClass);
         myProperties[98] = new PropertyDescriptor("InPrintTempsCsfStringsString1", rootClass);
         myProperties[99] = new PropertyDescriptor("OutCanUpdateCsfStringsString1", rootClass, "getOutCanUpdateCsfStringsString1", null);
         myProperties[100] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[101] = new PropertyDescriptor("OutFaxOrEmailCsfStringsString1", rootClass, "getOutFaxOrEmailCsfStringsString1", null);
         myProperties[102] = new PropertyDescriptor("OutPermExemptAllowedIefSuppliedFlag", rootClass, "getOutPermExemptAllowedIefSuppliedFlag", null);
         myProperties[103] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[104] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[105] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[106] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[107] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[108] = new PropertyDescriptor("OutStudentNameCsfStringsString100", rootClass, "getOutStudentNameCsfStringsString100", null);
         myProperties[109] = new PropertyDescriptor("OutWsQualificationCode", rootClass, "getOutWsQualificationCode", null);
         myProperties[110] = new PropertyDescriptor("OutWsQualificationShortDescription", rootClass, "getOutWsQualificationShortDescription", null);
         myProperties[111] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[112] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[113] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[114] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[115] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[116] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[117] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[118] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[119] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[120] = new PropertyDescriptor("OutSecurityWsUserNovellUserCode", rootClass, "getOutSecurityWsUserNovellUserCode", null);
         myProperties[121] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[122] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[123] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[124] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[125] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[126] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[127] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[128] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[129] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[130] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[131] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[132] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[133] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[134] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[135] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[136] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[137] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[138] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[139] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[140] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[141] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[142] = new PropertyDescriptor("OutCsfClientServerCommunicationsFirstpassFlag", rootClass, "getOutCsfClientServerCommunicationsFirstpassFlag", null);
         myProperties[143] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[144] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGroCsfLineActionBarAction", rootClass, null, null, "getOutGroCsfLineActionBarAction", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGroCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGroCsfLineActionBarSelectionFlag", null);
         myProperties[147] = new IndexedPropertyDescriptor("OutGroCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGroCsfLineActionBarTranslatedAction", null);
         myProperties[148] = new IndexedPropertyDescriptor("OutGroStudentAcadrecOtherMkEducationalInst", rootClass, null, null, "getOutGroStudentAcadrecOtherMkEducationalInst", null);
         myProperties[149] = new IndexedPropertyDescriptor("OutGroStudentAcadrecOtherStudyUnitCode", rootClass, null, null, "getOutGroStudentAcadrecOtherStudyUnitCode", null);
         myProperties[150] = new IndexedPropertyDescriptor("OutGroStudentAcadrecOtherAcademicYear", rootClass, null, null, "getOutGroStudentAcadrecOtherAcademicYear", null);
         myProperties[151] = new IndexedPropertyDescriptor("OutGroStudentAcadrecOtherMark", rootClass, null, null, "getOutGroStudentAcadrecOtherMark", null);
         myProperties[152] = new IndexedPropertyDescriptor("OutGroWsEducationalInstitutionEngName", rootClass, null, null, "getOutGroWsEducationalInstitutionEngName", null);
         myProperties[153] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarAction", rootClass, null, null, "getOutGrCsfLineActionBarAction", null);
         myProperties[154] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGrCsfLineActionBarSelectionFlag", null);
         myProperties[155] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGrCsfLineActionBarTranslatedAction", null);
         myProperties[156] = new IndexedPropertyDescriptor("OutGrCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGrCsfLineActionBarLineReturnCode", null);
         myProperties[157] = new IndexedPropertyDescriptor("OutGrUnisaWsStudyUnitCode", rootClass, null, null, "getOutGrUnisaWsStudyUnitCode", null);
         myProperties[158] = new IndexedPropertyDescriptor("OutGrCsfStringsString1", rootClass, null, null, "getOutGrCsfStringsString1", null);
         myProperties[159] = new IndexedPropertyDescriptor("OutGrCsfStringsString20", rootClass, null, null, "getOutGrCsfStringsString20", null);
         myProperties[160] = new IndexedPropertyDescriptor("OutGrPreviousExemptionCsfStringsString1", rootClass, null, null, "getOutGrPreviousExemptionCsfStringsString1", null);
         myProperties[161] = new IndexedPropertyDescriptor("OutGrValidCombinationIefSuppliedCount", rootClass, null, null, "getOutGrValidCombinationIefSuppliedCount", null);
         myProperties[162] = new IndexedPropertyDescriptor("OutGrWsCombinationDescriptionDescription", rootClass, null, null, "getOutGrWsCombinationDescriptionDescription", null);
         myProperties[163] = new IndexedPropertyDescriptor("OutGrWsEducationalInstitutionCode", rootClass, null, null, "getOutGrWsEducationalInstitutionCode", null);
         myProperties[164] = new PropertyDescriptor("OutQualificationSpecialityTypeSpecialityCode", rootClass, "getOutQualificationSpecialityTypeSpecialityCode", null);
         myProperties[165] = new PropertyDescriptor("OutQualificationSpecialityTypeEnglishDescription", rootClass, "getOutQualificationSpecialityTypeEnglishDescription", null);
         myProperties[166] = new PropertyDescriptor("OutMaj1StudentQualificationMajorMkMajorSubjectCode", rootClass, "getOutMaj1StudentQualificationMajorMkMajorSubjectCode", null);
         myProperties[167] = new PropertyDescriptor("OutMaj2StudentQualificationMajorMkMajorSubjectCode", rootClass, "getOutMaj2StudentQualificationMajorMkMajorSubjectCode", null);
         myProperties[168] = new PropertyDescriptor("OutEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", rootClass, "getOutEarliestExemptionStudentAcademicRecordEarliestExemptionUnisaYear", null);
         myProperties[169] = new PropertyDescriptor("OutEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", rootClass, "getOutEarliestExemptionStudentAcademicRecordEarliestExemptionOtherYear", null);
         myProperties[170] = new PropertyDescriptor("OutWsMatricCertificateCode", rootClass, "getOutWsMatricCertificateCode", null);
         myProperties[171] = new PropertyDescriptor("OutWsMatricCertificateEngDescription", rootClass, "getOutWsMatricCertificateEngDescription", null);
         myProperties[172] = new PropertyDescriptor("OutWsMatricCertificateAfrDescription", rootClass, "getOutWsMatricCertificateAfrDescription", null);
         myProperties[173] = new PropertyDescriptor("OutWsMatricRecordCategory", rootClass, "getOutWsMatricRecordCategory", null);
         myProperties[174] = new PropertyDescriptor("OutWsMatricRecordFullExemptionDate", rootClass, "getOutWsMatricRecordFullExemptionDate", null);
         myProperties[175] = new PropertyDescriptor("OutWsMatricRecordExemptionEffectiveDate", rootClass, "getOutWsMatricRecordExemptionEffectiveDate", null);
         myProperties[176] = new PropertyDescriptor("OutWsMatricRecordExemptionExpiryDate", rootClass, "getOutWsMatricRecordExemptionExpiryDate", null);
         myProperties[177] = new PropertyDescriptor("OutWsMatricRecordAuditedFlag", rootClass, "getOutWsMatricRecordAuditedFlag", null);
         myProperties[178] = new PropertyDescriptor("OutWsMatricRecordMkUserCode", rootClass, "getOutWsMatricRecordMkUserCode", null);
         myProperties[179] = new PropertyDescriptor("OutWsMatricRecordSymbol", rootClass, "getOutWsMatricRecordSymbol", null);
         myProperties[180] = new PropertyDescriptor("OutWsMatricRecordOrigExemptDate", rootClass, "getOutWsMatricRecordOrigExemptDate", null);
         myProperties[181] = new PropertyDescriptor("OutWsMatricStatusCode", rootClass, "getOutWsMatricStatusCode", null);
         myProperties[182] = new PropertyDescriptor("OutWsMatricStatusNumber", rootClass, "getOutWsMatricStatusNumber", null);
         myProperties[183] = new PropertyDescriptor("OutWsMatricStatusAfrDescription", rootClass, "getOutWsMatricStatusAfrDescription", null);
         myProperties[184] = new PropertyDescriptor("OutWsMatricStatusEngDescription", rootClass, "getOutWsMatricStatusEngDescription", null);
         myProperties[185] = new PropertyDescriptor("OutWsMatricStatusDateType", rootClass, "getOutWsMatricStatusDateType", null);
         myProperties[186] = new IndexedPropertyDescriptor("OutGWsMatricSubjectResultGrade", rootClass, null, null, "getOutGWsMatricSubjectResultGrade", null);
         myProperties[187] = new IndexedPropertyDescriptor("OutGWsMatricSubjectResultMark", rootClass, null, null, "getOutGWsMatricSubjectResultMark", null);
         myProperties[188] = new IndexedPropertyDescriptor("OutGWsMatricSubjectResultBorderMark", rootClass, null, null, "getOutGWsMatricSubjectResultBorderMark", null);
         myProperties[189] = new IndexedPropertyDescriptor("OutGWsMatricSubjectCode", rootClass, null, null, "getOutGWsMatricSubjectCode", null);
         myProperties[190] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[191] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
         myProperties[192] = new PropertyDescriptor("OutFaxNrCsfStringsString132", rootClass, "getOutFaxNrCsfStringsString132", null);
         myProperties[193] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[194] = new PropertyDescriptor("OutPrintPermsCsfStringsString1", rootClass, "getOutPrintPermsCsfStringsString1", null);
         myProperties[195] = new PropertyDescriptor("OutPrintTempsCsfStringsString1", rootClass, "getOutPrintTempsCsfStringsString1", null);
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
//         callMethod = Srces02sGrantExemptions.class.getMethod("execute", args);
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
