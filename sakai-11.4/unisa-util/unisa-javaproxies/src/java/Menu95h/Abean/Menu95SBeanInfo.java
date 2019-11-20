package Menu95h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Menu95SBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Menu95S.class;
 
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
      null,   // 101
      null,   // 102
      null,   // 103
      null,   // 104
      null,   // 105
      null,   // 106
      null,   // 107
      null,   // 108
      null,   // 109
      null,   // 110
      null,   // 111
      null,   // 112
      null,   // 113
      null,   // 114
      null,   // 115
      null,   // 116
      null,   // 117
      null,   // 118
      null,   // 119
      null,   // 120
      null,   // 121
      null,   // 122
      null,   // 123
      null,   // 124
      null,   // 125
      null,   // 126
      null,   // 127
      null,   // 128
      null,   // 129
      null,   // 130
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Menu95S.class);
      bd.setDisplayName(Menu95S.BEANNAME);
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
         myProperties[15] = new PropertyDescriptor("InWsWorkstationMacCode", rootClass);
         myProperties[16] = new PropertyDescriptor("InWsWorkstationComputerHostname", rootClass);
         myProperties[17] = new PropertyDescriptor("InOrderByFieldIefSuppliedCount", rootClass);
         myProperties[18] = new IndexedPropertyDescriptor("InGIefSuppliedSelectChar", rootClass, null, null, "getInGIefSuppliedSelectChar", "setInGIefSuppliedSelectChar");
         myProperties[19] = new IndexedPropertyDescriptor("InGWsFunctionNumber", rootClass, null, null, "getInGWsFunctionNumber", "setInGWsFunctionNumber");
         myProperties[20] = new IndexedPropertyDescriptor("InGWsFunctionTrancode", rootClass, null, null, "getInGWsFunctionTrancode", "setInGWsFunctionTrancode");
         myProperties[21] = new IndexedPropertyDescriptor("InGWsFunctionDescription", rootClass, null, null, "getInGWsFunctionDescription", "setInGWsFunctionDescription");
         myProperties[22] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[23] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[25] = new PropertyDescriptor("InWsUserNumberOfLogonAttempts", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsUserLoggedOnFlag", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsUserInUsedFlag", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsUserLastLogonDate", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsUserName", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsUserPersonnelNo", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsUserPhoneNumber", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsUserPassword", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsUserPassword32cs", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[35] = new PropertyDescriptor("InPhysicalMacWsWorkstationMacCode", rootClass);
         myProperties[36] = new PropertyDescriptor("InWorkstationLookupStatusCsfStringsString100", rootClass);
         myProperties[37] = new PropertyDescriptor("InFunnelgroupLookupStatusCsfStringsString100", rootClass);
         myProperties[38] = new PropertyDescriptor("InAssignedFgNrOnlyWsWkstationFunnelgroupConfigFunnelGroup", rootClass);
         myProperties[39] = new PropertyDescriptor("OutWsActionCode", rootClass, "getOutWsActionCode", null);
         myProperties[40] = new PropertyDescriptor("OutWsWorkstationCode", rootClass, "getOutWsWorkstationCode", null);
         myProperties[41] = new PropertyDescriptor("OutWsWorkstationMacCode", rootClass, "getOutWsWorkstationMacCode", null);
         myProperties[42] = new PropertyDescriptor("OutWsWorkstationComputerHostname", rootClass, "getOutWsWorkstationComputerHostname", null);
         myProperties[43] = new PropertyDescriptor("OutOrderByFieldIefSuppliedCount", rootClass, "getOutOrderByFieldIefSuppliedCount", null);
         myProperties[44] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[45] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[46] = new IndexedPropertyDescriptor("OutGIefSuppliedSelectChar", rootClass, null, null, "getOutGIefSuppliedSelectChar", null);
         myProperties[47] = new IndexedPropertyDescriptor("OutGWsFunctionNumber", rootClass, null, null, "getOutGWsFunctionNumber", null);
         myProperties[48] = new IndexedPropertyDescriptor("OutGWsFunctionTrancode", rootClass, null, null, "getOutGWsFunctionTrancode", null);
         myProperties[49] = new IndexedPropertyDescriptor("OutGWsFunctionDescription", rootClass, null, null, "getOutGWsFunctionDescription", null);
         myProperties[50] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[51] = new PropertyDescriptor("OutWsUserNumberOfLogonAttempts", rootClass, "getOutWsUserNumberOfLogonAttempts", null);
         myProperties[52] = new PropertyDescriptor("OutWsUserLoggedOnFlag", rootClass, "getOutWsUserLoggedOnFlag", null);
         myProperties[53] = new PropertyDescriptor("OutWsUserInUsedFlag", rootClass, "getOutWsUserInUsedFlag", null);
         myProperties[54] = new PropertyDescriptor("OutWsUserLastLogonDate", rootClass, "getOutWsUserLastLogonDate", null);
         myProperties[55] = new PropertyDescriptor("OutWsUserName", rootClass, "getOutWsUserName", null);
         myProperties[56] = new PropertyDescriptor("OutWsUserPersonnelNo", rootClass, "getOutWsUserPersonnelNo", null);
         myProperties[57] = new PropertyDescriptor("OutWsUserPhoneNumber", rootClass, "getOutWsUserPhoneNumber", null);
         myProperties[58] = new PropertyDescriptor("OutWsUserPassword", rootClass, "getOutWsUserPassword", null);
         myProperties[59] = new PropertyDescriptor("OutWsUserPassword32cs", rootClass, "getOutWsUserPassword32cs", null);
         myProperties[60] = new PropertyDescriptor("OutWsFunctionNumber", rootClass, "getOutWsFunctionNumber", null);
         myProperties[61] = new PropertyDescriptor("OutWsFunctionTrancode", rootClass, "getOutWsFunctionTrancode", null);
         myProperties[62] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
         myProperties[63] = new PropertyDescriptor("OutWsPrinterCode", rootClass, "getOutWsPrinterCode", null);
         myProperties[64] = new PropertyDescriptor("OutPhysicalMacWsWorkstationMacCode", rootClass, "getOutPhysicalMacWsWorkstationMacCode", null);
         myProperties[65] = new PropertyDescriptor("OutWorkstationLookupStatusCsfStringsString100", rootClass, "getOutWorkstationLookupStatusCsfStringsString100", null);
         myProperties[66] = new PropertyDescriptor("OutFunnelgroupLookupStatusCsfStringsString100", rootClass, "getOutFunnelgroupLookupStatusCsfStringsString100", null);
         myProperties[67] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelGroup", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelGroup", null);
         myProperties[68] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigServerName", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigServerName", null);
         myProperties[69] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigInUseFlag", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigInUseFlag", null);
         myProperties[70] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigActiveFlag", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigActiveFlag", null);
         myProperties[71] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode01", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode01", null);
         myProperties[72] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport01", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport01", null);
         myProperties[73] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription01", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription01", null);
         myProperties[74] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode02", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode02", null);
         myProperties[75] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport02", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport02", null);
         myProperties[76] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription02", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription02", null);
         myProperties[77] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode03", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode03", null);
         myProperties[78] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport03", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport03", null);
         myProperties[79] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription03", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription03", null);
         myProperties[80] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode04", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode04", null);
         myProperties[81] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport04", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport04", null);
         myProperties[82] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription04", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription04", null);
         myProperties[83] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode05", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode05", null);
         myProperties[84] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport05", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport05", null);
         myProperties[85] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription05", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription05", null);
         myProperties[86] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode06", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode06", null);
         myProperties[87] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport06", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport06", null);
         myProperties[88] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription06", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription06", null);
         myProperties[89] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode07", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode07", null);
         myProperties[90] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport07", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport07", null);
         myProperties[91] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription07", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription07", null);
         myProperties[92] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode08", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode08", null);
         myProperties[93] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport08", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport08", null);
         myProperties[94] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription08", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription08", null);
         myProperties[95] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode09", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode09", null);
         myProperties[96] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport09", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport09", null);
         myProperties[97] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription09", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription09", null);
         myProperties[98] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode10", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode10", null);
         myProperties[99] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport10", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport10", null);
         myProperties[100] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription10", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription10", null);
         myProperties[101] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode11", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode11", null);
         myProperties[102] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport11", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport11", null);
         myProperties[103] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription11", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription11", null);
         myProperties[104] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode12", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode12", null);
         myProperties[105] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport12", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport12", null);
         myProperties[106] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription12", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription12", null);
         myProperties[107] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode13", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode13", null);
         myProperties[108] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport13", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport13", null);
         myProperties[109] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription13", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription13", null);
         myProperties[110] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode14", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode14", null);
         myProperties[111] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport14", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport14", null);
         myProperties[112] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription14", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription14", null);
         myProperties[113] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode15", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode15", null);
         myProperties[114] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport15", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport15", null);
         myProperties[115] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription15", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription15", null);
         myProperties[116] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode16", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode16", null);
         myProperties[117] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport16", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport16", null);
         myProperties[118] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription16", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription16", null);
         myProperties[119] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode17", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode17", null);
         myProperties[120] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport17", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport17", null);
         myProperties[121] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription17", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription17", null);
         myProperties[122] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode18", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode18", null);
         myProperties[123] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport18", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport18", null);
         myProperties[124] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription18", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription18", null);
         myProperties[125] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode19", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode19", null);
         myProperties[126] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport19", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport19", null);
         myProperties[127] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription19", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription19", null);
         myProperties[128] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigTrancode20", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigTrancode20", null);
         myProperties[129] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigFunnelport20", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigFunnelport20", null);
         myProperties[130] = new PropertyDescriptor("OutAssignedWsWkstationFunnelgroupConfigDescription20", rootClass, "getOutAssignedWsWkstationFunnelgroupConfigDescription20", null);
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
//         callMethod = Menu95S.class.getMethod("execute", args);
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
