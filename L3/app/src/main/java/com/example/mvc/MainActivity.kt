/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 13.11.2023
 *
 * @brief:
 * MainActivity serves as the controller for an Android app form to input student or worker details.
 * The form includes common fields like name, birthday, and specific fields for each occupation.
 * Users choose occupation via radio buttons, and a DatePickerDialog is provided for selecting the birthday.
 * The app validates required fields and occupation before creating a Student or Worker object.
 * It also allows filling the form with a person's details.
 */

package com.example.mvc

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.constraintlayout.widget.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity class for the application.
 */
class MainActivity : AppCompatActivity() {

    // UI element references
    private lateinit var nameEditText: EditText
    private lateinit var firstnameEditText: EditText
    private lateinit var birthdayEditText: EditText
    private lateinit var nationalitySpinner: Spinner
    private lateinit var occupationRadioGroup: RadioGroup
    private lateinit var studentRadioButton: RadioButton
    private lateinit var workerRadioButton: RadioButton
    private lateinit var schoolEditText: EditText
    private lateinit var graduationYearEditText: EditText
    private lateinit var companyEditText: EditText
    private lateinit var sectorSpinner: Spinner
    private lateinit var experienceEditText: EditText
    private lateinit var additionalTitle: TextView
    private lateinit var emailEditText: EditText
    private lateinit var remarksEditText: EditText
    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button
    private lateinit var birthdayCakeButton: ImageButton
    private lateinit var groupStudentSpecific: Group
    private lateinit var groupWorkerSpecific: Group

    /**
     * Initializes the UI elements.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        initializeViews()
        hideSpecificLayouts()
        setListeners()


        // Note you should comment/uncomment if you want to try the filling mode or the prefilling mode
        // Fill form with an example person (Student in this case)
        // fillFormWithPerson(Person.exampleStudent)
        // Fill form with an example person (Worker in this case)
        // fillFormWithPerson(Person.exampleWorker)
    }

    /**
     * Initializes references to UI elements.
     */
    private fun initializeViews() {
        nameEditText = findViewById(R.id.input_edittext_name)
        firstnameEditText = findViewById(R.id.input_edittext_firstname)
        birthdayEditText = findViewById(R.id.input_edittext_birthday_date)
        nationalitySpinner = findViewById(R.id.input_spinner_nationality)
        occupationRadioGroup = findViewById(R.id.input_radio_group_occupation)
        studentRadioButton = findViewById(R.id.input_radio_button_student)
        workerRadioButton = findViewById(R.id.input_radio_button_worker)
        schoolEditText = findViewById(R.id.input_edittext_school_title)
        graduationYearEditText = findViewById(R.id.input_edittext_graduation_year)
        companyEditText = findViewById(R.id.input_edittext_company_title)
        sectorSpinner = findViewById(R.id.input_spinner_sector)
        experienceEditText = findViewById(R.id.input_edittext_experience)
        additionalTitle = findViewById(R.id.additional_title)
        emailEditText = findViewById(R.id.input_edittext_mail)
        remarksEditText = findViewById(R.id.input_edittext_remarks)
        btnCancel = findViewById(R.id.btn_cancel)
        btnOk = findViewById(R.id.btn_ok)
        birthdayCakeButton = findViewById(R.id.birthday_cake_button)
        groupStudentSpecific = findViewById(R.id.group_Student_Specific)
        groupWorkerSpecific = findViewById(R.id.group_Worker_Specific)
    }

    /**
     * Hides occupation-specific layouts on app startup.
     */
    private fun hideSpecificLayouts() {
        groupStudentSpecific.visibility = View.GONE
        groupWorkerSpecific.visibility = View.GONE
    }

    /**
     * Sets up event listeners for buttons and other UI elements.
     */
    private fun setListeners() {
        birthdayCakeButton.setOnClickListener {
            showDatePickerDialog()
        }

        occupationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.input_radio_button_student -> showStudentLayout()
                R.id.input_radio_button_worker -> showWorkerLayout()
            }
        }

        btnCancel.setOnClickListener {
            clearForm()
        }

        btnOk.setOnClickListener {
            createPerson()
        }

        emailEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // If the condition is true, we can call the function to create a person
                createPerson()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    /**
     * Shows student-specific layout and adjusts constraints.
     */
    private fun showStudentLayout() {
        groupStudentSpecific.visibility = View.VISIBLE
        groupWorkerSpecific.visibility = View.GONE
        val params = additionalTitle.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = R.id.input_edittext_graduation_year
    }

    /**
     * Shows worker-specific layout and adjusts constraints.
     */
    private fun showWorkerLayout() {
        groupStudentSpecific.visibility = View.GONE
        groupWorkerSpecific.visibility = View.VISIBLE
        val params = additionalTitle.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = R.id.input_edittext_experience
    }

    /**
     * Clears all form fields and hides occupation-specific layouts.
     */
    private fun clearForm() {
        nameEditText.text.clear()
        firstnameEditText.text.clear()
        birthdayEditText.text.clear()
        nationalitySpinner.setSelection(0)
        occupationRadioGroup.clearCheck()
        schoolEditText.text.clear()
        graduationYearEditText.text.clear()
        companyEditText.text.clear()
        sectorSpinner.setSelection(0)
        experienceEditText.text.clear()
        emailEditText.text.clear()
        remarksEditText.text.clear()
        hideSpecificLayouts();
    }

    /**
     * Checks for required fields, selects occupation, and creates a Student or Worker object.
     */
    private fun createPerson(){
        // Check if any of the required fields are empty
        if (nameEditText.text.isEmpty() || firstnameEditText.text.isEmpty() || birthdayEditText.text.isEmpty() ||
            nationalitySpinner.selectedItemPosition == 0 || emailEditText.text.isEmpty()) {
            Toast.makeText(this, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        when (occupationRadioGroup.checkedRadioButtonId) {
            R.id.input_radio_button_student -> {
                val student = createStudent()
                if(student != null) {
                    Toast.makeText(this, "Objet Student créé", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.input_radio_button_worker -> {
                val worker = createWorker()
                if(worker != null) {
                    Toast.makeText(this, "Objet Worker créé", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "Sélectionner une occupation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Checks if any of the student-specific fields are empty, validates, and creates a Student object.
     */
    private fun createStudent(): Student? {
        if (schoolEditText.text.isEmpty() || graduationYearEditText.text.isEmpty()) {
            Toast.makeText(this, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show()
            return null
        }
        val name = nameEditText.text.toString()
        val firstName = firstnameEditText.text.toString()
        val birthday = convertDateTextToCalendar(birthdayEditText.text.toString())
        val nationality = nationalitySpinner.selectedItem.toString()
        val email = emailEditText.text.toString()
        val remarks = remarksEditText.text.toString()
        val university = schoolEditText.text.toString()
        val graduationYear = Integer.parseInt(graduationYearEditText.text.toString())

        return Student(name, firstName, birthday, nationality, university, graduationYear, email, remarks)
    }

    /**
     * Checks if any of the worker-specific fields are empty, validates, and creates a Worker object.
     */
    private fun createWorker(): Worker? {
        if (companyEditText.text.isEmpty() || sectorSpinner.selectedItemPosition == 0 || experienceEditText.text.isEmpty()) {
            Toast.makeText(this, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show()
            return null
        }
        val name = nameEditText.text.toString()
        val firstName = firstnameEditText.text.toString()
        val birthday = convertDateTextToCalendar(birthdayEditText.text.toString())
        val nationality = nationalitySpinner.selectedItem.toString()
        val email = emailEditText.text.toString()
        val remarks = remarksEditText.text.toString()
        val company = companyEditText.text.toString()
        val sector = sectorSpinner.selectedItem.toString()
        val experienceYear = Integer.parseInt(experienceEditText.text.toString())

        return Worker(name, firstName, birthday, nationality, company, sector, experienceYear, email, remarks)
    }

    /**
     * Shows a DatePickerDialog to pick a date for the birthdayEditText.
     */
    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val userLocale = Locale.getDefault()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the birthdayEditText with the selected date
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", userLocale)
                    .format(Calendar.getInstance().apply {
                        set(Calendar.YEAR, selectedYear)
                        set(Calendar.MONTH, selectedMonth)
                        set(Calendar.DAY_OF_MONTH, selectedDay)
                    }.time)

                birthdayEditText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    /**
     * Converts date text to a Calendar object.
     */
    private fun convertDateTextToCalendar(dateText: String): Calendar {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.time = dateFormat.parse(dateText) ?: Date()
        return calendar
    }

    /**
     * Fills the form with details from a given person (common and occupation-specific fields).
     */
    private fun fillFormWithPerson(person: Person) {
        // Fill common fields
        nameEditText.setText(person.name)
        firstnameEditText.setText(person.firstName)
        birthdayEditText.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(person.birthDay.time))
        nationalitySpinner.setSelection(getIndex(nationalitySpinner, person.nationality))
        emailEditText.setText(person.email)
        remarksEditText.setText(person.remark)

        // Fill occupation-specific fields
        when (person) {
            is Student -> {
                // Fill student-specific fields
                schoolEditText.setText(person.university)
                graduationYearEditText.setText(person.graduationYear.toString())
                occupationRadioGroup.check(R.id.input_radio_button_student)
                showStudentLayout()
            }
            is Worker -> {
                // Fill worker-specific fields
                companyEditText.setText(person.company)
                sectorSpinner.setSelection(getIndex(sectorSpinner, person.sector))
                experienceEditText.setText(person.experienceYear.toString())
                occupationRadioGroup.check(R.id.input_radio_button_worker)
                showWorkerLayout()
            }
        }
    }

    /**
     * Helper function to get the index of an item in a Spinner.
     */
    private fun getIndex(spinner: Spinner, item: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == item) {
                return i
            }
        }
        return 0 // Default to the first item if not found
    }
}
