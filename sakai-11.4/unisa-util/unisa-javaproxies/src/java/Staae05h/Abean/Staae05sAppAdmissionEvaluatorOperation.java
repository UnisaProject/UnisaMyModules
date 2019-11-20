package Staae05h.Abean;
 
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
 
public class Staae05sAppAdmissionEvaluatorOperation
         implements Serializable
{
    String     className = "Staae05sAppAdmissionEvaluatorOperation";
    private Staae05sAppAdmissionEvaluator        client;
 
    private TranData tranData = new TranData(
                                             "STAAE05H",
                                             "Staae05h",
                                             "STAAE05H",
                                             "STAAE05S_APP_ADMISSION_EVALUATOR",
                                             "Staae05sAppAdmissionEvaluator",
                                             "STAAE05S",
                                             "DEV STA STUDENT ADMIN",
                                             "staae01",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "staae01",
                                             "staae01",
                                             "",
                                             "",
                                             "staae01",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Staae05sAppAdmissionEvaluatorOperation(Staae05sAppAdmissionEvaluator client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Staae05sAppAdmissionEvaluatorOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doStaae05sAppAdmissionEvaluatorOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doStaae05sAppAdmissionEvaluatorOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation", "Entering doStaae05sAppAdmissionEvaluatorOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doStaae05sAppAdmissionEvaluatorOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doStaae05sAppAdmissionEvaluatorOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationAsync", "Entering doStaae05sAppAdmissionEvaluatorOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doStaae05sAppAdmissionEvaluatorOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doStaae05sAppAdmissionEvaluatorOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doStaae05sAppAdmissionEvaluatorOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse", "Entering doStaae05sAppAdmissionEvaluatorOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doStaae05sAppAdmissionEvaluatorOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doStaae05sAppAdmissionEvaluatorOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationCheckResponse", "Entering doStaae05sAppAdmissionEvaluatorOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse", "Entering doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doStaae05sAppAdmissionEvaluatorOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
