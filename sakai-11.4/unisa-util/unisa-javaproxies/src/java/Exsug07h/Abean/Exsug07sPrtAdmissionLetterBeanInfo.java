package Exsug07h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Exsug07sPrtAdmissionLetterBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Exsug07sPrtAdmissionLetter.class;
 
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
      null,   // 210
      null,   // 211
      null,   // 212
      null,   // 213
      null,   // 214
      null,   // 215
      null,   // 216
      null,   // 217
      null,   // 218
      null,   // 219
      null,   // 220
      null,   // 221
      null,   // 222
      null,   // 223
      null,   // 224
      null,   // 225
      null,   // 226
      null,   // 227
      null,   // 228
      null,   // 229
      null,   // 230
      null,   // 231
      null,   // 232
      null,   // 233
      null,   // 234
      null,   // 235
      null,   // 236
      null,   // 237
      null,   // 238
      null,   // 239
      null,   // 240
      null,   // 241
      null,   // 242
      null,   // 243
      null,   // 244
      null,   // 245
      null,   // 246
      null,   // 247
      null,   // 248
      null,   // 249
      null,   // 250
      null,   // 251
      null,   // 252
      null,   // 253
      null,   // 254
      null,   // 255
      null,   // 256
      null,   // 257
      null,   // 258
      null,   // 259
      null,   // 260
      null,   // 261
      null,   // 262
      null,   // 263
      null,   // 264
      null,   // 265
      null,   // 266
      null,   // 267
      null,   // 268
      null,   // 269
      null,   // 270
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Exsug07sPrtAdmissionLetter.class);
      bd.setDisplayName(Exsug07sPrtAdmissionLetter.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InInternetFlagCsfStringsString1", rootClass);
         myProperties[15] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[16] = new PropertyDescriptor("InFaxNoCsfStringsString132", rootClass);
         myProperties[17] = new PropertyDescriptor("InToEmailAddressCsfStringsString132", rootClass);
         myProperties[18] = new PropertyDescriptor("InFromEmailAddressCsfStringsString132", rootClass);
         myProperties[19] = new PropertyDescriptor("InSecurityWsWorkstationCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InPracticalFlagWsPracticalsValuesString1", rootClass);
         myProperties[20].setPropertyEditorClass(Exsug07sPrtAdmissionLetterWsPracticalsValuesString1PropertyEditor.class);
         myProperties[21] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[22] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[23] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[24] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[25] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[26] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[27] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[28] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[29] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[34] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[35] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[37] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[38] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[39] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[40] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[41] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[42] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[43] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[46] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[47] = new PropertyDescriptor("InStudentStudyUnitExamYear", rootClass);
         myProperties[48] = new PropertyDescriptor("InStudentStudyUnitMkExamPeriod", rootClass);
         myProperties[49] = new PropertyDescriptor("InComment1CsfStringsString100", rootClass);
         myProperties[50] = new PropertyDescriptor("InComment2CsfStringsString100", rootClass);
         myProperties[51] = new PropertyDescriptor("InComment3CsfStringsString100", rootClass);
         myProperties[52] = new PropertyDescriptor("InComment4CsfStringsString100", rootClass);
         myProperties[53] = new PropertyDescriptor("InComment5CsfStringsString100", rootClass);
         myProperties[54] = new PropertyDescriptor("InComment6CsfStringsString100", rootClass);
         myProperties[55] = new PropertyDescriptor("InComment7CsfStringsString100", rootClass);
         myProperties[56] = new PropertyDescriptor("InComment8CsfStringsString100", rootClass);
         myProperties[57] = new PropertyDescriptor("InComment9CsfStringsString100", rootClass);
         myProperties[58] = new PropertyDescriptor("InComment10CsfStringsString100", rootClass);
         myProperties[59] = new PropertyDescriptor("InComment11CsfStringsString100", rootClass);
         myProperties[60] = new PropertyDescriptor("InComment12CsfStringsString100", rootClass);
         myProperties[61] = new PropertyDescriptor("InInvigilatorAddressCsfStringsString1", rootClass);
         myProperties[62] = new PropertyDescriptor("OutContactWsAddressV2ReferenceNo", rootClass, "getOutContactWsAddressV2ReferenceNo", null);
         myProperties[63] = new PropertyDescriptor("OutContactWsAddressV2AddressReferenceFlag", rootClass, "getOutContactWsAddressV2AddressReferenceFlag", null);
         myProperties[64] = new PropertyDescriptor("OutContactWsAddressV2Type", rootClass, "getOutContactWsAddressV2Type", null);
         myProperties[65] = new PropertyDescriptor("OutContactWsAddressV2Category", rootClass, "getOutContactWsAddressV2Category", null);
         myProperties[66] = new PropertyDescriptor("OutContactWsAddressV2CategoryDescription", rootClass, "getOutContactWsAddressV2CategoryDescription", null);
         myProperties[67] = new PropertyDescriptor("OutContactWsAddressV2AddressLine1", rootClass, "getOutContactWsAddressV2AddressLine1", null);
         myProperties[68] = new PropertyDescriptor("OutContactWsAddressV2AddressLine2", rootClass, "getOutContactWsAddressV2AddressLine2", null);
         myProperties[69] = new PropertyDescriptor("OutContactWsAddressV2AddressLine3", rootClass, "getOutContactWsAddressV2AddressLine3", null);
         myProperties[70] = new PropertyDescriptor("OutContactWsAddressV2AddressLine4", rootClass, "getOutContactWsAddressV2AddressLine4", null);
         myProperties[71] = new PropertyDescriptor("OutContactWsAddressV2AddressLine5", rootClass, "getOutContactWsAddressV2AddressLine5", null);
         myProperties[72] = new PropertyDescriptor("OutContactWsAddressV2AddressLine6", rootClass, "getOutContactWsAddressV2AddressLine6", null);
         myProperties[73] = new PropertyDescriptor("OutContactWsAddressV2PostalCode", rootClass, "getOutContactWsAddressV2PostalCode", null);
         myProperties[74] = new PropertyDescriptor("OutContactWsAddressV2HomeNumber", rootClass, "getOutContactWsAddressV2HomeNumber", null);
         myProperties[75] = new PropertyDescriptor("OutContactWsAddressV2WorkNumber", rootClass, "getOutContactWsAddressV2WorkNumber", null);
         myProperties[76] = new PropertyDescriptor("OutContactWsAddressV2FaxNumber", rootClass, "getOutContactWsAddressV2FaxNumber", null);
         myProperties[77] = new PropertyDescriptor("OutContactWsAddressV2CellNumber", rootClass, "getOutContactWsAddressV2CellNumber", null);
         myProperties[78] = new PropertyDescriptor("OutContactWsAddressV2EmailAddress", rootClass, "getOutContactWsAddressV2EmailAddress", null);
         myProperties[79] = new PropertyDescriptor("OutContactWsAddressV2ErrorCode", rootClass, "getOutContactWsAddressV2ErrorCode", null);
         myProperties[80] = new PropertyDescriptor("OutContactWsAddressV2UserNumber", rootClass, "getOutContactWsAddressV2UserNumber", null);
         myProperties[81] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[82] = new PropertyDescriptor("OutEmailOrFaxCsfStringsString1", rootClass, "getOutEmailOrFaxCsfStringsString1", null);
         myProperties[83] = new PropertyDescriptor("OutFaxNoCsfStringsString132", rootClass, "getOutFaxNoCsfStringsString132", null);
         myProperties[84] = new PropertyDescriptor("OutSubjectCsfStringsString132", rootClass, "getOutSubjectCsfStringsString132", null);
         myProperties[85] = new PropertyDescriptor("OutEmailWizfuncReportingControlPathAndFilename", rootClass, "getOutEmailWizfuncReportingControlPathAndFilename", null);
         myProperties[86] = new PropertyDescriptor("OutToEmailAddressCsfStringsString132", rootClass, "getOutToEmailAddressCsfStringsString132", null);
         myProperties[87] = new PropertyDescriptor("OutFromEmailAddressCsfStringsString132", rootClass, "getOutFromEmailAddressCsfStringsString132", null);
         myProperties[88] = new PropertyDescriptor("OutSecurityWsWorkstationCode", rootClass, "getOutSecurityWsWorkstationCode", null);
         myProperties[89] = new PropertyDescriptor("OutPrelimOrFinalTimetableCsfStringsString1", rootClass, "getOutPrelimOrFinalTimetableCsfStringsString1", null);
         myProperties[90] = new PropertyDescriptor("OutPrelimOrFinalTimetableCsfStringsString40", rootClass, "getOutPrelimOrFinalTimetableCsfStringsString40", null);
         myProperties[91] = new PropertyDescriptor("OutWsDepartmentCode", rootClass, "getOutWsDepartmentCode", null);
         myProperties[92] = new PropertyDescriptor("OutWsDepartmentTelephoneNo", rootClass, "getOutWsDepartmentTelephoneNo", null);
         myProperties[93] = new PropertyDescriptor("OutWsDepartmentFaxNumber", rootClass, "getOutWsDepartmentFaxNumber", null);
         myProperties[94] = new PropertyDescriptor("OutInvigilatorContactWsAddressAuditFlag", rootClass, "getOutInvigilatorContactWsAddressAuditFlag", null);
         myProperties[95] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine1", rootClass, "getOutInvigilatorContactWsAddressAddressLine1", null);
         myProperties[96] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine2", rootClass, "getOutInvigilatorContactWsAddressAddressLine2", null);
         myProperties[97] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine3", rootClass, "getOutInvigilatorContactWsAddressAddressLine3", null);
         myProperties[98] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine4", rootClass, "getOutInvigilatorContactWsAddressAddressLine4", null);
         myProperties[99] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine5", rootClass, "getOutInvigilatorContactWsAddressAddressLine5", null);
         myProperties[100] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressLine6", rootClass, "getOutInvigilatorContactWsAddressAddressLine6", null);
         myProperties[101] = new PropertyDescriptor("OutInvigilatorContactWsAddressPostalCode", rootClass, "getOutInvigilatorContactWsAddressPostalCode", null);
         myProperties[102] = new PropertyDescriptor("OutInvigilatorContactWsAddressReferenceNo", rootClass, "getOutInvigilatorContactWsAddressReferenceNo", null);
         myProperties[103] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressReferenceFlag", rootClass, "getOutInvigilatorContactWsAddressAddressReferenceFlag", null);
         myProperties[104] = new PropertyDescriptor("OutInvigilatorContactWsAddressType", rootClass, "getOutInvigilatorContactWsAddressType", null);
         myProperties[105] = new PropertyDescriptor("OutInvigilatorContactWsAddressAddressTimestamp", rootClass, "getOutInvigilatorContactWsAddressAddressTimestamp", null);
         myProperties[106] = new PropertyDescriptor("OutInvigilatorContactWsAddressContactTimestamp", rootClass, "getOutInvigilatorContactWsAddressContactTimestamp", null);
         myProperties[107] = new PropertyDescriptor("OutInvigilatorContactWsAddressCategory", rootClass, "getOutInvigilatorContactWsAddressCategory", null);
         myProperties[108] = new PropertyDescriptor("OutInvigilatorContactWsAddressCategoryDescription", rootClass, "getOutInvigilatorContactWsAddressCategoryDescription", null);
         myProperties[109] = new PropertyDescriptor("OutInvigilatorContactWsAddressFaxNumber", rootClass, "getOutInvigilatorContactWsAddressFaxNumber", null);
         myProperties[110] = new PropertyDescriptor("OutInvigilatorContactWsAddressEmailAddress", rootClass, "getOutInvigilatorContactWsAddressEmailAddress", null);
         myProperties[111] = new PropertyDescriptor("OutInvigilatorContactWsAddressHomeNumber", rootClass, "getOutInvigilatorContactWsAddressHomeNumber", null);
         myProperties[112] = new PropertyDescriptor("OutInvigilatorContactWsAddressWorkNumber", rootClass, "getOutInvigilatorContactWsAddressWorkNumber", null);
         myProperties[113] = new PropertyDescriptor("OutVenueWsAddressAuditFlag", rootClass, "getOutVenueWsAddressAuditFlag", null);
         myProperties[114] = new PropertyDescriptor("OutVenueWsAddressAddressLine1", rootClass, "getOutVenueWsAddressAddressLine1", null);
         myProperties[115] = new PropertyDescriptor("OutVenueWsAddressAddressLine2", rootClass, "getOutVenueWsAddressAddressLine2", null);
         myProperties[116] = new PropertyDescriptor("OutVenueWsAddressAddressLine3", rootClass, "getOutVenueWsAddressAddressLine3", null);
         myProperties[117] = new PropertyDescriptor("OutVenueWsAddressAddressLine4", rootClass, "getOutVenueWsAddressAddressLine4", null);
         myProperties[118] = new PropertyDescriptor("OutVenueWsAddressAddressLine5", rootClass, "getOutVenueWsAddressAddressLine5", null);
         myProperties[119] = new PropertyDescriptor("OutVenueWsAddressAddressLine6", rootClass, "getOutVenueWsAddressAddressLine6", null);
         myProperties[120] = new PropertyDescriptor("OutVenueWsAddressPostalCode", rootClass, "getOutVenueWsAddressPostalCode", null);
         myProperties[121] = new PropertyDescriptor("OutVenueWsAddressReferenceNo", rootClass, "getOutVenueWsAddressReferenceNo", null);
         myProperties[122] = new PropertyDescriptor("OutVenueWsAddressAddressReferenceFlag", rootClass, "getOutVenueWsAddressAddressReferenceFlag", null);
         myProperties[123] = new PropertyDescriptor("OutVenueWsAddressType", rootClass, "getOutVenueWsAddressType", null);
         myProperties[124] = new PropertyDescriptor("OutVenueWsAddressFaxNumber", rootClass, "getOutVenueWsAddressFaxNumber", null);
         myProperties[125] = new PropertyDescriptor("OutVenueWsAddressEmailAddress", rootClass, "getOutVenueWsAddressEmailAddress", null);
         myProperties[126] = new PropertyDescriptor("OutVenueWsAddressAddressTimestamp", rootClass, "getOutVenueWsAddressAddressTimestamp", null);
         myProperties[127] = new PropertyDescriptor("OutVenueWsAddressContactTimestamp", rootClass, "getOutVenueWsAddressContactTimestamp", null);
         myProperties[128] = new PropertyDescriptor("OutVenueWsAddressCategory", rootClass, "getOutVenueWsAddressCategory", null);
         myProperties[129] = new PropertyDescriptor("OutVenueWsAddressCategoryDescription", rootClass, "getOutVenueWsAddressCategoryDescription", null);
         myProperties[130] = new PropertyDescriptor("OutVenueWsAddressHomeNumber", rootClass, "getOutVenueWsAddressHomeNumber", null);
         myProperties[131] = new PropertyDescriptor("OutVenueWsAddressWorkNumber", rootClass, "getOutVenueWsAddressWorkNumber", null);
         myProperties[132] = new PropertyDescriptor("OutWsExamVenueCode", rootClass, "getOutWsExamVenueCode", null);
         myProperties[133] = new PropertyDescriptor("OutWsExamVenueInUseFlag", rootClass, "getOutWsExamVenueInUseFlag", null);
         myProperties[134] = new PropertyDescriptor("OutWsExamVenueCourierFlag", rootClass, "getOutWsExamVenueCourierFlag", null);
         myProperties[135] = new PropertyDescriptor("OutWsExamVenueAddressRefNo", rootClass, "getOutWsExamVenueAddressRefNo", null);
         myProperties[136] = new PropertyDescriptor("OutWsExamVenueEngName", rootClass, "getOutWsExamVenueEngName", null);
         myProperties[137] = new PropertyDescriptor("OutWsExamVenueAfrName", rootClass, "getOutWsExamVenueAfrName", null);
         myProperties[138] = new PropertyDescriptor("OutWsExamVenueNumberOfSeats", rootClass, "getOutWsExamVenueNumberOfSeats", null);
         myProperties[139] = new PropertyDescriptor("OutWsStudentExamCentreMkStudentNr", rootClass, "getOutWsStudentExamCentreMkStudentNr", null);
         myProperties[140] = new PropertyDescriptor("OutWsStudentExamCentreMkExamPeriodCode", rootClass, "getOutWsStudentExamCentreMkExamPeriodCode", null);
         myProperties[141] = new PropertyDescriptor("OutWsStudentExamCentreMkExamCentreCode", rootClass, "getOutWsStudentExamCentreMkExamCentreCode", null);
         myProperties[142] = new IndexedPropertyDescriptor("OutGcCsfLineActionBarAction", rootClass, null, null, "getOutGcCsfLineActionBarAction", null);
         myProperties[143] = new IndexedPropertyDescriptor("OutGcCalcCsfStringsString1", rootClass, null, null, "getOutGcCalcCsfStringsString1", null);
         myProperties[144] = new IndexedPropertyDescriptor("OutGcWsStudyUnitCode", rootClass, null, null, "getOutGcWsStudyUnitCode", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGcWsStudyUnitEngLongDescription", rootClass, null, null, "getOutGcWsStudyUnitEngLongDescription", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGcPaperNrCsfStringsString1", rootClass, null, null, "getOutGcPaperNrCsfStringsString1", null);
         myProperties[147] = new IndexedPropertyDescriptor("OutGcDateTimeCsfStringsString25", rootClass, null, null, "getOutGcDateTimeCsfStringsString25", null);
         myProperties[148] = new PropertyDescriptor("OutPracticalFlagWsPracticalsValuesString1", rootClass, "getOutPracticalFlagWsPracticalsValuesString1", null);
         myProperties[149] = new IndexedPropertyDescriptor("OutGCsfStringsString100", rootClass, null, null, "getOutGCsfStringsString100", null);
         myProperties[150] = new PropertyDescriptor("LclWizfuncReportingControlPrinterCode", rootClass, "getLclWizfuncReportingControlPrinterCode", null);
         myProperties[151] = new PropertyDescriptor("LclOutWsExamPeriodCode", rootClass, "getLclOutWsExamPeriodCode", null);
         myProperties[152] = new PropertyDescriptor("LclOutWsExamPeriodEngDescription", rootClass, "getLclOutWsExamPeriodEngDescription", null);
         myProperties[153] = new PropertyDescriptor("LclOutWsExamPeriodAfrDescription", rootClass, "getLclOutWsExamPeriodAfrDescription", null);
         myProperties[154] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[155] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[156] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[157] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[158] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[159] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[161] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[162] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[163] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[164] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[165] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[166] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[167] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[168] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[169] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[170] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[171] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[172] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[173] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[174] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[175] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[176] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[177] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[178] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[179] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[180] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[181] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[182] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[183] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[184] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[185] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[186] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[187] = new PropertyDescriptor("OutWsStudentSpecialCharacterFlag", rootClass, "getOutWsStudentSpecialCharacterFlag", null);
         myProperties[188] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[189] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[190] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[191] = new PropertyDescriptor("OutWsStudentPreviousSurname", rootClass, "getOutWsStudentPreviousSurname", null);
         myProperties[192] = new PropertyDescriptor("OutWsStudentSquashCode", rootClass, "getOutWsStudentSquashCode", null);
         myProperties[193] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[194] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[195] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[196] = new PropertyDescriptor("OutWsStudentGender", rootClass, "getOutWsStudentGender", null);
         myProperties[197] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[198] = new PropertyDescriptor("OutWsStudentMkHomeLanguage", rootClass, "getOutWsStudentMkHomeLanguage", null);
         myProperties[199] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[200] = new PropertyDescriptor("OutWsStudentDeceasedFlag", rootClass, "getOutWsStudentDeceasedFlag", null);
         myProperties[201] = new PropertyDescriptor("OutWsStudentLibraryBlackList", rootClass, "getOutWsStudentLibraryBlackList", null);
         myProperties[202] = new PropertyDescriptor("OutWsStudentExamFeesDebtFlag", rootClass, "getOutWsStudentExamFeesDebtFlag", null);
         myProperties[203] = new PropertyDescriptor("OutWsStudentDisciplinaryIncident", rootClass, "getOutWsStudentDisciplinaryIncident", null);
         myProperties[204] = new PropertyDescriptor("OutWsStudentPostGraduateStudiesApproved", rootClass, "getOutWsStudentPostGraduateStudiesApproved", null);
         myProperties[205] = new PropertyDescriptor("OutWsStudentPhasedOutStatus", rootClass, "getOutWsStudentPhasedOutStatus", null);
         myProperties[206] = new PropertyDescriptor("OutWsStudentFinancialBlockFlag", rootClass, "getOutWsStudentFinancialBlockFlag", null);
         myProperties[207] = new PropertyDescriptor("OutWsStudentTwinFlag", rootClass, "getOutWsStudentTwinFlag", null);
         myProperties[208] = new PropertyDescriptor("OutWsStudentAccessRestrictedFlag", rootClass, "getOutWsStudentAccessRestrictedFlag", null);
         myProperties[209] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[210] = new PropertyDescriptor("OutWsStudentUnisaUndergradYearsRegistered", rootClass, "getOutWsStudentUnisaUndergradYearsRegistered", null);
         myProperties[211] = new PropertyDescriptor("OutWsStudentUnisaHonoursYearsRegistered", rootClass, "getOutWsStudentUnisaHonoursYearsRegistered", null);
         myProperties[212] = new PropertyDescriptor("OutWsStudentUnisaMastersYearsRegistered", rootClass, "getOutWsStudentUnisaMastersYearsRegistered", null);
         myProperties[213] = new PropertyDescriptor("OutWsStudentUnisaDoctrateYearsRegistered", rootClass, "getOutWsStudentUnisaDoctrateYearsRegistered", null);
         myProperties[214] = new PropertyDescriptor("OutWsStudentOtherMastersYearsRegistered", rootClass, "getOutWsStudentOtherMastersYearsRegistered", null);
         myProperties[215] = new PropertyDescriptor("OutWsStudentOtherDoctrateYearsRegistered", rootClass, "getOutWsStudentOtherDoctrateYearsRegistered", null);
         myProperties[216] = new PropertyDescriptor("OutWsStudentPreviouslyPostGraduateFlag", rootClass, "getOutWsStudentPreviouslyPostGraduateFlag", null);
         myProperties[217] = new PropertyDescriptor("OutWsStudentPreviouslyUnisaPostGradFlag", rootClass, "getOutWsStudentPreviouslyUnisaPostGradFlag", null);
         myProperties[218] = new PropertyDescriptor("OutWsStudentResultBlockFlag", rootClass, "getOutWsStudentResultBlockFlag", null);
         myProperties[219] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[220] = new PropertyDescriptor("OutWsStudentMkLastAcademicPeriod", rootClass, "getOutWsStudentMkLastAcademicPeriod", null);
         myProperties[221] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[222] = new PropertyDescriptor("OutWsStudentPassportNo", rootClass, "getOutWsStudentPassportNo", null);
         myProperties[223] = new PropertyDescriptor("OutStudentAnnualRecordMkStudentNr", rootClass, "getOutStudentAnnualRecordMkStudentNr", null);
         myProperties[224] = new PropertyDescriptor("OutStudentStudyUnitExamYear", rootClass, "getOutStudentStudyUnitExamYear", null);
         myProperties[225] = new PropertyDescriptor("OutStudentStudyUnitMkExamPeriod", rootClass, "getOutStudentStudyUnitMkExamPeriod", null);
         myProperties[226] = new PropertyDescriptor("OutComment1CsfStringsString100", rootClass, "getOutComment1CsfStringsString100", null);
         myProperties[227] = new PropertyDescriptor("OutComment2CsfStringsString100", rootClass, "getOutComment2CsfStringsString100", null);
         myProperties[228] = new PropertyDescriptor("OutComment3CsfStringsString100", rootClass, "getOutComment3CsfStringsString100", null);
         myProperties[229] = new PropertyDescriptor("OutComment4CsfStringsString100", rootClass, "getOutComment4CsfStringsString100", null);
         myProperties[230] = new PropertyDescriptor("OutComment5CsfStringsString100", rootClass, "getOutComment5CsfStringsString100", null);
         myProperties[231] = new PropertyDescriptor("OutComment6CsfStringsString100", rootClass, "getOutComment6CsfStringsString100", null);
         myProperties[232] = new PropertyDescriptor("OutComment7CsfStringsString100", rootClass, "getOutComment7CsfStringsString100", null);
         myProperties[233] = new PropertyDescriptor("OutComment8CsfStringsString100", rootClass, "getOutComment8CsfStringsString100", null);
         myProperties[234] = new PropertyDescriptor("OutComment9CsfStringsString100", rootClass, "getOutComment9CsfStringsString100", null);
         myProperties[235] = new PropertyDescriptor("OutComment10CsfStringsString100", rootClass, "getOutComment10CsfStringsString100", null);
         myProperties[236] = new PropertyDescriptor("OutComment11CsfStringsString100", rootClass, "getOutComment11CsfStringsString100", null);
         myProperties[237] = new PropertyDescriptor("OutComment12CsfStringsString100", rootClass, "getOutComment12CsfStringsString100", null);
         myProperties[238] = new PropertyDescriptor("OutInvigilatorAddressCsfStringsString1", rootClass, "getOutInvigilatorAddressCsfStringsString1", null);
         myProperties[239] = new PropertyDescriptor("OutVenueDirectionsWsAddressAuditFlag", rootClass, "getOutVenueDirectionsWsAddressAuditFlag", null);
         myProperties[240] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine1", rootClass, "getOutVenueDirectionsWsAddressAddressLine1", null);
         myProperties[241] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine2", rootClass, "getOutVenueDirectionsWsAddressAddressLine2", null);
         myProperties[242] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine3", rootClass, "getOutVenueDirectionsWsAddressAddressLine3", null);
         myProperties[243] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine4", rootClass, "getOutVenueDirectionsWsAddressAddressLine4", null);
         myProperties[244] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine5", rootClass, "getOutVenueDirectionsWsAddressAddressLine5", null);
         myProperties[245] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressLine6", rootClass, "getOutVenueDirectionsWsAddressAddressLine6", null);
         myProperties[246] = new PropertyDescriptor("OutVenueDirectionsWsAddressPostalCode", rootClass, "getOutVenueDirectionsWsAddressPostalCode", null);
         myProperties[247] = new PropertyDescriptor("OutVenueDirectionsWsAddressReferenceNo", rootClass, "getOutVenueDirectionsWsAddressReferenceNo", null);
         myProperties[248] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressReferenceFlag", rootClass, "getOutVenueDirectionsWsAddressAddressReferenceFlag", null);
         myProperties[249] = new PropertyDescriptor("OutVenueDirectionsWsAddressType", rootClass, "getOutVenueDirectionsWsAddressType", null);
         myProperties[250] = new PropertyDescriptor("OutVenueDirectionsWsAddressFaxNumber", rootClass, "getOutVenueDirectionsWsAddressFaxNumber", null);
         myProperties[251] = new PropertyDescriptor("OutVenueDirectionsWsAddressEmailAddress", rootClass, "getOutVenueDirectionsWsAddressEmailAddress", null);
         myProperties[252] = new PropertyDescriptor("OutVenueDirectionsWsAddressAddressTimestamp", rootClass, "getOutVenueDirectionsWsAddressAddressTimestamp", null);
         myProperties[253] = new PropertyDescriptor("OutVenueDirectionsWsAddressContactTimestamp", rootClass, "getOutVenueDirectionsWsAddressContactTimestamp", null);
         myProperties[254] = new PropertyDescriptor("OutVenueDirectionsWsAddressCategory", rootClass, "getOutVenueDirectionsWsAddressCategory", null);
         myProperties[255] = new PropertyDescriptor("OutVenueDirectionsWsAddressCategoryDescription", rootClass, "getOutVenueDirectionsWsAddressCategoryDescription", null);
         myProperties[256] = new PropertyDescriptor("OutVenueDirectionsWsAddressHomeNumber", rootClass, "getOutVenueDirectionsWsAddressHomeNumber", null);
         myProperties[257] = new PropertyDescriptor("OutVenueDirectionsWsAddressWorkNumber", rootClass, "getOutVenueDirectionsWsAddressWorkNumber", null);
         myProperties[258] = new IndexedPropertyDescriptor("OutGAdmissionWizfuncReportDetailAttr", rootClass, null, null, "getOutGAdmissionWizfuncReportDetailAttr", null);
         myProperties[259] = new IndexedPropertyDescriptor("OutGFcWsStudyUnitCode", rootClass, null, null, "getOutGFcWsStudyUnitCode", null);
         myProperties[260] = new IndexedPropertyDescriptor("OutGNoAdmissionWsStudyUnitCode", rootClass, null, null, "getOutGNoAdmissionWsStudyUnitCode", null);
         myProperties[261] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphCode", rootClass, null, null, "getOutGWsDocumentParagraphCode", null);
         myProperties[262] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineText", rootClass, null, null, "getOutGWsDocumentParagraphLineText", null);
         myProperties[263] = new PropertyDescriptor("LclWsStudentChangeAuditTrailMkStudentNr", rootClass, "getLclWsStudentChangeAuditTrailMkStudentNr", null);
         myProperties[264] = new PropertyDescriptor("LclWsStudentChangeAuditTrailTimestamp", rootClass, "getLclWsStudentChangeAuditTrailTimestamp", null);
         myProperties[265] = new PropertyDescriptor("LclWsStudentChangeAuditTrailMkUserCode", rootClass, "getLclWsStudentChangeAuditTrailMkUserCode", null);
         myProperties[266] = new PropertyDescriptor("LclWsStudentChangeAuditTrailChangeCode", rootClass, "getLclWsStudentChangeAuditTrailChangeCode", null);
         myProperties[267] = new PropertyDescriptor("LclWsStudentChangeAuditTrailSupervisorCode", rootClass, "getLclWsStudentChangeAuditTrailSupervisorCode", null);
         myProperties[268] = new PropertyDescriptor("LclWsStudentChangeAuditTrailErrorFlag", rootClass, "getLclWsStudentChangeAuditTrailErrorFlag", null);
         myProperties[269] = new PropertyDescriptor("LclWsStudentChangeAuditTrailValueChanged", rootClass, "getLclWsStudentChangeAuditTrailValueChanged", null);
         myProperties[270] = new PropertyDescriptor("LclOutWsMethodResultReturnCode", rootClass, "getLclOutWsMethodResultReturnCode", null);
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
//         callMethod = Exsug07sPrtAdmissionLetter.class.getMethod("execute", args);
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
