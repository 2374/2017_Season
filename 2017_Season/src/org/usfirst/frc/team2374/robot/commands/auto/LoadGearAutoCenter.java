package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.CenterBeltOnTargetCenter;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToOffset;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
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
public class LoadGearAutoCenter extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public LoadGearAutoCenter() {
		requires(drive);
		requires(belt);
		requires(grabber);
		requires(camera);

		addSequential(new CenterBeltOnTargetCenter());
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_FOWRD, DriveToType.SHORT,
				AutoConstants.AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT));
		addSequential(new OpenGrabber(AutoConstants.AUTO_GRABBER_TIMEOUT));
		addSequential(new MoveBeltToOffset());
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_GEAR, DriveToType.VIOLENT,
				AutoConstants.AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT));
		addSequential(new DriveToInch(AutoConstants.AUTO_DRIVE_TO_INCH_BACK, DriveToType.SHORT));
		addParallel(new CloseGrabber());
		addSequential(new MoveBeltToPoint(0));
	}

}
