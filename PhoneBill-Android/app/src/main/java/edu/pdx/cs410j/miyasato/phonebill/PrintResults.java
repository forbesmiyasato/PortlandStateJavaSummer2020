package edu.pdx.cs410j.miyasato.phonebill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PrintResults extends AppCompatActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_results);

    ListView results = findViewById(R.id.results);

    PhoneBill phoneBill = null;
    Intent intent = getIntent();
    phoneBill = (PhoneBill) intent.getSerializableExtra("PhoneBill");
    assert phoneBill != null;
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
