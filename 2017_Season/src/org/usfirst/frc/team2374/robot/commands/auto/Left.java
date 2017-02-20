package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.CenterBeltOnTarget;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.drivetrain.CorrectForAngle;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInchLong;
import org.usfirst.frc.team2374.robot.commands.drivetrain.TurnToDegree;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Left extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;
	private double inchesToTarget = 60;// CHANGE THIS LATER
	private int feetToEnd = 45;// CHANGE THIS LATER
	private int correctionInches = 4;// CHANGE THIS LATER
	private final int INCHES_TO_FEET = 12;

	public Left() {
		requires(drive);
		requires(belt);
		requires(camera);
		requires(grabber);

		// drive forward
		// addSequential(new DriveToInch(inchesToTarget,
		// Drivetrain.MAX_AUTO_SPEED));
		// turn right 60 degrees
		addSequential(new TurnToDegree(60));
		// do we have a valid target?
		if (camera.isReal()) {
			// correct for angle
			for (int i = 0; i < 1; i += 0) {
				addSequential(new CorrectForAngle(1));
				addSequential(new DriveToInchLong(correctionInches));
				if (Math.abs(camera.compareAreas()) <= 20)
					i++;
			}
			// align gear with peg
			addSequential(new CenterBeltOnTarget());
			// drive to target
			// addSequential(new DriveStraightWithVision());
			// release gear
			// addSequential(new OpenGrabber());
			// back up
			addSequential(new DriveToInchLong(-2 * INCHES_TO_FEET));
			// turn left
			addSequential(new TurnToDegree(-60));
			// drive to other end of field, close grabber and center belt
			addParallel(new DriveToInchLong(feetToEnd * INCHES_TO_FEET));
			// addParallel(new CloseGrabber());
			addSequential(new MoveBeltToPoint(0));
		}
		// if not just cross the base line, don't drop the gear, and stay in the
		// same general area as the target
		else {
			// addSequential(new TurnToDegree(0));
			addSequential(new DriveToInchLong(3 * INCHES_TO_FEET));
		}
	}
}
