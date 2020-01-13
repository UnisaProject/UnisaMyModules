var xmlreq = null;
var xmlurl; 
function loadeXMLDoc()
{
   xmlreq = false;
   if (window.XMLHTTPRequest && !(window.ActiveXObject)){
      try{
          xmlreq = new XMLHttpRequest();
      }
      catch(e) {
            xmlreq = false;
      }
    else if(window.ActiveXObject){
           try{
                xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
           }
           catch(e){
                try{
                     xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
                 }
                 catch(e){
                      xmlreq = false;
                 }
           }
     }
   
     if(xmlreq){
           xmlreq.onreadystatechange = examValidator;
           xmlurl = "/myfile.xml";
           xmlreq.open("GET",xmlurl, true);
           xmlreq.setRequestHeader("Content-Type","application/html");
           xmlreq.send("");
     }
   }
}

function examValidator()
{
          if (xmlreq.readyState == 4){
            alert("STATUS OK");
               if(xmlreq.status == 200)
               {
                   var ele = document.getElementById('coursecode');
                   var xmlres = xmlreq.responseXML.getElementsByTagName('coursecode')[0];
                   for( var i = 0; i<xmlres.length; i++){
                        var vals = xmlres.item(i);
                        var xamyer = vals.getElementsByTagName('examyear');
                        var xam = xamyer.item(0).firstChild.nodeValue;
                        var xamper = vals.getElementsByTagName('period');
                        var per = xamper.item(0).firstChild.nodeValue;
                        var xampap = vals.getElementsByTagName('papernumber');
                        var num = xampap.item(0).firstChild.nodeValue;
                        var xamlang = vals.getElementsByTagName('language');
                        var lang = xamlang.item(0).firstChild.nodeValue;
                        var xamfile = vals.getElementsByTagName('filesize');
                        var fil = xamfile.item(0).firstChild.nodeValue;
                        document.getElementById(files).innerHTML="Examination Question Paper "+num+" "+valor+" "+xam+" "+"(" +lang+ ")";
                        document.getElementById(docsize).innerHTML=fil+" KB";
                   }
               }
               else{
                   alert("There was a problem retrieving the XML data:\n"+xmlreq.statusText);
               } 
       }
}

        

