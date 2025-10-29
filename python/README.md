# DIY Neural Network - Python

Python implementation of neural networks to understand backpropagation algorithm.

## Installation

### Option 1: Install as a package (Recommended)

```bash
cd python
pip install -e .
```

After installation, you can use the CLI commands:
- `diy-train` - Train the MNIST model
- `diy-predict` - Make predictions and visualize results

### Option 2: Development setup

Install dependencies directly:

```bash
pip install -r requirements.txt
```

For development with linting and testing tools:

```bash
pip install -r requirements-dev.txt
```

## Project Structure

```
python/
├── src/
│   └── diy_neural_network/      # Main source code package
│       ├── __init__.py           # Package exports
│       ├── config.py             # Configuration constants
│       ├── datasets/             # Dataset loading utilities (MNIST)
│       │   ├── __init__.py
│       │   └── mnist.py
│       ├── train_model.py        # Model creation and training
│       └── predict.py            # Prediction and visualization
├── scripts/                      # Legacy script wrappers (for backward compatibility)
│   ├── train.py
│   └── predict.py
├── models/                       # Saved model files (not in git)
├── tests/                        # Test files
├── pyproject.toml                # Project metadata and build config
├── requirements.txt              # Runtime dependencies
├── requirements-dev.txt          # Development dependencies
└── LICENSE                       # MIT License
```

## Usage

### Training the Model

Train a new MNIST digit classification model:

```bash
# Option 1: Using CLI command (if installed with pip install -e .)
diy-train

# Option 2: Using Python module
python -m diy_neural_network.train_model

# Option 3: Using legacy script (backward compatibility)
python scripts/train.py
```

The trained model will be saved to `models/mnist_model.keras`.

### Making Predictions

Predict and visualize 4 random test samples:

```bash
# Option 1: Using CLI command (if installed)
diy-predict
diy-predict --n-samples 8 --seed 42

# Option 2: Using Python module
python -m diy_neural_network.predict
python -m diy_neural_network.predict --n-samples 8 --seed 42

# Option 3: Using legacy script
python scripts/predict.py --n-samples 8

# With a custom model path
python -m diy_neural_network.predict --model-path models/my_model.keras
```

Or use the Python API:

```python
from diy_neural_network import predict_and_visualize_test_samples

# Predict and visualize 4 test samples
predict_and_visualize_test_samples(n_samples=4)
```

### Visualizing Dataset Samples

View sample images from the dataset:

```bash
python -m diy_neural_network.datasets.mnist
```

Or in Python:

```python
from diy_neural_network import load_mnist, visualize_samples

# Load dataset
(X_train, y_train), (X_test, y_test) = load_mnist()

# Visualize 4 training samples
visualize_samples(X_train, y_train, n_samples=4)
```

## Development

Run tests:

```bash
pytest
```

Format code:

```bash
black src scripts tests
```

Lint code:

```bash
flake8 src scripts tests
```
