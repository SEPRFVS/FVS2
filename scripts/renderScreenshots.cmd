@echo off
echo %HOMEDRIVE%%HOMEPATH%
for /D %%i in ("%HOMEDRIVE%%HOMEPATH%\taxescreenshots\*") do echo %%i
for /D %%i in ("%HOMEDRIVE%%HOMEPATH%\taxescreenshots\*") do ffmpeg -i "%%i/screenshot%%d.png" -r 30 -vcodec mpeg4 %%i.mp4
pause