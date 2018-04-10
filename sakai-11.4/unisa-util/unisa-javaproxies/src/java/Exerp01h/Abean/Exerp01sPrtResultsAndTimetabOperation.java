package Exerp01h.Abean;
 
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
 
public class Exerp01sPrtResultsAndTimetabOperation
         implements Serializable
{
    String     className = "Exerp01sPrtResultsAndTimetabOperation";
    private Exerp01sPrtResultsAndTimetab        client;
 
    private TranData tranData = new TranData(
                                             "EXERP01H",
                                             "Exerp01h",
                                             "EXERP01H",
                                             "EXERP01S_PRT_RESULTS_AND_TIMETAB",
                                             "Exerp01sPrtResultsAndTimetab",
                                             "EXERP01S",
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
 
    public Exerp01sPrtResultsAndTimetabOperation(Exerp01sPrtResultsAndTimetab client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Exerp01sPrtResultsAndTimetabOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doExerp01sPrtResultsAndTimetabOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doExerp01sPrtResultsAndTimetabOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation", "Entering doExerp01sPrtResultsAndTimetabOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doExerp01sPrtResultsAndTimetabOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doExerp01sPrtResultsAndTimetabOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doExerp01sPrtResultsAndTimetabOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doExerp01sPrtResultsAndTimetabOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationAsync", "Entering doExerp01sPrtResultsAndTimetabOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doExerp01sPrtResultsAndTimetabOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doExerp01sPrtResultsAndTimetabOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doExerp01sPrtResultsAndTimetabOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse", "Entering doExerp01sPrtResultsAndTimetabOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doExerp01sPrtResultsAndTimetabOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doExerp01sPrtResultsAndTimetabOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationCheckResponse", "Entering doExerp01sPrtResultsAndTimetabOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doExerp01sPrtResultsAndTimetabOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doExerp01sPrtResultsAndTimetabOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationIgnoreResponse", "Entering doExerp01sPrtResultsAndTimetabOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doExerp01sPrtResultsAndTimetabOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doExerp01sPrtResultsAndTimetabOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
