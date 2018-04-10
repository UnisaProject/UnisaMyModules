package Srslf01h.Abean;
 
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
 
public class Srslf01sDisplaySmsLogOperation
         implements Serializable
{
    String     className = "Srslf01sDisplaySmsLogOperation";
    private Srslf01sDisplaySmsLog        client;
 
    private TranData tranData = new TranData(
                                             "SRSLF01H",
                                             "Srslf01h",
                                             "SRSLF01H",
                                             "SRSLF01S_DISPLAY_SMS_LOG",
                                             "Srslf01sDisplaySmsLog",
                                             "SRSLF01S",
                                             "DEV SRC STUDENT ACADEMIC REC C",
                                             "SRC_J",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "src_j",
                                             "src_j",
                                             "",
                                             "",
                                             "src_j",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Srslf01sDisplaySmsLogOperation(Srslf01sDisplaySmsLog client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Srslf01sDisplaySmsLogOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doSrslf01sDisplaySmsLogOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doSrslf01sDisplaySmsLogOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation", "Entering doSrslf01sDisplaySmsLogOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrslf01sDisplaySmsLogOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrslf01sDisplaySmsLogOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf01sDisplaySmsLogOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doSrslf01sDisplaySmsLogOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationAsync", "Entering doSrslf01sDisplaySmsLogOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doSrslf01sDisplaySmsLogOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrslf01sDisplaySmsLogOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf01sDisplaySmsLogOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doSrslf01sDisplaySmsLogOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse", "Entering doSrslf01sDisplaySmsLogOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doSrslf01sDisplaySmsLogOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrslf01sDisplaySmsLogOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf01sDisplaySmsLogOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf01sDisplaySmsLogOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doSrslf01sDisplaySmsLogOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationCheckResponse", "Entering doSrslf01sDisplaySmsLogOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf01sDisplaySmsLogOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doSrslf01sDisplaySmsLogOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doSrslf01sDisplaySmsLogOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationIgnoreResponse", "Entering doSrslf01sDisplaySmsLogOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf01sDisplaySmsLogOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf01sDisplaySmsLogOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
