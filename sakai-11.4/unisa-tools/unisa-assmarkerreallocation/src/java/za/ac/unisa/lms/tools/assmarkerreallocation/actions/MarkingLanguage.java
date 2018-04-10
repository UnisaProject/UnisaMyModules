package za.ac.unisa.lms.tools.assmarkerreallocation.actions;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.tools.assmarkerreallocation.dao.MarkingLanguageDAO;

public class MarkingLanguage {
	           public MarkingLanguage (String studyUnit,String acadYear,String  period,String personellNumber,String assUniqNum){
		                        year=Short.parseShort(acadYear);
                                dummyAcadYear=Short.parseShort(acadYear);
                                semesterPeriod=Short.parseShort(period);
                                personellNum=Integer.parseInt(personellNumber);
                                this.assUniqNum= Integer.parseInt(assUniqNum);
                                this.studyUnit=studyUnit;
                               dao=new  MarkingLanguageDAO();
               }
	           public MarkingLanguage (String studyUnit,Short year,Short semesterPeriod,String personellNumber,String assUniqNum){
                   this.year=year;
                   dummyAcadYear=year;
                   this.semesterPeriod=semesterPeriod;
                   personellNum=Integer.parseInt(personellNumber);
                   this.assUniqNum= Integer.parseInt(assUniqNum);
                   this.studyUnit=studyUnit;
                  dao=new  MarkingLanguageDAO();
  }
	           public MarkingLanguage (){
	                 dao=new  MarkingLanguageDAO();
	          }
              Short year;
              Short dummyAcadYear;
              Integer assUniqNum;
              Short semesterPeriod;
              Integer personellNum;
              String studyUnit;
              MarkingLanguageDAO dao;
	          public  List getLangLinkedWithMarker()throws Exception{
                          return dao.getLangLinkedWithMarker(studyUnit, dummyAcadYear,semesterPeriod,personellNum,assUniqNum);
              }
	          public  void  linkLangWithMarker(String[] newLangList)throws Exception{
                              dao.removeLangLinkedWithMarker(personellNum,assUniqNum);
	                          dao.linkLangWithMarker(personellNum,assUniqNum,newLangList);
              }
              public  List getLangLinkedWithUniqueAssNum(Integer assUniqNum )throws Exception{
        	                   return dao.getLangLinkedWithUniqueAssNum(assUniqNum);

              }
              public void  removeLangLinkedWithMarker(Integer personellNum,Integer assUniqNum)throws Exception{
            	               dao.removeLangLinkedWithMarker(personellNum, assUniqNum);
              }

}
