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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{

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
        Snackbar.make(view, "This is a README!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });
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

  public void onReadme() {
    startActivity (new Intent(this,
            Readme.class));
  }

  public void createAccountClicked (View cView)
  {
    final View registerLayout = LayoutInflater.from
            (MainActivity.this).inflate (R.layout.activity_add_phone_call,
            null);

    new MaterialStyledDialog.Builder (MainActivity.this)
            .setIcon (R.drawable.ic_phonebill_foreground)
            .setTitle ("Add a Phone Bill")
            .setDescription ("Please fill all fields")
            .setCustomView (registerLayout)
            .setNegativeText ("CANCEL")
            .onNegative (new MaterialDialog.SingleButtonCallback ()
            {
              @Override
              public void onClick (@NonNull MaterialDialog dialog,
                                   @NonNull DialogAction which)
              {
                dialog.dismiss ();
              }
            })
            .setPositiveText ("ADD")
            .onPositive (new MaterialDialog.SingleButtonCallback ()
            {
              @Override
              public void onClick (@NonNull MaterialDialog dialog,
                                   @NonNull DialogAction which)
              {
                MaterialEditText addCustomerName = registerLayout.findViewById
                        (R.id.add_customer_name);
                MaterialEditText addCaller = registerLayout.findViewById
                        (R.id.add_caller);
                MaterialEditText addCallee = registerLayout.findViewById
                        (R.id.add_callee);
                MaterialEditText addStartTime = registerLayout.findViewById
                        (R.id.add_start_time);
                MaterialEditText addEndTime = registerLayout.findViewById
                        (R.id.add_end_time);

                if (fieldIsEmpty(addCustomerName)) return;
                if (fieldIsEmpty(addCaller)) return;
                if (fieldIsEmpty(addCallee)) return;
                if (fieldIsEmpty(addStartTime)) return;
                if (fieldIsEmpty(addEndTime)) return;


//                registerUser (editRegisterName.getText ().toString (),
//                        editRegisterPassword.getText ().toString ());
              }
            }).show ();
  }

  private boolean fieldIsEmpty(MaterialEditText cEditText) {
    if (TextUtils.isEmpty (cEditText.getText ().toString ()))
    {
      Toast.makeText (MainActivity.this,
              cEditText.getHint() + " cannot be empty",
              Toast.LENGTH_SHORT).show ();
      return true;
    }
    return false;
  }

//  String pattern = "MM/dd/yyyy hh:mm aa";
//  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//    simpleDateFormat.setLenient(false);
//  Date startTime = simpleDateFormat.parse(startTimeString);
//  Date endTime = simpleDateFormat.parse(endTimeString);
}
