package comp3350.projectmanagementapplication.presentation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import comp3350.projectmanagementapplication.R;
import comp3350.projectmanagementapplication.business.DatabaseAccessor;
import comp3350.projectmanagementapplication.databinding.ActivityTicketCreationBinding;
import comp3350.projectmanagementapplication.objects.FeatureTicket;
import comp3350.projectmanagementapplication.objects.Priority;
import comp3350.projectmanagementapplication.objects.Status;

public class NewTicketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityTicketCreationBinding binding;
    private Calendar calendar;
    private TextView dateView;
    private EditText nameInput, descInput, costInput;
    private Spinner priorityInput, statusInput;

    // Note months start from 0
    private int year, month, day;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_creation);

        this.binding = ActivityTicketCreationBinding.inflate(this.getLayoutInflater());

        args = this.getIntent().getExtras();

        nameInput = findViewById(R.id.editTextTextPersonName);
        descInput = findViewById(R.id.editTextTextMultiLine);
        priorityInput = findViewById(R.id.spinner_priority);
        costInput = findViewById(R.id.editTextNumber2);
        statusInput = findViewById(R.id.spinner_status);


        // Set the toolbar title
        this.binding.toolbar.setTitle("Create New Ticket");

        // Spinner for Priority drop down
        // Make instance of spinner and apply listen to it
        Spinner priorityDropDown = findViewById(R.id.spinner_priority);
        priorityDropDown.setOnItemSelectedListener(this);
        // Array for options to select
        String[] priorityOptions = new String[]{"Low", "Medium", "High"};
        // Set layout file for the spinner
        ArrayAdapter<String> adapterPriority = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorityOptions);
        // Set the ArrayAdapter
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorityDropDown.setAdapter(adapterPriority);

        // Spinner for status drop down
        // Make instance of spinner and apply listen to it
        Spinner statusDropDown = findViewById(R.id.spinner_status);
        statusDropDown.setOnItemSelectedListener(this);
        // Array for options to select
        String[] statusOptions = new String[]{"TO-DO", "In Progress", "Complete"};
        // Set layout file for the spinner
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);
        // Set the ArrayAdapter
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusDropDown.setAdapter(adapterStatus);

        dateView = findViewById(R.id.textViewDeadline);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    public void cancel(View view) {
        this.finish();
    }

    public void save(View view) {
        String teamName = args.getString("teamName");
        String projectName = args.getString("projectName");
        String cost = costInput.getText().toString();
        Calendar deadline = Calendar.getInstance();
        deadline.set(year, month, day);

        FeatureTicket featureTicket = new FeatureTicket(
                nameInput.getText().toString(), descInput.getText().toString(),
                Priority.valueOf(priorityInput.getSelectedItem().toString()),
                cost.isEmpty() ? 0 : Integer.parseInt(cost),
                Status.from(statusInput.getSelectedItem().toString()),
                deadline
        );

        if (nameInput.getText().toString().isEmpty()) {
            nameInput.setError("Ticket must have a name");
        } else {
            if (new DatabaseAccessor().addFeatureTicket(
                    teamName, projectName, featureTicket
            )) {
                this.finish();
            } else {
                nameInput.setError(String.format("A ticket with the name '%s' already exists", nameInput.getText().toString()));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Auto generated method
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
                    showDate(arg1, arg2 + 1, arg3);

                    year = arg1;
                    month = arg2 + 1;
                    day = arg3;
                }
            };

    private void showDate(int year, int month, int day){
        dateView.setText(assembleDateString(year, month, day));
    }

    private StringBuilder assembleDateString(int year, int month, int day){
        return new StringBuilder().append(day).append("/").append(month).append("/").append(year);
    }

}
