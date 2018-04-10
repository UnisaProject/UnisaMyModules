package Srslf05h.Abean;
 
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
 
public class Srslf05sDisplaySmsStaffInfoOperation
         implements Serializable
{
    String     className = "Srslf05sDisplaySmsStaffInfoOperation";
    private Srslf05sDisplaySmsStaffInfo        client;
 
    private TranData tranData = new TranData(
                                             "SRSLF05H",
                                             "Srslf05h",
                                             "SRSLF05H",
                                             "SRSLF05S_DISPLAY_SMS_STAFF_INFO",
                                             "Srslf05sDisplaySmsStaffInfo",
                                             "SRSLF051",
                                             "DEV SRC STUDENT ACADEMIC REC C",
                                             "smsenq",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "smsenq",
                                             "smsenq",
                                             "",
                                             "",
                                             "smsenq",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Srslf05sDisplaySmsStaffInfoOperation(Srslf05sDisplaySmsStaffInfo client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Srslf05sDisplaySmsStaffInfoOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doSrslf05sDisplaySmsStaffInfoOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doSrslf05sDisplaySmsStaffInfoOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation", "Entering doSrslf05sDisplaySmsStaffInfoOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf05sDisplaySmsStaffInfoOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doSrslf05sDisplaySmsStaffInfoOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationAsync", "Entering doSrslf05sDisplaySmsStaffInfoOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doSrslf05sDisplaySmsStaffInfoOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf05sDisplaySmsStaffInfoOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doSrslf05sDisplaySmsStaffInfoOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse", "Entering doSrslf05sDisplaySmsStaffInfoOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doSrslf05sDisplaySmsStaffInfoOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doSrslf05sDisplaySmsStaffInfoOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationCheckResponse", "Entering doSrslf05sDisplaySmsStaffInfoOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse", "Entering doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doSrslf05sDisplaySmsStaffInfoOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
