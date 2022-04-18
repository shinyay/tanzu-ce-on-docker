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