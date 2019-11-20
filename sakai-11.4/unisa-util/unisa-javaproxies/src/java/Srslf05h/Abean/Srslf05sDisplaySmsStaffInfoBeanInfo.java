package Srslf05h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srslf05sDisplaySmsStaffInfoBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srslf05sDisplaySmsStaffInfo.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srslf05sDisplaySmsStaffInfo.class);
      bd.setDisplayName(Srslf05sDisplaySmsStaffInfo.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InStaffNameCsfStringsString30", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsStaffPersno", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsStaffMkRcCode", rootClass);
         myProperties[17] = new PropertyDescriptor("InUserNameCsfStringsString30", rootClass);
         myProperties[18] = new PropertyDescriptor("InUserWsStaffPersno", rootClass);
         myProperties[19] = new PropertyDescriptor("InUserWsStaffSurname", rootClass);
         myProperties[20] = new PropertyDescriptor("InUserWsStaffInitials", rootClass);
         myProperties[21] = new PropertyDescriptor("InUserWsStaffTitle", rootClass);
         myProperties[22] = new PropertyDescriptor("InUserWsStaffEmailAddress", rootClass);
         myProperties[23] = new PropertyDescriptor("InUserWsStaffContactTelno", rootClass);
         myProperties[24] = new PropertyDescriptor("InSmsLogMkBatchNr", rootClass);
         myProperties[25] = new PropertyDescriptor("InSmsLogReferenceNr", rootClass);
         myProperties[26] = new PropertyDescriptor("InSmsLogCellNr", rootClass);
         myProperties[27] = new PropertyDescriptor("InSmsLogMessage", rootClass);
         myProperties[28] = new PropertyDescriptor("InSmsLogSentOn", rootClass);
         myProperties[29] = new PropertyDescriptor("InSmsLogDeliveredOn", rootClass);
         myProperties[30] = new PropertyDescriptor("InSmsLogMessageStatus", rootClass);
         myProperties[31] = new PropertyDescriptor("InSmsLogMkPersNr", rootClass);
         myProperties[32] = new PropertyDescriptor("InSmsLogSequenceNr", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("OutSmsMtnStatusCode", rootClass, "getOutSmsMtnStatusCode", null);
         myProperties[47] = new PropertyDescriptor("OutSmsMtnStatusStatus", rootClass, "getOutSmsMtnStatusStatus", null);
         myProperties[48] = new PropertyDescriptor("OutSmsMtnStatusDescription", rootClass, "getOutSmsMtnStatusDescription", null);
         myProperties[49] = new PropertyDescriptor("OutStaffNameCsfStringsString30", rootClass, "getOutStaffNameCsfStringsString30", null);
         myProperties[50] = new PropertyDescriptor("OutWsStaffPersno", rootClass, "getOutWsStaffPersno", null);
         myProperties[51] = new PropertyDescriptor("OutWsStaffMkRcCode", rootClass, "getOutWsStaffMkRcCode", null);
         myProperties[52] = new PropertyDescriptor("OutUserNameCsfStringsString30", rootClass, "getOutUserNameCsfStringsString30", null);
         myProperties[53] = new PropertyDescriptor("OutUserWsStaffPersno", rootClass, "getOutUserWsStaffPersno", null);
         myProperties[54] = new PropertyDescriptor("OutUserWsStaffSurname", rootClass, "getOutUserWsStaffSurname", null);
         myProperties[55] = new PropertyDescriptor("OutUserWsStaffInitials", rootClass, "getOutUserWsStaffInitials", null);
         myProperties[56] = new PropertyDescriptor("OutUserWsStaffTitle", rootClass, "getOutUserWsStaffTitle", null);
         myProperties[57] = new PropertyDescriptor("OutUserWsStaffMkDeptCode", rootClass, "getOutUserWsStaffMkDeptCode", null);
         myProperties[58] = new PropertyDescriptor("OutUserWsStaffEmailAddress", rootClass, "getOutUserWsStaffEmailAddress", null);
         myProperties[59] = new PropertyDescriptor("OutUserWsStaffContactTelno", rootClass, "getOutUserWsStaffContactTelno", null);
         myProperties[60] = new PropertyDescriptor("OutSmsLogMkBatchNr", rootClass, "getOutSmsLogMkBatchNr", null);
         myProperties[61] = new PropertyDescriptor("OutSmsLogReferenceNr", rootClass, "getOutSmsLogReferenceNr", null);
         myProperties[62] = new PropertyDescriptor("OutSmsLogCellNr", rootClass, "getOutSmsLogCellNr", null);
         myProperties[63] = new PropertyDescriptor("OutSmsLogMessage", rootClass, "getOutSmsLogMessage", null);
         myProperties[64] = new PropertyDescriptor("OutSmsLogSentOn", rootClass, "getOutSmsLogSentOn", null);
         myProperties[65] = new PropertyDescriptor("OutSmsLogDeliveredOn", rootClass, "getOutSmsLogDeliveredOn", null);
         myProperties[66] = new PropertyDescriptor("OutSmsLogMessageStatus", rootClass, "getOutSmsLogMessageStatus", null);
         myProperties[67] = new PropertyDescriptor("OutSmsLogMkPersNr", rootClass, "getOutSmsLogMkPersNr", null);
         myProperties[68] = new PropertyDescriptor("OutSmsLogSequenceNr", rootClass, "getOutSmsLogSequenceNr", null);
         myProperties[69] = new PropertyDescriptor("OutSmsLogMkMtnStatusCode", rootClass, "getOutSmsLogMkMtnStatusCode", null);
         myProperties[70] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[71] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[72] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[73] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[74] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[75] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[76] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[77] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[78] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[79] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[80] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
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
//         callMethod = Srslf05sDisplaySmsStaffInfo.class.getMethod("execute", args);
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
