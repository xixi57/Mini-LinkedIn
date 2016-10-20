package com.example.raoyinchen.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raoyinchen.minilinkedin.models.BasicInfo;
import com.example.raoyinchen.minilinkedin.models.Education;
import com.example.raoyinchen.minilinkedin.models.Experience;
import com.example.raoyinchen.minilinkedin.models.Project;
import com.example.raoyinchen.minilinkedin.utils.DateUtils;
import com.example.raoyinchen.minilinkedin.utils.ImageUtils;
import com.example.raoyinchen.minilinkedin.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_EXPERIENCE_EDIT = 101;
    private static final int REQ_CODE_PROJECT_EDIT = 102;
    private static final int REQ_CODE_BASIC_INFO = 103;


    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();

        setupUI();
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQ_CODE_EDUCATION_EDIT&& resultCode == Activity.RESULT_OK){
            Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
            updateEducation(education);
        }

        if(requestCode == REQ_CODE_EXPERIENCE_EDIT&& resultCode == Activity.RESULT_OK){
            Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
            updateExperience(experience);
        }

        if(requestCode == REQ_CODE_PROJECT_EDIT&& resultCode == Activity.RESULT_OK){
            Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
            updateProject(project);
        }
        if(requestCode == REQ_CODE_BASIC_INFO && resultCode == Activity.RESULT_OK){
            BasicInfo basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
            updateBasicInfo(basicInfo);
        }

    }


    private void updateBasicInfo(BasicInfo basicInfo) {
        ModelUtils.saveModel(this, MODEL_BASIC_INFO, basicInfo);

        this.basicInfo = basicInfo;
        setupBasicInfo();
    }

    private void updateEducation(Education newEducation){
        boolean found = false;
        for(int i = 0; i < educations.size();++i)
        {
            Education item = educations.get(i);
            if(TextUtils.equals(item.id, newEducation.id))
            {
                educations.set(i,newEducation);
                found = true;
                break;
            }
        }
        if(!found){
            educations.add(newEducation);
        }

        ModelUtils.saveModel(this, MODEL_EDUCATIONS, experiences);
        setupEducations();
    }


    private void updateExperience(Experience newExperience){
        boolean found = false;
        for(int i = 0; i < experiences.size();++i)
        {
            Experience item = experiences.get(i);
            if(TextUtils.equals(item.id, newExperience.id))
            {
                experiences.set(i,newExperience);
                found = true;
                break;
            }
        }
        if(!found){
            experiences.add(newExperience);
        }

        ModelUtils.saveModel(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void updateProject(Project newProject){
        boolean found = false;
        for(int i = 0; i < experiences.size();++i)
        {
            Experience item = experiences.get(i);
            if(TextUtils.equals(item.id, newProject.id))
            {
                projects.set(i,newProject);
                found = true;
                break;
            }
        }
        if(!found){
            projects.add(newProject);
        }

        ModelUtils.saveModel(this, MODEL_PROJECTS, experiences);
        setupProjects();
    }



    private void setupUI() {

        setContentView(R.layout.activity_main);

        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,EducationEditActivity.class);
                startActivityForResult(intent,REQ_CODE_EDUCATION_EDIT);
            }
        });

        findViewById(R.id.add_experience_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v1){
                Intent intent = new Intent(MainActivity.this,ExperienceEditActivity.class);
                startActivityForResult(intent,REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
             public void onClick(View v2){
                    Intent intent = new Intent(MainActivity.this,ProjectEditActivity.class);
                    startActivityForResult(intent,REQ_CODE_PROJECT_EDIT);
        }

        });
        setupBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();
    }

    private void setupBasicInfo()
    {
        ((TextView) findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)
                ? "Your name"
                : basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)
                ? "Your email"
                : basicInfo.email);
        ImageView userPicture = (ImageView) findViewById(R.id.user_picture);
        if (basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        } else {
            userPicture.setImageResource(R.drawable.user_ghost);
        }

        findViewById(R.id.edit_basic_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASIC_INFO);
            }
        });


    }

    private void setupEducations()
    {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations_layout);
        educationsLayout.removeAllViews();
        for(Education education:educations)
        {
            View view = getEducationView(education);
            educationsLayout.addView(view);
        }

    }


    private void setupExperiences()
    {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experiences_layout);
        experiencesLayout.removeAllViews();
        for(Experience experience:experiences)
        {
            View view = getExperienceView(experience);
            experiencesLayout.addView(view);
        }
    }

    private void setupProjects()
    {
        LinearLayout projectsLayout = (LinearLayout) findViewById(R.id.projects_layout);
        projectsLayout.removeAllViews();
        for(Project project:projects)
        {
            View view = getProjectView(project);
            projectsLayout.addView(view);
        }
    }


    private View getEducationView(final Education education)
    {
        View view = getLayoutInflater().inflate(R.layout.education_item,null);

        String dateEdu = DateUtils.dateToString(education.startDate) + '~'
                + DateUtils.dateToString(education.endDate);

        ((TextView) view.findViewById(R.id.education_school))
                .setText(education.school +" " + education.major+ "(" + dateEdu + ")");

        ((TextView) view.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION,education);
                startActivityForResult(intent,REQ_CODE_EDUCATION_EDIT);

            }
        });
        return view;

    }





    private View getExperienceView(final Experience experience)
    {
        View view = getLayoutInflater().inflate(R.layout.experience_item,null);

        String dateExp = DateUtils.dateToString(experience.startDate) + '~'
                + DateUtils.dateToString(experience.endDate);

        ((TextView) view.findViewById(R.id.experience_company))
                .setText(experience.company + " " + experience.title + " " + "(" + dateExp + ")");

        ((TextView) view.findViewById(R.id.experience_details))
                .setText(formatItems(experience.details));

        view.findViewById(R.id.edit_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent = new Intent(MainActivity.this,ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE,experience);
                startActivityForResult(intent,REQ_CODE_EXPERIENCE_EDIT);
            }
        });
        return view;

    }


    private View getProjectView(final Project project)
    {
        View view = getLayoutInflater().inflate(R.layout.project_item,null);

        String dateExp = DateUtils.dateToString(project.startDate) + '~'
                + DateUtils.dateToString(project.endDate);

        ((TextView) view.findViewById(R.id.project_company))
                .setText(project.company + " " + project.name + " " + "(" + dateExp + ")");

        ((TextView) view.findViewById(R.id.project_details))
                .setText(formatItems(project.details));

        view.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent = new Intent(MainActivity.this,ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT,project);
                startActivityForResult(intent,REQ_CODE_PROJECT_EDIT);
            }
        });
        return view;

    }





    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "Xi Wang";
        basicInfo.email = "xixiwang1010 @scu.edu";

        educations = new ArrayList<>();
        Education education1 = new Education();

        education1.courses = new ArrayList<>();

        education1.courses.add("Java");
        education1.courses.add("Python");
        education1.major = "CS";
        education1.school = "lanxiang";
        education1.startDate = DateUtils.stringToDate("09/2013");
        education1.endDate = DateUtils.stringToDate("09/2014");

        Education education2 = new Education();

        education2.courses = new ArrayList<>();

        education2.courses.add("Java");
        education2.courses.add("Python");
        education2.major = "CS";
        education2.school = "THU";
        education2.startDate = DateUtils.stringToDate("09/2016");
        education2.endDate = DateUtils.stringToDate("09/2018");


        Education education3 = new Education();
        education3 = new Education();
        education3.courses = new ArrayList<>();

        education3.courses.add("Java");
        education3.courses.add("Python");
        education3.major = "CS";
        education3.school = "MiN";
        education3.startDate = DateUtils.stringToDate("09/2019");
        education3.endDate = DateUtils.stringToDate("09/2020");




        educations.add(education1);
        educations.add(education2);
        educations.add(education3);




        experiences = new ArrayList<>();
        Experience experience1 = new Experience();
        experience1.details = new ArrayList();
        experience1.company = "facebook";
        experience1.title = "CEO";
        experience1.startDate = DateUtils.stringToDate("09/2013");
        experience1.endDate = DateUtils.stringToDate("09/2014");
        experience1.details = new ArrayList();
        experience1.details.add("build your own");
        experience1.details.add("build your own");
        experience1.details.add("build your own");

        Experience experience2 = new Experience();

        experience2.details = new ArrayList();
        experience2.company = "google";
        experience2.title = "intern";
        experience2.startDate = DateUtils.stringToDate("09/2013");
        experience2.endDate = DateUtils.stringToDate("09/2014");
        experience2.details = new ArrayList();
        experience2.details.add("build your own");
        experience2.details.add("build your own");
        experience2.details.add("build your own");

        experiences.add(experience1);
        experiences.add(experience2);



        projects = new ArrayList<>();

        Project project1 = new Project();
        project1.details = new ArrayList();
        project1.name = "Big data project";
        project1.details.add("build your own");
        project1.details.add("build your own");
        project1.details.add("build your own");
        project1.startDate = DateUtils.stringToDate("09/2013");
        project1.endDate = DateUtils.stringToDate("09/2014");
        projects.add(project1);
    }


    public static String formatItems(List<String> items)
    {
        StringBuilder sb = new StringBuilder();
        for(String item:items)
        {
            sb.append(' ').append('-').append(' ').append(item).append('\n');

        }
        if(sb.length() > 0)
        {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.readModel(this,
                MODEL_BASIC_INFO,
                new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.readModel(this,
                MODEL_EDUCATIONS,
                new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.readModel(this,
                MODEL_EXPERIENCES,
                new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProjects = ModelUtils.readModel(this,
                MODEL_PROJECTS,
                new TypeToken<List<Project>>(){});
        projects = savedProjects == null ? new ArrayList<Project>() : savedProjects;
    }

}
