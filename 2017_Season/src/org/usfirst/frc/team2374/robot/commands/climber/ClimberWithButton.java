package org.usfirst.frc.team2374.robot.commands.climber;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberWithButton extends Command {
	
	Climber climber = Robot.climber;
	
	public ClimberWithButton() {
		requires(climber);
	}
	
	protected void initialize() {
	}
	
	protected void execute() {
		if(Robot.oi.getButtonX()) climber.start();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void end() {
		climber.stop();
	}
	
	protected void interrupted(){
		end();
	}

}
