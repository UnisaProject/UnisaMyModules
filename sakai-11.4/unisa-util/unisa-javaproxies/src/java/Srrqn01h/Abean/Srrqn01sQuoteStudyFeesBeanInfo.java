package Srrqn01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srrqn01sQuoteStudyFeesBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srrqn01sQuoteStudyFees.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srrqn01sQuoteStudyFees.class);
      bd.setDisplayName(Srrqn01sQuoteStudyFees.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InPrintNqfCreditsIefSuppliedFlag", rootClass);
         myProperties[14].setPropertyEditorClass(Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor.class);
         myProperties[15] = new PropertyDescriptor("InPrintNqfLevelIefSuppliedFlag", rootClass);
         myProperties[15].setPropertyEditorClass(Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InProddevCsfStringsString4", rootClass);
         myProperties[17] = new PropertyDescriptor("InMatrExemptionIefSuppliedFlag", rootClass);
         myProperties[17].setPropertyEditorClass(Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor.class);
         myProperties[18] = new PropertyDescriptor("InMatrExemptionIefSuppliedTotalCurrency", rootClass);
         myProperties[19] = new PropertyDescriptor("InSmartcardIefSuppliedFlag", rootClass);
         myProperties[19].setPropertyEditorClass(Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor.class);
         myProperties[20] = new PropertyDescriptor("InSmartcardIefSuppliedTotalCurrency", rootClass);
         myProperties[21] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[22] = new PropertyDescriptor("InFaxNoCsfStringsString132", rootClass);
         myProperties[23] = new PropertyDescriptor("InToEmailAddressCsfStringsString132", rootClass);
         myProperties[24] = new PropertyDescriptor("InFromEmailAddressCsfStringsString132", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsForeignLevyCategoryCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsAddressAddressLine1", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsAddressAddressLine2", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsAddressAddressLine3", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsAddressAddressLine4", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsAddressAddressLine5", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsAddressAddressLine6", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsAddressPostalCode", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsAddressEmailAddress", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[36] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsStudentMkCorrespondenceLanguage", rootClass);
         myProperties[39] = new PropertyDescriptor("InLateRegIefSuppliedFlag", rootClass);
         myProperties[39].setPropertyEditorClass(Srrqn01sQuoteStudyFeesIefSuppliedFlagPropertyEditor.class);
         myProperties[40] = new PropertyDescriptor("InMatricExemFeeIefSuppliedAverageCurrency", rootClass);
         myProperties[41] = new PropertyDescriptor("InStudentAcademicRecordMkStudentNr", rootClass);
         myProperties[42] = new PropertyDescriptor("InStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[43] = new PropertyDescriptor("InSrrqn01gQuoteStudyFeesDate", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsWorkstationCode", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsWorkstationUserNumber", rootClass);
         myProperties[46] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsUserName", rootClass);
         myProperties[49] = new PropertyDescriptor("InWsUserPersonnelNo", rootClass);
         myProperties[50] = new PropertyDescriptor("InWsUserPhoneNumber", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[56] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[57] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[58] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[59] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[60] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[61] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[62] = new IndexedPropertyDescriptor("InGCsfLineActionBarLineReturnCode", rootClass, null, null, "getInGCsfLineActionBarLineReturnCode", "setInGCsfLineActionBarLineReturnCode");
         myProperties[63] = new IndexedPropertyDescriptor("InGStudentStudyUnitMkStudyUnitCode", rootClass, null, null, "getInGStudentStudyUnitMkStudyUnitCode", "setInGStudentStudyUnitMkStudyUnitCode");
         myProperties[64] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[65] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[66] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[67] = new PropertyDescriptor("InWsCountryCode", rootClass);
         myProperties[68] = new PropertyDescriptor("InWsCountryEngDescription", rootClass);
         myProperties[69] = new PropertyDescriptor("OutPrintNqfCreditsIefSuppliedFlag", rootClass, "getOutPrintNqfCreditsIefSuppliedFlag", null);
         myProperties[70] = new PropertyDescriptor("OutPrintNqfLevelIefSuppliedFlag", rootClass, "getOutPrintNqfLevelIefSuppliedFlag", null);
         myProperties[71] = new PropertyDescriptor("OutProddevCsfStringsString4", rootClass, "getOutProddevCsfStringsString4", null);
         myProperties[72] = new PropertyDescriptor("OutMatrExemptionIefSuppliedFlag", rootClass, "getOutMatrExemptionIefSuppliedFlag", null);
         myProperties[73] = new PropertyDescriptor("OutMatrExemptionIefSuppliedTotalCurrency", rootClass, "getOutMatrExemptionIefSuppliedTotalCurrency", null);
         myProperties[74] = new PropertyDescriptor("OutSmartcardIefSuppliedFlag", rootClass, "getOutSmartcardIefSuppliedFlag", null);
         myProperties[75] = new PropertyDescriptor("OutSmartcardIefSuppliedTotalCurrency", rootClass, "getOutSmartcardIefSuppliedTotalCurrency", null);
         myProperties[76] = new PropertyDescriptor("OutFaxNoCsfStringsString132", rootClass, "getOutFaxNoCsfStringsString132", null);
         myProperties[77] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[78] = new PropertyDescriptor("OutWsPrescribedBooksAmount", rootClass, "getOutWsPrescribedBooksAmount", null);
         myProperties[79] = new IndexedPropertyDescriptor("OutGInternetWsStudyUnitCode", rootClass, null, null, "getOutGInternetWsStudyUnitCode", null);
         myProperties[80] = new IndexedPropertyDescriptor("OutGInternetWsStudyUnitEngShortDescription", rootClass, null, null, "getOutGInternetWsStudyUnitEngShortDescription", null);
         myProperties[81] = new IndexedPropertyDescriptor("OutGInternetWsStudyUnitNqfCategory", rootClass, null, null, "getOutGInternetWsStudyUnitNqfCategory", null);
         myProperties[82] = new IndexedPropertyDescriptor("OutGInternetWsStudyUnitNqfCredits", rootClass, null, null, "getOutGInternetWsStudyUnitNqfCredits", null);
         myProperties[83] = new IndexedPropertyDescriptor("OutGInternetWsStudyUnitPqmNqfLevel", rootClass, null, null, "getOutGInternetWsStudyUnitPqmNqfLevel", null);
         myProperties[84] = new IndexedPropertyDescriptor("OutGStudyUnitCostIefSuppliedTotalCurrency", rootClass, null, null, "getOutGStudyUnitCostIefSuppliedTotalCurrency", null);
         myProperties[85] = new PropertyDescriptor("OutTotalIefSuppliedTotalCurrency", rootClass, "getOutTotalIefSuppliedTotalCurrency", null);
         myProperties[86] = new PropertyDescriptor("OutRegPaymentIefSuppliedTotalCurrency", rootClass, "getOutRegPaymentIefSuppliedTotalCurrency", null);
         myProperties[87] = new PropertyDescriptor("OutForeignLevyIefSuppliedTotalCurrency", rootClass, "getOutForeignLevyIefSuppliedTotalCurrency", null);
         myProperties[88] = new PropertyDescriptor("OutSrcLevyIefSuppliedTotalCurrency", rootClass, "getOutSrcLevyIefSuppliedTotalCurrency", null);
         myProperties[89] = new PropertyDescriptor("OutFromEmailAddressCsfStringsString132", rootClass, "getOutFromEmailAddressCsfStringsString132", null);
         myProperties[90] = new PropertyDescriptor("OutToEmailAddressCsfStringsString132", rootClass, "getOutToEmailAddressCsfStringsString132", null);
         myProperties[91] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[92] = new PropertyDescriptor("OutWsUserName", rootClass, "getOutWsUserName", null);
         myProperties[93] = new PropertyDescriptor("OutWsUserPersonnelNo", rootClass, "getOutWsUserPersonnelNo", null);
         myProperties[94] = new PropertyDescriptor("OutWsUserPhoneNumber", rootClass, "getOutWsUserPhoneNumber", null);
         myProperties[95] = new PropertyDescriptor("OutWsPrinterCode", rootClass, "getOutWsPrinterCode", null);
         myProperties[96] = new PropertyDescriptor("OutWsWorkstationCode", rootClass, "getOutWsWorkstationCode", null);
         myProperties[97] = new PropertyDescriptor("OutWsWorkstationUserNumber", rootClass, "getOutWsWorkstationUserNumber", null);
         myProperties[98] = new PropertyDescriptor("OutSrrqn01gQuoteStudyFeesDate", rootClass, "getOutSrrqn01gQuoteStudyFeesDate", null);
         myProperties[99] = new PropertyDescriptor("OutStudentAcademicRecordMkStudentNr", rootClass, "getOutStudentAcademicRecordMkStudentNr", null);
         myProperties[100] = new PropertyDescriptor("OutStudentAcademicRecordMkQualificationCode", rootClass, "getOutStudentAcademicRecordMkQualificationCode", null);
         myProperties[101] = new PropertyDescriptor("OutMatricExemFeeIefSuppliedAverageCurrency", rootClass, "getOutMatricExemFeeIefSuppliedAverageCurrency", null);
         myProperties[102] = new PropertyDescriptor("OutLateRegIefSuppliedFlag", rootClass, "getOutLateRegIefSuppliedFlag", null);
         myProperties[103] = new PropertyDescriptor("OutLclWsAddressAddressLine1", rootClass, "getOutLclWsAddressAddressLine1", null);
         myProperties[104] = new PropertyDescriptor("OutLclWsAddressAddressLine2", rootClass, "getOutLclWsAddressAddressLine2", null);
         myProperties[105] = new PropertyDescriptor("OutLclWsAddressAddressLine3", rootClass, "getOutLclWsAddressAddressLine3", null);
         myProperties[106] = new PropertyDescriptor("OutLclWsAddressAddressLine4", rootClass, "getOutLclWsAddressAddressLine4", null);
         myProperties[107] = new PropertyDescriptor("OutLclWsAddressAddressLine5", rootClass, "getOutLclWsAddressAddressLine5", null);
         myProperties[108] = new PropertyDescriptor("OutLclWsAddressAddressLine6", rootClass, "getOutLclWsAddressAddressLine6", null);
         myProperties[109] = new PropertyDescriptor("OutLclWsAddressPostalCode", rootClass, "getOutLclWsAddressPostalCode", null);
         myProperties[110] = new PropertyDescriptor("OutLclWsAddressReferenceNo", rootClass, "getOutLclWsAddressReferenceNo", null);
         myProperties[111] = new PropertyDescriptor("OutLclWsAddressType", rootClass, "getOutLclWsAddressType", null);
         myProperties[112] = new PropertyDescriptor("OutLclWsAddressCategory", rootClass, "getOutLclWsAddressCategory", null);
         myProperties[113] = new PropertyDescriptor("OutLclWsAddressEmailAddress", rootClass, "getOutLclWsAddressEmailAddress", null);
         myProperties[114] = new PropertyDescriptor("OutLclWsStudentNr", rootClass, "getOutLclWsStudentNr", null);
         myProperties[115] = new PropertyDescriptor("OutLclWsStudentMkCorrespondenceLanguage", rootClass, "getOutLclWsStudentMkCorrespondenceLanguage", null);
         myProperties[116] = new PropertyDescriptor("OutLclWsStudentMkTitle", rootClass, "getOutLclWsStudentMkTitle", null);
         myProperties[117] = new PropertyDescriptor("OutLclWsStudentSurname", rootClass, "getOutLclWsStudentSurname", null);
         myProperties[118] = new PropertyDescriptor("OutLclWsStudentFirstNames", rootClass, "getOutLclWsStudentFirstNames", null);
         myProperties[119] = new PropertyDescriptor("OutLclWsStudentInitials", rootClass, "getOutLclWsStudentInitials", null);
         myProperties[120] = new PropertyDescriptor("OutLclWsStudentMkCountryCode", rootClass, "getOutLclWsStudentMkCountryCode", null);
         myProperties[121] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[122] = new PropertyDescriptor("OutCsfStringsString12", rootClass, "getOutCsfStringsString12", null);
         myProperties[123] = new PropertyDescriptor("OutCsfStringsString7", rootClass, "getOutCsfStringsString7", null);
         myProperties[124] = new PropertyDescriptor("OutCsfStringsString3", rootClass, "getOutCsfStringsString3", null);
         myProperties[125] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[126] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[127] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[128] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[129] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[130] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[131] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[132] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[133] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[134] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[135] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[136] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[137] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[138] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[139] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[140] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[141] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[142] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[143] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[144] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutGCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGCsfLineActionBarLineReturnCode", null);
         myProperties[146] = new IndexedPropertyDescriptor("OutGStudentStudyUnitMkStudyUnitCode", rootClass, null, null, "getOutGStudentStudyUnitMkStudyUnitCode", null);
         myProperties[147] = new PropertyDescriptor("OutStudentAnnualRecordMkStudentNr", rootClass, "getOutStudentAnnualRecordMkStudentNr", null);
         myProperties[148] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicYear", rootClass, "getOutStudentAnnualRecordMkAcademicYear", null);
         myProperties[149] = new PropertyDescriptor("OutStudentAnnualRecordMkAcademicPeriod", rootClass, "getOutStudentAnnualRecordMkAcademicPeriod", null);
         myProperties[150] = new PropertyDescriptor("OutWsCountryCode", rootClass, "getOutWsCountryCode", null);
         myProperties[151] = new PropertyDescriptor("OutWsCountryEngDescription", rootClass, "getOutWsCountryEngDescription", null);
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
//         callMethod = Srrqn01sQuoteStudyFees.class.getMethod("execute", args);
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
