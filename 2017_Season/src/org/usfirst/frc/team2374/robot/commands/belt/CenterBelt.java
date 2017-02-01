package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;
// passed peer review
// need to account for offset
public class CenterBelt extends Command{
	
	private int initialPos;
	
	public CenterBelt(){
		requires(Robot.belt);
		requires(Robot.camera);
	}
	
	protected void initialize(){
		initialPos = Robot.camera.pixelsToCenter();
		Robot.belt.setPIDSpeed(Belt.MAX_BELT_SPEED);
		Robot.belt.setPIDSetpoint(0);
		Robot.belt.enablePID(true);
	}
	
	protected void execute() {
		Robot.belt.setBelt(Robot.belt.getPIDOutput());
	}
	
	@Override
	protected boolean isFinished() {
		if (Robot.belt.isAtLimit())
			return true;
		if(initialPos<0){
			return Robot.camera.pixelsToCenter() >= 0;
		}
		return Robot.camera.pixelsToCenter() <= 0;
	}
	
	protected void end() {
		Robot.belt.enablePID(false);
		Robot.belt.setBelt(0);
	}
	
	protected void interrupted(){
		end();
	}

}
