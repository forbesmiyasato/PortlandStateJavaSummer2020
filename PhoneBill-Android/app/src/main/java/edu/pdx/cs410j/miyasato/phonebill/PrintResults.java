package edu.pdx.cs410j.miyasato.phonebill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PrintResults extends AppCompatActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_results);
    LinearLayout resultLayout = (LinearLayout) findViewById(R.id.resultsLayout);
    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    ListView results = findViewById(R.id.results);

    TextView customerName = findViewById(R.id.result_customer_name);
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

    if (Objects.requireNonNull(phoneBill).getPhoneCalls().isEmpty())
    {
      TextView noPhoneBill = new TextView(this);
      noPhoneBill.setTextAppearance(appearanceID.data);
      noPhoneBill.setText(res.getString(R.string.no_phone_bills));
      resultLayout.addView(noPhoneBill, p);
    }
    else
    {
      List<PhoneCall> phoneCalls = new ArrayList<>(phoneBill.getPhoneCalls());
      results.setAdapter(new ResultsAdapter(this, android.R.layout.simple_list_item_1, phoneCalls));
    }
  }

  private class ResultsAdapter extends ArrayAdapter<PhoneCall>
  {
    private Context context;
    private List<PhoneCall> phoneCalls;

    ResultsAdapter(@NonNull Context context, int resource, @NonNull List<PhoneCall> objects)
    {
      super(context, resource, objects);
      this.context = context;
      this.phoneCalls = objects;
    }

    @Override
    public int getCount()
    {
      return phoneCalls.size();
    }

    @Override
    public PhoneCall getItem(int i)
    {
      return phoneCalls.get(i);
    }

    @Override
    public long getItemId(int i)
    {
      return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container)
    {
      if (convertView == null)
      {
        convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, container, false);
      }

      PhoneCall currentPC = getItem(position);

      TextView view = (TextView) super.getView(position, convertView, container);

      int duration = (int) TimeUnit.MILLISECONDS.toMinutes(Objects.requireNonNull(currentPC)
              .getEndTime().getTime() - currentPC.getStartTime().getTime());

      view.setText(getResources().getString(R.string.duration, currentPC.toString(), duration));

      return view;
    }

  }
}
