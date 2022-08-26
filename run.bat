::@echo off
javac Game.java GameObject.java Handler.java ID.java KeyInput.java Player.java Window.java 
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)