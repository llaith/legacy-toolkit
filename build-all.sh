#! /bin/sh

function pause(){
	read -s -n 1 -p "Press any key to continue . . ."
	echo
}

set -e

echo "Build-all starting"

mvn clean source:jar install

echo "All Done"
