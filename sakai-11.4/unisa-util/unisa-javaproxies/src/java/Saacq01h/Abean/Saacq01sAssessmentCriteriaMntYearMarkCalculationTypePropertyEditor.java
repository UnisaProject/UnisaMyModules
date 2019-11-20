package Saacq01h.Abean;
 
import java.beans.*;
 
public class Saacq01sAssessmentCriteriaMntYearMarkCalculationTypePropertyEditor extends PropertyEditorSupport {
 
   public String[] getTags() {
      String values[] = {
         "1",
         "2",
         "3",
         "4",
         "5",
         "6",
         "7",
         "P",
         "O",
         "E",
         "N",
         "R",
         "J",
         "M",
         "I",
         "A",
         "S",
         "T",
         "G",
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
