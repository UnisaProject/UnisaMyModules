package Grgdg01h.Abean;
 
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
 
public class Grgdg01sMntStudentGraduationOperation
         implements Serializable
{
    String     className = "Grgdg01sMntStudentGraduationOperation";
    private Grgdg01sMntStudentGraduation        client;
 
    private TranData tranData = new TranData(
                                             "GRGDG01H",
                                             "Grgdg01h",
                                             "GRGDG01H",
                                             "GRGDG01S_MNT_STUDENT_GRADUATION",
                                             "Grgdg01sMntStudentGraduation",
                                             "GRGDG01S",
                                             "DEV SRC STUDENT ACADEMIC REC C",
                                             "F326",
                                              new String [] {"","","","","","","",""},
                                              new String [] {"","","","","","","",""},
                                             "f326",
                                             "f326",
                                             "",
                                             "",
                                             "f326",
                                             "com.ca.gen80.odc.ITPIPTranEntry",
                                             new String [] {"", "0","Y"});
 
 
    private OutMessage out = new OutMessage();
    private InMessage in = new InMessage();
    private ITranEntry tran;
 
    public Grgdg01sMntStudentGraduationOperation(Grgdg01sMntStudentGraduation client)
    {
        this.client = client;
 
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, className, "new Grgdg01sMntStudentGraduationOperation( client )");
        }
 
    }
 
   // -------------------------------------------------------------------
   // doGrgdg01sMntStudentGraduationOperation is called to issue a single request to the
   //  transaction server.
   //
   public void doGrgdg01sMntStudentGraduationOperation()
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation", "Entering doGrgdg01sMntStudentGraduationOperation routine");
    }
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
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
         Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
            "Successfully performed a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doGrgdg01sMntStudentGraduationOperation", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
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
         Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperation",
            "Received CSUException:", e);
      }
        throw new ProxyException("doGrgdg01sMntStudentGraduationOperation", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doGrgdg01sMntStudentGraduationOperationAsync is called to begin a single request to the
   //  server asynchronously.
   //
   public int doGrgdg01sMntStudentGraduationOperationAsync(boolean noResponse) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationAsync", "Entering doGrgdg01sMntStudentGraduationOperationAsync routine");
    }
 
    int result = -1;
 
 
    // Setup the tran entry data
    tranData.setIImportView(client.importView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationAsync",
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
 
      result = CoopFlow.coopFlowPollResponse(tran, out, "doGrgdg01sMntStudentGraduationOperationAsync", noResponse);
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationAsync",
            "Successfully started an asynchronous cooperative flow, checking results, id=" + result);
      }
 
 
      return result;
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationAsync",
            "Received CSUException:", e);
      }
        throw new ProxyException("doGrgdg01sMntStudentGraduationOperationAsync", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doGrgdg01sMntStudentGraduationOperationGetResponse is called to retrieve the results
   //  of a particular asynchronous cooperative flow.
   //
   public boolean doGrgdg01sMntStudentGraduationOperationGetResponse(int id, boolean block)
               throws ProxyException, PropertyVetoException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse", "Entering doGrgdg01sMntStudentGraduationOperationGetResponse routine, id= " + id);
    }
 
 
    // Setup the tran entry data
    tranData.setIExportView(client.exportView);
 
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
        Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
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
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
              "GetResponse returned PENDING");
        }
        return false;
      }
 
      if (result == CoopFlow.INVALID_ID)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
              " Illegal identifier given for GetResponse: " + id);
        }
        throw new ProxyException("doGrgdg01sMntStudentGraduationOperationGetResponse", " Illegal asynchronous id given in get response call: " + id);
      }
 
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
            "Successfully performed a GetResponse on a cooperative flow, checking results");
      }
 
      if (in.errorOccurred() == true)
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
              "Apparently an error occurred, dumping it.");
 
           Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
              "Returned error number",
              new Integer(in.getError().getNumber()).toString());
 
           Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
              "Returned error message",
              in.getError().toString());
        }
        throw new ProxyException("doGrgdg01sMntStudentGraduationOperationGetResponse", in.getError().toString());
      }
      else
      {
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
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
         Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationGetResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doGrgdg01sMntStudentGraduationOperationGetResponse", e.getClass().getName() + ": " + e.toString());
    }
  }
 
   // -------------------------------------------------------------------
   // doGrgdg01sMntStudentGraduationOperationCheckResponse is called to inquire about the
   //  results of an asynchronous cooperative flow.
   //
   public int doGrgdg01sMntStudentGraduationOperationCheckResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationCheckResponse", "Entering doGrgdg01sMntStudentGraduationOperationCheckResponse routine, id= " + id);
    }
 
    try {
      return CoopFlow.coopFlowCheckStatus(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationCheckResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doGrgdg01sMntStudentGraduationOperationCheckResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
 
   // -------------------------------------------------------------------
   // doGrgdg01sMntStudentGraduationOperationIgnoreResponse is called to inquire that the
   //  indicated asynchronous request is no longer relevant and the
   //  results can be ignored.
   //
   public void doGrgdg01sMntStudentGraduationOperationIgnoreResponse(int id) throws ProxyException
   {
    if (Trace.isTracing(Trace.MASK_APPLICATION))
    {
       Trace.record(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationIgnoreResponse", "Entering doGrgdg01sMntStudentGraduationOperationIgnoreResponse routine, id= " + id);
    }
 
    try {
      CoopFlow.coopFlowIgnoreResponse(id);
    }
    catch (CSUException e) {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.dump(Trace.MASK_APPLICATION, "doGrgdg01sMntStudentGraduationOperationIgnoreResponse",
            "Received CSUException:", e);
      }
      throw new ProxyException("doGrgdg01sMntStudentGraduationOperationIgnoreResponse", e.getClass().getName() + ": " + e.toString());
    }
   }
}
