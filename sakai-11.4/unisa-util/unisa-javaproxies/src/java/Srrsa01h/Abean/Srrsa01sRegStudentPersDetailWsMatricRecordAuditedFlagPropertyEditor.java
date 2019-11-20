package Srrsa01h.Abean;
 
import java.beans.*;
 
public class Srrsa01sRegStudentPersDetailWsMatricRecordAuditedFlagPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "N",
         "Y",
         " ",
      };
      return values;
   }

   public boolean checkTag(String s) {
      String values[] = getTags();
      StringBuffer temp = new StringBuffer(s);
      while (temp.length() < 1)
         temp.append(' ');
      s = temp.toString();
      for(int n=0; n< values.length; n++) {
         if (s.compareTo(values[n]) == 0) {
            return true;
         }
      }
      return false;
   }
}
