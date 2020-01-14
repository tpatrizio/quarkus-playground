# quarkus-playground project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `quarkus-playground-1.0.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-playground-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/quarkus-playground-1.0.0-SNAPSHOT-runner`

If your operating system is not compatible with the one used to build the native image it may not be possible to execute the binary directly.
In this case it is necessary to build a docker image: `docker build -f src/main/docker/Dockerfile.native -t quarkus-playground/getting-started .`
and run it in a container: `docker run -i --rm -p 8080:8080 quarkus-playground/getting-started`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .

## Deploying native executable to OpenShift

Set the OpenShift cluster environment running the command:

```
eval $(minishift oc-env --shell bash)
```

Login as developer:

```
oc login $(minishift ip):8443 -u developer -p developer
```

### Binary image deployment

Create a binary build configuration:

```
oc new-build --binary --name=quarkus-playground -l app=quarkus-playground
```

Patch the build configuration to use the Quarkust native dockerfile:

```
oc patch bc/quarkus-playground -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.native\"}}}}"
```

Start the build process:

```
oc start-build quarkus-playground --from-dir=. --follow
```

Instantiate the image:

```
oc new-app --image-stream=quarkus-playground:latest
```

Expose a route:

```
oc expose service quarkus-playground
```

Get the route URL and invoke the service:

```
export URL="http://$(oc get route | grep quarkus-playground | awk '{print $2}')"
echo "Application URL: $URL"
curl $URL/hello && echo
```

### Source to Image (S2I) deployment

Build the image on OpenShift:

oc new-app quay.io/quarkus/ubi-quarkus-native-s2i:19.2.1~https://github.com/tpatrizio/quarkus-playground.git --name=quarkus-playground-native
oc logs -f bc/quarkus-playground-native

Expose a route:

```
oc expose svc/quarkus-playground-native
```

Get the route URL and invoke the service:

```
export URL="http://$(oc get route | grep quarkus-playground-native | awk '{print $2}')"
echo "Application URL: $URL"
curl $URL/hello && echo
```

### Clean up

If you want to clean up the current deployment deleting all the related resources in terms of Pods, Services, Routes and Deployments, you can execute the following oc command:

```
oc delete all -l app=quarkus-playground
```
