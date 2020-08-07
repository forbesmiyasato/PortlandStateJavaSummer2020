package edu.pdx.cs410j.miyasato.phonebill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrintResults extends AppCompatActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_results);
    ConstraintLayout resultLayout = (ConstraintLayout) findViewById(R.id.resultsLayout);
    ListView results = findViewById(R.id.results);

    TextView customerName = new TextView(this);
    TypedValue appearanceID = new TypedValue();
    Resources.Theme theme = getTheme();
    if (theme.resolveAttribute(R.attr.textAppearanceListItem, appearanceID, true))
    {
      customerName.setTextAppearance(appearanceID.data);
    }
    PhoneBill phoneBill;
    Intent intent = getIntent();
    phoneBill = (PhoneBill) intent.getSerializableExtra("PhoneBill");

    Resources res = getResources();
    customerName.setText(res.getString(R.string.result_customer_name, Objects.requireNonNull(phoneBill).getCustomer()));

    resultLayout.addView(customerName);
    if (Objects.requireNonNull(phoneBill).getPhoneCalls().size() == 0)
    {
      TextView noPhoneBill = new TextView(this);
      noPhoneBill.setText(res.getString(R.string.no_phone_bills));
    }

    List<PhoneCall> phoneCalls = new ArrayList<PhoneCall>(phoneBill.getPhoneCalls());
    results.setAdapter(new ResultsAdapter(this, android.R.layout.simple_list_item_1, phoneCalls));
  }

  private static class ResultsAdapter extends ArrayAdapter<PhoneCall>
  {
    ResultsAdapter(@NonNull Context context, int resource, @NonNull List<PhoneCall> objects)
    {
      super(context, resource, objects);
    }
  }
}
