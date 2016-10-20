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

import com.example.raoyinchen.minilinkedin.models.Education;
import com.example.raoyinchen.minilinkedin.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by raoyinchen on 8/22/16.
 */
@SuppressWarnings("ConstantConditions")
public class EducationEditActivity extends AppCompatActivity {

    public static final String KEY_EDUCATION = "education";
    private Education education;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_edit_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        education = getIntent().getParcelableExtra(KEY_EDUCATION);
        if(education != null)
        {
            ((TextView)findViewById(R.id.education_edit_school)).setText(education.school);
            ((TextView)findViewById(R.id.education_edit_major)).setText(education.major);
            ((TextView) findViewById(R.id.education_edit_start_date)).setText(DateUtils.dateToString(education.startDate));
            ((TextView) findViewById(R.id.education_edit_end_date)).setText(DateUtils.dateToString(education.endDate));
            ((TextView)findViewById(R.id.education_edit_courses)).setText(coursesToString(education.courses));
        }
    }

    private String coursesToString(List<String> courses)
    {
        StringBuilder sb = new StringBuilder();
        for(String course:courses)
        {
            sb.append(course).append("\n");
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
        if(education == null)
        {
            education = new Education();
        }

        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        education.startDate= DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        education.endDate= DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        education.courses = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.education_edit_courses)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, education);
        setResult(Activity.RESULT_OK,resultIntent);

        finish();
    }

}
