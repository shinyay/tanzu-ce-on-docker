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

### 4. Configure Management Cluster
Configure kubeconfig for Management Cluster
```shell
$ tanzu management-cluster kubeconfig get $MGMT_CLUSTER_NAME --admin
```

Set kubectl context to Management Cluster
```shell
$ kubectl config use-context $MGMT_CLUSTER_NAME-admin@$MGMT_CLUSTER_NAME
```

Node
```shell
$ kubectl get nodes -o wide

NAME                          STATUS   ROLES                  AGE   VERSION                               INTERNAL-IP   EXTERNAL-IP   OS-IMAGE           KERNEL-VERSION     CONTAINER-RUNTIME
arthur-control-plane-gt88x    Ready    control-plane,master   32m   v1.21.2+vmware.1-360497810732255795   172.18.0.4    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
arthur-md-0-74b864579-d976m   Ready    <none>                 31m   v1.21.2+vmware.1-360497810732255795   172.18.0.5    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
```

Default Pods
```shell
$ kubectl get pods -A

NAMESPACE                           NAME                                                             READY   STATUS    RESTARTS   AGE
capd-system                         capd-controller-manager-685f4d6876-wpgg5                         2/2     Running   0          26m
capi-kubeadm-bootstrap-system       capi-kubeadm-bootstrap-controller-manager-84c75dd587-hnp5c       2/2     Running   0          26m
capi-kubeadm-control-plane-system   capi-kubeadm-control-plane-controller-manager-756f646c68-l2wg4   2/2     Running   0          26m
capi-system                         capi-controller-manager-5468bf8995-9vt6f                         2/2     Running   0          26m
capi-webhook-system                 capi-controller-manager-b6f878dd8-hp7j6                          2/2     Running   0          26m
capi-webhook-system                 capi-kubeadm-bootstrap-controller-manager-67cf557cc6-xc7ft       2/2     Running   0          26m
capi-webhook-system                 capi-kubeadm-control-plane-controller-manager-798bb98b65-h768q   2/2     Running   0          26m
cert-manager                        cert-manager-cainjector-59d5cd55f5-ljxwd                         1/1     Running   0          32m
cert-manager                        cert-manager-fcbbdd748-l7vhh                                     1/1     Running   0          32m
cert-manager                        cert-manager-webhook-5cd7cf5fbb-wp8ml                            1/1     Running   0          32m
kube-system                         antrea-agent-mw2zl                                               2/2     Running   0          14m
kube-system                         antrea-agent-r8t2n                                               2/2     Running   0          14m
kube-system                         antrea-controller-549695d488-9qzdp                               1/1     Running   0          14m
kube-system                         coredns-8dcb5c56b-bb75h                                          1/1     Running   0          32m
kube-system                         coredns-8dcb5c56b-jz5t4                                          1/1     Running   0          32m
kube-system                         etcd-arthur-control-plane-gt88x                                  1/1     Running   0          33m
kube-system                         kube-apiserver-arthur-control-plane-gt88x                        1/1     Running   0          33m
kube-system                         kube-controller-manager-arthur-control-plane-gt88x               1/1     Running   0          33m
kube-system                         kube-proxy-84fpf                                                 1/1     Running   0          32m
kube-system                         kube-proxy-lqqtg                                                 1/1     Running   0          32m
kube-system                         kube-scheduler-arthur-control-plane-gt88x                        1/1     Running   0          33m
kube-system                         metrics-server-c5fdd7488-r5qwb                                   1/1     Running   0          14m
tkg-system                          kapp-controller-764fc6c69f-zf9w2                                 1/1     Running   0          31m
tkg-system                          tanzu-addons-controller-manager-65dc965d99-hh96m                 1/1     Running   0          27m
tkg-system                          tanzu-capabilities-controller-manager-69f58566d9-4tw9v           1/1     Running   0          32m
tkr-system                          tkr-controller-manager-cc88b6968-mmx7f                           1/1     Running   1          32m
```

### 5. Create Workload Cluster
```shell
$ set -x WORKLOAD_CLUSTER_NAME lancelot
$ tanzu cluster create $WORKLOAD_CLUSTER_NAME --plan dev
```

Confirmation
```shell
$ tanzu cluster list --include-management-cluster

  NAME      NAMESPACE   STATUS   CONTROLPLANE  WORKERS  KUBERNETES        ROLES       PLAN
  lancelot  default     running  1/1           1/1      v1.21.2+vmware.1  <none>      dev
  arthur    tkg-system  running  1/1           1/1      v1.21.2+vmware.1  management  dev
```



## References

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
- twitter: https://twitter.com/yanashin18618
