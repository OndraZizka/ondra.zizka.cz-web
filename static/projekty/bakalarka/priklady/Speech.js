// Pøedvedení Microsoft Speech
var vt = WScript.CreateObject("Speech.VoiceText");
vt.Register("", WScript.ScriptName);
var sText = "Hello everybody, this is a demonstration of the power of JavaScript. Play Miner JS, it's a cool game.";
sText += "Zdraaaavee vaas - Undraa Shishkaa.";
if(WScript.Arguments.length)
	sText = WScript.Arguments(0);
vt.Speak(sText, 1);
while(vt.IsSpeaking) WScript.Sleep(100);
WScript.Quit();