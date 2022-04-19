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