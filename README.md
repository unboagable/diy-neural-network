# diy-neural-network

Simple DIY neural network to understand backpropogation algorithm

## Modern ML module (Deeplearning4J)

Added a standalone Maven module in `modern-ml` that trains a tiny MLP to solve XOR using Deeplearning4J and ND4J.

Run it:

```bash
cd modern-ml
mvn -q -DskipTests exec:java
```

Main class: `org.example.Deeplearning4JPerceptron`

## Build and run with Maven (multi-module)

This repo is now a Maven multi-module project with:

- `legacy`: builds the existing sources under `Neural Network/src`
- `modern-ml`: Deeplearning4J XOR example

Build everything from the repo root:

```bash
mvn -q -DskipTests package
```

Run legacy (original) app:

```bash
cd legacy
mvn -q -DskipTests exec:java
```

Run legacy but invoke modern DL4J example via `Main` argument:

```bash
cd legacy
mvn -q -DskipTests exec:java -Dexec.args=dl4j
```

