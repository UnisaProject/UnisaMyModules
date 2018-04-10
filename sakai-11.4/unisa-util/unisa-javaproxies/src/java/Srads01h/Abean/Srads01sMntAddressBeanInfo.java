package Srads01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srads01sMntAddressBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srads01sMntAddress.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srads01sMntAddress.class);
      bd.setDisplayName(Srads01sMntAddress.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InPhysicalOkIefSuppliedFlag", rootClass);
         myProperties[14].setPropertyEditorClass(Srads01sMntAddressIefSuppliedFlagPropertyEditor.class);
         myProperties[15] = new PropertyDescriptor("InPhysicalAdrChgIefSuppliedFlag", rootClass);
         myProperties[15].setPropertyEditorClass(Srads01sMntAddressIefSuppliedFlagPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InAddressChgIefSuppliedFlag", rootClass);
         myProperties[16].setPropertyEditorClass(Srads01sMntAddressIefSuppliedFlagPropertyEditor.class);
         myProperties[17] = new PropertyDescriptor("InPOBoxCsfStringsString1", rootClass);
         myProperties[18] = new PropertyDescriptor("InSupervisorWsUserNumber", rootClass);
         myProperties[19] = new PropertyDescriptor("InSupervisorWsUserPassword", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsAddressV2ReferenceNo", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsAddressV2AddressReferenceFlag", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsAddressV2Type", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsAddressV2Category", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsAddressV2CategoryDescription", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsAddressV2AddressLine1", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsAddressV2AddressLine2", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsAddressV2AddressLine3", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsAddressV2AddressLine4", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsAddressV2AddressLine5", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsAddressV2AddressLine6", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsAddressV2PostalCode", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsAddressV2HomeNumber", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsAddressV2WorkNumber", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsAddressV2FaxNumber", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsAddressV2CellNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InWsAddressV2EmailAddress", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsAddressV2ErrorCode", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsAddressV2UserNumber", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsMagisterialDistrictCode", rootClass);
         myProperties[40] = new PropertyDescriptor("InWsMagisterialDistrictEngDescription", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsCountryCode", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsCountryEngDescription", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[50] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[56] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[57] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[58] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[59] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[60] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[61] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[62] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[63] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[64] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[65] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[66] = new PropertyDescriptor("InPhysicalCsfStringsString1", rootClass);
         myProperties[67] = new PropertyDescriptor("InPhysicalWsAddressV2ReferenceNo", rootClass);
         myProperties[68] = new PropertyDescriptor("InPhysicalWsAddressV2Type", rootClass);
         myProperties[69] = new PropertyDescriptor("InPhysicalWsAddressV2Category", rootClass);
         myProperties[70] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine1", rootClass);
         myProperties[71] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine2", rootClass);
         myProperties[72] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine3", rootClass);
         myProperties[73] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine4", rootClass);
         myProperties[74] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine5", rootClass);
         myProperties[75] = new PropertyDescriptor("InPhysicalWsAddressV2AddressLine6", rootClass);
         myProperties[76] = new PropertyDescriptor("InPhysicalWsAddressV2PostalCode", rootClass);
         myProperties[77] = new PropertyDescriptor("InPhysicalWsAddressV2UserNumber", rootClass);
         myProperties[78] = new PropertyDescriptor("InPhysicalWsAddressV2CourierContactNo", rootClass);
         myProperties[79] = new PropertyDescriptor("InPhysicalPOBoxCsfStringsString1", rootClass);
         myProperties[80] = new PropertyDescriptor("InCourierWsAddressV2ReferenceNo", rootClass);
         myProperties[81] = new PropertyDescriptor("InCourierWsAddressV2Type", rootClass);
         myProperties[82] = new PropertyDescriptor("InCourierWsAddressV2Category", rootClass);
         myProperties[83] = new PropertyDescriptor("InCourierWsAddressV2AddressLine1", rootClass);
         myProperties[84] = new PropertyDescriptor("InCourierWsAddressV2AddressLine2", rootClass);
         myProperties[85] = new PropertyDescriptor("InCourierWsAddressV2AddressLine3", rootClass);
         myProperties[86] = new PropertyDescriptor("InCourierWsAddressV2AddressLine4", rootClass);
         myProperties[87] = new PropertyDescriptor("InCourierWsAddressV2AddressLine5", rootClass);
         myProperties[88] = new PropertyDescriptor("InCourierWsAddressV2AddressLine6", rootClass);
         myProperties[89] = new PropertyDescriptor("InCourierWsAddressV2PostalCode", rootClass);
         myProperties[90] = new PropertyDescriptor("InCourierWsAddressV2UserNumber", rootClass);
         myProperties[91] = new PropertyDescriptor("InCourierWsAddressV2CourierContactNo", rootClass);
         myProperties[92] = new PropertyDescriptor("OutPhysicalOkIefSuppliedFlag", rootClass, "getOutPhysicalOkIefSuppliedFlag", null);
         myProperties[93] = new PropertyDescriptor("OutPhysicalAdrChgIefSuppliedFlag", rootClass, "getOutPhysicalAdrChgIefSuppliedFlag", null);
         myProperties[94] = new PropertyDescriptor("OutAddressChgIefSuppliedFlag", rootClass, "getOutAddressChgIefSuppliedFlag", null);
         myProperties[95] = new PropertyDescriptor("OutNameCsfStringsString50", rootClass, "getOutNameCsfStringsString50", null);
         myProperties[96] = new PropertyDescriptor("OutPOBoxCsfStringsString1", rootClass, "getOutPOBoxCsfStringsString1", null);
         myProperties[97] = new PropertyDescriptor("OutSupervisorWsUserNumber", rootClass, "getOutSupervisorWsUserNumber", null);
         myProperties[98] = new PropertyDescriptor("OutSupervisorWsUserPassword", rootClass, "getOutSupervisorWsUserPassword", null);
         myProperties[99] = new PropertyDescriptor("OutWsAddressV2ReferenceNo", rootClass, "getOutWsAddressV2ReferenceNo", null);
         myProperties[100] = new PropertyDescriptor("OutWsAddressV2AddressReferenceFlag", rootClass, "getOutWsAddressV2AddressReferenceFlag", null);
         myProperties[101] = new PropertyDescriptor("OutWsAddressV2Type", rootClass, "getOutWsAddressV2Type", null);
         myProperties[102] = new PropertyDescriptor("OutWsAddressV2Category", rootClass, "getOutWsAddressV2Category", null);
         myProperties[103] = new PropertyDescriptor("OutWsAddressV2CategoryDescription", rootClass, "getOutWsAddressV2CategoryDescription", null);
         myProperties[104] = new PropertyDescriptor("OutWsAddressV2AddressLine1", rootClass, "getOutWsAddressV2AddressLine1", null);
         myProperties[105] = new PropertyDescriptor("OutWsAddressV2AddressLine2", rootClass, "getOutWsAddressV2AddressLine2", null);
         myProperties[106] = new PropertyDescriptor("OutWsAddressV2AddressLine3", rootClass, "getOutWsAddressV2AddressLine3", null);
         myProperties[107] = new PropertyDescriptor("OutWsAddressV2AddressLine4", rootClass, "getOutWsAddressV2AddressLine4", null);
         myProperties[108] = new PropertyDescriptor("OutWsAddressV2AddressLine5", rootClass, "getOutWsAddressV2AddressLine5", null);
         myProperties[109] = new PropertyDescriptor("OutWsAddressV2AddressLine6", rootClass, "getOutWsAddressV2AddressLine6", null);
         myProperties[110] = new PropertyDescriptor("OutWsAddressV2PostalCode", rootClass, "getOutWsAddressV2PostalCode", null);
         myProperties[111] = new PropertyDescriptor("OutWsAddressV2HomeNumber", rootClass, "getOutWsAddressV2HomeNumber", null);
         myProperties[112] = new PropertyDescriptor("OutWsAddressV2WorkNumber", rootClass, "getOutWsAddressV2WorkNumber", null);
         myProperties[113] = new PropertyDescriptor("OutWsAddressV2FaxNumber", rootClass, "getOutWsAddressV2FaxNumber", null);
         myProperties[114] = new PropertyDescriptor("OutWsAddressV2CellNumber", rootClass, "getOutWsAddressV2CellNumber", null);
         myProperties[115] = new PropertyDescriptor("OutWsAddressV2EmailAddress", rootClass, "getOutWsAddressV2EmailAddress", null);
         myProperties[116] = new PropertyDescriptor("OutWsAddressV2ErrorCode", rootClass, "getOutWsAddressV2ErrorCode", null);
         myProperties[117] = new PropertyDescriptor("OutWsAddressV2UserNumber", rootClass, "getOutWsAddressV2UserNumber", null);
         myProperties[118] = new PropertyDescriptor("OutLclWsAddressV2ReferenceNo", rootClass, "getOutLclWsAddressV2ReferenceNo", null);
         myProperties[119] = new PropertyDescriptor("OutLclWsAddressV2AddressReferenceFlag", rootClass, "getOutLclWsAddressV2AddressReferenceFlag", null);
         myProperties[120] = new PropertyDescriptor("OutLclWsAddressV2Type", rootClass, "getOutLclWsAddressV2Type", null);
         myProperties[121] = new PropertyDescriptor("OutLclWsAddressV2Category", rootClass, "getOutLclWsAddressV2Category", null);
         myProperties[122] = new PropertyDescriptor("OutLclWsAddressV2CategoryDescription", rootClass, "getOutLclWsAddressV2CategoryDescription", null);
         myProperties[123] = new PropertyDescriptor("OutLclWsAddressV2AddressLine1", rootClass, "getOutLclWsAddressV2AddressLine1", null);
         myProperties[124] = new PropertyDescriptor("OutLclWsAddressV2AddressLine2", rootClass, "getOutLclWsAddressV2AddressLine2", null);
         myProperties[125] = new PropertyDescriptor("OutLclWsAddressV2AddressLine3", rootClass, "getOutLclWsAddressV2AddressLine3", null);
         myProperties[126] = new PropertyDescriptor("OutLclWsAddressV2AddressLine4", rootClass, "getOutLclWsAddressV2AddressLine4", null);
         myProperties[127] = new PropertyDescriptor("OutLclWsAddressV2AddressLine5", rootClass, "getOutLclWsAddressV2AddressLine5", null);
         myProperties[128] = new PropertyDescriptor("OutLclWsAddressV2AddressLine6", rootClass, "getOutLclWsAddressV2AddressLine6", null);
         myProperties[129] = new PropertyDescriptor("OutLclWsAddressV2PostalCode", rootClass, "getOutLclWsAddressV2PostalCode", null);
         myProperties[130] = new PropertyDescriptor("OutLclWsAddressV2HomeNumber", rootClass, "getOutLclWsAddressV2HomeNumber", null);
         myProperties[131] = new PropertyDescriptor("OutLclWsAddressV2WorkNumber", rootClass, "getOutLclWsAddressV2WorkNumber", null);
         myProperties[132] = new PropertyDescriptor("OutLclWsAddressV2FaxNumber", rootClass, "getOutLclWsAddressV2FaxNumber", null);
         myProperties[133] = new PropertyDescriptor("OutLclWsAddressV2CellNumber", rootClass, "getOutLclWsAddressV2CellNumber", null);
         myProperties[134] = new PropertyDescriptor("OutLclWsAddressV2EmailAddress", rootClass, "getOutLclWsAddressV2EmailAddress", null);
         myProperties[135] = new PropertyDescriptor("OutLclWsAddressV2CourierContactNo", rootClass, "getOutLclWsAddressV2CourierContactNo", null);
         myProperties[136] = new PropertyDescriptor("OutLclWsMagisterialDistrictCode", rootClass, "getOutLclWsMagisterialDistrictCode", null);
         myProperties[137] = new PropertyDescriptor("OutLclWsMagisterialDistrictEngDescription", rootClass, "getOutLclWsMagisterialDistrictEngDescription", null);
         myProperties[138] = new PropertyDescriptor("OutLclWsPostalCodeCode", rootClass, "getOutLclWsPostalCodeCode", null);
         myProperties[139] = new PropertyDescriptor("OutLclWsPostalCodeDescription", rootClass, "getOutLclWsPostalCodeDescription", null);
         myProperties[140] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[141] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[142] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[143] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[144] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[145] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[146] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[147] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[148] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[149] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[150] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[151] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[152] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[153] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[154] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[155] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[156] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[157] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[158] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[159] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[161] = new PropertyDescriptor("OutLclWizfuncReportingControlPrinterCode", rootClass, "getOutLclWizfuncReportingControlPrinterCode", null);
         myProperties[162] = new PropertyDescriptor("OutLclWsAddressChangeNoticeStudentNr", rootClass, "getOutLclWsAddressChangeNoticeStudentNr", null);
         myProperties[163] = new PropertyDescriptor("OutLclWsAddressChangeNoticeMkUserCode", rootClass, "getOutLclWsAddressChangeNoticeMkUserCode", null);
         myProperties[164] = new PropertyDescriptor("OutLclAdditionalExamArrangementMkStudentNr", rootClass, "getOutLclAdditionalExamArrangementMkStudentNr", null);
         myProperties[165] = new PropertyDescriptor("OutLclAdditionalExamArrangementExamYear", rootClass, "getOutLclAdditionalExamArrangementExamYear", null);
         myProperties[166] = new PropertyDescriptor("OutLclAdditionalExamArrangementMkExamPeriod", rootClass, "getOutLclAdditionalExamArrangementMkExamPeriod", null);
         myProperties[167] = new PropertyDescriptor("OutLclAdditionalExamArrangementSequenceNr", rootClass, "getOutLclAdditionalExamArrangementSequenceNr", null);
         myProperties[168] = new PropertyDescriptor("OutLclAdditionalExamArrangementMkStudyUnitCode", rootClass, "getOutLclAdditionalExamArrangementMkStudyUnitCode", null);
         myProperties[169] = new PropertyDescriptor("OutLclLecturerStudentCardRequestMkStudentNr", rootClass, "getOutLclLecturerStudentCardRequestMkStudentNr", null);
         myProperties[170] = new PropertyDescriptor("OutLclLecturerStudentCardRequestChangeCode", rootClass, "getOutLclLecturerStudentCardRequestChangeCode", null);
         myProperties[171] = new PropertyDescriptor("OutLclPrimaryIefSuppliedFlag", rootClass, "getOutLclPrimaryIefSuppliedFlag", null);
         myProperties[172] = new PropertyDescriptor("OutLclWsExamPeriodCode", rootClass, "getOutLclWsExamPeriodCode", null);
         myProperties[173] = new PropertyDescriptor("OutLclWsMethodResultReturnCode", rootClass, "getOutLclWsMethodResultReturnCode", null);
         myProperties[174] = new PropertyDescriptor("OutWsMagisterialDistrictCode", rootClass, "getOutWsMagisterialDistrictCode", null);
         myProperties[175] = new PropertyDescriptor("OutWsMagisterialDistrictEngDescription", rootClass, "getOutWsMagisterialDistrictEngDescription", null);
         myProperties[176] = new PropertyDescriptor("OutWsCountryCode", rootClass, "getOutWsCountryCode", null);
         myProperties[177] = new PropertyDescriptor("OutWsCountryEngDescription", rootClass, "getOutWsCountryEngDescription", null);
         myProperties[178] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[179] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[180] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[181] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[182] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[183] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[184] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[185] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[186] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[187] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[188] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[189] = new PropertyDescriptor("OutPhysicalCsfStringsString1", rootClass, "getOutPhysicalCsfStringsString1", null);
         myProperties[190] = new PropertyDescriptor("OutPhysicalWsAddressV2ReferenceNo", rootClass, "getOutPhysicalWsAddressV2ReferenceNo", null);
         myProperties[191] = new PropertyDescriptor("OutPhysicalWsAddressV2Type", rootClass, "getOutPhysicalWsAddressV2Type", null);
         myProperties[192] = new PropertyDescriptor("OutPhysicalWsAddressV2Category", rootClass, "getOutPhysicalWsAddressV2Category", null);
         myProperties[193] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine1", rootClass, "getOutPhysicalWsAddressV2AddressLine1", null);
         myProperties[194] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine2", rootClass, "getOutPhysicalWsAddressV2AddressLine2", null);
         myProperties[195] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine3", rootClass, "getOutPhysicalWsAddressV2AddressLine3", null);
         myProperties[196] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine4", rootClass, "getOutPhysicalWsAddressV2AddressLine4", null);
         myProperties[197] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine5", rootClass, "getOutPhysicalWsAddressV2AddressLine5", null);
         myProperties[198] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine6", rootClass, "getOutPhysicalWsAddressV2AddressLine6", null);
         myProperties[199] = new PropertyDescriptor("OutPhysicalWsAddressV2PostalCode", rootClass, "getOutPhysicalWsAddressV2PostalCode", null);
         myProperties[200] = new PropertyDescriptor("OutPhysicalWsAddressV2UserNumber", rootClass, "getOutPhysicalWsAddressV2UserNumber", null);
         myProperties[201] = new PropertyDescriptor("OutPhysicalWsAddressV2CourierContactNo", rootClass, "getOutPhysicalWsAddressV2CourierContactNo", null);
         myProperties[202] = new PropertyDescriptor("OutPhysicalPOBoxCsfStringsString1", rootClass, "getOutPhysicalPOBoxCsfStringsString1", null);
         myProperties[203] = new PropertyDescriptor("OutCourierWsAddressV2ReferenceNo", rootClass, "getOutCourierWsAddressV2ReferenceNo", null);
         myProperties[204] = new PropertyDescriptor("OutCourierWsAddressV2Type", rootClass, "getOutCourierWsAddressV2Type", null);
         myProperties[205] = new PropertyDescriptor("OutCourierWsAddressV2Category", rootClass, "getOutCourierWsAddressV2Category", null);
         myProperties[206] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine1", rootClass, "getOutCourierWsAddressV2AddressLine1", null);
         myProperties[207] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine2", rootClass, "getOutCourierWsAddressV2AddressLine2", null);
         myProperties[208] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine3", rootClass, "getOutCourierWsAddressV2AddressLine3", null);
         myProperties[209] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine4", rootClass, "getOutCourierWsAddressV2AddressLine4", null);
         myProperties[210] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine5", rootClass, "getOutCourierWsAddressV2AddressLine5", null);
         myProperties[211] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine6", rootClass, "getOutCourierWsAddressV2AddressLine6", null);
         myProperties[212] = new PropertyDescriptor("OutCourierWsAddressV2PostalCode", rootClass, "getOutCourierWsAddressV2PostalCode", null);
         myProperties[213] = new PropertyDescriptor("OutCourierWsAddressV2UserNumber", rootClass, "getOutCourierWsAddressV2UserNumber", null);
         myProperties[214] = new PropertyDescriptor("OutCourierWsAddressV2CourierContactNo", rootClass, "getOutCourierWsAddressV2CourierContactNo", null);
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
//         callMethod = Srads01sMntAddress.class.getMethod("execute", args);
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
