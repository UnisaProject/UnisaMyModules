package Srslf03h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srslf03sListSmsBatchnoBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srslf03sListSmsBatchno.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srslf03sListSmsBatchno.class);
      bd.setDisplayName(Srslf03sListSmsBatchno.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsStaffMkRcCode", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsStaffPersno", rootClass);
         myProperties[16] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[17] = new PropertyDescriptor("InToDateConversionParametersTimeStamp", rootClass);
         myProperties[18] = new PropertyDescriptor("InFromDateConversionParametersTimeStamp", rootClass);
         myProperties[19] = new PropertyDescriptor("InStartingSmsRequestBatchNr", rootClass);
         myProperties[20] = new PropertyDescriptor("InStartingSmsRequestRequestTimestamp", rootClass);
         myProperties[21] = new PropertyDescriptor("InStartingSmsRequestSmsMsg", rootClass);
         myProperties[22] = new PropertyDescriptor("InStartingSmsRequestMessageCnt", rootClass);
         myProperties[23] = new PropertyDescriptor("InStartingSmsRequestTotalCost", rootClass);
         myProperties[24] = new PropertyDescriptor("InHighSmsRequestBatchNr", rootClass);
         myProperties[25] = new PropertyDescriptor("InHighSmsRequestRequestTimestamp", rootClass);
         myProperties[26] = new PropertyDescriptor("InHighSmsRequestSmsMsg", rootClass);
         myProperties[27] = new PropertyDescriptor("InHighSmsRequestMessageCnt", rootClass);
         myProperties[28] = new PropertyDescriptor("InHighSmsRequestTotalCost", rootClass);
         myProperties[29] = new PropertyDescriptor("InLowSmsRequestBatchNr", rootClass);
         myProperties[30] = new PropertyDescriptor("InLowSmsRequestRequestTimestamp", rootClass);
         myProperties[31] = new PropertyDescriptor("InLowSmsRequestSmsMsg", rootClass);
         myProperties[32] = new PropertyDescriptor("InLowSmsRequestMessageCnt", rootClass);
         myProperties[33] = new PropertyDescriptor("InLowSmsRequestTotalCost", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[50] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[51] = new PropertyDescriptor("OutToDateConversionParametersTimeStamp", rootClass, "getOutToDateConversionParametersTimeStamp", null);
         myProperties[52] = new PropertyDescriptor("OutFromDateConversionParametersTimeStamp", rootClass, "getOutFromDateConversionParametersTimeStamp", null);
         myProperties[53] = new IndexedPropertyDescriptor("OutGSmsRequestBatchNr", rootClass, null, null, "getOutGSmsRequestBatchNr", null);
         myProperties[54] = new IndexedPropertyDescriptor("OutGSmsRequestRequestTimestamp", rootClass, null, null, "getOutGSmsRequestRequestTimestamp", null);
         myProperties[55] = new IndexedPropertyDescriptor("OutGSmsRequestSmsMsg", rootClass, null, null, "getOutGSmsRequestSmsMsg", null);
         myProperties[56] = new IndexedPropertyDescriptor("OutGSmsRequestMessageCnt", rootClass, null, null, "getOutGSmsRequestMessageCnt", null);
         myProperties[57] = new IndexedPropertyDescriptor("OutGSmsRequestTotalCost", rootClass, null, null, "getOutGSmsRequestTotalCost", null);
         myProperties[58] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[59] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[60] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[61] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[62] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[63] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[64] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[65] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[66] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[67] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[68] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[69] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[70] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[71] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[72] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[73] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[74] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[75] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[76] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[77] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[78] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[79] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[80] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[81] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[82] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[83] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
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
//         callMethod = Srslf03sListSmsBatchno.class.getMethod("execute", args);
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
