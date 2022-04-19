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