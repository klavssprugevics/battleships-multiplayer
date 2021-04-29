cd ..
cd src
dir /s /b *.java >sources.txt
javac @sources.txt
java client.Client
pause