package Staae01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Staae01sAppEnquireAdmissionBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Staae01sAppEnquireAdmission.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Staae01sAppEnquireAdmission.class);
      bd.setDisplayName(Staae01sAppEnquireAdmission.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsAcademicYearYear", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsQualificationCode", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsQualificationType", rootClass);
         myProperties[16].setPropertyEditorClass(Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor.class);
         myProperties[17] = new PropertyDescriptor("InWsQualificationLongEngDescription", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsQualificationSpecialityTypeMkQualificationCode", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsQualificationSpecialityTypeSpecialityCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsStudentIdentityNr", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsStudentBirthDate", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsStudentMkNationality", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsMatricStatusCode", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsMatricCertificateCode", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsMatricRecordCategory", rootClass);
         myProperties[31].setPropertyEditorClass(Staae01sAppEnquireAdmissionWsMatricRecordCategoryPropertyEditor.class);
         myProperties[32] = new PropertyDescriptor("InWsMatricRecordFullExemptionDate", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsMatricRecordExemptionEffectiveDate", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsMatricRecordExemptionExpiryDate", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsMatricRecordAuditedFlag", rootClass);
         myProperties[35].setPropertyEditorClass(Staae01sAppEnquireAdmissionWsMatricRecordAuditedFlagPropertyEditor.class);
         myProperties[36] = new PropertyDescriptor("InWsMatricRecordMkUserCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsMatricRecordSymbol", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsMatricRecordExamNumber", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsMatricRecordAggregate", rootClass);
         myProperties[40] = new PropertyDescriptor("InWsMatricRecordOrigExemptDate", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsMatricRecordSecEduComplete", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsMatricRecordMCount", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsMatricRecordComment0", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsMatricRecordHcerCompleted", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsMatricRecordDiplCompleted", rootClass);
         myProperties[46] = new PropertyDescriptor("InWsMatricRecordScienceFoundation", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsMatricRecordAlternativeRoute", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsMatricRecordAltComment1", rootClass);
         myProperties[49] = new PropertyDescriptor("InWsMatricRecordAltComment2", rootClass);
         myProperties[50] = new PropertyDescriptor("InWsMatricRecordApsScore", rootClass);
         myProperties[51] = new PropertyDescriptor("InWsMatricRecordAcsScore", rootClass);
         myProperties[52] = new IndexedPropertyDescriptor("InGCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGCsfLineActionBarSelectionFlag", "setInGCsfLineActionBarSelectionFlag");
         myProperties[53] = new IndexedPropertyDescriptor("InGCsfLineActionBarAction", rootClass, null, null, "getInGCsfLineActionBarAction", "setInGCsfLineActionBarAction");
         myProperties[54] = new IndexedPropertyDescriptor("InGWsUnisaCollegeCode", rootClass, null, null, "getInGWsUnisaCollegeCode", "setInGWsUnisaCollegeCode");
         myProperties[55] = new IndexedPropertyDescriptor("InGWsUnisaCollegeAbbreviation", rootClass, null, null, "getInGWsUnisaCollegeAbbreviation", "setInGWsUnisaCollegeAbbreviation");
         myProperties[56] = new IndexedPropertyDescriptor("InGWsQualificationCode", rootClass, null, null, "getInGWsQualificationCode", "setInGWsQualificationCode");
         myProperties[57] = new IndexedPropertyDescriptor("InGWsQualificationType", rootClass, null, null, "getInGWsQualificationType", "setInGWsQualificationType");
         myProperties[57].setPropertyEditorClass(Staae01sAppEnquireAdmissionWsQualificationTypePropertyEditor.class);
         myProperties[58] = new IndexedPropertyDescriptor("InGWsQualificationLongEngDescription", rootClass, null, null, "getInGWsQualificationLongEngDescription", "setInGWsQualificationLongEngDescription");
         myProperties[59] = new IndexedPropertyDescriptor("InGWsQualificationCollegeCode", rootClass, null, null, "getInGWsQualificationCollegeCode", "setInGWsQualificationCollegeCode");
         myProperties[60] = new IndexedPropertyDescriptor("InGWsQualificationSpecialityTypeSpecialityCode", rootClass, null, null, "getInGWsQualificationSpecialityTypeSpecialityCode", "setInGWsQualificationSpecialityTypeSpecialityCode");
         myProperties[61] = new IndexedPropertyDescriptor("InGCsfStringsString2", rootClass, null, null, "getInGCsfStringsString2", "setInGCsfStringsString2");
         myProperties[62] = new IndexedPropertyDescriptor("InGCsfStringsString3", rootClass, null, null, "getInGCsfStringsString3", "setInGCsfStringsString3");
         myProperties[63] = new IndexedPropertyDescriptor("InGCsfStringsString5", rootClass, null, null, "getInGCsfStringsString5", "setInGCsfStringsString5");
         myProperties[64] = new IndexedPropertyDescriptor("InGCsfStringsString60", rootClass, null, null, "getInGCsfStringsString60", "setInGCsfStringsString60");
         myProperties[65] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[66] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[67] = new IndexedPropertyDescriptor("InGmCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGmCsfLineActionBarSelectionFlag", "setInGmCsfLineActionBarSelectionFlag");
         myProperties[68] = new IndexedPropertyDescriptor("InGmWsMatricSubjectCode", rootClass, null, null, "getInGmWsMatricSubjectCode", "setInGmWsMatricSubjectCode");
         myProperties[69] = new IndexedPropertyDescriptor("InGmWsMatricSubjectEngDescription", rootClass, null, null, "getInGmWsMatricSubjectEngDescription", "setInGmWsMatricSubjectEngDescription");
         myProperties[70] = new IndexedPropertyDescriptor("InGmWsMatricSubjectResultGrade", rootClass, null, null, "getInGmWsMatricSubjectResultGrade", "setInGmWsMatricSubjectResultGrade");
         myProperties[70].setPropertyEditorClass(Staae01sAppEnquireAdmissionWsMatricSubjectResultGradePropertyEditor.class);
         myProperties[71] = new IndexedPropertyDescriptor("InGmWsMatricSubjectResultMark", rootClass, null, null, "getInGmWsMatricSubjectResultMark", "setInGmWsMatricSubjectResultMark");
         myProperties[72] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[73] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[74] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[75] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[76] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[77] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[78] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[79] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[80] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[81] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[82] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[83] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[84] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[85] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[86] = new PropertyDescriptor("InProddevCsfStringsString4", rootClass);
         myProperties[87] = new PropertyDescriptor("InCsfStringsString500", rootClass);
         myProperties[88] = new PropertyDescriptor("OutWsAcademicYearYear", rootClass, "getOutWsAcademicYearYear", null);
         myProperties[89] = new PropertyDescriptor("OutWsQualificationCode", rootClass, "getOutWsQualificationCode", null);
         myProperties[90] = new PropertyDescriptor("OutWsQualificationType", rootClass, "getOutWsQualificationType", null);
         myProperties[91] = new PropertyDescriptor("OutWsQualificationLongEngDescription", rootClass, "getOutWsQualificationLongEngDescription", null);
         myProperties[92] = new PropertyDescriptor("OutWsQualificationSpecialityTypeMkQualificationCode", rootClass, "getOutWsQualificationSpecialityTypeMkQualificationCode", null);
         myProperties[93] = new PropertyDescriptor("OutWsQualificationSpecialityTypeSpecialityCode", rootClass, "getOutWsQualificationSpecialityTypeSpecialityCode", null);
         myProperties[94] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[95] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[96] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[97] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[98] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[99] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[100] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[101] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[102] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[103] = new PropertyDescriptor("OutWsMatricStatusCode", rootClass, "getOutWsMatricStatusCode", null);
         myProperties[104] = new PropertyDescriptor("OutWsMatricCertificateCode", rootClass, "getOutWsMatricCertificateCode", null);
         myProperties[105] = new PropertyDescriptor("OutWsMatricRecordCategory", rootClass, "getOutWsMatricRecordCategory", null);
         myProperties[106] = new PropertyDescriptor("OutWsMatricRecordFullExemptionDate", rootClass, "getOutWsMatricRecordFullExemptionDate", null);
         myProperties[107] = new PropertyDescriptor("OutWsMatricRecordExemptionEffectiveDate", rootClass, "getOutWsMatricRecordExemptionEffectiveDate", null);
         myProperties[108] = new PropertyDescriptor("OutWsMatricRecordExemptionExpiryDate", rootClass, "getOutWsMatricRecordExemptionExpiryDate", null);
         myProperties[109] = new PropertyDescriptor("OutWsMatricRecordAuditedFlag", rootClass, "getOutWsMatricRecordAuditedFlag", null);
         myProperties[110] = new PropertyDescriptor("OutWsMatricRecordMkUserCode", rootClass, "getOutWsMatricRecordMkUserCode", null);
         myProperties[111] = new PropertyDescriptor("OutWsMatricRecordSymbol", rootClass, "getOutWsMatricRecordSymbol", null);
         myProperties[112] = new PropertyDescriptor("OutWsMatricRecordExamNumber", rootClass, "getOutWsMatricRecordExamNumber", null);
         myProperties[113] = new PropertyDescriptor("OutWsMatricRecordAggregate", rootClass, "getOutWsMatricRecordAggregate", null);
         myProperties[114] = new PropertyDescriptor("OutWsMatricRecordOrigExemptDate", rootClass, "getOutWsMatricRecordOrigExemptDate", null);
         myProperties[115] = new PropertyDescriptor("OutWsMatricRecordSecEduComplete", rootClass, "getOutWsMatricRecordSecEduComplete", null);
         myProperties[116] = new PropertyDescriptor("OutWsMatricRecordMCount", rootClass, "getOutWsMatricRecordMCount", null);
         myProperties[117] = new PropertyDescriptor("OutWsMatricRecordComment0", rootClass, "getOutWsMatricRecordComment0", null);
         myProperties[118] = new PropertyDescriptor("OutWsMatricRecordHcerCompleted", rootClass, "getOutWsMatricRecordHcerCompleted", null);
         myProperties[119] = new PropertyDescriptor("OutWsMatricRecordDiplCompleted", rootClass, "getOutWsMatricRecordDiplCompleted", null);
         myProperties[120] = new PropertyDescriptor("OutWsMatricRecordScienceFoundation", rootClass, "getOutWsMatricRecordScienceFoundation", null);
         myProperties[121] = new PropertyDescriptor("OutWsMatricRecordAlternativeRoute", rootClass, "getOutWsMatricRecordAlternativeRoute", null);
         myProperties[122] = new PropertyDescriptor("OutWsMatricRecordAltComment1", rootClass, "getOutWsMatricRecordAltComment1", null);
         myProperties[123] = new PropertyDescriptor("OutWsMatricRecordAltComment2", rootClass, "getOutWsMatricRecordAltComment2", null);
         myProperties[124] = new PropertyDescriptor("OutWsMatricRecordApsScore", rootClass, "getOutWsMatricRecordApsScore", null);
         myProperties[125] = new PropertyDescriptor("OutWsMatricRecordAcsScore", rootClass, "getOutWsMatricRecordAcsScore", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGWsUnisaCollegeCode", rootClass, null, null, "getOutGWsUnisaCollegeCode", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutGWsUnisaCollegeAbbreviation", rootClass, null, null, "getOutGWsUnisaCollegeAbbreviation", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutGWsQualificationCode", rootClass, null, null, "getOutGWsQualificationCode", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutGWsQualificationType", rootClass, null, null, "getOutGWsQualificationType", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutGWsQualificationLongEngDescription", rootClass, null, null, "getOutGWsQualificationLongEngDescription", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutGWsQualificationCollegeCode", rootClass, null, null, "getOutGWsQualificationCollegeCode", null);
         myProperties[134] = new IndexedPropertyDescriptor("OutGWsQualificationSpecialityTypeSpecialityCode", rootClass, null, null, "getOutGWsQualificationSpecialityTypeSpecialityCode", null);
         myProperties[135] = new IndexedPropertyDescriptor("OutGCsfStringsString2", rootClass, null, null, "getOutGCsfStringsString2", null);
         myProperties[136] = new IndexedPropertyDescriptor("OutGCsfStringsString3", rootClass, null, null, "getOutGCsfStringsString3", null);
         myProperties[137] = new IndexedPropertyDescriptor("OutGCsfStringsString5", rootClass, null, null, "getOutGCsfStringsString5", null);
         myProperties[138] = new IndexedPropertyDescriptor("OutGCsfStringsString60", rootClass, null, null, "getOutGCsfStringsString60", null);
         myProperties[139] = new PropertyDescriptor("OutWsPrinterCode", rootClass, "getOutWsPrinterCode", null);
         myProperties[140] = new PropertyDescriptor("OutWsFunctionNumber", rootClass, "getOutWsFunctionNumber", null);
         myProperties[141] = new IndexedPropertyDescriptor("OutSgCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutSgCsfLineActionBarSelectionFlag", null);
         myProperties[142] = new IndexedPropertyDescriptor("OutSgCsfStringsString60", rootClass, null, null, "getOutSgCsfStringsString60", null);
         myProperties[143] = new IndexedPropertyDescriptor("OutGmCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGmCsfLineActionBarSelectionFlag", null);
         myProperties[144] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectCode", rootClass, null, null, "getOutGmWsMatricSubjectCode", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectEngDescription", rootClass, null, null, "getOutGmWsMatricSubjectEngDescription", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectResultGrade", rootClass, null, null, "getOutGmWsMatricSubjectResultGrade", null);
         myProperties[147] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectResultMark", rootClass, null, null, "getOutGmWsMatricSubjectResultMark", null);
         myProperties[148] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[149] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[150] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[151] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[152] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[153] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[154] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[155] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[156] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[157] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[158] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[159] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[161] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[162] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[163] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[164] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[165] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[166] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[167] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[168] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[169] = new PropertyDescriptor("OutProddevCsfStringsString4", rootClass, "getOutProddevCsfStringsString4", null);
         myProperties[170] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
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
//         callMethod = Staae01sAppEnquireAdmission.class.getMethod("execute", args);
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
