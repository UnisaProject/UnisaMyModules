package Menu95h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Menu95STran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Menu95H.Servlet.Menu95SServlet";
    public String getServletName()     {return ServletName; }
 
    public Menu95STran ()
   {
      super ();
      String myName = "Menu95S()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Menu95STran (serverDialog sysObj,
                                  Menu95SInput viewIn,
                                  Menu95SOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Menu95STran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Menu95STran (serverDialog sysObj,
                                  Menu95SInput viewIn,
                                  Menu95SOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Menu95STran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Menu95SInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Menu95SOutput x)
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
    if (e.getNewValue().getClass().getName() == "Menu95H.Menu95SInput")
      checkInViewValue ((Menu95SInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Menu95SInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Menu95H.Menu95SOutput")
      checkOutViewValue ((Menu95SOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Menu95SOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Menu95SInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Menu95SOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
