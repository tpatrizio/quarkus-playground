## Setup a local OpenShift cluster

Create and configure a minishift profile with enough resources to execute native builds and run a Kafka cluster managed by Strimzi operator

```
minishift profile set playground
minishift config set memory 8GB
minishift config set cpus 4
minishift config set vm-driver hyperv
minishift config set hyperv-virtual-switch "External Network"
minishift config set image-caching true
minishift addon enable admin-user
minishift addon enable anyuid
minishift config set openshift-version v3.11.0
```

Start the OpenShift cluster: `minishift start`



