"""Model prediction and visualization utilities."""
from pathlib import Path
import numpy as np
import matplotlib.pyplot as plt
from typing import Tuple, Optional
import tensorflow as tf
import logging

from diy_neural_network.config import DEFAULT_MODEL_PATH
from diy_neural_network.train_model import load_model
from diy_neural_network.datasets import mnist


def predict_images(
    model: tf.keras.Model,
    images: np.ndarray,
    apply_softmax: bool = True
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Predict digit classes for images.
    
    Args:
        model: Trained Keras model
        images: Image array with shape (n_samples, 28, 28)
        apply_softmax: If True, apply softmax to get probabilities (default: canonical)
        
    Returns:
        Tuple of (predictions, probabilities)
        - predictions: Predicted class indices, shape (n_samples,)
        - probabilities: Class probabilities, shape (n_samples, 10)
    """
    # Get model predictions (logits)
    logits = model.predict(images, verbose=0)
    
    if apply_softmax:
        probabilities = tf.nn.softmax(logits).numpy()
    else:
        probabilities = logits
    
    # Get predicted class indices
    predictions = np.argmax(probabilities, axis=1)
    
    return predictions, probabilities


def visualize_predictions(
    images: np.ndarray,
    true_labels: np.ndarray,
    predicted_labels: np.ndarray,
    probabilities: Optional[np.ndarray] = None,
    n_samples: int = 4,
    figsize: Tuple[int, int] = (12, 3)
) -> None:
    """
    Visualize images with their true and predicted labels.
    
    Args:
        images: Image array with shape (n_samples, 28, 28)
        true_labels: True label array with shape (n_samples,)
        predicted_labels: Predicted label array with shape (n_samples,)
        probabilities: Optional probability array with shape (n_samples, 10)
        n_samples: Number of samples to visualize
        figsize: Figure size (width, height)
    """
    n_samples = min(n_samples, len(images))
    
    fig, axes = plt.subplots(1, n_samples, figsize=figsize)
    if n_samples == 1:
        axes = [axes]
    
    for i in range(n_samples):
        ax = axes[i]
        
        # Display image
        ax.imshow(images[i], cmap='gray')
        
        # Determine color based on correctness
        color = 'green' if true_labels[i] == predicted_labels[i] else 'red'
        
        # Create title with true and predicted labels
        title = f"True: {true_labels[i]}\nPred: {predicted_labels[i]}"
        
        # Add confidence if probabilities are provided
        if probabilities is not None:
            conf = probabilities[i][predicted_labels[i]]
            title += f"\nConf: {conf:.2%}"
        
        ax.set_title(title, color=color, fontsize=10)
        ax.axis('off')
    
    plt.tight_layout()
    plt.show()


def predict_and_visualize_test_samples(
    model_path: Optional[Path] = None,
    n_samples: int = 4,
    random_seed: Optional[int] = None
) -> None:
    """
    Load model, select test samples, predict, and visualize results.
    
    Args:
        model_path: Path to saved model. If None, uses default model path.
        n_samples: Number of test samples to visualize (default: 4)
        random_seed: Random seed for selecting samples (default: None)
    """
    # Load model
    logger = logging.getLogger(__name__)
    model = load_model(model_path)
    logger.info(f"Model loaded from {model_path or DEFAULT_MODEL_PATH}")
    
    # Load test dataset
    (_, _), (X_test, y_test) = mnist.load_mnist()
    
    # Select random samples
    if random_seed is not None:
        np.random.seed(random_seed)
    
    indices = np.random.choice(len(X_test), size=n_samples, replace=False)
    selected_images = X_test[indices]
    selected_labels = y_test[indices]
    
    # Make predictions
    logger.info(f"Making predictions on {n_samples} test samples...")
    predictions, probabilities = predict_images(model, selected_images)
    
    # Log predictions
    logger.info("Predictions:")
    for i, idx in enumerate(indices):
        correct = "✓" if predictions[i] == selected_labels[i] else "✗"
        conf = probabilities[i][predictions[i]]
        logger.info(
            f"Sample {i+1}: True={selected_labels[i]}, Predicted={predictions[i]}, "
            f"Confidence={conf:.2%} {correct}"
        )
    
    # Visualize
    visualize_predictions(
        selected_images,
        selected_labels,
        predictions,
        probabilities,
        n_samples=n_samples
    )


def main():
    """Main prediction function for CLI usage."""
    import argparse
    logging.basicConfig(level=logging.INFO)
    
    parser = argparse.ArgumentParser(description="Predict and visualize MNIST digit classifications")
    parser.add_argument(
        "--n-samples",
        type=int,
        default=4,
        help="Number of test samples to visualize (default: 4)"
    )
    parser.add_argument(
        "--model-path",
        type=str,
        default=None,
        help="Path to saved model (default: uses default model path)"
    )
    parser.add_argument(
        "--seed",
        type=int,
        default=None,
        help="Random seed for sample selection (default: None)"
    )
    
    args = parser.parse_args()
    
    predict_and_visualize_test_samples(
        model_path=Path(args.model_path) if args.model_path else None,
        n_samples=args.n_samples,
        random_seed=args.seed
    )


if __name__ == "__main__":
    main()

