package Srasa01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srasa01sLstAcademicRecordSunBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srasa01sLstAcademicRecordSun.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srasa01sLstAcademicRecordSun.class);
      bd.setDisplayName(Srasa01sLstAcademicRecordSun.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsWorkstationCode", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[16] = new PropertyDescriptor("InLowAcademicRecordStudyUnitMkStudyUnitCode", rootClass);
         myProperties[17] = new PropertyDescriptor("InLowAcademicRecordStudyUnitMkAcademicYear", rootClass);
         myProperties[18] = new PropertyDescriptor("InHighAcademicRecordStudyUnitMkStudyUnitCode", rootClass);
         myProperties[19] = new PropertyDescriptor("InHighAcademicRecordStudyUnitMkAcademicYear", rootClass);
         myProperties[20] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[21] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[22] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[23] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[24] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[25] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[26] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[27] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[28] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[29] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[36] = new PropertyDescriptor("InOrderByCsfRadioButtonsButton1", rootClass);
         myProperties[36].setPropertyEditorClass(Srasa01sLstAcademicRecordSunCsfRadioButtonsButton1PropertyEditor.class);
         myProperties[37] = new PropertyDescriptor("InStudentAcademicRecordMkStudentNr", rootClass);
         myProperties[38] = new PropertyDescriptor("InStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[39] = new PropertyDescriptor("InStartingFromAcademicRecordStudyUnitMkStudyUnitCode", rootClass);
         myProperties[40] = new PropertyDescriptor("InStartingFromAcademicRecordStudyUnitMkAcademicYear", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[43] = new PropertyDescriptor("InFunctionTypePersonnelFlagCsfStringsString1", rootClass);
         myProperties[44] = new IndexedPropertyDescriptor("InputGCsfLineActionBarAction", rootClass, null, null, "getInputGCsfLineActionBarAction", "setInputGCsfLineActionBarAction");
         myProperties[45] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitMkStudyUnitCode", rootClass, null, null, "getInGAcademicRecordStudyUnitMkStudyUnitCode", "setInGAcademicRecordStudyUnitMkStudyUnitCode");
         myProperties[46] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitMkAcademicYear", rootClass, null, null, "getInGAcademicRecordStudyUnitMkAcademicYear", "setInGAcademicRecordStudyUnitMkAcademicYear");
         myProperties[47] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitMkAcademicPeriod", rootClass, null, null, "getInGAcademicRecordStudyUnitMkAcademicPeriod", "setInGAcademicRecordStudyUnitMkAcademicPeriod");
         myProperties[48] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitFinalMark", rootClass, null, null, "getInGAcademicRecordStudyUnitFinalMark", "setInGAcademicRecordStudyUnitFinalMark");
         myProperties[49] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitCreditStatusCode", rootClass, null, null, "getInGAcademicRecordStudyUnitCreditStatusCode", "setInGAcademicRecordStudyUnitCreditStatusCode");
         myProperties[49].setPropertyEditorClass(Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCreditStatusCodePropertyEditor.class);
         myProperties[50] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitExemptionCode", rootClass, null, null, "getInGAcademicRecordStudyUnitExemptionCode", "setInGAcademicRecordStudyUnitExemptionCode");
         myProperties[50].setPropertyEditorClass(Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitExemptionCodePropertyEditor.class);
         myProperties[51] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitCancellationCode", rootClass, null, null, "getInGAcademicRecordStudyUnitCancellationCode", "setInGAcademicRecordStudyUnitCancellationCode");
         myProperties[51].setPropertyEditorClass(Srasa01sLstAcademicRecordSunAcademicRecordStudyUnitCancellationCodePropertyEditor.class);
         myProperties[52] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getInGAcademicRecordStudyUnitSupplementaryExamReasonCode", "setInGAcademicRecordStudyUnitSupplementaryExamReasonCode");
         myProperties[53] = new IndexedPropertyDescriptor("InGAcademicRecordStudyUnitResultDate", rootClass, null, null, "getInGAcademicRecordStudyUnitResultDate", "setInGAcademicRecordStudyUnitResultDate");
         myProperties[54] = new IndexedPropertyDescriptor("InGWsStudyUnitEngShortDescription", rootClass, null, null, "getInGWsStudyUnitEngShortDescription", "setInGWsStudyUnitEngShortDescription");
         myProperties[55] = new IndexedPropertyDescriptor("InGStudyUnitResultTypeCode", rootClass, null, null, "getInGStudyUnitResultTypeCode", "setInGStudyUnitResultTypeCode");
         myProperties[56] = new IndexedPropertyDescriptor("InGStudyUnitResultTypeEngDescription", rootClass, null, null, "getInGStudyUnitResultTypeEngDescription", "setInGStudyUnitResultTypeEngDescription");
         myProperties[57] = new IndexedPropertyDescriptor("InGStudyUnitResultTypeEngAbbreviation", rootClass, null, null, "getInGStudyUnitResultTypeEngAbbreviation", "setInGStudyUnitResultTypeEngAbbreviation");
         myProperties[58] = new PropertyDescriptor("InCreditsOnlyIefSuppliedFlag", rootClass);
         myProperties[58].setPropertyEditorClass(Srasa01sLstAcademicRecordSunIefSuppliedFlagPropertyEditor.class);
         myProperties[59] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[60] = new PropertyDescriptor("InFaxNrCsfStringsString132", rootClass);
         myProperties[61] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[62] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[63] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[64] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[65] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[66] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[67] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[68] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[69] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[70] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[71] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[72] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[73] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[74] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[75] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[76] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[77] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[78] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[79] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[80] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[81] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[82] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[83] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[84] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollUpFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollUpFlag", null);
         myProperties[85] = new PropertyDescriptor("OutCsfClientServerCommunicationsRgvScrollDownFlag", rootClass, "getOutCsfClientServerCommunicationsRgvScrollDownFlag", null);
         myProperties[86] = new PropertyDescriptor("OutCsfClientServerCommunicationsObjectRetrievedFlag", rootClass, "getOutCsfClientServerCommunicationsObjectRetrievedFlag", null);
         myProperties[87] = new PropertyDescriptor("OutStudentAcademicRecordMkStudentNr", rootClass, "getOutStudentAcademicRecordMkStudentNr", null);
         myProperties[88] = new PropertyDescriptor("OutStudentAcademicRecordMkQualificationCode", rootClass, "getOutStudentAcademicRecordMkQualificationCode", null);
         myProperties[89] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[90] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[91] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[92] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[93] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[94] = new PropertyDescriptor("OutStartingFromAcademicRecordStudyUnitMkStudyUnitCode", rootClass, "getOutStartingFromAcademicRecordStudyUnitMkStudyUnitCode", null);
         myProperties[95] = new PropertyDescriptor("OutStartingFromAcademicRecordStudyUnitMkAcademicYear", rootClass, "getOutStartingFromAcademicRecordStudyUnitMkAcademicYear", null);
         myProperties[96] = new PropertyDescriptor("OutWsQualificationCode", rootClass, "getOutWsQualificationCode", null);
         myProperties[97] = new PropertyDescriptor("OutWsQualificationEngDescription", rootClass, "getOutWsQualificationEngDescription", null);
         myProperties[98] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[99] = new IndexedPropertyDescriptor("OutGCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGCsfLineActionBarLineReturnCode", null);
         myProperties[100] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[101] = new IndexedPropertyDescriptor("OutGCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGCsfLineActionBarTranslatedAction", null);
         myProperties[102] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitMkStudyUnitCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitMkStudyUnitCode", null);
         myProperties[103] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitMkAcademicYear", rootClass, null, null, "getOutGAcademicRecordStudyUnitMkAcademicYear", null);
         myProperties[104] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitMkAcademicPeriod", rootClass, null, null, "getOutGAcademicRecordStudyUnitMkAcademicPeriod", null);
         myProperties[105] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitFinalMark", rootClass, null, null, "getOutGAcademicRecordStudyUnitFinalMark", null);
         myProperties[106] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitCreditStatusCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitCreditStatusCode", null);
         myProperties[107] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitExemptionCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitExemptionCode", null);
         myProperties[108] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitCancellationCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitCancellationCode", null);
         myProperties[109] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getOutGAcademicRecordStudyUnitSupplementaryExamReasonCode", null);
         myProperties[110] = new IndexedPropertyDescriptor("OutGAcademicRecordStudyUnitResultDate", rootClass, null, null, "getOutGAcademicRecordStudyUnitResultDate", null);
         myProperties[111] = new IndexedPropertyDescriptor("OutGWsStudyUnitEngShortDescription", rootClass, null, null, "getOutGWsStudyUnitEngShortDescription", null);
         myProperties[112] = new IndexedPropertyDescriptor("OutGStudyUnitResultTypeCode", rootClass, null, null, "getOutGStudyUnitResultTypeCode", null);
         myProperties[113] = new IndexedPropertyDescriptor("OutGStudyUnitResultTypeEngDescription", rootClass, null, null, "getOutGStudyUnitResultTypeEngDescription", null);
         myProperties[114] = new IndexedPropertyDescriptor("OutGStudyUnitResultTypeEngAbbreviation", rootClass, null, null, "getOutGStudyUnitResultTypeEngAbbreviation", null);
         myProperties[115] = new PropertyDescriptor("OutFunctionTypePersonnelFlagCsfStringsString1", rootClass, "getOutFunctionTypePersonnelFlagCsfStringsString1", null);
         myProperties[116] = new PropertyDescriptor("OutCreditsOnlyIefSuppliedFlag", rootClass, "getOutCreditsOnlyIefSuppliedFlag", null);
         myProperties[117] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[118] = new PropertyDescriptor("OutFaxNrCsfStringsString132", rootClass, "getOutFaxNrCsfStringsString132", null);
         myProperties[119] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[120] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
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
//         callMethod = Srasa01sLstAcademicRecordSun.class.getMethod("execute", args);
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
