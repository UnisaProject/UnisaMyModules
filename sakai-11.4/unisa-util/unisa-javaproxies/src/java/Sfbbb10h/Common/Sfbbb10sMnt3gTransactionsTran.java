package Sfbbb10h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Sfbbb10sMnt3gTransactionsTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Sfbbb10h.Servlet.Sfbbb10sMnt3gTransactionsServlet";
    public String getServletName()     {return ServletName; }
 
    public Sfbbb10sMnt3gTransactionsTran ()
   {
      super ();
      String myName = "Sfbbb10sMnt3gTransactions()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Sfbbb10sMnt3gTransactionsTran (serverDialog sysObj,
                                  Sfbbb10sMnt3gTransactionsInput viewIn,
                                  Sfbbb10sMnt3gTransactionsOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfbbb10sMnt3gTransactionsTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Sfbbb10sMnt3gTransactionsTran (serverDialog sysObj,
                                  Sfbbb10sMnt3gTransactionsInput viewIn,
                                  Sfbbb10sMnt3gTransactionsOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfbbb10sMnt3gTransactionsTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Sfbbb10sMnt3gTransactionsInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Sfbbb10sMnt3gTransactionsOutput x)
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
    if (e.getNewValue().getClass().getName() == "Sfbbb10h.Sfbbb10sMnt3gTransactionsInput")
      checkInViewValue ((Sfbbb10sMnt3gTransactionsInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Sfbbb10sMnt3gTransactionsInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Sfbbb10h.Sfbbb10sMnt3gTransactionsOutput")
      checkOutViewValue ((Sfbbb10sMnt3gTransactionsOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Sfbbb10sMnt3gTransactionsOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Sfbbb10sMnt3gTransactionsInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Sfbbb10sMnt3gTransactionsOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
