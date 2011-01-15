/**
 * Copyright (C) 2011 Jeremy Wall <jeremy@marzhillstudios.com>
 * All contents Licensed under the terms of the Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.marzhillstudios.teachme;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Jeremy Wall <jeremy@marzhillstudios.com>
 * 
 * Activity for managing cards. This Activity will be responsible for
 * handling Card Creation/Editing/Deletion and Import/Export.
 */
public class CardManagerActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.cardmanager);
	}
}
