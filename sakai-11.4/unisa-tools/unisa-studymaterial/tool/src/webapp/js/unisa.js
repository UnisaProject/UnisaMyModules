var reqHttp;
var url;
var reqHttpstring;

function startup() {
        document.forms[0].subscriptionID.focus = true;
}

function getfilesize() {
    var string = getIframeID();
    reqHttpstring = document.getElementById("fileName").value;
        if (window.XMLHttpRequest) {
                reqHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
                reqHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
		alert("File sizes are currently unavailable.");
	}
        url = "/fileserver/cgi-bin/filesize.pl";
        reqHttp.open("POST", url, true);
        reqHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    reqHttp.onreadystatechange = subscriptionValidator;
   reqHttp.send(reqHttpstring);
    setMainFrameHeight(string);

}

function subscriptionValidator() {
         if (reqHttp.readyState == 4) {
                if (reqHttp.status == 200) {
                        var indexObj = reqHttp.responseXML.getElementsByTagName("index")[0];
                        var index = reqHttp.responseXML.getElementsByTagName("file").length;
                        for (var i=0; i<index; i++)
                        {
                                var fieldObj = reqHttp.responseXML.getElementsByTagName("file")[i];
                                var field=fieldObj.getAttribute("id");
                                size = fieldObj.childNodes[0].nodeValue;
                                if (i == 0){
                                        field =  "url-row::filesize";
                                }else{
                                        field = "url-row:"+i+":filesize";
                                }
                                document.getElementById(field).innerHTML=size;
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
