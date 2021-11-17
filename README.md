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

## Installation
### 

## References

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
- twitter: https://twitter.com/yanashin18618
