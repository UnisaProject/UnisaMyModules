package Ecdcq01h.Abean;
 
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
 
public class Ecdcq01sEcapsDocumentMntOperation
         implements Serializable
{
    String     className = "Ecdcq01sEcapsDocumentMntOperation";
    private Ecdcq01sEcapsDocumentMnt        client;
 
    private TranData tranData = new TranData(
                                             "ECDCQ01H",
                                             "Ecdcq01h",
                                             "ECDCQ01H",
                                             "ECDCQ01S_ECAPS_DOCUMENT_MNT",
                                             "Ecdcq01sEcapsDocumentMnt",
                                             "ECDCQ01S",
                                             "DEV ASS STUDENT ASSIGNMENTS",
                                             "ASS_J",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "ass_j",
                                             "ass_j",
                                             "",
                                             "",
                                             "ass_j",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Ecdcq01sEcapsDocumentMntOperation(Ecdcq01sEcapsDocumentMnt client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Ecdcq01sEcapsDocumentMntOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doEcdcq01sEcapsDocumentMntOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doEcdcq01sEcapsDocumentMntOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation", "Entering doEcdcq01sEcapsDocumentMntOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doEcdcq01sEcapsDocumentMntOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doEcdcq01sEcapsDocumentMntOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doEcdcq01sEcapsDocumentMntOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doEcdcq01sEcapsDocumentMntOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationAsync", "Entering doEcdcq01sEcapsDocumentMntOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doEcdcq01sEcapsDocumentMntOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doEcdcq01sEcapsDocumentMntOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doEcdcq01sEcapsDocumentMntOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse", "Entering doEcdcq01sEcapsDocumentMntOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doEcdcq01sEcapsDocumentMntOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doEcdcq01sEcapsDocumentMntOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationCheckResponse", "Entering doEcdcq01sEcapsDocumentMntOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doEcdcq01sEcapsDocumentMntOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doEcdcq01sEcapsDocumentMntOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationIgnoreResponse", "Entering doEcdcq01sEcapsDocumentMntOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doEcdcq01sEcapsDocumentMntOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doEcdcq01sEcapsDocumentMntOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
