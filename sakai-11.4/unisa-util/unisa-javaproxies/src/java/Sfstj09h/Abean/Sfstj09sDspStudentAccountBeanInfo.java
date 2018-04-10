package Sfstj09h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Sfstj09sDspStudentAccountBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Sfstj09sDspStudentAccount.class;
 
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
      null,   // 271
      null,   // 272
      null,   // 273
      null,   // 274
      null,   // 275
      null,   // 276
      null,   // 277
      null,   // 278
      null,   // 279
      null,   // 280
      null,   // 281
      null,   // 282
      null,   // 283
      null,   // 284
      null,   // 285
      null,   // 286
      null,   // 287
      null,   // 288
      null,   // 289
      null,   // 290
      null,   // 291
      null,   // 292
      null,   // 293
      null,   // 294
      null,   // 295
      null,   // 296
      null,   // 297
      null,   // 298
      null,   // 299
      null,   // 300
      null,   // 301
      null,   // 302
      null,   // 303
      null,   // 304
      null,   // 305
      null,   // 306
      null,   // 307
      null,   // 308
      null,   // 309
      null,   // 310
      null,   // 311
      null,   // 312
      null,   // 313
      null,   // 314
      null,   // 315
      null,   // 316
      null,   // 317
      null,   // 318
      null,   // 319
      null,   // 320
      null,   // 321
      null,   // 322
      null,   // 323
      null,   // 324
      null,   // 325
      null,   // 326
      null,   // 327
      null,   // 328
      null,   // 329
      null,   // 330
      null,   // 331
      null,   // 332
      null,   // 333
      null,   // 334
      null,   // 335
      null,   // 336
      null,   // 337
      null,   // 338
      null,   // 339
      null,   // 340
      null,   // 341
      null,   // 342
      null,   // 343
      null,   // 344
      null,   // 345
      null,   // 346
      null,   // 347
      null,   // 348
      null,   // 349
      null,   // 350
      null,   // 351
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Sfstj09sDspStudentAccount.class);
      bd.setDisplayName(Sfstj09sDspStudentAccount.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InStudentFcCancellationsMkStudentNr", rootClass);
         myProperties[15] = new PropertyDescriptor("InStudentFcCancellationsMkAcademicYear", rootClass);
         myProperties[16] = new PropertyDescriptor("InStudentFcCancellationsNrOfModules", rootClass);
         myProperties[17] = new PropertyDescriptor("InStudentFcCancellationsAmount", rootClass);
         myProperties[18] = new IndexedPropertyDescriptor("InClassGStudentAccountClassificationCode", rootClass, null, null, "getInClassGStudentAccountClassificationCode", "setInClassGStudentAccountClassificationCode");
         myProperties[19] = new IndexedPropertyDescriptor("InClassGStudentAccountClassificationDescription", rootClass, null, null, "getInClassGStudentAccountClassificationDescription", "setInClassGStudentAccountClassificationDescription");
         myProperties[20] = new PropertyDescriptor("InHistoryIefSuppliedFlag", rootClass);
         myProperties[20].setPropertyEditorClass(Sfstj09sDspStudentAccountIefSuppliedFlagPropertyEditor.class);
         myProperties[21] = new PropertyDescriptor("InStudentAccountHistoryMkAcademicYear", rootClass);
         myProperties[22] = new PropertyDescriptor("InStudentAccountHistoryMkStudentNr", rootClass);
         myProperties[23] = new PropertyDescriptor("InStudentAccountHistoryBalance", rootClass);
         myProperties[24] = new PropertyDescriptor("InStudentAccountHistoryMkAccountType", rootClass);
         myProperties[25] = new PropertyDescriptor("InStudentAccountHistoryMkBranchCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InStudentAccountHistoryMkQualification", rootClass);
         myProperties[27] = new PropertyDescriptor("InStudentAccountHistoryMinRegistrationF", rootClass);
         myProperties[28] = new PropertyDescriptor("InStudentAccountHistoryDiverseTotal", rootClass);
         myProperties[29] = new PropertyDescriptor("InStudentAccountHistoryCreditTotal", rootClass);
         myProperties[30] = new PropertyDescriptor("InStudentAccountHistoryRegTotal", rootClass);
         myProperties[31] = new PropertyDescriptor("InStudentAccountHistorySemester1Final", rootClass);
         myProperties[32] = new PropertyDescriptor("InStudentAccountHistoryFirstPayment", rootClass);
         myProperties[33] = new PropertyDescriptor("InStudentAccountHistoryFinalPayment", rootClass);
         myProperties[34] = new PropertyDescriptor("InStudentAccountHistoryCurrent", rootClass);
         myProperties[35] = new PropertyDescriptor("InStudentAccountHistoryThirtyDays", rootClass);
         myProperties[36] = new PropertyDescriptor("InStudentAccountHistorySixtyDays", rootClass);
         myProperties[37] = new PropertyDescriptor("InStudentAccountHistoryNinetyDaysMore", rootClass);
         myProperties[38] = new PropertyDescriptor("InStudentAccountHistoryComments", rootClass);
         myProperties[39] = new PropertyDescriptor("InStudentAccountHistoryDueImmediately", rootClass);
         myProperties[40] = new PropertyDescriptor("InStudentAccountHistorySem6RegTotal", rootClass);
         myProperties[41] = new PropertyDescriptor("InStudentAccountHistorySem6FirstPayment", rootClass);
         myProperties[42] = new PropertyDescriptor("InStudentAccountHistorySem6FinalPayment", rootClass);
         myProperties[43] = new PropertyDescriptor("InStudentAccountHistoryPrevSem6First", rootClass);
         myProperties[44] = new PropertyDescriptor("InFinGlobalCurrentAcademicYear", rootClass);
         myProperties[45] = new PropertyDescriptor("InDisplayNameIefSuppliedFlag", rootClass);
         myProperties[45].setPropertyEditorClass(Sfstj09sDspStudentAccountIefSuppliedFlagPropertyEditor.class);
         myProperties[46] = new PropertyDescriptor("InWsLearningCentreCode", rootClass);
         myProperties[47] = new PropertyDescriptor("InFromEmailAddressCsfStringsString132", rootClass);
         myProperties[48] = new PropertyDescriptor("InToEmailAddressCsfStringsString132", rootClass);
         myProperties[49] = new PropertyDescriptor("InNextMarchIefSuppliedTotalCurrency", rootClass);
         myProperties[50] = new PropertyDescriptor("InImmediateIefSuppliedTotalCurrency", rootClass);
         myProperties[51] = new PropertyDescriptor("InMarchIefSuppliedTotalCurrency", rootClass);
         myProperties[52] = new PropertyDescriptor("InMayIefSuppliedTotalCurrency", rootClass);
         myProperties[53] = new PropertyDescriptor("InAugustIefSuppliedTotalCurrency", rootClass);
         myProperties[54] = new PropertyDescriptor("InNovemberIefSuppliedTotalCurrency", rootClass);
         myProperties[55] = new PropertyDescriptor("InRetrieveIefSuppliedFlag", rootClass);
         myProperties[55].setPropertyEditorClass(Sfstj09sDspStudentAccountIefSuppliedFlagPropertyEditor.class);
         myProperties[56] = new PropertyDescriptor("InCsfStringsString78", rootClass);
         myProperties[57] = new PropertyDescriptor("InWsWorkstationCode", rootClass);
         myProperties[58] = new PropertyDescriptor("InWsLibraryAmount", rootClass);
         myProperties[59] = new PropertyDescriptor("InWsAddressReferenceNo", rootClass);
         myProperties[60] = new PropertyDescriptor("InWsAddressAddressReferenceFlag", rootClass);
         myProperties[61] = new PropertyDescriptor("InWsAddressType", rootClass);
         myProperties[62] = new PropertyDescriptor("InWsAddressCategory", rootClass);
         myProperties[63] = new PropertyDescriptor("InWsAddressCategoryDescription", rootClass);
         myProperties[64] = new PropertyDescriptor("InWsAddressAddressLine1", rootClass);
         myProperties[65] = new PropertyDescriptor("InWsAddressAddressLine2", rootClass);
         myProperties[66] = new PropertyDescriptor("InWsAddressAddressLine3", rootClass);
         myProperties[67] = new PropertyDescriptor("InWsAddressAddressLine4", rootClass);
         myProperties[68] = new PropertyDescriptor("InWsAddressAddressLine5", rootClass);
         myProperties[69] = new PropertyDescriptor("InWsAddressAddressLine6", rootClass);
         myProperties[70] = new PropertyDescriptor("InWsAddressPostalCode", rootClass);
         myProperties[71] = new PropertyDescriptor("InWsAddressHomeNumber", rootClass);
         myProperties[72] = new PropertyDescriptor("InWsAddressWorkNumber", rootClass);
         myProperties[73] = new PropertyDescriptor("InWsAddressFaxNumber", rootClass);
         myProperties[74] = new PropertyDescriptor("InWsAddressEmailAddress", rootClass);
         myProperties[75] = new PropertyDescriptor("InWsEmployerContactPerson", rootClass);
         myProperties[76] = new PropertyDescriptor("InWsEmployerEngName", rootClass);
         myProperties[77] = new PropertyDescriptor("InWsEmployerAfrName", rootClass);
         myProperties[78] = new PropertyDescriptor("InWsEmployerLanguageCode", rootClass);
         myProperties[79] = new PropertyDescriptor("InWsEmployerCode", rootClass);
         myProperties[80] = new PropertyDescriptor("InComment1CsfStringsString100", rootClass);
         myProperties[81] = new PropertyDescriptor("InComment2CsfStringsString100", rootClass);
         myProperties[82] = new PropertyDescriptor("InComment3CsfStringsString100", rootClass);
         myProperties[83] = new PropertyDescriptor("InComment4CsfStringsString100", rootClass);
         myProperties[84] = new PropertyDescriptor("InComment5CsfStringsString100", rootClass);
         myProperties[85] = new PropertyDescriptor("InStudentAccountMkStudentNr", rootClass);
         myProperties[86] = new PropertyDescriptor("InStudentAccountBalance", rootClass);
         myProperties[87] = new PropertyDescriptor("InStudentAccountRefundBlockedInd", rootClass);
         myProperties[87].setPropertyEditorClass(Sfstj09sDspStudentAccountStudentAccountRefundBlockedIndPropertyEditor.class);
         myProperties[88] = new PropertyDescriptor("InStudentAccountComments", rootClass);
         myProperties[89] = new PropertyDescriptor("InStudentAccountMinRegistrationFee", rootClass);
         myProperties[90] = new PropertyDescriptor("InStudentAccountDiverseTotal", rootClass);
         myProperties[91] = new PropertyDescriptor("InStudentAccountCreditTotal", rootClass);
         myProperties[92] = new PropertyDescriptor("InStudentAccountRegTotal", rootClass);
         myProperties[93] = new PropertyDescriptor("InStudentAccountFirstPayment", rootClass);
         myProperties[94] = new PropertyDescriptor("InStudentAccountFinalPayment", rootClass);
         myProperties[95] = new PropertyDescriptor("InStudentAccountSrcLevyPaid", rootClass);
         myProperties[96] = new PropertyDescriptor("InStudentAccountSemester1Final", rootClass);
         myProperties[97] = new PropertyDescriptor("InStudentAccountSoftwareFeesCharged", rootClass);
         myProperties[98] = new PropertyDescriptor("InStudentAccountLibraryDebtFlag", rootClass);
         myProperties[99] = new PropertyDescriptor("InStudentAccountDueImmediately", rootClass);
         myProperties[100] = new PropertyDescriptor("InStudentAccountSem6RegTotal", rootClass);
         myProperties[101] = new PropertyDescriptor("InStudentAccountSem6FirstPayment", rootClass);
         myProperties[102] = new PropertyDescriptor("InStudentAccountSem6FinalPayment", rootClass);
         myProperties[103] = new PropertyDescriptor("InStudentAccountPrevSem6First", rootClass);
         myProperties[104] = new PropertyDescriptor("InStudentAccountClassificationCode", rootClass);
         myProperties[105] = new PropertyDescriptor("InStudentAccountClassificationDescription", rootClass);
         myProperties[106] = new PropertyDescriptor("InStudentAccountClassificationInUseFlag", rootClass);
         myProperties[106].setPropertyEditorClass(Sfstj09sDspStudentAccountStudentAccountClassificationInUseFlagPropertyEditor.class);
         myProperties[107] = new PropertyDescriptor("InStudentAccountClassificationControlEntity", rootClass);
         myProperties[108] = new PropertyDescriptor("InStudentAccountClassificationControlAccount", rootClass);
         myProperties[109] = new PropertyDescriptor("InWsStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[110] = new PropertyDescriptor("InWsStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[111] = new PropertyDescriptor("InWsStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[112] = new PropertyDescriptor("InWsStudentAnnualRecordMkHighestQualCode", rootClass);
         myProperties[113] = new PropertyDescriptor("InWsStudentAnnualRecordLateRegistrationFlag", rootClass);
         myProperties[113].setPropertyEditorClass(Sfstj09sDspStudentAccountWsStudentAnnualRecordLateRegistrationFlagPropertyEditor.class);
         myProperties[114] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[115] = new PropertyDescriptor("InWsUserName", rootClass);
         myProperties[116] = new PropertyDescriptor("InWsUserPhoneNumber", rootClass);
         myProperties[117] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[118] = new PropertyDescriptor("InWsAccountTypeBranchCode", rootClass);
         myProperties[119] = new PropertyDescriptor("InWsAccountTypeCode", rootClass);
         myProperties[120] = new PropertyDescriptor("InWsDocumentParagraphCode", rootClass);
         myProperties[121] = new PropertyDescriptor("InWsDocumentParagraphMkLanguageCode", rootClass);
         myProperties[122] = new PropertyDescriptor("InWsDocumentParagraphWidthType", rootClass);
         myProperties[123] = new PropertyDescriptor("InWsDocumentParagraphContent", rootClass);
         myProperties[124] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[125] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[126] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[127] = new PropertyDescriptor("InWsStudentLibraryBlackList", rootClass);
         myProperties[127].setPropertyEditorClass(Sfstj09sDspStudentAccountWsStudentLibraryBlackListPropertyEditor.class);
         myProperties[128] = new PropertyDescriptor("InWsStudentPhasedOutStatus", rootClass);
         myProperties[128].setPropertyEditorClass(Sfstj09sDspStudentAccountWsStudentPhasedOutStatusPropertyEditor.class);
         myProperties[129] = new PropertyDescriptor("InWsStudentFinancialBlockFlag", rootClass);
         myProperties[129].setPropertyEditorClass(Sfstj09sDspStudentAccountWsStudentFinancialBlockFlagPropertyEditor.class);
         myProperties[130] = new PropertyDescriptor("InWsStudentUnisaUndergradYearsRegistered", rootClass);
         myProperties[131] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[132] = new PropertyDescriptor("InWsStudentMkLastAcademicPeriod", rootClass);
         myProperties[133] = new PropertyDescriptor("InWsStudentMkCorrespondenceLanguage", rootClass);
         myProperties[134] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[135] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[136] = new PropertyDescriptor("InWsStudentExamFeesDebtFlag", rootClass);
         myProperties[136].setPropertyEditorClass(Sfstj09sDspStudentAccountWsStudentExamFeesDebtFlagPropertyEditor.class);
         myProperties[137] = new PropertyDescriptor("InWsStudentHandedOverFlag", rootClass);
         myProperties[138] = new PropertyDescriptor("InWsStudentRdChequeCode", rootClass);
         myProperties[139] = new PropertyDescriptor("InWsStudentNsfasContractBlock", rootClass);
         myProperties[140] = new PropertyDescriptor("InWsStudentAdministrationOrd", rootClass);
         myProperties[141] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[142] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[143] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[144] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[145] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[146] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[147] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[148] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[149] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[150] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[151] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[152] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[153] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[154] = new PropertyDescriptor("InStudent3gModemFee", rootClass);
         myProperties[155] = new PropertyDescriptor("InStudent3gSimFee", rootClass);
         myProperties[156] = new PropertyDescriptor("In3gActivatedCsfStringsString1", rootClass);
         myProperties[157] = new PropertyDescriptor("In3gPaidCsfStringsString1", rootClass);
         myProperties[158] = new IndexedPropertyDescriptor("OutClassGStudentAccountClassificationCode", rootClass, null, null, "getOutClassGStudentAccountClassificationCode", null);
         myProperties[159] = new IndexedPropertyDescriptor("OutClassGStudentAccountClassificationDescription", rootClass, null, null, "getOutClassGStudentAccountClassificationDescription", null);
         myProperties[160] = new PropertyDescriptor("OutNovemberIefSuppliedTotalCurrency", rootClass, "getOutNovemberIefSuppliedTotalCurrency", null);
         myProperties[161] = new PropertyDescriptor("OutNextMarchIefSuppliedTotalCurrency", rootClass, "getOutNextMarchIefSuppliedTotalCurrency", null);
         myProperties[162] = new PropertyDescriptor("OutMarchIefSuppliedTotalCurrency", rootClass, "getOutMarchIefSuppliedTotalCurrency", null);
         myProperties[163] = new PropertyDescriptor("OutMayIefSuppliedTotalCurrency", rootClass, "getOutMayIefSuppliedTotalCurrency", null);
         myProperties[164] = new PropertyDescriptor("OutAugustIefSuppliedTotalCurrency", rootClass, "getOutAugustIefSuppliedTotalCurrency", null);
         myProperties[165] = new PropertyDescriptor("OutHistoryIefSuppliedFlag", rootClass, "getOutHistoryIefSuppliedFlag", null);
         myProperties[166] = new PropertyDescriptor("OutStudentAccountHistoryMkAcademicYear", rootClass, "getOutStudentAccountHistoryMkAcademicYear", null);
         myProperties[167] = new PropertyDescriptor("OutStudentAccountHistoryMkStudentNr", rootClass, "getOutStudentAccountHistoryMkStudentNr", null);
         myProperties[168] = new PropertyDescriptor("OutStudentAccountHistoryBalance", rootClass, "getOutStudentAccountHistoryBalance", null);
         myProperties[169] = new PropertyDescriptor("OutStudentAccountHistoryMkAccountType", rootClass, "getOutStudentAccountHistoryMkAccountType", null);
         myProperties[170] = new PropertyDescriptor("OutStudentAccountHistoryMkBranchCode", rootClass, "getOutStudentAccountHistoryMkBranchCode", null);
         myProperties[171] = new PropertyDescriptor("OutStudentAccountHistoryMkQualification", rootClass, "getOutStudentAccountHistoryMkQualification", null);
         myProperties[172] = new PropertyDescriptor("OutStudentAccountHistoryMinRegistrationF", rootClass, "getOutStudentAccountHistoryMinRegistrationF", null);
         myProperties[173] = new PropertyDescriptor("OutStudentAccountHistoryDiverseTotal", rootClass, "getOutStudentAccountHistoryDiverseTotal", null);
         myProperties[174] = new PropertyDescriptor("OutStudentAccountHistoryCreditTotal", rootClass, "getOutStudentAccountHistoryCreditTotal", null);
         myProperties[175] = new PropertyDescriptor("OutStudentAccountHistoryRegTotal", rootClass, "getOutStudentAccountHistoryRegTotal", null);
         myProperties[176] = new PropertyDescriptor("OutStudentAccountHistorySemester1Final", rootClass, "getOutStudentAccountHistorySemester1Final", null);
         myProperties[177] = new PropertyDescriptor("OutStudentAccountHistoryFirstPayment", rootClass, "getOutStudentAccountHistoryFirstPayment", null);
         myProperties[178] = new PropertyDescriptor("OutStudentAccountHistoryFinalPayment", rootClass, "getOutStudentAccountHistoryFinalPayment", null);
         myProperties[179] = new PropertyDescriptor("OutStudentAccountHistoryCurrent", rootClass, "getOutStudentAccountHistoryCurrent", null);
         myProperties[180] = new PropertyDescriptor("OutStudentAccountHistoryThirtyDays", rootClass, "getOutStudentAccountHistoryThirtyDays", null);
         myProperties[181] = new PropertyDescriptor("OutStudentAccountHistorySixtyDays", rootClass, "getOutStudentAccountHistorySixtyDays", null);
         myProperties[182] = new PropertyDescriptor("OutStudentAccountHistoryNinetyDaysMore", rootClass, "getOutStudentAccountHistoryNinetyDaysMore", null);
         myProperties[183] = new PropertyDescriptor("OutStudentAccountHistoryComments", rootClass, "getOutStudentAccountHistoryComments", null);
         myProperties[184] = new PropertyDescriptor("OutStudentAccountHistoryDueImmediately", rootClass, "getOutStudentAccountHistoryDueImmediately", null);
         myProperties[185] = new PropertyDescriptor("OutStudentAccountHistorySem6RegTotal", rootClass, "getOutStudentAccountHistorySem6RegTotal", null);
         myProperties[186] = new PropertyDescriptor("OutStudentAccountHistorySem6FirstPayment", rootClass, "getOutStudentAccountHistorySem6FirstPayment", null);
         myProperties[187] = new PropertyDescriptor("OutStudentAccountHistorySem6FinalPayment", rootClass, "getOutStudentAccountHistorySem6FinalPayment", null);
         myProperties[188] = new PropertyDescriptor("OutStudentAccountHistoryPrevSem6First", rootClass, "getOutStudentAccountHistoryPrevSem6First", null);
         myProperties[189] = new PropertyDescriptor("OutFinGlobalCurrentAcademicYear", rootClass, "getOutFinGlobalCurrentAcademicYear", null);
         myProperties[190] = new PropertyDescriptor("OutDisplNameIefSuppliedFlag", rootClass, "getOutDisplNameIefSuppliedFlag", null);
         myProperties[191] = new PropertyDescriptor("OutLclWsAddressAuditFlag", rootClass, "getOutLclWsAddressAuditFlag", null);
         myProperties[192] = new PropertyDescriptor("OutLclWsAddressAddressLine1", rootClass, "getOutLclWsAddressAddressLine1", null);
         myProperties[193] = new PropertyDescriptor("OutLclWsAddressAddressLine2", rootClass, "getOutLclWsAddressAddressLine2", null);
         myProperties[194] = new PropertyDescriptor("OutLclWsAddressAddressLine3", rootClass, "getOutLclWsAddressAddressLine3", null);
         myProperties[195] = new PropertyDescriptor("OutLclWsAddressAddressLine4", rootClass, "getOutLclWsAddressAddressLine4", null);
         myProperties[196] = new PropertyDescriptor("OutLclWsAddressAddressLine5", rootClass, "getOutLclWsAddressAddressLine5", null);
         myProperties[197] = new PropertyDescriptor("OutLclWsAddressAddressLine6", rootClass, "getOutLclWsAddressAddressLine6", null);
         myProperties[198] = new PropertyDescriptor("OutLclWsAddressPostalCode", rootClass, "getOutLclWsAddressPostalCode", null);
         myProperties[199] = new PropertyDescriptor("OutLclWsAddressReferenceNo", rootClass, "getOutLclWsAddressReferenceNo", null);
         myProperties[200] = new PropertyDescriptor("OutLclWsAddressAddressReferenceFlag", rootClass, "getOutLclWsAddressAddressReferenceFlag", null);
         myProperties[201] = new PropertyDescriptor("OutLclWsAddressType", rootClass, "getOutLclWsAddressType", null);
         myProperties[202] = new PropertyDescriptor("OutLclWsAddressFaxNumber", rootClass, "getOutLclWsAddressFaxNumber", null);
         myProperties[203] = new PropertyDescriptor("OutLclWsAddressEmailAddress", rootClass, "getOutLclWsAddressEmailAddress", null);
         myProperties[204] = new PropertyDescriptor("OutLclWsAddressAddressTimestamp", rootClass, "getOutLclWsAddressAddressTimestamp", null);
         myProperties[205] = new PropertyDescriptor("OutLclWsAddressContactTimestamp", rootClass, "getOutLclWsAddressContactTimestamp", null);
         myProperties[206] = new PropertyDescriptor("OutLclWsAddressCategory", rootClass, "getOutLclWsAddressCategory", null);
         myProperties[207] = new PropertyDescriptor("OutLclWsAddressCategoryDescription", rootClass, "getOutLclWsAddressCategoryDescription", null);
         myProperties[208] = new PropertyDescriptor("OutLclWsAddressHomeNumber", rootClass, "getOutLclWsAddressHomeNumber", null);
         myProperties[209] = new PropertyDescriptor("OutLclWsAddressWorkNumber", rootClass, "getOutLclWsAddressWorkNumber", null);
         myProperties[210] = new PropertyDescriptor("OutFromEmailAddressCsfStringsString132", rootClass, "getOutFromEmailAddressCsfStringsString132", null);
         myProperties[211] = new PropertyDescriptor("OutToEmailAddressCsfStringsString132", rootClass, "getOutToEmailAddressCsfStringsString132", null);
         myProperties[212] = new PropertyDescriptor("OutLdStudentAccountMkStudentNr", rootClass, "getOutLdStudentAccountMkStudentNr", null);
         myProperties[213] = new PropertyDescriptor("OutLdStudentAccountBalance", rootClass, "getOutLdStudentAccountBalance", null);
         myProperties[214] = new PropertyDescriptor("OutLdStudentAccountClassificationCode", rootClass, "getOutLdStudentAccountClassificationCode", null);
         myProperties[215] = new PropertyDescriptor("OutSpesLibraryPatronsPatronNr", rootClass, "getOutSpesLibraryPatronsPatronNr", null);
         myProperties[216] = new PropertyDescriptor("OutSpesLibraryPatronsSurname", rootClass, "getOutSpesLibraryPatronsSurname", null);
         myProperties[217] = new PropertyDescriptor("OutSpesLibraryPatronsInitials", rootClass, "getOutSpesLibraryPatronsInitials", null);
         myProperties[218] = new PropertyDescriptor("OutSpesLibraryPatronsTitle", rootClass, "getOutSpesLibraryPatronsTitle", null);
         myProperties[219] = new PropertyDescriptor("OutSpesLibraryPatronsMkCorrespondence", rootClass, "getOutSpesLibraryPatronsMkCorrespondence", null);
         myProperties[220] = new PropertyDescriptor("OutWsWorkstationCode", rootClass, "getOutWsWorkstationCode", null);
         myProperties[221] = new PropertyDescriptor("OutWsLibraryAmount", rootClass, "getOutWsLibraryAmount", null);
         myProperties[222] = new PropertyDescriptor("OutComment1CsfStringsString100", rootClass, "getOutComment1CsfStringsString100", null);
         myProperties[223] = new PropertyDescriptor("OutComment2CsfStringsString100", rootClass, "getOutComment2CsfStringsString100", null);
         myProperties[224] = new PropertyDescriptor("OutComment3CsfStringsString100", rootClass, "getOutComment3CsfStringsString100", null);
         myProperties[225] = new PropertyDescriptor("OutComment4CsfStringsString100", rootClass, "getOutComment4CsfStringsString100", null);
         myProperties[226] = new PropertyDescriptor("OutComment5CsfStringsString100", rootClass, "getOutComment5CsfStringsString100", null);
         myProperties[227] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineSequenceNumber", rootClass, null, null, "getOutGWsDocumentParagraphLineSequenceNumber", null);
         myProperties[228] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineText", rootClass, null, null, "getOutGWsDocumentParagraphLineText", null);
         myProperties[229] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineAmountFlag", rootClass, null, null, "getOutGWsDocumentParagraphLineAmountFlag", null);
         myProperties[230] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineDateFlag", rootClass, null, null, "getOutGWsDocumentParagraphLineDateFlag", null);
         myProperties[231] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineTextFlag", rootClass, null, null, "getOutGWsDocumentParagraphLineTextFlag", null);
         myProperties[232] = new IndexedPropertyDescriptor("OutGWsDocumentParagraphLineNumberFlag", rootClass, null, null, "getOutGWsDocumentParagraphLineNumberFlag", null);
         myProperties[233] = new PropertyDescriptor("OutWsEmployerContactPerson", rootClass, "getOutWsEmployerContactPerson", null);
         myProperties[234] = new PropertyDescriptor("OutWsEmployerEngName", rootClass, "getOutWsEmployerEngName", null);
         myProperties[235] = new PropertyDescriptor("OutWsEmployerAfrName", rootClass, "getOutWsEmployerAfrName", null);
         myProperties[236] = new PropertyDescriptor("OutWsEmployerLanguageCode", rootClass, "getOutWsEmployerLanguageCode", null);
         myProperties[237] = new PropertyDescriptor("OutWsEmployerCode", rootClass, "getOutWsEmployerCode", null);
         myProperties[238] = new PropertyDescriptor("OutStudentAccountMkStudentNr", rootClass, "getOutStudentAccountMkStudentNr", null);
         myProperties[239] = new PropertyDescriptor("OutStudentAccountBalance", rootClass, "getOutStudentAccountBalance", null);
         myProperties[240] = new PropertyDescriptor("OutStudentAccountRefundBlockedInd", rootClass, "getOutStudentAccountRefundBlockedInd", null);
         myProperties[241] = new PropertyDescriptor("OutStudentAccountComments", rootClass, "getOutStudentAccountComments", null);
         myProperties[242] = new PropertyDescriptor("OutStudentAccountMinRegistrationFee", rootClass, "getOutStudentAccountMinRegistrationFee", null);
         myProperties[243] = new PropertyDescriptor("OutStudentAccountDiverseTotal", rootClass, "getOutStudentAccountDiverseTotal", null);
         myProperties[244] = new PropertyDescriptor("OutStudentAccountCreditTotal", rootClass, "getOutStudentAccountCreditTotal", null);
         myProperties[245] = new PropertyDescriptor("OutStudentAccountRegTotal", rootClass, "getOutStudentAccountRegTotal", null);
         myProperties[246] = new PropertyDescriptor("OutStudentAccountFirstPayment", rootClass, "getOutStudentAccountFirstPayment", null);
         myProperties[247] = new PropertyDescriptor("OutStudentAccountFinalPayment", rootClass, "getOutStudentAccountFinalPayment", null);
         myProperties[248] = new PropertyDescriptor("OutStudentAccountSrcLevyPaid", rootClass, "getOutStudentAccountSrcLevyPaid", null);
         myProperties[249] = new PropertyDescriptor("OutStudentAccountSemester1Final", rootClass, "getOutStudentAccountSemester1Final", null);
         myProperties[250] = new PropertyDescriptor("OutStudentAccountSoftwareFeesCharged", rootClass, "getOutStudentAccountSoftwareFeesCharged", null);
         myProperties[251] = new PropertyDescriptor("OutStudentAccountLibraryDebtFlag", rootClass, "getOutStudentAccountLibraryDebtFlag", null);
         myProperties[252] = new PropertyDescriptor("OutStudentAccountDueImmediately", rootClass, "getOutStudentAccountDueImmediately", null);
         myProperties[253] = new PropertyDescriptor("OutStudentAccountSem6RegTotal", rootClass, "getOutStudentAccountSem6RegTotal", null);
         myProperties[254] = new PropertyDescriptor("OutStudentAccountSem6FirstPayment", rootClass, "getOutStudentAccountSem6FirstPayment", null);
         myProperties[255] = new PropertyDescriptor("OutStudentAccountSem6FinalPayment", rootClass, "getOutStudentAccountSem6FinalPayment", null);
         myProperties[256] = new PropertyDescriptor("OutStudentAccountPrevSem6First", rootClass, "getOutStudentAccountPrevSem6First", null);
         myProperties[257] = new PropertyDescriptor("OutStudentAccountClassificationCode", rootClass, "getOutStudentAccountClassificationCode", null);
         myProperties[258] = new PropertyDescriptor("OutStudentAccountClassificationDescription", rootClass, "getOutStudentAccountClassificationDescription", null);
         myProperties[259] = new PropertyDescriptor("OutStudentAccountClassificationInUseFlag", rootClass, "getOutStudentAccountClassificationInUseFlag", null);
         myProperties[260] = new PropertyDescriptor("OutStudentAccountClassificationStudyFeeDebt", rootClass, "getOutStudentAccountClassificationStudyFeeDebt", null);
         myProperties[261] = new PropertyDescriptor("OutStudentAccountClassificationControlEntity", rootClass, "getOutStudentAccountClassificationControlEntity", null);
         myProperties[262] = new PropertyDescriptor("OutStudentAccountClassificationControlAccount", rootClass, "getOutStudentAccountClassificationControlAccount", null);
         myProperties[263] = new PropertyDescriptor("OutWsStudentAnnualRecordMkStudentNr", rootClass, "getOutWsStudentAnnualRecordMkStudentNr", null);
         myProperties[264] = new PropertyDescriptor("OutWsStudentAnnualRecordMkAcademicYear", rootClass, "getOutWsStudentAnnualRecordMkAcademicYear", null);
         myProperties[265] = new PropertyDescriptor("OutWsStudentAnnualRecordMkAcademicPeriod", rootClass, "getOutWsStudentAnnualRecordMkAcademicPeriod", null);
         myProperties[266] = new PropertyDescriptor("OutWsStudentAnnualRecordMkHighestQualCode", rootClass, "getOutWsStudentAnnualRecordMkHighestQualCode", null);
         myProperties[267] = new PropertyDescriptor("OutWsStudentAnnualRecordLateRegistrationFlag", rootClass, "getOutWsStudentAnnualRecordLateRegistrationFlag", null);
         myProperties[268] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[269] = new PropertyDescriptor("OutWsUserName", rootClass, "getOutWsUserName", null);
         myProperties[270] = new PropertyDescriptor("OutWsUserPhoneNumber", rootClass, "getOutWsUserPhoneNumber", null);
         myProperties[271] = new PropertyDescriptor("OutWsPrinterCode", rootClass, "getOutWsPrinterCode", null);
         myProperties[272] = new PropertyDescriptor("OutWsAccountTypeBranchCode", rootClass, "getOutWsAccountTypeBranchCode", null);
         myProperties[273] = new PropertyDescriptor("OutWsAccountTypeCode", rootClass, "getOutWsAccountTypeCode", null);
         myProperties[274] = new PropertyDescriptor("OutWsDocumentParagraphCode", rootClass, "getOutWsDocumentParagraphCode", null);
         myProperties[275] = new PropertyDescriptor("OutWsDocumentParagraphMkLanguageCode", rootClass, "getOutWsDocumentParagraphMkLanguageCode", null);
         myProperties[276] = new PropertyDescriptor("OutWsDocumentParagraphWidthType", rootClass, "getOutWsDocumentParagraphWidthType", null);
         myProperties[277] = new PropertyDescriptor("OutWsDocumentParagraphContent", rootClass, "getOutWsDocumentParagraphContent", null);
         myProperties[278] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[279] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[280] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[281] = new PropertyDescriptor("OutWsStudentLibraryBlackList", rootClass, "getOutWsStudentLibraryBlackList", null);
         myProperties[282] = new PropertyDescriptor("OutWsStudentPhasedOutStatus", rootClass, "getOutWsStudentPhasedOutStatus", null);
         myProperties[283] = new PropertyDescriptor("OutWsStudentFinancialBlockFlag", rootClass, "getOutWsStudentFinancialBlockFlag", null);
         myProperties[284] = new PropertyDescriptor("OutWsStudentUnisaUndergradYearsRegistered", rootClass, "getOutWsStudentUnisaUndergradYearsRegistered", null);
         myProperties[285] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[286] = new PropertyDescriptor("OutWsStudentMkLastAcademicPeriod", rootClass, "getOutWsStudentMkLastAcademicPeriod", null);
         myProperties[287] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[288] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[289] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[290] = new PropertyDescriptor("OutWsStudentExamFeesDebtFlag", rootClass, "getOutWsStudentExamFeesDebtFlag", null);
         myProperties[291] = new PropertyDescriptor("OutWsStudentHandedOverFlag", rootClass, "getOutWsStudentHandedOverFlag", null);
         myProperties[292] = new PropertyDescriptor("OutWsStudentRdChequeCode", rootClass, "getOutWsStudentRdChequeCode", null);
         myProperties[293] = new PropertyDescriptor("OutWsStudentNsfasContractBlock", rootClass, "getOutWsStudentNsfasContractBlock", null);
         myProperties[294] = new PropertyDescriptor("OutWsStudentSpecialCharacterFlag", rootClass, "getOutWsStudentSpecialCharacterFlag", null);
         myProperties[295] = new PropertyDescriptor("OutWsStudentGender", rootClass, "getOutWsStudentGender", null);
         myProperties[296] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[297] = new PropertyDescriptor("OutWsStudentDeceasedFlag", rootClass, "getOutWsStudentDeceasedFlag", null);
         myProperties[298] = new PropertyDescriptor("OutWsStudentDisciplinaryIncident", rootClass, "getOutWsStudentDisciplinaryIncident", null);
         myProperties[299] = new PropertyDescriptor("OutWsStudentPostGraduateStudiesApproved", rootClass, "getOutWsStudentPostGraduateStudiesApproved", null);
         myProperties[300] = new PropertyDescriptor("OutWsStudentAccessRestrictedFlag", rootClass, "getOutWsStudentAccessRestrictedFlag", null);
         myProperties[301] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[302] = new PropertyDescriptor("OutWsStudentResultBlockFlag", rootClass, "getOutWsStudentResultBlockFlag", null);
         myProperties[303] = new PropertyDescriptor("OutWsStudentAdministrationOrd", rootClass, "getOutWsStudentAdministrationOrd", null);
         myProperties[304] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[305] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[306] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[307] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[308] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[309] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[310] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[311] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[312] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[313] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[314] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[315] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[316] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[317] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[318] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[319] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[320] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[321] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[322] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[323] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[324] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[325] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[326] = new PropertyDescriptor("OutWsAddressReferenceNo", rootClass, "getOutWsAddressReferenceNo", null);
         myProperties[327] = new PropertyDescriptor("OutWsAddressAddressReferenceFlag", rootClass, "getOutWsAddressAddressReferenceFlag", null);
         myProperties[328] = new PropertyDescriptor("OutWsAddressType", rootClass, "getOutWsAddressType", null);
         myProperties[329] = new PropertyDescriptor("OutWsAddressCategory", rootClass, "getOutWsAddressCategory", null);
         myProperties[330] = new PropertyDescriptor("OutWsAddressCategoryDescription", rootClass, "getOutWsAddressCategoryDescription", null);
         myProperties[331] = new PropertyDescriptor("OutWsAddressAddressLine1", rootClass, "getOutWsAddressAddressLine1", null);
         myProperties[332] = new PropertyDescriptor("OutWsAddressAddressLine2", rootClass, "getOutWsAddressAddressLine2", null);
         myProperties[333] = new PropertyDescriptor("OutWsAddressAddressLine3", rootClass, "getOutWsAddressAddressLine3", null);
         myProperties[334] = new PropertyDescriptor("OutWsAddressAddressLine4", rootClass, "getOutWsAddressAddressLine4", null);
         myProperties[335] = new PropertyDescriptor("OutWsAddressAddressLine5", rootClass, "getOutWsAddressAddressLine5", null);
         myProperties[336] = new PropertyDescriptor("OutWsAddressAddressLine6", rootClass, "getOutWsAddressAddressLine6", null);
         myProperties[337] = new PropertyDescriptor("OutWsAddressPostalCode", rootClass, "getOutWsAddressPostalCode", null);
         myProperties[338] = new PropertyDescriptor("OutWsAddressHomeNumber", rootClass, "getOutWsAddressHomeNumber", null);
         myProperties[339] = new PropertyDescriptor("OutWsAddressWorkNumber", rootClass, "getOutWsAddressWorkNumber", null);
         myProperties[340] = new PropertyDescriptor("OutWsAddressFaxNumber", rootClass, "getOutWsAddressFaxNumber", null);
         myProperties[341] = new PropertyDescriptor("OutWsAddressEmailAddress", rootClass, "getOutWsAddressEmailAddress", null);
         myProperties[342] = new PropertyDescriptor("OutImmediateIefSuppliedTotalCurrency", rootClass, "getOutImmediateIefSuppliedTotalCurrency", null);
         myProperties[343] = new PropertyDescriptor("OutWsLearningCentreCode", rootClass, "getOutWsLearningCentreCode", null);
         myProperties[344] = new PropertyDescriptor("OutStudentFcCancellationsMkStudentNr", rootClass, "getOutStudentFcCancellationsMkStudentNr", null);
         myProperties[345] = new PropertyDescriptor("OutStudentFcCancellationsMkAcademicYear", rootClass, "getOutStudentFcCancellationsMkAcademicYear", null);
         myProperties[346] = new PropertyDescriptor("OutStudentFcCancellationsNrOfModules", rootClass, "getOutStudentFcCancellationsNrOfModules", null);
         myProperties[347] = new PropertyDescriptor("OutStudentFcCancellationsAmount", rootClass, "getOutStudentFcCancellationsAmount", null);
         myProperties[348] = new PropertyDescriptor("OutStudent3gModemFee", rootClass, "getOutStudent3gModemFee", null);
         myProperties[349] = new PropertyDescriptor("OutStudent3gSimFee", rootClass, "getOutStudent3gSimFee", null);
         myProperties[350] = new PropertyDescriptor("Out3gActivatedCsfStringsString1", rootClass, "getOut3gActivatedCsfStringsString1", null);
         myProperties[351] = new PropertyDescriptor("Out3gPaidCsfStringsString1", rootClass, "getOut3gPaidCsfStringsString1", null);
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
//         callMethod = Sfstj09sDspStudentAccount.class.getMethod("execute", args);
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
