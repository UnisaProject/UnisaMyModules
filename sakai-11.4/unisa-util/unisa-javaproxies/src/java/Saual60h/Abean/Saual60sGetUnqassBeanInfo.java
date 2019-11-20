package Saual60h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Saual60sGetUnqassBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Saual60sGetUnqass.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Saual60sGetUnqass.class);
      bd.setDisplayName(Saual60sGetUnqass.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsStudentAnnualRecordMkAcademicYear", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsStudentStudyUnitMkStudyUnitCode", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsStudentStudyUnitSemesterPeriod", rootClass);
         myProperties[17] = new IndexedPropertyDescriptor("OutGUniqueAssignmentYear", rootClass, null, null, "getOutGUniqueAssignmentYear", null);
         myProperties[18] = new IndexedPropertyDescriptor("OutGUniqueAssignmentPeriod", rootClass, null, null, "getOutGUniqueAssignmentPeriod", null);
         myProperties[19] = new IndexedPropertyDescriptor("OutGUniqueAssignmentUniqueNr", rootClass, null, null, "getOutGUniqueAssignmentUniqueNr", null);
         myProperties[20] = new IndexedPropertyDescriptor("OutGUniqueAssignmentMkStudyUnitCode", rootClass, null, null, "getOutGUniqueAssignmentMkStudyUnitCode", null);
         myProperties[21] = new IndexedPropertyDescriptor("OutGUniqueAssignmentAssignmentNr", rootClass, null, null, "getOutGUniqueAssignmentAssignmentNr", null);
         myProperties[22] = new IndexedPropertyDescriptor("OutGUniqueAssignmentNrOfQuestions", rootClass, null, null, "getOutGUniqueAssignmentNrOfQuestions", null);
         myProperties[23] = new IndexedPropertyDescriptor("OutGUniqueAssignmentType", rootClass, null, null, "getOutGUniqueAssignmentType", null);
         myProperties[24] = new IndexedPropertyDescriptor("OutGUniqueAssignmentNegativeMarkFactor", rootClass, null, null, "getOutGUniqueAssignmentNegativeMarkFactor", null);
         myProperties[25] = new IndexedPropertyDescriptor("OutGUniqueAssignmentMaxCredits", rootClass, null, null, "getOutGUniqueAssignmentMaxCredits", null);
         myProperties[26] = new IndexedPropertyDescriptor("OutGUniqueAssignmentCreditSystem", rootClass, null, null, "getOutGUniqueAssignmentCreditSystem", null);
         myProperties[27] = new IndexedPropertyDescriptor("OutGUniqueAssignmentPassMarkPercentage", rootClass, null, null, "getOutGUniqueAssignmentPassMarkPercentage", null);
         myProperties[28] = new IndexedPropertyDescriptor("OutGUniqueAssignmentClosingDate", rootClass, null, null, "getOutGUniqueAssignmentClosingDate", null);
         myProperties[29] = new IndexedPropertyDescriptor("OutGUniqueAssignmentCompulsory", rootClass, null, null, "getOutGUniqueAssignmentCompulsory", null);
         myProperties[30] = new IndexedPropertyDescriptor("OutGUniqueAssignmentTsaUniqueNr", rootClass, null, null, "getOutGUniqueAssignmentTsaUniqueNr", null);
         myProperties[31] = new IndexedPropertyDescriptor("OutGYearMarkCalculationType", rootClass, null, null, "getOutGYearMarkCalculationType", null);
         myProperties[32] = new IndexedPropertyDescriptor("OutGYearMarkCalculationNormalWeight", rootClass, null, null, "getOutGYearMarkCalculationNormalWeight", null);
         myProperties[33] = new IndexedPropertyDescriptor("OutGYearMarkCalculationOptionalityGc3", rootClass, null, null, "getOutGYearMarkCalculationOptionalityGc3", null);
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
//         callMethod = Saual60sGetUnqass.class.getMethod("execute", args);
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
