package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToOffset;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
import org.usfirst.frc.team2374.robot.commands.grabber.CloseGrabber;
import org.usfirst.frc.team2374.robot.commands.grabber.OpenGrabber;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Tester extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Grabber grabber = Robot.grabber;

	public Tester() {
		requires(drive);
		requires(belt);
		requires(grabber);

		addSequential(new DriveToInch(20.0, DriveToType.SHORT));
		addSequential(new OpenGrabber(), 0.8);
		addSequential(new MoveBeltToOffset());
		addSequential(new DriveToInch(4, DriveToType.VIOLENT));
		addSequential(new DriveToInch(-22, DriveToType.SHORT));
		addParallel(new CloseGrabber());
		addSequential(new MoveBeltToPoint(0));
	}
}
