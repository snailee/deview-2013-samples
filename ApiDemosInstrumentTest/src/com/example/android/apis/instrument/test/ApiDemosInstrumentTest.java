/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.instrument.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.apis.ApiDemos;
import com.example.android.apis.graphics.kube.Kube;

public class ApiDemosInstrumentTest extends
		ActivityInstrumentationTestCase2<ApiDemos> {

	private static final String TAG = ApiDemosInstrumentTest.class.getSimpleName();

	public ApiDemosInstrumentTest() {
		super(ApiDemos.class);
	}

	private View getChildViewByText(final ListView listView, String text) {
		View view = null;
		int count = listView.getCount();
		Log.e(TAG, "children: " + count);
		for (int i = 0; i < count; i++) {
			TextView textView = (TextView) listView.getChildAt(i);
			Log.e(TAG, i + "-th text view " + textView);
			if (textView != null && textView.getText().equals(text)) {
				view = textView;
				break;
			}
			if (textView == null) {
				final int lastPos = listView.getLastVisiblePosition();
				getInstrumentation().runOnMainSync(new Runnable() {
					public void run() {
						listView.smoothScrollToPosition(lastPos + 5, lastPos);
					}
				});
				getInstrumentation().waitForIdleSync();
				i = listView.getFirstVisiblePosition();
			}
		}
		return view;
	}
	
	public void testNavigate() {
		final ApiDemos apiDemos = getActivity();

		final ListView demoList = apiDemos.getListView();
		final View graphicsDemo = getChildViewByText(demoList, "Graphics");
		assertNotNull("Graphics demo should exist", graphicsDemo);
		apiDemos.runOnUiThread(new Runnable() {
			public void run() {
				int pos = demoList.getPositionForView(graphicsDemo);
				demoList.smoothScrollToPosition(pos);
				demoList.setSelection(pos);
				demoList.performItemClick(graphicsDemo, pos, graphicsDemo.getId());
			}
		});

		Instrumentation.ActivityMonitor graphicsDemoActivityMonitor = 
				getInstrumentation().addMonitor(ApiDemos.class.getName(), null, false);
		final ApiDemos graphicsDemoActivity = 
				(ApiDemos) graphicsDemoActivityMonitor.waitForActivity();
		assertNotNull("Graphics Demo (ApiDemos) activity should not be null", graphicsDemoActivity);
		
		final ListView graphicsDemoList = graphicsDemoActivity.getListView();
		final View openGlesDemo = getChildViewByText(graphicsDemoList, "OpenGL ES");
		assertNotNull("OpenGL ES demo should exist", openGlesDemo);

		graphicsDemoActivity.runOnUiThread(new Runnable() {
			public void run() {
				int pos = graphicsDemoList.getPositionForView(openGlesDemo);
				graphicsDemoList.smoothScrollToPosition(pos);
				graphicsDemoList.setSelection(pos);
				graphicsDemoList.performItemClick(openGlesDemo, pos, openGlesDemo.getId());
			}
		});

		Instrumentation.ActivityMonitor openGlesDemoActivityMonitor = 
				getInstrumentation().addMonitor(ApiDemos.class.getName(), null, false);
		final ApiDemos openGlesDemoActivity = 
				(ApiDemos) openGlesDemoActivityMonitor.waitForActivity();
		assertNotNull("OpenGL ES Demo (ApiDemos) activity should not be null", openGlesDemoActivity);
		
		final ListView openGlesDemoList = openGlesDemoActivity.getListView();
		final View kubeDemo = getChildViewByText(openGlesDemoList, "Kube");
		assertNotNull("Kube demo should exist", kubeDemo);

		openGlesDemoActivity.runOnUiThread(new Runnable() {
			public void run() {
				int pos = openGlesDemoList.getPositionForView(kubeDemo);
				openGlesDemoList.smoothScrollToPosition(pos);
				openGlesDemoList.setSelection(pos);
				openGlesDemoList.performItemClick(kubeDemo, pos, kubeDemo.getId());
			}
		});

		Instrumentation.ActivityMonitor kubeActivityMonitor = 
				getInstrumentation().addMonitor(Kube.class.getName(), null, false);
		final Kube kubeActivity = 
				(Kube) kubeActivityMonitor.waitForActivity();
		assertNotNull("Kube activity should not be null", kubeActivity);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		KeyEvent backKeyUp = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK);
		getInstrumentation().sendKeySync(backKeyUp);
	}
}
