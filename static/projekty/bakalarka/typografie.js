/************************************************************************
*  Typografick� pravidla                                                *
*                                                                       *
*  Aplikuje n�kter� typografick� pravidla na dokument.                  *
*                                                                       *
*  Funkce SvazPredlozky() sv�e jednoslabi�n� p�edlo�ky s n�sleduj�c�m  *
*  slovem ned�litelnou mezerou (\160).                                  *
************************************************************************/

function cTypografie(){
	var sa, as;
	this.sNbsp = String.fromCharCode(160);
	
	// --- Vytvo��me regul�rn� v�raz pro p�edlo�ky --- //
	sa = "a ale a� aby ani a� bez do i je jsem jsou ji� jen k ke na nad ne� o od ob p�ed po pod pro p�i"
	   + " s se si u u� v viz ve z ze za �e ISBN kdy kde kdo co jak tzv.";
	sa += " and are as at but for if in of the with"; // anglicke; vynechavam "to on" - touklo by se s cestinou 
	// Eskejpujeme specialni znaky regularnich vyrazu 
	sa = sa.replace(/\./g, "\\.");
	as = sa.split(" ");
	this.rePredlozky = new RegExp("\\b(" + as.join("|") + ") ", "gi");
	this.rePredlozky.substitute = "$1" + this.sNbsp;
	
	// --- R.E. pro zalozky --- //
	sa = "Ph.D. Inc. a.s. as. s.r.o. sro.";
	// Eskejpujeme specialni znaky regularnich vyrazu 
	sa = sa.replace(/\./g, "\\.");
	as = sa.split(" ");
	this.reZalozky = new RegExp(" (" + as.join("|") + ")", "g");
	this.reZalozky.substitute = this.sNbsp + "$1";

	// --- R.E. pro dvouslovn� fr�ze --- //
	sa = "Miner JS|Miner VGA";
	sa.replace(/\./g, "\\.");
	as = sa.split("|");
	//for(var i in as){ as[i] = "(?=" + as[i].split(" ").join(")( )(?=") + ")"; }
	//alert("\\b(" + as.join("|") + ")\\b");
	//this.reFraze = new RegExp("\\b(" + as.join("|") + ")\\b", "g");
	this.aaFraze = new Array();
	// Pole [ [vzor, nahrada], ... ]
	for(var i in as){ this.aaFraze.push( new Array(new RegExp(as[i], "g"), as[i].split(" ").join(this.sNbsp)) ); }
	
	// --- R.E. pro verze - Neco 2.0 --- //
	this.reVerze = new RegExp("([A-Z̊�؎���ɍ�����]\\w) (\\d+\\.\\d+)", "g");
	this.reVerze.substitute = "$1" + this.sNbsp + "$2";
	
	// --- R.E. pro inici�ly - X. Yz --- //
	this.reInicialy = new RegExp("([A-Z̊�؎���ɍ����]\\.) ([A-Z̊�؎���ɍ����]\\w+)", "g");
	this.reInicialy.substitute = "$1" + this.sNbsp + "$2";
	
	// --- R.E. pro roky - "(abc|r.) 1997(.|,|;| )" --- //
	//this.reRoky = new RegExp("(\\w+|r\\.) (\\d\\d\\d\\d\\W)", "g");
	this.reRoky = new RegExp("(\\w+|\\b\\w{1,3}\\.) (\\d\\d\\d\\d\\W)", "g");
	this.reRoky.substitute = "$1" + this.sNbsp + "$2";
	
	// --- R.E. pro �adov� ��slovky - "25. narozeniny" --- //
	this.reRadovky = new RegExp("\\b(\\d+\\.) ([a-z������������])", "g");
	this.reRadovky.substitute = "$1" + this.sNbsp + "$2";
}

cTypografie.prototype.SvazVsechno = function(oTextNode){
	this.SvazPredlozky(oTextNode);
	this.SvazZalozky(oTextNode);
	this.SvazFraze(oTextNode);
	this.SvazVerze(oTextNode);
	this.SvazInicialy(oTextNode);
	this.SvazRoky(oTextNode);
	this.SvazRadovky(oTextNode);
}

// P�edlo�ky - A�_nav�ky, o_n�s, bez_n�s, ...
cTypografie.prototype.SvazPredlozky = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.rePredlozky, this.rePredlozky.substitute);
}

// Z�lo�ky - Pan X Y,_Ph.D.
cTypografie.prototype.SvazZalozky = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reZalozky, this.reZalozky.substitute);
}

// Fr�ze - "Miner JS", "Miner VGA", atd.
cTypografie.prototype.SvazFraze = function(oTextNode){
	//oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reFraze, this.reFraze.substitute);
	var s = oTextNode.nodeValue;
	for(var i in this.aaFraze){
		s = s.replace(this.aaFraze[i][0], this.aaFraze[i][1]);
	}
	oTextNode.nodeValue = s;
}

// Verze - Neco 2.0 
cTypografie.prototype.SvazVerze = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reVerze, this.reVerze.substitute);
}

// Inici�ly - X. Yz 
cTypografie.prototype.SvazInicialy = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reInicialy, this.reInicialy.substitute);
}

// Roky - Neco 1997 
cTypografie.prototype.SvazRoky = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reRoky, this.reRoky.substitute);
}

// �adov� ��slovky - "25. narozeniny"
cTypografie.prototype.SvazRadovky = function(oTextNode){
	oTextNode.nodeValue = oTextNode.nodeValue.replace(this.reRadovky, this.reRadovky.substitute);
}




// AplikujTypografiiNaStrom(doc, oTypografie) 
function AplikujTypografiiNaStrom(doc, oTypografie){
	if(1 && doc.implementation.hasFeature("Traversal","2.0")){
		// Je k dispozici rozhrani DOM Traversal //
		var callbackFilterFunc = function(oNode){
			return oNode.nodeName != "pre" && oNode.nodeName != "code" && oNode.nodeType == 3 ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_SKIP;
		}
		var noteIter = doc.createNodeIterator(doc, NodeFilter.SHOW_ELEMENT, callbackFilterFunc, false);
		var oNodeIter; while( oNodeIter = noteIter.nextNode() ){
			oTypografie.SvazVsechno(oNodeIter);
		}
	}else{
		// Neni k dispozici rozhrani DOM Traversal //
		var fFunc = function(oNode){
			// P�esko��me uzly k�du 
			if(oNode.nodeName == "pre" || oNode.nodeName == "code") return;
			
			// Pokud ma tento element ID, pridame do seznamu. //
			if(3 == (oNode.nodeType||oNode.type)){
				oTypografie.SvazVsechno(oNode);
			}
			// Projedem deti touto funkci.           //
			var aChildren = (oNode.childNodes||oNode.children);
			for(var i in aChildren){
				arguments.callee(aChildren[i]);
			}
		}
		// Zavolame rekurzivni fci na dokument v argumentu doc //
		fFunc(doc);
	}
}

/*** Usage:

	var oTypografie = new cTypografie();
	if(!window.bTypografieAplikovana){
		AplikujTypografiiNaStrom(document, oTypografie);
		window.bTypografieAplikovana = true;
	}
	
*/