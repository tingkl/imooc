#!/usr/bin/env bash
mvn clean package

cp -r ./target/lib /Users/tingkl/Desktop/prod/ms

cp ./target/miaosha.jar /Users/tingkl/Desktop/prod/ms

cp ./src/main/resources/tingkl.conf /Users/tingkl/Desktop/prod/ms

cd /Users/tingkl/Desktop/prod/ms

git add *

git commit -m 'auto'

git push