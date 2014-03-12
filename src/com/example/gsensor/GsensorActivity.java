package com.example.gsensor;

import java.util.Arrays;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GsensorActivity extends Activity implements SensorEventListener {
	private TextView mGsensorContent;
	private TextView mGsensorStep;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private boolean mShootValue = false;

	private int []mResult = new int[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gsensor);

		mGsensorContent = (TextView) findViewById(R.id.TextViewResult);

		mSensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);   
		//Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY); 
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	}

	public void onClickTextView(View view){
		mGsensorStep = (TextView) view;
		mShootValue = true;
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		Math.floor(2.3);

		if(mShootValue){
			int index = Arrays.binarySearch(ids, mGsensorStep.getId());

			if(index != 3){
				mGsensorStep.setText(String.valueOf(event.values[0]));
				mGsensorStep.append("   ");
				mGsensorStep.append(String.valueOf(event.values[1]));
				mGsensorStep.append("   ");
				mGsensorStep.append(String.valueOf(event.values[2]));

				mResult[index] = get(event.values);
				
				mGsensorContent.setText("ABS_X ABS_Y ABS_Z\n        a           b           c\n        ");
				mGsensorContent.append(signs[mResult[0]]);
				mGsensorContent.append("           ");
				mGsensorContent.append(signs[mResult[1]]);
				mGsensorContent.append("           ");
				mGsensorContent.append(signs[mResult[2]]);
				
			}
			mShootValue = false;
		}
	}

	private int[] ids = {
			R.id.TextViewStep1,
			R.id.TextViewStep2,
			R.id.TextViewStep3,
			R.id.TextViewResult,
	};
	
	private String[] signs = {
			"-c",
			"-b",
			"-a",
			"a",
			"b",
			"c",
	};

	private int get(float[]value){
		double []mValue = new double[3];
		int max = 0;

		for ( int i = 0; i < 3; i++ ) {
			mValue[i] = Math.abs(Math.floor(value[i]));
		}

		for ( int i = 1; i < 3; i++ ) {
			if( mValue[max] < mValue[i] ){
				max = i;
			}
		}

		if(value[max]<0){
			max = -1 - max;
		}

		max += 3;
		
		return max;
	}
}
