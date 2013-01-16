
##  Resolve a softlink.
function resolve(){

  PRG="$1"

  if [ "" == "$1" ] ; then
    return ""
  fi

  if [ ! -x "$PRG" ] ; then
    PRGW=`which "$PRG"`
    if [ ! -x "$PRGW" ] ; then
      RET=""
      return -1
    fi
  fi


  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
      PRG="$link"
    else
      PRG=`dirname "$PRG"`/"$link"
    fi
  done

  RET=$PRG
  return 0
}

##  If called instead of sourced 
if [ "resolve" == "${0: -7}" ] ; then
  if [ "" == "$1" ] ; then
    echo "Usage:  resolve <softlink>";
    exit
  fi

  resolve $1
  if [ "" == "$RET" ] ; then
    echo "$PRG not found."
  else
    echo $RET;
  fi
fi
