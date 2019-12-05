package Saaal40j.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Saaal40jGetStudentCoursesBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Saaal40jGetStudentCourses.class;
 
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
         new BeanDescriptor(Saaal40jGetStudentCourses.class);
      bd.setDisplayName(Saaal40jGetStudentCourses.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWsStudentAnnualRecordMkStudentNr", rootClass);
         myProperties[15] = new PropertyDescriptor("InWsStudentStudyUnitMkStudyUnitCode", rootClass);
         myProperties[16] = new PropertyDescriptor("OutWsMethodResultReturnCode", rootClass, "getOutWsMethodResultReturnCode", null);
         myProperties[17] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkStudyUnitCode", rootClass, null, null, "getOutGWsStudentStudyUnitMkStudyUnitCode", null);
         myProperties[18] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitExamRefusalFlag", rootClass, null, null, "getOutGWsStudentStudyUnitExamRefusalFlag", null);
         myProperties[19] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitSemesterPeriod", rootClass, null, null, "getOutGWsStudentStudyUnitSemesterPeriod", null);
         myProperties[20] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkCourseStudyUnitCode", rootClass, null, null, "getOutGWsStudentStudyUnitMkCourseStudyUnitCode", null);
         myProperties[21] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkStudyUnitOptionCode", rootClass, null, null, "getOutGWsStudentStudyUnitMkStudyUnitOptionCode", null);
         myProperties[22] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkQualificationCode", rootClass, null, null, "getOutGWsStudentStudyUnitMkQualificationCode", null);
         myProperties[23] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitStatusCode", rootClass, null, null, "getOutGWsStudentStudyUnitStatusCode", null);
         myProperties[24] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitRegistrationDate", rootClass, null, null, "getOutGWsStudentStudyUnitRegistrationDate", null);
         myProperties[25] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitCancellationDate", rootClass, null, null, "getOutGWsStudentStudyUnitCancellationDate", null);
         myProperties[26] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitExamAdmissionCode", rootClass, null, null, "getOutGWsStudentStudyUnitExamAdmissionCode", null);
         myProperties[27] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitSupplementaryExamReasonCode", rootClass, null, null, "getOutGWsStudentStudyUnitSupplementaryExamReasonCode", null);
         myProperties[28] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitLanguageCode", rootClass, null, null, "getOutGWsStudentStudyUnitLanguageCode", null);
         myProperties[29] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitExamYear", rootClass, null, null, "getOutGWsStudentStudyUnitExamYear", null);
         myProperties[30] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkExamPeriod", rootClass, null, null, "getOutGWsStudentStudyUnitMkExamPeriod", null);
         myProperties[31] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitDespatchComment", rootClass, null, null, "getOutGWsStudentStudyUnitDespatchComment", null);
         myProperties[32] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitExamArrangementFlag", rootClass, null, null, "getOutGWsStudentStudyUnitExamArrangementFlag", null);
         myProperties[33] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitNrOfRepeats", rootClass, null, null, "getOutGWsStudentStudyUnitNrOfRepeats", null);
         myProperties[34] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitOverrideFlag", rootClass, null, null, "getOutGWsStudentStudyUnitOverrideFlag", null);
         myProperties[35] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitForeignLevyFlag", rootClass, null, null, "getOutGWsStudentStudyUnitForeignLevyFlag", null);
         myProperties[36] = new IndexedPropertyDescriptor("OutGWsStudentStudyUnitMkExamCentreCode", rootClass, null, null, "getOutGWsStudentStudyUnitMkExamCentreCode", null);
         myProperties[37] = new IndexedPropertyDescriptor("OutGWsStudentAnnualRecordMkStudentNr", rootClass, null, null, "getOutGWsStudentAnnualRecordMkStudentNr", null);
         myProperties[38] = new IndexedPropertyDescriptor("OutGWsStudentAnnualRecordMkAcademicYear", rootClass, null, null, "getOutGWsStudentAnnualRecordMkAcademicYear", null);
         myProperties[39] = new IndexedPropertyDescriptor("OutGWsQualificationUnderPostCategory", rootClass, null, null, "getOutGWsQualificationUnderPostCategory", null);
         myProperties[40] = new IndexedPropertyDescriptor("OutGWsQualificationCollegeCode", rootClass, null, null, "getOutGWsQualificationCollegeCode", null);
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
//         callMethod = Saaal40jGetStudentCourses.class.getMethod("execute", args);
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
