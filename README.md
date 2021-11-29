# Getting Started with Tanzu Community Edition on Docker

Tanzu Community Edition is a fully-featured, easy to manage, Kubernetes platform for learners and users.

- [GitHub Repository](https://github.com/vmware-tanzu/community-edition/)

## Description

In this procedure, you will do the following:

- [Installing Tanzu CLI](#1-download-tanzu-cli)
- [Creating a Management Cluster](#3-create-management-cluster)
- [Creating a Workload Cluster](#5-create-workload-cluster)

## Demo

## Features

- feature:1
- feature:2

## Requirement

- [Docker Desktop for Mac](https://docs.docker.com/desktop/mac/install/)
  - RAM: 6 GB
  - CPU: 2

![resouces](https://user-images.githubusercontent.com/3072734/143419251-6f61a2f3-433d-4301-ac30-cc03c0b4bdf0.png)

- [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl-macos/)

## Usage

## Installation

### 0. Clean up for Docker

```shell
$ docker kill (docker ps -aq)
$ docker system prune -a --volumes -f
```

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
$ set -x MGMT_CLUSTER_NAME mgmt
$ tanzu management-cluster create -i docker --name $MGMT_CLUSTER_NAME --plan dev --ceip-participation=false
```

![1-create-mgmt-cluster](https://user-images.githubusercontent.com/3072734/142351724-f3cbde50-b8d3-417f-a94a-c1a84e00fbca.gif)

Confirmation

```shell
$ tanzu management-cluster get

  NAME  NAMESPACE   STATUS   CONTROLPLANE  WORKERS  KUBERNETES        ROLES
  mgmt  tkg-system  running  1/1           1/1      v1.21.2+vmware.1  management


Details:

NAME                                                     READY  SEVERITY  REASON  SINCE  MESSAGE
/mgmt                                                    True                     20m
├─ClusterInfrastructure - DockerCluster/mgmt             True                     20m
├─ControlPlane - KubeadmControlPlane/mgmt-control-plane  True                     20m
│ └─Machine/mgmt-control-plane-mglw6                     True                     20m
└─Workers
  └─MachineDeployment/mgmt-md-0
    └─Machine/mgmt-md-0-595ff89958-8cskm                 True                     20m


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

NAME                         STATUS   ROLES                  AGE   VERSION                               INTERNAL-IP   EXTERNAL-IP   OS-IMAGE           KERNEL-VERSION     CONTAINER-RUNTIME
mgmt-control-plane-mglw6     Ready    control-plane,master   32m   v1.21.2+vmware.1-360497810732255795   172.18.0.4    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
mgmt-md-0-595ff89958-8cskm   Ready    <none>                 31m   v1.21.2+vmware.1-360497810732255795   172.18.0.5    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
```

Default Pods

```shell
$ kubectl get pods -A

NAMESPACE                           NAME                                                             READY   STATUS    RESTARTS   AGE
capd-system                         capd-controller-manager-685f4d6876-l9fxw                         2/2     Running   0          26m
capi-kubeadm-bootstrap-system       capi-kubeadm-bootstrap-controller-manager-84c75dd587-hbcxb       2/2     Running   2          27m
capi-kubeadm-control-plane-system   capi-kubeadm-control-plane-controller-manager-756f646c68-8svts   2/2     Running   1          26m
capi-system                         capi-controller-manager-5468bf8995-nfxf5                         2/2     Running   2          28m
capi-webhook-system                 capi-controller-manager-b6f878dd8-5nqsz                          2/2     Running   0          28m
capi-webhook-system                 capi-kubeadm-bootstrap-controller-manager-67cf557cc6-v5vzl       2/2     Running   0          27m
capi-webhook-system                 capi-kubeadm-control-plane-controller-manager-798bb98b65-lgq6g   2/2     Running   0          27m
cert-manager                        cert-manager-cainjector-59d5cd55f5-hbb4q                         1/1     Running   1          31m
cert-manager                        cert-manager-fcbbdd748-xcg8d                                     1/1     Running   0          31m
cert-manager                        cert-manager-webhook-5cd7cf5fbb-7bhtx                            1/1     Running   0          31m
kube-system                         antrea-agent-5bdp2                                               2/2     Running   0          21m
kube-system                         antrea-agent-jkhnh                                               2/2     Running   2          21m
kube-system                         antrea-controller-7466cd958-76jbj                                1/1     Running   0          21m
kube-system                         coredns-8dcb5c56b-h5vfg                                          1/1     Running   0          33m
kube-system                         coredns-8dcb5c56b-nc5l8                                          1/1     Running   0          33m
kube-system                         etcd-mgmt-control-plane-mglw6                                    1/1     Running   0          33m
kube-system                         kube-apiserver-mgmt-control-plane-mglw6                          1/1     Running   0          33m
kube-system                         kube-controller-manager-mgmt-control-plane-mglw6                 1/1     Running   0          33m
kube-system                         kube-proxy-gs4tg                                                 1/1     Running   0          33m
kube-system                         kube-proxy-szkg6                                                 1/1     Running   0          32m
kube-system                         kube-scheduler-mgmt-control-plane-mglw6                          1/1     Running   0          33m
kube-system                         metrics-server-9fd8f855-5k6sd                                    1/1     Running   3          22m
tkg-system                          kapp-controller-764fc6c69f-gskw2                                 1/1     Running   0          33m
tkg-system                          tanzu-addons-controller-manager-86c7ff746b-8wp2x                 1/1     Running   0          22m
tkg-system                          tanzu-capabilities-controller-manager-69f58566d9-scwp9           1/1     Running   0          33m
tkr-system                          tkr-controller-manager-cc88b6968-6xlwz                           1/1     Running   2          33m
```

### 5. Create Workload Cluster

```shell
$ set -x WORKLOAD_CLUSTER_NAME workload
$ tanzu cluster create $WORKLOAD_CLUSTER_NAME --plan dev
```

![2-create-work-cluster](https://user-images.githubusercontent.com/3072734/142355024-30bd6cb7-c63e-4a13-a355-30982d954e92.gif)

Confirmation

```shell
$ tanzu cluster list --include-management-cluster

  NAME      NAMESPACE   STATUS   CONTROLPLANE  WORKERS  KUBERNETES        ROLES       PLAN
  lancelot  default     running  1/1           1/1      v1.21.2+vmware.1  <none>      dev
  arthur    tkg-system  running  1/1           1/1      v1.21.2+vmware.1  management  dev
```

### 6. Configure Workload Cluster

Configure kubeconfig for Workload Cluster

```shell
$ tanzu cluster kubeconfig get $WORKLOAD_CLUSTER_NAME --admin
```

Set kubectl context to Workload Cluster

```shell
$ kubectl config use-context $WORKLOAD_CLUSTER_NAME-admin@$WORKLOAD_CLUSTER_NAME
```

Node

```shell
$ kubectl get node -o wide

NAME                             STATUS   ROLES                  AGE   VERSION                               INTERNAL-IP   EXTERNAL-IP   OS-IMAGE           KERNEL-VERSION     CONTAINER-RUNTIME
lancelot-control-plane-dck47     Ready    control-plane,master   15m   v1.21.2+vmware.1-360497810732255795   172.18.0.6    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
lancelot-md-0-65446f8c69-zjt29   Ready    <none>                 15m   v1.21.2+vmware.1-360497810732255795   172.18.0.7    <none>        Ubuntu 20.04 LTS   5.10.47-linuxkit   containerd://1.3.3-14-g449e9269
```

![3-cluster-config](https://user-images.githubusercontent.com/3072734/142356086-fe47ec1a-d751-4793-8859-366c1aa073e0.gif)

Default Pods

```shell
$ kubectl get pods -A

NAMESPACE     NAME                                                     READY   STATUS    RESTARTS   AGE
kube-system   antrea-agent-cxhlb                                       2/2     Running   0          13m
kube-system   antrea-agent-shlwr                                       2/2     Running   0          13m
kube-system   antrea-controller-5f444f5bd6-n2r26                       1/1     Running   0          13m
kube-system   coredns-8dcb5c56b-6sxnl                                  1/1     Running   0          17m
kube-system   coredns-8dcb5c56b-trq4v                                  1/1     Running   0          17m
kube-system   etcd-lancelot-control-plane-dck47                        1/1     Running   0          17m
kube-system   kube-apiserver-lancelot-control-plane-dck47              1/1     Running   0          17m
kube-system   kube-controller-manager-lancelot-control-plane-dck47     1/1     Running   0          17m
kube-system   kube-proxy-4lrvb                                         1/1     Running   0          17m
kube-system   kube-proxy-qpbsp                                         1/1     Running   0          17m
kube-system   kube-scheduler-lancelot-control-plane-dck47              1/1     Running   0          17m
kube-system   metrics-server-5c78796cf6-sx2m2                          1/1     Running   0          13m
tkg-system    kapp-controller-844d94b44d-tsh7s                         1/1     Running   0          17m
tkg-system    tanzu-capabilities-controller-manager-69f58566d9-gk24r   1/1     Running   0          17m
```

### 7. Confirm Docker Containers

- 2 Clusters: `Management Cluster` and `Workload Cluster`
- each cluster has
  - `Control Plane`
  - `Worker Node`
  - `HA Proxy` as a Loadbalancer for the control plane

```shell
$ docker ps

CONTAINER ID   IMAGE                                                             COMMAND                  CREATED             STATUS             PORTS                                  NAMES
73c2f2622d89   projects-stg.registry.vmware.com/tkg/kind/node:v1.21.2_vmware.1   "/usr/local/bin/entr…"   26 minutes ago      Up 26 minutes                                             lancelot-md-0-65446f8c69-zjt29
60624257d87b   projects-stg.registry.vmware.com/tkg/kind/node:v1.21.2_vmware.1   "/usr/local/bin/entr…"   27 minutes ago      Up 27 minutes      46347/tcp, 127.0.0.1:46347->6443/tcp   lancelot-control-plane-dck47
d947cbe97fb7   kindest/haproxy:v20210715-a6da3463                                "haproxy -sf 7 -W -d…"   27 minutes ago      Up 27 minutes      44073/tcp, 0.0.0.0:44073->6443/tcp     lancelot-lb
dd9e31b34ef0   projects-stg.registry.vmware.com/tkg/kind/node:v1.21.2_vmware.1   "/usr/local/bin/entr…"   About an hour ago   Up About an hour                                          arthur-md-0-74b864579-d976m
845eef7ae583   projects-stg.registry.vmware.com/tkg/kind/node:v1.21.2_vmware.1   "/usr/local/bin/entr…"   About an hour ago   Up About an hour   41765/tcp, 127.0.0.1:41765->6443/tcp   arthur-control-plane-gt88x
d390fad9250b   kindest/haproxy:v20210715-a6da3463                                "haproxy -sf 7 -W -d…"   About an hour ago   Up About an hour   41941/tcp, 0.0.0.0:41941->6443/tcp     arthur-lb
```

### 8. Install Package

List available default packages

```shell
$ tanzu package available list -A

  NAME                                                DISPLAY-NAME                       SHORT-DESCRIPTION                                                                                                                                                                                       NAMESPACE
  addons-manager.tanzu.vmware.com                     tanzu-addons-manager               This package provides TKG addons lifecycle management capabilities.                                                                                                                                     tkg-system
  ako-operator.tanzu.vmware.com                       ako-operator                       NSX Advanced Load Balancer using ako-operator                                                                                                                                                           tkg-system
  antrea.tanzu.vmware.com                             antrea                             networking and network security solution for containers                                                                                                                                                 tkg-system
  calico.tanzu.vmware.com                             calico                             Networking and network security solution for containers.                                                                                                                                                tkg-system
  kapp-controller.tanzu.vmware.com                    kapp-controller                    Kubernetes package manager                                                                                                                                                                              tkg-system
  load-balancer-and-ingress-service.tanzu.vmware.com  load-balancer-and-ingress-service  Provides L4+L7 load balancing for TKG clusters running on vSphere                                                                                                                                       tkg-system
  metrics-server.tanzu.vmware.com                     metrics-server                     Metrics Server is a scalable, efficient source of container resource metrics for Kubernetes built-in autoscaling pipelines.                                                                             tkg-system
  pinniped.tanzu.vmware.com                           pinniped                           Pinniped provides identity services to Kubernetes.                                                                                                                                                      tkg-system
  vsphere-cpi.tanzu.vmware.com                        vsphere-cpi                        The Cluster API brings declarative, Kubernetes-style APIs to cluster creation, configuration and management. Cluster API Provider for vSphere is a concrete implementation of Cluster API for vSphere.  tkg-system
  vsphere-csi.tanzu.vmware.com                        vsphere-csi                        vSphere CSI provider                                                                                                                                                                                    tkg-system
```

List installed packages

```shell
$ tanzu package installed list -A

  NAME            PACKAGE-NAME                     PACKAGE-VERSION  STATUS               NAMESPACE
  antrea          antrea.tanzu.vmware.com                           Reconcile succeeded  tkg-system
  metrics-server  metrics-server.tanzu.vmware.com                   Reconcile succeeded  tkg-system
```

Add Package Repository for Tanzu Community Edition

```shell
$ tanzu package repository add tce-repo \
  --url projects.registry.vmware.com/tce/main:0.9.1 \
  --namespace tanzu-package-repo-global
```

Confirm the Package Repository

```shell
$ tanzu package repository list -A
```

#### Local Path Storage

- [Local Path Storage - v0.0.20 ¶](https://tanzucommunityedition.io/docs/latest/package-readme-local-path-storage-0.0.20/)

|Value|Required/Optional|Description|
|-----|-----------------|-----------|
|namespace|Required|The namespace to deploy the local-path-storage pods|

Prepare for installation

```shell
$ kubectl create ns tce-package
```

Install Local Path Storage

```shell
$ tanzu package install local-path-storage --package-name local-path-storage.community.tanzu.vmware.com -v 0.0.20 -n tce-package
```

List Packages

```shell
$ tanzu package installed list -A

  NAME                PACKAGE-NAME                                   PACKAGE-VERSION  STATUS               NAMESPACE
  local-path-storage  local-path-storage.community.tanzu.vmware.com  0.0.20           Reconcile succeeded  tce-package
  antrea              antrea.tanzu.vmware.com                                         Reconcile succeeded  tkg-system
  metrics-server      metrics-server.tanzu.vmware.com                                 Reconcile succeeded  tkg-system
```

List Pods

```shell
$ kubectl get pods -A

NAMESPACE                  NAME                                                                READY   STATUS    RESTARTS   AGE
kube-system                antrea-agent-4sfnr                                                  2/2     Running   0          5h43m
kube-system                antrea-agent-lzzjc                                                  2/2     Running   0          5h43m
kube-system                antrea-controller-5dc4698767-2ftnt                                  1/1     Running   0          5h43m
kube-system                coredns-8dcb5c56b-mdcs5                                             1/1     Running   0          5h47m
kube-system                coredns-8dcb5c56b-w2d9f                                             1/1     Running   0          5h47m
kube-system                etcd-yanashin-work-cluster-control-plane-slksz                      1/1     Running   0          5h47m
kube-system                kube-apiserver-yanashin-work-cluster-control-plane-slksz            1/1     Running   0          5h47m
kube-system                kube-controller-manager-yanashin-work-cluster-control-plane-slksz   1/1     Running   1          5h47m
kube-system                kube-proxy-k7gdk                                                    1/1     Running   0          5h46m
kube-system                kube-proxy-p48m7                                                    1/1     Running   0          5h47m
kube-system                kube-scheduler-yanashin-work-cluster-control-plane-slksz            1/1     Running   1          5h47m
kube-system                metrics-server-5895b667b4-mrjm2                                     1/1     Running   0          5h43m
tanzu-local-path-storage   local-path-provisioner-7dcb6d6b98-hv8kl                             1/1     Running   0          3m11s
tkg-system                 kapp-controller-866cb85b86-v28dk                                    1/1     Running   0          5h47m
tkg-system                 tanzu-capabilities-controller-manager-69f58566d9-r9t4q              1/1     Running   1          5h47m
```

Confirm Resource

```shell
$ kubectl get storageclass  

NAME                   PROVISIONER             RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
local-path (default)   rancher.io/local-path   Delete          WaitForFirstConsumer   false                  4m21s
```

### 9. MetalLB

MetalLB is a load-balancer implementation for bare metal Kubernetes clusters, using standard routing protocols.

- [MetalLB](https://github.com/metallb/metallb)

The necessary manifests for MetalLB

```shell
$ kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.11.0/manifests/namespace.yaml
$ kubectl create secret generic -n metallb-system memberlist --from-literal=secretkey="(openssl rand -base64 128)"
$ kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.11.0/manifests/metallb.yaml
```

ConfigMap for MetalLB

IP Range should be 172.18.0.0/16

```shell
$ string trim '
apiVersion: v1
kind: ConfigMap
metadata:
  namespace: metallb-system
  name: config
data:
  config: |
    address-pools:
    - name: default
      protocol: layer2
      addresses:
      - 172.18.0.150-172.18.0.200
' > metallb-config.yaml

$ kubectl apply -f metallb-config.yaml
```

### Delete Workload Cluster

```shell
$ tanzu cluster delete $WORKLOAD_CLUSTER_NAME -y
```

### Delete Management Cluster

```shell
$ tanzu management-cluster delete $MGMT_CLUSTER_NAME -y
```

<details><summary>Sample Application</summary><div>

### Sample Application

#### Initialize a New Project

```shell
$ spring --dependencies=webflux,data-r2dbc,h2 --build=gradle --language=kotlin --java-version=11
```

#### Database Schema and Test Data

- Schema

```sql
DROP TABLE IF EXISTS book;
CREATE TABLE book (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR (255) NOT NULL
);
```

- Data

```sql
INSERT INTO book VALUES (1, 'Spring in Action')
INSERT INTO book VALUES (2, 'Spring in Practice')
INSERT INTO book VALUES (3, 'Spring Boot in Action')
INSERT INTO book VALUES (4, 'Spring Boot in Practice')
```

</div></details>

#### Entity

- Data Class
  - Book elements
    - ID element: `id` as `Long`
    - `name` as `String`
  - Second Constructor
    - It is used for auto incremented id

```kotlin
data class Book(
    @Id
    val id: Long,
    val name: String
) {
    constructor(name: String) : this(0, name)
}
```

#### Repository Interface

- ReactiveCrudRepository

```kotlin
@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long>
```

#### Controller as Functional Endpoint

Two ways of Flux controller
- [ ] Annotated Controller
- [x] Functional Endpoint
  - `Handler` and `Router`


- Handler

```kotlin
fun findAllHandler(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse.ok().body(repository.findAll(), Book::class.java)
}
```

- Router

```kotlin
@Bean
fun router(): RouterFunction<ServerResponse> {
    return route()
        .GET("/books", this::findAllHandler)
        .build()
}
```

## References

- [Getting Started with Tanzu Community Edition](https://tanzucommunityedition.io/docs/latest/getting-started/)
- [MetalLB](https://metallb.universe.tf/)

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)

- twitter: <https://twitter.com/yanashin18618>
