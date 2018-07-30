function Prompt(bDestroy){
  if(!this.oIE) this.oIE = WScript.CreateObject("InternetExplorer.Application");
  this.oIE.Visible = 0;
  this.oIE.navigate("about:blank");
  while(this.oIE.Busy){ WScript.Sleep(60); }
  var sInput = this.oIE.Document.Script.prompt("Ahoj", "Lidi");
  if(bDestroy){ this.oIE.Quit(); delete this.oIE; }
}
Prompt();