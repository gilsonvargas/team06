package cl.devpool.service;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

public class MotorController {

	private PwmOutput MotorAPwm;
	private PwmOutput MotorBPwm;

	private DigitalOutput MotorA1;
	private DigitalOutput MotorA2;
	private DigitalOutput MotorB1;
	private DigitalOutput MotorB2;

	private IOIO iioio;
	private final Integer freq = 6000;
	private final Float dutyCycle = 0.5f;

	public MotorController(IOIO iioio) {
		this.iioio = iioio;
	}

	public void Motor_Init(DigitalOutput.Spec MotorAPwm,
			DigitalOutput.Spec MotorBPwm, DigitalOutput MotorA1,
			DigitalOutput MotorA2, DigitalOutput MotorB1, DigitalOutput MotorB2)
			throws ConnectionLostException {
		this.MotorAPwm = iioio.openPwmOutput(MotorAPwm, freq);
		this.MotorBPwm = iioio.openPwmOutput(MotorBPwm, freq);
		this.MotorA1 = MotorA1;
		this.MotorA2 = MotorA2;
		this.MotorB1 = MotorB1;
		this.MotorB2 = MotorB2;
		this.Change_Duty(dutyCycle);
	}

	/********** Go Forward 
	 * @throws ConnectionLostException ************/
	public void Forward(Float speed) throws ConnectionLostException {
	    Change_Duty(speed);
	    Motor_A_FWD();
	    Motor_B_FWD();
	}

	/************************************/

	/********** Go Backward ************/
	public void Backward(Float speed) throws ConnectionLostException {
	    Change_Duty(speed);
	    Motor_A_BWD();
	    Motor_B_BWD();
	}

	/************************************/

	/********** Spin Left *************/
	public void S_Right(Float speed) throws ConnectionLostException {
	    Change_Duty(speed);
	    Motor_A_FWD();
	    Motor_B_BWD();
	}

	/************************************/

	/********** Spin Right ************/
	public void S_Left(Float speed) throws ConnectionLostException {
	    Change_Duty(speed);
	    Motor_A_BWD();
	    Motor_B_FWD();
	}

	/************************************/

	/********** Stop Motor ************/
	public void Motor_Stop() throws ConnectionLostException {
		this.MotorAPwm.close();
		this.MotorBPwm.close();
		this.Motor_A_Off();
		this.Motor_B_Off();
	}
	/************************************/
	
	/********** Motor A Forward  ************/
	private void Motor_A_FWD() throws ConnectionLostException {
		this.MotorA1.write(false);
		this.MotorA2.write(true);
	}
	/************************************/

	/********** Motor B Forward  ********/
	private void Motor_B_FWD() throws ConnectionLostException {
		this.MotorB1.write(false);
		this.MotorB2.write(true);
	}
	/************************************/

	/********** Motor A Backward  *******/
	private void Motor_A_BWD() throws ConnectionLostException {
		this.MotorA1.write(true);
		this.MotorA2.write(false);
	}
	/************************************/

	/********** Motor B Backward  *******/
	private void Motor_B_BWD() throws ConnectionLostException {
		this.MotorB1.write(true);
		this.MotorB2.write(false);
	}
	/************************************/

	/********** Motor A Off  ************/
	private void Motor_A_Off() throws ConnectionLostException {
		this.MotorA1.write(false);
		this.MotorA2.write(false);
	}
	/************************************/

	/********** Motor B Off  ************/
	private void Motor_B_Off() throws ConnectionLostException {
		this.MotorB1.write(false);
		this.MotorB2.write(false);
	}
	
	//****************************************************
	//********** Control Duty Cycle  *********************
	//****************************************************
	private void Change_Duty(float speed) throws ConnectionLostException {
		this.MotorAPwm.setDutyCycle(dutyCycle);
		this.MotorBPwm.setDutyCycle(dutyCycle);
	}
	//****************************************************
}
