package Srrqn01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srrqn01sQuoteStudyFeesTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srrqn01h.Servlet.Srrqn01sQuoteStudyFeesServlet";
    public String getServletName()     {return ServletName; }
 
    public Srrqn01sQuoteStudyFeesTran ()
   {
      super ();
      String myName = "Srrqn01sQuoteStudyFees()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srrqn01sQuoteStudyFeesTran (serverDialog sysObj,
                                  Srrqn01sQuoteStudyFeesInput viewIn,
                                  Srrqn01sQuoteStudyFeesOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrqn01sQuoteStudyFeesTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srrqn01sQuoteStudyFeesTran (serverDialog sysObj,
                                  Srrqn01sQuoteStudyFeesInput viewIn,
                                  Srrqn01sQuoteStudyFeesOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrqn01sQuoteStudyFeesTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srrqn01sQuoteStudyFeesInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srrqn01sQuoteStudyFeesOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srrqn01h.Srrqn01sQuoteStudyFeesInput")
      checkInViewValue ((Srrqn01sQuoteStudyFeesInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srrqn01sQuoteStudyFeesInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srrqn01h.Srrqn01sQuoteStudyFeesOutput")
      checkOutViewValue ((Srrqn01sQuoteStudyFeesOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srrqn01sQuoteStudyFeesOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srrqn01sQuoteStudyFeesInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srrqn01sQuoteStudyFeesOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
