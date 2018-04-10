package Srslf02h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srslf02sListSmsMessagesBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srslf02sListSmsMessages.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srslf02sListSmsMessages.class);
      bd.setDisplayName(Srslf02sListSmsMessages.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsSmsView", rootClass);
         myProperties[14].setPropertyEditorClass(Srslf02sListSmsMessagesWsSmsViewPropertyEditor.class);
         myProperties[15] = new PropertyDescriptor("InCsfStringsString500", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsStaffPersno", rootClass);
         myProperties[17] = new PropertyDescriptor("InWsStaffMkRcCode", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsStaffSurname", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsStaffInitials", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsStaffTitle", rootClass);
         myProperties[21] = new PropertyDescriptor("InSmsRequestBatchNr", rootClass);
         myProperties[22] = new PropertyDescriptor("InSmsRequestMkRcCode", rootClass);
         myProperties[23] = new PropertyDescriptor("InSmsRequestMkPersNr", rootClass);
         myProperties[24] = new PropertyDescriptor("InSmsRequestMkDepartmentCode", rootClass);
         myProperties[25] = new PropertyDescriptor("InSmsRequestRequestTimestamp", rootClass);
         myProperties[26] = new PropertyDescriptor("InSmsRequestSmsMsg", rootClass);
         myProperties[27] = new PropertyDescriptor("InSmsRequestControlCellNr", rootClass);
         myProperties[28] = new PropertyDescriptor("InSmsRequestMessageCnt", rootClass);
         myProperties[29] = new PropertyDescriptor("InSmsRequestTotalCost", rootClass);
         myProperties[30] = new PropertyDescriptor("InSmsRequestFromSystemGc26", rootClass);
         myProperties[31] = new PropertyDescriptor("InSmsRequestReasonGc27", rootClass);
         myProperties[32] = new PropertyDescriptor("InSmsRequestSelectionCriteria", rootClass);
         myProperties[33] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[34] = new PropertyDescriptor("InStaffNameCsfStringsString30", rootClass);
         myProperties[35] = new PropertyDescriptor("InStartingSmsLogMkBatchNr", rootClass);
         myProperties[36] = new PropertyDescriptor("InStartingSmsLogReferenceNr", rootClass);
         myProperties[37] = new PropertyDescriptor("InStartingSmsLogCellNr", rootClass);
         myProperties[38] = new PropertyDescriptor("InStartingSmsLogMessage", rootClass);
         myProperties[39] = new PropertyDescriptor("InStartingSmsLogSentOn", rootClass);
         myProperties[40] = new PropertyDescriptor("InStartingSmsLogMessageStatus", rootClass);
         myProperties[41] = new PropertyDescriptor("InHighSmsLogMkBatchNr", rootClass);
         myProperties[42] = new PropertyDescriptor("InHighSmsLogReferenceNr", rootClass);
         myProperties[43] = new PropertyDescriptor("InHighSmsLogCellNr", rootClass);
         myProperties[44] = new PropertyDescriptor("InHighSmsLogMessage", rootClass);
         myProperties[45] = new PropertyDescriptor("InHighSmsLogSentOn", rootClass);
         myProperties[46] = new PropertyDescriptor("InHighSmsLogMessageStatus", rootClass);
         myProperties[47] = new PropertyDescriptor("InLowSmsLogMkBatchNr", rootClass);
         myProperties[48] = new PropertyDescriptor("InLowSmsLogReferenceNr", rootClass);
         myProperties[49] = new PropertyDescriptor("InLowSmsLogCellNr", rootClass);
         myProperties[50] = new PropertyDescriptor("InLowSmsLogMessage", rootClass);
         myProperties[51] = new PropertyDescriptor("InLowSmsLogSentOn", rootClass);
         myProperties[52] = new PropertyDescriptor("InLowSmsLogMessageStatus", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[56] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[57] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[58] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[59] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[60] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[61] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[62] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[63] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[64] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[65] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[66] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[67] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[68] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[69] = new PropertyDescriptor("OutWsStaffPersno", rootClass, "getOutWsStaffPersno", null);
         myProperties[70] = new PropertyDescriptor("OutWsStaffMkRcCode", rootClass, "getOutWsStaffMkRcCode", null);
         myProperties[71] = new PropertyDescriptor("OutSmsRequestBatchNr", rootClass, "getOutSmsRequestBatchNr", null);
         myProperties[72] = new PropertyDescriptor("OutSmsRequestMkRcCode", rootClass, "getOutSmsRequestMkRcCode", null);
         myProperties[73] = new PropertyDescriptor("OutSmsLogMkBatchNr", rootClass, "getOutSmsLogMkBatchNr", null);
         myProperties[74] = new PropertyDescriptor("OutSmsLogSequenceNr", rootClass, "getOutSmsLogSequenceNr", null);
         myProperties[75] = new PropertyDescriptor("OutSmsLogReferenceNr", rootClass, "getOutSmsLogReferenceNr", null);
         myProperties[76] = new PropertyDescriptor("OutSmsLogCellNr", rootClass, "getOutSmsLogCellNr", null);
         myProperties[77] = new PropertyDescriptor("OutSmsLogMessage", rootClass, "getOutSmsLogMessage", null);
         myProperties[78] = new PropertyDescriptor("OutSmsLogSentOn", rootClass, "getOutSmsLogSentOn", null);
         myProperties[79] = new PropertyDescriptor("OutSmsLogDeliveredOn", rootClass, "getOutSmsLogDeliveredOn", null);
         myProperties[80] = new PropertyDescriptor("OutSmsLogMessageStatus", rootClass, "getOutSmsLogMessageStatus", null);
         myProperties[81] = new PropertyDescriptor("OutSmsLogMkPersNr", rootClass, "getOutSmsLogMkPersNr", null);
         myProperties[82] = new PropertyDescriptor("OutSmsLogLogRefId", rootClass, "getOutSmsLogLogRefId", null);
         myProperties[83] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[84] = new PropertyDescriptor("OutStaffNameCsfStringsString30", rootClass, "getOutStaffNameCsfStringsString30", null);
         myProperties[85] = new IndexedPropertyDescriptor("OutGSmsLogMkBatchNr", rootClass, null, null, "getOutGSmsLogMkBatchNr", null);
         myProperties[86] = new IndexedPropertyDescriptor("OutGSmsLogReferenceNr", rootClass, null, null, "getOutGSmsLogReferenceNr", null);
         myProperties[87] = new IndexedPropertyDescriptor("OutGSmsLogCellNr", rootClass, null, null, "getOutGSmsLogCellNr", null);
         myProperties[88] = new IndexedPropertyDescriptor("OutGSmsLogMessage", rootClass, null, null, "getOutGSmsLogMessage", null);
         myProperties[89] = new IndexedPropertyDescriptor("OutGSmsLogSentOn", rootClass, null, null, "getOutGSmsLogSentOn", null);
         myProperties[90] = new IndexedPropertyDescriptor("OutGSmsLogMessageStatus", rootClass, null, null, "getOutGSmsLogMessageStatus", null);
         myProperties[91] = new IndexedPropertyDescriptor("OutGSmsLogSequenceNr", rootClass, null, null, "getOutGSmsLogSequenceNr", null);
         myProperties[92] = new IndexedPropertyDescriptor("OutGLogCsfLineActionBarAction", rootClass, null, null, "getOutGLogCsfLineActionBarAction", null);
         myProperties[93] = new IndexedPropertyDescriptor("OutGLogCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGLogCsfLineActionBarSelectionFlag", null);
         myProperties[94] = new IndexedPropertyDescriptor("OutGMessageStatusDescCsfStringsString30", rootClass, null, null, "getOutGMessageStatusDescCsfStringsString30", null);
         myProperties[95] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[96] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[97] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[98] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[99] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[100] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[101] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[102] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[103] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[104] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[105] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[106] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[107] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[112] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[113] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[114] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[117] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[118] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
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
//         callMethod = Srslf02sListSmsMessages.class.getMethod("execute", args);
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
