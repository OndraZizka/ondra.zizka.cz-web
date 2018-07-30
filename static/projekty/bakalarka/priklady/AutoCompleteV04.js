/*
	Autocomplete script v0.4
	Earle Castledine
	earle@notproducts.com
	Last updated: 20Feb2004

	ABOUT
		This script completes the word, tag name, variable name etc. that you have started
		to type before running the script. It works by searching the active document for matches
		and writing it out. It also writes all the possible matches in the status bar.
		
		Heres an example: 
		* Your document contains: "Vegetarians eat vegetables and Vegemite."
		* You then type "v" and run the script (via keyboard shortcut is best :)
		* The "v" automagically becomes "vege" and shows "Vegetarians, vegetables,
		  Vegemite" in the status bar.
		* Add an "m" to the "vege" and run it again. It becomes "Vegemite".
		
		I have this set up to be activated by "ctrl-`" which is pretty easy to hit.
		
		Vegemite puts a rose in every cheek.

	BUGS
	- Undo after script shows the intermediate steps (when non-alpha in input or inserted space).

	FIXED since v0.3
	- Tony Fenleish fixed a bug that displayed the first character of word twice if the script was 
	  run on one letter, at the start of line.

	FIXED since v0.2
	- Temporarily turn off "bing" when showing status bar messages.
	- Fixed bad match when characters follow cursor.
	- Added case sensitive searching.
	- Put script in Begin/EndUpdate - fixed flicker.
	
	Copyright 2004 Earle Castledine.	
*/

//Settings:
var sCaseSensitive=false;	//Set this to "true" if you want case sensitivity in searching
var sMatchDollar=false;		//Set this true to match $ as part of word. Good for php variables.

//Globals:
var gWordArray=new Array(); 	//bad global array...
var gApp=Application;			//shortcuts for homesite objects
var gActive=gApp.ActiveDocument;

//Regexpressions
var reAlpha 		= sMatchDollar? /[a-z0-9_\$]/i : 	/[a-z0-9_]/i;
var reNoAlpha		= sMatchDollar? /[^a-z0-9_\$]/gi : 	/[^a-z0-9_]/gi;
var reNoAlphaOrEOI	= sMatchDollar? /[^a-z0-9_\$]|$/i: 	/[^a-z0-9_\$]|$/i

//===============
function Main(){
//===============
	gActive.BeginUpdate();
	WarningBeep=Application.GetApplicationSetting(44); //Grab current warning bell value
	Application.SetApplicationSetting(44,0);		   //Turn it off. Dont need "bing"	

	var StartCursorPos = gActive.SelStart; 		//Get initial cursor position
	var InsertedSpace = InsertSpaceIfCharactersFollowCursor();
	var WordFragment = GetInputWordFragment(); 	//Get the characters that have been typed so far
	if((NumberOfMatches = FindWordMatches(WordFragment)) > 0){	//Search for matches
		if(NumberOfMatches > 1)gApp.StatusWarning(gWordArray);	//Several matches - show in status bar
		if(gActive.SelText.length == 0){ //If there's only one character, fix the SelStart and SelLength
			gActive.SelStart -= 1;		 //Thanks Tony!
			gActive.SelLength += 1;
		}
		gActive.SelText = GetSmallestCommonWord(WordFragment); 	//Write out appropriate word
	} 
	else {
		gActive.SelStart = StartCursorPos;		//No matches - return cursor to start
		if(WordFragment.length>0)gApp.StatusWarning("no match for " + WordFragment); //tell em the news
	}
	if(InsertedSpace)gApp.ExecCommand(45, 0); 	//Inserted a space to stop bad match - now delete it

	Application.SetApplicationSetting(44,WarningBeep);	//Restore the "bing" to original value
	gActive.EndUpdate();
}

//========================================
function InsertSpaceIfCharactersFollowCursor(){
//========================================
	/* This stops bad matches if script is run with characters immediately following cursor. */
	if(gActive.GetCurrentChar().search(reAlpha)!=-1){	//characters follow..
		gActive.InsertText(" ", false);					//so insert a space
		gActive.CursorLeft(false);
		return true;
	} 
	else 
		return false;
}

//===============================
function GetInputWordFragment(){
//===============================
	/* Make sure input contains only AlphaNumeric characters. */
 	gActive.CursorWordLeft(true); //select current word (plus non-alpha.)
	var WordFragment 		= gActive.SelText;
	var NonAlphaCharArray 	= WordFragment.match(reNoAlpha);
	if(NonAlphaCharArray!=null){ //word contains non-AlphaNum - just get the word...
		NonAlphaLength 	= WordFragment.length;
		WordFragment 	= WordFragment.substring(WordFragment.lastIndexOf(NonAlphaCharArray[NonAlphaCharArray.length-1])+1); 
		for(i=0;i<NonAlphaLength-WordFragment.length;i++)
			gActive.CursorRight(true); //adjust the current selection
	}
	return (WordFragment.length==0)?"":WordFragment;
}

//=======================================
function FindWordMatches(pWordFragment){
//=======================================
	/* Search for matches, and stickem in the array... */
	if(pWordFragment=="")return 0;//If no fragement, exit
	var MaxWordLength=50;//Set so we dont need to grab whole text - can be real high if you want...
	var curCharPos=0, foundMatch=true;
	while(foundMatch){
		var MatchWordStart = !sCaseSensitive?
			gActive.Text.toLowerCase().indexOf(pWordFragment.toLowerCase(), curCharPos):
			gActive.Text.indexOf(pWordFragment, curCharPos);//find the start of a match
		if(MatchWordStart!=-1){
			if(gActive.Text.charAt(MatchWordStart-1).search(reAlpha)==-1){//is it the start of a word?
				TextFromMatch = gActive.Text.substring(MatchWordStart,MatchWordStart+MaxWordLength);//Read following chars
				MatchedWord = TextFromMatch.substring(0, TextFromMatch.search(reNoAlphaOrEOI));//Find end of word
				AddToWordArray(MatchedWord, pWordFragment);//Add word to our global array
			}
			curCharPos = MatchWordStart + 1;//go to the next character in the text after match
		}
		else foundMatch=false;//No more matches.
	}
	return gWordArray.length;
}

//===============================================
function AddToWordArray(pWord, pInitFragment){
//===============================================
	var AlreadyInArray=false;
	for(i=0;i<=gWordArray.length;i++){
		var arrayWord=sCaseSensitive?gWordArray[i]:(gWordArray[i]==null)?"":gWordArray[i].toLowerCase();
		var newWord=sCaseSensitive?pWord:pWord.toLowerCase();
		if(arrayWord==newWord){
			AlreadyInArray=true;
			break;
		}
	}
	if(!AlreadyInArray&&pWord!=pInitFragment&&pWord!="")
		gWordArray[gWordArray.length]=pWord;
}

//==============================================
function GetSmallestCommonWord(pInitFragment){
//==============================================
	if(gWordArray.length==1)return gWordArray[0];//Only 1 match. Return it.
	var nextLetter, AllTheSameLetter = true, FullWordMatch = "";
	var ReturnWord = pInitFragment;
	while(AllTheSameLetter){//find smallest common subset of characters
		nextLetter=gWordArray[0].charAt(ReturnWord.length);
		if(nextLetter==""){FullWordMatch=gWordArray[0];break}//matched full word.
		for(i=1;i<=gWordArray.length-1;i++){
			csArrayChar	= sCaseSensitive?gWordArray[i].charAt(ReturnWord.length):
										 gWordArray[i].charAt(ReturnWord.length).toLowerCase();
			csNextLetter= sCaseSensitive?nextLetter:nextLetter.toLowerCase();
			if(csArrayChar!=csNextLetter){
				if(csArrayChar=="")FullWordMatch=gWordArray[i];
				AllTheSameLetter = false;
				break;
			}
		}
		if(AllTheSameLetter)		//All letters the same?
			ReturnWord+=nextLetter; //yep, move to the next letter of each word.
	}
	return FullWordMatch==""?ReturnWord:FullWordMatch;
}