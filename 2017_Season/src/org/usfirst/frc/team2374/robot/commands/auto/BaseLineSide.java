package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BaseLineSide extends CommandGroup {

	public BaseLineSide() {
		requires(Robot.drivetrain);

		addSequential(new DriveToInch(AutoConstants.BASE_LINE_SIDE, DriveToType.LONG));
	}
}
