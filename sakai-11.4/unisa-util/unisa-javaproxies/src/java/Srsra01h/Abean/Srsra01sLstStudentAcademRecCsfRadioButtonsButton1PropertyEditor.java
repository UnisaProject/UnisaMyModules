package Srsra01h.Abean;
 
import java.beans.*;
 
public class Srsra01sLstStudentAcademRecCsfRadioButtonsButton1PropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "1",
         "2",
         "5",
         "4",
         "3",
         "0",
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
