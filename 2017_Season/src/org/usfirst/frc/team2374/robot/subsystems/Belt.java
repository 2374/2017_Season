package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.util.SimplePIDOutput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Belt extends Subsystem {

	private SpeedController beltController;
	private Encoder beltEncoder;
	private DigitalInput leftLimitSwitch, rightLimitSwitch;
	
	private PIDController beltPID;
	private SimplePIDOutput beltOut;
	private static final double beltP = 0;
	private static final double beltI = 0;
	private static final double beltD = 0;
	
	private static final double MAX_BELT_SPEED = 0.5;
	
	//direction reference:
	//		front
	// left		  right
	//		back
	
	public Belt() {
		beltController = new Spark(RobotMap.speedControllerBelt);
		beltEncoder = new Encoder(RobotMap.encoderBeltA, RobotMap.encoderBeltB);
		leftLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltLeft);
		rightLimitSwitch = new DigitalInput(RobotMap.limitSwitchBeltRight);
		
		beltEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		beltPID = new PIDController(beltP, beltI, beltD, beltEncoder, beltOut);
		beltPID.setSetpoint(0);
		beltPID.setOutputRange(-MAX_BELT_SPEED, MAX_BELT_SPEED);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //negative speed is left and positive speed is right
  	public boolean setBelt(double speed) {
  		if (speed < 0 && leftLimitSwitch.get())
  			return false;
  		else if (speed > 0 && rightLimitSwitch.get())
  			return false;
  		beltController.set(Math.min(speed, MAX_BELT_SPEED));
  		return true;
  	}
  	
  	public void setPIDPosition(double setpoint) {
  		beltPID.setSetpoint(setpoint);
  	}
  	
  	public void enablePID() {
  		beltPID.enable();
  	}
  	
  	public void disablePID() {
  		beltPID.disable();
  	}
}

