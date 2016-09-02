package tddc17;


import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.util.Random;

class MyAgentState
{
	public int[][] world = new int[20][20];
	public int initialized = 0;
	final int UNKNOWN 	= 0;
	final int WALL 		= 1;
	final int CLEAR 	= 2;
	final int DIRT		= 3;
	final int HOME		= 4;
	final int ACTION_NONE 			= 0;
	final int ACTION_MOVE_FORWARD 	= 1;
	final int ACTION_TURN_RIGHT 	= 2;
	final int ACTION_TURN_LEFT 		= 3;
	final int ACTION_SUCK	 		= 4;

	public int AI_STATE	 		= 0;  
	final int FIND_WALL 		= 0;
	final int FIND_UNKNOWN 		= 1;
	final int FIND_HOME	 		= 2;

	public int agent_x_position = 1;
	public int agent_y_position = 1;

	public int agent_x_start = 0;
	public int agent_y_start = 0;

	public int agent_last_action = ACTION_NONE;
	public int agent_last_turn = ACTION_NONE;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;

	MyAgentState()
	{
		for (int i=0; i < world.length; i++)
			for (int j=0; j < world[i].length ; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}
	// Based on the last action and the received percept updates the x & y agent position
	public void updatePosition(DynamicPercept p)
	{
		Boolean bump = (Boolean)p.getAttribute("bump");

		if (agent_last_action==ACTION_MOVE_FORWARD && !bump)
		{
			switch (agent_direction) {
			case MyAgentState.NORTH:
				agent_y_position--;
				break;
			case MyAgentState.EAST:
				agent_x_position++;
				break;
			case MyAgentState.SOUTH:
				agent_y_position++;
				break;
			case MyAgentState.WEST:
				agent_x_position--;
				break;
			}
		}

	}

	public void updateWorld(int x_position, int y_position, int info)
	{
		world[x_position][y_position] = info;
	}

	public void printWorldDebug()
	{
		for (int i=0; i < world.length; i++)
		{
			for (int j=0; j < world[i].length ; j++)
			{
				if (world[j][i]==UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i]==WALL)
					System.out.print("# ");
				if (world[j][i]==CLEAR)
					System.out.print(" . ");
				if (world[j][i]==DIRT)
					System.out.print(" D ");
				if (world[j][i]==HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}
}

class MyAgentProgram implements AgentProgram {

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();
	public Boolean first_bump = false;
	// Here you can define your variables!
	public int iterationCounter = 10;
	public MyAgentState state = new MyAgentState();

	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if(action==0) {
			state.agent_direction = ((state.agent_direction-1) % 4);
			if (state.agent_direction<0) 
				state.agent_direction +=4;
			state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action==1) {
			state.agent_direction = ((state.agent_direction+1) % 4);
			state.agent_last_action = state.ACTION_TURN_RIGHT;
			return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		} 
		state.agent_last_action=state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}


	@Override
	public Action execute(Percept percept) {

		// DO NOT REMOVE this if condition!!!
		if (initnialRandomActions>0) {
			return moveToRandomStartPosition((DynamicPercept) percept);
		} else if (initnialRandomActions==0) {
			// process percept for the last step of the initial random actions
			initnialRandomActions--;
			state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action=state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		}

		// This example agent program will update the internal agent state while only moving forward.
		// START HERE - code below should be modified!

		System.out.println("x=" + state.agent_x_position);
		System.out.println("y=" + state.agent_y_position);
		System.out.println("x_s=" + state.agent_x_start);
		System.out.println("y_s=" + state.agent_y_start);
		System.out.println("dir=" + state.agent_direction);


		iterationCounter--;

		if (iterationCounter+1000==0)
			return NoOpAction.NO_OP;

		DynamicPercept p = (DynamicPercept) percept;
		Boolean bump = (Boolean)p.getAttribute("bump");
		Boolean dirt = (Boolean)p.getAttribute("dirt");
		Boolean home = (Boolean)p.getAttribute("home");
		System.out.println("percept: " + p);

		// State update based on the percept value and the last action
		state.updatePosition((DynamicPercept)percept);
		if (bump) {
			switch (state.agent_direction) {
			case MyAgentState.NORTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position-1,state.WALL);
				break;
			case MyAgentState.EAST:
				state.updateWorld(state.agent_x_position+1,state.agent_y_position,state.WALL);
				break;
			case MyAgentState.SOUTH:
				state.updateWorld(state.agent_x_position,state.agent_y_position+1,state.WALL);
				break;
			case MyAgentState.WEST:
				state.updateWorld(state.agent_x_position-1,state.agent_y_position,state.WALL);
				break;
			}
		}
		if (dirt)
			state.updateWorld(state.agent_x_position,state.agent_y_position,state.DIRT);
		else
			state.updateWorld(state.agent_x_position,state.agent_y_position,state.CLEAR);

		state.printWorldDebug();


		// Next action selection based on the percept value
		if (dirt)
		{
			System.out.println("DIRT -> choosing SUCK action!");
			state.agent_last_action=state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		} 
		else
		{
			if (bump)
			{
				state.agent_last_action=state.ACTION_TURN_LEFT;
				state.agent_last_turn=state.ACTION_TURN_LEFT;
				state.agent_direction = ((((state.agent_direction-1) % 4) + 4) % 4);
				if(!first_bump){
					state.agent_x_start = state.agent_x_position;
					state.agent_y_start = state.agent_y_position;
					first_bump = true;
				}
				return LIUVacuumEnvironment.ACTION_TURN_LEFT;
			}
			else
			{
				if(state.AI_STATE == state.FIND_WALL){
					if(state.agent_x_position == state.agent_x_start &&
							state.agent_y_position == state.agent_y_start){


						int ww = 100, ew = 0, nw = 100, sw = 0;

						for(int i = 0; i < state.world.length; ++i ){
							for(int j = 0; j < state.world[0].length; ++j){

								if(state.world[i][j] == 1){
									if(i < ww)
										ww = i;
									if(i > ew)
										ew = i;
									if(j < nw)
										nw = j;
									if(j > sw)
										sw = j;
								}

							}
						}

						int WALL_width = Math.abs(ww-ew);
						int WALL_hight = Math.abs(nw-sw);

						System.out.println("WEST WALL x-led = " + ww);
						System.out.println("EAST WALL x-led = " + ew);
						System.out.println("N WALL y-led = " + nw);
						System.out.println("S WALL y-led = " + sw);

						System.out.println("WALL Width = " + WALL_width);
						System.out.println("WALL hight = " + WALL_hight);

						if(WALL_width > 3 && WALL_hight > 3){

							System.out.println("HITTAT HELA KONTUREN AV BANAN");
							state.AI_STATE++;
							//return NoOpAction.NO_OP;
						}

					}

					if(state.AI_STATE == 0){
						if(state.agent_last_action == state.ACTION_TURN_LEFT){
							System.out.println("Last LEFT, Now FORWARD");
							state.agent_last_action=state.ACTION_MOVE_FORWARD;
							return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
						}
						else if(state.agent_last_turn==state.ACTION_TURN_LEFT ||
								(state.agent_last_action==state.ACTION_MOVE_FORWARD && first_bump)){
							System.out.println("Right undiscoverd, Go right");
							state.agent_direction  = ((((state.agent_direction+1) % 4) + 4) % 4);
							state.agent_last_turn=state.ACTION_TURN_RIGHT;
							state.agent_last_action=state.ACTION_TURN_RIGHT;
							return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
						}
						else{
							System.out.println("Move Forward");
							state.agent_last_action=state.ACTION_MOVE_FORWARD;
							return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
						}
					}
				}
				if(state.AI_STATE == state.FIND_UNKNOWN){
					
					//Sök kod
					
					return NoOpAction.NO_OP;
				}
				else{
					return NoOpAction.NO_OP;
				}
			}
		}
	}
}

public class MyVacuumAgent extends AbstractAgent {
	public MyVacuumAgent() {
		super(new MyAgentProgram());
	}
}
