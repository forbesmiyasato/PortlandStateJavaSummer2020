package edu.pdx.cs410j.miyasato.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
  private PhoneBillModel phoneBillModel;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        Snackbar.make(view, "Go to Menu -> README for usage help!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });

    try
    {
      phoneBillModel = new PhoneBillModel(getApplicationContext());
    } catch (IOException | ClassNotFoundException e)
    {
      Log.e("Error", e.getMessage());
      displayToastMessage(e.getMessage());
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_readme)
    {
      onReadme();
    }

    return true;
  }

  public void onReadme()
  {
    startActivity(new Intent(this,
            Readme.class));
  }

  public void addPhoneBillClicked(View cView)
  {
    final View addLayout = LayoutInflater.from
            (MainActivity.this).inflate(R.layout.activity_add_phone_call,
            null);

    new MaterialStyledDialog.Builder(MainActivity.this)
            .setIcon(R.drawable.ic_phonebill_foreground)
            .setTitle("Add a Phone Bill")
            .autoDismiss(false)
            .setDescription("Please fill all fields")
            .setCustomView(addLayout)
            .setNegativeText("CANCEL")
            .onNegative(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {
                dialog.dismiss();
              }
            })
            .setPositiveText("ADD")
            .onPositive(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {
                MaterialEditText addCustomerName = addLayout.findViewById
                        (R.id.add_customer_name);
                MaterialEditText addCaller = addLayout.findViewById
                        (R.id.add_caller);
                MaterialEditText addCallee = addLayout.findViewById
                        (R.id.add_callee);
                MaterialEditText addStartTime = addLayout.findViewById
                        (R.id.add_start_time);
                MaterialEditText addEndTime = addLayout.findViewById
                        (R.id.add_end_time);

                if (fieldIsEmpty(addCustomerName)) return;
                if (fieldIsEmpty(addCaller)) return;
                if (fieldIsEmpty(addCallee)) return;
                if (fieldIsEmpty(addStartTime)) return;
                if (fieldIsEmpty(addEndTime)) return;

                String customerName = Objects.requireNonNull(addCustomerName.getText()).toString();
                String caller = Objects.requireNonNull(addCaller.getText()).toString();
                String callee = Objects.requireNonNull(addCallee.getText()).toString();
                String startTime = Objects.requireNonNull(addStartTime.getText()).toString();
                String endTime = Objects.requireNonNull(addEndTime.getText()).toString();

                PhoneCall phoneCall = null;
                try
                {
                  phoneCall = new PhoneCall(caller, callee, startTime, endTime);
                } catch (IllegalArgumentException e)
                {
                  displayToastMessage(e.getMessage());
                  return;
                }

                try
                {
                  phoneBillModel.addPhoneCallToPhoneBill(customerName, phoneCall);
                } catch (IOException e)
                {
                  displayToastMessage(e.getMessage());
                }
                displayToastMessage(customerName + " added " + phoneCall.toString());
                dialog.dismiss();
              }
            }).show();
  }

  public void getPhoneBillClicked(View cView)
  {
    final View getLayout = LayoutInflater.from
            (MainActivity.this).inflate(R.layout.activity_get_phone_bill,
            null);

    new MaterialStyledDialog.Builder(MainActivity.this)
            .setIcon(R.drawable.ic_phonebill_foreground)
            .setTitle("Get Phone Bill")
            .autoDismiss(false)
            .setDescription("Please provide the customer name")
            .setCustomView(getLayout)
            .setNegativeText("CANCEL")
            .setCancelable(false)
            .onNegative(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {
                dialog.dismiss();
              }
            })
            .setPositiveText("GET")
            .onPositive(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {

                MaterialEditText getCustomerName = getLayout.findViewById
                        (R.id.get_customer_name);
                if (fieldIsEmpty(getCustomerName)) return;

                PhoneBill phoneBill = null;
                try
                {
                  phoneBill = phoneBillModel.getPhoneBill(Objects.requireNonNull(getCustomerName.getText()).toString());
                } catch (NoSuchElementException e)
                {
                  displayToastMessage(e.getMessage());
                  return;
                }

                  dialog.dismiss();
                  Intent intent = new Intent(MainActivity.this, PrintResults.class);
                  intent.putExtra("customer", getCustomerName.getText().toString());
                  intent.putExtra("PhoneBill", phoneBill);
                  startActivity(intent);
              }
            }).show();
  }

  public void searchButtonClicked(View cView)
  {
    final View getLayout = LayoutInflater.from
            (MainActivity.this).inflate(R.layout.activity_search_phone_bill,
            null);

    new MaterialStyledDialog.Builder(MainActivity.this)
            .setIcon(R.drawable.ic_phonebill_foreground)
            .setTitle("Search Phone Bill")
            .autoDismiss(false)
            .setDescription("Please fill all fields")
            .setCustomView(getLayout)
            .setNegativeText("CANCEL")
            .onNegative(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {
                dialog.dismiss();
              }
            })
            .setPositiveText("SEARCH")
            .onPositive(new MaterialDialog.SingleButtonCallback()
            {
              @Override
              public void onClick(@NonNull MaterialDialog dialog,
                                  @NonNull DialogAction which)
              {
                MaterialEditText searchCustomerName = getLayout.findViewById
                        (R.id.search_customer_name);
                MaterialEditText searchStartTime = getLayout.findViewById
                        (R.id.search_start_time);
                MaterialEditText searchEndTime = getLayout.findViewById
                        (R.id.search_end_time);
                if (fieldIsEmpty(searchCustomerName)) return;
                if (fieldIsEmpty(searchStartTime)) return;
                if (fieldIsEmpty(searchEndTime)) return;

                String customerName = Objects.requireNonNull(searchCustomerName.getText()).toString();
                String startTimeString = Objects.requireNonNull(searchStartTime.getText()).toString();
                String endTimeString = Objects.requireNonNull(searchEndTime.getText()).toString();

                String pattern = "MM/dd/yyyy hh:mm aa";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                simpleDateFormat.setLenient(false);
                Date startTime;
                Date endTime;

                try
                {
                  checkDateTimeFormat(startTimeString, "Invalid Start Time Format");
                  checkDateTimeFormat(endTimeString, "Invalid End Time Format");
                  startTime = simpleDateFormat.parse(startTimeString);
                  endTime = simpleDateFormat.parse(endTimeString);
                } catch (ParseException | IllegalArgumentException e)
                {
                  displayToastMessage(e.getMessage());
                  return;
                }

                PhoneBill phoneBill;
                try
                {
                  phoneBill = phoneBillModel.searchPhoneBill(customerName, startTime, endTime);
                } catch (NoSuchElementException e)
                {
                  displayToastMessage(e.getMessage());
                  return;
                }

                Intent intent = new Intent(MainActivity.this, PrintResults.class);
                intent.putExtra("PhoneBill", phoneBill);
                startActivity(intent);
                dialog.dismiss();
              }
            }).show();
  }

  private boolean fieldIsEmpty(MaterialEditText cEditText)
  {
    if (TextUtils.isEmpty(Objects.requireNonNull(cEditText.getText()).toString()))
    {
      displayToastMessage(cEditText.getHint() + " cannot be empty");
      return true;
    }
    return false;
  }

  private void displayToastMessage(String errorMessage)
  {
    Toast.makeText(MainActivity.this,
            errorMessage,
            Toast.LENGTH_SHORT).show();
  }

  private void checkDateTimeFormat(String dateTime, String errorMessage)
  {
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} (AM|PM|am|pm|aM|Am|pM|Pm)$";
    if (dateTime == null || !dateTime.matches(dateTimeRegex))
    {
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
