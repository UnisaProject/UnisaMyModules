package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Utilities;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkingLangModel;
public class MarkingLanguageDAO extends LoginDAO{
		   public  List getLangLinkedWithMarker(String studyUnit,Short year,Short period,Integer personellNum,Integer assUniqNum)throws Exception{
		                           String sql="select   language_gc203 ,other_lang_desc  from assmrkln a,assmrk b"+
			                                " where mk_unique_nr=fk_unique_nr  and fk_unique_nr="+assUniqNum+" and a.mk_lecturer_nr=b.mk_lecturer_nr"+
				                            " and b.mk_lecturer_nr="+ personellNum+"  and  fk_unq_ass_year="+year+" and fk_unq_ass_period="+period;
		                           List langList=new ArrayList();
	                               try{ 
			                               JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			                               List queryList = jdt.queryForList(sql);
        	                               Iterator i = queryList.iterator();
			                               while (i.hasNext()) {
				                                  ListOrderedMap data = (ListOrderedMap) i.next();
				                                  String language=Utilities.replaceNull(data.get("language_gc203"));
				                                  String otherLanguagedescr=Utilities.replaceNull(data.get("other_Lang_desc"));
				                                  if(language.equalsIgnoreCase("ENGLISH")||(language.equalsIgnoreCase("AFRIKAANS"))){
				          		                           langList.add(language);
				                                  }else{
				                                	      langList.add(otherLanguagedescr);
				                                  }
			                               }
		                          }catch (Exception ex) {
			                                 throw new Exception("AssMarkerReallocationDAO : Error reading assmrkln,assmrk / " + ex);
		                          }		
	                             if((langList!=null)&&(!langList.isEmpty())){
	      	                            List<String> subList = langList.subList(0,langList.size());
	      	                            Collections.sort(subList);
	      	                            return subList;	
	      		                 }else{
	      		            	        return langList;
	      		                 }
	       }
		   public void  removeLangLinkedWithMarker(Integer personellNum,Integer assUniqNum)throws Exception{
			                 String sql="delete from    assmrkln  where  mk_unique_nr="+assUniqNum+" and mk_lecturer_nr="+ personellNum;
			                 try{ 
	                                 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	                                 jdt.update(sql);
	                         }catch (Exception ex) {
	                                 throw new Exception("AssMarkerReallocationDAO : deleting records from  reading assmrkln / " + ex);
                            }		
          }
          public  void  linkLangWithMarker(Integer personellNum,Integer assUniqNum,String[] newLangList)throws Exception{
			                     for(String lang:newLangList){
			                    	   insertMarkerLangToDb(lang,personellNum,assUniqNum);
			                     }
          }
		  private void  insertMarkerLangToDb(String lang,Integer personellNum,Integer assUniqNum)throws Exception{
			                   String  otherLangDesc="  ";
			                   String linkedLang=lang;
			                   if(lang.equalsIgnoreCase("ENGLISH")||(lang.equalsIgnoreCase("AFRIKAANS"))){
		                              otherLangDesc="  ";
                               }else{
	                        	        linkedLang="OTHER";
	                        	        otherLangDesc=lang;
		                       }
			                   String sql="insert into assmrkln (mk_unique_nr,mk_lecturer_nr,language_gc203,other_lang_desc)"+
	                                      "values("+assUniqNum+","+personellNum+",'"+linkedLang+"','"+otherLangDesc+"')";
	                           try{ 
                                     JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                                     jdt.update(sql);
                               }catch (Exception ex) {
                                          throw new Exception("AssMarkerReallocationDAO : Error reading assmrkln / " + ex);
                               }
		}
		public  List getLangLinkedWithUniqueAssNum(Integer assUniqNum )throws Exception{
			                String sql="select  distinct language_gc203 ,other_lang_desc  from onasln "+
                            " where mk_unique_nr="+assUniqNum;
                       List langList=new ArrayList();
                       try{ 
                            JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                            List queryList = jdt.queryForList(sql);
                            Iterator i = queryList.iterator();
                            while (i.hasNext()) {
                                  ListOrderedMap data = (ListOrderedMap) i.next();
                                  String language=Utilities.replaceNull(data.get("language_gc203"));
                                  String otherLanguage=Utilities.replaceNull(data.get("other_lang_desc"));
                                  MarkingLangModel  markingLangData=new MarkingLangModel();
                                  if(otherLanguage.trim().equals("")){
                                	     markingLangData.setOtherLanguageDesc("");
                                	     markingLangData.setLanguage(language);
                                  }else{
                                         markingLangData.setOtherLanguageDesc(otherLanguage);
                                         markingLangData.setLanguage(otherLanguage);
                                  }
                                 
                           	    langList.add(markingLangData);
                            }
                      }catch (Exception ex) {
                             throw new Exception("AssMarkerReallocationDAO : Error reading usrsun / " + ex);
                      }		
                      if((langList!=null)&&(!langList.isEmpty())){
                    	  sortLanguage(langList);
	                  }
		              return langList;
		}	
		private  void sortLanguage(List<MarkingLangModel> list){
			               List  tempList=new ArrayList();
			               for(MarkingLangModel markingLangModel:list){
			            	    tempList.add(markingLangModel.getLanguage());
			               }
			               List<String> subList = tempList.subList(0,tempList.size());
	                       Collections.sort(subList);
	                       for(int x=0;x<list.size();x++){
	                    	   MarkingLangModel markingLangModel=(MarkingLangModel)list.get(x);
	                    	   markingLangModel.setLanguage(subList.get(x).toString());
			               }
		}
}
