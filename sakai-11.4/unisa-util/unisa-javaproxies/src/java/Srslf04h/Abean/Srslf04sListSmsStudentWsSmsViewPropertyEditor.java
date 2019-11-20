package Srslf04h.Abean;
 
import java.beans.*;
 
public class Srslf04sListSmsStudentWsSmsViewPropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "A ",
         "U ",
         "D ",
         "  ",
      };
      return values;
   }

   public boolean checkTag(String s) {
      String values[] = getTags();
      StringBuffer temp = new StringBuffer(s);
      while (temp.length() < 2)
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
