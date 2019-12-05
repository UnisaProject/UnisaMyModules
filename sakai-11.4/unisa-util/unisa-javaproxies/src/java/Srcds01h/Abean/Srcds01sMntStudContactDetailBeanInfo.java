package Srcds01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srcds01sMntStudContactDetailBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srcds01sMntStudContactDetail.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srcds01sMntStudContactDetail.class);
      bd.setDisplayName(Srcds01sMntStudContactDetail.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InStudentNameCsfStringsString50", rootClass);
         myProperties[15] = new PropertyDescriptor("InPrisonIefSuppliedFlag", rootClass);
         myProperties[15].setPropertyEditorClass(Srcds01sMntStudContactDetailIefSuppliedFlagPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InWsAddressV2ReferenceNo", rootClass);
         myProperties[17] = new PropertyDescriptor("InWsAddressV2AddressReferenceFlag", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsAddressV2Type", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsAddressV2Category", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsAddressV2HomeNumber", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsAddressV2WorkNumber", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsAddressV2FaxNumber", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsAddressV2CellNumber", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsAddressV2EmailAddress", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsAddressV2ErrorCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsAddressV2UserNumber", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsAddressV2CourierContactNo", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsStudentMkCorrespondenceLanguage", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
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
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[47] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[49] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[50] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[51] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[52] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[53] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[54] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[55] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[56] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[57] = new PropertyDescriptor("OutCourierWsAddressV2ReferenceNo", rootClass, "getOutCourierWsAddressV2ReferenceNo", null);
         myProperties[58] = new PropertyDescriptor("OutCourierWsAddressV2Type", rootClass, "getOutCourierWsAddressV2Type", null);
         myProperties[59] = new PropertyDescriptor("OutCourierWsAddressV2Category", rootClass, "getOutCourierWsAddressV2Category", null);
         myProperties[60] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine1", rootClass, "getOutCourierWsAddressV2AddressLine1", null);
         myProperties[61] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine2", rootClass, "getOutCourierWsAddressV2AddressLine2", null);
         myProperties[62] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine3", rootClass, "getOutCourierWsAddressV2AddressLine3", null);
         myProperties[63] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine4", rootClass, "getOutCourierWsAddressV2AddressLine4", null);
         myProperties[64] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine5", rootClass, "getOutCourierWsAddressV2AddressLine5", null);
         myProperties[65] = new PropertyDescriptor("OutCourierWsAddressV2AddressLine6", rootClass, "getOutCourierWsAddressV2AddressLine6", null);
         myProperties[66] = new PropertyDescriptor("OutCourierWsAddressV2PostalCode", rootClass, "getOutCourierWsAddressV2PostalCode", null);
         myProperties[67] = new PropertyDescriptor("OutCourierWsAddressV2CourierContactNo", rootClass, "getOutCourierWsAddressV2CourierContactNo", null);
         myProperties[68] = new PropertyDescriptor("OutNationalityWsCountryV2EngDescription", rootClass, "getOutNationalityWsCountryV2EngDescription", null);
         myProperties[69] = new PropertyDescriptor("OutWsCountryV2Code", rootClass, "getOutWsCountryV2Code", null);
         myProperties[70] = new PropertyDescriptor("OutWsCountryV2EngDescription", rootClass, "getOutWsCountryV2EngDescription", null);
         myProperties[71] = new PropertyDescriptor("OutWsEthnicGroupEngDescription", rootClass, "getOutWsEthnicGroupEngDescription", null);
         myProperties[72] = new PropertyDescriptor("OutWsExamCentreCode", rootClass, "getOutWsExamCentreCode", null);
         myProperties[73] = new PropertyDescriptor("OutWsExamCentreEngDescription", rootClass, "getOutWsExamCentreEngDescription", null);
         myProperties[74] = new PropertyDescriptor("OutWsRegionalOfficeEngDescription", rootClass, "getOutWsRegionalOfficeEngDescription", null);
         myProperties[75] = new PropertyDescriptor("OutWsDisabilityTypeEngDescription", rootClass, "getOutWsDisabilityTypeEngDescription", null);
         myProperties[76] = new PropertyDescriptor("OutHomeWsLanguageEngDescription", rootClass, "getOutHomeWsLanguageEngDescription", null);
         myProperties[77] = new PropertyDescriptor("OutPhysicalWsAddressV2ReferenceNo", rootClass, "getOutPhysicalWsAddressV2ReferenceNo", null);
         myProperties[78] = new PropertyDescriptor("OutPhysicalWsAddressV2Type", rootClass, "getOutPhysicalWsAddressV2Type", null);
         myProperties[79] = new PropertyDescriptor("OutPhysicalWsAddressV2Category", rootClass, "getOutPhysicalWsAddressV2Category", null);
         myProperties[80] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine1", rootClass, "getOutPhysicalWsAddressV2AddressLine1", null);
         myProperties[81] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine2", rootClass, "getOutPhysicalWsAddressV2AddressLine2", null);
         myProperties[82] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine3", rootClass, "getOutPhysicalWsAddressV2AddressLine3", null);
         myProperties[83] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine4", rootClass, "getOutPhysicalWsAddressV2AddressLine4", null);
         myProperties[84] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine5", rootClass, "getOutPhysicalWsAddressV2AddressLine5", null);
         myProperties[85] = new PropertyDescriptor("OutPhysicalWsAddressV2AddressLine6", rootClass, "getOutPhysicalWsAddressV2AddressLine6", null);
         myProperties[86] = new PropertyDescriptor("OutPhysicalWsAddressV2PostalCode", rootClass, "getOutPhysicalWsAddressV2PostalCode", null);
         myProperties[87] = new PropertyDescriptor("OutPostalWsAddressV2ReferenceNo", rootClass, "getOutPostalWsAddressV2ReferenceNo", null);
         myProperties[88] = new PropertyDescriptor("OutPostalWsAddressV2Type", rootClass, "getOutPostalWsAddressV2Type", null);
         myProperties[89] = new PropertyDescriptor("OutPostalWsAddressV2Category", rootClass, "getOutPostalWsAddressV2Category", null);
         myProperties[90] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine1", rootClass, "getOutPostalWsAddressV2AddressLine1", null);
         myProperties[91] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine2", rootClass, "getOutPostalWsAddressV2AddressLine2", null);
         myProperties[92] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine3", rootClass, "getOutPostalWsAddressV2AddressLine3", null);
         myProperties[93] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine4", rootClass, "getOutPostalWsAddressV2AddressLine4", null);
         myProperties[94] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine5", rootClass, "getOutPostalWsAddressV2AddressLine5", null);
         myProperties[95] = new PropertyDescriptor("OutPostalWsAddressV2AddressLine6", rootClass, "getOutPostalWsAddressV2AddressLine6", null);
         myProperties[96] = new PropertyDescriptor("OutPostalWsAddressV2PostalCode", rootClass, "getOutPostalWsAddressV2PostalCode", null);
         myProperties[97] = new PropertyDescriptor("OutStudentChangeAuditTrailMkStudentNr", rootClass, "getOutStudentChangeAuditTrailMkStudentNr", null);
         myProperties[98] = new PropertyDescriptor("OutStudentChangeAuditTrailTimestamp", rootClass, "getOutStudentChangeAuditTrailTimestamp", null);
         myProperties[99] = new PropertyDescriptor("OutStudentChangeAuditTrailMkUserCode", rootClass, "getOutStudentChangeAuditTrailMkUserCode", null);
         myProperties[100] = new PropertyDescriptor("OutStudentChangeAuditTrailChangeCode", rootClass, "getOutStudentChangeAuditTrailChangeCode", null);
         myProperties[101] = new PropertyDescriptor("OutStudentChangeAuditTrailSupervisorCode", rootClass, "getOutStudentChangeAuditTrailSupervisorCode", null);
         myProperties[102] = new PropertyDescriptor("OutStudentChangeAuditTrailErrorFlag", rootClass, "getOutStudentChangeAuditTrailErrorFlag", null);
         myProperties[103] = new PropertyDescriptor("OutStudentChangeAuditTrailValueChanged", rootClass, "getOutStudentChangeAuditTrailValueChanged", null);
         myProperties[104] = new PropertyDescriptor("OutStudentNameCsfStringsString50", rootClass, "getOutStudentNameCsfStringsString50", null);
         myProperties[105] = new PropertyDescriptor("OutContactsWsAddressV2ReferenceNo", rootClass, "getOutContactsWsAddressV2ReferenceNo", null);
         myProperties[106] = new PropertyDescriptor("OutContactsWsAddressV2Type", rootClass, "getOutContactsWsAddressV2Type", null);
         myProperties[107] = new PropertyDescriptor("OutContactsWsAddressV2Category", rootClass, "getOutContactsWsAddressV2Category", null);
         myProperties[108] = new PropertyDescriptor("OutContactsWsAddressV2CategoryDescription", rootClass, "getOutContactsWsAddressV2CategoryDescription", null);
         myProperties[109] = new PropertyDescriptor("OutContactsWsAddressV2HomeNumber", rootClass, "getOutContactsWsAddressV2HomeNumber", null);
         myProperties[110] = new PropertyDescriptor("OutContactsWsAddressV2WorkNumber", rootClass, "getOutContactsWsAddressV2WorkNumber", null);
         myProperties[111] = new PropertyDescriptor("OutContactsWsAddressV2FaxNumber", rootClass, "getOutContactsWsAddressV2FaxNumber", null);
         myProperties[112] = new PropertyDescriptor("OutContactsWsAddressV2CellNumber", rootClass, "getOutContactsWsAddressV2CellNumber", null);
         myProperties[113] = new PropertyDescriptor("OutContactsWsAddressV2EmailAddress", rootClass, "getOutContactsWsAddressV2EmailAddress", null);
         myProperties[114] = new PropertyDescriptor("OutContactsWsAddressV2CourierContactNo", rootClass, "getOutContactsWsAddressV2CourierContactNo", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[117] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[118] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[119] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[120] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[121] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[122] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[123] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[124] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[125] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[126] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[127] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[128] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[129] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[130] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[131] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[132] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[133] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[134] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[135] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[136] = new PropertyDescriptor("OutLclWizfuncReportingControlPrinterCode", rootClass, "getOutLclWizfuncReportingControlPrinterCode", null);
         myProperties[137] = new PropertyDescriptor("OutLclWsMethodResultReturnCode", rootClass, "getOutLclWsMethodResultReturnCode", null);
         myProperties[138] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[139] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[140] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[141] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[142] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[143] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[144] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[145] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[146] = new PropertyDescriptor("OutWsStudentPreviousSurname", rootClass, "getOutWsStudentPreviousSurname", null);
         myProperties[147] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[148] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[149] = new PropertyDescriptor("OutWsStudentGender", rootClass, "getOutWsStudentGender", null);
         myProperties[150] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[151] = new PropertyDescriptor("OutWsStudentMkHomeLanguage", rootClass, "getOutWsStudentMkHomeLanguage", null);
         myProperties[152] = new PropertyDescriptor("OutWsStudentPassportNo", rootClass, "getOutWsStudentPassportNo", null);
         myProperties[153] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[154] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[155] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[156] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[157] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[158] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[159] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[160] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[161] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[162] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[163] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
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
//         callMethod = Srcds01sMntStudContactDetail.class.getMethod("execute", args);
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
