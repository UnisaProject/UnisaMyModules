package Exfyq10h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Exfyq10sListFiYearStudBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Exfyq10sListFiYearStud.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Exfyq10sListFiYearStud.class);
      bd.setDisplayName(Exfyq10sListFiYearStud.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsStudyUnitCode", rootClass);
         myProperties[15] = new PropertyDescriptor("InCalledFromCsfStringsString1", rootClass);
         myProperties[16] = new PropertyDescriptor("InBeginActionDateStudentPaperLogUpdatedOn", rootClass);
         myProperties[17] = new PropertyDescriptor("InEndActionDateStudentPaperLogUpdatedOn", rootClass);
         myProperties[18] = new PropertyDescriptor("InRmkStatusWsGenericCodeCode", rootClass);
         myProperties[19] = new PropertyDescriptor("InRmkStatusWsGenericCodeEngDescription", rootClass);
         myProperties[20] = new PropertyDescriptor("InActionCsfStringsString2", rootClass);
         myProperties[21] = new PropertyDescriptor("InHighWsDepartmentCode", rootClass);
         myProperties[22] = new PropertyDescriptor("InHighWsDepartmentEngDescription", rootClass);
         myProperties[23] = new PropertyDescriptor("InHighWsUnisaCollegeCode", rootClass);
         myProperties[24] = new PropertyDescriptor("InHighWsUnisaCollegeAbbreviation", rootClass);
         myProperties[25] = new PropertyDescriptor("InHighWsUnisaSchoolCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InHighWsUnisaSchoolAbbreviation", rootClass);
         myProperties[27] = new PropertyDescriptor("InHighExamReMarkMkStudentNr", rootClass);
         myProperties[28] = new PropertyDescriptor("InHighExamReMarkMkStudyUnitCode", rootClass);
         myProperties[29] = new PropertyDescriptor("InLowWsDepartmentCode", rootClass);
         myProperties[30] = new PropertyDescriptor("InLowWsDepartmentEngDescription", rootClass);
         myProperties[31] = new PropertyDescriptor("InLowWsUnisaCollegeCode", rootClass);
         myProperties[32] = new PropertyDescriptor("InLowWsUnisaCollegeAbbreviation", rootClass);
         myProperties[33] = new PropertyDescriptor("InLowWsUnisaSchoolCode", rootClass);
         myProperties[34] = new PropertyDescriptor("InLowWsUnisaSchoolAbbreviation", rootClass);
         myProperties[35] = new PropertyDescriptor("InLowExamReMarkMkStudentNr", rootClass);
         myProperties[36] = new PropertyDescriptor("InLowExamReMarkMkStudyUnitCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InRmkTypeWsGenericCodeCode", rootClass);
         myProperties[38] = new PropertyDescriptor("InRmkTypeWsGenericCodeEngDescription", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsGenericCategoryCode", rootClass);
         myProperties[40] = new PropertyDescriptor("InWsUnisaSchoolCode", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsUnisaSchoolEngDescription", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsUnisaCollegeCode", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsUnisaCollegeEngDescription", rootClass);
         myProperties[44] = new PropertyDescriptor("InWsDepartmentCode", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsDepartmentEngDescription", rootClass);
         myProperties[46] = new PropertyDescriptor("InExamReMarkExamYear", rootClass);
         myProperties[47] = new PropertyDescriptor("InExamReMarkMkExamPeriodCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InExamReMarkType", rootClass);
         myProperties[49] = new PropertyDescriptor("InExamReMarkPrintBatch", rootClass);
         myProperties[50] = new PropertyDescriptor("InSecurityWsWorkstationCode", rootClass);
         myProperties[51] = new PropertyDescriptor("InSecurityWsPrinterCode", rootClass);
         myProperties[52] = new PropertyDescriptor("InSecurityWsUserNumber", rootClass);
         myProperties[53] = new PropertyDescriptor("InSecurityWsUserNumberOfLogonAttempts", rootClass);
         myProperties[54] = new PropertyDescriptor("InSecurityWsUserLoggedOnFlag", rootClass);
         myProperties[55] = new PropertyDescriptor("InSecurityWsUserInUsedFlag", rootClass);
         myProperties[56] = new PropertyDescriptor("InSecurityWsUserLastLogonDate", rootClass);
         myProperties[57] = new PropertyDescriptor("InSecurityWsUserName", rootClass);
         myProperties[58] = new PropertyDescriptor("InSecurityWsUserPersonnelNo", rootClass);
         myProperties[59] = new PropertyDescriptor("InSecurityWsUserPhoneNumber", rootClass);
         myProperties[60] = new PropertyDescriptor("InSecurityWsUserPassword", rootClass);
         myProperties[61] = new PropertyDescriptor("InSecurityWsActionCode", rootClass);
         myProperties[62] = new PropertyDescriptor("InSecurityWsActionDescription", rootClass);
         myProperties[63] = new PropertyDescriptor("InSecurityWsFunctionNumber", rootClass);
         myProperties[64] = new PropertyDescriptor("InSecurityWsFunctionTrancode", rootClass);
         myProperties[65] = new PropertyDescriptor("InSecurityWsFunctionDescription", rootClass);
         myProperties[66] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[67] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[68] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[69] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[70] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[71] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[72] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[73] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[74] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[75] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[76] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[77] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[78] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[79] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[80] = new PropertyDescriptor("OutRmkStatusWsGenericCodeCode", rootClass, "getOutRmkStatusWsGenericCodeCode", null);
         myProperties[81] = new PropertyDescriptor("OutRmkStatusWsGenericCodeEngDescription", rootClass, "getOutRmkStatusWsGenericCodeEngDescription", null);
         myProperties[82] = new PropertyDescriptor("OutWsUnisaSchoolCode", rootClass, "getOutWsUnisaSchoolCode", null);
         myProperties[83] = new PropertyDescriptor("OutWsUnisaSchoolEngDescription", rootClass, "getOutWsUnisaSchoolEngDescription", null);
         myProperties[84] = new PropertyDescriptor("OutWsUnisaCollegeCode", rootClass, "getOutWsUnisaCollegeCode", null);
         myProperties[85] = new PropertyDescriptor("OutWsUnisaCollegeEngDescription", rootClass, "getOutWsUnisaCollegeEngDescription", null);
         myProperties[86] = new PropertyDescriptor("OutWsDepartmentCode", rootClass, "getOutWsDepartmentCode", null);
         myProperties[87] = new PropertyDescriptor("OutWsDepartmentEngDescription", rootClass, "getOutWsDepartmentEngDescription", null);
         myProperties[88] = new PropertyDescriptor("OutScSecurityFunctionNumber", rootClass, "getOutScSecurityFunctionNumber", null);
         myProperties[89] = new PropertyDescriptor("OutScSecurityUserNumber", rootClass, "getOutScSecurityUserNumber", null);
         myProperties[90] = new PropertyDescriptor("OutScSecurityFunctionType", rootClass, "getOutScSecurityFunctionType", null);
         myProperties[91] = new PropertyDescriptor("OutSecurityWsUserNumber", rootClass, "getOutSecurityWsUserNumber", null);
         myProperties[92] = new PropertyDescriptor("OutSecurityWsUserNumberOfLogonAttempts", rootClass, "getOutSecurityWsUserNumberOfLogonAttempts", null);
         myProperties[93] = new PropertyDescriptor("OutSecurityWsUserLoggedOnFlag", rootClass, "getOutSecurityWsUserLoggedOnFlag", null);
         myProperties[94] = new PropertyDescriptor("OutSecurityWsUserInUsedFlag", rootClass, "getOutSecurityWsUserInUsedFlag", null);
         myProperties[95] = new PropertyDescriptor("OutSecurityWsUserLastLogonDate", rootClass, "getOutSecurityWsUserLastLogonDate", null);
         myProperties[96] = new PropertyDescriptor("OutSecurityWsUserName", rootClass, "getOutSecurityWsUserName", null);
         myProperties[97] = new PropertyDescriptor("OutSecurityWsUserPersonnelNo", rootClass, "getOutSecurityWsUserPersonnelNo", null);
         myProperties[98] = new PropertyDescriptor("OutSecurityWsUserPhoneNumber", rootClass, "getOutSecurityWsUserPhoneNumber", null);
         myProperties[99] = new PropertyDescriptor("OutSecurityWsUserPassword", rootClass, "getOutSecurityWsUserPassword", null);
         myProperties[100] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[101] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[102] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[103] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[104] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[105] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[106] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[107] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[108] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[109] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[110] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[111] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[112] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[113] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[114] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[115] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[116] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[117] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[118] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[119] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[120] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[121] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[122] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[123] = new IndexedPropertyDescriptor("OutStatusGIefSuppliedSelectChar", rootClass, null, null, "getOutStatusGIefSuppliedSelectChar", null);
         myProperties[124] = new IndexedPropertyDescriptor("OutStatusGWsUnisaCollegeCode", rootClass, null, null, "getOutStatusGWsUnisaCollegeCode", null);
         myProperties[125] = new IndexedPropertyDescriptor("OutStatusGWsUnisaCollegeAbbreviation", rootClass, null, null, "getOutStatusGWsUnisaCollegeAbbreviation", null);
         myProperties[126] = new IndexedPropertyDescriptor("OutStatusGWsUnisaSchoolCode", rootClass, null, null, "getOutStatusGWsUnisaSchoolCode", null);
         myProperties[127] = new IndexedPropertyDescriptor("OutStatusGWsUnisaSchoolAbbreviation", rootClass, null, null, "getOutStatusGWsUnisaSchoolAbbreviation", null);
         myProperties[128] = new IndexedPropertyDescriptor("OutStatusGWsDepartmentCode", rootClass, null, null, "getOutStatusGWsDepartmentCode", null);
         myProperties[129] = new IndexedPropertyDescriptor("OutStatusGWsDepartmentEngDescription", rootClass, null, null, "getOutStatusGWsDepartmentEngDescription", null);
         myProperties[130] = new IndexedPropertyDescriptor("OutStatusGExamReMarkMkStudentNr", rootClass, null, null, "getOutStatusGExamReMarkMkStudentNr", null);
         myProperties[131] = new IndexedPropertyDescriptor("OutStatusGExamReMarkMkStudyUnitCode", rootClass, null, null, "getOutStatusGExamReMarkMkStudyUnitCode", null);
         myProperties[132] = new IndexedPropertyDescriptor("OutStatusGExamReMarkPrintBatch", rootClass, null, null, "getOutStatusGExamReMarkPrintBatch", null);
         myProperties[133] = new IndexedPropertyDescriptor("OutStatusGExamReMarkRemarker", rootClass, null, null, "getOutStatusGExamReMarkRemarker", null);
         myProperties[134] = new IndexedPropertyDescriptor("OutStatusGWsStudentExamResultFinalMark", rootClass, null, null, "getOutStatusGWsStudentExamResultFinalMark", null);
         myProperties[135] = new IndexedPropertyDescriptor("OutStatusGWsStudentExamResultMkResultTypeCode", rootClass, null, null, "getOutStatusGWsStudentExamResultMkResultTypeCode", null);
         myProperties[136] = new IndexedPropertyDescriptor("OutStatusGWsStudentExamResultMkAcademicYear", rootClass, null, null, "getOutStatusGWsStudentExamResultMkAcademicYear", null);
         myProperties[137] = new IndexedPropertyDescriptor("OutStatusGWsStudentExamResultSemesterPeriod", rootClass, null, null, "getOutStatusGWsStudentExamResultSemesterPeriod", null);
         myProperties[138] = new IndexedPropertyDescriptor("OutStatusGStudentNameCsfStringsString28", rootClass, null, null, "getOutStatusGStudentNameCsfStringsString28", null);
         myProperties[139] = new IndexedPropertyDescriptor("OutStatusGRemarkerNameCsfStringsString28", rootClass, null, null, "getOutStatusGRemarkerNameCsfStringsString28", null);
         myProperties[140] = new IndexedPropertyDescriptor("OutStatusGExamPaperDetailsNr", rootClass, null, null, "getOutStatusGExamPaperDetailsNr", null);
         myProperties[141] = new IndexedPropertyDescriptor("OutStatusGShelfCsfStringsString7", rootClass, null, null, "getOutStatusGShelfCsfStringsString7", null);
         myProperties[142] = new IndexedPropertyDescriptor("OutStatusGStudentPaperLogActionGc79", rootClass, null, null, "getOutStatusGStudentPaperLogActionGc79", null);
         myProperties[143] = new IndexedPropertyDescriptor("OutStatusGStudentPaperLogRequestActionFrom", rootClass, null, null, "getOutStatusGStudentPaperLogRequestActionFrom", null);
         myProperties[144] = new IndexedPropertyDescriptor("OutStatusGStudentPaperLogUpdatedBy", rootClass, null, null, "getOutStatusGStudentPaperLogUpdatedBy", null);
         myProperties[145] = new IndexedPropertyDescriptor("OutStatusGStudentPaperLogUpdatedOn", rootClass, null, null, "getOutStatusGStudentPaperLogUpdatedOn", null);
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
//         callMethod = Exfyq10sListFiYearStud.class.getMethod("execute", args);
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
