package Exfyq01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Exfyq01sMntFiYearStudConcBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Exfyq01sMntFiYearStudConc.class;
 
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
         new BeanDescriptor(Exfyq01sMntFiYearStudConc.class);
      bd.setDisplayName(Exfyq01sMntFiYearStudConc.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InResponsibleLecturerWsStaffPersno", rootClass);
         myProperties[15] = new PropertyDescriptor("InResponsibleLecturerWsStaffName", rootClass);
         myProperties[16] = new PropertyDescriptor("InResponsibleLecturerWsStaffNovellUserId", rootClass);
         myProperties[17] = new PropertyDescriptor("InAcademicSupportWsGenericCodeCode", rootClass);
         myProperties[18] = new PropertyDescriptor("InAcademicSupportWsGenericCodeEngDescription", rootClass);
         myProperties[19] = new PropertyDescriptor("InAlternativeInformationWsGenericCodeCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InAlternativeInformationWsGenericCodeEngDescription", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsWsFinalYearStudentAccessmentProcessedFlag", rootClass);
         myProperties[21].setPropertyEditorClass(Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentProcessedFlagPropertyEditor.class);
         myProperties[22] = new PropertyDescriptor("InWsWsFinalYearStudentAccessmentDeclinedFlag", rootClass);
         myProperties[22].setPropertyEditorClass(Exfyq01sMntFiYearStudConcWsFinalYearStudentAccessmentDeclinedFlagPropertyEditor.class);
         myProperties[23] = new PropertyDescriptor("InExamReMarkExamYear", rootClass);
         myProperties[24] = new PropertyDescriptor("InExamReMarkMkExamPeriodCode", rootClass);
         myProperties[25] = new PropertyDescriptor("InExamReMarkMkStudentNr", rootClass);
         myProperties[26] = new PropertyDescriptor("InExamReMarkMkStudyUnitCode", rootClass);
         myProperties[27] = new PropertyDescriptor("InExamReMarkType", rootClass);
         myProperties[28] = new PropertyDescriptor("InExamReMarkTrackStatus", rootClass);
         myProperties[29] = new PropertyDescriptor("InExamReMarkRemarker", rootClass);
         myProperties[30] = new PropertyDescriptor("InExamReMarkAssessOptGc91", rootClass);
         myProperties[31] = new PropertyDescriptor("InExamReMarkAssessOptOther", rootClass);
         myProperties[32] = new PropertyDescriptor("InExamReMarkSupportProvGc92", rootClass);
         myProperties[33] = new PropertyDescriptor("InExamReMarkSupportProdOther", rootClass);
         myProperties[34] = new PropertyDescriptor("InExamReMarkRevisedFinalMark", rootClass);
         myProperties[35] = new PropertyDescriptor("InExamReMarkAuthResponseEmail", rootClass);
         myProperties[36] = new PropertyDescriptor("InExamReMarkDeclineOptGc98", rootClass);
         myProperties[37] = new PropertyDescriptor("InExamReMarkDeclineOptOther", rootClass);
         myProperties[38] = new PropertyDescriptor("InSecurityWsWorkstationCode", rootClass);
         myProperties[39] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[40] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[41] = new PropertyDescriptor("InSecurityWsUserNovellUserCode", rootClass);
         myProperties[42] = new PropertyDescriptor("InSecurityWsActionCode", rootClass);
         myProperties[43] = new PropertyDescriptor("InSecurityWsActionDescription", rootClass);
         myProperties[44] = new PropertyDescriptor("InSecurityWsFunctionNumber", rootClass);
         myProperties[45] = new PropertyDescriptor("InSecurityWsFunctionTrancode", rootClass);
         myProperties[46] = new PropertyDescriptor("InSecurityWsFunctionDescription", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[48] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[49] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[50] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[51] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[52] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[53] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[54] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[55] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[56] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[57] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[58] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[59] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[60] = new PropertyDescriptor("InStudentPaperLogRequestActionFrom", rootClass);
         myProperties[61] = new PropertyDescriptor("InStudentPaperLogComment0", rootClass);
         myProperties[62] = new PropertyDescriptor("OutDeclineOptionWsGenericCodeCode", rootClass, "getOutDeclineOptionWsGenericCodeCode", null);
         myProperties[63] = new PropertyDescriptor("OutDeclineOptionWsGenericCodeEngDescription", rootClass, "getOutDeclineOptionWsGenericCodeEngDescription", null);
         myProperties[64] = new PropertyDescriptor("OutStatusWsGenericCodeCode", rootClass, "getOutStatusWsGenericCodeCode", null);
         myProperties[65] = new PropertyDescriptor("OutStatusWsGenericCodeEngDescription", rootClass, "getOutStatusWsGenericCodeEngDescription", null);
         myProperties[66] = new PropertyDescriptor("OutExamReMarkExamYear", rootClass, "getOutExamReMarkExamYear", null);
         myProperties[67] = new PropertyDescriptor("OutExamReMarkMkExamPeriodCode", rootClass, "getOutExamReMarkMkExamPeriodCode", null);
         myProperties[68] = new PropertyDescriptor("OutExamReMarkMkStudentNr", rootClass, "getOutExamReMarkMkStudentNr", null);
         myProperties[69] = new PropertyDescriptor("OutExamReMarkMkStudyUnitCode", rootClass, "getOutExamReMarkMkStudyUnitCode", null);
         myProperties[70] = new PropertyDescriptor("OutExamReMarkStatusCode", rootClass, "getOutExamReMarkStatusCode", null);
         myProperties[71] = new PropertyDescriptor("OutExamReMarkMarksPrintedFlag", rootClass, "getOutExamReMarkMarksPrintedFlag", null);
         myProperties[72] = new PropertyDescriptor("OutExamReMarkApplicationPrintedFlag", rootClass, "getOutExamReMarkApplicationPrintedFlag", null);
         myProperties[73] = new PropertyDescriptor("OutExamReMarkOriginalFinalMark", rootClass, "getOutExamReMarkOriginalFinalMark", null);
         myProperties[74] = new PropertyDescriptor("OutExamReMarkRevisedFinalMark", rootClass, "getOutExamReMarkRevisedFinalMark", null);
         myProperties[75] = new PropertyDescriptor("OutExamReMarkOriginalResultTypeCode", rootClass, "getOutExamReMarkOriginalResultTypeCode", null);
         myProperties[76] = new PropertyDescriptor("OutExamReMarkRevisedResultTypeCode", rootClass, "getOutExamReMarkRevisedResultTypeCode", null);
         myProperties[77] = new PropertyDescriptor("OutExamReMarkMoneyRefundedFlag", rootClass, "getOutExamReMarkMoneyRefundedFlag", null);
         myProperties[78] = new PropertyDescriptor("OutExamReMarkType", rootClass, "getOutExamReMarkType", null);
         myProperties[79] = new PropertyDescriptor("OutExamReMarkPrintBatch", rootClass, "getOutExamReMarkPrintBatch", null);
         myProperties[80] = new PropertyDescriptor("OutExamReMarkTrackStatus", rootClass, "getOutExamReMarkTrackStatus", null);
         myProperties[81] = new PropertyDescriptor("OutExamReMarkRemarker", rootClass, "getOutExamReMarkRemarker", null);
         myProperties[82] = new PropertyDescriptor("OutExamReMarkAssessOptGc91", rootClass, "getOutExamReMarkAssessOptGc91", null);
         myProperties[83] = new PropertyDescriptor("OutExamReMarkAssessOptOther", rootClass, "getOutExamReMarkAssessOptOther", null);
         myProperties[84] = new PropertyDescriptor("OutExamReMarkSupportProvGc92", rootClass, "getOutExamReMarkSupportProvGc92", null);
         myProperties[85] = new PropertyDescriptor("OutExamReMarkSupportProdOther", rootClass, "getOutExamReMarkSupportProdOther", null);
         myProperties[86] = new PropertyDescriptor("OutExamReMarkAuthResponseEmail", rootClass, "getOutExamReMarkAuthResponseEmail", null);
         myProperties[87] = new PropertyDescriptor("OutExamReMarkDeclineOptGc98", rootClass, "getOutExamReMarkDeclineOptGc98", null);
         myProperties[88] = new PropertyDescriptor("OutExamReMarkDeclineOptOther", rootClass, "getOutExamReMarkDeclineOptOther", null);
         myProperties[89] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[90] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[91] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[92] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[93] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[94] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[95] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[96] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[97] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[98] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[99] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[100] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[101] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[102] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[103] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[104] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[105] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[106] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[107] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[112] = new PropertyDescriptor("OutResponsibleLecturerWsStaffPersno", rootClass, "getOutResponsibleLecturerWsStaffPersno", null);
         myProperties[113] = new PropertyDescriptor("OutResponsibleLecturerWsStaffName", rootClass, "getOutResponsibleLecturerWsStaffName", null);
         myProperties[114] = new PropertyDescriptor("OutResponsibleLecturerWsStaffNovellUserId", rootClass, "getOutResponsibleLecturerWsStaffNovellUserId", null);
         myProperties[115] = new PropertyDescriptor("OutAcademicSupportWsGenericCodeCode", rootClass, "getOutAcademicSupportWsGenericCodeCode", null);
         myProperties[116] = new PropertyDescriptor("OutAcademicSupportWsGenericCodeEngDescription", rootClass, "getOutAcademicSupportWsGenericCodeEngDescription", null);
         myProperties[117] = new PropertyDescriptor("OutAlternativeInformationWsGenericCodeCode", rootClass, "getOutAlternativeInformationWsGenericCodeCode", null);
         myProperties[118] = new PropertyDescriptor("OutAlternativeInformationWsGenericCodeEngDescription", rootClass, "getOutAlternativeInformationWsGenericCodeEngDescription", null);
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
//         callMethod = Exfyq01sMntFiYearStudConc.class.getMethod("execute", args);
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
