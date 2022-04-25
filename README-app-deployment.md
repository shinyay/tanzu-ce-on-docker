# Getting Started with Tanzu Community Edition on Docker - Application Deployment

## Deployment YAML for sample demo app

### Create Deployment YAML Template

```shell
kubectl create deployment --image=shinyay/docker-mario:0.0.1 demo --dry-run=client -o=yaml > deployment.yml
```
