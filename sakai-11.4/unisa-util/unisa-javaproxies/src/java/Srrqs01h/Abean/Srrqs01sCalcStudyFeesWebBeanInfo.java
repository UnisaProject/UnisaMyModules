package Srrqs01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srrqs01sCalcStudyFeesWebBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srrqs01sCalcStudyFeesWeb.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srrqs01sCalcStudyFeesWeb.class);
      bd.setDisplayName(Srrqs01sCalcStudyFeesWeb.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsQualificationCode", rootClass);
         myProperties[15] = new PropertyDescriptor("InMtrExemptionIefSuppliedFlag", rootClass);
         myProperties[15].setPropertyEditorClass(Srrqs01sCalcStudyFeesWebIefSuppliedFlagPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InMtrExemptionIefSuppliedTotalCurrency", rootClass);
         myProperties[17] = new PropertyDescriptor("InSmartcardIefSuppliedFlag", rootClass);
         myProperties[17].setPropertyEditorClass(Srrqs01sCalcStudyFeesWebIefSuppliedFlagPropertyEditor.class);
         myProperties[18] = new PropertyDescriptor("InSmartcardIefSuppliedTotalCurrency", rootClass);
         myProperties[19] = new PropertyDescriptor("InWsAddressAddressLine1", rootClass);
         myProperties[20] = new PropertyDescriptor("InWsAddressAddressLine2", rootClass);
         myProperties[21] = new PropertyDescriptor("InWsAddressAddressLine3", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsAddressAddressLine4", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsAddressAddressLine5", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsAddressAddressLine6", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsAddressPostalCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsAddressEmailAddress", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[28] = new PropertyDescriptor("InLateRegIefSuppliedFlag", rootClass);
         myProperties[28].setPropertyEditorClass(Srrqs01sCalcStudyFeesWebIefSuppliedFlagPropertyEditor.class);
         myProperties[29] = new PropertyDescriptor("InMatricExemFeeIefSuppliedAverageCurrency", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[41] = new IndexedPropertyDescriptor("InGCsfLineActionBarAction", rootClass, null, null, "getInGCsfLineActionBarAction", "setInGCsfLineActionBarAction");
         myProperties[42] = new IndexedPropertyDescriptor("InGStudentStudyUnitMkStudyUnitCode", rootClass, null, null, "getInGStudentStudyUnitMkStudyUnitCode", "setInGStudentStudyUnitMkStudyUnitCode");
         myProperties[43] = new IndexedPropertyDescriptor("InGStudentStudyUnitSemesterPeriod", rootClass, null, null, "getInGStudentStudyUnitSemesterPeriod", "setInGStudentStudyUnitSemesterPeriod");
         myProperties[44] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[45] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[46] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsCountryCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsCountryEngDescription", rootClass);
         myProperties[49] = new PropertyDescriptor("InPrintNqfCreditsIefSuppliedFlag", rootClass);
         myProperties[49].setPropertyEditorClass(Srrqs01sCalcStudyFeesWebIefSuppliedFlagPropertyEditor.class);
         myProperties[50] = new PropertyDescriptor("InPrintNqfLevelIefSuppliedFlag", rootClass);
         myProperties[50].setPropertyEditorClass(Srrqs01sCalcStudyFeesWebIefSuppliedFlagPropertyEditor.class);
         myProperties[51] = new PropertyDescriptor("OutRegPaymentIefSuppliedTotalCurrency", rootClass, "getOutRegPaymentIefSuppliedTotalCurrency", null);
         myProperties[52] = new PropertyDescriptor("OutTotalIefSuppliedTotalCurrency", rootClass, "getOutTotalIefSuppliedTotalCurrency", null);
         myProperties[53] = new PropertyDescriptor("OutSmartcardIefSuppliedFlag", rootClass, "getOutSmartcardIefSuppliedFlag", null);
         myProperties[54] = new PropertyDescriptor("OutSmartcardIefSuppliedTotalCurrency", rootClass, "getOutSmartcardIefSuppliedTotalCurrency", null);
         myProperties[55] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[56] = new PropertyDescriptor("OutWsStudentMkCorrespondenceLanguage", rootClass, "getOutWsStudentMkCorrespondenceLanguage", null);
         myProperties[57] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[58] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[59] = new PropertyDescriptor("OutWsStudentFirstNames", rootClass, "getOutWsStudentFirstNames", null);
         myProperties[60] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[61] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[62] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
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
         myProperties[83] = new PropertyDescriptor("OutWsCountryCode", rootClass, "getOutWsCountryCode", null);
         myProperties[84] = new PropertyDescriptor("OutWsCountryEngDescription", rootClass, "getOutWsCountryEngDescription", null);
         myProperties[85] = new PropertyDescriptor("OutPrintNqfCreditsIefSuppliedFlag", rootClass, "getOutPrintNqfCreditsIefSuppliedFlag", null);
         myProperties[86] = new PropertyDescriptor("OutPrintNqfLevelIefSuppliedFlag", rootClass, "getOutPrintNqfLevelIefSuppliedFlag", null);
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
//         callMethod = Srrqs01sCalcStudyFeesWeb.class.getMethod("execute", args);
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
