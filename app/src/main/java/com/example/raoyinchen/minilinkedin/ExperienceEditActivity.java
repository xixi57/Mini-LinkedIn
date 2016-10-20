package com.example.raoyinchen.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


import com.example.raoyinchen.minilinkedin.models.Experience;
import com.example.raoyinchen.minilinkedin.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by raoyinchen on 8/22/16.
 */
@SuppressWarnings("ConstantConditions")
public class ExperienceEditActivity extends AppCompatActivity {

    public static final String KEY_EXPERIENCE = "experience";
    private Experience experience;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_edit_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        experience = getIntent().getParcelableExtra(KEY_EXPERIENCE);
        if(experience != null)
        {
            ((TextView)findViewById(R.id.experience_edit_company)).setText(experience.company);
            ((TextView)findViewById(R.id.experience_edit_title)).setText(experience.title);
            ((TextView) findViewById(R.id.experience_edit_start_date)).setText(DateUtils.dateToString(experience.startDate));
            ((TextView) findViewById(R.id.experience_edit_end_date)).setText(DateUtils.dateToString(experience.endDate));
            ((TextView)findViewById(R.id.experience_edit_details)).setText(detailsToString(experience.details));
        }
    }

    private String detailsToString(List<String> details)
    {
        StringBuilder sb = new StringBuilder();
        for(String detail:details)
        {
            sb.append(detail).append("\n");
        }
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit()
    {
        if(experience == null)
        {
            experience = new Experience();
        }

        experience.company = ((EditText) findViewById(R.id.experience_edit_company)).getText().toString();
        experience.title = ((EditText) findViewById(R.id.experience_edit_title)).getText().toString();
        experience.startDate= DateUtils.stringToDate(((EditText) findViewById(R.id.experience_edit_start_date)).getText().toString());
        experience.endDate= DateUtils.stringToDate(((EditText) findViewById(R.id.experience_edit_end_date)).getText().toString());
        experience.details = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.experience_edit_details)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXPERIENCE, experience);
        setResult(Activity.RESULT_OK,resultIntent);

        finish();
    }

}
