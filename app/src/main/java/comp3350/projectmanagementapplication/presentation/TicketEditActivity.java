package comp3350.projectmanagementapplication.presentation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityTicketEditBinding;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Status;

public class TicketEditActivity extends AppCompatActivity {
    private ActivityTicketEditBinding binding;
    private Calendar currentDeadline;
    private EditText editTicketName;
    private EditText editTicketDesc;
    private EditText editCost;
    private TextView editDeadline;
    private DatabaseAccessor databaseAccessor;

    String currTeamName, currProjectName, currTicketName, currTicketDesc;

    Spinner statusSelection, prioritySelection;

    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_edit);

        databaseAccessor = new DatabaseAccessor();

        Bundle args = this.getIntent().getExtras();
        currTicketName = args.getString("ticketName");
        currTicketDesc = args.getString("ticketDescription");
        int currCost = args.getInt("ticketCost");
        String convertedCurrCost = Integer.toString(currCost);
        currProjectName = args.getString("currProjectName");
        currTeamName = args.getString("currTeamName");

        currentDeadline = Calendar.getInstance();

        year = args.getInt("ticketYearDeadline");
        month = args.getInt("ticketMonthDeadline");
        day = args.getInt("ticketDayDeadline");

        currentDeadline.set(year, month, day);

        this.binding = ActivityTicketEditBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        Toolbar toolb = findViewById(R.id.toolbar);
        toolb.setTitle("Edit Ticket");

        editTicketName = (EditText) findViewById(R.id.editTicketName);
        editTicketDesc = (EditText) findViewById(R.id.editTicketDesc);
        editCost = (EditText) findViewById(R.id.editCost);
        editDeadline = (TextView) findViewById(R.id.editDeadline);

        editTicketName.setText(currTicketName);
        editTicketDesc.setText(currTicketDesc);
        editCost.setText(convertedCurrCost);
        editDeadline.setText(day + "/" + month + "/" + year);

        ArrayList<String> statuses = new ArrayList<String>();
        statuses.add("TODO");
        statuses.add("InProgress");
        statuses.add("Complete");

        ArrayList<String> priorities = new ArrayList<String>();
        priorities.add("Low");
        priorities.add("Medium");
        priorities.add("High");

        statusSelection = findViewById(R.id.statusSpinner);
        ArrayAdapter statusAdapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSelection.setAdapter(statusAdapter);

        prioritySelection = findViewById(R.id.prioritySpinner);
        ArrayAdapter priorityAdapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_spinner_item, priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySelection.setAdapter(priorityAdapter);

        Button confirmEdit = (Button) findViewById(R.id.confirmEdit);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editTicketName.getText().toString();
                String newDesc = editTicketDesc.getText().toString();
                Priority newPriority = Priority.valueOf(prioritySelection.getSelectedItem().toString());
                Status newStatus = Status.valueOf(statusSelection.getSelectedItem().toString());
                setDate(view);
                Calendar newDeadline = currentDeadline;

                if (newName.equalsIgnoreCase("") || editCost.getText().toString().equalsIgnoreCase("")) {
                    AlertDialog.Builder inputError = new AlertDialog.Builder(TicketEditActivity.this);
                    inputError.setMessage("Ticket Name cannot be empty, and Cost should be larger than 0.");
                    inputError.setCancelable(true);
                    inputError.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog deletionAlert = inputError.create();
                    deletionAlert.show();
                } else {
                    int newCost = Integer.parseInt(editCost.getText().toString());
                    FeatureTicket toBeUpdated = new FeatureTicket(newName, newDesc, newPriority, newCost, newStatus, newDeadline);
                    boolean check = databaseAccessor.updateFeatureTicket(currTeamName, currProjectName, currTicketName, toBeUpdated);
                    Context ctx = view.getContext();
                    Intent goBackIntent = new Intent(ctx, TicketBoardActivity.class);
                    Bundle args = new Bundle();

                    args.putString("teamName", currTeamName);
                    args.putString("projectName", currProjectName);
                    goBackIntent.putExtras(args);
                    ctx.startActivity(goBackIntent);
                }
            }
        });

        Button deleteTicket = (Button) findViewById(R.id.deleteTicket);
        deleteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder wantToDelete = new AlertDialog.Builder(TicketEditActivity.this);
                    wantToDelete.setMessage("Are you sure you want to delete this ticket?");
                    wantToDelete.setCancelable(true);

                    wantToDelete.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    boolean check = databaseAccessor.removeFeatureTicket(currTeamName, currProjectName, currTicketName);
                                    if (!check) {
                                        Log.d("DELETION ERROR", " Cannot delete");
                                    }
                                    Context ctx = view.getContext();
                                    Intent goBackIntent = new Intent(ctx, TicketBoardActivity.class);
                                    Bundle args = new Bundle();

                                    args.putString("teamName", currTeamName);
                                    args.putString("projectName", currProjectName);
                                    goBackIntent.putExtras(args);
                                    ctx.startActivity(goBackIntent);
                                }
                            });

                    wantToDelete.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog deletionAlert = wantToDelete.create();
                    deletionAlert.show();
                }
        });

        Button cancelEdit = (Button) findViewById(R.id.cancelEdit);
        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private final DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    modifyDate(currentDeadline, year, month, day);
                }
            };

    private void showDate(int year, int month, int day){
        editDeadline.setText(assembleDateString(year, month, day));
    }

    private StringBuilder assembleDateString(int year, int month, int day){
        return new StringBuilder().append(day).append("/").append(month).append("/").append(year);
    }

    private void modifyDate(Calendar cal, int year, int month, int day) {
        cal.set(year, month, day);
    }

}