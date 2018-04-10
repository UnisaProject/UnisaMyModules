package Srslf01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srslf01sDisplaySmsLogBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srslf01sDisplaySmsLog.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srslf01sDisplaySmsLog.class);
      bd.setDisplayName(Srslf01sDisplaySmsLog.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InCsfStringsString500", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsStaffV2Persno", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsStaffV2Surname", rootClass);
         myProperties[17] = new PropertyDescriptor("InWsStaffV2Initials", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsStaffV2Title", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsStaffV2MkRcCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[21] = new PropertyDescriptor("InStaffNameCsfStringsString30", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsStaffPersno", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsStaffSurname", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsStaffInitials", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsStaffTitle", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsStaffMkRcCode", rootClass);
         myProperties[27] = new IndexedPropertyDescriptor("InGSmsRequestBatchNr", rootClass, null, null, "getInGSmsRequestBatchNr", "setInGSmsRequestBatchNr");
         myProperties[28] = new IndexedPropertyDescriptor("InGSmsRequestRequestTimestamp", rootClass, null, null, "getInGSmsRequestRequestTimestamp", "setInGSmsRequestRequestTimestamp");
         myProperties[29] = new IndexedPropertyDescriptor("InGSmsRequestSmsMsg", rootClass, null, null, "getInGSmsRequestSmsMsg", "setInGSmsRequestSmsMsg");
         myProperties[30] = new IndexedPropertyDescriptor("InGSmsRequestMessageCnt", rootClass, null, null, "getInGSmsRequestMessageCnt", "setInGSmsRequestMessageCnt");
         myProperties[31] = new IndexedPropertyDescriptor("InGSmsRequestTotalCost", rootClass, null, null, "getInGSmsRequestTotalCost", "setInGSmsRequestTotalCost");
         myProperties[32] = new IndexedPropertyDescriptor("InGReqCsfLineActionBarAction", rootClass, null, null, "getInGReqCsfLineActionBarAction", "setInGReqCsfLineActionBarAction");
         myProperties[33] = new IndexedPropertyDescriptor("InGReqCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGReqCsfLineActionBarSelectionFlag", "setInGReqCsfLineActionBarSelectionFlag");
         myProperties[34] = new IndexedPropertyDescriptor("InGSmsLogMkBatchNr", rootClass, null, null, "getInGSmsLogMkBatchNr", "setInGSmsLogMkBatchNr");
         myProperties[35] = new IndexedPropertyDescriptor("InGSmsLogReferenceNr", rootClass, null, null, "getInGSmsLogReferenceNr", "setInGSmsLogReferenceNr");
         myProperties[36] = new IndexedPropertyDescriptor("InGSmsLogCellNr", rootClass, null, null, "getInGSmsLogCellNr", "setInGSmsLogCellNr");
         myProperties[37] = new IndexedPropertyDescriptor("InGSmsLogMessage", rootClass, null, null, "getInGSmsLogMessage", "setInGSmsLogMessage");
         myProperties[38] = new IndexedPropertyDescriptor("InGSmsLogSentOn", rootClass, null, null, "getInGSmsLogSentOn", "setInGSmsLogSentOn");
         myProperties[39] = new IndexedPropertyDescriptor("InGSmsLogMessageStatus", rootClass, null, null, "getInGSmsLogMessageStatus", "setInGSmsLogMessageStatus");
         myProperties[40] = new IndexedPropertyDescriptor("InGSmsCsfLineActionBarAction", rootClass, null, null, "getInGSmsCsfLineActionBarAction", "setInGSmsCsfLineActionBarAction");
         myProperties[41] = new IndexedPropertyDescriptor("InGSmsCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGSmsCsfLineActionBarSelectionFlag", "setInGSmsCsfLineActionBarSelectionFlag");
         myProperties[42] = new PropertyDescriptor("InStartingSmsLogMkBatchNr", rootClass);
         myProperties[43] = new PropertyDescriptor("InStartingSmsLogReferenceNr", rootClass);
         myProperties[44] = new PropertyDescriptor("InStartingSmsLogCellNr", rootClass);
         myProperties[45] = new PropertyDescriptor("InStartingSmsLogMessage", rootClass);
         myProperties[46] = new PropertyDescriptor("InStartingSmsLogSentOn", rootClass);
         myProperties[47] = new PropertyDescriptor("InStartingSmsLogMessageStatus", rootClass);
         myProperties[48] = new PropertyDescriptor("InLowSmsLogMkBatchNr", rootClass);
         myProperties[49] = new PropertyDescriptor("InLowSmsLogReferenceNr", rootClass);
         myProperties[50] = new PropertyDescriptor("InLowSmsLogCellNr", rootClass);
         myProperties[51] = new PropertyDescriptor("InLowSmsLogMessage", rootClass);
         myProperties[52] = new PropertyDescriptor("InLowSmsLogSentOn", rootClass);
         myProperties[53] = new PropertyDescriptor("InLowSmsLogMessageStatus", rootClass);
         myProperties[54] = new PropertyDescriptor("InHighSmsLogMkBatchNr", rootClass);
         myProperties[55] = new PropertyDescriptor("InHighSmsLogReferenceNr", rootClass);
         myProperties[56] = new PropertyDescriptor("InHighSmsLogCellNr", rootClass);
         myProperties[57] = new PropertyDescriptor("InHighSmsLogMessage", rootClass);
         myProperties[58] = new PropertyDescriptor("InHighSmsLogSentOn", rootClass);
         myProperties[59] = new PropertyDescriptor("InHighSmsLogMessageStatus", rootClass);
         myProperties[60] = new PropertyDescriptor("InSmsRequestBatchNr", rootClass);
         myProperties[61] = new PropertyDescriptor("InSmsRequestMkPersNr", rootClass);
         myProperties[62] = new PropertyDescriptor("InSmsRequestMkRcCode", rootClass);
         myProperties[63] = new PropertyDescriptor("InSmsRequestMkDepartmentCode", rootClass);
         myProperties[64] = new PropertyDescriptor("InSmsRequestRequestTimestamp", rootClass);
         myProperties[65] = new PropertyDescriptor("InSmsRequestSmsMsg", rootClass);
         myProperties[66] = new PropertyDescriptor("InSmsRequestControlCellNr", rootClass);
         myProperties[67] = new PropertyDescriptor("InSmsRequestMessageCnt", rootClass);
         myProperties[68] = new PropertyDescriptor("InSmsRequestTotalCost", rootClass);
         myProperties[69] = new PropertyDescriptor("InSmsRequestFromSystemGc26", rootClass);
         myProperties[70] = new PropertyDescriptor("InSmsRequestReasonGc27", rootClass);
         myProperties[71] = new PropertyDescriptor("InSmsRequestSelectionCriteria", rootClass);
         myProperties[72] = new PropertyDescriptor("InSmsLogMkBatchNr", rootClass);
         myProperties[73] = new PropertyDescriptor("InSmsLogSequenceNr", rootClass);
         myProperties[74] = new PropertyDescriptor("InSmsLogReferenceNr", rootClass);
         myProperties[75] = new PropertyDescriptor("InSmsLogCellNr", rootClass);
         myProperties[76] = new PropertyDescriptor("InSmsLogMessage", rootClass);
         myProperties[77] = new PropertyDescriptor("InSmsLogSentOn", rootClass);
         myProperties[78] = new PropertyDescriptor("InSmsLogDeliveredOn", rootClass);
         myProperties[79] = new PropertyDescriptor("InSmsLogWaspSequenceNr", rootClass);
         myProperties[80] = new PropertyDescriptor("InSmsLogMessageStatus", rootClass);
         myProperties[81] = new PropertyDescriptor("InSmsLogMkPersNr", rootClass);
         myProperties[82] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[83] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[84] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[85] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[86] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[87] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[88] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[89] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[90] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[91] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[92] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[93] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[94] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[95] = new PropertyDescriptor("OutRcDescrCsfStringsString60", rootClass, "getOutRcDescrCsfStringsString60", null);
         myProperties[96] = new PropertyDescriptor("OutWsGlEntityCode", rootClass, "getOutWsGlEntityCode", null);
         myProperties[97] = new PropertyDescriptor("OutWsGlEntityDescription", rootClass, "getOutWsGlEntityDescription", null);
         myProperties[98] = new PropertyDescriptor("OutWsFinanceCheckDigitParmsCodeString", rootClass, "getOutWsFinanceCheckDigitParmsCodeString", null);
         myProperties[99] = new PropertyDescriptor("OutWsFinanceCheckDigitParmsAction", rootClass, "getOutWsFinanceCheckDigitParmsAction", null);
         myProperties[100] = new PropertyDescriptor("OutWsFinanceCheckDigitParmsResultCode", rootClass, "getOutWsFinanceCheckDigitParmsResultCode", null);
         myProperties[101] = new PropertyDescriptor("OutWsStaffV2Persno", rootClass, "getOutWsStaffV2Persno", null);
         myProperties[102] = new PropertyDescriptor("OutWsStaffV2Surname", rootClass, "getOutWsStaffV2Surname", null);
         myProperties[103] = new PropertyDescriptor("OutWsStaffV2Initials", rootClass, "getOutWsStaffV2Initials", null);
         myProperties[104] = new PropertyDescriptor("OutWsStaffV2Title", rootClass, "getOutWsStaffV2Title", null);
         myProperties[105] = new PropertyDescriptor("OutWsStaffV2MkRcCode", rootClass, "getOutWsStaffV2MkRcCode", null);
         myProperties[106] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[107] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[108] = new PropertyDescriptor("OutStaffNameCsfStringsString30", rootClass, "getOutStaffNameCsfStringsString30", null);
         myProperties[109] = new PropertyDescriptor("OutWsStaffPersno", rootClass, "getOutWsStaffPersno", null);
         myProperties[110] = new PropertyDescriptor("OutWsStaffMkRcCode", rootClass, "getOutWsStaffMkRcCode", null);
         myProperties[111] = new PropertyDescriptor("OutWsStaffSurname", rootClass, "getOutWsStaffSurname", null);
         myProperties[112] = new PropertyDescriptor("OutWsStaffInitials", rootClass, "getOutWsStaffInitials", null);
         myProperties[113] = new PropertyDescriptor("OutWsStaffTitle", rootClass, "getOutWsStaffTitle", null);
         myProperties[114] = new IndexedPropertyDescriptor("OutGSmsRequestBatchNr", rootClass, null, null, "getOutGSmsRequestBatchNr", null);
         myProperties[115] = new IndexedPropertyDescriptor("OutGSmsRequestRequestTimestamp", rootClass, null, null, "getOutGSmsRequestRequestTimestamp", null);
         myProperties[116] = new IndexedPropertyDescriptor("OutGSmsRequestSmsMsg", rootClass, null, null, "getOutGSmsRequestSmsMsg", null);
         myProperties[117] = new IndexedPropertyDescriptor("OutGSmsRequestMessageCnt", rootClass, null, null, "getOutGSmsRequestMessageCnt", null);
         myProperties[118] = new IndexedPropertyDescriptor("OutGSmsRequestTotalCost", rootClass, null, null, "getOutGSmsRequestTotalCost", null);
         myProperties[119] = new IndexedPropertyDescriptor("OutGReqCsfLineActionBarAction", rootClass, null, null, "getOutGReqCsfLineActionBarAction", null);
         myProperties[120] = new IndexedPropertyDescriptor("OutGReqCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGReqCsfLineActionBarSelectionFlag", null);
         myProperties[121] = new IndexedPropertyDescriptor("OutGSmsLogMkBatchNr", rootClass, null, null, "getOutGSmsLogMkBatchNr", null);
         myProperties[122] = new IndexedPropertyDescriptor("OutGSmsLogReferenceNr", rootClass, null, null, "getOutGSmsLogReferenceNr", null);
         myProperties[123] = new IndexedPropertyDescriptor("OutGSmsLogCellNr", rootClass, null, null, "getOutGSmsLogCellNr", null);
         myProperties[124] = new IndexedPropertyDescriptor("OutGSmsLogMessage", rootClass, null, null, "getOutGSmsLogMessage", null);
         myProperties[125] = new IndexedPropertyDescriptor("OutGSmsLogSentOn", rootClass, null, null, "getOutGSmsLogSentOn", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutGSmsLogMessageStatus", rootClass, null, null, "getOutGSmsLogMessageStatus", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutGSmsCsfLineActionBarAction", rootClass, null, null, "getOutGSmsCsfLineActionBarAction", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutGSmsCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGSmsCsfLineActionBarSelectionFlag", null);
         myProperties[129] = new PropertyDescriptor("OutSmsRequestBatchNr", rootClass, "getOutSmsRequestBatchNr", null);
         myProperties[130] = new PropertyDescriptor("OutSmsRequestMkPersNr", rootClass, "getOutSmsRequestMkPersNr", null);
         myProperties[131] = new PropertyDescriptor("OutSmsRequestMkRcCode", rootClass, "getOutSmsRequestMkRcCode", null);
         myProperties[132] = new PropertyDescriptor("OutSmsRequestMkDepartmentCode", rootClass, "getOutSmsRequestMkDepartmentCode", null);
         myProperties[133] = new PropertyDescriptor("OutSmsRequestRequestTimestamp", rootClass, "getOutSmsRequestRequestTimestamp", null);
         myProperties[134] = new PropertyDescriptor("OutSmsRequestSmsMsg", rootClass, "getOutSmsRequestSmsMsg", null);
         myProperties[135] = new PropertyDescriptor("OutSmsRequestControlCellNr", rootClass, "getOutSmsRequestControlCellNr", null);
         myProperties[136] = new PropertyDescriptor("OutSmsRequestMessageCnt", rootClass, "getOutSmsRequestMessageCnt", null);
         myProperties[137] = new PropertyDescriptor("OutSmsRequestTotalCost", rootClass, "getOutSmsRequestTotalCost", null);
         myProperties[138] = new PropertyDescriptor("OutSmsRequestFromSystemGc26", rootClass, "getOutSmsRequestFromSystemGc26", null);
         myProperties[139] = new PropertyDescriptor("OutSmsRequestReasonGc27", rootClass, "getOutSmsRequestReasonGc27", null);
         myProperties[140] = new PropertyDescriptor("OutSmsRequestSelectionCriteria", rootClass, "getOutSmsRequestSelectionCriteria", null);
         myProperties[141] = new PropertyDescriptor("OutSmsLogMkBatchNr", rootClass, "getOutSmsLogMkBatchNr", null);
         myProperties[142] = new PropertyDescriptor("OutSmsLogSequenceNr", rootClass, "getOutSmsLogSequenceNr", null);
         myProperties[143] = new PropertyDescriptor("OutSmsLogReferenceNr", rootClass, "getOutSmsLogReferenceNr", null);
         myProperties[144] = new PropertyDescriptor("OutSmsLogCellNr", rootClass, "getOutSmsLogCellNr", null);
         myProperties[145] = new PropertyDescriptor("OutSmsLogMessage", rootClass, "getOutSmsLogMessage", null);
         myProperties[146] = new PropertyDescriptor("OutSmsLogSentOn", rootClass, "getOutSmsLogSentOn", null);
         myProperties[147] = new PropertyDescriptor("OutSmsLogDeliveredOn", rootClass, "getOutSmsLogDeliveredOn", null);
         myProperties[148] = new PropertyDescriptor("OutSmsLogWaspSequenceNr", rootClass, "getOutSmsLogWaspSequenceNr", null);
         myProperties[149] = new PropertyDescriptor("OutSmsLogMessageStatus", rootClass, "getOutSmsLogMessageStatus", null);
         myProperties[150] = new PropertyDescriptor("OutSmsLogMkPersNr", rootClass, "getOutSmsLogMkPersNr", null);
         myProperties[151] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[152] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[153] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[154] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[155] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[156] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[157] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[158] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[159] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[160] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[161] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[162] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[163] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[164] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[165] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[166] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[167] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[168] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[169] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[170] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[171] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
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
//         callMethod = Srslf01sDisplaySmsLog.class.getMethod("execute", args);
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
