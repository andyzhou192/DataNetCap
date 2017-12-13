#/bin/sh
echo "************************************************************"
echo "*                                                          *"
echo "*                    DataNetCap startup                    *"
echo "*                                                          *"
echo "************************************************************"

SOURCE="$0"
while [ -h "$SOURCE"  ]; do # resolve $SOURCE until the file is no longer a symlink
    DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
    SOURCE="$(readlink "$SOURCE")"
    [[ $SOURCE != /*  ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
echo ${DIR}

if [ -z $JAVA_HOME ];then
    echo "java is not exists"  
else
    echo "JAVA_HOME = $JAVA_HOME"  
fi

JAVA_VERSION=`java -version 2>&1 |awk 'NR==1{ gsub(/"/,""); print $3 }'`
if [ -z $JAVA_VERSION ];
then echo ERROR: java is not installed; exit 11;
#elif [ '1.7' -lt= $JAVA_VERSION ];then
#echo "OK"
#else
#echo "ERROR: java version can't be less than 1.7";exit 12;
fi

DNAME=$(dirname "$PWD")
echo $DNAME
export ProDir=$DNAME
export JAR_HOME=$DNAME/lib
JARS=$(ls ${JAR_HOME}/*.jar)
for f in $JARS
do
CLASSPATH=$CLASSPATH:$f
done 
export CLASSPATH
echo $CLASSPATH

java -classpath $CLASSPATH com.Main
