package Srsms01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srsms01sSendSingleSmsBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srsms01sSendSingleSms.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srsms01sSendSingleSms.class);
      bd.setDisplayName(Srsms01sSendSingleSms.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[15] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[16] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[17] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[18] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[19] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[20] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[21] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[22] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[23] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[24] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[25] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[26] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[27] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[28] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[29] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[37] = new PropertyDescriptor("InSmsQueueMkBatchNr", rootClass);
         myProperties[38] = new PropertyDescriptor("InSmsQueueSequenceNr", rootClass);
         myProperties[39] = new PropertyDescriptor("InSmsQueueReferenceNr", rootClass);
         myProperties[40] = new PropertyDescriptor("InSmsQueueCellNr", rootClass);
         myProperties[41] = new PropertyDescriptor("InSmsQueueMessage", rootClass);
         myProperties[42] = new PropertyDescriptor("InSmsRequestBatchNr", rootClass);
         myProperties[43] = new PropertyDescriptor("InSmsRequestMkPersNr", rootClass);
         myProperties[44] = new PropertyDescriptor("InSmsRequestMkRcCode", rootClass);
         myProperties[45] = new PropertyDescriptor("InSmsRequestMkDepartmentCode", rootClass);
         myProperties[46] = new PropertyDescriptor("InSmsRequestRequestTimestamp", rootClass);
         myProperties[47] = new PropertyDescriptor("InSmsRequestSmsMsg", rootClass);
         myProperties[48] = new PropertyDescriptor("InSmsRequestControlCellNr", rootClass);
         myProperties[49] = new PropertyDescriptor("InSmsRequestMessageCnt", rootClass);
         myProperties[50] = new PropertyDescriptor("InSmsRequestTotalCost", rootClass);
         myProperties[51] = new PropertyDescriptor("InSmsRequestFromSystemGc26", rootClass);
         myProperties[52] = new PropertyDescriptor("InSmsRequestReasonGc27", rootClass);
         myProperties[53] = new PropertyDescriptor("InSmsRequestSelectionCriteria", rootClass);
         myProperties[54] = new PropertyDescriptor("InStudentNameCsfStringsString100", rootClass);
         myProperties[55] = new PropertyDescriptor("InErrorFlagCsfStringsString1", rootClass);
         myProperties[56] = new PropertyDescriptor("InNovellCodeWsStaffEmailAddress", rootClass);
         myProperties[57] = new PropertyDescriptor("InWsSmsToSmsTo", rootClass);
         myProperties[57].setPropertyEditorClass(Srsms01sSendSingleSmsWsSmsToSmsToPropertyEditor.class);
         myProperties[58] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[59] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[60] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[61] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[62] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[63] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[64] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[65] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[66] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[67] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[68] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[69] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[70] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[71] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[72] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[73] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[74] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[75] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[76] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[77] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[78] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[79] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[80] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[81] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[82] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[83] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[84] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[85] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[86] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[87] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[88] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[89] = new PropertyDescriptor("OutSmsQueueMkBatchNr", rootClass, "getOutSmsQueueMkBatchNr", null);
         myProperties[90] = new PropertyDescriptor("OutSmsQueueSequenceNr", rootClass, "getOutSmsQueueSequenceNr", null);
         myProperties[91] = new PropertyDescriptor("OutSmsQueueReferenceNr", rootClass, "getOutSmsQueueReferenceNr", null);
         myProperties[92] = new PropertyDescriptor("OutSmsQueueCellNr", rootClass, "getOutSmsQueueCellNr", null);
         myProperties[93] = new PropertyDescriptor("OutSmsQueueMessage", rootClass, "getOutSmsQueueMessage", null);
         myProperties[94] = new PropertyDescriptor("OutSmsRequestBatchNr", rootClass, "getOutSmsRequestBatchNr", null);
         myProperties[95] = new PropertyDescriptor("OutSmsRequestMkPersNr", rootClass, "getOutSmsRequestMkPersNr", null);
         myProperties[96] = new PropertyDescriptor("OutSmsRequestMkRcCode", rootClass, "getOutSmsRequestMkRcCode", null);
         myProperties[97] = new PropertyDescriptor("OutSmsRequestMkDepartmentCode", rootClass, "getOutSmsRequestMkDepartmentCode", null);
         myProperties[98] = new PropertyDescriptor("OutSmsRequestRequestTimestamp", rootClass, "getOutSmsRequestRequestTimestamp", null);
         myProperties[99] = new PropertyDescriptor("OutSmsRequestSmsMsg", rootClass, "getOutSmsRequestSmsMsg", null);
         myProperties[100] = new PropertyDescriptor("OutSmsRequestControlCellNr", rootClass, "getOutSmsRequestControlCellNr", null);
         myProperties[101] = new PropertyDescriptor("OutSmsRequestMessageCnt", rootClass, "getOutSmsRequestMessageCnt", null);
         myProperties[102] = new PropertyDescriptor("OutSmsRequestTotalCost", rootClass, "getOutSmsRequestTotalCost", null);
         myProperties[103] = new PropertyDescriptor("OutSmsRequestFromSystemGc26", rootClass, "getOutSmsRequestFromSystemGc26", null);
         myProperties[104] = new PropertyDescriptor("OutSmsRequestReasonGc27", rootClass, "getOutSmsRequestReasonGc27", null);
         myProperties[105] = new PropertyDescriptor("OutSmsRequestSelectionCriteria", rootClass, "getOutSmsRequestSelectionCriteria", null);
         myProperties[106] = new PropertyDescriptor("OutStudentNameCsfStringsString100", rootClass, "getOutStudentNameCsfStringsString100", null);
         myProperties[107] = new PropertyDescriptor("OutErrorFlagCsfStringsString1", rootClass, "getOutErrorFlagCsfStringsString1", null);
         myProperties[108] = new PropertyDescriptor("OutNovellCodeWsStaffEmailAddress", rootClass, "getOutNovellCodeWsStaffEmailAddress", null);
         myProperties[109] = new PropertyDescriptor("OutWsSmsToSmsTo", rootClass, "getOutWsSmsToSmsTo", null);
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
//         callMethod = Srsms01sSendSingleSms.class.getMethod("execute", args);
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
