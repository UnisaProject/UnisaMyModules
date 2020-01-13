package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class SmsDAO extends StudentSystemDAO{
	
	   //for  when  batch number is entered on first input screen
	    private String dateStatement(String fromDate,String toDate){
		     return " and  to_char(sent_on,'yyyy-MM-dd')>='"+fromDate+"' and to_char(sent_on,'yyyy-MM-dd')<='"+toDate+"'";
	    }
	     public ArrayList getAllSmsData(String batchNum){

	     		String sql = " select mk_batch_nr,reference_nr,cell_nr,message,sent_on,delivered_on,"+
		             "sequence_nr,nvl(message_status,'Unknown'),mk_pers_nr,log_ref_id,mk_mtn_status_code "+
	                 "from smslog where mk_batch_nr="+batchNum +" order by reference_nr";
	       	       return getDataFromDatabase(sql);
	     }
	     public ArrayList getAllSmsDataForStudent(String batchNum,String stuNum){

	       		 String sql = " select mk_batch_nr,sequence_nr,reference_nr,cell_nr,message,sent_on,delivered_on,"+
		             "sequence_nr,nvl(message_status,'Unknown') "+
	                 "from smslog where mk_batch_nr="+batchNum +" and reference_nr="+stuNum+" order by reference_nr";
                  return getDataFromDatabase(sql);
	     }
	     public ArrayList getDeliveredSmsData(String batchNum){
  
	     		     String sql = " select mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
		               "sequence_nr,nvl(message_status,'Unknown'),mk_mtn_status_code "+
	                   "from smslog  where mk_batch_nr="+batchNum +"  and message_status='DELIVRD' order by reference_nr";
	       	         return getDataFromDatabase(sql);
	     }
	     public ArrayList getDeliveredSmsDataForStu(String batchNum,String stuNum){

 		     String sql = " select mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
               "sequence_nr,nvl(message_status,'Unknown'),mk_mtn_status_code "+
               "from smslog  where mk_batch_nr='"+batchNum +"'  and message_status='DELIVRD' and reference_nr="+stuNum+" order by reference_nr";
   	         return getDataFromDatabase(sql);
         }
	     public  ArrayList getUndeliveredSmsForStu(String batchNum,String stuNum){

	    	        String sql = " select  sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                      "nvl(message_status,'Unknown') "+
                      "from smslog  where mk_batch_nr="+batchNum +"  and message_status <> 'DELIVRD'  and reference_nr="+stuNum+" order by reference_nr";
	       	        return getDataFromDatabase(sql);
	     }
	     public  ArrayList getUndeliveredSms(String batchNum){
                    String sql = " select sequence_nr,reference_nr,cell_nr,message,sent_on,"+
                                 "nvl(message_status,'Unknown')"+
                                 "from smslog  where mk_batch_nr="+batchNum +"  and message_status <> 'DELIVRD' order by reference_nr";
    	        return getDataFromDatabase(sql);
         }
	     
	     //when student number is selected
	public ArrayList getAllSmsDataForStu(String stuNum,String respCode,
			                             String personnelNum,String fromDate,String toDate,String filterStr){
 	        String personnelNumStatement=" ";
 	        String filterStrStatement="";
 	        if(!filterStr.equals(""))
 	        	filterStrStatement=" and message like '%"+filterStr+"%'";
             if(!personnelNum.equals(""))
                   personnelNumStatement=" and mk_pers_nr="+personnelNum+" ";
             String sql = " select   sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,nvl(message_status,'Unknown') as status from smslog where reference_nr="+stuNum+
                ""+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by reference_nr";
             ArrayList  interimResults= getDataFromDatabase(sql);
             if(!respCode.equals("")){
                    retainSmsWithRCNum(interimResults,respCode);
             }
             return interimResults;
    }
    public ArrayList getDeliveredSmsDataForStu(String stuNum,String respCode,String personnelNum,String fromDate,String toDate,String filterStr){
             String personnelNumStatement="";
             String filterStrStatement="";
  	        if(!filterStr.equals(""))
  	        	filterStrStatement=" and message like '%"+filterStr+"%'";
             if(!personnelNum.equals(""))
                  personnelNumStatement=" and mk_pers_nr="+personnelNum+"";
             String sql = " select   sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                          "nvl(message_status,'Unknown') as status "+
                          "from smslog  where  message_status='DELIVRD' and reference_nr="+stuNum+""+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by sent_on desc";
             ArrayList  interimResults= getDataFromDatabase(sql);
             if(!respCode.equals("")){
                   retainSmsWithRCNum(interimResults,respCode);
             }
             return interimResults;
     }
     public  ArrayList getUndeliveredSmsForStu(String stuNum,String respCode,String personnelNum,String fromDate,String toDate,String filterStr){
                          String personnelNumStatement="";
                          String filterStrStatement="";
	                      if(!filterStr.equals(""))
	        	              filterStrStatement=" and message like '%"+filterStr+"%'";
                          if(!personnelNum.equals(""))
                               personnelNumStatement=" and mk_pers_nr="+personnelNum+"";
                            String sql = " select sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                             "nvl(message_status,'Unknown') as status "+
                              "from smslog  where message_status <> 'DELIVRD'  and  reference_nr="+stuNum+""+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by sent_on desc";
                ArrayList  interimResults= getDataFromDatabase(sql);
                if(!respCode.equals("")){
                        retainSmsWithRCNum(interimResults,respCode);
                }
                return interimResults;
     }	     //when a cellphone is selected
	     public ArrayList getAllSmsDataForCellNum(String cellNum,String respCode,String personnelNum,String fromDate,String toDate,String filterStr){
             String personnelNumStatement="";
             String filterStrStatement="";
             if(!filterStr.equals(""))
	              filterStrStatement=" and message like '%"+filterStr+"%'";
                    if(!personnelNum.equals(""))
                          personnelNumStatement=" and mk_pers_nr="+personnelNum+"";
                    String sql = " select   sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                    "nvl(message_status,'Unknown') as status "+
                    " from smslog where cell_nr='"+cellNum+"'"+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by sent_on desc";
                    ArrayList  interimResults= getDataFromDatabase(sql);
                    if(!respCode.equals("")){
                           retainSmsWithRCNum(interimResults,respCode);
                    }
                    return interimResults;
           }
           public ArrayList getDeliveredSmsDataForCellNum(String cellNum,String respCode,String personnelNum,String fromDate,String toDate,String filterStr){
               String personnelNumStatement="";
               String filterStrStatement="";
               if(!filterStr.equals(""))
 	              filterStrStatement=" and message like '%"+filterStr+"%'";
                    if(!personnelNum.equals(""))
 	                    personnelNumStatement=" and mk_pers_nr="+personnelNum+"";
                    String sql = " select   sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                                 "nvl(message_status,'Unknown') as status "+
                                 "from smslog  where  message_status='DELIVRD' and cell_nr='"+cellNum+"'"+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by sent_on desc";
                    ArrayList  interimResults= getDataFromDatabase(sql);
                    if(!respCode.equals("")){
	                      retainSmsWithRCNum(interimResults,respCode);
                    }
                    return interimResults;
            }
            public  ArrayList getUndeliveredSmsForCellNum(String cellNum,String respCode,String personnelNum,String fromDate,String toDate,String filterStr){
                String personnelNumStatement="";
                String filterStrStatement="";
                if(!filterStr.equals(""))
  	              filterStrStatement=" and message like '%"+filterStr+"%'";
                        if(!personnelNum.equals(""))
	                    personnelNumStatement=" and mk_pers_nr="+personnelNum+"";
                       String sql = " select sequence_nr,mk_batch_nr,reference_nr,cell_nr,message,sent_on,"+
                                    "nvl(message_status,'Unknown') as status "+
                                     "from smslog  where message_status <> 'DELIVRD'  and cell_nr='"+cellNum+"'"+personnelNumStatement+dateStatement(fromDate,toDate)+filterStrStatement+" order by sent_on desc";
                       ArrayList  interimResults= getDataFromDatabase(sql);
                       if(!respCode.equals("")){
  	                         retainSmsWithRCNum(interimResults,respCode);
                       }
                       return interimResults;
            }
            //smsdata for batchNum
            
            public ArrayList getSmsForBatchNum(String view,String responsibilityCode,String personnelNumber,String startRef,String batchNum){
           		String sql = " select mk_batch_nr,reference_nr,cell_nr,message,sent_on,delivered_on,"+
		             "sequence_nr,nvl(message_status,'Unknown') as status,mk_pers_nr,log_ref_id,mk_mtn_status_code "+
	                 "from smslog  where mk_batch_nr="+batchNum;
	     		     if(view.equals("Only Delivered items")){
	     		    	   sql+=" and  message_status = 'DELIVRD'";
	     		     }else if(view.equals("Only Undelivered items")){
	     		    	   sql+=" and  message_status <> 'DELIVRD'";
	     		     }
	     		     if(!responsibilityCode.equals("")){
	     		    	      
	     			          sql+=" and mk_rc_code="+responsibilityCode;
	                 }else if(!personnelNumber.equals("")){
	     		    	       sql+="  and mk_pers_nr="+personnelNumber;
	       		     }
	     		     if(startRef!=null&&!startRef.equals("")){
	     		    	      sql+=" and reference_nr >"+startRef;
	     		     }
	          		return getDataFromDatabase(sql);
	          		
	     }
           private ArrayList getDataFromDatabase(String sql){
	    	             ArrayList results = new ArrayList();
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	            List queryList;
	            queryList = jdt.queryForList(sql);
	            Iterator i = queryList .iterator();
	            while (i.hasNext()){	
	                  ListOrderedMap data = (ListOrderedMap) i.next();
	                  Sms smsData = new Sms();
	                  String deliveryStatus=data.get("status").toString();
	                  if(deliveryStatus.equalsIgnoreCase("DELIVRD")){
	                	      deliveryStatus="Delivered";
	                  }
	                  if(!deliveryStatus.equals("Unknown")&&(!deliveryStatus.equalsIgnoreCase("Delivered"))){
	                    	  deliveryStatus="Undelivered";
	                  }
	                try{
	                	   smsData.setBatchNr(data.get("mk_batch_nr").toString());
 	 	                   smsData.setSentOn(data.get("sent_on").toString());
 	 	                   smsData.setCellNumber(data.get("cell_nr").toString());
 	 	                   smsData.setRefNumber(data.get("reference_nr").toString());
 	 	                   smsData.setMessageStatus(deliveryStatus);
 	 	                   smsData.setMessage(data.get("message").toString());
 	 	                   smsData.setSequenceNr(data.get("sequence_nr").toString());
 	 	                   results.add(smsData);			
	                }catch(NullPointerException e ){e.printStackTrace();}
	            }
	            return results;
	    	 
	     }
         private String querySingleValue(String query, String field){
	 		                JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	 		                List queryList;
	 		                String result = "";
	 		        		queryList = jdt.queryForList(query);
	 		                Iterator i = queryList.iterator();
	 		                if (i.hasNext()) {
	                            ListOrderedMap data = (ListOrderedMap) i.next();
	                            if (data.get(field) == null){
	                            } else {
	                                result = data.get(field).toString();
	                            }
	 		                }
	 		                return result;
	    }
	      private String getCellNumber(String refNum){
          	        String sql = " select cell_number from adrph where reference_no="+refNum+" and fk_adrcatcode=1";
          	        return querySingleValue(sql,"cell_number");
         }
	      private String getSmsHavingRCNum(String message,String refCode){
    	        String sql = " select sms_msg from smsreq where sms_msg="+message+" and mk_rc_code='"+refCode+"'";
    	        return querySingleValue(sql,"sms_msg");
   }
	     public void retainSmsWithRCNum(ArrayList smsRecords,String refCode){
	      	 	  		if(smsRecords!=null){
	  			           ArrayList requiredRecords=new ArrayList();
	  	    	           for(int x=0;x<smsRecords.size();x++){
	  			                Sms smsRecord= (Sms)smsRecords.get(x);
	  			                String message=smsRecord.getMessage().trim();
	  			                String sms_msg=getSmsHavingRCNum(message,refCode);
	  			                if(!sms_msg.equals("")){
	  			                	  requiredRecords.add(smsRecord);
	  			                }//if
	  			   	       }//for
	  	    	           smsRecords.clear();
	  	    	           smsRecords.addAll(requiredRecords);
	  		        }
	   	}
	     public ArrayList getSmsWithStuNum(String stuNum,String view,String responsiblityCode,String personnelNumber,String fromDate,String toDate,String filterStr){
	    	                     if(view.equals("All items")){
	    	                    	    return getAllSmsDataForStu(stuNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
	    	                     }else if(view.equals("Only Delivered items")){
	    	                    	      return getDeliveredSmsDataForStu(stuNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
	    	                     }else{
	    	                    	      return getUndeliveredSmsForStu(stuNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
	    	                     }
	     }
	     public ArrayList getSmsWithCellNum(String cellNum,String view,String responsiblityCode,String personnelNumber,String fromDate,String toDate,String filterStr){
                     if(view.equals("All items")){
            	          return getAllSmsDataForCellNum(cellNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
                     }else if(view.equals("Only Delivered items")){
            	                return getDeliveredSmsDataForCellNum(cellNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
                     }else{
            	             return getUndeliveredSmsForCellNum(cellNum,responsiblityCode,personnelNumber,fromDate,toDate,filterStr);
                     }
	     }
}
