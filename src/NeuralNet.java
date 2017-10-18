/* Build the 2 layer neural network generated from R
 * As only two layer, I just calculate directly
 * */
public class NeuralNet {

	int temp;
	int error;
	double w11 = 375.6785;
	double w12 = 6.997;
	double w2 = 286.34112;
	double l111 = 0.37677;
	double l112 = -0.0831;
	double l121 = -3.50246;
	double l122 = -0.00018;
	double l21 = -144.41323;
	double l22 = -277.80356;
	
	public NeuralNet(int temp, int error){
		this.temp = temp;
		this.error = error;
	}
	
	public boolean isAnomaly(){
		// calculate the value for each neuron		
		double n11 = sigmoid(w11 + temp*l111 + error*l121);
		double n12 = sigmoid(w12 + temp*l112 + error*l122);
		double n3 = sigmoid(w2 + n11*l21 + n12*l22);
		if(n3 < 0.5){
			return false;
		}
		else{
			return true;
		}
	}
	public double sigmoid(double sum){
		return 1/(1 + Math.pow(Math.E, (-1*sum)));
	}
}
