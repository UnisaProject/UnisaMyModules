package Grgdg01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Grgdg01sMntStudentGraduationBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Grgdg01sMntStudentGraduation.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Grgdg01sMntStudentGraduation.class);
      bd.setDisplayName(Grgdg01sMntStudentGraduation.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InOverrideIefSuppliedFlag", rootClass);
         myProperties[14].setPropertyEditorClass(Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor.class);
         myProperties[15] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[16] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[17] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[18] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[19] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[20] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[21] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[22] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[23] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[24] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[25] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[26] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[27] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsQualificationEngDescription", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[31] = new PropertyDescriptor("InStudentGraduationDetailMkStudentNr", rootClass);
         myProperties[32] = new PropertyDescriptor("InStudentGraduationDetailMkQualificationCode", rootClass);
         myProperties[33] = new PropertyDescriptor("InStudentGraduationDetailDateAltered", rootClass);
         myProperties[34] = new PropertyDescriptor("InStudentGraduationDetailMkGraduationCeremonyCode", rootClass);
         myProperties[35] = new PropertyDescriptor("InStudentGraduationDetailLanguage", rootClass);
         myProperties[35].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailLanguagePropertyEditor.class);
         myProperties[36] = new PropertyDescriptor("InStudentGraduationDetailSeatNo", rootClass);
         myProperties[37] = new PropertyDescriptor("InStudentGraduationDetailNoOfGuests", rootClass);
         myProperties[38] = new PropertyDescriptor("InStudentGraduationDetailPresentFlag", rootClass);
         myProperties[38].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailPresentFlagPropertyEditor.class);
         myProperties[39] = new PropertyDescriptor("InStudentGraduationDetailCollectFlag", rootClass);
         myProperties[39].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailCollectFlagPropertyEditor.class);
         myProperties[40] = new PropertyDescriptor("InStudentGraduationDetailGownSize", rootClass);
         myProperties[40].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailGownSizePropertyEditor.class);
         myProperties[41] = new PropertyDescriptor("InStudentGraduationDetailCapSize", rootClass);
         myProperties[41].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailCapSizePropertyEditor.class);
         myProperties[42] = new PropertyDescriptor("InStudentGraduationDetailPreviousDegree", rootClass);
         myProperties[43] = new PropertyDescriptor("InStudentGraduationDetailPurchaseFlag", rootClass);
         myProperties[43].setPropertyEditorClass(Grgdg01sMntStudentGraduationStudentGraduationDetailPurchaseFlagPropertyEditor.class);
         myProperties[44] = new PropertyDescriptor("InStudentGraduationDetailAmount", rootClass);
         myProperties[45] = new PropertyDescriptor("InStudentGraduationDetailBundleDate", rootClass);
         myProperties[46] = new PropertyDescriptor("InStudentGraduationDetailBundleNr", rootClass);
         myProperties[47] = new PropertyDescriptor("InStudentGraduationDetailBundleDoc", rootClass);
         myProperties[48] = new PropertyDescriptor("InStudentGraduationDetailTransferBlockedFlag", rootClass);
         myProperties[49] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[50] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[51] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[52] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[53] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[54] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[55] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[56] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[57] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[58] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[59] = new PropertyDescriptor("InWsGraduationCeremonyCentreV2EngDescription", rootClass);
         myProperties[60] = new PropertyDescriptor("InCellPhoneNumberWsAddressV2CellNumber", rootClass);
         myProperties[61] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[62] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[63] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[64] = new PropertyDescriptor("InFaxNumberCsfStringsString132", rootClass);
         myProperties[65] = new PropertyDescriptor("InFaxOrEmailCsfStringsString1", rootClass);
         myProperties[66] = new PropertyDescriptor("InSmsMessageIefSuppliedFlag", rootClass);
         myProperties[66].setPropertyEditorClass(Grgdg01sMntStudentGraduationIefSuppliedFlagPropertyEditor.class);
         myProperties[67] = new PropertyDescriptor("OutOverrideIefSuppliedFlag", rootClass, "getOutOverrideIefSuppliedFlag", null);
         myProperties[68] = new IndexedPropertyDescriptor("OutGCsfStringsString50", rootClass, null, null, "getOutGCsfStringsString50", null);
         myProperties[69] = new IndexedPropertyDescriptor("OutGCsfEventCommunicationsRgvExistingLineSelectedFlag", rootClass, null, null, "getOutGCsfEventCommunicationsRgvExistingLineSelectedFlag", null);
         myProperties[70] = new IndexedPropertyDescriptor("OutGWsGraduationCeremonyCentreV2EngDescription", rootClass, null, null, "getOutGWsGraduationCeremonyCentreV2EngDescription", null);
         myProperties[71] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailMkStudentNr", rootClass, null, null, "getOutGStudentGraduationDetailMkStudentNr", null);
         myProperties[72] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailMkQualificationCode", rootClass, null, null, "getOutGStudentGraduationDetailMkQualificationCode", null);
         myProperties[73] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailDateAltered", rootClass, null, null, "getOutGStudentGraduationDetailDateAltered", null);
         myProperties[74] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailMkGraduationCeremonyCode", rootClass, null, null, "getOutGStudentGraduationDetailMkGraduationCeremonyCode", null);
         myProperties[75] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailLanguage", rootClass, null, null, "getOutGStudentGraduationDetailLanguage", null);
         myProperties[76] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailSeatNo", rootClass, null, null, "getOutGStudentGraduationDetailSeatNo", null);
         myProperties[77] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailNoOfGuests", rootClass, null, null, "getOutGStudentGraduationDetailNoOfGuests", null);
         myProperties[78] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailPresentFlag", rootClass, null, null, "getOutGStudentGraduationDetailPresentFlag", null);
         myProperties[79] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailCollectFlag", rootClass, null, null, "getOutGStudentGraduationDetailCollectFlag", null);
         myProperties[80] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailGownSize", rootClass, null, null, "getOutGStudentGraduationDetailGownSize", null);
         myProperties[81] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailCapSize", rootClass, null, null, "getOutGStudentGraduationDetailCapSize", null);
         myProperties[82] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailPreviousDegree", rootClass, null, null, "getOutGStudentGraduationDetailPreviousDegree", null);
         myProperties[83] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailPurchaseFlag", rootClass, null, null, "getOutGStudentGraduationDetailPurchaseFlag", null);
         myProperties[84] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailAmount", rootClass, null, null, "getOutGStudentGraduationDetailAmount", null);
         myProperties[85] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailBundleDate", rootClass, null, null, "getOutGStudentGraduationDetailBundleDate", null);
         myProperties[86] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailBundleNr", rootClass, null, null, "getOutGStudentGraduationDetailBundleNr", null);
         myProperties[87] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailBundleDoc", rootClass, null, null, "getOutGStudentGraduationDetailBundleDoc", null);
         myProperties[88] = new IndexedPropertyDescriptor("OutGStudentGraduationDetailTransferBlockedFlag", rootClass, null, null, "getOutGStudentGraduationDetailTransferBlockedFlag", null);
         myProperties[89] = new IndexedPropertyDescriptor("OutGWsQualificationEngDescription", rootClass, null, null, "getOutGWsQualificationEngDescription", null);
         myProperties[90] = new IndexedPropertyDescriptor("OutGWsQualificationCode", rootClass, null, null, "getOutGWsQualificationCode", null);
         myProperties[91] = new IndexedPropertyDescriptor("OutGWsQualificationType", rootClass, null, null, "getOutGWsQualificationType", null);
         myProperties[92] = new IndexedPropertyDescriptor("OutGWsQualificationInUseFlag", rootClass, null, null, "getOutGWsQualificationInUseFlag", null);
         myProperties[93] = new IndexedPropertyDescriptor("OutGWsQualificationMkFacultyCode", rootClass, null, null, "getOutGWsQualificationMkFacultyCode", null);
         myProperties[94] = new IndexedPropertyDescriptor("OutGWsQualificationMkDepartmentCode", rootClass, null, null, "getOutGWsQualificationMkDepartmentCode", null);
         myProperties[95] = new IndexedPropertyDescriptor("OutGWsQualificationShortDescription", rootClass, null, null, "getOutGWsQualificationShortDescription", null);
         myProperties[96] = new IndexedPropertyDescriptor("OutGWsQualificationAfrDescription", rootClass, null, null, "getOutGWsQualificationAfrDescription", null);
         myProperties[97] = new IndexedPropertyDescriptor("OutGWsQualificationLongEngDescription", rootClass, null, null, "getOutGWsQualificationLongEngDescription", null);
         myProperties[98] = new IndexedPropertyDescriptor("OutGWsQualificationLongAfrDescription", rootClass, null, null, "getOutGWsQualificationLongAfrDescription", null);
         myProperties[99] = new IndexedPropertyDescriptor("OutGWsQualificationNumberRegistered", rootClass, null, null, "getOutGWsQualificationNumberRegistered", null);
         myProperties[100] = new IndexedPropertyDescriptor("OutGWsQualificationNumberCancelled", rootClass, null, null, "getOutGWsQualificationNumberCancelled", null);
         myProperties[101] = new IndexedPropertyDescriptor("OutGWsQualificationContactPhoneNo1", rootClass, null, null, "getOutGWsQualificationContactPhoneNo1", null);
         myProperties[102] = new IndexedPropertyDescriptor("OutGWsQualificationContactPhoneNo2", rootClass, null, null, "getOutGWsQualificationContactPhoneNo2", null);
         myProperties[103] = new IndexedPropertyDescriptor("OutGWsQualificationMinimumTime", rootClass, null, null, "getOutGWsQualificationMinimumTime", null);
         myProperties[104] = new PropertyDescriptor("LclOutWsGraduationCeremonyCode", rootClass, "getLclOutWsGraduationCeremonyCode", null);
         myProperties[105] = new PropertyDescriptor("LclOutWsGraduationCeremonyContactPhoneNo1", rootClass, "getLclOutWsGraduationCeremonyContactPhoneNo1", null);
         myProperties[106] = new PropertyDescriptor("LclOutWsGraduationCeremonyContactPhoneNo2", rootClass, "getLclOutWsGraduationCeremonyContactPhoneNo2", null);
         myProperties[107] = new PropertyDescriptor("LclOutWsGraduationCeremonyDate", rootClass, "getLclOutWsGraduationCeremonyDate", null);
         myProperties[108] = new PropertyDescriptor("LclOutWsGraduationCeremonyNoOfStudentsAbsentia", rootClass, "getLclOutWsGraduationCeremonyNoOfStudentsAbsentia", null);
         myProperties[109] = new PropertyDescriptor("LclOutWsGraduationCeremonyNoOfStudentsAttending", rootClass, "getLclOutWsGraduationCeremonyNoOfStudentsAttending", null);
         myProperties[110] = new PropertyDescriptor("LclOutWsGraduationCeremonyStartingTime", rootClass, "getLclOutWsGraduationCeremonyStartingTime", null);
         myProperties[111] = new PropertyDescriptor("LclOutWsGraduationCeremonyNoOutstanding", rootClass, "getLclOutWsGraduationCeremonyNoOutstanding", null);
         myProperties[112] = new PropertyDescriptor("LclOutWsGraduationCeremonyNo12YrAbsent", rootClass, "getLclOutWsGraduationCeremonyNo12YrAbsent", null);
         myProperties[113] = new PropertyDescriptor("LclTestStudentGraduationDetailMkStudentNr", rootClass, "getLclTestStudentGraduationDetailMkStudentNr", null);
         myProperties[114] = new PropertyDescriptor("LclTestStudentGraduationDetailMkQualificationCode", rootClass, "getLclTestStudentGraduationDetailMkQualificationCode", null);
         myProperties[115] = new PropertyDescriptor("LclTestStudentGraduationDetailDateAltered", rootClass, "getLclTestStudentGraduationDetailDateAltered", null);
         myProperties[116] = new PropertyDescriptor("LclTestStudentGraduationDetailMkGraduationCeremonyCode", rootClass, "getLclTestStudentGraduationDetailMkGraduationCeremonyCode", null);
         myProperties[117] = new PropertyDescriptor("LclTestStudentGraduationDetailLanguage", rootClass, "getLclTestStudentGraduationDetailLanguage", null);
         myProperties[118] = new PropertyDescriptor("LclTestStudentGraduationDetailSeatNo", rootClass, "getLclTestStudentGraduationDetailSeatNo", null);
         myProperties[119] = new PropertyDescriptor("LclTestStudentGraduationDetailNoOfGuests", rootClass, "getLclTestStudentGraduationDetailNoOfGuests", null);
         myProperties[120] = new PropertyDescriptor("LclTestStudentGraduationDetailPresentFlag", rootClass, "getLclTestStudentGraduationDetailPresentFlag", null);
         myProperties[121] = new PropertyDescriptor("LclTestStudentGraduationDetailCollectFlag", rootClass, "getLclTestStudentGraduationDetailCollectFlag", null);
         myProperties[122] = new PropertyDescriptor("LclTestStudentGraduationDetailGownSize", rootClass, "getLclTestStudentGraduationDetailGownSize", null);
         myProperties[123] = new PropertyDescriptor("LclTestStudentGraduationDetailCapSize", rootClass, "getLclTestStudentGraduationDetailCapSize", null);
         myProperties[124] = new PropertyDescriptor("LclTestStudentGraduationDetailPreviousDegree", rootClass, "getLclTestStudentGraduationDetailPreviousDegree", null);
         myProperties[125] = new PropertyDescriptor("LclTestStudentGraduationDetailPurchaseFlag", rootClass, "getLclTestStudentGraduationDetailPurchaseFlag", null);
         myProperties[126] = new PropertyDescriptor("LclTestStudentGraduationDetailAmount", rootClass, "getLclTestStudentGraduationDetailAmount", null);
         myProperties[127] = new PropertyDescriptor("LclTestStudentGraduationDetailBundleDate", rootClass, "getLclTestStudentGraduationDetailBundleDate", null);
         myProperties[128] = new PropertyDescriptor("LclTestStudentGraduationDetailBundleNr", rootClass, "getLclTestStudentGraduationDetailBundleNr", null);
         myProperties[129] = new PropertyDescriptor("LclTestStudentGraduationDetailBundleDoc", rootClass, "getLclTestStudentGraduationDetailBundleDoc", null);
         myProperties[130] = new PropertyDescriptor("LclTestStudentGraduationDetailTransferBlockedFlag", rootClass, "getLclTestStudentGraduationDetailTransferBlockedFlag", null);
         myProperties[131] = new PropertyDescriptor("LclOutWizfuncReportingControlPrinterCode", rootClass, "getLclOutWizfuncReportingControlPrinterCode", null);
         myProperties[132] = new PropertyDescriptor("LclOutCsfStringsString500", rootClass, "getLclOutCsfStringsString500", null);
         myProperties[133] = new PropertyDescriptor("LclOutStudentAcademicRecordMkStudentNr", rootClass, "getLclOutStudentAcademicRecordMkStudentNr", null);
         myProperties[134] = new PropertyDescriptor("LclOutStudentAcademicRecordMkQualificationCode", rootClass, "getLclOutStudentAcademicRecordMkQualificationCode", null);
         myProperties[135] = new PropertyDescriptor("LclOutStudentAcademicRecordLastAcademicRegistrationYear", rootClass, "getLclOutStudentAcademicRecordLastAcademicRegistrationYear", null);
         myProperties[136] = new PropertyDescriptor("LclOutStudentAcademicRecordActualCompletionYear", rootClass, "getLclOutStudentAcademicRecordActualCompletionYear", null);
         myProperties[137] = new PropertyDescriptor("LclOutStudentAcademicRecordMkGraduationCeremonyCode", rootClass, "getLclOutStudentAcademicRecordMkGraduationCeremonyCode", null);
         myProperties[138] = new PropertyDescriptor("LclOutStudentAcademicRecordGraduationCeremonyDate", rootClass, "getLclOutStudentAcademicRecordGraduationCeremonyDate", null);
         myProperties[139] = new PropertyDescriptor("LclOutStudentAcademicRecordLastRegistrationDate", rootClass, "getLclOutStudentAcademicRecordLastRegistrationDate", null);
         myProperties[140] = new PropertyDescriptor("LclOutStudentAcademicRecordFirstRegistrationDate", rootClass, "getLclOutStudentAcademicRecordFirstRegistrationDate", null);
         myProperties[141] = new PropertyDescriptor("LclOutStudentAcademicRecordLastExamDate", rootClass, "getLclOutStudentAcademicRecordLastExamDate", null);
         myProperties[142] = new PropertyDescriptor("LclOutStudentAcademicRecordStatusCode", rootClass, "getLclOutStudentAcademicRecordStatusCode", null);
         myProperties[143] = new PropertyDescriptor("LclOutStudentAcademicRecordTemporaryFlag", rootClass, "getLclOutStudentAcademicRecordTemporaryFlag", null);
         myProperties[144] = new PropertyDescriptor("LclOutStudentAcademicRecordTemporaryStatusCode", rootClass, "getLclOutStudentAcademicRecordTemporaryStatusCode", null);
         myProperties[145] = new PropertyDescriptor("LclOutStudentAcademicRecordDistinctionFlag", rootClass, "getLclOutStudentAcademicRecordDistinctionFlag", null);
         myProperties[146] = new PropertyDescriptor("LclOutStudentAcademicRecordComment1", rootClass, "getLclOutStudentAcademicRecordComment1", null);
         myProperties[147] = new PropertyDescriptor("LclOutStudentAcademicRecordComment2", rootClass, "getLclOutStudentAcademicRecordComment2", null);
         myProperties[148] = new PropertyDescriptor("LclOutStudentAcademicRecordLastUserCode", rootClass, "getLclOutStudentAcademicRecordLastUserCode", null);
         myProperties[149] = new PropertyDescriptor("LclOutStudentAcademicRecordFinalMarkControlTotal", rootClass, "getLclOutStudentAcademicRecordFinalMarkControlTotal", null);
         myProperties[150] = new PropertyDescriptor("LclOutStudentAcademicRecordTotalCreditsControlTotal", rootClass, "getLclOutStudentAcademicRecordTotalCreditsControlTotal", null);
         myProperties[151] = new PropertyDescriptor("LclOutStudentAcademicRecordAveragePercent", rootClass, "getLclOutStudentAcademicRecordAveragePercent", null);
         myProperties[152] = new PropertyDescriptor("LclOutStudentAcademicRecordAdmissionRequirements", rootClass, "getLclOutStudentAcademicRecordAdmissionRequirements", null);
         myProperties[153] = new PropertyDescriptor("LclOutStudentAcademicRecordOtherAdmissionRequirements", rootClass, "getLclOutStudentAcademicRecordOtherAdmissionRequirements", null);
         myProperties[154] = new PropertyDescriptor("LclOutStudentAcademicRecordWeeksExperience", rootClass, "getLclOutStudentAcademicRecordWeeksExperience", null);
         myProperties[155] = new PropertyDescriptor("LclOutStudentAcademicRecordPrevQualRegistrationFlag", rootClass, "getLclOutStudentAcademicRecordPrevQualRegistrationFlag", null);
         myProperties[156] = new PropertyDescriptor("LclOutStudentAcademicRecordEarliestExemptionUnisaYear", rootClass, "getLclOutStudentAcademicRecordEarliestExemptionUnisaYear", null);
         myProperties[157] = new PropertyDescriptor("LclOutStudentAcademicRecordEarliestExemptionOtherYear", rootClass, "getLclOutStudentAcademicRecordEarliestExemptionOtherYear", null);
         myProperties[158] = new PropertyDescriptor("LclOutWsMethodResultReturnCode", rootClass, "getLclOutWsMethodResultReturnCode", null);
         myProperties[159] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[161] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[162] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[163] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[164] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[165] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[166] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[167] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[168] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[169] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[170] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[171] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[172] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[173] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[174] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[175] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[176] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[177] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[178] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[179] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[180] = new PropertyDescriptor("OutWsQualificationEngDescription", rootClass, "getOutWsQualificationEngDescription", null);
         myProperties[181] = new PropertyDescriptor("OutWsQualificationCode", rootClass, "getOutWsQualificationCode", null);
         myProperties[182] = new PropertyDescriptor("OutWsQualificationType", rootClass, "getOutWsQualificationType", null);
         myProperties[183] = new PropertyDescriptor("OutWsQualificationInUseFlag", rootClass, "getOutWsQualificationInUseFlag", null);
         myProperties[184] = new PropertyDescriptor("OutWsQualificationMkFacultyCode", rootClass, "getOutWsQualificationMkFacultyCode", null);
         myProperties[185] = new PropertyDescriptor("OutWsQualificationMkDepartmentCode", rootClass, "getOutWsQualificationMkDepartmentCode", null);
         myProperties[186] = new PropertyDescriptor("OutWsQualificationShortDescription", rootClass, "getOutWsQualificationShortDescription", null);
         myProperties[187] = new PropertyDescriptor("OutWsQualificationAfrDescription", rootClass, "getOutWsQualificationAfrDescription", null);
         myProperties[188] = new PropertyDescriptor("OutWsQualificationLongEngDescription", rootClass, "getOutWsQualificationLongEngDescription", null);
         myProperties[189] = new PropertyDescriptor("OutWsQualificationLongAfrDescription", rootClass, "getOutWsQualificationLongAfrDescription", null);
         myProperties[190] = new PropertyDescriptor("OutWsQualificationNumberRegistered", rootClass, "getOutWsQualificationNumberRegistered", null);
         myProperties[191] = new PropertyDescriptor("OutWsQualificationNumberCancelled", rootClass, "getOutWsQualificationNumberCancelled", null);
         myProperties[192] = new PropertyDescriptor("OutWsQualificationContactPhoneNo1", rootClass, "getOutWsQualificationContactPhoneNo1", null);
         myProperties[193] = new PropertyDescriptor("OutWsQualificationContactPhoneNo2", rootClass, "getOutWsQualificationContactPhoneNo2", null);
         myProperties[194] = new PropertyDescriptor("OutWsQualificationMinimumTime", rootClass, "getOutWsQualificationMinimumTime", null);
         myProperties[195] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[196] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[197] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[198] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[199] = new PropertyDescriptor("OutWsStudentSpecialCharacterFlag", rootClass, "getOutWsStudentSpecialCharacterFlag", null);
         myProperties[200] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[201] = new PropertyDescriptor("OutWsStudentPreviousSurname", rootClass, "getOutWsStudentPreviousSurname", null);
         myProperties[202] = new PropertyDescriptor("OutWsStudentSquashCode", rootClass, "getOutWsStudentSquashCode", null);
         myProperties[203] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[204] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[205] = new PropertyDescriptor("OutWsStudentGender", rootClass, "getOutWsStudentGender", null);
         myProperties[206] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[207] = new PropertyDescriptor("OutWsStudentMkHomeLanguage", rootClass, "getOutWsStudentMkHomeLanguage", null);
         myProperties[208] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[209] = new PropertyDescriptor("OutWsStudentDeceasedFlag", rootClass, "getOutWsStudentDeceasedFlag", null);
         myProperties[210] = new PropertyDescriptor("OutWsStudentLibraryBlackList", rootClass, "getOutWsStudentLibraryBlackList", null);
         myProperties[211] = new PropertyDescriptor("OutWsStudentExamFeesDebtFlag", rootClass, "getOutWsStudentExamFeesDebtFlag", null);
         myProperties[212] = new PropertyDescriptor("OutWsStudentDisciplinaryIncident", rootClass, "getOutWsStudentDisciplinaryIncident", null);
         myProperties[213] = new PropertyDescriptor("OutWsStudentPostGraduateStudiesApproved", rootClass, "getOutWsStudentPostGraduateStudiesApproved", null);
         myProperties[214] = new PropertyDescriptor("OutWsStudentPhasedOutStatus", rootClass, "getOutWsStudentPhasedOutStatus", null);
         myProperties[215] = new PropertyDescriptor("OutWsStudentFinancialBlockFlag", rootClass, "getOutWsStudentFinancialBlockFlag", null);
         myProperties[216] = new PropertyDescriptor("OutWsStudentTwinFlag", rootClass, "getOutWsStudentTwinFlag", null);
         myProperties[217] = new PropertyDescriptor("OutWsStudentAccessRestrictedFlag", rootClass, "getOutWsStudentAccessRestrictedFlag", null);
         myProperties[218] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[219] = new PropertyDescriptor("OutWsStudentUnisaUndergradYearsRegistered", rootClass, "getOutWsStudentUnisaUndergradYearsRegistered", null);
         myProperties[220] = new PropertyDescriptor("OutWsStudentUnisaHonoursYearsRegistered", rootClass, "getOutWsStudentUnisaHonoursYearsRegistered", null);
         myProperties[221] = new PropertyDescriptor("OutWsStudentUnisaMastersYearsRegistered", rootClass, "getOutWsStudentUnisaMastersYearsRegistered", null);
         myProperties[222] = new PropertyDescriptor("OutWsStudentUnisaDoctrateYearsRegistered", rootClass, "getOutWsStudentUnisaDoctrateYearsRegistered", null);
         myProperties[223] = new PropertyDescriptor("OutWsStudentOtherMastersYearsRegistered", rootClass, "getOutWsStudentOtherMastersYearsRegistered", null);
         myProperties[224] = new PropertyDescriptor("OutWsStudentOtherDoctrateYearsRegistered", rootClass, "getOutWsStudentOtherDoctrateYearsRegistered", null);
         myProperties[225] = new PropertyDescriptor("OutWsStudentPreviouslyPostGraduateFlag", rootClass, "getOutWsStudentPreviouslyPostGraduateFlag", null);
         myProperties[226] = new PropertyDescriptor("OutWsStudentPreviouslyUnisaPostGradFlag", rootClass, "getOutWsStudentPreviouslyUnisaPostGradFlag", null);
         myProperties[227] = new PropertyDescriptor("OutWsStudentResultBlockFlag", rootClass, "getOutWsStudentResultBlockFlag", null);
         myProperties[228] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[229] = new PropertyDescriptor("OutWsStudentMkLastAcademicPeriod", rootClass, "getOutWsStudentMkLastAcademicPeriod", null);
         myProperties[230] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[231] = new PropertyDescriptor("OutStudentGraduationDetailMkStudentNr", rootClass, "getOutStudentGraduationDetailMkStudentNr", null);
         myProperties[232] = new PropertyDescriptor("OutStudentGraduationDetailMkQualificationCode", rootClass, "getOutStudentGraduationDetailMkQualificationCode", null);
         myProperties[233] = new PropertyDescriptor("OutStudentGraduationDetailDateAltered", rootClass, "getOutStudentGraduationDetailDateAltered", null);
         myProperties[234] = new PropertyDescriptor("OutStudentGraduationDetailMkGraduationCeremonyCode", rootClass, "getOutStudentGraduationDetailMkGraduationCeremonyCode", null);
         myProperties[235] = new PropertyDescriptor("OutStudentGraduationDetailLanguage", rootClass, "getOutStudentGraduationDetailLanguage", null);
         myProperties[236] = new PropertyDescriptor("OutStudentGraduationDetailSeatNo", rootClass, "getOutStudentGraduationDetailSeatNo", null);
         myProperties[237] = new PropertyDescriptor("OutStudentGraduationDetailNoOfGuests", rootClass, "getOutStudentGraduationDetailNoOfGuests", null);
         myProperties[238] = new PropertyDescriptor("OutStudentGraduationDetailPresentFlag", rootClass, "getOutStudentGraduationDetailPresentFlag", null);
         myProperties[239] = new PropertyDescriptor("OutStudentGraduationDetailCollectFlag", rootClass, "getOutStudentGraduationDetailCollectFlag", null);
         myProperties[240] = new PropertyDescriptor("OutStudentGraduationDetailGownSize", rootClass, "getOutStudentGraduationDetailGownSize", null);
         myProperties[241] = new PropertyDescriptor("OutStudentGraduationDetailCapSize", rootClass, "getOutStudentGraduationDetailCapSize", null);
         myProperties[242] = new PropertyDescriptor("OutStudentGraduationDetailPreviousDegree", rootClass, "getOutStudentGraduationDetailPreviousDegree", null);
         myProperties[243] = new PropertyDescriptor("OutStudentGraduationDetailPurchaseFlag", rootClass, "getOutStudentGraduationDetailPurchaseFlag", null);
         myProperties[244] = new PropertyDescriptor("OutStudentGraduationDetailAmount", rootClass, "getOutStudentGraduationDetailAmount", null);
         myProperties[245] = new PropertyDescriptor("OutStudentGraduationDetailBundleDate", rootClass, "getOutStudentGraduationDetailBundleDate", null);
         myProperties[246] = new PropertyDescriptor("OutStudentGraduationDetailBundleNr", rootClass, "getOutStudentGraduationDetailBundleNr", null);
         myProperties[247] = new PropertyDescriptor("OutStudentGraduationDetailBundleDoc", rootClass, "getOutStudentGraduationDetailBundleDoc", null);
         myProperties[248] = new PropertyDescriptor("OutStudentGraduationDetailTransferBlockedFlag", rootClass, "getOutStudentGraduationDetailTransferBlockedFlag", null);
         myProperties[249] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[250] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[251] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[252] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[253] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[254] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[255] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[256] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[257] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[258] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[259] = new PropertyDescriptor("OutWsGraduationCeremonyCentreV2EngDescription", rootClass, "getOutWsGraduationCeremonyCentreV2EngDescription", null);
         myProperties[260] = new PropertyDescriptor("OutCellPhoneNumberWsAddressV2CellNumber", rootClass, "getOutCellPhoneNumberWsAddressV2CellNumber", null);
         myProperties[261] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[262] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
         myProperties[263] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[264] = new PropertyDescriptor("OutFaxNumberCsfStringsString132", rootClass, "getOutFaxNumberCsfStringsString132", null);
         myProperties[265] = new PropertyDescriptor("OutFaxOrEmailCsfStringsString1", rootClass, "getOutFaxOrEmailCsfStringsString1", null);
         myProperties[266] = new PropertyDescriptor("OutSmsMessageIefSuppliedFlag", rootClass, "getOutSmsMessageIefSuppliedFlag", null);
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
//         callMethod = Grgdg01sMntStudentGraduation.class.getMethod("execute", args);
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
