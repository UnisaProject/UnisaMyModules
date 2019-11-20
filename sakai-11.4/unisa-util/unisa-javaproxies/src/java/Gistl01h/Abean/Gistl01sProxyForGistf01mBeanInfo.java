package Gistl01h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Gistl01sProxyForGistf01mBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Gistl01sProxyForGistf01m.class;
 
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
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Gistl01sProxyForGistf01m.class);
      bd.setDisplayName(Gistl01sProxyForGistf01m.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InStaffPersno", rootClass);
         myProperties[15] = new PropertyDescriptor("InStaffSurname", rootClass);
         myProperties[16] = new PropertyDescriptor("InStaffInitials", rootClass);
         myProperties[17] = new PropertyDescriptor("InStaffTitle", rootClass);
         myProperties[18] = new PropertyDescriptor("InStaffName", rootClass);
         myProperties[19] = new PropertyDescriptor("InStaffLanguageCode", rootClass);
         myProperties[20] = new PropertyDescriptor("InStaffMkDeptCode", rootClass);
         myProperties[21] = new PropertyDescriptor("InStaffResignDate", rootClass);
         myProperties[22] = new PropertyDescriptor("InStaffEmailAddress", rootClass);
         myProperties[23] = new PropertyDescriptor("InStaffContactTelno", rootClass);
         myProperties[24] = new PropertyDescriptor("InStaffOfficeNo", rootClass);
         myProperties[25] = new PropertyDescriptor("InStaffMkSubdeptCode", rootClass);
         myProperties[26] = new PropertyDescriptor("InStaffOldStaffNr", rootClass);
         myProperties[27] = new PropertyDescriptor("InStaffOldInstitution", rootClass);
         myProperties[28] = new PropertyDescriptor("InStaffMkRcCode", rootClass);
         myProperties[29] = new PropertyDescriptor("InStaffFirstNames", rootClass);
         myProperties[30] = new PropertyDescriptor("InStaffDateOfBirth", rootClass);
         myProperties[31] = new PropertyDescriptor("InStaffIdNr", rootClass);
         myProperties[32] = new PropertyDescriptor("InStaffPassportNr", rootClass);
         myProperties[33] = new PropertyDescriptor("InStaffTaxNr", rootClass);
         myProperties[34] = new PropertyDescriptor("InStaffTaxDirectiveNr", rootClass);
         myProperties[35] = new PropertyDescriptor("InStaffGender", rootClass);
         myProperties[36] = new PropertyDescriptor("InStaffMkRaceCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InStaffNovellUserId", rootClass);
         myProperties[38] = new PropertyDescriptor("InStaffBuilding", rootClass);
         myProperties[39] = new PropertyDescriptor("InStaffCellNumber", rootClass);
         myProperties[40] = new PropertyDescriptor("OutStaffPersno", rootClass, "getOutStaffPersno", null);
         myProperties[41] = new PropertyDescriptor("OutStaffSurname", rootClass, "getOutStaffSurname", null);
         myProperties[42] = new PropertyDescriptor("OutStaffInitials", rootClass, "getOutStaffInitials", null);
         myProperties[43] = new PropertyDescriptor("OutStaffTitle", rootClass, "getOutStaffTitle", null);
         myProperties[44] = new PropertyDescriptor("OutStaffName", rootClass, "getOutStaffName", null);
         myProperties[45] = new PropertyDescriptor("OutStaffLanguageCode", rootClass, "getOutStaffLanguageCode", null);
         myProperties[46] = new PropertyDescriptor("OutStaffMkDeptCode", rootClass, "getOutStaffMkDeptCode", null);
         myProperties[47] = new PropertyDescriptor("OutStaffResignDate", rootClass, "getOutStaffResignDate", null);
         myProperties[48] = new PropertyDescriptor("OutStaffEmailAddress", rootClass, "getOutStaffEmailAddress", null);
         myProperties[49] = new PropertyDescriptor("OutStaffContactTelno", rootClass, "getOutStaffContactTelno", null);
         myProperties[50] = new PropertyDescriptor("OutStaffOfficeNo", rootClass, "getOutStaffOfficeNo", null);
         myProperties[51] = new PropertyDescriptor("OutStaffMkSubdeptCode", rootClass, "getOutStaffMkSubdeptCode", null);
         myProperties[52] = new PropertyDescriptor("OutStaffOldStaffNr", rootClass, "getOutStaffOldStaffNr", null);
         myProperties[53] = new PropertyDescriptor("OutStaffOldInstitution", rootClass, "getOutStaffOldInstitution", null);
         myProperties[54] = new PropertyDescriptor("OutStaffMkRcCode", rootClass, "getOutStaffMkRcCode", null);
         myProperties[55] = new PropertyDescriptor("OutStaffFirstNames", rootClass, "getOutStaffFirstNames", null);
         myProperties[56] = new PropertyDescriptor("OutStaffDateOfBirth", rootClass, "getOutStaffDateOfBirth", null);
         myProperties[57] = new PropertyDescriptor("OutStaffIdNr", rootClass, "getOutStaffIdNr", null);
         myProperties[58] = new PropertyDescriptor("OutStaffPassportNr", rootClass, "getOutStaffPassportNr", null);
         myProperties[59] = new PropertyDescriptor("OutStaffTaxNr", rootClass, "getOutStaffTaxNr", null);
         myProperties[60] = new PropertyDescriptor("OutStaffTaxDirectiveNr", rootClass, "getOutStaffTaxDirectiveNr", null);
         myProperties[61] = new PropertyDescriptor("OutStaffGender", rootClass, "getOutStaffGender", null);
         myProperties[62] = new PropertyDescriptor("OutStaffMkRaceCode", rootClass, "getOutStaffMkRaceCode", null);
         myProperties[63] = new PropertyDescriptor("OutStaffNovellUserId", rootClass, "getOutStaffNovellUserId", null);
         myProperties[64] = new PropertyDescriptor("OutStaffBuilding", rootClass, "getOutStaffBuilding", null);
         myProperties[65] = new PropertyDescriptor("OutStaffCellNumber", rootClass, "getOutStaffCellNumber", null);
         myProperties[66] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
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
//         callMethod = Gistl01sProxyForGistf01m.class.getMethod("execute", args);
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
