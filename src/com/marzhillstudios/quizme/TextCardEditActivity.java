package com.marzhillstudios.quizme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TextCardEditActivity extends Activity {
	
	public static final String EXTRA_KEY = "CardTextContent";
	
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.plain_text_edit);
		Button saveButton = (Button) findViewById(R.id.saveTextBtn);
		final EditText txtBox = (EditText) findViewById(R.id.defaultEditTextBox);
		Intent intention = getIntent();
		CharSequence currentText = intention.getExtras().getString(EXTRA_KEY);
		txtBox.append(currentText);
		final TextCardEditActivity self = this;
		OnClickListener saveListener = (OnClickListener) new OnClickListener() {
			public void onClick(View editTextView) {
				Intent intent = new Intent();
				intent.putExtra(EXTRA_KEY, txtBox.getText().toString());
				setResult(1, intent);
				self.finish();
			}
		};
		saveButton.setOnClickListener(saveListener);
	}
}
