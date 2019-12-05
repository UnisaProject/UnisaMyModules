package Srsra01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srsra01sLstStudentAcademRecBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srsra01sLstStudentAcademRec.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srsra01sLstStudentAcademRec.class);
      bd.setDisplayName(Srsra01sLstStudentAcademRec.BEANNAME);
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
         myProperties[15] = new PropertyDescriptor("InPrintDetailsOfStudentAcademicRecordMkStudentNr", rootClass);
         myProperties[16] = new PropertyDescriptor("InPrintDetailsOfStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[17] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[18] = new PropertyDescriptor("InStartingFromStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[19] = new PropertyDescriptor("InStartingFromStudentAcademicRecordLastAcademicRegistrationYear", rootClass);
         myProperties[20] = new PropertyDescriptor("InLowStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[21] = new PropertyDescriptor("InLowStudentAcademicRecordLastAcademicRegistrationYear", rootClass);
         myProperties[22] = new PropertyDescriptor("InHighStudentAcademicRecordMkQualificationCode", rootClass);
         myProperties[23] = new PropertyDescriptor("InHighStudentAcademicRecordLastAcademicRegistrationYear", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[28] = new PropertyDescriptor("InOrderByCsfRadioButtonsButton1", rootClass);
         myProperties[28].setPropertyEditorClass(Srsra01sLstStudentAcademRecCsfRadioButtonsButton1PropertyEditor.class);
         myProperties[29] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[30] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[31] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[32] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[33] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[34] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[35] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[36] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[38] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[39] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[40] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[41] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollUpFlag", rootClass);
         myProperties[42] = new PropertyDescriptor("InCsfClientServerCommunicationsRgvScrollDownFlag", rootClass);
         myProperties[43] = new PropertyDescriptor("InCsfClientServerCommunicationsObjectRetrievedFlag", rootClass);
         myProperties[44] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[46] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[47] = new PropertyDescriptor("InFunctionTypePersonnelFlagCsfStringsString1", rootClass);
         myProperties[48] = new PropertyDescriptor("InCreditsOnlyIefSuppliedFlag", rootClass);
         myProperties[48].setPropertyEditorClass(Srsra01sLstStudentAcademRecIefSuppliedFlagPropertyEditor.class);
         myProperties[49] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[50] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[51] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[52] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[53] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[54] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[55] = new IndexedPropertyDescriptor("OutGCsfLineActionBarAction", rootClass, null, null, "getOutGCsfLineActionBarAction", null);
         myProperties[56] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordMkStudentNr", rootClass, null, null, "getOutGStudentAcademicRecordMkStudentNr", null);
         myProperties[57] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordMkQualificationCode", rootClass, null, null, "getOutGStudentAcademicRecordMkQualificationCode", null);
         myProperties[58] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordLastAcademicRegistrationYear", rootClass, null, null, "getOutGStudentAcademicRecordLastAcademicRegistrationYear", null);
         myProperties[59] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordFirstRegistrationDate", rootClass, null, null, "getOutGStudentAcademicRecordFirstRegistrationDate", null);
         myProperties[60] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordStatusCode", rootClass, null, null, "getOutGStudentAcademicRecordStatusCode", null);
         myProperties[61] = new IndexedPropertyDescriptor("OutGStudentAcademicRecordLastRegistrationDate", rootClass, null, null, "getOutGStudentAcademicRecordLastRegistrationDate", null);
         myProperties[62] = new IndexedPropertyDescriptor("OutGWsQualificationCode", rootClass, null, null, "getOutGWsQualificationCode", null);
         myProperties[63] = new IndexedPropertyDescriptor("OutGWsQualificationShortDescription", rootClass, null, null, "getOutGWsQualificationShortDescription", null);
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
         myProperties[87] = new PropertyDescriptor("OutFunctionTypePersonnelFlagCsfStringsString1", rootClass, "getOutFunctionTypePersonnelFlagCsfStringsString1", null);
         myProperties[88] = new PropertyDescriptor("OutCreditsOnlyIefSuppliedFlag", rootClass, "getOutCreditsOnlyIefSuppliedFlag", null);
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
//         callMethod = Srsra01sLstStudentAcademRec.class.getMethod("execute", args);
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
