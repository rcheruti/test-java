@echo off

set "params= "
set "params=%params% -Xms12m -Xmx60m -XX:MaxMetaspaceSize=70m "
set "params=%params% -XX:MaxHeapFreeRatio=2 -XX:MinHeapFreeRatio=1 "
set "params=%params% -XX:+UnlockCommercialFeatures "
set "params=%params% -XX:+FlightRecorder "
rem -XX:StartFlightRecording=duration=10s,filename=myrecording.jfr 

start java %params% -jar .\target\blz-api.jar
