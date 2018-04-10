package Exfyq01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Exfyq01sMntFiYearStudConcTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Exfyq01h.Servlet.Exfyq01sMntFiYearStudConcServlet";
    public String getServletName()     {return ServletName; }
 
    public Exfyq01sMntFiYearStudConcTran ()
   {
      super ();
      String myName = "Exfyq01sMntFiYearStudConc()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Exfyq01sMntFiYearStudConcTran (serverDialog sysObj,
                                  Exfyq01sMntFiYearStudConcInput viewIn,
                                  Exfyq01sMntFiYearStudConcOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exfyq01sMntFiYearStudConcTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Exfyq01sMntFiYearStudConcTran (serverDialog sysObj,
                                  Exfyq01sMntFiYearStudConcInput viewIn,
                                  Exfyq01sMntFiYearStudConcOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Exfyq01sMntFiYearStudConcTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Exfyq01sMntFiYearStudConcInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Exfyq01sMntFiYearStudConcOutput x)
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
    if (e.getNewValue().getClass().getName() == "Exfyq01h.Exfyq01sMntFiYearStudConcInput")
      checkInViewValue ((Exfyq01sMntFiYearStudConcInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Exfyq01sMntFiYearStudConcInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Exfyq01h.Exfyq01sMntFiYearStudConcOutput")
      checkOutViewValue ((Exfyq01sMntFiYearStudConcOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Exfyq01sMntFiYearStudConcOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Exfyq01sMntFiYearStudConcInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Exfyq01sMntFiYearStudConcOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
