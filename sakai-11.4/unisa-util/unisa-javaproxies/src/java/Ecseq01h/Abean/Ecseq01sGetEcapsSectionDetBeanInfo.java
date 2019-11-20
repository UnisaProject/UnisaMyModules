package Ecseq01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Ecseq01sGetEcapsSectionDetBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Ecseq01sGetEcapsSectionDet.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Ecseq01sGetEcapsSectionDet.class);
      bd.setDisplayName(Ecseq01sGetEcapsSectionDet.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InEcapsSectionId", rootClass);
         myProperties[15] = new PropertyDescriptor("InEcapsSectionTextFromDate", rootClass);
         myProperties[16] = new PropertyDescriptor("InEcapsSectionTextPosition", rootClass);
         myProperties[17] = new PropertyDescriptor("InEcapsSectionTextHeader", rootClass);
         myProperties[18] = new PropertyDescriptor("InEcapsSectionTextTextArea", rootClass);
         myProperties[19] = new PropertyDescriptor("InEcapsSectionAdminFromDate", rootClass);
         myProperties[20] = new PropertyDescriptor("InEcapsSectionAdminDueDate", rootClass);
         myProperties[21] = new PropertyDescriptor("InEcapsSectionAdminNovellUserId", rootClass);
         myProperties[22] = new PropertyDescriptor("InCurrentWsDateDate", rootClass);
         myProperties[23] = new PropertyDescriptor("OutEcapsSectionId", rootClass, "getOutEcapsSectionId", null);
         myProperties[24] = new PropertyDescriptor("OutEcapsSectionTextFromDate", rootClass, "getOutEcapsSectionTextFromDate", null);
         myProperties[25] = new PropertyDescriptor("OutEcapsSectionTextPosition", rootClass, "getOutEcapsSectionTextPosition", null);
         myProperties[26] = new PropertyDescriptor("OutEcapsSectionTextHeader", rootClass, "getOutEcapsSectionTextHeader", null);
         myProperties[27] = new PropertyDescriptor("OutEcapsSectionTextTextArea", rootClass, "getOutEcapsSectionTextTextArea", null);
         myProperties[28] = new PropertyDescriptor("OutEcapsSectionAdminFromDate", rootClass, "getOutEcapsSectionAdminFromDate", null);
         myProperties[29] = new PropertyDescriptor("OutEcapsSectionAdminDueDate", rootClass, "getOutEcapsSectionAdminDueDate", null);
         myProperties[30] = new PropertyDescriptor("OutEcapsSectionAdminNovellUserId", rootClass, "getOutEcapsSectionAdminNovellUserId", null);
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
//         callMethod = Ecseq01sGetEcapsSectionDet.class.getMethod("execute", args);
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
