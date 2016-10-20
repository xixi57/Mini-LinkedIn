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
import com.example.raoyinchen.minilinkedin.models.Project;
import com.example.raoyinchen.minilinkedin.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by raoyinchen on 8/22/16.
 */
@SuppressWarnings("ConstantConditions")
public class ProjectEditActivity extends AppCompatActivity {

    public static final String KEY_PROJECT = "project";
    private Project project;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_edit_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = getIntent().getParcelableExtra(KEY_PROJECT);
        if(project != null)
        {
            ((TextView)findViewById(R.id.project_edit_company)).setText(project.company);
            ((TextView)findViewById(R.id.project_edit_name)).setText(project.name);
            ((TextView) findViewById(R.id.project_edit_start_date)).setText(DateUtils.dateToString(project.startDate));
            ((TextView) findViewById(R.id.project_edit_end_date)).setText(DateUtils.dateToString(project.endDate));
            ((TextView)findViewById(R.id.project_edit_details)).setText(detailsToString(project.details));
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
        if(project == null)
        {
            project = new Project();
        }

        project.company = ((EditText) findViewById(R.id.project_edit_company)).getText().toString();
        project.name = ((EditText) findViewById(R.id.project_edit_name)).getText().toString();
        project.startDate= DateUtils.stringToDate(((EditText) findViewById(R.id.project_edit_start_date)).getText().toString());
        project.endDate= DateUtils.stringToDate(((EditText) findViewById(R.id.project_edit_end_date)).getText().toString());
        project.details = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.project_edit_details)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_PROJECT, project);
        setResult(Activity.RESULT_OK,resultIntent);

        finish();
    }

}
