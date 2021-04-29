cd ..
cd src
dir /b /s *.java >sources.txt
javac @sources.txt
java server.Server 8989
pause