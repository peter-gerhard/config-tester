# config-tester

config-tester is a simple application that only prints its config.
https://github.com/peter-gerhard/config-tester/blob/master/src/main/scala/Main.scala

The build.sbt enables docker packaging using sbt similar to the way we do it in the backend
https://github.com/peter-gerhard/config-tester/blob/master/build.sbt#L20

To give layered configuration capabilities to the application it gets multiple config files from a config-map
https://github.com/peter-gerhard/config-tester/blob/master/config-tester-chart/templates/config-map.yaml#L6

The actual config files can be found here https://github.com/peter-gerhard/config-tester/tree/master/config-tester-chart/config
As you can see the `application.conf` file includes the `global.conf`

```
include "global"
```
Therefore we can fall back on values defined in `global.conf` or overwrite them in `application.conf`

The deployment mounts both files in the config directory.
https://github.com/peter-gerhard/config-tester/blob/master/config-tester-chart/templates/deployment.yaml#L32
https://github.com/peter-gerhard/config-tester/blob/master/config-tester-chart/templates/deployment.yaml#L53

The `application.conf` file is picked up automatically due to the way we specified the docker container in `build.sbt`

Additionally there is a secret defined in https://github.com/peter-gerhard/config-tester/blob/master/secret.yaml.
The secret needs to be present in the cluster before the application is deployed.
You can use `install.sh` to ensure this or just manually install the secret with `kubectl apply -f secret.sh`

The deployment defines an environment variable and get the value from a kubernetes secret.
https://github.com/peter-gerhard/config-tester/blob/master/config-tester-chart/templates/deployment.yaml#L26

The application picks up the secret via typesafe config.
https://github.com/peter-gerhard/config-tester/blob/master/config-tester-chart/config/global.conf#L6

This way the secret cannot accidentally get into any log output.

The application is installed with `helm install config-tester-chart`

If you check the output for the pod you should see

```
 a: application_a  # overwritten in application.conf
 b: application_b  # overwritten in application.conf
 c: global_c       # fallback value from global.conf
 secret: a1234     # value taken from env var which in turn was taken from kubernetes secret
```

