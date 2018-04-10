package Srscs01h.Abean;
 
import com.ca.gen80.csu.trace.*;
import com.ca.gen80.csu.exception.*;
import com.ca.gen80.jprt.*;
import com.ca.gen80.odc.*;
import com.ca.gen80.odc.msgobj.*;
import com.ca.gen80.odc.coopflow.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.util.Vector;
import java.beans.*;
import java.util.*;
import java.math.*;
 
public class Srscs01sGetRegStuCollegeOperation
         implements Serializable
{
    String     className = "Srscs01sGetRegStuCollegeOperation";
    private Srscs01sGetRegStuCollege        client;
 
    private TranData tranData = new TranData(
                                             "SRSCS01H",
                                             "Srscs01h",
                                             "SRSCS01H",
                                             "SRSCS01S_GET_REG_STU_COLLEGE",
                                             "Srscs01sGetRegStuCollege",
                                             "SRSCS01S",
                                             "DEV SRA STUDENT ACADEMIC REC A",
                                             "SRA_J",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "sra_j",
                                             "sra_j",
                                             "",
                                             "",
                                             "sra_j",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Srscs01sGetRegStuCollegeOperation(Srscs01sGetRegStuCollege client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Srscs01sGetRegStuCollegeOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doSrscs01sGetRegStuCollegeOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doSrscs01sGetRegStuCollegeOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation", "Entering doSrscs01sGetRegStuCollegeOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
            "About to perform cooperative flow");
    }
 
    try {
      out.clearMessage();
      in.clearMessage();
 
      out.setUserid(client.getClientId());
      out.setPassword(client.getClientPassword());
      out.setCommand(client.getCommandSent());
      out.setDialect(client.getDialect());
      out.setNextLocation(client.getNextLocation());
      out.setExitStateNum(client.getExitStateSent());
 
      tranData.setFileEncoding(client.getFileEncoding());
 
      tran = tranData.getTranEntry(tran, client.getComCfg(), this.getClass().getClassLoader());
 
      CoopFlow.coopFlow(tran, out, in);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrscs01sGetRegStuCollegeOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
              "Apparently no error occurred, retrieving returned data.");
        }
 
        client.setCommandReturned(in.getCommand());
        client.setExitStateReturned(in.getExitStateNum());
        client.setExitStateType(in.getExitStateType());
        client.setExitStateMsg(in.getExitStateMessage());
 
 
      }
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrscs01sGetRegStuCollegeOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrscs01sGetRegStuCollegeOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doSrscs01sGetRegStuCollegeOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationAsync", "Entering doSrscs01sGetRegStuCollegeOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationAsync",
            "About to perform asynchronous cooperative flow");
    }
 
    try {
      out.clearMessage();
 
      out.setUserid(client.getClientId());
      out.setPassword(client.getClientPassword());
      out.setCommand(client.getCommandSent());
      out.setDialect(client.getDialect());
      out.setNextLocation(client.getNextLocation());
      out.setExitStateNum(client.getExitStateSent());
 
      tranData.setFileEncoding(client.getFileEncoding());
 
      tran = tranData.getTranEntry(tran, client.getComCfg(), this.getClass().getClassLoader());
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doSrscs01sGetRegStuCollegeOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrscs01sGetRegStuCollegeOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrscs01sGetRegStuCollegeOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doSrscs01sGetRegStuCollegeOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse", "Entering doSrscs01sGetRegStuCollegeOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
            "About to retrieve asynchronous results for a cooperative flow");
    }
 
    try {
      in.clearMessage();
 
      tranData.setFileEncoding(client.getFileEncoding());
 
      tran = tranData.getTranEntry(tran, client.getComCfg(), this.getClass().getClassLoader());
 
      int result = CoopFlow.coopFlowGetResponse(tran, in, id, block);
 
      if (result == CoopFlow.DATA_NOT_READY)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doSrscs01sGetRegStuCollegeOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrscs01sGetRegStuCollegeOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
              "Apparently no error occurred, retrieving returned data.");
        }
 
        client.setCommandReturned(in.getCommand());
        client.setExitStateReturned(in.getExitStateNum());
        client.setExitStateType(in.getExitStateType());
        client.setExitStateMsg(in.getExitStateMessage());
 
 
       return true;
      }
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrscs01sGetRegStuCollegeOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrscs01sGetRegStuCollegeOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doSrscs01sGetRegStuCollegeOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationCheckResponse", "Entering doSrscs01sGetRegStuCollegeOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrscs01sGetRegStuCollegeOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doSrscs01sGetRegStuCollegeOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doSrscs01sGetRegStuCollegeOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationIgnoreResponse", "Entering doSrscs01sGetRegStuCollegeOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrscs01sGetRegStuCollegeOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrscs01sGetRegStuCollegeOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
