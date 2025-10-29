"""Model training utilities."""
from pathlib import Path
import logging
import tensorflow as tf
from tensorflow import keras

from diy_neural_network.config import DEFAULT_MODEL_PATH, IMAGE_HEIGHT, IMAGE_WIDTH
from diy_neural_network.datasets import mnist


def create_model():
    """
    Create a simple sequential neural network model for MNIST digit classification.
    
    Returns:
        A compiled Keras model.
    """
    model = tf.keras.Sequential([
        keras.layers.Input(shape=(IMAGE_HEIGHT, IMAGE_WIDTH,)),
        keras.layers.Flatten(),
        keras.layers.Dense(512, activation='relu'),
        keras.layers.Dropout(0.2),
        keras.layers.Dense(10)
    ])

    model.compile(
        optimizer='adam',
        loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
        metrics=[tf.keras.metrics.SparseCategoricalAccuracy()]
    )

    return model


def load_model(model_path=None):
    """
    Load a saved model from disk.
    
    Args:
        model_path: Path to the saved model. If None, uses default model path.
        
    Returns:
        Loaded Keras model.
        
    Raises:
        FileNotFoundError: If model file doesn't exist.
    """
    if model_path is None:
        model_path = DEFAULT_MODEL_PATH
    
    model_path = Path(model_path)
    if not model_path.exists():
        raise FileNotFoundError(f"Model not found at {model_path}. Train the model first.")
    
    return tf.keras.models.load_model(model_path)


def main():
    """Main training function for CLI usage."""
    logging.basicConfig(level=logging.INFO)
    logger = logging.getLogger(__name__)
    # Create a model instance
    model = create_model()

    # Display the model's architecture
    logger.info("Model Architecture:")
    model.summary()
    

    # Load dataset
    logger.info("Loading MNIST dataset...")
    (X_train, y_train), (X_test, y_test) = mnist.load_mnist()
    
    # Train the model
    logger.info(f"Training on {len(X_train)} samples...")
    history = model.fit(
        X_train, y_train,
        epochs=10,
        batch_size=32,
        validation_data=(X_test, y_test),
        verbose=1
    )

    # Evaluate the model
    logger.info("Evaluating model...")
    loss, accuracy = model.evaluate(X_test, y_test, verbose=1)
    logger.info(f"Test Loss: {loss:.4f}")
    logger.info(f"Test Accuracy: {accuracy:.4f}")
    
    # Save the model
    model.save(DEFAULT_MODEL_PATH)
    logger.info(f"Model saved to {DEFAULT_MODEL_PATH}")


if __name__ == "__main__":
    main()