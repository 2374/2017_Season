package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.CenterBeltOnTarget;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToOffset;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToTarget;
import org.usfirst.frc.team2374.robot.commands.grabber.CloseGrabber;
import org.usfirst.frc.team2374.robot.commands.grabber.OpenGrabber;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Center extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public Center() {
		requires(drive);
		requires(belt);
		requires(grabber);
		requires(camera);

		addSequential(new DriveToTarget(AutoConstants.AUTO_DRIVE_TO_TAR));
		addSequential(new CenterBeltOnTarget());
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_FOWRD, DriveToType.SHORT));
		addSequential(new OpenGrabber(AutoConstants.AUTO_GRABBER_TIMEOUT));
		addSequential(new MoveBeltToOffset());
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_GEAR, DriveToType.VIOLENT));
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_BACK, DriveToType.SHORT));
		addParallel(new CloseGrabber());
		addSequential(new MoveBeltToPoint(0));
	}
}
