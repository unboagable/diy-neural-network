import shiffmanSinglePerceptron.ShiffmanPerceptronSimulation;
import org.example.Deeplearning4JPerceptron;

/**
 * 
 */

/**
 * @author Chang-Hyun
 *
 */
public class Main {

	/**
	 * @param args
	 */
    public static void main(String[] args) {
        if (args != null && args.length > 0 && ("dl4j".equalsIgnoreCase(args[0]) || "modern".equalsIgnoreCase(args[0]))) {
            Deeplearning4JPerceptron.main(new String[]{});
        } else {
            ShiffmanPerceptronSimulation.main(args);
        }
    }

}
