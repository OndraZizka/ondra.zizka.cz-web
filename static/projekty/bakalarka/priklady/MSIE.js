function MSIE(bDestroy){
  if(bDestroy){ this.oIE.Quit(); delete this.oIE; return; }
  if(!this.oIE) this.oIE = WScript.CreateObject("InternetExplorer.Application");
}

MSIE.init = function(bDestroy){
  if(bDestroy){ MSIE.oIE.Quit(); delete this.oIE; return; }
  if(!MSIE.oIE) MSIE.oIE = WScript.CreateObject("InternetExplorer.Application");
	MSIE.oIE.Visible = 0;
  MSIE.oIE.navigate("about:blank");
	while(MSIE.oIE.Busy){ WScript.Sleep(60); }
}

MSIE.prompt = function(sMessage, sValue){
  MSIE.init();
	if("string" != typeof sValue) sValue = "";
  return sInput = MSIE.oIE.Document.Script.prompt(sMessage, sValue);
}

MSIE.alert = function(s){
  MSIE.init();
  return sInput = MSIE.oIE.Document.Script.alert(s);
}

MSIE.alert(MSIE.prompt("MSIE prompt() & alert() test", "Default message"));