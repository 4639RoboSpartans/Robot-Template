/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

public class OI {
	private final Joystick[] sticks;
	private final Button[][] rawButtons;
	private final Button[][] povButtons;

	// port values of the axes/buttons
	private static final int leftJoystickX = 0, leftJoystickY = 1, leftTrigger = 2, rightTrigger = 3,
			rightJoystickX = 4, rightJoystickY = 5;

	private static double deadzoneValue = 0.01;

	public OI() {
		sticks = new Joystick[2];
		rawButtons = new JoystickButton[6][8];
		povButtons = new POVButton[6][8];
		for (int i = 0; i < sticks.length; i++) {
			sticks[i] = new Joystick(i);
			for (int j = 0; j < rawButtons[i].length; j++) {
				rawButtons[i][j] = new JoystickButton(sticks[i], j);
			}
			for (int j = 0; j < povButtons[i].length; j++) {
				povButtons[i][j] = new POVButton(sticks[i], j * 45);
			}
		}
	}

	/**
	 * Creates a deadzone for joysticks, the controllers sticks are a little loose
	 * and so there is a margin of error for where they should be considered
	 * "neutral/not pushed"
	 *
	 * @param d
	 *            Double between -1 and 1 to be deadzoned
	 * @return The deadzoned value
	 */
	private static double deadzone(double value) {
		// whenever the controller moves LESS than the magic number, the
		// joystick is in the loose position so return zero - as if the
		// joystick was not moved
		if (Math.abs(value) < deadzoneValue) {
			return 0;
		}

		// When the joystick is greater than the margin of error, scale the value so
		// that the point right after the deadzone
		// is 0 so the robot does not jerk forward when it passes the deadzone
		// It properly scales the controls to the new workable zone
		return (value / Math.abs(value)) * ((Math.abs(value) - deadzoneValue) / (1 - deadzoneValue));
	}

	public double getLeftX(int joystick) {
		double valueLeftX = sticks[joystick].getRawAxis(leftJoystickX);
		return deadzone(valueLeftX);
	}

	public double getLeftY(int joystick) {
		double valueLeftY = sticks[joystick].getRawAxis(leftJoystickY);
		return deadzone(-valueLeftY);
	}

	public double getRightX(int joystick) {
		double valueRightX = sticks[joystick].getRawAxis(rightJoystickX);
		return deadzone(valueRightX);
	}

	public double getRightY(int joystick) {
		double valueRightY = sticks[joystick].getRawAxis(rightJoystickY);
		return deadzone(-valueRightY);
	}

	public double getLeftTrigger(int joystick) {
		double valueLeftTrigger = sticks[joystick].getRawAxis(leftTrigger);
		return deadzone(valueLeftTrigger);
	}

	public double getRightTrigger(int joystick) {
		double valueRightTrigger = sticks[joystick].getRawAxis(rightTrigger);
		return deadzone(valueRightTrigger);
	}

	public Button getButton(int joystick, int button) {
		return rawButtons[joystick][button];
	}

	public Button getPovButton(int joystick, int button) {
		return povButtons[joystick][button];
	}

}
