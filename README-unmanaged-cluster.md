## Installation

### 1. Download Tanzu CLI

Install CLI with Homebrew

```shell
brew install vmware-tanzu/tanzu/tanzu-community-edition

{HOMEBREW-INSTALL-LOCATION}/configure-tce.sh
```

### 2. Deploy an Unmanaged Cluster

Create a cluster named `my-cluster`

```shell
tanzu unmanaged-cluster create my-cluster
```

<details>
<summary>creating unmanaged-cluster</summary>

<details>
<summary>image</summary>

![tce-unmanaged](https://user-images.githubusercontent.com/3072734/164588959-51b1bd10-e593-4a64-a6cb-be167b4656c8.gif)

</details>

```shell
📁 Created cluster directory

🔧 Resolving Tanzu Kubernetes Release (TKR)
   projects.registry.vmware.com/tce/tkr:v0.17.0
   Downloaded to: /Users/yanagiharas/.config/tanzu/tkg/unmanaged/bom/projects.registry.vmware.com_tce_tkr_v0.17.0
   Rendered Config: /Users/yanagiharas/.config/tanzu/tkg/unmanaged/my-cluster/config.yaml
   Bootstrap Logs: /Users/yanagiharas/.config/tanzu/tkg/unmanaged/my-cluster/bootstrap.log

🔧 Processing Tanzu Kubernetes Release

🎨 Selected base image
   projects.registry.vmware.com/tce/kind:v1.22.4

📦 Selected core package repository
   projects.registry.vmware.com/tce/repo-10:0.10.0

📦 Selected additional package repositories
   projects.registry.vmware.com/tce/main:v0.11.0

📦 Selected kapp-controller image bundle
   projects.registry.vmware.com/tce/kapp-controller-multi-pkg:v0.30.1

🚀 Creating cluster my-cluster
   Cluster creation using kind!
   ❤️  Checkout this awesome project at https://kind.sigs.k8s.io
   Base image downloaded
   Cluster created
   To troubleshoot, use:

   kubectl ${COMMAND} --kubeconfig /Users/yanagiharas/.config/tanzu/tkg/unmanaged/my-cluster/kube.conf

📧 Installing kapp-controller
   kapp-controller status: Running

📧 Installing package repositories
   Core package repo status: Reconcile succeeded

🌐 Installing CNI
   calico.community.tanzu.vmware.com:3.22.1

✅ Cluster created

🎮 kubectl context set to my-cluster

View available packages:
   tanzu package available list
View running pods:
   kubectl get po -A
Delete this cluster:
   tanzu unmanaged delete my-cluster
```

</details>

<details>
<summary>tanzu package available list</summary>

```shell
  NAME                                                 DISPLAY-NAME                 SHORT-DESCRIPTION                                                                                                                                          LATEST-VERSION
  app-toolkit.community.tanzu.vmware.com               App-Toolkit package for TCE  Kubernetes-native toolkit to support application lifecycle                                                                                                 0.1.0
  cartographer.community.tanzu.vmware.com              Cartographer                 Kubernetes native Supply Chain Choreographer.                                                                                                              0.2.2
  cert-injection-webhook.community.tanzu.vmware.com    cert-injection-webhook       The Cert Injection Webhook injects CA certificates and proxy environment variables into pods                                                               0.1.0
  cert-manager.community.tanzu.vmware.com              cert-manager                 Certificate management                                                                                                                                     1.6.1
  contour.community.tanzu.vmware.com                   contour                      An ingress controller                                                                                                                                      1.20.1
  external-dns.community.tanzu.vmware.com              external-dns                 This package provides DNS synchronization functionality.                                                                                                   0.10.0
  fluent-bit.community.tanzu.vmware.com                fluent-bit                   Fluent Bit is a fast Log Processor and Forwarder                                                                                                           1.7.5
  fluxcd-source-controller.community.tanzu.vmware.com  Flux Source Controller       The source-controller is a Kubernetes operator, specialised in artifacts acquisition from external sources such as Git, Helm repositories and S3 buckets.  0.21.2
  gatekeeper.community.tanzu.vmware.com                gatekeeper                   policy management                                                                                                                                          3.7.0
  grafana.community.tanzu.vmware.com                   grafana                      Visualization and analytics software                                                                                                                       7.5.11
  harbor.community.tanzu.vmware.com                    harbor                       OCI Registry                                                                                                                                               2.3.3
  knative-serving.community.tanzu.vmware.com           knative-serving              Knative Serving builds on Kubernetes to support deploying and serving of applications and functions as serverless containers                               1.0.0
  kpack.community.tanzu.vmware.com                     kpack                        kpack builds application source code into OCI compliant images using Cloud Native Buildpacks                                                               0.5.1
  local-path-storage.community.tanzu.vmware.com        local-path-storage           This package provides local path node storage and primarily supports RWO AccessMode.                                                                       0.0.20
  multus-cni.community.tanzu.vmware.com                multus-cni                   This package provides the ability for enabling attaching multiple network interfaces to pods in Kubernetes                                                 3.7.1
  prometheus.community.tanzu.vmware.com                prometheus                   A time series database for your metrics                                                                                                                    2.27.0-1
  velero.community.tanzu.vmware.com                    velero                       Disaster recovery capabilities                                                                                                                             1.8.0
  whereabouts.community.tanzu.vmware.com               whereabouts                  A CNI IPAM plugin that assigns IP addresses cluster-wide                                                                                                   0.5.0
  ```

</details>

<details>
<summary>kubectl get po -A</summary>

```shell
NAMESPACE            NAME                                               READY   STATUS    RESTARTS   AGE
kube-system          calico-kube-controllers-5f8888f94-652vn            1/1     Running   0          5m4s
kube-system          calico-node-fsh8r                                  1/1     Running   0          5m4s
kube-system          coredns-78fcd69978-g94gj                           1/1     Running   0          5m51s
kube-system          coredns-78fcd69978-jtxd8                           1/1     Running   0          5m51s
kube-system          etcd-my-cluster-control-plane                      1/1     Running   0          6m5s
kube-system          kube-apiserver-my-cluster-control-plane            1/1     Running   0          6m6s
kube-system          kube-controller-manager-my-cluster-control-plane   1/1     Running   0          6m6s
kube-system          kube-proxy-dwbj4                                   1/1     Running   0          5m51s
kube-system          kube-scheduler-my-cluster-control-plane            1/1     Running   0          6m5s
local-path-storage   local-path-provisioner-85494db59d-5r9hz            1/1     Running   0          5m51s
tkg-system           kapp-controller-779d9777dc-xc4mv                   1/1     Running   0          5m51s
```

</details>

### 3. Deploy a Package

List the available package repositories

```shell
tanzu package repository list --all-namespaces
```

<details>
<summary>listing repositories</summary>

```shell
  NAME                                           REPOSITORY                                TAG      STATUS               DETAILS  NAMESPACE
  projects.registry.vmware.com-tce-main-v0.11.0  projects.registry.vmware.com/tce/main     v0.11.0  Reconcile succeeded           tanzu-package-repo-global
  tkg-core-repository                            projects.registry.vmware.com/tce/repo-10  0.10.0   Reconcile succeeded           tkg-system
```

</details>

List the available version of the specific package

```shell
tanzu package available list {PACKAGE_NAME}

tanzu package available list contour.community.tanzu.vmware.com
```

<details>
<summary>listing repositories</summary>

```shell
  NAME                                VERSION  RELEASED-AT
  contour.community.tanzu.vmware.com  1.18.1   2021-07-24 03:00:00 +0900 JST
  contour.community.tanzu.vmware.com  1.19.1   2021-10-26 09:00:00 +0900 JST
  contour.community.tanzu.vmware.com  1.20.1   2022-02-24 09:00:00 +0900 JST
```

</details>

Install the package

```shell
tanzu package install {INSTALLED_PACKAGE_NAME} --package-name {PACKAGE_NAME} --version {VERSION}

tanzu package install contour --package-name contour.community.tanzu.vmware.com --version 1.20.1
```

List the installed package

```shell
tanzu package installed list
```

<details>
<summary>listing repositories</summary>

```shell
  NAME     PACKAGE-NAME                        PACKAGE-VERSION  STATUS
  contour  contour.community.tanzu.vmware.com  1.20.1            Reconcile succeeded
```

</details>

### 4. Delete a Cluster

List the available cluster

```shell
tanzu unmanaged-cluster list

  NAME        PROVIDER
  my-cluster  kind
```


Delete the cluster

```shell
tanzu unmanaged-cluster delete my-cluster
```

## Deploy workloads

### 1. Deploy Direct Image `kuard`

```shell
kubectl run --restart=Never --image=gcr.io/kuar-demo/kuard-amd64:blue kuard
```

```shell
kubectl port-forward kuard 8080:8080
```

Access to <http://localhost:8080>

### 2. Deploy with Contour

Configure Environments without support for LoadBalancer services for Docker

```shell
string trim '
envoy:
  service:
    type: ClusterIP
' > contour-values.yaml
```

Confirm available package list

```shell
tanzu package available list
```

Confirm available Contour version

```shell
 tanzu package available list contour.community.tanzu.vmware.com
```

```shell
string trim '
apiVersion: projectcontour.io/v1
kind: HTTPProxy
metadata:
  name: nginx-example-proxy
  namespace: contour-example-workload
  labels:
    app: ingress
spec:
  virtualhost:
    fqdn: nginx-example.projectcontour.io
  routes:
  - conditions:
    - prefix: /
    services:
    - name: nginx-example
      port: 80' > httpproxy.yml
```

## Deploy MetalLB

- [MetalLB Installation](https://metallb.universe.tf/installation/)

Confirm kube-proxy mode

```shell
kubectl get pods -n kube-system | grep kube-proxy
kubectl logs kube-proxy-xxxxx -n kube-system
```

You can see `Using iptables Proxier`

```shell
I0422 02:29:53.377505       1 node.go:172] Successfully retrieved node IP: 172.18.0.2
I0422 02:29:53.377609       1 server_others.go:140] Detected node IP 172.18.0.2
I0422 02:29:53.396687       1 server_others.go:206] kube-proxy running in dual-stack mode, IPv4-primary
I0422 02:29:53.396788       1 server_others.go:212] Using iptables Proxier.
I0422 02:29:53.396807       1 server_others.go:219] creating dualStackProxier for iptables.
W0422 02:29:53.396844       1 server_others.go:495] detect-local-mode set to ClusterCIDR, but no IPv6 cluster CIDR defined, , defaulting to no-op detect-local for IPv6
I0422 02:29:53.397386       1 server.go:649] Version: v1.22.4
```

Configure kube-proxy mode

```shell
kubectl edit configmaps kube-proxy -n kube-system
```

Before

```yaml
    metricsBindAddress: ""
    mode: iptables
    nodePortAddresses: null
```

```yaml
    ipvs:
      strictARP: false
```

After

```yaml
    metricsBindAddress: ""
    mode: ipvs
    nodePortAddresses: null
```

```yaml
    ipvs:
      strictARP: true
```

Apply kube-proxy mode

```shell
kubectl rollout restart daemonset kube-proxy -n kube-system
```

Then logs again

```shell
I0422 06:54:08.701543       1 node.go:172] Successfully retrieved node IP: 172.18.0.2
I0422 06:54:08.701612       1 server_others.go:140] Detected node IP 172.18.0.2
I0422 06:54:08.743806       1 server_others.go:206] kube-proxy running in dual-stack mode, IPv4-primary
I0422 06:54:08.743997       1 server_others.go:274] Using ipvs Proxier.
I0422 06:54:08.744136       1 server_others.go:276] creating dualStackProxier for ipvs.
W0422 06:54:08.744215       1 server_others.go:495] detect-local-mode set to ClusterCIDR, but no IPv6 cluster CIDR defined, , defaulting to no-op detect-local for IPv6
I0422 06:54:08.745908       1 proxier.go:440] "IPVS scheduler not specified, use rr by default"
I0422 06:54:08.746213       1 proxier.go:440] "IPVS scheduler not specified, use rr by default"
W0422 06:54:08.746327       1 ipset.go:113] ipset name truncated; [KUBE-6-LOAD-BALANCER-SOURCE-CIDR] -> [KUBE-6-LOAD-BALANCER-SOURCE-CID]
W0422 06:54:08.746380       1 ipset.go:113] ipset name truncated; [KUBE-6-NODE-PORT-LOCAL-SCTP-HASH] -> [KUBE-6-NODE-PORT-LOCAL-SCTP-HAS]
I0422 06:54:08.746759       1 server.go:649] Version: v1.22.4
```

MetalLB Apply

```shell
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/namespace.yaml
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/metallb.yaml
```

Confirm MetalLB running

```shell
kubectl get pods -n metallb-system

NAME                          READY   STATUS    RESTARTS   AGE
controller-66445f859d-tjdg7   1/1     Running   0          93m
speaker-r2dpf                 1/1     Running   0          93m
```

```shell
kubectl get daemonsets -n metallb-system

NAME      DESIRED   CURRENT   READY   UP-TO-DATE   AVAILABLE   NODE SELECTOR            AGE
speaker   1         1         1       1            1           kubernetes.io/os=linux   93m
```