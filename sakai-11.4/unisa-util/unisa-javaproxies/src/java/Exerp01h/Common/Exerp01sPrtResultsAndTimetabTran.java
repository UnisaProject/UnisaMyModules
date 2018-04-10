package Exerp01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Exerp01sPrtResultsAndTimetabTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Exerp01h.Servlet.Exerp01sPrtResultsAndTimetabServlet";
    public String getServletName()     {return ServletName; }
 
    public Exerp01sPrtResultsAndTimetabTran ()
   {
      super ();
      String myName = "Exerp01sPrtResultsAndTimetab()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Exerp01sPrtResultsAndTimetabTran (serverDialog sysObj,
                                  Exerp01sPrtResultsAndTimetabInput viewIn,
                                  Exerp01sPrtResultsAndTimetabOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exerp01sPrtResultsAndTimetabTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Exerp01sPrtResultsAndTimetabTran (serverDialog sysObj,
                                  Exerp01sPrtResultsAndTimetabInput viewIn,
                                  Exerp01sPrtResultsAndTimetabOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exerp01sPrtResultsAndTimetabTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Exerp01sPrtResultsAndTimetabInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Exerp01sPrtResultsAndTimetabOutput x)
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
    if (e.getNewValue().getClass().getName() == "Exerp01h.Exerp01sPrtResultsAndTimetabInput")
      checkInViewValue ((Exerp01sPrtResultsAndTimetabInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Exerp01sPrtResultsAndTimetabInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Exerp01h.Exerp01sPrtResultsAndTimetabOutput")
      checkOutViewValue ((Exerp01sPrtResultsAndTimetabOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Exerp01sPrtResultsAndTimetabOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Exerp01sPrtResultsAndTimetabInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Exerp01sPrtResultsAndTimetabOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
