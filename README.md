# Getting Started with Tanzu Community Edition on Docker

Tanzu Community Edition is a fully-featured, easy to manage, Kubernetes platform for learners and users.

- [GitHub Repository](https://github.com/vmware-tanzu/community-edition/) 

## Description

## Demo

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation
### 1. Download Tanzu CLI
Download the latest module from [GitHub](https://github.com/vmware-tanzu/community-edition/releases/)

```shell
$ curl -sSLO https://github.com/vmware-tanzu/community-edition/releases/download/v0.7.0-rc.4/tce-darwin-amd64-v0.7.0-rc.4.tar.gz
$ tar xzvf tce-darwin-amd64-*
$ rm tce-darwin-amd64-*.gz
```

Install Tanzu CLI
```shell
$ tce-darwin-amd64-*/install.sh
```

Confirmation
```shell
$ tanzu version

version: v1.4.0-pre-alpha-2
buildDate: 2021-08-18
sha: ab30672
```

#### Tanzu CLI
```shell
$ tanzu -h

Tanzu CLI

Usage:
  tanzu [command]

Available command groups:

  Admin
    builder                 Build Tanzu components

  Run
    cluster                 Kubernetes cluster operations
    conformance             Run Sonobuoy conformance tests against clusters
    kubernetes-release      Kubernetes release operations
    management-cluster      Kubernetes management cluster operations
    package                 Tanzu package management
    standalone-cluster      Create clusters without a dedicated management cluster

  System
    completion              Output shell completion code
    config                  Configuration for the CLI
    init                    Initialize the CLI
    login                   Login to the platform
    plugin                  Manage CLI plugins
    update                  Update the CLI
    version                 Version information
```

### 2. Prepare for Installation
Retrieve the Load Balancer (HA Proxy) from Docker Hub in advance to prevent from Docker Hub Rate limiting

Download the latest tag
- [kindest/haproxy](https://hub.docker.com/r/kindest/haproxy)

```shell
$ docker pull kindest/haproxy:v20211115-b0f54c86
```

### 3. Create Management Cluster
```shell
$ set -x MGMT_CLUSTER_NAME arthur
$ tanzu management-cluster create -i docker --name $MGMT_CLUSTER_NAME -v 10 --plan dev --ceip-participation=false
```

Confirmation
```shell
$ tanzu management-cluster get

  NAME    NAMESPACE   STATUS   CONTROLPLANE  WORKERS  KUBERNETES        ROLES
  arthur  tkg-system  running  1/1           1/1      v1.21.2+vmware.1  management


Details:

NAME                                                       READY  SEVERITY  REASON  SINCE  MESSAGE
/arthur                                                    True                     10m
├─ClusterInfrastructure - DockerCluster/arthur             True                     10m
├─ControlPlane - KubeadmControlPlane/arthur-control-plane  True                     10m
│ └─Machine/arthur-control-plane-gt88x                     True                     10m
└─Workers
  └─MachineDeployment/arthur-md-0
    └─Machine/arthur-md-0-74b864579-d976m                  True                     10m


Providers:

  NAMESPACE                          NAME                   TYPE                    PROVIDERNAME  VERSION  WATCHNAMESPACE
  capd-system                        infrastructure-docker  InfrastructureProvider  docker        v0.3.23
  capi-kubeadm-bootstrap-system      bootstrap-kubeadm      BootstrapProvider       kubeadm       v0.3.23
  capi-kubeadm-control-plane-system  control-plane-kubeadm  ControlPlaneProvider    kubeadm       v0.3.23
  capi-system                        cluster-api            CoreProvider            cluster-api   v0.3.23
```

## References

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
- twitter: https://twitter.com/yanashin18618
