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



function cSemafor(sId, iInitValue){
  this.sId = sId;
  this.sKey = "HKEY_CURRENT_USER\\Software\\OndraZizka\\WshProcesy\\";
  this.WshShell = WScript.CreateObject("WScript.Shell");
  this.WshShell.RegWrite(this.sKey + sId, iInitValue, "REG_DWORD");
}
cSemafor.prototype.Set = function(iValue){
  try{ this.WshShell.RegWrite(this.sKey + this.sId, iValue, "REG_DWORD"); }
  catch(e){ return false; } return true;
}
cSemafor.prototype.Get = function(){
  var xVal;
  try{ xVal = this.WshShell.RegRead(this.sKey + this.sId); }
  catch(e){ return false; } return xVal;
}

function cDataStore(sFileName){
  // Create FileSystemObject object to access file system.
  var fso = WScript.CreateObject("Scripting.FileSystemObject");
  if(!fso.FileExists(sFileName)){ return; }
  var fp = fso.GetFile(sFileName);      // Get file handle object.
  this.inp  = fso.OpenTextFile(sFileName, 1);
  this.out = fso.OpenTextFile(sFileName, 2, true);
}
cDataStore.prototype.Destroy = function(){
  if(this.inp){ this.inp.Close(); this.inp  = null; }
  if(this.out){ this.out.Close(); this.out = null; }
}




// Prvni proces - spustit v jednom WSH skriptu 
function ProcesPriprava(){
  var bPokracuj = true;
  while(bPokracuj){
    // Poèkáme, dokud nám druhý proces nesdìlí, že si data vybral.
    while(1 == oSemafor.Get()) WScript.Sleep(100);
    
    var sData = MSIE.prompt();
    if(sData == null){ bPokracuj = false; oSemafor.Set(2); break; }
    oDataStore.out.Write(sData);    
    
    // Sdìlíme druhému procesu, že jsou data pøipravena
    oSemafor.Set(1);
  }
}

// Druhy proces - spustit v jinem WSH skriptu 
function ProcesCteni(){
  var bPokracuj = true;
  while(bPokracuj){
    // Poèkáme, dokud nám druhý proces nesdìlí, že data pøipravil.
    while(0 == oSemafor.Get()) WScript.Sleep(100);
    if(2 == oSemafor.Get()){ bPokracuj = false; break; }
    
    var sData = oDataStore.inp.ReadAll();
    // ... zpracovani dat 
    
    // Sdìlíme druhému procesu, že byla vybrána
    oSemafor.Set(0);
  }
}


var oSemafor;
var oDataStore;

function OnLoad(){
  if(WScript.Arguments.length > 0){
    var c = WScript.Arguments[0].charAt(0).toLowerCase();
    var bCreate = false;
    if(c == "r" || c == "w"){
      WScript.oSemafor   = new cSemafor("semafor1", 0);
       WScript.oDataStore = new cDataStore("WSH_DataStore.txt");
    }
    switch(c){
      case "r": ProcesCteni();    break;  // read
      case "w": ProcesPriprava(); break;  // write
    }
  }
  else{
    MSIE.alert("Pouziti:\n\tProcesy w\nnebo\n\tProcesy r");
  }
}

//OnLoad.apply(OnLoad, WScript.Arguments);
//OnLoad.apply(null, Array.prototype.slice.apply(WScript.Arguments, [1]));
//MSIE.alert(OnLoad.apply);
//MSIE.alert(Array.prototype.slice.apply(WScript.Arguments, [1]));
var ArgumentsArray = new Array();
for(var i = 0; i < WScript.Arguments.length; i++) args.push(WScript.Arguments[i]);
OnLoad.apply(null, ArgumentsArray);