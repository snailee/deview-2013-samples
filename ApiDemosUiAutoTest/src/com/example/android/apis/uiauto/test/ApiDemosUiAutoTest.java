package com.example.android.apis.uiauto.test;

import android.widget.TextView;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class ApiDemosUiAutoTest extends UiAutomatorTestCase {   

	public void testNavigate() throws UiObjectNotFoundException {
		getUiDevice().pressHome();

		UiObject allAppsButton = new UiObject(
				new UiSelector().description("Apps"));
		allAppsButton.clickAndWaitForNewWindow();

		UiObject appsTab = new UiObject(
				new UiSelector().text("Apps"));
		appsTab.click();

		UiScrollable appViews = new UiScrollable(
				new UiSelector().scrollable(true));
		appViews.setAsHorizontalList();
		UiObject apiDemos = appViews.getChildByText(
				new UiSelector().className(TextView.class.getName()), "API Demos");
		apiDemos.clickAndWaitForNewWindow();

		UiObject apiDemosPackage = new UiObject(new UiSelector().packageName("com.example.android.apis"));
		assertTrue("Should be on ApiDemos", apiDemosPackage.exists());   

		UiScrollable demosList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		
		UiObject graphicsDemo = demosList.getChildByText(
				new UiSelector().className(TextView.class.getName()), "Graphics");
		graphicsDemo.clickAndWaitForNewWindow();

		UiObject alphaBitmapDemo = new UiObject(new UiSelector().text("AlphaBitmap"));
		assertTrue("AlphaBitmap should be visible", alphaBitmapDemo.exists());   

		UiScrollable graphicsDemoList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		
		UiObject openGlesDemo = graphicsDemoList.getChildByText(
				new UiSelector().className(TextView.class.getName()), "OpenGL ES");
		openGlesDemo.clickAndWaitForNewWindow();

		// Validate that the package name is the expected one
		UiObject compressedTextureDemo = new UiObject(new UiSelector().text("Compressed Texture"));
		assertTrue("Compressed Texture should be visible", compressedTextureDemo.exists());   

		UiScrollable openGlesDemoList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		
		UiObject kubeDemo = openGlesDemoList.getChildByText(
				new UiSelector().className(TextView.class.getName()), "Kube");
		kubeDemo.clickAndWaitForNewWindow();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		getUiDevice().pressBack();
		getUiDevice().pressBack();
		getUiDevice().pressBack();
		getUiDevice().pressBack();
	}   
}
