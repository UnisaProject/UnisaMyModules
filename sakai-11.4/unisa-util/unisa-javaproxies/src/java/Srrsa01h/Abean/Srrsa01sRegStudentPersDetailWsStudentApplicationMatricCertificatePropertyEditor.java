package Srrsa01h.Abean;
 
import java.beans.*;
 
public class Srrsa01sRegStudentPersDetailWsStudentApplicationMatricCertificatePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         " ",
         "OO",
         "NV",
         "SC",
         "NC",
         "SS",
         "FF",
         "CG",
         "",
      };
      return values;
   }

   public boolean checkTag(String s) {
      String values[] = getTags();
      for(int n=0; n< values.length; n++) {
         if (s.compareTo(values[n]) == 0) {
            return true;
         }
      }
      return false;
   }
}
