package comp3350.projectmanagementapplication.presentation;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Project;
import comp3350.projectmanagementapplication.objects.Status;
import comp3350.projectmanagementapplication.objects.User;
import comp3350.projectmanagementapplication.persistence.DataAccess;
import comp3350.projectmanagementapplication.persistence.HSQLDatabase;

public class ProjectStatActivity extends AppCompatActivity {

    private Bundle args;

    int toDONum, inProNum, doneNum;
    String projectName,teamName,description;
    Project currentProject;
    DataAccess dbAccess;
    ArrayList<FeatureTicket> ticketList;

    private PieChart pieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.project_stat);

        loadData();

        pieChart = findViewById(R.id.projectStat_pieChart);


        setupPieChart();
        loadPieChartData();
        showData(teamName,projectName);

    }

    protected void loadData(){
        args = this.getIntent().getExtras();
        dbAccess = new HSQLDatabase();

        projectName = args.getString("projectName");
        teamName = args.getString("teamName");

        currentProject = dbAccess.getProject(teamName,projectName);//a pointer to current project

        //load
        ticketList = dbAccess.getFeatureTickets(teamName,projectName);
        toDONum = 0;
        inProNum = 0;
        doneNum = 0;
        for(FeatureTicket ticket: ticketList){
            if(ticket.getStatus() == Status.TODO){
                toDONum++;
            }else if(ticket.getStatus() == Status.InProgress){
                inProNum++;
            }else if(ticket.getStatus() == Status.Complete){
                doneNum++;
            }
        }
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(24);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Status distribution");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    protected void loadPieChartData() {
        float overallNum = doneNum + inProNum + toDONum;//this is float for division later

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(doneNum/overallNum, "done(" + doneNum + ")"));
        entries.add(new PieEntry(inProNum/overallNum, "in progress(" + inProNum + ")"));
        entries.add(new PieEntry(toDONum/overallNum, "to do (" + toDONum + ")"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "ticket distribution");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(30f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }


    protected void showData(String teamName, String projectName){



        //name and description
        TextView showProjName = (TextView)findViewById(R.id.ProjectName);
        showProjName.setText(projectName);
        TextView showDescription = (TextView)findViewById(R.id.Description);
        showDescription.setText(currentProject.getDescription());

        //each status num
        String toDoSpace = "";
        String inProSpace = "";
        String doneSpace = "";
        for(FeatureTicket ticket: ticketList){
            if(ticket.getStatus() == Status.TODO){
                toDoSpace += "- - ";
            }else if(ticket.getStatus() == Status.InProgress){
                inProSpace += "- - ";
            }else if(ticket.getStatus() == Status.Complete){
                doneSpace += "- - ";
            }
        }

        //members list
        ArrayList<User> memberList = dbAccess.getTeam(teamName).getUsers();
        int teamSize = 0;
        if(memberList != null) teamSize = memberList.size();


        String statusFakeGraph = teamSize + " people are working on this" +
                "\n" + toDoSpace + "to do: " + toDONum +
                "\n" + inProSpace + "in progress: " + inProNum +
                "\n" + doneSpace + "completed: " + doneNum;
        TextView fakeGraph = (TextView) findViewById(R.id.statusStat);
        fakeGraph.setText(statusFakeGraph);


    }
}