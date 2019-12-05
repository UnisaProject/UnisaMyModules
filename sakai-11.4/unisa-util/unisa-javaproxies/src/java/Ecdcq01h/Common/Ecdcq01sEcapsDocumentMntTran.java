package Ecdcq01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Ecdcq01sEcapsDocumentMntTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Ecdcq01h.Servlet.Ecdcq01sEcapsDocumentMntServlet";
    public String getServletName()     {return ServletName; }
 
    public Ecdcq01sEcapsDocumentMntTran ()
   {
      super ();
      String myName = "Ecdcq01sEcapsDocumentMnt()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Ecdcq01sEcapsDocumentMntTran (serverDialog sysObj,
                                  Ecdcq01sEcapsDocumentMntInput viewIn,
                                  Ecdcq01sEcapsDocumentMntOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Ecdcq01sEcapsDocumentMntTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Ecdcq01sEcapsDocumentMntTran (serverDialog sysObj,
                                  Ecdcq01sEcapsDocumentMntInput viewIn,
                                  Ecdcq01sEcapsDocumentMntOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Ecdcq01sEcapsDocumentMntTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Ecdcq01sEcapsDocumentMntInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Ecdcq01sEcapsDocumentMntOutput x)
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
    if (e.getNewValue().getClass().getName() == "Ecdcq01h.Ecdcq01sEcapsDocumentMntInput")
      checkInViewValue ((Ecdcq01sEcapsDocumentMntInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Ecdcq01sEcapsDocumentMntInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Ecdcq01h.Ecdcq01sEcapsDocumentMntOutput")
      checkOutViewValue ((Ecdcq01sEcapsDocumentMntOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Ecdcq01sEcapsDocumentMntOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Ecdcq01sEcapsDocumentMntInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Ecdcq01sEcapsDocumentMntOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
