package Staae05h.Abean;
 
import java.beans.*;
import java.lang.reflect.*;
 
public class Staae05sAppAdmissionEvaluatorBeanInfo extends SimpleBeanInfo {
 
   private final static Class rootClass = Staae05sAppAdmissionEvaluator.class;
 
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
      null,   // 131
      null,   // 132
      null,   // 133
      null,   // 134
      null,   // 135
      null,   // 136
      null,   // 137
      null,   // 138
      null,   // 139
      null,   // 140
      null,   // 141
      null,   // 142
      null,   // 143
};
 
   public BeanDescriptor getBeanDescriptor() {
      BeanDescriptor bd = 
         new BeanDescriptor(Staae05sAppAdmissionEvaluator.class);
      bd.setDisplayName(Staae05sAppAdmissionEvaluator.BEANNAME);
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
         myProperties[14] = new PropertyDescriptor("InWebStuApplicationQualAcademicYear", rootClass);
         myProperties[15] = new PropertyDescriptor("InWebStuApplicationQualMkStudentNr", rootClass);
         myProperties[16] = new PropertyDescriptor("InWebStuApplicationQualApplicationPeriod", rootClass);
         myProperties[17] = new PropertyDescriptor("InWebStuApplicationQualStatusCode", rootClass);
         myProperties[18] = new PropertyDescriptor("InWebStuApplicationQualChoiceNr", rootClass);
         myProperties[19] = new PropertyDescriptor("InWebStuApplicationQualNewQual", rootClass);
         myProperties[20] = new PropertyDescriptor("InWebStuApplicationQualOfferAccepted", rootClass);
         myProperties[21] = new PropertyDescriptor("InGencodQualLevelCsfStringsString2", rootClass);
         myProperties[22] = new PropertyDescriptor("InWsAcademicYearYear", rootClass);
         myProperties[23] = new PropertyDescriptor("InWsQualificationCode", rootClass);
         myProperties[24] = new PropertyDescriptor("InWsQualificationType", rootClass);
         myProperties[24].setPropertyEditorClass(Staae05sAppAdmissionEvaluatorWsQualificationTypePropertyEditor.class);
         myProperties[25] = new PropertyDescriptor("InWsQualificationLongEngDescription", rootClass);
         myProperties[26] = new PropertyDescriptor("InWsQualificationSpecialityTypeMkQualificationCode", rootClass);
         myProperties[27] = new PropertyDescriptor("InWsQualificationSpecialityTypeSpecialityCode", rootClass);
         myProperties[28] = new PropertyDescriptor("InWsStudentNr", rootClass);
         myProperties[29] = new PropertyDescriptor("InWsStudentMkTitle", rootClass);
         myProperties[30] = new PropertyDescriptor("InWsStudentSurname", rootClass);
         myProperties[31] = new PropertyDescriptor("InWsStudentInitials", rootClass);
         myProperties[32] = new PropertyDescriptor("InWsStudentIdentityNr", rootClass);
         myProperties[33] = new PropertyDescriptor("InWsStudentBirthDate", rootClass);
         myProperties[34] = new PropertyDescriptor("InWsStudentMkNationality", rootClass);
         myProperties[35] = new PropertyDescriptor("InWsStudentMkLastAcademicYear", rootClass);
         myProperties[36] = new PropertyDescriptor("InWsStudentMkCountryCode", rootClass);
         myProperties[37] = new PropertyDescriptor("InWsMatricStatusCode", rootClass);
         myProperties[38] = new PropertyDescriptor("InWsMatricCertificateCode", rootClass);
         myProperties[39] = new PropertyDescriptor("InWsMatricRecordCategory", rootClass);
         myProperties[39].setPropertyEditorClass(Staae05sAppAdmissionEvaluatorWsMatricRecordCategoryPropertyEditor.class);
         myProperties[40] = new PropertyDescriptor("InWsMatricRecordFullExemptionDate", rootClass);
         myProperties[41] = new PropertyDescriptor("InWsMatricRecordExemptionEffectiveDate", rootClass);
         myProperties[42] = new PropertyDescriptor("InWsMatricRecordExemptionExpiryDate", rootClass);
         myProperties[43] = new PropertyDescriptor("InWsMatricRecordAuditedFlag", rootClass);
         myProperties[43].setPropertyEditorClass(Staae05sAppAdmissionEvaluatorWsMatricRecordAuditedFlagPropertyEditor.class);
         myProperties[44] = new PropertyDescriptor("InWsMatricRecordMkUserCode", rootClass);
         myProperties[45] = new PropertyDescriptor("InWsMatricRecordSymbol", rootClass);
         myProperties[46] = new PropertyDescriptor("InWsMatricRecordExamNumber", rootClass);
         myProperties[47] = new PropertyDescriptor("InWsMatricRecordAggregate", rootClass);
         myProperties[48] = new PropertyDescriptor("InWsMatricRecordOrigExemptDate", rootClass);
         myProperties[49] = new PropertyDescriptor("InWsMatricRecordSecEduComplete", rootClass);
         myProperties[50] = new PropertyDescriptor("InWsMatricRecordMCount", rootClass);
         myProperties[51] = new PropertyDescriptor("InWsMatricRecordComment0", rootClass);
         myProperties[52] = new PropertyDescriptor("InWsMatricRecordHcerCompleted", rootClass);
         myProperties[53] = new PropertyDescriptor("InWsMatricRecordDiplCompleted", rootClass);
         myProperties[54] = new PropertyDescriptor("InWsMatricRecordScienceFoundation", rootClass);
         myProperties[55] = new PropertyDescriptor("InWsMatricRecordAlternativeRoute", rootClass);
         myProperties[56] = new PropertyDescriptor("InWsMatricRecordAltComment1", rootClass);
         myProperties[57] = new PropertyDescriptor("InWsMatricRecordAltComment2", rootClass);
         myProperties[58] = new PropertyDescriptor("InWsMatricRecordApsScore", rootClass);
         myProperties[59] = new PropertyDescriptor("InWsMatricRecordAcsScore", rootClass);
         myProperties[60] = new PropertyDescriptor("InWsPrinterCode", rootClass);
         myProperties[61] = new PropertyDescriptor("InWsFunctionNumber", rootClass);
         myProperties[62] = new IndexedPropertyDescriptor("InGmCsfLineActionBarSelectionFlag", rootClass, null, null, "getInGmCsfLineActionBarSelectionFlag", "setInGmCsfLineActionBarSelectionFlag");
         myProperties[63] = new IndexedPropertyDescriptor("InGmWsMatricSubjectCode", rootClass, null, null, "getInGmWsMatricSubjectCode", "setInGmWsMatricSubjectCode");
         myProperties[64] = new IndexedPropertyDescriptor("InGmWsMatricSubjectEngDescription", rootClass, null, null, "getInGmWsMatricSubjectEngDescription", "setInGmWsMatricSubjectEngDescription");
         myProperties[65] = new IndexedPropertyDescriptor("InGmWsMatricSubjectResultGrade", rootClass, null, null, "getInGmWsMatricSubjectResultGrade", "setInGmWsMatricSubjectResultGrade");
         myProperties[65].setPropertyEditorClass(Staae05sAppAdmissionEvaluatorWsMatricSubjectResultGradePropertyEditor.class);
         myProperties[66] = new IndexedPropertyDescriptor("InGmWsMatricSubjectResultMark", rootClass, null, null, "getInGmWsMatricSubjectResultMark", "setInGmWsMatricSubjectResultMark");
         myProperties[67] = new PropertyDescriptor("InWsUserNumber", rootClass);
         myProperties[68] = new PropertyDescriptor("InCsfClientServerCommunicationsClientVersionNumber", rootClass);
         myProperties[69] = new PropertyDescriptor("InCsfClientServerCommunicationsClientRevisionNumber", rootClass);
         myProperties[70] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDevelopmentPhase", rootClass);
         myProperties[71] = new PropertyDescriptor("InCsfClientServerCommunicationsAction", rootClass);
         myProperties[72] = new PropertyDescriptor("InCsfClientServerCommunicationsClientIsGuiFlag", rootClass);
         myProperties[73] = new PropertyDescriptor("InCsfClientServerCommunicationsClientDate", rootClass);
         myProperties[74] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTime", rootClass);
         myProperties[75] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTimestamp", rootClass);
         myProperties[76] = new PropertyDescriptor("InCsfClientServerCommunicationsClientTransactionCode", rootClass);
         myProperties[77] = new PropertyDescriptor("InCsfClientServerCommunicationsClientUserId", rootClass);
         myProperties[78] = new PropertyDescriptor("InCsfClientServerCommunicationsListLinkFlag", rootClass);
         myProperties[79] = new PropertyDescriptor("InCsfClientServerCommunicationsMaintLinkFlag", rootClass);
         myProperties[80] = new PropertyDescriptor("InCsfClientServerCommunicationsFirstpassFlag", rootClass);
         myProperties[81] = new PropertyDescriptor("InProddevCsfStringsString4", rootClass);
         myProperties[82] = new PropertyDescriptor("InCsfStringsString500", rootClass);
         myProperties[83] = new PropertyDescriptor("OutWsAcademicYearYear", rootClass, "getOutWsAcademicYearYear", null);
         myProperties[84] = new PropertyDescriptor("OutWsQualificationCode", rootClass, "getOutWsQualificationCode", null);
         myProperties[85] = new PropertyDescriptor("OutWsQualificationType", rootClass, "getOutWsQualificationType", null);
         myProperties[86] = new PropertyDescriptor("OutWsQualificationSpecialityTypeMkQualificationCode", rootClass, "getOutWsQualificationSpecialityTypeMkQualificationCode", null);
         myProperties[87] = new PropertyDescriptor("OutWsQualificationSpecialityTypeSpecialityCode", rootClass, "getOutWsQualificationSpecialityTypeSpecialityCode", null);
         myProperties[88] = new PropertyDescriptor("OutWsStudentNr", rootClass, "getOutWsStudentNr", null);
         myProperties[89] = new PropertyDescriptor("OutWsStudentMkTitle", rootClass, "getOutWsStudentMkTitle", null);
         myProperties[90] = new PropertyDescriptor("OutWsStudentSurname", rootClass, "getOutWsStudentSurname", null);
         myProperties[91] = new PropertyDescriptor("OutWsStudentInitials", rootClass, "getOutWsStudentInitials", null);
         myProperties[92] = new PropertyDescriptor("OutWsStudentIdentityNr", rootClass, "getOutWsStudentIdentityNr", null);
         myProperties[93] = new PropertyDescriptor("OutWsStudentBirthDate", rootClass, "getOutWsStudentBirthDate", null);
         myProperties[94] = new PropertyDescriptor("OutWsStudentMkNationality", rootClass, "getOutWsStudentMkNationality", null);
         myProperties[95] = new PropertyDescriptor("OutWsStudentMkLastAcademicYear", rootClass, "getOutWsStudentMkLastAcademicYear", null);
         myProperties[96] = new PropertyDescriptor("OutWsStudentMkCountryCode", rootClass, "getOutWsStudentMkCountryCode", null);
         myProperties[97] = new PropertyDescriptor("OutWsMatricStatusCode", rootClass, "getOutWsMatricStatusCode", null);
         myProperties[98] = new PropertyDescriptor("OutWsMatricCertificateCode", rootClass, "getOutWsMatricCertificateCode", null);
         myProperties[99] = new PropertyDescriptor("OutWsMatricRecordCategory", rootClass, "getOutWsMatricRecordCategory", null);
         myProperties[100] = new PropertyDescriptor("OutWsMatricRecordFullExemptionDate", rootClass, "getOutWsMatricRecordFullExemptionDate", null);
         myProperties[101] = new PropertyDescriptor("OutWsMatricRecordExemptionEffectiveDate", rootClass, "getOutWsMatricRecordExemptionEffectiveDate", null);
         myProperties[102] = new PropertyDescriptor("OutWsMatricRecordExemptionExpiryDate", rootClass, "getOutWsMatricRecordExemptionExpiryDate", null);
         myProperties[103] = new PropertyDescriptor("OutWsMatricRecordAuditedFlag", rootClass, "getOutWsMatricRecordAuditedFlag", null);
         myProperties[104] = new PropertyDescriptor("OutWsMatricRecordSymbol", rootClass, "getOutWsMatricRecordSymbol", null);
         myProperties[105] = new PropertyDescriptor("OutWsMatricRecordOrigExemptDate", rootClass, "getOutWsMatricRecordOrigExemptDate", null);
         myProperties[106] = new PropertyDescriptor("OutWsMatricRecordHcerCompleted", rootClass, "getOutWsMatricRecordHcerCompleted", null);
         myProperties[107] = new PropertyDescriptor("OutWsMatricRecordDiplCompleted", rootClass, "getOutWsMatricRecordDiplCompleted", null);
         myProperties[108] = new PropertyDescriptor("OutWsMatricRecordApsScore", rootClass, "getOutWsMatricRecordApsScore", null);
         myProperties[109] = new PropertyDescriptor("OutWsMatricRecordAcsScore", rootClass, "getOutWsMatricRecordAcsScore", null);
         myProperties[110] = new IndexedPropertyDescriptor("OutGCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGCsfLineActionBarSelectionFlag", null);
         myProperties[111] = new IndexedPropertyDescriptor("OutGWsQualificationCode", rootClass, null, null, "getOutGWsQualificationCode", null);
         myProperties[112] = new IndexedPropertyDescriptor("OutGWsQualificationType", rootClass, null, null, "getOutGWsQualificationType", null);
         myProperties[113] = new IndexedPropertyDescriptor("OutGWsQualificationCollegeCode", rootClass, null, null, "getOutGWsQualificationCollegeCode", null);
         myProperties[114] = new IndexedPropertyDescriptor("OutGWsQualificationSpecialityTypeSpecialityCode", rootClass, null, null, "getOutGWsQualificationSpecialityTypeSpecialityCode", null);
         myProperties[115] = new IndexedPropertyDescriptor("OutGCsfStringsString2", rootClass, null, null, "getOutGCsfStringsString2", null);
         myProperties[116] = new IndexedPropertyDescriptor("OutGCsfStringsString3", rootClass, null, null, "getOutGCsfStringsString3", null);
         myProperties[117] = new IndexedPropertyDescriptor("OutGCsfStringsString4", rootClass, null, null, "getOutGCsfStringsString4", null);
         myProperties[118] = new IndexedPropertyDescriptor("OutGmCsfLineActionBarSelectionFlag", rootClass, null, null, "getOutGmCsfLineActionBarSelectionFlag", null);
         myProperties[119] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectCode", rootClass, null, null, "getOutGmWsMatricSubjectCode", null);
         myProperties[120] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectResultGrade", rootClass, null, null, "getOutGmWsMatricSubjectResultGrade", null);
         myProperties[121] = new IndexedPropertyDescriptor("OutGmWsMatricSubjectResultMark", rootClass, null, null, "getOutGmWsMatricSubjectResultMark", null);
         myProperties[122] = new PropertyDescriptor("OutWsUserNumber", rootClass, "getOutWsUserNumber", null);
         myProperties[123] = new PropertyDescriptor("OutCsfClientServerCommunicationsReturnCode", rootClass, "getOutCsfClientServerCommunicationsReturnCode", null);
         myProperties[124] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerVersionNumber", rootClass, "getOutCsfClientServerCommunicationsServerVersionNumber", null);
         myProperties[125] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsServerRevisionNumber", null);
         myProperties[126] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsServerDevelopmentPhase", null);
         myProperties[127] = new PropertyDescriptor("OutCsfClientServerCommunicationsAction", rootClass, "getOutCsfClientServerCommunicationsAction", null);
         myProperties[128] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientIsGuiFlag", rootClass, "getOutCsfClientServerCommunicationsClientIsGuiFlag", null);
         myProperties[129] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerDate", rootClass, "getOutCsfClientServerCommunicationsServerDate", null);
         myProperties[130] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTime", rootClass, "getOutCsfClientServerCommunicationsServerTime", null);
         myProperties[131] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTimestamp", rootClass, "getOutCsfClientServerCommunicationsServerTimestamp", null);
         myProperties[132] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerTransactionCode", rootClass, "getOutCsfClientServerCommunicationsServerTransactionCode", null);
         myProperties[133] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerUserId", rootClass, "getOutCsfClientServerCommunicationsServerUserId", null);
         myProperties[134] = new PropertyDescriptor("OutCsfClientServerCommunicationsServerRollbackFlag", rootClass, "getOutCsfClientServerCommunicationsServerRollbackFlag", null);
         myProperties[135] = new PropertyDescriptor("OutCsfClientServerCommunicationsActionsPendingFlag", rootClass, "getOutCsfClientServerCommunicationsActionsPendingFlag", null);
         myProperties[136] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientVersionNumber", rootClass, "getOutCsfClientServerCommunicationsClientVersionNumber", null);
         myProperties[137] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientRevisionNumber", rootClass, "getOutCsfClientServerCommunicationsClientRevisionNumber", null);
         myProperties[138] = new PropertyDescriptor("OutCsfClientServerCommunicationsClientDevelopmentPhase", rootClass, "getOutCsfClientServerCommunicationsClientDevelopmentPhase", null);
         myProperties[139] = new PropertyDescriptor("OutCsfClientServerCommunicationsListLinkFlag", rootClass, "getOutCsfClientServerCommunicationsListLinkFlag", null);
         myProperties[140] = new PropertyDescriptor("OutCsfClientServerCommunicationsCancelFlag", rootClass, "getOutCsfClientServerCommunicationsCancelFlag", null);
         myProperties[141] = new PropertyDescriptor("OutCsfClientServerCommunicationsMaintLinkFlag", rootClass, "getOutCsfClientServerCommunicationsMaintLinkFlag", null);
         myProperties[142] = new PropertyDescriptor("OutCsfClientServerCommunicationsForceDatabaseRead", rootClass, "getOutCsfClientServerCommunicationsForceDatabaseRead", null);
         myProperties[143] = new PropertyDescriptor("OutCsfStringsString500", rootClass, "getOutCsfStringsString500", null);
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
//         callMethod = Staae05sAppAdmissionEvaluator.class.getMethod("execute", args);
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
