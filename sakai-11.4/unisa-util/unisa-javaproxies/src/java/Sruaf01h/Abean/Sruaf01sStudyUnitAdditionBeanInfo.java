package Sruaf01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Sruaf01sStudyUnitAdditionBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Sruaf01sStudyUnitAddition.class;
 
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
      null,   // 352
      null,   // 353
      null,   // 354
      null,   // 355
      null,   // 356
      null,   // 357
      null,   // 358
      null,   // 359
      null,   // 360
      null,   // 361
      null,   // 362
      null,   // 363
      null,   // 364
      null,   // 365
      null,   // 366
      null,   // 367
      null,   // 368
      null,   // 369
      null,   // 370
      null,   // 371
      null,   // 372
      null,   // 373
      null,   // 374
      null,   // 375
      null,   // 376
      null,   // 377
      null,   // 378
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Sruaf01sStudyUnitAddition.class);
      bd.setDisplayName(Sruaf01sStudyUnitAddition.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InFaxNrCsfStringsString132", rootClass);
         myProperties[15] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[16] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[17] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[18] = new PropertyDescriptor("InFaxOrEmailCsfStringsString1", rootClass);
         myProperties[19] = new PropertyDescriptor("InAcadOverrideIefSuppliedFlag", rootClass);
         myProperties[19].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[20] = new PropertyDescriptor("InWsWorkstationCode", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[22] = new PropertyDescriptor("InStudentAccountTypeAuditTrailReasonCode", rootClass);
         myProperties[22].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAccountTypeAuditTrailReasonCodePropertyEditor.class);
         myProperties[23] = new PropertyDescriptor("InStudentAccountTypeAuditTrailAuthorisation", rootClass);
         myProperties[24] = new PropertyDescriptor("InAccountTypeCode", rootClass);
         myProperties[25] = new PropertyDescriptor("InAccountTypeBranchCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InAccountTypeEnglishDescription", rootClass);
         myProperties[27] = new PropertyDescriptor("InAccountTypeMinimumFeeFlag", rootClass);
         myProperties[27].setPropertyEditorClass(Sruaf01sStudyUnitAdditionAccountTypeMinimumFeeFlagPropertyEditor.class);
         myProperties[28] = new PropertyDescriptor("InAccountTypeStudyBenefitType", rootClass);
         myProperties[28].setPropertyEditorClass(Sruaf01sStudyUnitAdditionAccountTypeStudyBenefitTypePropertyEditor.class);
         myProperties[29] = new PropertyDescriptor("InAccountTypeDate", rootClass);
         myProperties[30] = new PropertyDescriptor("InAccountTypeMkAddressReferenceNo", rootClass);
         myProperties[31] = new PropertyDescriptor("InAccountTypeContractFlag", rootClass);
         myProperties[31].setPropertyEditorClass(Sruaf01sStudyUnitAdditionAccountTypeContractFlagPropertyEditor.class);
         myProperties[32] = new PropertyDescriptor("InAccountTypeInUseFlag", rootClass);
         myProperties[32].setPropertyEditorClass(Sruaf01sStudyUnitAdditionAccountTypeInUseFlagPropertyEditor.class);
         myProperties[33] = new PropertyDescriptor("InAccountTypeIgnoreFinancialsFlag", rootClass);
         myProperties[33].setPropertyEditorClass(Sruaf01sStudyUnitAdditionAccountTypeIgnoreFinancialsFlagPropertyEditor.class);
         myProperties[34] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsStudentSpecialCharacterFlag", rootClass);
         myProperties[35].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentSpecialCharacterFlagPropertyEditor.class);
         myProperties[36] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsStudentFirstNames", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsStudentPreviousSurname", rootClass);
         myProperties[40] = new PropertyDescriptor("InWsStudentSquashCode", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsStudentIdentityNr", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsStudentBirthDate", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsStudentGender", rootClass);
         myProperties[44].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentGenderPropertyEditor.class);
         myProperties[45] = new PropertyDescriptor("InWsStudentMkNationality", rootClass);
         myProperties[46] = new PropertyDescriptor("InWsStudentMkHomeLanguage", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsStudentMkCorrespondenceLanguage", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsStudentDeceasedFlag", rootClass);
         myProperties[48].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentDeceasedFlagPropertyEditor.class);
         myProperties[49] = new PropertyDescriptor("InWsStudentLibraryBlackList", rootClass);
         myProperties[49].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentLibraryBlackListPropertyEditor.class);
         myProperties[50] = new PropertyDescriptor("InWsStudentExamFeesDebtFlag", rootClass);
         myProperties[50].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentExamFeesDebtFlagPropertyEditor.class);
         myProperties[51] = new PropertyDescriptor("InWsStudentDisciplinaryIncident", rootClass);
         myProperties[51].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentDisciplinaryIncidentPropertyEditor.class);
         myProperties[52] = new PropertyDescriptor("InWsStudentPostGraduateStudiesApproved", rootClass);
         myProperties[52].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentPostGraduateStudiesApprovedPropertyEditor.class);
         myProperties[53] = new PropertyDescriptor("InWsStudentPhasedOutStatus", rootClass);
         myProperties[53].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentPhasedOutStatusPropertyEditor.class);
         myProperties[54] = new PropertyDescriptor("InWsStudentFinancialBlockFlag", rootClass);
         myProperties[54].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentFinancialBlockFlagPropertyEditor.class);
         myProperties[55] = new PropertyDescriptor("InWsStudentTwinFlag", rootClass);
         myProperties[55].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentTwinFlagPropertyEditor.class);
         myProperties[56] = new PropertyDescriptor("InWsStudentAccessRestrictedFlag", rootClass);
         myProperties[56].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentAccessRestrictedFlagPropertyEditor.class);
         myProperties[57] = new PropertyDescriptor("InWsStudentNumberRestrictedFlag", rootClass);
         myProperties[57].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentNumberRestrictedFlagPropertyEditor.class);
         myProperties[58] = new PropertyDescriptor("InWsStudentUnisaUndergradYearsRegistered", rootClass);
         myProperties[59] = new PropertyDescriptor("InWsStudentUnisaHonoursYearsRegistered", rootClass);
         myProperties[60] = new PropertyDescriptor("InWsStudentUnisaMastersYearsRegistered", rootClass);
         myProperties[61] = new PropertyDescriptor("InWsStudentUnisaDoctrateYearsRegistered", rootClass);
         myProperties[62] = new PropertyDescriptor("InWsStudentOtherMastersYearsRegistered", rootClass);
         myProperties[63] = new PropertyDescriptor("InWsStudentOtherDoctrateYearsRegistered", rootClass);
         myProperties[64] = new PropertyDescriptor("InWsStudentPreviouslyPostGraduateFlag", rootClass);
         myProperties[64].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentPreviouslyPostGraduateFlagPropertyEditor.class);
         myProperties[65] = new PropertyDescriptor("InWsStudentPreviouslyUnisaPostGradFlag", rootClass);
         myProperties[65].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentPreviouslyUnisaPostGradFlagPropertyEditor.class);
         myProperties[66] = new PropertyDescriptor("InWsStudentResultBlockFlag", rootClass);
         myProperties[66].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentResultBlockFlagPropertyEditor.class);
         myProperties[67] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[68] = new PropertyDescriptor("InWsStudentMkLastAcademicPeriod", rootClass);
         myProperties[69] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[70] = new PropertyDescriptor("InWsStudentSmartCardIssued", rootClass);
         myProperties[71] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[72] = new PropertyDescriptor("InWsUserType", rootClass);
         myProperties[73] = new PropertyDescriptor("InQualificationSpecialityTypeSpecialityCode", rootClass);
         myProperties[74] = new PropertyDescriptor("InAcademicRecordExistsIefSuppliedFlag", rootClass);
         myProperties[74].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[75] = new PropertyDescriptor("InFinalYearIefSuppliedFlag", rootClass);
         myProperties[75].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[76] = new PropertyDescriptor("InStudentAcademicRecordMkStudentNr", rootClass);
         myProperties[77] = new PropertyDescriptor("InStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[78] = new PropertyDescriptor("InStudentAcademicRecordLastAcademicRegistrationYear", rootClass);
         myProperties[79] = new PropertyDescriptor("InStudentAcademicRecordActualCompletionYear", rootClass);
         myProperties[80] = new PropertyDescriptor("InStudentAcademicRecordMkGraduationCeremonyCode", rootClass);
         myProperties[81] = new PropertyDescriptor("InStudentAcademicRecordGraduationCeremonyDate", rootClass);
         myProperties[82] = new PropertyDescriptor("InStudentAcademicRecordLastRegistrationDate", rootClass);
         myProperties[83] = new PropertyDescriptor("InStudentAcademicRecordFirstRegistrationDate", rootClass);
         myProperties[84] = new PropertyDescriptor("InStudentAcademicRecordLastExamDate", rootClass);
         myProperties[85] = new PropertyDescriptor("InStudentAcademicRecordStatusCode", rootClass);
         myProperties[85].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAcademicRecordStatusCodePropertyEditor.class);
         myProperties[86] = new PropertyDescriptor("InStudentAcademicRecordTemporaryFlag", rootClass);
         myProperties[86].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAcademicRecordTemporaryFlagPropertyEditor.class);
         myProperties[87] = new PropertyDescriptor("InStudentAcademicRecordTemporaryStatusCode", rootClass);
         myProperties[87].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAcademicRecordTemporaryStatusCodePropertyEditor.class);
         myProperties[88] = new PropertyDescriptor("InStudentAcademicRecordDistinctionFlag", rootClass);
         myProperties[88].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAcademicRecordDistinctionFlagPropertyEditor.class);
         myProperties[89] = new PropertyDescriptor("InStudentAcademicRecordComment1", rootClass);
         myProperties[90] = new PropertyDescriptor("InStudentAcademicRecordComment2", rootClass);
         myProperties[91] = new PropertyDescriptor("InStudentAcademicRecordLastUserCode", rootClass);
         myProperties[92] = new PropertyDescriptor("InStudentAcademicRecordFinalMarkControlTotal", rootClass);
         myProperties[93] = new PropertyDescriptor("InStudentAcademicRecordTotalCreditsControlTotal", rootClass);
         myProperties[94] = new PropertyDescriptor("InStudentAcademicRecordAveragePercent", rootClass);
         myProperties[95] = new PropertyDescriptor("InStudentAcademicRecordAdmissionRequirements", rootClass);
         myProperties[96] = new PropertyDescriptor("InStudentAcademicRecordOtherAdmissionRequirements", rootClass);
         myProperties[97] = new PropertyDescriptor("InStudentAcademicRecordWeeksExperience", rootClass);
         myProperties[98] = new PropertyDescriptor("InStudentAcademicRecordPrevQualRegistrationFlag", rootClass);
         myProperties[98].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAcademicRecordPrevQualRegistrationFlagPropertyEditor.class);
         myProperties[99] = new PropertyDescriptor("InStudentAcademicRecordEarliestExemptionUnisaYear", rootClass);
         myProperties[100] = new PropertyDescriptor("InStudentAcademicRecordEarliestExemptionOtherYear", rootClass);
         myProperties[101] = new PropertyDescriptor("InStudentAcademicRecordReqvStatus", rootClass);
         myProperties[102] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[103] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[104] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[105] = new PropertyDescriptor("InStudentAnnualRecordMkDisabilityTypeCode", rootClass);
         myProperties[106] = new PropertyDescriptor("InStudentAnnualRecordFellowStudentFlag", rootClass);
         myProperties[106].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordFellowStudentFlagPropertyEditor.class);
         myProperties[107] = new PropertyDescriptor("InStudentAnnualRecordBursaryType", rootClass);
         myProperties[107].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordBursaryTypePropertyEditor.class);
         myProperties[108] = new PropertyDescriptor("InStudentAnnualRecordBursaryAmount", rootClass);
         myProperties[109] = new PropertyDescriptor("InStudentAnnualRecordMkRegionalOfficeCode", rootClass);
         myProperties[110] = new PropertyDescriptor("InStudentAnnualRecordFirstRegistrationFlag", rootClass);
         myProperties[110].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordFirstRegistrationFlagPropertyEditor.class);
         myProperties[111] = new PropertyDescriptor("InStudentAnnualRecordPreviousUnisaFlag", rootClass);
         myProperties[111].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordPreviousUnisaFlagPropertyEditor.class);
         myProperties[112] = new PropertyDescriptor("InStudentAnnualRecordMkPrevEducationalInstitCode", rootClass);
         myProperties[113] = new PropertyDescriptor("InStudentAnnualRecordPrevEducationalInstitutionYr", rootClass);
         myProperties[114] = new PropertyDescriptor("InStudentAnnualRecordMkOtherEducationalInstitCode", rootClass);
         myProperties[115] = new PropertyDescriptor("InStudentAnnualRecordRegistrationMethodCode", rootClass);
         myProperties[115].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordRegistrationMethodCodePropertyEditor.class);
         myProperties[116] = new PropertyDescriptor("InStudentAnnualRecordDespatchMethodCode", rootClass);
         myProperties[117] = new PropertyDescriptor("InStudentAnnualRecordMkOccupationCode", rootClass);
         myProperties[118] = new PropertyDescriptor("InStudentAnnualRecordMkEconomicSectorCode", rootClass);
         myProperties[119] = new PropertyDescriptor("InStudentAnnualRecordStatusCode", rootClass);
         myProperties[120] = new PropertyDescriptor("InStudentAnnualRecordLibraryAccessLevel", rootClass);
         myProperties[121] = new PropertyDescriptor("InStudentAnnualRecordSpecialLibraryAccessLevel", rootClass);
         myProperties[122] = new PropertyDescriptor("InStudentAnnualRecordMkHighestQualificationCode", rootClass);
         myProperties[123] = new PropertyDescriptor("InStudentAnnualRecordLateRegistrationFlag", rootClass);
         myProperties[123].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordLateRegistrationFlagPropertyEditor.class);
         myProperties[124] = new PropertyDescriptor("InStudentAnnualRecordPersonnelNr", rootClass);
         myProperties[125] = new PropertyDescriptor("InStudentAnnualRecordTefsaApplicationFlag", rootClass);
         myProperties[125].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordTefsaApplicationFlagPropertyEditor.class);
         myProperties[126] = new PropertyDescriptor("InStudentAnnualRecordMatriculationBoardFlag", rootClass);
         myProperties[126].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentAnnualRecordMatriculationBoardFlagPropertyEditor.class);
         myProperties[127] = new PropertyDescriptor("InStudentAnnualRecordSemesterType", rootClass);
         myProperties[128] = new PropertyDescriptor("InStudentAnnualRecordRegDeliveryMethod", rootClass);
         myProperties[129] = new PropertyDescriptor("InStudentAnnualRecordSpecialityCode", rootClass);
         myProperties[130] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[131] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[132] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[133] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[134] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[135] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[136] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[137] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[138] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[139] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[140] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[141] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[142] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[143] = new IndexedPropertyDescriptor("InGSuCsfLineActionBarAction", rootClass, null, null, "getInGSuCsfLineActionBarAction", "setInGSuCsfLineActionBarAction");
         myProperties[144] = new IndexedPropertyDescriptor("InGSuCsfLineActionBarLineReturnCode", rootClass, null, null, "getInGSuCsfLineActionBarLineReturnCode", "setInGSuCsfLineActionBarLineReturnCode");
         myProperties[145] = new IndexedPropertyDescriptor("InGSuCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGSuCsfLineActionBarSelectionFlag", "setInGSuCsfLineActionBarSelectionFlag");
         myProperties[146] = new IndexedPropertyDescriptor("InGSuCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGSuCsfLineActionBarTranslatedAction", "setInGSuCsfLineActionBarTranslatedAction");
         myProperties[147] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitMkCourseStudyUnitCode", rootClass, null, null, "getInGSuStudentStudyUnitMkCourseStudyUnitCode", "setInGSuStudentStudyUnitMkCourseStudyUnitCode");
         myProperties[148] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitMkStudyUnitOptionCode", rootClass, null, null, "getInGSuStudentStudyUnitMkStudyUnitOptionCode", "setInGSuStudentStudyUnitMkStudyUnitOptionCode");
         myProperties[149] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitLanguageCode", rootClass, null, null, "getInGSuStudentStudyUnitLanguageCode", "setInGSuStudentStudyUnitLanguageCode");
         myProperties[149].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentStudyUnitLanguageCodePropertyEditor.class);
         myProperties[150] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitExamYear", rootClass, null, null, "getInGSuStudentStudyUnitExamYear", "setInGSuStudentStudyUnitExamYear");
         myProperties[151] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitMkExamPeriod", rootClass, null, null, "getInGSuStudentStudyUnitMkExamPeriod", "setInGSuStudentStudyUnitMkExamPeriod");
         myProperties[152] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getInGSuStudentStudyUnitSupplementaryExamReasonCode", "setInGSuStudentStudyUnitSupplementaryExamReasonCode");
         myProperties[153] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitSemesterPeriod", rootClass, null, null, "getInGSuStudentStudyUnitSemesterPeriod", "setInGSuStudentStudyUnitSemesterPeriod");
         myProperties[154] = new IndexedPropertyDescriptor("InGSuStudentStudyUnitTutorialFlag", rootClass, null, null, "getInGSuStudentStudyUnitTutorialFlag", "setInGSuStudentStudyUnitTutorialFlag");
         myProperties[155] = new IndexedPropertyDescriptor("InGSuNdpIefSuppliedFlag", rootClass, null, null, "getInGSuNdpIefSuppliedFlag", "setInGSuNdpIefSuppliedFlag");
         myProperties[155].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[156] = new IndexedPropertyDescriptor("InGSuOverrideIefSuppliedFlag", rootClass, null, null, "getInGSuOverrideIefSuppliedFlag", "setInGSuOverrideIefSuppliedFlag");
         myProperties[156].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[157] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitMkCourseStudyUnitCode", rootClass, null, null, "getInhpGSuStudentStudyUnitMkCourseStudyUnitCode", "setInhpGSuStudentStudyUnitMkCourseStudyUnitCode");
         myProperties[158] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitMkStudyUnitOptionCode", rootClass, null, null, "getInhpGSuStudentStudyUnitMkStudyUnitOptionCode", "setInhpGSuStudentStudyUnitMkStudyUnitOptionCode");
         myProperties[159] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitLanguageCode", rootClass, null, null, "getInhpGSuStudentStudyUnitLanguageCode", "setInhpGSuStudentStudyUnitLanguageCode");
         myProperties[159].setPropertyEditorClass(Sruaf01sStudyUnitAdditionStudentStudyUnitLanguageCodePropertyEditor.class);
         myProperties[160] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitExamYear", rootClass, null, null, "getInhpGSuStudentStudyUnitExamYear", "setInhpGSuStudentStudyUnitExamYear");
         myProperties[161] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitMkExamPeriod", rootClass, null, null, "getInhpGSuStudentStudyUnitMkExamPeriod", "setInhpGSuStudentStudyUnitMkExamPeriod");
         myProperties[162] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getInhpGSuStudentStudyUnitSupplementaryExamReasonCode", "setInhpGSuStudentStudyUnitSupplementaryExamReasonCode");
         myProperties[163] = new IndexedPropertyDescriptor("InhpGSuStudentStudyUnitSemesterPeriod", rootClass, null, null, "getInhpGSuStudentStudyUnitSemesterPeriod", "setInhpGSuStudentStudyUnitSemesterPeriod");
         myProperties[164] = new IndexedPropertyDescriptor("InhpGSuNdpIefSuppliedFlag", rootClass, null, null, "getInhpGSuNdpIefSuppliedFlag", "setInhpGSuNdpIefSuppliedFlag");
         myProperties[164].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[165] = new PropertyDescriptor("InWsStudentAccountMkStudentNr", rootClass);
         myProperties[166] = new PropertyDescriptor("InWsStudentAccountBalance", rootClass);
         myProperties[167] = new PropertyDescriptor("InWsStudentAccountUnclaimedCreditFlag", rootClass);
         myProperties[167].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentAccountUnclaimedCreditFlagPropertyEditor.class);
         myProperties[168] = new PropertyDescriptor("InWsStudentAccountLastTransDate", rootClass);
         myProperties[169] = new PropertyDescriptor("InWsStudentAccountTransCount", rootClass);
         myProperties[170] = new PropertyDescriptor("InWsStudentAccountEbankInd", rootClass);
         myProperties[171] = new PropertyDescriptor("InWsStudentAccountCreditBlockedInd", rootClass);
         myProperties[172] = new PropertyDescriptor("InWsStudentAccountRefundBlockedInd", rootClass);
         myProperties[172].setPropertyEditorClass(Sruaf01sStudyUnitAdditionWsStudentAccountRefundBlockedIndPropertyEditor.class);
         myProperties[173] = new PropertyDescriptor("InWsStudentAccountMinRegistrationFee", rootClass);
         myProperties[174] = new PropertyDescriptor("InWsStudentAccountRefundForfeited", rootClass);
         myProperties[175] = new PropertyDescriptor("InWsStudentAccountComments", rootClass);
         myProperties[176] = new PropertyDescriptor("InPpSupervisorWsUserNumber", rootClass);
         myProperties[177] = new PropertyDescriptor("InPpSupervisorWsUserPassword", rootClass);
         myProperties[178] = new PropertyDescriptor("InPpOverrideReasonReason", rootClass);
         myProperties[179] = new PropertyDescriptor("InPpOverrideIefSuppliedFlag", rootClass);
         myProperties[179].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[180] = new PropertyDescriptor("InExpOverrideIefSuppliedFlag", rootClass);
         myProperties[180].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[181] = new PropertyDescriptor("InExpOverrideReasonReason", rootClass);
         myProperties[182] = new PropertyDescriptor("InExpSupervisorWsUserNumber", rootClass);
         myProperties[183] = new PropertyDescriptor("InExpSupervisorWsUserPassword", rootClass);
         myProperties[184] = new PropertyDescriptor("InLateRegOverrideReasonReason", rootClass);
         myProperties[185] = new PropertyDescriptor("InLateRegIefSuppliedFlag", rootClass);
         myProperties[185].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[186] = new PropertyDescriptor("InLateRegSupervisorWsUserNumber", rootClass);
         myProperties[187] = new PropertyDescriptor("InLateRegSupervisorWsUserPassword", rootClass);
         myProperties[188] = new PropertyDescriptor("InCourierAddressExistsIefSuppliedFlag", rootClass);
         myProperties[188].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[189] = new PropertyDescriptor("InDoTempRegistrationIefSuppliedFlag", rootClass);
         myProperties[189].setPropertyEditorClass(Sruaf01sStudyUnitAdditionIefSuppliedFlagPropertyEditor.class);
         myProperties[190] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[191] = new PropertyDescriptor("OutFaxNrCsfStringsString132", rootClass, "getOutFaxNrCsfStringsString132", null);
         myProperties[192] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[193] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
         myProperties[194] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[195] = new PropertyDescriptor("OutFaxOrEmailCsfStringsString132", rootClass, "getOutFaxOrEmailCsfStringsString132", null);
         myProperties[196] = new PropertyDescriptor("OutAcadOverrideIefSuppliedFlag", rootClass, "getOutAcadOverrideIefSuppliedFlag", null);
         myProperties[197] = new PropertyDescriptor("OutWsPrinterCode", rootClass, "getOutWsPrinterCode", null);
         myProperties[198] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagCode", rootClass, null, null, "getOutGOrigWsStudentFlagCode", null);
         myProperties[199] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagDescription", rootClass, null, null, "getOutGOrigWsStudentFlagDescription", null);
         myProperties[200] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagFlagType", rootClass, null, null, "getOutGOrigWsStudentFlagFlagType", null);
         myProperties[201] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagOwner", rootClass, null, null, "getOutGOrigWsStudentFlagOwner", null);
         myProperties[202] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagDocumentType", rootClass, null, null, "getOutGOrigWsStudentFlagDocumentType", null);
         myProperties[203] = new IndexedPropertyDescriptor("OutGOrigWsStudentFlagAdminOnlyFlag", rootClass, null, null, "getOutGOrigWsStudentFlagAdminOnlyFlag", null);
         myProperties[204] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[205] = new PropertyDescriptor("OutWsUserType", rootClass, "getOutWsUserType", null);
         myProperties[206] = new PropertyDescriptor("OutQualificationSpecialityTypeSpecialityCode", rootClass, "getOutQualificationSpecialityTypeSpecialityCode", null);
         myProperties[207] = new PropertyDescriptor("OutQualificationSpecialityTypeExpertFlag", rootClass, "getOutQualificationSpecialityTypeExpertFlag", null);
         myProperties[208] = new PropertyDescriptor("OutAcademicRecordExistsIefSuppliedFlag", rootClass, "getOutAcademicRecordExistsIefSuppliedFlag", null);
         myProperties[209] = new PropertyDescriptor("OutFinalYearIefSuppliedFlag", rootClass, "getOutFinalYearIefSuppliedFlag", null);
         myProperties[210] = new PropertyDescriptor("OutStudentAccountTypeAuditTrailReasonCode", rootClass, "getOutStudentAccountTypeAuditTrailReasonCode", null);
         myProperties[211] = new PropertyDescriptor("OutStudentAccountTypeAuditTrailAuthorisation", rootClass, "getOutStudentAccountTypeAuditTrailAuthorisation", null);
         myProperties[212] = new PropertyDescriptor("OutAccountTypeCode", rootClass, "getOutAccountTypeCode", null);
         myProperties[213] = new PropertyDescriptor("OutAccountTypeBranchCode", rootClass, "getOutAccountTypeBranchCode", null);
         myProperties[214] = new PropertyDescriptor("OutAccountTypeInUseFlag", rootClass, "getOutAccountTypeInUseFlag", null);
         myProperties[215] = new PropertyDescriptor("OutAccountTypeIgnoreFinancialsFlag", rootClass, "getOutAccountTypeIgnoreFinancialsFlag", null);
         myProperties[216] = new PropertyDescriptor("OutAccountTypeEnglishDescription", rootClass, "getOutAccountTypeEnglishDescription", null);
         myProperties[217] = new PropertyDescriptor("OutAccountTypeMinimumFeeFlag", rootClass, "getOutAccountTypeMinimumFeeFlag", null);
         myProperties[218] = new PropertyDescriptor("OutAccountTypeStudyBenefitType", rootClass, "getOutAccountTypeStudyBenefitType", null);
         myProperties[219] = new PropertyDescriptor("OutAccountTypeMkAddressReferenceNo", rootClass, "getOutAccountTypeMkAddressReferenceNo", null);
         myProperties[220] = new PropertyDescriptor("OutDcNdpIefSuppliedFlag", rootClass, "getOutDcNdpIefSuppliedFlag", null);
         myProperties[221] = new PropertyDescriptor("OutDcCertIefSuppliedFlag", rootClass, "getOutDcCertIefSuppliedFlag", null);
         myProperties[222] = new PropertyDescriptor("OutDcUndergradIefSuppliedFlag", rootClass, "getOutDcUndergradIefSuppliedFlag", null);
         myProperties[223] = new PropertyDescriptor("OutDcHedIefSuppliedFlag", rootClass, "getOutDcHedIefSuppliedFlag", null);
         myProperties[224] = new PropertyDescriptor("OutDcBedIefSuppliedFlag", rootClass, "getOutDcBedIefSuppliedFlag", null);
         myProperties[225] = new PropertyDescriptor("OutDcPostgradIefSuppliedFlag", rootClass, "getOutDcPostgradIefSuppliedFlag", null);
         myProperties[226] = new PropertyDescriptor("OutDcHonoursIefSuppliedFlag", rootClass, "getOutDcHonoursIefSuppliedFlag", null);
         myProperties[227] = new PropertyDescriptor("OutDcMastersIefSuppliedFlag", rootClass, "getOutDcMastersIefSuppliedFlag", null);
         myProperties[228] = new PropertyDescriptor("OutDcDoctorsIefSuppliedFlag", rootClass, "getOutDcDoctorsIefSuppliedFlag", null);
         myProperties[229] = new PropertyDescriptor("OutStudentAcademicRecordMkStudentNr", rootClass, "getOutStudentAcademicRecordMkStudentNr", null);
         myProperties[230] = new PropertyDescriptor("OutStudentAcademicRecordMkQualificationCode", rootClass, "getOutStudentAcademicRecordMkQualificationCode", null);
         myProperties[231] = new PropertyDescriptor("OutStudentAcademicRecordLastAcademicRegistrationYear", rootClass, "getOutStudentAcademicRecordLastAcademicRegistrationYear", null);
         myProperties[232] = new PropertyDescriptor("OutStudentAcademicRecordLastRegistrationDate", rootClass, "getOutStudentAcademicRecordLastRegistrationDate", null);
         myProperties[233] = new PropertyDescriptor("OutStudentAcademicRecordFirstRegistrationDate", rootClass, "getOutStudentAcademicRecordFirstRegistrationDate", null);
         myProperties[234] = new PropertyDescriptor("OutStudentAcademicRecordStatusCode", rootClass, "getOutStudentAcademicRecordStatusCode", null);
         myProperties[235] = new PropertyDescriptor("OutStudentAcademicRecordTemporaryFlag", rootClass, "getOutStudentAcademicRecordTemporaryFlag", null);
         myProperties[236] = new PropertyDescriptor("OutStudentAcademicRecordTemporaryStatusCode", rootClass, "getOutStudentAcademicRecordTemporaryStatusCode", null);
         myProperties[237] = new PropertyDescriptor("OutStudentAcademicRecordComment1", rootClass, "getOutStudentAcademicRecordComment1", null);
         myProperties[238] = new PropertyDescriptor("OutStudentAcademicRecordComment2", rootClass, "getOutStudentAcademicRecordComment2", null);
         myProperties[239] = new PropertyDescriptor("OutStudentAcademicRecordLastUserCode", rootClass, "getOutStudentAcademicRecordLastUserCode", null);
         myProperties[240] = new PropertyDescriptor("OutStudentAcademicRecordAdmissionRequirements", rootClass, "getOutStudentAcademicRecordAdmissionRequirements", null);
         myProperties[241] = new PropertyDescriptor("OutStudentAcademicRecordOtherAdmissionRequirements", rootClass, "getOutStudentAcademicRecordOtherAdmissionRequirements", null);
         myProperties[242] = new PropertyDescriptor("OutStudentAcademicRecordPrevQualRegistrationFlag", rootClass, "getOutStudentAcademicRecordPrevQualRegistrationFlag", null);
         myProperties[243] = new PropertyDescriptor("OutStudentAcademicRecordActualCompletionYear", rootClass, "getOutStudentAcademicRecordActualCompletionYear", null);
         myProperties[244] = new PropertyDescriptor("OutStudentAcademicRecordMkGraduationCeremonyCode", rootClass, "getOutStudentAcademicRecordMkGraduationCeremonyCode", null);
         myProperties[245] = new PropertyDescriptor("OutStudentAcademicRecordGraduationCeremonyDate", rootClass, "getOutStudentAcademicRecordGraduationCeremonyDate", null);
         myProperties[246] = new PropertyDescriptor("OutStudentAcademicRecordLastExamDate", rootClass, "getOutStudentAcademicRecordLastExamDate", null);
         myProperties[247] = new PropertyDescriptor("OutStudentAcademicRecordDistinctionFlag", rootClass, "getOutStudentAcademicRecordDistinctionFlag", null);
         myProperties[248] = new PropertyDescriptor("OutStudentAcademicRecordFinalMarkControlTotal", rootClass, "getOutStudentAcademicRecordFinalMarkControlTotal", null);
         myProperties[249] = new PropertyDescriptor("OutStudentAcademicRecordTotalCreditsControlTotal", rootClass, "getOutStudentAcademicRecordTotalCreditsControlTotal", null);
         myProperties[250] = new PropertyDescriptor("OutStudentAcademicRecordAveragePercent", rootClass, "getOutStudentAcademicRecordAveragePercent", null);
         myProperties[251] = new PropertyDescriptor("OutStudentAcademicRecordWeeksExperience", rootClass, "getOutStudentAcademicRecordWeeksExperience", null);
         myProperties[252] = new PropertyDescriptor("OutStudentAcademicRecordEarliestExemptionUnisaYear", rootClass, "getOutStudentAcademicRecordEarliestExemptionUnisaYear", null);
         myProperties[253] = new PropertyDescriptor("OutStudentAcademicRecordEarliestExemptionOtherYear", rootClass, "getOutStudentAcademicRecordEarliestExemptionOtherYear", null);
         myProperties[254] = new PropertyDescriptor("OutStudentAcademicRecordReqvStatus", rootClass, "getOutStudentAcademicRecordReqvStatus", null);
         myProperties[255] = new PropertyDescriptor("OutStudentAnnualRecordMkStudentNr", rootClass, "getOutStudentAnnualRecordMkStudentNr", null);
         myProperties[256] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicYear", rootClass, "getOutStudentAnnualRecordMkAcademicYear", null);
         myProperties[257] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicPeriod", rootClass, "getOutStudentAnnualRecordMkAcademicPeriod", null);
         myProperties[258] = new PropertyDescriptor("OutStudentAnnualRecordMkDisabilityTypeCode", rootClass, "getOutStudentAnnualRecordMkDisabilityTypeCode", null);
         myProperties[259] = new PropertyDescriptor("OutStudentAnnualRecordFellowStudentFlag", rootClass, "getOutStudentAnnualRecordFellowStudentFlag", null);
         myProperties[260] = new PropertyDescriptor("OutStudentAnnualRecordBursaryType", rootClass, "getOutStudentAnnualRecordBursaryType", null);
         myProperties[261] = new PropertyDescriptor("OutStudentAnnualRecordBursaryAmount", rootClass, "getOutStudentAnnualRecordBursaryAmount", null);
         myProperties[262] = new PropertyDescriptor("OutStudentAnnualRecordMkRegionalOfficeCode", rootClass, "getOutStudentAnnualRecordMkRegionalOfficeCode", null);
         myProperties[263] = new PropertyDescriptor("OutStudentAnnualRecordFirstRegistrationFlag", rootClass, "getOutStudentAnnualRecordFirstRegistrationFlag", null);
         myProperties[264] = new PropertyDescriptor("OutStudentAnnualRecordPreviousUnisaFlag", rootClass, "getOutStudentAnnualRecordPreviousUnisaFlag", null);
         myProperties[265] = new PropertyDescriptor("OutStudentAnnualRecordMkPrevEducationalInstitCode", rootClass, "getOutStudentAnnualRecordMkPrevEducationalInstitCode", null);
         myProperties[266] = new PropertyDescriptor("OutStudentAnnualRecordPrevEducationalInstitutionYr", rootClass, "getOutStudentAnnualRecordPrevEducationalInstitutionYr", null);
         myProperties[267] = new PropertyDescriptor("OutStudentAnnualRecordMkOtherEducationalInstitCode", rootClass, "getOutStudentAnnualRecordMkOtherEducationalInstitCode", null);
         myProperties[268] = new PropertyDescriptor("OutStudentAnnualRecordRegistrationMethodCode", rootClass, "getOutStudentAnnualRecordRegistrationMethodCode", null);
         myProperties[269] = new PropertyDescriptor("OutStudentAnnualRecordDespatchMethodCode", rootClass, "getOutStudentAnnualRecordDespatchMethodCode", null);
         myProperties[270] = new PropertyDescriptor("OutStudentAnnualRecordMkOccupationCode", rootClass, "getOutStudentAnnualRecordMkOccupationCode", null);
         myProperties[271] = new PropertyDescriptor("OutStudentAnnualRecordMkEconomicSectorCode", rootClass, "getOutStudentAnnualRecordMkEconomicSectorCode", null);
         myProperties[272] = new PropertyDescriptor("OutStudentAnnualRecordStatusCode", rootClass, "getOutStudentAnnualRecordStatusCode", null);
         myProperties[273] = new PropertyDescriptor("OutStudentAnnualRecordLibraryAccessLevel", rootClass, "getOutStudentAnnualRecordLibraryAccessLevel", null);
         myProperties[274] = new PropertyDescriptor("OutStudentAnnualRecordSpecialLibraryAccessLevel", rootClass, "getOutStudentAnnualRecordSpecialLibraryAccessLevel", null);
         myProperties[275] = new PropertyDescriptor("OutStudentAnnualRecordMkHighestQualificationCode", rootClass, "getOutStudentAnnualRecordMkHighestQualificationCode", null);
         myProperties[276] = new PropertyDescriptor("OutStudentAnnualRecordLateRegistrationFlag", rootClass, "getOutStudentAnnualRecordLateRegistrationFlag", null);
         myProperties[277] = new PropertyDescriptor("OutStudentAnnualRecordPersonnelNr", rootClass, "getOutStudentAnnualRecordPersonnelNr", null);
         myProperties[278] = new PropertyDescriptor("OutStudentAnnualRecordTefsaApplicationFlag", rootClass, "getOutStudentAnnualRecordTefsaApplicationFlag", null);
         myProperties[279] = new PropertyDescriptor("OutStudentAnnualRecordMatriculationBoardFlag", rootClass, "getOutStudentAnnualRecordMatriculationBoardFlag", null);
         myProperties[280] = new PropertyDescriptor("OutStudentAnnualRecordSemesterType", rootClass, "getOutStudentAnnualRecordSemesterType", null);
         myProperties[281] = new PropertyDescriptor("OutStudentAnnualRecordRegistrationDate", rootClass, "getOutStudentAnnualRecordRegistrationDate", null);
         myProperties[282] = new PropertyDescriptor("OutStudentAnnualRecordRegDeliveryMethod", rootClass, "getOutStudentAnnualRecordRegDeliveryMethod", null);
         myProperties[283] = new PropertyDescriptor("OutStudentAnnualRecordSpecialityCode", rootClass, "getOutStudentAnnualRecordSpecialityCode", null);
         myProperties[284] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[285] = new PropertyDescriptor("OutWsStudentSpecialCharacterFlag", rootClass, "getOutWsStudentSpecialCharacterFlag", null);
         myProperties[286] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[287] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[288] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[289] = new PropertyDescriptor("OutWsStudentPreviousSurname", rootClass, "getOutWsStudentPreviousSurname", null);
         myProperties[290] = new PropertyDescriptor("OutWsStudentSquashCode", rootClass, "getOutWsStudentSquashCode", null);
         myProperties[291] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[292] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[293] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[294] = new PropertyDescriptor("OutWsStudentGender", rootClass, "getOutWsStudentGender", null);
         myProperties[295] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[296] = new PropertyDescriptor("OutWsStudentMkHomeLanguage", rootClass, "getOutWsStudentMkHomeLanguage", null);
         myProperties[297] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[298] = new PropertyDescriptor("OutWsStudentDeceasedFlag", rootClass, "getOutWsStudentDeceasedFlag", null);
         myProperties[299] = new PropertyDescriptor("OutWsStudentLibraryBlackList", rootClass, "getOutWsStudentLibraryBlackList", null);
         myProperties[300] = new PropertyDescriptor("OutWsStudentExamFeesDebtFlag", rootClass, "getOutWsStudentExamFeesDebtFlag", null);
         myProperties[301] = new PropertyDescriptor("OutWsStudentDisciplinaryIncident", rootClass, "getOutWsStudentDisciplinaryIncident", null);
         myProperties[302] = new PropertyDescriptor("OutWsStudentPostGraduateStudiesApproved", rootClass, "getOutWsStudentPostGraduateStudiesApproved", null);
         myProperties[303] = new PropertyDescriptor("OutWsStudentPhasedOutStatus", rootClass, "getOutWsStudentPhasedOutStatus", null);
         myProperties[304] = new PropertyDescriptor("OutWsStudentFinancialBlockFlag", rootClass, "getOutWsStudentFinancialBlockFlag", null);
         myProperties[305] = new PropertyDescriptor("OutWsStudentTwinFlag", rootClass, "getOutWsStudentTwinFlag", null);
         myProperties[306] = new PropertyDescriptor("OutWsStudentAccessRestrictedFlag", rootClass, "getOutWsStudentAccessRestrictedFlag", null);
         myProperties[307] = new PropertyDescriptor("OutWsStudentNumberRestrictedFlag", rootClass, "getOutWsStudentNumberRestrictedFlag", null);
         myProperties[308] = new PropertyDescriptor("OutWsStudentUnisaUndergradYearsRegistered", rootClass, "getOutWsStudentUnisaUndergradYearsRegistered", null);
         myProperties[309] = new PropertyDescriptor("OutWsStudentUnisaHonoursYearsRegistered", rootClass, "getOutWsStudentUnisaHonoursYearsRegistered", null);
         myProperties[310] = new PropertyDescriptor("OutWsStudentUnisaMastersYearsRegistered", rootClass, "getOutWsStudentUnisaMastersYearsRegistered", null);
         myProperties[311] = new PropertyDescriptor("OutWsStudentUnisaDoctrateYearsRegistered", rootClass, "getOutWsStudentUnisaDoctrateYearsRegistered", null);
         myProperties[312] = new PropertyDescriptor("OutWsStudentOtherMastersYearsRegistered", rootClass, "getOutWsStudentOtherMastersYearsRegistered", null);
         myProperties[313] = new PropertyDescriptor("OutWsStudentOtherDoctrateYearsRegistered", rootClass, "getOutWsStudentOtherDoctrateYearsRegistered", null);
         myProperties[314] = new PropertyDescriptor("OutWsStudentPreviouslyPostGraduateFlag", rootClass, "getOutWsStudentPreviouslyPostGraduateFlag", null);
         myProperties[315] = new PropertyDescriptor("OutWsStudentPreviouslyUnisaPostGradFlag", rootClass, "getOutWsStudentPreviouslyUnisaPostGradFlag", null);
         myProperties[316] = new PropertyDescriptor("OutWsStudentResultBlockFlag", rootClass, "getOutWsStudentResultBlockFlag", null);
         myProperties[317] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[318] = new PropertyDescriptor("OutWsStudentMkLastAcademicPeriod", rootClass, "getOutWsStudentMkLastAcademicPeriod", null);
         myProperties[319] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[320] = new PropertyDescriptor("OutWsStudentSmartCardIssued", rootClass, "getOutWsStudentSmartCardIssued", null);
         myProperties[321] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[322] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[323] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[324] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[325] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[326] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[327] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[328] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[329] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[330] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[331] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[332] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[333] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[334] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[335] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[336] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[337] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[338] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[339] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[340] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[341] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[342] = new IndexedPropertyDescriptor("OutGSuCsfLineActionBarAction", rootClass, null, null, "getOutGSuCsfLineActionBarAction", null);
         myProperties[343] = new IndexedPropertyDescriptor("OutGSuCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGSuCsfLineActionBarLineReturnCode", null);
         myProperties[344] = new IndexedPropertyDescriptor("OutGSuCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGSuCsfLineActionBarSelectionFlag", null);
         myProperties[345] = new IndexedPropertyDescriptor("OutGSuCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGSuCsfLineActionBarTranslatedAction", null);
         myProperties[346] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitMkCourseStudyUnitCode", rootClass, null, null, "getOutGSuStudentStudyUnitMkCourseStudyUnitCode", null);
         myProperties[347] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitMkStudyUnitOptionCode", rootClass, null, null, "getOutGSuStudentStudyUnitMkStudyUnitOptionCode", null);
         myProperties[348] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitLanguageCode", rootClass, null, null, "getOutGSuStudentStudyUnitLanguageCode", null);
         myProperties[349] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitExamYear", rootClass, null, null, "getOutGSuStudentStudyUnitExamYear", null);
         myProperties[350] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitMkExamPeriod", rootClass, null, null, "getOutGSuStudentStudyUnitMkExamPeriod", null);
         myProperties[351] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getOutGSuStudentStudyUnitSupplementaryExamReasonCode", null);
         myProperties[352] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitSemesterPeriod", rootClass, null, null, "getOutGSuStudentStudyUnitSemesterPeriod", null);
         myProperties[353] = new IndexedPropertyDescriptor("OutGSuStudentStudyUnitTutorialFlag", rootClass, null, null, "getOutGSuStudentStudyUnitTutorialFlag", null);
         myProperties[354] = new IndexedPropertyDescriptor("OutGSuNdpIefSuppliedFlag", rootClass, null, null, "getOutGSuNdpIefSuppliedFlag", null);
         myProperties[355] = new IndexedPropertyDescriptor("OutGSuOverrideIefSuppliedFlag", rootClass, null, null, "getOutGSuOverrideIefSuppliedFlag", null);
         myProperties[356] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitMkCourseStudyUnitCode", rootClass, null, null, "getOuthpGSuStudentStudyUnitMkCourseStudyUnitCode", null);
         myProperties[357] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitMkStudyUnitOptionCode", rootClass, null, null, "getOuthpGSuStudentStudyUnitMkStudyUnitOptionCode", null);
         myProperties[358] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitLanguageCode", rootClass, null, null, "getOuthpGSuStudentStudyUnitLanguageCode", null);
         myProperties[359] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitExamYear", rootClass, null, null, "getOuthpGSuStudentStudyUnitExamYear", null);
         myProperties[360] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitMkExamPeriod", rootClass, null, null, "getOuthpGSuStudentStudyUnitMkExamPeriod", null);
         myProperties[361] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getOuthpGSuStudentStudyUnitSupplementaryExamReasonCode", null);
         myProperties[362] = new IndexedPropertyDescriptor("OuthpGSuStudentStudyUnitSemesterPeriod", rootClass, null, null, "getOuthpGSuStudentStudyUnitSemesterPeriod", null);
         myProperties[363] = new IndexedPropertyDescriptor("OuthpGSuNdpIefSuppliedFlag", rootClass, null, null, "getOuthpGSuNdpIefSuppliedFlag", null);
         myProperties[364] = new PropertyDescriptor("OutStudentCsfStringsString50", rootClass, "getOutStudentCsfStringsString50", null);
         myProperties[365] = new PropertyDescriptor("OutWsStudentAccountMkStudentNr", rootClass, "getOutWsStudentAccountMkStudentNr", null);
         myProperties[366] = new PropertyDescriptor("OutWsStudentAccountBalance", rootClass, "getOutWsStudentAccountBalance", null);
         myProperties[367] = new PropertyDescriptor("OutWsStudentAccountUnclaimedCreditFlag", rootClass, "getOutWsStudentAccountUnclaimedCreditFlag", null);
         myProperties[368] = new PropertyDescriptor("OutWsStudentAccountLastTransDate", rootClass, "getOutWsStudentAccountLastTransDate", null);
         myProperties[369] = new PropertyDescriptor("OutWsStudentAccountTransCount", rootClass, "getOutWsStudentAccountTransCount", null);
         myProperties[370] = new PropertyDescriptor("OutWsStudentAccountEbankInd", rootClass, "getOutWsStudentAccountEbankInd", null);
         myProperties[371] = new PropertyDescriptor("OutWsStudentAccountCreditBlockedInd", rootClass, "getOutWsStudentAccountCreditBlockedInd", null);
         myProperties[372] = new PropertyDescriptor("OutWsStudentAccountRefundBlockedInd", rootClass, "getOutWsStudentAccountRefundBlockedInd", null);
         myProperties[373] = new PropertyDescriptor("OutWsStudentAccountMinRegistrationFee", rootClass, "getOutWsStudentAccountMinRegistrationFee", null);
         myProperties[374] = new PropertyDescriptor("OutWsStudentAccountRefundForfeited", rootClass, "getOutWsStudentAccountRefundForfeited", null);
         myProperties[375] = new PropertyDescriptor("OutWsStudentAccountComments", rootClass, "getOutWsStudentAccountComments", null);
         myProperties[376] = new PropertyDescriptor("OutCourierAddressExistsIefSuppliedFlag", rootClass, "getOutCourierAddressExistsIefSuppliedFlag", null);
         myProperties[377] = new PropertyDescriptor("OutDoTempRegistrationIefSuppliedFlag", rootClass, "getOutDoTempRegistrationIefSuppliedFlag", null);
         myProperties[378] = new PropertyDescriptor("OutWsFunctionNumber", rootClass, "getOutWsFunctionNumber", null);
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
//         callMethod = Sruaf01sStudyUnitAddition.class.getMethod("execute", args);
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
