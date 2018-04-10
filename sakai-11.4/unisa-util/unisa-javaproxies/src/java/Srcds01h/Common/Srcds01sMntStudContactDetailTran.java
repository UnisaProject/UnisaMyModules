package Srcds01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srcds01sMntStudContactDetailTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srcds01h.Servlet.Srcds01sMntStudContactDetailServlet";
    public String getServletName()     {return ServletName; }
 
    public Srcds01sMntStudContactDetailTran ()
   {
      super ();
      String myName = "Srcds01sMntStudContactDetail()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srcds01sMntStudContactDetailTran (serverDialog sysObj,
                                  Srcds01sMntStudContactDetailInput viewIn,
                                  Srcds01sMntStudContactDetailOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srcds01sMntStudContactDetailTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srcds01sMntStudContactDetailTran (serverDialog sysObj,
                                  Srcds01sMntStudContactDetailInput viewIn,
                                  Srcds01sMntStudContactDetailOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srcds01sMntStudContactDetailTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srcds01sMntStudContactDetailInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srcds01sMntStudContactDetailOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srcds01h.Srcds01sMntStudContactDetailInput")
      checkInViewValue ((Srcds01sMntStudContactDetailInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srcds01sMntStudContactDetailInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srcds01h.Srcds01sMntStudContactDetailOutput")
      checkOutViewValue ((Srcds01sMntStudContactDetailOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srcds01sMntStudContactDetailOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srcds01sMntStudContactDetailInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srcds01sMntStudContactDetailOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
