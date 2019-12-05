package Expsh01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Expsh01sExamScriptOutstandingTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Expsh01h.Servlet.Expsh01sExamScriptOutstandingServlet";
    public String getServletName()     {return ServletName; }
 
    public Expsh01sExamScriptOutstandingTran ()
   {
      super ();
      String myName = "Expsh01sExamScriptOutstanding()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Expsh01sExamScriptOutstandingTran (serverDialog sysObj,
                                  Expsh01sExamScriptOutstandingInput viewIn,
                                  Expsh01sExamScriptOutstandingOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Expsh01sExamScriptOutstandingTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Expsh01sExamScriptOutstandingTran (serverDialog sysObj,
                                  Expsh01sExamScriptOutstandingInput viewIn,
                                  Expsh01sExamScriptOutstandingOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Expsh01sExamScriptOutstandingTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Expsh01sExamScriptOutstandingInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Expsh01sExamScriptOutstandingOutput x)
       throws PropertyVetoException
  {
    checkOutViewValue (x);
    super.setOutView (x);
  }
 
  //--------------------------------------------------
  // Process vetoable change requests for input properties with permitted values
 
  public synchronized void InViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Expsh01h.Expsh01sExamScriptOutstandingInput")
      checkInViewValue ((Expsh01sExamScriptOutstandingInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Expsh01sExamScriptOutstandingInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Expsh01h.Expsh01sExamScriptOutstandingOutput")
      checkOutViewValue ((Expsh01sExamScriptOutstandingOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Expsh01sExamScriptOutstandingOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Expsh01sExamScriptOutstandingInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Expsh01sExamScriptOutstandingOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
