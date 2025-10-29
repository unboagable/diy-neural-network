"""MNIST dataset loading and preprocessing."""
from typing import Tuple
import numpy as np
try:
    from keras.datasets import mnist
except ImportError:
    from tensorflow.keras.datasets import mnist
import matplotlib.pyplot as plt
import logging


def load_mnist(normalize: bool = True) -> Tuple[Tuple[np.ndarray, np.ndarray], Tuple[np.ndarray, np.ndarray]]:
    """
    Load the MNIST dataset.
    
    Args:
        normalize: If True, normalize pixel values to [0, 1] range (default: True)
        
    Returns:
        Tuple containing ((X_train, y_train), (X_test, y_test))
        - X_train, X_test: Images with shape (n_samples, 28, 28), dtype uint8 or float32
        - y_train, y_test: Labels with shape (n_samples,), dtype uint8
    """
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    
    if normalize:
        # Normalize pixel values from [0, 255] to [0, 1]
        X_train = X_train.astype(np.float32) / 255.0
        X_test = X_test.astype(np.float32) / 255.0
    
    return (X_train, y_train), (X_test, y_test)


def visualize_samples(
    X: np.ndarray, 
    y: np.ndarray, 
    n_samples: int = 4,
    figsize: Tuple[int, int] = (10, 5)
) -> None:
    """
    Visualize sample images from the dataset.
    
    Args:
        X: Image array with shape (n_samples, 28, 28)
        y: Label array with shape (n_samples,)
        n_samples: Number of samples to visualize
        figsize: Figure size (width, height)
    """
    plt.figure(figsize=figsize)
    for i in range(n_samples):
        plt.subplot(1, n_samples, i + 1)
        plt.imshow(X[i], cmap='gray')
        plt.title(f"Label: {y[i]}")
        plt.axis('off')
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    # Load the MNIST dataset (normalized)
    (X_train, y_train), (X_test, y_test) = load_mnist(normalize=True)
    
    logging.basicConfig(level=logging.INFO)
    logger = logging.getLogger(__name__)
    logger.info(f"Training set: {X_train.shape[0]} samples")
    logger.info(f"Test set: {X_test.shape[0]} samples")
    logger.info(f"Image shape: {X_train.shape[1:]}")
    logger.info(f"Pixel value range: [{X_train.min():.2f}, {X_train.max():.2f}]")
    
    # Visualize 4 training samples
    visualize_samples(X_train, y_train, n_samples=4)


