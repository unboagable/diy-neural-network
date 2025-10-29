#!/usr/bin/env python3
"""Legacy script wrapper - use 'diy-train' command or python -m diy_neural_network.train_model instead."""
import sys
from pathlib import Path

# Add src to path for backward compatibility
sys.path.insert(0, str(Path(__file__).parent.parent / "src"))

# Import and call the main function from the module
from diy_neural_network.train_model import main

if __name__ == "__main__":
    main()
