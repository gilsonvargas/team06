package cl.devpool.service;

import com.codebutler.android_websockets.WebSocketClient;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.DigitalOutput.Spec.Mode;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;

public class RoverBrainService extends IOIOService {

	protected static final String TAG = "RoverBrainService";
	private final RoverBrainServiceBinder binder = new RoverBrainServiceBinder();
	private MotorController roverMotor;
	private WebSocketClient wsClient;
	private String roverName;
	private String addressServer;
	private String RemoteControlCommand = "";

	/* Control de Hardware */
	private boolean internalLedStatus = true;
	private boolean redLedStatus = true;
	private boolean blueLedStatus = true;
	private boolean greenLedStatus = true;

	private float temperatureValue = 0;
	private float photocellValue = 0;

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new BaseIOIOLooper() {

			private final int MotorAPwm_PIN = 34;
			private final int MotorBPwm_PIN = 35;
			private final int MotorA1_PIN = 40;
			private final int MotorA2_PIN = 41;
			private final int MotorB1_PIN = 42;
			private final int MotorB2_PIN = 42;
			private final int IRSensor_PIN = 42;
			private final int LightSensor_PIN = 42;

			private DigitalOutput.Spec MotorAPwmSpec;
			private DigitalOutput.Spec MotorBPwmSpec;

			private DigitalOutput MotorA1;
			private DigitalOutput MotorA2;
			private DigitalOutput MotorB1;
			private DigitalOutput MotorB2;
			
			private AnalogInput IRSensor;
			private AnalogInput LightSensor;

			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {
				roverMotor = new MotorController(ioio_);
				
				MotorAPwmSpec = new DigitalOutput.Spec(MotorAPwm_PIN, Mode.OPEN_DRAIN);
				MotorBPwmSpec = new DigitalOutput.Spec(MotorBPwm_PIN, Mode.OPEN_DRAIN);
				
				MotorA1 = ioio_.openDigitalOutput(MotorA1_PIN, Mode.OPEN_DRAIN, false);
				MotorA2 = ioio_.openDigitalOutput(MotorA2_PIN, Mode.OPEN_DRAIN, false);
				MotorB1 = ioio_.openDigitalOutput(MotorB1_PIN, Mode.OPEN_DRAIN, false);
				MotorB2 = ioio_.openDigitalOutput(MotorB2_PIN, Mode.OPEN_DRAIN, false);

				roverMotor.Motor_Init(MotorAPwmSpec, MotorBPwmSpec, MotorA1, MotorA2, MotorB1, MotorB2);
				
				IRSensor = ioio_.openAnalogInput(IRSensor_PIN);
				LightSensor = ioio_.openAnalogInput(LightSensor_PIN);
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {


				if(RemoteControlCommand.equals("Move")) {
					
				} else if(RemoteControlCommand.equals("Move Back")){
					
				}else if(RemoteControlCommand.equals("Turn Left")){
					
				}else if(RemoteControlCommand.equals("Turn Right")){
					
				}else if(RemoteControlCommand.equals("Stop")){
					
				}
				

				Thread.sleep(20);
			}
		};
	}

	/* __Fin Control Hardware__ */

	@Override
	public void onCreate() {
		super.onCreate();
		List<BasicNameValuePair> extraHeaders = Arrays
				.asList(new BasicNameValuePair("Cookie", "session=abcd"));
		wsClient = new WebSocketClient(URI.create("ws://" + this.addressServer
				+ "/RoverControlServer/service/rover?roverName="
				+ this.roverName), new RoverConnectionListener(), extraHeaders);
	}

	@Override
	public void onDestroy() {
		this.wsClient.disconnect();
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		this.wsClient.connect();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return this.binder;
	}

	public void sendWSMessage(String message) {
		if (this.wsClient.isConnected()) {
			this.wsClient.send(message);
		}
	}

	public void setRoverName(String roverName) {
		this.roverName = roverName;
	}

	public void setServerAddress(String serverAddress) {
		this.addressServer = serverAddress;
	}

	/* Web Sockets Events */
	private class RoverConnectionListener implements WebSocketClient.Listener {

		@Override
		public void onConnect() {
			Log.d(TAG, "Connected!");
		}

		@Override
		public void onMessage(String message) {
			Log.d(TAG, String.format("Got string message! %s", message));
			 RemoteControlCommand = message;
		}

		@Override
		public void onMessage(byte[] data) {
			Log.e(TAG, "Binary data not supported");
		}

		@Override
		public void onDisconnect(int code, String reason) {
			Log.d(TAG, "Disconnected!");
			try {
				roverMotor.Motor_Stop();
			} catch (ConnectionLostException e) {
				Log.e(TAG, "Error to Sopping Rover", e);
			}
		}

		@Override
		public void onError(Exception error) {
			Log.e(TAG, "Error!", error);
			try {
				roverMotor.Motor_Stop();
			} catch (ConnectionLostException e) {
				Log.e(TAG, "Error to Sopping Rover", e);
			}
		}
	}

	/* -- Fin WebSockets Events -- */

	/**
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	public class RoverBrainServiceBinder extends Binder {
		public RoverBrainService getService() {
			// Return this instance of IOHttpService so clients can call public
			// methods
			return RoverBrainService.this;
		}
	}
}
