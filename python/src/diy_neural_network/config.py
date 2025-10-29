"""Configuration constants and path management."""
from pathlib import Path

# Project root directory (python/ directory)
PROJECT_ROOT = Path(__file__).parent.parent.parent

# Model directory and default model path
MODEL_DIR = PROJECT_ROOT / "models"
MODEL_DIR.mkdir(exist_ok=True, parents=True)
DEFAULT_MODEL_PATH = MODEL_DIR / "mnist_model.keras"

# MNIST image dimensions
IMAGE_HEIGHT = 28
IMAGE_WIDTH = 28
NUM_CLASSES = 10

