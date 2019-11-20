package Sfrrf03h.Abean;
 
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
 
public class Sfrrf03sMntOnlineCcPaymentsOperation
         implements Serializable
{
    String     className = "Sfrrf03sMntOnlineCcPaymentsOperation";
    private Sfrrf03sMntOnlineCcPayments        client;
 
    private TranData tranData = new TranData(
                                             "SFRRF03H",
                                             "Sfrrf03h",
                                             "SFRRF03H",
                                             "SFRRF03S_MNT_ONLINE_CC_PAYMENTS",
                                             "Sfrrf03sMntOnlineCcPayments",
                                             "SFRRF03S",
                                             "DEV STF STUDENT FINANCE",
                                             "SFRRF03H",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "sfrrf03h",
                                             "sfrrf03h",
                                             "",
                                             "",
                                             "sfrrf03h",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Sfrrf03sMntOnlineCcPaymentsOperation(Sfrrf03sMntOnlineCcPayments client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Sfrrf03sMntOnlineCcPaymentsOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doSfrrf03sMntOnlineCcPaymentsOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doSfrrf03sMntOnlineCcPaymentsOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation", "Entering doSfrrf03sMntOnlineCcPaymentsOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSfrrf03sMntOnlineCcPaymentsOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doSfrrf03sMntOnlineCcPaymentsOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationAsync", "Entering doSfrrf03sMntOnlineCcPaymentsOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doSfrrf03sMntOnlineCcPaymentsOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSfrrf03sMntOnlineCcPaymentsOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doSfrrf03sMntOnlineCcPaymentsOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse", "Entering doSfrrf03sMntOnlineCcPaymentsOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse", "Entering doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse", "Entering doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSfrrf03sMntOnlineCcPaymentsOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
