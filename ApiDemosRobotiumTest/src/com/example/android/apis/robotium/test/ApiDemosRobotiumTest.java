package com.example.android.apis.robotium.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.android.apis.ApiDemos;
import com.example.android.apis.graphics.kube.Kube;
import com.jayway.android.robotium.solo.Solo;

public class ApiDemosRobotiumTest extends
		ActivityInstrumentationTestCase2<ApiDemos> {

	private static final String TAG = ApiDemosRobotiumTest.class.getSimpleName();
	
	private Solo solo;

	public ApiDemosRobotiumTest() {
		super(ApiDemos.class);
	}

	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testNavigate() {
		solo.assertCurrentActivity("Should be on ApiDemos", ApiDemos.class);

		solo.clickOnText("^Graphics$", 1, true);
		solo.assertCurrentActivity("Should be on a new ApiDemos", ApiDemos.class, true);

		solo.clickOnText("^OpenGL ES$", 1, true);
		solo.assertCurrentActivity("Should be on a new ApiDemos", ApiDemos.class, true);
		
		solo.clickOnText("^Kube$", 1, true);
		solo.assertCurrentActivity("Should be on a CubeMapActivity", Kube.class, true);
		
		solo.sleep(3000);
	}
}
