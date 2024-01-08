# Virtual threads issue

This project is linked to this [issue](https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/10181)

## HOW TO REPRODUCE

Run `docker compose up -d`  

The demo service is ran twice. The service running on the port 8081 has the `-Djdk.tracePinnedThreads=short` VM option 

To reproduce the issue, simply run :
`curl -X GET localhost:8081`  
The call hangs and makes the service unresponsive. You have to kill the container to make it stop

The same call `curl -X GET localhost:8080` to the service without the VM option works
