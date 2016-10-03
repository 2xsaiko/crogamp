which java > /dev/null 2> /dev/null || ( printf "Please install the Java Runtime Environment to use this program.\n" && exit 0 )
cd "$(dirname $0)"
test -f .crogamp.jar && rm .crogamp.jar
unpack_crogamp.jar .crogamp.jar
java -jar .crogamp.jar
rm .crogamp.jar
