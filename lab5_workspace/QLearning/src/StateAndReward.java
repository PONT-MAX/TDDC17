public class StateAndReward {
	

	
	public static String getStateAngle(double angle, double vx, double vy) {
		
		double M_PI = 3.141592653;
		String state;
		/* TODO: IMPLEMENT THIS FUNCTION */
		if(angle < 0){
			if(angle > -M_PI/6.0)
				state = "l1";
			else if(angle > -M_PI*2.0/6.0)
				state = "l2";
			else if(angle > -M_PI*3.0/6.0)
				state = "l3";
			else if(angle > -M_PI*4.0/6.0)
				state = "l4";
			else if(angle > -M_PI*5.0/6.0)
				state = "l5";
			else	
				state = "l6";
		}
		else{

			if(angle < M_PI/6.0)
				state = "r1";
			else if(angle < M_PI*2.0/6.0)
				state = "r2";
			else if(angle < M_PI*3.0/6.0)
				state = "r3";
			else if(angle < M_PI*4.0/6.0)
				state = "r4";
			else if(angle < M_PI*5.0/6.0)
				state = "r5";
			else	
				state = "r6";
		}
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		double M_PI = 3.141592653;
		double reward;
		
		if(angle < 0){
			if(angle > -M_PI/12.0)
				reward = 30;
			else if(angle > -M_PI/6.0)
				reward = 20;
			else if(angle > -M_PI*2.0/6.0)
				reward = 10;
			else if(angle > -M_PI*3.0/6.0)
				reward = 5;
			else if(angle > -M_PI*4.0/6.0)
				reward = 2;
			else if(angle > -M_PI*5.0/6.0)
				reward = 1;
			else	
				reward = 0;
		}
		else{
			if(angle < M_PI/12.0)
				reward = 30;
			else if(angle < M_PI/6.0)
				reward = 20;
			else if(angle < M_PI*2.0/6.0)
				reward = 10;
			else if(angle < M_PI*3.0/6.0)
				reward = 5;
			else if(angle < M_PI*4.0/6.0)
				reward = 2;
			else if(angle < M_PI*5.0/6.0)
				reward = 1;
			else	
				reward = 0;
		}
		

		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		String state;
		/* TODO: IMPLEMENT THIS FUNCTION */
		if(vy < 0){
			if(vy > -0.5)
				state = "u1";
			else if(vy > -1)
				state = "u2";
			else if(vy > -2)
				state = "u3";
			else if(vy > -3)
				state = "u4";
			else if(vy > -5)
				state = "u5";
			else
				state = "u6";
		}
		else{
			if(vy < 0.5)
				state = "d1";
			else if(vy < 1)
				state = "d2";
			else if(vy < 2)
				state = "d3";
			else if(vy < 3)
				state = "d4";
			else if(vy < 5)
				state = "d5" ;
			else
				state = "d6";
		}
		return state;	/* TODO: IMPLEMENT THIS FUNCTION */
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		if(vy < 0){
			if(vy > -0.5)
				reward = 30;
			else if(vy > -1)
				reward = 20;
			else if(vy > -2)
				reward = 10;
			else if(vy > -3)
				reward = 5;
			else if(vy > -5)
				reward = 3;
			else
				reward  = -10;
		}
		else{
			if(vy < 0.5)
				reward = 30;
			else if(vy < 1)
				reward = 20;
			else if(vy < 2)
				reward = 10;
			else if(vy < 3)
				reward = 5;
			else if(vy < 5)
				reward = 3;
			else
				reward = -10;
		}
		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
									double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
