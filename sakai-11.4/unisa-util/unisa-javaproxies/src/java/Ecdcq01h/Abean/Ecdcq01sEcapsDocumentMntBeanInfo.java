package Ecdcq01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Ecdcq01sEcapsDocumentMntBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Ecdcq01sEcapsDocumentMnt.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Ecdcq01sEcapsDocumentMnt.class);
      bd.setDisplayName(Ecdcq01sEcapsDocumentMnt.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InDocDepIndWsEcapsString1", rootClass);
         myProperties[15] = new PropertyDescriptor("InFieldEcapsDocumentAttrField", rootClass);
         myProperties[16] = new PropertyDescriptor("InActionIefSuppliedCommand", rootClass);
         myProperties[17] = new PropertyDescriptor("InTimestampEcapsDocumentAttrCreatedTimestamp", rootClass);
         myProperties[18] = new PropertyDescriptor("InEcapsDocumentMkAcademicYear", rootClass);
         myProperties[19] = new PropertyDescriptor("InEcapsDocumentSemester", rootClass);
         myProperties[20] = new PropertyDescriptor("InEcapsDocumentMkStudyUnitCode", rootClass);
         myProperties[21] = new PropertyDescriptor("InEcapsDocumentStatusGc31", rootClass);
         myProperties[22] = new PropertyDescriptor("InEcapsDocumentSignedTimestamp", rootClass);
         myProperties[23] = new IndexedPropertyDescriptor("InGrpDocDepIndWsEcapsString1", rootClass, null, null, "getInGrpDocDepIndWsEcapsString1", "setInGrpDocDepIndWsEcapsString1");
         myProperties[24] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrField", rootClass, null, null, "getInGrpEcapsDocumentAttrField", "setInGrpEcapsDocumentAttrField");
         myProperties[25] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrFieldValue", rootClass, null, null, "getInGrpEcapsDocumentAttrFieldValue", "setInGrpEcapsDocumentAttrFieldValue");
         myProperties[26] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrCreatedTimestamp", rootClass, null, null, "getInGrpEcapsDocumentAttrCreatedTimestamp", "setInGrpEcapsDocumentAttrCreatedTimestamp");
         myProperties[27] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrExamYear", rootClass, null, null, "getInGrpEcapsDocumentAttrExamYear", "setInGrpEcapsDocumentAttrExamYear");
         myProperties[28] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrExamPeriod", rootClass, null, null, "getInGrpEcapsDocumentAttrExamPeriod", "setInGrpEcapsDocumentAttrExamPeriod");
         myProperties[29] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrDepenencyIndGc30", rootClass, null, null, "getInGrpEcapsDocumentAttrDepenencyIndGc30", "setInGrpEcapsDocumentAttrDepenencyIndGc30");
         myProperties[30] = new IndexedPropertyDescriptor("InGrpEcapsDocumentAttrNovellUserId", rootClass, null, null, "getInGrpEcapsDocumentAttrNovellUserId", "setInGrpEcapsDocumentAttrNovellUserId");
         myProperties[31] = new IndexedPropertyDescriptor("InGrpErrCsfStringsString10", rootClass, null, null, "getInGrpErrCsfStringsString10", "setInGrpErrCsfStringsString10");
         myProperties[32] = new PropertyDescriptor("OutEcapsDocumentMkAcademicYear", rootClass, "getOutEcapsDocumentMkAcademicYear", null);
         myProperties[33] = new PropertyDescriptor("OutEcapsDocumentSemester", rootClass, "getOutEcapsDocumentSemester", null);
         myProperties[34] = new PropertyDescriptor("OutEcapsDocumentMkStudyUnitCode", rootClass, "getOutEcapsDocumentMkStudyUnitCode", null);
         myProperties[35] = new PropertyDescriptor("OutEcapsDocumentStatusGc31", rootClass, "getOutEcapsDocumentStatusGc31", null);
         myProperties[36] = new PropertyDescriptor("OutEcapsDocumentSignedTimestamp", rootClass, "getOutEcapsDocumentSignedTimestamp", null);
         myProperties[37] = new IndexedPropertyDescriptor("OutGrpDocDepIndWsEcapsString1", rootClass, null, null, "getOutGrpDocDepIndWsEcapsString1", null);
         myProperties[38] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrField", rootClass, null, null, "getOutGrpEcapsDocumentAttrField", null);
         myProperties[39] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrFieldValue", rootClass, null, null, "getOutGrpEcapsDocumentAttrFieldValue", null);
         myProperties[40] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrCreatedTimestamp", rootClass, null, null, "getOutGrpEcapsDocumentAttrCreatedTimestamp", null);
         myProperties[41] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrExamYear", rootClass, null, null, "getOutGrpEcapsDocumentAttrExamYear", null);
         myProperties[42] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrExamPeriod", rootClass, null, null, "getOutGrpEcapsDocumentAttrExamPeriod", null);
         myProperties[43] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrDepenencyIndGc30", rootClass, null, null, "getOutGrpEcapsDocumentAttrDepenencyIndGc30", null);
         myProperties[44] = new IndexedPropertyDescriptor("OutGrpEcapsDocumentAttrNovellUserId", rootClass, null, null, "getOutGrpEcapsDocumentAttrNovellUserId", null);
         myProperties[45] = new IndexedPropertyDescriptor("OutGrpErrCsfStringsString10", rootClass, null, null, "getOutGrpErrCsfStringsString10", null);
         myProperties[46] = new PropertyDescriptor("OutErrmsgCsfStringsString500", rootClass, "getOutErrmsgCsfStringsString500", null);
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
//         callMethod = Ecdcq01sEcapsDocumentMnt.class.getMethod("execute", args);
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
