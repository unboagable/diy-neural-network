"""DIY Neural Network - Python implementation."""

__version__ = "0.1.0"

# Optionally expose commonly used dataset functions
from diy_neural_network.datasets import load_mnist, visualize_samples

__all__ = ["__version__", "load_mnist", "visualize_samples"]

