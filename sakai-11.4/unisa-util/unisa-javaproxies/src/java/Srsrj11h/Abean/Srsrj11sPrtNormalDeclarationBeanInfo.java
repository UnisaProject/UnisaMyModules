package Srsrj11h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srsrj11sPrtNormalDeclarationBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srsrj11sPrtNormalDeclaration.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srsrj11sPrtNormalDeclaration.class);
      bd.setDisplayName(Srsrj11sPrtNormalDeclaration.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InPrintAcademicSupplementIefSuppliedFlag", rootClass);
         myProperties[14].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor.class);
         myProperties[15] = new PropertyDescriptor("InNqfLevelIefSuppliedFlag", rootClass);
         myProperties[15].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor.class);
         myProperties[16] = new PropertyDescriptor("InNqfCreditsIefSuppliedFlag", rootClass);
         myProperties[16].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor.class);
         myProperties[17] = new PropertyDescriptor("InFunctionTypeCsfStringsString6", rootClass);
         myProperties[18] = new PropertyDescriptor("InWsQualificationCode", rootClass);
         myProperties[19] = new PropertyDescriptor("In1DistinctWsMajorSubjectCode", rootClass);
         myProperties[20] = new PropertyDescriptor("In2DistinctWsMajorSubjectCode", rootClass);
         myProperties[21] = new PropertyDescriptor("In3DistinctWsMajorSubjectCode", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsProvSubjDeclarationYear", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsProvSubjDeclarationLanguage", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsProvSubjDeclarationExamDate", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsProvSubjDeclarationHours", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsProvSubjDeclarationReportDate", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsProvSubjDeclarationLastPracticalDate", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsProvSubjDeclarationReference", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsProvSubjDeclarationCoverLetter", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsProvSubjDeclarationMarksFlag", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsProvSubjDeclarationOldDegree", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsProvSubjDeclarationCoursesFlag", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsProvSubjDeclarationNumberCopies", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsProvSubjDeclarationLlbOtherLine1", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsProvSubjDeclarationLlbOtherLine2", rootClass);
         myProperties[36] = new PropertyDescriptor("InWsProvSubjDeclarationLlbOtherLine3", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsProvSubjDeclarationEducInst1", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsProvSubjDeclarationEducInst2", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsProvSubjDeclarationEducInst3", rootClass);
         myProperties[40] = new PropertyDescriptor("In1WsStudyUnitCode", rootClass);
         myProperties[41] = new PropertyDescriptor("In2WsStudyUnitCode", rootClass);
         myProperties[42] = new PropertyDescriptor("In3WsStudyUnitCode", rootClass);
         myProperties[43] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[44] = new PropertyDescriptor("InStudentAnnualRecordPersonnelNr", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[46] = new PropertyDescriptor("InWizfuncReportingControlPrinterCode", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsWorkstationCode", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[49] = new PropertyDescriptor("InWsServiceFeesReasonCode", rootClass);
         myProperties[49].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationWsServiceFeesReasonCodePropertyEditor.class);
         myProperties[50] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[51] = new PropertyDescriptor("InSepWsProvSubjDeclarationYear", rootClass);
         myProperties[52] = new PropertyDescriptor("InActionCsfClientServerCommunicationsAction", rootClass);
         myProperties[53] = new PropertyDescriptor("InSendByEmailIefSuppliedFlag", rootClass);
         myProperties[53].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor.class);
         myProperties[54] = new PropertyDescriptor("InEmailAddressWsAddressEmailAddress", rootClass);
         myProperties[55] = new PropertyDescriptor("InEmailAddressWsAddressCellNumber", rootClass);
         myProperties[56] = new PropertyDescriptor("InSendSmsIefSuppliedFlag", rootClass);
         myProperties[56].setPropertyEditorClass(Srsrj11sPrtNormalDeclarationIefSuppliedFlagPropertyEditor.class);
         myProperties[57] = new PropertyDescriptor("InEmailFromWsAddressEmailAddress", rootClass);
         myProperties[58] = new PropertyDescriptor("OutNqfLevelIefSuppliedFlag", rootClass, "getOutNqfLevelIefSuppliedFlag", null);
         myProperties[59] = new PropertyDescriptor("OutNqfCreditsIefSuppliedFlag", rootClass, "getOutNqfCreditsIefSuppliedFlag", null);
         myProperties[60] = new PropertyDescriptor("OutEducInstIefSuppliedCount", rootClass, "getOutEducInstIefSuppliedCount", null);
         myProperties[61] = new PropertyDescriptor("OutMajorSubjectIefSuppliedCount", rootClass, "getOutMajorSubjectIefSuppliedCount", null);
         myProperties[62] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[63] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[64] = new PropertyDescriptor("OutErrmsgCsfStringsString500", rootClass, "getOutErrmsgCsfStringsString500", null);
         myProperties[65] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[66] = new PropertyDescriptor("OutEmailCellAddressToWsAddressEmailAddress", rootClass, "getOutEmailCellAddressToWsAddressEmailAddress", null);
         myProperties[67] = new PropertyDescriptor("OutEmailCellAddressToWsAddressCellNumber", rootClass, "getOutEmailCellAddressToWsAddressCellNumber", null);
         myProperties[68] = new PropertyDescriptor("OutEmailAddressFromWsAddressEmailAddress", rootClass, "getOutEmailAddressFromWsAddressEmailAddress", null);
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
//         callMethod = Srsrj11sPrtNormalDeclaration.class.getMethod("execute", args);
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
