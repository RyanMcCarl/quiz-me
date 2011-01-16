/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.quizme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * Start Activity for the TeachMe application.
 * 
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 *
 */
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context mainCtx = this;
        Button quizLaunchBtn = (Button)findViewById(R.id.LaunchQuizBtn);
        Button managerLaunchBtn = (Button)findViewById(R.id.launchManagerBtn);
        
        OnClickListener quizBtnListener = new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(mainCtx, QuizActivity.class));
			}
		};
		
		OnClickListener manageBtnListener = new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(mainCtx, CardManagerActivity.class));
			}
		};
		
		quizLaunchBtn.setOnClickListener(quizBtnListener);
		managerLaunchBtn.setOnClickListener(manageBtnListener);
    }
}