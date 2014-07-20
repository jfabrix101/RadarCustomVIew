/*
 * Copyright (C) 2013 Yasir Ali Javed
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

package frusso.radartest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import frusso.radartest.R;
import frusso.radartest.RadarView;

/**
 * @author Yasir.Ali
 *
 */
public class MainActivity extends Activity {

    RadarView mRadarView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mRadarView = (RadarView) findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
	}


    public void stopAnimation(View view) {
        if (mRadarView != null) mRadarView.stopAnimation();
    }

    public void startAniamtion(View view) {
        if (mRadarView != null) mRadarView.startAnimation();
    }
}
