package Srsms02h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srsms02sSendSmsToStudListBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srsms02sSendSmsToStudList.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srsms02sSendSmsToStudList.class);
      bd.setDisplayName(Srsms02sSendSmsToStudList.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InTotalCostIefSuppliedTotalCurrency", rootClass);
         myProperties[15] = new PropertyDescriptor("InAvailableBugetAmountIefSuppliedTotalCurrency", rootClass);
         myProperties[16] = new PropertyDescriptor("InBudgetAmountIefSuppliedTotalCurrency", rootClass);
         myProperties[17] = new PropertyDescriptor("InSmsCostCostPerSms", rootClass);
         myProperties[18] = new PropertyDescriptor("InRcDesriptionCsfStringsString60", rootClass);
         myProperties[19] = new PropertyDescriptor("InFileNameWizfuncReportingControlPathAndFilename", rootClass);
         myProperties[20] = new PropertyDescriptor("InSendSmsCsfStringsString1", rootClass);
         myProperties[21] = new PropertyDescriptor("InMagisterialCriteriaTypeCsfStringsString1", rootClass);
         myProperties[22] = new PropertyDescriptor("InRegistrationCriteriaTypeCsfStringsString1", rootClass);
         myProperties[23] = new IndexedPropertyDescriptor("InMagisterialGpCsfStringsString15", rootClass, null, null, "getInMagisterialGpCsfStringsString15", "setInMagisterialGpCsfStringsString15");
         myProperties[24] = new IndexedPropertyDescriptor("InRegistrationGpCsfStringsString15", rootClass, null, null, "getInRegistrationGpCsfStringsString15", "setInRegistrationGpCsfStringsString15");
         myProperties[25] = new PropertyDescriptor("InWsStudyUnitPeriodDetailMkAcademicYear", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsStudyUnitPeriodDetailMkAcademicPeriod", rootClass);
         myProperties[27] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[28] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[29] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[30] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[31] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[32] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[33] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[34] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[35] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[50] = new PropertyDescriptor("InSmsRequestBatchNr", rootClass);
         myProperties[51] = new PropertyDescriptor("InSmsRequestMkPersNr", rootClass);
         myProperties[52] = new PropertyDescriptor("InSmsRequestMkRcCode", rootClass);
         myProperties[53] = new PropertyDescriptor("InSmsRequestMkDepartmentCode", rootClass);
         myProperties[54] = new PropertyDescriptor("InSmsRequestRequestTimestamp", rootClass);
         myProperties[55] = new PropertyDescriptor("InSmsRequestSmsMsg", rootClass);
         myProperties[56] = new PropertyDescriptor("InSmsRequestControlCellNr", rootClass);
         myProperties[57] = new PropertyDescriptor("InSmsRequestMessageCnt", rootClass);
         myProperties[58] = new PropertyDescriptor("InSmsRequestTotalCost", rootClass);
         myProperties[59] = new PropertyDescriptor("InSmsRequestFromSystemGc26", rootClass);
         myProperties[60] = new PropertyDescriptor("InSmsRequestReasonGc27", rootClass);
         myProperties[61] = new PropertyDescriptor("InSmsRequestSelectionCriteria", rootClass);
         myProperties[62] = new PropertyDescriptor("InSmsRequestProgramName", rootClass);
         myProperties[63] = new PropertyDescriptor("InSmsRequestControlCellNr2", rootClass);
         myProperties[64] = new PropertyDescriptor("InSmsRequestControlCellNr3", rootClass);
         myProperties[65] = new PropertyDescriptor("InErrorFlagCsfStringsString1", rootClass);
         myProperties[66] = new PropertyDescriptor("InNovellCodeWsStaffEmailAddress", rootClass);
         myProperties[67] = new IndexedPropertyDescriptor("OutGRcWsStaffRcCodeMkRcCode", rootClass, null, null, "getOutGRcWsStaffRcCodeMkRcCode", null);
         myProperties[68] = new IndexedPropertyDescriptor("OutGRcWsStaffRcCodeAppointmentType", rootClass, null, null, "getOutGRcWsStaffRcCodeAppointmentType", null);
         myProperties[69] = new IndexedPropertyDescriptor("OutGRcDescriptionCsfStringsString60", rootClass, null, null, "getOutGRcDescriptionCsfStringsString60", null);
         myProperties[70] = new IndexedPropertyDescriptor("OutGRcAvailableBudgetIefSuppliedTotalCurrency", rootClass, null, null, "getOutGRcAvailableBudgetIefSuppliedTotalCurrency", null);
         myProperties[71] = new IndexedPropertyDescriptor("OutGRcTotalBudgetIefSuppliedTotalCurrency", rootClass, null, null, "getOutGRcTotalBudgetIefSuppliedTotalCurrency", null);
         myProperties[72] = new PropertyDescriptor("OutTotalCostIefSuppliedTotalCurrency", rootClass, "getOutTotalCostIefSuppliedTotalCurrency", null);
         myProperties[73] = new PropertyDescriptor("OutAvailableBudgetAmountIefSuppliedTotalCurrency", rootClass, "getOutAvailableBudgetAmountIefSuppliedTotalCurrency", null);
         myProperties[74] = new PropertyDescriptor("OutBudgetAmountIefSuppliedTotalCurrency", rootClass, "getOutBudgetAmountIefSuppliedTotalCurrency", null);
         myProperties[75] = new PropertyDescriptor("OutSmsCostCostPerSms", rootClass, "getOutSmsCostCostPerSms", null);
         myProperties[76] = new PropertyDescriptor("OutRcDesriptionCsfStringsString60", rootClass, "getOutRcDesriptionCsfStringsString60", null);
         myProperties[77] = new PropertyDescriptor("OutFileNameWizfuncReportingControlPathAndFilename", rootClass, "getOutFileNameWizfuncReportingControlPathAndFilename", null);
         myProperties[78] = new PropertyDescriptor("OutSendSmsCsfStringsString1", rootClass, "getOutSendSmsCsfStringsString1", null);
         myProperties[79] = new IndexedPropertyDescriptor("OutGpWsGenericCodeCode", rootClass, null, null, "getOutGpWsGenericCodeCode", null);
         myProperties[80] = new IndexedPropertyDescriptor("OutGpWsGenericCodeEngDescription", rootClass, null, null, "getOutGpWsGenericCodeEngDescription", null);
         myProperties[81] = new PropertyDescriptor("OutMagisterialCriteriaTypeCsfStringsString1", rootClass, "getOutMagisterialCriteriaTypeCsfStringsString1", null);
         myProperties[82] = new PropertyDescriptor("OutRegistrationCriteriaTypeCsfStringsString1", rootClass, "getOutRegistrationCriteriaTypeCsfStringsString1", null);
         myProperties[83] = new IndexedPropertyDescriptor("OutMagisterialGpCsfStringsString15", rootClass, null, null, "getOutMagisterialGpCsfStringsString15", null);
         myProperties[84] = new IndexedPropertyDescriptor("OutRegistrationGpCsfStringsString15", rootClass, null, null, "getOutRegistrationGpCsfStringsString15", null);
         myProperties[85] = new PropertyDescriptor("OutWsStudyUnitPeriodDetailMkAcademicYear", rootClass, "getOutWsStudyUnitPeriodDetailMkAcademicYear", null);
         myProperties[86] = new PropertyDescriptor("OutWsStudyUnitPeriodDetailMkAcademicPeriod", rootClass, "getOutWsStudyUnitPeriodDetailMkAcademicPeriod", null);
         myProperties[87] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[88] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[89] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[90] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[91] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[92] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[93] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[94] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[95] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[96] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[97] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[98] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[99] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[100] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[101] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[102] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[103] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[104] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[105] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[106] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[107] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[112] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[113] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[114] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[117] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[118] = new PropertyDescriptor("OutSmsRequestBatchNr", rootClass, "getOutSmsRequestBatchNr", null);
         myProperties[119] = new PropertyDescriptor("OutSmsRequestMkPersNr", rootClass, "getOutSmsRequestMkPersNr", null);
         myProperties[120] = new PropertyDescriptor("OutSmsRequestMkRcCode", rootClass, "getOutSmsRequestMkRcCode", null);
         myProperties[121] = new PropertyDescriptor("OutSmsRequestMkDepartmentCode", rootClass, "getOutSmsRequestMkDepartmentCode", null);
         myProperties[122] = new PropertyDescriptor("OutSmsRequestRequestTimestamp", rootClass, "getOutSmsRequestRequestTimestamp", null);
         myProperties[123] = new PropertyDescriptor("OutSmsRequestSmsMsg", rootClass, "getOutSmsRequestSmsMsg", null);
         myProperties[124] = new PropertyDescriptor("OutSmsRequestControlCellNr", rootClass, "getOutSmsRequestControlCellNr", null);
         myProperties[125] = new PropertyDescriptor("OutSmsRequestMessageCnt", rootClass, "getOutSmsRequestMessageCnt", null);
         myProperties[126] = new PropertyDescriptor("OutSmsRequestTotalCost", rootClass, "getOutSmsRequestTotalCost", null);
         myProperties[127] = new PropertyDescriptor("OutSmsRequestFromSystemGc26", rootClass, "getOutSmsRequestFromSystemGc26", null);
         myProperties[128] = new PropertyDescriptor("OutSmsRequestReasonGc27", rootClass, "getOutSmsRequestReasonGc27", null);
         myProperties[129] = new PropertyDescriptor("OutSmsRequestSelectionCriteria", rootClass, "getOutSmsRequestSelectionCriteria", null);
         myProperties[130] = new PropertyDescriptor("OutSmsRequestProgramName", rootClass, "getOutSmsRequestProgramName", null);
         myProperties[131] = new PropertyDescriptor("OutSmsRequestControlCellNr2", rootClass, "getOutSmsRequestControlCellNr2", null);
         myProperties[132] = new PropertyDescriptor("OutSmsRequestControlCellNr3", rootClass, "getOutSmsRequestControlCellNr3", null);
         myProperties[133] = new PropertyDescriptor("OutErrorFlagCsfStringsString1", rootClass, "getOutErrorFlagCsfStringsString1", null);
         myProperties[134] = new PropertyDescriptor("OutNovellCodeWsStaffEmailAddress", rootClass, "getOutNovellCodeWsStaffEmailAddress", null);
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
//         callMethod = Srsms02sSendSmsToStudList.class.getMethod("execute", args);
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
