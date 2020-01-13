var xmlreq;
var xmlurl;
var xmlreqstring;

function startup() {
        document.forms[0].subscriptionID.focus = true;
}

function init() {
		xmlreq=false;
        if (window.XMLHttpRequest) {
                xmlreq = new XMLHttpRequest();
                if (xmlreq.overrideMimeType) {
            		xmlreq.overrideMimeType('text/xml');
         		}
                
        } else if (window.ActiveXObject) {
                try {
            		xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
         		} catch (e) {
            		try {
               			xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            		} catch (e) {}
            	}
         }      
		if (!xmlreq) {
         	
      	}

		xmlurl = "/rcmclient/Default.aspx?coursecode="+xmlreqstring;
        xmlreq.open("GET", xmlurl, true);

}

function getexamfiles() {
    var string = getIframeID();
    xmlreqstring = document.getElementById("coursecode").value;
    init();
    
    xmlreq.onreadystatechange = exampaperValidator;
	xmlreq.setRequestHeader("Content-type", "application/xml");
        if (window.XMLHttpRequest) {
    		xmlreq.send(null);
        } else if (window.ActiveXObject) {
    		xmlreq.send();
	}
    setMainFrameHeight(string);

}

function exampaperValidator(){
        if (xmlreq.readyState == 4) {
                if (xmlreq.status == 200) {
                		var xmldoc = xmlreq.responseXML;
                       	var root = xmldoc.getElementsByTagName('exampapers')[0];
                        var indexer = xmlreq.responseXML.getElementsByTagName("exampaper").length;
                        var element = new Array();
                        element[0] = "papernumber";
                        element[1] = "period";
                        element[2] = "examyear";
                        element[3] = "filesize";
                        element[4] = "language";
                        element[5] = "coursecode";
                        element[6] = "link";
                    	var headers = "";
                        if(root.hasChildNodes()) { 
							document.getElementById('descr').style.visibility = "visible";
							document.getElementById('fsize').style.visibility = "visible";
							document.getElementById('heading').style.visibility = "visible";
							document.getElementById('space').style.visibility = "visible";
                        	for (var i=0; i<indexer; i++)
                        		{
                        			var fieldObj = xmlreq.responseXML.getElementsByTagName("exampaper")[i];                            	
                                	if (fieldObj != null && fieldObj.hasChildNodes()){
                                		var desc = "Examination Question Paper ";
                                		var link = "";
                                		var size = "";
                                		var lang = "";
                                		var langDesc = "";
                                		var bksize = "";
                                		var newRowHTML = "";
										for (var k=0 ; k < element.length; k++){
                                		//for (var k=0 ; k < fieldObj.childNodes.length; k++){
                                			var courseField = xmlreq.responseXML.getElementsByTagName(element[k])[i];
                                			if (k ==0 ){
                                				desc+=courseField.childNodes[0].nodeValue;
                                			} else if (k == 1){
                                				if (courseField.childNodes[0].nodeValue == 6){
                                					desc +=" May/June ";
                                				} else if (courseField.childNodes[0].nodeValue == 1){
                                					desc +=" January/February ";
                                				} else if (courseField.childNodes[0].nodeValue == 10){
                                					desc +=" October/November ";
                                				}                                			
                                			} else if (k == 2){
                                				desc += courseField.childNodes[0].nodeValue;
                                			} else if (k == 3){
                                				size = courseField.childNodes[0].nodeValue;
												bksize = parseInt(parseInt(size)/1024);
												
                                			} else if (k == 4){
                                				lang = courseField.childNodes[0].nodeValue;
                                				if(lang == "a" || lang == "A"){
                                					langDesc = "(Afr)";
                                				} else if(lang == "e" || lang == "E"){
                                					langDesc ="(Eng)";
                                				} else if (lang == "b" || lang == "B"){
                                					langDesc ="(Both)";
                                				}
                                				
                                			}
                                			else if (k == 6){
                                				link = courseField.childNodes[0].nodeValue;
                                			}
                                		}
										newRowHTML = "<tr><td><img src='/library/image/sakai/pdf.gif'/></td><td>"+link+desc+langDesc+"</a></td><td>"+bksize+"KB </td></tr>";
                                        if(window.ActiveXObject) { 
                                        	var et = document.getElementById("exampaperstable");                                           
											newRow = et.insertRow();
											newCell = newRow.insertCell();
											newCell.innerHTML = "<img src='/library/image/sakai/pdf.gif'/>";
											newCell = newRow.insertCell();
											newCell.innerHTML = link+desc+langDesc+"</a>";
											newCell = newRow.insertCell();
											newCell.innerHTML = bksize+" KB";
										} else {
                                            document.getElementById("exampaperstable").innerHTML += newRowHTML;
                                        }

                                	} 
                        		}
                        } else {
                        		document.getElementById('examWarning').style.visibility = "visible";
                        		document.getElementById('examWarning').style.display= "block";
                        }                        
                }
        }
}

function getIframeID()
{
    var myTop;
    if (window.frameElement) {
                    myTop = window.frameElement;
    } else if (window.top) {
            myTop = window.top;
            var myURL = location.href;
            var iFs = myTop.document.getElementsByTagName('iframe');
            var x, i = iFs.length;
            while ( i-- ){
                    x = iFs[i];
                    if (x.src && x.src == myURL){
                            myTop = x;
                            break;
                    }
            }
    }
    if (myTop){
            return myTop.id;
    } else {
            return 'Couldn\'t find the iframe';
    }
}
