package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import ro.pub.cs.systems.eim.lab04.contactsmanager.general.Constants;


public class ContactsManagerActivity extends AppCompatActivity {

    private Button additionalFieldsButton, saveButton, cancelButton;

    private EditText nameEditText, phoneNumberEditText, emailEditText, addressEditText, jobTitleEditText, companyEditText;

    private LinearLayout additionalFieldsContainer;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String name = nameEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String jobTitle = jobTitleEditText.getText().toString();
            String company = companyEditText.getText().toString();


            switch (view.getId()) {
                case R.id.save_fields_button:

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phoneNumber != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }

                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
//                    startActivity(intent);
                    break;

                case R.id.cancel_fields_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;

                case R.id.additional_fields_button:

                    if (additionalFieldsContainer.getVisibility() == View.INVISIBLE) {
                        additionalFieldsContainer.setVisibility(View.VISIBLE);
                        additionalFieldsButton.setText(getResources().getString(R.string.hide_additional_fields));
                    }
                    else if (additionalFieldsContainer.getVisibility() == View.VISIBLE) {
                        additionalFieldsContainer.setVisibility(View.INVISIBLE);
                        additionalFieldsButton.setText(getResources().getString(R.string.show_additional_fields));
                    }

                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        additionalFieldsButton = (Button) findViewById(R.id.additional_fields_button);
        additionalFieldsButton.setOnClickListener(buttonClickListener);
        saveButton = (Button) findViewById(R.id.save_fields_button);
        saveButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button) findViewById(R.id.cancel_fields_button);
        cancelButton.setOnClickListener(buttonClickListener);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        addressEditText = (EditText) findViewById(R.id.address_edit_text);
        jobTitleEditText = (EditText) findViewById(R.id.jobTitle_edit_text);
        companyEditText = (EditText) findViewById(R.id.company_edit_text);

        additionalFieldsContainer = (LinearLayout) findViewById(R.id.additional_fields_container);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra(Constants.PHONE_NUMBER_KEY);
            if (phone != null) {
                phoneNumberEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_number_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
