package Sruaf01h.Abean;
 
import java.beans.*;
 
public class Sruaf01sStudyUnitAdditionStudentAnnualRecordRegistrationMethodCodePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "P",
         "C",
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
