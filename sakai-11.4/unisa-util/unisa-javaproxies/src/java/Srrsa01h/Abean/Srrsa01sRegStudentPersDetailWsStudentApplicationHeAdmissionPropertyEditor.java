package Srrsa01h.Abean;
 
import java.beans.*;
 
public class Srrsa01sRegStudentPersDetailWsStudentApplicationHeAdmissionPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         " ",
         "S",
         "E",
         "M",
         "B",
         "C",
         "O",
         "F",
         "D",
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
