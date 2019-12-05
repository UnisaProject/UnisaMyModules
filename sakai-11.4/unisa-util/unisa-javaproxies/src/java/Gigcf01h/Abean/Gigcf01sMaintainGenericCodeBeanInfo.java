package Gigcf01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Gigcf01sMaintainGenericCodeBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Gigcf01sMaintainGenericCode.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Gigcf01sMaintainGenericCode.class);
      bd.setDisplayName(Gigcf01sMaintainGenericCode.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InCsfEventCommunicationsRgvExistingLineSelectedFlag", rootClass);
         myProperties[15] = new PropertyDescriptor("InCsfEventCommunicationsRgvNewLineSelectedFlag", rootClass);
         myProperties[16] = new PropertyDescriptor("InGenericCodeCode", rootClass);
         myProperties[17] = new PropertyDescriptor("InGenericCodeEngDescription", rootClass);
         myProperties[18] = new PropertyDescriptor("InGenericCodeAfrDescription", rootClass);
         myProperties[19] = new PropertyDescriptor("InGenericCodeInUseFlag", rootClass);
         myProperties[20] = new PropertyDescriptor("InGenericCodeInfo", rootClass);
         myProperties[21] = new PropertyDescriptor("InGenericCategoryCode", rootClass);
         myProperties[22] = new PropertyDescriptor("InGenericCategoryDescription", rootClass);
         myProperties[23] = new PropertyDescriptor("InGenericCategoryInUseFlag", rootClass);
         myProperties[24] = new IndexedPropertyDescriptor("InGGenericCodeCode", rootClass, null, null, "getInGGenericCodeCode", "setInGGenericCodeCode");
         myProperties[25] = new IndexedPropertyDescriptor("InGGenericCodeEngDescription", rootClass, null, null, "getInGGenericCodeEngDescription", "setInGGenericCodeEngDescription");
         myProperties[26] = new IndexedPropertyDescriptor("InGGenericCodeAfrDescription", rootClass, null, null, "getInGGenericCodeAfrDescription", "setInGGenericCodeAfrDescription");
         myProperties[27] = new IndexedPropertyDescriptor("InGGenericCodeInUseFlag", rootClass, null, null, "getInGGenericCodeInUseFlag", "setInGGenericCodeInUseFlag");
         myProperties[28] = new IndexedPropertyDescriptor("InGGenericCodeInfo", rootClass, null, null, "getInGGenericCodeInfo", "setInGGenericCodeInfo");
         myProperties[29] = new IndexedPropertyDescriptor("InGCsfLineActionBarAction", rootClass, null, null, "getInGCsfLineActionBarAction", "setInGCsfLineActionBarAction");
         myProperties[30] = new IndexedPropertyDescriptor("InGCsfLineActionBarLineReturnCode", rootClass, null, null, "getInGCsfLineActionBarLineReturnCode", "setInGCsfLineActionBarLineReturnCode");
         myProperties[31] = new IndexedPropertyDescriptor("InGCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGCsfLineActionBarSelectionFlag", "setInGCsfLineActionBarSelectionFlag");
         myProperties[32] = new IndexedPropertyDescriptor("InGCsfLineActionBarTranslatedAction", rootClass, null, null, "getInGCsfLineActionBarTranslatedAction", "setInGCsfLineActionBarTranslatedAction");
         myProperties[33] = new PropertyDescriptor("InputWsUserNumber", rootClass);
         myProperties[34] = new PropertyDescriptor("InputWsPrinterCode", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[45] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[46] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[47] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[48] = new PropertyDescriptor("OutCsfEventCommunicationsRgvExistingLineSelectedFlag", rootClass, "getOutCsfEventCommunicationsRgvExistingLineSelectedFlag", null);
         myProperties[49] = new PropertyDescriptor("OutCsfEventCommunicationsRgvNewLineSelectedFlag", rootClass, "getOutCsfEventCommunicationsRgvNewLineSelectedFlag", null);
         myProperties[50] = new PropertyDescriptor("OutGenericCodeCode", rootClass, "getOutGenericCodeCode", null);
         myProperties[51] = new PropertyDescriptor("OutGenericCodeEngDescription", rootClass, "getOutGenericCodeEngDescription", null);
         myProperties[52] = new PropertyDescriptor("OutGenericCodeAfrDescription", rootClass, "getOutGenericCodeAfrDescription", null);
         myProperties[53] = new PropertyDescriptor("OutGenericCodeInUseFlag", rootClass, "getOutGenericCodeInUseFlag", null);
         myProperties[54] = new PropertyDescriptor("OutGenericCodeInfo", rootClass, "getOutGenericCodeInfo", null);
         myProperties[55] = new PropertyDescriptor("OutWizfuncReportingControlPath", rootClass, "getOutWizfuncReportingControlPath", null);
         myProperties[56] = new PropertyDescriptor("OutWizfuncReportingControlFilename", rootClass, "getOutWizfuncReportingControlFilename", null);
         myProperties[57] = new PropertyDescriptor("OutWizfuncReportingControlSupressFormFeed", rootClass, "getOutWizfuncReportingControlSupressFormFeed", null);
         myProperties[58] = new PropertyDescriptor("OutWizfuncReportingControlPrintOptions", rootClass, "getOutWizfuncReportingControlPrintOptions", null);
         myProperties[59] = new PropertyDescriptor("OutWizfuncReportingControlPrinterCode", rootClass, "getOutWizfuncReportingControlPrinterCode", null);
         myProperties[60] = new PropertyDescriptor("OutWizfuncReportingControlFunction", rootClass, "getOutWizfuncReportingControlFunction", null);
         myProperties[61] = new PropertyDescriptor("OutWizfuncReportingControlPathAndFilename", rootClass, "getOutWizfuncReportingControlPathAndFilename", null);
         myProperties[62] = new PropertyDescriptor("OutWizfuncReportingControlProgramName", rootClass, "getOutWizfuncReportingControlProgramName", null);
         myProperties[63] = new PropertyDescriptor("OutWizfuncReportingControlReportWidth", rootClass, "getOutWizfuncReportingControlReportWidth", null);
         myProperties[64] = new PropertyDescriptor("OutWizfuncReportingControlStartingLineCount", rootClass, "getOutWizfuncReportingControlStartingLineCount", null);
         myProperties[65] = new PropertyDescriptor("OutWizfuncReportingControlEndingLineCount", rootClass, "getOutWizfuncReportingControlEndingLineCount", null);
         myProperties[66] = new PropertyDescriptor("OutGenericCategoryCode", rootClass, "getOutGenericCategoryCode", null);
         myProperties[67] = new PropertyDescriptor("OutGenericCategoryDescription", rootClass, "getOutGenericCategoryDescription", null);
         myProperties[68] = new PropertyDescriptor("OutGenericCategoryInUseFlag", rootClass, "getOutGenericCategoryInUseFlag", null);
         myProperties[69] = new IndexedPropertyDescriptor("OutGGenericCodeCode", rootClass, null, null, "getOutGGenericCodeCode", null);
         myProperties[70] = new IndexedPropertyDescriptor("OutGGenericCodeEngDescription", rootClass, null, null, "getOutGGenericCodeEngDescription", null);
         myProperties[71] = new IndexedPropertyDescriptor("OutGGenericCodeAfrDescription", rootClass, null, null, "getOutGGenericCodeAfrDescription", null);
         myProperties[72] = new IndexedPropertyDescriptor("OutGGenericCodeInUseFlag", rootClass, null, null, "getOutGGenericCodeInUseFlag", null);
         myProperties[73] = new IndexedPropertyDescriptor("OutGGenericCodeInfo", rootClass, null, null, "getOutGGenericCodeInfo", null);
         myProperties[74] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[75] = new IndexedPropertyDescriptor("OutGCsfLineActionBarLineReturnCode", rootClass, null, null, "getOutGCsfLineActionBarLineReturnCode", null);
         myProperties[76] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[77] = new IndexedPropertyDescriptor("OutGCsfLineActionBarTranslatedAction", rootClass, null, null, "getOutGCsfLineActionBarTranslatedAction", null);
         myProperties[78] = new PropertyDescriptor("OutputWsUserNumber", rootClass, "getOutputWsUserNumber", null);
         myProperties[79] = new PropertyDescriptor("OutputWsPrinterCode", rootClass, "getOutputWsPrinterCode", null);
         myProperties[80] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[81] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[82] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[83] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[84] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[85] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[86] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[87] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[88] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[89] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[90] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[91] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[92] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[93] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[94] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[95] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[96] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[97] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[98] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[99] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[100] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
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
//         callMethod = Gigcf01sMaintainGenericCode.class.getMethod("execute", args);
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
