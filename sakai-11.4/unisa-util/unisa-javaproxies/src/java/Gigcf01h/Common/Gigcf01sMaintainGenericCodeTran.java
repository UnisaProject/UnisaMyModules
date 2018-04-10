package Gigcf01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Gigcf01sMaintainGenericCodeTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Gigcf01h.Servlet.Gigcf01sMaintainGenericCodeServlet";
    public String getServletName()     {return ServletName; }
 
    public Gigcf01sMaintainGenericCodeTran ()
   {
      super ();
      String myName = "Gigcf01sMaintainGenericCode()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Gigcf01sMaintainGenericCodeTran (serverDialog sysObj,
                                  Gigcf01sMaintainGenericCodeInput viewIn,
                                  Gigcf01sMaintainGenericCodeOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Gigcf01sMaintainGenericCodeTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Gigcf01sMaintainGenericCodeTran (serverDialog sysObj,
                                  Gigcf01sMaintainGenericCodeInput viewIn,
                                  Gigcf01sMaintainGenericCodeOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Gigcf01sMaintainGenericCodeTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Gigcf01sMaintainGenericCodeInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Gigcf01sMaintainGenericCodeOutput x)
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
    if (e.getNewValue().getClass().getName() == "Gigcf01h.Gigcf01sMaintainGenericCodeInput")
      checkInViewValue ((Gigcf01sMaintainGenericCodeInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Gigcf01sMaintainGenericCodeInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Gigcf01h.Gigcf01sMaintainGenericCodeOutput")
      checkOutViewValue ((Gigcf01sMaintainGenericCodeOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Gigcf01sMaintainGenericCodeOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Gigcf01sMaintainGenericCodeInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Gigcf01sMaintainGenericCodeOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
