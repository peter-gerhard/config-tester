#!/usr/bin/env bash

kubectl apply -f secret.yaml

helm install config-tester-chart