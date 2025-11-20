// Function to show/hide sections
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
}

// Placeholder form handling (replace with actual backend integration)
document.getElementById('addPatientForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const message = document.getElementById('addPatientMessage');
    message.textContent = 'Patient added successfully! ✅';
    message.style.color = 'green';
    // In a real app, send data to Java backend via Fetch API
});

document.getElementById('bookAppointmentForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const message = document.getElementById('bookAppointmentMessage');
    message.textContent = 'Appointment booked successfully! 📅';
    message.style.color = 'green';
    // In a real app, send data to Java backend via Fetch API
});

// Initially show the first section
showSection('addPatient');