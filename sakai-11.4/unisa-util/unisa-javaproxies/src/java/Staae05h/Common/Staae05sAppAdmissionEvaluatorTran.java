package Staae05h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Staae05sAppAdmissionEvaluatorTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Staae05h.Servlet.Staae05sAppAdmissionEvaluatorServlet";
    public String getServletName()     {return ServletName; }
 
    public Staae05sAppAdmissionEvaluatorTran ()
   {
      super ();
      String myName = "Staae05sAppAdmissionEvaluator()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Staae05sAppAdmissionEvaluatorTran (serverDialog sysObj,
                                  Staae05sAppAdmissionEvaluatorInput viewIn,
                                  Staae05sAppAdmissionEvaluatorOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Staae05sAppAdmissionEvaluatorTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Staae05sAppAdmissionEvaluatorTran (serverDialog sysObj,
                                  Staae05sAppAdmissionEvaluatorInput viewIn,
                                  Staae05sAppAdmissionEvaluatorOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Staae05sAppAdmissionEvaluatorTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Staae05sAppAdmissionEvaluatorInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Staae05sAppAdmissionEvaluatorOutput x)
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
    if (e.getNewValue().getClass().getName() == "Staae05h.Staae05sAppAdmissionEvaluatorInput")
      checkInViewValue ((Staae05sAppAdmissionEvaluatorInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Staae05sAppAdmissionEvaluatorInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Staae05h.Staae05sAppAdmissionEvaluatorOutput")
      checkOutViewValue ((Staae05sAppAdmissionEvaluatorOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Staae05sAppAdmissionEvaluatorOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Staae05sAppAdmissionEvaluatorInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Staae05sAppAdmissionEvaluatorOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
