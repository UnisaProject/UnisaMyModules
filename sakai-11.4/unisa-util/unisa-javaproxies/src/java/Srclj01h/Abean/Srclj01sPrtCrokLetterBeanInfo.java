package Srclj01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Srclj01sPrtCrokLetterBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Srclj01sPrtCrokLetter.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Srclj01sPrtCrokLetter.class);
      bd.setDisplayName(Srclj01sPrtCrokLetter.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[15] = new PropertyDescriptor("InFaxOrEmailCsfStringsString1", rootClass);
         myProperties[16] = new PropertyDescriptor("InFaxNameCsfStringsString132", rootClass);
         myProperties[17] = new PropertyDescriptor("InFaxNoCsfStringsString132", rootClass);
         myProperties[18] = new PropertyDescriptor("InEmailToCsfStringsString132", rootClass);
         myProperties[19] = new PropertyDescriptor("InEmailFromCsfStringsString132", rootClass);
         myProperties[20] = new PropertyDescriptor("InNoOfCopiesCsfStringsString1", rootClass);
         myProperties[21] = new PropertyDescriptor("InComment1CsfStringsString100", rootClass);
         myProperties[22] = new PropertyDescriptor("InComment2CsfStringsString100", rootClass);
         myProperties[23] = new PropertyDescriptor("InComment3CsfStringsString100", rootClass);
         myProperties[24] = new PropertyDescriptor("InComment4CsfStringsString100", rootClass);
         myProperties[25] = new PropertyDescriptor("InComment5CsfStringsString100", rootClass);
         myProperties[26] = new PropertyDescriptor("InStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[27] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[28] = new PropertyDescriptor("InStudentAnnualRecordMkAcademicPeriod", rootClass);
         myProperties[29] = new PropertyDescriptor("InDuplicateIefSuppliedFlag", rootClass);
         myProperties[29].setPropertyEditorClass(Srclj01sPrtCrokLetterIefSuppliedFlagPropertyEditor.class);
         myProperties[30] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[31] = new PropertyDescriptor("InWizfuncReportingControlPrinterCode", rootClass);
         myProperties[32] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[33] = new PropertyDescriptor("OutNameCsfStringsString40", rootClass, "getOutNameCsfStringsString40", null);
         myProperties[34] = new PropertyDescriptor("OutFaxOrEmailCsfStringsString1", rootClass, "getOutFaxOrEmailCsfStringsString1", null);
         myProperties[35] = new PropertyDescriptor("OutFaxNameCsfStringsString132", rootClass, "getOutFaxNameCsfStringsString132", null);
         myProperties[36] = new PropertyDescriptor("OutFaxNoCsfStringsString132", rootClass, "getOutFaxNoCsfStringsString132", null);
         myProperties[37] = new PropertyDescriptor("OutEmailToCsfStringsString132", rootClass, "getOutEmailToCsfStringsString132", null);
         myProperties[38] = new PropertyDescriptor("OutEmailFromCsfStringsString132", rootClass, "getOutEmailFromCsfStringsString132", null);
         myProperties[39] = new PropertyDescriptor("OutNoOfCopiesCsfStringsString1", rootClass, "getOutNoOfCopiesCsfStringsString1", null);
         myProperties[40] = new PropertyDescriptor("OutErrmsgCsfStringsString500", rootClass, "getOutErrmsgCsfStringsString500", null);
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
//         callMethod = Srclj01sPrtCrokLetter.class.getMethod("execute", args);
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
