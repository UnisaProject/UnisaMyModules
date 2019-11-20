package Expsc10h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Expsc10sLstExamPaperVenueStBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Expsc10sLstExamPaperVenueSt.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Expsc10sLstExamPaperVenueSt.class);
      bd.setDisplayName(Expsc10sLstExamPaperVenueSt.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("In10RedCsfStringsString40", rootClass);
         myProperties[15] = new PropertyDescriptor("InStartingExamPaperVenueStatisticsExamYear", rootClass);
         myProperties[16] = new PropertyDescriptor("InStartingExamPaperVenueStatisticsMkExamPeriodCode", rootClass);
         myProperties[17] = new PropertyDescriptor("InStartingExamPaperVenueStatisticsMkStudyUnitCode", rootClass);
         myProperties[18] = new PropertyDescriptor("InStartingExamPaperVenueStatisticsMkExamVenueCode", rootClass);
         myProperties[19] = new PropertyDescriptor("InStartingExamPaperVenueStatisticsNr", rootClass);
         myProperties[20] = new PropertyDescriptor("OnHighExamPaperVenueStatisticsExamYear", rootClass);
         myProperties[21] = new PropertyDescriptor("OnHighExamPaperVenueStatisticsMkExamPeriodCode", rootClass);
         myProperties[22] = new PropertyDescriptor("OnHighExamPaperVenueStatisticsMkStudyUnitCode", rootClass);
         myProperties[23] = new PropertyDescriptor("OnHighExamPaperVenueStatisticsMkExamVenueCode", rootClass);
         myProperties[24] = new PropertyDescriptor("OnHighExamPaperVenueStatisticsNr", rootClass);
         myProperties[25] = new PropertyDescriptor("InLowExamPaperVenueStatisticsExamYear", rootClass);
         myProperties[26] = new PropertyDescriptor("InLowExamPaperVenueStatisticsMkExamPeriodCode", rootClass);
         myProperties[27] = new PropertyDescriptor("InLowExamPaperVenueStatisticsMkStudyUnitCode", rootClass);
         myProperties[28] = new PropertyDescriptor("InLowExamPaperVenueStatisticsMkExamVenueCode", rootClass);
         myProperties[29] = new PropertyDescriptor("InLowExamPaperVenueStatisticsNr", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[47] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[48] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[49] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[50] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[51] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[52] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[53] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[54] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[55] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[56] = new PropertyDescriptor("Out10RedCsfStringsString40", rootClass, "getOut10RedCsfStringsString40", null);
         myProperties[57] = new PropertyDescriptor("OutTotMcqOutstIefSuppliedCount", rootClass, "getOutTotMcqOutstIefSuppliedCount", null);
         myProperties[58] = new PropertyDescriptor("OutTotMcqIefSuppliedCount", rootClass, "getOutTotMcqIefSuppliedCount", null);
         myProperties[59] = new PropertyDescriptor("OutTotOutstandingIefSuppliedCount", rootClass, "getOutTotOutstandingIefSuppliedCount", null);
         myProperties[60] = new PropertyDescriptor("OutTotReceivedIefSuppliedCount", rootClass, "getOutTotReceivedIefSuppliedCount", null);
         myProperties[61] = new PropertyDescriptor("OutTotAbsentIefSuppliedCount", rootClass, "getOutTotAbsentIefSuppliedCount", null);
         myProperties[62] = new PropertyDescriptor("OutTotExpectedIefSuppliedCount", rootClass, "getOutTotExpectedIefSuppliedCount", null);
         myProperties[63] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[64] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[65] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[66] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[67] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[68] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[69] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[70] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[71] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[72] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[73] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[74] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[75] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[76] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[77] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[78] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[79] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[80] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[81] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[82] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[83] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[84] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[85] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[86] = new IndexedPropertyDescriptor("OutGWsExamVenueEngName", rootClass, null, null, "getOutGWsExamVenueEngName", null);
         myProperties[87] = new IndexedPropertyDescriptor("OutGCsfStringsString1", rootClass, null, null, "getOutGCsfStringsString1", null);
         myProperties[88] = new IndexedPropertyDescriptor("OutGOutstandingIefSuppliedCount", rootClass, null, null, "getOutGOutstandingIefSuppliedCount", null);
         myProperties[89] = new IndexedPropertyDescriptor("OutGMcqOutstIefSuppliedCount", rootClass, null, null, "getOutGMcqOutstIefSuppliedCount", null);
         myProperties[90] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsExamYear", rootClass, null, null, "getOutGExamPaperVenueStatisticsExamYear", null);
         myProperties[91] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsMkExamPeriodCode", rootClass, null, null, "getOutGExamPaperVenueStatisticsMkExamPeriodCode", null);
         myProperties[92] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsMkStudyUnitCode", rootClass, null, null, "getOutGExamPaperVenueStatisticsMkStudyUnitCode", null);
         myProperties[93] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsMkExamVenueCode", rootClass, null, null, "getOutGExamPaperVenueStatisticsMkExamVenueCode", null);
         myProperties[94] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsNr", rootClass, null, null, "getOutGExamPaperVenueStatisticsNr", null);
         myProperties[95] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsStatusCode", rootClass, null, null, "getOutGExamPaperVenueStatisticsStatusCode", null);
         myProperties[96] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsNumberExpected", rootClass, null, null, "getOutGExamPaperVenueStatisticsNumberExpected", null);
         myProperties[97] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsNumberReceived", rootClass, null, null, "getOutGExamPaperVenueStatisticsNumberReceived", null);
         myProperties[98] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsNumberAbsent", rootClass, null, null, "getOutGExamPaperVenueStatisticsNumberAbsent", null);
         myProperties[99] = new IndexedPropertyDescriptor("OutGExamPaperVenueStatisticsNumberMcqReceived", rootClass, null, null, "getOutGExamPaperVenueStatisticsNumberMcqReceived", null);
         myProperties[100] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[101] = new PropertyDescriptor("OutSecurityWsPrinterCode", rootClass, "getOutSecurityWsPrinterCode", null);
         myProperties[102] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[103] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[104] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[105] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[106] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[107] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[108] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[109] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[110] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
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
//         callMethod = Expsc10sLstExamPaperVenueSt.class.getMethod("execute", args);
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
