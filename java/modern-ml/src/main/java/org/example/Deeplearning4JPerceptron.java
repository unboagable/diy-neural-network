package org.example;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class Deeplearning4JPerceptron {

    public static void main(String[] args) {
        // XOR dataset
        INDArray input = Nd4j.create(new double[][]{
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        });
        INDArray labels = Nd4j.create(new double[][]{
                {0},
                {1},
                {1},
                {0}
        });
        DataSet dataSet = new DataSet(input, labels);

        int seed = 123;
        double learningRate = 0.1;

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .updater(new Nesterovs(learningRate, 0.9))
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(2)
                        .nOut(4)
                        .activation(Activation.SIGMOID)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.XENT)
                        .nIn(4)
                        .nOut(1)
                        .activation(Activation.SIGMOID)
                        .build())
                .build();

        MultiLayerNetwork network = new MultiLayerNetwork(configuration);
        network.init();
        network.setListeners(new ScoreIterationListener(100));

        int epochs = 3000;
        for (int i = 0; i < epochs; i++) {
            network.fit(dataSet);
        }

        INDArray output = network.output(input);
        System.out.println("Predictions for XOR:\n" + output);
    }
}



