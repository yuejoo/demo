## Build
```mvn clean package```

## Run
* Method 1. Build and run.
```sh bin/prodlinearity.sh -p <PreBuildDays> -i <InputFile> -o <OutputFile>```
* Method 2. Use the pre-build jar
Modify the bin/prodlinearity.sh to use:
```
# Use the prebuild jar
# java -jar ${DIR}/pre-build-jar/ProdLinearity-1.0-SNAPSHOT.jar $@
```


## Environment
* Java 1.8
* Maven 3.6.1
