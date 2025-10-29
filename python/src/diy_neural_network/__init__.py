"""DIY Neural Network - Python implementation.

A simple neural network implementation for understanding backpropagation,
featuring MNIST digit classification.
"""

__version__ = "0.1.0"

# Expose commonly used functions
from diy_neural_network.datasets import load_mnist, visualize_samples
from diy_neural_network.train_model import create_model, load_model, main as train_main
from diy_neural_network.predict import (
    predict_images,
    visualize_predictions,
    predict_and_visualize_test_samples,
    main as predict_main,
)

__all__ = [
    "__version__",
    "load_mnist",
    "visualize_samples",
    "create_model",
    "load_model",
    "predict_images",
    "visualize_predictions",
    "predict_and_visualize_test_samples",
]

