# diy-neural-network

Simple DIY neural network to understand backpropogation algorithm

## Project Structure

This project is organized into two main directories:

- `java/` - Java implementations using Maven multi-module structure
- `python/` - Python implementations with modern packaging

## Java Implementation

### Modern ML module (Deeplearning4J)

Added a standalone Maven module in `java/modern-ml` that trains a tiny MLP to solve XOR using Deeplearning4J and ND4J.

Run it:

```bash
cd java/modern-ml
mvn -q -DskipTests exec:java
```

Main class: `org.example.Deeplearning4JPerceptron`

### Build and run with Maven (multi-module)

This repo is now a Maven multi-module project with:

- `java/legacy`: builds the existing sources under `Neural Network/src`
- `java/modern-ml`: Deeplearning4J XOR example

Build everything from the java directory:

```bash
cd java
mvn -q -DskipTests package
```

Run legacy (original) app:

```bash
cd java/legacy
mvn -q -DskipTests exec:java
```

Run legacy but invoke modern DL4J example via `Main` argument:

```bash
cd java/legacy
mvn -q -DskipTests exec:java -Dexec.args=dl4j
```

## Python Implementation

The Python directory contains scaffolding for neural network implementations. See `python/README.md` for more details.

To get started:

```bash
cd python
pip install -r requirements.txt
```

